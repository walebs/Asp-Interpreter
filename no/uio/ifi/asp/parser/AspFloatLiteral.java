package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.RuntimeFloatValue;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFloatLiteral extends AspAtom {
    Double value;

	AspFloatLiteral(int s) {
		super(s);
	}
	
	static AspFloatLiteral parse(Scanner s) {
		enterParser("float literal");
		AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());

		afl.value = s.curToken().floatLit;
		skip(s, floatToken);

		leaveParser("float literal");
		return afl;
	}

	@Override
	void prettyPrint() {
		prettyWrite(value.toString());
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		RuntimeFloatValue v = new RuntimeFloatValue(value);
		return v;
	}
}
