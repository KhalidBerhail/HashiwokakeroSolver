package application;

import components.DegreeInput;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import solving.*;



public class PuzzleController {
    
	//texts
    @FXML
    private Text puzzle_title1, puzzle_title2, error_msg;

    //shapes
    @FXML
    private Rectangle grid_rec;
    
    //panes
    @FXML
    private Pane puzzle_pane, puzzle_sub_pane, error_panel;
         
    @FXML
    private GridPane puzzle_grid;
    
    //buttons
    @FXML
    private Button new_rand_puzzle, solve_btn, new_Entry_button, generate_btn, close_error_btn;
    
    // Puzzle
    private  puzzleModel puzzle;
    
    // Puzzle solver
    private  PuzzleSolver solver;
    
    //Tableau d'input
    private DegreeInput fields[][];
    
    //Nombre de lignes, colonnes, d'ils
    private int linesNbr, columnsNbr, islandsNbr;
    
    //boolean pour stocker l'état de visibilité du panel d'erreur
	private boolean err_ishiden = false;
    

    
    //constructeur
    public PuzzleController() {
    	 
    }
    
    //fonction qui crée une grille d'input vide ou contenant des degrés d'un puzzle aléatoire selon les spécifications de l'utilisateur 
    public void CreatePuzzle(double lines, double columns, double islands, boolean rand) {
    	
    	
    	this.linesNbr = (int) lines;
    	this.columnsNbr = (int) columns;
    	this.islandsNbr = (int) islands;
    	//creation du puzzle
    	puzzle=new puzzleModel(linesNbr,columnsNbr);
    	//création d'un tableau de DegreeInput
    	fields = new DegreeInput[linesNbr][columnsNbr];
        
    	//largeur et longeur des inputs
    	double w = grid_rec.getWidth()/columnsNbr;
    	double h = grid_rec.getHeight()/linesNbr;
    	 
    	//specification sur les colonnes de la grid pane contenant les inputs
    	for (int x = 0 ; x < columns ; x++) {
             ColumnConstraints cc = new ColumnConstraints();
             cc.setFillWidth(true);
             cc.setHgrow(Priority.ALWAYS);
             puzzle_grid.getColumnConstraints().add(cc);
         }
    	//specification sur les lignes de la grid pane contenant les inputs
    	 for (int y = 0 ; y < lines ; y++) {
             RowConstraints rc = new RowConstraints();
             rc.setFillHeight(true);
             rc.setVgrow(Priority.ALWAYS);
             puzzle_grid.getRowConstraints().add(rc);
         }
    	
    	 //
    	 for(int i = 0 ; i < lines ; i++ ) {
    		  
    		 for(int j = 0 ; j < columns ; j++ ) {
        		 
    			 //Création d'un nouveau DegreeInput et modifier ces propriétés et événements et l'ajouté sur la gride
    			 DegreeInput lignes_field = new DegreeInput(i,j);
    			 lignes_field.MoldInput(linesNbr, columnsNbr, w,h);
    			 fields[i][j] = lignes_field;
    			 lignes_field.ControllInput(fields);
    			 puzzle_grid.add(lignes_field, j, i);
        	 }    		
    	 }
    	 
    	 //si le puzzle est random on génére un puzzle random et on remplie les input avex setBoard();
    	 if(rand == true) {
    		 
    		 puzzle.SetIslands(islandsNbr);
    		 puzzle.setPuzzleElements();
    		 Setgrid();
    	 }
    }

    
    
    
    
    
    
//Méthode d'initialisation du contrelleur
    @FXML
	public void initialize() {
        
        
    	//enlever tous les cellule de la gride dud puzzle
    	while( puzzle_grid.getRowConstraints().size() > 0){
    		
    		 puzzle_grid.getRowConstraints().remove(0);
    	}
    	while( puzzle_grid.getColumnConstraints().size() > 0){
    		
   		     puzzle_grid.getColumnConstraints().remove(0);
   	    }

    	

	}

    //Méthode à executer quand le button "Generate Random" est cliqué   
    @FXML
    void generate_random(MouseEvent event) {
    	
    	//Vider la Pane du puzzle
    	puzzle_sub_pane.getChildren().clear();
    	//vider les inputs et les rendre visible
        for(int i=0; i<linesNbr;i++) {
        	 for(int j=0; j<columnsNbr;j++) {
        		 fields[i][j].setText("");
        		 fields[i][j].setVisible(true);
     		}
        }
         //créer un nouveau puzzle
         puzzle = new puzzleModel(linesNbr,columnsNbr);
         puzzle.SetIslands(islandsNbr);
		 puzzle.setPuzzleElements();
		 Setgrid();
		 
		 //Désactiver le button "Generate"
		 generate_btn.setDisable(false);
    		
    		
    }

