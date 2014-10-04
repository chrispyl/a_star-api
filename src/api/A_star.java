package api;
import java.util.ArrayList;
import static java.lang.Math.abs;
import static api.Normalize.zeroToOneScale;


/**
 * A class that holds the methods for finding and returning paths from one point to another in a grid type map.
 *
 */
public class A_star
{
	private int XmultY;	//total number of tiles
	private int Xsize;
	private int Ysize;
	private Tile[] tiles;
	private int start;		 
	private int destination; 
	private float maxscale;
	private float cross_cost; 
	private float diag_cost; 
	private float cantPass;	
	private float maxH;
	private boolean manhattanheu=true;
	private boolean diagheu=false;
	private boolean cutcorners=false;
	 	
	/**
	 * @param map	The map chunk that the user gives.
	 * @param Xsize	The height of the map.
	 * @param Ysize	The width of the map.
	 * @param start Start.
	 * @param destination Destination.
	 * @param cantPass	From this weight and beyond that value the access is impossible.
	 * @param maxscale	Maximum weight of the map.
	 */
	public A_star(float[] map, int Xsize, int Ysize, int start, int destination, float cantPass, float maxscale)
	{
		this.start=start;
		this.destination=destination;
		this.Xsize=Xsize;
		this.Ysize=Ysize;
		this.XmultY=Xsize*Ysize;
		this.cantPass=cantPass;
		this.maxscale=maxscale;
		cross_cost=1.0f;
		diag_cost=1.4f;
		calculateMaxH();
		createTiles(map);
	}
	
	
	public void setArray(float ar[])	//dokimastikh den tha einai mallon mesa sto api
	{
		createTiles(ar);
	}
	
	
	/**
	 * Movement cost for cross movement.
	 * @param cost
	 */
	public void changeCrossCost(float cost)
	{
		cross_cost=cost;
		calculateMaxH();
	}
	
	/**
	 * Movement cost for diagonal movement.
	 * @param cost
	 */
	public void changeDiagCost(float cost)
	{
		diag_cost=cost;
		calculateMaxH();
	}
	
