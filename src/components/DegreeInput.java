package components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class DegreeInput extends TextField {
	
	private int i;
	private int j;
	
	public DegreeInput(int x, int y) {
		super();
		i = x;
		j = y;
	}
	
	public void MoldInput(int lines, int columns, double width, double height) {
		
		this.setPrefSize(width,height);
		double fontSize = Math.min(height/2 - 2, width/2 - 2);
		
		Font custumfont = Font.loadFont(this.getClass().getResource("/resources/kenvector_future.ttf").toExternalForm(),fontSize);
		setFont(custumfont);
		this.textProperty().addListener(new ChangeListener<String>() {
			 

				@Override
				public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
					// TODO Auto-generated method stub
					
					if (!arg2.matches("\\d*")) {
						setText(arg2.replaceAll("[^\\d]", ""));
			        }
					if (getText().length() > 1) {
		                String s = getText().substring(0, 1);
		                setText(s);
		            }
					if (arg2.equals("0") ||arg2.equals("9")) {
		                
		                clear();
		            }
					
				}
			});
		
		
	}
	
	public void ControllInput(DegreeInput[][] tab) {
		
		this.textProperty().addListener(new ChangeListener<String>() {
			 

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				
				if (!arg2.matches("\\d*")) {
					setText(arg2.replaceAll("[^\\d]", ""));
		        }
				if (getText().length() > 1) {
	                String s = getText().substring(0, 1);
	                setText(s);
	            }
				if (arg2.equals("0") ||arg2.equals("9")) {
	                
	                clear();
	            }
				
				
				
				
				
				
				if(i == 0) {
					
					if(!tab[i+1][j].getText().isEmpty())
						clear();	
				} else if(i!=0 && i<tab.length-1) {
					
					if(!tab[i+1][j].getText().isEmpty() || !tab[i-1][j].getText().isEmpty())
						clear();
				} else {
					if(!tab[i-1][j].getText().isEmpty())
						clear();
					
				}
				
				if(j == 0) {
					
					if(!tab[i][j+1].getText().isEmpty())
						clear();
				} else if(j!=0 && j<tab.length-1 ) {
					
					if(!tab[i][j+1].getText().isEmpty() || !tab[i][j-1].getText().isEmpty())
						clear();
				} else {
					
					if(!tab[i][j-1].getText().isEmpty())
						clear();
				}
					
					
					
					
						
						
				
				
				
				
			}
		});
		
		
		
	}

}
