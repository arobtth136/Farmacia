use farmacia;

/* Consulta para el login */
select Nombre from Empleado where Usuario = ? and Password = sha1(?);

/* Caja registradora */
	/* Información del producto*/
    select Descripcion, Precio_Venta, Receta from Producto where Cod_Barras = ?;
    /* Si no tiene codigo de barras, por Codigo */
    select Descripcion, Precio, Venta, Receta from Producto where idProducto = ?;
    /* Insertar una venta cuando se complete la compra */
    insert into Venta (Fecha, Total) values (curdate(), ?);
		/* Insertar el detalle de la venta cuando se haya insertado la venta */
		insert into Detalle_Venta (idVenta, idProducto, idEmpleado, Cantidad, idCliente) values (?, ?, ?, ?, ?);
	/* Actualizar puntos del cliente cuando complete una compra (si es que está registrado) */
    update Cliente set Puntos = ? where idCliente = ?;

/* Inventario */
	/* Infromación del inventario */
    select Descripcion, Precio_Venta, Cantidad, Categoria from Producto;
    /* Mostrar información para modificar el producto */
    select * from Producto where idProducto = ?;
        /* Eliminar el producto */
        delete from Producto where idProducto = ?;
    /* Insertar un nuevo producto */
    insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria) 
        values (?, ?, ?, ?, ?, ?, ?, ?, ?);
    
/* Extras */
    /* Ingresar nuevo usuario */
    insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values (?, ?, ?, sha1(?), ?);
    /* Mostrar Empleados */
    Select * from Empleado;
        /* Borrar un empleado */
        delete from Empleado where idEmpleado = ?;
    /* Ingresar nuevo proveedor */
    insert into Proveedor (Nombre, Telefono, Correo, Direccion) values (?, ?, ?, ?);
        /* Borrar proveedor */
        delete from Proveedor where idProveedor = ?;
    /* Mostrar proveedores */
    select * from Proveedor;
    /* Reporte de ventas */
    select Venta.idVenta, Venta.Fecha, (select Nombre from Empleado where idEmpleado = Detalle_Venta.idEmpleado) as 'Empleado', Venta.Total, 
    (select Nombre from Cliente where Cliente.idCliente = Detalle_Venta = idCliente)
    from Venta inner join Detalle_Venta on Venta.idVenta = Detalle_Venta.idVenta
    where Venta.fecha between ? and ?;
        /* Mostrar productos del reporte de ventas */
        select (select Descripcion from Producto where Producto.idProducto = Detalle_Venta.idProducto) as 'Descripcion', Detalle_Venta.Cantidad
        from Detalle_Venta
        where idVenta = ?;
    /* Insertar nueva compra de producto */
    insert into Compra (id_proveedor, Total_Compra, Fecha) values (?, ?, ?);
        /* Insertar detalle de la compra */
        insert into Detalle_Compra (idProducto, Cantidad, idCompra, Precio_Compra) values (?, ?, ?, ?);
    /* Ver promociones */
    select Descripcion, Activa
    from promocion;
        /* Mostrar detalles de la promocion */
        select Producto.Descripcion
        from Producto 
        inner join Detalle_Promocion on Detalle_Promocion.idProducto = Producto.idProducto;
        /* Crear una nueva Promoción */
        insert into Promocion (Descripcion, Activa) values (?, ?);
            /* Insertar detalles de la promoción */
            insert into Detalle_Promocion (idPromocion, idProducto) values (?, ?);
        /* Borrar alguna promoción */
        delete from Promocion where idPromocion = ?;
        delete from Detalle_Promocion where idPromocion = ?;
        
/* Consultas */
	/* El producto que más se vende */
	select Detalle_Venta.idProducto As 'Código del producto', 
	Producto.Descripcion As 'Descripción', 
	concat('$', Producto.Precio_Compra) As 'Precio de compra', 
	concat('$', Producto.Precio_Venta) As 'Precio de venta', 
	Producto.Cantidad 'En inventario', 
	count(Detalle_Venta.idProducto) As 'Ventas_Totales'
	from Detalle_Venta inner join Producto 
	on Detalle_Venta.idProducto = Producto.idProducto
	group by Detalle_Venta.idProducto
	order by Ventas_Totales desc
	Limit 1;
    /* Reporte de promociones vigentes */
    select Descripcion, Activa
    from Promocion
    where Activa = 1;
    /* Listado de productos por categoría */
    select Producto.idProducto As 'Código del producto', 
    Producto.Descripcion As 'Descripción', 
    concat('$', Producto.Precio_Compra) As 'Precio de compra',
    concat('$', Producto.Precio_Venta) As 'Precio de venta',
    Producto.Cantidad As 'En inventario',
    Categorias.Descripcion As 'Categoria'
    from Producto inner join Categorias
    on Producto.idCategoria = Categorias.idCategoria
    order by Categorias.Descripcion desc;
    /* Reporte de proovedores por producto */
    select Producto.idProducto As 'Código del producto', 
    Producto.Descripcion As 'Descripción', 
    concat('$', Producto.Precio_Compra) As 'Precio de compra',
    concat('$', Producto.Precio_Venta) As 'Precio de venta',
    Producto.Cantidad As 'En inventario',
    Proveedor.Nombre As 'Proveedor',
    Proveedor.Telefono As 'Teléfono'
    from Producto inner join Proveedor
    on Producto.Proveedor = Proveedor.idProveedor
    order by Proveedor.Nombre desc;
    /* El cliente que más puntos tiene */
	select Nombre, Puntos 
    from Cliente
    order by Puntos desc limit 1;
    /*Listado de compras en un mes */
	SELECT 
		Proveedor.nombre, idCompra, Total_Compra, fecha
	FROM
		compra
			INNER JOIN
		proveedor ON compra.idProveedor = proveedor.idProveedor
	WHERE
		fecha BETWEEN '2019-09-01' AND '2019-09-30'; 
    
    /*Producto con mayor utilidad*/
	SELECT 
		descripcion AS Producto,
		Precio_venta - Precio_Compra AS Utilidad
	FROM
		producto
	ORDER BY utilidad DESC
	LIMIT 1;

   /*Empleado con mas ventas*/ 
	SELECT 
		empleado.Nombre, COUNT(venta.idEmpleado) AS 'N° de ventas'
	FROM
		venta
			INNER JOIN
		empleado ON venta.idEmpleado = empleado.idEmpleado
	GROUP BY venta.idEmpleado
	ORDER BY 'N° de ventas' DESC limit 1;

	/*Reporte de ventas en un mes*/
	SELECT 
		venta.idVenta,
		fecha,
		total AS 'Total de la venta',
		producto.Descripcion
	FROM
		venta
			INNER JOIN
		detalle_venta ON venta.idVenta = detalle_venta.idVenta
			INNER JOIN
		producto ON detalle_venta.idProducto = producto.idProducto
	WHERE
		fecha BETWEEN '2019-08-01' AND '2019-08-30'; 

	/*Cantidad en inventario de cada producto */
	SELECT 
		descripcion, cantidad AS 'En inventario'
	FROM
		producto;

/* Triggers */
	/* Actualizar los puntos */
    drop trigger if exists Actualizar_Puntos;
    Create Trigger Actualizar_Puntos After insert on Venta
    for each row
        update Cliente set Cliente.Puntos = Puntos + ((3 * new.Total)/100)
        where new.idCliente = Cliente.idCliente;
        
    
