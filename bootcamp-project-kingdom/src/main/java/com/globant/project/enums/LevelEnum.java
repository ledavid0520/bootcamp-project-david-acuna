package com.globant.project.enums;

import com.globant.project.exceptions.IllegalEnumException;

/**
 * Enums level types
 * 
 */
public enum LevelEnum {
	
	GOLD("GOLD"),
	PLATINUM("PLATINUM");
	
	private String level;
	
	LevelEnum(final String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static LevelEnum getValueOf(final String level) {
        for (LevelEnum le : values()) {
            if (le.getLevel().equals(level)) {
                return le;
            }
        }
        throw new IllegalEnumException ("The level: " + level + " does not exists");
    }
	
}
