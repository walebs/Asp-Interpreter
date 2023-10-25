// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    ArrayList<AspAndTest> andTests = new ArrayList<>();

    AspExpr(int n) {
	    super(n);
    }

    public static AspExpr parse(Scanner s) {
        enterParser("expr");
        AspExpr ae = new AspExpr(s.curLineNum());

        while (true) {
            ae.andTests.add(AspAndTest.parse(s));
            if (s.curToken().kind != orToken) break;
            skip(s, orToken);
        }

        leaveParser("expr");
        return ae;
    }


    @Override
    public void prettyPrint() {
        for (int i = 0; i < andTests.size(); i++) {
            andTests.get(i).prettyPrint();
            if (i+1 < andTests.size()) prettyWrite(" or ");
        }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = andTests.get(0).eval(curScope);
        for (int i = 1; i < andTests.size(); i++) {
            if (v.getBoolValue("or operand", this)) return v;
            v = andTests.get(i).eval(curScope);
        }
        return v;
    }
}
