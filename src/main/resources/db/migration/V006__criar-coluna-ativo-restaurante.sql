ALTER TABLE restaurante ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1;
UPDATE restaurante SET ativo = 1;