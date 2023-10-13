package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspStringLiteral extends AspAtom {
	String value;

	AspStringLiteral(int s) {
		super(s);
	}

	static AspStringLiteral parse(Scanner s) {
		AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
		asl.value = s.curToken().stringLit;
		skip(s, stringToken);
		// når vi bruker ref-asp.jar på gal-ordbok gir den </expr> som siste, hvis man tar enterParser på toppen vil siste være <string literal>
		enterParser("string literal");
		leaveParser("string literal");
		return asl;
	}

	@Override
	void prettyPrint() {
		prettyWrite("\"" + value + "\"");
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		// Auto-generated
		throw new UnsupportedOperationException("Unimplemented method 'eval'");
	}
}