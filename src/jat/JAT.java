package jat;

import java.util.InputMismatchException;
import java.util.Scanner;

public class JAT {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);     
        JobSeekers seekerConf = new JobSeekers();
        Jobs jobConf = new Jobs();
                
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


