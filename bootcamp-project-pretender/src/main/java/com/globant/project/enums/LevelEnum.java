package com.globant.project.enums;

/**
 * Enums level types
 * 
 */
public enum LevelEnum {
	
	GOLD("GOLD"),
	PLATINUM("PLATINUM"),
	JS("JS");
	
	private String level;
	
	LevelEnum(final String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
	
}
