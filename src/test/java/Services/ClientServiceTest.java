package Services;

import daos.ClientDao;
import daos.ClientDaoImpl;
import models.Client;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
           // MEMBER VARIABLES
    ClientDao clientDao = Mockito.mock(ClientDao.class);
    ClientService clientService;

          // CONSTRUCTORS
    public ClientServiceTest() {
        this.clientService = new ClientService(clientDao);
    }

    @Test
    void getClients() {
        // Arrange
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Alpha"));
        expectedResult.add(new Client(2, "Beta"));
        expectedResult.add(new Client(3, "Omega"));
        // The clientDaoImpl is not actually hit. Mockito returns the expected result, and we use that to test the service.
        Mockito.when(clientDao.getClients()).thenReturn(expectedResult);

        //Act
        List<Client> actualResult = clientService.getClients();

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientReturnsClient() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        Client expectedResult = clients.get(0);

        //Since clientDao object is mocked and clientService references this dao object, clientService can also be mocked.
        Mockito.when(clientService.getClients()).thenReturn(clients);
        Mockito.when(clientDao.getClient(expectedResult.getClientId())).thenReturn(expectedResult);

        //Act
        Client actualResult = clientService.getClient(expectedResult.getClientId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getClientReturnsNull() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // client with id out of the list of ids
        Client client = new Client(1, "Delta");

        Mockito.when(clientService.getClients()).thenReturn(clients);
        //Mockito.when(clientDao.getClient(client.getClientId())).thenReturn(client);

        //Act
        Client actualResult = clientService.getClient(client.getClientId());

        //assert
        assertNull(actualResult);
    }

    @Test
    void createClientReturnTrue() {
        // Arrange
        Client client = new Client(1, "Alpha");

        //Act
        Boolean actualResult = clientService.createClient(client);

        //assert
        assertTrue(actualResult);

    }

    @Test
    void createClientReturnFalse() {
        // Arrange
        Client client = new Client(1, "Alpha Blondy is from Ivory Coast");

        //Act
        Boolean actualResult = clientService.createClient(client);

        //assert
        assertFalse(actualResult);

    }

    @Test
    void updateClientReturnsTrue() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        Client client = clients.get(0);

        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = clientService.updateClient(client.getClientId(), client.getClientName());

        //assert
        assertTrue(actualResult);
    }

    @Test
    void updateClientReturnsFalse() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha Blondy is from Ivory Coast"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // Either the client id is not in the listIds or the length of the client name is over 20 or both.
        Client client = clients.get(0);

        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = clientService.updateClient(client.getClientId(), client.getClientName());

        //assert
        assertFalse(actualResult);
    }

    @Test
    void deleteClientReturnsTrue() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        Client client = clients.get(0);

        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = clientService.deleteClient(client.getClientId());

        //assert
        assertTrue(actualResult);
    }

    @Test
    void deleteClientReturnsFalse() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        Client client = new Client(5, "Delta");

        Mockito.when(clientService.getClients()).thenReturn(clients);

        //Act
        Boolean actualResult = clientService.deleteClient(client.getClientId());

        //assert
        assertFalse(actualResult);
    }
}