package segmentedfilesystem;

import java.util.Arrays;

public abstract class Packet {
  byte statusByte;
  byte fileID;
  boolean isDataPacket;

  static Packet makePacket(byte[] packetBuffer){
    byte statusByte = packetBuffer[0];
    byte fileID = packetBuffer[1];
    boolean isDataPacket = statusIsDataPacket(statusByte);



    if (isDataPacket) {
      DataPacket dataPacket = new DataPacket(statusByte,fileID,
          packetBuffer[2] * 256 + packetBuffer[3],
          Arrays.copyOfRange(packetBuffer,4,packetBuffer.length));
      return dataPacket;
    } else {
      HeaderPacket headerPacket = new HeaderPacket(fileID,
          Arrays.copyOfRange(packetBuffer,2,packetBuffer.length));
      return headerPacket;
    }
  };

  static boolean statusIsDataPacket(byte statusByte){
    return statusByte % 2 == 1;
  }
}
