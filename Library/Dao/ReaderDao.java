package Dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReaderDao {
	//设置时间格式
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //查询读者，密码是AES加密过的
    public String[][] queryReader(String id) {
        String[][] Counter = new String[2][12];
        try{
            String sql="select * from Reader where ReaderID = " + id ;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[0][0]=rs.getString(1);
                Counter[0][1]=AES.AESDncode(AES.encodeRules, rs.getString(2));
                Counter[0][2]=rs.getString(3);
                Counter[0][3]=rs.getString(4);
                Counter[0][4]=rs.getString(5);
                Counter[0][5]=rs.getString(6);
                Counter[0][6]=rs.getString(7);
                Counter[0][7]=rs.getString(8);
                Counter[0][8]=rs.getString(9);
                Counter[0][9]=rs.getString(10);
                Counter[0][10]=rs.getString(11);
                Counter[0][11]=rs.getString(12);
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //查询借书数量
    public int queryLoadBookCount(String id){
        String sql="select count(*) from LoadBookInformation where ReaderID = " + id;
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }
    //查询借书信息
    public String[][] queryLoadBook(String id) {
        int num = queryLoadBookCount(id);
        String [][] Counter=new String[num+1][6];
        try{
            String sql="select * from LoadBookInformation where ReaderID = " + id;
            int n = 0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[n][0]=rs.getString(1);
                Counter[n][1]=rs.getString(2);
                Counter[n][2]=rs.getString(3);
                Counter[n][3]=rs.getString(4);
                Counter[n][4]=rs.getString(5);
                Counter[n][5]=rs.getString(6);
                n++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //查询书籍数量
    public int queryISBNBookCount(){
        String sql="select count(id) from BookISBN";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }
    //查询书籍信息
    public String[][] queryISBNBook() {
        int nums=queryISBNBookCount();
        String [][] Counter=new String[nums+1][7];
        try{
            String sql="select * from BookISBN";
            int n=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[n][0]=rs.getString(1);
                Counter[n][1]=rs.getString(3);
                Counter[n][2]=rs.getString(2);
                Counter[n][3]=rs.getString(4);
                Counter[n][4]=rs.getString(5);
                Counter[n][5]=rs.getString(8);
                Counter[n][6]=rs.getString(10);
                n++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //查询书号数量
    public int queryBookCount(){
        String sql="select count(*) from Book";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }
    //查询书号信息
    public String[][] queryBook() {
        int nums=queryBookCount();
        String [][] Counter=new String[nums+1][3];
        try{
            String sql="select * from Book";
            int n=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[n][0]=rs.getString(1);
                Counter[n][1]=rs.getString(2);
                Counter[n][2]=rs.getString(3);
                n++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //还书，还书成功为1，否则为0
    public int ReturnBook(String readerid, String bookID) {

        String sql = "update LoadInformation " +
                "set DateReturnReal = '" + df.format(new Date()) + "', booleanReturn = 1" +
                "where DateReturnReal is null and booleanReturn = 0 and ReaderID = '" + readerid + "' and BookID = '" + bookID +
                "';";
        return SQLHelper.executeUpdate(sql);
    }
    //借书，借书成功为1，否则为0
    public int LoadBook(String readerId, String bookID) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.MONTH, 1);
        String sql = "insert into LoadInformation values('" +readerId +"','" + bookID + "','" +
                df.format(c1.getTime()) + "' , '" + df.format(c2.getTime()) + "' , null , 0, 0, null)";
        return SQLHelper.executeUpdate(sql);
    }
    //查询书籍
    public int SelectBook(String selectItem, int n) {
        int m = 0;
        String sql ="";
        try{
            if (n == 0){
                sql = "execute SelectBookName " + selectItem;
            }
            else if (n ==1 ){
                sql = "execute SelectBookISBN " + selectItem;
            }
            else if (n == 2){
                sql = "execute SelectBookAuthor " + selectItem;
            }
            else if (n == 3)
                sql = "execute SelectBookPublishingHouse " + selectItem;
            ResultSet rs = SQLHelper.executeQuery(sql);
            while (rs.next())
                m++;

        }catch (Exception e){
            e.printStackTrace();
        }

        return m;
    }
    //查询书籍数量
    public int SeelctISBNBooksCount(String selectItem, int n){
        int m = 0;
        try{
            String sql ="";
            if (n == 0){
                sql = "execute SelectBookName " + selectItem;
            }
            else if (n ==1 ){
                sql = "execute SelectBookISBN " + selectItem;
            }
            else if (n == 2){
                sql = "execute SelectBookAuthor " + selectItem;
            }
            else if (n == 3)
                sql = "execute SelectBookPublishingHouse " + selectItem;

            ResultSet rs = SQLHelper.executeQuery(sql);
            while (rs.next())
                m++;
        }catch (Exception e){
            e.printStackTrace();
        }

        return m;
    }
    //查询书籍信息
    public String[][] SelectISBNBooks(String selectItem, int n){
        int nums= SeelctISBNBooksCount(selectItem, n);
        String [][] Counter=new String[nums+1][7];
        try{
            String sql="";
            if (n == 0){
                sql = "execute SelectBookName " + selectItem;
            }
            else if (n ==1 ){
                sql = "execute SelectBookISBN " + selectItem;
            }
            else if (n == 2){
                sql = "execute SelectBookAuthor " + selectItem;
            }
            else if (n == 3)
                sql = "execute SelectBookPublishingHouse " + selectItem;
            int m=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[m][0]=rs.getString(1);
                Counter[m][1]=rs.getString(2);
                Counter[m][2]=rs.getString(3);
                Counter[m][3]=rs.getString(4);
                Counter[m][4]=rs.getString(5);
                Counter[m][5]=rs.getString(6);
                Counter[m][6]=rs.getString(7);
                m++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //通过条件查询书籍数量
    public int SelectBooksIDCount(String selectItem, int n){
        int m = 0;
        try{
            String sql ="";
            if (n == 0){
                sql = "execute SelectBookIDName " + selectItem;
            }
            else if (n ==1 ){
                sql = "execute SelectBookIDISBN " + selectItem;
            }
            else if (n == 2){
                sql = "execute SelectBookIDAuthor " + selectItem;
            }
            else if (n == 3)
                sql = "execute SelectBookIDPublishingHouse " + selectItem;
            ResultSet rs = SQLHelper.executeQuery(sql);
            while (rs.next())
               m++;
        }catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }
    //通过条件查询书籍信息
    public String[][] SelectBooksID(String selectItem, int n){
        int nums= SelectBooksIDCount(selectItem, n);
        String [][] Counter=new String[nums+1][10];
        try{
            String sql="";
            if (n == 0){
                sql = "execute SelectBookIDName " + selectItem;
            }
            else if (n ==1 ){
                sql = "execute SelectBookIDISBN " + selectItem;
            }
            else if (n == 2){
                sql = "execute SelectBookIDAuthor " + selectItem;
            }
            else if (n == 3)
                sql = "execute SelectBookIDPublishingHouse " + selectItem;
            int m=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[m][0]=rs.getString(1);
                Counter[m][1]=rs.getString(2);
                Counter[m][2]=rs.getString(3);
                m++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //重置信息
    public int ResetInformation(String selectItem, String ReaderID, int n) {
        String sql = "";
        if (n == 0){
            sql = "update Reader set Password = '" + AES.AESEncode(AES.encodeRules, selectItem).trim() + "' where ReaderID = " + ReaderID;
        }else if (n == 1){
            sql = "update Reader set AddressHome = '" + selectItem + "' where ReaderID = " + ReaderID;
        }else if (n == 2){
            sql = "update Reader set Phone = '" + selectItem + "' where ReaderID = " + ReaderID;
        }
        return SQLHelper.executeUpdate(sql);
    }
    //在借书前查询是否逾期未还
    public boolean SelectNoReturnBooksByReaderID(String ID) {
    	boolean flag = false;
    	String sql = "";
    	try{
            sql = "execute  NoReturnBooksByReaderID '" + ID + "'";
            ResultSet RS = SQLHelper.executeQuery(sql);
            while(RS.next())
                flag = true;
            SQLHelper.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}

