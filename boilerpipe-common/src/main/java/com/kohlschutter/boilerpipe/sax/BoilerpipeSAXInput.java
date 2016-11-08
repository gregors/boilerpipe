package com.kohlschutter.boilerpipe.sax;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.kohlschutter.boilerpipe.BoilerpipeInput;
import com.kohlschutter.boilerpipe.BoilerpipeProcessingException;
import com.kohlschutter.boilerpipe.document.TextDocument;

/**
 * Parses an {@link InputSource} using SAX and returns a {@link TextDocument}.
 */
public final class BoilerpipeSAXInput implements BoilerpipeInput {
  private final InputSource is;

  /**
   * Creates a new instance of {@link BoilerpipeSAXInput} for the given {@link InputSource}.
   * 
   * @param is
   * @throws SAXException
   */
  public BoilerpipeSAXInput(final InputSource is) throws SAXException {
    this.is = is;
  }

  /**
   * Retrieves the {@link TextDocument} using a default HTML parser.
   */
  public TextDocument getTextDocument() throws BoilerpipeProcessingException {
    return getTextDocument(new BoilerpipeHTMLParser());
  }

  /**
   * Retrieves the {@link TextDocument} using the given HTML parser.
   * 
   * @param parser The parser used to transform the input into boilerpipe's internal representation.
   * @return The retrieved {@link TextDocument}
   * @throws BoilerpipeProcessingException
   */
  public TextDocument getTextDocument(final BoilerpipeHTMLParser parser)
      throws BoilerpipeProcessingException {
    try {
      parser.parse(is);
    } catch (IOException e) {
      throw new BoilerpipeProcessingException(e);
    } catch (SAXException e) {
      throw new BoilerpipeProcessingException(e);
    }

    return parser.toTextDocument();
  }

}
