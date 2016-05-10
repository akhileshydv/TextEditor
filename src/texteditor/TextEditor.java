/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texteditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Akhilesh Yadav
 */
public class TextEditor extends Application {
    /**
     * @param args the command line arguments
     */
    private TabPane tp;
    private final Vector<SimpleEditor> editors=new Vector();  //to implement multitab functionality  
    private String str=""; //for dock feature purpose
    private Stage stage;
    private String text=""; //for searching purpose
    private String srch=""; //for searching purpose
    private int pos=-1;  //for searching purpose
    private String repl="";//for searching purpose
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        this.stage=stage;
        stage.setTitle("TextEditor+"); //stage is top level container
        stage.setWidth(800);
        stage.setHeight(600);
        //to create Menu bar    
        MenuBar mbr=new MenuBar();  
            Menu m1=new Menu("File");
                Menu m11=new Menu("New");
                    MenuItem m111=new MenuItem("C file");
                    MenuItem m112=new MenuItem("C++ file");
                    MenuItem m113=new MenuItem("Java file");
                    m111.setOnAction((ActionEvent t) -> {
                        createNewCFile("Untitled");   //to create new c file
                    });
                    m112.setOnAction((ActionEvent t) -> {
                        createNewCppFile("Untitled"); //to create new cpp file
                    });
                    m113.setOnAction((ActionEvent t) -> {
                        createNewJavaFile("Untitled"); //to create new java file
                    });
                m11.getItems().addAll(m111,m112,m113);
                MenuItem m12=new MenuItem("Open");
                m12.setOnAction((ActionEvent t) -> {
                     loadFile();   //to open file using FileChooser class   
                });
                MenuItem m13=new MenuItem("Save");
                m13.setOnAction((ActionEvent t) -> {
                     saveFile(); //to save file using FileChooser class
                });
                MenuItem m14=new MenuItem("Save As");
                m14.setOnAction((ActionEvent t) -> {
                    saveAsFile(); //to save file using FileChooser class
                });
                MenuItem m15=new MenuItem("Exit");
                m15.setOnAction((ActionEvent t) -> {
                    confirmDialogBox(); //to confirm whether you want to exit texteditor or not
                });
            m1.getItems().addAll(m11,m12,m13,m14,m15);
            Menu m2=new Menu("Edit");
                MenuItem m22=new MenuItem("Copy");
                m22.setOnAction((ActionEvent t) -> {
                     copy();  //to copy selected text
                });
                MenuItem m23=new MenuItem("Cut");
                m23.setOnAction((ActionEvent t) -> {
                     cut();  //to cut selected text
                });
                MenuItem m24=new MenuItem("Paste");
                m24.setOnAction((ActionEvent t) -> {
                     paste(); //to paste text at given caret position
                });
                MenuItem m25=new MenuItem("Delete");
                m25.setOnAction((ActionEvent t) -> {
                     delete(); //to delete selected text
                });
            m2.getItems().addAll(m22,m23,m24,m25);    
            Menu m3=new Menu("Tools");
                MenuItem m31=new MenuItem("Find");
                m31.setOnAction((ActionEvent t) -> {
                     find(); //to find given string in the text
                });
                
