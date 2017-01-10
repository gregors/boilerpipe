package com.kohlschutter.boilerpipe.filters.heuristics;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.labels.DefaultLabels;

/**
 * Marks all blocks as content that:
 * <ol>
 * <li>are on the same tag-level as very likely main content (usually the level of the largest
 * block)</li>
 * <li>have a significant number of words, currently: at least 100</li>
 * </ol>
 */
public final class LargeBlockSameTagLevelToContentFilter implements BoilerpipeFilter {
  public static final LargeBlockSameTagLevelToContentFilter INSTANCE =
      new LargeBlockSameTagLevelToContentFilter();

  private LargeBlockSameTagLevelToContentFilter() {
  }

  public boolean process(final TextDocument doc) throws BoilerpipeProcessingException {

    boolean changes = false;

    int tagLevel = -1;
    for (TextBlock tb : doc.getTextBlocks()) {
      if (tb.isContent() && tb.hasLabel(DefaultLabels.VERY_LIKELY_CONTENT)) {
        tagLevel = tb.getTagLevel();
        break;
      }
    }

    if (tagLevel == -1) {
      return false;
    }

    for (TextBlock tb : doc.getTextBlocks()) {
      if (!tb.isContent()) {

        if (tb.getNumWords() >= 100 && tb.getTagLevel() == tagLevel) {
          tb.setIsContent(true);
          changes = true;
        }
      }
    }

    return changes;

  }
}
