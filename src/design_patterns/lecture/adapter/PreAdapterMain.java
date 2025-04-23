package design_patterns.lecture.adapter;

// --- Target Interface (What the client expects) ---
interface CustomerDataProvider {
    String getCustomerDataById(int customerId); // Client expects simple string data
}

// --- Concrete Implementation (Works with the client) ---
class DatabaseCustomerProvider implements CustomerDataProvider {
    @Override
    public String getCustomerDataById(int customerId) {
        // Simulate fetching from DB and formatting as a simple string
        System.out.println("DatabaseProvider: Fetching data for customer " + customerId);
        return "DB Customer Data {ID: " + customerId + ", Name: 'John Doe (DB)', Email: 'john.db@example.com'}";
    }
}

// --- Adaptee (The incompatible service/class) ---
// Represents the legacy or third-party service format
class XMLUserData {
    private String xmlContent;

    public XMLUserData(String content) {
        this.xmlContent = content;
    }

    public String getXmlContent() {
        return xmlContent;
    }
}

// The incompatible legacy service interface/class
class LegacyUserService {
    public XMLUserData getUserData(int userId) {
        // Simulate fetching from the legacy system and returning XML structure
        System.out.println("LegacyUserService: Fetching XML user data for ID " + userId);
        String xml = "<User><ID>" + userId
                + "</ID><Name>Jane Smith (Legacy)</Name><Contact>jane.legacy@example.net</Contact></User>";
        return new XMLUserData(xml);
    }
}

// --- Client Code ---
class CustomerInfoDisplay {
    private CustomerDataProvider dataProvider;

    // Client depends on the Target interface
    public CustomerInfoDisplay(CustomerDataProvider provider) {
        this.dataProvider = provider;
    }

    public void displayCustomer(int customerId) {
        System.out.println("\n--- Displaying Customer Info ---");
        // Client calls the method from the Target interface
        String customerDataString = dataProvider.getCustomerDataById(customerId);

        if (customerDataString != null && !customerDataString.isEmpty()) {
            System.out.println("Displaying Data:\n" + customerDataString);
        } else {
            System.out.println("No data found for customer ID: " + customerId);
        }
    }

    // Method to change provider (to show the problem later)
    public void setDataProvider(CustomerDataProvider provider) {
        System.out.println("Display: Setting new data provider: " + provider.getClass().getSimpleName());
        this.dataProvider = provider;
    }
}

// --- Main Application / Usage (Illustrative, not in UML) ---
public class PreAdapterMain {
    public static void main(String[] args) {
        // 1. Client works fine with the compatible provider
        CustomerDataProvider dbProvider = new DatabaseCustomerProvider();
        CustomerInfoDisplay display = new CustomerInfoDisplay(dbProvider);
        display.displayCustomer(101);

        // 2. Instantiate the incompatible legacy service
        LegacyUserService legacyService = new LegacyUserService();

        // *** PROBLEM AREA START ***
        // We CANNOT directly pass legacyService to the display client,
        // because LegacyUserService does NOT implement CustomerDataProvider.
        // This would be a COMPILE ERROR:
        // display.setDataProvider(legacyService); // Compile Error!

        // The client needs a String, but the legacy service returns XMLUserData.
        XMLUserData xmlData = legacyService.getUserData(202);
        System.out.println("\nLegacy service returned XML directly: " + xmlData.getXmlContent());

        // The client (CustomerInfoDisplay) cannot use this XMLUserData object directly.
        // We need a way to make the legacy service 'look like' a CustomerDataProvider.
        System.out.println("\nPROBLEM: Cannot directly use LegacyUserService where CustomerDataProvider is expected.");
        // *** PROBLEM AREA END ***
    }
}
