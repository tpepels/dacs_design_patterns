package architectures.rpc.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import architectures.rpc.Calculator;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    public CalculatorImpl() throws RemoteException {
        super();
    }

    public int add(int x, int y) throws RemoteException {
        System.out.println("Let me add those for you!");
        return x + y;
    }

    public int subtract(int x, int y) throws RemoteException {
        System.out.println("Let me subtract those for you!");
        return x - y;
    }
}
