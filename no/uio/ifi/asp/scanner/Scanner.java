// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
		curFileName = fileName;
		indents.push(0);

		try {
			sourceFile = new LineNumberReader(
					new InputStreamReader(
					new FileInputStream(fileName),
					"UTF-8"));
		} catch (IOException e) {
			scannerError("Cannot read " + fileName + "!");
		}
    }


    private void scannerError(String message) {
		String m = "Asp scanner error";
		if (curLineNum() > 0)
			m += " on line " + curLineNum();
		m += ": " + message;

		Main.error(m);
    }


    public Token curToken() {
		while (curLineTokens.isEmpty()) {
			readNextLine();
		}
		return curLineTokens.get(0);
    }


    public void readNextToken() {
		if (! curLineTokens.isEmpty())
			curLineTokens.remove(0);
    }


    private void readNextLine() {
		curLineTokens.clear();

		// Read the next line:
		String line = null;
		try {
			line = sourceFile.readLine();
			if (line == null) {
				sourceFile.close();
				sourceFile = null;
			} else {
				Main.log.noteSourceLine(curLineNum(), line);
			}
		} catch (IOException e) {
			sourceFile = null;
			scannerError("Unspecified I/O error!");
		}

		// Innledende TAB-er oversettes til blanke.
		String noTABline = expandLeadingTabs(line);

		// Indentering beregnes, og INDENT/DEDENT-er legges i curLineTokens.
		int indentsOnLine = findIndent(noTABline);

		if (indentsOnLine > indents.peek()) {
			// hvis linjen har mer indent
			indents.push(indentsOnLine);
			curLineTokens.add(new Token(indentToken, curLineNum()));
		} else if (indentsOnLine < indents.peek()) {
			// hvis linjen har færre indents
			while (indentsOnLine < indents.peek()) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}

			if (indentsOnLine != indents.peek()) {
				// Error-handling som jeg er usikker på
				scannerError("Indentation error on line: " + curLineNum()); 
				System.exit(1);
			}
		}

		// Gå gjennom linjen:		
		int pos = 0;
		boolean commentOrBlank = false;

		while (pos < noTABline.length()) {
			//TODO fjern 
			System.out.println("11111111111111");
			char c = noTABline.charAt(pos);
			char cNext = noTABline.charAt(pos++);

			// Blanke tegn og TAB-er ignoreres.
			if (Character.isWhitespace(c)) {
				// ikke gjør noe
				pos++;
				if (pos == noTABline.length()) {
					commentOrBlank = true;
				}
			} else if (c == '#') {
				// En ’#’ angir at resten av linjen skal ignoreres.
				commentOrBlank = true;
				break;
			} else if (isDigit(c) || c == '.') {
				// setter opp en string for å sjekke om det er int eller float
				String str = "";
				boolean isFloat = false;
				boolean isInt = true;

				while (isDigit(c) || c == '.') {
					//TODO fjern 
					System.out.println("22222222222222222");
					// skiller om det er int eller float når man finner '.'
					if (c == 0) {
						if (str == "") {
							// legges ikke inn hvis det bare er 0			
						} else {
							str += c;
						}
					} else if (c == '.') {
						// error-handling hvis det er et ulovelig flyttal, hvis det starter med '.' eller slutter med '.'
						if (str == "" || !isDigit(cNext)) {
							scannerError("Not a valid float value on line: " + curLineNum());
							System.exit(1);
						} else {
							str += c;
							isFloat = true;
							isInt = false;
						}
					} else {
						str += c;
					}
					
					pos++;
					c = noTABline.charAt(pos);
				}

				// sjekker om det er float eller int
				if (isFloat) {
					Token t = new Token(floatToken, curLineNum());
					t.floatLit = Float.parseFloat(str);
					curLineTokens.add(t);
				} else if (isInt) {
					Token t = new Token(integerToken, curLineNum());
					t.integerLit = Integer.parseInt(str);
					curLineTokens.add(t);
				}

			} else if (isLetterAZ(c)) {
				// setter opp for å sjekke en string mot alle mulige keywords
				String str = "";
				while (isLetterAZ(c) || isDigit(c) || c == '$') {
					//TODO fjern 
					System.out.println("333333333333333");
					System.out.println("pos: " + pos);
					if (c == '$') {
						// kaster en error ved $
						scannerError("Ugyldig name token on line: " + curLineNum());
						System.exit(1);
					}
					str += c;
					c = noTABline.charAt(pos);
					pos++;
				}

				// Finner riktig keyword til stringen
				if (str.equals("and")) {
					curLineTokens.add(new Token(andToken, curLineNum()));
				} else if (str.equals("def")) {
					curLineTokens.add(new Token(defToken, curLineNum()));
				} else if (str.equals("elif")) {
					curLineTokens.add(new Token(elifToken, curLineNum()));
				} else if (str.equals("else")) {
					curLineTokens.add(new Token(elseToken, curLineNum()));
				} else if (str.equals("False")) {
					curLineTokens.add(new Token(falseToken, curLineNum()));
				} else if (str.equals("for")) {
					curLineTokens.add(new Token(forToken, curLineNum()));
				} else if (str.equals("global")) {
					curLineTokens.add(new Token(globalToken, curLineNum()));
				} else if (str.equals("if")) {
					curLineTokens.add(new Token(ifToken, curLineNum()));
				} else if (str.equals("in")) {
					curLineTokens.add(new Token(inToken, curLineNum()));
				} else if (str.equals("None")) {
					curLineTokens.add(new Token(noneToken, curLineNum()));
				} else if (str.equals("not")) {
					curLineTokens.add(new Token(notToken, curLineNum()));
				} else if (str.equals("or")) {
					curLineTokens.add(new Token(orToken, curLineNum()));
				} else if (str.equals("pass")) {
					curLineTokens.add(new Token(passToken, curLineNum()));
				} else if (str.equals("return")) {
					curLineTokens.add(new Token(returnToken, curLineNum()));
				} else if (str.equals("True")) {
					curLineTokens.add(new Token(trueToken, curLineNum()));
				} else if (str.equals("while")) {
					curLineTokens.add(new Token(whileToken, curLineNum()));
				} else {
					// hvis name legge til riktig verdier til Token
					Token t = new Token(nameToken, curLineNum());
					t.name = str;
					curLineTokens.add(t);
				}
			} else if (c == '"' || c == '\'') {
				// finner stringen og legger den til som et Token
				String str = "";

				while (cNext != '"' || cNext != '\'') {
					//TODO fjern 
					System.out.println("4444444444444444444444");
					// error-handling hvis pos indeks er lengre enn linjen.
					if (pos >= noTABline.length()) {
						scannerError("Invalid string on line: " + curLineNum());
						System.exit(1);
					}
					str += cNext;
					pos++;
					cNext = noTABline.charAt(pos++);
				}

				Token t = new Token(stringToken, curLineNum());
				t.stringLit = str;
				curLineTokens.add(t);

				// For å komme seg forbi siste anførselstegnet og fortsette på resten av linjen hvis det er mer.
				// Med logikken som er her, så vil pos være den siste char i stringen før anførselstegnet
				// og for å unngå en nullPointerException sjekker jeg om pos - line.length() == 1 og legger til 1 eller 2 til pos utifra
				// hva sjekken gir meg.
				// Er en failsafe i den ytterste while loopen som skal fange opp om pos == line.length()
				if (pos - noTABline.length() == 1) {
					pos++;
				} else {
					pos += 2;
				}

			} else if (c == '+') {
				curLineTokens.add(new Token(plusToken, curLineNum()));
				pos++;
			} else if (c == '-') {
				curLineTokens.add(new Token(minusToken, curLineNum()));
				pos++;
			} else if (c == '*') {
				curLineTokens.add(new Token(astToken, curLineNum()));
				pos++;
			} else if (c == '/') {
				if (cNext == '/') {
					curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(slashToken, curLineNum()));
					pos++;
				}
			} else if (c == '=') {
				if (cNext == '=') {
					curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(equalToken, curLineNum()));
					pos++;
				}
			} else if (c == '>') {
				if (cNext == '=') {
					curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(greaterToken, curLineNum()));
					pos++;
				}
			} else if (c == '<') {
				if (cNext == '=') {
					curLineTokens.add(new Token(lessEqualToken, curLineNum()));
					pos++;
				} else {
					curLineTokens.add(new Token(lessToken, curLineNum()));
					pos++;
				}
			} else if (c == '!') {
				if (cNext == '=') {
					curLineTokens.add(new Token(notEqualToken, curLineNum()));
					pos++;
				}
			} else if (c == '%') {
				curLineTokens.add(new Token(percentToken, curLineNum()));
				pos++;
			} else if (c == ':') {
				curLineTokens.add(new Token(colonToken, curLineNum()));
				pos++;
			} else if (c == ',') {
				curLineTokens.add(new Token(commaToken, curLineNum()));
				pos++;
			} else if (c == '{') {
				curLineTokens.add(new Token(leftBraceToken, curLineNum()));
				pos++;
			} else if (c == '}') {
				curLineTokens.add(new Token(rightBraceToken, curLineNum()));
				pos++;
			} else if (c == '[') {
				curLineTokens.add(new Token(leftBracketToken, curLineNum()));
				pos++;
			} else if (c == ']') {
				curLineTokens.add(new Token(rightBracketToken, curLineNum()));
				pos++;
			} else if (c == '(') {
				curLineTokens.add(new Token(leftParToken, curLineNum()));
				pos++;
			} else if (c == ')') {
				curLineTokens.add(new Token(rightParToken, curLineNum()));
				pos++;
			} else if (c == ';') {
				curLineTokens.add(new Token(semicolonToken, curLineNum()));
				pos++;
			}
		}
		
		// hvis sourceFile blir null er vi på slutten av filen da må man legge til nødvendige dedentoken, newline og E-o-f tokens
		if (sourceFile == null) {
			while (indents != null) {
				indents.pop();
				curLineTokens.add(new Token(dedentToken, curLineNum()));
			}
			curLineTokens.add(new Token(newLineToken,curLineNum()));
			curLineTokens.add(new Token(eofToken, curLineNum()));
		} else if (!commentOrBlank) {
			// Terminate line:
			curLineTokens.add(new Token(newLineToken,curLineNum()));
		}

		for (Token t: curLineTokens) 
			Main.log.noteToken(t);
    }

    public int curLineNum() {
		return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
		int indent = 0;

		while (indent<s.length() && s.charAt(indent)==' ') indent++;
		return indent;
    }

    /*
	 * Om former TAB til antall mellomrom sånn at det er lett for findIndent() å telle antall indent
	 */
	private String expandLeadingTabs(String s) {
		int n = 0;
		String text = s.stripLeading();
		String line = "";

		while (n < s.length() && (s.charAt(n) == ' ' || s.charAt(n) == '\t')) {
			if (s.charAt(n) == '\t') {
				for (int i = 0; i <  TABDIST - (n % TABDIST); i++) {
					line += ' ';
					n++;
				}
			} else {
				line += ' ';
				n++;
			}
		}
		line += text;
		return line;
    }


    private boolean isLetterAZ(char c) {
		return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
		return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorPrefix() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean isFactorOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }
	

    public boolean isTermOpr() {
		TokenKind k = curToken().kind;
		//-- Must be changed in part 2:
		return false;
    }


    public boolean anyEqualToken() {
		for (Token t: curLineTokens) {
			if (t.kind == equalToken) return true;
			if (t.kind == semicolonToken) return false;
		}
		return false;
    }
}
