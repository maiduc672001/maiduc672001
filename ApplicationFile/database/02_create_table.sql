create table userEntity(
userid bigint not null primary key auto_increment,
name varchar(255) null,
password varchar(255) null,
fullname varchar(350) null,
createddate timestamp null
);
create tabe roleEntity(
roleid bigint not null primary key,
name varchar(255) null
);