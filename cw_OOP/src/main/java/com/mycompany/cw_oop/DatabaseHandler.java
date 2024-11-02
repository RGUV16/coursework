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

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/article_recommendation";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Replace with your DB password

    private Map<String, List<String>> categoryKeywords;

    public DatabaseHandler() {
        categoryKeywords = new HashMap<>();
        categoryKeywords.put("Business", Arrays.asList("companies", "business", "strategy", "economic"));
        categoryKeywords.put("Sports", Arrays.asList("sports",  "player", "championship"));
        categoryKeywords.put("Science", Arrays.asList("scientists", "Research", "scientific"));
        categoryKeywords.put("Educational", Arrays.asList("education", "learning", "Schools", "students"));
        categoryKeywords.put("Political", Arrays.asList("Political", "United", "Global", "Nations", "election"));
        categoryKeywords.put("Health", Arrays.asList("treatment", "disease", "healthcare", "hospitals", "medical"));
        categoryKeywords.put("Automotive", Arrays.asList("automotive", "models", "vehicles", "cars", "insurance"));
        categoryKeywords.put("Weather", Arrays.asList("weather", "hurricane", "temperatures", "emergency"));
        categoryKeywords.put("World-news", Arrays.asList("international", "Countries", "climate", "security", "Tensions"));
        categoryKeywords.put("Real-state", Arrays.asList("housing", "Estate", "properties", "construction"));
        categoryKeywords.put("Lifestyle", Arrays.asList("lifestyle", "stress", "well-being", "living", "daily"));
        categoryKeywords.put("Entertainment", Arrays.asList("audiences", "media", "entertainment", "music", "streaming"));
        categoryKeywords.put("Technological", Arrays.asList("technology", "industries", "Advancements", "innovation", "Artificial"));
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

                articles.add(new Article(no, category, title, content, author));
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

                articles.add(new Article(no, category, title, content, author));
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
}


