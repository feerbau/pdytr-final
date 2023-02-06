import java.io.IOException;
import java.rmi.*;

public interface ClientIface extends Remote {
    void onReadComplete(String document, Long startTime) throws IOException;
}