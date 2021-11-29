package daos;

import models.Client;

import java.util.List;

public interface ClientDao {

        //METHOD SIGNATURES
    List<Client> getClients();
    Client getClient(Integer clientId);
    void createClient(Client client);
    void updateClient(Integer clientId, String clientNewName); // put: update the client name.
    void deleteClient(Integer clientId);
}
