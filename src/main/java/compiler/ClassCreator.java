package compiler;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

public class ClassCreator {

    public CtClass createClass() throws CannotCompileException, ClassNotFoundException {
        // creating new class
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("MyClass");
        cc.toClass(this.getClass().getClassLoader(), this.getClass().getProtectionDomain());
        Class.forName("MyClass");
        return cc;
    }
}
