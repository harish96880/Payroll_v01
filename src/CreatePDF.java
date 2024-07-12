import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class CreatePDF {
    private static final String pdfDirectory = "C:/Users/sriharish_r/git/Payroll_v01/"; //Change the Pdf Directory to your location
    private static final String pdfName = "salary_details.pdf";
    private static final String imagePath = "C:/Users/sriharish_r/git/Payroll_v01/logo.png"; // Change the logo Directory to your location
    
    // Method to generate PDF
    void generatePdf(String data[][], String EmployeeName, String Designation, String Month, String Year, int cheque, String currentDate) {
    	Document document = new Document();
        try {
            // Ensure that the directory exists
            File directory = new File(pdfDirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory if it doesn't exist
            }

            // Create the PDF file
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File(pdfDirectory + pdfName)));
            document.open();

            // Add image at the top center
            Image image = Image.getInstance(imagePath);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
            
            // Font properties
            Font bigBoldFont = FontFactory.getFont(FontFactory.defaultEncoding, 18);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            
            // Add "Coimbatore" text
            Paragraph location = new Paragraph("Coimbatore", bigBoldFont);
            location.setAlignment(Element.ALIGN_CENTER);
            document.add(location);

            // Add "Salary Slip" text
            Paragraph title = new Paragraph("Salary Slip", bigBoldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph("\n"));
            
            // Add "EmployeeName" text
            Font textField = FontFactory.getFont(FontFactory.defaultEncoding, 18);
            Paragraph employeeName = new Paragraph("Employee Name: " + EmployeeName, textField);
            employeeName.setAlignment(Element.ALIGN_LEFT);
            document.add(employeeName);
            
            // Add "Designation" text
            Paragraph designation = new Paragraph("Designation: " + Designation, textField);
            designation.setAlignment(Element.ALIGN_LEFT);
            document.add(designation);
            
            // Add "Month & Year" text
            Paragraph month_Year = new Paragraph("Month & Year: " + Month + " " + Year, textField);
            month_Year.setAlignment(Element.ALIGN_LEFT);
            document.add(month_Year);
           
            // For extra line space
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            
            // Create PdfPTable
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setPaddingTop(200);

            PdfPCell earningsCell = new PdfPCell();
            Paragraph earningsParagraph = new Paragraph("Earnings", boldFont); // Add bold font to the paragraph
            earningsParagraph.setAlignment(Element.ALIGN_CENTER); // Align text to the center
            earningsCell.addElement(earningsParagraph); // Add paragraph to cell
            earningsCell.setColspan(2);
            table.addCell(earningsCell);

            PdfPCell deductionsCell = new PdfPCell();
            Paragraph deductionsParagraph = new Paragraph("Deductions", boldFont); // Add bold font to the paragraph
            deductionsParagraph.setAlignment(Element.ALIGN_CENTER); // Align text to the center
            deductionsCell.addElement(deductionsParagraph); // Add paragraph to cell
            deductionsCell.setColspan(2);
            table.addCell(deductionsCell);

            // Add table content
            for (String[] row : data) {
                for (String cellData : row) {
                    table.addCell(cellData);
                }
            }

            // Add the table to the document
            document.add(table);
            
            // For additional line spaces
            for (int i = 0; i < 5; i++) {
                document.add(new Paragraph("\n"));
            }
            
            // Add "Cheque" text
            Paragraph chequeParagraph = new Paragraph("Cheque No: " + cheque, bigBoldFont);
            chequeParagraph.setAlignment(Element.ALIGN_BOTTOM);
            document.add(chequeParagraph);
            
            // Add "Date" text
            Paragraph date = new Paragraph("Date: " + currentDate, bigBoldFont);
            date.setAlignment(Element.ALIGN_BOTTOM);
            document.add(date);
            
            // Add "Bank" text
            Paragraph bank = new Paragraph("Name of the bank: Indian Bank", bigBoldFont);
            bank.setAlignment(Element.ALIGN_BOTTOM);
            document.add(bank);
            
            // For additional line spaces
            for (int i = 0; i < 5; i++) {
                document.add(new Paragraph("\n"));
            }
            
            // Create a PdfPTable with two columns
            PdfPTable signatureTable = new PdfPTable(3);
            signatureTable.setWidthPercentage(100);
            signatureTable.getDefaultCell().setBorderWidth(0);
            signatureTable.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            Font tableFont = FontFactory.getFont(FontFactory.defaultEncoding, 14);
            
            // Add the first signature line
            signatureTable.addCell(new Paragraph("Signature of the Employee:", tableFont));

            // Add a tab space between the two signature lines
            signatureTable.addCell("\t");

            // Add the second signature line
            signatureTable.addCell(new Paragraph("Director: ", tableFont));

            // Add the table to the document
            document.add(signatureTable);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
