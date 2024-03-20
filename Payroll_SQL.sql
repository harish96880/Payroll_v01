create database payroll;
use payroll;
CREATE TABLE Employee_Details (
    Employee_id INT PRIMARY KEY,
    First_Name VARCHAR(50),
    Last_Name VARCHAR(50),
    Date_Of_Birth DATE,
    Gender VARCHAR(10),
    Phone_Number VARCHAR(15),
    Email_Address VARCHAR(100),
    Address VARCHAR(255),
    Joining_Date DATE,
    Department_Designation VARCHAR(100)
);

INSERT INTO Employee_Details (Employee_id, First_Name, Last_Name, Date_Of_Birth, Gender, Phone_Number, Email_Address, Address, Joining_Date, Department_Designation)
VALUES
(1001, 'Sriharish', 'Ravikumar', '2002-10-24', 'Male', '9878987898', 'sriharish@gmail.com', '24 Street Covai', '2023-10-21', 'Junior Developer'),
(1002, 'Senthil', 'Kumar', '1995-05-15', 'Male', '9876543210', 'senthil@gmail.com', '15 Gandhi Nagar', '2022-07-05', 'Senior Developer'),
(1003, 'Priya', 'Devi', '1998-12-03', 'Female', '8765432109', 'priya@gmail.com', '3 Krishna Street', '2024-01-10', 'Junior Developer'),
(1004, 'Rajesh', 'Gupta', '1990-09-18', 'Male', '7890123456', 'rajesh@gmail.com', '18 Patel Road', '2021-11-30', 'Team Lead'),
(1005, 'Anjali', 'Sharma', '1987-03-29', 'Female', '8901234567', 'anjali@gmail.com', '29 Nehru Avenue', '2020-08-15', 'Project Manager');


CREATE TABLE Salary_Structure (
    Department_Designation VARCHAR(100) PRIMARY KEY,
    Basic_Pay DECIMAL(10, 2),
    HRA DECIMAL(10, 2),
    Conveyance_Allowances DECIMAL(10, 2),
    Special_Allowances DECIMAL(10, 2),
    Other_Allowances DECIMAL(10, 2),
    Tax_Deductions DECIMAL(10, 2),
    PF DECIMAL(10, 2),
    ESI DECIMAL(10, 2),
    Professional_Tax DECIMAL(10, 2),
    TDS_TCS DECIMAL(10, 2),
    Night_Shift_Allowance DECIMAL(10, 2)
);

INSERT INTO Salary_Structure (Department_Designation, Basic_Pay, HRA, Conveyance_Allowances, Special_Allowances, Other_Allowances, Tax_Deductions, PF, ESI, Professional_Tax, TDS_TCS, Night_Shift_Allowance)
VALUES
('Junior Developer', 25000.00, 5000.00, 2000.00, 1500.00, 1000.00, 500.00, 2000.00, 1000.00, 200.00, 0.00, 500.00),
('Senior Developer', 40000.00, 8000.00, 3000.00, 2500.00, 2000.00, 1000.00, 3000.00, 1500.00, 300.00, 0.00, 1000.00),
('Team Lead', 60000.00, 12000.00, 4000.00, 3500.00, 3000.00, 1500.00, 4000.00, 2000.00, 400.00, 0.00, 1500.00),
('Project Manager', 80000.00, 15000.00, 5000.00, 4000.00, 3500.00, 2000.00, 5000.00, 2500.00, 500.00, 0.00, 2000.00),
('Junior Business Analyst', 30000.00, 6000.00, 2500.00, 2000.00, 1500.00, 800.00, 2500.00, 1200.00, 250.00, 0.00, 800.00),
('Senior Business Analyst', 45000.00, 9000.00, 3500.00, 3000.00, 2500.00, 1200.00, 3500.00, 1800.00, 350.00, 0.00, 1200.00),
('Junior Quality Analyst', 28000.00, 5500.00, 2200.00, 1800.00, 1200.00, 600.00, 2200.00, 1100.00, 220.00, 0.00, 600.00),
('Senior Quality Analyst', 42000.00, 8500.00, 3200.00, 2700.00, 2200.00, 1100.00, 3200.00, 1600.00, 320.00, 0.00, 1100.00),
('Release', 35000.00, 7000.00, 2800.00, 2300.00, 1800.00, 900.00, 2800.00, 1400.00, 280.00, 0.00, 900.00),
('Junior DEVOPS Engineer', 32000.00, 6500.00, 2600.00, 2100.00, 1600.00, 700.00, 2600.00, 1300.00, 260.00, 0.00, 700.00),
('Senior DEVOPS Engineer', 48000.00, 9500.00, 3800.00, 3200.00, 2500.00, 1300.00, 3800.00, 1900.00, 380.00, 0.00, 1300.00),
('Software Architect', 70000.00, 13500.00, 4500.00, 3700.00, 3200.00, 1800.00, 4500.00, 2200.00, 450.00, 0.00, 1800.00);

