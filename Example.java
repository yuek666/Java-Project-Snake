import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

// == 主程式
public class Projext_T extends JFrame {

	private static GamePanel p1 = new GamePanel();//創建遊戲面板
	private static InformationPanel p2 = new InformationPanel();//創建信息面板


	public Projext_T()throws IOException, Exception
	{
		
		//配置框架的佈局
		setLayout( new BorderLayout() );
		add( p1 , BorderLayout.CENTER );
		add( p2 , BorderLayout.EAST );
	}
	
	public static void main(String[] args) throws IOException, Exception {
		
		JFrame frame = new Projext_T();//新建框架

		//配置框架
		frame.setTitle("貪吃蛇");
		frame.setSize(1100, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		//新建線程
		Thread thread1 = new Thread( p1 );
		thread1.start();
		Thread thread2 = new Thread( p2 );
		thread2.start();
		
	}
}

//左邊
class GamePanel extends JPanel implements Runnable
{
    public static final int Per_Unit_Length = 20;//單位長度
	public static final int Multiples_Time = 15;//倍數
	public static final int Half_Side = Multiples_Time * Per_Unit_Length;//遊戲邊框的一半長 = 倍數 * 單位長度
        
    private boolean initialize = true;//判斷是否需要初始化
	private boolean start = false;//判斷是否開始
	private boolean pause = false;//判斷是否暫停
	private static boolean nibaikuso = false;
	private static boolean status_change = true;//判斷第二次

    private static int eat_amount = 0;//吃的個數
	private static double time_run = 0;
	private static int score = 0;//分數
	private static int information = 0;//傳遞遊戲信息

	private static Snake snake = new Snake();//蛇
	private Dot target = new Dot();//目標

	protected void paintComponent( Graphics g )
	{
		//System.out.println("test");
		super.paintComponent( g );

		int x_centre = getWidth() / 2;
		int y_centre = getHeight() / 2;

		int x_random;
		int y_random;

		if ( initialize )
		{
			initialize = false;

			//遊戲邊筐
			//System.out.println("畫邊框test");
			g.drawRect( x_centre - Half_Side , y_centre - Half_Side  , Half_Side * 2 , Half_Side * 2 );

			//蛇頭
			snake.GetHead().SetX( x_centre );
			snake.GetHead().SetY( y_centre );		
			g.setColor( Color.ORANGE );
			g.fillOval( snake.GetHead().GetX() , snake.GetHead().GetY() , Per_Unit_Length , Per_Unit_Length );

			//標的
			do
			{
				x_random = x_centre - Half_Side + ( (int)( Math.random() * Multiples_Time * 2 ) ) * Per_Unit_Length;
				y_random = y_centre - Half_Side + ( (int)( Math.random() * Multiples_Time * 2 ) ) * Per_Unit_Length;
			} while ( x_random == snake.GetHead().GetX() && y_random == snake.GetHead().GetY() ); //重疊時

			target.SetX( x_random );
			target.SetY( y_random );
			g.setColor(Color.DARK_GRAY);
			g.fillOval( target.GetX() , target.GetY() , Per_Unit_Length , Per_Unit_Length );
		}
		else
		{
			//繪畫遊戲邊框
			g.drawRect( x_centre - Half_Side , y_centre - Half_Side , Half_Side * 2 , Half_Side * 2 );

			//繪畫蛇身
			g.setColor(Color.MAGENTA);
			for( int i = 0;i < snake.GetBody().size() ; i++ )
			{
				g.fillOval( snake.GetBody().get(i).GetX(), snake.GetBody().get(i).GetY(), Per_Unit_Length , Per_Unit_Length );
			}

			//繪畫蛇頭
			g.setColor( Color.ORANGE );
			g.fillOval( snake.GetHead().GetX(), snake.GetHead().GetY(), Per_Unit_Length, Per_Unit_Length );

			//如果蛇吃到點心，則更新點心
			if( IsEncountered() )
			{
				do
				{
					x_random = x_centre - Half_Side + ( (int)(Math.random() * Multiples_Time * 2) ) * Per_Unit_Length;
					y_random = y_centre - Half_Side + ( (int)(Math.random() * Multiples_Time * 2) ) * Per_Unit_Length;
				} while( x_random == snake.GetHead().GetX() && y_random == snake.GetHead().GetY() );
				target.SetX( x_random );
				target.SetY( y_random );
			}
			g.setColor( Color.DARK_GRAY );
			g.fillOval( target.GetX(), target.GetY() , Per_Unit_Length , Per_Unit_Length );

			//如果遊戲結束，則追加繪畫GAME OVER
			if( IsCrushed() )
			{
				g.setColor(Color.BLACK);
				FontMetrics fm = g.getFontMetrics();
				int stringWidth = fm.stringWidth("GAME OVER" );
				int stringAscent = fm.getAscent();
				int xCoordinate = x_centre - stringWidth / 2;
				int yCoordinate = y_centre - stringAscent / 2;
				g.drawString("GAME OVER" , xCoordinate , yCoordinate );	
			}					
		}		
	}

