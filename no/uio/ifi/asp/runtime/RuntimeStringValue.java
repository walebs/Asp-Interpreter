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
        return "string";
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
    public boolean getBoolValue(String what, AspSyntax where) {
        if (value == "") return false;
        return true;
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue((long) value.length());
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) runtimeError("String can't add with integer: " + v.showInfo(), where);
        if (v instanceof RuntimeFloatValue) runtimeError("String can't add with float: " + v.showInfo(), where);
        String str = value + v.getStringValue("", where);
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
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(value != v.getStringValue("", where));
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(value == v.getStringValue("", where));
    }
    
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(value.compareTo(v.getStringValue("", where)) > 0);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(value.compareTo(v.getStringValue("", where)) >= 0);
    }
    
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(value.compareTo(v.getStringValue("", where)) < 0);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(value.compareTo(v.getStringValue("", where)) <= 0);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(!getBoolValue("", where));
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) runtimeError("A string index must be an integer!", where);
        int index = (int) v.getIntValue("", where);
        String indexValue = "" + value.charAt(index) + "";
        return new RuntimeStringValue(indexValue);
    }
}
