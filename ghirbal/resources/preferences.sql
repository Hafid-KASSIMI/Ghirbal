BEGIN TRANSACTION;

-- Table preferences
CREATE TABLE IF NOT EXISTS `preferences` ( 
    `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    `libelle` VARCHAR ( 128 ),
    `value` TEXT
);
-- Content:
INSERT INTO `preferences` (`libelle`, `value`) VALUES 
    ("SERIALIZED_DB_NAME_GENERATED", "0"),
    ("SERIALIZED_DB_NAME", "")
;

COMMIT;