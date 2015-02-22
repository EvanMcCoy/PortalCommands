 package com.qwertyness.portalcommands.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CooldownUtil {
	private List<HashMap<String, String>> cooldownList = new ArrayList<HashMap<String, String>>();
	
	public void addEntry(String playerName, String portalName) {
		HashMap<String, String> hash = new HashMap<String, String>();
		hash.put(playerName, portalName);
		cooldownList.add(hash);
	}
	
	public void removeEntry(String playerName, String portalName) {
		if (this.isCoolingDown(playerName, portalName)) {
			this.cooldownList.remove((int)this.getCoolingDown(playerName, portalName));
		}
	}
	
	public Integer getCoolingDown(String playerName, String portalName) {
		for (int counter = 0;counter < cooldownList.size();counter++) {
			HashMap<String, String> hash = cooldownList.get(counter);
			if (hash.containsKey(playerName)) {
				if (hash.get(playerName).equals(portalName)) {
					return counter;
				}
			}
		}
		return null;
	}
	
	public boolean isCoolingDown(String playerName, String portalName) {
		if (this.getCoolingDown(playerName, portalName) == null) {
			return false;
		}
		return true;
	}
}
