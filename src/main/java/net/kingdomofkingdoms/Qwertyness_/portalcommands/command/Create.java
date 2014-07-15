package net.kingdomofkingdoms.Qwertyness_.portalcommands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.kingdomofkingdoms.Qwertyness_.interactables.command.CommandLabel;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractCommand;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractSender;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.utils.PortalUtil;

public class Create extends CommandLabel {
	private PortalUtil portalUtil;

	public Create(PortalCommands plugin) {
		super("createportal", "<portal_name> <-t CONSOLE, PLAYER, or SPECIAL> <command>", "Creates a portal.", plugin);
		this.plugin = plugin;
		this.portalUtil = plugin.getPortalUtil();
	}

	public void run(Player player, String[] args) {
		boolean createSuccess = false;
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED + "Too few arguments! /interactable " + this.name + " " + this.syntax);
			return;
		}
		if (args[1].equalsIgnoreCase("-t")) {
			player.sendMessage(ChatColor.RED + "Cannot use " + ChatColor.GOLD + "-t" + ChatColor.RED + " as a portal name!");
			return;
		}
		if (this.plugin.getInteractablesAPI().getInteractableManager().isRegistered(args[1])) {
			player.sendMessage(ChatColor.RED + "That portal already exists!");
			return;
		}
		
		Portal portal = new Portal(plugin);
		if (args[2].equalsIgnoreCase("-t")) {
			if (args[3].equalsIgnoreCase("player")) {
				if (player.hasPermission("pc.portal.create.player")) {
					createSuccess = this.portalUtil.create(player, portal, args[1]);
					portal.addCommand(new InteractCommand(PortalUtil.getPortalCommand(args), InteractSender.PLAYER));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
					return;
				}
			}
			else if (args[3].equalsIgnoreCase("console")) {
				if (player.hasPermission("pc.portal.create.console")) {
					createSuccess = this.portalUtil.create(player, portal, args[1]);
					portal.addCommand(new InteractCommand(PortalUtil.getPortalCommand(args), InteractSender.CONSOLE));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
					return;
				}
			}
			else if (args[3].equalsIgnoreCase("special")) {
				if (player.hasPermission("pc.portal.create.special")) {
					createSuccess = this.portalUtil.create(player, portal, args[1]);
					portal.addCommand(new InteractCommand(PortalUtil.getPortalCommand(args), InteractSender.SPECIAL));
				}
				else {
					player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
					return;
				}
			}
			else {
				player.sendMessage(ChatColor.RED + "Invalid portal type! Types: player, console, special");
			}
		}
		else {
			if (player.hasPermission("pc.portal.create.player")) {
				createSuccess = this.portalUtil.create(player, portal, args[1]);
				portal.addCommand(new InteractCommand(PortalUtil.getPortalCommand(args), InteractSender.PLAYER));
			}
			else {
				player.sendMessage(ChatColor.RED + "You do not have permission to create portals of this type!");
				return;
			}
		}
		if (createSuccess) {
			player.sendMessage(ChatColor.GREEN + "You have created the portal " + ChatColor.GOLD + portal.getName());
			this.plugin.getInteractablesAPI().getInteractableManager().registerInteractable(portal);
		}
		
	}
}
