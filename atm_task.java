import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String accountId;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposit of Rs." + amount + " successful.");
        System.out.println("Current Balance: Rs." + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal of Rs." + amount + " successful.");
            System.out.println("Current Balance: Rs." + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
            System.out.println("Transfer of Rs." + amount + " successful.");
            System.out.println("Current Balance: Rs." + balance);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account " + accountId);
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": Rs." + transaction.getAmount());
        }
    }
}

class User {
    private String userId;
    private String pin;
    private Account account;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.account = new Account(userId);
    }

    public String getUserId() {
        return userId;
    }

    public boolean isValidPin(String pin) {
        return this.pin.equals(pin);
    }

    public Account getAccount() {
        return account;
    }
}

class ATM {
    private List<User> users;

    public ATM() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public boolean validateUser(String userId, String pin) {
        User user = getUser(userId);
        return user != null && user.isValidPin(pin);
    }
}

public class atm_task {
    public static void main(String[] args) {
        ATM atm = new ATM();

        User user1 = new User("msc3107", "3107");
        User user2 = new User("msc3109", "3109");
        atm.addUser(user1);
        atm.addUser(user2);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (atm.validateUser(userId, pin)) {
            User user = atm.getUser(userId);
            Account account = user.getAccount();

            System.out.println("Welcome, " + userId + "!");

            boolean quit = false;
            while (!quit) {
                System.out.println("Select an option:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Transfer");
                System.out.println("4. View Transaction History");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); 
                        account.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        scanner.nextLine(); 
                        account.withdraw(withdrawalAmount);
                        break;
                    case 3:
                        System.out.print("Enter recipient's account ID: ");
                        String recipientId = scanner.nextLine();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine(); 

                        User recipient = atm.getUser(recipientId);
                        if (recipient != null) {
                            account.transfer(recipient.getAccount(), transferAmount);
                        } else {
                            System.out.println("Recipient account not found.");
                        }
                        break;
                    case 4:
                        account.printTransactionHistory();
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Thank You For Using MY ATM \n Visit Again..");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN.");
        }
    }
}
