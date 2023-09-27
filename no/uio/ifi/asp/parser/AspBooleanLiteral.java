package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspBooleanLiteral extends AspAtom {
	boolean value;

	AspBooleanLiteral(int s) {
		super(s);
	}

	static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());

        if (s.curToken().kind == trueToken) {
            skip(s, trueToken);
			abl.value = true;
        }
        else if (s.curToken().kind == falseToken) {
            skip(s, falseToken);
			abl.value = false;
        }
		
        leaveParser("boolean literal");
        return abl;
    }

	@Override
	void prettyPrint() {
		// TODO: Auto-generated 
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// TODO Auto-generated method stub
		return null;
	}
}