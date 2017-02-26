yang-pullparser-java
====================

The yang-pullparser-java is nothing but a pull-style parser for [YANG models](https://en.wikipedia.org/wiki/YANG)
written in Java.

It parses YANG models basically based on [RFC 6020](https://tools.ietf.org/html/rfc6020).
[RFC 7950](https://tools.ietf.org/html/rfc7950) is currently out of scope. I think the parser can read a major
part of YANG 1.1 models because it is not so strict.

The parser fires one of the following events for each next() call.

 - END_MODULE - when the parser hits the end of the module
 - STATEMENT_START - when the parser reads a new statement
 - STATEMENT_END - when the parser reads the end of statement (a semicolon(;) or a closing curly bracket(}))

When the parser comes to a new statement, it reads a statement keyword and a string after the keyword. Whether
it takes the string as an identifier or an argument depends on the section 12 in RFC 6020. If identifier-arg-str
or identifier-ref-arg-str follows the keyword, the string is an identifier, otherwise an argument.

As of now, there is no official specification of a pull-style parser API for Yang models. The design of this
parser is just based on a personal definition.

## How to build
```
$ gradle build
```

## How to use
```Java
  YangPullParser ypp = new YangPullParserImpl(fileReader);

  while (true) {
    EventType eventType = ypp.next();
    if (eventType == EventType.END_MODULE) {
      break;
    }

     // 
  }
```

## Tools
The build.gradle creates one more jar, yang-pullparser-java-tool.jar. It contains an example program to show how to use
this library.

```
$ java -jar yang-pullparser-java-tool.jar crawl ./foo.yang
```

## Todo

 - RFC 7950
 - stream-ize

