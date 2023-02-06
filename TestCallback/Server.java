import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* This class implements the interface with remote methods */
// La Server Class a la que se va a llamar para invocar los metodos.
class Server extends UnicastRemoteObject implements ServerIface {
    
    protected Server() throws RemoteException {
        super();
    }

    // private final ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 30,
    //         0L, TimeUnit.MILLISECONDS,
    //         new LinkedBlockingQueue<>());

    private Map<String, ClientIface> callbacks = new HashMap<>();

    // public static void main(String[] args) {
    //     try {
    //         String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
    //         Server server = new Server();
    //         Naming.rebind(rname, server);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public void readMovie(String document, ClientIface callback, Long startTime) throws RemoteException {
        callbacks.put(document, callback);
        // executor.execute(() -> {
        new Thread(() -> {
            File movieFile = new File("../Examen Final/movie.mp4");
            int bytesRead = 0;
            int bufferSize = 1024 * 1024;
            try (FileInputStream inputStream = new FileInputStream(movieFile)) {
                byte[] buffer = new byte[bufferSize];
                long nRead;
                while ((nRead = inputStream.read(buffer)) != -1) {
                    bytesRead += nRead;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                callbacks.get(document).onReadComplete(document, startTime);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        // });
    }
}
