package net.kingdomofkingdoms.Qwertyness_.portalcommands.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractCommand;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractSender;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

public class VersionConvertUtil {
	private PortalCommands plugin;
	
	public VersionConvertUtil(PortalCommands plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		this.convBefListToList();
		this.conv164to172();
		this.conv172toAPI();
	}
	
	public void convBefListToList() {
		List<String> portals;
		try {
			portals = new ArrayList<String>(this.plugin.getConfig().getConfigurationSection("Portals").getKeys(false));
		} catch(NullPointerException e) {return;}
		
		for (String portalString : portals) {
			if (!plugin.getConfig().isList("Portals." + portalString + ".Command")) {
				InteractSender sender;
				if (plugin.getConfig().getString("Portals." + portalString + ".Sender") != null) {
					sender = InteractSender.getInteractSender(plugin.getConfig().getString("Portals." + portalString + ".Sender"));
				}
				else {
					sender = InteractSender.PLAYER;
				}
				plugin.getConfig().set("Portals." + portalString + ".Sender", null);
				List<HashMap<String, String>> commands = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> command = new HashMap<String, String>();
				command.put(plugin.getConfig().getString("Portals." + portalString + ".Command"), sender.toString());
				commands.add(command);
				plugin.getConfig().set("Portals." + portalString + ".Command", commands);
				plugin.getConfig().set("Portals." + portalString + ".Command", commands);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void conv164to172() {
		List<String> portals;
		try {
			portals = new ArrayList<String>(this.plugin.getConfig().getConfigurationSection("Portals").getKeys(false));
		} catch(NullPointerException e) {return;}
		for (String portalString : portals) {
			if (plugin.getConfig().getString("Portals." + portalString + ".Sender") != null) {
				List<String> commands = (List<String>) plugin.getConfig().getList("Portals." + portalString + ".Command");
				InteractSender sender = InteractSender.getInteractSender(plugin.getConfig().getString("Portals." + portalString + ".Sender"));
				List<Map<String, String>> mappedCommands = new ArrayList<Map<String, String>>();
				
				for (String command : commands) {
					Map<String, String> map = new HashMap<String, String>();
					map.put(command, sender.toString());
					mappedCommands.add(map);
				}
				
				plugin.getConfig().set("Portals." + portalString + ".Command", mappedCommands);
				
				plugin.getConfig().set("Portals." + portalString + ".Sender", null);
				plugin.saveConfig();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void conv172toAPI() {
		if (this.plugin.getConfig().getConfigurationSection("Portals") == null) {
			return;
		}
		List<String> portals = new ArrayList<String>(this.plugin.getConfig().getConfigurationSection("Portals").getKeys(false));
		portals.remove("PortalList");
		for (String portalName : portals) {
			List<String> input = new ArrayList<String>();
			
			// Manually read command strings from file. Bukkit FileConfiguration.getMapList() causing null keys.
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(new File(this.plugin.getDataFolder().getPath(), "config.yml"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (fis == null) {
				return;
			}
			BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
			String line;
			boolean inPortal = false;
			boolean inSection = false;
			int spacing = 0;
			try {
				while((line = buf.readLine()) != null) {
					if (line.contains(portalName)) {
						inPortal = true;
						continue;
					}
					if (inPortal) {
						if (line.contains("Command:")) {
							inSection = true;
							continue;
						}
						if (inSection) {
							if (spacing == 0) {
								spacing = getSpacing(line);
								input.add(removeListMarkers(removeSpacing(line)));
								continue;
							}
							if (getSpacing(line) == spacing && !line.contains("Max:")) {
								input.add(removeListMarkers(removeSpacing(line)));
							}
							else {
								break;
							}
						}
					}
				}
			} catch (IOException e) {}
			
			List<InteractCommand> commands = toCommandList(input);
			// End manual file read.
			
			Portal portal = new Portal(plugin);
			portal.name = portalName;
			portal.setCommands(commands);
			portal.setMessages((List<String>)this.plugin.getConfig().getList("Portals." + portalName + ".Message"));
			portal.setMinimum(this.plugin.getConfig().getVector("Portals." + portalName + ".Min"));
			portal.setMaximum(this.plugin.getConfig().getVector("Portals." + portalName + ".Max"));
			portal.setWorld(this.plugin.getConfig().getString("Portals." + portalName + ".World"));
			portal.setCooldown(this.plugin.getConfig().getInt("Portals." + portalName + ".cooldown"));
			portal.save();
			this.plugin.getConfig().set("Portals." + portalName, null);
		}
		this.plugin.getConfig().set("Portals", null);
		this.plugin.saveConfig();
	}
	
	public List<InteractCommand> toCommandList(List<String> input) {
		List<InteractCommand> output = new ArrayList<InteractCommand>();
		for (String string : input) {
			boolean onSpace = false;
			boolean onValue = false;
			String key = "";
			String value = "";
			for (char character : string.toCharArray()) {
				if (onValue) {
					value += character;
					continue;
				}
				if (character == ':') {
					onSpace = true;
					continue;
				}
				if (onSpace) {
					onSpace = false;
					onValue = true;
					continue;
				}
				key += character;
			}
			output.add(new InteractCommand(key, InteractSender.getInteractSender(value)));
		}
		return output;
	}
	
	public int getSpacing(String input) {
		int spacing = 0;
		for (char ch : input.toCharArray()) {
			if (ch != ' ') {
				break;
			}
			spacing++;
		}
		return spacing;
	}
	
	public String removeSpacing(String input) {
		String output = "";
		for (int counter = getSpacing(input);counter < input.length();counter++) {
			output += input.toCharArray()[counter];
		}
		return output;
	}
	
	public String removeListMarkers(String input) {
		String output = "";
		for (int counter = 0;counter < input.length();counter++) {
			if (counter > 1) {
				output += input.toCharArray()[counter];
			}
		}
		return output;
	}
	
}
