import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongStack {

   public static void main(String[] argum) {
      // TODO!!! Your tests here!
   }

   private LinkedList<Long> list;

   LongStack() {
      this.list = new LinkedList<>();
   }

   LongStack(LinkedList<Long> list) {
      this.list = list;
   }

   @Override
   public Object clone() throws CloneNotSupportedException {

      LinkedList<Long> newlist = new LinkedList<>(list);

      return new LongStack(newlist);
   }

   public boolean stEmpty() {
      return list.size() == 0;
   }

   public void push(long a) {
      list.add(a);
   }

   public long pop() {
      if(!stEmpty() && list.peekLast() != null) {
         return list.remove(list.size() - 1);
      }

      throw new RuntimeException("Empty stack!");
   }

   public void op(String s) {
      if (list.size() < 2) {
         throw new RuntimeException("Not enough numbers for " + s);
      }

      long prelast = pop();
      long last = pop();

      switch (s) {
         case "+":
            push(last + prelast);
            break;
         case "-":
            push(last - prelast);
            break;
         case "/":
            push(last / prelast);
            break;
         case "*":
            push(last * prelast);
            break;
         default:
            throw new RuntimeException("Invalid operation " + s);
      }
   }

   public long tos() {
      if (list.size() == 0) {
         throw new RuntimeException("Empty stack!");
      } else {
         return list.getLast();
      }
   }

   @Override
   public boolean equals(Object o) {
      if (stEmpty() && ((LongStack) o).stEmpty()) {
         return true;
      }
      if (list.size() == ((LongStack) o).list.size()) {
         for (int i = 0; i < list.size(); i++)
            if (!((LongStack) o).list.get(i).equals(list.get(i)))
               return false;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return String.valueOf(list);
   }

   public static long interpret(String pol) {
      String[] her = pol.split(" ");
      LongStack result = new LongStack();
      long res = 0L;

      for (String el : her) {


         String lastElOfEl = "";
         if (el.length()> 0){
            lastElOfEl = el.split("")[el.length()-1];
         }
         if (el.equals("SWAP")) {

            if (result.list.size() < 2)
               throw new RuntimeException(String.format("Cannot perform %s in expression %s", el, pol));

            long a = result.pop();
            long b = result.pop();

            result.push(a);
            result.push(b);
            continue;
         }
         if (el.equals("ROT")) {

            if (result.list.size() < 3)
               throw new RuntimeException(String.format("Cannot perform %s in expression %s", el, pol));

            long a = result.pop();
            long b = result.pop();
            long c = result.pop();

            result.push(b);
            result.push(a);
            result.push(c);
            continue;
         }
         if (el.trim().equals("")){
            continue;
         }
         else if (Character.isDigit(lastElOfEl.charAt(0))) {
            result.push(Integer.parseInt(el.trim()));
         } else {
            result.op(el.trim());
         }
      }

      if (result.stEmpty()) {
         throw new RuntimeException("Empty expression");
      } else if (result.list.size() > 1) {
         throw new RuntimeException(String.format("Too many numbers in expression, %s", pol));
      } else {
         res += result.list.getLast();
      }
      return res;

   }

}