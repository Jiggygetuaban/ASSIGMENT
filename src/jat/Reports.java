package jat;

import java.util.Scanner;

public class Reports {

    public void rtransaction() {
        Scanner sc = new Scanner(System.in);
        String response = null;

        do {
            System.out.println("\n------------------------------------");
            System.out.println("   |    === Report Panel ===    |");
            System.out.println("------------------------------------");
            System.out.println("1. View All Job Seekers");
            System.out.println("2. View All Jobs");
            System.out.println("3. View All Applications");
            System.out.println("4. View Job Seeker Applications");
            System.out.println("5. Exit");

            System.out.print("Enter Selection: ");
            int act = sc.nextInt();
            Reports rp = this; // Correctly assign 'this' to 'rp'
            sc.nextLine(); // Consume the newline character
            switch (act) {
                case 1:
                    rp.viewAllJobSeekers();
                    break;
                case 2:
                    rp.viewAllJobs();
                    break;
                case 3:
                    rp.viewAllApplications();
                    break;
                case 4:
                    rp.viewJobSeekerApplications();
                    break;
                case 5:
                    System.out.println("Exiting Reports Panel...");
                    break;
                default:
                    System.out.println("Invalid Selection. Please try again.");
            }
            if (act != 5) { // Only ask for continuation if not exiting
                System.out.println("Do you want to continue?(yes/no):");
                response = sc.nextLine();
            }
        } while (response.equalsIgnoreCase("yes"));
    }

    public void viewAllJobSeekers() {
        String qry = "SELECT * FROM job_seekers";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Phone"};
        String[] clms = {"seeker_id", "fname", "lname", "email", "phone"};

        Config conf = new Config();
        conf.viewRecords(qry, hdrs, clms);
    }

    public void viewAllJobs() {
        String qry = "SELECT * FROM jobs";
        String[] hdrs = {"ID", "Job Title", "Company", "Location", "Type of Job"};
        String[] clms = {"job_id", "job_title", "company_name", "location", "job_type"};

        Config conf = new Config();
        conf.viewRecords(qry, hdrs, clms);
    }

    public void viewAllApplications() {
        String qry = "SELECT applications.appl_id, job_seekers.fname, job_seekers.lname, jobs.job_title, applications.appl_status, applications.appl_date " +
                "FROM applications " +
                "JOIN job_seekers ON applications.seeker_id = job_seekers.seeker_id " +
                "JOIN jobs ON applications.job_id = jobs.job_id";
        String[] hdrs = {"Application ID", "Job Seeker First Name", "Job Seeker Last Name", "Job Title", "Application Status", "Application Date"};
        String[] clms = {"appl_id", "fname", "lname", "job_title", "appl_status", "appl_date"};

        Config conf = new Config();
        conf.viewRecords(qry, hdrs, clms);
    }

    public void viewJobSeekerApplications() {
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        JobSeekers jb = new JobSeekers();

        viewJobSeekers();
        System.out.print("Enter Job Seeker ID: ");
        int jobSeekerId = sc.nextInt();

        if (conf.getSingleValue("SELECT seeker_id FROM job_seekers WHERE seeker_id = ?", jobSeekerId) == 0) {
            System.out.println("Job Seeker ID not found!");
            return;
        }

        String qry = "SELECT applications.appl_id, jobs.job_title, job_seekers.fname, job_seekers.lname, applications.appl_status, applications.appl_date " +
                "FROM applications " +
                "JOIN jobs ON applications.job_id = jobs.job_id " +
                "JOIN job_seekers ON applications.seeker_id = job_seekers.seeker_id " +
                "WHERE applications.seeker_id = ?";

        String[] hdrs = {"Application ID", "Job Title", "Job Seeker First Name", "Job Seeker Last Name", "Application Status", "Application Date"};
        String[] clms = {"appl_id", "job_title", "fname", "lname", "appl_status", "appl_date"};

        conf.viewRecords(qry, hdrs, clms, jobSeekerId);
    }

    public void viewJobSeekers() {
        String qry = "SELECT seeker_id, fname, lname FROM job_seekers";
        String[] hdrs = {"ID", "First Name", "Last Name"};
        String[] clms = {"seeker_id", "fname", "lname"};

        Config conf = new Config();
        conf.viewRecords(qry, hdrs, clms);
    }
}