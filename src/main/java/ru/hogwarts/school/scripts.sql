select *
from student;


select *
from faculty;

select *
from student
where student.age > 10
  and student.age < 20;


select name
from student
where name like '%%';


select *
from student
where name like '%2%';

select *
from student
where age < student.id;

select *
from student
ORDER BY age;
