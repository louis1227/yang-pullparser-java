package iwostaq.yppj;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.UnbufferedCharStream;

import iwostaq.yppj.exception.YangPullParserException;
import iwostaq.yppj.g.YangLexer;

/**
 * A Yang pull parser implementation.
 *
 */
public class YangPullParserImpl implements YangPullParser {

  protected static StatementType[] statementTypes = new StatementType[YangLexer.ruleNames.length];
  static {
    Arrays.fill(statementTypes, null);
    List<String> ruleNameList = Arrays.asList(YangLexer.ruleNames);

    for (StatementType stType : StatementType.values()) {
      String ruleName = "KW_" + stType.name().toLowerCase();
      int index = ruleNameList.indexOf(ruleName);
      if (0 <= index) {
        statementTypes[index] = stType;
      }
    }
  }

  protected Reader fromReader;

  protected Stack<Event> eventStack;

  protected Event lastEvent;

  protected Token reservedNextToken;

  protected CommonTokenStream tokenStream;

  /**
   * Constructor.
   *
   * @param fromReader
   *          the Reader object to read data.
   */
  public YangPullParserImpl(Reader fromReader) {
    if (fromReader == null) {
      throw new IllegalArgumentException();
    }
    this.fromReader = fromReader;

    CharStream fromCharStream = new UnbufferedCharStream(fromReader);
    YangLexer lexer = new YangLexer(fromCharStream);
    lexer.setTokenFactory(new CommonTokenFactory(true));
    this.tokenStream = new CommonTokenStream(lexer);
    //this.tokenStream.seek(0);

    this.eventStack = new Stack<>();
    this.lastEvent = null;
    this.reservedNextToken = null;
  }

  /**
   * Returns next parsing event.
   * 
   * @return the next parsing event
   * @throws IOException 
   * @throws YangPullParserException
   */
  @Override
  public EventType next() throws IOException, YangPullParserException {
    Token tok = this.getCurrentToken();
    if (tok.getType() == YangLexer.EOF) {
      this.lastEvent = Event.END_DEFINITION;
      return this.lastEvent.getEventType();
    }

    switch (tok.getType()) {
    case YangLexer.KW_anyxml:
    case YangLexer.KW_base:
    case YangLexer.KW_belongs_to:
    case YangLexer.KW_bit:
    case YangLexer.KW_case:
    case YangLexer.KW_choice:
    case YangLexer.KW_container:
    case YangLexer.KW_extension:
    case YangLexer.KW_feature:
    case YangLexer.KW_grouping:
    case YangLexer.KW_identity:
    case YangLexer.KW_import:
    case YangLexer.KW_include:
    case YangLexer.KW_leaf:
    case YangLexer.KW_leaf_list:
    case YangLexer.KW_list:
    case YangLexer.KW_module:
    case YangLexer.KW_rpc:
    case YangLexer.KW_submodule:
    case YangLexer.KW_type:
    case YangLexer.KW_typedef:
    case YangLexer.KW_uses:
      this.startStatement(tok.getType());
      break;
    case YangLexer.KW_argument:
    case YangLexer.KW_augment:
    case YangLexer.KW_config:
    case YangLexer.KW_contact:
    case YangLexer.KW_default:
    case YangLexer.KW_description:
    case YangLexer.KW_enum:
    case YangLexer.KW_error_app_tag:
    case YangLexer.KW_error_message:
    case YangLexer.KW_fraction_digits:
    case YangLexer.KW_key:
    case YangLexer.KW_length:
    case YangLexer.KW_max_elements:
    case YangLexer.KW_min_elements:
    case YangLexer.KW_must:
    case YangLexer.KW_namespace:
    case YangLexer.KW_ordered_by:
    case YangLexer.KW_organization:
    case YangLexer.KW_path:
    case YangLexer.KW_pattern:
    case YangLexer.KW_prefix:
    case YangLexer.KW_presence:
    case YangLexer.KW_range:
    case YangLexer.KW_reference:
    case YangLexer.KW_refine:
    case YangLexer.KW_revision:
    case YangLexer.KW_revision_date:
    case YangLexer.KW_status:
    case YangLexer.KW_unique:
    case YangLexer.KW_value:
    case YangLexer.KW_when:
    case YangLexer.KW_yang_version:
    case YangLexer.KW_yin_element:
      this.startStatementWithArg(tok.getType());
      break;
    case YangLexer.S_RBR:
    case YangLexer.S_SEMICOLON:
      this.endStatement();
      break;
    case YangLexer.IDENTIFIER:
      this.startUnknownStatement();
      break;
    case YangLexer.KW_input:
    case YangLexer.KW_output:
      this.startStatementWithoutIdNorArg(tok.getType());
      break;
    default:
      throw new YangPullParserException();
    }
    return this.lastEvent.getEventType();
  }

