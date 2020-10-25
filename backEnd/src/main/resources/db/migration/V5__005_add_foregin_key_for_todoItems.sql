
alter table todo add column todo_list_id bigint not null ;
alter table todo add foreign key (todo_list_id) references todo_list(`id`);
