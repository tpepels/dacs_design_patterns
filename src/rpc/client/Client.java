package rpc.client;

import java.rmi.Naming;

import rpc.Calculator;

/**
 * This class looks up the CalculatorService on the server and calls the add and
 * subtract methods remotely.
 */

public class Client {
    public static void main(String[] args) {
        try {
            Calculator c = (Calculator) Naming.lookup("rmi://localhost:1099/CalculatorService");
            int result = c.add(3, 4);
            System.out.println("3 + 4 = " + result);
            result = c.subtract(7, 2);
            System.out.println("7 - 2 = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
