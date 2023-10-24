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
        return value.isEmpty();
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where){
        return new RuntimeBoolValue(getBoolValue("", where));
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        return null;
    }
}
