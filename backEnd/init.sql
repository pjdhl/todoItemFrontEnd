USE `todoItem`;

DROP TABLE IF EXISTS `todoList`;
CREATE TABLE `todoList`(
  `id`  id int(11) NOT NULL,
  `description` VARCHAR(255),
  `status` INT(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
