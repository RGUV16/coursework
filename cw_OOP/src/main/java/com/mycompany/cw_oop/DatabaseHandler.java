/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

/**
 *
 * @author HP
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.sql.Date;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/article_recommendation";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Replace with your DB password

    private Map<String, List<String>> categoryKeywords;

    public DatabaseHandler() {
        categoryKeywords = new HashMap<>();
        categoryKeywords.put("Business", Arrays.asList("sustainability", "business", "strategies", "economy"));
        categoryKeywords.put("Sports", Arrays.asList("sports",  "players", "championship", "Football"));
        categoryKeywords.put("Science", Arrays.asList("scientists", "Research", "technology", "discoveries"));
        categoryKeywords.put("Educational", Arrays.asList("education", "learning", "schools", "students"));
        categoryKeywords.put("Political", Arrays.asList("political", "leaders", "Global", "nations", "election"));
        categoryKeywords.put("Health", Arrays.asList("treatment", "disease", "health", "hospitals", "medical"));
        categoryKeywords.put("Automotive", Arrays.asList("automotive", "models", "vehicles", "cars"));
        categoryKeywords.put("Weather", Arrays.asList("weather", "hurricane", "temperatures", "climate"));
        categoryKeywords.put("World-news", Arrays.asList("international", "Countries", "geopolitics", "security"));
        categoryKeywords.put("Real-state", Arrays.asList("housing", "real estate", "properties", "construction"));
        categoryKeywords.put("Lifestyle", Arrays.asList("lifestyle", "stress", "well-being", "living"));
        categoryKeywords.put("Entertainment", Arrays.asList("audiences", "media", "entertainment", "music", "streaming"));
        categoryKeywords.put("Technological", Arrays.asList("technology", "industries", "Advancements", "innovation", "AI"));
    }
    
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean addUser(String username, String password, String email) {
        String query = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a user is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Article> getRandomArticles(int count) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM articles ORDER BY RAND() LIMIT ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, count);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int no = rs.getInt("no");
                String category = rs.getString("category");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String author = rs.getString("author");
                Date date = rs.getDate("date");

                articles.add(new Article(no, category, title, content, author, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Indicate an error occurred
        }
        return articles;
    }
    
    // Method to retrieve articles based on a list of keywords
    public List<Article> getArticlesByKeywords(List<String> keywords) {
        List<Article> articles = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM articles WHERE ");

        // Build the query with keyword checks for title and content
        for (int i = 0; i < keywords.size(); i++) {
            if (i > 0) queryBuilder.append(" OR ");
            queryBuilder.append("(title LIKE ? OR content LIKE ?)");
        }
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < keywords.size(); i++) {
                String keywordPattern = "%" + keywords.get(i) + "%";
                stmt.setString(2 * i + 1, keywordPattern); // For title
                stmt.setString(2 * i + 2, keywordPattern); // For content
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int no = rs.getInt("no");
                String category = rs.getString("category");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String author = rs.getString("author");
                Date date = rs.getDate("date");

                articles.add(new Article(no, category, title, content, author, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Indicate an error occurred
        }
        return articles;
    }

    // Getter for categoryKeywords map
    public List<String> getKeywordsForCategory(String category) {
        return categoryKeywords.getOrDefault(category, new ArrayList<>());
    }
    
    public void recordUserFeedback(String username, Article article, String feedbackType) {
        String query = "INSERT INTO user_feedback (username, article_no, feedback_type) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, article.getNo()); // Use the article number as the article ID
            stmt.setString(3, feedbackType);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Methods for admin (now)
    public boolean validateAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if an admin is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to add a new article to the database
    public boolean addArticle(String category, String title, String content, String author, Date date) {
        String query = "INSERT INTO articles (category, title, content, author, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.setString(4, author);
            stmt.setDate(5, date);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing article in the database
    public boolean updateArticle(int articleNo, String category, String title, String content, String author, Date date) {
        String query = "UPDATE articles SET category = ?, title = ?, content = ?, author = ? , date = ? WHERE no = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.setString(4, author);
            stmt.setDate(5, date);
            stmt.setInt(6, articleNo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete an article from the database
    public boolean deleteArticle(int articleNo) {
        String query = "DELETE FROM articles WHERE no = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleNo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to view all users in the system
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String query = "SELECT username FROM user";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method to delete a user from the database
    public boolean deleteUser(String username) {
        String query = "DELETE FROM user WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if the article number exists in the database
    public boolean articleExists(int articleNo) {
        String query = "SELECT COUNT(*) FROM articles WHERE no = ?";
        try (Connection conn = connect(); // Use the existing connect method
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, articleNo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, article exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Check if the username exists in the database
    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (Connection conn = connect(); // Use the existing connect method
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // If count is greater than 0, user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Static method to validate email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email != null && email.matches(emailRegex);
    }
    
    //Manage profile
    public boolean updateUserDetails(String username, String newEmail, String newPassword) {
        String query;
        boolean updateEmail = newEmail != null && !newEmail.isEmpty();
        boolean updatePassword = newPassword != null && !newPassword.isEmpty();

        if (updateEmail && updatePassword) {
            query = "UPDATE user SET email = ?, password = ? WHERE username = ?";
        } else if (updateEmail) {
            query = "UPDATE user SET email = ? WHERE username = ?";
        } else if (updatePassword) {
            query = "UPDATE user SET password = ? WHERE username = ?";
        } else {
            return false; // No updates to perform
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (updateEmail && updatePassword) {
                stmt.setString(1, newEmail);
                stmt.setString(2, newPassword);
                stmt.setString(3, username);
            } else if (updateEmail) {
                stmt.setString(1, newEmail);
                stmt.setString(2, username);
            } else {
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


