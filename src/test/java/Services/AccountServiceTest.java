package Services;

import daos.AccountDao;
import daos.ClientDao;
import models.Account;
import models.Client;
import org.checkerframework.checker.units.qual.C;
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
        this.accountService = new AccountService(accountDao);
        this.clientService = new ClientService(clientDao);
    }

    // Mocking the clientDao object. Does not work when done in the AccountServiceTest constructor.
    //ClientService clientService = Mockito.mock(ClientService.class);

    @Test
    void getClientAccountsReturnsAccounts() {
        // Arrange
        // List of all clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Gamma"));


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

        // The client
        Client client = clients.get(0);

        // List of accounts of the client
        List<Account> expectedResult = new ArrayList<>();
        for(Account account : accounts){
            if(account.getClientId().equals(client.getClientId())){
                expectedResult.add(account);
            }
        }

        /* To review later:
        * the clientDao object is not mocked while the accountDao object is.
       */

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of accounts to the method get getClientIdsListInAccount()
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);
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
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount()
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);
        // The Dao is not run in this part of the method.
        Mockito.when(accountDao.getClientAccounts(client.getClientId())).thenReturn(expectedResult);

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
        Client client = clients.get(0);
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
        for(Account account : accounts){
            if(account.getClientId().equals(client.getClientId()) && (account.getBalance() > minBalance) && (account.getBalance() < maxBalance)){
                expectedResult.add(account);
            }
        }

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount()
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);
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
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount()
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

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
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);
        Mockito.when(accountDao.getClientAccount(expectedResult.getClientId(), expectedResult.getAccountId())).thenReturn(expectedResult);

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
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

        //Act
        Account actualResult = accountService.getClientAccount(client.getClientId(), 4); // any number for the account id.
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


       // The client for which the account will be created
       Client client = clients.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);

        //Act
       Boolean actualResult = accountService.createAccount(client.getClientId());
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

        // The client for which the account will be created (client not in the list of clients)
        Client client = new Client(4, "Sekou");


        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = accountService.createAccount(client.getClientId());
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

        // Let's update the account with id = 1 for the client with id = 1
        Account account = accounts.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

        //Act
        Boolean actualResult = accountService.updateAccount(account.getAccountId(), account.getClientId(), "Saving",
                1000.0, 0.0, 0.0, 0.0, false);
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

        // Let's try to update an account not existing in the list of accounts.
        Account account = new Account(5, 1, "Checking", 400.0,
                0.0, 0.0, 0.0, true);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

        //Act
        Boolean actualResult = accountService.updateAccount(account.getAccountId(), account.getClientId(), "Saving",
                1000.0, 0.0, 0.0, 0.0, false);
        //assert
        assertFalse(actualResult);
    }

    @Test
    void deleteAccountReturnsTrue() {
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

        // Let's delete the account with id = 1 for the client with id = 1
        Account account = accounts.get(0);

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

        //Act
        Boolean actualResult = accountService.deleteAccount(account.getClientId(), account.getAccountId());
        //assert
        assertTrue(actualResult);
    }

    @Test
    void deleteAccountReturnsFalse() {
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

        // Let's try to delete an account that not belongs to the client 1
        Integer clientId = 1;
        Integer accountId = 4;

        // Return the list of clients to the method getClientIdList()
        Mockito.when(clientDao.getClients()).thenReturn(clients);
        //Return the list of account to the method get getClientIdsListInAccount() and getClientAccountsIdsList(clientId)
        Mockito.when(accountDao.getAccounts()).thenReturn(accounts);

        //Act
        Boolean actualResult = accountService.deleteAccount(clientId, accountId);
        //assert
        assertFalse(actualResult);
    }
}