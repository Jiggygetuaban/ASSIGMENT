package jat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JAT {

    
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver          
            con = DriverManager.getConnection("jdbc:sqlite:jobtracker.db"); // Establish connection
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);       
        int opt;

        // Main menu loop
        do {    
            try {
                System.out.println("\n\t=== Job Application Tracker ===\n");
                System.out.println("1. Job Applications\n2. Interview Schedules\n3. View All Companies\n4. Generate Reports\n5. Exit");
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();
                System.out.println("");

                // Switch case for handling different options
                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        JobApplicationTracker jobAppTracker = new JobApplicationTracker();
                        jobAppTracker.manageApplications(scan); // Managing job applications
                        break;
                        
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        // Interview schedules logic can be added here
                        System.out.println("Feature Coming Soon: Interview Schedules");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        // View all companies logic can be added here
                        System.out.println("Feature Coming Soon: View All Companies");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        // Generate reports logic can be added here
                        System.out.println("Feature Coming Soon: Generate Reports");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                opt = -1; 
            }
        } while (opt != 5);        
    }
}


