package iwostaq.yppj.tool;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Stack;

import iwostaq.yppj.YangPullParser;
import iwostaq.yppj.YangPullParser.EventType;
import iwostaq.yppj.YangPullParser.StatementType;
import iwostaq.yppj.YangPullParserImpl;
import iwostaq.yppj.exception.YangPullParserException;

/**
 * A crawler that lists all the statements in a given Yang model.
 * 
 */
public final class YangCrawler {

  private Stack<String> stmtStack;

  private Reader fromReader;

  private PrintStream toOutputStream;

  /**
   * Constructor.
   * 
   * @param fromReader
   *          the reader where data is read
   * @param toOutputStream
   *          the output stream where the list is written to
   */
  public YangCrawler(Reader fromReader, PrintStream toOutputStream) {
    if (fromReader == null || toOutputStream == null) {
      throw new IllegalArgumentException();
    }
    this.stmtStack = new Stack<>();
    this.fromReader = fromReader;
    this.toOutputStream = toOutputStream;
  }

  /**
   * Constructor. The output is set to standard output.
   * 
   * @param fromReader
   *          the reader where data is read from
   */
  public YangCrawler(Reader fromReader) {
    this(fromReader, System.out);
  }

  /**
   * Starts the crawling.
   * 
   * @throws IOException
   *           raised when problems related to I/O are occurred.
   * @throws YangPullParserException
   *           raised when parsing problems are occurred.
   */
  public void crawl() throws IOException, YangPullParserException {
    YangPullParser ypp = new YangPullParserImpl(this.fromReader);

    while (true) {
      EventType eventType = ypp.next();
      if (eventType == EventType.END_MODULE) {
        break;
      }

      switch (eventType) {
      case STATEMENT_START:
        StatementType stmt = ypp.getStatementType();
        String namespace = ypp.getNamespace();
        String identifier = ypp.getIdentifier();
        String argument = ypp.getArgument();
        String elem = null;
        if (identifier == null) {
          elem = String.format("%s(%s)", stmt.name(), argument);
        } else {
          if (namespace == null) {
            elem = String.format("%s[%s]", stmt.name(), identifier);
          } else {
            elem = String.format("%s[%s:%s]", stmt.name(), namespace, identifier);
          }
        }
        this.stmtStack.push(elem);

        for (String s : this.stmtStack) {
          this.toOutputStream.print('/');
          this.toOutputStream.print(s);
        }
        this.toOutputStream.println();

        break;
      case STATEMENT_END:
        this.stmtStack.pop();
        break;
      case END_MODULE:
        throw new YangPullParserException();
      }
    }
  }
}
