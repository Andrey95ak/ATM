package ATM;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Amazon bank");
        User newUser = theBank.addUser("David", "Johnson", "1234");
        Account newAccount = new Account("savings", newUser, theBank);
        newUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        User curUser;
        while (true) {
            curUser = ATM.menuPrompt(theBank, sc);

            ATM.printUserMenu(curUser, sc);
        }

    }

    public static User menuPrompt(Bank theBank, Scanner sc) {

        String userId;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\n Welcome to the %s\n\n", theBank.getName());

            System.out.println("Please enter your id: ");
            userId = sc.nextLine();

            System.out.println("Please enter your pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userId, pin);

            if (authUser == null) {
                System.out.println("The combination of id and pin not correct, please try again");
            }

        } while (authUser == null);

        return authUser;

    }

    public static void printUserMenu(User theUser, Scanner sc) {

        int choice;

        do {
            System.out.printf("Welcome %s", theUser.getFirstName());
            theUser.printAccountSummary();

            System.out.println("What would you like to do ?");
            System.out.println(" 1). transfer");
            System.out.println(" 2). withdraw");
            System.out.println(" 3). deposit");
            System.out.println(" 4). Show account transaction history");
            System.out.println(" 5). Quit");
            System.out.print("Enter choice ");
            choice = sc.nextInt();

            if (choice < 0 || choice > 5) {
                System.out.println("Invalid input, please try again");
            }
        } while (choice < 0 || choice > 5);

        switch (choice) {
            case 1:
                ATM.transferFunds(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.showAccountTransactionHistory(theUser, sc);
                break;
        }

        if (choice != 5) {
            printUserMenu(theUser, sc);
        }

    }

    public static void transferFunds(User theUser, Scanner sc) {

        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;

        // get account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account, please the again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBalance = theUser.getAcctBalance(fromAcct);

        // get account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBalance) {
                System.out.printf("Amount not be greater than balance $%.02f \n", acctBalance);
            }
        } while (amount < 0 || amount > acctBalance);

        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)
        ));

        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer from account %s", theUser.getAcctUUID(fromAcct)
        ));

    }

    public static void withdrawFunds(User theUser, Scanner sc) {

        int fromAcct;
        double amount;
        double acctBalance;


        do {
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

        acctBalance = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBalance) {
                System.out.printf("Amount not be greater than balance $%.02f \n", acctBalance);
            }
        } while (amount < 0 || amount > acctBalance);

        theUser.addAcctTransaction(fromAcct, -1 * amount, "");

    }

    public static void depositFunds(User theUser, Scanner sc) {

        int toAcct;
        double amount;
        double accBalance;

        do {
            System.out.printf("Enter the number (1-%d) of the account " +
                    "deposit from", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        accBalance = theUser.getAcctBalance(toAcct);

        do {
            System.out.printf("Enter the amount to deposit ($%.02f): $", accBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0);

        theUser.addAcctTransaction(toAcct, amount, "");

    }

    public static void showAccountTransactionHistory(User theUser, Scanner sc) {

        int theAcc;

        do {
            System.out.printf("Enter the number (1-%d) of the account: ",
                    theUser.numAccounts());
            theAcc = sc.nextInt() - 1;
            if (theAcc < 0 || theAcc > theUser.numAccounts()) {
                System.out.println("Invalid account, please try again");
            }
        } while (theAcc < 0 || theAcc > theUser.numAccounts());

        theUser.showAcctTransHistory(theAcc);

    }

}
