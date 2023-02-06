import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.Naming; /* lookup */
import java.rmi.registry.Registry; /* REGISTRY_PORT */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Client {
  public static void main(String[] args) throws MalformedURLException, IOException {
    try {
      String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
      IfaceRemoteClass remote = (IfaceRemoteClass) Naming.lookup(rname);

      String filePath = "../Examen Final/movie.mp4";

      int sampleSize = Integer.parseInt(args[0]);
      File file = new File("./tiempos-sinc.txt");
      long[] times = new long[sampleSize];
      long totalTime = 0;
      RandomAccessFile file2 = new RandomAccessFile(file, "rw");

      for (int i = 0; i < sampleSize; i++) {
        long startTime = System.nanoTime();

        int response = remote.readMovie(filePath);
        long endTime = System.nanoTime();
        times[i] = ((endTime - startTime));
        totalTime += times[i];

        file2.writeBytes("Tiempo nro" + i + " = " + ((endTime - startTime))+"\n");
      }

      double average = totalTime / sampleSize;
      

      System.out.println("Tiempo total: " + totalTime + " nanoseg.");
      System.out.println("Tiempo promedio: " + average + " nanoseg.");
      file2.writeBytes("Tiempo total: " + totalTime + " nanoseg.\n");
      file2.writeBytes("Tiempo promedio: " + average + " nanoseg.\n");
      file2.close();

      // response = remote.read(filePath, offset, batchRead);
    } catch (RemoteException | NotBoundException e) {
      e.printStackTrace();
    }
  }

}
