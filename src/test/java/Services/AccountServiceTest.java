package Services;

import daos.AccountDao;
import daos.ClientDao;
import models.Account;
import models.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
              // MEMBER VARIABLES
    AccountDao accountDao = Mockito.mock(AccountDao.class);
    ClientDao clientDao = Mockito.mock(ClientDao.class);
    AccountService accountService;
    ClientService clientService;

                // CONSTRUCTORS

    public AccountServiceTest() {
        // The test can reference (and mock) both account and client dao objects.
        this.accountService = new AccountService(accountDao);
        this.clientService = new ClientService(clientDao);
    }

    @Test
    void getClientAccountsReturnsAccounts() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Gamma"));

        // The client
        Client client = clients.get(0);

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // List of accounts of the client with id 1
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);
        Mockito.when(accountDao.getClientAccounts(client.getClientId())).thenReturn(expectedResult);

        //Act
        List<Account> actualResult = accountService.getClientAccounts(client.getClientId());
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientAccountsReturnsEmptyList() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Gamma"));

        // The client
        Client client = clients.get(2); // client id = 3

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // client with id = 3 does not have any account. An empty list is returned.
        List<Account> expectedResult = new ArrayList<>();

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);
        // The Dao is not run in this part of the method.
        //Mockito.when(accountDao.getClientAccounts(client.getClientId())).thenReturn(expectedResult);

        //Act
        List<Account> actualResult = accountService.getClientAccounts(client.getClientId());
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientAccountsReturnAccounts() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Gamma"));

        // The client
        Client client = clients.get(1);
        Double minBalance = 50.0;
        Double maxBalance = 250.0;

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // List of accounts of the client matching the criteria of balance
        List<Account> expectedResult = new ArrayList<>();
        expectedResult.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        expectedResult.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);
        Mockito.when(accountDao.getClientAccounts(client.getClientId(), minBalance, maxBalance)).thenReturn(expectedResult);

        //Act
        List<Account> actualResult = accountService.getClientAccounts(client.getClientId(), minBalance, maxBalance);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientAccountsReturnEmptyList() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // The client
        Client client = clients.get(2); // client with id = 3
        Double minBalance = 50.0;
        Double maxBalance = 250.0;

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // client with id = 3 does not have any account. An empty list is returned.
        List<Account> expectedResult = new ArrayList<>();

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);

        //Act
        List<Account> actualResult = accountService.getClientAccounts(client.getClientId(), minBalance, maxBalance);

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientAccountReturnsAccount() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's get account id 1 for the client id 1
        Account expectedResult = accounts.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);
        Mockito.when(clientService.getClients()).thenReturn(clients);
        Mockito.when(accountDao.getClientAccount(clients.get(0).getClientId(), expectedResult.getAccountId())).thenReturn(expectedResult);

        //Act
        Account actualResult = accountService.getClientAccount(expectedResult.getClientId(), expectedResult.getAccountId());
        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientAccountReturnsNull() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // The client with id = 3 does not have any account
        Client client = clients.get(2);
        Account expectedResult = null;

        // Return the list of clients to the method getClientIdList()
        Mockito.when(accountService.getAccounts()).thenReturn(accounts);

        //Act
        Account actualResult = accountService.getClientAccount(client.getClientId(), 5); // any number for the account id.
        //assert
        assertNull(actualResult);
    }

    @Test
    void createAccountReturnsTrue() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's create an account for the client with id = 1.
        Account account = new Account(5,  1, "Checking", 500.0,
                0.0, 0.0, 0.0, true);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
       Boolean actualResult = accountService.createAccount(account.getClientId());
        //assert
        assertTrue(actualResult);

    }

    @Test
    void createAccountReturnsFalse() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 2, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's create an account for the client with id = 5 who is not in the list of client.
        Account account = new Account(6,  5, "Checking", 500.0,
                0.0, 0.0, 0.0, true);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = accountService.createAccount(account.getClientId());
        //assert
        assertFalse(actualResult);

    }

    @Test
    void updateAccountReturnsTrue() {

        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 1, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's update the account for the client with id = 1
        Account account = accounts.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientService.getClients()).thenReturn(clients);
        //Mockito.when(accountDao.updateAccount(account.getAccountId(), account.getClientId(), account.getCategory(),
               // account.getBalance(), account.getDeposit(), account.getWithdraw(), account.getTransfer(), account.getAccount_is_Active()))

        //Act
        Boolean actualResult = accountService.updateAccount(account.getAccountId(), account.getClientId(), account.getCategory(),
                account.getBalance(), account.getDeposit(), account.getWithdraw(), account.getTransfer(), account.getAccount_is_Active());
        //assert
        assertTrue(actualResult);


    }
    @Test
    void updateAccountReturnsFalse() {

        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 1, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's update the account for the client who does not exist in the client list
        Account account = new Account(4, 4, "Checking", 400.0,
                0.0, 0.0, 0.0, true);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = accountService.updateAccount(account.getAccountId(), account.getClientId(), account.getCategory(),
                account.getBalance(), account.getDeposit(), account.getWithdraw(), account.getTransfer(), account.getAccount_is_Active());
        //assert
        assertFalse(actualResult);
    }

    @Test
    void updateAccountBalanceByDepositReturnTrue() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // List of all accounts
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, 1, "Checking", 100.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(2, 1, "Checking", 200.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(3, 1, "Checking", 300.0,
                0.0, 0.0, 0.0, true));
        accounts.add(new Account(4, 1, "Checking", 400.0,
                0.0, 0.0, 0.0, true));

        // Let's update the account deposit for the client with id = 1
        Account account = accounts.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = accountService.updateAccountBalanceByDeposit(account.getClientId(), account.getAccountId(),
                account.getDeposit());
        //assert
        assertTrue(actualResult);


    }

    @Test
    void updateAccountBalanceByWithdraw() {
    }

    @Test
    void updateAccountsBalanceByTransfer() {
    }

    @Test
    void deleteAccount() {
    }
}