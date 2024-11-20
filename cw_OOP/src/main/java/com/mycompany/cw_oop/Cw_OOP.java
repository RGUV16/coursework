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
    private static User currentUser; // Track the logged-in user
    private static DatabaseHandler dbHandler = new DatabaseHandler(); // Database handler instance
    private static Scanner scanner = new Scanner(System.in); // Shared scanner instance

    public static void main(String[] args) {
        System.out.println("WELCOME TO THE PERSONALIZED NEWS RECOMMENDATION SYSTEM!\n\nSelect an option by entering its number.");

        // Main menu loop
        while (true) {
            System.out.println("1. Register (User)");
            System.out.println("2. Login (User)");
            System.out.println("3. Login (Administrator)");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    loginAdmin();
                    break;
                case 4:
                    System.out.println("Exiting the system.");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (currentUser != null) {
                // User or admin is logged in, display appropriate menu
                if (currentUser instanceof Administrator) {
                    adminMenu();
                } else {
                    userMenu();
                }
            }
        }
    }

    private static void registerUser() {
        User user = new User(dbHandler);
        if (user.register()) {
            System.out.println("Registration successful! Please log in.");
            loginUser(); // Prompt the user to log in after registration
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void loginUser() {
        User user = new User(dbHandler);
        if (attemptLogin(user)) {
            currentUser = user;
            System.out.println("Login successful! Welcome, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("Maximum login attempts reached. Returning to the main menu.");
        }
    }

    private static void loginAdmin() {
        Administrator admin = new Administrator(dbHandler, null, null, null, "Admin");
        if (attemptLogin(admin)) {
            currentUser = admin;
            System.out.println("Administrator login successful!");
        } else {
            System.out.println("Maximum login attempts reached. Returning to the main menu.");
        }
    }

    private static boolean attemptLogin(User user) {
        int attempts = 0;
        while (attempts < 3) {
            if (user.login()) {
                return true;
            } else {
                attempts++;
                System.out.println("Login failed. Please try again (" + (3 - attempts) + " attempts left).");
            }
        }
        return false;
    }

    private static void adminMenu() {
        Administrator admin = (Administrator) currentUser;

        System.out.println("\nAdministrator Menu:");
        System.out.println("1. Add Articles");
        System.out.println("2. Update Articles");
        System.out.println("3. Delete Articles");
        System.out.println("4. View Users");
        System.out.println("5. Delete Users");
        System.out.println("6. Log Out");

        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (admin.addArticle()) {
                        System.out.println("Article added successfully!");
                    } else {
                        System.out.println("Failed to add article.");
                    }
                    break;
                case 2:
                    if (admin.updateArticle()) {
                        System.out.println("Article updated successfully!");
                    } else {
                        System.out.println("Failed to update article.");
                    }
                    break;
                case 3:
                    if (admin.deleteArticle()) {
                        System.out.println("Article deleted successfully!");
                    } else {
                        System.out.println("Failed to delete article.");
                    }
                    break;
                case 4:
                    admin.viewAllUsers();
                    break;
                case 5:
                    if (admin.deleteUser()) {
                        System.out.println("User deleted successfully!");
                    } else {
                        System.out.println("Failed to delete user.");
                    }
                    break;
                case 6:
                    logout();
                    return; // Exit admin menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu() {
        System.out.println("\nUser Menu:");
        System.out.println("1. View All Articles");
        System.out.println("2. Get Recommendations");
        System.out.println("3. Manage Profile");
        System.out.println("4. Log Out");

        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewArticles();
                    break;
                case 2:
                    RecommendationEngine recommendationEngine = new RecommendationEngine(currentUser.getUsername());
                    recommendationEngine.getRecommendations(); // Fetch and display recommendations
                    break;
                case 3:
                    manageProfile();
                    break;
                case 4:
                    logout();
                    return; // Exit user menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewArticles() {
        System.out.println("Choose a category or view all articles:");
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
        System.out.println("15. Return to the User Menu");
                
        while (true) {
            int articleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        
            // Log out if the user chooses the option
            if (articleChoice == 15) {
                System.out.println("Returning to the User Menu...");
                break; // Exit the loop and return to the user menu
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
                    category = "Real-state";
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
                            dbHandler.recordUserFeedback(currentUser.getUsername(), article, "like");
                            System.out.println("You liked this article.");
                            break;
                        case 2:
                            dbHandler.recordUserFeedback(currentUser.getUsername(), article, "dislike") ;
                            System.out.println("You disliked this article.");
                            break;
                        case 3:
                            dbHandler.recordUserFeedback(currentUser.getUsername(), article, "skip"); // Record "skip" feedback
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

    private static void getRecommendations() {
        // Logic to fetch recommendations based on user's history or preferences
        System.out.println("Fetching recommendations for you...");
    }

    private static void manageProfile() {
        System.out.println("Manage Profile:");
        System.out.println("1. Update Email");
        System.out.println("2. Update Password");
        System.out.println("3. Update Both Email and Password");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String newEmail = null, newPassword = null;
        if (choice == 1 || choice == 3) {
            System.out.print("Enter new email: ");
            newEmail = scanner.nextLine();
        }
        if (choice == 2 || choice == 3) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();
        }

        if (currentUser.updateDetails(dbHandler, newEmail, newPassword)) {
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Failed to update profile. Please try again.");
        }
    }

    private static void logout() {
        System.out.println("Logging out...");
        currentUser = null; // Clear the current user session
    }
}


