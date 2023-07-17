--Описание структуры: у каждого человека есть машина.
-- Причем несколько человек могут пользоваться одной машиной.
-- У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
-- У каждой машины есть марка, модель и стоимость.
-- Также не забудьте добавить таблицам первичные ключи и связать их.

CREATE TABLE cars
(
    id    BIGSERIAL PRIMARY KEY,
    brand VARCHAR(15) not null,
    model VARCHAR(31)  not null,
    price INT CHECK ( price > 0 ) not null
);


CREATE TABLE owners
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR( 15 ) not null ,
    age int check ( age > 17 )  not null,
    has_driver_license boolean default true not null ,
    car_id BIGINT REFERENCES cars (id) not null
);

INSERT INTO cars(brand, model, price)
VALUES ('Brand', 'Model', 10101010);

INSERT INTO owners(name, age, car_id)
VALUES ('Second', 25, 1);

INSERT INTO owners(name, age, car_id)
VALUES ('First', 24, 1);





