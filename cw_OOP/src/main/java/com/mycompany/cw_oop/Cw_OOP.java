/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cw_oop;

/**
 *
 * @author HP
 */
import java.util.Scanner;

public class Cw_OOP {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User(dbHandler);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Personalized News Recommendation System!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean loggedIn = false;
        if (choice == 1) {
            if (user.register()) {
                System.out.println("Registration successful! Please log in.");
                loggedIn = user.login();
            } else {
                System.out.println("Registration failed.");
            }
        } else if (choice == 2) {
            loggedIn = user.login();
        }

        if (loggedIn) {
            System.out.println("Login successful! Welcome, ");
            // Additional functionalities like viewing articles can be added here.
        } else {
            System.out.println("Login failed.");
        }
    }
}


