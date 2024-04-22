
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.management.timer.TimerMBean;
//*/
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
        //frame_start.add(new GamePanel());//新增遊戲內容
        frame1.setVisible(true);//設定表單可見
    }
    
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
            //timer.start();
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
                Data.right.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            else if ( head_direct == "L" )
            {
                Data.left.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            if ( head_direct == "U" )
            {
                Data.up.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }
            else if ( head_diret == "D" )
            {
                Data.down.paintIcon( this , g , snake_x[0] , snake_y[0] );
            }

            //畫身體
            for ( int i = 0 ; i < snake_leng ; i ++ )
            {
                Data.body.paintIcon( this , g , snake_x[i] ,snake_y[i] );
            }
            //畫食物
            Data.food.paintIcon( this , g , food_x , food_y );

            //畫積分🧺
            g.setColoe( Color.BLACK );
            g.setFont( new Font( "test1" , Fon.BOLD , 20 ) );
            s.drawString( "分數" , score , 730 , 60 );


            
                    
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
        }

    }



}
