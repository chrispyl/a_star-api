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

public class Ui extends JFrame{		
	
	private static final long serialVersionUID = 1L;

	static float[] array;
	static njb[] buttons;
	public static int size=10;
	static int start=size*size/2+size/2;
	static int destination=size/2;
	static ArrayList<Integer> path;
	public static Color paintThisColor=Color.LIGHT_GRAY;
	public static boolean changed = false;
	cButton grass;
	cButton lava;
	cButton water;
	cButton road;
	cButton brown;
	Color dirtColor = new Color(176/255f, 90/255f, 37/255f);
	Color waterColor = new Color(140/255f, 215/255f, 255/255f);
	Color lavaColor = new Color(255/255f, 109/255f, 18/255f);
	Color grassColor = new Color(52/255f, 199/255f, 91/255f);
	int lavaWeight=90;
	int roadWeight=10;
	int dirtWeight=30;
	int grassWeight=20;
	int waterWeight=80;
	
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
		
		grass = new cButton(grassColor);
		grass.setBackground(grassColor);
		JLabel greenlabel = new JLabel("grass");
		
		lava = new cButton(lavaColor);
		lava.setBackground(lavaColor);
		JLabel orangelabel = new JLabel("lava");

		water = new cButton(waterColor);
		water.setBackground(waterColor);
		JLabel cyanlabel = new JLabel("water");

		brown = new cButton(dirtColor);
		brown.setBackground(dirtColor);
		JLabel dirtlabel = new JLabel("dirt");

		road = new cButton(Color.LIGHT_GRAY);
		road.setBackground(Color.LIGHT_GRAY);
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
		
		g.add(grass, BorderLayout.EAST);
		g.add(greenlabel, BorderLayout.CENTER);
		
		o.add(lava, BorderLayout.EAST);
		o.add(orangelabel, BorderLayout.CENTER);
		
		c.add(water, BorderLayout.EAST);
		c.add(cyanlabel, BorderLayout.CENTER);
		
		d.add(brown, BorderLayout.EAST);
		d.add(dirtlabel, BorderLayout.CENTER);
		
		r.add(road, BorderLayout.EAST);
		r.add(roadlabel, BorderLayout.CENTER);
		
		colorbuttons.add(g);
		colorbuttons.add(o);
		colorbuttons.add(c);
		colorbuttons.add(d);
		colorbuttons.add(r);
		
		buttons = new njb[100];
		
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
			array[i]=roadWeight;
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
				weight=roadWeight;
			}
			else if(paintThisColor==lavaColor) //lava
			{
				weight=lavaWeight;
			}
			else if(paintThisColor==waterColor) //water
			{
				weight=waterWeight;
			}
			else if(paintThisColor==grassColor) //grass
			{
				weight=grassWeight;
			}
			else if(paintThisColor==dirtColor) //dirt
			{
				weight=dirtWeight;
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
		A_star astar = new A_star(array, size, size, start, destination, 90, 100);
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
