package compiler;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;

import java.io.IOException;

public class mylangCustomVisitor extends mylangBaseVisitor<String>{
    CtClass ctClass;
    //CtMethod main;
    mylangCustomVisitor(CtClass ctClass) throws NotFoundException {
        this.ctClass = ctClass;
        //main = ctClass.getDeclaredMethod("main");
    }

    @Override
    public String visitProgram(mylangParser.ProgramContext ctx) {
        return "public static void main(String[] args) {\n" + visit(ctx.block()) +
                    "}\n";
    }

    @Override
    public String visitBlock(mylangParser.BlockContext ctx) {
        StringBuilder block = new StringBuilder("");
        for (var statement : ctx.statement()){
            String src = visit(statement);
            block.append(src).append("\n");
        }

        return block.toString();
    }

    @Override
    public String visitStatement(mylangParser.StatementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public String visitLet(mylangParser.LetContext ctx) {
        if (ctx.NUMBER() != null) {
            return "int " + ctx.VAR().getText() + " = " + ctx.getChild(2).getText() + ";";
        } else if (ctx.STRING() != null) {
            return "String " + ctx.VAR().getText() + " = " + ctx.getChild(2).getText() + ";";
        } else throw new IllegalStateException("No value assigned to a variable");
    }

    @Override
    public String visitShow(mylangParser.ShowContext ctx) {
        return "System.out.println(" + ctx.getChild(1).getText() + ");";
    }

    @Override
    public String visitIf_stat(mylangParser.If_statContext ctx) {
        return super.visitIf_stat(ctx);
    }

    @Override
    public String visitCondition_block(mylangParser.Condition_blockContext ctx) {
        return super.visitCondition_block(ctx);
    }

    @Override
    public String visitStatement_block(mylangParser.Statement_blockContext ctx) {
        return super.visitStatement_block(ctx);
    }

    @Override
    public String visitWhile_stat(mylangParser.While_statContext ctx) {
        return super.visitWhile_stat(ctx);
    }

    @Override
    public String visitUnaryMinusExpr(mylangParser.UnaryMinusExprContext ctx) {
        return super.visitUnaryMinusExpr(ctx);
    }

    @Override
    public String visitMultiplicationExpr(mylangParser.MultiplicationExprContext ctx) {
        return super.visitMultiplicationExpr(ctx);
    }

    @Override
    public String visitAtomExpr(mylangParser.AtomExprContext ctx) {
        return super.visitAtomExpr(ctx);
    }

    @Override
    public String visitAdditiveExpr(mylangParser.AdditiveExprContext ctx) {
        return super.visitAdditiveExpr(ctx);
    }

    @Override
    public String visitRelationalExpr(mylangParser.RelationalExprContext ctx) {
        return super.visitRelationalExpr(ctx);
    }

    @Override
    public String visitEqualityExpr(mylangParser.EqualityExprContext ctx) {
        return super.visitEqualityExpr(ctx);
    }

    @Override
    public String visitParExpr(mylangParser.ParExprContext ctx) {
        return super.visitParExpr(ctx);
    }

    @Override
    public String visitNumberAtom(mylangParser.NumberAtomContext ctx) {
        return super.visitNumberAtom(ctx);
    }

    @Override
    public String visitVarAtom(mylangParser.VarAtomContext ctx) {
        return super.visitVarAtom(ctx);
    }

    @Override
    public String visitStringAtom(mylangParser.StringAtomContext ctx) {
        return super.visitStringAtom(ctx);
    }
}
