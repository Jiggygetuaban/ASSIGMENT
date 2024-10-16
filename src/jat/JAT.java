package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JAT {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);     
        
        JobSeekers seekerConf = new JobSeekers();
        Jobs jobConf = new Jobs();
        Applications applConf = new Applications();
                
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Job Application Tracker ===\n");
                System.out.println("1. Applications\n"
                        + "2. Job Seekers\n"
                        + "3. Jobs\n"
                        + "4. Reports\n"
                        + "5. Exit");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();
                System.out.println("");
                
                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        applConf.manageApplications(scan);
                        break;
                        
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        seekerConf.manageJobSeekers(scan);
                        break;

                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        jobConf.manageJobs(scan);
                        break;

                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        invidualReport(scan, seekerConf);
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
    
    public static void invidualReport(Scanner scan, JobSeekers seekerConf){
        Config conf = new Config();
        
        seekerConf.viewJobSeekers("SELECT * FROM job_seekers");
        
        System.out.println("\nView Individual Reports:");
        int s_id;
        do{
            System.out.print("Job Seeker ID: ");
            s_id = scan.nextInt();
            if(!conf.doesIDExist("job_seekers", "seeker_id", s_id)){
                System.out.println("Job Seeker ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("job_seekers", "seeker_id", s_id));
        System.out.println("-------------------------------------------------------------------------------------------------------------\n");
        
        String fname = conf.getDataFromID("job_seekers", s_id, "seeker_id", "fname");
        String lname = conf.getDataFromID("job_seekers", s_id, "seeker_id", "lname");
        String email = conf.getDataFromID("job_seekers", s_id, "seeker_id", "email");
        String phone = conf.getDataFromID("job_seekers", s_id, "seeker_id", "phone");
        
        System.out.println("\n\t\t + Job Seeker Applications Report +");
        
        System.out.println("\nJob Seeker ID: " + s_id);
        System.out.println("Name: " + fname + " " + lname);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phone);
        
        System.out.println("\nApplications Histoty:");
        
        String sql = "SELECT "
                        + "jobs.job_title, "
                        + "jobs.company_name, "
                        + "jobs.location, "
                        + "jobs.job_type, "
                        + "applications.appl_date, "
                        + "applications.status "
                    + "FROM "
                         + "applications "
                    + "JOIN "
                        + "job_seekers ON applications.seeker_id = job_seekers.seeker_id "
                    + "JOIN "
                        + "jobs ON applications.job_id = jobs.job_id WHERE job_seekers.seeker_id = " + s_id;
        
        String[] headers = {"Job Title", "Company Name", "Location", "Job Type", "Application Date", "Status"};
        String[] columns = {"job_title", "company_name", "location", "job_type", "appl_date", "status"};
        
        conf.viewRecords(sql, headers, columns);
        System.out.println("\n");
    }
}


