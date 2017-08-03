/*
	Таблица содержит логи с серверов
*/
create table T_monitor_log
(
	 id int not null						-- уникальный идентификатор лога
	,idIp int not null references T_monitor_server(id)		-- id ip сервера с которого получен лог
	,dtLog timestamp not null					-- время формирования лога	
	,dtAdd	timestamp not null default current_timestamp		-- дата добавления записи
	,PRIMARY KEY(id)
)