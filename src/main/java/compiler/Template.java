package compiler;

public class Template {
    public static Integer unaryMinus (Integer a) {
        return -a;
    }
    public static Integer sumInt (Integer a, Integer b) {
        return a + b;
    }
    public static Integer subInt (Integer a, Integer b) {
        return a - b;
    }
    public static Integer multInt (Integer a, Integer b) {
        return a * b;
    }
    public static Integer divInt (Integer a, Integer b) {
        return a / b;
    }
    public static boolean eq (Object a, Object b) {
        return a.equals(b);
    }
    public static boolean neq (Object a, Object b) {
        return !a.equals(b);
    }
    public static boolean gtInt (Integer a, Integer b) {
        return a > b;
    }
    public static boolean ltInt (Integer a, Integer b) {
        return a < b;
    }
    public static boolean gteqInt (Integer a, Integer b) {
        return a >= b;
    }
    public static boolean lteqInt (Integer a, Integer b) {
        return a <= b;
    }




}
