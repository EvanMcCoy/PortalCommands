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
//    You should have received a copy of the GNU General Public License	             ///
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.           ///
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

package com.qwertyness.portalcommands.portal;

import org.bukkit.Location;

import com.qwertyness.portalcommands.PortalCommands;

public class PortalRegion extends Portal{
	
	public PortalRegion(Portal portal, PortalCommands command) {
		super(portal);
	}
	public boolean isWithin(Location loc) {
		if (loc.getWorld().getName().equalsIgnoreCase(world)) {
			if (loc.getBlockX() <= maximum.getBlockX() && loc.getBlockX() >= minimum.getBlockX()) {
				if (loc.getBlockY() <= maximum.getBlockY() && loc.getBlockY() >= minimum.getBlockY()) {
					if (loc.getBlockZ() <= maximum.getBlockZ() && loc.getBlockZ() >= minimum.getBlockZ()) {
						return true;
					} else {return false;}
				} else {return false;}
			}else {return false;}
		}else {return false;}
	}
}
