package sample;

import Objetos.Producto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FinalizarCompraController implements Initializable {

    @FXML
    private JFXTreeTableView<Proveedor> TableProveedor;

    @FXML
    private TreeTableColumn<Proveedor, String> IDProveedor;

    @FXML
    private TreeTableColumn<Proveedor, String> NombreProveedor;

    @FXML
    private JFXTextField TxtProveedor;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXButton FinalizarCompra;

    @FXML
    private JFXTextField TxtPago;

    @FXML
    private Label lblCambio;

    public static final ObservableList<Proveedor> LProveedor = FXCollections.observableArrayList();
    public static Double Total;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.lblTotal.setText(String.valueOf(Total));
        CargarTabla();
        CargarProveedor();
    }

    public void FinalizarCompra(MouseEvent mouseEvent) {
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement = con.prepareStatement("insert into Compra(idProveedor, Total_Compra, Fecha) values (?, ?, curdate());");
            statement.setString(1, TxtProveedor.getText());
            statement.setString(2, lblTotal.getText());
            statement.executeQuery();
            for(Producto objeto : InventarioController.LProductoCompra){
                statement = con.prepareStatement("insert into Detalle_Compra(idProducto, Cantidad, idCompra, Precio_Compra) values (?, ?, ?, ?)");
                statement.setString(1, objeto.GetID());
                statement.setString(2, objeto.GetStock());
                statement.setString(3, GetIdCompra());
                statement.setString(4, objeto.GetCompra());
                statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String GetIdCompra() throws SQLException {
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select max(idCompra) from Compra;");
            while(rs.next()){
                return rs.getString(1);
            }
            return null;
    }

    public void CargarCambio(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtPago.getText())){
                this.lblCambio.setText(String.valueOf(Double.parseDouble(TxtPago.getText()) - Double.parseDouble(lblTotal.getText())));
            }
        }
    }

    private void CargarTabla(){
        IDProveedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().sGetId());
        NombreProveedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<Proveedor, String> param) -> param.getValue().getValue().sGetNombre());
        final TreeItem<Proveedor> root = new RecursiveTreeItem<>(LProveedor, RecursiveTreeObject::getChildren);
        this.TableProveedor.setRoot(root);
        this.TableProveedor.setShowRoot(false);
        this.TxtProveedor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableProveedor.setPredicate((TreeItem<Proveedor> t) -> {
                Boolean flag = t.getValue().sGetId().getValue().contains(newValue);
                return flag;
            });
        });
    }

    private void CargarProveedor(){
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("Select idProveedor, Nombre from Proveedor;");
            while(rs.next()){
                LProveedor.add(new Proveedor(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public class Proveedor extends RecursiveTreeObject<Proveedor>{
        StringProperty id, Nombre;

        public Proveedor(String id, String Nombre){
            this.id = new SimpleStringProperty(id);
            this.Nombre = new SimpleStringProperty(Nombre);
        }

        public StringProperty sGetId(){ return id; }
        public StringProperty sGetNombre(){ return Nombre; }
    }

}
