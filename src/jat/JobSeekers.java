package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JobSeekers {
    Config conf = new Config();
    
    public void manageJobSeekers(Scanner scan){
        
        int opt;
        do {    
            try {
                System.out.println("\n---------------------------------------");
                System.out.println("|    === Job Seeker Management ===    |");
                System.out.println("---------------------------------------");
                System.out.println("1. View All Job Seekers\n"
                        + "2. Add a Job Seeker\n"
                        + "3. Remove a Job Seeker\n"
                        + "4. Edit a Job Seeker\n"
                        + "5. Exit..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 
                JobSeekers jb = new JobSeekers();

                switch (opt) {
                    case 1:
                        jb.viewJobSeekers("SELECT * FROM job_seekers");
                        break;
                    case 2:
                        jb.addJobSeeker(scan);
                        break;
                    case 3:
                        jb.deleteJobSeeker(scan);
                        break;
                    case 4:
                        jb.editJobSeeker(scan);
                        break;           
                    case 5:
                        System.out.println("\nGoing back to Main Menu...");
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
        System.out.println("\n--------------------------------------");
        System.out.println("  === JOB SEEKERS LIST ===\n");
        System.out.println("----------------------------------------");
       
        String[] Headers = {"ID", "First Name", "Last Name", "Email", "Phone Number"};
        String[] Columns = {"seeker_id", "fname", "lname", "email", "phone"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addJobSeeker(Scanner scan){
        System.out.println("\n--------------------------------------");
        System.out.println("  == ADD A NEW JOB SEEKER ==  ");
        System.out.println("----------------------------------------");
        
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
        System.out.println("\n--------------------------------------");
        System.out.println("  == REMOVE A JOB SEEKER   ==");
        System.out.println("----------------------------------------");
        
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM job_seekers WHERE seeker_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editJobSeeker(Scanner scan){
        System.out.println("\n--------------------------------------");
        System.out.println("    == EDIT A JOB SEEKER ===  ");
        System.out.println("----------------------------------------");
        
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

    void viewJobSeekers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}