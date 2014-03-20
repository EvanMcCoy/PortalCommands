////////////////////////////////////////////////////////////////////////////////////////
// PortalCommands: Bukkit Portal Plugin                                              ///
// Copyright (C) 2013  Qwertyness_                                                   ///
//                                                                                   ///
//     This program is free software: you can redistribute it and/or modify          ///
//    it under the terms of the GNU General Public License as published by           ///
//    the Free Software Foundation, either version 3 of the License, or              ///
//    (at your option) any later version.                                            ///
//                                                                                   ///
//    This program is distributed in the hope that it will be useful,                ///
//    but WITHOUT ANY WARRANTY; without even the implied warranty of                 ///
//    MERpluginANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  ///
//    GNU General Public License for more details.                                   ///
//                                                                                   ///
//    You should have received a copy of the GNU General Public License	             ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package net.kingdomofkingdoms.Qwertyness_.portalcommands.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import net.kingdomofkingdoms.Qwertyness_.interactables.InteractablesPlugin;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractCommand;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.Interactable;

import org.bukkit.ChatColor;

public class Portal implements Interactable {
	private InteractablesPlugin plugin;
	
	//Attributes
	public String name;
	private int cooldown;
	private List<InteractCommand> commands = new ArrayList<InteractCommand>();
	private List<String> messages = new ArrayList<String>();
	
	//Location
	protected Vector maximum = new Vector();
	protected Vector minimum = new Vector();
	protected String world;
	
	//Data grabbing
	public String basePath;
	
	public Portal(InteractablesPlugin plugin) {
		this.plugin = plugin;
	}
	
	public Portal(String name, InteractablesPlugin plugin) {
		this.plugin = plugin;
		this.name = name;
		this.basePath = "Portals." + this.getName();
		this.initialize();
	}
	
	public Portal(Portal portal) {
		this.commands = portal.getCommands();
		this.messages = portal.getMessages();
		this.cooldown = portal.getCooldown();
		this.name = portal.getName();
		this.plugin = portal.getPlugin();
		this.maximum = portal.getMaximum();
		this.minimum = portal.getMinimum();
		this.world = portal.getWorld();
	}
	
	//////////////////////////////
	// Name Methods
	//////////////////////////////
	
	public String getName() {
		return name;
	}
	
	//////////////////////////////
	// Command Methods
	//////////////////////////////
	
	public List<InteractCommand> getCommands() {
		return this.commands;
	}
	
	public void addCommand(InteractCommand command) {
		this.commands.add(command);
	}

	public void removeCommand(int index) {
		this.commands.remove(index);
	}
	
	public void setCommands(List<InteractCommand> commands) {
		this.commands = commands;
	}
	
	//////////////////////////////
	// Message Methods
	//////////////////////////////

	public List<String> getMessages() {
		try {
			for (int counter = 0;counter < this.messages.size();counter++) {
				String message = ChatColor.translateAlternateColorCodes('&', this.messages.get(counter));
				this.messages.set(counter, message);
			}
		} catch(NullPointerException e) {}
		return this.messages;
	}
	
	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public void removeMessage(int index) {
		this.messages.remove(index);
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	//////////////////////////////
	// Location Methods
	//////////////////////////////
	
	public Vector getMinimum() {
		return minimum;
	}
	
	public void setMinimum(Vector minimum) {
		this.minimum = minimum;
	}
	
	public Vector getMaximum() {
		return maximum;
	}
	
	public void setMaximum(Vector maximum) {
		this.maximum = maximum;
	}
	
	public String getWorld() {
		return world;
	}
	
	public void setWorld(String world) {
		this.world = world;
	}
	
	//////////////////////////////
	// Cooldown Methods
	//////////////////////////////
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	
	public InteractablesPlugin getPlugin() {
		return this.plugin;
	}
	
	//////////////////////////////
	// Utils
	//////////////////////////////

	public void save() {
		this.basePath = "Portals." + this.getName();
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put(this.basePath + ".commands", InteractCommand.toStringList(this.getCommands()));
		values.put(this.basePath + ".messages", this.messages);
		values.put(this.basePath + ".cooldown", this.cooldown);
		values.put(this.basePath + ".min", this.minimum);
		values.put(this.basePath + ".max", this.maximum);
		values.put(this.basePath + ".world", this.world);
		this.plugin.getInteractablesAPI().getInteractableManager().saveInteractable(this.plugin, values);
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		FileConfiguration dataFile = this.getPlugin().getInteractablesAPI().dataFiles.get(this.plugin).get();
		this.setCommands(InteractCommand.toCommandList(dataFile.getStringList(this.basePath + ".commands")));
		this.messages = (List<String>) dataFile.getList(this.basePath + ".messages");
		this.cooldown = dataFile.getInt(this.basePath);
		this.minimum = dataFile.getVector(this.basePath + ".min");
		this.maximum = dataFile.getVector(this.basePath + ".max");
		this.world = dataFile.getString(this.basePath + ".world");
	}
}
