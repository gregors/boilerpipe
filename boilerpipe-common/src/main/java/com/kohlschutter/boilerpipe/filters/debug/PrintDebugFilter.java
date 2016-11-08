package com.kohlschutter.boilerpipe.filters.debug;

import java.io.PrintWriter;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextDocument;

/**
 * Prints debug information about the current state of the TextDocument. (= calls
 * {@link TextDocument#debugString()}.
 */
public final class PrintDebugFilter implements BoilerpipeFilter {
  /**
   * Returns the default instance for {@link PrintDebugFilter}, which dumps debug information to
   * <code>System.out</code>
   */
  public static final PrintDebugFilter INSTANCE = new PrintDebugFilter(new PrintWriter(System.out,
      true));
  private final PrintWriter out;

  /**
   * Returns the default instance for {@link PrintDebugFilter}, which dumps debug information to
   * <code>System.out</code>
   */
  public static PrintDebugFilter getInstance() {
    return INSTANCE;
  }

  /**
   * Creates a new instance of {@link PrintDebugFilter}.
   * 
   * Only use this method if you are not going to dump the debug information to
   * <code>System.out</code> -- for this case, use {@link #getInstance()} instead.
   * 
   * @param out The target {@link PrintWriter}. Will not be closed
   */
  public PrintDebugFilter(final PrintWriter out) {
    this.out = out;

  }

  @Override
  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    out.println(doc.debugString());

    return false;
  }
}
