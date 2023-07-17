--- Возраст студента не может быть меньше 16 лет.
--- Имена студентов должны быть уникальными и не равны нулю.
--- Пара “значение названия” - “цвет факультета” должна быть уникальной.
--- При создании студента без возраста ему автоматически должно присваиваться 20 лет.

ALTER TABLE student
    ADD CHECK ( age > 15 );

ALTER TABLE student
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE student
    ALTER COLUMN name SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);