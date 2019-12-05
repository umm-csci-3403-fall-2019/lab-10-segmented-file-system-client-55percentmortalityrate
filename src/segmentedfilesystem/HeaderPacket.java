package segmentedfilesystem;

public class HeaderPacket extends Packet {
  String filename;

  HeaderPacket(byte fileID, byte[] filenameBuffer,int bufferLength){
    this.fileID = fileID;
    this.isDataPacket = false;
    String filename = new String(filenameBuffer,0,bufferLength-2);
    this.filename = filename;
  }

  @Override
  public void addToFile(File file) {
    file.add(this);
  }
}
