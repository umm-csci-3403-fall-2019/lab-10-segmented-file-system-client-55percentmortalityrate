package segmentedfilesystem;

public class DataPacket extends Packet {
  int packetNumber;
  boolean isLastPacket;
  byte[] data;

  public DataPacket (byte statusByte, byte fileID,int packetNumber, byte[] packetBuffer){
    this.fileID = fileID;
    this.packetNumber = packetNumber;
    this.isLastPacket = statusByte % 4 == 3;
    this.isDataPacket = true;
    this.data = packetBuffer;
  }
}
