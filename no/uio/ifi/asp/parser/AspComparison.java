package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspComparison extends AspSyntax {
    ArrayList<AspTerm> terms = new ArrayList<>();
    ArrayList<AspCompOpr> compOprs = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison ac = new AspComparison(s.curLineNum());

        while (true) {
            ac.terms.add(AspTerm.parse(s));
            if (s.isCompOpr()) {
                ac.compOprs.add(AspCompOpr.parse(s, s.curToken().kind));
            } else break;
        }

        leaveParser("comparison");
        return ac;
    }

    @Override
    void prettyPrint() {
        // TODO Auto-generated method stub
        
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }
    
}
