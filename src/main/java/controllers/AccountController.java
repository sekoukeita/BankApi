package controllers;

import Services.AccountService;
import Services.ClientService;
import io.javalin.http.Context;
import models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountController {
    // MEMBER VARIABLES

    AccountService accountService; // References the AccountService


    // CONSTRUCTORS
    public AccountController() {
        this.accountService = new AccountService();
    }

    //HANDLER IMPLEMENTATIONS

    ClientService clientService = new ClientService(); // create the object clientController to call the helper method.

    // POST /clients/5/accounts =>creates a new account for client with the id of 5 return a 201 status code
    public void createAccount(Context ctx) {
        Integer clientId = Integer.parseInt(ctx.pathParam("cId"));

        if (clientService.getClientIdsList().contains(clientId)) { // The method getIdsList return the list of client ids in the database.
            accountService.createAccount(clientId);
            ctx.status(201);
            ctx.result("An account has been successfully created for the client with id " + clientId + " in the database!");
        } else {
            ctx.status(404);
            ctx.result("The client with id " + clientId + " does not exist in the database. He needs to be created before creating an account for him.");
        }
    }

    /*
     * GET /clients/7/accounts => get all accounts for client 7 return 404 if no client exists
     * And
     * GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and
     * 2000 return 404 if no client exists
     * */
    public void getClientAccounts(Context ctx) {
        Integer clientId = Integer.parseInt(ctx.pathParam("cId"));
        if (!(clientService.getClientIdsList().contains(clientId))) {
            ctx.status(404);
            ctx.result("The client with id " + clientId + " does not exists in the database.");
            return;
        }
        if (!(accountService.getClientIdsListInAccount().contains(clientId))) {
            ctx.status(404);
            ctx.result("The client with id " + clientId + " does not have any account in the database.");
            return;
        }

        if ((ctx.queryParam("amountLessThan") != null) && (ctx.queryParam("amountGreaterThan") != null)) {
            Double minBalance = Double.parseDouble(ctx.queryParam("amountGreaterThan"));
            Double maxBalance = Double.parseDouble(ctx.queryParam("amountLessThan"));
            // If query parameters are provided in the url, use the method with 3 arguments
            // to return the client accounts' that have selected balance
            ctx.json(accountService.getClientAccounts(clientId, minBalance, maxBalance));
        } else {
            // If query parameters are not provided in the url, use the method with 1 argument
            // to return all accounts for the client.
            ctx.json(accountService.getClientAccounts(clientId));
        }
    }

    // GET /clients/9/accounts/4 => get account 4 for client 9 return 404 if no account or client exists
    public void getClientAccount(Context ctx) {
        Integer clientId = Integer.parseInt(ctx.pathParam("cId"));
        Integer accountId = Integer.parseInt(ctx.pathParam("aId"));

        if (!clientService.getClientIdsList().contains(clientId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not exist in the database.");
        } else if (!accountService.getAccountIdsList().contains(accountId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountId);
        }
        else {
            ctx.json(accountService.getClientAccount(clientId,accountId));
        }
    }

    // PUT /clients/10/accounts/3 => update account with the id 3 for client 10 return 404 if no account or client exists
    public void updateAccount(Context ctx){
        Account account = ctx.bodyAsClass(Account.class); // get the all account variables (except accountId and ClientId) form the url body
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter
        Integer accountId = Integer.parseInt(ctx.pathParam("aId")); // get the account id from the path parameter

        if (!clientService.getClientIdsList().contains(clientId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not exist in the database.");
        } else if (!accountService.getAccountIdsList().contains(accountId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountId);
        }
        else {
            accountService.updateAccount(accountId, clientId, account.getCategory(), account.getBalance(), account.getDeposit(),
                    account.getWithdraw(), account.getTransfer(), account.getAccount_is_Active());
            ctx.result("The account with id " + accountId + " for the client with id " + clientId + " has been successfully updated!" );
        }
    }

    // DELETE /clients/15/accounts/6 => delete account 6 for client 15 return 404 if no account or client exists
    public void deleteAccount(Context ctx){
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter
        Integer accountId = Integer.parseInt(ctx.pathParam("aId")); // get the account id from the path parameter

        if (!clientService.getClientIdsList().contains(clientId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not exist in the database.");
        } else if (!accountService.getAccountIdsList().contains(accountId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountId);
        }
        else {
            accountService.deleteAccount(clientId, accountId);
            ctx.result("The account with id " + accountId + " for the client with id " + clientId + " has been successfully deleted!" );
        }
    }

    // PATCH /clients/17/accounts/12 => Withdraw/deposit given amount
    // (Body: {"deposit":500} or {"withdraw":250} return 404 if no account or client exists return 422 if insufficient funds
    public void updateAccountBalance(Context ctx){
        Account account = ctx.bodyAsClass(Account.class); // get the deposit or the withdrawal amount from the url body.
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter
        Integer accountId = Integer.parseInt(ctx.pathParam("aId")); // get the account id from the path parameter

        if (!clientService.getClientIdsList().contains(clientId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not exist in the database.");
        } else if (!accountService.getAccountIdsList().contains(accountId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountId);
        }
        else {
            if(account.getDeposit() != null){
                accountService.updateAccountBalanceByDeposit(clientId, accountId, account.getDeposit());
                ctx.result("The account with id " + accountId + " for the client with id " + clientId +
                        " balance has been successfully increased by $" + account.getDeposit());
            }
            if(account.getWithdraw() != null){
                if(accountService.getClientAccount(clientId, accountId).getBalance() < account.getWithdraw()){
                    ctx.status(422);
                    ctx.result("Insufficient funds: \nThe account with id " + accountId + " balance is only $" +
                            accountService.getClientAccount(clientId, accountId).getBalance());
                }
                else{
                    accountService.updateAccountBalanceByWithdraw(clientId, accountId, account.getDeposit() );
                    ctx.result("The account with id " + accountId + " for the client with id " + clientId +
                            " balance has been successfully decreased by $" + account.getWithdraw());
                }
            }
        }
    }

    // PATCH /clients/12/accounts/7/transfer/8 => transfer funds from account 7 to account 8
    // (Body: {"amount":500}) return 404 if no client or either account exists return 422 if insufficient funds
    public void updateAccountsByTransfer(Context ctx){
        Account account = ctx.bodyAsClass(Account.class); // get the transfer amount from the url body.
        Integer clientId = Integer.parseInt(ctx.pathParam("cId")); // get the client id from the path parameter
        Integer accountFromId = Integer.parseInt(ctx.pathParam("aId")); // get the account from id from the path parameter
        Integer accountToId = Integer.parseInt(ctx.pathParam("aId2")); // get the account to id from the path parameter

        if (!clientService.getClientIdsList().contains(clientId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not exist in the database.");
        } else if (!accountService.getAccountIdsList().contains(accountFromId)) {
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountFromId);
        } else if(!accountService.getAccountIdsList().contains(accountToId)){
            ctx.status(404);
            ctx.result("The client with " + clientId + " does not have any account with id " + accountToId);
        }else if(accountService.getClientAccount(clientId, accountFromId).getBalance() < account.getTransfer()){
            ctx.status(422);
            ctx.result("Insufficient funds: \nThe account with id " + accountFromId + " balance is only $" +
                    accountService.getClientAccount(clientId, accountFromId).getBalance());
        }
        else{
            accountService.updateAccountBalanceByWithdraw(clientId, accountFromId, account.getTransfer());
            accountService.updateAccountBalanceByDeposit(clientId, accountToId, account.getTransfer());
            ctx.result("The amount of $" + account.getTransfer() + " has been successfully transferred from the account with id " +
                    accountFromId + " to the account with id " + accountToId);
        }
    }
}










































