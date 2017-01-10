package com.kohlschutter.boilerpipe.sax;

import org.apache.xerces.parsers.AbstractSAXParser;

import com.kohlschutter.boilerpipe.BoilerpipeDocumentSource;
import com.kohlschutter.boilerpipe.document.TextBlock;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.org.cyberneko.html.HTMLConfiguration;

/**
 * A simple SAX Parser, used by {@link BoilerpipeSAXInput}. The parser uses <a
 * href="http://nekohtml.sourceforge.net/">CyberNeko</a> to parse HTML content.
 */
public class BoilerpipeHTMLParser extends AbstractSAXParser implements BoilerpipeDocumentSource {

  private BoilerpipeHTMLContentHandler contentHandler;

  /**
   * Constructs a {@link BoilerpipeHTMLParser} using a default HTML content handler.
   */
  public BoilerpipeHTMLParser() {
    this(new BoilerpipeHTMLContentHandler());
  }

  /**
   * Constructs a {@link BoilerpipeHTMLParser} using the given {@link BoilerpipeHTMLContentHandler}.
   * 
   * @param contentHandler
   */
  public BoilerpipeHTMLParser(BoilerpipeHTMLContentHandler contentHandler) {
    super(new HTMLConfiguration());
    setContentHandler(contentHandler);
  }

  public void setContentHandler(final BoilerpipeHTMLContentHandler contentHandler) {
    this.contentHandler = contentHandler;
    super.setContentHandler(contentHandler);
  }

  /**
   * Returns a {@link TextDocument} containing the extracted {@link TextBlock} s. NOTE: Only call
   * this after {@link #parse(org.xml.sax.InputSource)}.
   * 
   * @return The {@link TextDocument}
   */
  public TextDocument toTextDocument() {
    return contentHandler.toTextDocument();
  }
}
