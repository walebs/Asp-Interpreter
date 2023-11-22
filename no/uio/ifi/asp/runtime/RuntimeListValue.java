package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    public ArrayList<RuntimeValue> value;

    public RuntimeListValue(ArrayList<RuntimeValue> v) {
        value = v;
    }

    @Override
    public String typeName() {
        return "list";
    }

    @Override
    public String showInfo() {
        return toString();
    }

    @Override
    public String toString() {
        String str = "[";
        for (int i = 0; i < value.size(); i++) {
            str += value.get(i);
            if (i < value.size()-1) str += ", ";
        }
        return str + "]";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return value.isEmpty();
    }

    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
        value.add(val);
    }

    @Override
    public RuntimeValue evalLen(AspSyntax where) {
        return new RuntimeIntValue((long) value.size());
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
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        return new RuntimeBoolValue(getBoolValue("", where));
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) runtimeError("A list index must be an integer!", where);
        int index = (int) v.getIntValue("", where);
        if (index >= value.size()) runtimeError("List index " + index + " out of range!", where);
        return value.get(index);
    }
}
