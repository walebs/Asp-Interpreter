package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIntegerLiteral extends AspAtom {
    static long value;

	AspIntegerLiteral(int s) {
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

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
		value = s.curToken().integerLit;
        skip(s, integerToken);
        leaveParser("integer literal");
        return ail;
    } 
}