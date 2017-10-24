package com.jacl.capstone.world.atmosphere;

/**
 * In-game time represented in hours, minutes, and seconds.
 * 
 * @author Lee
 *
 */
public class GameTime
{
	public int hours, minutes;
	public boolean recently_updated_minute;
	private float buffer;
	
	/**
	 * Starts the game time at 00:00.
	 */
	public GameTime()
	{
		hours = 0;
		minutes = 0;
	}
	
	/**
	 * Sets the game time after extracting the time from the passed string. Used in loading saves.
	 * @param time
	 */
	public GameTime(String time)
	{
		String[] splitter = time.split(":");
		hours = Integer.parseInt(splitter[0]);
		minutes = Integer.parseInt(splitter[1]);
		recently_updated_minute = true;
	}
	
	/**
	 * Allows us to initialize the game time from a specific point.
	 * @param hours
	 * @param minutes
	 */
	public GameTime(int hours, int minutes)
	{
		this.hours = hours;
		this.minutes = minutes;
		recently_updated_minute = true;
	}
	
	/**
	 * Game world updated. Add the new timing into the game time.
	 * @param delta
	 */
	public void update(float delta)
	{
		buffer += delta;
		updateTime();
	}
	
	@Override
	/**
	 * Represent the game time as a string of the
	 * form hours:minutes. Note: Will format hours
	 * or minutes less than 10 to a two-digit 
	 * format.
	 */
	public String toString()
	{
		if(hours < 10 && minutes < 10)
		{
			return "0" + hours + ":0" + minutes;
		}
		else if(hours < 10)
		{
			return "0" + hours + ":" + minutes;
		}
		else if(minutes < 10)
		{
			return hours + ":0" + minutes;
		}
		else
		{
			return hours + ":" + minutes;
		}
	}
	
	/**
	 * Update hours and minutes as necessary.
	 */
	private void updateTime()
	{
		recently_updated_minute = false;
		if(buffer >= 1f)
		{
			buffer -= 1f;		
			minutes++;
			recently_updated_minute = true;
		}
		if(minutes >= 60)
		{
			minutes -= 60;
			hours++;
		}
		if(hours >= 24)
		{
			hours = 0;
		}
	}
}
