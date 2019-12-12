package segmentedfilesystem;

import java.util.Arrays;

public abstract class Packet {
  byte fileID;
  boolean isDataPacket;

  public static Packet makePacket(byte[] packetBuffer){
    byte statusByte = packetBuffer[0];
    byte fileID = packetBuffer[1];
    boolean isDataPacket = statusIsDataPacket(statusByte);
    Packet packet;

    if (isDataPacket) {
      byte[] buffer = Arrays.copyOfRange(packetBuffer,4,packetBuffer.length);
      int packetNum = (packetBuffer[2] & 0xFF) * 256 + (packetBuffer[3] & 0xFF);
      packet = new DataPacket(statusByte,fileID, packetNum,buffer);
    } else {
      byte[] buffer = Arrays.copyOfRange(packetBuffer,2,packetBuffer.length);
      packet = new HeaderPacket(fileID, buffer);
    }
    return packet;
  }

  static boolean statusIsDataPacket(byte statusByte){
    return statusByte % 2 == 1;
  }

  public abstract void addToFile(File file);
}
