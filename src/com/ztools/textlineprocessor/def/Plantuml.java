package com.ztools.textlineprocessor.def;

import java.util.HashMap;
import java.util.Map;

import com.ztools.textlineprocessor.ILineProcessor;

public class Plantuml implements ILineProcessor {
  private static final String ENDLINE = "\n";
  private static String head = "skinparam class {" + ENDLINE +
    "  BackgroundColor LightSeaGreen" + ENDLINE +
    "  ArrowColor SeaGreen" +    ENDLINE +
    "  BorderColor OliveDrab" +  ENDLINE +
    "}" + ENDLINE;

  private static Map<String, StringBuilder> pkgMap
    = new HashMap<String, StringBuilder>();

  private StringBuilder currentPkg = null;
  private StringBuilder currentCls = null;
  private String clsName = null;
  private String pkgName = null;

  private static String lastPkgName = null;

  @Override
  public void beforeRead() {
    currentCls = new StringBuilder();
  }

  public void process(final String line, Object... outObjs) {
    System.out.println(PrintLine.class.getSimpleName() + ": " + line);
    if (line.startsWith("package")) {
      this.pkgName = line.substring(8, line.length() - 1);
      this.currentPkg = pkgMap.get(this.pkgName);
      if (null == this.currentPkg) {
        this.currentPkg = new StringBuilder("package "
                                            + this.currentPkg + " {\n}\n");
        pkgMap.put(this.pkgName, this.currentPkg);
        if (null == lastPkgName) {
          lastPkgName = this.pkgName;
        }
      }
    } else if (line.startsWith("public class")) {
      //this.clsName = line.
      this.currentCls = new StringBuilder("class ");
      //this.currentCls.append(this.);
    } else if (line.startsWith("public interface")) {
    } else if (line.startsWith("public enum")) {
    } else if (line.startsWith("public abstract class")) {

    }

  }

  @Override
  public void afterRead() {
    if (null != this.clsName && null != this.pkgName) {
      this.currentCls.append("\n}\n");
      this.currentPkg.insert(this.currentPkg.length() - 2, this.currentCls);
      pkgMap.put(this.pkgName, this.currentPkg);
    }
  }

}
