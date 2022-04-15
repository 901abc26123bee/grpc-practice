DROP TABLE If EXISTS `test`.`stock`;
CREATE TABLE IF NOT EXISTS `test`.`stock` (
  `id` INTEGER NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) DEFAULT NULL,
  `offer_number` INTEGER,
  `price` DOUBLE,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
