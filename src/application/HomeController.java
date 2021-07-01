package application;



import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;



public class HomeController  {
	//panes
    @FXML
    private Pane home_pane;
    @FXML
    private Pane custom_menu;
    //buttons
    @FXML
    private Button home_custom_btn;
    @FXML
    private Button home_random_btn;
    @FXML
    private Button menu_confirm_btn;
    @FXML
    private Button menu_cancel_btn;    


    //texts
    @FXML
    private Text hashi_title1;
    @FXML
    private Text hashi_title2;
    @FXML
    private Text menu_title1;
    @FXML
    private Text menu_title2;
    
    @FXML
    private Text menu_lignes_title;

    @FXML
    private Text menu_columns_title;

    @FXML
    private Text menu_islands_title;
    //Fields
    @FXML
    private TextField lignes_field;

    @FXML
    private TextField columns_field;

    @FXML
    private TextField islands_fields;
    
    
    //sliders
    @FXML
    private Slider lignes_slider;

    @FXML
    private Slider columns_slider;

    @FXML
    private Slider islands_slider;
    
    //imageViews
    @FXML
    private ImageView custom_menu_img;
    
    
    
    private boolean ishiden = false;
    private boolean random = false;

    public HomeController() {
 
    }
    
    //factorize put the sliders in a list
    @FXML
	public void initialize() {
    	
    	sliderEvent();

	}
    
    

    @FXML
    void SetRandTrue(MouseEvent event) {
         random = true;
         ShowParamMenu(event); 
    }

    @FXML
    void setRandFalse(MouseEvent event) {
    	 random = false;
    	 ShowParamMenu(event); 
    }
    
    
    void ShowParamMenu(MouseEvent event) {
        
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(custom_menu);
		if(ishiden) {
			
			transition.setToY(-433);
		}
		else {

			transition.setToY(422);
			islands_slider.setValue(islands_slider.getMin());
			lignes_slider.setValue(lignes_slider.getMin());
			columns_slider.setValue(columns_slider.getMin());
			
		}
		transition.play();
		ishiden = !ishiden;
       //moveDown();
       
    }
    
    @FXML
    public void loadCustomPuzzle() throws IOException  {
    	/*Parent root =FXMLLoader.load(getClass().getResource("puzzle.fxml"));
    	Stage window = (Stage) menu_confirm_btn.getScene().getWindow();
    	window.setScene(new Scene(root, 750,500));*/
    	
    	double width = 980, height = 649;
    	FXMLLoader Loader = new FXMLLoader();
    	Loader.setLocation(getClass().getResource("/fxml/puzzle.fxml"));
        Loader.load();
        PuzzleController PC = Loader.getController();
        PC.CreatePuzzle(lignes_slider.getValue(),columns_slider.getValue(),islands_slider.getValue(), random);
        Parent root = Loader.getRoot();
        Stage window = (Stage) menu_confirm_btn.getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    	window.setX((screenBounds.getWidth() - width) / 2); 
        window.setY((screenBounds.getHeight() - height) / 2);
    	window.setScene(new Scene(root, width, height));
    }
    

    
    
    
    
    public void sliderEvent() {
    	
    	lignes_field.setText(""+lignes_slider.getValue());
    	columns_field.setText(""+columns_slider.getValue());
    	islands_fields.setText(""+islands_slider.getValue());
    	
    	
    	lignes_slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				double value = lignes_slider.getValue();
				lignes_field.setText((String.format("%.0f", value)));
				
			}
    		
    	});
    	
    	columns_slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				double value = columns_slider.getValue();
				columns_field.setText((String.format("%.0f", value)));
				
			}
    		
    	});
    	
    	islands_slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				double value = islands_slider.getValue();
				double maxIslands =columns_slider.getValue() * lignes_slider.getValue();
				
				if(value<=maxIslands )
				islands_fields.setText((String.format("%.0f", value)));
				else
					islands_fields.setText((String.format("%.0f", maxIslands)));
				
			}
    		
    	});
    }


}
