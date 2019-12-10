package segmentedfilesystem;

import java.util.Arrays;

public abstract class Packet {
  byte fileID;
  boolean isDataPacket;

  public static Packet makePacket(byte[] packetBuffer, int bufferLength){
    byte statusByte = packetBuffer[0];
    byte fileID = packetBuffer[1];
    boolean isDataPacket = statusIsDataPacket(statusByte);

    if (isDataPacket) {
      DataPacket dataPacket = new DataPacket(statusByte,fileID,
          (packetBuffer[2] & 0xFF) * 256 + (packetBuffer[3] & 0xFF),
          Arrays.copyOfRange(packetBuffer,4,packetBuffer.length),bufferLength);
      return dataPacket;
    } else {
      HeaderPacket headerPacket = new HeaderPacket(fileID,
          Arrays.copyOfRange(packetBuffer,2,packetBuffer.length),bufferLength);
      return headerPacket;
    }
  }

  static boolean statusIsDataPacket(byte statusByte){
    return statusByte % 2 == 1;
  }

  public abstract void addToFile(File file);
}
