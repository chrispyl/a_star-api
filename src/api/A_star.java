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
	private int xSize;
	private int ySize;
	private Tile[] tiles;
	private int start;		 
	private int destination; 
	private float maxScale;
	private float crossCost; 
	private float diagCost; 
	private float cantPass;	
	private float maxH;
	private boolean manhattanheu=true;
	private boolean diagheu=false;
	private boolean cutCorners=false;
	 	
	/**
	 * @param map	The map chunk that the user gives.
	 * @param Xsize	The height of the map.
	 * @param Ysize	The width of the map.
	 * @param start Start.
	 * @param destination Destination.
	 * @param cantPass	From this weight and beyond that value the access is impossible.
	 * @param maxScale	Maximum weight of the map.
	 */
	public A_star(float[] map, int xSize, int ySize, int start, int destination, float cantPass, float maxScale)
	{
		this.start=start;
		this.destination=destination;
		this.xSize=xSize;
		this.ySize=ySize;
		this.XmultY=xSize*ySize;
		this.cantPass=cantPass;
		this.maxScale=maxScale;
		crossCost=1.0f;
		diagCost=1.4f;
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
		crossCost=cost;
		calculateMaxH();
	}
	
	/**
	 * Movement cost for diagonal movement.
	 * @param cost
	 */
	public void changeDiagCost(float cost)
	{
		diagCost=cost;
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
	public void cutCorners(boolean cutCorners)
	{
		this.cutCorners=cutCorners;
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
			tiles[i] = new Tile(map[i], i);
		}
		//tiles[destination].setWeight(1);	//an to destination exei >cantPass den tha ftasei pote an den uparxei auth h grammh
	}
	
	/**
	 * Checks if tile exists.
	 * @param tileNumber The serial number of the tile which we check for existence
	 * @param previousTile The serial number of the tile where we stand right now.
	 * @return
	 */
	private boolean exist(int tileNumber, int previousTile)
	{
		if(tileNumber>=(XmultY) || tileNumber<0) return false;
		if(tileNumber%ySize==0 && (previousTile+1)%ySize==0) return false; 
		if((tileNumber+1)%ySize==0 && previousTile%ySize==0) return false;
		return true;
	}
	
	/**
	 * Calculates the G, F and H values for a tile. Also, it finds its parents.
	 * @param tile The serial number of the tile whose G, F and H values we want to calculate.
	 * @param currentTile The tile where we are standing right now.
	 * @param openlist	The list which holds the serial numbers of the tiles to be examined later.
	 * @param closedlist	The list which holds the serial numbers of the tiles that were examined already.
	 * @param openlistExist	The list which keeps track of the existence of tiles in openlist.
	 */
	private void parentsAndGFH(int tile, int currentTile, BinaryHeap openlist, ArrayList<Integer> closedlist)
	{
		float step;
		if(tiles[tile].getWeight()<cantPass && closedlist.get(tile)==0)
		{
			if((tile+1==currentTile) || (tile-1==currentTile) || (tile+ySize==currentTile) || (tile-ySize==currentTile))
			{
				step=crossCost;
			}
			else
			{
				step=diagCost;
			}
			
			if(openlist.contains(tile)==false)
			{
				tiles[tile].setH(calculateH(tile));
				tiles[tile].setPointsTo(currentTile);
				tiles[tile].setG(tiles[currentTile].getG()+step*zeroToOneScale(tiles[tile].getWeight(), maxScale));
				tiles[tile].setF(tiles[tile].getG()+tiles[tile].getH());
				openlist.insert(tiles[tile]);
			}
			else
			{
				if(tiles[currentTile].getG()+step<tiles[tile].getG()) 
				{
					tiles[tile].setPointsTo(currentTile);
					tiles[tile].setG(tiles[currentTile].getG()+step*zeroToOneScale(tiles[tile].getWeight(), maxScale));
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
		BinaryHeap openlist = new BinaryHeap(XmultY);
		ArrayList<Integer> closedlist = new ArrayList<Integer>();
		for(int i=0; i<XmultY; i++) {closedlist.add(0);}
		int currentTile=start;
		tiles[start].setH(calculateH(start));
		tiles[start].setF(tiles[start].getH()); 	
		tiles[start].setG(0);
		tiles[destination].setPointsTo(-1); //gia na dw an eftase sto destination
		while(currentTile!=destination)
		{
			closedlist.set(currentTile, 1);
			
			if(currentTile>ySize && currentTile<XmultY-ySize && currentTile%ySize!=0  && (currentTile+1)%ySize!=0)
			{
				parentsAndGFH(currentTile-1, currentTile, openlist, closedlist);
				parentsAndGFH(currentTile+1, currentTile, openlist, closedlist);
				parentsAndGFH(currentTile-ySize, currentTile, openlist, closedlist);
				parentsAndGFH(currentTile+ySize, currentTile, openlist, closedlist);
				if(cutCorners)
				{
					if(tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile-ySize].getWeight()<cantPass) {parentsAndGFH(currentTile-ySize-1, currentTile, openlist, closedlist);}
					if(tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile+ySize].getWeight()<cantPass) {parentsAndGFH(currentTile+ySize-1, currentTile, openlist, closedlist);}
					if(tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile-ySize].getWeight()<cantPass) {parentsAndGFH(currentTile-ySize+1, currentTile, openlist, closedlist);}
					if(tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile+ySize].getWeight()<cantPass) {parentsAndGFH(currentTile+ySize+1, currentTile, openlist, closedlist);}
				}
				else
				{
					parentsAndGFH(currentTile-ySize-1, currentTile, openlist, closedlist);
					parentsAndGFH(currentTile+ySize-1, currentTile, openlist, closedlist);
					parentsAndGFH(currentTile-ySize+1, currentTile, openlist, closedlist);
					parentsAndGFH(currentTile+ySize+1, currentTile, openlist, closedlist);
				}
			}
			else
			{
				if(exist(currentTile-1, currentTile)) {parentsAndGFH(currentTile-1, currentTile, openlist, closedlist);}
				if(exist(currentTile+1, currentTile)) {parentsAndGFH(currentTile+1, currentTile, openlist, closedlist);}
				if(exist(currentTile-ySize, currentTile)) {parentsAndGFH(currentTile-ySize, currentTile, openlist, closedlist);}
				if(exist(currentTile+ySize, currentTile)) {parentsAndGFH(currentTile+ySize, currentTile, openlist, closedlist);}
				if(cutCorners)
				{
					if(exist(currentTile-1, currentTile) && exist(currentTile-ySize, currentTile) && tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile-ySize].getWeight()<cantPass) {parentsAndGFH(currentTile-ySize-1, currentTile, openlist, closedlist);}
					if(exist(currentTile-1, currentTile) && exist(currentTile+ySize, currentTile) && tiles[currentTile-1].getWeight()<cantPass && tiles[currentTile+ySize].getWeight()<cantPass) {parentsAndGFH(currentTile+ySize-1, currentTile, openlist, closedlist);}
					if(exist(currentTile+1, currentTile) && exist(currentTile-ySize, currentTile) && tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile-ySize].getWeight()<cantPass) {parentsAndGFH(currentTile-ySize+1, currentTile, openlist, closedlist);}
					if(exist(currentTile+1, currentTile) && exist(currentTile+ySize, currentTile) && tiles[currentTile+1].getWeight()<cantPass && tiles[currentTile+ySize].getWeight()<cantPass) {parentsAndGFH(currentTile+ySize+1, currentTile, openlist, closedlist);}
				}
				else
				{
					if(exist(currentTile-ySize-1, currentTile)) {parentsAndGFH(currentTile-ySize-1, currentTile, openlist, closedlist);}
					if(exist(currentTile+ySize-1, currentTile)) {parentsAndGFH(currentTile+ySize-1, currentTile, openlist, closedlist);}
					if(exist(currentTile-ySize+1, currentTile)) {parentsAndGFH(currentTile-ySize+1, currentTile, openlist, closedlist);}
					if(exist(currentTile+ySize+1, currentTile)) {parentsAndGFH(currentTile+ySize+1, currentTile, openlist, closedlist);}
				}
			}
			
			if(openlist.heapEmpty())
			{
				break;
			}
			
			currentTile=openlist.getMin().getNumber();
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
	 * @param tile  The serial number of the tile 
	 * @return The calculated H value of the function F = G + H
	 */
	private float calculateH(int tile) 
	{
		float h=0;
		int tilex=tile/ySize, tiley=tile%ySize, destinationx=destination/ySize, destinationy=destination%ySize;
		int distx=abs(tilex-destinationx);       
		int disty=abs(tiley-destinationy);
		if(diagheu)	
		{
			if(distx>disty)
			{
				h = diagCost*disty + crossCost*(distx-disty);
			}
			else
			{
				h = diagCost*distx + crossCost*(disty-distx);
			}
		}
		else  
		{
			h=crossCost*(distx+disty);	
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
			maxH=((xSize-1)+(ySize-1))*crossCost;
		}
		else
		{
			int tempTilex=0;
			int tempTiley=0;
			int tempDestinationx=xSize-1;
			int tempDestinationy=ySize-1;

			while(tempTilex!=tempDestinationx && tempTiley!=tempDestinationy)
			{
				if(tempTilex<tempDestinationx)
				{
					if(tempTiley<tempDestinationy)
					{
						tempTilex++;
						tempTiley++;
					}
					else
					{
						tempTilex++;
						tempTiley--;
					}
				}
				else
				{
					if(tempTiley<tempDestinationy)
					{
						tempTilex--;
						tempTiley++;
					}
					else
					{
						tempTilex--;
						tempTiley--;
					}
				}
				maxH+=diagCost;
			}
			if(tempTilex==tempDestinationx)
			{
				maxH+=abs(tempTiley-tempDestinationy)*crossCost;
			}
			else if(tempTiley==tempDestinationy)
			{
				maxH+=abs(tempTilex-tempDestinationx)*crossCost;
			}
		}
	}
}

