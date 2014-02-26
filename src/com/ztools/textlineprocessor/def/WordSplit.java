package com.ztools.textlineprocessor.def;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.ztools.textlineprocessor.ILineProcessor;

public class WordSplit implements ILineProcessor {

  private Map<String, Integer> wordSet = new HashMap<String, Integer>();
  private OutputStreamWriter fw = null;

  public void beforeRead() {
    System.out.println(this.getClass().getSimpleName() + ": Begin...");
    try {
      fw = new OutputStreamWriter(new FileOutputStream("english-words.txt"
                                                       , true), "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void process(final String line, Object... outObjs) {
    if (null != line) {
      String regex = "[ .;,_:：。；，?!\"？！“”]|(--)";
      String[] words = line.split(regex);
      for (int i = 0; i < words.length; i++) {
        if (words[i].matches("[a-zA-Z-]+")) {
          String w = words[i].toLowerCase();
          Integer count = wordSet.get(w);
          if (null == count) {
            count = 0;
          }
          wordSet.put(w, count + 1);
        }
      }
    }
  }

  public void afterRead() {
    StringBuilder sbd = new StringBuilder();
    for (String w : wordSet.keySet()) {
      sbd.append(w).append(" ").append(wordSet.get(w)).append("\n");
    }

    try {
      if (null != fw)
        fw.write(sbd.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (null != fw) {
      try {
        fw.flush();
        fw.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.out.println(this.getClass().getSimpleName() + ": End!!!");
  }
}
