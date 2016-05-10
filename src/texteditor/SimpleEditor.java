/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texteditor;

import javafx.scene.control.TextArea;

/**
 *
 * @author Akhilesh Yadav
 */
public class SimpleEditor{
    public boolean modified = false;
    public TextArea textArea = new TextArea();
    public String filename = null;
    public SimpleEditor(){
        textArea.setPrefRowCount(100);
        textArea.setPrefColumnCount(100);
        textArea.setWrapText(true);
        textArea.setPrefWidth(150);
    }

    public boolean isModified() {
        return modified;
    }
    
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public TextArea getRoot() {
        return textArea;
    }

    public void setText(String text) {
        textArea.setText(text);
    }
    
    public String getText() {
        return textArea.getText();
    }
}
