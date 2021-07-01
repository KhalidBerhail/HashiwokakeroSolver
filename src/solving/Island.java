package solving;


public class Island {

	

	/** represents the degree of the Island, as in how many edges should be connected to it**/
	private int poidsMax;
	
	/** represents how many more edges should be connected to this node to match the MAXdegree**/
	private int poids;
	
	/***** if the node is locked or note
	 * 
	 */
	private boolean isLocked=false;
	/**Island position in the board**/
	private int x,y;
	/**Island position on the puzzle**/
	private double DrawX,DrawY;
	
	
	
	/** the Islands that this Island can be connected to**/
	private Island northIsland, eastIsland, southIsland, westIsland;
	
	/** the status for an edges between two nodes. 0 = no edge, 1 = single, 2 = double**/
	private int northConnection, eastConnection, southConnection, westConnection;
	
	/********************************************************************
	 * Constructor for the Island Class. Sets the degree of the Island
	 * @param d Degree of the Island
	 ********************************************************************/
	public Island(int d){
		poidsMax = d;
		poids = d;
		
		//the Island starts out not connected to any other Islands
		northConnection = 0;
		eastConnection = 0;
		southConnection = 0;
		westConnection = 0;
	}
	public Island(int d,int x,int y){
		poidsMax = d;
		poids = d;
		this.x=x;
		this.y=y;
		//the Island starts out not connected to any other Islands
		northConnection = 0;
		eastConnection = 0;
		southConnection = 0;
		westConnection = 0;
	}
	/**********************************************************************
	 * Decrements the degree by 1
	 **********************************************************************/
	public void dec(){
		poids--;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**********************************************************************
	 * Increments the degree by 1
	 **********************************************************************/
	public void inc(){
		poids++;
	}
	
	public int getDegree(){
		return poids;
	}
	public void setDegree(int deg){
		 poids=deg;
	}
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public int getMAXDegree(){
		return poidsMax;
	}
	
	public void setNorthIsland(Island n){
		northIsland = n;
	}
	
	public Island getNorthIsland(){
		return northIsland;
	}
	
	public void setEastIsland(Island n){
		eastIsland = n;
	}
	
	public Island getEastIsland(){
		return eastIsland;
	}
	
	public void setSouthIsland(Island n){
		southIsland = n;
	}
	
	public Island getSouthIsland(){
		return southIsland;
	}
	
	public void setWestIsland(Island n){
		westIsland = n;
	}
	
	public Island getWestIsland(){
		return westIsland;
	}

	public int getNorthConnection() {
		return northConnection;
	}

	public void setNorthConnection(int northConnection) {
		this.northConnection = northConnection;
	}

	public int getEastConnection() {
		return eastConnection;
	}

	public void setEastConnection(int eastConnection) {
		this.eastConnection = eastConnection;
	}

	public int getSouthConnection() {
		return southConnection;
	}

	public void setSouthConnection(int southConnection) {
		this.southConnection = southConnection;
	}

	public int getWestConnection() {
		return westConnection;
	}

	public void setWestConnection(int westConnection) {
		this.westConnection = westConnection;
	}
	
	public double getDrawX() {
		
		return DrawX;
	}
	
    public double getDrawY() {
		
    	return DrawY;
	}
	public void setDrawParams(double x, double y) {
		DrawX = x;
		DrawY = y;
	}

}