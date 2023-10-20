package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    //TODO hva skal valuen være????

    @Override
    String typeName() {
        return "dict";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        //TODO sjekke om value et tom
        return true;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeNoneValue)) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where){
        if (!getBoolValue("", where)) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }
}
