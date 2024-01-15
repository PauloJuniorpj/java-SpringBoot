ALTER TABLE restaurante ADD COLUMN aberto TINYINT(1) NOT NULL DEFAULT 1;
UPDATE restaurante SET aberto = false;