package Services;

import daos.ClientDao;
import daos.ClientDaoImpl;
import models.Client;

import java.util.List;

public class ClientService {
                // MEMBER VARIABLES
    // References the ClientDao
    ClientDao clientDao;

                // CONSTRUCTORS
    public ClientService() {
        this.clientDao = new ClientDaoImpl();
    }

                // MEMBERS METHODS TO DEFINE THE BUSINESS LOGIC
    public List<Client> getClients(){
        System.out.println("The list of clients has successfully been returned.");
        return clientDao.getClients();
    }

    public Client getClient(Integer clientId){
        System.out.println("The client has successfully been returned.");
        return clientDao.getClient(clientId);
    }

    public void createClient(Client client){
        clientDao.createClient(client);
        System.out.println("The client has successfully been created in the database.");
    }

    public void updateClient(Integer clientId, String clientNewName){
        if (clientNewName.length() > 20){
            System.out.printf("This name length is %s characters. It should be less or equal to 20 characters.", clientNewName.length());
        }
        else{
            clientDao.updateClient(clientId, clientNewName);
            System.out.printf("The name of the client with id %s has been replaced by %s.", clientId, clientNewName);
        }
    }

    public void deleteClient(Integer clientId){
        clientDao.deleteClient(clientId);
        System.out.printf("The client with id %s has been successfully removed from the database", clientId);
    }
}
