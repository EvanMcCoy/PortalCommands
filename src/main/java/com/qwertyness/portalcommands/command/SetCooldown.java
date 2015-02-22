package com.qwertyness.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.portalcommands.PortalCommands;
import com.qwertyness.portalcommands.portal.Portal;

public class SetCooldown extends CommandLabel {

	public SetCooldown(PortalCommands plugin) {
		super("setportalcooldown", "Sets the cooldown, in seconds, of the specified portal.", "<portal_name> <cooldown>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("pc.portal.edit.cooldown")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a portal's " + ChatColor.GOLD + "cooldown");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That portal does not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof Portal)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a Portal!");
			return;
		}
			
		Portal portal = (Portal) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		try {
			portal.setCooldown(Integer.parseInt(args[2]));
			player.sendMessage(ChatColor.GREEN + "You have set the cooldown of "  + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + Integer.parseInt(args[2]));
		} catch(Exception e) {
			player.sendMessage(ChatColor.RED + "Cooldown value must be an integer!");
		}
	}

}
