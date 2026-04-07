# Basic Java Projects

This repository contains a collection of basic Java projects. These projects are designed to demonstrate fundamental programming concepts in Java, such as basic arithmetic, data structures, file I/O, networking, and object-oriented programming.

## Project Overview

- **Calculator**: A command-line calculator with support for basic operators (+, -, *, /, %, ^) and scientific functions (sin, cos, tan, sqrt, log, ln, abs).
- **To-Do List**: A simple application for managing tasks, allowing users to add, view, complete, edit, and delete tasks.
- **Number Guessing Game**: An interactive game where players guess a randomly generated number with progressive hints.
- **Simple Chat Application**: A multi-threaded client-server chat application for real-time messaging.
- **Student Management System**: A system to manage student records, including adding, removing, and viewing students.
- **Tea Recipe Manager**: A simple application for managing tea preparation recipes, grounded in local evidence from the `misc` directory.

## How to Run

To run any of the projects, follow these steps:

1. **Compile the code**: Navigate to the project directory and compile the `.java` files using `javac`.
2. **Execute the application**: Run the compiled class using the `java` command.

### Examples:

- **Calculator**:
  ```bash
  javac Calculator/Calculator.java && cd Calculator && java Calculator
  ```
- **To-Do List**:
  ```bash
  javac TODOList/TODOList.java && cd TODOList && java TODOList
  ```
- **Number Guessing Game**:
  ```bash
  javac NumberGuessingGame/NumberGuessingGame.java && cd NumberGuessingGame && java NumberGuessingGame
  ```
- **Simple Chat Application**:
  - Start the Server:
    ```bash
    javac SimpleChatApplication/ChatServer.java && cd SimpleChatApplication && java ChatServer
    ```
  - Start the Client (in a new terminal):
    ```bash
    javac SimpleChatApplication/ChatClient.java && cd SimpleChatApplication && java ChatClient
    ```
- **Student Management System**:
  ```bash
  javac StudentManagementSystem/*.java && cd StudentManagementSystem && java StudentManagementSystem
  ```
- **Tea Recipe Manager**:
  ```bash
  javac TeaRecipeManager/TeaRecipeManager.java && cd TeaRecipeManager && java TeaRecipeManager
  ```

For a detailed list and links to the projects, please see [java_projects.md](./java_projects.md).
