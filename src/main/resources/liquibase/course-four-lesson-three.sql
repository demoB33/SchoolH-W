--liquibase formatted sql

--changeset vasilenko:1
CREATE INDEX IDX_students_name ON student (name);

--changeset vasilenko:2
CREATE INDEX IDX_faculties_name_color ON faculty (color);
