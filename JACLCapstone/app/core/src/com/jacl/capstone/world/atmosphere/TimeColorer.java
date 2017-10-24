package com.jacl.capstone.world.atmosphere;

import com.badlogic.gdx.graphics.Color;

/**
 * This is a side project that allows for a day-night cycle in the game.<br><br>
 *  
 * Each game update, sent the time into this class. Use this to determine
 * the in-game time. From this time, determine the color to use in the rendering. By tinting the
 * sprites with this color, we can make the outdoors look different depending upon the time of
 * day.
 * 
 * @author Lee
 *
 */
public class TimeColorer
{
	private static final String COLOR_NIGHT = "24707B50";
	private static final String COLOR_MORNING = "FFFFFF30";
	private static final String COLOR_DAY = "00000000";
	
	public static Color getColor(GameTime time)
	{
		if(time.hours < 6 || time.hours >= 22)
			return Color.valueOf(COLOR_NIGHT);
		else if(time.hours >= 6 && time.hours < 14)
			return Color.valueOf(COLOR_MORNING);
		else
			return Color.valueOf(COLOR_DAY);
	}
}
