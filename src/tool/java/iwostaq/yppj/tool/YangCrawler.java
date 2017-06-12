package iwostaq.yppj.tool;

import java.io.PrintStream;
import java.io.Reader;

import iwostaq.yppj.YangPullParser;
import iwostaq.yppj.YangPullParser.StatementType;

/**
 * A crawler that lists all the statements in a given Yang model.
 * 
 */
public final class YangCrawler extends YangStackWriter {

  public YangCrawler(Reader fromReader, PrintStream toOutputStream) {
    super(fromReader, toOutputStream);
  }

  public YangCrawler(Reader fromReader) {
    super(fromReader);
  }

  @Override
  public void handleStartStatement(YangPullParser ypp) {
    StatementType stmt = ypp.getStatementType();
    String namespace = ypp.getNamespace();
    String identifier = ypp.getIdentifier();
    String argument = ypp.getArgument();
    String elem = null;
    if (identifier == null) {
      elem = stmt.name() + "(" + argument + ")";
    } else {
      if (namespace == null) {
        elem = stmt.name() + "[" + identifier + "]";
      } else {
        elem = stmt.name() + "[" + namespace + ":" + identifier + "]";
      }
    }
    super.stmtStack.push(elem);
    for (String s : super.stmtStack) {
      super.toOutputStream.print('/');
      super.toOutputStream.print(s);
    }
    this.toOutputStream.println();

  }

  @Override
  public void handleEndStatementEvent(YangPullParser ypp) {
    this.stmtStack.pop();
  }

}
