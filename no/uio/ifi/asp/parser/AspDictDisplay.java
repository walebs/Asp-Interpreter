package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;

import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> string = new ArrayList<>();
    ArrayList<AspExpr> expr = new ArrayList<>();

	AspDictDisplay(int s) {
		super(s);
		// Auto-generated
	}

	@Override
	void prettyPrint() {
		//Auto-generated
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// Auto-generated
		throw new UnsupportedOperationException("Unimplemented method 'eval'");
	}
    
    // Må sjekke logikken her engang til
    static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay add = new AspDictDisplay(s.curLineNum());

        skip(s, leftBraceToken);
        while (s.curToken().kind != rightBraceToken) {
            add.string.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            add.expr.add(AspExpr.parse(s));
            if (s.curToken().kind != rightBraceToken) {
                skip(s, commaToken);
            }
        }
        skip(s, rightBraceToken);
        leaveParser("dict display");
        return add;
    }
}