package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JobSeekers {
    Config conf = new Config();
    
    public void manageJobSeekers(Scanner scan){
        
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Job Seeker Management ===\n");
                System.out.println("1. View All Job Seekers\n"
                        + "2. Add a Job Seeker\n"
                        + "3. Remove a Job Seeker\n"
                        + "4. Edit a Job Seeker\n"
                        + "5. Go back..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 

                switch (opt) {
                    case 1:
                        System.out.println("\n------------------------------------------------------------------");
                        viewJobSeekers("SELECT * FROM job_seekers");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addJobSeeker(scan);
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteJobSeeker(scan);
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editJobSeeker(scan);
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
    
    public void viewJobSeekers(String query){ 
        System.out.println("\n\t\t\t\t\t\t\t   === JOB SEEKERS LIST ===\n");
       
        String[] Headers = {"ID", "First Name", "Last Name", "Email", "Phone Number"};
        String[] Columns = {"seeker_id", "fname", "lname", "email", "phone"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addJobSeeker(Scanner scan){
        System.out.println("\n\t\t=== ADD A NEW JOB SEEKER ===\n");
        
        System.out.println("Enter Job Seeker's Details:");
        
        System.out.print("\nFirst Name: ");
        String fname = scan.nextLine();
        
        System.out.print("Last Name: ");
        String lname = scan.nextLine();
        
        System.out.print("Email: ");
        String email = scan.nextLine();
        
        System.out.print("Phone Number: ");
        String phone = scan.nextLine();
        
        System.out.println("");
        String sql = "INSERT INTO job_seekers (fname, lname, email, phone) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, phone);
    }

    public void deleteJobSeeker(Scanner scan){
        System.out.println("\n\t\t=== REMOVE A JOB SEEKER ===\n");
        
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM job_seekers WHERE seeker_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editJobSeeker(Scanner scan){
        System.out.println("\n\t\t=== EDIT A JOB SEEKER ===\n");
        
        int id;
        do{
            System.out.print("\nEnter Seeker ID: ");
            id = scan.nextInt();
            if(!conf.doesIDExist("job_seekers", "seeker_id", id)){
                System.out.println("Seeker ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("job_seekers", "seeker_id", id));
        scan.nextLine();
        
        System.out.println("Selected Record:");
        viewJobSeekers("SELECT * FROM job_seekers WHERE seeker_id = " + id);
        
        System.out.println("Enter Job Seeker's Details:");
        
        System.out.print("\nNew First Name: ");
        String fname = scan.nextLine();
        
        System.out.print("New Last Name: ");
        String lname = scan.nextLine();
        
        System.out.print("New Email: ");
        String email = scan.nextLine();
        
        System.out.print("New Phone Number: ");
        String phone = scan.nextLine();
        
        System.out.println("");
        String sql = "UPDATE job_seekers SET fname = ?, lname = ?, email = ?, phone = ? WHERE seeker_id = ?";
        conf.updateRecord(sql, fname, lname, email, phone, id);
    }

}
