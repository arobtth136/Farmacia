package sample;

import Objetos.Conexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private StackPane StackPane;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField TxtUser;

    @FXML
    private JFXPasswordField TxtPass;

    @FXML
    private JFXButton BtnSalir;

    @FXML
    private JFXButton BtnEntrar;

    @FXML
    private Label LblTitle;

    @FXML
    private JFXButton BtnCrear;

    public void Ingresar(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println(this.StackPane.getWidth());
        System.out.println(this.StackPane.getHeight());
        try{
            Connection con = Conexion.getConnection();
            PreparedStatement statement;
            statement = con.prepareStatement("select idEmpleado from Empleado where Usuario = ? and Password = sha1(?)");
            statement.setString(1, this.TxtUser.getText());
            statement.setString(2, this.TxtPass.getText());
            ResultSet rs = statement.executeQuery();
            int Resultado = 0;
            while (rs.next()){
                //Cosas je
                Resultado = 1;
            }
            if(Resultado == 1){
                //Entrar a el main
                Stage Main = new Stage();
                Parent Mroot = FXMLLoader.load(getClass().getResource("Principal.fxml"));
                Scene scene = new Scene(Mroot);
                Main.setScene(scene);
                Main.show();
            }
        } catch (SQLException | IOException ex){
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
