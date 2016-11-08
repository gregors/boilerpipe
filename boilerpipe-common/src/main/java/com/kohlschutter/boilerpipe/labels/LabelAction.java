package com.kohlschutter.boilerpipe.labels;

import java.util.Arrays;

import com.kohlschutter.boilerpipe.document.TextBlock;

/**
 * Helps adding labels to {@link TextBlock}s.
 * 
 * @see ConditionalLabelAction
 */
public class LabelAction {
  protected final String[] labels;

  public LabelAction(String... labels) {
    this.labels = labels;
  }

  public void addTo(final TextBlock tb) {
    addLabelsTo(tb);
  }

  protected final void addLabelsTo(final TextBlock tb) {
    tb.addLabels(labels);
  }

  public String toString() {
    return super.toString() + "{" + Arrays.asList(labels) + "}";
  }
}
