package compiler;
import java.util.HashMap;

public class mylangCustomListener extends mylangBaseListener{
    HashMap<String, Integer> variableMap = new HashMap<>();

    @Override
    public void exitShow(mylangParser.ShowContext ctx) {
        if(ctx.NUMBER() != null){
            System.out.println(ctx.NUMBER().getText());
        }
        else if(ctx.VAR() != null){
            System.out.println(this.variableMap.get(ctx.VAR().getText()));
        }
    }

    @Override
    public void exitLet(mylangParser.LetContext ctx) {
        this.variableMap.put(ctx.VAR().getText(),
                Integer.parseInt(ctx.NUMBER().getText()));
    }
}
