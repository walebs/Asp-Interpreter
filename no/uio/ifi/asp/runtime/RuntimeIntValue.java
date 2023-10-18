package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
    long value;
    
    public RuntimeIntValue(Long v) {
        value = v;
    }

    @Override
    String typeName() {
        return "int";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return Long.toString(value);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return value;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (value != 0);
    }

    public double getFloatValue(String what, AspSyntax where) {
        return (double) value;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue((double)value + v.getFloatValue("", where));
        return new RuntimeIntValue(value + v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue((double)value / v.getFloatValue("", where));
        return new RuntimeIntValue(value / v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value == v.getFloatValue("", where));
        return new RuntimeBoolValue(value == v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value > v.getFloatValue("", where));
        return new RuntimeBoolValue(value > v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value >= v.getFloatValue("", where));
        return new RuntimeBoolValue(value >= v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue(Math.floor((double)value / v.getFloatValue("", where)));
        return new RuntimeIntValue(Math.floorDiv(value, v.getIntValue("", where)));
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value < v.getFloatValue("", where));
        return new RuntimeBoolValue(value < v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value <= v.getFloatValue("", where));
        return new RuntimeBoolValue(value <= v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue(value - (v.getFloatValue("", where) * Math.floor(value / v.getFloatValue("", where))));
        return new RuntimeIntValue(Math.floorMod(value, v.getIntValue("", where)));
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue(value * v.getFloatValue("", where));
        return new RuntimeIntValue(value * v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntValue(-value);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        // dette kan hende at det må byttes på, obs på feil når vi tester
        if (!getBoolValue("", where)) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        if (v instanceof RuntimeFloatValue) return new RuntimeBoolValue(value != v.getFloatValue("", where));
        return new RuntimeBoolValue(value != v.getIntValue("", where));
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntValue(value);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeFloatValue) return new RuntimeFloatValue(value - v.getFloatValue("", where));
        return new RuntimeIntValue(value - v.getIntValue("", where));
    }
}
