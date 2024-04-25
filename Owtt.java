
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.management.timer.TimerMBean;

import javax.management.timer.TimerMBean;
//*/
import java.awt.*;
import java.awt.*;
import javax.swing.*;

//*/



public class Owtt {
    
    //圖檔存取
    public class Data {
        //貪吃蛇頭部
        public static ImageIcon up_head = new ImageIcon(); //上
        public static ImageIcon down_head = new ImageIcon(); //下
        public static ImageIcon left_head = new ImageIcon(); //左
        public static ImageIcon right_head = new ImageIcon(); //右
        //貪食蛇身體
        public static ImageIcon body = new ImageIcon(); //中
        //食物
        public static ImageIcon food = new ImageIcon( "ImageLib\fruit.png"); //食
    }

    public static void main(String[] args) {
        //建立遊戲視窗
        JFrame frame1 = new JFrame("Java專題-貪吃蛇小遊戲");//標題
        frame1.setSize(900,720);//視窗大小
        frame1.setLocationRelativeTo(null);//視窗顯示螢幕中間
        frame1.setResizable(false);//固定視窗大小
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//設定表單關閉事件
        //frame1.add( new GamePanel(  ) ) ;//新增遊戲內容
        frame1.setVisible(true);//設定表單可見
    }
    
    public class GamePanel extends JPanel implements KeyListener , ActionListener {
    public class GamePanel extends JPanel implements KeyListener , ActionListener {

        public static int[] snake_x = new int[50];
        public static int[] snake_y = new int[50];
        int food_x;//食物橫座標
        int food_y;//食物縱座標
        int snake_leng;//貪吃蛇的長度
        String head_direct;//蛇頭方向
        int score;//積分
        Random rand = new Random();
        Timer timer = new Timer();
        boolean start;
        boolean fail;
        
        public GamePanel(){
            init();
            this.setFocusable(true);
            this.addKeyListener(this);
            timer.schedule(null, null );
        }

        //小蛇初始位置
        public void init(){
            snake_leng = 3;
            snake_x[0] = 100;
            snake_y[0] = 100;
            snake_x[1] = 75;
            snake_y[1] = 100;
            snake_x[2] = 50;
            snake_y[2] = 100;
            head_direct = "R" ;
            Eat( food_x ,food_y );
            start = false;
            fail = false;
            score = 0;    
        }

        //食物位置隨機
        public void Eat( int x , int y )
        {
            x = 25 + 25 * rand.nextInt(34);
            y = 75 + 25 * rand.nextInt(24);
            for ( int i = 0; i < snake_leng ; i++ ) 
            {
                if( snake_x[i] == x && snake_y[i] == y )
                {
                    x = 25 + 25 * rand.nextInt(34);
                    y = 75 + 25 * rand.nextInt(24);
                }
            }
            food_x = x;
            food_y = y;
        }

        //介面繪製
        public void PaintStruct( Graphics g )
        {
            super.paintComponent( g );
            this.setBackground( Color.white );//設定背景板為白色

            //畫標題
            g.setColor( Color.GREEN );
            g.setFont( new Font("幼圓",Font.BOLD,50 ) );
            g.drawString("貪吃蛇",300,60);

            //繪製遊戲區域
            g.setColor(Color.GRAY);
            g.fillRect(25,75,850,600);

            //畫貪食蛇頭部
            if ( head_direct == "R" )
            {
                Data.right_head.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            else if ( head_direct == "L" )
            {
                Data.left_head.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            if ( head_direct == "U" )
            {
                Data.up_head.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            else if ( head_direct == "D" )
            {
                Data.down_head.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }

            //畫身體
            for ( int i = 0 ; i < snake_leng ; i ++ )
            {
                Data.body.paintIcon( this , g , snake_x[i] ,snake_y[i] );
            }
            //畫食物
            Data.food.paintIcon( this , g , food_x , food_y );

            //畫積分欄位
            g.setColor( Color.BLACK );
            //g.setFont( new Font( "test1" , Font.BOLD , 20 ) );
            g.drawString( "分數" + score , 730 , 60 );

            if( !start ){
                g.setColor( Color.BLACK );
                //g.setFont(new Font("asd",Font.BOLD,40));
                g.drawString("按空格鍵開始遊戲",300,300);
            }
            //失敗判斷
            if( !fail ){
                g.setColor(Color.RED);
                //g.setFont(new Font("幼圓",Font.BOLD,40));
                g.drawString("遊戲失敗，按空格鍵重新開始",300,300);
            }
            
                    
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //判斷遊戲狀態
            if( start && !fail )
            {
                //移動身體
                for ( int i = snake_leng - 1 ; i > 0 ; i -- )
                {
                    snake_x[i] = snake_x[i-1];
                    snake_y[i] = snake_y[i-1];
                }

                //移動頭部
                if ( head_direct == "R" )
                {
                    snake_x[0] += 25;
                    if ( snake_x[0] > 850 )
                    {
                        snake_x[0] = 25;
                    }
                }
                else  if ( head_direct == "L" )
                {
                    snake_x[0] -= 25;
                    if ( snake_x[0] < 25 )
                    {
                        snake_x[0] = 850;
                    }
                }
                else  if ( head_direct == "U" )
                {
                    snake_y[0] -= 25;
                    if ( snake_y[0] < 75) 
                    {
                        snake_y[0] = 650;
                    }
                }
                else  if ( head_direct == "D" )
                {
                    snake_y[0] += 25;
                    if ( snake_y[0] > 650 )
                    {
                        snake_y[0] = 75;
                    }
                }

                //吃食物
                if( snake_x[0] == food_x && snake_y[0] == food_y )
                {
                    snake_leng ++ ;
                    score += 10;
                    Eat( food_x , food_y );
                }
                //死亡判定
                for (int i = 1; i < snake_leng ; i ++ ) 
                {
                    if( snake_x[0] == snake_x[i] && snake_y[0] == snake_y[i] )
                    {
                        fail = true;
                    }
                }
                repaint();
            }
            timer.schedule(null, ABORT);;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();//獲取按下的按鍵
        //判斷空格
        if( keyCode == KeyEvent.VK_SPACE ){
            if( fail ){
                fail = false;
                init();
            }
            else{
                start = !start;
            }
            repaint();
        }
        //判斷方向
        if ( keyCode == KeyEvent.VK_LEFT && head_direct != "R" )
        {
            head_direct = "L";
        }
        else if ( keyCode == KeyEvent.VK_RIGHT && head_direct != "L" )
        {
            head_direct = "R";
        }
        else if ( keyCode == KeyEvent.VK_UP && head_direct != "D" )
        {
            head_direct = "U";
        }
        else if ( keyCode == KeyEvent.VK_DOWN && head_direct != "U" )
        {
            head_direct = "D";
        }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }

    }



}