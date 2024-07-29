# Online Bookstore Test Automation

This project is a sample test automation suite for an online bookstore using Selenium WebDriver with HTMLUnitDriver and TestNG in Java.
The tests verify that the bookstore functionalities such as viewing the list of books, adding books to the cart, and proceeding to checkout work correctly.

## Basic requirements

1. **Java Development Kit (JDK)**
   - Ensure you have JDK. You can download it from (https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe).

2. **Apache Maven**
   - Maven is used to manage the project's dependencies and to build and run the tests. 
You can download Maven from [Maven's official website](https://maven.apache.org/download.cgi).

3. **IDE - integrated development environment **
   - ECLIPSE download 
	https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2023-12/R/eclipse-inst-jre-win64.exe

## Project files
* src/BookOnlineTest/BookStore - Contains the test cases.
* pom.xml - Maven configuration file with dependencies.

## Running tests
1. After opening the project, open .src/BookOnlineTest/BookStore.java 
then right-click in the file choose (Run As) then (TestNG test)
OR 
2. To run the tests, use the following command:
mvn test

## Test Scenarios Covered
1. Verify that the list of books is displayed correctly.
2. Verify that a user can add a book to the cart.
3. Verify that the cart updates correctly when a book is added.
4. Verify that the user can proceed to checkout from the cart.

## Dependencies
* Selenium WebDriver: For browser automation.
* HtmlUnitDriver: A lightweight headless browser for faster testing.
* TestNG: For organizing the test cases.

