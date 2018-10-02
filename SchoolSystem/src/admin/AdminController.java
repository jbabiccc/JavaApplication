package admin;


import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;

    @FXML
    private TableView<StudentData> studenttable;

    @FXML
    private TableColumn<StudentData,String> idcolumn;

    @FXML
    private TableColumn <StudentData,String> firstnamecolumn;

    @FXML
    private TableColumn <StudentData,String> lastnamecolumn;

    @FXML
    private TableColumn <StudentData,String> emailcolumn;

    @FXML
    private TableColumn <StudentData,String> dobcolumn;

    private dbConnection db;
    private ObservableList<StudentData> data;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        db = new dbConnection();
    }

    @FXML
    private void loadStudentData(ActionEvent event) throws SQLException
    {
        try{

            Connection conn  = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students");

            while(rs.next())
            {
                this.data.add(new StudentData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)) );
            }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("dob"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }
    @FXML
    public void addStudent(ActionEvent event) throws SQLException
    {
        String SQLADD = "INSERT INTO students(id,fName,lName,email,dob) VALUES (?,?,?,?,?)";

        try{
           Connection conn = dbConnection.getConnection();
           PreparedStatement pr = conn.prepareStatement(SQLADD);
            pr.setString(1, this.id.getText());
            pr.setString(1, this.firstName.getText());
            pr.setString(1, this.lastName.getText());
            pr.setString(1, this.email.getText());
            pr.setString(1, this.dob.getEditor().getText());

            pr.execute();
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
    }
        @FXML

    private void Clear(ActionEvent event)
        {
            this.id.setText("");
            this.firstName.setText("");
            this.lastName.setText("");
            this.email.setText("");
            this.dob.setValue(null);
        }

}
