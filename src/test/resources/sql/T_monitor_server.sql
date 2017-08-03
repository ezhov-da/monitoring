/*
	Таблица содержит список серверов
*/
create table T_monitor_server
(
	id int not null						-- id сервера
	,ip varchar(15) not null 				-- ip сервера
	,dtAdd timestamp not null default current_timestamp 	-- дата добавления сервера
	,PRIMARY KEY(id)
)