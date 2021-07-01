package solving;



enum Direction{
	East(1),West(2),North(3),South(4);
	private int value;
	Direction(int i) {
		// TODO Auto-generated constructor stub
		value=i;
	}
	private static Direction getDirection(int i) {
		switch(i) {
			case 1:
				return East;
			case 2:
				return West;
			case 3:
				return North;
			case 4:
				return South;
				
			default: return South; 
				
		}
		
	}
	public static Direction getRandomDirection() {
		int dir=(int) (1+Math.random()*3);
		return getDirection(dir);
		
		
	}
}