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

        System.out.println("Welcome to the Personalized News Recommendation System!\nInsert the number of the selcted choice.");
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
            System.out.println("\n\nLogin successful! Welcome!\nInsert the number of the selected choice.");
            System.out.println("1. View Available Articles (All Categories)");
            System.out.println("2. View Business Articles");
            System.out.println("3. View Sports Articles");
            System.out.println("4. View Science Articles");
            System.out.println("5. View Eductional Articles");
            System.out.println("6. View Political Articles");
            System.out.println("7. View Health Articles");
            System.out.println("8. View Automotive (Vehicle-related) Articles");
            System.out.println("9. View Weather Articles");
            System.out.println("10. View World-news Articles");
            System.out.println("11. View Real-state Articles");
            System.out.println("12. View Lifestyle Articles");
            System.out.println("13. View Entertainment Articles");
            System.out.println("14. View Technological Articles");
            System.out.println("15. Get Recommendations");
        } else {
            System.out.println("Login failed.");
        }
    }
}


