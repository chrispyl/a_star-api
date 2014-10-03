package api;

/**
 * The inner representation of a map tile that the generic a* algorithm works with.
 *
 */
public class tile
{
	private int h, f, g, pointsTo;
	private int weight;
	
	/**Sets the weight of the tile.
	 * @param weight 
	 */
	void setWeight(int weight)
	{
		this.weight=weight;
	}
	
	/**
	 * @param weight The weight of the tile.
	 */
	tile(int weight)
	{
		this.weight=weight;
	}
	
	/**
	 * @return The weight of the tile.
	 */
	int getWeight()
	{
		return weight;
	}
	
	/**Sets this tile as a parent to another tile.
	 * @param pointsto	The serial number of the tile which we want to have this tile as a parent.
	 */
	void setPointsTo(int pointsto)
	{
		this.pointsTo=pointsto;
	}
	
	/**
	 * @return The serial number of the tile that has this tile as a parent.
	 */
	int getPointsTo()
	{
		return pointsTo;
	}
	
	/**Sets the movement cost from start to this tile.
	 * @param g G
	 */
	void setG(int g)
	{
		this.g=g;
	}
	
	/**
	 * @return Gets the movement cost from start to this tile.
	 */
	int getG()
	{
		return g;
	}
	
	/**Sets the distance cost from start to destination calculated by a heuristic.
	 * @param h H
	 */
	void setH(int h)
	{
		this.h=h;
	}
	
	/**
	 * @return The distance cost from start to destination calculated by a heuristic.
	 */
	int getH()
	{
		return h;
	}
	
	/**Sets the total cost from start to this node.
	 * @param f F
	 */
	void setF(int f)
	{
		this.f=f;
	}
	
	/**
	 * @return The total cost from start to this node.
	 */
	int getF()
	{
		return f;
	}
}
