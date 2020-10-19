package GUIStuff;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Welcome extends Application{
	
	private static CheckBox[] cbs; //Save booleans of checked/unchecked
	private static String fileInPath = " "; //Save the csv file path
	private static String fileOutPath = " "; //Save the Folder path
    private static File selectedFile; //csv file
    private static File selectedDirectory; //folder
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) throws IOException {
		
		/*
		 * inherited from Application
		 * params: stage 
		 * returns: N/A
		 */
		
		
		GridPane grid = new GridPane(); //my grid pane
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        FileChooser fileb = new FileChooser(); //the file chooser window
        DirectoryChooser directoryb = new DirectoryChooser(); //the folder chooser window
        
		Text text1 = new Text(40, 10, "Math Graph"
				+ " Question Maker\n                               1.0.0"); //texts in the gui...
		text1.getStyleClass().add("text_one");//...
		text1.setFill(Color.WHITE);
		Text text2 = new Text(40, 10, "Press 'Browse' to enter your input file.");
		
		Text text3 = new Text(40, 10, "Press 'Browse' to enter your output file.");
		
		Text text4 = new Text(40, 10, "Press 'Next' to start, whenever you're done.");
		
		text3.setFill(Color.CYAN);
		text4.setFill(Color.YELLOW);
		text2.setFill(Color.CYAN);
		
		Text text5 = new Text(40, 10, " ");
		Text text6 = new Text(40, 10, " ");
		
		Text text7 = new Text(40, 10, " ");
		Text text8 = new Text(40, 10, " ");
		
		Text text9 = new Text(40, 10, "Categories in the test Checklist");
		Text text10 = new Text(40, 10, "");
		
		text5.setFill(Color.RED);
		text6.setFill(Color.RED);
		text9.setFill(Color.ORANGE);
		text10.setFill(Color.RED);
		
		text1.setFont(new Font(40));
		text2.setFont(new Font(25));
		text3.setFont(new Font(25));
		text4.setFont(new Font(25));
		text5.setFont(new Font(20));
		text6.setFont(new Font(20));
		text7.setFont(new Font(20));
		text8.setFont(new Font(20));
		text9.setFont(new Font(20));
		text7.setFill(Color.WHITE);
		text8.setFill(Color.WHITE);
		
		grid.add(text1, 2, 1);//blit the texts on the grid
		grid.add(text2, 2, 4);
		grid.add(text3, 2, 8);
		grid.add(text4, 2, 12);
		grid.add(text5, 2, 6);
		grid.add(text6, 2, 10);
		grid.add(text7, 2, 5);
		grid.add(text8, 2, 9);
		grid.add(text9, 3, 2);
		grid.add(text10, 3, 3);
		
		CheckBox checkbox1 = new CheckBox("Trigonometric");//checkboxes
		checkbox1.setTextFill(Color.LIGHTPINK);
		CheckBox checkbox2 = new CheckBox("Polynomial");
		checkbox2.setTextFill(Color.LIGHTPINK);
		CheckBox checkbox3 = new CheckBox("Exponential");
		checkbox3.setTextFill(Color.LIGHTPINK);
		CheckBox checkbox4 = new CheckBox("Square root");
		checkbox4.setTextFill(Color.LIGHTPINK);
		CheckBox checkbox5 = new CheckBox("Rational");
		checkbox5.setTextFill(Color.LIGHTPINK);
		
		cbs = new CheckBox[] {checkbox1, checkbox2, checkbox3, checkbox4, checkbox5}; //checkbox holder
		
		
		grid.add(checkbox1, 3, 4); //throw checkboxes on the grid
		grid.add(checkbox2, 3, 5);
		grid.add(checkbox3, 3, 6);
		grid.add(checkbox4, 3, 7);
		grid.add(checkbox5, 3, 8);
		
		Button btn2 = new Button("Browse"); //buttons
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);

		Button btn3 = new Button("Browse");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn3.getChildren().add(btn3);
		
		Button btn = new Button("Next");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        
        grid.add(hbBtn, 1, 12);
        grid.add(hbBtn2, 1, 4);
        grid.add(hbBtn3, 1, 8);
        
        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		try {
        			giveCsvInfo();
	        		selectedFile = fileb.showOpenDialog(stage);
	        		String temp = selectedFile.getAbsolutePath();
	    			text5.setText(" ");
	    			text7.setText(temp);
	        		if (temp.charAt(temp.length() - 1) == 'v' && temp.charAt(temp.length() - 2) == 's' && temp.charAt(temp.length() - 3) == 'c' && temp.charAt(temp.length() - 4) == '.') {
	        			fileInPath = temp;
	        		}
	        		else {
	        			fileInPath = " ";
	        		}
        		}
        		catch (Exception e1) {
        			System.out.print("");
        		}
        	}
        });
        
        btn3.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		giveFolderInfo();
        		selectedDirectory = directoryb.showDialog(stage);
	        	try {
        			String temp = selectedDirectory.getAbsolutePath();
	    			text6.setText(" ");
	    			text8.setText(temp);
	        		fileOutPath = temp;
	        	}
	        	catch (Exception e1) {
	        		System.out.print("");
	        	}
	        	
        	}
        });
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e){ //check if input is properly done
            	boolean a = false;
            	if (countTrues() < 2) {
        			text10.setText("Choose at least two categories.");
        			sayNoo();
        			a = true;
        		}
            	else {
            		text10.setText(" ");
            	}
        		if (!fileInPath.equals(" ") && !fileOutPath.equals(" ")) {
        			if (a == false) {
        				sayYes();
        			}
        		}
        		else if (!fileOutPath.equals(" ")) {
        			text5.setText("You didn't choose a file to input or your input file is invalid format.");
        			text6.setText(" ");
        			sayNo();
        		}
        		else if(!fileInPath.equals(" ")){
        			text5.setText(" ");
        			text6.setText("You didn't choose a directory to make the questions.");
        			sayNo();
        		}
        		
        		else {
        			text5.setText("You didn't choose a file to input or your input file is invalid format.");
        			text6.setText("You didn't choose a directory to make the questions.");
        			sayNo();
        		}
        	
        		
            }
          });
		
		Scene scene = new Scene(grid, 1200, 700); //scene
		scene.getStylesheets().add("GUIStuff/stylesheet.css"); //link to css
		stage.setTitle("MGQM 1.0.0"); //caption
		stage.getIcons().add(new Image(Welcome.class.getResourceAsStream("favicon.png"))); //icon
		stage.setScene(scene); //throw scene into the stage
	
		stage.show();
		
		}
	public static String getOutPath() {//returns the folder path after changing \ to /
		char[] l = fileOutPath.toCharArray();
		for (int h = 0; h < fileOutPath.length(); h++) {
			if (fileOutPath.charAt(h) == '\\') {
				l[h] = '/';
			}
		}
		fileOutPath = new String(l);
		return fileOutPath;
	}
	public static String getInPath() {//returns the file path
		return fileInPath;
	}
	public static void sayNo() {//message box for input error
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Invalid Data");
    	alert.setHeaderText("Information lacks!");
    	alert.setContentText("Check where did you enter wrong data.");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.print("");
    	    }
    	    
    	});
	}
	public static void sayNoo() {//message box for checkbox error
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Invalid CheckList");
    	alert.setHeaderText("Not enough!");
    	alert.setContentText("Check at least two cartegories.");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        System.out.print("");
    	    }
    	    
    	});
	}
	public static void sayYes() { //confirm
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Message");
    	alert.setHeaderText("Data Validated.");
    	alert.setContentText("Press OK to proceed, CANCEL to close this message box and edit your data.");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	        Platform.exit();
    	    }
    	    else if (rs == ButtonType.CANCEL) {
    	    	System.out.println("");
    	    }
    	    
    	});
	}
	public static void giveFolderInfo() { //message box after press browse for folder
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");
    	alert.setHeaderText("Folder Selection");
    	alert.setContentText("Choose a directory as your workspace, the PDFs folder will be created in your workspace.");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	    	System.out.println("");
    	    }
    	    
    	});
	}
	public static void giveCsvInfo() { //message box after press browse for csv
		Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Alert");
    	alert.setHeaderText("File Selection");
    	alert.setContentText("Choose a Comma Separated Value (CSV) file including your students data. (NAME,LASTNAME,ID)");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	    	System.out.println("");
    	    }
    	    
    	});
	}
	public static boolean[] reportCats() { //returns cbs into a boolean
		boolean[] ret = new boolean[6];
		if (cbs[0].isSelected() == true) {
			ret[0] = true;
			ret[1] = true;
		}
		else {
			ret[0] = false;
			ret[1] = false;
		}
		for (int h = 1; h < cbs.length; h++) {
			if (cbs[h].isSelected() == true) {
				ret[h+1] = true;
			}
			else {
				ret[h+1] = false;
			}
		}
		return ret;
	}
	public static int countTrues() {//in the cbs checkbox[] counts # of checkboxes that is checked.
		int a = 0;
		for (int h = 0; h < cbs.length; h++) {
			if (cbs[h].isSelected()) {
				a++;
			}
		}
		return a;
	}
	
}
