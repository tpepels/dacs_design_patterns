package SOLID.single_responsibility;

/*
 * In this example, the Report class has three responsibilities: generating a
 * report, saving the report to a file, and sending the report by email. This
 * violates the SRP because the class has more than one reason to change.
 */
class BadReport {
    public void generateReport() {
        // Code to generate a report
    }

    public void saveReportToFile() {
        // Code to save the report to a file
    }

    public void sendReportByEmail() {
        // Code to send the report by email
    }
}

/*
 * To solve this problem, we can create separate ReportSaver and EmailService
 * classes that are responsible for saving the report to a file and sending it
 * by email, respectively. The Report class is now only responsible for
 */

class Report {
    public void generateReport() {
        // Code to generate a report
    }
}

class ReportSaver {
    public void saveReportToFile(Report report) {
        // Code to save the report to a file
    }
}

class EmailService {
    public void sendReportByEmail(Report report) {
        // Code to send the report by email
    }
}
