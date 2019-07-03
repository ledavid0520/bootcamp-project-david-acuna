package com.globant.project.utils;

import java.util.Random;

public class GeneratorUtils {

	/**
	 * Generates a random number
	 * 
	 * @return Random number
	 */
	public static Integer generateNumber(Boolean isPlatinum) {
		Integer random = 0;
		if(isPlatinum) {
			random = new Random().ints(1500, 6000).findFirst().getAsInt();
		}
		random = new Random().ints(0, 6000).findFirst().getAsInt();
		return random;
	}

}
