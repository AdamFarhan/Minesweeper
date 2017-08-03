/** 
 * @author	Adam Farhan
 * @date	11.17.2015
 * @project	Minesweeper  
 * TODO: comments?
 */
import java.awt.Color;
import java.util.Scanner;
public class Minesweeper {
	public static Scanner input = new Scanner(System.in);
	public static int canvasWidth, canvasHeight, rows, cols;
	public static int mouseInc = 0;
	public static boolean isFirst = true;
	public static void main(String[] args) {
		System.out.println("What difficulty would you like to play?\n"
						 + "1. Easy\n"
						 + "2. Medium\n"
						 + "3. Hard");
		
		int diff=1;
		try{
			diff = Integer.parseInt(input.next());

		}catch(Exception e){
			System.out.println("ERROR: Not a valid option");
		}
		
		if(diff == 1)		//if you entered Easy
		{
			canvasWidth = 164;
			canvasHeight = 208;
			rows = 9;
			cols = 9;
			
		}else if(diff == 2)	//if you entered Medium
		{
			canvasWidth	= 	277;
			canvasHeight=	321;
			rows		=	16;
			cols		=	16;
		}else if(diff == 3)
		{
			canvasWidth	=	500;
			canvasHeight=	319;
			rows		=	30;
			cols		=	16;
		}
		
		StdDraw.setCanvasSize(canvasWidth,canvasHeight);
		StdDraw.setXscale(0,canvasWidth);
		StdDraw.setYscale(0, canvasHeight);
		StdDraw.setPenColor(new Color(0,0,0));
		Grid g = new Grid(rows,cols,canvasWidth,canvasHeight,diff);
		g.startTimer();
		g.createSquares();
		g.draw();
		g.check();
		
		
	}
}
