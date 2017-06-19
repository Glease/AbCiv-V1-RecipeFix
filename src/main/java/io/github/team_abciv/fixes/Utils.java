package io.github.team_abciv.fixes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author glease
 * @since 1.0
 */
public class Utils {
	public static final Logger getLogger(String name) {
		return LogManager.getLogger("AbCiv-Fix."+name);
	}
}
