package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

public class RemoveCommand extends CommandLabel {

	public RemoveCommand(PortalCommands plugin) {
		super("removeportalcommand", "Removes a command from specified portal.", "<portal_name> <command_index>", plugin);
	}

	public void run(Player player, String[] args) {
		if (!player.hasPermission("pc.portal.edit.command.remove")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit a portal's " + ChatColor.GOLD + "commands");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable removeportalcommand <name> <command_index>");
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
			player.sendMessage(ChatColor.GREEN + "Command " + ChatColor.GOLD + portal.getCommands().get(Integer.parseInt(args[2]) - 1).getCommand() + ChatColor.GREEN + " removed from portal " + ChatColor.GOLD + portal.getName() + ChatColor.GREEN + "!");
			portal.removeCommand(Integer.parseInt(args[2]) - 1);
		} catch (Exception e) {
			player.sendMessage("Command index must be a whole number and be within the highest command index!");
		}
	}

}
