/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

/**
 *
 * @author HP
 */
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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

    public boolean addArticle() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter article category: ");
        String category = scanner.nextLine();
        System.out.print("Enter article title: ");
        String title = scanner.nextLine();
        System.out.print("Enter article content: ");
        String content = scanner.nextLine();
        System.out.print("Enter article author: ");
        String author = scanner.nextLine();
        System.out.print("Enter article date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();

        // Parse date string to java.sql.Date
        Date date = parseDate(dateString);
        if (date == null) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }

        return dbHandler.addArticle(category, title, content, author, date);
    }

    public boolean updateArticle() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 3; // Allow up to 3 attempts for retrying
        
        while (attempts > 0) {
            System.out.print("Enter article number to update: ");
            int articleNo = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Check if article number exists
            if (!dbHandler.articleExists(articleNo)) {
                System.out.println("Error: Article number does not exist. Please try again.");
                attempts--;
                if (attempts == 0) {
                    System.out.println("Maximum attempts reached. Exiting update process.");
                    return false;
                }
                continue;
            }

            System.out.print("Enter new article category: ");
            String category = scanner.nextLine();
            System.out.print("Enter new article title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new article content: ");
            String content = scanner.nextLine();
            System.out.print("Enter new article author: ");
            String author = scanner.nextLine();
            System.out.print("Enter new article date (yyyy-MM-dd): ");
            String dateString = scanner.nextLine();

            // Parse date string to java.sql.Date
            Date date = parseDate(dateString);
            if (date == null) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return false;
            }

            return dbHandler.updateArticle(articleNo, category, title, content, author, date);
        }
        return false;
    }
    
    private Date parseDate(String dateString) {
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            return new Date(utilDate.getTime()); // Convert to java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteArticle() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 3; // Allow up to 3 attempts for retrying
        
        while (attempts > 0) {
            System.out.print("Enter article number to delete: ");
            int articleNo = scanner.nextInt();
            
            // Check if article number exists
            if (!dbHandler.articleExists(articleNo)) {
                System.out.println("Error: Article number does not exist. Please try again.");
                attempts--;
                if (attempts == 0) {
                    System.out.println("Maximum attempts reached. Exiting delete process.");
                    return false;
                }
                continue;
            }

            return dbHandler.deleteArticle(articleNo);
        }
        return false;
    }

    public void viewAllUsers() {
        List<String> users = dbHandler.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Registered Users:");
            for (String user : users) {
                System.out.println("- " + user);
            }
        }
    }

    public boolean deleteUser() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 3; // Allow up to 3 attempts for retrying
        
        while (attempts > 0) {
            System.out.print("Enter username of the user to delete: ");
            String username = scanner.nextLine();
            
            // Check if username exists
            if (!dbHandler.userExists(username)) {
                System.out.println("Error: Username does not exist. Please try again.");
                attempts--;
                if (attempts == 0) {
                 System.out.println("Maximum attempts reached. Exiting delete process.");
                    return false;
                }
                continue;
            }
            return dbHandler.deleteUser(username);
        }
        return false;
    }
}


