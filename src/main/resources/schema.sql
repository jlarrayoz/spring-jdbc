create table if not exists OrdenPizza (
  id identity,
  nombrePersona varchar(50) not null,
  ciudad varchar(50) not null,
  barrio varchar(50) not null,
  direccion varchar(50) not null,
  nroTarjeta varchar(16) not null,
  fecVencimiento varchar(5) not null,
  codigoCVV varchar(3) not null
);

create table if not exists Pizza (
  id identity,
  nombre varchar(50) not null,
  fk_ordenPizza_id bigint not null
);


create table if not exists Ingrediente (
  id varchar(4) not null,
  nombre varchar(25) not null,
  tipo varchar(10) not null
);



create table if not exists IngredientePizza (
  fk_ingrediente_id varchar(4) not null,
  fk_pizza_id bigint not null
);


ALTER TABLE Ingrediente ADD PRIMARY KEY (id);
alter table Pizza add foreign key (fk_ordenPizza_id) references OrdenPizza(id);
alter table IngredientePizza add foreign key (fk_ingrediente_id) references Ingrediente(id);