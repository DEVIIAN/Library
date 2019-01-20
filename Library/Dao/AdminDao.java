package Dao;

import java.sql.ResultSet;

public class AdminDao {
    /**
     *	增加读者，读者密码用AES进行加密
     * @param ReaderID
     * @param Password
     * @param Name
     * @param Sex
     * @param Birth
     * @param Id
     * @param ReadClass
     * @param NumberLending
     * @param NumberLent
     * @param Department
     * @param AddressHome
     * @param Phone
     * @return
     */
    public int addReader(String ReaderID, String Password, String Name, String Sex, String Birth,
                         String Id,String ReadClass, 
                         String Department, String AddressHome, String Phone){
        String sql="execute distinguishClass '"+ReaderID+"','"+Password+"','"+Name+"','"+Sex+
                "','"+Birth+"','"+Id+"','"+ReadClass+
                "','"+Department+"','"+AddressHome+"','"+Phone+"'";
        return SQLHelper.executeUpdate(sql);
    }

    /**
     *	增加书籍
     * @param ID
     * @param BookCategories
     * @param BookName
     * @param Author
     * @param PublishingHouse
     * @param PublicationDate
     * @param NumberLibrary
     * @param NumberLoad
     * @param Price
     * @param BookIntroduce
     * @return
     */
    public int addBook(String ID, String BookCategories, String BookName, String Author, String PublishingHouse,
                         String PublicationDate,String NumberLibrary, String NumberLoad, String Price,
                         String BookIntroduce){
        String sql="insert into BookISBN values("+ID+" ,'"+BookCategories+"' ,'"+BookName+" ','"+Author+
                " ','"+PublishingHouse+" ','"+PublicationDate+" ',"+Integer.valueOf(NumberLibrary)+" ,"+Integer.valueOf(NumberLoad)+
                " ,"+Double.valueOf(Price)+ " ,'"+BookIntroduce+"')";
        return SQLHelper.executeUpdate(sql);
    }
    /**
     * 删除读者
     */
    public int deleteReaderById(String ID) {
        String sql = "execute DeleteReader " + ID;
        return SQLHelper.executeUpdate(sql);
    }

    /**
     *	删除书籍
     * @param ID
     * @return
     */
    public int deleteBook(String ID){
        String sql = "execute DeleteBook " + ID;
        return SQLHelper.executeUpdate(sql);
    }
    /**
     * 获取当前读者数量
     * @return
     */
    public int queryReaderCount(){
        String sql="select count(ReaderID) from Reader";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }

