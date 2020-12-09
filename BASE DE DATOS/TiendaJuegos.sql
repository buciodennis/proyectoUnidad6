create database TiendaJuegos;
use TiendaJuegos;

create table empresa_proveedor(
id_provedor varchar(10)primary key not null,
nombre_compania varchar(50) not null,
nombre_gerente varchar(50) not null,
direccion varchar(50) not null,
cuidad varchar(50) not null,
telefono char(10) not null
);

create table empleados(
id_empleado varchar(10) primary key not null,
Nombre varchar(20) not null,
PrimerApellido varchar(20) not null,
SegundoApellido varchar(20) not null,
Puesto enum('Administrador','Empleado') not null,
Direccion varchar(60) not null,
Ciudad varchar(15) not null,
Telefono char(10) not null,
UsuarioLogin varchar(20) not null,
Contraseña blob not null
);


create table clientes(
id_cliente varchar(10)primary key not null,
nombre varchar(50) not null,
apellidos varchar(50) not null,
fechaNacimiento date not null,
direccion varchar(50) not null,
cuidad varchar(50) not null,
telefono char(10) not null
);

create table categorias(
id_categoria varchar(10) primary key not null,
tipo varchar(50) not null
);


create table Productos(
id_producto varchar(10) primary key not null,
Nombre varchar(50) not null,
descripcion text not null,
precio decimal(10,2) not null,
exitencias int not null,
id_categoria varchar(10) not null,
id_provedor varchar(10) not null,
constraint foreign key (id_categoria)
references categorias(id_categoria),
constraint foreign key (id_provedor)
references empresa_proveedor(id_provedor)
on delete cascade on update cascade
);

create table ventas(
id_venta varchar(10)primary key not null,
fecha date not null,
id_empleado varchar(10) not null,
id_cliente varchar(10) not null,
monto_total decimal(9,2)not null,
constraint foreign key (id_empleado)
references empleados(id_empleado),
constraint foreign key (id_cliente)
references clientes(id_cliente)
on delete cascade on update cascade
);



create table detalles_venta(
id_producto varchar(10) not null,
id_venta varchar(10) not null,
fecha date not null,
cantidad int not null,
importe decimal(10,2) not null,
primary key(id_producto, id_venta),
constraint foreign key (id_producto)
references productos(id_producto),
constraint foreign key (id_venta)
references ventas(id_venta)
on delete cascade on update cascade
);

delimiter $$
create procedure agregarCategoria(in id varchar(10), in descripcion varchar(50) )
begin
	insert into categorias
    values
    (id,descripcion);
end$$

create procedure actualizarCategoria(in id varchar(10), in descripcion varchar(50) )
begin
	update categorias 
    set tipo=descripcion where id_categoria=id;
end$$

create procedure eliminarCategoria(in id varchar(10) )
begin
	delete from  categorias where id_categoria=id;
end$$

insert into categorias
values
('1','Accion'),
('2','Arcadee'),
('3', 'Deportivo'),
('4','Estrategia'),
('5','Terror')
;

insert into empresa_proveedor
values
('1','RockStar Games', 'Miguel Lopez','Balbastro 770', 'Buenos Aires', 4452986543),
('2','Activision', 'Jose Perez','Almirante Grau 221', 'La Paz, Bolivia', 4458761098),
('3', 'EA Sports', 'Maria Garcia','Santa Maria 2670', 'Providencia, Santiago', 4458739809),
('4','Capcom', 'Rosa Torres','Carrera 11 #95', 'Bogotá', 4870916483),
('5','Konami', 'Ana Ruiz','Calle Puentezuelas, 55', 'Granada, España', 4457629812)
;

insert into empleados
values 
('1', 'Dennis','Bucio', 'Palmerin', 'Empleado', 'Moroleon 200 #150', 'Moroleon', 445098765, 'DennisB', sha1('dennis') ),
('2', 'Jose','Niño', 'Lopez', 'Empleado', 'Centro  #1', 'Uriangato', 4451239876, 'JoseN', sha1('jose') ),
('3', 'Daniel','Herrera', 'Garcia', 'Administrador', 'Independencia #24', 'Uriangato', 4456734567, 'DanielH', sha1('daniel'))
;

insert into clientes
values
('1', 'Jose', 'Flores Reyes', '1997-10-16', 'Benito Juarez #2', 'Moroleon', 4367628917),
('2', 'Juan', 'Alcantar Tena', '1995-02-10', 'Diamante #15', 'Moroleon', 4450982688),
('3', 'Adrian', 'Tinoco Nuñez', '1999-09-07', 'Rio Bravo #110', 'Moroleon', 4451239807)
;

insert into productos
values
('1', 'Gran teheft auto', 'cuenta la historia de distintos criminales y aunque sean varios, por una razón se van relacionando y envolviendo en problemas a más personajes conforme va pasando el tiempo',
549.00, 100, 1,1),
('2', 'Pixel Puzzle', 'presenta diversos personajes de las franquicias de Konami',
320.00, 100, 2,5),
('3', 'Madden NFL 2021', ' videojuego de fútbol americano, su nombre hace honor a John Madden' ,
1499.00, 100, 3,3),
('4', 'Guitar Hero III: Legends of Rock', ' videojuego de música y el tercer título de la serie Guitar Hero' ,
299.00, 100, 4,2),
('5', 'Resident evil 4', ' videojuego de disparos en tercera persona del estilo supervivencia y horror ' ,
479.00, 100, 5,4)
;



