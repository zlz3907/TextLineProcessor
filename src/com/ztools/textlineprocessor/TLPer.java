package com.ztools.textlineprocessor;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;

/**
 * <code>TLPer</code>(Text Line Processor)是文本行处理程序的入口.
 *
 * <p>
 * 在各种项目过程中，我们经常需要读取一些文本，并进行处理，不同的需求
 * 只是处理的逻辑不一样而已。一般情况下，Java对读取文件的操作流程也是
 * 一样的（打开文件、处理文件和关闭文件）。在这个过程中，我们将处理过
 * 程独立出来，通过Java类反射的机制将处理逻辑注入。
 * </p>
 *
 * @author <a href="mailto:zlz.3907@gmail.com">Zhong Lizhi</a>
 * @version 1.0
 */
public final class TLPer {
  /**
   * <code>lineProcessors</code>一组行处理器，他们的实现类专门用来执行
   * 文本行的处理逻辑.
   *
   */
  private ILineProcessor[] lineProcessors = null;

  /**
   * Creates a new <code>TLPer</code> instance.
   *
   */
  public TLPer() {

  }

  /**
   * Creates a new <code>TLPer</code> instance.
   *
   * @param aLineProcessors an <code>ILineProcessor</code> value
   */
  public TLPer(final ILineProcessor[] aLineProcessors) {
    this.lineProcessors = aLineProcessors;
  }

  /**
   * 读取一个文件.
   *
   * @param in an <code>InputStream</code> value
   * @param outObjs an <code>Object</code> value
   * @return a <code>byte[]</code> value
   */
  public byte[] read(final InputStream in, Object... outObjs) {
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    //br.readLine();
    return null;
  }


  /**
   * Describe <code>read</code> method here.
   *
   * @param in an <code>InputStream</code> value
   * @return a <code>byte[]</code> value
   */
  public byte[] read(final InputStream in) {
    return read(in, new Object[] {});
  }

  /**
   * Describe <code>read</code> method here.
   *
   * @param file a <code>File</code> value
   * @return a <code>byte[]</code> value
   * @exception FileNotFoundException if an error occurs
   */
  public byte[] read(final File file) throws FileNotFoundException {
    if (null != file && file.exists()) {
      return read(new FileInputStream(file));
    }

    throw new FileNotFoundException("File not found:" + file);
  }

  /**
   * Describe <code>read</code> method here.
   *
   * @param path a <code>String</code> value
   * @return a <code>byte[]</code> value
   * @exception FileNotFoundException if an error occurs
   */
  public byte[] read(final String path) throws FileNotFoundException {
    if (null != path && !path.isEmpty()) {
      return read(new File(path));
    }

    throw new FileNotFoundException("File not found: " + path);
  }
}
