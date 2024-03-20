import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class represents the main application entry point.
 */
public class PayrollApplication {

    // JDBC URL for connecting to the database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll";

    // Database user credentials
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /*  The main method of the application.  */
    
    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);
//		Playground        
//        try {
//        	connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//        	EmployeeWorkflow emp = new EmployeeWorkflow(connection);
//        	emp.displayEmployeeDetails(1001);
//        	
//        } catch (Exception e) {
//        	System.out.println(e.getMessage());
//        }
        

        try {
            // Establishing connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            if (connection != null) {
                // Displaying welcome message
                System.out.println("==========================================");
                System.out.println("Welcome to the Solartis Payroll System !!!");
                System.out.println("==========================================");

                // Prompting user to choose role
                System.out.println("Choose your role: \n1.Admin \n2.Employee \n3.Exit");
                System.out.print("Enter your choice: ");
                int choiceForRole = scanner.nextInt();

                // Processing user's role selection
                switch (choiceForRole) {
                    case 1:
                        // Workflow for Admin
                        AdminWorkflow adminWorkflow = new AdminWorkflow(connection);
                        adminWorkflow.processAdminWorkflow();
                        break;
                    case 2:
                        // Workflow for Employee
                        EmployeeWorkflow employeeWorkflow = new EmployeeWorkflow(connection);
                        employeeWorkflow.processEmployeeWorkflow();
                        break;
                    case 3:
                        // Exiting
                        System.out.println("Shutting down.......");
                        System.out.println("Bye !!!");
                        break;
                    default:
                        System.out.println("Invalid Choice. Please select either 1 or 2");
                        break;
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            // Handling SQL exceptions
            System.out.println("SQL Exception: " + e.getMessage());
        } finally {
            // Closing resources
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close connection: " + e.getMessage());
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
