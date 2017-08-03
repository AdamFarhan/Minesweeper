
public class Smile extends Square{
	
	public Smile(double xIn, double yIn, int wIn, int hIn)
	{
		super(xIn, yIn, wIn, hIn);
	}
	
	public void draw()
	{
		StdDraw.picture(super.x, super.y, "res/smile.png");
	}

	public void win()
	{
		StdDraw.picture(super.x,super.y,"res/smile_win.png");
	}
	public void lose()
	{
		StdDraw.picture(super.x, super.y, "res/smile_lose.png");
	}
}
