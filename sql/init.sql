create table courses
(
    id          int auto_increment
        primary key,
    name        varchar(128)      not null,
    remark      varchar(512)      not null,
    create_time datetime          not null,
    create_user int               not null,
    update_time datetime          not null,
    update_user int               not null,
    enable      tinyint default 1 not null
);


INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (1, '语文', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);
INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (2, '数学', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);
INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (3, '英语', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);
INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (4, '物理', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);
INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (5, '化学', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);
INSERT INTO database_agent.courses (id, name, remark, create_time, create_user, update_time, update_user, enable) VALUES (6, '生物', '这是一个学科', '2025-07-22 13:56:21', 1, '2025-07-22 13:56:35', 1, 1);


create table database_agent.students
(
    id          int auto_increment
        primary key,
    name        varchar(128)      not null,
    address     varchar(128)      not null,
    age         int               not null,
    create_time datetime          not null,
    create_user int               not null,
    update_time datetime          not null,
    update_user int               not null,
    enable      tinyint default 1 not null
);

INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (1, '管理员', '南通市', 18, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);
INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (2, '张三', '南通市', 18, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);
INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (3, '李四', '江苏市', 19, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);
INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (4, '王五', '南京市', 25, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);
INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (5, '赵六', '上海市', 30, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);
INSERT INTO database_agent.students (id, name, address, age, create_time, create_user, update_time, update_user, enable) VALUES (6, '李梅', '淮安市', 24, '2025-07-22 13:58:05', 1, '2025-07-22 13:58:08', 1, 1);


create table database_agent.students_course
(
    id          int auto_increment
        primary key,
    student_id  int      not null,
    course_id   int      not null,
    create_time datetime not null
);


