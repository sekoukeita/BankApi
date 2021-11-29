import controllers.AccountController;
import controllers.ClientController;
import frontController.FrontController;
import io.javalin.Javalin;

public class MainJavalin {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(9000);
        new FrontController(app);






/*



        ///////////////// CLIENT  //////////////////////////////////////
        ClientController clientController = new ClientController();

        // POST /clients => Creates a new client return a 201 status code
        app.post("/clients", clientController::createClient);

        // GET /clients => gets all clients return 200
        app.get("/clients", clientController::getClients);

        // GET /clients/10 => get client with id of 10 return 404 if no such client exist
        app.get("/clients/{cId}", clientController::getClient);

        // PUT /clients/12 => updates client with id of 12 return 404 if no such client exist
        app.put("/clients/{cId}", clientController::updateClient);

        // DELETE /clients/15 => deletes client with the id of 15 return 404 if no such client exist return 205 if success
        app.delete("/clients/{cId}", clientController::deleteClient);

        /////////////////////  ACCOUNT   ///////////////////////////////////////////
        AccountController accountController = new AccountController();

        // POST /clients/5/accounts =>creates a new account for client with the id of 5 return a 201 status code
        app.post("/clients/{cId}/accounts", accountController::createAccount);



      */
/*  GET /clients/7/accounts => get all accounts for client 7 return 404 if no client exists
                                          AND
        * GET /clients/7/accounts?amountLessThan=2000&amountGreaterThan400 => get all accounts for client 7 between 400 and
        * 2000 return 404 if no client exists
       * *//*

        app.get("/clients/{cId}/accounts", accountController::getClientAccounts);

        // GET /clients/9/accounts/4 => get account 4 for client 9 return 404 if no account or client exists
        app.get("/clients/{cId}/accounts/{aId}", accountController::getClientAccount);

        // PUT /clients/10/accounts/3 => update account with the id 3 for client 10 return 404 if no account or client exists
        app.put("/clients/{cId}/accounts/{aId}", accountController::updateAccount);

        // DELETE /clients/15/accounts/6 => delete account 6 for client 15 return 404 if no account or client exists
        app.delete("/clients/{cId}/accounts/{aId}", accountController::deleteAccount);

        // PATCH /clients/17/accounts/12 => Withdraw/deposit given amount
        // (Body: {"deposit":500} or {"withdraw":250} return 404 if no account or client exists return 422 if insufficient funds
        app.patch("/clients/{cId}/accounts/{aId}", accountController::updateAccountBalance);

        // PATCH /clients/12/accounts/7/transfer/8 => transfer funds from account 7 to account 8
        // (Body: {"amount":500}) return 404 if no client or either account exists return 422 if insufficient funds
        app.patch("/clients/{cId}/accounts/{aId}/transfer/{aId2}", accountController::updateAccountsByTransfer);



*/











    }
}
