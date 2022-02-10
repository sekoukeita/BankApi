package Services;

import daos.ClientDao;
import daos.ClientDaoImpl;
import models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
                // MEMBER VARIABLES
    // References the ClientDao
    ClientDao clientDao;

                // CONSTRUCTORS
    public ClientService() {
        this.clientDao = new ClientDaoImpl();
    }

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    // MEMBERS METHODS TO DEFINE THE BUSINESS LOGIC
    public List<Client> getClients(){
        return clientDao.getClients();
    }

    public Client getClient(Integer clientId){
        if(getClientIdsList().contains(clientId)){ // if the client exists in the database.
            return clientDao.getClient(clientId);
        }
        else{
            System.out.println("The client does not exist!");
            return null;
        }
    }

    public Boolean createClient(Client client){
        if(client.getClientName().length() > 20){ // if the name of the client to create has more than 20 characters, don't create it.
            System.out.println("The client name should not be longer than 20 characters!");
            return false;
        }
        else{
            clientDao.createClient(client);
            System.out.println("The client has been successfully created!");
            return true;
        }
    }

    public Boolean updateClient(Integer clientId, String clientNewName){
        // if the client does not exist in the database or if its name has more than 20 characters.
        if(!getClientIdsList().contains(clientId) || clientNewName.length() > 20){
            if(!getClientIdsList().contains(clientId)){
                System.out.println("The client does not exist!");
            }
            else{
                System.out.println("The client name should not be longer than 20 characters!");
            }
            return false;
        }
        else{
            clientDao.updateClient(clientId, clientNewName);
            System.out.println("The client name has been successfully updated!");
            return true;
        }
    }

    public Boolean deleteClient(Integer clientId){
        if(getClientIdsList().contains(clientId)){ // if the client exists in the database.
            clientDao.deleteClient(clientId);
            System.out.println("The client has been successfully deleted!");
            return true;
        }
        else{
            System.out.println("The client does not exist!");
            return false;
        }
    }

                            // HELPER METHODS

    // Returns the list of clientIds in the database.
    public List<Integer> getClientIdsList(){
        List<Client> clients = getClients(); // get the list of clients in the database.
        List<Integer> clientIds = new ArrayList<>(); // create an empty ArrayList of ids.

        for(Client client : clients){
            clientIds.add(client.getClientId()); // loop through clients' list and add each id to the ids' list.
        }
        return clientIds;
    }
}
