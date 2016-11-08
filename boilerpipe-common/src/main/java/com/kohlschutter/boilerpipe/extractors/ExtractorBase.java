package com.kohlschutter.boilerpipe.extractors;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.kohlschutter.boilerpipe.BoilerpipeExtractor;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextDocument;
import com.kohlschutter.boilerpipe.sax.BoilerpipeSAXInput;

/**
 * The base class of Extractors. Also provides some helper methods to quickly retrieve the text that
 * remained after processing.
 */
public abstract class ExtractorBase implements BoilerpipeExtractor {

  /**
   * Extracts text from the HTML code given as a String.
   * 
   * @param html The HTML code as a String.
   * @return The extracted text.
   * @throws BoilerpipeProcessingException
   */
  public String getText(final String html) throws BoilerpipeProcessingException {
    try {
      return getText(
               new BoilerpipeSAXInput(
                 new InputSource(
                   new StringReader(html)
                 )
               ).getTextDocument()
             );
    } catch (SAXException e) {
      throw new BoilerpipeProcessingException(e);
    }
  }

  /**
   * Extracts text from the HTML code available from the given {@link InputSource}.
   * 
   * @param is The InputSource containing the HTML
   * @return The extracted text.
   * @throws BoilerpipeProcessingException
   */
  public String getText(final InputSource is) throws BoilerpipeProcessingException {
    try {
      return getText(new BoilerpipeSAXInput(is).getTextDocument());
    } catch (SAXException e) {
      throw new BoilerpipeProcessingException(e);
    }
  }

  /**
   * Extracts text from the HTML code available from the given {@link Reader}.
   * 
   * @param r The Reader containing the HTML
   * @return The extracted text.
   * @throws BoilerpipeProcessingException
   */
  public String getText(final Reader r) throws BoilerpipeProcessingException {
    return getText(new InputSource(r));
  }

  /**
   * Extracts text from the given {@link TextDocument} object.
   * 
   * @param doc The {@link TextDocument}.
   * @return The extracted text.
   * @throws BoilerpipeProcessingException
   */
  public String getText(TextDocument doc) throws BoilerpipeProcessingException {
    process(doc);
    return doc.getContent();
  }
}
