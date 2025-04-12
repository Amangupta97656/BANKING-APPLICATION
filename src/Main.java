import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Simple Banking System!");

        int choice;
        do {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    AccountManagement.createAccount();
                    break;
                case 2:
                    System.out.print("Enter Account Number: ");
                    long accountNumber = scanner.nextLong();
                    System.out.print("Enter PIN: ");
                    String pin = scanner.next();

                    if (Authentication.login(accountNumber, pin)) {
                        userMenu(accountNumber);
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    private static void userMenu(long accountNumber) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    AccountManagement.checkBalance(accountNumber);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    Transactions.deposit(accountNumber, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    Transactions.withdraw(accountNumber, withdrawAmount);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }
}
