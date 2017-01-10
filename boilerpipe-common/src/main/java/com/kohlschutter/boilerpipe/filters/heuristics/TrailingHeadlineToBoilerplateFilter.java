package com.kohlschutter.boilerpipe.filters.heuristics;

import java.util.List;
import java.util.ListIterator;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.labels.DefaultLabels;

/**
 * Marks trailing headlines ({@link TextBlock}s that have the label {@link DefaultLabels#HEADING})
 * as boilerplate. Trailing means they are marked content and are below any other content block.
 */
public final class TrailingHeadlineToBoilerplateFilter implements BoilerpipeFilter {
  public static final TrailingHeadlineToBoilerplateFilter INSTANCE =
      new TrailingHeadlineToBoilerplateFilter();

  /**
   * Returns the singleton instance for ExpandTitleToContentFilter.
   */
  public static TrailingHeadlineToBoilerplateFilter getInstance() {
    return INSTANCE;
  }

  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    boolean changes = false;

    List<TextBlock> list = doc.getTextBlocks();

    for (ListIterator<TextBlock> it = list.listIterator(list.size()); it.hasPrevious();) {
      TextBlock tb = it.previous();
      if (tb.isContent()) {
        if (tb.hasLabel(DefaultLabels.HEADING)) {
          tb.setIsContent(false);
          changes = true;
        } else {
          break;
        }
      }
    }

    return changes;
  }

}
