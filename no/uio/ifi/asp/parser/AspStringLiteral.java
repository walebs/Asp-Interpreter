package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;


// Idk om ferdig
public class AspStringLiteral extends AspAtom {
	String value;

	AspStringLiteral(int s) {
		super(s);
	}

	@Override
	void prettyPrint() {
		// Auto-generated
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// Auto-generated
		throw new UnsupportedOperationException("Unimplemented method 'eval'");
	}

    static AspStringLiteral parse(Scanner s) {
		enterParser("string literal");
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		asl.value = s.curToken().name;
		skip(s, nameToken);
		leaveParser("string literal");
		return asl;
	}
}