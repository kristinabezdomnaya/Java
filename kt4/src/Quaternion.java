import java.util.*;

/** Quaternions. Basic operations. */
public class Quaternion {
   private final double a;
   private final double i;
   private final double j;
   private final double k;
   private static double constant = 0.0001;


   /** Constructor from four double values.
    * @param a real part
    * @param b imaginary part i
    * @param c imaginary part j
    * @param d imaginary part k
    */
   public Quaternion (double a, double b, double c, double d) {
      this.a = a;
      this.i = b;
      this.j = c;
      this.k = d;
   }

   /** Real part of the quaternion.
    * @return real part
    */
   public double getRpart() {
      return this.a;
   }

   /** Imaginary part i of the quaternion. 
    * @return imaginary part i
    */
   public double getIpart() {
      return this.i;
   }

   /** Imaginary part j of the quaternion. 
    * @return imaginary part j
    */
   public double getJpart() {
      return this.j;
   }

   /** Imaginary part k of the quaternion. 
    * @return imaginary part k
    */
   public double getKpart() {
      return this.k;
   }

   /** Conversion of the quaternion to the string.
    * @return a string form of this quaternion: 
    * "a+bi+cj+dk"
    * (without any brackets)
    */
   @Override
   public String toString() {

      String res =String.format("%s+%si+%sj+%sk", a, i, j, k);
      res = res.replace("+-", "-");
      res = res.replace("--", "+");
      res = res.replace("-+", "-");
      res = res.replace("+0.0i", "");
      res = res.replace("+0.0j", "");
      res = res.replace("+0.0k", "");

      return res;
   }

   /** Conversion from the string to the quaternion. 
    * Reverse to <code>toString</code> method.
    * @throws IllegalArgumentException if string s does not represent 
    *     a quaternion (defined by the <code>toString</code> method)
    * @param s string of form produced by the <code>toString</code> method
    * @return a quaternion represented by string s
    */
   public static Quaternion valueOf (String s) {

      if (s.equals("")) {
         throw new IllegalArgumentException("Quternion is empty!");
      }

      s = s.replace("--", "+");
      s = s.replace("-", "+-");

      String[] res = s.split("\\+");

      String[] withoutSpaces = new String[4];

      for (int i = 0; i < res.length ; i++) {
         if (!res[i].equals("") && !res[i].contains("i") && !res[i].contains("j") && !res[i].contains("k")){
            if (withoutSpaces[0] == null){
               withoutSpaces[0] = res[i];
            }else {
               throw new IllegalArgumentException("Invalid Quaterion!" + s);
            }

         }
         if (res[i].contains("i")){
            if (withoutSpaces[1] == null){
               withoutSpaces[1] = res[i];
            }else {
               throw new IllegalArgumentException("Invalid Quaterion!" + s);
            }
         }
         if (res[i].contains("j")){
            if (withoutSpaces[2] == null){
               withoutSpaces[2] = res[i];
            }else {
               throw new IllegalArgumentException("Invalid Quaterion!" + s);
            }
         }
         if (res[i].contains("k")){
            if (withoutSpaces[3] == null){
               withoutSpaces[3] = res[i];
            }else {
               throw new IllegalArgumentException("Invalid Quaterion!" + s);
            }
         }

      }

      for (int i=0; i < withoutSpaces.length; i++) {
         if (withoutSpaces[i] == null) {
            if (i == 0) {
               withoutSpaces[i] = "0.0";
            } else if (i == 1) {
               withoutSpaces[i] = "0.0i";
            } else if (i == 2) {
               withoutSpaces[i] = "0.0j";
            } else if (i == 3) {
               withoutSpaces[i] = "0.0k";
            }
         }
      }
      for (String o: withoutSpaces) {
         System.out.println(o);
      }

      if ((withoutSpaces[0].contains("i") || withoutSpaces[0].contains("j") || withoutSpaces[0].contains("k"))) {
         throw new IllegalArgumentException("Invalid Quaternion" + s);
      } else if ((!withoutSpaces[1].contains("i") || withoutSpaces[1].contains("ii") || withoutSpaces[1].contains("j") || withoutSpaces[1].contains("k"))) {
         throw new IllegalArgumentException("Invalid Quaternion" + s);
      } else if ((!withoutSpaces[2].contains("j") || withoutSpaces[2].contains("jj") || withoutSpaces[2].contains("i") || withoutSpaces[2].contains("k"))) {
         throw new IllegalArgumentException("Invalid Quaternion" + s);
      } else if ((!withoutSpaces[3].contains("k") || withoutSpaces[3].contains("kk") || withoutSpaces[3].contains("i") || withoutSpaces[3].contains("j"))) {
         throw new IllegalArgumentException("Invalid Quaternion" + s);
      }

      double a = Double.parseDouble(withoutSpaces[0]);
      double b = Double.parseDouble(withoutSpaces[1].substring(0, withoutSpaces[1].length() - 1));
      double c = Double.parseDouble(withoutSpaces[2].substring(0, withoutSpaces[2].length() - 1));
      double d = Double.parseDouble(withoutSpaces[3].substring(0, withoutSpaces[3].length() - 1));

      return new Quaternion(a, b, c, d);

   }

