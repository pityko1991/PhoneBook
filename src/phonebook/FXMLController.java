/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebook;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author pityk
 */
public class FXMLController implements Initializable {
    //fxml elemek változóinak deklarálása
    @FXML
    TableView table;
    @FXML
    TextField inputLastname;
    @FXML
    TextField inputFirstname;
    @FXML
    TextField inputEmail;
    @FXML
    TextField inputNumber;
    @FXML
    Button addNewContactButton;
    @FXML
    StackPane menuPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane exportPane;
    @FXML
    TextField inputExportName;
    @FXML
    Button exportButton;
    @FXML
    AnchorPane anchor;
    @FXML
    SplitPane mainSplit;
    
            
    
    DB db = new DB();
  
    
    //final osztályváltozók létrehozása a menünek
    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";

    private final ObservableList<Person> data = FXCollections.observableArrayList();
         
    
    
    @FXML
    // az a metódus amely az új kontaktok felvitelét kezeli 
    private void addContact(ActionEvent event){
        String email = inputEmail.getText();
        if(email.length()>3 && email.contains("@")){
            Person newPerson = new Person(inputLastname.getText(), inputFirstname.getText(), email, inputNumber.getText());
             data.add(newPerson);
             db.addContact(newPerson);
              inputLastname.clear();
              inputFirstname.clear();
              inputEmail.clear();
              inputNumber.clear();
        }else{
            alert("Adj meg egy valódi email címet!");
        }
      }
    
    @FXML
    private void exportList(ActionEvent event){
        //a felhasználó által beírt fájlnevet elmentjük egy változóba
        String fileName = inputExportName.getText();
        //kicseréljük a felhasználó által beírt space karaktereket semmire
        fileName = fileName.replaceAll("\\s+", "");
        //megvizsgáljuk hogy a felhasználó beírt e valamit, és csak is akkor történhet meg a pdf fájl létrehozása, ha ez igaz
        if(fileName!=null && !fileName.equals("")){
             PdfGeneration pdfCreator = new PdfGeneration();   
             pdfCreator.pdfGeneration(fileName, data);
        }else{
            alert("Adj meg egy fájlnevet!");
        } 
      }
    
    public void setTableData(){
        
        //Adattábla létrehozása
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        //beállítom a minimum szélességet
        lastNameCol.setMinWidth(100);
        //beállítjuk hogy milyen tulajdonságú legyen a táblához tartozó cella (textfield)
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        // a Person class-bol Stringként fogunk megjeleniteni egy "lastName" változót
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        //egy esemenyékezelővel lehetővé tesszük hogy az adott cella tartalmát módosíthassuk
        lastNameCol.setOnEditCommit( //változtatás elküldése
           new EventHandler<TableColumn.CellEditEvent<Person, String>>(){
               @Override
               public void handle(TableColumn.CellEditEvent<Person, String> t){
                   Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                   actualPerson.setLastName(t.getNewValue());
                   db.updateContact(actualPerson);
               }           
           } 
        );
        
        TableColumn firstNameCol = new TableColumn("Keresztnév");        
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        
        firstNameCol.setOnEditCommit(
           new EventHandler<TableColumn.CellEditEvent<Person, String>>(){
               @Override
               public void handle(TableColumn.CellEditEvent<Person, String> t){
                   Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                   actualPerson.setFirstName(t.getNewValue());
                   db.updateContact(actualPerson);
               }           
           } 
        );
        
        TableColumn emailCol = new TableColumn("Email cím");        
        emailCol.setMinWidth(150);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        
        emailCol.setOnEditCommit(
           new EventHandler<TableColumn.CellEditEvent<Person, String>>(){
               @Override
               public void handle(TableColumn.CellEditEvent<Person, String> t){
                   Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                   actualPerson.setEmail(t.getNewValue());
                   db.updateContact(actualPerson);
               }           
           } 
        );
        
        TableColumn numberCol = new TableColumn("Telefonszám");        
        numberCol.setMinWidth(150);
        numberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        numberCol.setCellValueFactory(new PropertyValueFactory<Person, String>("number"));
        
        numberCol.setOnEditCommit(
           new EventHandler<TableColumn.CellEditEvent<Person, String>>(){
               @Override
               public void handle(TableColumn.CellEditEvent<Person, String> t){
                   Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                   actualPerson.setNumber(t.getNewValue());
                   db.updateContact(actualPerson);
               }           
           } 
        );
        
        TableColumn removeCol = new TableColumn("Törlés");
        emailCol.setMinWidth(110);
        Callback<TableColumn<Person, String>,TableCell<Person, String>> cellFactory = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell call(final TableColumn<Person, String> param) {
                
                final TableCell<Person, String> cell = new TableCell<Person, String>(){
                    
                   final Button btn = new Button("Törlés");
                   
                   @Override
                   public void updateItem (String item, boolean empty){
                       super.updateItem(item, empty);
                       if(empty){
                           setGraphic(null);
                           setText(null);
                       }else{
                           btn.setOnAction((ActionEvent event) ->{
                               Person person = getTableView().getItems().get(getIndex());
                               data.remove(person);
                               db.removeContact(person);
                           
                           });
                           
                           setGraphic(btn);
                           setText(null);
                       }
                   }
                };
                return cell;
               
            }
        };
        removeCol.setCellFactory(cellFactory);
        
