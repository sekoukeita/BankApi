package daos;

import models.Account;

import java.util.List;

public interface AccountDao {

                      // METHOD SIGNATURES
    List<Account> getAccounts(); // All accounts in the account table

    List<Account> getClientAccounts(Integer clientId); // All accounts for this client

    // All accounts for this client with balance between minimum and maximum provided balances
    // Overloads the previous methods.
    List<Account> getClientAccounts(Integer clientId, Double minBalance, Double maxBalance);

    Account getClientAccount(Integer clientId, Integer accountId); // A specific account for this client

    /*
    * Will use the path parameter in the url rather than inserting the json {"clientId": 5} in the body of the request.
    * In which case, we would pass an object account to method using the one argument constructor with clientId.
    * */
    void createAccount(Integer clientId);

    // put: update the whole entity. use the constructor with all arguments
    void updateAccount(Integer accountId, Integer clientId, String category, Double balance,
                               Double deposit, Double withdraw, Double transfer, Boolean isActive);

    void updateAccountCategory(Integer clientId, Integer accountId, String category); // patch: only update category

    void updateAccountBalanceByDeposit(Integer clientId, Integer accountId, Double deposit); // patch: only update deposit and balance

    void updateAccountBalanceByWithdraw(Integer clientId, Integer accountId, Double withdraw); // patch: only update withdraw and balance

    void updateAccountStatus(Integer clientId, Integer accountId, Boolean isActive); // patch: only update isActive

    void updateAccountsBalanceByTransfer(Integer clientId, Integer accountFromId,
                                          Integer accountToId, Double transferAmount); // calls withdraw and deposit inside

    void deleteAccount(Integer clientId,Integer accountId);
}
