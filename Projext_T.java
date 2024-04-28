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
import java.util.Scanner;

import javax.swing.JFrame;
//import javax.swing.JPanel;

public class Projext_T extends JFrame{
    //private static GamePanel game_panel = new GamePanel(); //創建遊戲面板
	public static void main(String[] args) throws IOException, Exception {
		Load load = new Load();
		int[] highest_score = load.GetHighestScore();

	}
    



}

/*
class GamePanel extends JPanel implements Runnable
{
    public static final int per_unit_length = 20;//單位長度
	public static final int MULTIPLE = 5;//倍數
	public static final int HALF_SIDE = MULTIPLE * per_unit_length;//遊戲邊框的一半長 = 倍數 * 單位長度
        
    private boolean initialize = true;//判斷是否需要初始化
	private boolean start = false;//判斷是否開始
	private boolean pause = false;//判斷是否暫停

    private static int eat_amount = 0;//吃的個數
	private static int score = 0;//分數
	private static int information = 0;//傳遞遊戲信息
}

class Snake
{
    
}
*/


// =分數導入/導出=
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 51273acc48e4102188ed70e31d40381c6da069b1
