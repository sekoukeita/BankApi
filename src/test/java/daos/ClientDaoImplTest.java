package daos;

import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDaoImplTest {

    ClientDao clientDao;

    public ClientDaoImplTest() {
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
    void getClientsIT() {
        // Arrange
        // List of clients
        List<Client> expectedResult = new ArrayList<>();
        expectedResult.add(new Client(1, "Alpha"));
        expectedResult.add(new Client(2, "Beta"));
        expectedResult.add(new Client(3, "Omega"));

        // Populate the H2 db with the clients
        clientDao.createClient(expectedResult.get(0));
        clientDao.createClient(expectedResult.get(1));
        clientDao.createClient(expectedResult.get(2));

        // Act
        List<Client> actualResult = clientDao.getClients();

        //assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void getClientIT() {
        // Arrange
        // List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        Client expectedResult = clients.get(0);

        // Populate the H2 db with the clients
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));
        clientDao.createClient(clients.get(2));

        // Act
        Client actualResult = clientDao.getClient(expectedResult.getClientId());

        //assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void createClientIT() {
        // Arrange
        // List of clients
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Alpha"));
        clients.add(new Client(2, "Beta"));
        clients.add(new Client(3, "Omega"));

        // The size of the list to enter into the db will be compared with the size of the list get from the db
        int expectedResult = clients.size();


        // Populate the H2 db with the clients
        clientDao.createClient(clients.get(0));
        clientDao.createClient(clients.get(1));
        clientDao.createClient(clients.get(2));

        // Act
        int actualResult = clientDao.getClients().size();

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateClientIT() {
        // Arrange
        // List of clients
        Client client = new Client(1, "Alpha");
        Client expectedResult = new Client(1, "Beta");
        clientDao.createClient(client);


        // Act
        clientDao.updateClient(client.getClientId(), expectedResult.getClientName());
        Client actualResult = clientDao.getClient(expectedResult.getClientId());


        //assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteClientIT() {
        // Arrange
        // List of clients
        Client client = new Client(1, "Alpha");
        clientDao.createClient(client);


        // Act
        clientDao.deleteClient(client.getClientId());
        Client actualResult = clientDao.getClient(client.getClientId());

        //assert
        assertNull(actualResult);
    }
}