	public GamePanel(){
		//配置面膽屬性
		//System.out.println("運行");
		setFocusable(true);
		setFont( new Font("Californian FB" ,Font.BOLD ,80 ) );//字體，顏色要更改
	
		//註冊鍵盤監聽器
		addKeyListener( new KeyAdapter()
		{
			public void keyPressed( KeyEvent e ) //鍵盤控制
			{
				int direction = snake.GetDirection();
				switch( e.getKeyCode() )
				{
					case KeyEvent.VK_UP://上
						if( start && !pause && !IsCrushed() )
						{
								if( direction != Snake.Direction_Up && direction != Snake.Direction_Down)
								{
									snake.SetDirection( Snake.Direction_Up );
									changeSnakeLocation();
								}
							}
						break;

					case KeyEvent.VK_DOWN://下
						if( start && !pause && !IsCrushed() )
						{
								if(direction != Snake.Direction_Down && direction != Snake.Direction_Down)
								{
									snake.SetDirection( Snake.Direction_Down );
									changeSnakeLocation();
								}
							}
						break;

					case KeyEvent.VK_LEFT://左
						if( start && !pause && !IsCrushed() )
						{
								if(direction != Snake.Direction_Left && direction != Snake.Direction_Right )
								{
									snake.SetDirection( Snake.Direction_Left );
									changeSnakeLocation();
								}
							}
						break;

					case KeyEvent.VK_RIGHT://右
						if( start && !pause && !IsCrushed() )
						{
								if(direction != Snake.Direction_Left && direction != Snake.Direction_Right )
								{
									snake.SetDirection( Snake.Direction_Right );
									changeSnakeLocation();
								}
							}
						break;

					case KeyEvent.VK_ENTER://enter =>開始
						if( IsCrushed() )
						{
							//如果遊戲結束，則重新開始遊戲
							snake.SetDirection( Snake.Direction_Right );
							snake.SetSpeed( Snake.SPEED_3 );
							snake.SetBody( new LinkedList<Dot>() );
							
							initialize = true;
							start = false;
							pause = false;

							eat_amount = 0;
							time_run = 0;
							information = 0;
							score = 0;

							repaint();
						}
						else
						{
							start = true;
						}
						status_change = true;
						break;

					case KeyEvent.VK_SPACE: //空格 =>暫停
						if( start && !IsCrushed() )
						{
							pause = !pause;
							//System.out.println("time test:");
						}								
						break;

					//速度調整
					case KeyEvent.VK_F1:snake.SetSpeed( Snake.SPEED_1 );break;
					case KeyEvent.VK_F2:snake.SetSpeed( Snake.SPEED_2 );break;
					case KeyEvent.VK_F3:snake.SetSpeed( Snake.SPEED_3 );break;
					case KeyEvent.VK_F4:snake.SetSpeed( Snake.SPEED_4) ;break;
					case KeyEvent.VK_F5:snake.SetSpeed( Snake.SPEED_5 );break;					
					default:
				}
				if ( e.getKeyCode() == KeyEvent.VK_CONTROL )
				{
					nibaikuso = true;
				}
				else
				{
					nibaikuso = false;
				}
				
			}	
		});
	}

