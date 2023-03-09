CREATE TABLE teacher(
    id int not null primary key auto_increment,
    name varchar(100) not null,
    password varchar(50) not null
);
CREATE TABLE platoon(
    id int not null primary key auto_increment,
    platoon varchar(10) not null
);
CREATE TABLE student(
    id int not null primary key auto_increment,
    name varchar(100) not null,
    password varchar(50) not null,
    platoon_id int not null references platoon(id) on update cascade on delete cascade
);
CREATE TABLE platoon_teacher(
    teacher_id int not null references teacher(id)  on update cascade on delete cascade,
    platoon_id int not null references platoon(id)  on update cascade on delete cascade
);
CREATE TABLE test(
    id int not null primary key auto_increment,
    version int not null,
    password text not null
);
CREATE TABLE student_test(
    student_id int not null references student(id)  on update cascade on delete cascade,
    test_id int not null references test(id)  on update cascade on delete set null ,
    result varchar(50)
);

