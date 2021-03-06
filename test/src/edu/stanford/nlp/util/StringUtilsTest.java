package edu.stanford.nlp.util;

import junit.framework.TestCase;

import java.util.*;

public class StringUtilsTest extends TestCase {

  public void testTr() {
    assertEquals(StringUtils.tr("chris", "irs", "mop"), "chomp");
  }

  public void testGetBaseName() {
    assertEquals(StringUtils.getBaseName("/u/wcmac/foo.txt"), "foo.txt");
    assertEquals(StringUtils.getBaseName("/u/wcmac/foo.txt", ""), "foo.txt");
    assertEquals(StringUtils.getBaseName("/u/wcmac/foo.txt", ".txt"), "foo");
    assertEquals(StringUtils.getBaseName("/u/wcmac/foo.txt", ".pdf"), "foo.txt");
  }

  public void testArgsToProperties() {
    Properties p1 = new Properties();
    p1.setProperty("fred", "-2");
    p1.setProperty("", "joe");
    Properties p2 = new Properties();
    p2.setProperty("fred", "true");
    p2.setProperty("2", "joe");
    Map<String,Integer> argNums = new HashMap<String,Integer>();
    argNums.put("fred", 1);
    assertEquals(StringUtils.argsToProperties(new String[]{"-fred", "-2", "joe"}), p2);
    assertEquals(StringUtils.argsToProperties(new String[]{"-fred", "-2", "joe"}, argNums), p1);
  }

  public void testValueSplit() {
    List<String> vals1 = StringUtils.valueSplit("arg(a,b),foo(d,e,f)", "[a-z]*(?:\\([^)]*\\))?", "\\s*,\\s*");
    List<String> ans1 = Arrays.asList("arg(a,b)", "foo(d,e,f)");
    assertEquals("Split failed", ans1, vals1);
    vals1 = StringUtils.valueSplit("arg(a,b) , foo(d,e,f) , ", "[a-z]*(?:\\([^)]*\\))?", "\\s*,\\s*");
    assertEquals("Split failed", ans1, vals1);
    vals1 = StringUtils.valueSplit(",arg(a,b),foo(d,e,f)", "[a-z]*(?:\\([^)]*\\))?", "\\s*,\\s*");
    List<String> ans2 = Arrays.asList("", "arg(a,b)", "foo(d,e,f)");
    assertEquals("Split failed", ans2, vals1);
    List<String> vals3 = StringUtils.valueSplit("\"quoted,comma\",\"with \\\"\\\" quote\" , \"stuff\",or not,quoted,",
             "\"(?:[^\"\\\\]+|\\\\\")*\"|[^,\"]+", "\\s*,\\s*");
    List<String> ans3 = Arrays.asList("\"quoted,comma\"", "\"with \\\"\\\" quote\"", "\"stuff\"", "or not", "quoted");
    assertEquals("Split failed", ans3, vals3);
  }

  public void testLongestCommonSubstring(){
    assertEquals(12,StringUtils.longestCommonSubstring("Jo3seph Smarr!", "Joseph R Smarr"));
    assertEquals(12,StringUtils.longestCommonSubstring("Joseph R Smarr","Jo3seph Smarr!"));
  }

  public void testEditDistance() {
    // test insert
    assertEquals(4, StringUtils.editDistance("Hi!","Hi you!"));
    assertEquals(5, StringUtils.editDistance("Hi!","Hi you!?"));
    assertEquals(1, StringUtils.editDistance("sdf", "asdf"));
    assertEquals(1, StringUtils.editDistance("asd", "asdf"));
    // test delete
    assertEquals(4, StringUtils.editDistance("Hi you!","Hi!"));
    assertEquals(5, StringUtils.editDistance("Hi you!?", "Hi!"));
    assertEquals(1, StringUtils.editDistance("asdf", "asd"));
    assertEquals(1, StringUtils.editDistance("asdf", "sdf"));
    // test modification
    assertEquals(3, StringUtils.editDistance("Hi you!","Hi Sir!"));
    assertEquals(5, StringUtils.editDistance("Hi you!","Hi Sir!!!"));
    // test transposition
    assertEquals(2, StringUtils.editDistance("hello", "hlelo"));
    assertEquals(2, StringUtils.editDistance("asdf", "adsf"));
    assertEquals(2, StringUtils.editDistance("asdf", "sadf"));
    assertEquals(2, StringUtils.editDistance("asdf", "asfd"));
    // test empty
    assertEquals(0, StringUtils.editDistance("", ""));
    assertEquals(3, StringUtils.editDistance("", "bar"));
    assertEquals(3, StringUtils.editDistance("foo", ""));
  }

  public void testSplitOnChar() {
    assertEquals(3, StringUtils.splitOnChar("hello\tthere\tworld", '\t').length);
    assertEquals(2, StringUtils.splitOnChar("hello\tworld", '\t').length);
    assertEquals(1, StringUtils.splitOnChar("hello", '\t').length);

    assertEquals("hello", StringUtils.splitOnChar("hello\tthere\tworld", '\t')[0]);
    assertEquals("there", StringUtils.splitOnChar("hello\tthere\tworld", '\t')[1]);
    assertEquals("world", StringUtils.splitOnChar("hello\tthere\tworld", '\t')[2]);

    assertEquals(1, StringUtils.splitOnChar("hello\tthere\tworld\n", ' ').length);
    assertEquals("hello\tthere\tworld\n", StringUtils.splitOnChar("hello\tthere\tworld\n", ' ')[0]);

    assertEquals(5, StringUtils.splitOnChar("a\tb\tc\td\te", '\t').length);
    assertEquals(5, StringUtils.splitOnChar("\t\t\t\t", '\t').length);
    assertEquals("", StringUtils.splitOnChar("\t\t\t\t", '\t')[0]);
    assertEquals("", StringUtils.splitOnChar("\t\t\t\t", '\t')[1]);
    assertEquals("", StringUtils.splitOnChar("\t\t\t\t", '\t')[4]);

  }

  public void testNormalize() {
    assertEquals("can't", StringUtils.normalize("can't"));
    assertEquals("Beyonce", StringUtils.normalize("Beyoncé"));
    assertEquals("krouzek", StringUtils.normalize("kroužek"));
    assertEquals("office", StringUtils.normalize("o\uFB03ce"));
    assertEquals("DZ", StringUtils.normalize("Ǆ"));
    assertEquals("1⁄4", StringUtils.normalize("¼"));
    assertEquals("한국어", StringUtils.normalize("한국어"));
    assertEquals("조선말", StringUtils.normalize("조선말"));
    assertEquals("が", StringUtils.normalize("が"));
    assertEquals("か", StringUtils.normalize("か"));
  }
}
