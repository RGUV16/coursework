/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String email;
    private DatabaseHandler dbHandler;

    public User(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }
    
    // (now)New constructor to set username, password, and email directly
    public User(DatabaseHandler dbHandler, String username, String password, String email) {
        this.dbHandler = dbHandler;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Getter for username
    public String getUsername() {
        return username;
    }
    
    // now Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for email (optional, add if needed)
    public String getEmail() {
        return email;
    }

    // Getter for password (optional, typically avoided for security)
    public String getPassword() {
        return password;
    }
    
    // NOW Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!isValidEmail(email));
        return dbHandler.addUser(username, password, email);
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();
        return dbHandler.validateUser(username, password);
    }
    
    public boolean updateDetails(DatabaseHandler dbHandler, String newEmail, String newPassword) {
        if ((newEmail == null || newEmail.isEmpty()) && (newPassword == null || newPassword.isEmpty())) {
            return false; // No updates provided
        }
        boolean success = dbHandler.updateUserDetails(username, newEmail, newPassword);
        if (success) {
            if (newEmail != null && !newEmail.isEmpty()) this.email = newEmail; // Update local email
            if (newPassword != null && !newPassword.isEmpty()) this.password = newPassword; // Update local password
        }
        return success;
    }
}


