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
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  ///
//    GNU General Public License for more details.                                   ///
//                                                                                   ///
//    You should have received a copy of the GNU General Public License	             ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package net.kingdomofkingdoms.Qwertyness_.portalcommands.portal;

import java.util.Arrays;
import java.util.List;

import org.bukkit.util.Vector;

import net.kingdomofkingdoms.Qwertyness_.portalcommands.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Portal {
	CommandHandler ch;
	WorldEditPlugin worldedit;
	public Selection portalselection;
	Player player;
	public String name;
	public String world;
	public List<String> portalcommands;
	public List<String> messages;
	public PortalSender sender;
	public Vector maximum = new Vector();
	public Vector minimum = new Vector();
	
	public Portal(Player play, CommandHandler command) {
		ch = command;
		player = play;
		worldedit = ch.getWorldEdit();
	}
	@SuppressWarnings("unchecked")
	public Portal(String portalname, CommandHandler command) {
		ch = command;
		name = portalname;
		portalcommands = (List<String>) ch.getConfig().getList("Portals." + name + ".Command");
		messages = (List<String>) ch.getConfig().getList("Portals." + name + ".Message");
		sender = PortalSender.toPortalSender(ch.getConfig().getString("Portals." + name + ".Sender"));
		minimum = ch.getConfig().getVector("Portals." + name + ".Min");
		maximum = ch.getConfig().getVector("Portals." + name + ".Max");
		world = ch.getConfig().getString("Portals." + name + ".World");
	}
	
	public void create() {
		try{
			portalselection = worldedit.getSelection(player);
			setMaximum(portalselection.getMaximumPoint().toVector());
			setMinimum(portalselection.getMinimumPoint().toVector());
			setWorld(portalselection.getWorld().getName());
			setMessages(null);
			log();
			player.sendMessage(ChatColor.GREEN + "Portal "  + ChatColor.GOLD + name + ChatColor.GREEN + " Created!");
			player.sendMessage(ChatColor.GREEN + "Commands: "  + ChatColor.GOLD + portalcommands);
		}catch (NullPointerException e){
			player.sendMessage(ChatColor.RED + "You must have a region selected to create a portal!");
		}
			
		
	}
	public void delete() {
		try {
			ch.getConfig().set("Portals." + name, null);
			ch.saveConfig();
			List<String> portals = getPortals();
			for (int counter = 0;counter < portals.size();counter++) {
				if (portals.get(counter).equalsIgnoreCase(name)) {
					portals.remove(counter);
				}
			}
			setPortals(portals);
		}catch (NullPointerException e) {
			player.sendMessage(ChatColor.RED + "That portal does not exist!");
			e.printStackTrace();
		}
	}
	public List<String> getCommands() {
		return portalcommands;
	}
	public void addCommand(String command) {
		portalcommands.add(command);
		this.setCommands(portalcommands);
	}
	public void removeCommand(int index) {
		portalcommands.remove(index);
		this.setCommands(portalcommands);
	}
	public void setCommands(List<String> cmds) {
		portalcommands = cmds;
		ch.getConfig().set("Portals." + name + ".Command", portalcommands);
		ch.saveConfig();
	}
	public String getName() {
		return name;
	}
	public void setName(String portalname) {
		name = portalname;
	}
	public List<String> getMessages() {
		for (int counter = 0;counter < messages.size();counter++) {
			String message = ChatColor.translateAlternateColorCodes('&', messages.get(counter));
			messages.set(counter, message);
		}
		return messages;
	}
	public void addMessage(String message) {
		try { 
			messages.add(message);
			this.setMessages(messages);
		} catch (NullPointerException e) {
			setMessages(Arrays.asList(message));
		}
	}
	public void removeMessage(int index) {
		messages.remove(index);
		this.setMessages(messages);
	}
	public void setMessages(List<String> messages) {
		ch.getConfig().set("Portals." + name + ".Message", messages);
		ch.saveConfig();
	}
	public PortalSender getType() {
		return sender;
	}
	public void setType(PortalSender type) {
		ch.getConfig().set("Portals." + name + ".Sender", type.toString());
		ch.saveConfig();
	}
	public Vector getMinimum() {
		return minimum;
	}
	public void setMinimum(Vector min) {
		minimum = min;
		ch.getConfig().set("Portals." + name + ".Min", minimum);
		ch.saveConfig();
	}
	public Vector getMaximum() {
		return maximum;
	}
	public void setMaximum(Vector max) {
		maximum = max;
		ch.getConfig().set("Portals." + name + ".Max", maximum);
		ch.saveConfig();
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String worldname) {
		world = worldname;
		ch.getConfig().set("Portals." + name + ".World", world);
		ch.saveConfig();
	}
	public void log() {
		List<String> portals = getPortals();
		portals.add(name);
		setPortals(portals);
		ch.saveConfig();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPortals() {
		List<String> portals = (List<String>) ch.getConfig().getList("Portals.PortalList");
		return portals;
	}
	public void setPortals(List<String> portals) {
		ch.getConfig().set("Portals.PortalList", portals);
		ch.saveConfig();
	}
	public boolean exists() {
		if (getPortals().contains(name)) {
			return true;
		}
		else {
			return false;
		}
	}
}
