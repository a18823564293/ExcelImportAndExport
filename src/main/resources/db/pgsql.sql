CREATE SEQUENCE demo_id_seq START 1;
-- 创建表
CREATE TABLE IF NOT EXISTS demo.demo
(
    id integer NOT NULL DEFAULT nextval('demo_id_seq'::regclass),
    title character(20) COLLATE pg_catalog."default" NOT NULL,
    content character(45) COLLATE pg_catalog."default" NOT NULL,
    c1 character(45) COLLATE pg_catalog."default",
    c2 character(45) COLLATE pg_catalog."default",
    c3 character(45) COLLATE pg_catalog."default",
    c4 character(45) COLLATE pg_catalog."default",
    c5 character(45) COLLATE pg_catalog."default",
    create_date timestamp without time zone,
    CONSTRAINT demo_pkey PRIMARY KEY (id)
)
-- 插入1条数据
INSERT INTO demo.demo(title,content,c1,c2,c3,c4,c5,create_date)
VALUES(substr(random()::text, 3, 10),gen_random_uuid(),gen_random_uuid(),gen_random_uuid(),gen_random_uuid(),gen_random_uuid(),gen_random_uuid(),now());
-- 生成数据（多次执行）
INSERT INTO `demo`(`title`,`content`,`c1`,`c2`,`c3`,`c4`,`c5`,`create_date`)
SELECT substring(rand(),3,10),uuid(),uuid(),uuid(),uuid(),uuid(),uuid(),now()
FROM `demo`