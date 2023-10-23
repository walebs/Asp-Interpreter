package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String value;

    public RuntimeStringValue(String v) {
        value = v;
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public String typeName() {
        return "String";
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return value;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        String str = value + v.getStringValue("", where);   //"" for å passe getStringValue()
        return new RuntimeStringValue(str);
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        String str = "";
        for (int i = 0; i < v.getIntValue("", where); i++) {
            str += value;
        }
        return new RuntimeStringValue(str);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (value == v.getStringValue("", where)) { 
            return new RuntimeBoolValue(false);
        }
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (value == v.getStringValue("", where)) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }
    
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (value.length() > v.getStringValue("", where).length()) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (value.length() >= v.getStringValue("", where).length()) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }
    
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (value.length() < v.getStringValue("", where).length()) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (value.length() <= v.getStringValue("", where).length()) {
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }
}
