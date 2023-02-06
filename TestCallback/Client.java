import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Client implements ClientIface, Serializable {


    public static void main(String[] args) {
        try {
            String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
		    ServerIface server = (ServerIface) Naming.lookup(rname);

            int sampleSize = Integer.parseInt(args[0]);
            File file = new File("./tiempos-asinc.txt");
            long[] times = new long[sampleSize];
            long totalTime = 0;
            RandomAccessFile file2 = new RandomAccessFile(file, "rw");

            ClientIface client = new Client();
            for (int i = 0; i < sampleSize; i++) {
                long startTime = System.nanoTime();
                server.readMovie("doc" + i, client, startTime);
                long endTime = System.nanoTime();
                times[i] = ((endTime - startTime));
                totalTime += times[i];

                file2.writeBytes("Tiempo nro" + i + " = " + ((endTime - startTime))+"\n");
            }

            double average = totalTime / sampleSize;

            System.out.println("Tiempo total de comunicacion: " + totalTime + " nanoseg.");
            System.out.println("Tiempo promedio de comunicacion: " + average + " nanoseg.");
            file2.writeBytes("Tiempo total de comunicacion: " + totalTime + " nanoseg."+"\n");
            file2.writeBytes("Tiempo promedio de comunicacion: " + average + " nanoseg."+"\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onReadComplete(String document, Long startTime) throws IOException {
        long endTime = System.nanoTime();
        long time = (endTime - startTime);
        String num= document.replaceAll("[^0-9]", "");
        System.out.println("Tiempo " + num + ": " + time);
    }

}