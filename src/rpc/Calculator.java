package rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    public int add(int x, int y) throws RemoteException;

    public int subtract(int x, int y) throws RemoteException;
}
