import java.rmi.Remote;
import java.rmi.RemoteException;

// Server Class
public interface IfaceRemoteClass extends Remote {
    // void registerCallback(Callback callback) throws RemoteException;

    public int readMovie(String moviePath) throws RemoteException;
}
