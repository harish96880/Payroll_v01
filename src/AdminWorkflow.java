import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class AdminWorkflow {
    private final Connection connection;
 // Getting database connection with the help of constructor
    public AdminWorkflow(Connection connection) {
        this.connection = connection;
    }
    
 // Method to check if the Employee ID exists in the database
    private boolean isEmployeeIdExists(int employeeId) throws SQLException {
        String query = "SELECT * FROM Employee_Details WHERE Employee_Id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, employeeId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    
    // Method to check if the Employee ID and month exists in the database
    private boolean checkForPaySlip(int employeeId, int month) throws SQLException {
        String query = "select * from employee_details e join salary_details sd on e.employee_id = sd.employee_id where e.employee_id = ? and sd.month = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);	
        preparedStatement.setInt(1, employeeId);
        preparedStatement.setInt(2, month);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    
// Simple Auth for admin
    public void processAdminWorkflow() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("You selected Admin role");
            int adminSecretKey = 12345;
            System.out.print("\nEnter the secret key for admin: ");
            int adminSecretKeyInput = scanner.nextInt();

            if (adminSecretKeyInput == adminSecretKey) {
                System.out.println("Access approved");
                displayAdminMenu(scanner);
            } else {
                System.out.println("Incorrect admin secret key. Access denied");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

// Displaying admin menu
    private void displayAdminMenu(Scanner scanner) throws SQLException {
        boolean isValidChoice = false;
        
        while (!isValidChoice) {
            try {
                System.out.println("\nChoose an operation to proceed:");
                System.out.println("1. Database Operations");
                System.out.println("2. Generate Payslip");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int adminPortalChoose = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                switch (adminPortalChoose) {
                // Handling Database Operations
                    case 1:
                        handleDatabaseOperations(scanner);
                        break;
                        
                // For generating pay slip
                    case 2:
                        EmailSender emailSender = new EmailSender("sriharishr105@gmail.com", "czwympvajwawowbh", "smtp.gmail.com", 587);
                        System.out.print("Enter the id of the employee you want to generate payslip: ");
                        int employeeId = scanner.nextInt();
                        System.out.println();
                        System.out.print("Enter the month(In number): ");
                        int month = scanner.nextInt();
                        if (!checkForPaySlip(employeeId, month)) {
                            System.out.println("Employee ID or month not exist in the database.");
                            displayAdminMenu(scanner);
                            return;
                        }
                        MyPayslipGenerator pGenerator = new MyPayslipGenerator(connection, emailSender);
                        pGenerator.generatePayslip(employeeId, month);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        isValidChoice = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.nextLine(); // Consume invalid input to prevent infinite loop
            } catch (NoSuchElementException e) {
                System.out.println("No input detected. Please try again.");
                isValidChoice = true;
            }
        }
    }

    // Function for admin different database operation
    private void handleDatabaseOperations(Scanner scanner) throws SQLException {
        boolean continueMenu = true;
        
        while (continueMenu) {
            System.out.println("\nChoose database to proceed:");
            System.out.println("1. Employee Details");
            System.out.println("2. Salary Details");
            System.out.println("3. Salary Structure");
            System.out.println("5. Exit");
            System.out.print("Enter your Choice: ");
            int dbChooseAdmin = scanner.nextInt();
            
            switch (dbChooseAdmin) {
                case 1:
                    System.out.println("Enter an operation to proceed: \n1.View \n2.Update \n3.Back");
                    System.out.print("Enter your choice: ");
                    int adminOperationEmployeeDetails = scanner.nextInt();
                    
                    if (adminOperationEmployeeDetails == 1) {
                        viewEmployeeDetails();
                    } else if (adminOperationEmployeeDetails == 2) {
                        updateEmployeeDetails();
                    } 
                    break;
                case 2:
                	System.out.println("Enter an operation to proceed: \n1.View \n2.Back");
                    System.out.print("Enter your choice: ");
                    int adminOperationEmployeeDetails2 = scanner.nextInt();
                    
                    if (adminOperationEmployeeDetails2 == 1) {
                        viewSalaryDetails();
                    }
                    break;
                case 3:
                	System.out.println("Enter an operation to proceed: \n1.View \n2.Back");
                    System.out.print("Enter your choice: ");
                    int adminOperationEmployeeDetails3 = scanner.nextInt();
                    
                    if (adminOperationEmployeeDetails3 == 1) {
                        viewSalaryStructure();
                    } 
                    break;
                case 5:
                    System.out.println("Exiting...");
                    continueMenu = false; // Exit the menu loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Function for displaying employee details
    public void viewEmployeeDetails() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee_Details")) {
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        	System.out.println("Employee ID    First Name    Last Name    Date of Birth    Gender    Phone Number    Email Address    			Address    		Joining Date    Department Designation\n"
        			         + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        	// For storing the data from the table
        	List<List<Object>> tableData = new ArrayList<>();

            while (resultSet.next()) {
                List<Object> rowData = new ArrayList<>();
                rowData.add(resultSet.getString("Employee_Id"));
                rowData.add(resultSet.getString("First_Name"));
                rowData.add(resultSet.getString("Last_Name"));
                rowData.add(resultSet.getDate("Date_Of_Birth").toString());
                rowData.add(resultSet.getString("Gender"));
                rowData.add(resultSet.getString("Phone_Number"));
                rowData.add(resultSet.getString("Email_Address"));
                rowData.add(resultSet.getString("Address"));
                rowData.add(resultSet.getString("Joining_Date"));
                rowData.add(resultSet.getString("Department_Designation"));

                tableData.add(rowData);
            }


            // Display table data
            for (List<Object> rowData : tableData) {
                System.out.printf("%-15s%-14s%-13s%-17s%-10s%-16s%-35s%-24s%-16s%-20s%n",
                        rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3),
                        rowData.get(4), rowData.get(5), rowData.get(6), rowData.get(7),
                        rowData.get(8), rowData.get(9));
            }
        	
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        }
    }
    
    // Function for displaying the Salary_Details table for admin
    
    public void viewSalaryDetails() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Salary_Details")) {
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        	System.out.println("Employee ID    Month	Year    Basic Pay    HRA 	Conveyance All.    Special All.    	Other All.    	Deductions    Net Salary\n"
        			         + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        	List<List<Object>> tableData = new ArrayList<>();

            while (resultSet.next()) {
            	
            	// For storing the data
                List<Object> rowData = new ArrayList<>();
                rowData.add(resultSet.getString("Employee_Id"));
                rowData.add(resultSet.getInt("Month"));
                rowData.add(resultSet.getInt("Year"));
                rowData.add(resultSet.getBigDecimal("Basic_Pay"));
                rowData.add(resultSet.getBigDecimal("HRA"));
                rowData.add(resultSet.getBigDecimal("Conveyance_Allowances"));
                rowData.add(resultSet.getBigDecimal("Special_Allowances"));
                rowData.add(resultSet.getBigDecimal("Other_Allowances"));
                rowData.add(resultSet.getBigDecimal("Deductions"));
                rowData.add(resultSet.getBigDecimal("Net_Salary"));

                tableData.add(rowData);
            }

            // Display table data
            for (List<Object> rowData : tableData) {
                System.out.printf("%-15s%-9s%-8s%-13s%-11s%-19s%-21s%-16s%-14s%-20s%n",
                        rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3),
                        rowData.get(4), rowData.get(5), rowData.get(6), rowData.get(7),
                        rowData.get(8), rowData.get(9));
            }
        	
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        }
    }
    
 // Function for displaying the Salary_Structure table for admin
    
    public void viewSalaryStructure() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Salary_Structure")) {
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        	System.out.println("Department Designation    Basic Pay	 HRA	 	Conveyance All.    Special All.    	Other All.    	Tax Deductions    PF	      ESI		Professional Tax\n"
        			         + "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        	 List<List<Object>> tableData = new ArrayList<>();

             while (resultSet.next()) {
                 List<Object> rowData = new ArrayList<>();
                 rowData.add(resultSet.getString("Department_Designation"));
                 rowData.add(resultSet.getBigDecimal("Basic_Pay"));
                 rowData.add(resultSet.getBigDecimal("HRA"));
                 rowData.add(resultSet.getBigDecimal("Conveyance_Allowances"));
                 rowData.add(resultSet.getBigDecimal("Special_Allowances"));
                 rowData.add(resultSet.getBigDecimal("Other_Allowances"));
                 rowData.add(resultSet.getBigDecimal("Tax_Deductions"));
                 rowData.add(resultSet.getBigDecimal("PF"));
                 rowData.add(resultSet.getBigDecimal("ESI"));
                 rowData.add(resultSet.getBigDecimal("Professional_Tax"));
                 rowData.add(resultSet.getBigDecimal("TDS_TCS"));

                 tableData.add(rowData);
             }
             for (List<Object> rowData : tableData) {
                 System.out.printf("%-26s%-15s%-15s%-19s%-21s%-16s%-18s%-12s%-18s%-20s%n",
                         rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3),
                         rowData.get(4), rowData.get(5), rowData.get(6), rowData.get(7),
                         rowData.get(8), rowData.get(9), rowData.get(10));
             }
        	
        	System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void updateEmployeeDetails() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the Employee ID whose details you want to update: ");
            int employeeId = scanner.nextInt();
            scanner.nextLine(); // Check if the employee ID exists
            
            if (!isEmployeeIdExists(employeeId)) {
                System.out.println("Employee ID does not exist in the database.");
                return;
            }
            
            System.out.println("Enter updated First Name (leave blank to skip): ");
            String firstName = scanner.nextLine();
            System.out.println("Enter updated Last Name (leave blank to skip): ");
            String lastName = scanner.nextLine();
            System.out.println("Enter updated Date of Birth (YYYY-MM-DD, leave blank to skip): ");
            String dateOfBirth = scanner.nextLine();
            System.out.println("Enter updated Gender (Male/Female, leave blank to skip): ");
            String gender = scanner.nextLine();
            System.out.println("Enter updated Phone Number (leave blank to skip): ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Enter updated Email Address (leave blank to skip): ");
            String emailAddress = scanner.nextLine();
            System.out.println("Enter updated Address (leave blank to skip): ");
            String address = scanner.nextLine();
            System.out.println("Enter updated Joining Date (YYYY-MM-DD, leave blank to skip): ");
            String joiningDate = scanner.nextLine();
            System.out.println("Enter updated Department Designation (leave blank to skip): ");
            String departmentDesignation = scanner.nextLine();
            
            // Update the employee details in the database
            String updateQuery = "UPDATE Employee_Details SET ";
            boolean isFirstField = true;
            
            if (!firstName.isEmpty()) {
                updateQuery += "First_Name=?, ";
                isFirstField = false;
            }
            if (!lastName.isEmpty()) {
                updateQuery += "Last_Name=?, ";
                isFirstField = false;
            }
            if (!dateOfBirth.isEmpty()) {
                updateQuery += "Date_Of_Birth=?, ";
                isFirstField = false;
            }
            if (!gender.isEmpty()) {
                updateQuery += "Gender=?, ";
                isFirstField = false;
            }
            if (!phoneNumber.isEmpty()) {
                updateQuery += "Phone_Number=?, ";
                isFirstField = false;
            }
            if (!emailAddress.isEmpty()) {
                updateQuery += "Email_Address=?, ";
                isFirstField = false;
            }
            if (!address.isEmpty()) {
                updateQuery += "Address=?, ";
                isFirstField = false;
            }
            if (!joiningDate.isEmpty()) {
                updateQuery += "Joining_Date=?, ";
                isFirstField = false;
            }
            if (!departmentDesignation.isEmpty()) {
                updateQuery += "Department_Designation=?, ";
                isFirstField = false;
            }
            // Similar checks for other fields
            
            if (!isFirstField) {
                // Remove the trailing comma and space
                updateQuery = updateQuery.substring(0, updateQuery.length() - 2);
                updateQuery += " WHERE Employee_Id=?";
                
                // Prepare the statement and set parameters
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                int parameterIndex = 1;
                
                if (!firstName.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, firstName);
                }
                if (!lastName.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, lastName);
                }
                if (!dateOfBirth.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, dateOfBirth);
                }
                if (!gender.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, gender);
                }
                if (!phoneNumber.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, phoneNumber);
                }
                if (!emailAddress.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, emailAddress);
                }
                if (!address.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, address);
                }
                if (!joiningDate.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, joiningDate);
                }
                if (!departmentDesignation.isEmpty()) {
                    preparedStatement.setString(parameterIndex++, departmentDesignation);
                }
                // Similar parameter setting for other fields
                
                preparedStatement.setInt(parameterIndex, employeeId);
                
                // Execute update
                int rowsUpdated = preparedStatement.executeUpdate();
                
                if (rowsUpdated > 0) {
                    System.out.println("Employee details updated successfully!");
                } else {
                    System.out.println("Failed to update employee details.");
                }
            } else {
                System.out.println("No fields to update.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

}
