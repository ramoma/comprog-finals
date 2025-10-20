import java.util.Scanner;

public class App{
    static String[] patientNames = new String[100];
    static String[] appointmentDates = new String[100];
    static String[] appointmentTimes = new String[100];
    static String[] services = new String[100];
    static String[] dentists = new String[100];
    static double[] costs = new double[100];
    static int count = 0;

    static String check_date;
    static String check_time;
    static String check_dentist;
    static boolean is_conflicting;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Dental Clinic Management ===");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Edit Appointment");
            System.out.println("4. Delete Appointment");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addAppointment(sc);
                case 2 -> viewAppointments();
                case 3 -> editAppointment(sc);
                case 4 -> deleteAppointment(sc);
                case 5 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    static void addAppointment(Scanner sc) {
        if (count >= 100) {
            System.out.println("Cannot add more appointments. Limit reached.");
            return;
        }

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Enter appointment time (e.g. 10:00 AM): ");
        String time = sc.nextLine();

        System.out.print("Enter dental service (e.g. Cleaning, Extraction): ");
        String service = sc.nextLine();

        System.out.print("Enter attending dentist: ");
        String dentist = sc.nextLine();

        System.out.print("Enter service cost: ₱");
        double cost = sc.nextDouble();
        sc.nextLine(); // clear buffer

        check_conflicts(date, time, dentist, name, service, cost);      
    }

    static void viewAppointments() {
        System.out.println("\nAppointment List:");
        if (count == 0) {
            System.out.println("No appointments found.");
        } else {
            double total = 0;
            for (int i = 0; i < count; i++) {
                System.out.println((i + 1) + ". " + patientNames[i] + " - " +
                        appointmentDates[i] + " at " + appointmentTimes[i] + " with attending dentist: " + dentists[i] +".");
                System.out.println("   Service: " + services[i]);
                System.out.println("   Cost: ₱" + costs[i]);
                total += costs[i];
            }
            System.out.println("\n💰 Total Billing: ₱" + total);
        }
    }

    static void editAppointment(Scanner sc) {
        if (count == 0) {
            System.out.println("No appointments available to edit.");
            return;
        }

        viewAppointments();

        System.out.print("\nEnter the appointment number to edit: ");
        int num = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (num < 1 || num > count) {
            System.out.println("Invalid appointment number.");
            return;
        }

        int index = num - 1;

        System.out.println("Editing appointment for: " + patientNames[index]);

        System.out.print("Enter new patient name (leave blank to keep current): ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) patientNames[index] = newName;

        System.out.print("Enter new date (leave blank to keep current): ");
        String newDate = sc.nextLine();
        if (!newDate.isEmpty()) appointmentDates[index] = newDate;

        System.out.print("Enter new time (leave blank to keep current): ");
        String newTime = sc.nextLine();
        if (!newTime.isEmpty()) appointmentTimes[index] = newTime;

        System.out.print("Enter new service (leave blank to keep current): ");
        String newService = sc.nextLine();
        if (!newService.isEmpty()) services[index] = newService;

        System.out.println("Enter new Dentist (leave blank to keep current)");
        String newDentist = sc.nextLine();
        if(!newDentist.isEmpty()) dentists[index] = newDentist;

        System.out.print("Enter new cost (leave blank to keep current): ");
        String newCostInput = sc.nextLine();
        if (!newCostInput.isEmpty()) {costs[index] = Double.parseDouble(newCostInput);}

        System.out.println("Appointment updated successfully!");
    }

    static void deleteAppointment(Scanner sc) {
        if (count == 0) {
            System.out.println("No appointments available to delete.");
            return;
        }

        viewAppointments();

        System.out.print("\nEnter the appointment number to delete: ");
        int num = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (num < 1 || num > count) {
            System.out.println("Invalid appointment number.");
            return;
        }

        int index = num - 1;

        System.out.println("Deleting appointment for: " + patientNames[index]);

        // Shift remaining items up
        for (int i = index; i < count - 1; i++) {
            patientNames[i] = patientNames[i + 1];
            appointmentDates[i] = appointmentDates[i + 1];
            appointmentTimes[i] = appointmentTimes[i + 1];
            services[i] = services[i + 1];
            dentists[i] = dentists[i+1];
            costs[i] = costs[i + 1];
        }

        count--;
        System.out.println("Appointment deleted successfully!");
    }

    static void check_conflicts(String date, String time, String dentist, String name, String service, double cost){

        for(int i = 0; i < count; ++i){
            check_date = appointmentDates[i];
            check_time = appointmentTimes[i];
            check_dentist =dentists[i];
            
            if(date.equals(check_date) && time.equals(check_time) && dentist.equals(check_dentist)){
                is_conflicting = true;
            }
        }
        if(is_conflicting){
            System.out.println("Schedule Conflicts Detected!");
        }
        else{
            patientNames[count] = name;
            appointmentDates[count] = date;
            appointmentTimes[count] = time;
            services[count] = service;
            dentists[count] = dentist;
            costs[count] = cost;
            count++;
            System.out.println("Appointment added successfully!");     
        }

    }

}
