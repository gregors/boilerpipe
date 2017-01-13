package com.kohlschutter.boilerpipe.filters.heuristics;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.kohlschutter.boilerpipe.BoilerpipeFilter;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.labels.DefaultLabels;

/**
 * Marks {@link TextBlock}s which contain parts of the HTML <code>&lt;TITLE&gt;</code> tag, using
 * some heuristics which are quite specific to the news domain.
 */
public final class DocumentTitleMatchClassifier implements BoilerpipeFilter {

  private final Set<String> potentialTitles;

  public DocumentTitleMatchClassifier(String title) {
    if (title == null) {
      this.potentialTitles = null;
    } else {

      title = title.replace('\u00a0', ' ');
      title = title.replace("'", "");

      title = title.trim().toLowerCase();

      if (title.length() == 0) {
        this.potentialTitles = null;
      } else {
        this.potentialTitles = new HashSet<String>();

        potentialTitles.add(title);

        String p;

        p = getLongestPart(title, "[ ]*[\\|»|-][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }
        p = getLongestPart(title, "[ ]*[\\|»|:][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }
        p = getLongestPart(title, "[ ]*[\\|»|:\\(\\)][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }
        p = getLongestPart(title, "[ ]*[\\|»|:\\(\\)\\-][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }
        p = getLongestPart(title, "[ ]*[\\|»|,|:\\(\\)\\-][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }
        p = getLongestPart(title, "[ ]*[\\|»|,|:\\(\\)\\-\u00a0][ ]*");
        if (p != null) {
          potentialTitles.add(p);
        }

        addPotentialTitles(title, "[ ]+[\\|][ ]+", 4);
        addPotentialTitles(title, "[ ]+[\\-][ ]+", 4);

        potentialTitles.add(title.replaceFirst(" - [^\\-]+$", ""));
        potentialTitles.add(title.replaceFirst("^[^\\-]+ - ", ""));
      }
    }
  }

  public Set<String> getPotentialTitles() {
    return potentialTitles;
  }

  private void addPotentialTitles(final String title, final String pattern, final int minWords) {
    String[] parts = title.split(pattern);
    if (parts.length == 1) {
      return;
    }
    for (int i = 0; i < parts.length; i++) {
      String p = parts[i];
      if (p.contains(".com")) {
        continue;
      }
      final int numWords = p.split("[\b ]+").length;
      if (numWords >= minWords) {
        this.potentialTitles.add(p);
      }
    }
  }

  private String getLongestPart(final String title, final String pattern) {
    String[] parts = title.split(pattern);
    if (parts.length == 1) {
      return null;
    }
    int longestNumWords = 0;
    String longestPart = "";
    for (int i = 0; i < parts.length; i++) {
      String p = parts[i];
      if (p.contains(".com")) {
        continue;
      }
      final int numWords = p.split("[\b ]+").length;
      if (numWords > longestNumWords || p.length() > longestPart.length()) {
        longestNumWords = numWords;
        longestPart = p;
      }
    }
    if (longestPart.length() == 0) {
      return null;
    } else {
      return longestPart.trim();
    }
  }

  private static final Pattern PAT_REMOVE_CHARACTERS = Pattern.compile("[\\?\\!\\.\\-\\:]+");

  public boolean process(TextDocument doc) throws BoilerpipeProcessingException {
    if (potentialTitles == null) {
      return false;
    }
    boolean changes = false;

    for (final TextBlock tb : doc.getTextBlocks()) {
      String text = tb.getText();

      text = text.replace('\u00a0', ' ');
      text = text.replace("'", "");

      text = text.trim().toLowerCase();

      if (potentialTitles.contains(text)) {
        tb.addLabel(DefaultLabels.TITLE);
        changes = true;
        break;
      }

      text = PAT_REMOVE_CHARACTERS.matcher(text).replaceAll("").trim();
      if (potentialTitles.contains(text)) {
        tb.addLabel(DefaultLabels.TITLE);
        changes = true;
        break;
      }
    }
    return changes;
  }

}
