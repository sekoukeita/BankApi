package daos;

import models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao{

                // MEMBER VARIABLES: Strings needed for jdbc connection
    String url;
    String username;
    String password;

                // CONSTRUCTORS
    public AccountDaoImpl() {
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/bank_db";
        this.username = System.getenv("RDS_USERNAME");;
        this.password = System.getenv("RDS_PASSWORD");
    }

    public AccountDaoImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

                 // METHOD IMPLEMENTATIONS
    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){ // use the constructor with all arguments to get values from each record of the result set
                accounts.add(new Account(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getDouble(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDouble(7), rs.getBoolean(8)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> getClientAccounts(Integer clientId) {
        List<Account> accounts = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts WHERE client_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                accounts.add(new Account(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getDouble(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDouble(7), rs.getBoolean(8)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> getClientAccounts(Integer clientId, Double minBalance, Double maxBalance) {
        List<Account> accounts = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_balance > ? AND account_balance < ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setDouble(2, minBalance);
            ps.setDouble(3, maxBalance);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                accounts.add(new Account(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getDouble(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDouble(7), rs.getBoolean(8)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getClientAccount(Integer clientId, Integer accountId) {
        Account account = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                account = new Account(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getDouble(4), rs.getDouble(5), rs.getDouble(6),
                        rs.getDouble(7), rs.getBoolean(8));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void createAccount(Integer clientId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            // On the database side, the sql statement needs only the client id. The rest are default.
            String sql = "INSERT INTO accounts VALUES (DEFAULT, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Integer accountId, Integer clientId, String category,
                              Double balance, Double deposit, Double withdraw, Double transfer, Boolean isActive) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE accounts \n" +
                    "SET client_id = ?,\n" +
                    "\taccount_category = ?,\n" +
                    "\taccount_balance = ?,\n" +
                    "\taccount_deposit = ?,\n" +
                    "\taccount_withdraw = ?,\n" +
                    "\taccount_transfer = ?,\n" +
                    "\taccount_is_active = ? \n" +
                    "WHERE account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setString(2, category);
            ps.setDouble(3, balance);
            ps.setDouble(4, deposit);
            ps.setDouble(5, withdraw);
            ps.setDouble(6, transfer);
            ps.setBoolean(7, isActive);
            ps.setInt(8, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountCategory(Integer clientId, Integer accountId, String category) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE accounts SET account_category = ? WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category);
            ps.setInt(2, clientId);
            ps.setInt(3, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountBalanceByDeposit(Integer clientId, Integer accountId, Double deposit) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            /*
            * There are 2 sql statements:
            *   1. updates the deposit value of the specific account
            *   2. updates the balance value of the specific account
            * */
            String sql = "UPDATE accounts \n" +
                    "SET account_deposit = ?\n" +
                    "WHERE client_id = ? AND account_id = ?;\n" +
                    "\n" +
                    "UPDATE accounts \n" +
                    "SET account_balance = account_balance + account_deposit \n" +
                    "WHERE client_id = ? AND account_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, deposit);
            ps.setInt(2, clientId);
            ps.setInt(3, accountId);
            ps.setInt(4, clientId);
            ps.setInt(5, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountBalanceByWithdraw(Integer clientId, Integer accountId, Double withdraw) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            /*
             * There are 2 sql statements:
             *   1. updates the withdrawal value of the specific account
             *   2. updates the balance value of the specific account
             * */
            String sql = "UPDATE accounts \n" +
                    "SET account_withdraw = ?\n" +
                    "WHERE client_id = ? AND account_id = ?;\n" +
                    "\n" +
                    "UPDATE accounts \n" +
                    "SET account_balance = account_balance - account_withdraw \n" +
                    "WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, withdraw);
            ps.setInt(2, clientId);
            ps.setInt(3, accountId);
            ps.setInt(4, clientId);
            ps.setInt(5, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountStatus(Integer clientId, Integer accountId, Boolean isActive) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE accounts SET account_is_active = ? WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isActive);
            ps.setInt(2, clientId);
            ps.setInt(3, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccountsBalanceByTransfer(Integer clientId, Integer accountFromId, Integer accountToId, Double transferAmount) {
        // Updates the transfer value for the account from.
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE accounts SET account_transfer = ? WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, transferAmount);
            ps.setInt(2, clientId);
            ps.setInt(3, accountFromId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        updateAccountBalanceByWithdraw(clientId, accountFromId, transferAmount); // For the account from
        updateAccountBalanceByDeposit(clientId, accountToId, transferAmount); // For the account to
    }

    @Override
    public void deleteAccount(Integer clientId, Integer accountId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM accounts WHERE client_id = ? AND account_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ps.setInt(2, accountId);

            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
