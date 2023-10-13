package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;
import no.uio.ifi.asp.runtime.RuntimeReturnValue;
import no.uio.ifi.asp.runtime.RuntimeScope;
import no.uio.ifi.asp.runtime.RuntimeValue;

public class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTests = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest aat = new AspAndTest(s.curLineNum());
        
        while (true) {
            aat.notTests.add(AspNotTest.parse(s));
            if (s.curToken().kind != andToken) break;
            skip(s, andToken);
        }

        leaveParser("and test");
        return aat;
    }
    
    @Override
    public void prettyPrint() {
        for (int i = 0; i < notTests.size(); i++) {
            notTests.get(i).prettyPrint();
            if (i+1 < notTests.size()) prettyWrite(" and ");
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = notTests.get(0).eval(curScope);
        for (int i = 1; i < notTests.size(); i++) {
            if (!v.getBoolValue("and operand", this)) return v;
            v = notTests.get(i).eval(curScope);
        }
        return v;
    }
}