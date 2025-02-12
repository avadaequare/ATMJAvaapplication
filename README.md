# ATMJAvaapplication
ATM Web aplication deployment

make sure you install java jdk>17 ( apt install openjdk-17-jdk-headless) and maven installed (apt install maven -y)

https://github.com/kishanrajput23/Java-Projects-Collections.git Atm interface

📌 Steps to Convert the ATM App into a Web Application
1️ Create a Spring Boot Project
Since we need a web interface, we'll use Spring Boot with Spring MVC.
You can create the project manually or use Spring Initializr.

Create the Project Using Spring Initializr
Run the following command in your terminal:

mvn archetype:generate -DgroupId=com.example -DartifactId=ATMWebApp \
-DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false --. Breaking Down the Command

1. mvn archetype:generate
This tells Maven to generate a new project based on a predefined archetype (a template).
An archetype is a Maven project template that defines the basic structure of the project.
2. -DgroupId=com.example
The groupId is a unique identifier for your project, usually based on your organization's domain.
In this case, com.example is used as a placeholder, but in a real project, it could be something like com.mycompany.
3. -DartifactId=ATMWebApp
The artifactId is the name of the project (and also the name of the JAR file produced).
Here, ATMWebApp is the project name.
4. -DarchetypeArtifactId=maven-archetype-quickstart
Specifies the template to use.
The maven-archetype-quickstart archetype is a basic Java project structure with:
A src/main/java folder for application code.
A src/test/java folder for unit tests.
A sample App.java file.
A pom.xml file with basic dependencies.
5. -DinteractiveMode=false
This disables interactive prompts, meaning:
Maven will not ask for user input.
It will use the provided groupId, artifactId, and archetype settings automatically.
What Happens When You Run This?
Maven downloads the necessary files for the maven-archetype-quickstart template.
It creates a new Java project structure like this:

ATMWebApp/
├── pom.xml                # Maven build configuration file
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── example
│   │               └── App.java  # Sample Java application
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── AppTest.java  # Sample JUnit test


2️ Modify pom.xml to Include Spring Boot Dependencies
Replace the pom.xml with the following content:

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>ATMWebApp</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>ATMWebApp</name>
    <description>ATM Web Application using Spring Boot</description>

    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.1.0</version>
        </dependency>

        <!-- Spring Boot Starter Thymeleaf for HTML Views -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
            <version>3.1.0</version>
        </dependency>

        <!-- JUnit 4 for Testing -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Spring Boot Maven Plugin -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>3.1.0</version>
            </plugin>

            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>


3️ Create the Spring Boot Application Class (ATMWebAppApplication.java)
Create a file ATMWebAppApplication.java inside src/main/java/com/example/:

package com.example;

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ATMWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ATMWebAppApplication.class, args);
    }
}



4️ Create an ATM Controller (ATMController.java)
Inside src/main/java/com/example/controller/, create a new file ATMController.java:

package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ATMController {

    private int balance = 100000; // Initial balance

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("balance", balance);
        return "index";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int amount, Model model) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            model.addAttribute("message", "Withdraw successful!");
        } else {
            model.addAttribute("message", "Insufficient balance or invalid amount!");
        }
        model.addAttribute("balance", balance);
        return "index";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam int amount, Model model) {
        if (amount > 0) {
            balance += amount;
            model.addAttribute("message", "Deposit successful!");
        } else {
            model.addAttribute("message", "Enter a valid amount!");
        }
        model.addAttribute("balance", balance);
        return "index";
    }
}
5️ Create the HTML Web Interface (index.html)
Inside src/main/resources/templates/, create a new file index.html:


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ATM Web Application</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { width: 50%; margin: auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1>ATM Web Application</h1>
        <h2>Current Balance: $<span th:text="${balance}"></span></h2>

        <h3>Withdraw Money</h3>
        <form action="/withdraw" method="post">
            <input type="number" name="amount" placeholder="Enter amount" required>
            <button type="submit">Withdraw</button>
        </form>

        <h3>Deposit Money</h3>
        <form action="/deposit" method="post">
            <input type="number" name="amount" placeholder="Enter amount" required>
            <button type="submit">Deposit</button>
        </form>

        <p style="color: green;" th:text="${message}"></p>
    </div>
</body>
</html>

6️ Run the Web Application
Now, build and run the Spring Boot application:

mvn clean install
mvn spring-boot:run

nohup mvn spring-boot:run > app.log 2>&1 &(to run the application in backgroud even if we exit terminal)

The web application will start on http://localhost:8080 

<img width="947" alt="image" src="https://github.com/user-attachments/assets/15107c7c-0582-45e9-872a-0afadac345bc" />

