package Services;

import daos.AccountDao;
import daos.AccountDaoImpl;
import models.Account;

import java.util.List;

public class AccountService {

                // MEMBER VARIABLES
    // References the AccountDao
    AccountDao accountDao;

                // CONSTRUCTORS
    public AccountService() {
        this.accountDao = new AccountDaoImpl();
    }

                // MEMBERS METHODS TO DEFINE THE BUSINESS LOGIC
    public List<Account> getAccounts(){
        System.out.println("The list of all accounts of the database has been successfully returned.");
        return accountDao.getAccounts();
    }

    public List<Account> getClientAccounts(Integer clientId){
        System.out.printf("The list of all accounts for the client with id %s has been successfully returned.", clientId);
        return accountDao.getClientAccounts(clientId);
    }

    public List<Account> getClientAccounts(Integer clientId, Double minBalance, Double maxBalance){
        System.out.printf("The list of accounts for the client with id %s that have a balance value between $%s and $%s has been returned.",
                clientId, minBalance, maxBalance);
        return accountDao.getClientAccounts(clientId, minBalance, maxBalance);
    }


    public Account getClientAccount(Integer clientId, Integer accountId){
        System.out.printf("The account with id %s for the client with id %s has been successfully returned.", clientId, accountId);
        return accountDao.getClientAccount(clientId, accountId);
    }

    public void createAccount(Integer clientId){
        accountDao.createAccount(clientId);
        System.out.printf("An account has been successfully created for the client with id %s", clientId);
    }

    public void updateAccount(Integer accountId, Integer clientId, String category,
                              Double balance, Double deposit, Double withdraw, Double transfer, Boolean isActive){
        accountDao.updateAccount(accountId, clientId, category, balance, deposit, withdraw, transfer, isActive);
        System.out.printf("The account with id %s has been successfully updated with the following values:\n" +
                "clientId: %s\n" +
                "category: %s\n" +
                "balance: %s\n" +
                "deposit: %s\n" +
                "withdraw: %s\n" +
                "transfer: %s\n" +
                "isActive: %s\n", accountId, clientId, category, balance, deposit, withdraw, transfer, isActive);
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

    public void updateAccountBalanceByDeposit(Integer clientId, Integer accountId, Double deposit){
        accountDao.updateAccountBalanceByDeposit(clientId, accountId, deposit);
        System.out.printf("The account id %s for the client id %s balance has been successful increased by $%s",
                accountId, clientId, deposit);
    }

    public void updateAccountBalanceByWithdraw(Integer clientId, Integer accountId, Double withdraw){
        accountDao.updateAccountBalanceByWithdraw(clientId, accountId, withdraw);
        System.out.printf("The account id %s for the client id %s balance has been successful decreased by $%s",
                accountId, clientId, withdraw);
    }

    public void updateAccountStatus(Integer clientId, Integer accountId, Boolean isActive){
        accountDao.updateAccountStatus(clientId, accountId, isActive);
        System.out.printf("The account id %s for the client id %s isActive propriety has been updated to %s",
                accountId, clientId, isActive);
    }

    public void updateAccountsBalanceByTransfer(Integer clientId, Integer accountFromId, Integer accountToId, Double transferAmount){
        accountDao.updateAccountsBalanceByTransfer(clientId, accountFromId, accountToId, transferAmount);
        System.out.printf("The amount of %s has been successfully transferred from the account id %s to the account id %s of the client id %s",
                transferAmount, accountFromId, accountToId, clientId);
    }

    public void deleteAccount(Integer clientId, Integer accountId){
        accountDao.deleteAccount(clientId, accountId);
        System.out.printf("The account id %s for the client id %s has been successfully removed.", accountId, clientId);
    }















}
