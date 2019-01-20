package Dao;

import java.sql.ResultSet;

public class LoginDao {
	//查询账号密码;输入登录账号和登陆类型;返回密码
    public String SelectPassword(String ID, String type){
        String password = "";
        String sql = "";
        try{
            if (type.equals("读者"))
                sql = "select password from Reader where ReaderID = " + ID;
            else if (type.equals("管理员"))
                sql = "select password from UserLibrary where MID = " + ID;
            ResultSet RS = SQLHelper.executeQuery(sql);
            while(RS.next())
                password = AES.AESDncode(AES.encodeRules, RS.getString(1));
            SQLHelper.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return password;
    }
    //检查读者有没有逾期未还的书,如果有则返回TRUE
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
