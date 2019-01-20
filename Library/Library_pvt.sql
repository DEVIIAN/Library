

use Library
go
--������ �����鼮
--���ݹݲ�������ͼ����
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
			raiserror('�����鼮���ܳ���999��', 16, 10)
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
			raiserror('�����鼮��������1��', 16, 10)
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

--������ ����
create trigger LoadBook
	on LoadInformation
	with encryption
	after insert
AS
	declare @BookID varchar(8), @ISBNID varchar(8), @ReaderID varchar(8), @BooleanLoad bit, @NumberLending int, @NumberLent int, @BookCategories varchar(50)
	select @BookID = BookID, @ReaderID = ReaderID
	from inserted

	begin transaction   --ͼ�����

		select @NumberLending = NumberLending, @NumberLent = NumberLent
		from Reader
		where ReaderID = @ReaderID

		if @NumberLent >= @Numberlending
			begin
			rollback
			raiserror('������', 16, 10)
			end
		else
			begin

			select @BooleanLoad = BooleanLoad
			from Book
			where BookID = @BookID

			

			if @BooleanLoad = 0
				begin
				rollback
				raiserror('�Ȿ���Ѿ����', 16, 10)
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
values('20162151', '10001002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '�����')
insert into LoadInformation
values('20162151', '10002002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '�����')
insert into LoadInformation
values('20160002', '30001001', '2017-9-1', DATEADD(MONTH, 1, '2017-9-1'), null, 0, null, '����')
insert into LoadInformation
values('20160002', '10002001',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '�����')
insert into LoadInformation
values('20160001', '10001002',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '�����')
insert into LoadInformation
values('20160002', '10001001',getdate(),dateadd(MONTH, 1, GETDATE()),null,0,null, '�����')

drop trigger ReturnBook
go
--������ ����
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
				raiserror('�Ȿ���ѻ�', 16, 10)	
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

--��ͼ ���߿�ͼ����Ϣ
create view BookISBN_Reader
AS
	select Id ���ISBN, BookName ����,BookCategories �鼮���, Author ����, NumberLoad �ɽ�����, BookIntroduce �鼮���� 
	from BookISBN
	with check option






--drop view BookISBN_Reader
--��ͼ ����Ա��ͼ����Ϣ
create view BookISBN_Admin
As
	select Id ���ISBN, BookName ����,BookCategories �鼮���, Author ����,  NumberLibrary ͼ��ݱ�������,  NumberLoad �ɽ�����, Price �鼮�۸�, BookIntroduce �鼮���� 
	from BookISBN
	with check option



drop view BookISBN_Admin
select * from BookISBN_Reader
select * from BookISBN_Admin







--��ͼ ��ѯ������Ϣ
create view LoadBookInformation
AS
	select ReaderID , BookID , DateOut , DateReturn , BooleanReturn ,Fine
	from LoadInformation
	with check option

	
	
drop view LoadBookInformation
select * from LoadBookInformation where ReaderID = '20160001'
select count(*) from LoadBookInformation
drop view LoadBookInformation






--��ͼ ��ѯ������Ϣ
create view ReaderView
As
	select ReaderID , Name , ReadClass , NumberLending , NumberLent 
	from Reader
	with check option



drop view ReaderView
select * from ReaderView where ReaderID = '20160001'






--��ͼ ��ѯ����δ����ͼ��
--��� ����δ��ͼ���š�����������֤�š������������������
create view NoReturnBooksView
AS 
	select B.BookID , BI.BookName , R.ReaderID , R.Name , L.DateOut 
	from LoadInformation L, BookISBN BI, Book B, Reader R
	where getdate() > L.DateReturn and L.BookID = B.BookID and B.ISBNID = BI.Id and L.ReaderID = R.ReaderID
	with check option


select * from NoReturnBooksView
drop view NoReturnBooksView
execute NoReturnBook '30001001'




--�洢���� ��ѯ�����ѽ�����Ϣ 
--���� ����֤��
--��� �黹��ͼ���ź����� 
create procedure ReaderBookInformation
	@ReaderID varchar(8)
AS
	select B.BookID ͼ����, BI.BookName ����, L.ReaderID ����֤��, B.BooleanLoad �Ƿ���
	from LoadInformation L, BookISBN BI, Book B
	where L.ReaderID = @ReaderID and L.BookId = B.BookID and B.ISBNID = BI.Id



execute ReaderBookInformation '20160001'
drop procedure ReaderBookInformation





--�洢���� ��ѯ���߸�����Ϣ
--���� ����֤�š���������
--��� ���ߵ�ȫ��������Ϣ
create procedure ReaderInformation
	@ReaderID varchar(8), @Name varchar(12)
AS
	select ReaderID ����֤��, Name ����, Sex �Ա�, Birth ��������,
	 ID ���֤��, ReadClass ����ȼ�, NumberLending �ɽ�������,
		NumberLent �ѽ�������, Department ���ڲ���,
		 AddressHome ��ͥסַ, Phone ��ϵ��ʽ
	from Reader
	where ReaderID = @ReaderID and Name =  @Name 

execute ReaderInformation '20162151', '���'
drop procedure ReaderInformation





--�洢���� ��ѯ�ѵ��黹ʱ�䵫δ����Ķ���
--��� δ������ߵĶ���֤�ź������Լ�δ�黹ͼ�����
create procedure NoReturnBookReader
AS	
	select R.ReaderID ����֤��, R.Name ����, L.BookID ���
	from LoadInformation L, Reader R
	where L.BooleanReturn = 0 and L.DateReturn is not null and L.DateReturn < GETDATE() and L.ReaderID = R.ReaderID 


drop procedure NoReturnBookReader
execute NoReturnBookReader




--�洢���� ��ѯ����δ����ͼ��
--��� ����δ��ͼ���š�����������֤�š������������������
create procedure NoReturnBooks
AS 
	select B.BookID ͼ����, BI.BookName ����, R.ReaderID ����֤��,
	 R.Name ��������, L.DateOut �������
	from LoadInformation L, BookISBN BI, Book B, Reader R
	where getdate() > L.DateReturn and L.BookID = B.BookID and B.ISBNID = BI.Id and L.ReaderID = R.ReaderID

drop procedure NoReturnBooks 
execute NoReturnBooks




--�洢���� ͳ��ĳһ��ʱ����ĳ��ͼ��Ľ��Ĵ���
--���� ͼ����� ʱ���(date1, date2)
--��� ���Ĵ���
create procedure CountLoadBook
	@BookCategories varchar(50), @Date1 datetime, @Date2 datetime
AS
	select BookCategories ���,  Count(BookCategories) ���Ĵ���
	from LoadInformation
	where BookCategories = @BookCategories and @Date1 < DateOut and DateOut < @Date2
	group by BookCategories



drop procedure CountLoadBook
execute CountLoadBook '�����', '2018-8-31', '2018-10-31'
execute CountLoadBook '����',  '2017-8-31', '2018-12-31'





--�洢���� ͳ��ĳһ��ʱ�����������ͼ��Ľ��Ĵ���
--���� ʱ���(date1, date2)
--��� ���Ĵ���
create procedure CountLoadBooks
	 @Date1 datetime, @Date2 datetime
AS
	select BookCategories ���,  Count(BookCategories) ���Ĵ���
	from LoadInformation
	where  @Date1 < DateOut and DateOut < @Date2
	group by BookCategories


drop procedure CountLoadBooks
execute CountLoadBooks  '2017-8-31', '2018-10-31'




--�洢���� ����ͨ������������ģ������
--���� ��֪������
--��� ���ҵ���ȫ����Ϣ
create procedure SelectBookName
	@BookName varchar(50)
AS
	select Id ���ISBN, BookName ����, BookCategories �鼮���, Author ����,  PublishingHouse ������, NumberLoad �ɽ�����, BookIntroduce �鼮����
	from BookISBN
	where BookName like concat('%', @BookName, '%')





--�洢���� ����ͨ������������ģ������
--���� ��֪������
--��� ��ŵ��й���Ϣ
execute SelectBookName '����'
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
execute SelectBookIDName '����'




--�洢���� ����ͨ�������߽���ģ������
--���� ��֪������
--��� ���ҵ���ȫ����Ϣ
create procedure SelectBookAuthor
	@Author varchar(50)
AS
	select Id ���ISBN, BookName ����, BookCategories �鼮���, Author ����, PublishingHouse ������, NumberLoad �ɽ�����, BookIntroduce �鼮����
	from BookISBN
	where Author like concat('%', @Author, '%')


execute SelectBookAuthor 'Ҷ'
drop procedure SelectBookAuthor





--�洢���� ����ͨ�������߽���ģ������
--���� ��֪������
--��� ��ŵ��й���Ϣ
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



--�洢���� ����ͨ���Գ��������ģ������
--���� ��֪�ĳ�������
--��� ���ҵ���ȫ����Ϣ
create procedure SelectBookPublishingHouse
	@PublishingHouse varchar(50)
AS
	select Id ���ISBN, BookName ����, BookCategories �鼮���, Author ����, PublishingHouse ������, NumberLoad �ɽ�����, BookIntroduce �鼮����
	from BookISBN
	where PublishingHouse like concat('%', @PublishingHouse, '%')



execute SelectBookPublishingHouse '����'





--�洢���� ����ͨ���Գ��������ģ������
--���� ��֪�ĳ�������
--��� ��ŵ��й���Ϣ
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




--�洢���� ����ͨ����ISBN�Ž���ģ������
--���� ��֪�ĳ�������
--��� ���ҵ���ȫ����Ϣ
create procedure SelectBookISBN
	@ISBNID varchar(8)
AS
	select Id ���ISBN, BookName ����, BookCategories �鼮���, Author ����, PublishingHouse ������, NumberLoad �ɽ�����, BookIntroduce �鼮����
	from BookISBN
	where Id like concat('%', @ISBNID, '%')



execute SelectBookISBN '1000'

--�洢����
--��isbn����Ž���ģ����ѯ
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




--�洢���� ����Աɾ���鼮
--���� ISBNID
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
				raiserror ('�鼮��裬��δ�黹������ɾ��',16,10)
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




--�洢���� ����Աɾ������
--����: ����֤��
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
				raiserror ('�鼮��裬��δ�黹������ɾ��',16, 10)
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

--�洢���� �������  �������ֽ���ȼ� �������֤�š����롢�������Ա𡢳������ڡ�
--���֤�š��Ķ��ȼ��������š���ͥסַ����ϵ�绰
create procedure distinguishClass
	@ReaderID varchar(8), @Password varchar(16), @Name varchar(12), @Sex varchar(2),
	@Birth datetime, @ID char(18), @ReadClass varchar(18),
	@Department varchar(20), @AddressHome varchar(20), @Phone varchar(11)
As
	begin
		declare @NumberLending int
		if @ReadClass like '������'
			begin
				set @NumberLending = 5
			end
		else if @ReadClass like '�о���'
			begin
				set @NumberLending = 10
			end
		else if @ReadClass like '��ʿ'
			begin
				set @NumberLending = 15
			end
		else if @ReadClass like '��ʦ'
		     begin
			    set @NumberLending =20
				end
			else
			begin
				raiserror('����ȼ�ֻ���Ǳ��������о�������ʿ�ͽ�ʦ',16,10)
			end
		insert into Reader values(
			@ReaderID, @Password, @Name, @Sex, @Birth, @ID,
			@ReadClass, @NumberLending, 0, @Department, @AddressHome,
			@Phone)
	end

	drop procedure distinguishClass ;
	select *from Reader
exec distinguishClass '20151212','20151212','�','Ů','1996-02-09','622824199602090488','������','�Ϻ�����ѧԺ','����','13313141314';



--�洢���� ���������� ����ö�����������δ���Ķ���֤�š��Լ��������ڡ����
create procedure NoReturnBooksByReaderID
	@ReaderID varchar(8)
AS 
	begin transaction 
		declare @DateNumber int, @BookID varchar(8),@Price money, @DateOut datetime

		select L.ReaderID ����֤��, L.BookID ���,L.DateOut ��������
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
--���� ��ͼ����ISBN�趨�Ǿۼ�����
create nonclustered INDEX INDEX_ISBNID on BookISBN(Id desc)
--���� ��ͼ�����趨�Ǿۼ�����
create nonclustered INDEX INDEX_BookID on Book(BookId desc)



use Library
select count(*) from LoadInformation where ReaderID = '20160001'
