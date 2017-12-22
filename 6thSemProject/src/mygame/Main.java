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
	public boolean CollisionDetected=false;
	
	int count =0;
	
	
	public Main(String title,int width, int height)
	{
		
		this.title=title;
		this.width=width;
		this.height=height;
		
		
		display=new Display(title,width,height);
		
		player_xpos=20;
		player_ypos=600;
		player_height=40;
		player_width=100;
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
		
		calculateDistance(player_xpos+player_width/2,player_ypos+player_height/2,ball_xpos,ball_ypos);
	}
	
    int calculateDistance(int x1, int y1, int x2, int y2)
	{
	    int x=(x2-x1)*(x2-x1);
	    int y= (y2-y1)*(y2-y1);
	    
	    System.out.println("x1 : "+x1+"    y1 : "+y1);
	    System.out.println(" x2 : "+x2+"    y2 : "+y2);
	    
	    int distance =(int) Math.sqrt(x+y);
	    
	    calculateCollision(x1,y1,x2,y2,distance);
	    
	    return  distance;
	    
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
		
		if(!BallIsMoving)
		{
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			{
				BallIsMoving=true;
			}
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
			count=0;
		
		}
		if(ball_xpos>=960)
		{
			ball_xspeed=-ball_xspeed;
			count=0;
		}
		
		if(ball_ypos<=20)
		{
			ball_yspeed=-ball_yspeed;
			count=0;
		}
		if(ball_ypos>=660)
		{
			ball_yspeed=-ball_yspeed;
			count=0;
		}
		
		
		
		if(CollisionDetected && count==0)
		{
			
			
			CollisionDetected=false;
			if(ball_ypos>player_ypos)
			{
				ball_xspeed=-ball_xspeed;
			}
			else if( ball_ypos<player_ypos)
			{
				ball_yspeed =-ball_yspeed;
			}
			
			count++;
			
			
			
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
	
	void calculateCollision(int x1,int y1, int x2, int y2, int distance)
	{
		 if(x1<x2 && distance<54 && y2-y1 <40)
		    {
		    	if(y1>y2)
		    	{
		    		if(y1-y2 <40)
		    		{
		    			if(!CollisionDetected)
					    {
					    	CollisionDetected =true;
					    }
		    		}
		    		else
	    				CollisionDetected =false;
		    	}
		    	else if(y2>y1)
		    	{
		    		if(y2-y1 <20)
		    		{
		    			if(!CollisionDetected)
					    {
					    	CollisionDetected =true;
					    }
		    		}
		    		else
	    				CollisionDetected =false;
		    	}
		    	
				    
		    	
		    }
		    
		    else if(x1>x2 && distance<70 )
		    {
		    	
		    	if(y1>y2)
		    	{
		    		if(y1-y2 <40)
		    		{
		    			if(!CollisionDetected)
					    {
					    	CollisionDetected =true;
					    }
		    		}
		    		else
		    			CollisionDetected =false;
		    			
		    	}
		    	else if(y2>y1)
		    	{
		    		if(y2-y1 <20)
		    		{
		    			if(!CollisionDetected)
					    {
					    	CollisionDetected =true;
					    }
		    			
		    				
		    		}
		    		else
	    				CollisionDetected =false;
		    	}
				 
		    	
		    }
		    else if(x1==x2 && distance <40)
		    {
		    	  {
		  	    	
		  	    	
					    if(!CollisionDetected)
					    {
					    	CollisionDetected =true;
					    }
			    	
			    }
		    }
		    	
		   
		    
		    else 
		    {
		    	CollisionDetected =false;
		    }
	}
	

}
