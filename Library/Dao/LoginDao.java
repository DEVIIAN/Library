package Dao;

import java.sql.ResultSet;

public class LoginDao {
	//��ѯ�˺�����;�����¼�˺ź͵�½����;��������
    public String SelectPassword(String ID, String type){
        String password = "";
        String sql = "";
        try{
            if (type.equals("����"))
                sql = "select password from Reader where ReaderID = " + ID;
            else if (type.equals("����Ա"))
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
    //��������û������δ������,������򷵻�TRUE
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
