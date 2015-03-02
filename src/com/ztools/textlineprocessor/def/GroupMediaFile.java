package com.ztools.textlineprocessor.def;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import com.ztools.textlineprocessor.ILineProcessor;

public class GroupMediaFile implements ILineProcessor {

  private OutputStreamWriter fw = null;

  public void beforeRead() {
    System.out.println(this.getClass().getSimpleName() + ": Begin...");
  }

  static final String FIND_FLAG = "resourceName\":\"";
  static final String ALBUM_FLAG = "xmpDM:album\":\"";
  public void process(final String line, Object... outObjs) {
    if (null != line) {
      int b = line.indexOf(FIND_FLAG) + FIND_FLAG.length();
      int e = line.indexOf("\"", b);
      String resourceName = line.substring(b, e);
      String[] spt = resourceName.split(" - |[.]mp3");
      String dir = spt[spt.length - 1];
      b = line.indexOf(ALBUM_FLAG) + ALBUM_FLAG.length();
      e = line.indexOf("\"", b);
      String album = line.substring(b, e);

      File file = new File("/home/lizhi/mnt/home/3zso/Videos/CloudMusic/"
                          + dir + "/" + resourceName);
      if (file.exists()) {
        System.out.println("rename: " + file);
        File ndir = new File("/home/lizhi/mnt/home/3zso/Videos/albums/"
                             + album);
        if (!ndir.exists()) {
          ndir.mkdirs();
        }
        
        file.renameTo(new File("/home/lizhi/mnt/home/3zso/Videos/albums/"
                               + album + "/" + resourceName));
        System.out.println("dest: " + "/home/lizhi/mnt/home/3zso/Videos/albums/"
                           + album + "/" + resourceName);
      }
    }
  }

  public void afterRead() {
    System.out.println(this.getClass().getSimpleName() + ": End!!!");
  }
}
