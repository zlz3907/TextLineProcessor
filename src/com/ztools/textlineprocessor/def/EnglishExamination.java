package com.ztools.textlineprocessor.def;

import com.ztools.textlineprocessor.ILineProcessor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class EnglishExamination implements ILineProcessor {

  private StringBuilder sbd = new StringBuilder();
  private OutputStreamWriter fw = null;
  private String cacheQuestion = null;
  private boolean isQuestion = false;
  private boolean isBody = false;
  private int lineNumber = 0;
  private int questionNumber = 0;
  private int indents = 0;
  private final String COLON_SPACE = "    - ";
  private String style = "";

  private final String[] SKIP_LINE_PREFIXS = new String[] {
    "开始时间", "状态", "完成于", "耗时", "未标记标记题目",
    "题干", "未回答", "满分", "选择一项", "反馈"
  };

  private final String[] stars = new String[] {
    "* ", "** ", "*** ", "**** ", "***** ", "****** "
  };

  private final String[] spaces = new String[] {
    "", " ", "  ", "   ", "    ", "     ", "      "
  };

  private boolean isSkipLine(final String line, final String[] filters) {
    if (null == line) {
      return true;
    }
    if (null != filters && 0 < filters.length) {
      for (String prefixWord : filters) {
        if (line.startsWith(prefixWord)) {
          return true;
        }
      }
    }
    return false;
  }

  public void beforeRead() {
    System.out.println(this.getClass().getSimpleName() + ": Begin...");
    try {
      fw = new OutputStreamWriter(new FileOutputStream("english-examination.org"),
                                  "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    style = "#+BEGIN_HTML" +
      "<link rel=\"stylesheet\" title=\"Standard\" \n"
      + "href=\"http://blog.3zso.com/css/worg.css\" type=\"text/css\" />\n"
      + "<style>\n"
      + "body {\n"
      + "font-family: Trebuchet MS, Lucida Grande, Tahoma,"
      + "Verdana, Arial, sans-serif;\n"
      + "font-size: 16px;line-height: 1.7;\n"
      + "}\n"
      + "a {color: #6e7d8e;\n"
      + "ext-decoration: none;\n"
      + "font-weight: bold;\n"
      + "}\n"
      + "</style>\n"
      + "#+END_HTML\n";
  }

  public void process(final String line, Object... outObjs) {
    if (null == line || line.isEmpty()
        || isSkipLine(line, SKIP_LINE_PREFIXS))
      return;
    // 格式化
    String fixLine = line.replaceAll("　" , " ").trim();
    if (fixLine.matches("^Part (I|II|III|V|IV|V|VI) .*")) {
      sbd.append(stars[0]);
      isBody = true;
    } else if (fixLine.matches("^[ABCD][.] .*")) {
      sbd.append(spaces[indents]).append(COLON_SPACE);
    } else if (fixLine.matches("^（[0-9]）.*")) {
      sbd.append(spaces[4]).append("- ");
    } else if (fixLine.matches("^[0-9]+[．.] ?.*")) {
      sbd.append(spaces[4]).append("- ");
      fixLine = fixLine.replaceAll("．", ". ");
    } else if (fixLine.startsWith("范文")) {
      sbd.append(stars[1]);
    } else if (fixLine.startsWith("正确答案")) {
      sbd.append(COLON_SPACE).append(spaces[2]);
    } else if (fixLine.startsWith("题目")) {
      sbd.append(spaces[2]).append("- ");
      sbd.append(++questionNumber).append(". ");
      isQuestion = true;
      indents = questionNumber > 20 ? 2 : 0;
      return;
    } else if (isQuestion) {
      isQuestion = false;
    } else if (isBody) {
      sbd.append(spaces[2]).append("- ");
    }

    if (questionNumber == 25) {
      //testLine = testLine.replaceAll("   ", "");
    }

    sbd.append(fixLine).append("\n");
    lineNumber++;
    if (1 == lineNumber) {
      sbd.append(style).append("\n");
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
