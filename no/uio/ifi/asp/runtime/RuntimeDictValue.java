package no.uio.ifi.asp.runtime;

import java.util.HashMap;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    HashMap<String, RuntimeValue> value = new HashMap<>();
    
    public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
        value = v;
    }
    @Override
    String typeName() {
        return "dict";
    }

    @Override
    public String showInfo() {
        return value.toString();
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        if (value.isEmpty()) return false;
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
