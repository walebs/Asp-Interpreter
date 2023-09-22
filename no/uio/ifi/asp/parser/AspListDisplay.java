package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> expr = new ArrayList<>();

	AspListDisplay(int s) {
		super(s);
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


    static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);
        while (s.curToken().kind != rightBracketToken) {
            ald.expr.add(AspExpr.parse(s));
            if (s.curToken().kind == rightBracketToken) break;
            skip(s, commaToken);
        }
        skip(s, rightBracketToken);

        leaveParser("list display");
        return ald;
    }
}