   /** Clone of the quaternion.
    * @return independent clone of <code>this</code>
    */
   @Override
   public Object clone() throws CloneNotSupportedException {

      return new Quaternion(a, i, j, k);
   }

   /** Test whether the quaternion is zero. 
    * @return true, if the real part and all the imaginary parts are (close to) zero
    */
   public boolean isZero() {
      return doubleCompare(0, a) && doubleCompare(0, i) && doubleCompare(0, j) && doubleCompare(0, k);
   }

   public static boolean doubleCompare(double a, double b){
      return Math.abs(a - b) < constant;
   }

   /** Conjugate of the quaternion. Expressed by the formula 
    *     conjugate(a+bi+cj+dk) = a-bi-cj-dk
    * @return conjugate of <code>this</code>
    */
   public Quaternion conjugate() {

      return new Quaternion(a, -i, -j, -k);
   }

   /** Opposite of the quaternion. Expressed by the formula 
    *    opposite(a+bi+cj+dk) = -a-bi-cj-dk
    * @return quaternion <code>-this</code>
    */
   public Quaternion opposite() {
      return new Quaternion(-a, -i, -j, -k);
   }

   /** Sum of quaternions. Expressed by the formula 
    *    (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
    * @param q addend
    * @return quaternion <code>this+q</code>
    */
   public Quaternion plus (Quaternion q) {
      return new Quaternion(a + q.a, i + q.i, j + q.j, k + q.k);
   }

   /** Product of quaternions. Expressed by the formula
    *  (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
    *  (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
    * @param q factor
    * @return quaternion <code>this*q</code>
    */
   public Quaternion times (Quaternion q) {
      double a1 = getRpart();
      double b1 = getIpart();
      double c1 = getJpart();
      double d1 = getKpart();
      double a2 = q.a;
      double b2 = q.i;
      double c2 = q.j;
      double d2 = q.k;

      double a = a1*a2-b1*b2-c1*c2-d1*d2;
      double i = a1*b2+b1*a2+c1*d2-d1*c2;
      double j = a1*c2-b1*d2+c1*a2+d1*b2;
      double k = a1*d2+b1*c2-c1*b2+d1*a2;

      return new Quaternion(a, i, j, k);
   }

   /** Multiplication by a coefficient.
    * @param r coefficient
    * @return quaternion <code>this*r</code>
    */
   public Quaternion times (double r) {
      return new Quaternion(a * r, i * r, j * r, k * r);
   }

   /** Inverse of the quaternion. Expressed by the formula
    *     1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) + 
    *     ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
    * @return quaternion <code>1/this</code>
    */
   public Quaternion inverse() {
      if (a == 0 && i == 0 && j == 0 && k == 0) {
         throw new RuntimeException("Division by zero is prohibited!");
      }
      Double x = (getRpart()*getRpart()) + (getIpart()*getIpart()) + (getJpart()*getJpart()) + (getKpart()*getKpart());
      return new Quaternion(getRpart()/x, (-getIpart())/x, (-getJpart())/x, (-getKpart())/x);
   }

   /** Difference of quaternions. Expressed as addition to the opposite.
    * @param q subtrahend
    * @return quaternion <code>this-q</code>
    */
   public Quaternion minus (Quaternion q) {
      return this.plus(q.opposite());
   }

