package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import no.uio.ifi.asp.scanner.TokenKind;

public class AspNoneLiteral extends AspAtom {

	AspNoneLiteral(int s) {
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
    
    static AspNoneLiteral parse(Scanner s) {
        enterParser("none literal");
        AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
        skip(s, TokenKind.noneToken);
        leaveParser("none literal");
        return anl;
    }
    
}