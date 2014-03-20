package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.Interactable;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

public class Delete extends CommandLabel {

	public Delete(PortalCommands plugin) {
		super("deleteportal", "<portal_name>", "Deletes a portal.", plugin);
	}

	public void run(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (!player.hasPermission("pc.portal.delete")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to " + ChatColor.GOLD + "delete" + ChatColor.GREEN + "portals.");
			return;
		}
		if (!this.plugin.getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That portal does not exist!");
			return;
		}
		Interactable interactable = this.plugin.getInteractablesAPI().getInteractableManager().getInteractable(args[1]);
		if (!(interactable instanceof Portal)) {
			player.sendMessage(ChatColor.RED + "The selected interactable is not a portal!");
			return;
		}
		Portal portal = (Portal) interactable;
		player.sendMessage(ChatColor.GOLD + "You have deleted the portal " + ChatColor.RED + args[1] + ChatColor.RED + "!");
		this.plugin.getInteractablesAPI().getInteractableManager().unregisterInteractable(portal);
		this.plugin.getInteractablesAPI().getInteractableManager().deleteInteractable(portal.getPlugin(), portal.basePath);
	}
}
