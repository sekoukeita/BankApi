package controllers;

import Services.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.http.Context;
import models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientController {
                    // MEMBER VARIABLES
    // References the ClientService
    ClientService clientService;

                     // CONSTRUCTORS
    public ClientController() {
        this.clientService = new ClientService();
    }

                     // HANDLER IMPLEMENTATIONS

    // POST /clients => Creates a new client return a 201 status code
    public void createClient(Context ctx){
        // json: {"clientName": "Sayon Traore"}. This json is entered in the body of the request.
        // it returns only the clientName, what is needed to use the one argument constructor with clientName
        // because the clientId is default in the database.
        Client client = ctx.bodyAsClass(Client.class);
        if(client.getClientName().length() > 20){
            ctx.status(404);
            ctx.result("The client has not been created. The name length is over the 20 characters allowed.");
        }
        else{
            clientService.createClient(client);
            ctx.status(201);
            ctx.result("The new client named " + client.getClientName() + " has been successfully created in the database!");
        }
    }

    // GET /clients => gets all clients return 200
    public void getClients(Context ctx){
        ctx.json(clientService.getClients());
    }

    // GET /clients/10 => get client with id of 10 return 404 if no such client exist
    public void getClient(Context ctx) {
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the id from the path parameter entered into the url

        if(clientService.getClientIdsList().contains(clientId)){
            ctx.json(clientService.getClient(clientId)); //if the clientId is inside the ids list, get the client.
        }
        else{
            ctx.status(404); // else return the status code 404
            ctx.result("The client with id " + clientId + " does not exist in the database!");
        }
    }

    // PUT /clients/12 => updates client with id of 12 return 404 if no such client exist
    public void updateClient(Context ctx){
        Client client = ctx.bodyAsClass(Client.class); // get the client new name from the json in the body
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter

        if (client.getClientName().length() > 20){
            ctx.status(404); //return the status code 404
            ctx.result("The client name should not be longer than 20 characters!");
        }
        else if (!clientService.getClientIdsList().contains(clientId)){
            ctx.status(404);
            ctx.result("The client with id " + clientId + " does not exist in the database!");
        }
        else{
            clientService.updateClient(clientId, client.getClientName()); //if the clientId is inside the ids list, update the client name.
            ctx.result("The name of the client with id " + clientId + " has been successfully updated to: " + client.getClientName());
        }
    }

    // DELETE /clients/15 => deletes client with the id of 15 return 404 if no such client exist return 205 if success
    public void deleteClient(Context ctx){
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter

        if(clientService.getClientIdsList().contains(clientId)){
            clientService.deleteClient(clientId); //if the clientId is inside the ids list, delete the client name.
            ctx.status(205);
            ctx.result("The client with id " + clientId + " (and its accounts if any) have been successfully deleted from the database!");
        }
        else{
            ctx.status(404); // else return the status code 404
            ctx.result("The client with id " + clientId + " does not exist in the database!");
        }
    }
}
