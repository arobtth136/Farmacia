use Farmacia;

/* Insertar en Farmacia.Empleado */
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Gerardo Galván Chavez', '2299025028', 'GerardoGalvan', sha1('Gerardo123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Rafael Antonio Gonzalez Zamora', '2295242553', 'RafaelZamora', sha1('Rafael123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Karla Lizbeth Valdes Morales', '2295061936', 'KarVm', sha1('Karla123'), 1);
insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values ('Alejandro Vega Hernandez', '5540907400', 'AlexVega', sha1('Vega123'), 1);

/* Insertar en Farmacia.Proveedores */
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Pepsico', '229 112 7509', 'evolucion_adm@hotmail.com', 'Carretera Veracruz Medellin KM 2.3');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Laboratorios PISA', '229 921 8654', '', 'Ejército Mexicano, Adalberto Tejada, Veracruz');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('AMSA Laboratorios', '55 5998 2100', '', 'Flores 56, Amp Candelaria, 04380 Ciudad de México');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ('Novag Infancia', '55 5666 4120', '', 'Calz. de Tlalpan 3417, Sta. Úrsula Coapa, 04650 Ciudad de México');
insert into Proveedor (Nombre, Telefono, Correo, Direccion) values ("LABORATORIOS COLUMBIA, S.A. DE C.V.","52 55 5726 5584","","Calzada del Hueso No. 160 Col. Ejidos de Santa Ursula Coapa 04850 México, D.F.");

/* Insertar en Farmacia.Categorias */
insert into Categorias (idCategoria, Descripcion) values (1, 'Refrescos / Bebidas');
insert into Categorias (idCategoria, Descripcion) values (2, 'Analgelsicos');
insert into Categorias (idCategoria, Descripcion) values (3, 'Laxante');
insert into Categorias (idCategoria, Descripcion) values (4, 'Antialérgicos');
insert into Categorias (idCategoria, Descripcion) values (5, 'Genéricos');

/* Insertar en Farmacia.Presentación */ 
insert into Presentacion (idPresentacion, Descripcion) values (1, 'Lata');
insert into Presentacion (idPresentacion, Descripcion) values (2, 'Botella');
insert into Presentacion (idPresentacion, Descripcion) values (3, 'Tabletas');
insert into Presentacion (idPresentacion, Descripcion) values (4, 'Capsulas');

/* Insertar en Farmacia.Productos */
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria) 
    values ('7501031311309', 'Pepsi 355ML', 1, 1, 8.0, 10.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501031311606', 'Pepsi 2L', 2, 1, 12.0, 15.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('036731501004', 'Gatorade Naranja 500ML', 2, 1, 10.0, 13.0, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086801121', 'Agua Natural Epura 600ML', 2, 1, 6.00, 8.50, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086801046', 'Agua Natural Epura 1LT', 2, 1, 8.00, 10.00, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501031360024', 'Manzanita Sol 600ML', 2, 1, 9.00, 11.50, 0, 0, 1);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501349029880', 'Tramadol, Paracetamol 325 mg.', 3, 2, 90.0, 125.0, 0, 0, 2); 
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7502223703735', 'Ketorolaco 30 Mg.', 3, 3, 25.0, 31.0, 0, 1, 2);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501075722253', 'Nineka neomicina, Caolín y Pectina', 3, 4, 40.0, 60.0, 0, 0, 2);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086313204', 'OPTIUM 125/50/1MG', 3, 5, 280.00, 402.00, 0, 1, 2);
insert into Producto (Cod_Barras, Descripcion, Presentación, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, idCategoria)
    values ('7501086300891', 'Ad-Col Oral Ad', 3, 5,78.00,112.00, 0, 0, 4);

/* Insertar en Farmacia.Cliente */
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Abelardo Hernandez Mota', 'Su casa', '229 368 4747', '20', 0);
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('Jorge Antonio Pedroza Rendón', 'Su casa', '229 137 0546', '20', 0);
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('', 'Su casa', '', '20', 0);
insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values ('', 'Su casa', '', '20', 0);