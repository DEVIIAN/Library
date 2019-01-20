import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
    public static void main(String args[]) {
        // Create a variable for the connection string.
//        String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=DBLinkTest;integratedSecurity=true;";

        String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=Library;user=sa;password=L741025";//sa身份连接

//        String url2 = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DBLinkTest;integratedSecurity=true;";//windows集成模式连接

        // Declare the JDBC objects.
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            System.out.println("begin.");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url);
            System.out.println("end.");

            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT  * FROM Reader";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " );
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                }
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (Exception e) {
                }
        }
    }
}
