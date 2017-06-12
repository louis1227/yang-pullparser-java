package iwostaq.yppj.tool;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.Stack;

import iwostaq.yppj.YangPullParser;
import iwostaq.yppj.YangPullParser.EventType;
import iwostaq.yppj.YangPullParserImpl;
import iwostaq.yppj.exception.YangPullParserException;

public abstract class YangStackWriter {

  protected Stack<String> stmtStack;

  protected Reader fromReader;

  protected PrintStream toOutputStream;

  /**
   * A constructor.
   * 
   * @param fromReader
   *          the reader where a YANG data is read
   * @param toOutputStream the PrintStream object to write the converted data
   */
  public YangStackWriter(Reader fromReader, PrintStream toOutputStream) {
    if (fromReader == null || toOutputStream == null) {
      throw new IllegalArgumentException();
    }

    this.stmtStack = new Stack<>();
    this.fromReader = fromReader;
    this.toOutputStream = toOutputStream;
  }

  /**
   * A constructor. The output is set to standard output.
   * 
   * @param fromReader
   *          the reader where a YANG data is read
   */
  public YangStackWriter(Reader fromReader) {
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
        this.handleStartStatement(ypp);
        break;
      case STATEMENT_END:
        this.handleEndStatementEvent(ypp);
        break;
      case END_MODULE:
        throw new YangPullParserException();
      }
    }
  }

  public abstract void handleStartStatement(YangPullParser ypp);

  public abstract void handleEndStatementEvent(YangPullParser ypp);
}
