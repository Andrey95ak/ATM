package ATM;

import java.security.MessageDigest;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {

        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        } catch (Exception e) {
            System.out.println("error, caught exception: " + e.getMessage());
            System.exit(1);
        }

        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created\n",
                firstName, lastName, this.uuid);

    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        }
        catch (Exception e) {
            System.out.println("error, no such algorithm found");
            System.exit(1);
        }

        return false;
    }

    public String getUUID() {
        return this.uuid;
    }

    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void printAccountSummary() {
        System.out.printf("\n\n%s's, account summary\n", this.firstName);

        for (int i = 0; i < this.accounts.size(); i++) {
            String line = this.accounts.get(i).getSummaryLine();
            System.out.println(line);
        }
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public double getAcctBalance(int acctIndex) {
        return this.accounts.get(acctIndex).getBalance();
    }

    public String getAcctUUID(int acctIndex) {
        return this.accounts.get(acctIndex).getUUID();
    }

    public void addAcctTransaction(int acctIndex, double amount, String memo) {
        this.accounts.get(acctIndex).addTransaction(amount, memo);
    }

    public void showAcctTransHistory(int acctIndex) {
        this.accounts.get(acctIndex).showTransactions();
    }

}
