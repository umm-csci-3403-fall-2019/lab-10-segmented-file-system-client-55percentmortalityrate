package segmentedfilesystem;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;


public class Main {
    
    public static void main(String[] args) throws IOException {

        DatagramSocket socket = new DatagramSocket();
        HashMap<Integer,File> files = new HashMap<>();
        int filesRemaining = 3;
        helloServer(socket,6014);

        while (filesRemaining > 0) {
            byte[] buf = new byte[1028];
            DatagramPacket recieved = new DatagramPacket(buf, buf.length);
            socket.receive(recieved);
            Packet packet = Packet.makePacket(recieved.getData(),recieved.getLength());
            if(files.containsKey((int) packet.fileID)){
                packet.addToFile(files.get((int) packet.fileID));
                if (files.get((int) packet.fileID).fileComplete()){
                    filesRemaining --;
                    files.remove((int) packet.fileID);

                }
            } else {
                files.put((int) packet.fileID,new File());
                packet.addToFile(files.get((int) packet.fileID));
            }
        }
    }

        

    static private void helloServer(DatagramSocket socket, int port) throws IOException {
        byte[] buf = new byte[1028];
        InetAddress address = InetAddress.getByName("csci-4409.morris.umn.edu");
        DatagramPacket packet = new DatagramPacket(buf, buf.length,
            address, port);
        socket.send(packet);
    }

}
