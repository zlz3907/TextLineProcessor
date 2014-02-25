package com.ztools.textlineprocessor.def;

import com.ztools.textlineprocessor.ILineProcessor;

public class PrintLine implements ILineProcessor {
  public void process(final String line, Object... outObjs) {
    System.out.println(PrintLine.class.getSimpleName() + ": " + line);
  }

  public void beforeRead() {

  }

  public void afterRead() {

  }
}
