package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;
import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt {
    AspName name;
    AspExpr expr;
    ArrayList<AspSubscription> subscriptions = new ArrayList<>();

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");

        AspAssignment ass = new AspAssignment(s.curLineNum());
        ass.name = AspName.parse(s);
        while (s.curToken().kind != equalToken) {
            ass.subscriptions.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        ass.expr = AspExpr.parse(s);

        leaveParser("assignment");
        return ass;
    }

	@Override
	void prettyPrint() {
		name.prettyPrint();
        if (!subscriptions.isEmpty()) {
            for (int i = 0; i < subscriptions.size(); i++) {
                subscriptions.get(i).prettyPrint();
            }
        }
        prettyWrite(" = ");
        expr.prettyPrint();
	}

	@Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        /*TODO gjøre
         * finne riktig element i subscriptions
         * kalle på assignElem (assignElem skal implementers i ListValue og DictValue)
         */
        RuntimeValue v = name.eval(curScope);
        curScope.assign(name.value, v);
        
        
        trace("assignment");
        return null;
    }
}
