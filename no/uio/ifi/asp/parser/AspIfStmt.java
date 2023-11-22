package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspSuite> suits = new ArrayList<>();
    ArrayList<AspExpr> exprs = new ArrayList<>();

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ais = new AspIfStmt(s.curLineNum());
        
        skip(s, ifToken);
        while (true) {
            ais.exprs.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suits.add(AspSuite.parse(s));
            if (s.curToken().kind != elifToken) break;
            skip(s, elifToken);
        }
        if (s.curToken().kind == elseToken) {
            skip(s, elseToken);
            skip(s, colonToken);
            ais.suits.add(AspSuite.parse(s));
        }
        
        leaveParser("if stmt");
        return ais;
    }

    @Override
    void prettyPrint() {
        int suitCounter = 0;

        prettyWrite("if ");
        for (int i = 0; i < exprs.size(); i++) {
            exprs.get(i).prettyPrint();
            prettyWrite(": ");
            suits.get(i).prettyPrint();

            suitCounter++;
            if (i+1 < exprs.size()) prettyWrite("elif ");
        }
        if (suitCounter < suits.size()) {
            prettyWrite("else");
            prettyWrite(": ");
            suits.get(suitCounter).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        int index = -1;
        for (int i = 0; i < exprs.size(); i++) {
            RuntimeValue v = exprs.get(i).eval(curScope);
            if (v.getBoolValue("if test", this)) {
                index = i; 
                break;
            }
        }
        if (index != -1) suits.get(index).eval(curScope);
        else if (index == -1 && exprs.size() < suits.size()) suits.get(suits.size()-1).eval(curScope);
        trace("if stmt");
        return null;
    }
}
