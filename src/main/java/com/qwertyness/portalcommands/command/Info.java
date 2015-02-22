package com.qwertyness.portalcommands.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.qwertyness.interactables.command.CommandLabel;
import com.qwertyness.portalcommands.PortalCommands;
import com.qwertyness.portalcommands.portal.Portal;

public class Info extends CommandLabel {
	
	public Info(PortalCommands plugin) {
		super("portalinfo", "gives the info of the specified portal.", "<portal_name>", plugin);
	}

	public void run(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That portal doe not exist!");
			return;
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof Portal)) {
			player.sendMessage(ChatColor.RED + "The selected interactable is not a Portal!");
		}
		Portal portal = (Portal) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		java.util.List<String> commands = new ArrayList<String>();
		for (int counter = 0;counter < portal.getCommands().size();counter++) {
			commands.add(ChatColor.WHITE + portal.getCommands().get(counter).getCommand() + ChatColor.GOLD + ":" + ChatColor.WHITE + portal.getCommands().get(counter).getSender());
		}
				
		player.sendMessage(ChatColor.GREEN + "----- Portal Info: " + portal.getName() + " -----");
		player.sendMessage(ChatColor.GREEN + "Commands: " + commands);
		player.sendMessage(ChatColor.GREEN + "Messages: " + portal.getMessages());
		player.sendMessage(ChatColor.GREEN + "Cooldown: " + portal.getCooldown());
		player.sendMessage(ChatColor.GREEN + "Maximum: " + portal.getMaximum());
		player.sendMessage(ChatColor.GREEN + "Minimum: " + portal.getMinimum());
	}
}
