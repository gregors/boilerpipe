package com.kohlschutter.boilerpipe.filters.heuristics;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.labels.DefaultLabels;

/**
 * Marks all {@link TextBlock}s "content" which are between the headline and the part that has
 * already been marked content, if they are marked {@link DefaultLabels#MIGHT_BE_CONTENT}.
 * 
 * This filter is quite specific to the news domain.
 */
public final class ExpandTitleToContentFilter implements BoilerpipeFilter {
  public static final ExpandTitleToContentFilter INSTANCE = new ExpandTitleToContentFilter();

  /**
   * Returns the singleton instance for ExpandTitleToContentFilter.
   */
  public static ExpandTitleToContentFilter getInstance() {
    return INSTANCE;
  }

  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    int i = 0;
    int title = -1;
    int contentStart = -1;
    for (TextBlock tb : doc.getTextBlocks()) {
      if (contentStart == -1 && tb.hasLabel(DefaultLabels.TITLE)) {
        title = i;
        contentStart = -1;
      }
      if (contentStart == -1 && tb.isContent()) {
        contentStart = i;
      }

      i++;
    }

    if (contentStart <= title || title == -1) {
      return false;
    }
    boolean changes = false;
    for (TextBlock tb : doc.getTextBlocks().subList(title, contentStart)) {
      if (tb.hasLabel(DefaultLabels.MIGHT_BE_CONTENT)) {
        changes = tb.setIsContent(true) | changes;
      }
    }
    return changes;
  }

}