        table.getColumns().addAll(lastNameCol,firstNameCol,emailCol,numberCol,removeCol);
        
        data.addAll(db.getAllContacts());
        
        table.setItems(data);       
    }
    
    private void setMenuData() {
       //referencia ág létrehozása 
       TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
       //fa létrehozása, majd átadjuk neki a referenciát
       TreeView<String> treeView = new TreeView<>(treeItemRoot1);
       treeView.setShowRoot(false);
       
       //létrehozunk két mellék ágat
       TreeItem<String> nodeItemA = new TreeItem<>(MENU_CONTACTS);
       TreeItem<String> nodeItemB = new TreeItem<>(MENU_EXIT);
       
       //kép hozzáadása az alágakhoz
       Node contactNode = new ImageView(new Image(getClass().getResourceAsStream("/contact.png")));
       Node exportNode = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
       //további alágak létrehozása
       TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_LIST,contactNode);
       TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_EXPORT,exportNode);
       
       //a menü ezzel már indítás után nyitva lesz
       nodeItemA.setExpanded(true);
       
       //az 'nodeItemA' ágnak átadjuk a két al ágát
       nodeItemA.getChildren().addAll(nodeItemA1,nodeItemA2);
       
       //a referencia ágnak átadjuk a mellékágakat
       treeItemRoot1.getChildren().addAll(nodeItemA,nodeItemB);
       
       //a menüre ráhelyezzük a komplett fa szerkezetet
       menuPane.getChildren().add(treeView);
       
       //létrehozunk egy eseménykezelőt amely befolyással van a lenyíló menüre, és annak almenüire 
       //beállítjuk hogy egyszeri kattintással tudjon lenyílni a kontakt menünk 
       treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
           public void changed(ObservableValue observable, Object oldValue, Object newValue){
               TreeItem<String> selectedItem = (TreeItem<String>) newValue;
               String selectedMenu;
               selectedMenu = selectedItem.getValue();
               System.out.println("választott menü: " + selectedMenu);
               
               if(null != selectedMenu){
                   switch(selectedMenu){
                       case MENU_CONTACTS:
                               selectedItem.setExpanded(true);
                           break;
                       case MENU_LIST:
                           contactPane.setVisible(true);
                           exportPane.setVisible(false);
                           break;
                       case MENU_EXPORT:
                           contactPane.setVisible(false);
                           exportPane.setVisible(true);
                           break;
                       case MENU_EXIT:
                           System.exit(0);
                           break;
                   }
               }
           }
       });
    }
    
    private void alert(String text){
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { // indulásnál minden lefut ami a törzsében van
        setTableData();
        setMenuData();
        
        
            
    }

   

    
}
    
