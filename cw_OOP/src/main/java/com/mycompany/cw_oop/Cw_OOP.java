/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cw_oop;

/**
 *
 * @author HP
 */
import java.util.List;
import java.util.Scanner;

public class Cw_OOP {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User(dbHandler);
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME TO THE PRSONALIZED NEWS RECOMMONDATION SYSTEM!\n\nInsert the number of the selcted choice.");
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
            System.out.println("\nLogin successful! Welcome!\n\nInsert the number of the selected choice.");
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
            
            int articleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (articleChoice == 1) {
                // Fetch and display a random selection of articles
                List<Article> articles = dbHandler.getRandomArticles(5); // Get 5 random articles
                for (Article article : articles) {
                    System.out.println("Category: " + article.getCategory());
                    System.out.println("Title: " + article.getTitle());
                    System.out.println(article.getContent());
                    System.out.println("Author: " + article.getAuthor());
                    System.out.println();
                }
            } else {
                // Handle other choices
            }
        } else {
            System.out.println("Login failed. Username or password must be incorrect. Please enter valid details!");
        }
    }
}


