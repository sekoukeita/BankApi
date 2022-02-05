import Services.AccountService;
import controllers.AccountController;
import daos.AccountDao;
import daos.AccountDaoImpl;
import models.Account;
import util.H2Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //ClientDao clientDao = new ClientDaoImpl();
       // AccountDao accountDao = new AccountDaoImpl();

        //Account account = accountDao.getClientAccount(1, 1);

        //accountDao.createAccount(1);
        //accountDao.updateAccount(6, 5, "Checking", 500.50, 0.0, 0.0, 0.0, true);

        //accountDao.updateAccountsBalanceByTransfer(1,3,1,500.0);

        //accountDao.deleteAccount(1,3);

                                   // ClientService Test
        //ClientService cs = new ClientService();
        /*List<Client> clients = cs.getClients();
        System.out.println(clients);*/

        //cs.createClient(new Client("Ali Toure"));

        //cs.updateClient(9,"Sekou keita");

        //cs.deleteClient(9);

                  //AccountService Test
        AccountDao ad = new AccountDaoImpl();
        AccountService as = new AccountService();
       AccountController ac = new AccountController();
           // List<Account> accounts = as.getClientAccounts(2);
        //System.out.println(accounts);

        /*List<Account> accounts = as.getAccounts();
        System.out.println(accounts);*/

        /*List<Account> accounts = as.getClientAccounts(2);
        System.out.println(accounts);*/

        /*List<Account> accounts = as.getClientAmountSelectedAccounts(2,2000.0,3000.0);
        System.out.println(accounts);*/


        //System.out.println(b);

        //as.createAccount(1);

        //ac.updateAccountBalanceByWithdraw(1,7,500.0);

       //Boolean b = as.updateAccountsBalanceByTransfer(1,2,1, 500.0);
       //as.updateAccountCategory(1,1,"Checking");

       // as.updateAccountBalanceByWithdraw(5,6,500.5);

        //as.updateAccountStatus(2,9,true);

        //as.updateAccountsBalanceByTransfer(2,7,2,1000.0);

        //as.deleteAccount(1,1);

        //List<Integer> l = new ArrayList<>();

       // List<Account> accounts = ad.getClientAccounts(1, 1000.0, 4000.0)
        // System.out.println(accounts);

        //H2Util.createTable();
        //H2Util.dropTable();













    }
}
