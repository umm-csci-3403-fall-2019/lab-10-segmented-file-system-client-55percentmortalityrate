package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class File {
  HeaderPacket headerPacket;
  DataPacket lastPacket;
  String filename;
  Integer numPackets;

  HashMap<Integer,DataPacket> datapackets = new HashMap<>();

  public void write(){
    System.out.println("Writing file " + filename);
    ArrayList<Byte> outputBuffer = new ArrayList<>();
    ArrayList<Integer> ints = new ArrayList<>();
    for(int i = 0; i < numPackets; i++){
      ints.add(i);
    }
    for(int i = 0; i < numPackets; i++){
      outputBuffer.addAll(datapackets.get(ints.get(i)).getBoxedData());
    }
    byte[] byteArr = new byte[outputBuffer.size()];
    for (int i = 0; i <outputBuffer.size() ; i++) {
      byteArr[i]=(byte) outputBuffer.get(i);
    }
    try {
      FileOutputStream file = new FileOutputStream(filename);
      file.write(byteArr);
    } catch (IOException e){
      System.err.println("Error attempting to write file " + filename);
      System.err.println(e);
    }

  }


  public void add(HeaderPacket headerPacket){
    this.headerPacket = headerPacket;
    this.filename = headerPacket.filename;
    if (fileComplete()){
      write();
    }
  }

  public void add(DataPacket addedPacket){
    datapackets.put(addedPacket.packetNumber,addedPacket);
    if (addedPacket.isLastPacket){
      numPackets = addedPacket.packetNumber + 1;
      lastPacket = addedPacket;
    }
    if (fileComplete()){
      write();
    }
  }

  public boolean fileComplete(){
    if(numPackets==null)return false;

    return numPackets.equals(datapackets.size()) && headerPacket != null;
  }

}
