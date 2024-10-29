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

public class User {
    private String username;
    private String password;
    private String email;
    private DatabaseHandler dbHandler;

    public User(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
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
}


