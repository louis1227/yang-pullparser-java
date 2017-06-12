package iwostaq.yppj.tool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import iwostaq.yppj.exception.YangPullParserException;

public final class AppMain {

  private static final String[] HELP_MESSAGES = new String[] {
      "usage: java -jar yang-pull-parser-tool.jar <command> [<arg>]",
      "",
      "COMMAND:",
      "",
      "help",
      "\tDisplays this messages",
      "crawl <file path>",
      "\tcrawls a YANG file given as the argument.",
      "enum <file path>",
      "\tenumerates all the names appeared in the YANG model."
  };

  public static void main(String[] args) throws IOException, YangPullParserException {
    if (args.length < 1) {
      AppMain.help();
      throw new IllegalArgumentException("no arg");
    }

    switch (args[0]) {
    case "crawl":
      yangCrawler(args[1]);
      break;
    case "name":
      enumerateNames(args[1]);
      break;
    default:
      AppMain.help();
      System.exit(0);
    }
  }

  /**
   * Prints help messages in the standard output.
   */
  private static void help() {
    for (String helpMessage : AppMain.HELP_MESSAGES) {
      System.out.println(helpMessage);
    }
  }

  /**
   * Calls the YangCrawler.
   */
  private static void yangCrawler(String filePath) throws IOException, YangPullParserException {
    try (Reader fromReader = new FileReader(filePath)) {
      YangCrawler yangCrawler = new YangCrawler(fromReader);
      yangCrawler.crawl();
    }
  }

  private static void enumerateNames(String filePath) throws IOException, YangPullParserException {
    try (Reader fromReader = new FileReader(filePath)) {
      NameEnumerator enumerator = new NameEnumerator(fromReader);
      enumerator.crawl();
    }
  }
}
