package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {

    //connection strings
    public static String url = "jdbc:h2:./h2/db"; // where the H2 database will be stored(./h2/db)
    public static String username = "sa";
    public static String password = "sa";

    // no constructor because the class variables and method will be called on a class scope

    // Methods
    public static void createTable(){
        try(Connection conn = DriverManager.getConnection(url, username, password);){
            String sql = "CREATE TABLE clients(\n" +
                    "\tclient_id serial PRIMARY KEY,\n" +
                    "\tclient_name varchar(20) NOT NULL \n" +
                    ");" +
                    "CREATE TABLE accounts(\n" +
                    "\taccount_id serial PRIMARY KEY,\n" +
                    "\tclient_id int,\n" +
                    "\taccount_category varchar(20) NOT NULL DEFAULT 'Checking',\n" +
                    "\taccount_balance double PRECISION NOT NULL DEFAULT 0,\n" +
                    "\taccount_deposit double PRECISION NOT NULL DEFAULT 0,\n" +
                    "\taccount_withdraw double PRECISION NOT NULL DEFAULT 0,\n" +
                    "\taccount_transfert double PRECISION NOT NULL DEFAULT 0,\n" +
                    "\taccount_isactive boolean NOT NULL DEFAULT TRUE,\n" +
                    "\tFOREIGN KEY(client_id) REFERENCES clients(client_id)\n" +
                    ");";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void dropTable(){
        try(Connection conn = DriverManager.getConnection(url, username, password);){
            String sql = "DROP TABLE accounts;" +
                    "DROP TABLE clients";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}

