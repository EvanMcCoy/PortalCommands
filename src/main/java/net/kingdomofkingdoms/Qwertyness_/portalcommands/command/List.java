package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.Interactable;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;

public class List extends CommandLabel {

	public List(PortalCommands plugin) {
		super("listportals", "Lists all of the portals registered with PortalCommands.", plugin);
	}

	public void run(Player player, String[] args) {
		java.util.List<Interactable> interactables = this.getPlugin().getInteractablesAPI().getInteractableManager().getInteractables();
		java.util.List<Portal> portals = new ArrayList<Portal>();
		for (Interactable interactable : interactables) {
			if (interactable instanceof Portal) {
				portals.add((Portal)interactable);
			}
		}
		java.util.List<String> portalNames = new ArrayList<String>();
		for (Portal portal : portals) {
			portalNames.add(portal.getName());
		}
		player.sendMessage(ChatColor.GREEN + "Portals: " + portalNames);
	}
}
