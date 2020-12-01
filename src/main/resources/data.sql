insert into categoria
values(900,0, 'Mercearia',0);
insert into categoria
values(901,0.1, 'Perecíveis',1);
insert into categoria
values(902, 0.0, 'Limpeza',0);
insert into categoria
values(903, 0.0,'Higiene',0);

insert into produto(id, categoria_id, codigo_barra, fabricante, nome, preco, situacao)
values(10001,900, '87654321-B', 'Parmalat', 'Leite Integral', 4.5, 1);
insert into produto(id, categoria_id, codigo_barra, fabricante, nome, preco, situacao)
values(10002,900, '87654322-B', 'Tio Joao', 'Arroz Integral', 5.5, 1);
insert into produto(id, categoria_id, codigo_barra, fabricante, nome, preco, situacao)
values(10003,900, '87654323-B', 'OMO', 'Sabao em Po', 12, 1);
insert into produto(id, categoria_id, codigo_barra, fabricante, nome, preco, situacao)
values(10004,900, '87654324-C', 'Dragao', 'Agua Sanitaria', 3, 1);
insert into produto(id, categoria_id, codigo_barra, fabricante, nome, preco, situacao)
values(10005,900, '87654325-C', 'Colgate', 'Creme Dental', 2.5, 1);


insert into lote
values(1, '10/05/2021', 20, 10001);
insert into lote
values(2, '08/12/2025', 1, 10002);
insert into lote
values(3, '13/01/2023', 14, 10003);
insert into lote
values(4, '05/06/2021', 3, 10004);
insert into lote
values(5, '02/05/2022', 21, 10005);
