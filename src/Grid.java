import java.util.Timer;
import java.util.TimerTask;

/** 
 * @author	Adam Farhan
 * @date	11.17.2015
 * @project	Minesweeper  
 */
public class Grid {
	private int x, y;
	private int sqW, sqH;
	private Square squares[][];
	private int diff;
	private int numBombs, numFlags;
	private int mouseInc;
	public boolean isFirst = true;
	public boolean isFlag, isPlaying;
	public int leftMargin, bottomMargin;//margin of the "squares"
	private Smile btn_smile;	//reset button
	private Timer t; 
	private int timerX, flagX, smileX, trayY;
	public Grid(int xIn, int yIn, int frameWidth, int frameHeight, int diff)
	{
		this.x = xIn;
		this.y = yIn;
		sqW = 26;
		sqH = 26;
		
		squares = new Square[this.x][this.y];
		
		this.diff = diff;
		
		//Set the BG and all the x,y values for the buttons, timer, etc.
		if(this.diff == 1)		//Easy
		{
			this.numBombs = 10;
			this.leftMargin = 14;
			this.bottomMargin = 7;
			this.timerX = 149;
			this.trayY = 187;
			this.flagX = 46;
			this.smileX = 84;
			StdDraw.picture(frameWidth/2, frameHeight/2, "res/board_easy.png");
		}else if(this.diff == 2)//Medium
		{
			this.numBombs = 40;
			this.leftMargin = 8;
			this.bottomMargin= 2;
			this.timerX = 266;	
			this.flagX = 41;
			this.trayY = 306;
			this.smileX = 140;
			StdDraw.picture(frameWidth/2, frameHeight/2, "res/board_medium.png");
		}else if(this.diff == 3)//Hard
		{
			this.numBombs = 99;
			this.leftMargin = -2;
			this.bottomMargin= 2;
			this.timerX = 501;	
			this.flagX = 29;
			this.trayY = 305;
			this.smileX = 252;
			StdDraw.picture(frameWidth/2, frameHeight/2, "res/board_hard.png");
		}
		
		this.numFlags = this.numBombs;
		isPlaying = true;
	}//end of Grid()

	
	public void createSquares()
	{
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++)
			{	//new Square(x, y, width, height)
				Square temp = new Square(this.leftMargin+i*17.6, this.bottomMargin+j*17.6, sqW, sqH);
				squares[i][j] = temp;
			}//end of for j->y
		}//end of i->x	
		
		btn_smile = new Smile(this.smileX, this.trayY,26,26);
	}//end of createSquares()
	
	
	/**
	 * After you click, goes through all the squares and randomly adds bombs.
	 * If the random square is already a bomb, try again.
	 * @param firstX - the square x you clicked on first
	 * @param firstY - the square y you clicked on first
	 */
	public void createBombs(int firstI, int firstJ)
	{
		for(int i=0;i<this.numBombs;i++)
		{
			int tempX = (int)(Math.random()*this.x);
			int tempY = (int)(Math.random()*this.y);
			
			//System.out.println("i: "+i+"\t tempX: "+tempX+", \t tempY: "+tempY+"\t isBomb?: "+squares[tempX][tempY].getIsBomb());
			
			if(tempX != firstI || tempY != firstJ)
			{
				if(!squares[tempX][tempY].getIsBomb()){
					squares[tempX][tempY].setBomb();
				}else if(squares[tempX][tempY].getIsBomb()){
					i--;	
				}
			}else{
					i--;
			}
		}
	}
	
	/**
	 * For each square, look at all surrounding squares, and add up how many bombs there are
	 * out of 9 squares.
	 */
	public void calcTouching()
	{
		int tempBombs = 0;
		for(int i=0;i<this.x;i++){
			for(int j=0;j<this.y;j++){	
				for(int lookX = (i-1);lookX<=(i+1);lookX++){		//go one square behind, to one square in front
					for(int lookY = (j-1);lookY<=(j+1);lookY++){	//go one square up, to one square down
						try{
						if(this.squares[lookX][lookY].getIsBomb()){
							tempBombs++;
						}
						}catch(Exception e){}
					}
				}
				squares[i][j].setTouching(tempBombs);
				tempBombs = 0;
			}//end of for j->y
		}//end of for i->x
	}
	
	//draw each square, the flag count, and the smile button
	public void draw()
	{
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				squares[i][j].draw();
			}	
		}
		btn_smile.draw();
		drawFlagCount();
	}//end of draw()
	
	/**
	 * Convert the flag count into 3 images
	 * If you have 0-9 flags, the "number of flags" = "004"
	 * If you have 10-99 flags, the number is 094
	 * If you have 100-999, the number is 874
	 */
	public void drawFlagCount()
	{	
		String temp = "";
		if(numFlags<=9)
		{
			temp = "00"+numFlags;
		}else if(numFlags<100&& numFlags>9){
			temp = "0"+numFlags;
		}else if(numFlags>99){
			temp = ""+numFlags;
		}

		StdDraw.picture(this.flagX, this.trayY, "res/time_"+temp.charAt(2)+".png");
		StdDraw.picture(this.flagX-14, this.trayY, "res/time_"+temp.charAt(1)+".png");
		StdDraw.picture(this.flagX-28, this.trayY, "res/time_"+temp.charAt(0)+".png");
	}//end of drawFlagCount()
	
	/**
	 * Similar to the flag counter, except the timer increases on its own, not user
	 * driven. Timer ticks every 1 second.
	 */
	public void startTimer()
	{
		t = new Timer();

		t.scheduleAtFixedRate(new TimerTask(){
			private int ones = 0, tens = 0, hunds = 0;
			//@Override
			public void run() {
				
				if(ones>9)
				{
					ones = 0;
					tens++;
				}
				if(tens>9)
				{
					tens = 0;
					hunds++;
				}
				
				if(hunds>9)
				{
					hunds = 9;
					tens = 9;
					ones = 9;
					t.cancel();
				}
				StdDraw.picture(timerX, trayY, "res/time_"+ones+".png");
				StdDraw.picture(timerX-14, trayY, "res/time_"+tens+".png");
				StdDraw.picture(timerX-28, trayY, "res/time_"+hunds+".png");
				ones++;
			}
		}, 0, 1000);
	}//end of startTimer()
	
	public void stopTimer()
	{
		t.cancel();
	}
	
	/**
	 * The main function of the grid.
	 * check if the "f" key is held down{}
	 * if you click{
	 * 	if you're playing{
	 * 		if you're holding the "f" key, and you have available flags{
	 * 			find the square you clicked on, and set/remove a flag
	 * 		}else if you're clicked normally{
	 * 			if this is your first click{
	 * 				create bombs on squares not on the square you clicked
	 * 				calculate how many bombs each square touches
	 * 				reveal the square you clicked on
	 * 			}else if its any other click{
	 * 				find the square you clicked on{
	 * 					if it's a bomb, reveal everything/lose
	 * 					if it's a # >0, reveal only that square
	 * 					if it = 0, reveal squares around it
	 * 				}
	 * 			}
	 * 		}
	 * 		(still inside the if(isPlaying)
	 * 		if you click the smile button{
	 * 			reset the game
	 * 		}
	 * 	}
	 * }
	 *
	 *At any point the # of revealed squares = the number of total squares - number of bombs{
	 *		win the game
	 *		reveal all the bombs
	 *		set the smile to the cool guy face
	 *		"stop" playing
	 *}		
	 */
	public void check()
	{
		while(true)
		{
			if(StdDraw.isKeyPressed(70))
			{
				this.isFlag = true;
			}else
			{
				this.isFlag = false;
			}
			
			
			if(StdDraw.mousePressed())
			{
				mouseInc++;
				if(mouseInc == 1)
				{
					double mX = StdDraw.mouseX();
					double mY = StdDraw.mouseY();
					if(isPlaying){
						if(this.isFlag && this.numFlags >0)
						{
							for(int i=0;i<x;i++){
								for(int j=0;j<y;j++){
									if(squares[i][j].contains(mX, mY)){
										if(squares[i][j].getState() == 0){
											squares[i][j].setFlag();
											this.numFlags--;
										}else if(squares[i][j].getState() == 2)
										{
											squares[i][j].removeFlag();
											this.numFlags ++;
										}
									}//end of if squares[i][j].check
								}//end of for(j>y)
							}//end of for(i>x)
							drawFlagCount();
						}else{
							if(isFirst)
							{
								for(int i=0;i<x;i++){
									for(int j=0;j<y;j++){
										if(squares[i][j].contains(mX, mY)){
											createBombs(i, j);
											calcTouching();
											if(squares[i][j].getTouching() == 0){
												reveal(i,j);
											}else{
												squares[i][j].reveal();
											}
											isFirst = false;
										}//end of(if squares contains mouseX,Y)
									}//end of for j->y
								}//end of for i->x
							}else{
								for(int i=0;i<x;i++){
									for(int j=0;j<y;j++){
										if(squares[i][j].contains(mX, mY)){
											if(squares[i][j].getIsBomb()){
												revealAll();
											}else if(squares[i][j].getTouching() == 0){
												reveal(i,j);
											}else{
												squares[i][j].reveal();
											}
										}//end of if(squares contains mouseX,Y)
									}//end of for j->y
								}//end of for i->x
								
							}//end of if(isFirst)
						}//end of if(isFlag)
					}//end of isPlaying
					if(btn_smile.check(mX, mY))
					{
						reset();
					}
				}//end of if(mouseInc == 1)
			}else
			{
				mouseInc = 0;
			}//end of if(mousePress)
		
			
			//if you win
			if(squares[0][0].getRevealedNum() == (this.x*this.y)-this.numBombs && this.isPlaying == true)
			{
				System.out.println("you win");
				this.stopTimer();
				isPlaying = false;
				btn_smile.win();
			}
		}//end of while(true)
	}//end of check()
	
	public void reveal(int startX, int startY)
	{
		for(int i=startX-1;i<=startX+1;i++){
			for(int j=startY-1;j<=startY+1;j++){
				try{
					if(squares[i][j].getState() == 0&& squares[i][j].getTouching()==0 && i>=0 && j>=0)
					{
						squares[i][j].reveal();
						this.reveal(i,j);
					}
					squares[i][j].reveal();
				}catch(Exception e){}
				
			}//end of for(j)
		}//end of for(i)
	}//end of reveal()
	
	public void revealAll()
	{
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				if(squares[i][j].getIsBomb()){
					squares[i][j].reveal();
				}//end if
			}//end of for(j<y)
		}//end of for(i<x)
		
		this.isPlaying = false;
		btn_smile.lose();
		this.stopTimer();
	}//end of revealAll()
	
	public void reset()
	{
		this.isFirst = true;
		this.isPlaying = true;
		this.createSquares();
		squares[0][0].resetRevealed();
		this.numFlags = this.numBombs;
		this.stopTimer();
		this.startTimer();
		this.draw();
		this.check();
		
	}//end of reset()
}//end of Grid
