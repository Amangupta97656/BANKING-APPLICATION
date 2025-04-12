import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Transactions {
    public static void deposit(long accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateBalance = "UPDATE Users SET Balance = Balance + ? WHERE AccountNumber=?";
            PreparedStatement stmt = conn.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setLong(2, accountNumber);
            stmt.executeUpdate();

            logTransaction(accountNumber, "Deposit", amount);

            System.out.println("Deposit Successful! ₹" + amount + " credited.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withdraw(long accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkBalance = "SELECT Balance FROM Users WHERE AccountNumber=?";
            PreparedStatement stmt = conn.prepareStatement(checkBalance);
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("Balance");
                if (amount > currentBalance) {
                    System.out.println("Insufficient Balance.");
                    return;
                }

                String updateBalance = "UPDATE Users SET Balance = Balance - ? WHERE AccountNumber=?";
                PreparedStatement stmt2 = conn.prepareStatement(updateBalance);
                stmt2.setDouble(1, amount);
                stmt2.setLong(2, accountNumber);
                stmt2.executeUpdate();

                logTransaction(accountNumber, "Withdrawal", amount);
                System.out.println("Withdrawal Successful! ₹" + amount + " debited.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void logTransaction(long accountNumber, String type, double amount) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Transactions (AccountNumber, Type, Amount, BalanceAfter) " +
                    "VALUES (?, ?, ?, (SELECT Balance FROM Users WHERE AccountNumber=?))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, accountNumber);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.setLong(4, accountNumber);
            stmt.executeUpdate();
        }
    }
}
