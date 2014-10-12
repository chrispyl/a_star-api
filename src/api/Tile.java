package api;

/**
 * The inner representation of a map tile that the generic a* algorithm works with.
 *
 */
public class Tile
{
	private float h, f, g, weight;
	private int pointsTo, number;
	
	/**Sets the weight of the tile.
	 * @param weight 
	 */
	int getNumber()
	{
		return number;
	}
	
	void setWeight(float weight)
	{
		this.weight=weight;
	}
	
	/**
	 * @param weight The weight of the tile.
	 */
	Tile(float weight, int number)
	{
		this.weight=weight;
		this.number=number;
	}
	
	/**
	 * @return The weight of the tile.
	 */
	float getWeight()
	{
		return weight;
	}
	
	/**Sets this tile as a parent to another tile.
	 * @param pointsto	The serial number of the tile which we want to have this tile as a parent.
	 */
	void setPointsTo(int pointsTo)
	{
		this.pointsTo=pointsTo;
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
	void setG(float g)
	{
		this.g=g;
	}
	
	/**
	 * @return Gets the movement cost from start to this tile.
	 */
	float getG()
	{
		return g;
	}
	
	/**Sets the distance cost from start to destination calculated by a heuristic.
	 * @param h H
	 */
	void setH(float h)
	{
		this.h=h;
	}
	
	/**
	 * @return The distance cost from start to destination calculated by a heuristic.
	 */
	float getH()
	{
		return h;
	}
	
	/**Sets the total cost from start to this node.
	 * @param f F
	 */
	void setF(float f)
	{
		this.f=f;
	}
	
	/**
	 * @return The total cost from start to this node.
	 */
	float getF()
	{
		return f;
	}
}
