package iwostaq.yppj;

import static org.junit.Assert.*;

import java.io.FileReader;

import org.junit.Test;

import iwostaq.yppj.YangPullParser.StatementType;

public class YppTest02Body {

  @Test
  public void parseExtension() {
    try (FileReader fromFile = Util.getFileReader("testmodule-02extension.yang")) {
      YangPullParser ypp = new YangPullParserImpl(fromFile);

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.MODULE, null, "testmodule-02extension");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-01");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-02");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-01");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-02");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-03");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-02");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-02");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-03");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-04");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-03");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.YIN_ELEMENT, "true");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.YIN_ELEMENT, "true");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-03");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-04");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-05");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-04");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.YIN_ELEMENT, "false");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.YIN_ELEMENT, "false");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.ARGUMENT, "arg-04");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-05");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-06");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.DESCRIPTION, "desc-01");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.DESCRIPTION, "desc-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-06");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.EXTENSION, null, "ext-07");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.REFERENCE, "ref-01");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.REFERENCE, "ref-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.EXTENSION, null, "ext-07");

      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.MODULE, null, "testmodule-02extension");

    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void parseIdentity() {
    try (FileReader fromFile = Util.getFileReader("testmodule-02identity.yang")) {
      YangPullParser ypp = new YangPullParserImpl(fromFile);

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.MODULE, null, "testmodule-02identity");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-01");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-02");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-02");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-03");
      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.BASE, null, "base-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.BASE, null, "base-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-03");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-04");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.STATUS, "current");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.STATUS, "current");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-04");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-05");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.DESCRIPTION, "desc-01");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.DESCRIPTION, "desc-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-05");

      ypp.next();
      Util.assertStartStatementWithId(ypp, StatementType.IDENTITY, null, "id-06");
      ypp.next();
      Util.assertStartStatementWithStringArg(ypp, StatementType.REFERENCE, "ref-01");
      ypp.next();
      Util.assertEndStatementWithStringArg(ypp, StatementType.REFERENCE, "ref-01");
      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.IDENTITY, null, "id-06");

      ypp.next();
      Util.assertEndStatementWithId(ypp, StatementType.MODULE, null, "testmodule-02identity");
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
