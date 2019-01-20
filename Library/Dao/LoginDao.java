package Dao;

import java.sql.ResultSet;

public class LoginDao {

    public String SelectPassword(String ID, String type){
        String password = "";
        String sql = "";
        try{
            if (type.equals("读者"))
                sql = "select password from Reader where ReaderID = " + ID;
            else if (type.equals("管理员"))
                sql = "select password from UserLibrary where ID = " + ID;
            ResultSet RS = SQLHelper.executeQuery(sql);
            while(RS.next())
                password = RS.getString(1);
            SQLHelper.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return password;
    }
}
