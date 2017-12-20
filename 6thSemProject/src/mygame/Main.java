package mygame;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferStrategy;


public class Main implements Runnable,KeyListener {
	
	
	private Graphics g;
	private BufferStrategy bs; // used to provide good graphical display
	public Image ballImg=null; // used for getting image 
	public Image playerImg =null;
	public Image brickImg=null;
	
	
	Thread t;
	boolean isRunning=false;
	
	
	private Display display; // it is just the class we created 
	public  String title;
	public int width,height;
	
	int player_xpos,player_ypos,player_height,player_width;
	int ball_xpos,ball_ypos,ball_radius;
	
	int ball_xspeed,ball_yspeed;
	int player_speed;
	
	public boolean BallIsMoving=false;
	
	public Main(String title,int width, int height)
	{
		
		this.title=title;
		this.width=width;
		this.height=height;
		
		
		display=new Display(title,width,height);
		
		player_xpos=20;
		player_ypos=600;
		player_height=40;
		player_width=120;
		player_speed=1;
		
		ball_xpos=200;
		ball_ypos=585;
		ball_radius=20;
		ball_xspeed=3;
		ball_yspeed=3;
		
		
		
			
		
		display.getCanvas().addKeyListener(this);
		display.getCanvas().setFocusable(true);
	}
	
	// write graphics related code here 
	public synchronized void graphicsRenderer()
	{
		bs=display.getCanvas().getBufferStrategy(); // this is used to provide smooth graphics
		
		if(bs==null) // used so that bs value is set only once
		{
			display.getCanvas().createBufferStrategy(3); // 3 denotes triple buffering
														// 2 denotes double buffering 
													   // more than 3 causes problems
			return;
		}
		
		g=bs.getDrawGraphics(); // graphics is initialized
		g.clearRect(0, 0, width, height); // clears whole screen
		
		
		
		playerImg =display.getImage("icons\\player.png");
		g.drawImage(playerImg, player_xpos, player_ypos, player_width, player_height, null, null);
		
		ballImg =display.getImage("icons\\green_ball.png");
		g.drawImage(ballImg, ball_xpos, ball_ypos, ball_radius, ball_radius, null, null);
		
		bs.show();
		
		g.dispose();
		
	}
	
	//write main game logic here//
	/////////////////////////////
	public synchronized void Update()
	{
		moveBall();
		
		int distance =calculateDistance(player_xpos+player_width/2,player_ypos+player_height/2,ball_xpos,ball_ypos);
		System.out.println(distance);
	}
	
    int calculateDistance(int x1, int y1, int x2, int y2)
	{
	    int x=(x2-x1)*(x2-x1);
	    int y= (y2-y1)*(y2-y1);
	    
	    
		
		return   (int) Math.sqrt(x+y);
		
		
	}
	
	void calculateCollision()
	{
		
	}
	
	

	
	public synchronized void run()
	{
		
		 int fps=60;
		 double timePerTick=1000000000/fps;
		 double delta=0;
		 long now;
		 long lastTime=System.nanoTime();
		 while(isRunning) // this is for infinite loop with 60 fps
		 {
			 now =System.nanoTime();
			 delta+=(now-lastTime)/timePerTick;
			 lastTime=now;
			 
			 if(delta>=1)
			 {
				
				 graphicsRenderer();
				 Update();
				 delta--;
			 }
			
		 }
		 
		 stop();
		System.out.println("hello");
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			if(player_xpos<=860)
			{
				player_xpos+=10;
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			if(player_xpos>=20)
			{
				player_xpos-=10;
			}
		}
		
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			ball_xpos--;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			ball_xpos++;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			ball_ypos++;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			ball_ypos--;
		}
	}
	
	public void keyReleased(KeyEvent e) 
	{
		// not used 
	}

	
	public void keyTyped(KeyEvent e)
	{
		// not used
		
	}
	
	public void moveBall()
	{
		if(BallIsMoving)
		{
			ball_xpos=ball_xpos+ball_xspeed;
			ball_ypos=ball_ypos+ball_yspeed;
		}
		
		
		if(ball_xpos<=20)
		{
			ball_xspeed=-ball_xspeed;
		
		}
		if(ball_xpos>=960)
		{
			ball_xspeed=-ball_xspeed;
		}
		
		if(ball_ypos<=20)
		{
			ball_yspeed=-ball_yspeed;
		}
		if(ball_ypos>=660)
		{
			ball_yspeed=-ball_yspeed;
		}
	}
	
	
	public synchronized void start()
	{
		if(isRunning)
			return;
		isRunning=true;
		t=new Thread(this);
		t.start();
	}
	
	public synchronized void stop()
	{
		if(!isRunning)
		return;
		
		isRunning=false;
		try 
		{
			t.join();
		} 
		
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
	

}
