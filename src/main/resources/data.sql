delete from IngredientePizza;
delete from Pizza;
delete from OrdenPizza;
delete from Ingrediente;

insert into Ingrediente (id, nombre, tipo) values
('MC', 'Masa común', 'MASA'),
('MM', 'Masa madre', 'MASA'),
('MIT', 'Masa italiana', 'MASA');

insert into Ingrediente (id, nombre, tipo) values
('QM', 'Queso muzzarella', 'QUESO'),
('QD', 'Queso dambo', 'QUESO'),
('QP', 'Queso persistente', 'QUESO');	

