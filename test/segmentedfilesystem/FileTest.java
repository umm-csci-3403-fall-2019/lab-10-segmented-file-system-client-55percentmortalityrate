package segmentedfilesystem;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileTest {

  @Test
  public void fileComplete(){

    byte[] bytes = new byte[3];
    bytes[2] = 48; //make filename be "0"
    HeaderPacket headerPacket = (HeaderPacket) Packet.makePacket(bytes,3);
    assertFalse(headerPacket.isDataPacket);
    byte[] buffer = new byte[5];
    buffer[0] = 1;
    //We set the byte at 0 to be 1 to say this is a data packet.
    DataPacket dataPacket = (DataPacket) Packet.makePacket(buffer,5);
    assertTrue(dataPacket.isDataPacket);
    buffer[0] = 3; //set the byte to be 3 to make this be the final packet
    buffer[3] = 1; //set packet number to be 1
    DataPacket finalPacket = (DataPacket) Packet.makePacket(buffer,5);
    assertTrue(finalPacket.isDataPacket);
    assertTrue(finalPacket.isLastPacket);
    File file = new File();
    assertFalse(file.fileComplete());
    dataPacket.addToFile(file);
    assertFalse(file.fileComplete());
    headerPacket.addToFile(file);
    assertFalse(file.fileComplete());

    java.io.File file1 = new java.io.File("0");
    if(file1.exists()){
      file1.delete();
    }
    assertFalse(file1.exists());

    finalPacket.addToFile(file);
    assertEquals("0",file.filename);
    assertEquals(2,file.numPackets.intValue());
    assertTrue(file.fileComplete());
    file1 = new java.io.File("0");
    assertTrue(file1.exists());
  }

}
