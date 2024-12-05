### README File for News Article Recommendation System  

#### **Project Title**  
**Personalized News Recommendation System**

---

### **Overview**  
This project is a Personalized News Recommendation System developed using Java with object-oriented principles and machine learning integration. The system provides users with tailored news recommendations based on their reading history and feedback, leveraging a Python-based recommendation model (`recommendation_model.py`).  

The backend uses a MySQL database to manage user, administrator, and article details. This README file provides instructions to set up the database, dependencies, and run the application.  

---

### **Features**  
- User and Admin Management.  
- Article Categorization.  
- Personalized News Recommendations using Machine Learning.  
- Concurrency and Error Handling for smooth performance.  

---

### **Prerequisites**  
Ensure you have the following installed:  
1. **Java Development Kit (JDK)** - Version 8 or above.  
2. **MySQL** - Version 8.0 or above.  
3. **Python** - Version 3.8 or above (with necessary libraries for `recommendation_model.py`).  
4. **Maven** - For managing dependencies.  

---

### **Dependencies**  
The following dependencies are used in this project (included in `pom.xml`):  
- **MySQL Connector**: `com.mysql:mysql-connector-j:8.0.33`  
- **Google Gson**: `com.google.code.gson:gson:2.10.1`  
- **Protobuf**: `com.google.protobuf:protobuf-java:3.21.9`  

---

### **Database Setup**  
1. Open **MySQL Workbench** or a similar tool.  
2. Import the provided SQL file (`article_recommendation.sql`) into your database:  
   - Use the following command in the MySQL command line:  
     ```bash
     mysql -u [username] -p [database_name] < article_recommendation.sql
     ```  
   - Replace `[username]` and `[database_name]` with your MySQL credentials and the desired database name.  

3. Confirm that the database includes all necessary tables and initial data (users, administrators, and articles).  

---

### **Running the Project**  
1. **Backend Setup**:  
   - Ensure your MySQL database is running and configured with the correct connection properties in the source code (`src` folder).  
   - Use Maven to install required dependencies if they are not already downloaded:  
     ```bash
     mvn install
     ```  

2. **Recommendation Model Setup**:  
   - Navigate to the folder containing `recommendation_model.py`.  
   - Install the required Python libraries using the `pip` command:  
     ```bash
     pip install -r requirements.txt
     ```  
   - Start the Flask API by running:  
     ```bash
     python recommendation_model.py
     ```  

3. **Run the Java Application**:  
   - Compile and run the project using your preferred IDE or command line:  
     ```bash
     mvn exec:java -Dexec.mainClass="your.main.class.Name"
     ```  
   - Replace `"your.main.class.Name"` with the name of your main class.

---


### **Notes**  
- Ensure proper synchronization between the Python model API and the Java application.  
- If the program requires user credentials, use the preloaded ones in the database or update the database directly.  

---

### **Contact**  
For further assistance, contact:  
- **Name**: Vinuji Dilanya Hewapathirana 
- **Email**: vinuji.20231061@iit.ac.lk 

