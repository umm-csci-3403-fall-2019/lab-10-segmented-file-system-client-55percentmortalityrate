package segmentedfilesystem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class File {
  HeaderPacket headerPacket;
  DataPacket lastPacket;
  String filename;
  Integer numPackets;

  HashMap<Integer,DataPacket> datapackets = new HashMap<>();

  public void write(){
    System.out.println("Writing file " + filename);
    ArrayList<Byte> outputBuffer = new ArrayList();
    ArrayList<Integer> ints = new ArrayList<>();
    for(int i = 0; i < numPackets; i++){
      ints.add(i);
    }
    //Collections.shuffle(ints);
    for(int i = 0; i < numPackets; i++){
      outputBuffer.addAll(datapackets.get(ints.get(i)).getBoxedData());
    }
    //Collections.shuffle(outputBuffer);
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

//  public void add(Packet packet) {
//    if(packet instanceof HeaderPacket){
//      add((HeaderPacket) packet);
//    }else {
//      add((DataPacket) packet);
//    }
//  }

  public void add(HeaderPacket headerPacket){
    this.headerPacket = headerPacket;
    this.filename = headerPacket.filename;
    System.out.println("Recieved headerpacket for " + filename + " id: " + headerPacket.fileID);
    if (fileComplete()){
      write();
    }
  }

  public void add(DataPacket addedPacket){
    datapackets.put(addedPacket.packetNumber,addedPacket);

    System.out.print("packet file id " + addedPacket.fileID);
    System.out.print(", number "+ addedPacket.packetNumber);
    if (numPackets != null){
      System.out.print(" of " + numPackets);
    }
    System.out.print(" with " + datapackets.size() + " recieved (" +datapackets.keySet()+")");
    System.out.print("\n");

    if (addedPacket.isLastPacket){
      numPackets = addedPacket.packetNumber + 1;
      lastPacket = addedPacket;
      System.out.print("packet number " + addedPacket.packetNumber + " was lastPacket.");
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
