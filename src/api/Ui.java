package api;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ui extends JFrame{		//ena proxeiro ui gia dokimes
	
	private static final long serialVersionUID = 1L;

	static float[] array;
	static njb[] buttons;
	public static int size=10;
	static int start=size*size/2+size/2;
	static int destination=size/2;
	static ArrayList<Integer> path;
	public static Color paintThisColor=Color.LIGHT_GRAY;
	public static boolean changed = false;
	cButton green;
	cButton orange;
	cButton cyan;
	cButton light_gray;
	cButton black;
	
	public Ui()
	{
		//Random rand = new Random();
		//start=rand.nextInt(size*size);
		//destination=rand.nextInt(size*size);
	}
	
	public void myui()
	{
		JPanel mapPanel = new JPanel();
		GridLayout mapLayout = new GridLayout(size, size, 0, 0);
		mapPanel.setLayout(mapLayout);
		
		JPanel borderJPanel = new JPanel();
		BorderLayout borderlayout = new BorderLayout();
		borderJPanel.setLayout(borderlayout);
		
		JPanel colorbuttons = new JPanel();
		GridLayout colorbuttonLayout = new GridLayout(20, 1, 0, 0);
		colorbuttons.setLayout(colorbuttonLayout);
		
		green = new cButton(Color.green);
		green.setBackground(Color.green);
		JLabel greenlabel = new JLabel("grass");
		
		orange = new cButton(Color.orange);
		orange.setBackground(Color.orange);
		JLabel orangelabel = new JLabel("lava");

		cyan = new cButton(Color.cyan);
		cyan.setBackground(Color.cyan);
		JLabel cyanlabel = new JLabel("water");

		black = new cButton(Color.black);
		black.setBackground(Color.black);
		JLabel dirtlabel = new JLabel("dirt");

		light_gray = new cButton(Color.LIGHT_GRAY);
		light_gray.setBackground(Color.LIGHT_GRAY);
		JLabel roadlabel = new JLabel("road");
		
		JPanel g = new JPanel();
		BorderLayout gb = new BorderLayout();
		g.setLayout(gb);
		
		JPanel o = new JPanel();
		BorderLayout ob = new BorderLayout();
		o.setLayout(ob);
		
		JPanel c = new JPanel();
		BorderLayout cb = new BorderLayout();
		c.setLayout(cb);
		
		JPanel d = new JPanel();
		BorderLayout db = new BorderLayout();
		d.setLayout(db);
		
		JPanel r = new JPanel();
		BorderLayout rb = new BorderLayout();
		r.setLayout(rb);
		
		g.add(green, BorderLayout.EAST);
		g.add(greenlabel, BorderLayout.CENTER);
		
		o.add(orange, BorderLayout.EAST);
		o.add(orangelabel, BorderLayout.CENTER);
		
		c.add(cyan, BorderLayout.EAST);
		c.add(cyanlabel, BorderLayout.CENTER);
		
		d.add(black, BorderLayout.EAST);
		d.add(dirtlabel, BorderLayout.CENTER);
		
		r.add(light_gray, BorderLayout.EAST);
		r.add(roadlabel, BorderLayout.CENTER);
		
		colorbuttons.add(g);
		colorbuttons.add(o);
		colorbuttons.add(c);
		colorbuttons.add(d);
		colorbuttons.add(r);
		
		buttons = new njb[2500];
		
		for(int i=0; i<size*size; i++)
		{
			buttons[i]= new njb(i);
			mapPanel.add(buttons[i]);
		}
		buttons[start].setBackground(Color.blue);
		buttons[destination].setBackground(Color.green);
		
		borderJPanel.add(mapPanel, BorderLayout.CENTER);
		borderJPanel.add(colorbuttons, BorderLayout.EAST);
		add(borderJPanel);
		setSize(450, 450); 
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void redraw()
	{
		for(int i=0; i<size*size; i++)
		{
			buttons[i].setBackground(buttons[i].colori);
		}
		buttons[start].setBackground(Color.blue);
		buttons[destination].setBackground(Color.green);
		
		for(int i=0; i<path.size(); i++)
		{
			buttons[path.get(i)].setBackground(Color.white);
		}
	}
	
	public void makeMatrix()
	{
		Random rand = new Random();
		array = new float[size*size];
		
		for(int i=0; i<size*size; i++)
		{
			array[i]=100;
		}
		/*
		start=rand.nextInt(size*size);

		do{
			destination=rand.nextInt(size*size);
		}while(destination==start);*/
	}
	
	public class cButton extends JButton implements MouseListener {

		private static final long serialVersionUID = 1L;
		Color buttoncolor;
		
		public cButton(Color c)
		{
			super();
			addMouseListener(this);
			buttoncolor=c;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			paintThisColor=buttoncolor;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class njb extends JButton implements MouseListener
	{

		private static final long serialVersionUID = 1L;
		int number;
		Color colori=Color.LIGHT_GRAY;
		
		public njb(int number)
		{
			this.number=number;
			addMouseListener(this);
			setBackground(Color.LIGHT_GRAY);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			setBackground(paintThisColor);
			colori=paintThisColor;
			int weight = 0;
			changed=true;
			if(paintThisColor==Color.LIGHT_GRAY) //road
			{
				weight=100;
			}
			else if(paintThisColor==Color.ORANGE) //lava
			{
				weight=9000;
			}
			else if(paintThisColor==Color.CYAN) //water
			{
				weight=8000;
			}
			else if(paintThisColor==Color.GREEN) //grass
			{
				weight=500;
			}
			else if(paintThisColor==Color.BLACK) //dirt
			{
				weight=1000;
			}
			array[number]=weight;
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println(array[number]);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static void main(String args[])
	{
		Ui in = new Ui();
		in.makeMatrix();
		in.myui();
		A_star astar = new A_star(array, size, size, start, destination, 9000, 10000);
		astar.findPath();
		path=astar.getPath();
		in.redraw();
		
		while(true)
		{
			if(changed==true)
			{	
				astar.setArray(array);
				astar.findPath();
				path = astar.getPath();
				in.redraw();
				changed=false;
			}
			
			try {		
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				System.out.println("Error in mysleep()");
				e1.printStackTrace();
			}
			
		}
	}
}
