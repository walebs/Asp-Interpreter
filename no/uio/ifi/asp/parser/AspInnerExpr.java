package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {
    static AspExpr ae = null;


	AspInnerExpr(int s) {
		super(s);
		//TODO Auto-generated
	}

	@Override
	void prettyPrint() {
		// TODO Auto-generated
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// TODO Auto-generated
		throw new UnsupportedOperationException("Unimplemented method 'eval'");
	}

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

		// Not done!!
        skip(s, leftParToken);
        ae = AspExpr.parse(s);
        skip(s, rightParToken);
        
        leaveParser("inner expr");
        return aie;
    }
}