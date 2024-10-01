
package jat;

import static jobtracker.JobTracker.connectDB; // Assuming connection is from JobTracker package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JATS {
    String companyName;
    String jobTitle;
    String applicationDate;
    String status;

    public void viewApplications(){
        String sql = "SELECT * FROM JOB_APPLICATIONS";
        try {
            Connection con = connectDB();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet result = pst.executeQuery();
            
            if (!result.isBeforeFirst()) {  
                System.out.println("No job applications found."); return;
            }
            
            System.out.println("\n\t\t      === JOB APPLICATIONS LIST ===\n");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-20s %-20s %-15s\n", "App ID", "Company Name", "Job Title", "Application Date", "Status");
            System.out.println("------------------------------------------------------------------");
            while(result.next()){
                int id = result.getInt("ID");
                String cName = result.getString("company_name");
                String jTitle = result.getString("job_title");
                String appDate = result.getString("application_date");
                String appStatus = result.getString("status");
                
                System.out.printf("  %-13d   %-18s   %-18s   %-18s   %-10s\n", id, cName, jTitle, appDate, appStatus);
            }
            System.out.println("------------------------------------------------------------------");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addApplication(Scanner scan){
        System.out.println("\n\t\t=== ADDING NEW JOB APPLICATION ===\n");
        System.out.println("Enter Job Application Details:");
        
        System.out.print("Company Name: ");
        this.companyName = scan.nextLine();
        
        System.out.print("Job Title: ");
        this.jobTitle = scan.nextLine();
        
        System.out.print("Application Date (YYYY-MM-DD): ");
        this.applicationDate = scan.nextLine();
        
        System.out.print("Application Status (e.g., Applied, Interviewing, Offer, etc.): ");
        this.status = scan.nextLine();
                
        String sql = "INSERT INTO JOB_APPLICATIONS (company_name, job_title, application_date, status) VALUES (?, ?, ?, ?)";       
        try {
            Connection con = connectDB();           
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, companyName);
            pst.setString(2, jobTitle);
            pst.setString(3, applicationDate);
            pst.setString(4, status);
            pst.executeUpdate();
            System.out.println("\nJob Application Successfully Added");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteApplication(Scanner scan){
        System.out.println("\n\t\t=== DELETING A JOB APPLICATION ===\n");
        
        System.out.print("Application ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM JOB_APPLICATIONS WHERE ID = ?";
        try {
            Connection con = connectDB();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            int success = pst.executeUpdate();
            
            if(success > 0){
                System.out.println("\nJob Application Successfully Deleted.");
            }else{
                System.out.println("\nNo Application Found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void editApplication(Scanner scan){
        System.out.println("\n\t\t=== EDIT A JOB APPLICATION ===\n");
        
        String findID = "SELECT * FROM JOB_APPLICATIONS WHERE ID = ?";
        String sql = "UPDATE JOB_APPLICATIONS SET company_name = ?, job_title = ?, application_date = ?, status = ? WHERE ID = ?";
        
        System.out.print("Application ID you want to Edit: ");
        int id = scan.nextInt();
        scan.nextLine();
        
        try {
            Connection con = connectDB();
            
            PreparedStatement findIDpst = con.prepareStatement(findID);
            findIDpst.setInt(1, id);
            ResultSet rs = findIDpst.executeQuery();
            
            if(!rs.next()){
                System.out.println("Job Application with ID " + id + " Doesn't Exist.");
                return;
            }
            
            int appId = rs.getInt("ID");
            String cName = rs.getString("company_name");
            String jTitle = rs.getString("job_title");
            String appDate = rs.getString("application_date");
            String appStatus = rs.getString("status");
            
            System.out.println("\n Selected Job Application");
            System.out.printf("%-15s %-20s %-20s %-20s %-15s\n", "App ID", "Company Name", "Job Title", "Application Date", "Status");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("  %-13d   %-18s   %-18s   %-18s   %-10s\n", appId, cName, jTitle, appDate, appStatus);
            System.out.println("------------------------------------------------------------------");
            
            System.out.print("Enter new Company Name: ");
            String newCompanyName = scan.nextLine();
            
            System.out.print("Enter new Job Title: ");
            String newJobTitle = scan.nextLine();
            
            System.out.print("Enter new Application Date (YYYY-MM-DD): ");
            String newApplicationDate = scan.nextLine();
            
            System.out.print("Enter new Application Status: ");
            String newStatus = scan.nextLine();
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newCompanyName);
            pst.setString(2, newJobTitle);
            pst.setString(3, newApplicationDate);
            pst.setString(4, newStatus);
            pst.setInt(5, id);
            pst.executeUpdate();
            
            System.out.println("\nJob Application was Edited Successfully!");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void manageApplications(Scanner scan){
        int opt;

        do {    
            try {
                System.out.println("\n\t=== Job Application Management ===\n");
                System.out.println("1. View All Applications\n2. Add a Job Application\n3. Delete a Job Application\n4. Edit a Job Application\n5. Go back..");
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 

                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        viewApplications();
                        System.out.println("\n------------------------------------------------------------------");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addApplication(scan);
                        System.out.println("\n------------------------------------------------------------------");
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteApplication(scan);
                        System.out.println("\n------------------------------------------------------------------");
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editApplication(scan);
                        System.out.println("\n------------------------------------------------------------------");
                        break;           
                        
                    case 5:
                        System.out.println("\nGoing back to Main Menu...");
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



