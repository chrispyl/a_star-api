package api;

import java.util.ArrayList;

/**
 * An optimized version of insertion sort for the generic a* algorithm.
 *
 */
public class insertion_sort {
	
	/**
	 * @param openlist	The list which holds the serial number of the nodes to be examined later.
	 * @param tiles An array with the tiles of the map.
	 * @param IsStartTile	Checks if the current tile is the one that we start from.
	 */
	void sort(ArrayList<Integer> openlist, tile[] tiles, boolean IsStartTile)
	{
		int number;
		if(IsStartTile==true) 
		{
			number=8;
		}
		else
		{
			number=5;
		}
		
		for(int size=openlist.size(), i=size-1-number; i<size; i++) 
		{
			for(int j=i; j>0; --j)
			{
				if(tiles[openlist.get(j-1)].getF() < tiles[openlist.get(j)].getF())
				{
					int temp = openlist.get(j);
					openlist.set(j, openlist.get(j-1));
					openlist.set(j-1, temp);
				}
			}
		}
	}
}