package iwostaq.yppj.tool;

import java.io.PrintStream;
import java.io.Reader;

import iwostaq.yppj.YangPullParser;
import iwostaq.yppj.YangPullParser.StatementType;

/**
 * A crawler that lists all the statements in a given Yang model.
 * 
 */
public final class NameEnumerator extends YangStackWriter {

  public NameEnumerator(Reader fromReader, PrintStream toOutputStream) {
    super(fromReader, toOutputStream);
  }

  public NameEnumerator(Reader fromReader) {
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
      elem = stmt.name();
    } else {
      if (namespace == null) {
        elem = identifier;
      } else {
        elem = namespace + ":" + identifier;
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
