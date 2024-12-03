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
        System.out.println("  === JOB SEEKERS LIST ===");
        System.out.println("----------------------------------------");
       
        String[] Headers = {"ID", "First Name", "Last Name", "Email", "Phone Number"};
        String[] Columns = {"seeker_id", "fname", "lname", "email", "phone"};
        
        conf.viewRecords(query, Headers, Columns);
    }

    public void addJobSeeker(Scanner scan) {
    System.out.println("\n--------------------------------------");
    System.out.println("  == ADD A NEW JOB SEEKER ==  ");
    System.out.println("----------------------------------------");
    
    System.out.println("Enter Job Seeker's Details:");
    
    System.out.print("\nFirst Name: ");
    String fname = scan.nextLine();
    
    System.out.print("Last Name: ");
    String lname = scan.nextLine();
    
    String email;
    do {
        System.out.print("Email: ");
        email = scan.nextLine();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Invalid email format. Please try again.");
        }
    } while (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
    
    String phone;
    do {
        System.out.print("Phone Number: ");
        phone = scan.nextLine();
        if (!phone.matches("\\d{11}")) {
            System.out.println("Invalid phone number. Please enter an 11-digit number.");
        }
    } while (!phone.matches("\\d{11}"));
    
    System.out.println("");
    String sql = "INSERT INTO job_seekers (fname, lname, email, phone) VALUES (?, ?, ?, ?)";
    conf.addRecord(sql, fname, lname, email, phone);
}

public void editJobSeeker(Scanner scan) {
    System.out.println("\n--------------------------------------");
    System.out.println("    == EDIT A JOB SEEKER ===  ");
    System.out.println("----------------------------------------");
    
    int id;
    do {
        System.out.print("\nEnter Seeker ID: ");
        id = scan.nextInt();
        if (!conf.doesIDExist("job_seekers", "seeker_id", id)) {
            System.out.println("Seeker ID Doesn't Exist.");
        }
    } while (!conf.doesIDExist("job_seekers", "seeker_id", id));
    scan.nextLine();
    
    System.out.println("Selected Record:");
    viewJobSeekers("SELECT * FROM job_seekers WHERE seeker_id = " + id);
    
    System.out.println("Enter Job Seeker's Details:");
    
    System.out.print("\nNew First Name: ");
    String fname = scan.nextLine();
    
    System.out.print("New Last Name: ");
    String lname = scan.nextLine();
    
    String email;
    do {
        System.out.print("New Email: ");
        email = scan.nextLine();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("Invalid email format. Please try again.");
        }
    } while (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
    
    String phone;
    do {
        System.out.print("New Phone Number: ");
        phone = scan.nextLine();
        if (!phone.matches("\\d{11}")) {
            System.out.println("Invalid phone number. Please enter an 11-digit number.");
        }
    } while (!phone.matches("\\d{11}"));
    
    System.out.println("");
    String sql = "UPDATE job_seekers SET fname = ?, lname = ?, email = ?, phone = ? WHERE seeker_id = ?";
    conf.updateRecord(sql, fname, lname, email, phone, id);
}


    public void deleteJobSeeker(Scanner scan) {
    System.out.println("\n--------------------------------------");
    System.out.println("    == DELETE A JOB SEEKER ==  ");
    System.out.println("----------------------------------------");

    int id;
    do {
        System.out.print("\nEnter Seeker ID to delete: ");
        id = scan.nextInt();
        if (!conf.doesIDExist("job_seekers", "seeker_id", id)) {
            System.out.println("Seeker ID Doesn't Exist. Please try again.");
        }
    } while (!conf.doesIDExist("job_seekers", "seeker_id", id));
    scan.nextLine(); // Consume newline

    System.out.println("Selected Record:");
    viewJobSeekers("SELECT * FROM job_seekers WHERE seeker_id = " + id);

    System.out.print("\nAre you sure you want to delete this record? (yes/no): ");
    String confirmation = scan.nextLine();

    if (confirmation.equalsIgnoreCase("yes")) {
        String sql = "DELETE FROM job_seekers WHERE seeker_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Job Seeker with ID " + id + " has been deleted successfully.");
    } else {
        System.out.println("Deletion canceled.");
    }
}

}