package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Jobs {
    Config conf = new Config();
    
    public void manageJobs(Scanner scan){
        
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Job Job Management ===\n");
                System.out.println("1. View All Jobs\n"
                        + "2. Add a New Job\n"
                        + "3. Remove a Job\n"
                        + "4. Edit Job Details\n"
                        + "5. Go back..");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine(); 

                switch (opt) {
                    case 1:
                        System.out.println("\n------------------------------------------------------------------");
                        viewJobs("SELECT * FROM jobs");
                        break;
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        addJob(scan);
                        break;
                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        deleteJob(scan);
                        break;
                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        editJob(scan);
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
    
    public void viewJobs(String query){ 
        System.out.println("\n\t\t\t\t\t\t\t   === JOBS LIST ===\n");
       
        String[] Headers = {"ID", "Job Title", "Company Name", "Location", "Job Type"};
        String[] Columns = {"job_id", "job_title", "company_name", "location", "job_type"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addJob(Scanner scan){
        System.out.println("\n\t\t=== ADD A NEW JOB ===\n");
        
        System.out.println("Enter Job Details:");
        
        System.out.print("\nJob Title: ");
        String job_title = scan.nextLine();
        
        System.out.print("Company Name: ");
        String company_name = scan.nextLine();
        
        System.out.print("Location: ");
        String location = scan.nextLine();
        
        System.out.print("Job Type: ");
        String job_type = scan.nextLine();
        
        System.out.println("");
        String sql = "INSERT INTO jobs (job_title, company_name, location, job_type) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, job_title, company_name, location, job_type);
    }

    public void deleteJob(Scanner scan){
        System.out.println("\n\t\t=== REMOVE A JOB ===\n");
        
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM jobs WHERE job_id = ?";
        conf.deleteRecord(sql, id);
    }

    public void editJob(Scanner scan){
        System.out.println("\n\t\t=== EDIT JOB DETAILS ===\n");
        
        int id;
        do{
            System.out.print("\nEnter Job ID: ");
            id = scan.nextInt();
            if(!conf.doesIDExist("jobs", "job_id", id)){
                System.out.println("Job ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("jobs", "job_id", id));
        scan.nextLine();
        
        System.out.println("Selected Record:");
        viewJobs("SELECT * FROM jobs WHERE job_id = " + id);
        
        System.out.println("Enter Job Details:");
        
        System.out.print("\nJob Title: ");
        String job_title = scan.nextLine();
        
        System.out.print("Company Name: ");
        String company_name = scan.nextLine();
        
        System.out.print("Location: ");
        String location = scan.nextLine();
        
        System.out.print("Job Type: ");
        String job_type = scan.nextLine();
        
        System.out.println("");
        String sql = "UPDATE jobs SET job_title = ?, company_name = ?, location = ?, job_type = ? WHERE job_id = ?";
        conf.updateRecord(sql, job_title, company_name, location, job_type, id);
    }

}
