package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double value;

    public RuntimeFloatValue(Double v) {
        value = v;
    }
    
    @Override
    public String showInfo() {
        return "" + value + "";
    }

    @Override
    public String typeName() {
        return "float";
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return Double.toString(value);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return value;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        return (value != 0.0);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return (long) value;
    }

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(value + v.getIntValue("", where));
        return new RuntimeFloatValue(value + v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(value / v.getIntValue("", where));
        return new RuntimeFloatValue(value / v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value == v.getIntValue("", where));
        return new RuntimeBoolValue(value == v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value > v.getIntValue("", where));
        return new RuntimeBoolValue(value > v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value >= v.getIntValue("", where));
        return new RuntimeBoolValue(value >= v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(Math.floor(value / v.getIntValue("", where)));
        return new RuntimeFloatValue(Math.floor(value / v.getFloatValue("", where)));
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value < v.getIntValue("", where));
        return new RuntimeBoolValue(value < v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value <= v.getIntValue("", where));
        return new RuntimeBoolValue(value <= v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(value - (v.getIntValue("", where) * Math.floor(value / v.getIntValue("", where))));
        return new RuntimeFloatValue(value - (v.getFloatValue("", where) * Math.floor(value / v.getFloatValue("", where))));
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(value * v.getIntValue("", where));
        return new RuntimeFloatValue(value * v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue(-value);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
        if (value == 0.0) return new RuntimeBoolValue(true);
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) return new RuntimeBoolValue(false);
        if (v instanceof RuntimeIntValue) return new RuntimeBoolValue(value != v.getIntValue("", where));
        return new RuntimeBoolValue(value != v.getFloatValue("", where));
    }

    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(value);
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntValue) return new RuntimeFloatValue(value - v.getIntValue("", where));
        return new RuntimeFloatValue(value - v.getFloatValue("", where));
    }
}