	//控制蛇自動前進
	public void run()
	{		
		while( true )
		{
			int tttt = (nibaikuso)?1:3;
			//System.out.println("time test:");
			if( start && !pause && !IsCrushed() ) 
			{
				changeSnakeLocation();
				double tmp = tttt * snake.GetSpeed() / 1000.0;
				time_run = time_run + tmp;
				//System.out.println("time test:" + snake.GetSpeed() );
			}
			try
			{	
				//System.out.println("test:" + time_run );
				
				Thread.sleep( snake.GetSpeed() * tttt );
			}
			catch( InterruptedException ex )
			{
				ex.printStackTrace();
			}
		}
	}
	
	public synchronized void changeSnakeLocation(){//改變蛇的位置信息並重畫

		//存儲蛇頭先前的信息，爲蛇身的更新提供依據
		int xPrevious = snake.GetHead().GetX();
		int yPrevious = snake.GetHead().GetY();

		//更新蛇頭位置
		switch( snake.GetDirection() )
		{
			case Snake.Direction_Up:
				snake.GetHead().SetY(yPrevious - Per_Unit_Length );
				break;
			case Snake.Direction_Down:
				snake.GetHead().SetY(yPrevious + Per_Unit_Length );
				break;
			case Snake.Direction_Left:
				snake.GetHead().SetX(xPrevious - Per_Unit_Length );
				break;
			case Snake.Direction_Right:
				snake.GetHead().SetX(xPrevious + Per_Unit_Length );
				break;
			default:
		}

		//更新蛇身位置
		if( IsEncountered() )//吃到點心
		{
			eat_amount++;
			snake.GetBody().addFirst( new Dot( xPrevious, yPrevious ) );
		}
		else	//其他
		{
			snake.GetBody().addFirst( new Dot( xPrevious , yPrevious ) );
			snake.GetBody().removeLast();
		}

		//重畫並獲取焦點
		repaint();
		requestFocus();
	}
	//判斷遊戲是否結束
	public boolean IsCrushed(){
		
		//判斷碰觸邊框
		boolean isCrushedByBorder = snake.GetHead().GetX() >= getWidth() / 2 + Half_Side  
			|| snake.GetHead().GetX() < getWidth() / 2 - Half_Side 
			|| snake.GetHead().GetY() >= getHeight() / 2 + Half_Side 
			|| snake.GetHead().GetY() < getHeight() / 2 - Half_Side;
		if( isCrushedByBorder )
		{
			information = 1;
			return true;
		}

		//判斷碰觸自身
		boolean isCrushedByItself = false;
		for( int i = 0 ; i < snake.GetBody().size() ; i++ )
		{
			if( snake.GetHead().GetX() == snake.GetBody().get(i).GetX() 
			&& snake.GetHead().GetY() == snake.GetBody().get(i).GetY() && !isCrushedByItself )
			{
				isCrushedByItself = true;
			}				
		}
		
		if( isCrushedByItself )
		{
			information = 2;
			return true;
		}
		else
		{			
			return false;
		}
	}
	//吃到點心
	public boolean IsEncountered(){
		if(snake.GetHead().GetX() == target.GetX() 
		&& snake.GetHead().GetY() == target.GetY())
		{
			SetScore();
			return true;
		}
		else
		{
			return false;
		}
	}

	public static int getEat_amount(){
		return eat_amount;
	}

	public static int getInformation(){
		return information;
	}

	public static double GetTime()
	{
		return time_run;
	}

	public static boolean GetStatus()
	{
		return status_change;
	}

	public static void SetStatus( boolean ss )
	{
		status_change = ss;
	}

	//成績公式
	public static void SetScore()
	{
		score += eat_amount * 100  / (int)Math.sqrt(snake.GetSpeed()) / (int)Math.sqrt(time_run); 
		System.out.println(score);
	}

	public static int GetScore()
	{
		return score;
	}
}



//右邊的顯示介面
class InformationPanel extends JPanel implements Runnable 
{
	private Box box = Box.createVerticalBox();//創建一個垂直盒子容器
	private JLabel[] help = new JLabel[5];//顯示幫助信息
	private JLabel score = new JLabel("分數：0");//顯示分數
	private JLabel time_show = new JLabel( "時間:" );//顯示時間
	private JLabel show = new JLabel();//顯示信息
	private JLabel score_txt = new JLabel();
	private JLabel[] history_score = new JLabel[5];//歷史成績
	private int[] score_his = new int[5];
	private Load load_d;
	

