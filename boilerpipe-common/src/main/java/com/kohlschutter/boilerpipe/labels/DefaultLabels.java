package com.kohlschutter.boilerpipe.labels;

import com.kohlschutter.boilerpipe.document.TextBlock;

/**
 * Some pre-defined labels which can be used in conjunction with {@link TextBlock#addLabel(String)}
 * and {@link TextBlock#hasLabel(String)}.
 */
public final class DefaultLabels {
  public static final String TITLE = "de.l3s.boilerpipe/TITLE";
  public static final String ARTICLE_METADATA = "de.l3s.boilerpipe/ARTICLE_METADATA";
  public static final String INDICATES_END_OF_TEXT = "de.l3s.boilerpipe/INDICATES_END_OF_TEXT";
  public static final String MIGHT_BE_CONTENT = "de.l3s.boilerpipe/MIGHT_BE_CONTENT";
  public static final String VERY_LIKELY_CONTENT = "de.l3s.boilerpipe/VERY_LIKELY_CONTENT";
  public static final String STRICTLY_NOT_CONTENT = "de.l3s.boilerpipe/STRICTLY_NOT_CONTENT";
  public static final String HR = "de.l3s.boilerpipe/HR";
  public static final String LI = "de.l3s.boilerpipe/LI";

  public static final String HEADING = "de.l3s.boilerpipe/HEADING";
  public static final String H1 = "de.l3s.boilerpipe/H1";
  public static final String H2 = "de.l3s.boilerpipe/H2";
  public static final String H3 = "de.l3s.boilerpipe/H3";

  public static final String MARKUP_PREFIX = "<";

  private DefaultLabels() {
    // not to be instantiated
  }
}
