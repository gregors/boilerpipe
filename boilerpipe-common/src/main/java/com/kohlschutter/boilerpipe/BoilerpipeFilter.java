package com.kohlschutter.boilerpipe;

import com.kohlschutter.boilerpipe.document.TextDocument;

/**
 * A generic {@link BoilerpipeFilter}. Takes a {@link TextDocument} and processes it somehow.
 */
public interface BoilerpipeFilter {
  /**
   * Processes the given document <code>doc</code>.
   * 
   * @param doc The {@link TextDocument} that is to be processed.
   * @return <code>true</code> if changes have been made to the {@link TextDocument}.
   * @throws BoilerpipeProcessingException
   */
  boolean process(final TextDocument doc) throws BoilerpipeProcessingException;
}
