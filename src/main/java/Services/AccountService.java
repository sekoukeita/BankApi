package Services;

import daos.AccountDao;
import daos.AccountDaoImpl;
import daos.ClientDao;
import models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

                // MEMBER VARIABLES
    AccountDao accountDao; // References the AccountDao
    //ClientDao clientDao; // References the ClientDao

                // CONSTRUCTORS
    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /*        // added to use in unit testing
    public AccountService(ClientDao clientDao) {
        this.clientDao = clientDao;

    }*/

    // MEMBERS METHODS TO DEFINE THE BUSINESS LOGIC

    ClientService clientService = new ClientService(); // create the object clientController to call the helper method.

    public List<Account> getClientAccounts(Integer clientId){

        if (!(getClientIdsListInAccount().contains(clientId))) {
            return new ArrayList<Account>(); //empty list
        }
        else{ // if client id in listIds and not query parameters found
            return accountDao.getClientAccounts(clientId);
        }
    }

    public List<Account> getClientAccounts(Integer clientId, Double minBalance, Double maxBalance){

        if (!(getClientIdsListInAccount().contains(clientId))) {
            return new ArrayList<Account>(); //empty list
        }
        else{ // if client id in listIds and  query parameters found
            return accountDao.getClientAccounts(clientId, minBalance, maxBalance);
        }
    }

    public Account getClientAccount(Integer clientId, Integer accountId){
        if (!getClientIdsListInAccount().contains(clientId) || !getAccountIdsList().contains(accountId)){
            return null;
        }
        else { // if client id in listIds and account id in list in accountIds
            return accountDao.getClientAccount(clientId, accountId);
        }
    }

    public Boolean createAccount(Integer clientId){
        if (clientService.getClientIdsList().contains(clientId)) { // if client id in listIds
            accountDao.createAccount(clientId);
            return true;
        } else {
           return false;
        }
    }

    public Boolean updateAccount(Integer accountId, Integer clientId, String category,
                              Double balance, Double deposit, Double withdraw, Double transfer, Boolean isActive){

        if (!getClientIdsListInAccount().contains(clientId) || !getAccountIdsList().contains(accountId)) {
            return false;
        }
        else { // client id in listIds and account id in accountIds
            accountDao.updateAccount(accountId, clientId, category, balance, deposit, withdraw, transfer, isActive);
            return true;
        }
    }

    public Boolean updateAccountBalanceByDeposit(Integer clientId, Integer accountId, Double deposit){
        if (!getClientIdsListInAccount().contains(clientId) || !getAccountIdsList().contains(accountId)){
            return false;
        }
        else{ // client id in listIds and account id in accountIds
            accountDao.updateAccountBalanceByDeposit(clientId, accountId, deposit);
            return true;
        }
    }

    public Boolean updateAccountBalanceByWithdraw(Integer clientId, Integer accountId, Double withdraw){
        /*if ( !getAccountIdsList().contains(accountId) ||
                !(getClientAccount(clientId, accountId).getBalance() < withdraw)){
            return false;
        }
        else{ // client id in listIds and account id in accountIds and balance >= withdraw
            accountDao.updateAccountBalanceByWithdraw(clientId, accountId, withdraw);*/
        accountDao.updateAccountBalanceByWithdraw(clientId, accountId, withdraw);
        return true;

    }

    public Boolean updateAccountsBalanceByTransfer(Integer clientId, Integer accountFromId, Integer accountToId, Double transferAmount) {
        /*if (!getClientIdsListInAccount().contains(clientId) || !getAccountIdsList().contains(accountFromId) ||
                !getAccountIdsList().contains(accountToId) ||
                !(getClientAccount(clientId, accountFromId).getBalance() < transferAmount) ) {
            return false;
        }
        else{
            accountDao.updateAccountsBalanceByTransfer(clientId, accountFromId, accountToId, transferAmount);
            return true;
        }*/
        accountDao.updateAccountsBalanceByTransfer(clientId, accountFromId, accountToId, transferAmount);
        return true;

    }

    public Boolean deleteAccount(Integer clientId, Integer accountId){
        if (!getClientIdsListInAccount().contains(clientId) || !getAccountIdsList().contains(accountId)){
            return false;
        }
        else { // if client id in listIds and account id in list in accountIds
            accountDao.deleteAccount(clientId, accountId);
            return true;
        }
    }

                        // HELPER METHODS

    // Returns the list of accountIds for the client.
    public List<Integer> getAccountIdsList(){
        List<Account> accounts = getAccounts(); // get the list of all accounts.
        List<Integer> accountIds = new ArrayList<>(); // create an empty ArrayList of ids.

        for(Account account : accounts){
            accountIds.add(account.getAccountId()); // loop through accounts list and add each id to the ids' list.
        }
        return accountIds;
    }

    public List<Integer> getClientIdsListInAccount(){
        List<Account> accounts = getAccounts(); // get the list of all accounts.
        List<Integer> clientIdsInAccount = new ArrayList<>(); // create an empty ArrayList of ids.

        for(Account account : accounts){
            clientIdsInAccount.add(account.getClientId()); // loop through accounts' list and add each client id to the ids' list.
        }
        return clientIdsInAccount;
    }


                         // NON USED METHODS

    public List<Account> getAccounts(){
        return accountDao.getAccounts();
    }

    // The categories allowed are Checking and Saving
    public void updateAccountCategory(Integer clientId, Integer accountId, String category){
        if (category.equalsIgnoreCase("Checking") || category.equalsIgnoreCase("Saving")){
            accountDao.updateAccountCategory(clientId, accountId, category);
            System.out.printf("The account with id %s of the client of id %s category has been successfully updated to %s",
                    accountId, clientId, category);
        }
        else{
            System.out.println(" The account category has not been updated. The category should be \"Checking\" or \"Saving\".");
        }
    }

    public void updateAccountStatus(Integer clientId, Integer accountId, Boolean isActive){
        accountDao.updateAccountStatus(clientId, accountId, isActive);
        System.out.printf("The account id %s for the client id %s isActive propriety has been updated to %s",
                accountId, clientId, isActive);
    }

}
