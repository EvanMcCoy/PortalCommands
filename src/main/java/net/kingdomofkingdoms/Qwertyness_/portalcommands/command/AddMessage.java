package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.utils.PortalUtil;

public class AddMessage extends CommandLabel {

	public AddMessage(PortalCommands plugin) {
		super("addportalmessage", "Adds a message to the specified portal.", "<portal_name> <message>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("pc.portal.edit.message")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a portal's " + ChatColor.GOLD + "messages");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
		}
		if (!this.getPlugin().getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That portal does not exist!");
		}
		if (!(this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]) instanceof Portal)) {
			player.sendMessage(ChatColor.RED + "Specified interactable is not a Portal!");
			return;
		}
		
		Portal portal = (Portal) this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractable(args[1]);;
		portal.addMessage(PortalUtil.getPortalCommand(args));
		player.sendMessage(ChatColor.GREEN + "Message " + ChatColor.GOLD + PortalUtil.getPortalCommand(args) + ChatColor.GREEN + " added to portal " + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + "!");
	}
}
