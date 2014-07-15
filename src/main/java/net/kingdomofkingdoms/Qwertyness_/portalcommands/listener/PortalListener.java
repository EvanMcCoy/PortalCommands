////////////////////////////////////////////////////////////////////////////////////////
// PortalCommands: Bukkit Portal Plugin                                              ///
// Copyright (C) 2013  Qwertyness_                                                   ///
//                                                                                   ///
//     This program is free software: you can redistribute it and/or modify          ///
//    it under the terms of the GNU General Public License as published by           ///
//    the Free Software Foundation, either version 3 of the License, or              ///
//    (at your option) any later version.                                            ///
//                                                                                   ///
//    This program is distributed in the hope that it will be useful,                ///
//    but WITHOUT ANY WARRANTY; without even the implied warranty of                 ///
//    MERpluginANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  ///
//    GNU General Public License for more details.                                   ///
//                                                                                   ///
//    You should have received a copy of the GNU General Public License              ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package net.kingdomofkingdoms.Qwertyness_.portalcommands.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractCommand;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.InteractSender;
import net.kingdomofkingdoms.Qwertyness_.interactables.interactable.Interactable;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.PortalCommands;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.PortalRegion;

public class PortalListener implements Listener {
	PortalCommands plugin;
	
	public PortalListener(PortalCommands plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		
		for (Interactable interactable : this.plugin.getInteractablesAPI().getInteractableManager().getInteractables()) {
			if (!(interactable instanceof Portal)) {
				continue;
			}
			if (!interactable.getPlugin().getInteractablesAPI().getInteractableManager().canUse(player, interactable)) {
				continue;
			}
			final PortalRegion portal = new PortalRegion((Portal) interactable, this.plugin);
			
			if (player.hasPermission("pc.portal.use." + portal.getName()) || player.hasPermission("pc.portal.use.*")) {
				
				if (portal.isWithin(event.getTo())) {
					
					if (portal.isWithin(event.getFrom())) {
						return;
					}
					
					try {
						for (String message : portal.getMessages()) {
							message = message.replaceAll("@p", player.getName());
							player.sendMessage(message);
						}
					} catch (NullPointerException e) {}
					
					for (int commandCounter = 0;commandCounter < portal.getCommands().size();commandCounter++) {
						InteractCommand command = portal.getCommands().get(commandCounter);
						String executeCommand = command.getCommand().replaceAll("@p", player.getName());
						if (command.getSender() == InteractSender.CONSOLE) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), executeCommand);
						}
						else if (command.getSender() == InteractSender.SPECIAL) {
							player.chat("/" + executeCommand);
						}
						else {
							Bukkit.dispatchCommand(player, executeCommand);
						}
					}
					interactable.getPlugin().getInteractablesAPI().getInteractableManager().useInteractable(player, interactable);
					break;
				}
			}
		}
	}
}
