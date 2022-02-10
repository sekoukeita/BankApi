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
    void getClientAccounts3ArgumentsIT() {
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

        // List of account for the client with id = 1 with balance > 100 and  balance < 300
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(2, 1, "Checking", 200.0,
                200.0, 0.0, 0.0, true));

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // update the balances of client's 1 accounts
        accountDao.updateAccountBalanceByDeposit(accounts.get(0).getClientId(), accounts.get(0).getAccountId(), 100.0);
        accountDao.updateAccountBalanceByDeposit(accounts.get(1).getClientId(), accounts.get(1).getAccountId(), 200.0);
        accountDao.updateAccountBalanceByDeposit(accounts.get(2).getClientId(), accounts.get(2).getAccountId(), 300.0);

        // Act
        List<Account> actualResult = accountDao.getClientAccounts(accounts.get(0).getClientId(), 100.0, 300.0);

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

        Account expectedResult = new Account(1, 1, "Saving", 500.0,
                3000.0, 200.0, 100.0, false);

        // Populate tables  with the  clients and accounts
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));

        accountDao.createAccount(accounts.get(0).getClientId());
        accountDao.createAccount(accounts.get(1).getClientId());
        accountDao.createAccount(accounts.get(2).getClientId());
        accountDao.createAccount(accounts.get(3).getClientId());

        // Act

        // update the account 1 for the client 1 to match the expected result
        accountDao.updateAccount(expectedResult.getClientId(), expectedResult.getAccountId(), expectedResult.getCategory(),
                expectedResult.getBalance(), expectedResult.getDeposit(), expectedResult.getWithdraw(), expectedResult.getTransfer(),
                expectedResult.getAccount_is_Active());
        Account actualResult = accountDao.getClientAccount(expectedResult.getClientId(), expectedResult.getAccountId());

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteAccountIT() {
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
        accountDao.deleteAccount(accounts.get(0).getClientId(), accounts.get(0).getAccountId());
        Account actualResult = accountDao.getClientAccount(accounts.get(0).getClientId(), accounts.get(0).getAccountId());

        //Assert
        assertNull(actualResult);
    }
}