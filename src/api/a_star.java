package api;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class a_star
{
	private int XmultY;
	private int Xsize;
	private int Ysize;
	private tile[] tiles;
	private int start;		 
	private int maxscale;
	private int destination; 
	private int cross_cost; 
	private int diag_cost; 
	private int cantPass;
	private boolean manhattanheu=true;
	private boolean diagheu=false;
	private boolean cutcorners=false;
	 	
	public a_star(int[] map, int Xsize, int Ysize, int start, int destination, int cantPass, int maxscale)
	{
		this.start=start;
		this.destination=destination;
		this.Xsize=Xsize;
		this.Ysize=Ysize;
		this.XmultY=Xsize*Ysize;
		this.cantPass=cantPass;
		this.maxscale=maxscale;
		cross_cost=5*maxscale;
		diag_cost=7*maxscale;
		createtiles(map);
	}
	
	
	public void setArray(int ar[])
	{
		createtiles(ar);
	}
	
	
	public void changeCrossCost(int cost)
	{
		cross_cost=cost*maxscale;
	}
	
	public void changeDiagCost(int cost)
	{
		diag_cost=cost*maxscale;
	}
	
	public void setHeuristic(String heuristic)
	{
		if(heuristic.toLowerCase().equals("manhattan"))
		{
			manhattanheu=true;
			diagheu=false;
		}
		else if(heuristic.toLowerCase().equals("diagonal"))
		{
			manhattanheu=false;
			diagheu=true;
		}
	}
	
	public void cutCorners(boolean cutcorners)
	{
		this.cutcorners=cutcorners;
	}
	
	private void createtiles(int[] map)
	{
		tiles = new tile[map.length];
		for(int i=0; i<map.length; i++)
		{
			tiles[i] = new tile(map[i]);
		}
		//tiles[destination].setWeight(1);	//an to destination exei >cantPass den tha ftasei pote an den uparxei auth h grammh
	}
	
	private boolean exist(int tilenumber, int previoustile)
	{
		if(tilenumber>=(XmultY) || tilenumber<0) return false;
		if(tilenumber%Ysize==0 && (previoustile+1)%Ysize==0) return false; 
		if((tilenumber+1)%Ysize==0 && previoustile%Ysize==0) return false;
		return true;
	}
	
	private void parentsAndGFH(int tile, int currentTile, ArrayList<Integer> openlist, ArrayList<Integer> closedlist, ArrayList<Integer> openlistE)
	{
		int step;
		if(tiles[tile].getWeight()<cantPass && closedlist.get(tile)==0)
		{
			if((tile+1==currentTile) || (tile-1==currentTile) || (tile+Ysize==currentTile) || (tile-Ysize==currentTile))
			{
				step=cross_cost;
			}
			else
			{
				step=diag_cost;
			}
			
			if(openlistE.get(tile)==0)
			{
				openlist.add(tile);
				openlistE.set(tile, 1);
				tiles[tile].setH(calculateH(tile));
				tiles[tile].setPointsTo(currentTile);
				tiles[tile].setG(tiles[currentTile].getG()+step+tiles[tile].getWeight());
				tiles[tile].setF(tiles[tile].getG()+tiles[tile].getH());
			}
			else
			{
				if(tiles[currentTile].getG()+step<tiles[tile].getG()) 
				{
					tiles[tile].setPointsTo(currentTile);
					tiles[tile].setG(tiles[currentTile].getG()+step+tiles[tile].getWeight());
					tiles[tile].setF(tiles[tile].getG()+tiles[tile].getH());
				}
			}
		}
	}
	
	public void findPath()
	{
		ArrayList<Integer> openlist = new ArrayList<Integer>();
		ArrayList<Integer> openlistE = new ArrayList<Integer>();
		ArrayList<Integer> closedlist = new ArrayList<Integer>();
		for(int i=0; i<XmultY; i++) {closedlist.add(0); openlistE.add(0);}
		insertion_sort sortObject = new insertion_sort();
		int currentTile=start;
		tiles[start].setH(calculateH(start));
		tiles[start].setF(tiles[start].getH()); 	
		tiles[start].setG(0);
		tiles[destination].setPointsTo(-1); //gia na dw an eftase sto destination
		while(currentTile!=destination)
		{
			closedlist.set(currentTile, 1);
			
			if(exist(currentTile-1, currentTile)) {parentsAndGFH(currentTile-1, currentTile, openlist, closedlist, openlistE);}
			if(exist(currentTile+1, currentTile)) {parentsAndGFH(currentTile+1, currentTile, openlist, closedlist, openlistE);}
			if(exist(currentTile-Ysize, currentTile)) {parentsAndGFH(currentTile-Ysize, currentTile, openlist, closedlist, openlistE);}
			if(exist(currentTile+Ysize, currentTile)) {parentsAndGFH(currentTile+Ysize, currentTile, openlist, closedlist, openlistE);}
			if(cutcorners)
			{
				if(exist(currentTile-1, currentTile) && exist(currentTile-Ysize, currentTile) && tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile-Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile-Ysize-1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile-1, currentTile) && exist(currentTile+Ysize, currentTile) && tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile+Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile+Ysize-1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile+1, currentTile) && exist(currentTile-Ysize, currentTile) && tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile-Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile-Ysize+1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile+1, currentTile) && exist(currentTile+Ysize, currentTile) && tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile+Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile+Ysize+1, currentTile, openlist, closedlist, openlistE);}
			}
			else
			{
				if(exist(currentTile-Ysize-1, currentTile)) {parentsAndGFH(currentTile-Ysize-1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile+Ysize-1, currentTile)) {parentsAndGFH(currentTile+Ysize-1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile-Ysize+1, currentTile)) {parentsAndGFH(currentTile-Ysize+1, currentTile, openlist, closedlist, openlistE);}
				if(exist(currentTile+Ysize+1, currentTile)) {parentsAndGFH(currentTile+Ysize+1, currentTile, openlist, closedlist, openlistE);}
			}
			
			if(openlist.isEmpty())
			{
				break;
			}
			
			if(currentTile!=start) 
			{
				sortObject.sort(openlist, tiles, false);
			} 
			else
			{
				sortObject.sort(openlist, tiles, true);	//kanei sort me bash ta f
			}
			currentTile=openlist.get(openlist.size()-1);
			openlist.remove(openlist.size()-1);	
			openlistE.set(currentTile, 0);
		}
	}
	
	public ArrayList<Integer> getPath()
	{
		ArrayList<Integer> path = new ArrayList<Integer>();

		if(tiles[destination].getPointsTo()!=-1)
		{
			int i=destination;
			while(i!=start)
			{
				i=tiles[i].getPointsTo();
				if(i!=start) path.add(i);
			}
		}
		return path;
	}
	
	private int calculateH(int tile) 
	{
		int h=0;
		int tilex=tile/Ysize, tiley=tile%Ysize, destinationx=destination/Ysize, destinationy=destination%Ysize;
		int distx=abs(tilex-destinationx);       
		int disty=abs(tiley-destinationy);
		if(diagheu)	
		{
			if(distx>disty)
			{
				h = diag_cost*disty + cross_cost*(distx-disty);
			}
			else
			{
				h = diag_cost*distx + cross_cost*(disty-distx);
			}
		}
		else  
		{
			h=cross_cost*(distx+disty);	
		} 
		return h;
	}					   
}