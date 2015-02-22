package com.qwertyness.portalcommands.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.InteractSender;
import com.qwertyness.portalcommands.PortalCommands;
import com.qwertyness.portalcommands.portal.Portal;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PortalUtil {
	private WorldEditPlugin worldEdit;
	
	public PortalUtil(PortalCommands plugin) {
		this.worldEdit = plugin.getWorldEdit();
	}
	
	//////////////////////////////
	// Create
	//////////////////////////////
	
	public boolean create(Player player, Portal portal, String name) {
		portal.name = name;
		try {
			portal.setMaximum(worldEdit.getSelection(player).getMaximumPoint().toVector());
			portal.setMinimum(worldEdit.getSelection(player).getMinimumPoint().toVector());
			portal.setWorld(worldEdit.getSelection(player).getWorld().getName());
			portal.setCommands(new ArrayList<InteractCommand>());
			portal.setMessages(new ArrayList<String>());
			portal.setCooldown(0);
			portal.save();
			return true;
		} catch(NullPointerException e) {
			player.sendMessage(ChatColor.RED + "Error occurred. Did you make a WorldEdit selection?");
			return false;
		}
	}
	
	//////////////////////////////
	// Command Util
	//////////////////////////////
	
	public static String getPortalCommand(String[] args) {
		int startingArgument = 2;
		if (args[startingArgument].equalsIgnoreCase("-t")) {
			startingArgument = startingArgument + 2;
		}
		if (args[startingArgument].charAt(0) == '/') {
			char[] chars = args[startingArgument].toCharArray();
			args[startingArgument] = "";
			for (int counter = 1;counter < chars.length;counter++) {
				args[startingArgument] += chars[counter];
			}
		}
		String command = args[startingArgument];
		for (int counter = startingArgument + 1;counter < args.length;counter++) {
			command += " " + args[counter];
		}
		return command;
	}
	
	public static List<Entry<String, InteractSender>> entryStringConverter(List<String> stringList) {
		List<Entry<String, InteractSender>> entryList = new ArrayList<Entry<String, InteractSender>>();
		for (String string : stringList) {
			String command = "";
			String sender = "";
			boolean secondString = false;
			for (char character : string.toCharArray()) {
				if (character == ':') {
					secondString = true;
					continue;
				}
				
				if (secondString) {
					sender += character;
					continue;
				}
				command += character;
			}
			HashMap<String, InteractSender> map = new HashMap<String, InteractSender>();
			map.put(command, InteractSender.getInteractSender(sender));
			entryList.add(new ArrayList<Entry<String, InteractSender>>(map.entrySet()).get(0));
		}
		return entryList;
	}
}
