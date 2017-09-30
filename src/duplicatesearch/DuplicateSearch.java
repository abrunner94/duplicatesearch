/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesearch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alexander
 */
public class DuplicateSearch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("DuplicateSearch.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("DuplicateSearch");
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
