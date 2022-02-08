package daos;

import models.Client;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao{

                 // MEMBER VARIABLES: Strings needed for jdbc connection
    String url;
    String username;
    String password;

    // create the logger object to log events in the file project_0.log
    Logger logger = Logger.getLogger(ClientDaoImpl.class);


                 // CONSTRUCTORS
    public ClientDaoImpl() {
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/bank_db";
        this.username = System.getenv("RDS_USERNAME");;
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ClientDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
                 // METHOD IMPLEMENTATIONS
    @Override
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM clients;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                clients.add(new Client(rs.getInt(1), rs.getString(2)));
            }
        }
        catch(SQLException e){
           // e.printStackTrace();
            logger.error(e); // implement logging for sql exceptions
        }
        return clients;
    }

    @Override
    public Client getClient(Integer clientId) {
        Client client = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,clientId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Client(rs.getInt(1), rs.getString(2));
            }
        }
        catch(SQLException e){
            logger.error(e);
        }
        return client;
    }

    @Override
    public void createClient(Client client) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO clients VALUES (DEFAULT, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getClientName()); // get the client name from the client provided and set it into the sql
            ps.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
        }
    }

    @Override
    public void updateClient(Integer clientId, String clientNewName) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE clients SET client_name = ? WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, clientNewName);
            ps.setInt(2, clientId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
        }
    }

    @Override
    public void deleteClient(Integer clientId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            // Delete the clients' accounts before deleting the client.
            String sql = "DELETE FROM accounts WHERE client_id = ?;DELETE FROM clients WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, clientId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
        }
    }
}
