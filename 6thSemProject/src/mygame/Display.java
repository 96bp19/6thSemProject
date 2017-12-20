package mygame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Display	
{
	JFrame frame;
	Canvas canvas;
	String title;
	int height,width;
	
	public  Display(String title, int width ,int height)
	{
		
		this.height=700;
		this.width=1000;
		this.title=title;
		
		displayFrame();
	}
	
	void displayFrame()
	{
		frame=new JFrame(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas=new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		
		frame.add(canvas);
		frame.pack();
		
		
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public Image getImage(String path)
	{
		Image img =null;
		
		try {
			//img=new ImageIcon("D:\\6thSemProject\\6thSemProject\\src\\abcd.jpg").getImage();
			img = new ImageIcon(getClass().getResource(path)).getImage();
		}
		
		catch(Exception e)
		{
			System.out.println("no image found");
			System.exit(1);
		}
		
		
		
		
		return img;
	}

	
	
	
	
}
