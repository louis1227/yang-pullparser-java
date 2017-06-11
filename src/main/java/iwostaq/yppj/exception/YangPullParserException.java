package iwostaq.yppj.exception;

import iwostaq.yppj.Message;

public class YangPullParserException extends Exception {

  /**
   * Default constructor.
   */
  public YangPullParserException() {
    super();
  }

  /**
   * Constructor.
   * 
   * @param messageId the message ID
   * @param args argument array for the message
   */
  public YangPullParserException(String messageId, Object... args) {
    super(Message.getById(messageId, args));
  }
}
