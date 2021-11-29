package models;

public class Account {
           // MEMBER VARIABLES
    private Integer accountId;
    private Integer clientId;
    private String category;
    private Double balance;
    private Double deposit;
    private Double withdraw;
    private Double transfer;
    private Boolean account_is_Active;

            // CONSTRUCTORS
    public Account() {
    }

    public Account(Integer clientId) {
        this.clientId = clientId;
    }

    public Account(Integer accountId, Integer clientId, String category, Double balance,
                   Double deposit, Double withdraw, Double transfer, Boolean account_is_Active) {
        this.accountId = accountId;
        this.clientId = clientId;
        this.category = category;
        this.balance = balance;
        this.deposit = deposit;
        this.withdraw = withdraw;
        this.transfer = transfer;
        this.account_is_Active = account_is_Active;
    }

    // GETTERS AND SETTERS
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Double getTransfer() {
        return transfer;
    }

    public void setTransfer(Double transfer) {
        this.transfer = transfer;
    }

    public Boolean getAccount_is_Active() {
        return account_is_Active;
    }

    public void setAccount_is_Active(Boolean account_is_Active) {
        this.account_is_Active = account_is_Active;
    }

              // METHODS OVERRIDDEN

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", clientId=" + clientId +
                ", category='" + category + '\'' +
                ", balance=" + balance +
                ", deposit=" + deposit +
                ", withdraw=" + withdraw +
                ", transfer=" + transfer +
                ", account_is_Active=" + account_is_Active +
                '}';
    }
}
