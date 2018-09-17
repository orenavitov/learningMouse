# Mysql

## MySql安装与配置

## 索引

http://www.cnblogs.com/hustcat/archive/2009/10/28/1591648.html

## Mysql数据库引擎

### 锁

#### 行级锁（row-loevel）

#### 表级锁（table-level）

#### 页级锁（page-level）

## Mysql操作命令

1. 登录
````
mysql -h [host] -u[用户名] -p
````

2. 退出
````
quit
````

3. 创建一个新的用户

````
create user '用户名'@'登录ip' indentified by '密码'
````
indentified by '密码'可以不加，表示登录不需要密码

@'ip'可用@'%'表示没有登录ip限制

4. 给用户授权

````
grant 权限 on 数据库名.表明 to 'test'@'%' 
````
被授权的用户无法对其他用户授权，如果想让被授权的用户能够对其他用户授权：

````
grant 权限 on 数据库名.表名 to 'test'@'%' with grant option
````

5. 查看用户权限

````
show grants for 'test'@'%';
````

6. 修改用户的权限

````
grant 权限 on 数据库名.表名 to 'test'@'%'
````

7. 修改用户密码

````
set password for 'test'@'%' = password('newpassword')
````

8. 撤销用户权限

````
revoke 权限 on *.* from 'test'@'%';
````

9. 删除用户

````
drop user 'test'@'%';
````

10. 载入数据

````
load data infile './pet.txt' int table pet fields terminated by ' ' lines terminated by '\r\n';
````

在执行这条指令的时候my.ini配置中的Secure File Priv 设置为：“secure-file-priv=”, secure-file-priv显示mysql载入数据与输出数据的路径，每次修改需要重启Mysql;
fields terminated by指定每行数据以什么字符分割；
lines terminated by指定每行数据以什么字符结尾；
./pet.text表示在Date文件下（默认数据路径）；
pet.txt中如果每行数据有空值用“\N”表示；

11. 更新数据

````
update pet set birth = '1989-08-31' where name = 'xx'
````

12. 条件查询

````
select * from pet where name = 'xx'

select * from pet where birth  >= 'xx'

select * from pet where species = 'xx' and sex = 'xx'

select * from pet where species = 'xx' or sex = 'xx'

select * from pet where (speciees = 'xx' and sex = 'xx') or (speciese = 'xx' and sex = 'xx')

select name, birth from pet

select name from pet (查出的数据可能有重复的)

select distinct name from pet (查出的数据没有重复的)

select name from pet order by birth desc (对查询出来的数据按照birth进行排序, 默认升序 desc 表示降序， asc 表示升序)

select name from pet order by birth, species desc (按照多个属性进行排序）

````

13. 多表查询

````
select a.id, a.name, a.address, a.date, b.math, b.english, b.chinese from table1 as a, table2 as b where a.id = b.id;

select id, name, pwd from table1 UNION uid, price, date from table2; (union 会删除重复行)

select id, name, pwd from table1 ALL uid price, date from table2; (ALL 不会删除重复行)
````

14. 分页查询

````
select * from table1 limit start, size;(start表示其实位置， size表示页大小， 起始位置从0算起)

select * from table1 limit size;
````

### 添加外键

````
//创建表时
id varchar(40) references table1(id)

foreign key(id) references table1(id)

constraint 外键名 foreign key(id) references table1(id)

````
### alter操作

1. change (与modify相比可以改名字)

````
alter table t1 change col1 newCol1 bigint;
````

2. modify (单独修改某一列的属性， 会丢掉原来的属性， 不能修改名字)
````
alter table t1 modify col1 bigint;
````

3. drop (删除某一列， 如果一个表中只有一个列， 删除失败)
````
alter table table1 drop col1;
````

4. add (添加列)
````
alter table table1 add col1 int;

//将新加的属性放在第一列

alter table table1 add col1 int First;

//将新加的属性放在某一列后面

alter table table1 add col1 int After coln;
````

5. 修改默认值

````
alter table table1 alter col1 set default n;
````

### default value



## Mysql运算表达式与函数

### 运算符

1. 加(+): select 1 + 1

2. 减(-): select 2 -1

3. 乘(*): select 2 * 1

4. 除(/): select 5 / 3

5. 商(div): select 5 div 2

6. 模(%或mod()): select 5 % 2 或 select mod(5, 2)

7. 等于(=)

8. 不等于(!= 或 <>)

9. 小于(<)

10. 小于等于(<=)

11. 大于(>)

12. 大于等于(>=)

13. between: select 10 between 10 and 20

14. in: select 1 in (1, 2, 3)

15. is null: select o is null

16. is not null: select 0 is not null

17. like: select 123456 like '123%'

18. regexp: select 'abcdef' regexp 'ab'

19. 非(not 或 ！)

20. 与(and 或 &&)

21. 或(or 或 ||)

22. 异或(xor 或 ^)

### 函数

1. concat() 字符链接

2. concat_ws() 使用指定的分隔符进行字符链接

3. format() 数字格式化

4. lower() 转化小写字母

5. upper() 转化大写字母

6. left() 获取左侧字符

7. right() 获取右侧字符

8. length() 获取字符串长度

9. ltrim() 删除前导空格

10. rtrim() 删除后续空格

11. trim() 删除空格

12. substring() 字符串截取

13. [not] like 模式匹配

14. replace() 字符串替换

15. ceil() 进一取整

16. div() 整数除法

17. floor() 舍一取整

18. Mod() 取余数

19. power() 幂运算

20. round() 四舍五入

21. truncate() 数字截取

22. now() 当前日期和时间

23. curdate() 当前日期

24. curtime() 当前时间

25. date_add() 日期变化

26. dateDiff() 日期差值

27. date_format() 日期格式化

28. avg() 平均值

29. count() 计数

````
select count(*) from pet
````

20. max()

21. min()

22. sum() 求和