    /**
     * 获取当前读者信息，读者的密码用AES算法解密
     */
    public String[][] queryReader(){
        int nums=queryReaderCount();
        String [][] Counter=new String[nums+1][12];
        try{
            String sql="select * from Reader";
            int n=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[n][0]=rs.getString(1);
                Counter[n][1]= rs.getString(2);
                Counter[n][2]=rs.getString(3);
                Counter[n][4]=rs.getString(5);
                Counter[n][3]=rs.getString(4);
                Counter[n][5]=rs.getString(6);
                Counter[n][6]=rs.getString(7);
                Counter[n][7]=rs.getString(8);
                Counter[n][8]=rs.getString(9);
                Counter[n][9]=rs.getString(10);
                Counter[n][10]=rs.getString(11);
                Counter[n][11]=rs.getString(12);
                n++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }

    /**
     * 重置读者密码，将读者密码与读者证号保持一致
     */
    public int ResetReaderPwdById(String id){
        String pwd = AES.AESEncode(AES.encodeRules, id);
        String sql="update Reader set Password= '" + pwd + "' where ReaderID = '" + id + "'";
        return SQLHelper.executeUpdate(sql);
    }

    /**
     * 获取当前借书信息数量
     * @return
     */
    public int queryLoadBookCount(){
        String sql="select count(*) from LoadBookInformation";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }

    /**
     * 获取当前借书信息
     * @return
     */
    public String[][] queryLoadBook() {
        int nums=queryLoadBookCount();
        String [][] Counter=new String[nums+1][6];
        try{
            String sql="select * from LoadBookInformation";
            int n=0;
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
    //获取书籍表的ISBN的书籍数量
    public int queryISBNBookCount(){
        String sql="select count(id) from BookISBN";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }
    //获取书籍表的ISBN的书籍信息
    public String[][] queryISBNBook() {
        int nums=queryISBNBookCount();
        String [][] Counter=new String[nums+1][10];
        try{
            String sql="select * from BookISBN";
            int n=0;
            ResultSet rs=SQLHelper.executeQuery(sql);
            while(rs.next()){
                Counter[n][0]=rs.getString(1);
                Counter[n][1]=rs.getString(2);
                Counter[n][2]=rs.getString(3);
                Counter[n][3]=rs.getString(4);
                Counter[n][4]=rs.getString(5);
                Counter[n][5]=rs.getString(6);
                Counter[n][6]=rs.getString(7);
                Counter[n][7]=rs.getString(8);
                Counter[n][8]=rs.getString(9);
                Counter[n][9]=rs.getString(10);
                n++;
            }
            SQLHelper.closeConnection();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return Counter;
    }
    //查询书号表的书数量
    public int queryBookCount(){
        String sql="select count(*) from Book";
        Object obj=SQLHelper.executeSingleQuery(sql);
        return Integer.parseInt(obj.toString());
    }
    //查询书号表的书信息
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
    //查询相关查询的信息
    public int SelectReaderCount(String txtSelectReader1, String txtSelectReader2, int n){
        String sql = "";
        int m = 0;
        if (n == 1){
            sql = "execute ReaderBookInformation '" + txtSelectReader1 +"' ";
        }
        else if (n == 2){
            sql = "execute ReaderInformation '" + txtSelectReader1 + "', '" + txtSelectReader2 + "'";
        }
        else if (n ==3){
            sql = "execute NoReturnBookReader ";
        }else if (n == 4){
            sql = "execute NoReturnBooks ";
        }else if ( n == 5){
            sql = "execute CountLoadBooks '" + txtSelectReader1 + "', '" + txtSelectReader2 + "'";
        }
        try{
            ResultSet rs = SQLHelper.executeQuery(sql);
            while (rs.next())
                m++;
        }catch (Exception e){
            e.printStackTrace();
        }
       return m;
    }
    //查询相关读者
    public String[][] SelectReader(String txtSelectReader1, String txtSelectReader2, int n) {
        int num = SelectReaderCount(txtSelectReader1, txtSelectReader2, n);
        String [][] Counter = new String[num][];
        String sql = "";
        try{
            if (n == 1){
                Counter = new String[num][4];
                int m = 0;
                sql = "execute ReaderBookInformation '" + txtSelectReader1 +"' ";
                ResultSet rs=SQLHelper.executeQuery(sql);
                while(rs.next()){
                    Counter[m][0]=rs.getString(1);
                    Counter[m][1]=rs.getString(2);
                    Counter[m][2]=rs.getString(3);
                    Counter[m][3]=rs.getString(4);
                    m++;
                }
                SQLHelper.closeConnection();
            }
            else if (n == 2){
                Counter = new String[num][11];
                int m = 0;
                sql = "execute ReaderInformation '" + txtSelectReader1 + "', '" + txtSelectReader2 + "'";
                ResultSet rs=SQLHelper.executeQuery(sql);
                while(rs.next()){
                    Counter[m][0]=rs.getString(1);
                    Counter[m][1]=rs.getString(2);
                    Counter[m][2]=rs.getString(3);
                    Counter[m][3]=rs.getString(4);
                    Counter[m][4]=rs.getString(5);
                    Counter[m][5]=rs.getString(6);
                    Counter[m][6]=rs.getString(7);
                    Counter[m][7]=rs.getString(8);
                    Counter[m][8]=rs.getString(9);
                    Counter[m][9]=rs.getString(10);
                    Counter[m][10]=rs.getString(11);
                    m++;
                }
                SQLHelper.closeConnection();
            }
            else if (n ==3){
                Counter = new String[num][3];
                int m = 0;
                sql = "execute NoReturnBookReader ";
                ResultSet rs=SQLHelper.executeQuery(sql);
                while(rs.next()){
                    Counter[m][0]=rs.getString(1);
                    Counter[m][1]=rs.getString(2);
                    Counter[m][2]=rs.getString(3);
                    m++;
                }
                SQLHelper.closeConnection();
            }
            else if (n == 4){
                Counter = new String[num][5];
                int m = 0;
                sql = "execute NoReturnBooks";
                ResultSet rs=SQLHelper.executeQuery(sql);
                while(rs.next()){
                    Counter[m][0]=rs.getString(1);
                    Counter[m][1]=rs.getString(2);
                    Counter[m][2]=rs.getString(3);
                    Counter[m][3]=rs.getString(4);
                    Counter[m][4]=rs.getString(5);
                    m++;
                }
                SQLHelper.closeConnection();
            }
            else if (n == 5){
                Counter = new String[num][2];
                int m = 0;
                sql = "execute CountLoadBooks  '" + txtSelectReader1 + "', '" + txtSelectReader2 + "'";
                ResultSet rs=SQLHelper.executeQuery(sql);
                while(rs.next()){
                    Counter[m][0]=rs.getString(1);
                    Counter[m][1]=rs.getString(2);
                    m++;
                }
                SQLHelper.closeConnection();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return Counter;
    }
}
