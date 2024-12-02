package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Applications {
    Config conf = new Config();
    
    public void manageApplications(Scanner scan){
        
        int opt;
        do {    
            try {
                System.out.println("\n----------------------------------------");
                System.out.println("| === Application Management ===    |");
                System.out.println("----------------------------------------");
                System.out.println("1. View All Applications\n"
                        + "2. Add an Application\n"
                        + "3. Remove an Application\n"
                        + "4. Edit Application Details\n"
                        + "5. Exit..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); // Consume the newline character
                Applications ap = this; // Use 'this' to refer to the current object

                switch (opt) {
                    case 1:
                        ap.viewApplications("SELECT * FROM applications");
                        break;
                    case 2:
                        ap.addApplication(scan);
                        break;
                    case 3:
                        ap.deleteApplication(scan);
                        break;
                    case 4:
                        ap.editApplication(scan);
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
    
    public void viewApplications(String query){ 
        System.out.println("\n--------------------------------------");
        System.out.println("|    == APPLICATIONS LIST ==     |");
        System.out.println("--------------------------------------");
        String[] Headers = {"Application ID", "Seeker's ID", "Job ID", "Application Date", "Status"};
        String[] Columns = {"appl_id", "seeker_id", "job_id", "appl_date", "appl_status"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addApplication(Scanner scan){
        System.out.println("\n--------------------------------------");
        System.out.println("     == ADD AN NEW APPLICATION ==     ");
        System.out.println("--------------------------------------");
        
        System.out.println("Enter Application Details:");
        
        viewJobSeekers();

        int seeker_id;
        do{
            System.out.print("\nJob Seeker ID: ");
            seeker_id = scan.nextInt();
            if(!conf.doesIDExist("job_seekers", "seeker_id", seeker_id)){
                System.out.println("Seeker ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("job_seekers", "seeker_id", seeker_id));
        
        

        int job_id;
        do{
            System.out.print("Job ID: ");
            job_id = scan.nextInt();
            if(!conf.doesIDExist("jobs", "job_id", job_id)){
                System.out.println("Job ID Doesn't Exist.\n");
            }
        }while(!conf.doesIDExist("jobs", "job_id", job_id));
        scan.nextLine();
        
        System.out.print("Application Date: ");
        String date = scan.nextLine();
        
        System.out.print("Status: ");
        String stats = scan.nextLine();
        
        System.out.println("");
        String sql = "INSERT INTO applications (seeker_id, job_id, appl_date, appl_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, seeker_id, job_id, date, stats);
    }

    public void deleteApplication(Scanner scan){
        System.out.println("\n--------------------------------------");
        System.out.println("   == REMOVE AN APPLICATION  ==  ");
        System.out.println("--------------------------------------");
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM applications WHERE appl_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editApplication(Scanner scan){
         System.out.println("\n--------------------------------------");
        System.out.println("  == EDIT AN APPLICATION ==");
         System.out.println("--------------------------------------");
        int id;
        do{
            System.out.print("\nEnter Application ID: ");
            id = scan.nextInt();
            if(!conf.doesIDExist("applications", "appl_id", id)){
                System.out.println("Application ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("applications", "appl_id", id));
        scan.nextLine();
        
        System.out.println("Selected Record:");
        viewApplications("SELECT * FROM applications WHERE appl_id = " + id);
        
        System.out.println("Enter New Application Details:");
        
        int seeker_id;
        do{
            System.out.print("\nNew Job Seeker ID: ");
            seeker_id = scan.nextInt();
            if(!conf.doesIDExist("job_seekers", "seeker_id", seeker_id)){
                System.out.println("Seeker ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("job_seekers", "seeker_id", seeker_id));
        
        int job_id;
        do{
            System.out.print("New Job ID: ");
            job_id = scan.nextInt();
            if(!conf.doesIDExist("jobs", "job_id", job_id)){
                System.out.println("Job ID Doesn't Exist.\n");
            }
        }while(!conf.doesIDExist("jobs", "job_id", job_id));
        scan.nextLine();
        
        System.out.print("New Application Date: ");
        String date = scan.nextLine();
        
        System.out.print("New Status: ");
        String stats = scan.nextLine();
        
        System.out.println("");
        String sql = "UPDATE applications SET seeker_id = ?, job_id = ?, appl_date = ?, appl_status = ? WHERE appl_id = ?";
        conf.updateRecord(sql, seeker_id, job_id, date, stats, id);
    }
    
    // Add this method to view all Job Seekers
    public void viewJobSeekers() {
        String qry = "SELECT * FROM job_seekers";
        String[] Headers = {"ID", "First Name", "Last Name", "Email", "Phone"};
        String[] Columns = {"seeker_id", "fname", "lname", "email", "phone"};

        conf.viewRecords(qry, Headers, Columns);
    }
}