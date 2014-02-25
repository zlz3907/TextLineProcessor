package com.ztools.textlineprocessor;

public interface ILineProcessor {

  /**
   * Describe <code>process</code> method here.
   *
   * @param line a <code>String</code> value
   * @param outObjs an <code>Object</code> value
   * @return a <code>String</code> value
   */
  void process(final String line, Object... outObjs);

  void beforeRead();

  void afterRead();
}
