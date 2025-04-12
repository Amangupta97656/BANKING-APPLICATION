import java.security.MessageDigest;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Authentication {
    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCK_TIME_MINUTES = 5; // Lock duration

    public static boolean login(long accountNumber, String pin) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT PIN_HASH, WrongAttempts, LockedUntil FROM Users WHERE AccountNumber=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Account not found.");
                return false;
            }

            String storedHash = rs.getString("PIN_HASH");
            int failedAttempts = rs.getInt("WrongAttempts");
            Timestamp lockedUntil = rs.getTimestamp("LockedUntil");

            // Check if account is locked
            if (lockedUntil != null && lockedUntil.after(new Timestamp(System.currentTimeMillis()))) {
                String formattedLockTime = formatTimestamp(lockedUntil);
                System.out.println("🔒 Account is locked until: " + formattedLockTime);
                return false;
            }

            // Verify PIN
            if (storedHash.equals(hashPin(pin))) {
                System.out.println("Login successful!");
                resetFailedAttempts(accountNumber);
                return true;
            } else {
                System.out.println("Incorrect PIN.");
                incrementFailedAttempts(accountNumber, failedAttempts);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void resetFailedAttempts(long accountNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Users SET WrongAttempts = 0, LockedUntil = NULL WHERE AccountNumber=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, accountNumber);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void incrementFailedAttempts(long accountNumber, int currentAttempts) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (currentAttempts + 1 >= MAX_ATTEMPTS) {
                Timestamp lockUntil = new Timestamp(System.currentTimeMillis() + (LOCK_TIME_MINUTES * 60 * 1000));
                String formattedLockTime = formatTimestamp(lockUntil);

                String query = "UPDATE Users SET WrongAttempts = ?, LockedUntil = ? WHERE AccountNumber=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, MAX_ATTEMPTS);
                stmt.setTimestamp(2, lockUntil);
                stmt.setLong(3, accountNumber);
                stmt.executeUpdate();

                System.out.println("Account locked until: " + formattedLockTime);
            } else {
                String query = "UPDATE Users SET WrongAttempts = WrongAttempts + 1 WHERE AccountNumber=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setLong(1, accountNumber);
                stmt.executeUpdate();
                System.out.println(" # " + (MAX_ATTEMPTS - currentAttempts - 1) + " attempts remaining.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatTimestamp(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
