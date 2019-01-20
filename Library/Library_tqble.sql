create database Library;

/*�»���Ϊ������б��Ϊ���
BookISBN��(ISBN��š�ͼ��������������ߡ������硢�������ڡ�ͼ��ݹݲ�������
           ͼ������������ͼ��۸�ͼ�����)
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

/*Book��(ISBN��š���š��Ƿ�ɽ�)
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




/*Reader��(����֤�š����롢�������Ա𡢳������ڡ����֤�š�
               ����ȼ����ɽ���������
           �ѽ����������������š���ͥסַ����ϵ�绰)
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
	check(Sex = '��' or Sex = 'Ů'),
	check(Birth < getdate()),
	check(NumberLending >= NumberLent),
	check(ID like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][X0-9]')
	)
--drop table Reader
select*from Reader

alter table Reader
alter column Password varchar(100)


/*UserLibrary��(�˺š�����)*/
create table UserLibrary(
	MID varchar(8) not null primary key,
	Password varchar(18) not null
)

alter table UserLibrary
alter column Password varchar(100)
--DROP table UserLibrary
select* from UserLibrary ;
drop table LoadInformation
/*LoadInformation��(����֤�š���š�������ڡ�ͼ��Ӧ�����ڡ�
                    ʵ�ʻ������ڡ��Ƿ�黹������ͼ�����)
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
values('10001','�����','���ݿ�ԭ��Ӧ��','Ҷ�ĬB','�廪��ѧ������',
'2012-09-01','5','5','39.5',
'���ݿ⼼���Ǽ������ѧ�뼼���з�չ���ļ���֮һ�����Ѿ���Ϊ�����
��Ϣϵͳ��Ӧ��ϵͳ�ĺ��ļ�������Ҫ������')

insert into BookISBN
values('20001','��ʷ','����־','����','����������','2001-1-1',10,10,50,'���齲���˶���ĩ��Ⱥ�����ԵĹ���')



insert into Book
values('10001','10001001','1')        --1�ɽ�0���ɽ�
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
values('20162151','20160051','���','Ů','1998-11-08','622824199701280467','�о���','10','0',
'�Ϻ�������ѧ','�Ϻ�','13127859819')
insert into Reader 
values('20160002','20160002','����','Ů','1998-1-24','351024199801241352','������','5','0',
'�Ϻ�������ѧ','�Ϻ�','15815425155')
insert into Reader 
values('20160003','20160003','��ӱ','Ů','1998-10-15','351024199810152542','������','5','0',
'�Ϻ�������ѧ','�Ϻ�','15512385436')
insert into Reader 
values('20160004','20160004','�ŷ�','��','1998-10-15','351024199810152548','��ʿ','15','0',
'�Ϻ�������ѧ','�Ϻ�','15512385437')
insert into Reader 
values('20160005','20160005','����','��','1998-10-30','351024199810152545','��ʦ','20','0',
'�Ϻ�������ѧ','�Ϻ�','15512385438')

insert into LoadInformation
values('20160002','10001003','2018-10-1','2018-11-1',null,'0',null,null)





select * from LoadInformation
select * from Reader

--drop table Reader
--drop table LoadInformation
