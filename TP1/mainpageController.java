package TP1;

import java.sql.Connection;
import java.util.Map;

import TP1.Item.status;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class mainpageController {

    @FXML
    private TextField IMEI;

    @FXML
    private TableColumn<Phone, String> IMEI_phone;

    @FXML
    private TextField MAC;

    @FXML
    private TableColumn<Computer,String> MAC_computer;

    @FXML
    private TableColumn<Phone, String> MAC_phone;

    @FXML
    private Tab checkTab;

    @FXML
    private TableView<Computer> computer_table;

    @FXML
    private TextArea desciption;

    @FXML
    private TableColumn<Computer,String> description_computer;

    @FXML
    private TableColumn<Item,String> description_other;

    @FXML
    private TableColumn<Item,String> serial_other;
    @FXML
    private TableColumn<Phone, String> description_phone;

    @FXML
    private ChoiceBox<String> deviceChoice;

    @FXML
    private VBox extraVBox;

    @FXML
    private HBox imeiHBox;

    @FXML
    private TextField lastSeenLocation;

    @FXML
    private TableColumn<Computer,String> lastSeen_computer;

    @FXML
    private TableColumn<Item,String> lastSeen_other;

    @FXML
    private TableColumn<Phone, String> lastSeen_phone;

    

    @FXML
    private TableView<Phone> phones_table;

    @FXML
    private Tab registerTab;

    @FXML
    private TableColumn<Computer,String> reported_computer;

    @FXML
    private TableColumn<Item,String> reported_other;

    @FXML
    private TableColumn<Phone, String> reported_phone;

    @FXML
    private TextField serialNumber;

    @FXML
    private TableView<Item>    other_table;

    @FXML
    private TableColumn<Computer,String> serial_computer;

    @FXML
    private TableColumn<Phone, String> serial_phone;

    @FXML
    private ChoiceBox<String> statusChoice;

    @FXML
    private TableColumn<Computer,String> status_computer;

    @FXML
    private TableColumn<Item,String> status_other;

    @FXML
    private TableColumn<Phone, String> status_phone;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab viewTab;

    OwnerValidation owner;

    Connection connection;
    @FXML
    void openCheckTab(ActionEvent event) {
        tabPane.getSelectionModel().select(checkTab);
    }

    @FXML
    void openRegisterTab(ActionEvent event) {
        tabPane.getSelectionModel().select(registerTab);
    }

    @FXML
    void openViewTab(ActionEvent event) {
        tabPane.getSelectionModel().select(viewTab);
    }

    Phone selectedPhone;
    Computer selectedComputer;
    Item selectedItem;
  


    @FXML
    void registerDevice(ActionEvent event) {
        boolean stolen=(statusChoice.getValue()=="stolen")?true:false;
        status Status=Item.getStatisEnumFromString(statusChoice.getValue());
        Item item =new Item(serialNumber.getText(), Status,stolen , desciption.getText(), lastSeenLocation.getText());
        if(deviceChoice.getValue().equals("Phone/Tablets")){
            Phone phone=new Phone(item, IMEI.getText(), MAC.getText());
            phone.save(connection, owner.getDBid());
        }
        else if(deviceChoice.getValue().equals("Computers")){
            Computer computer=new Computer(item, MAC.getText());
            computer.save(connection, owner.getDBid());
        }
        else if(deviceChoice.getValue().equals("Others")){
            item.save(connection, owner.getDBid());
        }
    
        System.out.println(item);
        

    }

    public void initializeParameters(Connection connection,OwnerValidation owner){
        this.connection=connection;
        this.owner=owner;
        System.out.print(owner.getDBid());
        for(status Status:status.values()){
            statusChoice.getItems().add(Status.name());
        }
        statusChoice.setValue(status.recoverd.name());

        deviceChoice.getItems().addAll("Phone/Tablets","Computers","Others");
        deviceChoice.setValue("Phone/Tablets");

        deviceChoice.setOnAction(e->{
            extraVBox.setVisible(true);
            imeiHBox.setVisible(true);
            if(deviceChoice.getValue().equals("Computers")){
                imeiHBox.setVisible(false);
            }
            else if(deviceChoice.getValue().equals("Others")){
                extraVBox.setVisible(false);
            }
        });
        //initialize table

        initialize_tables(connection);
        //setup event listeners for tables
        phones_table.setOnMouseClicked(e->{
            selectedPhone=phones_table.getSelectionModel().getSelectedItem();
        });
        computer_table.setOnMouseClicked(e->{
            selectedComputer=computer_table.getSelectionModel().getSelectedItem();
        });
        other_table.setOnMouseClicked(e->{
            selectedItem=other_table.getSelectionModel().getSelectedItem();
        });

       

    }

    private    void initialize_tables(Connection connection){
        initialize_tPhoneTable(connection);
        initialize_OtherTable(connection);
        initialize_ComputerTable(connection);
    }
    Map<Phone,Integer> phoneMap;
    Map<Computer,Integer> computerMap;
    Map<Item,Integer> othersMap;
    private void initialize_tPhoneTable(Connection connection){
        //get phones from database
         phoneMap=databaseControl.getPhones(connection);
        //link phone class with table
        serial_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getSerialNumber()));
        status_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getStatus().name()));
        reported_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().isReportedStolen()?"yes":"no"));
        description_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getDescription()));
        lastSeen_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getLastSeenLocation()));
        IMEI_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getIMEI()));
        MAC_phone.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getMAC()));
        //create fxarralyi
        ObservableList<Phone> phoneList=FXCollections.observableArrayList(phoneMap.keySet());
        phones_table.setItems(phoneList);

    }
    private void initialize_ComputerTable(Connection connection){
        computerMap=databaseControl.getComputers(connection);
        serial_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getSerialNumber()));
        status_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getStatus().name()));
        reported_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().isReportedStolen()?"yes":"no"));
        description_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getDescription()));
        lastSeen_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getLastSeenLocation()));
  
        MAC_computer.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getMAC()));
        ObservableList<Computer> computerList=FXCollections.observableArrayList(computerMap.keySet());
        computer_table.setItems(computerList);

    }
    private void initialize_OtherTable(Connection connection){
         othersMap=databaseControl.getOthers(connection);
        serial_other.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getSerialNumber()));
        status_other.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getStatus().name()));
        reported_other.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().isReportedStolen()?"yes":"no"));
        description_other.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getDescription()));
        lastSeen_other.setCellValueFactory(celldata->new SimpleObjectProperty<>(celldata.getValue().getLastSeenLocation()));
        ObservableList<Item> itemList=FXCollections.observableArrayList(othersMap.keySet());
        other_table.setItems(itemList);
       
    }

    @FXML
    void Computer_reportStolen(ActionEvent event) {
        if(selectedComputer!=null){
            int computer_id=computerMap.get(selectedComputer);
            System.out.println(computer_id);
        }
    }

    @FXML
    void Other_reportStolen(ActionEvent event) {
        if(selectedItem!=null){
            int id=othersMap.get(selectedItem);
            System.out.println(id);
        }
    }

    @FXML
    void Phone_reportStolen(ActionEvent event) {
        if(selectedPhone!=null){
            int phoneId=phoneMap.get(selectedPhone);
            databaseControl.setPhoneStolen(phoneId, status.stolen.name(), connection);
            initialize_tables(connection);
        }
    }

}