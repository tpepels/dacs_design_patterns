package rpc.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import rpc.Calculator;

/**
 * This class creates an instance of the CalculatorImpl class, registers it with
 * the RMI registry, and binds it to a name (CalculatorService).
 */

public class Server {
    public static void main(String[] args) {
        try {
            Calculator c = new CalculatorImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("CalculatorService", c);
            System.out.println("Server running waiting to calculate!...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
