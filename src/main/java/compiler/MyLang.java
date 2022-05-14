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
            var c = Template.class;
            CharStream input = CharStreams.fromFileName("src/main/resources/program.myl");

            //ClassFile cf = new ClassFile(false, "Program", null);
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.getCtClass("compiler.Template");
            ctClass.setName("Program");

            /*mylangLexer lexer = new mylangLexer(input);
            mylangParser parser = new mylangParser(new CommonTokenStream(lexer));
            parser.addParseListener(new mylangCustomListener());
            parser.program();*/

            mylangLexer lexer = new mylangLexer(input);
            mylangParser parser = new mylangParser(new CommonTokenStream(lexer));

            ParseTree tree = parser.program();
            /*String src = """
                            public static void main(String[] args) {
                                Integer a = Integer.valueOf(100);
                                Integer b = Integer.valueOf(200);
                                Boolean c = eq(a, b);
                                System.out.println(c);
                            }""";*/
            String src = new mylangCustomVisitor(ctClass).visit(tree);
            System.out.println("Generated Java source:");
            System.out.println(src);

            CtMethod mainMethod = CtNewMethod.make(src, ctClass);
            ctClass.addMethod(mainMethod);
            ctClass.writeFile("src/main/resources");
            //cf.write(new DataOutputStream(new FileOutputStream("src/main/resources/Program.class")));
        } catch (IOException | CannotCompileException | NotFoundException ex) {
            ex.printStackTrace();
        }


    }
}
