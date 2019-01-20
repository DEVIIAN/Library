

use Library
go
--触发器 增加书籍
--根据馆藏数量给图书编号
--drop trigger AddBook
select*from BookISBN
select*from BOOK
create trigger AddBook
	on BookISBN
	with encryption
	after insert
As
	declare @ISBNID varchar(8), @NumberLibrary int, @N int
	select @ISBNID = ID, @NumberLibrary = NumberLibrary
	from inserted
	if @NumberLibrary > 999
		begin
			rollback tran
			raiserror('增加书籍不能超过999本', 16, 10)
		end
	else if @NumberLibrary >= 1 and @NumberLibrary < 10
		begin
		set @N = 1
		while @N <= @NumberLibrary
			begin
			insert into Book
			values(@ISBNID, concat(@ISBNID, '00', @N), 1)
			set @N = @N + 1
			end
		end
	else if @NumberLibrary >= 10 and @NumberLibrary <= 99
		begin
		set @N = 1
		while @N < 10
			begin
			insert into Book
			values(@ISBNID, concat(@ISBNID, '00', @N), 1)
			set @N = @N + 1
			end
		while @N >= 10 and @N <= @NumberLibrary
			begin
			insert into Book
			values(@ISBNID, concat(@ISBNID, '0', @N), 1)
			set @N = @N + 1
			end
		end
	else if @NumberLibrary >= 100 and @NumberLibrary <= 999
		begin
		set @N = 1
		while @N < 10
			begin
			insert into Book
			values(@ISBNID,concat(@ISBNID, '00', @N), 1)
			set @N = @N + 1
			end
		while @N >= 10 and @N < 100
			begin
			insert into Book
			values(@ISBNID,concat(@ISBNID, '0', @N), 1)
			set @N = @N + 1
			end
		while @N >= 100 and @N <= @NumberLibrary
			begin
			insert into Book
			values(@ISBNID, concat(@ISBNID, @N), 1)
			set @N = @N + 1
			end
		end
	else if @NumberLibrary < 1
		begin
			rollback tran
			raiserror('增加书籍不能少于1本', 16, 10)
		end

delete
from Book
where ISBNID = '10001'

--drop trigger AddBook


delete
from BookISBN
where id = '30001'

use library
go

--触发器 借书
create trigger LoadBook
	on LoadInformation
	with encryption
	after insert
AS
	declare @BookID varchar(8), @ISBNID varchar(8), @ReaderID varchar(8), @BooleanLoad bit, @NumberLending int, @NumberLent int, @BookCategories varchar(50)
	select @BookID = BookID, @ReaderID = ReaderID
	from inserted

	begin transaction   --图书类别

		select @NumberLending = NumberLending, @NumberLent = NumberLent
		from Reader
		where ReaderID = @ReaderID

		if @NumberLent >= @Numberlending
			begin
			rollback
			raiserror('借书额超标', 16, 10)
			end
		else
			begin

			select @BooleanLoad = BooleanLoad
			from Book
			where BookID = @BookID

			

			if @BooleanLoad = 0
				begin
				rollback
				raiserror('这本书已经外借', 16, 10)
				end
			else
				begin 
				select @ISBNID = ISBNID
				from Book
				where BookID = @BookID

				select @BookCategories = BookCategories
				from BookISBN
				where Id = @ISBNID

				update LoadInformation
				set BookCategories = @BookCategories
				where ReaderID = @ReaderID and BookID = @BookID

				update BookISBN
				set NumberLoad = NumberLoad - 1
				where id = @ISBNID

				update Book
				set BooleanLoad = 0
				where BookID = @BookID

				update reader
				set NumberLent = NumberLent + 1
				where ReaderID = @ReaderID
				end
			end		
	commit transaction



select * from LoadInformation

--drop trigger LoadBook

