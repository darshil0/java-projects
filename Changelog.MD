# Changelog

All notable changes to this project will be documented in this file.

## [1.2.0] - 2026-04-20

### Fixed
- **TeaRecipeManager**: Fixed resource leak by properly closing Scanner using try-with-resources
- **TeaRecipeManager**: Fixed spelling inconsistencies in default recipe (capitalization of "cups", "ginger powder")
- **Documentation**: Fixed typo "ingrediants" → "ingredients" in Tea Project reference
- **misc/Tea Project**: Fixed multiple spelling errors ("ingrediants" → "ingredients", "Server" → "Serve")

## [1.1.0] - 2025-04-07

### Added
- **Tea Recipe Manager**: A new project created based on grounded evidence from `misc/Tea Project`, allowing users to manage tea preparation recipes.
- **Detailed Documentation**: Enhanced `README.md` with Project Overviews and a "How to Run" section with specific compilation and execution commands for each project.

### Fixed
- **Calculator**: Repaired the `evaluate` method by implementing a clean recursive descent parser for basic arithmetic, scientific functions, and parentheses. Fixed significant syntax errors.
- **To-Do List**: Renamed the source file from `Todolist.java` to `TODOList.java` to match the class name and fix a compilation error.
- **Simple Chat Application**:
    - Cleaned up `ChatClient.java`, removing duplicate initialization blocks and fixing missing braces.
    - Cleaned up `ChatServer.java`, removing redundant `synchronized` blocks and logic.
- **Student Management System**: Fixed redundant input handling and duplicate student removal logic in `StudentManagementSystem.java`.

### Changed
- **Project Structure**: Updated `java_projects.md` to reflect the new `Tea Recipe Manager` project.
- **General Cleanup**: Standardized input handling across various CLI-based applications.
