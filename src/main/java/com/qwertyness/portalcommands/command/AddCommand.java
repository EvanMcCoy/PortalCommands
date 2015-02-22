package com.qwertyness.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.interactables.interactable.InteractCommand;
import com.qwertyness.interactables.interactable.InteractSender;
import com.qwertyness.portalcommands.PortalCommands;
import com.qwertyness.portalcommands.portal.Portal;
import com.qwertyness.portalcommands.utils.PortalUtil;

public class AddCommand extends CommandLabel {

	public AddCommand(PortalCommands plugin) {
		super("addportalcommand", "Adds a command to specified portal.", "<portal_name> <-t CONSOLE, PLAYER, or SPECIAL> <command>", plugin);
	}

	public void run(Player player, String[] args) {
		InteractSender interactSender = InteractSender.PLAYER;
		if (args[2].equalsIgnoreCase("-t")) {
			if (args[3].equalsIgnoreCase("player")) {
				if (!player.hasPermission("pc.portal.edit.command.player")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "PLAYER" + ChatColor.RED + " type command to a portal.");
					return;
				}
				interactSender = InteractSender.PLAYER;
			}
			else if (args[3].equalsIgnoreCase("console")) {
				if (!player.hasPermission("pc.portal.edit.command.console")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "CONSOLE" + ChatColor.RED + " type command to a portal.");
					return;
				}
				interactSender = InteractSender.CONSOLE;
			}
			else if (args[3].equalsIgnoreCase("special")) {
				if (!player.hasPermission("pc.portal.edit.command.special")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "SPECIAL" + ChatColor.RED + " type command to a portal.");
					return;
				}
				interactSender = InteractSender.SPECIAL;
			}
			else {
				player.sendMessage(ChatColor.RED + "Invalid portal type! Types: player, console, special");
			}
		}
		else {
			if (!player.hasPermission("pc.portal.edit.command.player")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to add a " + ChatColor.GOLD + "PLAYER" + ChatColor.RED + " type command to a portal.");
				return;
			}
			interactSender = InteractSender.PLAYER;
		}
		
		
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1]))) {
			player.sendMessage(ChatColor.RED + "That portal does not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof Portal)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a Portal!");
			return;
		}
		
		Portal portal = (Portal) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		portal.addCommand(new InteractCommand(PortalUtil.getPortalCommand(args), interactSender));
		player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + PortalUtil.getPortalCommand(args) + ChatColor.GREEN + " added to portal " + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + "!");
		
	}

}
