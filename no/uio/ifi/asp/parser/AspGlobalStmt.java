package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> names = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt ags = new AspGlobalStmt(s.curLineNum());
        
        skip(s, globalToken);
        while (true) {
            ags.names.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        
        leaveParser("global stmt");
        return ags;
    }

	@Override
	void prettyPrint() {
		prettyWrite("global ");
        for (int i = 0; i < names.size(); i++) {
            names.get(i).prettyPrint();

            if (i+1 < names.size()) prettyWrite(",");
        }
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		for (AspName an : names) {
            curScope.registerGlobalName(an.value);
        }
        trace("global stmt");
        return null;
	}
}
