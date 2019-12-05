package segmentedfilesystem;

import java.util.ArrayList;
import java.util.Collection;

public class DataPacket extends Packet {
  int packetNumber;
  boolean isLastPacket;
  byte[] data;
  int dataBufferLength;

  public DataPacket (byte statusByte, byte fileID,int packetNumber, byte[] packetBuffer,int bufferLength){
    this.fileID = fileID;
    this.packetNumber = packetNumber;
    this.isLastPacket = statusByte % 4 == 3;
    this.isDataPacket = true;
    this.data = packetBuffer;
    this.dataBufferLength = bufferLength-4;
  }

  public ArrayList<Byte> getBoxedData(){
    ArrayList<Byte> outputBytes = new ArrayList<>();
    for (int i =0; i < dataBufferLength;i++){
      outputBytes.add( data[i]);
    }
    return outputBytes;
  }


  @Override
  public void addToFile(File file) {
    file.add(this);
  }
}
