package com.ztools.textlineprocessor;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import com.ztools.textlineprocessor.def.*;

public class Main {

  private static void usage(String info) {

  }

  private static void iteration(final File file, boolean isRecursion,
                               List<File> subFiles) {
    if (isRecursion && file.isDirectory()) {
      File[] fs = file.listFiles();
      for (File f : fs) {
        iteration(f, isRecursion, subFiles);
      }
    } else if (file.isFile()) {
      subFiles.add(file);
    }

  }

  public static void main(String[] args) {
    boolean isPrintLine = false;
    boolean isRecursion = false;
    List<String> readFiles = new ArrayList<String>();
    List<String> lineProcessorClassesNames = new ArrayList<String>();
    if (null != args) {
      for (int i = 0; i < args.length; i++) {
        if ("-p".equals(args[i]) || "--printline".equals(args[i])) {
          isPrintLine = true;
        } else if (("-f".equals(args[i]) || "-d".equals(args[i]))
                   && i < args.length - 1) {
          readFiles.add(args[++i]);
        } else if ("-r".equals(args[i]) && i < args.length - 1) {
          isRecursion = true;
        } else {
          if (!args[i].isEmpty() && !args[i].startsWith("-"))
            lineProcessorClassesNames.add(args[i]);
        }
      }
    }

    int point = isPrintLine ? 1 : 0;
    ILineProcessor[] lineProcessors =
      new ILineProcessor[lineProcessorClassesNames.size()
                         + (point)];

    if (isPrintLine) {
      lineProcessors[0] = new PrintLine();
    }

    for (int i = 0; i < lineProcessorClassesNames.size(); i++) {
      String c = lineProcessorClassesNames.get(i);
      try {
        Class<?> processorClass = Class.forName(c);
        Object obj = processorClass.newInstance();
        if (obj instanceof ILineProcessor) {
          ILineProcessor aProcessor = (ILineProcessor) obj;
          lineProcessors[point++] = aProcessor;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    List<File> needToProcessFiles = new ArrayList<File>();
    for (int i = 0; i < readFiles.size(); i++) {
      File file = new File(readFiles.get(i));
      if (file.exists()) {
        if (file.isFile()) {
          needToProcessFiles.add(file);
        } else if (file.isDirectory()) {
          File[] fs = file.listFiles();
          for (File f : fs) {
            iteration(f, isRecursion, needToProcessFiles);
          }
        }
      }
    }

    System.out.println("Start...");
    TLPer tlper = new TLPer(lineProcessors);
    for (int i = 0; i < needToProcessFiles.size(); i++) {
      try {
        tlper.read(needToProcessFiles.get(i));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    System.out.println("End!!!");
  }
}
