package solving;

import java.util.ArrayList;
import java.util.List;




public class puzzleModel {

	
	private Object[][] puzzleElements;
	
	
	private Island testIsland;
	
	
	private Connection testConnection;
	
	private Island randomNgb; 
	private List<Island> allIslands=new ArrayList<Island>();
	
	private int Lines,Columns;
	
	
	
	public puzzleModel(int Lines, int Columns){
		this.Columns = Columns;
		this.Lines = Lines;
		testIsland = new Island(0);
		puzzleElements = new Object[Lines][Columns];	
		for(int row = 0; row < Lines; row++){
			for(int col = 0; col < Columns; col++){
				puzzleElements[row][col] = new Connection();
			}
		}
	}
	

	
	public void findPossibleConnections(){
		for(int x = 0; x < Lines; x++){
			for(int y = 0; y < Columns; y++){
				//checks if object is a Island
				if(puzzleElements[x][y].getClass().equals(testIsland.getClass())){
					//checks above this Island for other Islands
					for(int y2 = y - 1; y2 >= 0; y2--){
						if(puzzleElements[x][y2].getClass().equals(testIsland.getClass())){
							((Island) puzzleElements[x][y]).setNorthIsland((Island)puzzleElements[x][y2]);
							y2 = -1;
						}
					}
					//checks west of this Island for other Islands
					for(int x2 = x - 1; x2 >= 0; x2--){
						if(puzzleElements[x2][y].getClass().equals(testIsland.getClass())){
							((Island) puzzleElements[x][y]).setWestIsland((Island)puzzleElements[x2][y]);
							x2 = -1;
						}
					}
					//checks east of this Island for other Islands
					for(int x2 = x + 1; x2 < Lines; x2++){
						if(puzzleElements[x2][y].getClass().equals(testIsland.getClass())){
							((Island) puzzleElements[x][y]).setEastIsland((Island)puzzleElements[x2][y]);
							x2 = Lines;
						}
					}
					//checks below this Island for other Islands
					for(int y2 = y + 1; y2 < Columns; y2++){
						if(puzzleElements[x][y2].getClass().equals(testIsland.getClass())){
							((Island) puzzleElements[x][y]).setSouthIsland((Island)puzzleElements[x][y2]);
							y2 = Columns;
						}
					}
				}
			}
		}
	}
	
	
	public void addConnection(Island n1, Island n2){
		//decrements the Islands' needed degree count
		n1.dec();
		n2.dec();
		//finds the position of n1 on the board
		int x = 0;
		int y = 0;
		while(puzzleElements[x][y] != n1){
			y = 0;
			while(puzzleElements[x][y] != n1 && y < Columns - 1){
				y++;
			}
			if(puzzleElements[x][y] != n1)
				x++;
		}
		//updates the edge's value (0, 1, or 2)
		if(n1.getNorthIsland() == n2){
			n1.setNorthConnection(n1.getNorthConnection() + 1);
			n2.setSouthConnection(n2.getSouthConnection() + 1);
			for(int y2 = y-1; y2 >= 0; y2--){
				if(puzzleElements[x][y2] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x][y2]).setConnectionType(((Connection)puzzleElements[x][y2]).getConnectionType() + 1);
					((Connection)puzzleElements[x][y2]).setConnectionDirection(2);
				}
			}
		}
		else if(n1.getEastIsland() == n2){
			n1.setEastConnection(n1.getEastConnection() + 1);
			n2.setWestConnection(n2.getWestConnection() + 1);
			for(int x2 = x+1; x2 < Lines; x2++){
				if(puzzleElements[x2][y] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x2][y]).setConnectionType(((Connection)puzzleElements[x2][y]).getConnectionType() + 1);
					((Connection)puzzleElements[x2][y]).setConnectionDirection(1);
				}
			}
		}
		else if(n1.getSouthIsland() == n2){
			n1.setSouthConnection(n1.getSouthConnection() + 1);
			n2.setNorthConnection(n2.getNorthConnection() + 1);
			for(int y2 = y+1; y2 < Columns; y2++){
				if(puzzleElements[x][y2] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x][y2]).setConnectionType(((Connection)puzzleElements[x][y2]).getConnectionType() + 1);
					((Connection)puzzleElements[x][y2]).setConnectionDirection(2);
				}
			}
		}
		else if(n1.getWestIsland() == n2){
			n1.setWestConnection(n1.getWestConnection() + 1);
			n2.setEastConnection(n2.getEastConnection() + 1);
			for(int x2 = x-1; x2 >= 0; x2--){
				if(puzzleElements[x2][y] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x2][y]).setConnectionType(((Connection)puzzleElements[x2][y]).getConnectionType() + 1);
					((Connection)puzzleElements[x2][y]).setConnectionDirection(1);
				}
			}
		}
		else
			System.out.println("Error: HashiGame.addConnection() method: n2 is not a valid parameter for n1");
	}
	
	
	public void removeConnection(Island n1, Island n2){
		//increments the Islands' needed degree counts
		n1.inc();
		n2.inc();
		//finds the position of n1 on the board
		int x = 0;
		int y = 0;
		while(puzzleElements[x][y] != n1){
			y = 0;
			while(puzzleElements[x][y] != n1 && y < Columns - 1){
				y++;
			}
			if(puzzleElements[x][y] != n1)
				x++;
		}
		//updates the edge's value (0, 1, or 2)
		if(n1.getNorthIsland() == n2){
			n1.setNorthConnection(n1.getNorthConnection() - 1);
			n2.setSouthConnection(n2.getSouthConnection() - 1);
			for(int y2 = y-1; y2 >= 0; y2--){
				if(puzzleElements[x][y2] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x][y2]).setConnectionType(((Connection)puzzleElements[x][y2]).getConnectionType() - 1);
					if(((Connection)puzzleElements[x][y2]).getConnectionType() == 0)
						((Connection)puzzleElements[x][y2]).setConnectionDirection(0);
				}
			}
		}
		else if(n1.getEastIsland() == n2){
			n1.setEastConnection(n1.getEastConnection() - 1);
			n2.setWestConnection(n2.getWestConnection() - 1);
			for(int x2 = x+1; x2 < Lines; x2++){
				if(puzzleElements[x2][y] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x2][y]).setConnectionType(((Connection)puzzleElements[x2][y]).getConnectionType() - 1);
					if(((Connection)puzzleElements[x2][y]).getConnectionType() == 0)
						((Connection)puzzleElements[x2][y]).setConnectionDirection(0);
				}
			}
		}
		else if(n1.getSouthIsland() == n2){
			n1.setSouthConnection(n1.getSouthConnection() - 1);
			n2.setNorthConnection(n2.getNorthConnection() - 1);
			for(int y2 = y+1; y2 < Columns; y2++){
				if(puzzleElements[x][y2] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x][y2]).setConnectionType(((Connection)puzzleElements[x][y2]).getConnectionType() - 1);
					if(((Connection)puzzleElements[x][y2]).getConnectionType() == 0)
						((Connection)puzzleElements[x][y2]).setConnectionDirection(0);
				}
			}
		}
		else if(n1.getWestIsland() == n2){
			n1.setWestConnection(n1.getWestConnection() - 1);
			n2.setEastConnection(n2.getEastConnection() - 1);
			for(int x2 = x-1; x2 >= 0; x2--){
				if(puzzleElements[x2][y] == n2){
					break;
				}
				else{
					((Connection)puzzleElements[x2][y]).setConnectionType(((Connection)puzzleElements[x2][y]).getConnectionType() - 1);
					if(((Connection)puzzleElements[x2][y]).getConnectionType() == 0)
						((Connection)puzzleElements[x2][y]).setConnectionDirection(0);
				}
			}
		}
		else
			System.out.println("Error: HashiGame.addConnection() method: n2 is not a valid parameter for n1");
	}
	
	
	public boolean solutionWasFound(){
		for(int x = 0; x < Lines; x++){
			for(int y = 0; y < Columns; y++){
				if(puzzleElements[x][y].getClass().equals(testIsland.getClass())){
					if(((Island) puzzleElements[x][y]).getDegree() > 0){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	public boolean hasPotentialMoves(){
		for(int x = 0; x < Lines; x++){
			for(int y = 0; y < Columns; y++){
				if(puzzleElements[x][y].getClass().equals(testIsland.getClass())){
					if(((Island)puzzleElements[x][y]).getDegree() > 0){
						//if there is a valid move with this node, return true
						if(canConnecte((Island) puzzleElements[x][y], ((Island) puzzleElements[x][y]).getNorthIsland())){
							return true;
						}
						if(canConnecte((Island) puzzleElements[x][y], ((Island) puzzleElements[x][y]).getEastIsland())){
							return true;
						}
						if(canConnecte((Island) puzzleElements[x][y], ((Island) puzzleElements[x][y]).getSouthIsland())){
							return true;
						}
						if(canConnecte((Island) puzzleElements[x][y], ((Island) puzzleElements[x][y]).getWestIsland())){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	public boolean canConnecte(Island n1, Island n2){
		//if either node has already reached its MAX degree, or is null, return false
		if(n1 == null || n2 == null){
			return false;
		}
		if(n1.getDegree() == 0 || n2.getDegree() == 0){
			return false;
		}
		//returns false if the nodes are not related
		if(n1.getNorthIsland() != n2 && n1.getEastIsland() != n2 && n1.getSouthIsland() != n2 && n1.getWestIsland() != n2){
			return false;
		}
		//finds the position of n1 on the board
		int x = 0;
		int y = 0;
		while(puzzleElements[x][y] != n1){
			y = 0;
			while(puzzleElements[x][y] != n1 && y < Columns - 1){
				y++;
			}
			if(puzzleElements[x][y] != n1)
				x++;
		}
		//returns false if the edge to be added is already at a value of 2
		if(n1.getNorthIsland() == n2){
			if(n1.getNorthConnection() == 2)
				return false;
			else{
				//returns false if there is already an edge crossing between these two nodes
				for(int y2 = y-1; y2 >= 0; y2--){
					if(puzzleElements[x][y2] == n2){
						break;
					}
					else{
						if(((Connection)puzzleElements[x][y2]).getConnectionDirection() == 1)
							return false;
					}
				}
			}
		}
		if(n1.getEastIsland() == n2){
			if(n1.getEastConnection() == 2)
				return false;
			else{
				//returns false if there is already an edge crossing between these two nodes
				for(int x2 = x+1; x2 < Lines; x2++){
					if(puzzleElements[x2][y] == n2){
						break;
					}
					else{
						if(((Connection)puzzleElements[x2][y]).getConnectionDirection() == 2)
							return false;
					}
				}
			}
		}
		if(n1.getSouthIsland() == n2){
			if(n1.getSouthConnection() == 2)
				return false;
			else{
				//returns false if there is already an edge crossing between these two nodes
				for(int y2 = y+1; y2 < Columns; y2++){
					if(puzzleElements[x][y2] == n2){
						break;
					}
					else{
						if(((Connection)puzzleElements[x][y2]).getConnectionDirection() == 1)
							return false;
					}
				}
			}
		}
		if(n1.getWestIsland() == n2){
			if(n1.getWestConnection() == 2)
				return false;
			else{
				//returns false if there is already an edge crossing between these two nodes
				for(int x2 = x-1; x2 >= 0; x2--){
					if(puzzleElements[x2][y] == n2){
						break;
					}
					else{
						if(((Connection)puzzleElements[x2][y]).getConnectionDirection() == 2)
							return false;
					}
				}
			}
		}
		
		return true;
	}
	
    public void SetIslands(int nb) {
		while (!generate(nb)) {
			allIslands.clear();
		}
	}
	
    public void setPuzzleElements() {
		for (int i = 0; i < puzzleElements.length; i++)
			for (int j = 0; j < puzzleElements[i].length; j++) {
				if(((Island) puzzleElements[i][j]).getDegree()==0) {
				puzzleElements[i][j] = new Connection();
				}
				else {
					puzzleElements[i][j] = new Island(((Island) puzzleElements[i][j]).getDegree(),i,j);
				}
			}
	}
	
	public boolean findRandomNgbTo(Island island) {
		int x = island.getX(), y = island.getY();
	//Random rand = new Random(); 
		// timer
		int timer = 12;
		do {
			//Il y a 12 tentatives pour trouver un voisin aléatoire
			timer--;

			// Direction aléatoire: 0 - gauche, 1 - haut, 2 - droite, 3 - bas
			switch (Direction.getRandomDirection()) {
			case West:
				y = island.getY();
				x = (int) (Math.random()*island.getX());
				break;
			case North:
				x = island.getX();
				y =(int) (Math.random()*island.getY());
				break;
			case East:
				y = island.getY();
				x = 1 + island.getX() + (int) ((Math.random() * ((Lines-2) - island.getX())));
				break;
			case South:
				x = island.getX();
				y = 1 + island.getY() + (int) ((Math.random() * ((Columns-2) - island.getY())));
				break;
			}

			// Vérifie si l'îlot trouvé est à une distance de 1 des voisins.
			if (x > 0 && x < Lines-1)
				if (y < Columns-1 && y > 0)
					if (((Island) puzzleElements[x + 1][y]).getDegree()== 0  && ((Island) puzzleElements[x][y-1]).getDegree()== 0 )
						if (((Island) puzzleElements[x - 1][y]).getDegree()== 0  && ((Island) puzzleElements[x][y+1]).getDegree() == 0 )
							if (((Island) puzzleElements[x][y]).getDegree()== 0  &&  connectable(island,(Island)puzzleElements[x][y])) {
								((Island)puzzleElements[x][y]).setLocked(true);					
								randomNgb=(Island)puzzleElements[x][y];
								return true;
							}
		} while (timer > 0);
		return false;
	}
	
    public boolean connectable(Island a, Island b) {

		int maxX,minX;
		int maxY,minY;
		minX=Math.min(a.getX(), b.getX())+1;
		maxX=Math.max(a.getX(), b.getX());
		maxY=Math.max(a.getY(), b.getY());
		minY=Math.min(a.getY(), b.getY())+1;
		// On vérifie si les deux îles sont sur un axe
		if ((a.getX() != b.getX()) && (a.getY() != b.getY())) {
			return false;}

		// On vérifie qu'il n'y a pas de bloc entre les îles.
		if (a.getY() == b.getY()) {
			for (int i = minX; i < maxX; i++) {
				if (((Island) puzzleElements[i][a.getY()]).isLocked() ) 
				{
					return false;}}
		}
		if (a.getX() == b.getX()) {
			for (int i = minY; i < maxY; i++) {
				if (((Island) puzzleElements[a.getX()][i]).isLocked()) {return false;}}
		}
		
		return true;
	}
	
    private void initField() {
		for (int i = 0; i < puzzleElements.length; i++)
			for (int j = 0; j < puzzleElements[i].length; j++) {
				puzzleElements[i][j] = new Island(0,i, j);
		
			}
	}
	
    public Island selectRandomIsland() {
		int x = (int) (Math.random() * allIslands.size());
		return allIslands.get(x);
	}
	
    public boolean generate(int nbOfIslands) {
		
		Island temp;
		initField(); 
		int x=(int) (Math.random()*(Lines - 1));
		int y=(int) (Math.random()*(Columns - 1));

		((Island) puzzleElements[x][y]).inc();
		((Island) puzzleElements[x][y]).setLocked(true);
		allIslands.add((Island) puzzleElements[x][y]);
		while(!findRandomNgbTo(((Island) puzzleElements[x][y])));
			
			temp=randomNgb;
			allIslands.add((Island) puzzleElements[temp.getX()][temp.getY()]);
			((Island) puzzleElements[temp.getX()][temp.getY()]).inc();
			//nbOfIslands --;
			connect(temp, ((Island) puzzleElements[x][y]));
			while (allIslands.size()!=nbOfIslands) {
				// Minuteur. Il y a 1000 tentatives pour trouver un voisin aléatoire.
				int timer = 1000;
				do {
					// Mémorisez une île au hasard sur le terrain de jeu
					temp = selectRandomIsland();
					timer--;
					//Lorsque le minuteur expire, la méthode se termine par false
					if (timer < 1) {
						return false;
					}
					//jusqu'à ce qu'un voisin aléatoire soit trouvé
				} while (!findRandomNgbTo(temp));
				// Augmenter le nombre de ponts sur les deux îles
				connect(temp, ((Island) puzzleElements[randomNgb.getX()][randomNgb.getY()]));
				((Island) puzzleElements[temp.getX()][temp.getY()]).inc();
				((Island) puzzleElements[randomNgb.getX()][randomNgb.getY()]).inc();
			
			
				allIslands.add(randomNgb);
				

				// Les îles doivent d'abord être connectées
				// pour être sûr que le puzzle est résoluble
				//connect(pnt1, randomNgb);
				// L'île finale trouvée pour la liste avec tous les ponts
				// consiste
				//allIslands.add(randomNgb);
				//ile--;
			}
	
		//	bridgeSolver();
			return true;
		
		
			
	}
	
	public void connect(Island a, Island b) {

		// Si les deux îles sont déjà connectées à un pont, un
		// ajout d'un double pont
		int maxX,minX;
		int maxY,minY;
		minX=Math.min(a.getX(), b.getX())+1;
		maxX=Math.max(a.getX(), b.getX());
		maxY=Math.max(a.getY(), b.getY());
		minY=Math.min(a.getY(), b.getY())+1;

		// Vérifiez si vous pouvez connecter les îles;
		
			// axe vertical
			if (a.getX() == b.getX()) {

				// Toutes les îles 0 entre les deux sont bloquées avec -1
				for (int i = minY; i < maxY; i++) {
					((Island) puzzleElements[a.getX()][i]).setLocked(true);}

				// positionne le pointeur sur les voisins
				//((Island) board[a.getX()][Math.min(a.getY(), b.getY())]).setSouthIsland((Island) board[a.getX()][Math.max(a.getY(), b.getY())]);
			}
			// axe Horizontale 
			if (a.getY() == b.getY()) {

				// Les 0 îles entre les deux sont verrouillées avec -1
				for (int i = minX; i < maxX; i++) {
					((Island) puzzleElements[i][a.getY()]).setLocked(true);}

				// positionne le pointeur sur les voisins
				//((Island) board[Math.min(a.getX(), b.getX())][a.getY()]).setEastIsland((Island) board[Math.max(a.getX(), b.getX())][a.getY()]);
			}
		

	}

	public int getColumns() {
		return Columns;
	}
	
	public int getLines() {
		return Lines;
	}
	

	public Object[][] getPuzzleElements(){
		return puzzleElements;
	}
}
