package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

public class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factors = new ArrayList<>();
    ArrayList<AspTermOpr> termOpr = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm at = new AspTerm(s.curLineNum());
        
        while (true) {
            at.factors.add(AspFactor.parse(s));
            if (s.isTermOpr()) {
                at.termOpr.add(AspTermOpr.parse(s, s.curToken().kind));
            } else break;
        }

        leaveParser("term");
        return at;
    }

    @Override
    void prettyPrint() {
        // Fra Uke 38 forslag
        // Ikke 100% ferdig kanskje pga vi lurer på om vi skal gjøre operators til tokens som i ukeforslaget
        factors.get(0).prettyPrint();
        for (int i = 0; i < factors.size(); i++) {
            System.out.println(" " + termOpr.get(i - 1) + " ");
            factors.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eval'");
    }

}
