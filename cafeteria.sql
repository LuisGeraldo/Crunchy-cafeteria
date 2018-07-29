create database cafeteria;

use cafeteria;

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
    TipoUsuario int,
    constraint fk_usuario foreign key(TipoUsuario) references tipoUsuario(id)
);


select * from usuario;



