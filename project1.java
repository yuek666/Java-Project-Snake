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

import java.awt.*;
import javax.swing.*;
//import javax.xml.crypto.Data;

import java.net.URL;

//測試




public class project1 {
    
    public class Data {
        //貪吃蛇頭部
        public static URL upUrl = Data.class.getResource("/statics/up.png");
        public static ImageIcon up = new ImageIcon(upUrl);
        public static URL downUrl = Data.class.getResource("/statics/down.png");
        public static ImageIcon down = new ImageIcon(downUrl);
        public static URL leftUrl = Data.class.getResource("/statics/left.png");
        public static ImageIcon left = new ImageIcon(leftUrl);
        public static URL rightUrl = Data.class.getResource("/statics/right.png");
        public static ImageIcon right = new ImageIcon(rightUrl);
        //貪食蛇身體
        public static URL bodyUrl = Data.class.getResource("/statics/body.png");
        public static ImageIcon body = new ImageIcon(bodyUrl);
        //食物
        public static URL foodUrl = Data.class.getResource("/statics/food.png");
        public static ImageIcon food = new ImageIcon(foodUrl);
    }
    
    public static void main(String[] args){
        //建立遊戲視窗
        JFrame frame = new JFrame("Java-貪吃蛇小遊戲");//標題
        frame.setSize(900,720);//視窗大小
        frame.setLocationRelativeTo(null);//視窗顯示螢幕中間
        frame.setResizable(false);//固定視窗大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//設定表單關閉事件
        frame.add(new GamePanel());//新增遊戲內容
        frame.setVisible(true);//設定表單可見
    }
    

    public class GamePanel extends JPanel implements KeyListener, ActionListener {
        int[] snakeX = new int[500];//貪吃蛇橫座標
        int[] snakeY = new int[500];//貪吃蛇縱座標
        int foodX;//食物橫座標
        int foodY;//食物蛇縱座標
        int length;//貪吃蛇的長度
        String  direction;//貪吃蛇頭方向
        int score;//積分
        Random r = new Random();
        Timer timer = new Timer(100,this);
        boolean isStart;
        boolean isFail;
        //建構函式
        public GamePanel(){
            init();
            this.setFocusable(true);
            this.addKeyListener(this);
            timer.start();
        }
        private void init(){
            length=3;
            snakeX[0]=100;snakeY[0]=100;
            snakeX[1]=75;snakeY[1]=100;
            snakeX[2]=50;snakeY[2]=100;
            direction = "R";
            eat(foodX,foodY);
            isStart = false;
            isFail = false;
            score = 0;
    
        }
        private void eat(int x,int y){
            x= 25 + 25*r.nextInt(34);
            y= 75 + 25*r.nextInt(24);
            for (int i = 0; i < length; i++) {
                if(snakeX[i]==x&&snakeY[i]==y){
                    x = 25 + 25*r.nextInt(34);
                    y = 75 + 25*r.nextInt(24);
                }
            }
            foodX = x;foodY = y;
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(Color.white);//設定背景板為白色
            //畫標題
            g.setColor(Color.GREEN);
            g.setFont(new Font("幼圓",Font.BOLD,50));
            g.drawString("貪吃蛇遊戲",300,60);
            //繪製遊戲區域
            g.setColor(Color.GRAY);
            g.fillRect(25,75,850,600);
            //畫貪吃蛇頭部
            if(direction=="R"){
                Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            else if(direction=="L"){
                Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            if(direction=="U"){
                Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            else if(direction=="D"){
                Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
            }
            //畫身體
            for (int i = 1; i < length ; i++) {
                Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
            }
            //畫食物
            Data.food.paintIcon(this,g,foodX,foodY);
            //繪製積分欄
            g.setColor(Color.BLACK);
            g.setFont(new Font("幼圓",Font.BOLD,20));
            g.drawString("長度："+length,730,30);
            g.drawString("得分："+score,730,60);
            //遊戲開始提醒
            if(isStart==false){
                g.setColor(Color.BLACK);
                g.setFont(new Font("幼圓",Font.BOLD,40));
                g.drawString("按空格鍵開始遊戲",300,300);
            }
            //失敗判斷
            if(isFail){
                g.setColor(Color.RED);
                g.setFont(new Font("幼圓",Font.BOLD,40));
                g.drawString("遊戲失敗，按空格鍵重新開始",300,300);
            }
        }
    
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();//獲取按下的按鍵
            //判斷空格
            if(keyCode==KeyEvent.VK_SPACE){
                if(isFail){
                    isFail = false;
                    init();
                }
                else{
                    isStart = !isStart;
                }
                repaint();
            }
            //判斷方向
            if(keyCode==KeyEvent.VK_LEFT&&direction!="R"){
                direction = "L";
            }
            else if(keyCode==KeyEvent.VK_RIGHT&&direction!="L"){
                direction = "R";
            }
            else if(keyCode==KeyEvent.VK_UP&&direction!="D"){
                direction = "U";
            }
            else if(keyCode==KeyEvent.VK_DOWN&&direction!="U"){
                direction = "D";
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
    
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
    
    
        @Override
        public void actionPerformed(ActionEvent e) {
            //判斷遊戲狀態
            if(isStart&&!isFail){
                //移動身體
                for (int i = length-1; i > 0 ; i--) {
                    snakeX[i] = snakeX[i-1];
                    snakeY[i] = snakeY[i-1];
                }
                //移動頭部
                if(direction=="R"){
                    snakeX[0] += 25;
                    if(snakeX[0]>850){
                        snakeX[0] = 25;
                    }
                }
                else  if(direction=="L"){
                    snakeX[0] -= 25;
                    if(snakeX[0]<25){
                        snakeX[0] = 850;
                    }
                }
                else  if(direction=="U"){
                    snakeY[0] -= 25;
                    if(snakeY[0]<75){
                        snakeY[0] = 650;
                    }
                }
                else  if(direction=="D"){
                    snakeY[0] += 25;
                    if(snakeY[0]>650){
                        snakeY[0] = 75;
                    }
                }
                //吃食物
                if(snakeX[0]==foodX&&snakeY[0]==foodY){
                    length++;
                    score += 10;
                    eat(foodX,foodY);
                }
                //死亡判定
                for (int i = 1; i < length; i++) {
                    if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]){
                        isFail=true;
                    }
                }
                repaint();
            }
            timer.start();
        }
    }
}