  /**
   * Returns the depth of the current statement from the root module statement.
   * 
   * @return the depth of the current statement
   */
  @Override
  public int getDepth() {
    return this.eventStack.size();
  }

  /**
   * Returns the type of the current event.
   * 
   * @return the type of the current event.
   */
  @Override
  public EventType getEventType() {
    if (this.lastEvent == null) {
      return null;
    } else {
      return this.lastEvent.getEventType();
    }
  }

  /**
   * Returns the type of the current statement.
   *
   * @return the type of the current statement
   */
  @Override
  public StatementType getStatementType() {
    if (this.lastEvent == null) {
      return null;
    } else {
      return this.lastEvent.getStatementType();
    }
  }

  /**
   * Returns the namespace of the current statement.
   * 
   * @return the namespace of the current statement
   */
  public String getNamespace() {
    if (this.lastEvent == null) {
      return null;
    } else {
      return this.lastEvent.getNamespace();
    }
  }

  /**
   * Returns the identifier of the current statement.
   * 
   * @return the identifier of the current statement
   */
  @Override
  public String getIdentifier() {
    if (this.lastEvent == null) {
      return null;
    } else {
      return this.lastEvent.getIdentifier();
    }
  }

  /**
   * Returns the argument of the current statement.
   *
   * @return the argument of the current statement.
   */
  @Override
  public String getArgument() {
    if (this.lastEvent == null) {
      return null;
    } else {
      return this.lastEvent.getArgument();
    }
  }

  /**
   * Returns the current lexical token.
   * 
   * @return the current lexical token.
   */
  protected Token getCurrentToken() {
    return this.tokenStream.LT(1);
  }

  /**
   * Consumes the current lexical token and prepares the next token in the
   * stream.
   */
  protected void consume() {
    this.tokenStream.consume();
  }

  protected void startStatement(int ruleNameIndex) throws YangPullParserException {
    assert(0 < ruleNameIndex && ruleNameIndex <= YangLexer.ruleNames.length);
    
    Event event = this.createStartStatementEvent(ruleNameIndex);
    this.consume();

    this.readIdentifier(event);

    Token nextToken = this.getCurrentToken();
    switch (nextToken.getType()) {
    case YangLexer.S_LBR:
      this.consume();
      break;
    case YangLexer.S_SEMICOLON:
      break;
    default:
      throw new YangPullParserException();
    }

    this.eventStack.push(event);
    this.lastEvent = event;
  }

  protected void startStatementWithArg(int ruleNameIndex) throws YangPullParserException {
    assert(0 < ruleNameIndex && ruleNameIndex <= YangLexer.ruleNames.length);

    Event event = this.createStartStatementEvent(ruleNameIndex);
    this.consume();

    event.setArgument(this.readArgument());

    Token nextToken = this.getCurrentToken();
    switch (nextToken.getType()) {
    case YangLexer.S_LBR:
      this.consume();
      break;
    case YangLexer.S_SEMICOLON:
      break;
    default:
      throw new YangPullParserException();

    }

    this.eventStack.push(event);
    this.lastEvent = event;
  }

