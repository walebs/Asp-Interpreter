package no.uio.ifi.asp.runtime;

import java.util.HashMap;
import java.util.Map;

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
        return toString();
    }

    @Override
    public String toString() {
        String str = "{";
        int counter = 0;
        for (Map.Entry<String,RuntimeValue> set : value.entrySet()) {
            str += "'" + set.getKey() + "'" + ": " + set.getValue();
            if (counter != value.size()-1) str += ", ";
            counter++;
        }
        return str + "}";
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
        String index = v.getStringValue("", where);
        return value.get(index);
    }
}
