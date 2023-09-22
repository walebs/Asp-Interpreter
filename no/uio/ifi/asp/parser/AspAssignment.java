package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.Scanner;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt {
    static AspName name;
    static AspExpr expr;
    ArrayList<AspSubscription> subscriptions = new ArrayList<>();

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");

        AspAssignment ass = new AspAssignment(s.curLineNum());
        name = AspName.parse(s);
        while (s.curToken().kind != equalToken) {
            ass.subscriptions.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        expr = AspExpr.parse(s);

        leaveParser("assignment");
        return ass;
    }

}
