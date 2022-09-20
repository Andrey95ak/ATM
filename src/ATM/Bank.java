package ATM;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        users = new ArrayList<User>();
        accounts = new ArrayList<Account>();
    }

    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public String getNewUserUUID() {
        Random rng = new Random();
        String uuid;
        boolean nonUnique;
        int len = 6;

        do {
            uuid = "";

            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }

            }
        } while (nonUnique);

        return uuid;

    }

    public String getNewAccountUUID() {
        Random rng = new Random();
        String uuid;
        boolean nonUnique;
        int len = 10;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("savings", newUser, this);
        this.accounts.add(newAccount);
        newUser.addAccount(newAccount);
        return newUser;
    }

    public User userLogin(String userId, String pin) {
        for (User u : this.users) {
            if (u.getUUID().compareTo(userId) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        return null;
    }

    public String getName() {
        return this.name;
    }

}
