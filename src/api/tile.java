package api;

public class tile
{
	private int h, f, g, pointsTo;
	private int weight;
	
	void setWeight(int weight)
	{
		this.weight=weight;
	}
	
	tile(int weight)
	{
		this.weight=weight;
	}
	
	int getWeight()
	{
		return weight;
	}
	
	void setPointsTo(int pointsto)
	{
		this.pointsTo=pointsto;
	}
	
	int getPointsTo()
	{
		return pointsTo;
	}
	
	void setG(int g)
	{
		this.g=g;
	}
	
	int getG()
	{
		return g;
	}
	
	void setH(int h)
	{
		this.h=h;
	}
	
	int getH()
	{
		return h;
	}
	
	void setF(int f)
	{
		this.f=f;
	}
	
	int getF()
	{
		return f;
	}
}
