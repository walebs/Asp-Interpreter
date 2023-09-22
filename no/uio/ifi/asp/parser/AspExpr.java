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
	    //TODO -- Must be changed in part 2:
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //TODO -- Must be changed in part 3:
        return null;
    }
}