    //Méthode à executer quand le button "Solve" est cliqué     
    @FXML
    void SolvePuzzle(MouseEvent event) {
    	
    	Line line;
    	double xA,yA,xB,yB;
		//initialisation du solver
        solver = new PuzzleSolver(puzzle);
        
        //Si le puzzle n'est pas résolu afficher la Pane d'erreur et réinitiliser la grid
        if(!solver.IsSolved()) {
        	
        	error_msg.setText(" Couldn't find a solution!");
        	ShowErrMenu();
        	
        	puzzle_sub_pane.getChildren().clear();
            for(int i=0; i<linesNbr;i++) {
           	 for(int j=0; j<columnsNbr;j++) {
           		 fields[i][j].setText("");
           		 fields[i][j].setVisible(true);
        		}
            }
            
            solve_btn.setDisable(true);   
            generate_btn.setDisable(false);
        //    
        } else {
        	for(int row = 0; row < linesNbr; row++){
    			for(int col = 0; col < columnsNbr; col++){
    		if(puzzle.getPuzzleElements() [row][col] instanceof Connection) {	
                 if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionDirection() == 1){
				      if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionType() == 1){
					
					
					       xA =fields[row][col].getLayoutX()+fields[row][col].getWidth()/2;
					       yA =fields[row][col].getLayoutY();
					       xB =xA ;
					       yB =fields[row][col].getLayoutY()+fields[row][col].getHeight();
					       //System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
					       line = new Line(xA,yA,xB,yB);
					       
					       line.setStroke(Color.web("#e86a17"));
					       line.setStyle("-fx-stroke-width:3");
					       
					       line.toBack();
					       puzzle_sub_pane.getChildren().add(line);
					
				      }
					  else if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionType() == 2){
						  
							xA =fields[row][col].getLayoutX()+fields[row][col].getWidth()/2;
							yA =fields[row][col].getLayoutY();
							xB =xA ;
							yB =fields[row][col].getLayoutY()+fields[row][col].getHeight();
							//System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
							line = new Line(xA,yA,xB,yB);
							line.setStroke(Color.web("#e86a17"));
 					        line.setStyle("-fx-stroke-width:3");
							line.toBack();
							puzzle_sub_pane.getChildren().add(line);
							xA = xA+6;
							xB = xB+6;
							//System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
							line = new Line(xA,yA,xB,yB);
							line.setStroke(Color.web("#e86a17"));
 					        line.setStyle("-fx-stroke-width:3");
							line.toBack();
							puzzle_sub_pane.getChildren().add(line);
						
					  }
			      }
			//if object is a double Edge
					else if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionDirection() == 2){
						if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionType() == 1){

							xA =fields[row][col].getLayoutX();
							yA =fields[row][col].getLayoutY()+fields[row][col].getHeight()/2;
							xB =fields[row][col].getLayoutX()+fields[row][col].getWidth();
							yB =yA;
							
							//System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
							line = new Line(xA,yA,xB,yB);
							line.setStroke(Color.web("#e86a17"));
 					        line.setStyle("-fx-stroke-width:3");
							line.toBack();
							puzzle_sub_pane.getChildren().add(line);
							
						}
						else if(((Connection)puzzle.getPuzzleElements()[row][col]).getConnectionType() == 2){

							
							xA =fields[row][col].getLayoutX();
							yA =fields[row][col].getLayoutY()+fields[row][col].getHeight()/2;
							xB =fields[row][col].getLayoutX()+fields[row][col].getWidth();
							yB =yA;
							//System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
							line = new Line(xA,yA,xB,yB);
							line.setStroke(Color.web("#e86a17"));
 					        line.setStyle("-fx-stroke-width:3");
							line.toBack();
							puzzle_sub_pane.getChildren().add(line);
							yA = yA+6;
							yB = yB+6;
							//System.out.println("XA:"+xA+"YA:"+yA+",,XB:"+xB+"YA:"+yB);
							line = new Line(xA,yA,xB,yB);
							line.setStroke(Color.web("#e86a17"));
 					        line.setStyle("-fx-stroke-width:3");
							
							line.toBack();
							puzzle_sub_pane.getChildren().add(line);
							
							
							
						}
					}
					else{

						//System.out.println("last else");
					}
            
        }}}  
            
         solve_btn.setDisable(true);   
         generate_btn.setDisable(true);
        }
            
    }
    
    
    
    
    @FXML
    public void Generate() {
    	
    	
    	
    	double nX;
    	double nY;
    	
    	int islandsnbr = 0;
      for(int x=0; x<linesNbr;x++) {
    		
    		for(int y=0; y<columnsNbr;y++) {
    			
    			if(!fields[x][y].getText().isEmpty()){
    				islandsnbr = islandsnbr + 1;
    			}
    		}
       }
    	
    	
    	if(islandsnbr != this.islandsNbr) {
    		
    		int missing = islandsNbr - islandsnbr;
    		if(missing > 0)
    		   error_msg.setText("Add "+missing+" more island");
    		if(missing < 0)
     		   error_msg.setText("Remove "+(-missing)+" more island");
    		
    		ShowErrMenu();
    	}else {
    		
    		for(int i=0; i<linesNbr;i++) {
        		
        		for(int j=0; j<columnsNbr;j++) {
        			
        			
        			
        			fields[i][j].setVisible(false);
        			if(!fields[i][j].getText().isEmpty()) {
        				
        				int degre = (int) Integer.parseInt(fields[i][j].getText());
        				nX = fields[i][j].getLayoutX() + fields[i][j].getWidth()/2;
        				nY = fields[i][j].getLayoutY() + fields[i][j].getHeight()/2;
        				Island n = new Island(degre, j, i);
        				n.setDrawParams(nX,nY);
        				puzzle.getPuzzleElements()[i][j]= n;
        				Circle circle = new Circle();
        				circle.setRadius(fields[i][j].getWidth()/2);
        				circle.setFill(Color.web("#e86a17"));
        				
        				Text text = new Text(""+degre);
        				
        				
        				double fontSize = Math.min(grid_rec.getHeight()/linesNbr/2 - 2,grid_rec.getWidth()/columnsNbr/2 - 2); 
        				Font custumfont = Font.loadFont(this.getClass().getResource("/resources/kenvector_future.ttf").toExternalForm(),fontSize);
        				text.setFont(custumfont);
        				
        				text.setFill(Color.BLACK); 
        				text.setStyle("-fx-color:white;");
        				
        				
        				StackPane layout = new StackPane();
        		        layout.getChildren().addAll(
        		                circle,
        		                text
        		        );
        		        
        		        
        		        layout.setLayoutX(fields[i][j].getLayoutX());
        		        layout.setLayoutY(fields[i][j].getLayoutY());
        		        puzzle_sub_pane.getChildren().add(layout);
        				
        				
        				
        				
        				
        			} else {
        				Connection n = new Connection();
        				puzzle.getPuzzleElements()[i][j] = n;
        			}
        			
        			
        		}
        	}
        	puzzle.findPossibleConnections();
        	
        	solve_btn.setDisable(false);
        	 generate_btn.setDisable(true);
    	}
    	
    	
    	
    	
    }

    public void Setgrid() {
    	 for(int i = 0; i < linesNbr; i++){
				for(int j = 0; j < columnsNbr; j++){
					if(puzzle.getPuzzleElements()[i][j] instanceof Island ) {
						fields[i][j].setText(""+ ((Island)puzzle.getPuzzleElements()[i][j]).getDegree());
					}
				}
			}
	}

    
    
    @FXML
    void newEntry(MouseEvent event) {
    	puzzle_sub_pane.getChildren().clear();
        for(int i=0; i<linesNbr;i++) {
       	 for(int j=0; j<columnsNbr;j++) {
       		 fields[i][j].setText("");
       		 fields[i][j].setVisible(true);
    		}
    }
        generate_btn.setDisable(false);
    }
    
    @FXML
    void closeErrPanel(MouseEvent event) {
    	ShowErrMenu();
    }
    
    
void ShowErrMenu() {
        
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(error_panel);
		if(err_ishiden) {
			
			transition.setToY(-433);
		}
		else {

			transition.setToY(422);
			
			
		}
		transition.play();
		err_ishiden = !err_ishiden;
       //moveDown();
       
    }
}





