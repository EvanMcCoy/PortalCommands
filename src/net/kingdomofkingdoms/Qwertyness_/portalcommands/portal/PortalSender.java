package net.kingdomofkingdoms.Qwertyness_.portalcommands.portal;

public enum PortalSender {
	PLAYER, CONSOLE;
	
	public static PortalSender toPortalSender(String senderVal) {
		try {
			if (senderVal.equalsIgnoreCase("player")) {
				return PortalSender.PLAYER;
			}
			else if (senderVal.equalsIgnoreCase("console")) {
				return PortalSender.CONSOLE;
			}
			else {
				return PortalSender.PLAYER;
			}
		} catch (NullPointerException e) {
			return PortalSender.PLAYER;
		}
	}
	
	public String toString() {
		if (this == PortalSender.PLAYER) {
			return "PLAYER";
		}
		else {
			return "CONSOLE";
		}
	}
}
