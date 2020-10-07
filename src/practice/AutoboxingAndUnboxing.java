package practice;

public class AutoboxingAndUnboxing {
    boolean b;
    int i;
    char c;
    String s;

    private final static AutoboxingAndUnboxing singleton = new AutoboxingAndUnboxing();
    private AutoboxingAndUnboxing() {}

    int compareTo(int a, int b) {
        return a < b ? 1:-1;
    }

    public static void main(String[] args) {
        AutoboxingAndUnboxing myClass = new AutoboxingAndUnboxing();

        System.out.println("default value:");
        System.out.println("boolean : " + myClass.b);
        System.out.println("int : " + myClass.i);
        System.out.println("char : " + myClass.c);
        System.out.println("if char c = '\\u0000' : " + (myClass.c == '\u0000'));
        System.out.println("String : " + myClass.s);

        System.out.println("~~~~~~~~~~~~~~");
        int five = 5;
        Integer a = 5;
        Integer b = 5;
        Integer c = new Integer(5);
        Integer d = 128;
        Integer e = 128; // -128 ~ 127

        System.out.println(a == five);
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(d == e);
        System.out.println(d == a);

        System.out.println("~~~~~~~~~~~~~~");
        String s1 = "abc";
        String s2 = "abc";
        String s3 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);


        System.out.println("~~~~~~~~~~~~~~");
        Integer i1 = null;
        Integer i2 = 1;
        // if (i1 == i2) or > or <
        // 会把i1先转成primitive type, 所以会unboxing null, 导致NPE

        char[] asd = {1,2,3};
        System.out.println(asd);

        float v = 1.8f;

        int n = 70;
        double p = 1.0;
        for (int i = 1; i < n; i++) {
            p = p * (365 - i) * 1.0 / 365.0;
        }
        System.out.println(1.0 - p);
    }
}