                MenuItem m33=new MenuItem("Replace");
                m33.setOnAction((ActionEvent t) -> {
                     replace(); //to replace given string with another string
                });
                MenuItem m34=new MenuItem("ReplaceAll");
                m34.setOnAction((ActionEvent t) -> {
                     replaceAll(); //to replace all instances of given string with another string
                });
                MenuItem m35=new MenuItem("Select All");
                m35.setOnAction((ActionEvent t) -> {
                     selectAll(); //to select whole text in text area
                });
            m3.getItems().addAll(m31,m33,m34,m35);    
            Menu m4=new Menu("Goto"); 
                MenuItem m42=new MenuItem("Symbol");
                m42.setOnAction((ActionEvent t) -> {
                     gotoSymbol(); //to select whole text in text area
                });
                MenuItem m43=new MenuItem("Word");
                m43.setOnAction((ActionEvent t) -> {
                     gotoWord(); //to select whole text in text area
                });
            m4.getItems().addAll(m42,m43);
            Menu m5=new Menu("Editor");
                MenuItem m51=new MenuItem("Font");
                MenuItem m52=new MenuItem("Theme");
            m5.getItems().addAll(m51,m52);    
            Menu m6=new Menu("Others");
               MenuItem m62=new MenuItem("About");
                m62.setOnAction((ActionEvent t) -> {
                     about(); //to select whole text in text area
                });
            m6.getItems().addAll(m62);
        mbr.getMenus().addAll(m1,m2,m3,m4,m5,m6);
        tp=new TabPane();
        VBox vb=new VBox();
        vb.getChildren().addAll(mbr,tp);
        Scene scene = new Scene(vb, 800, 600);
        scene.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    private void createNewCFile(String str) {  
             Tab tab=new Tab();
             SimpleEditor content=new SimpleEditor();
             String itext=""; //to set initial text
             itext ="#include <stdio.h>\n" +
                "#include <string.h>\n" +
                "#include <math.h>\n" +
                "#include <stdlib.h>\n" +
                "#define max (a>b?a:b)\n" +
                "#define min (a<b?a:b)\n" +
                "int main() {\n" +
                "    /* Enter your code here... */    \n" +
                "    return 0;\n" +
                "}";
             
             editors.add(content);
             tab.setContent(content.getRoot());
             tab.setText(str);
             tp.getTabs().add(tab);
             SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();
             selectionModel.select(tab);
             TextArea ta=content.getRoot();
             ta.setText(itext);
             
    }
    private void createNewCppFile(String str) {
             Tab tab=new Tab();
             SimpleEditor content=new SimpleEditor();
             String itext=""; //to set initial text
             itext="#include<bits/stdc++.h>\n" +
                   "#define vi vector<int>\n" + 
                   "#define vvi vector< vi >\n" +
                   "#define pii pair<int,int>\n" +
                   "#define pb push_back\n" +
                   "#define mp make_pair\n" +
                   "#define all(c) c.begin(),c.end()\n" +
                   "#define rall(c) c.rbegin(),c.rend() \n" +
                   "#define sz(c) c.size()\n" +
                   "#define tr(c,it)  for(typeof(c.begin()) it=c.begin();it!=c.end();it++)  \n" +
                   "#define max(a,b) (a>b?a:b)\n" +
                   "#define min(a,b) (a<b?a:b)\n" +
                   "#define present(c,x) find(all(c),x)!=c.end()\n" + 
                   "#define cpresent(c,x) c.find(x)!=c.end()  \n" +
                   "#define permute(c) next_permutation(all(c))\n" +
                   "using namespace std;\n" +
                   "int main(){\n" +
                   "     /*Enter your code here...  */\n" +
                   "     //freopen(\"input_file_name.in\",\"r\",stdin);\n" +
                   "     //freopen(\"output_file_name.out\",\"w\",stdout);\n" +
                   "     return 0;\n" +
                   "}";
             editors.add(content);
             tab.setContent(content.getRoot());
             tab.setText(str);
             tp.getTabs().add(tab);
             SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();//to select new tab
             selectionModel.select(tab); //to select new tab
             TextArea ta=content.getRoot(); 
             ta.setText(itext);
    }
    private void createNewJavaFile(String str) {
             Tab tab=new Tab();
             SimpleEditor content=new SimpleEditor();
             String itext="";//to set initial text
             itext="import java.io.*;\n" +
             "import java.util.*;\n" +
             "import java.text.*;\n" +
             "import java.math.*;\n" +
             "import java.util.regex.*;\n" +
             "\n" +
             "public class <class name> {\n" +
             "\n" +
             "    public static void main(String[] args) {\n" +
             "        /* Enter your code here.... */\n" +
             "    }\n" +
             "}";
             editors.add(content);
             tab.setContent(content.getRoot());
             tab.setText(str);
             tp.getTabs().add(tab);
             SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();//to select the new tab
             selectionModel.select(tab); //to select new tab
             TextArea ta=content.getRoot();
             ta.setText(itext);
    }
    private SimpleEditor getEditorForTextArea(TextArea area){
        Iterator<SimpleEditor> iter=editors.iterator();//iterate through all editors and check the match for given text area
        while(iter.hasNext()){
            SimpleEditor ed=iter.next();
            if(area==(TextArea)ed.getRoot()){
                 return ed;
            }
        }
        return null;
    }
    private void loadFile() {
            FileChooser fto=new FileChooser();
            File fo=fto.showOpenDialog(null); //to open 'open file dialog box'
            String buffer=""; //store text from opened file
            if(fo!=null){
                try {
                    Scanner fin=new Scanner(fo); //to copy text from opened file and save it in buffer 
                    while(fin.hasNext()){
                         buffer+=fin.nextLine()+"\n"; 
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                SimpleEditor ed=new SimpleEditor();
                ed.filename=fo.getAbsolutePath();// set name of file opened in the editor 
                ed.setText(buffer);//set text in buffer to text area
                editors.add(ed); // add editor to the vector
                Tab tab=new Tab(fo.getName());  //name of the tab is the name of the file
                tab.setContent(ed.getRoot()); //set editor as the content of the tab
                tp.getTabs().add(tab); //setting tab to the tabpane
                SingleSelectionModel<Tab> selectionModel = tp.getSelectionModel();
                selectionModel.select(tab);//to select new opened tab
            }
    }
    private void saveFile(){
          File file=null;
          SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
          Tab selTab=ssm.getSelectedItem();//to get the selected tab
          SimpleEditor ed=getEditorForTextArea((TextArea)selTab.getContent()); 
          if(ed==null){
              return;
          }
          String openfilename=ed.filename;//get file name of the file opened in the editor
          if(openfilename==null){ //it means file hasn't been saved in a system
              FileChooser fc=new FileChooser();
              File newfile=fc.showSaveDialog(null); //show save dialog box 
              if(newfile!=null){ //add '.txt' to the file if the file hasn't been given any extension
                  if(!newfile.getName().contains(".")){
                       String newFilePath=newfile.getAbsolutePath();
                       newFilePath+=".txt";
                       newfile.delete();
                       newfile=new File(newFilePath);
                  }
                  file=newfile;
                  openfilename=file.getAbsolutePath();
                  ed.filename=openfilename;
                  selTab.setText(file.getName());
              }
          }
          else{ //file is already available in system and save content to that file
              file=new File(openfilename);
          }
        try {  //save content of text area to the file
              try (PrintWriter pw = new PrintWriter(file)) {
                  pw.print(ed.getText());
              }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    private void saveAsFile() {
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab selTab=ssm.getSelectedItem();  
        SimpleEditor ed=getEditorForTextArea((TextArea)selTab.getContent());
        if(ed==null){
              return;
        }
        String openfilename;
        openfilename = ed.filename;
        File file=null;
        FileChooser fc=new FileChooser();
        File newfile=fc.showSaveDialog(null);
        if(newfile!=null){
            if(!newfile.getName().contains(".")){
                       String newFilePath=newfile.getAbsolutePath();
                       newFilePath+=".txt";
                       newfile.delete();
                       newfile=new File(newFilePath);
            }
            file=newfile;
            openfilename=file.getAbsolutePath();
            ed.filename=openfilename;
            selTab.setText(file.getName());
        }
        try {
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.print(ed.getText());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cut(){
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        str=ta.getSelectedText();
        int i=ta.getText().indexOf(str);
        ta.replaceText(i, i+str.length(), "");
    }
    private void copy(){
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        str=ta.getSelectedText();
    }
    private void paste(){
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        pos=ta.getCaretPosition();
        ta.insertText(pos, str);
    }
    private void delete(){
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        int len=ta.getSelectedText().length();
        pos=ta.getText().indexOf(ta.getSelectedText());
        ta.replaceText(pos,pos+len,"");
    }
    private void find(){
          findDialogBox();  
    }
    private void replace(){
          replaceDialogBox();
        
    }
    private void replaceAll(){
         replaceAllDialogBox();
    }
    private void selectAll(){ 
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        ta.selectAll();
    }
    private void findDialogBox(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("Find");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Find Next");
        Button b2=new Button("Cancel");
        Label l1=new Label("Find what?");
        TextField t1=new TextField();
        gd.add(l1, 2, 4);
        gd.add(t1, 3, 4);
        gd.add(b1, 7, 4);
        gd.add(b2, 7, 5);
        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Vector<Integer> store=new Vector();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        pos=-1;
        text=ta.getText();
        b1.setOnAction((ActionEvent e) -> {
              srch=t1.getText();
              pos=text.indexOf(srch, pos+1);
              if(pos!=-1)
                ta.selectRange(pos, pos+srch.length());
        });
        b2.setOnAction((ActionEvent e) -> {
              dialog.close();
        });
       
    }
    private void replaceDialogBox(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("Repalce");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Replace Next");
        Button b2=new Button("Cancel");
        b1.setId("b1");
        b2.setId("b2");
        Label l1=new Label("Replace what?");
        Label l2=new Label("Replace with:");
        TextField t1=new TextField();
        TextField t2=new TextField();
        gd.add(l1, 2, 4);
        gd.add(t1, 3, 4);
        gd.add(t2, 3, 5);
        gd.add(b1, 7, 4);
        gd.add(b2, 7, 5);
        gd.add(l2, 2, 5);
        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        pos=-1;
        b1.setOnAction((ActionEvent e) -> {
              srch=t1.getText();
              text=ta.getText();
              repl=t2.getText();
              pos=text.indexOf(srch, pos+1);
              ta.replaceText(pos, pos+srch.length(), repl);
        });
        b2.setOnAction((ActionEvent e) -> {
              dialog.close();
        });
    }
    private void replaceAllDialogBox(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("Repalce All");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Replace");
        Button b2=new Button("Cancel");
        b1.setId("b1");
        b2.setId("b2");
        Label l1=new Label("Replace what?");
        Label l2=new Label("Replace with:");
        TextField t1=new TextField();
        TextField t2=new TextField();
        gd.add(l1, 2, 4);
        gd.add(t1, 3, 4);
        gd.add(b1, 7, 4);
        gd.add(b2, 7, 5);
        gd.add(l2, 2, 5);
        gd.add(t2, 3, 5);
        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Vector<Integer> store=new Vector();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        repl="";
        b1.setOnAction((ActionEvent e) -> {
            text=ta.getText();
            srch=t1.getText();
            pos = -1;
            do{
                text=ta.getText();  
                repl=t2.getText();
                pos=text.indexOf(srch,pos+1);
                if(pos!=-1)
                    ta.replaceText(pos, pos+srch.length(), repl);
            }while(pos!=-1);
        });
        b2.setOnAction((ActionEvent e) -> {
              dialog.close();
        });
    }
    private void confirmDialogBox(){
        Stage dialog=new Stage();
        dialog.setWidth(250);
        dialog.setHeight(185);
        dialog.setTitle("Exit");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setAlignment(Pos.BASELINE_CENTER);
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Yes");
        Button b2=new Button("No");
        b1.setPrefHeight(15);
        b1.setPrefWidth(50);
        b2.setPrefHeight(15);
        b2.setPrefWidth(50);
        HBox hb=new HBox();
        hb.setSpacing(50);
        hb.getChildren().addAll(b1,b2);
        gd.add(hb, 2, 6);
        b1.setId("b1");
        b2.setId("b2");
        Label l1=new Label("Are you sure you want to quit?"); 
        gd.add(l1, 2, 4);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        b1.setOnAction((ActionEvent e) -> {
             stage.close();
             dialog.close();
        });
        b2.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
    }
    private void gotoSymbol(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("Goto");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Next");
        Button b2=new Button("Done");
        Label l1=new Label("Enter Symbol:");
        TextField t1=new TextField();
        gd.add(l1, 2, 4);
        gd.add(t1, 3, 4);
        gd.add(b1, 7, 4);
        gd.add(b2, 7, 5);
        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        pos=-1;
        text=ta.getText();
        b1.setOnAction((ActionEvent e) -> {
              srch=t1.getText();
              pos=text.indexOf(srch, pos+1);
              if(pos!=-1)
                ta.positionCaret(pos);
        });
        b2.setOnAction((ActionEvent e) -> {
              dialog.close();
        });
    }
    private void gotoWord(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("Goto");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Button b1=new Button("Next");
        Button b2=new Button("Done");
        Label l1=new Label("Enter Word:");
        TextField t1=new TextField();
        gd.add(l1, 2, 4);
        gd.add(t1, 3, 4);
        gd.add(b1, 7, 4);
        gd.add(b2, 7, 5);
        b1.setMaxWidth(Double.MAX_VALUE);
        b2.setMaxWidth(Double.MAX_VALUE);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();
        SingleSelectionModel<Tab> ssm=tp.getSelectionModel();
        Tab tab=ssm.getSelectedItem();
        TextArea ta=(TextArea)tab.getContent();
        pos=-1;
        text=ta.getText();
        b1.setOnAction((ActionEvent e) -> {
              srch=t1.getText();
              pos=text.indexOf(srch, pos+1);
              if(pos!=-1)
                ta.positionCaret(pos);
        });
        b2.setOnAction((ActionEvent e) -> {
              dialog.close();
        });
    }
    private void about(){
        Stage dialog=new Stage();
        dialog.setWidth(400);
        dialog.setHeight(180);
        dialog.setTitle("About");
        dialog.initModality(Modality.WINDOW_MODAL);
        GridPane gd=new GridPane();
        gd.setId("dialog");
        gd.setHgap(10);
        gd.setVgap(10);
        Label l1=new Label("Project Name              :     TextEditor+");
        Label l2=new Label("Manufactured by        :     Akhilesh Yadav");
        Label l3=new Label("Tools & technology    :     JavaFX");
        TextField t1=new TextField();
        gd.add(l1, 8, 3);
        gd.add(l2, 8, 4);
        gd.add(l3, 8, 5);
        Scene sc=new Scene(gd);
        sc.getStylesheets().add (TextEditor.class.getResource("StyleSheet.css").toExternalForm());
        dialog.setResizable(false);
        dialog.setScene(sc);
        dialog.show();  
    }
}
