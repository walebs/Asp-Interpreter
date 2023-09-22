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
        while (s.curToken().kind != elifToken) {
            ais.exprs.add(AspExpr.parse(s));
            skip(s, colonToken);
            ais.suits.add(AspSuite.parse(s));
            if (s.curToken().kind == elifToken) skip(s, elifToken);
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
        // TODO Auto-generated method stub
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        // TODO Auto-generated method stub
        return null;
    }

}
