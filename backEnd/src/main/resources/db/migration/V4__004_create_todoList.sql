create table if not exists todo_list
(
    id      BIGINT  not null AUTO_INCREMENT,
    name    VARCHAR(256) not null,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


