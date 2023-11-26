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
        if (subscriptions.size() > 0) {
            String str = name.value;
            RuntimeValue v = name.eval(curScope);

            for (int i = 0; i < subscriptions.size() - 1; i++) {
                RuntimeValue key = subscriptions.get(i).eval(curScope);
                str += "[" + key.showInfo() + "]";
                v = v.evalSubscription(key, this);
            }

            RuntimeValue key = subscriptions.get(subscriptions.size() - 1).eval(curScope);
            str += "[" + key.showInfo() + "]";
            RuntimeValue val = expr.eval(curScope);
            trace(str + " = " + val.showInfo());
            v.evalAssignElem(key, val, this);
        } else {
            RuntimeValue exprV = expr.eval(curScope);
            curScope.assign(name.value, exprV);
            trace(name.value + " = " + exprV.showInfo());
        }

        return null;
    }
}