	public InformationPanel() 
	{
		try
		{
			load_d = new Load();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		//初始化數組
		for(int i = 0;i < help.length;i++)
		{
			help[i] = new JLabel();
			history_score[i] = new JLabel();
		}

		//配置字體
		Font font1 = new Font("DialogInput", Font.BOLD, 20);
		Font font2 = new Font("DialogInput", Font.BOLD + Font.ITALIC, 25);
		

		for(int i = 0;i < help.length;i++)
		{
			help[i].setFont(font1);
		}
		for(int i = 0;i < help.length;i++)
		{
			history_score[i].setFont(font1);
		}	
		score_txt.setFont(font1);
		score.setFont(font2);
		score.setForeground(Color.BLUE);
		show.setFont(font2);
		show.setForeground(Color.RED);
		time_show.setFont(font2);

		//配置幫助信息
		score_txt.setText( "歷史最高成績");
		help[0].setText("=======");
		help[1].setText("方向鍵移動蛇");
		help[2].setText("Space暫停遊戲");
		help[3].setText("按鍵F1-F5調節蛇速");
		help[4].setText("按Enter開始遊戲");

		score_his = load_d.GetHighestScore();
		for(int i = 0;i < help.length;i++)
		{
			history_score[i].setText( Integer.toString( score_his[4-i] ) );
		}

		//配置信息面板
		


		add(box);
		box.add(Box.createVerticalStrut(30));
		box.add( score_txt );
		box.add(Box.createVerticalStrut(10));
		for(int i = 0;i < help.length;i++){
			box.add(history_score[i]);
			//box.add(Box.createVerticalStrut(10));
		}
		for(int i = 0;i < help.length;i++){
			box.add(help[i]);
			box.add(Box.createVerticalStrut(10));
		}
		
		box.add(Box.createVerticalStrut(60));
		box.add(score);
		box.add(time_show);	
		box.add(Box.createVerticalStrut(60));
		box.add(show);	

		box.add(Box.createVerticalStrut(30));
		
	}

	public void run() 
	{
		//更新遊戲信息
		while(true){
			String string1 = "分數：" + Integer.toString( GamePanel.GetScore() );
			DecimalFormat df = new DecimalFormat("#.##");

			String time2 = "時間:" + df.format( GamePanel.GetTime());
			time_show.setText( time2 );
			score.setText( string1 );
			String string2 = null;
			switch( GamePanel.getInformation() )
			{
				case 0:break;
				case 1:string2 = "撞到牆壁了！";break;
				case 2:string2 = "撞到自己了！";break;
				default:
			}
			boolean testtt = GamePanel.getInformation() == 1 || GamePanel.getInformation() == 2;
			if ( GamePanel.GetStatus() && testtt )
			{
				load_d.SortScore( GamePanel.GetScore() );
				score_his  = load_d.GetHighestScore();
				for ( int i = 0 ; i < 5 ; i ++ )
				{
					history_score[i].setText( Integer.toString( score_his [4-i]));
				}
				try 
				{
					load_d.Save();
				} catch (Exception e) {
					e.printStackTrace();
				}
				GamePanel.SetStatus( false );
			}
			show.setText(string2);
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
	public static final int SPEED_1 = 100;
	public static final int SPEED_2 = 70;
	public static final int SPEED_3 = 50;
	public static final int SPEED_4 = 30;
	public static final int SPEED_5 = 10;
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
	private static int[] highest_score = new int[5];	


	public Load() throws Exception , java.io.IOException
    {
        String score_file_name = "data";
		File score_file = new File( score_file_name );		
		if ( !score_file.exists() )
		{
			System.out.println("no file");
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
		String score_file_name = "data";
		File score_file = new File( score_file_name );		
		score_file.delete();
		PrintWriter output = new PrintWriter( score_file_name );		
		output.println( highest_score[0] + " " + highest_score[1] + " " + highest_score[2] + " " + highest_score[3] + " " + highest_score[4] );
		output.close();
	}

	public int[] GetHighestScore()
	{
		return highest_score;
	}
	//排序
	public void SortScore( int input_score )
	{
		if ( input_score > highest_score[0] )
		{
			highest_score[0] = input_score;
			Arrays.sort( highest_score );
		}
	}
}
