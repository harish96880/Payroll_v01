import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeWorkflow {
    private Connection connection;

    // Constructor to initialize the connection
    public EmployeeWorkflow(Connection connection) {
        this.connection = connection;
    }

    // Method to check if employee ID exists
    private boolean isEmployeeIdExists(int employeeId, String email) throws SQLException {
        String query = "SELECT * FROM Employee_Details WHERE Employee_Id = ? AND Email_Address = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Method to display employee details
    public void displayEmployeeDetails(int empId) throws SQLException {
        String query = "SELECT e.Employee_Id, e.First_Name, e.Last_Name, sd.Month, sd.Year, sd.Net_Salary "
                +	 "FROM Employee_Details e JOIN Salary_Details sd ON e.Employee_id = sd.Employee_Id "
                + "WHERE e.Employee_Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, empId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.println("Employee ID    First Name    Last Name    Month    Year    Net Salary");
                System.out.println("---------------------------------------------------------------------------------------------");
                
             // ArrayList to store table data retrieved from the database.
                List<List<Object>> tableData = new ArrayList<>();

                while (resultSet.next()) {
                    List<Object> rowData = new ArrayList<>();
                    rowData.add(resultSet.getInt("Employee_Id"));
                    rowData.add(resultSet.getString("First_Name"));
                    rowData.add(resultSet.getString("Last_Name"));
                    rowData.add(resultSet.getInt("Month"));
                    rowData.add(resultSet.getInt("Year"));
                    rowData.add(resultSet.getBigDecimal("Net_Salary"));
                    tableData.add(rowData);
                }

                for (List<Object> rowData : tableData) {
                    System.out.printf("%-15s%-14s%-13s%-9s%-8s%-1s%n",
                            rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3),
                            rowData.get(4), rowData.get(5));
                }

                System.out.println("---------------------------------------------------------------------------------------------");
            }
        }
    }

    // Method to process employee workflow
    public void processEmployeeWorkflow() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You selected Employee role.");
        System.out.print("Enter your Employee ID: ");
        int empId = scanner.nextInt();
        System.out.print("Enter your Email: ");
        String empMail = scanner.next();
        try {
            if (!isEmployeeIdExists(empId, empMail)) {
                System.out.println("Employee does not exist");
                System.out.println("Access denied");
                System.out.println("Exiting...");
                return;
            } else {
                displayEmployeeDetails(empId);
                System.out.print("Enter 1 to exit: ");
                int empOpt = 0;
                while (empOpt != 1) {
                    empOpt = scanner.nextInt();
                    if (empOpt == 1) {
                        System.out.println("Exiting..");
                        return;
                    } else {
                        System.out.println("Invalid Input try again!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
