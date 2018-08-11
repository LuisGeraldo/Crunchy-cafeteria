create database cafeteria;

use cafeteria;

insert into tipoUsuario (nombre, descripcion) values('usuario', 'usuario para...');

create table tipoUsuario(
	Id int auto_increment primary key,
    nombre varchar(10) not null,
    descripcion varchar(60) not null
);


create table usuario(
	Id int auto_increment primary key,
    UserName varchar(30) not null,
    Email varchar(60) not null,
	uPassword varchar(60) not null,
    idTipoUsuario int,
    constraint fk_usuario foreign key(idTipoUsuario) references tipoUsuario(id) on delete cascade
);

create table estado(
	id int auto_increment primary key,
    descripcion varchar(10)
);

create table marca(
	id int auto_increment primary key, 
    nombre varchar(30),
    descripcion varchar(60),
    idEstado int,
    constraint fk_estado foreign key(idEstado) references estado(id) on delete cascade
);



create table sucursal(
	id int auto_increment primary key, 
    nombre varchar(30),
    descripcion varchar(60),
    idEstado int,
    constraint fk02_estado foreign key(idEstado) references estado(id) on delete cascade
);

create table proveedor(
	id int auto_increment primary key, 
    nombre varchar(30),
    rnc varchar(20),
	fechaRegistro varchar(20),
    idEstado int,
    constraint fk03_estado foreign key(idEstado) references estado(id) on delete cascade
);

create table cafeteria(
	id int auto_increment primary key, 
    nombre varchar(30),
    descripcion varchar(30),
    encargado varchar(30),
    idSucursal int,
    idEstado int,
    constraint fk04_estado foreign key(idEstado) references estado(id) on delete cascade,
    constraint fk01_sucursal foreign key(idSucursal) references estado(id) on delete cascade
);

create table tipoCliente(
	Id int auto_increment primary key,
    nombre varchar(10) not null,
    descripcion varchar(60) not null
);


create table cliente(
	id int auto_increment primary key, 
    nombre varchar(30),
    cedula varchar(30),
    limiteCredito decimal,
    fechaIngreso varchar(30),
	idTipoCliente int,
    idEstado int,
    constraint fk05_estado foreign key(idEstado) references estado(id) on delete cascade,
    constraint fk_TipoCliente foreign key(idTipoCliente) references tipoCliente(id) on delete cascade
);


create table tandaLabor(
	id int auto_increment primary key,
    nombre varchar(30),
    descripcion varchar(30)
);

create table tipoEmpleado(
	id int auto_increment primary key,
    nombre varchar(30),
    descripcion varchar(60)
);


create table empleado(
	id int auto_increment primary key, 
    nombre varchar(30),
    cedula varchar(30),
    comision decimal,
    fechaIngreso varchar(30),
    idTipoEmpleado int,
    idTandaLabor int,
    idEstado int,
    constraint fk06_estado foreign key(idEstado) references estado(id) on delete cascade,
    constraint fk_tandaLabor foreign key(idTandaLabor) references tandaLabor(id) on delete cascade,
    constraint fk_tipoEmpleado foreign key(idTipoEmpleado) references  tipoUsuario(id) on delete cascade
);

create table articulo(
	id int auto_increment primary key, 
    nombre varchar(40),
	descripcion varchar(40),
	idMarca int, 
	costo decimal,
	idProveedor int,
	existencia int,
	idEstado int,
    constraint fk07_estado foreign key(idEstado) references estado(id) on delete cascade,
    constraint fk_proveedor foreign key(idProveedor) references proveedor(id) on delete cascade,
    constraint fk_marca foreign key(idMarca) references marca(id) on delete cascade
);

create table modoPago(
	id int auto_increment primary key,
    nombre varchar(30),
    descripcion varchar(60)
);

create table estadoFactura(
	id int auto_increment primary key,
    nombre varchar(30),
    descripcion varchar(60)
);


create table factura(
	id int auto_increment primary key,
    idCafeteria int,
	idEmpleado int,
    idCliente int,
    fecha varchar(30),
    idModoPago int,
    idEstado int,
    comentario varchar(60),
    montoTotal decimal,
    constraint fk01_cafeteria foreign key(idCafeteria) references cafeteria(id) on delete cascade,
    constraint fk01_empleado foreign key(idEmpleado) references empleado(id) on delete cascade,
    constraint fk01_cliente foreign key(idCliente) references cliente(id) on delete cascade,
    constraint fk01_modoPago foreign key(idModoPago) references modoPago(id) on delete cascade,
    constraint fk01_estadoFactura foreign key(idEstado) references estadoFactura(id) on delete cascade
);


create table detalleFactura(
	id int auto_increment primary key,
    idFactura int,
	idArticulo int,
    cantidad int,
    constraint fk_factura foreign key(idFactura) references factura(id) on delete cascade,
    constraint fk_articulo foreign key(idArticulo) references articulo(id) on delete cascade
);
































