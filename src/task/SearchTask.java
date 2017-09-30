/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import model.DuplicateFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 *
 * @author Alexander
 */
public class SearchTask extends Task<Void> {

    private String directory;
    private TableView table;
    private ProgressBar progressBar;
    private Label filesScannedLabel;
    private ObservableList<DuplicateFile> fileList = FXCollections.observableArrayList();
    private ObservableList<DuplicateFile> duplicateFileList = FXCollections.observableArrayList();
    private HashSet<String> tempSet = new HashSet<>();
    
    public SearchTask(String directory, TableView table, ProgressBar progressBar) {
        this.directory = directory;
        this.table = table;
        this.progressBar = progressBar;
    }
    
    private ObservableList<DuplicateFile> getDuplicateList(ObservableList<DuplicateFile> fileList) {
        ObservableList<DuplicateFile> newRows = FXCollections.observableArrayList();
        Set<String> set = new HashSet<>();
        
        for (DuplicateFile duplicateFile : fileList) {
            if (!set.add(duplicateFile.getHash())) {
                newRows.add(duplicateFile);  
            }
        }
        return newRows;
    }
    
    private long getFileLengthFromList(ObservableList<DuplicateFile> duplicateFileList) throws FileNotFoundException {
        long fileLength = 0;
        for(int i = 0; i < duplicateFileList.size(); i++) {
            File temp = new File(duplicateFileList.get(i).getPath());
            fileLength += temp.getTotalSpace();
        }
        return fileLength;
    }
    
    private String getFilesScanned(ObservableList<DuplicateFile> duplicateFileList) {
        return String.valueOf(duplicateFileList.size());
    }
    
    @Override
    protected Void call() throws Exception {
        File dir = new File(directory);
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        
        for (int i = 0; i < files.size(); i++) {
            FileInputStream fis = new FileInputStream(files.get(i));
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            
            DuplicateFile duplicateFile = new DuplicateFile(files.get(i).getName(), files.get(i).getPath(), md5);
            fileList.add(duplicateFile);
            duplicateFileList = getDuplicateList(fileList);
            
            long bytesRead = 0L;
            long fileLength = getFileLengthFromList(duplicateFileList);
            byte[] buf = new byte[8192];
            int n;
            while ((n = fis.read(buf)) > 0) {
                bytesRead += n;
                updateProgress(bytesRead, fileLength); 
            }
            table.setItems(duplicateFileList);
            fis.close();
        }
        return null;
    }
    
    @Override
    protected void failed() {
        
    }
    
    @Override
    protected void succeeded() {
        table.setItems(duplicateFileList);
        progressBar.setVisible(false);
    }
    
}
