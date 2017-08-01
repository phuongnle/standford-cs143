/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
    return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
    filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
    return filename;
    }

    int comment_level = 0;
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
      case YYINITIAL:
      /* nothing special to do in the initial state */
      break;
      /* If necessary, add code for other states here, e.g:
      */
      case COMMENT:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "EOF in comment");
      case STRING:
        yybegin(YYINITIAL);
        return new Symbol(TokenConstants.ERROR, "EOF in string content");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%state COMMENT, STRING, ERROR_STRING

CLASS               = [Cc][Ll][Aa][Ss][Ss]
ELSE                = [Ee][Ll][Ss][Ee]
FI                  = [Ff][Ii]
IF                  = [Ii][Ff]
IN                  = [Ii][Nn]
INHERITS            = [Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss]
LET                 = [Ll][Ee][Tt]
LOOP                = [Ll][Oo][Oo][Pp]
POOL                = [Pp][Oo][Oo][Ll]
THEN                = [Tt][Hh][Ee][Nn]
WHILE               = [Ww][Hh][Ii][Ll][Ee]
CASE                = [Cc][Aa][Ss][Ee]
ESAC                = [Ee][Ss][Aa][Cc]
OF                  = [Oo][Ff]
NEW                 = [Nn][Ee][Ww]
ISVOID              = [Ii][Ss][Vv][Oo][Ii][Dd]
DARROW              = =>
ASSIGN              = <-
NOT                 = [nN][oO][tT]
LE                  = <=
PLUS                = \+
DIV                 = /
MINUS               = -
MULT                = \*
EQ                  = =
LT                  = <
DOT                 = \.
NEG                 = ~
COMMA               = ,
SEMI                = ;
COLON               = :
LPAREN              = \(
RPAREN              = \)
AT                  = @
LBRACE              = \{
RBRACE              = \}
DIGIT               = [0-9]
BOOL_CONST          = t[rR][uU][eE]|f[aA][lL][sS][eE]
TYPE_IDENTIFIER     = [A-Z][a-zA-Z0-9_]*
OBJ_IDENTIFIER      = [a-z][a-zA-Z0-9_]*

WHITESPACE          = [ \f\r\t\v\013]
NEW_LINE            = \n

SINGLE_COMMENT      = --.*
START_BLOCK_COMMENT = \(\*
END_BLOCK_COMMENT   = \*\)

%%

{NEW_LINE} {
  curr_lineno++;
  switch (yy_lexical_state) {
    case STRING:
      yybegin(YYINITIAL);
      string_buf.setLength(0);
      return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
    case ERROR_STRING:
      yybegin(YYINITIAL);
      string_buf.setLength(0);
      break;
  }
}
{WHITESPACE}+ { ; }


<YYINITIAL, COMMENT>{START_BLOCK_COMMENT} { yybegin(COMMENT); ++comment_level; }
<COMMENT>.|\\{START_BLOCK_COMMENT}|\\{END_BLOCK_COMMENT} { ; }
<COMMENT>{END_BLOCK_COMMENT} { --comment_level; if (comment_level == 0) yybegin(YYINITIAL); }
<YYINITIAL>{SINGLE_COMMENT} { ; }

<YYINITIAL>{END_BLOCK_COMMENT} { return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }

<YYINITIAL>{CLASS} { return new Symbol(TokenConstants.CLASS); }

<YYINITIAL>{ELSE} { return new Symbol(TokenConstants.ELSE); }

<YYINITIAL>{FI} { return new Symbol(TokenConstants.FI); }

<YYINITIAL>{IF} { return new Symbol(TokenConstants.IF); }

<YYINITIAL>{IN} { return new Symbol(TokenConstants.IN); }

<YYINITIAL>{INHERITS} { return new Symbol(TokenConstants.INHERITS); }

<YYINITIAL>{LET} { return new Symbol(TokenConstants.LET); }

<YYINITIAL>{LOOP} { return new Symbol(TokenConstants.LOOP); }

<YYINITIAL>{POOL} { return new Symbol(TokenConstants.POOL); }

<YYINITIAL>{THEN} { return new Symbol(TokenConstants.THEN); }

<YYINITIAL>{WHILE} { return new Symbol(TokenConstants.WHILE); }

<YYINITIAL>{CASE} { return new Symbol(TokenConstants.CASE); }

