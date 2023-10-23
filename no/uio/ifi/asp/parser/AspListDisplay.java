package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeListValue;
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
	
	static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay ald = new AspListDisplay(s.curLineNum());

        skip(s, leftBracketToken);
        while (true) {
            ald.expr.add(AspExpr.parse(s));
            if (s.curToken().kind == rightBracketToken) break;
            skip(s, commaToken);
        }
        skip(s, rightBracketToken);

        leaveParser("list display");
        return ald;
	}

	@Override
	void prettyPrint() {
		prettyWrite("[");
		if (!expr.isEmpty()) {
			for (int i = 0; i < expr.size(); i++) {
				expr.get(i).prettyPrint();

				if (i+1 < expr.size()) prettyWrite(", ");
			}
		}
		prettyWrite("]");
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		ArrayList<RuntimeValue> value = new ArrayList<>();
		for (AspExpr ae : expr) {
			value.add(ae.eval(curScope));
		}
		return new RuntimeListValue(value);
	}
}
