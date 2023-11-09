package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspFuncDef;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFuncValue extends RuntimeValue {
    AspFuncDef def;
    RuntimeScope defScope;
    String name;

    @Override
    String typeName() {
        return "Func";
    }
 
    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actPars, AspSyntax where) {
        /*
         * 1. sjekke antall parametre
         * 2. Oprette nytt skop
         * 3. Initiere parametrene
         * 4. Utføre funksjonen
         */
        return null;
    }
}
