package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.Scanner;

abstract class AspCompoundStmt extends AspStmt {

    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s) {
        AspCompoundStmt acs = null;
        switch (s.curToken().kind) {
            case forToken:
                acs = AspForStmt.parse(s);
            case ifToken:
                acs = AspIfStmt.parse(s);
            case defToken:
                acs = AspFuncDef.parse(s);
            case whileToken:
                acs = AspWhileStmt.parse(s);
        default:
            parserError("Expected a compound stmt but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        
        return acs;
    }
}
