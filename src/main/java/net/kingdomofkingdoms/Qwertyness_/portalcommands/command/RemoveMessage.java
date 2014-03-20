package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

public class RemoveMessage extends CommandLabel {

	public RemoveMessage(PortalCommands plugin) {
		super("removeportalmessage", "Removes a message from the specified portal.", "<portal_name> <message_index>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("pc.portal.edit.message")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a portal's " + ChatColor.GOLD + "messages");
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
			player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + portal.getMessages().get(Integer.parseInt(args[2]) - 1) + ChatColor.GREEN + " removed from portal " + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + "!");
			portal.removeMessage(Integer.parseInt(args[2]) - 1);
		} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "Message index must be a whole number and be within the highest message index!");
		}
	}

}
