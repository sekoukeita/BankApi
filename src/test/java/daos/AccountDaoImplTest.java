package daos;

import models.Account;
import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoImplTest {

    AccountDao accountDao;
    ClientDao clientDao;

    public AccountDaoImplTest() {
        this.accountDao = new AccountDaoImpl(H2Util.url, H2Util.username, H2Util.password);
        this.clientDao = new ClientDaoImpl(H2Util.url, H2Util.username, H2Util.password);
    }



    @BeforeEach
    void setUp() {
        H2Util.createTable();
    }

    @AfterEach
    void tearDown() {
        H2Util.dropTable();
    }

    @Test
    void getAccountsIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(expectedResult.get(0).getClientId());
        accountDao.createAccount(expectedResult.get(1).getClientId());
        accountDao.createAccount(expectedResult.get(2).getClientId());
        accountDao.createAccount(expectedResult.get(3).getClientId());

        // Act
        List<Account> actualResult = accountDao.getAccounts();

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getClientAccountsIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        // List of account for the client with id = 1
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act
        List<Account> actualResult = accountDao.getClientAccounts(expectedResult.get(0).getClientId());

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void testGetClientAccountsIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act
        accountDao.updateAccountBalanceByDeposit(accounts.get(0).getClientId(), accounts.get(0).getAccountId(), 100.0);
        accountDao.updateAccountBalanceByDeposit(accounts.get(1).getClientId(), accounts.get(1).getAccountId(), 200.0);
        accountDao.updateAccountBalanceByDeposit(accounts.get(2).getClientId(), accounts.get(2).getAccountId(), 300.0);

        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(1, 1, "Checking", 100.0,
                100.0, 0.0, 0.0, true));
        expectedResult.add(new Account(2, 1, "Checking", 200.0,
                200.0, 0.0, 0.0, true));

        List<Account> actualResult = accountDao.getClientAccounts(accounts.get(0).getClientId(), 50.0, 250.0);

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getClientAccountIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        Account expectedResult = accounts.get(2);

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act
        Account actualResult = accountDao.getClientAccount(expectedResult.getClientId(), expectedResult.getAccountId());

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void createAccountIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        int expectedResult = accounts.size();


        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act
        int actualResult = accountDao.getAccounts().size();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateAccountIT() {
        //Arrange
        //List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));

        // List of  accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 0.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 0.0,
                0.0, 0.0, 0.0, true));

        Account expectedResult = new Account(1, 1, "Saving", 100.0,
                0.0, 0.0, 0.0, false);

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act
        /*accountDao.updateAccount(accounts.get(0).getAccountId(),accounts.get(0).getClientId(),
                accounts.get(0).getCategory(),accounts.get(0).getBalance(),accounts.get(0).getDeposit(),
                accounts.get(0).getWithdraw(),accounts.get(0).getTransfer(),accounts.get(0).getAccount_is_Active());*/
        accountDao.updateAccount(1,1,
                "saving",100.0,0.0,
                0.0,0.0,false);
        Account actualResult = accountDao.getClientAccount(expectedResult.getClientId(), expectedResult.getAccountId());

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void updateAccountCategoryIT() {
    }

    @Test
    void updateAccountBalanceByDepositIT() {
    }

    @Test
    void updateAccountBalanceByWithdrawIT() {
    }

    @Test
    void updateAccountStatusIT() {
    }

    @Test
    void updateAccountsBalanceByTransferIT() {
    }

    @Test
    void deleteAccountIT() {
    }
}