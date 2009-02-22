package org.javakontor.sherlog.core.impl.internal.reader;

import java.util.ArrayList;
import java.util.Properties;

public class ThreadNameFactory {

	static ArrayList<String> myHerkunftMap = new ArrayList<String>();
	static ArrayList<String> myHerkunftPatternMap = new ArrayList<String>();

	static Properties datenHerkunft2AlphaMap = new Properties();

	static public String getThreadname(final String threadName,
			final String datenHerkunft) {
		return getPrefix(datenHerkunft) + threadName;
	}

	static public String getThreadnamePattern(final String threadName,
			final String datenHerkunft) {
		return getPatternPrefix(datenHerkunft) + threadName;
	}

	static private String getPrefix(final String datenHerkunft) {
		if (!myHerkunftMap.contains(datenHerkunft)) {
			myHerkunftMap.add(datenHerkunft);
		}
		return "SRC_" + myHerkunftMap.lastIndexOf(datenHerkunft) + "-";
	}

	static private String getPatternPrefix(final String datenHerkunft) {
		if (!datenHerkunft2AlphaMap.containsKey(datenHerkunft)) {
			datenHerkunft2AlphaMap.put(datenHerkunft,
					eliminierteZeitstempel(datenHerkunft));
		}
		final String alphaOnly = datenHerkunft2AlphaMap
				.getProperty(datenHerkunft);

		if (!myHerkunftPatternMap.contains(alphaOnly)) {
			myHerkunftPatternMap.add(alphaOnly);
		}
		return "PAT_" + myHerkunftPatternMap.lastIndexOf(alphaOnly) + "-";
	}

	static private String eliminierteZeitstempel(final String datenHerkunft) {
		final StringBuffer retVal = new StringBuffer();
		int lastIndex = datenHerkunft.lastIndexOf('/');
		if (lastIndex == -1) {
			lastIndex = datenHerkunft.lastIndexOf('\\');
		}

		final String lastPart = lastIndex > 0 ? datenHerkunft
				.substring(lastIndex) : datenHerkunft;

		// org.apache.log4j.Logger.getLogger(ThreadNameFactory.class).debug(
		// "alphaOnly(1):" + lastPart);
		final char[] asCharArray = lastPart.toCharArray();
		for (int i = 0; i < asCharArray.length; i++) {
			if (!Character.isDigit(asCharArray[i])) {
				retVal.append(asCharArray[i]);
			}

		}
		// org.apache.log4j.Logger.getLogger(ThreadNameFactory.class).debug(
		// "alphaOnly(2):" + retVal);
		return retVal.toString();
	}
}
