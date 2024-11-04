/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

/**
 *
 * @author HP
 */
import java.util.Scanner;

public class Administrator extends User {
    private String role;
    private DatabaseHandler dbHandler;

    public Administrator(DatabaseHandler dbHandler, String username, String password, String email, String role) {
        // Call the new User constructor that sets username, password, and email
        super(dbHandler, username, password, email);
        this.role = role;
        this.dbHandler = dbHandler;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role (optional, if you want to change the role later)
    public void setRole(String role) {
        this.role = role;
    }

    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        setUsername(scanner.nextLine());
        System.out.print("Enter admin password: ");
        setPassword(scanner.nextLine());
        return dbHandler.validateAdmin(getUsername(), getPassword());
    }

    // Example method specific to Administrator for managing users
    public void manageUsers() {
        System.out.println("Admin managing users...");
        // Implement user management functionality here
    }

    // Example method for viewing system statistics (optional)
    public void viewSystemStats() {
        System.out.println("Admin viewing system statistics...");
        // Implement system statistics functionality here
    }
}


