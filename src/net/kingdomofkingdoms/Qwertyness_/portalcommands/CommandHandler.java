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
//    You should have received a copy of the GNU General Public License              ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package net.kingdomofkingdoms.Qwertyness_.portalcommands;

import java.util.Arrays;
import java.util.logging.Logger;

import net.kingdomofkingdoms.Qwertyness_.portalcommands.listener.PortalListener;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.permission.PortalPermission;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.permission.PortalPlayer;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.PortalSender;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class CommandHandler extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	public Plugin worldedit;
	
	@SuppressWarnings("unused")
	public void onEnable() {
		saveDefaultConfig();
		this.logger.info("[CommandPortals] Enabling listeners...");
		PortalListener pl = new PortalListener(this);
        worldedit = getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit == null) {
            this.logger.warning("[CommandPortals] WorldEdit not detected! CommandPortals will not work without WorldEdit!");
        }
	}
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		reloadConfig();
		Player player = (Player) sender;
		PortalPlayer pp = new PortalPlayer(player);
		if (commandLabel.equalsIgnoreCase("cmdportal")) {
			Portal portal = new Portal(player, this);
			if (args.length < 1) {
				player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal <action>");
				player.sendMessage(ChatColor.RED + "Use /cmdportal help to see available actions.");
			}
			else {
				if (args[0].equalsIgnoreCase("create")) {
					if (args.length >= 3) {
						if (!args[1].equalsIgnoreCase("-t")) {
							portal.setName(args[1]);
							if (!portal.exists()) {
								if (args[2].equalsIgnoreCase("-t")) {
									if (args[3].equalsIgnoreCase("player")) {
										if (pp.hasPortalPermission(PortalPermission.PORTAL_CREATE_PLAYER)) {
											portal.setType(PortalSender.toPortalSender(args[3]));
											portal.setCommands(Arrays.asList(getPortalCommand(args)));
											portal.create();
										}
										else {
											player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
										}
									}
									else if (args[3].equalsIgnoreCase("console")) {
										if (pp.hasPortalPermission(PortalPermission.PORTAL_CREATE_CONSOLE)) {
											portal.setType(PortalSender.toPortalSender(args[3]));
											portal.setCommands(Arrays.asList(getPortalCommand(args)));
											portal.create();
										}
										else {
											player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
										}
									}
									else {
										player.sendMessage(ChatColor.RED + "Invalid portal type! Types: player, console");
									}
								}
								else {
									if (pp.hasPortalPermission(PortalPermission.PORTAL_CREATE_PLAYER)) {
										portal.setType(PortalSender.PLAYER);
										portal.setCommands(Arrays.asList(getPortalCommand(args)));
										portal.create();
									}
									else {
										player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
									}
								}
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal already exists!");
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "Cannot use " + ChatColor.GOLD + "-t" + ChatColor.RED + " as a portal name!");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal create <name> <command>");
					}
				}
				
				else if (args[0].equalsIgnoreCase("delete")) {
					if (args.length <= 2) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_DELETE)) {
						if (args.length == 2) {
							portal.setName(args[1]);
							if (portal.exists()) {
								player.sendMessage(ChatColor.GOLD + "You have deleted the portal " + ChatColor.RED + args[1] + ChatColor.RED + "!");
								portal.delete();
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "Too many arguments! /cmdportal delete <name>");
						}
					}
					}
					else {
						player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal delete <name>");
					}
				}
				else if (args[0].equalsIgnoreCase("setcommand")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_COMMAND)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal setcommand <name> <command>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								portal.setCommands(Arrays.asList(getPortalCommand(args)));
								player.sendMessage(ChatColor.GREEN + "Portal " + ChatColor.GOLD + portal.name + ChatColor.GREEN + " command set to: " + ChatColor.GOLD + getPortalCommand(args) + ChatColor.GREEN + "!");
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("addcommand")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_COMMAND)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal addcommand <name> <command>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								Portal currentPortal = new Portal(args[1], this);
								currentPortal.addCommand(getPortalCommand(args));
								player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + getPortalCommand(args) + ChatColor.GREEN + " added to portal " + ChatColor.GOLD + currentPortal.name + ChatColor.GREEN + "!");
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("removecommand")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_COMMAND)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal removecommand <name> <command_index>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								Portal currentPortal = new Portal(args[1], this);
								try {
									String command = currentPortal.getCommands().get(Integer.parseInt(args[2]) - 1);
									currentPortal.removeCommand(Integer.parseInt(args[2]) - 1);
									player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + command + ChatColor.GREEN + " removed from portal " + ChatColor.GOLD + portal.name + ChatColor.GREEN + "!");
								} catch (Exception e) {
									e.printStackTrace();
									player.sendMessage("Command index must be a whole number and be within the highest command index!");
								}
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("listcommands")) {
					if (args.length > 1) {
						portal.setName(args[1]);
						if (portal.exists()) {
							Portal currentPortal = new Portal(args[1], this);
							player.sendMessage(ChatColor.GREEN + "------- " + ChatColor.GOLD + currentPortal.name + " - Commands" + ChatColor.GREEN + " -------");
							for (int counter = 0;counter < currentPortal.getCommands().size();counter++) {
								player.sendMessage(ChatColor.GREEN + new Integer(counter + 1).toString() + ": " + ChatColor.GOLD + currentPortal.getCommands().get(counter));
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "That portal doe not exist!");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Too fe arguments! /cmdportal listcommands <name>");
					}
				}
				else if (args[0].equalsIgnoreCase("setmessage")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_MESSAGE)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal setmessage <name> <message>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								portal.setMessages(Arrays.asList(getPortalCommand(args)));
								player.sendMessage(ChatColor.GREEN + "Portal " + ChatColor.GOLD + portal.name + ChatColor.GREEN + " message set to: " + ChatColor.GOLD + getPortalCommand(args) + ChatColor.GREEN + "!");
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("addmessage")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_MESSAGE)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal addmessage <name> <command>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								Portal currentPortal = new Portal(args[1], this);
								currentPortal.addMessage(getPortalCommand(args));
								player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + getPortalCommand(args) + ChatColor.GREEN + " added to portal " + ChatColor.GOLD + currentPortal.name + ChatColor.GREEN + "!");
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("removemessage")) {
					if (pp.hasPortalPermission(PortalPermission.PORTAL_EDIT_MESSAGE)) {
						if (args.length < 3) {
							player.sendMessage(ChatColor.RED + "Too few arguments! /cmdportal removemessage <name> <command_index>");
						}
						else {
							portal.setName(args[1]);
							if (portal.exists()) {
								Portal currentPortal = new Portal(args[1], this);
								try {
									String message = currentPortal.getMessages().get(Integer.parseInt(args[2]) - 1);
									currentPortal.removeMessage(Integer.parseInt(args[2]) - 1);
									player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + message + ChatColor.GREEN + " removed from portal " + ChatColor.GOLD + portal.name + ChatColor.GREEN + "!");
								} catch (Exception e) {
									player.sendMessage(ChatColor.RED + "Message index must be a whole number and be within the highest message index!");
								}
							}
							else {
								player.sendMessage(ChatColor.RED + "That portal does not exist!");
							}
						}
					}
				}
				else if (args[0].equalsIgnoreCase("listmessages")) {
					if (args.length > 1) {
						portal.setName(args[1]);
						if (portal.exists()) {
							Portal currentPortal = new Portal(args[1], this);
							player.sendMessage(ChatColor.GREEN + "------- " + ChatColor.GOLD + currentPortal.name + " - Messages" + ChatColor.GREEN + " -------");
							for (int counter = 0;counter < currentPortal.getMessages().size();counter++) {
								player.sendMessage(ChatColor.GREEN + new Integer(counter + 1).toString() + ": " + ChatColor.GOLD + currentPortal.getMessages().get(counter));
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "That portal doe not exist!");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Too fe arguments! /cmdportal listmessages <name>");
					}
				}
				else if (args[0].equalsIgnoreCase("info")) {
					if (args.length == 2) {
						portal.setName(args[1]);
						if (portal.exists()) {
							Portal currentPortal = new Portal(args[1], this);
							player.sendMessage(ChatColor.GREEN + "----- Portal Info: " + portal.name + " -----");
							player.sendMessage(ChatColor.GREEN + "Commands: " + currentPortal.getCommands());
							player.sendMessage(ChatColor.GREEN + "Message: " + currentPortal.getMessages());
							player.sendMessage(ChatColor.GREEN + "Sender: " + currentPortal.getType());
							player.sendMessage(ChatColor.GREEN + "Maximum: " + currentPortal.getMaximum());
							player.sendMessage(ChatColor.GREEN + "Minimum: " + currentPortal.getMinimum());
						}
						else {
							player.sendMessage(ChatColor.RED + "That portal doe not exist!");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + "Too many arguments! /cmdportal info <name>");
					}
				}
				else if (args[0].equalsIgnoreCase("list")) {
					player.sendMessage(ChatColor.GREEN + "Portals: " + portal.getPortals());
				}
				else if (args[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.GREEN + "----- PortalCommands Help -----");
					player.sendMessage(ChatColor.GREEN + "/cmdportal help - Shows this help page!");
					player.sendMessage(ChatColor.GREEN + "/cmdportal create <name> <OPTIONAL: -t [player or console]> <command> - Creates a portal with the specified attributes.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal delete <name> - Deletes the specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal setcommand <name> <command> - Sets the command of the specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal addcommand <name> <command> - Adds a command to the specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal removecommand <name> <index (Must be number)> - Removes a command from the specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal listcommands <name> - Lists the commands with their indexes of a specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal setmessage <name> <message> - Sets the message of the specified portal.");
					player.sendMessage(ChatColor.GREEN + "/cmdportal list - Lists the exisiting portals.");
				}
				else {
					player.sendMessage(ChatColor.RED + "Invalid argument!");
					player.sendMessage(ChatColor.RED + "Valid arguments: create, delete, setcommand, , addcommand, removecommand, listcommands, setmessage, info, list, help");
				}
			}
		}
		saveConfig();
		return true;
	}
	public String getPortalCommand(String[] args) {
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
	
    public WorldEditPlugin getWorldEdit() {
    	if (worldedit instanceof WorldEditPlugin) {
            return (WorldEditPlugin) worldedit;
        } else {
        	this.logger.warning("[CommandPortals] WorldEdit detection failed! CommandPortals will not work without WorldEdit!");
        	return null;
        }
    }

}
