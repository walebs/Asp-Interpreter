package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPrimary extends AspSyntax {
    AspAtom atom;
    ArrayList<AspPrimarySuffix> primarySuffixs = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary ap = new AspPrimary(s.curLineNum());
        
        ap.atom = AspAtom.parse(s);
        while (s.curToken().kind == leftParToken || s.curToken().kind == leftBracketToken) {
            ap.primarySuffixs.add(AspPrimarySuffix.parse(s));
        }

        leaveParser("primary");
        return ap;
    }

    @Override
    void prettyPrint() {
        atom.prettyPrint();
        if (!primarySuffixs.isEmpty()) {
            for (int i = 0; i < primarySuffixs.size(); i++) {
                primarySuffixs.get(i).prettyPrint();
            }
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = atom.eval(curScope);
        for (AspPrimarySuffix aps : primarySuffixs) {
            if (aps instanceof AspSubscription) {
                v = v.evalSubscription(aps.eval(curScope), this);
            } else if (aps instanceof AspArguments) {
                RuntimeListValue rlv = (RuntimeListValue) aps.eval(curScope);
                trace("Call function " + v.showInfo() + " with params " + rlv.showInfo());
                v = v.evalFuncCall(rlv.value, this);
            }
        }
        return v;
    }
}
