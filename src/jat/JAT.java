package jat;

import java.util.Scanner;

public class JAT {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        do {
            System.out.println("\n---------------------------------------");
            System.out.println("|    === JOB SEEKER MANAGEMENT ===    |");
            System.out.println("---------------------------------------");
            System.out.println("1. Job Seekers");
            System.out.println("2. Jobs");
            System.out.println("3. Applications");
            System.out.println("4. Reports");
            System.out.println("5. EXIT");

            System.out.println("Enter Action: ");
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    JobSeekers js = new JobSeekers();
                    js.manageJobSeekers(sc);
                    break;
                case 2:
                    Jobs jb = new Jobs();
                    jb.manageJobs(sc);
                    break;
                case 3:
                    Applications ap = new Applications();
                    ap.manageApplications(sc);
                    break;
                case 4:
                    Reports rp = new Reports();
                    rp.rtransaction();
                    break;
                case 5:
                    System.out.println("Exit Selected...type 'yes' to continue: ");
                    String resp = sc.nextLine();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;
                default:
                    System.out.println("Invalid Selection. Please try again.");
            }
        } while (exit);
    }

}