CREATE TABLE Salary_Details (
    Employee_Id INT,
    Month INT,
    Year INT,
    Basic_Pay DECIMAL(10, 2),
    HRA DECIMAL(10, 2),
    Conveyance_Allowances DECIMAL(10, 2),
    Special_Allowances DECIMAL(10, 2),
    Other_Allowances DECIMAL(10, 2),
    Deductions DECIMAL(10, 2),
    Net_Salary DECIMAL(10, 2),
    PRIMARY KEY (Employee_Id, Month, Year)
);

INSERT INTO Salary_Details (Employee_Id, Month, Year, Basic_Pay, HRA, Conveyance_Allowances, Special_Allowances, Other_Allowances, Deductions, Net_Salary)
VALUES
(1001, 3, 2024, 25000.00, 5000.00, 2000.00, 1500.00, 1000.00, 1500.00, 29400.00),
(1002, 3, 2024, 40000.00, 8000.00, 3000.00, 2500.00, 2000.00, 2500.00, 39200.00),
(1003, 3, 2024, 30000.00, 6000.00, 2500.00, 2000.00, 1500.00, 1800.00, 34300.00),
(1004, 3, 2024, 60000.00, 12000.00, 4000.00, 3500.00, 3000.00, 3500.00, 60500.00),
(1005, 3, 2024, 80000.00, 15000.00, 5000.00, 4000.00, 3500.00, 4500.00, 99500.00),
(1001, 4, 2024, 25000.00, 5000.00, 2000.00, 1500.00, 1000.00, 1500.00, 29400.00),
(1002, 4, 2024, 40000.00, 8000.00, 3000.00, 2500.00, 2000.00, 2500.00, 39200.00),
(1003, 4, 2024, 30000.00, 6000.00, 2500.00, 2000.00, 1500.00, 1800.00, 34300.00),
(1004, 4, 2024, 60000.00, 12000.00, 4000.00, 3500.00, 3000.00, 3500.00, 60500.00),
(1005, 4, 2024, 80000.00, 15000.00, 5000.00, 4000.00, 3500.00, 4500.00, 99500.00);

CREATE TABLE Payslip (
    employee_id INT,
    month INT,
    no_of_leave INT,
    sick_leave_balance INT,
    casual_leave_balance INT,
    loss_of_pay INT,
    FOREIGN KEY (Employee_id) REFERENCES Employee_details(employee_id)
);

-- Sample INSERT queries for Payslip table
INSERT INTO Payslip (employee_id, month, no_of_leave, sick_leave_balance, casual_leave_balance, loss_of_pay)
VALUES
    (1001, 1, 2, 4, 3, 1),  -- Sample data for employee ID 1001 for month 1
    (1001, 2, 1, 3, 2, 0),  -- Sample data for employee ID 1001 for month 2
    (1002, 1, 1, 5, 4, 0),  -- Sample data for employee ID 1002 for month 1
    (1002, 2, 3, 2, 1, 1),  -- Sample data for employee ID 1002 for month 2
    (1003, 1, 0, 6, 5, 0),  -- Sample data for employee ID 1003 for month 1
    (1003, 2, 2, 4, 3, 0),  -- Sample data for employee ID 1003 for month 2
    (1004, 1, 3, 3, 2, 1),  -- Sample data for employee ID 1004 for month 1
    (1004, 2, 0, 2, 1, 0),  -- Sample data for employee ID 1004 for month 2
    (1005, 1, 1, 4, 3, 0),  -- Sample data for employee ID 1005 for month 1
    (1005, 2, 0, 2, 1, 0);  -- Sample data for employee ID 1005 for month 2

CREATE TABLE night_shift_allowance (
    Employee_id INT,
    month INT,
    no_of_days INT,
    FOREIGN KEY (Employee_id) REFERENCES Employee_details(employee_id)
);

insert into night_shift_allowance values(1001, 1, 2), (1002, 3, 1), (1003, 3, 3);
