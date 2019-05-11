package animations;

import javax.swing.*;
import java.awt.*;

public class ThPanel extends JPanel implements Runnable, IAnimation {
	private Thread _thread;
	private int _updateRate;
	String _name;
	
	private boolean _threadIsSuspended = false;
	
	ThPanel(int updateRate, String name) {
		this._updateRate = updateRate;
		this._name = name;
	}
	
	protected void paint(Graphics2D g2d) {}
	
	protected void loop() {
		while (true) {
			this.updateFrame();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!this._threadIsSuspended) {
			this.paint((Graphics2D) g);
		}
	}
	
	@Override
	public void run() {
		this.loop();
	}
	
	void updateFrame() {
		this.revalidate();
		this.repaint();
		try {
			if (this._updateRate == 0)
			{
				this._updateRate =1;
			}
			Thread.sleep(1000/this._updateRate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (this._threadIsSuspended) {
			synchronized(this) {
				while (this._threadIsSuspended) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void start () {
		if (this._thread == null) {
			this._thread = new Thread (this, this._name);
			this._thread.start();
		}
	}
	
	public void pause() {
		this._threadIsSuspended = true;
	}
	
	public synchronized void resume() {
		this._threadIsSuspended = false;
		this.notify();
	}
	
	public void setFrameRate(int rate) {
		this._updateRate = rate;
	}
	
	public boolean isPaused() {
		return this._threadIsSuspended;
	}
	
	public int getFrameRate() {
		return this._updateRate;
	}
	
	public String getName() {
		return this._name;
	}
}
