package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspInnerExpr extends AspAtom {
    AspExpr expr;

	AspInnerExpr(int s) {
		super(s);
	}

	static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

        skip(s, leftParToken);
        aie.expr = AspExpr.parse(s);
        skip(s, rightParToken);
        
        leaveParser("inner expr");
        return aie;
    }

	@Override
	void prettyPrint() {
		prettyWrite("(");
		expr.prettyPrint();
		prettyWrite(")");
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// TODO Auto-generated
		return null;
	}
}