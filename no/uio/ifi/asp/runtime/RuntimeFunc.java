package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    public RuntimeFunc(String string) {
        name = string;
    }

    @Override
    String typeName() {
        return "Func";
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actPars, AspSyntax where) {
        if (actPars.size() == def.nameList.size()) {
            RuntimeScope newScope = new RuntimeScope(defScope);
            for (int i = 0; i < actPars.size(); i++) {
                newScope.assign(def.nameList.get(i).value, actPars.get(i));
            }

            try {
                def.suite.eval(newScope);
            } catch (RuntimeReturnValue e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
