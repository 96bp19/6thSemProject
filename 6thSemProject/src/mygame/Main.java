package mygame;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.image.BufferStrategy;


public class Main implements Runnable,KeyListener {
	
	
	private Graphics g;
	private BufferStrategy bs; // used to provide good graphical display
	private Image img=null; // used for getting image 
	
	Thread t;
	boolean isRunning=false;
	
	
	private Display display; // it is just the class we created 
	public  String title;
	public int width,height;
	
	public Main(String title,int width, int height)
	{
		
		this.title=title;
		this.width=width;
		this.height=height;
		
		
		display=new Display(title,width,height);
		
		
			
		
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
		
		g.setColor(Color.GRAY);
		g.fillOval(100, 100, 50, 50);
		
		//img =display.getImage("Images\\game.jpg");
		
		//g.drawImage(img, 100, 100, 200, 200, null, null);
		
		bs.show();
		
		g.dispose();
		
	}
	
	//write main game logic here//
	/////////////////////////////
	public void Update()
	{
		
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
		
	}
	
	public void keyReleased(KeyEvent e) 
	{
		// not used 
	}

	
	public void keyTyped(KeyEvent e)
	{
		// not used
		
	}
	
	

	

}
