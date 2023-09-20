package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspGlobalStmt extends AspSmallStmt {
    ArrayList<AspName> names = new ArrayList<>();

    AspGlobalStmt(int n) {
        super(n);
        //TODO Auto-generated constructor stub
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt ags = new AspGlobalStmt(s.curLineNum());
        while (true) {
            skip(s, globalToken);
            ags.names.add(AspName.parse(s));
            if (s.curToken().kind != commaToken) break;
            skip(s, commaToken);
        }
        leaveParser("global stmt");
        return ags;
    }
}
