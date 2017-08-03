import java.awt.Font;

/** 
 * @author	Adam Farhan
 * @date	11.17.2015
 * @project	Minesweeper  
 */
public class Square {
	protected double x, y;				//States:
	private int state;	//0: default
	private boolean isBomb;				//1: revealed
	private int w,h;					//2: flagged
	private int touching;
	private boolean isClicked, isFlagged;
	public static int revealedSquares = 0;
	
	public Square(double xIn, double yIn, int wIn, int hIn)
	{
		this.w = wIn;
		this.h = hIn;
		this.x = xIn;
		this.y = yIn;
		this.isBomb = false;
		this.state = 0;
		this.isClicked = false;
		
	}//end of Square()
	
	public void setBomb()
	{
		this.isBomb = true;
	}
	
	public boolean getIsBomb()
	{
		return this.isBomb;
	}
	public int getState()
	{
		return this.state;
	}
	
	public void setTouching(int t)
	{
		this.touching = t;
	}
	
	public int getTouching()
	{
		return this.touching;
	}
	
	public boolean getClicked()
	{
		return this.isClicked;
	}
	
	public boolean getFlag()
	{
		return this.isFlagged;
	}
	
	public void setFlag()
	{
		//this.isFlagged = true;
		this.state = 2;
		draw();
	}
	
	public void removeFlag()
	{
		//this.isFlagged = false;	
		this.state = 0;
		draw();
	}

	public int getRevealedNum()
	{
		return revealedSquares;
	}
	
	public void resetRevealed()
	{
		revealedSquares = 0;
	}
	public void draw()
	{
		StdDraw.setFont(new Font("Arial",500, 14));
		
		if(this.state == 0){
			StdDraw.picture(this.x, this.y, "res/tile_0.png");
			StdDraw.setPenColor(0,0,0);
		}
		else if(this.state == 1){
			if(this.isBomb){
				if(this.isClicked && this.state == 1)
					StdDraw.picture(this.x, this.y, "res/tile_bomb_clicked.png");
				else if(!this.isClicked)
					StdDraw.picture(this.x, this.y, "res/tile_bomb.png");
			}else{
				StdDraw.picture(this.x, this.y, "res/num_"+this.touching+".png");
			}
		}else if(this.state == 2){
			StdDraw.picture(this.x, this.y, "res/tile_2.png");
		}
	}//end of draw()
	
	/**
	 * Function that sets this square to a "clicked" state
	 * @param mX mouse X
	 * @param mY mouse Y
	 * @return if it works, return true, else return false
	 */
	public boolean check(double mX, double mY)
	{
		if(this.contains(mX, mY))
		{
			this.isClicked = true;
			this.reveal();
			return true;
		}else{
			return false;
		}
	}//end of check()
	
	public void reveal()
	{
		if(this.state == 0){
			
			this.state = 1;
			revealedSquares ++;
			draw();
		}else if(this.state == 2 && this.isBomb)
		{
			StdDraw.picture(this.x, this.y, "res/tile_bomb_flagged.png");
			//draw();
		}
	}
	/**
	 * Function that checks if this square contains a specific x,y point.
	 * @param mX x
	 * @param mY y
	 * @return 
	 */
	public boolean contains(double mX, double mY) 
	{
		if(mX>=(this.x-this.w/3) && mX<(this.x+this.w/3)&&
				   mY>=(this.y-this.h/3)&& mY<(this.y+this.h/3)){
			return true;
		}else{
			return false;
		}
	}
}
