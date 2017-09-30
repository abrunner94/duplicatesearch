/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Alexander
 */
public class DuplicateFile {
    private SimpleStringProperty name, path, hash;
    
    public DuplicateFile(String name, String path, String hash) {
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
        this.hash = new SimpleStringProperty(hash);
    }

    public String getName() {
        return name.get();
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(SimpleStringProperty path) {
        this.path = path;
    }

    public String getHash() {
        return hash.get();
    }

    public void setHash(SimpleStringProperty hash) {
        this.hash = hash;
    }
     
}
