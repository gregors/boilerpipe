package com.kohlschutter.boilerpipe.filters.english;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.labels.DefaultLabels;

/**
 * Finds blocks which are potentially indicating the end of an article text and marks them with
 * {@link DefaultLabels#INDICATES_END_OF_TEXT}. This can be used in conjunction with a downstream
 * {@link IgnoreBlocksAfterContentFilter}.
 * 
 * @see IgnoreBlocksAfterContentFilter
 */
public class TerminatingBlocksFinder implements BoilerpipeFilter {
  public static final TerminatingBlocksFinder INSTANCE = new TerminatingBlocksFinder();

  public static TerminatingBlocksFinder getInstance() {
    return INSTANCE;
  }

  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    boolean changes = false;

    for (TextBlock tb : doc.getTextBlocks()) {
      final int numWords = tb.getNumWords();
      if (numWords < 15) {
        final String text = tb.getText().trim();
        final int len = text.length();
        if (len >= 8) {
          final String textLC = text.toLowerCase();
          if (textLC.startsWith("comments")
              || startsWithNumber(textLC, len, " comments", " users responded in")
              || textLC.startsWith("© reuters") || textLC.startsWith("please rate this")
              || textLC.startsWith("post a comment") || textLC.contains("what you think...")
              || textLC.contains("add your comment") || textLC.contains("add comment")
              || textLC.contains("reader views") || textLC.contains("have your say")
              || textLC.contains("reader comments") || textLC.contains("rätta artikeln")
              || textLC.equals("thanks for your comments - this feedback is now closed")) {
            tb.addLabel(DefaultLabels.INDICATES_END_OF_TEXT);
            changes = true;
          }
        } else if (tb.getLinkDensity() == 1.0) {
          if (text.equals("Comment")) {
            tb.addLabel(DefaultLabels.INDICATES_END_OF_TEXT);
            changes = true;
          }
        }
      }
    }

    return changes;
  }

  /**
   * Checks whether the given text t starts with a sequence of digits, followed by one of the given
   * strings.
   * 
   * @param t The text to examine
   * @param len The length of the text to examine
   * @param str Any strings that may follow the digits.
   * @return true if at least one combination matches
   */
  private static boolean startsWithNumber(final String t, final int len, final String... str) {
    int j = 0;
    while (j < len && isDigit(t.charAt(j))) {
      j++;
    }
    if (j != 0) {
      for (String s : str) {
        if (t.startsWith(s, j)) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isDigit(final char c) {
    return c >= '0' && c <= '9';
  }

}
