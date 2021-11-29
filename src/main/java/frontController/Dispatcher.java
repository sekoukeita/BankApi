package frontController;

import controllers.AccountController;
import controllers.ClientController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Dispatcher {

    // create controller objects to get their methods
    ClientController clientController = new ClientController();
    AccountController accountController = new AccountController();

    public Dispatcher(Javalin app) {
        app.routes(() -> {
            path("clients", () -> {
                get(clientController::getClients);
                post(clientController::createClient);
                path("{cId}", () -> {
                    get(clientController::getClient);
                    put(clientController::updateClient);
                    delete(clientController::deleteClient);
                    path("accounts", () -> {
                        get(accountController::getClientAccounts);
                        post(accountController::createAccount);
                        path("{aId}", () -> {
                            get(accountController::getClientAccount);
                            put(accountController::updateAccount);
                            delete(accountController::deleteAccount);
                            patch(accountController::updateAccountBalance);
                            path("transfer", () -> {
                                path("{aId2}", () -> {
                                    patch(accountController::updateAccountsByTransfer);
                                });
                            });
                        });
                    });
                });
            });
        });


    }
}
