package animations;

public interface IAnimation
{
	void start();
	void pause();
	void resume();
	void setFrameRate(int rate);
	boolean isPaused();
	int getFrameRate();
	String getName();
}
