import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener 
{
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 48;
	
	private Timer timer;
	private int delay=5;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -2;
	private int ballYdir = -4;
	
	private MapGenerator map;
	//Yeni mapin oluşturulması
	public Gameplay()
	{		
		map = new MapGenerator(4, 12);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	
	//map renklerinin ayarlanması
	public void paint(Graphics g)
	{    		
		// Arkaplan
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//Mapin Çizimi
		map.draw((Graphics2D) g);
		
		//Sınırlar
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Puanlar 		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,30);
		
		//Kürek
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//Top
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
	
		// Oyun kazanıldığında
		if(totalBricks <= 0)
		{
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
		// Oyun kaybedildiğinde
		if(ballposY > 570)
        {
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Game Over, Scores: "+score, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);        
        }
		
		g.dispose();
	}	
	//Tuş atamaları
	public void keyPressed(KeyEvent e) 
	{
		//Sağ'a basıldığında sınırın kontrol edilmesi sınıra gelinmemişse sağ'a ilerlenmesi
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
        }
		
		//Sol'a basıldığında sınırın kontrol edilmesi sınıra gelinmemişse sol'a ilerlenmesi
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
        }
		//Oyun oynanmıyorsa enter'a basıldığında başlatmaya yarıyor
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -2;
				ballYdir = -4;
				playerX = 310;
				score = 0;
				totalBricks = 48;
				map = new MapGenerator(4, 12);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	//sağ'a hareket
	public void moveRight()
	{
		play = true;
		playerX+=20;	
	}
	
	//sol'a hareket
	public void moveLeft()
	{
		play = true;
		playerX-=20;	 	
	}
	
	//Kenarlara çarpıldığında yapılacaklar
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(play)
		{	
			//Top tavana çarptığında hızının ters yöne dönmesi 
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			//Top küreğe açılı geldiğinde topun hızlanması
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			//Top küreğe düşük açılı gelirse hızının değişmeden dönmesi
			else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// topla harita çarpışmasının kontrolü 		
			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//Tuğla hitbox'ının oluşturulması
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							totalBricks--;
							
							// top tuğlanın sağına veya soluna çarptığında yön değiştirmesi
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							//top tuğlanın üstüne veya altına çarptığında yön değiştirmesi
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			//Başlangıç hızı
			ballposX += ballXdir;
			ballposY += ballYdir;
			//Topun  sol tarafa çarptığında yön değiştirmesi
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			//Topun tavana çarptığında yön değiştirmesi
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			//Topun sağ tarafa çarptığında yön değiştirmesi
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}		
			
			repaint();		
		}
	}
}
