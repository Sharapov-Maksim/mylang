package compiler;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;
import org.stringtemplate.v4.ST;

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
        String type = ctx.TYPE().getText().equals("int") ? "Integer " : "String ";
        return type + ctx.VAR().getText() + " = " + visit(ctx.expr()) + ";";
    }

    @Override
    public String visitAssign(mylangParser.AssignContext ctx) {
        return ctx.VAR().getText() + " = " + visit(ctx.expr()) + ";";
    }

    @Override
    public String visitShow(mylangParser.ShowContext ctx) {
        return "System.out.println(" + visit(ctx.expr()) + ");";
    }

    @Override
    public String visitIf_stat(mylangParser.If_statContext ctx) {
        StringBuilder stringBuilder = new StringBuilder();
        for(var node : ctx.children) {
            switch (node.getText()) {
                case "if", "else" -> {
                    stringBuilder.append(node.getText());
                }
                default -> {
                    stringBuilder.append(visit(node));
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String visitCondition_block(mylangParser.Condition_blockContext ctx) {
        return "(" + visit(ctx.expr()) + ")" +
                visit(ctx.statement_block());
    }

    @Override
    public String visitStatement_block(mylangParser.Statement_blockContext ctx) {
        if (ctx.statement() != null)
            // one statement after if() or else
            return "\n" + visit(ctx.statement());
        else
            return "{\n" + visit(ctx.block()) + "\n}";
    }

    @Override
    public String visitWhile_stat(mylangParser.While_statContext ctx) {
        return "while(" + visit(ctx.expr()) + ")" +
                visit(ctx.statement_block());
    }

    @Override
    public String visitUnaryMinusExpr(mylangParser.UnaryMinusExprContext ctx) {
        return "unaryMinus(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitMultiplicationExpr(mylangParser.MultiplicationExprContext ctx) {
                String operation = ctx.MULT() != null ? "multInt(" : "divInt(";
        return operation + visit(ctx.expr(0)) + ", " + visit(ctx.expr(1)) + ")";
    }

    @Override
    public String visitAtomExpr(mylangParser.AtomExprContext ctx) {
        return visit(ctx.atom());
    }

    @Override
    public String visitAdditiveExpr(mylangParser.AdditiveExprContext ctx) {
        String operation = ctx.PLUS() != null ? "sumInt(" : "subInt(";
        return operation + visit(ctx.expr(0)) + ", " + visit(ctx.expr(1)) + ")";
    }

    @Override
    public String visitRelationalExpr(mylangParser.RelationalExprContext ctx) {
        String operation;
        switch (ctx.op.getType()) {
            case mylangParser.GT -> {
                operation = "gtInt(";
            }
            case mylangParser.LT -> {
                operation = "ltInt(";
            }
            case mylangParser.GTEQ -> {
                operation = "gteqInt(";
            }
            case mylangParser.LTEQ -> {
                operation = "lteqInt(";
            }
            default -> {
                throw new IllegalStateException("Invalid relational expression");
            }
        }
        return operation + visit(ctx.expr(0)) + ", " + visit(ctx.expr(1)) + ")";
    }

    @Override
    public String visitEqualityExpr(mylangParser.EqualityExprContext ctx) {
        String operation = ctx.EQ() != null ? "eq(" : "neq(";
        return operation + visit(ctx.expr(0)) + ", " + visit(ctx.expr(1)) + ")";
    }

    @Override
    public String visitParExpr(mylangParser.ParExprContext ctx) {
        return "(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitNumberAtom(mylangParser.NumberAtomContext ctx) {
        return "Integer.valueOf(" + ctx.NUMBER().getText() + ")";
    }

    @Override
    public String visitVarAtom(mylangParser.VarAtomContext ctx) {
        return ctx.VAR().getText();
    }

    @Override
    public String visitStringAtom(mylangParser.StringAtomContext ctx) {
        return ctx.STRING().getText();
    }
}
