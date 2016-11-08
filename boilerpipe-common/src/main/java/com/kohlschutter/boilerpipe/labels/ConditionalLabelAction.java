package com.kohlschutter.boilerpipe.labels;

import com.kohlschutter.boilerpipe.conditions.TextBlockCondition;
import com.kohlschutter.boilerpipe.document.TextBlock;

/**
 * Adds labels to a {@link TextBlock} if the given criteria are met.
 */
public final class ConditionalLabelAction extends LabelAction {

  private final TextBlockCondition condition;

  public ConditionalLabelAction(TextBlockCondition condition, String... labels) {
    super(labels);
    this.condition = condition;
  }

  public void addTo(final TextBlock tb) {
    if (condition.meetsCondition(tb)) {
      addLabelsTo(tb);
    }
  }
}
