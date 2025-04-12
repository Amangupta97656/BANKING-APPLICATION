# Simple Banking System

## Description

The **Simple Banking System** is a Java-based console application that allows users to perform basic banking operations like creating an account, logging in, checking balance, depositing money, and withdrawing money. It provides a simple interface for users to interact with their bank accounts.

## Features

1. **Create Account**: Allows the creation of a new account.
2. **Login**: Allows users to log in using their account number and PIN.
3. **User Menu**: After logging in, users can perform the following actions:
   - Check Account Balance
   - Deposit Money
   - Withdraw Money
4. **Exit**: Exits the system.

## Prerequisites

- **Java 8 or higher** installed on your system.
- A basic understanding of Java programming.

## Setup

1. **Clone or Download the Repository**:  
   Clone the repository or download the project folder to your local machine.

   ```bash
   git clone <repository-url>
   ```

2. **Open the Project in an IDE**:  
   Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans).

3. **Compile and Run**:  
   Navigate to the `Main.java` file and run the program. You can do this by either:
   - Using your IDE's built-in run functionality.
   - Compiling the program manually via the terminal:

   ```bash
   javac Main.java
   java Main
   ```

## How to Use

### 1. Create Account
   - Upon running the program, you'll be presented with a menu.
   - Select option **1** to create a new account. (Note: The implementation for creating accounts should be handled by the `AccountManagement` class.)

### 2. Login
   - Select option **2** to log in to your account.
   - Enter your **Account Number** and **PIN** when prompted.
   - If the login is successful, you will be redirected to the user menu.

### 3. User Menu
   Once logged in, you can choose from the following options:
   - **1. Check Balance**: View your account balance.
   - **2. Deposit**: Deposit money into your account.
   - **3. Withdraw**: Withdraw money from your account.
   - **4. Exit**: Log out and exit the user menu.

### 4. Exit the Program
   - Select option **3** from the main menu to exit the program.

## Code Structure

- **Main.java**: The main class that controls the program's flow and user interaction.
- **AccountManagement.java**: Handles account creation and balance checking.
- **Authentication.java**: Manages login functionality.
- **Transactions.java**: Manages deposit and withdrawal operations.

## Example Output

```bash
Welcome to Simple Banking System!

1. Create Account
2. Login
3. Exit
Enter choice: 2
Enter Account Number: 123456
Enter PIN: 1234

User Menu:
1. Check Balance
2. Deposit
3. Withdraw
4. Exit
Enter choice: 1
Your current balance is $1000.00
```
