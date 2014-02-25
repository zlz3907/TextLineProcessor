package com.ztools.textlineprocessor.def;

import java.io.FileWriter;
import com.ztools.textlineprocessor.ILineProcessor;

public class EnglishTest implements ILineProcessor {

  private StringBuilder sbd = new StringBuilder();
  private FileWriter fw = null;
  private String cacheQuestion = null;

  public void beforeRead() {
    System.out.println(this.getClass().getSimpleName() + ": Begin...");
    try {
      fw = new FileWriter("english-test.txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void process(final String line, Object... outObjs) {
    if (line.startsWith("题目")) {
      cacheQuestion = line;
    } else if (line.startsWith("正确答案")) {
      if (null != cacheQuestion) {
        String[] answerSplit = line.split("[：:]");
        String answer = answerSplit.length > 0 ?
                            answerSplit[1] : answerSplit[0];
        String outLine = null;
        outLine = cacheQuestion.replaceAll("[_]{3,}", " _" + answer + "_ ");
        sbd.append("  - ").append(outLine).append("\n");
      }
      cacheQuestion = null;
    } else if (line.startsWith("* 词汇结构练习")) {
      sbd.append(line).append("\n");
    }

  }

  public void afterRead() {
    System.out.println(this.getClass().getSimpleName() + ": End!!!");
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
  }
}
