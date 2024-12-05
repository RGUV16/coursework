/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cw_oop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class RecommendationEngine {
    private String username;

    public RecommendationEngine(String username) {
        this.username = username;
    }

    public void getRecommendations() {
        try {
            String urlString = "http://localhost:5000/recommend?username=" + username;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Unable to fetch recommendations. API returned status code " + conn.getResponseCode());
                return;      
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(content.toString());
            if (jsonArray.length() == 0) {
                System.out.println("No recommendations found.");
                return;
            }

            System.out.println("Recommended Articles:");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject article = jsonArray.getJSONObject(i);
                System.out.println("Title: " + article.getString("title"));
                System.out.println("Content: " + article.getString("content"));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching recommendations: " + e.getMessage());
        }
    }
}

