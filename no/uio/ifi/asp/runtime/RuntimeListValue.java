package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> value;

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        value = v;
    }

    @Override
    public String typeName() {
        return "list";
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
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) {
            ArrayList<RuntimeValue> result = new ArrayList<>();
            for (int i = 0; i < v.getIntValue("", where); i++) {
                result.addAll(value);
            }
            return new RuntimeListValue(result);
        }
        
        runtimeError("Type error for *.", where);
        return null;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeValue) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (!(v instanceof RuntimeValue)) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (!getBoolValue("", where)) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }
}
