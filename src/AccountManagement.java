import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountManagement {
    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();

        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.next();

        long accountNumber = generateAccountNumber();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String hashedPin = Authentication.hashPin(pin);

            String query = "INSERT INTO Users (AccountNumber, Name, PIN_HASH, Balance) VALUES (?, ?, ?, 0)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, accountNumber);
            stmt.setString(2, name);
            stmt.setString(3, hashedPin);
            stmt.executeUpdate();

            System.out.println("Account Created Successfully! Your Account Number: " + accountNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkBalance(long accountNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT Balance FROM Users WHERE AccountNumber=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Your Balance: ₹" + rs.getDouble("Balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long generateAccountNumber() {
        return (long) (Math.random() * 9000000000L) + 1000000000L; // 10-digit random number
    }
}
