package patt;

import javax.sound.sampled.Clip;

public class MoosicThread extends Thread {
	
	
	private Clip clip;
	
	private boolean die;
	
	
	public MoosicThread(Clip c)
	{
		clip = c;
		die = false;
	}
	
	public void run()
	{
		clip.start();
		while (!die)
		{
			if (isSongDone())
			{
				clip.setFramePosition(0);
			}
			
			if (this.isInterrupted())
			{
				clip.stop();
				die = true;
			}
		}
			
	}
	
	public Clip getClip()
	{
		return clip;
	}
	
	public boolean isSongDone()
	{
		return clip.getFramePosition() >= clip.getFrameLength();
	}

}