  protected void startStatementWithoutIdNorArg(int ruleNameIndex) throws YangPullParserException {
    assert(0 < ruleNameIndex && ruleNameIndex <= YangLexer.ruleNames.length);

    Event event = this.createStartStatementEvent(ruleNameIndex);
    this.consume();

    Token nextToken = this.getCurrentToken();
    switch (nextToken.getType()) {
    case YangLexer.S_LBR:
      this.consume();
      break;
    case YangLexer.S_SEMICOLON:
      break;
    default:
      throw new YangPullParserException();
    }

    this.eventStack.push(event);
    this.lastEvent = event;
  }

  protected void startUnknownStatement() throws YangPullParserException {
    Event event = new Event(EventType.STATEMENT_START, StatementType.UNKNOWN);

    this.readIdentifier(event);

    Token nextToken = this.getCurrentToken();
    if (nextToken.getType() == YangLexer.IDENTIFIER || nextToken.getType() == YangLexer.STRING) {
      event.setArgument(this.readArgument());
      nextToken = this.getCurrentToken();
    }

    switch (nextToken.getType()) {
    case YangLexer.S_LBR:
      this.consume();
      break;
    case YangLexer.S_SEMICOLON:
      break;
    default:
      throw new YangPullParserException();
    }
    this.eventStack.push(event);
    this.lastEvent = event;
  }

  protected void endStatement() throws YangPullParserException {
    if (this.eventStack.isEmpty()) {
      throw new YangPullParserException();
    }

    Event startEvent = this.eventStack.pop();
    Event endEvent = new Event(EventType.STATEMENT_END, startEvent.getStatementType());
    endEvent.setNamespace(startEvent.getNamespace());
    endEvent.setIdentifier(startEvent.getIdentifier());
    endEvent.setArgument(startEvent.getArgument());

    this.lastEvent = endEvent;
    this.consume();
  }

  protected Event createStartStatementEvent(int ruleNameIndex) throws YangPullParserException {
    assert(0 < ruleNameIndex && ruleNameIndex <= YangLexer.ruleNames.length);

    StatementType statementType = YangPullParserImpl.statementTypes[ruleNameIndex - 1];
    if (statementType == null) {
      throw new YangPullParserException();
    }
    return new Event(EventType.STATEMENT_START, statementType);
  }

  protected void readIdentifier(Event event) throws YangPullParserException {
    assert(event != null);

    Token idOrNsToken = this.getCurrentToken();

    // FIXME This function does not check if the type of the token is really IDENTIFIER or STRING
    // because I saw a model using a keyword as an identifier without quotes.

    this.consume();
    Token nextToken = this.getCurrentToken();
    if (nextToken.getType() == YangLexer.S_COLON) {
      this.consume();
      Token idToken = this.getCurrentToken();
      if (idToken.getType() != YangLexer.IDENTIFIER) {
        throw new YangPullParserException();
      }
      event.setNamespace(idOrNsToken.getText());
      event.setIdentifier(idToken.getText());
      this.consume();
    } else {
      event.setNamespace(null);
      event.setIdentifier(idOrNsToken.getText());
    }
  }

  protected String readArgument() throws YangPullParserException {
    Token strToken = this.getCurrentToken();
    this.consume();

    StringBuilder sb = new StringBuilder(strToken.getText());
    out: while (true) {
      Token nextToken = this.getCurrentToken();
      switch (nextToken.getType()) {
      case YangLexer.S_PLUS:
        this.consume();
        Token nextStringToken = this.getCurrentToken();
        if (nextStringToken.getType() != YangLexer.STRING) {
          throw new YangPullParserException();
        }
        sb.append(nextStringToken.getText());
        this.consume();
        break;
      case YangLexer.S_LBR:
        break out;
      case YangLexer.S_SEMICOLON:
        break out;
      default:
        throw new YangPullParserException();
      }
    }

    return sb.toString();
  }
}
