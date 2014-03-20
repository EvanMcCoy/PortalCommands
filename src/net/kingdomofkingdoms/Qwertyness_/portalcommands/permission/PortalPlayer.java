package net.kingdomofkingdoms.Qwertyness_.portalcommands.permission;

import org.bukkit.entity.Player;

public class PortalPlayer {
	private Player player;
	
	public PortalPlayer(Player newplayer) {
		this.player = newplayer;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public boolean hasPortalPermission(PortalPermission pp) {
		for (String permission : pp.getPermissions()) {
			if (this.player.hasPermission(permission)) {
				return true;
			}
		}
		if (player.isOp()) {
			return true;
		}
		return false;
	}
	
	public boolean hasPortalPermission(PortalPermission pp, String portalname) {
		for (String permission : pp.getPermissions()) {
			if (this.player.hasPermission(permission)) {
				return true;
			}
		}
		if (player.hasPermission("pc.portal.use." + portalname) || player.isOp()) {
			return true;
		}
		return false;
	}
}
