package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    //TODO må ha en verdi, vet ikke hvordan det skal implementeres

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
        //TODO må sjekke om veriden i klassen er tom
        return true;
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
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
