import java.util.*;

public class Node {

    private String name;
    private Node firstChild;
    private Node nextSibling;

    public Node(String n, Node d, Node r) {
        this.name = n;
        this.firstChild = d;
        this.nextSibling = r;
    }


    public static Node parsePostfix(String s) {

        if (s.contains("()"))
            throw new RuntimeException("Incorrect input form: " + s + " empty brackets");
        if (!(s.contains("(") || s.contains(")")) && s.contains(","))
            throw new RuntimeException("Incorrect input form: " + s + " invalid sibling");
        if(s.contains("((") && s.contains("))") || s.contains(",,") || s.contains("(,"))
            throw new RuntimeException("Incorrect input form: " + s + " invalid sibling");


        for (char el : s.toCharArray()) {
            if (String.valueOf(el).equals(" ")) {
                throw new RuntimeException();
            }
        }
        if (s.indexOf(")") < s.indexOf("(")) {
            throw new RuntimeException();
        }

        StringBuffer w = new StringBuffer();

        int leftbracket = 0;
        int rightbracket = 0;
        int deep = 0;

        for (char el : s.toCharArray()) {
            if (!String.valueOf(el).equals(")") && !String.valueOf(el).equals("(") && !String.valueOf(el).equals(",")) {
                w.append(el);
            }

            if (String.valueOf(el).equals("(")) {
                deep++;
                leftbracket++;
            }
            if (String.valueOf(el).equals(")")) {
                deep--;
                rightbracket++;
            }
            if (deep == 0 && String.valueOf(el).equals(",")) {
                throw new RuntimeException("Incorrect input form: " + s + " (Invalid Node)");
            }
        }

        if (leftbracket != rightbracket) {
            throw new RuntimeException();
        }

        if (w.toString().trim().equals("")) {
            throw new RuntimeException();
        }


        s = s.replace(")", "!)!");
        s = s.replace("(", "!(!");
        s = s.replace(",", "!,!");
        s = s.replace("!!", "!");

        char[] c = s.toCharArray();
        if (String.valueOf(c[0]).equals("!")) {
            s = s.substring(1);
        }

        String[] elements = s.split("!");

        return insertNode(elements);
    }

    private static Node insertNode(String[] elements) {

        String name = "";
        Node firstchild = null;
        Node nextsibling = null;

        for (int i = 0; i < elements.length; i++) {
            if (!"(), ".contains(elements[i])) {
                name = elements[i];
            }

            if (elements[i].equals("(")) {

                int deep = 0;

                for (int j = i; j < elements.length; j++) {
                    if (elements[j].equals("(")) {
                        deep++;
                    }
                    if (elements[j].equals(")")) {
                        deep--;
                    }
                    if (deep == 0) {
                        firstchild = insertNode(Arrays.copyOfRange(elements, i + 1, j));
                        i = j;
                        break;
                    }
                }
            }
            if (elements[i].equals(",")) {
                nextsibling = insertNode(Arrays.copyOfRange(elements, i + 1, elements.length));
                break;
            }
        }

        return new Node(name, firstchild, nextsibling);
    }


    public String leftParentheticRepresentation() {

        String returnString = this.name;

        StringBuffer x = new StringBuffer();

        x.append(returnString);
        if (this.firstChild != null) {
            x.append("(");
            x.append(this.firstChild.leftParentheticRepresentation());
            x.append(")");
        }
        if (this.nextSibling != null) {
            x.append(",");
            x.append(this.nextSibling.leftParentheticRepresentation());
        }

        return x.toString();
    }

    public String represent() {
        StringBuilder res = new StringBuilder();
        tagRepresentationXML(this, res,1);

        return res.toString();
    }

    public void tagRepresentationXML(Node subNode, StringBuilder result, int d) {
        result.append(String.format("<L%s> %s ", d, subNode.name));

        if (subNode.firstChild != null) {
            result.append("\n");
            d++;
            tagRepresentationXML(subNode.firstChild, result, d);
            d--;
            result.append("\t".repeat(d - 1));
            result.append(String.format("<L/%s>\n", d));
        } else {
            result.append(String.format("</L%s>\n", d));
        }

        if (subNode.nextSibling != null) {
            tagRepresentationXML(subNode.nextSibling, result, d);
        }

    }

    public static void main(String[] param) {
        String s = "(B,C)A";
        Node t = Node.parsePostfix(s);
        String v = t.leftParentheticRepresentation();
        String x = t.represent();
        System.out.println(x);
        System.out.println(s + " ==> " + v); // (B1,C)A ==> A(B1,C)
    }
}

