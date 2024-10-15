package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Applications {
    Config conf = new Config();
    
    public void manageApplications(Scanner scan){
        
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Application Management ===\n");
                System.out.println("1. View All Applications\n"
                        + "2. Add an Application\n"
                        + "3. Remove an Application\n"
                        + "4. Edit Application Details\n"
                        + "5. Go back..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 

                switch (opt) {
                    case 1:
                        System.out.println("\n------------------------------------------------------------------");
                        viewApplications("SELECT * FROM applications");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addApplication(scan);
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteApplication(scan);
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editApplication(scan);
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
    
    public void viewApplications(String query){ 
        System.out.println("\n\t\t\t\t\t\t\t   === APPLICATIONS LIST ===\n");
       
        String[] Headers = {"Application ID", "Seeker's ID", "Job ID", "Application Date", "Status"};
        String[] Columns = {"appl_id", "seeker_id", "job_id", "appl_date", "status"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addApplication(Scanner scan){
        System.out.println("\n\t\t=== ADD AN NEW APPLICATION ===\n");
        
        System.out.println("Enter Application Details:");
        
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
        String sql = "INSERT INTO applications (seeker_id, job_id, appl_date, status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, seeker_id, job_id, date, stats);
    }

    public void deleteApplication(Scanner scan){
        System.out.println("\n\t\t=== REMOVE AN APPLICATION ===\n");
        
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM applications WHERE appl_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editApplication(Scanner scan){
        System.out.println("\n\t\t=== EDIT AN APPLICATION ===\n");
        
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
        String sql = "UPDATE applications SET seeker_id = ?, job_id = ?, appl_date = ?, status = ? WHERE appl_id = ?";
        conf.updateRecord(sql, seeker_id, job_id, date, stats, id);
    }
}
