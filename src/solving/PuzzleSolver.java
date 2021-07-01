package solving;


public class PuzzleSolver {

	private puzzleModel puzzle;
	
	private Island[] Islands;

	private int numOfIslands;

	private Island testIslands;

	private boolean solutionFound;
	
	
	
	public PuzzleSolver(puzzleModel p) {
		
		puzzle = p;
		testIslands = new Island(0);
		createArray();
		numOfIslands = Islands.length;
		solutionFound = false;

		int n = 1;
		while(n <= (numOfIslands - 1) && !solutionFound){
			if(!findSolution(Islands[0], Islands[n])){
				
				n++;
			}else{
				
				solutionFound = true;
			}
		}
		
	}
	
	public void createArray() {
		Object[][] board = puzzle.getPuzzleElements();
		int count = 0;
		for(int x = 0; x < puzzle.getLines(); x++) {
			for(int y = 0; y < puzzle.getColumns(); y++) {
				
				if(board[x][y].getClass().equals(testIslands.getClass())) {
					count++;
				}
			}
		}
		Islands = new Island[count];
		int num = 0;
		for(int x = 0; x <  puzzle.getLines(); x++) {
			for(int y = 0; y <  puzzle.getColumns(); y++) {
				
				if(board[x][y].getClass().equals(testIslands.getClass())) {
					
					Islands[num] = (Island)board[x][y];
					num++;
				}
			}
		}
	}
	
	public boolean findSolution(Island n1, Island n2){

		if(n1 == n2) {
			
			return false;
		}
		if(puzzle.canConnecte(n1, n2)){
			
			puzzle.addConnection(n1, n2);
		}else {

			return false;
		}
		if(puzzle.solutionWasFound()){

			return true;
		}else if(!puzzle.hasPotentialMoves()) {

			puzzle.removeConnection(n1, n2);
			return false;
		}else {
			
			int pIsland1 = 0;
			int pIsland2 = 0;
			for(int i = 0; i < numOfIslands -1; i++) {
				
				if(n1 == Islands[i])
					pIsland1 = i;
				if(n2 == Islands[i])
					pIsland2 = i;
			}

			do {

				if(Islands[pIsland1].getDegree() == 0 || pIsland2 == numOfIslands - 1) {
					
					do{
						
						pIsland1++;
						pIsland2 = pIsland1;
					} while(Islands[pIsland1].getDegree() == 0 && pIsland1 < numOfIslands-1);
				}else {
					pIsland2++;
				}

				if(Islands[pIsland1].getNorthIsland() == Islands[pIsland2] || Islands[pIsland1].getEastIsland() == Islands[pIsland2] || 
						Islands[pIsland1].getSouthIsland() == Islands[pIsland2] || Islands[pIsland1].getWestIsland() == Islands[pIsland2]) {
					
					if(findSolution(Islands[pIsland1], Islands[pIsland2])) {
						
						return true;
					}
				}
			}while(!(pIsland2 >= (numOfIslands - 2) && pIsland1 >= (numOfIslands -1)));

			if(!findSolution(n1, n2)) {
				
				puzzle.removeConnection(n1, n2);
				return false;
			}
		}
		return true;
	}
	
	public boolean IsSolved() {
		
		return solutionFound;
	}
}
