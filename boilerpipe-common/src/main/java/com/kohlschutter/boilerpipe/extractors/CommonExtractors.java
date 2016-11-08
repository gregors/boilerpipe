package com.kohlschutter.boilerpipe.extractors;

import com.kohlschutter.boilerpipe.BoilerpipeExtractor;

/**
 * Provides quick access to common {@link BoilerpipeExtractor}s.
 */
public final class CommonExtractors {
  private CommonExtractors() {
  }

  /**
   * Works very well for most types of Article-like HTML.
   */
  public static final ArticleExtractor ARTICLE_EXTRACTOR = ArticleExtractor.INSTANCE;

  /**
   * Usually worse than {@link ArticleExtractor}, but simpler/no heuristics.
   */
  public static final DefaultExtractor DEFAULT_EXTRACTOR = DefaultExtractor.INSTANCE;

  /**
   * Like {@link DefaultExtractor}, but keeps the largest text block only.
   */
  public static final LargestContentExtractor LARGEST_CONTENT_EXTRACTOR =
      LargestContentExtractor.INSTANCE;
}
