
import static org.junit.Assert.*;
import org.junit.Test;


public class NodeTest {

   @Test (timeout=1000)
   public void testParsePostfix() { 
      String s = "(B1,C,D)A";
      Node t = Node.parsePostfix (s);
      String r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "A(B1,C,D)", r);
      s = "(((2,1)-,4)*,(69,3)/)+";
      t = Node.parsePostfix (s);
      r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "+(*(-(2,1),4),/(69,3))", r);
   }

   @Test (timeout=1000)
   public void testParsePostfixAndLeftParentheticRepresentation1() {
      String s = "((((E)D)C)B)A";
      Node t = Node.parsePostfix (s);
      String r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "A(B(C(D(E))))", r);
      s = "((C,(E)D)B,F)A";
      t = Node.parsePostfix (s);
      r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "A(B(C,D(E)),F)", r);
   }

   @Test (timeout=1000)
   public void testParsePostfixAndLeftParentheticRepresentation2() {
      String s = "(((512,1)-,4)*,(-6,3)/)+";
      Node t = Node.parsePostfix (s);
      String r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "+(*(-(512,1),4),/(-6,3))", r);
      s = "(B,C,D,E,F)A";
      t = Node.parsePostfix (s);
      r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "A(B,C,D,E,F)", r);
      s = "((1,(2)3,4)5)6";
      t = Node.parsePostfix (s);
      r = t.leftParentheticRepresentation();
       assertEquals ("Tree: " + s, "6(5(1,3(2),4))", r);
   }

   @Test (timeout=1000)
   public void testSingleRoot() {
      String s = "ABC";
      Node t = Node.parsePostfix (s);
      String r = t.leftParentheticRepresentation();
      assertEquals ("Tree: " + s, "ABC", r);
      s = ".Y.";
      t = new Node (s, null, null);
      r = t.leftParentheticRepresentation();
      assertEquals ("Single node" + s, s, r);
   }

   @Test (expected=RuntimeException.class)
   public void testSpaceInNodeName() {
      Node root = Node.parsePostfix ("A B");
   }

   @Test (expected=RuntimeException.class)
   public void testTwoCommas() {
      Node t = Node.parsePostfix ("(B,,C)A");
   }

   @Test (expected=RuntimeException.class)
   public void testEmptySubtree() {
      Node root = Node.parsePostfix ("()A");
   }

   @Test (expected=RuntimeException.class)
   public void testInputWithBracketsAndComma() {
      Node t = Node.parsePostfix ("( , ) ");
   }

   @Test (expected=RuntimeException.class)
   public void testInputWithoutBrackets() {
      Node t = Node.parsePostfix ("A,B");
   }

   @Test (expected=RuntimeException.class)
   public void testInputWithDoubleBrackets() {
      Node t = Node.parsePostfix ("((C,D))A");
   }

   @Test (expected=RuntimeException.class)
   public void testComma1() {
      Node root = Node.parsePostfix ("(,B)A");
   }

   @Test (expected=RuntimeException.class)
   public void testComma2() {
      Node root = Node.parsePostfix ("(B)A,(D)C");
   }

   @Test (expected=RuntimeException.class)
   public void testComma3() {
      Node root = Node.parsePostfix ("(B,C)A,D");
   }

   @Test (expected=RuntimeException.class)
   public void testTab1() {
      Node root = Node.parsePostfix ("\t");
   }

   @Test (expected=RuntimeException.class)
   public void testWeirdBrackets() {
      Node root = Node.parsePostfix (")A(");
   }

   @Test (timeout=1000)
   public void testXml() {
      String s = "(B,C,D)A";
      Node t = Node.parsePostfix (s);
      String x = t.represent();
      assertEquals ("New view " + s, "<L1> A \n" +
              "\t<L2> B </L2>\n" +
              "\t<L2> C </L2>\n" +
              "\t<L2> D </L2>\n" +
              "</L1>\n", x);

      s = "(((2,1)-,4)*,(69,3)/)+";
      t = Node.parsePostfix (s);
      x = t.represent();
      assertEquals ("New view " + s, "<L1> + \n" +
              "\t<L2> * \n" +
              "\t\t<L3> - \n" +
              "\t\t\t<L4> 2 </L4>\n" +
              "\t\t\t<L4> 1 </L4>\n" +
              "\t\t</L3>\n" +
              "\t\t<L3> 4 </L3>\n" +
              "\t</L2>\n" +
              "\t<L2> / \n" +
              "\t\t<L3> 69 </L3>\n" +
              "\t\t<L3> 3 </L3>\n" +
              "\t</L2>\n" +
              "</L1>\n", x);

      s = "((((E)D)C)B)A";
      t = Node.parsePostfix (s);
      x = t.represent();
      assertEquals ("New view " + s, "<L1> A \n" +
              "\t<L2> B \n" +
              "\t\t<L3> C \n" +
              "\t\t\t<L4> D \n" +
              "\t\t\t\t<L5> E </L5>\n" +
              "\t\t\t</L4>\n" +
              "\t\t</L3>\n" +
              "\t</L2>\n" +
              "</L1>\n", x);

      s = "ABC";
      t = Node.parsePostfix (s);
      x = t.represent();
      assertEquals ("New view " + s, "<L1> ABC </L1>\n", x);

      s = "(B,C,D,E,F)A";
      t = Node.parsePostfix (s);
      x = t.represent();
      assertEquals ("New view " + s, "<L1> A \n" +
              "\t<L2> B </L2>\n" +
              "\t<L2> C </L2>\n" +
              "\t<L2> D </L2>\n" +
              "\t<L2> E </L2>\n" +
              "\t<L2> F </L2>\n" +
              "</L1>\n", x);
   }

}

