package design_patterns.lecture.adapter;

// --- Target Interface (What the client expects - unchanged) ---
interface CustomerDataProvider {
    String getCustomerDataById(int customerId);
}

// --- Concrete Implementation (Works with the client - unchanged) ---
class DatabaseCustomerProvider implements CustomerDataProvider {
    @Override
    public String getCustomerDataById(int customerId) {
        System.out.println("DatabaseProvider: Fetching data for customer " + customerId);
        return "DB Customer Data {ID: " + customerId + ", Name: 'John Doe (DB)', Email: 'john.db@example.com'}";
    }
}

// --- Adaptee (The incompatible service/class - unchanged) ---
class XMLUserData {
    private String xmlContent;

    public XMLUserData(String content) {
        this.xmlContent = content;
    }

    public String getXmlContent() {
        return xmlContent;
    }
}

class LegacyUserService {
    public XMLUserData getUserData(int userId) {
        System.out.println("LegacyUserService: Fetching XML user data for ID " + userId);
        String xml = "<User><ID>" + userId
                + "</ID><Name>Jane Smith (Legacy)</Name><Contact>jane.legacy@example.net</Contact></User>";
        return new XMLUserData(xml);
    }
}

// --- The Adapter ---
/**
 * The Adapter makes the Adaptee's interface compatible with the Target's
 * interface.
 * It uses composition - holds an instance of the Adaptee.
 */
class UserServiceAdapter implements CustomerDataProvider { // Implements the Target interface
    private LegacyUserService legacyService; // Holds an instance of the Adaptee

    public UserServiceAdapter(LegacyUserService service) {
        System.out.println("Adapter: Wrapping LegacyUserService.");
        this.legacyService = service;
    }

    // The core adaptation logic happens here
    @Override
    public String getCustomerDataById(int customerId) {
        System.out.println("Adapter: Received request for customer ID " + customerId);
        // 1. Call the Adaptee's method
        XMLUserData xmlData = legacyService.getUserData(customerId);

        // 2. Translate the Adaptee's response (XMLUserData) into the Target's expected
        // format (String)
        String adaptedData = convertXmlToString(xmlData); // Perform the translation
        System.out.println("Adapter: Translated XML to String format.");

        return adaptedData;
    }

    // Private helper method for the translation logic
    private String convertXmlToString(XMLUserData xmlData) {
        if (xmlData == null || xmlData.getXmlContent() == null) {
            return "";
        }
        // Simple, naive "parsing" for demonstration. A real implementation
        // would use a proper XML parser (like JAXB, DOM, SAX).
        String xml = xmlData.getXmlContent();
        try {
            String id = xml.substring(xml.indexOf("<ID>") + 4, xml.indexOf("</ID>"));
            String name = xml.substring(xml.indexOf("<Name>") + 6, xml.indexOf("</Name>"));
            String contact = xml.substring(xml.indexOf("<Contact>") + 9, xml.indexOf("</Contact>"));
            // Format as the simple string the client expects
            return "Adapted Legacy Data {ID: " + id + ", Name: '" + name + "', Contact: '" + contact + "'}";
        } catch (Exception e) {
            System.err.println("Adapter: Error parsing XML - " + e.getMessage());
            return "Error processing legacy data: " + xml; // Return something indicating an error
        }
    }
}

// --- Client Code (Unchanged) ---
class CustomerInfoDisplay {
    private CustomerDataProvider dataProvider;

    public CustomerInfoDisplay(CustomerDataProvider provider) {
        this.dataProvider = provider;
    }

    public void displayCustomer(int customerId) {
        System.out.println("\n--- Displaying Customer Info ---");
        String customerDataString = dataProvider.getCustomerDataById(customerId);
        if (customerDataString != null && !customerDataString.isEmpty()) {
            System.out.println("Displaying Data:\n" + customerDataString);
        } else {
            System.out.println("No data found for customer ID: " + customerId);
        }
    }

    public void setDataProvider(CustomerDataProvider provider) {
        System.out.println("Display: Setting new data provider: " + provider.getClass().getSimpleName());
        this.dataProvider = provider;
    }
}

// --- Main Application / Usage (Illustrative, not in UML) ---
public class PostAdapterMain {
    public static void main(String[] args) {
        // Client using the original, compatible provider
        CustomerDataProvider dbProvider = new DatabaseCustomerProvider();
        CustomerInfoDisplay display = new CustomerInfoDisplay(dbProvider);
        System.out.println("## Using Database Provider ##");
        display.displayCustomer(101);

        // Now, let's use the legacy service via the Adapter
        System.out.println("\n## Using Legacy Service via Adapter ##");
        // 1. Create the Adaptee instance
        LegacyUserService legacyService = new LegacyUserService();
        // 2. Create the Adapter instance, wrapping the Adaptee
        CustomerDataProvider adapter = new UserServiceAdapter(legacyService); // adapter *IS* a CustomerDataProvider

        // 3. Give the Adapter (which matches the Target interface) to the Client
        display.setDataProvider(adapter);

        // 4. Client uses the Adapter seamlessly, unaware of the underlying legacy
        // service
        display.displayCustomer(202);
        display.displayCustomer(203); // Call again to show interaction

        // The client (CustomerInfoDisplay) code did not need to change at all!
    }
}