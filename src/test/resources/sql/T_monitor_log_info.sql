/*
	Таблица содержит логи с серверов
*/
create table T_monitor_log_info
(
	 id int not null						-- уникальный идентификатор информации о логе
	,idLog int not null references T_monitor_log(id)		-- id лога 
	,
	
	,dtLog timestamp not null					-- время формирования информации о логе
	,dtAdd	timestamp not null default current_timestamp		-- дата добавления записи
	,PRIMARY KEY(id)
)