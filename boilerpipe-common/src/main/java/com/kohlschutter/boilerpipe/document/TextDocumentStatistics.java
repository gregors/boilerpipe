package com.kohlschutter.boilerpipe.document;

/**
 * Provides shallow statistics on a given {@link TextDocument}
 */
public final class TextDocumentStatistics {
  private int numWords = 0;
  private int numBlocks = 0;

  /**
   * Computes statistics on a given {@link TextDocument}.
   * 
   * @param doc The {@link TextDocument}.
   * @param contentOnly if true then o
   */
  public TextDocumentStatistics(final TextDocument doc, final boolean contentOnly) {
    for (TextBlock tb : doc.getTextBlocks()) {
      if (contentOnly && !tb.isContent()) {
        continue;
      }

      numWords += tb.getNumWords();
      numBlocks++;
    }
  }

  /**
   * Returns the average number of words at block-level (= overall number of words divided by the
   * number of blocks).
   * 
   * @return Average
   */
  public float avgNumWords() {
    return numWords / (float) numBlocks;
  }

  /**
   * Returns the overall number of words in all blocks.
   * 
   * @return Sum
   */
  public int getNumWords() {
    return numWords;
  }
}
