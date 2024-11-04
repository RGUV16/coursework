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
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME TO THE PERSONALIZED NEWS RECOMMENDATION SYSTEM!\n\nInsert the number of the selected choice.");
        System.out.println("1. Register (User)");
        System.out.println("2. Login (User)");
        System.out.println("3. Login (Administrator)");
        System.out.println("4. Log Out");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean loggedIn = false;
        User user = null;
        
        if (choice == 1) {
            user = new User(dbHandler);
            if (user.register()) {
                System.out.println("Registration successful! Please log in.");
                loggedIn = user.login();
            } else {
                System.out.println("Registration failed.");
            }
        } else if (choice == 2) {
            user = new User(dbHandler);
            loggedIn = user.login();
        } else if (choice == 3) {
            Administrator admin = new Administrator(dbHandler, null, null, null, "Admin");
            if (admin.login()) {
                System.out.println("Administrator login successful!");
                loggedIn = true;
                user = admin;
            } else {
                System.out.println("Administrator login failed. Username or password is incorrect.");
            }
        } else if (choice == 4) {
            System.out.println("Logged out. Exiting the system.");
            System.exit(0); // Ends the program if user chooses to log out
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        if (loggedIn) {
            System.out.println("\nLogin successful! Welcome!\n\nInsert the number of the selected choice.");
            // Check if the user is an administrator
            if (user instanceof Administrator) {
                // Admin options
                System.out.println("1. Add Articles");
                System.out.println("2. Update Articles");
                System.out.println("3. Delete Articles");
                System.out.println("4. View Users");
                System.out.println("5. Delete Users");
                System.out.println("6. Log Out");
                
                while (true) {
                    int adminChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (adminChoice) {
                        case 1:
                            if (((Administrator) user).addArticle()) {
                                System.out.println("Article added successfully!");
                            } else {
                                System.out.println("Failed to add article.");
                            }
                            break;
                        case 2:
                            if (((Administrator) user).updateArticle()) {
                                System.out.println("Article updated successfully!");
                            } else {
                                System.out.println("Failed to update article.");
                            }
                            break;
                        case 3:
                            if (((Administrator) user).deleteArticle()) {
                                System.out.println("Article deleted successfully!");
                            } else {
                                System.out.println("Failed to delete article.");
                            }
                            break;
                        case 4:
                            ((Administrator) user).viewAllUsers();
                            break;
                        case 5:
                            if (((Administrator) user).deleteUser()) {
                                System.out.println("User deleted successfully!");
                            } else {
                                System.out.println("Failed to delete user.");
                            }
                            break;
                        case 6:
                            System.out.println("Logged out. Exiting the system.");
                            return; // Exit the program
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else { //User options
                // Keep displaying choices until user logs out
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
                System.out.println("16. Log Out");
                
                while (true) {
                    int articleChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                
                    // Log out if the user chooses the option
                    if (articleChoice == 16) {
                        System.out.println("Logged out. Exiting the system.");
                        break; // Exit the loop and end the program
                    }

                    List<Article> articles = null;
                    String category = null;
                    boolean showCategory = false;

                    switch (articleChoice) {
                        case 1:
                            articles = dbHandler.getRandomArticles(15); // Get 15 random articles
                            showCategory = true; 
                            break;
                        case 2:
                            category = "Business";
                            break;
                        case 3:
                            category = "Sports";
                            break;
                        case 4:
                            category = "Science";
                            break;
                        case 5:
                            category = "Educational";
                            break;
                        case 6:
                            category = "Political";
                            break;
                        case 7:
                            category = "Health";
                            break;
                        case 8:
                            category = "Automotive";
                            break;
                        case 9:
                            category = "Weather";
                            break;
                        case 10:
                            category = "World-news";
                            break;
                        case 11:
                            category = "Real-estate";
                            break;
                        case 12:
                            category = "Lifestyle";
                            break;
                        case 13:
                            category = "Entertainment";
                            break;
                        case 14:
                            category = "Technological";
                            break;
                        default:
                            System.out.println("Invalid choice.");
                            return;
                    }

                    // Retrieve articles by keywords if a specific category is chosen
                    if (category != null) {
                        List<String> keywords = dbHandler.getKeywordsForCategory(category);
                        articles = dbHandler.getArticlesByKeywords(keywords);
                    }

                    // Display articles
                    if (articles == null || articles.isEmpty()) {
                        System.out.println("No articles found for the selected category.");
                    } else {
                        for (Article article : articles) {
                            System.out.println("Article Number: " + article.getNo());
                            if (showCategory) {
                                System.out.println("Category: " + article.getCategory());
                            }
                            System.out.println("Title: " + article.getTitle());
                            System.out.println(article.getContent());
                            System.out.println("Author: " + article.getAuthor());
                            System.out.println();

                            // Prompt user for feedback on each article
                            System.out.println("Do you like this article? (1. Like  2. Dislike  3. Skip)");
                            int feedbackChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (feedbackChoice) {
                                case 1:
                                    dbHandler.recordUserFeedback(user.getUsername(), article, "like");
                                    System.out.println("You liked this article.");
                                    break;
                                case 2:
                                    dbHandler.recordUserFeedback(user.getUsername(), article, "dislike");
                                    System.out.println("You disliked this article.");
                                    break;
                                case 3:
                                    dbHandler.recordUserFeedback(user.getUsername(), article, "skip"); // Record "skip" feedback
                                    System.out.println("You skipped this article.");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Skipping to the next article.");
                            }

                            // Ask if the user wants to continue or go back to the menu
                            System.out.println("Do you want to continue reading articles? (1. Yes  2. No)");
                            int continueChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            if (continueChoice == 2) {
                                System.out.println("Returning to the main menu...");
                                break; // Break out of the loop to return to the main menu
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Login failed. Username or password must be incorrect. Please enter valid details!");
        }
    } 
}


