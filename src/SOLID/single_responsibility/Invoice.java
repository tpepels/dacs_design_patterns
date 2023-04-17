package SOLID.single_responsibility;

/*
 * In this example, the Invoice class has three responsibilities: 
 * generating an invoice, printing an invoice, and emailing an invoice to the customer. 
 * This violates the SRP because the class has more than one reason to change.

 */
class BadInvoice {
    public void generateInvoice() {
        // Code to generate an invoice
    }

    public void printInvoice() {
        // Code to print an invoice
    }

    public void emailInvoice() {
        // Code to email an invoice to the customer
    }
}

/*
 * To solve this problem, we can create separate Printer and EmailService
 * classes that are responsible for printing and emailing invoices,
 * respectively. The Invoice class is now only responsible for generating
 * invoices, and the other classes are responsible for printing and emailing
 * them. This ensures that each class has only one reason to change.
 */

class Invoice {
    public void generateInvoice() {
        // Code to generate an invoice
    }
}

class Printer {
    public void printInvoice(Invoice invoice) {
        // Code to print an invoice
    }
}

class EmailService {
    public void emailInvoice(Invoice invoice) {
        // Code to email an invoice to the customer
    }
}
