package segmentedfilesystem;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class testPacket {



  @Test
  public void headerPacket(){
    byte[] buffer = new byte[3];
    buffer[0] = 0;
    buffer[1]= 17;
    buffer[2] = 42;
    Packet headerPacket = Packet.makePacket(buffer,buffer.length);
    assertFalse(headerPacket.isDataPacket);
    assertEquals(17,headerPacket.fileID);
  }

  @Test
  public void dataPacket(){
    byte[] buffer = new byte[5];
    buffer[0] = 1;
    buffer[1]= 17;
    buffer[2] = 42;
    buffer[3] = 1;
    buffer[4] = 10;
    DataPacket dataPacket = (DataPacket) Packet.makePacket(buffer,buffer.length);
    assertTrue(dataPacket.isDataPacket);
    assertFalse(dataPacket.isLastPacket);
    assertEquals(17,dataPacket.fileID);
    assertEquals(42*256+1,dataPacket.packetNumber);
  }

  @Test
  public void lastDataPacket(){
    byte[] buffer = new byte[5];
    buffer[0] = 3;
    buffer[1]= 17;
    buffer[2] = 42;
    buffer[3] = 1;
    buffer[4] = 10;
    DataPacket dataPacket = (DataPacket) Packet.makePacket(buffer,buffer.length);
    assertTrue(dataPacket.isDataPacket);
    assertTrue(dataPacket.isLastPacket);
    assertEquals(17,dataPacket.fileID);
    assertEquals(42*256+1,dataPacket.packetNumber);
  }

  @Test
  public void data(){
    ArrayList<Byte> buffer = new ArrayList<Byte>();
    buffer.add((byte)1);
    buffer.add((byte)17);
    buffer.add((byte)0);
    buffer.add((byte)1);
    Random random = new Random();
    byte[] bytes = new byte[1000];
    random.nextBytes(bytes);

    for (int i = 0; i <bytes.length ; i++) {
      buffer.add(bytes[i]);
    }

    byte[] byteArr = new byte[buffer.size()];
    for (int i = 0; i <buffer.size() ; i++) {
      byteArr[i]=(byte) buffer.get(i);
    }
    DataPacket dataPacket = (DataPacket) Packet.makePacket(byteArr,byteArr.length);
    assertTrue(dataPacket.isDataPacket);
    assertEquals(17,dataPacket.fileID);
    assertEquals(1,dataPacket.packetNumber);
    assertArrayEquals(bytes,dataPacket.data);
  }

  @Test
  public void filename(){
    ArrayList<Byte> buffer = new ArrayList<Byte>();
    buffer.add((byte)0);
    buffer.add((byte)17);
    String filename = "frogsord.txt";
    byte[] bytes = filename.getBytes();
    for (int i = 0; i <bytes.length ; i++) {
      buffer.add(bytes[i]);
    }

    byte[] byteArr = new byte[buffer.size()];
    for (int i = 0; i <buffer.size() ; i++) {
      byteArr[i]=(byte) buffer.get(i);
    }
    HeaderPacket headerPacket = (HeaderPacket) Packet.makePacket(byteArr,byteArr.length);
    assertFalse(headerPacket.isDataPacket);
    assertEquals(17,headerPacket.fileID);
    assertEquals("frogsord.txt",headerPacket.filename);
  }
}
