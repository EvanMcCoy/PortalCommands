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
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  ///
//    GNU General Public License for more details.                                   ///
//                                                                                   ///
//    You should have received a copy of the GNU General Public License              ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package net.kingdomofkingdoms.Qwertyness_.portalcommands.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.kingdomofkingdoms.Qwertyness_.portalcommands.CommandHandler;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.Portal;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.PortalRegion;
import net.kingdomofkingdoms.Qwertyness_.portalcommands.portal.PortalSender;

public class PortalListener implements Listener {
	CommandHandler ch;
	
	public PortalListener(CommandHandler command) {
		ch = command;
		registerListeners();
	}
	
	public void registerListeners() {
		Bukkit.getServer().getPluginManager().registerEvents(this, Bukkit.getServer().getPluginManager().getPlugin("PortalCommands"));
	}
	
	@EventHandler
	public void playerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Portal p = new Portal(player, ch);
		List<String> portals = p.getPortals();
				
		
		for (int counter = 0;counter < portals.size();counter++) {
			PortalRegion portal = new PortalRegion(portals.get(counter), ch);
			if (player.hasPermission("pc.portal.use." + portal.getName()) || player.isOp() || player.hasPermission("pc.portal.use.*")) {
				String portalfrom = "";
				if (portal.isWithin(event.getFrom()) != null) {
					portalfrom = portal.isWithin(event.getFrom());
				}
				if (portal.isWithin(event.getTo()) != null && !portal.isWithin(event.getTo()).equals(portalfrom)) {
					try {
						for (String message : portal.getMessages()) {
							player.sendMessage(message);
						}
					} catch (NullPointerException e) {}
					if (portal.getType() == PortalSender.CONSOLE) {
						for (String command : portal.getCommands()) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
						}
					}
					else {
						for (String command : portal.getCommands()) {
							Bukkit.dispatchCommand(player, command);
						}
					}
					break;
				}
			}
		}
	}

}
