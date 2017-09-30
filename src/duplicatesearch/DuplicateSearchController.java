/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesearch;

import model.DuplicateFile;
import task.SearchTask;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Alexander
 */
public class DuplicateSearchController implements Initializable {
    
    @FXML private TextField searchField;
    @FXML private Button searchButton, deleteButton, cancelButton, selectAllButton;
    @FXML private TableView<DuplicateFile> table;
    @FXML private TableColumn<DuplicateFile, SimpleStringProperty> nameColumn, pathColumn, hashColumn;
    @FXML private ProgressBar progressBar;
    @FXML private Label filesScannedLabel;
    
    private Task<Void> searchTask;
    private Thread thread;
    
    @FXML
    private void handleSearchButton(ActionEvent event) {
        if (!searchField.getText().isEmpty() && searchField.getText().length() > 4) {
            table.getSelectionModel().clearSelection();
            table.setItems(null);
            searchField.setStyle(null);
            searchTask = new SearchTask(searchField.getText(), table, progressBar);
            progressBar.setVisible(true);
            progressBar.progressProperty().bind(searchTask.totalWorkProperty());

            thread = new Thread(searchTask);
            thread.setDaemon(true);
            thread.start();
            cancelButton.setDisable(false);
        } else {
            searchField.setStyle("-fx-border-color: red");
        }
    }
    
    @FXML
    private void handleDeleteButton(ActionEvent event) {
        table.getSelectionModel().getSelectedItems().forEach(file -> {
            try {
                FileUtils.touch(new File(file.getPath()));
            } catch (IOException ex) {   }
            File filesToDelete = FileUtils.getFile(file.getPath());
            FileUtils.deleteQuietly(filesToDelete);
        });
        table.getSelectionModel().clearSelection();
        table.setItems(null);
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        if (thread.isAlive()) 
            thread.stop();
        cancelButton.setDisable(true);
        progressBar.setVisible(false);
        table.getSelectionModel().clearSelection();
        table.setItems(null);
    }
    
    @FXML
    private void handleSelectAllButton(ActionEvent event) {
        table.getSelectionModel().selectAll();  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("Path"));
        hashColumn.setCellValueFactory(new PropertyValueFactory<>("Hash"));
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
}