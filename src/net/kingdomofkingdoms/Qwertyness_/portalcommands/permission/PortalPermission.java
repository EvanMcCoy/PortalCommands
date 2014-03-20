package net.kingdomofkingdoms.Qwertyness_.portalcommands.permission;

import java.util.Arrays;
import java.util.List;

public enum PortalPermission {
	PORTAL_CREATE_CONSOLE (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.create.*", "pc.portal.create.console")), 
	PORTAL_CREATE_PLAYER (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.create.*", "pc.portal.create.player")),
	PORTAL_DELETE (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.delete")),
	PORTAL_EDIT_COMMAND (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.edit.*", "pc.portal.edit.command")),
	PORTAL_EDIT_MESSAGE (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.edit.*", "pc.portal.edit.message")),
	PORTAL_USE (Arrays.asList("pc.*", "pc.portal.*", "pc.portal.use.*"));
	
	private List<String> permissions;
	
	private PortalPermission(List<String> possiblepermissions) {
		permissions = possiblepermissions;
	}
	
	public List<String> getPermissions() {
		return permissions;
	}
}
