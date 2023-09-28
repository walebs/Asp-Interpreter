package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefs = new ArrayList<>();
    ArrayList<AspPrimary> primary = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprs = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    static AspFactor parse(Scanner s) {
        enterParser("factor");
        AspFactor af = new AspFactor(s.curLineNum());

        while (true) {
            if (s.isFactorPrefix()) af.factorPrefs.add(AspFactorPrefix.parse(s, s.curToken().kind));
            else af.factorPrefs.add(null);
            af.primary.add(AspPrimary.parse(s));
            if (s.isFactorOpr()) af.factorOprs.add(AspFactorOpr.parse(s, s.curToken().kind));
            else break;
        }

        leaveParser("factor");
        return af;
    }

    @Override
    void prettyPrint() {
        for (int i = 0; i < primary.size(); i++) {
            // TODO kan lage problemer
            if (factorPrefs.get(i) != null) factorPrefs.get(i).prettyPrint();
            primary.get(i).prettyPrint();
            if (i+1 < factorOprs.size()) factorOprs.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

}
