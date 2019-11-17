package sample;

import Objetos.Persona;
import com.jfoenix.controls.*;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {



    @FXML
    private JFXDatePicker FechaDe;

    @FXML
    private JFXDatePicker FechaHasta;

    @FXML
    private JFXTreeTableView<ObjetoReporte> TableReporte;

    @FXML
    private Label Total;

    @FXML
    private JFXTextField TxtFiltro;

    @FXML
    private JFXButton BtnBuscar;

    @FXML
    private Label VentasTotales;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Cliente;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> IDVenta;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> TotalVenta;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Vendedor;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Fecha;

    @FXML
    private JFXTreeTableView<ObjetoDetalleReporte> TableDetalleVenta;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> Detalle_IDVenta;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetalleProducto;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetallePrecio;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetalleCantidad;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetallePromocion;

    @FXML
    private JFXDatePicker FechaDeC;

    @FXML
    private JFXDatePicker FechaHastaC;

    private static final ObservableList<Persona> LVendedor = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoReporte> LReporte = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoDetalleReporte> LDReporte = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InicializarTablas();
    }

    void InicializarTablas() {
        //Tabla Reporte
        IDVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetIdVenta());
        Vendedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetVendedor());
        Fecha.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetFecha());
        TotalVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetTotal());
        Cliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetCliente());
        final TreeItem<ObjetoReporte> root = new RecursiveTreeItem<>(LReporte, RecursiveTreeObject::getChildren);
        this.TableReporte.setRoot(root);
        this.TableReporte.setShowRoot(false);
        //Filtro
        this.TxtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableReporte.setPredicate((TreeItem<ObjetoReporte> t) -> {
                Boolean flag = t.getValue().sGetIdVenta().getValue().contains(newValue);
                return flag;
            });
        });
        //Tabla Detalle de reporte
        Detalle_IDVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetIdVenta());
        DetalleProducto.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetDescripcion());
        DetallePrecio.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetPrecion());
        DetalleCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetCantidad());
        DetallePromocion.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetPromocion());
        final TreeItem<ObjetoDetalleReporte> root2 = new RecursiveTreeItem<>(LDReporte, RecursiveTreeObject::getChildren);
        this.TableDetalleVenta.setRoot(root2);
        this.TableDetalleVenta.setShowRoot(false);
        //Filtro
        this.TxtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableDetalleVenta.setPredicate((TreeItem<ObjetoDetalleReporte> t) -> {
                Boolean flag = t.getValue().GetIdVenta().getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void CargarReporteVentas() {
        LReporte.clear();
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement = con.prepareStatement("select count(*) from Venta where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                this.VentasTotales.setText(rs.getString(1));
            }
            statement = con.prepareStatement("select sum(Total) from Venta where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                this.Total.setText(rs.getString(1));
            }
            statement = con.prepareStatement("select idVenta, Fecha, Total, Empleado.Nombre, Cliente.Nombre\n" +
                    "from Venta inner join Empleado on Venta.idEmpleado = Empleado.idEmpleado\n" +
                    "inner join Cliente on Venta.idCliente = Cliente.idCliente\n" +
                    "where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                LReporte.add(new ObjetoReporte(rs.getString(1), rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(5)));
            }
            CargarDetalleVenta();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void CargarDetalleVenta(){
        LDReporte.clear();
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement = con.prepareStatement("select Detalle_Venta.idVenta, Producto.Descripcion, Detalle_Venta.Precio_Venta, Detalle_Venta.Cantidad, Promocion.Descripcion\n" +
                    "from Detalle_Venta inner join Producto on Producto.idProducto = Detalle_Venta.idProducto\n" +
                    "inner join Promocion on Promocion.idPromocion = Detalle_Venta.Promocion\n" +
                    "where idVenta between ? and ?;");
            statement.setInt(1, Integer.parseInt(LReporte.get(0).GetIdVenta()));
            statement.setInt(2, Integer.parseInt(LReporte.get(LReporte.size() - 1).GetIdVenta()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LDReporte.add(new ObjetoDetalleReporte(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public class ObjetoReporte extends RecursiveTreeObject<ObjetoReporte> {
        StringProperty IdVenta, Vendedor, Fecha, Total, Cliente;

        public ObjetoReporte(String IdVenta, String Vendedor, String Fecha, String Total, String Cliente) {
            this.IdVenta = new SimpleStringProperty(IdVenta);
            this.Vendedor = new SimpleStringProperty(Vendedor);
            this.Fecha = new SimpleStringProperty(Fecha);
            this.Total = new SimpleStringProperty(Total);
            this.Cliente = new SimpleStringProperty(Cliente);
        }

        public String GetIdVenta() { return IdVenta.get(); }
        public String GetVendedor() { return Vendedor.get(); }
        public String GetFecha() { return Fecha.get(); }
        public String GetTotal() { return Total.get(); }
        public String GetCliente() { return Cliente.get(); }
        public StringProperty sGetIdVenta() {
            return IdVenta;
        }
        public StringProperty sGetVendedor() {
            return Vendedor;
        }
        public StringProperty sGetFecha() {
            return Fecha;
        }
        public StringProperty sGetTotal() {
            return Total;
        }
        public StringProperty sGetCliente() {
            return Cliente;
        }
    }

    public class ObjetoDetalleReporte extends RecursiveTreeObject<ObjetoDetalleReporte> {
        StringProperty IdVenta, Descripcion, Precio, Cantidad, Promocion;

        public ObjetoDetalleReporte(String IdVenta, String Descripcion, String Precio, String Cantidad, String Promocion){
            this.IdVenta = new SimpleStringProperty(IdVenta);
            this.Descripcion = new SimpleStringProperty(Descripcion);
            this.Precio = new SimpleStringProperty(Precio);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Promocion = new SimpleStringProperty(Promocion);
        }

        public StringProperty GetIdVenta(){ return IdVenta; }
        public StringProperty GetDescripcion(){ return Descripcion; }
        public StringProperty GetPrecion(){ return Precio; }
        public StringProperty GetCantidad(){ return Cantidad; }
        public StringProperty GetPromocion(){ return Promocion; }
    }
}