   /** Right quotient of quaternions. Expressed as multiplication to the inverse.
    * @param q (right) divisor
    * @return quaternion <code>this*inverse(q)</code>
    */
   public Quaternion divideByRight (Quaternion q) {
       if (isZero()) {
           throw new RuntimeException("Division by zero is prohibited!");
       }
      return this.times(q.inverse());
   }

   /** Left quotient of quaternions.
    * @param q (left) divisor
    * @return quaternion <code>inverse(q)*this</code>
    */
   public Quaternion divideByLeft (Quaternion q) {
       if (isZero()) {
           throw new RuntimeException("Division by zero is prohibited!");
       }
      return q.inverse().times(this);
   }
   
   /** Equality test of quaternions. Difference of equal numbers
    *     is (close to) zero.
    * @param qo second quaternion
    * @return logical value of the expression <code>this.equals(qo)</code>
    */
   @Override
   public boolean equals (Object qo) {
      if(qo instanceof Quaternion) {
         return doubleCompare(a, ((Quaternion) qo).a) && doubleCompare(i, ((Quaternion) qo).i) && doubleCompare(j, ((Quaternion) qo).j) && doubleCompare(k, ((Quaternion) qo).k);
      }
      return false;
   }

   /** Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
    * @param q factor
    * @return dot product of this and q
    */
   public Quaternion dotMult (Quaternion q) {
      return this.times(q.conjugate()).plus(q.times(this.conjugate())).times(0.5);
   }

   /** Integer hashCode has to be the same for equal objects.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return Objects.hash(a, i, j, k);
   }

   /** Norm of the quaternion. Expressed by the formula 
    *     norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
    * @return norm of <code>this</code> (norm is a real number)
    */
   public double norm() {
      return Math.sqrt(a*a+i*i+j*j+k*k);
   }

   /** Main method for testing purposes. 
    * @param arg command line parameters
    */
   public static void main (String[] arg) {

      System.out.println(valueOf("-1i-9j+3i"));
    /*  Quaternion arv1 = new Quaternion (-1., 1, 2., -2.);
      if (arg.length > 0)
         arv1 = valueOf (arg[0]);
      System.out.println ("first: " + arv1.toString());
      System.out.println ("real: " + arv1.getRpart());
      System.out.println ("imagi: " + arv1.getIpart());
      System.out.println ("imagj: " + arv1.getJpart());
      System.out.println ("imagk: " + arv1.getKpart());
      System.out.println ("isZero: " + arv1.isZero());
      System.out.println ("conjugate: " + arv1.conjugate());
      System.out.println ("opposite: " + arv1.opposite());
      System.out.println ("hashCode: " + arv1.hashCode());
      Quaternion res = null;
      try {
         res = (Quaternion)arv1.clone();
      } catch (CloneNotSupportedException e) {};
      System.out.println ("clone equals to original: " + res.equals (arv1));
      System.out.println ("clone is not the same object: " + (res!=arv1));
      System.out.println ("hashCode: " + res.hashCode());
      res = valueOf (arv1.toString());
      System.out.println ("string conversion equals to original: " 
         + res.equals (arv1));
      Quaternion arv2 = new Quaternion (1., -2.,  -1., 2.);
      if (arg.length > 1)
         arv2 = valueOf (arg[1]);
      System.out.println ("second: " + arv2.toString());
      System.out.println ("hashCode: " + arv2.hashCode());
      System.out.println ("equals: " + arv1.equals (arv2));
      res = arv1.plus (arv2);
      System.out.println ("plus: " + res);
      System.out.println ("times: " + arv1.times (arv2));
      System.out.println ("minus: " + arv1.minus (arv2));
      double mm = arv1.norm();
      System.out.println ("norm: " + mm);
      System.out.println ("inverse: " + arv1.inverse());
      System.out.println ("divideByRight: " + arv1.divideByRight (arv2));
      System.out.println ("divideByLeft: " + arv1.divideByLeft (arv2));
      System.out.println ("dotMult: " + arv1.dotMult (arv2));*/
   }
}
// https://gist.github.com/mxrguspxrt/4044808
