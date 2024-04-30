/*
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
*/
import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
/*
import javax.management.timer.TimerMBean;
import java.awt.*;
import javax.swing.*;

*/
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
//import javax.swing.JPanel;

// == 主程式
public class Projext_T extends JFrame{
    //private static GamePanel game_panel = new GamePanel(); //創建遊戲面板
	public static void main(String[] args) throws IOException, Exception {
		Load load = new Load();
		int[] highest_score = load.GetHighestScore();

	}
    



}


class GamePanel extends JPanel implements Runnable
{
    public static final int Per_Unit_Length = 20;//單位長度
	public static final int Multiples_Time = 5;//倍數
	public static final int Half_Side = Multiples_Time * Per_Unit_Length;//遊戲邊框的一半長 = 倍數 * 單位長度
        
    private boolean initialize = true;//判斷是否需要初始化
	private boolean start = false;//判斷是否開始
	private boolean pause = false;//判斷是否暫停

    private static int eat_amount = 0;//吃的個數
	private static int score = 0;//分數
	private static int information = 0;//傳遞遊戲信息

	private Snake snake = new Snake();//蛇
	private Dot target = new Dot();//目標

	protected void PaintComponent( Graphics g )
	{
		super.paintComponent( g );

		int x_centre = GetWisth() / 2;
		int y_centre = GetHeight() / 2;

		int x_random;
		int y_random;

		if ( initialize )
		{
			initilaze = false;

			//遊戲邊筐
			g.grawRect( x_centre - Half_Side , y_ccentre - Half_Side  , Hide_Side * 2 , Half_Side * 2);

			//蛇頭
			snake.GetHead.SetX( x_centre );
			snake.GetHead.SetY( y_centre );		
			g.setColor( Color.ORANGE );
			g.fillOval( snake.GetHead().GetX() , snake.GetHead().GetY() , Per_Unit_Length , Per_Unit_Length );

			//標的
			do
			{
				x_random = x_centre - Half_Side + ( (int)( Math.random() * Multiple * 2 ) ) * Per_Unit_Length;
				y_random = y_centre - Half_Side + ( (int)( Math.random() * Multiple * 2 ) ) * Per_Unit_Length;
			} while ( x_random == snake.GetHead() && y == snake.GetHead() ) //重疊時

			//畫舌頭
			g.setColor( Color.ORANGE );
			g.fillOval( snake.GetHead().GetX() , snake

				
					
		}
		
	}
}


// == 蛇類 ==
class Snake
{
    public static final int Direction_Up = 1;
	public static final int Direction_Down = 2;
	public static final int Direction_Left = 3;
	public static final int Direction_Right = 4;
	public static final int SPEED_1 = 300;
	public static final int SPEED_2 = 200;
	public static final int SPEED_3 = 150;
	public static final int SPEED_4 = 100;
	public static final int SPEED_5 = 30;
	private int direction = Direction_Right;
	private int speed = SPEED_3;
	private Dot head = new Dot();
	private LinkedList<Dot> body = new LinkedList<Dot>();

	public Snake()
	{
	}

	public Dot GetHead()
	{
		return head;
	}

	public LinkedList<Dot> GetBody()
	{
		return body;
	}

	public int GetDirection(){
		return direction;
	}

	public int GetSpeed(){
		return speed;
	}

	public void SetBody( LinkedList<Dot> body )
	{
		this.body = body;
	}

	public void SetDirection( int direction )
	{
		this.direction = direction;
	}

	public void SetSpeed( int speed )
	{
		this.speed = speed;
	}
}



// == 點類 ==
class Dot
{
	private int x = 0;
	private int y = 0;

	public Dot()
	{
	}

	public Dot( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	public int GetX()
	{
		return x;
	}

	public int GetY()
	{
		return y;
	}

	public void SetX( int x )
	{
		this.x = x;
	}

	public void SetY( int y )
	{
		this.y = y;
	}
}

// == 分數導入/導出 ==
class Load 
{
	public static int[] highest_score = new int[5];	
	public Load() throws Exception , java.io.IOException
    {
        String score_file_name = "data";
		File score_file = new File( score_file_name );		
		if ( !score_file.exists() )
		{
			PrintWriter output = new PrintWriter( score_file_name );		
			output.println( "0 0 0 0 0" );
			output.close();
		}
        
        Scanner load_data = new Scanner( score_file );
		String score_load_t = "";
		while ( load_data.hasNext() ) 
        {
            score_load_t = score_load_t + load_data.next() + "\n" ;
        }     

		String[] score_load_t2 = score_load_t.split( " |\n" );
		
		for ( int i = 0 ; i < 5 ; i ++ )
		{
			highest_score[i] = Integer.parseInt(score_load_t2[i]);
			//System.out.println( highest_score[i] );
		}
		load_data.close();
    }
	
	public void Save() throws java.io.IOException
	{

	}

	public int[] GetHighestScore()
	{
		return highest_score;
	}

}
