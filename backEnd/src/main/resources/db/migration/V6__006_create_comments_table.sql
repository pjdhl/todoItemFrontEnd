
create  table if not exists comment(
    id int(11) not null auto_increment,
    text varchar(256) ,
    comment_item_id int not null ,
    primary key(id),
    foreign key(comment_item_id) references todo(id)
)engine = InnoDB default charset =utf8;