<YYINITIAL>{ESAC} { return new Symbol(TokenConstants.ESAC); }

<YYINITIAL>{OF} { return new Symbol(TokenConstants.OF); }

<YYINITIAL>{DARROW} { return new Symbol(TokenConstants.DARROW); }

<YYINITIAL>{NEW} { return new Symbol(TokenConstants.NEW); }

<YYINITIAL>{ISVOID} { return new Symbol(TokenConstants.ISVOID); }

<YYINITIAL>{ASSIGN} { return new Symbol(TokenConstants.ASSIGN); }

<YYINITIAL>{NOT} { return new Symbol(TokenConstants.NOT); }

<YYINITIAL>{LE} { return new Symbol(TokenConstants.LE); }

<YYINITIAL>{PLUS} { return new Symbol(TokenConstants.PLUS); }

<YYINITIAL>{DIV} { return new Symbol(TokenConstants.DIV); }

<YYINITIAL>{MINUS} { return new Symbol(TokenConstants.MINUS); }

<YYINITIAL>{MULT} { return new Symbol(TokenConstants.MULT); }

<YYINITIAL>{EQ} { return new Symbol(TokenConstants.EQ); }

<YYINITIAL>{LT} { return new Symbol(TokenConstants.LT); }

<YYINITIAL>{DOT} { return new Symbol(TokenConstants.DOT); }

<YYINITIAL>{NEG} { return new Symbol(TokenConstants.NEG); }

<YYINITIAL>{COMMA} { return new Symbol(TokenConstants.COMMA); }

<YYINITIAL>{SEMI} { return new Symbol(TokenConstants.SEMI); }

<YYINITIAL>{COLON} { return new Symbol(TokenConstants.COLON); }

<YYINITIAL>{LPAREN} { return new Symbol(TokenConstants.LPAREN); }

<YYINITIAL>{RPAREN} { return new Symbol(TokenConstants.RPAREN); }

<YYINITIAL>{AT} { return new Symbol(TokenConstants.AT); }

<YYINITIAL>{LBRACE} { return new Symbol(TokenConstants.LBRACE); }

<YYINITIAL>{RBRACE} { return new Symbol(TokenConstants.RBRACE); }

<YYINITIAL>{DIGIT}+  {
    return new Symbol(TokenConstants.INT_CONST, new IntSymbol(yytext(), yytext().length(), yytext().hashCode()));
}

<YYINITIAL>{BOOL_CONST}  {
    return new Symbol(TokenConstants.BOOL_CONST, Boolean.parseBoolean(yytext()));
}

<YYINITIAL>{TYPE_IDENTIFIER} {
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}

<YYINITIAL>{OBJ_IDENTIFIER} {
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}


<YYINITIAL>\" { yybegin(STRING); }
<ERROR_STRING>\" { yybegin(YYINITIAL); }
<STRING>\" {
  yybegin(YYINITIAL);
  String text = string_buf.toString();
  string_buf.setLength(0);
  if (text.length() >= MAX_STR_CONST) {
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  }
  return new Symbol(TokenConstants.STR_CONST, new StringSymbol(text, text.length(), text.hashCode()));
}
<STRING>\\n { string_buf.append("\n"); }
<STRING>\\t { string_buf.append("\t"); }
<STRING>\\b { string_buf.append("\b"); }
<STRING>\\b { string_buf.append("\b"); }
<STRING>\\f { string_buf.append("\f"); }
<STRING>\\\" { string_buf.append("\""); }
<STRING>\\\\ { string_buf.append("\\"); }
<STRING>\\ { ; }
<STRING>\\\n { string_buf.append("\n");}
<STRING>\\\t { string_buf.append("\t");}
<STRING>\\\f { string_buf.append("\f");}
<STRING>\0 {
  yybegin(ERROR_STRING);
  string_buf.setLength(0);
  return new Symbol(TokenConstants.ERROR, "String contains null character");
}
<STRING>[^\"\0\n\\]+ {
  string_buf.append(yytext());
}

. {
    /* This rule should be the very last
       in your lexical specification and
       will match match everything not
       matched by other lexical rules.
    // System.err.println("LEXER BUG - UNMATCHED: " + yytext());*/
    return new Symbol(TokenConstants.ERROR, yytext());
}
