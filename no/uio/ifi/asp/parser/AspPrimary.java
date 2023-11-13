package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeDictValue;
import no.uio.ifi.asp.runtime.RuntimeListValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeStringValue;
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
            if (v instanceof RuntimeDictValue || v instanceof RuntimeListValue || v instanceof RuntimeStringValue) {
                v = v.evalSubscription(aps.eval(curScope), this);
            }
        }
        // hvordan skal vi få en liste her?
        if (v instanceof RuntimeListValue) {
            v.evalFuncCall(null, atom);
        }
        return v;
    }
}
