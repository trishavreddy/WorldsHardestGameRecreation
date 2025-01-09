import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Level10 extends JPanel implements KeyListener,Runnable
{
	private int x;
	private int y;
	private String failsText;
	private Rectangle r1;
	private Rectangle r2;
	private Ellipse2D.Double c1,c2,c3,c4,c5;
	private int vx, vy;
	private JFrame frame;
	private Thread t;
	private boolean gameOn;
	private Font f;
	private Color color;
	private Polygon poly;
	private int cLeft;
	private int cRight;
	public Level10()
	{
		frame=new JFrame();
		x=317;
		y=115;
		vx=0;
		vy=0;
		failsText = "FAILS: 0";
		cLeft = 237;
		cRight = 517;
		int c1y = 167;
		int c2y = 207;
		int c3y = 247;
		int c4y = 287;
		int c5y = 327;
		gameOn=true;
		r1 = new Rectangle(x,y,22,22);
		c1 = new Ellipse2D.Double(cLeft,c1y,25,25);
		c2 = new Ellipse2D.Double(cRight,c2y,25,25);
		c3 = new Ellipse2D.Double(cLeft,c3y,25,25);
		c4 = new Ellipse2D.Double(cRight,c4y,25,25);
		c5 = new Ellipse2D.Double(cLeft,c5y,25,25);

		f=new Font("SansSerif",Font.PLAIN,30);
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(800,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t=new Thread(this);
		t.start();
		int fails = 0;
		int polyX[] = {300, 360, 360, 330, 330,360,360,300,300,480,480,420,420,450,450,420,420,480,480,540,540,480,480,300,300,240,240,300};
		int polyY[] = {100, 100, 160, 160, 190,190,280,280,310,310,280,280,190,190,160,160,100,100,250,250,340,340,370,370,340,340,250,250};
		poly = new Polygon(polyX, polyY, 28);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Color boxGreen = new Color(158, 242, 155);
		Color purpleSquare = new Color(224, 218, 254);
		Color whiteSquare = new Color(248,247,255);

		//fill background
		g2d.setPaint(new Color(170,165,255));
		g2d.fillRect(0,0,800,500);
		g2d.setColor(whiteSquare);
		g2d.fill(poly);


		//draw scoreboard
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0,800, 40);
		g2d.setColor(Color.WHITE);
		g2d.setFont(f);
		g2d.drawString("LEVEL: 10",10,30);
		g2d.drawString(failsText,630,30);

		//boxes
		g2d.setColor(boxGreen);
		g2d.fillRect(300,100,60,60);
		g2d.fillRect(420,100,60,60);

/*

		//tiles
		g2d.setColor(purpleSquare);
		g2d.fillRect(230,360,40,40);
		g2d.fillRect(550,120,40,40);
		for(int x = 230; x <= 510; x+=40)
		{
			if((x-230)%80 == 0)
			{
				for(int y = 200; y <= 280; y+=80)
				{
					g2d.fillRect(x,y,40,40);
				}
			}
			else
			{
				for(int y = 160; y <= 320; y+=80)
				{
					g2d.fillRect(x,y,40,40);
				}
			}
		}

*/

		//polygon
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(poly);

		//your character
		g2d.setColor(Color.RED);
		r1 = new Rectangle(x,y,22,22);
		g2d.fill(r1);
/*
		//enemy
		g2d.setColor(Color.BLUE);
		c1 = new Ellipse2D.Double(cLeft,167,25,25);
		g2d.fill(c1);
		c2 = new Ellipse2D.Double(cRight,207,25,25);
		g2d.fill(c2);
		c3 = new Ellipse2D.Double(cLeft,247,25,25);
		g2d.fill(c3);
		c4 = new Ellipse2D.Double(cRight,287,25,25);
		g2d.fill(c4);
		c5 = new Ellipse2D.Double(cLeft,327,25,25);
		g2d.fill(c5);
		if(!gameOn)
		{
			g2d.setColor(whiteSquare);
			g2d.fillRect(310,240, 160, 40);
			g2d.setColor(Color.BLACK);
			g2d.drawString("YOU WON!",310,270);

		}

*/


	}
	public void run()
	{
		int fails = 0;
		boolean movingRight=true;
		while(true)
		{
			if(gameOn)
			{
				//moving balls
				if(cLeft==237 && cRight == 517)
					movingRight=true;
				else if(cLeft==517 && cRight==237)
					movingRight=false;
				if(movingRight)
				{
						cLeft++;
						cRight--;
				}
				else
				{
						cLeft--;
						cRight++;
				}

				Rectangle r = new Rectangle(x+vx-2,y+vy-2,26,26);
				if(poly.contains(r))
				{
					x += vx;
					y += vy;
				}


				//check for crashes
				if (c1.intersects(r1) || c2.intersects(r1) || c3.intersects(r1) || c4.intersects(r1) || c5.intersects(r1))
				{
					fails++;
					failsText = "FAILS: "+fails;
					x=317;
					y=115;
				}

				//if won
				if(x>=590)
					gameOn=false;


				//this is the code for delay
				try
				{
					t.sleep(6);
				}catch(InterruptedException e)
				{
				}
				repaint();
			}
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		//look up keycodes online.  39 is right arrow key
		System.out.println(ke.getKeyCode() + " x" + x + " y:" + y);
		if(ke.getKeyCode()==39)
			vx=1;
		if(ke.getKeyCode()==37)
			vx=-1;
		if(ke.getKeyCode()==38)
			vy=-1;
		if(ke.getKeyCode()==40)
			vy=1;



	}
	public void keyReleased(KeyEvent ke)
	{
		vx=0;
		vy=0;

	}
	public void keyTyped(KeyEvent ke)
	{
	}
	public static void main(String args[])
	{
		Level10 app=new Level10();
	}
}