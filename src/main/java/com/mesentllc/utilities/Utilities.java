package com.mesentllc.utilities;

public class Utilities {
	public static String explode(long millis) {
		long hours = millis / 3600000;
		millis -= hours * 3600000;
		long minutes = millis / 60000;
		millis -= minutes * 60000;
		long seconds = millis / 1000;
		millis -= seconds * 1000;
		return String.format("[%02d:%02d:%02d", hours, minutes, seconds) + '.' + String.format("%03d]", millis);
	}
}
