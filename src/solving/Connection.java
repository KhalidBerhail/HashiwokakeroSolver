package solving;





public class Connection{

	/**represents the different type of edges. (0 = no edge, 1 = one edge, 2 = two edges)**/
	private int connectionType;
	
	/**represents the edge direction. (0 = no edge, 1 = horizontal, 2 = vertical)**/
	private int connectionDirection;
	
	public Connection(){
		connectionType = 0;
		connectionDirection = 0;
	}

	public int getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(int Type) {
		this.connectionType = Type;
	}

	public int getConnectionDirection() {
		return connectionDirection;
	}

	public void setConnectionDirection(int Direction) {
		this.connectionDirection = Direction;
	}
}
