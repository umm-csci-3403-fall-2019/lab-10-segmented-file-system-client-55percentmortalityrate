package segmentedfilesystem;

public class HeaderPacket extends Packet {
  String filename;

  HeaderPacket(byte fileID, byte[] filenameBuffer){
    this.fileID = fileID;
    this.isDataPacket = false;
    String filename = new String(filenameBuffer);
    this.filename = filename;
  }

  @Override
  public void addToFile(File file) {
    file.add(this);
  }
}
