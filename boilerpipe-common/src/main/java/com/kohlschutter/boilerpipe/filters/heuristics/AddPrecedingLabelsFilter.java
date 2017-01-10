package com.kohlschutter.boilerpipe.filters.heuristics;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;

/**
 * Adds the labels of the preceding block to the current block, optionally adding a prefix.
 */
public final class AddPrecedingLabelsFilter implements BoilerpipeFilter {

  public static final AddPrecedingLabelsFilter INSTANCE = new AddPrecedingLabelsFilter("");
  public static final AddPrecedingLabelsFilter INSTANCE_PRE = new AddPrecedingLabelsFilter("^");

  private final String labelPrefix;

  /**
   * Creates a new {@link AddPrecedingLabelsFilter} instance.
   * 
   * @param maxBlocksDistance The maximum distance in blocks.
   * @param contentOnly
   */
  public AddPrecedingLabelsFilter(final String labelPrefix) {
    this.labelPrefix = labelPrefix;
  }

  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    List<TextBlock> textBlocks = doc.getTextBlocks();
    if (textBlocks.size() < 2) {
      return false;
    }

    boolean changes = false;
    int remaining = textBlocks.size();

    TextBlock blockBelow = null;
    TextBlock block;
    for (ListIterator<TextBlock> it = textBlocks.listIterator(textBlocks.size()); it.hasPrevious();) {
      if (--remaining <= 0) {
        break;
      }
      if (blockBelow == null) {
        blockBelow = it.previous();
        continue;
      }
      block = it.previous();

      Set<String> labels = block.getLabels();
      if (labels != null && !labels.isEmpty()) {
        for (String l : labels) {
          blockBelow.addLabel(labelPrefix + l);
        }
        changes = true;
      }
      blockBelow = block;
    }

    return changes;
  }
}
