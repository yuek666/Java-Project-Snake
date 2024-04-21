
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
//*/
//import java.awt.*;
import javax.swing.*;
//import javax.xml.crypto.Data;

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
    
    public class GamePanel extends JPanel implements KeyListener , ActionListener{

        public static int[] snake_x = new int[50];
        public static int[] snake_y = new int[50];
        
        
        
        
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
