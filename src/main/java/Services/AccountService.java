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
    ClientDao clientDao;

    // CONSTRUCTORS
    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    // MEMBERS METHODS TO DEFINE THE BUSINESS LOGIC

    ClientService clientService = new ClientService(); // create the object clientService to call the helper method.

    public List<Account> getClientAccounts(Integer clientId) {
        if (!clientService.getClientIdsList().contains(clientId) || !getClientIdsListInAccount().contains(clientId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else {
                System.out.println("This client does not have any account!");
            }
            return new ArrayList<Account>(); //empty list
        } else {
            return accountDao.getClientAccounts(clientId);
        }

    }

    public List<Account> getClientAccounts(Integer clientId, Double minBalance, Double maxBalance) {
        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId))) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else {
                System.out.println("This client does not have any account!");
            }
            return new ArrayList<Account>(); //empty list
        } else { // if client id in listIds and  query parameters found
            return accountDao.getClientAccounts(clientId, minBalance, maxBalance);
        }
    }

    public Account getClientAccount(Integer clientId, Integer accountId) {
        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId)) ||
                !getClientAccountsIdsList(clientId).contains(accountId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else if (!getClientIdsListInAccount().contains(clientId)) {
                System.out.println("This client does not have any account!");
            }
            else {
                System.out.println("There is no such account for this client!");
            }
            return null;
        } else {
            return accountDao.getClientAccount(clientId, accountId);
        }
    }

    public Boolean createAccount(Integer clientId) {
        if (clientService.getClientIdsList().contains(clientId)) { // if client id in listIds
            accountDao.createAccount(clientId);
            System.out.println("Account successfully created!");
            return true;
        } else {
            System.out.println("This client does not exist!. Account not created!");
            return false;
        }
    }

    public Boolean updateAccount(Integer accountId, Integer clientId, String category,
                                 Double balance, Double deposit, Double withdraw, Double transfer, Boolean isActive) {

        if (!getAccountIdsList().contains(accountId)) {
            System.out.println("This account does not exist!");
            return false;
        } else {
            accountDao.updateAccount(accountId, clientId, category, balance, deposit, withdraw, transfer, isActive);
            System.out.println("The account has been successfully updated!");
            return true;
        }
    }

    public Boolean updateAccountBalanceByDeposit(Integer clientId, Integer accountId, Double deposit) {
        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId)) ||
                !getClientAccountsIdsList(clientId).contains(accountId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else if (!getClientIdsListInAccount().contains(clientId)) {
                System.out.println("This client does not have any account!");
            }
            else {
                System.out.println("There is no such account for this client!");
            }
            return false;
        } else {
            accountDao.updateAccountBalanceByDeposit(clientId, accountId, deposit);
            System.out.printf("The amount of %.2f has been deposited into the account", deposit);
            return true;
        }
    }

    public Boolean updateAccountBalanceByWithdraw(Integer clientId, Integer accountId, Double withdraw) {

        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId)) ||
                !getClientAccountsIdsList(clientId).contains(accountId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else if (!getClientIdsListInAccount().contains(clientId)) {
                System.out.println("This client does not have any account!");
            }
            else {
                System.out.println("There is no such account for this client!");
            }
            return false;
        } else {
            accountDao.updateAccountBalanceByWithdraw(clientId, accountId, withdraw);
            System.out.printf("The amount of %.2f has been withdrawn from the account", withdraw);
            return true;
        }

    }

    public Boolean updateAccountsBalanceByTransfer(Integer clientId, Integer accountFromId, Integer accountToId, Double transferAmount) {
        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId)) ||
                !getClientAccountsIdsList(clientId).contains(accountFromId) || !getClientAccountsIdsList(clientId).contains(accountToId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }else if (!getClientIdsListInAccount().contains(clientId)) {
                System.out.println("This client does not have any account!");
            }
            else if (!getClientAccountsIdsList(clientId).contains(accountFromId)) {
                System.out.println("The account 'From' does not belong to the client");
            }
            else {
                System.out.println("The account 'To' does not belong to the client");
            }
            return false;
        } else {
            accountDao.updateAccountsBalanceByTransfer(clientId, accountFromId, accountToId, transferAmount);
            System.out.printf("The amount of %s has been transferred  from the account %s to the account %s",
                    transferAmount, accountFromId, accountToId);
            return true;
        }
    }

    public Boolean deleteAccount(Integer clientId, Integer accountId) {
        if (!clientService.getClientIdsList().contains(clientId) || !(getClientIdsListInAccount().contains(clientId)) ||
                !getClientAccountsIdsList(clientId).contains(accountId)) {
            if (!clientService.getClientIdsList().contains(clientId)) {
                System.out.println("This client does not exist!");
            }
            else if (!getClientIdsListInAccount().contains(clientId)) {
                System.out.println("This client does not have any account!");
            }
            else {
                System.out.println("There is no such account for this client!");
            }
            return false;
        } else { // if client id in listIds and account id in list in accountIds
            accountDao.deleteAccount(clientId, accountId);
            System.out.println("The account has been successfully deleted!");
            return true;
        }
    }


                        // HELPER METHODS

    // Returns the list of all accountIds.
    public List<Integer> getAccountIdsList(){
        List<Account> accounts = getAccounts(); // get the list of all accounts.
        List<Integer> accountIds = new ArrayList<>(); // create an empty ArrayList of ids.

        for(Account account : accounts){
            accountIds.add(account.getAccountId()); // loop through accounts list and add each id to the ids' list.
        }
        return accountIds;
    }

    // Returns the list of all clientIds
    public List<Integer> getClientIdsListInAccount(){
        List<Account> accounts = getAccounts(); // get the list of all accounts.
        List<Integer> clientIdsInAccount = new ArrayList<>(); // create an empty ArrayList of ids.

        for(Account account : accounts){
            clientIdsInAccount.add(account.getClientId()); // loop through accounts' list and add each client id to the ids' list.
        }
        return clientIdsInAccount;
    }

    // Returns the list of the AccountIds for the client
    public List<Integer> getClientAccountsIdsList(Integer clientId){
        List<Account> accounts = getAccounts();
        List<Integer> clientAccountsIdsList = new ArrayList<>();
        for(Account account : accounts){
            if (account.getClientId().equals(clientId)) {
                clientAccountsIdsList.add(account.getAccountId());
            }
        }
        return clientAccountsIdsList;
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
