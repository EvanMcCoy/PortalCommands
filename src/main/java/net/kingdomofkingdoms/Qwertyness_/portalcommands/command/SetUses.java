package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetUses extends CommandLabel {
	
	public SetUses(PortalCommands plugin) {
		super("setportaluses", "Sets the use limit of the specified portal.", "<portal_name> <uses>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("pc.portal.edit.uses")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a portal's " + ChatColor.GOLD + "use limit.");
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
			portal.setUses(Integer.parseInt(args[2]));
			player.sendMessage(ChatColor.GREEN + "You have set the use limit of "  + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + Integer.parseInt(args[2]));
		} catch(Exception e) {
			player.sendMessage(ChatColor.RED + "Uses value must be an integer!");
		}
	}
}
