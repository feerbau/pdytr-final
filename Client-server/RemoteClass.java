
/*
* RemoteClass.java
* Just implements the RemoteMethod interface as an extension to
* UnicastRemoteObject
*
*/
/* Needed for implementing remote method/s */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/* This class implements the interface with remote methods */
// La Server Class a la que se va a llamar para invocar los metodos.
public class RemoteClass extends UnicastRemoteObject implements IfaceRemoteClass {
    protected RemoteClass() throws RemoteException {
        super();
    }

    public int readMovie(String moviePath) throws RemoteException {
        File movieFile = new File(moviePath);
        int bytesRead = 0;
        int bufferSize = 1024 * 1024; // 1MB buffer
        try (FileInputStream inputStream = new FileInputStream(movieFile)) {
            byte[] buffer = new byte[bufferSize];
            int nRead;
            while ((nRead = inputStream.read(buffer)) != -1) {
                bytesRead += nRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Error reading movie file", e);
        }
        return bytesRead;
    }

}