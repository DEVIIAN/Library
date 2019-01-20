create database Library;

/*下划线为主键、斜体为外键
BookISBN表(ISBN编号、图书类别、书名、作者、出版社、出版日期、图书馆馆藏数量、
           图书可外借数量、图书价格、图书介绍)
*/
create table BookISBN(
	Id varchar(8) not null primary key,
	BookCategories varchar(50) not null,
	BookName varchar(50) not null,
	Author varchar(50) not null,
	PublishingHouse varchar(50) not null,
	PublictionDate datetime not null,
	NumberLibrary int not null,
	NumberLoad int not null,
	Price money not null,
	BookIntroduce text not null,
	check(PublictionDate < getdate()),
	check(NumberLibrary >= NumberLoad)
	)

select*from BookISBN

/*Book表(ISBN编号、书号、是否可借)
*/

create table Book(
	ISBNID varchar(8) not null,
	BookID varchar(8) not null,
	BooleanLoad bit not null,
	primary key(ISBNID, BookID),
	foreign key(ISBNID) references BookISBN(Id)
)

select*from Book;
alter table Book 
add constraint uqn_1 unique (BookID)
--drop table Reader




/*Reader表(读者证号、密码、姓名、性别、出生日期、身份证号、
               借书等级、可借书数量、
           已借书数量、工作部门、家庭住址、联系电话)
*/
create table Reader(
	ReaderID varchar(8) not null primary key,
	Password varchar(16) not null ,
	Name varchar(12) not null,
	Sex varchar(2) not null,
	Birth datetime not null,
	ID char(18) not null,
	ReadClass varchar(18) not null,
	NumberLending int not null,
	NumberLent int not null,
	Department varchar(20) not null,
	AddressHome varchar(20) not null,
	Phone varchar(11) not null,
	check(Sex = '男' or Sex = '女'),
	check(Birth < getdate()),
	check(NumberLending >= NumberLent),
	check(ID like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][X0-9]')
	)
--drop table Reader
select*from Reader

alter table Reader
alter column Password varchar(100)


/*UserLibrary表(账号、密码)*/
create table UserLibrary(
	MID varchar(8) not null primary key,
	Password varchar(18) not null
)

alter table UserLibrary
alter column Password varchar(100)
--DROP table UserLibrary
select* from UserLibrary ;
drop table LoadInformation
/*LoadInformation表(读者证号、书号、外借日期、图书应还日期、
                    实际还书日期、是否归还、罚金、图书类别)
*/
create table LoadInformation(
	ReaderID varchar(8) not null ,
	BookID varchar(8) not null ,
	DateOut datetime not null,
	DateReturn datetime not null,
	DateReturnReal datetime ,
	BooleanReturn bit not null,
	Fine money ,
	BookCategories varchar(50),
	primary key(ReaderID, BookID),
	foreign key(ReaderID) references Reader(ReaderID),
	foreign key(BookID) references Book(BookID),
	check(DateOut < DateReturn and DateOut < DateReturnReal)
)
select*from LoadInformation ;


insert into BookISBN
values('10001','计算机','数据库原理及应用','叶文珺','清华大学出版社',
'2012-09-01','5','5','39.5',
'数据库技术是计算机科学与技术中发展最快的技术之一，它已经成为计算机
信息系统与应用系统的核心技术和重要基础。')

insert into BookISBN
values('20001','历史','三国志','陈寿','三国出版社','2001-1-1',10,10,50,'本书讲述了东汉末年群雄争霸的故事')



insert into Book
values('10001','10001001','1')        --1可借0不可借
insert into Book
values('10001','10001002','1')
insert into Book
values('10001','10001003','1')
insert into Book
values('10001','10001004','1')
insert into Book
values('10001','10001005','1')

insert into UserLibrary
values('20162151','123456')

insert into Reader 
values('20162151','20160051','杨欢欢','女','1998-11-08','622824199701280467','研究生','10','0',
'上海电力大学','上海','13127859819')
insert into Reader 
values('20160002','20160002','赵燕','女','1998-1-24','351024199801241352','本科生','5','0',
'上海电力大学','上海','15815425155')
insert into Reader 
values('20160003','20160003','徐颖','女','1998-10-15','351024199810152542','本科生','5','0',
'上海电力大学','上海','15512385436')
insert into Reader 
values('20160004','20160004','张飞','男','1998-10-15','351024199810152548','博士','15','0',
'上海电力大学','上海','15512385437')
insert into Reader 
values('20160005','20160005','赵云','男','1998-10-30','351024199810152545','教师','20','0',
'上海电力大学','上海','15512385438')

insert into LoadInformation
values('20160002','10001003','2018-10-1','2018-11-1',null,'0',null,null)





select * from LoadInformation
select * from Reader

--drop table Reader
--drop table LoadInformation
