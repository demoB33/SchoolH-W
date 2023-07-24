--liquibase formatted sql

--changeset vasilenko:1
--rollback DROP INDEX idx_name_unique
CREATE INDEX idx_name_unique ON student (name);


--changeset vasilenko:2
--rollback DROP INDEX idx_name_color_unique
CREATE INDEX idx_name_color_unique ON faculty (color, name);