insert into LoadInformation
values('20162151', '10001002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '计算机')
insert into LoadInformation
values('20162151', '10002002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '计算机')
insert into LoadInformation
values('20160002', '30001001', '2017-9-1', DATEADD(MONTH, 1, '2017-9-1'), null, 0, null, '物理')
insert into LoadInformation
values('20160002', '10002001',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '计算机')
insert into LoadInformation
values('20160001', '10001002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '计算机')
insert into LoadInformation
values('20160002', '10001001',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '计算机')

drop trigger ReturnBook
go
--触发器 还书
create trigger ReturnBook
	on LoadInformation
	with encryption
	after update
AS
	declare @BooleanReturn bit, @DateReturn datetime, @ReaderID varchar(8), @BookID varchar(8), @ISBNID varchar(8), @Price money,
	 @DateReturnReal datetime, @DateReturnReal_late datetime, @BooleanReturn_Now bit
	
	select  @DateReturn = DateReturn, @BookID = BookID, @ReaderID = ReaderID, @DateReturnReal = DateReturnReal
	from inserted
	
	select @BooleanReturn = BooleanReturn, @DateReturnReal_late = DateReturnReal
	from deleted
	where BookID = @BookID
	begin transaction
		if @DateReturnReal is not null and @DateReturnReal_late is null and  @BooleanReturn = 0
			begin
				if DATEDIFF(year, @DateReturnReal, @DateReturn) > 1
					begin 
						select @ISBNID = ISBNID
						from Book
						where BookID = @BookID

						select @Price = Price
						from BookISBN
						where id = @ISBNID

						update LoadInformation
						set Fine = @Price
						where BookID = @BookID and ReaderID = @ReaderID

						update Book
						set BooleanLoad = 1
						where BookID = @BookID

						update BookISBN
						set NumberLoad = NumberLoad + 1
						where id = @ISBNID

						update Reader
						set NumberLent = NumberLent - 1
						where ReaderID = @ReaderID
					end
				else
					begin
						select @ISBNID = ISBNID
						from Book
						where BookID = @BookID

						update Reader
						set NumberLent = NumberLent - 1
						where ReaderID = @ReaderID

						update Book
						set BooleanLoad = 1
						where BookID = @BookID

						update BookISBN
						set NumberLoad = NumberLoad + 1
						where id = @ISBNID

								
					end
			end
		else if @BooleanReturn = 1 or @DateReturnReal_late is not null
			begin
				rollback
				raiserror('这本书已还', 16, 10)	
			end
	commit transaction

--drop trigger ReturnBook

update LoadInformation
set DateReturnReal = getdate(), booleanReturn = 1	
where ReaderID = '20160002'	and BookID = '10001001'

update LoadInformation
set DateReturnReal = getdate(), booleanReturn = 1	
where ReaderID = '20160002'	and BookID = '10002001'

update LoadInformation
set DateReturnReal = getdate(), booleanReturn = 1	
where ReaderID = '20160001'	and BookID = '10002002'

--视图 读者看图书信息
create view BookISBN_Reader
AS
	select Id 书号ISBN, BookName 书名,BookCategories 书籍类别, Author 作者, NumberLoad 可借数量, BookIntroduce 书籍介绍 
	from BookISBN
	with check option






--drop view BookISBN_Reader
--视图 管理员看图书信息
create view BookISBN_Admin
As
	select Id 书号ISBN, BookName 书名,BookCategories 书籍类别, Author 作者,  NumberLibrary 图书馆本书数量,  NumberLoad 可借数量, Price 书籍价格, BookIntroduce 书籍介绍 
	from BookISBN
	with check option



drop view BookISBN_Admin
select * from BookISBN_Reader
select * from BookISBN_Admin







--视图 查询借书信息
create view LoadBookInformation
AS
	select ReaderID , BookID , DateOut , DateReturn , BooleanReturn ,Fine
	from LoadInformation
	with check option

	
	
drop view LoadBookInformation
select * from LoadBookInformation where ReaderID = '20160001'
select count(*) from LoadBookInformation
drop view LoadBookInformation






--视图 查询读者信息
create view ReaderView
As
	select ReaderID , Name , ReadClass , NumberLending , NumberLent 
	from Reader
	with check option



drop view ReaderView
select * from ReaderView where ReaderID = '20160001'






--视图 查询到期未还的图书
--输出 到期未还图书编号、书名、读者证号、读者姓名、借出日期
create view NoReturnBooksView
AS 
	select B.BookID , BI.BookName , R.ReaderID , R.Name , L.DateOut 
	from LoadInformation L, BookISBN BI, Book B, Reader R
	where getdate() > L.DateReturn and L.BookID = B.BookID and B.ISBNID = BI.Id and L.ReaderID = R.ReaderID
	with check option


select * from NoReturnBooksView
drop view NoReturnBooksView
execute NoReturnBook '30001001'




--存储过程 查询读者已借书信息 
--输入 读者证号
--输出 归还的图书编号和书名 
create procedure ReaderBookInformation
	@ReaderID varchar(8)
AS
	select B.BookID 图书编号, BI.BookName 书名, L.ReaderID 读者证号, B.BooleanLoad 是否还书
	from LoadInformation L, BookISBN BI, Book B
	where L.ReaderID = @ReaderID and L.BookId = B.BookID and B.ISBNID = BI.Id



execute ReaderBookInformation '20160001'
drop procedure ReaderBookInformation





--存储过程 查询读者个人信息
--输入 读者证号、读者姓名
--输出 读者的全部个人信息
create procedure ReaderInformation
	@ReaderID varchar(8), @Name varchar(12)
AS
	select ReaderID 借书证号, Name 姓名, Sex 性别, Birth 出生日期,
	 ID 身份证号, ReadClass 借书等级, NumberLending 可借书数量,
		NumberLent 已借书数量, Department 所在部门,
		 AddressHome 家庭住址, Phone 联系方式
	from Reader
	where ReaderID = @ReaderID and Name =  @Name 

execute ReaderInformation '20162151', '杨欢欢'
drop procedure ReaderInformation





--存储过程 查询已到归还时间但未还书的读者
--输出 未还书读者的读者证号和姓名以及未归还图书书号
create procedure NoReturnBookReader
AS	
	select R.ReaderID 借书证号, R.Name 姓名, L.BookID 书号
	from LoadInformation L, Reader R
	where L.BooleanReturn = 0 and L.DateReturn is not null and L.DateReturn < GETDATE() and L.ReaderID = R.ReaderID 


drop procedure NoReturnBookReader
execute NoReturnBookReader




--存储过程 查询到期未还的图书
--输出 到期未还图书编号、书名、读者证号、读者姓名、借出日期
create procedure NoReturnBooks
AS 
	select B.BookID 图书编号, BI.BookName 书名, R.ReaderID 读者证号,
	 R.Name 读者姓名, L.DateOut 借出日期
	from LoadInformation L, BookISBN BI, Book B, Reader R
	where getdate() > L.DateReturn and L.BookID = B.BookID and B.ISBNID = BI.Id and L.ReaderID = R.ReaderID

drop procedure NoReturnBooks 
execute NoReturnBooks




--存储过程 统计某一段时间内某类图书的借阅次数
--输入 图书类别 时间段(date1, date2)
--输出 借阅次数
create procedure CountLoadBook
	@BookCategories varchar(50), @Date1 datetime, @Date2 datetime
AS
	select BookCategories 类别,  Count(BookCategories) 借阅次数
	from LoadInformation
	where BookCategories = @BookCategories and @Date1 < DateOut and DateOut < @Date2
	group by BookCategories



drop procedure CountLoadBook
execute CountLoadBook '计算机', '2018-8-31', '2018-10-31'
execute CountLoadBook '物理',  '2017-8-31', '2018-12-31'





--存储过程 统计某一段时间内所有类别图书的借阅次数
--输入 时间段(date1, date2)
--输出 借阅次数
create procedure CountLoadBooks
	 @Date1 datetime, @Date2 datetime
AS
	select BookCategories 类别,  Count(BookCategories) 借阅次数
	from LoadInformation
	where  @Date1 < DateOut and DateOut < @Date2
	group by BookCategories


drop procedure CountLoadBooks
execute CountLoadBooks  '2017-8-31', '2018-10-31'




--存储过程 读者通过对书名进行模糊查找
--输入 已知的书名
--输出 查找到的全部信息
create procedure SelectBookName
	@BookName varchar(50)
AS
	select Id 书号ISBN, BookName 书名, BookCategories 书籍类别, Author 作者,  PublishingHouse 出版社, NumberLoad 可借数量, BookIntroduce 书籍介绍
	from BookISBN
	where BookName like concat('%', @BookName, '%')





--存储过程 读者通过对书名进行模糊查找
--输入 已知的书名
--输出 书号等有关信息
execute SelectBookName '物理'
drop procedure SelectBookName

Create procedure SelectBookIDName
	@BookName varchar(50)
AS
	begin
		declare @ISBNID varchar(8)
		select @ISBNID = Id 
		from BookISBN
		where BookName like concat('%', @BookName, '%')
		select *
		from Book
		where ISBNID = @ISBNID
	end	


	
use Library
execute SelectBookIDName '物理'




--存储过程 读者通过对作者进行模糊查找
--输入 已知的作者
--输出 查找到的全部信息
create procedure SelectBookAuthor
	@Author varchar(50)
AS
	select Id 书号ISBN, BookName 书名, BookCategories 书籍类别, Author 作者, PublishingHouse 出版社, NumberLoad 可借数量, BookIntroduce 书籍介绍
	from BookISBN
	where Author like concat('%', @Author, '%')


execute SelectBookAuthor '叶'
drop procedure SelectBookAuthor





--存储过程 读者通过对作者进行模糊查找
--输入 已知的作者
--输出 书号等有关信息
create procedure SelectBookIDAuthor
	@Author varchar(50)
AS
	begin
		declare @ISBNID varchar(8)
		select @ISBNID = Id
		from BookISBN
		where Author like concat('%', @Author, '%')

		select *
		from Book
		where ISBNID = @ISBNID 
	end



--存储过程 读者通过对出版社进行模糊查找
--输入 已知的出版社名
--输出 查找到的全部信息
create procedure SelectBookPublishingHouse
	@PublishingHouse varchar(50)
AS
	select Id 书号ISBN, BookName 书名, BookCategories 书籍类别, Author 作者, PublishingHouse 出版社, NumberLoad 可借数量, BookIntroduce 书籍介绍
	from BookISBN
	where PublishingHouse like concat('%', @PublishingHouse, '%')



execute SelectBookPublishingHouse '电力'





--存储过程 读者通过对出版社进行模糊查找
--输入 已知的出版社名
--输出 书号等有关信息
create procedure SelectBookIDPublishingHouse
	@PublishingHouse varchar(50)
AS
	begin
		declare @ISBNID varchar(8)
		select @ISBNID = Id
		from BookISBN
		where PublishingHouse like concat('%', @PublishingHouse, '%')

		select *
		from Book
		where ISBNID = @ISBNID 
	end	




--存储过程 读者通过对ISBN号进行模糊查找
--输入 已知的出版社名
--输出 查找到的全部信息
create procedure SelectBookISBN
	@ISBNID varchar(8)
AS
	select Id 书号ISBN, BookName 书名, BookCategories 书籍类别, Author 作者, PublishingHouse 出版社, NumberLoad 可借数量, BookIntroduce 书籍介绍
	from BookISBN
	where Id like concat('%', @ISBNID, '%')



execute SelectBookISBN '1000'

--存储过程
--对isbn的书号进行模糊查询
create procedure SelectBookIDISBN
	@ISBNID varchar(8)
AS
	begin
		select *
		from Book
		where ISBNID like concat('%', @ISBNID, '%')
	end



execute SelectBookIDISBN '1000'
drop procedure SelectBookIDISBN




--存储过程 管理员删除书籍
--输入 ISBNID
create procedure DeleteBook
	@ISBNID varchar(8)
AS
	begin
		declare @NumberLibrary int, @NumberLoad int
		select @NumberLibrary = NumberLibrary, @NumberLoad = NumberLoad
		from BookISBN
		where id = @ISBNID

		if @NumberLibrary <> @NumberLoad
			begin
				raiserror ('书籍外借，还未归还，不可删除',16,10)
			end
		else
			begin
				delete from Book
				where ISBNID = @ISBNID
				delete from BookISBN
				where id = @ISBNID
			end
	end

drop procedure DeleteBook
execute DeleteBook '10001'




--存储过程 管理员删除读者
--输入: 读者证号
create procedure DeleteReader
	@ReaderID varchar(8)
AS
	begin 
		declare @NumberLent int
		select  @NumberLent = NumberLent
		from Reader
		where ReaderID = @ReaderID

		if  @NumberLent <> 0
			begin
				raiserror ('书籍外借，还未归还，不可删除',16, 10)
			end
		else
			begin
				delete
				from Reader
				where ReaderID = @ReaderID
			end
	end




drop procedure DeleteReader
execute DeleteReader '20160002'

--存储过程 插入读者  用于区分借书等级 输入借书证号、密码、姓名、性别、出生日期、
--身份证号、阅读等级工作部门、家庭住址、联系电话
create procedure distinguishClass
	@ReaderID varchar(8), @Password varchar(16), @Name varchar(12), @Sex varchar(2),
	@Birth datetime, @ID char(18), @ReadClass varchar(18),
	@Department varchar(20), @AddressHome varchar(20), @Phone varchar(11)
As
	begin
		declare @NumberLending int
		if @ReadClass like '本科生'
			begin
				set @NumberLending = 5
			end
		else if @ReadClass like '研究生'
			begin
				set @NumberLending = 10
			end
		else if @ReadClass like '博士'
			begin
				set @NumberLending = 15
			end
		else if @ReadClass like '教师'
		     begin
			    set @NumberLending =20
				end
			else
			begin
				raiserror('借书等级只能是本科生、研究生、博士和教师',16,10)
			end
		insert into Reader values(
			@ReaderID, @Password, @Name, @Sex, @Birth, @ID,
			@ReadClass, @NumberLending, 0, @Department, @AddressHome,
			@Phone)
	end

	drop procedure distinguishClass ;
	select *from Reader
exec distinguishClass '20151212','20151212','李华','女','1996-02-09','622824199602090488','本科生','上海电力学院','陕西','13313141314';



--存储过程 检测借书逾期 输出该读者有书逾期未还的读者证号、以及借书日期、书号
create procedure NoReturnBooksByReaderID
	@ReaderID varchar(8)
AS 
	begin transaction 
		declare @DateNumber int, @BookID varchar(8),@Price money, @DateOut datetime

		select L.ReaderID 读者证号, L.BookID 书号,L.DateOut 借书日期
		from LoadInformation L
		where getdate() > L.DateReturn and L.ReaderID = @ReaderID 

		select @BookID = L.BookID, @DateNumber = DATEDIFF(DAY, L.DateReturn, GETDATE()),@DateOut = L.DateOut
		from LoadInformation L
		where getdate() > L.DateReturn and L.ReaderID = @ReaderID 

		select @Price = BI.Price
		from BookISBN BI, Book B
		where B.BookID = @BookID and B.ISBNID = BI.Id

		update LoadInformation
		set fine = @Price * 0.01 * @DateNumber
		where ReaderID = @ReaderID and BookID = @BookID and DateOut = @DateOut
	commit transaction
	
drop procedure NoReturnBooksByReaderID
exec NoReturnBooksByReaderID  '20162151';

insert into LoadInformation values( '20160004','20001004','2018-1-6','2018-2-6',null,0,0,null)

use Library
--索引 对图书编号ISBN设定非聚集索引
create nonclustered INDEX INDEX_ISBNID on BookISBN(Id desc)
--索引 对图书编号设定非聚集索引
create nonclustered INDEX INDEX_BookID on Book(BookId desc)



use Library
select count(*) from LoadInformation where ReaderID = '20160001'