	/**
	 * Sets the heuristic to be used to find the distance from start to destination.
	 * @param heuristic Choices are "manhattan" and "diagonal"
	 */
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
		calculateMaxH();
	}
	
	/**
	 * Makes the path smoother by avoiding going diagonally through corners.
	 * @param cutcorners Set true to activate, otherwise false.
	 */
	public void cutCorners(boolean cutcorners)
	{
		this.cutcorners=cutcorners;
	}
	
	/**
	 * Creates an array of tiles where we save attributes for them.
	 * @param map The map chunk the user gives.
	 */
	private void createTiles(float[] map)
	{
		tiles = new Tile[map.length];
		for(int i=0; i<map.length; i++)
		{
			tiles[i] = new Tile(map[i]);
		}
		//tiles[destination].setWeight(1);	//an to destination exei >cantPass den tha ftasei pote an den uparxei auth h grammh
	}
	
	/**
	 * Checks if node exists.
	 * @param tilenumber The serial number of the node whom we check for existence
	 * @param previoustile The serial number of the node where we stand right now.
	 * @return
	 */
	private boolean exist(int tilenumber, int previoustile)
	{
		if(tilenumber>=(XmultY) || tilenumber<0) return false;
		if(tilenumber%Ysize==0 && (previoustile+1)%Ysize==0) return false; 
		if((tilenumber+1)%Ysize==0 && previoustile%Ysize==0) return false;
		return true;
	}
	
	/**
	 * Calculates the G, F and H values for a tile. Also, it finds its parents.
	 * @param tile The serial number of the tile whose G, F and H values we want to calculate.
	 * @param currentTile The tile where we are standing right now.
	 * @param openlist	The list which holds the serial numbers of the tiles to be examined later.
	 * @param closedlist	The list which holds the serial numbers of the tiles that were examined already.
	 * @param openlistE	The list which keeps track of the existence of tiles in openlist.
	 */
	private void parentsAndGFH(int tile, int currentTile, ArrayList<Integer> openlist, ArrayList<Integer> closedlist, ArrayList<Integer> openlistE)
	{
		float step;
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
				tiles[tile].setG(tiles[currentTile].getG()+step*zeroToOneScale(tiles[tile].getWeight(), maxscale));
				tiles[tile].setF(tiles[tile].getG()+tiles[tile].getH());
			}
			else
			{
				if(tiles[currentTile].getG()+step<tiles[tile].getG()) 
				{
					tiles[tile].setPointsTo(currentTile);
					tiles[tile].setG(tiles[currentTile].getG()+step*zeroToOneScale(tiles[tile].getWeight(), maxscale));
					tiles[tile].setF(tiles[tile].getG()+tiles[tile].getH());
				}
			}
		}
	}
	
	/**
	 * Finds the path from start to destination.
	 */
	public void findPath()
	{
		ArrayList<Integer> openlist = new ArrayList<Integer>();
		ArrayList<Integer> openlistE = new ArrayList<Integer>();
		ArrayList<Integer> closedlist = new ArrayList<Integer>();
		for(int i=0; i<XmultY; i++) {closedlist.add(0); openlistE.add(0);}
		InsertionSort sortObject = new InsertionSort();
		int currentTile=start;
		tiles[start].setH(calculateH(start));
		tiles[start].setF(tiles[start].getH()); 	
		tiles[start].setG(0);
		tiles[destination].setPointsTo(-1); //gia na dw an eftase sto destination
		while(currentTile!=destination)
		{
			closedlist.set(currentTile, 1);
			
			if(currentTile>Ysize && currentTile<XmultY-Ysize && currentTile%Ysize!=0  && (currentTile+1)%Ysize!=0)
			{
				parentsAndGFH(currentTile-1, currentTile, openlist, closedlist, openlistE);
				parentsAndGFH(currentTile+1, currentTile, openlist, closedlist, openlistE);
				parentsAndGFH(currentTile-Ysize, currentTile, openlist, closedlist, openlistE);
				parentsAndGFH(currentTile+Ysize, currentTile, openlist, closedlist, openlistE);
				if(cutcorners)
				{
					if(tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile-Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile-Ysize-1, currentTile, openlist, closedlist, openlistE);}
					if(tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile+Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile+Ysize-1, currentTile, openlist, closedlist, openlistE);}
					if(tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile-Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile-Ysize+1, currentTile, openlist, closedlist, openlistE);}
					if(tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile+Ysize].getWeight()<cantPass) {parentsAndGFH(currentTile+Ysize+1, currentTile, openlist, closedlist, openlistE);}
				}
				else
				{
					parentsAndGFH(currentTile-Ysize-1, currentTile, openlist, closedlist, openlistE);
					parentsAndGFH(currentTile+Ysize-1, currentTile, openlist, closedlist, openlistE);
					parentsAndGFH(currentTile-Ysize+1, currentTile, openlist, closedlist, openlistE);
					parentsAndGFH(currentTile+Ysize+1, currentTile, openlist, closedlist, openlistE);
				}
			}
			else
			{
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
	
	/**
	 * @return Path from start to destination.
	 */
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
	
	/**
	 * @param tile  The serial number of the node 
	 * @return The calculated H value of the function F = G + H
	 */
	private float calculateH(int tile) 
	{
		float h=0;
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
		return zeroToOneScale(h, maxH);
	}
	
	/**
	 * Calculates the maximum H value, that a tile can have, depending on the current heuristic.
	 */
	private void calculateMaxH()
	{
		if(diagheu==false)
		{
			maxH=((Xsize-1)+(Ysize-1))*cross_cost;
		}
		else
		{
			int temptilex=0;
			int temptiley=0;
			int tempdestinationx=Xsize-1;
			int tempdestinationy=Ysize-1;

			while(temptilex!=tempdestinationx && temptiley!=tempdestinationy)
			{
				if(temptilex<tempdestinationx)
				{
					if(temptiley<tempdestinationy)
					{
						temptilex++;
						temptiley++;
					}
					else
					{
						temptilex++;
						temptiley--;
					}
				}
				else
				{
					if(temptiley<tempdestinationy)
					{
						temptilex--;
						temptiley++;
					}
					else
					{
						temptilex--;
						temptiley--;
					}
				}
				maxH+=diag_cost;
			}
			if(temptilex==tempdestinationx)
			{
				maxH+=abs(temptiley-tempdestinationy)*cross_cost;
			}
			else if(temptiley==tempdestinationy)
			{
				maxH+=abs(temptilex-tempdestinationx)*cross_cost;
			}
		}
	}
}

