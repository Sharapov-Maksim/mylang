package compiler;

import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyLang {


    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromFileName("src/main/resources/program.myl");

            //ClassFile cf = new ClassFile(false, "Program", null);
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass("Program");


            /*mylangLexer lexer = new mylangLexer(input);
            mylangParser parser = new mylangParser(new CommonTokenStream(lexer));
            parser.addParseListener(new mylangCustomListener());
            parser.program();*/

            mylangLexer lexer = new mylangLexer(input);
            mylangParser parser = new mylangParser(new CommonTokenStream(lexer));

            ParseTree tree = parser.program();

            /*String src = """
                            public static void main(String[] args) {
                                int a = 100;
                                System.out.println(a);
                            }""";*/
            String src = new mylangCustomVisitor(ctClass).visit(tree);
            CtMethod mainMethod = CtNewMethod.make(src, ctClass);
            ctClass.addMethod(mainMethod);
            ctClass.writeFile("src/main/resources");
            //cf.write(new DataOutputStream(new FileOutputStream("src/main/resources/Program.class")));
        } catch (IOException | CannotCompileException | NotFoundException ex) {
            ex.printStackTrace();
        }


    }
}
