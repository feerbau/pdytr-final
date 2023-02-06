import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIface extends Remote {
        void readMovie(String document, ClientIface callback, Long startTime) throws RemoteException;
}


