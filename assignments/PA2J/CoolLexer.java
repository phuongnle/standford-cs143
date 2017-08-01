/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int STRING = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int ERROR_STRING = 3;
	private final int yy_state_dtrans[] = {
		0,
		66,
		96,
		99
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"65,5:8,63,1,6,64,2,5:18,6,5,61,5:5,3,8,4,29,33,9,31,30,39:10,35,34,28,24,25" +
",5,36,43,44,45,46,47,15,44,48,49,44:2,50,44,51,52,53,44,54,55,20,56,57,58,4" +
"4:3,5,7,5:2,59,5,12,62,10,27,14,42,60,18,16,60:2,11,60,17,21,22,60,19,13,40" +
",41,26,23,60:3,37,5,38,32,5,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,176,
"0,1:2,2,3,4,1:2,5,6,7,8,9,1:10,10,1:3,11,12,13,14,13,1:3,13:7,12,13:6,3,1,1" +
"5,16,1:13,17,18,19,20,13,12,21,12:8,13,12:5,22,23,24,25,1,26,27,28,29,30,31" +
",32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56" +
",57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81" +
",82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,13,12,99,100,101,102,10" +
"3,104,105,106,107")[0];

	private int yy_nxt[][] = unpackFromString(108,66,
"1,2,3,4,5,6,3,6,7,8,9,125,165:2,167,10,68,127,165:2,69,89,169,171,11,6,165:" +
"2,12,13,14,15,16,17,18,19,20,21,22,23,173,165,93,166:2,168,166,170,166,90,1" +
"26,128,94,172,166:4,174,6,165,24,165,3:2,6,-1:68,3,-1:3,3,-1:56,3:2,-1:5,25" +
",-1:69,26,-1:66,27,-1:66,165,175,129,165:11,-1:2,165:2,-1:11,165:4,129,165:" +
"6,175,165:10,-1,165,-1:13,166:6,28,166:7,-1:2,166:2,-1:11,166:10,28,166:11," +
"-1,166,-1:28,32,-1:49,33,-1:14,34,-1:80,23,-1:29,27:63,-1:10,166:14,-1:2,16" +
"6:2,-1:11,166:22,-1,166,-1:13,165:14,-1:2,165:2,-1:11,165:22,-1,165,-1:13,1" +
"65:8,153,165:5,-1:2,165:2,-1:11,165:9,153,165:12,-1,165,-1:5,51:5,-1,51:53," +
"-1,51:3,-1:2,55,-1:5,56,-1:9,57,-1:22,58,-1,59,-1:18,60,61,62,63,-1,1,2,3,4" +
"9,87,91,3,95,91:55,3:2,91,-1:2,67,51:3,67,-1,51:53,-1,51,67:2,-1:11,165:3,1" +
"37,165,29,165,30,165:6,-1:2,165:2,-1:11,165:3,29,165:8,30,165:3,137,165:5,-" +
"1,165,-1:13,166:8,130,166:5,-1:2,166:2,-1:11,166:9,130,166:12,-1,166,-1:13," +
"166:8,152,166:5,-1:2,166:2,-1:11,166:9,152,166:12,-1,166,-1:11,50,-1:61,91," +
"-1:71,165:5,31,165:8,-1:2,165:2,-1:11,165:3,31,165:18,-1,165,-1:13,166:3,14" +
"0,166,71,166,72,166:6,-1:2,166:2,-1:11,166:3,71,166:8,72,166:3,140,166:5,-1" +
",166,-1:11,91,-1:67,165:2,147,165:3,70,165:7,-1:2,165:2,-1:11,165:4,147,165" +
":5,70,165:11,-1,165,-1:13,166:5,73,166:8,-1:2,166:2,-1:11,166:3,73,166:18,-" +
"1,166,-1:6,88,92,-1:61,1,2,67,51:3,67,52,51:53,53,51,67:2,54,-1:10,165:10,3" +
"5,165:3,-1:2,165:2,-1:11,165,35,165:20,-1,165,-1:13,166:10,74,166:3,-1:2,16" +
"6:2,-1:11,166,74,166:20,-1,166,-1:3,1,2,3,64:3,3,64:54,65,64,3:2,64,-1:10,1" +
"65:13,36,-1:2,165:2,-1:11,165:19,36,165:2,-1,165,-1:13,166:13,75,-1:2,166:2" +
",-1:11,166:19,75,166:2,-1,166,-1:13,165:10,37,165:3,-1:2,165:2,-1:11,165,37" +
",165:20,-1,165,-1:13,166:10,76,166:3,-1:2,166:2,-1:11,166,76,166:20,-1,166," +
"-1:13,165:4,38,165:9,-1:2,165:2,-1:11,165:8,38,165:13,-1,165,-1:13,166:7,42" +
",166:6,-1:2,166:2,-1:11,166:12,42,166:9,-1,166,-1:13,165:12,39,165,-1:2,165" +
":2,-1:11,165:14,39,165:7,-1,165,-1:13,166:4,77,166:9,-1:2,166:2,-1:11,166:8" +
",77,166:13,-1,166,-1:13,165:4,40,165:9,-1:2,165:2,-1:11,165:8,40,165:13,-1," +
"165,-1:13,166:4,79,166:9,-1:2,166:2,-1:11,166:8,79,166:13,-1,166,-1:13,41,1" +
"65:13,-1:2,165:2,-1:11,165:6,41,165:15,-1,165,-1:13,80,166:13,-1:2,166:2,-1" +
":11,166:6,80,166:15,-1,166,-1:13,165,43,165:12,-1:2,165:2,-1:11,165:11,43,1" +
"65:10,-1,165,-1:13,166:12,78,166,-1:2,166:2,-1:11,166:14,78,166:7,-1,166,-1" +
":13,165:7,81,165:6,-1:2,165:2,-1:11,165:12,81,165:9,-1,165,-1:13,166,82,166" +
":12,-1:2,166:2,-1:11,166:11,82,166:10,-1,166,-1:13,165:4,44,165:9,-1:2,165:" +
"2,-1:11,165:8,44,165:13,-1,165,-1:13,166:3,83,166:10,-1:2,166:2,-1:11,166:1" +
"6,83,166:5,-1,166,-1:13,165:3,45,165:10,-1:2,165:2,-1:11,165:16,45,165:5,-1" +
",165,-1:13,166:4,84,166:9,-1:2,166:2,-1:11,166:8,84,166:13,-1,166,-1:13,165" +
":4,46,165:9,-1:2,165:2,-1:11,165:8,46,165:13,-1,165,-1:13,166:14,-1:2,166,8" +
"5,-1:11,166:7,85,166:14,-1,166,-1:13,165:14,-1:2,165,47,-1:11,165:7,47,165:" +
"14,-1,165,-1:13,166:3,86,166:10,-1:2,166:2,-1:11,166:16,86,166:5,-1,166,-1:" +
"13,165:3,48,165:10,-1:2,165:2,-1:11,165:16,48,165:5,-1,165,-1:13,165:4,97,1" +
"65:6,131,165:2,-1:2,165:2,-1:11,165:8,97,165:4,131,165:8,-1,165,-1:13,166:4" +
",98,166:6,142,166:2,-1:2,166:2,-1:11,166:8,98,166:4,142,166:8,-1,166,-1:13," +
"165:4,100,165:6,102,165:2,-1:2,165:2,-1:11,165:8,100,165:4,102,165:8,-1,165" +
",-1:13,166:4,101,166:6,103,166:2,-1:2,166:2,-1:11,166:8,101,166:4,103,166:8" +
",-1,166,-1:13,165:3,104,165:10,-1:2,165:2,-1:11,165:16,104,165:5,-1,165,-1:" +
"13,166:4,105,166:9,-1:2,166:2,-1:11,166:8,105,166:13,-1,166,-1:13,165:11,10" +
"6,165:2,-1:2,165:2,-1:11,165:13,106,165:8,-1,165,-1:13,166:2,148,166:11,-1:" +
"2,166:2,-1:11,166:4,148,166:17,-1,166,-1:13,165:3,108,165:10,-1:2,165:2,-1:" +
"11,165:16,108,165:5,-1,165,-1:13,166:3,107,166:10,-1:2,166:2,-1:11,166:16,1" +
"07,166:5,-1,166,-1:13,165:2,110,165:11,-1:2,165:2,-1:11,165:4,110,165:17,-1" +
",165,-1:13,166:3,109,166:10,-1:2,166:2,-1:11,166:16,109,166:5,-1,166,-1:13," +
"165:14,-1:2,151,165,-1:11,165:18,151,165:3,-1,165,-1:13,166:2,111,166:11,-1" +
":2,166:2,-1:11,166:4,111,166:17,-1,166,-1:13,165:11,112,165:2,-1:2,165:2,-1" +
":11,165:13,112,165:8,-1,165,-1:13,166:14,-1:2,150,166,-1:11,166:18,150,166:" +
"3,-1,166,-1:13,165:6,155,165:7,-1:2,165:2,-1:11,165:10,155,165:11,-1,165,-1" +
":13,166:11,113,166:2,-1:2,166:2,-1:11,166:13,113,166:8,-1,166,-1:13,165:4,1" +
"14,165:9,-1:2,165:2,-1:11,165:8,114,165:13,-1,165,-1:13,166:11,115,166:2,-1" +
":2,166:2,-1:11,166:13,115,166:8,-1,166,-1:13,165:14,-1:2,165:2,-1:11,165:2," +
"116,165:14,116,165:4,-1,165,-1:13,166:6,154,166:7,-1:2,166:2,-1:11,166:10,1" +
"54,166:11,-1,166,-1:13,165,157,165:12,-1:2,165:2,-1:11,165:11,157,165:10,-1" +
",165,-1:13,166:3,117,166:10,-1:2,166:2,-1:11,166:16,117,166:5,-1,166,-1:13," +
"165:3,118,165:10,-1:2,165:2,-1:11,165:16,118,165:5,-1,165,-1:13,166:11,156," +
"166:2,-1:2,166:2,-1:11,166:13,156,166:8,-1,166,-1:13,165:11,159,165:2,-1:2," +
"165:2,-1:11,165:13,159,165:8,-1,165,-1:13,166:4,158,166:9,-1:2,166:2,-1:11," +
"166:8,158,166:13,-1,166,-1:13,165:4,161,165:9,-1:2,165:2,-1:11,165:8,161,16" +
"5:13,-1,165,-1:13,166,119,166:12,-1:2,166:2,-1:11,166:11,119,166:10,-1,166," +
"-1:13,165,120,165:12,-1:2,165:2,-1:11,165:11,120,165:10,-1,165,-1:13,166:6," +
"121,166:7,-1:2,166:2,-1:11,166:10,121,166:11,-1,166,-1:13,165:3,116,165:10," +
"-1:2,165:2,-1:11,165:16,116,165:5,-1,165,-1:13,166:9,160,166:4,-1:2,166:2,-" +
"1:11,166:15,160,166:6,-1,166,-1:13,165:6,122,165:7,-1:2,165:2,-1:11,165:10," +
"122,165:11,-1,165,-1:13,166:6,162,166:7,-1:2,166:2,-1:11,166:10,162,166:11," +
"-1,166,-1:13,165:9,163,165:4,-1:2,165:2,-1:11,165:15,163,165:6,-1,165,-1:13" +
",166:10,123,166:3,-1:2,166:2,-1:11,166,123,166:20,-1,166,-1:13,165:6,164,16" +
"5:7,-1:2,165:2,-1:11,165:10,164,165:11,-1,165,-1:13,165:10,124,165:3,-1:2,1" +
"65:2,-1:11,165,124,165:20,-1,165,-1:13,165,133,165,135,165:10,-1:2,165:2,-1" +
":11,165:11,133,165:4,135,165:5,-1,165,-1:13,166,132,134,166:11,-1:2,166:2,-" +
"1:11,166:4,134,166:6,132,166:10,-1,166,-1:13,165:11,139,165:2,-1:2,165:2,-1" +
":11,165:13,139,165:8,-1,165,-1:13,166,136,166,138,166:10,-1:2,166:2,-1:11,1" +
"66:11,136,166:4,138,166:5,-1,166,-1:13,165:8,141,165:5,-1:2,165:2,-1:11,165" +
":9,141,165:12,-1,165,-1:13,166:11,144,166:2,-1:2,166:2,-1:11,166:13,144,166" +
":8,-1,166,-1:13,165:8,143,145,165:4,-1:2,165:2,-1:11,165:9,143,165:5,145,16" +
"5:6,-1,165,-1:13,166:8,146,166:5,-1:2,166:2,-1:11,166:9,146,166:12,-1,166,-" +
"1:13,165:2,149,165:11,-1:2,165:2,-1:11,165:4,149,165:17,-1,165,-1:3");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
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
					case -3:
						break;
					case 3:
						{ ; }
					case -4:
						break;
					case 4:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -5:
						break;
					case 5:
						{ return new Symbol(TokenConstants.MULT); }
					case -6:
						break;
					case 6:
						{
    /* This rule should be the very last
       in your lexical specification and
       will match match everything not
       matched by other lexical rules.
    // System.err.println("LEXER BUG - UNMATCHED: " + yytext());*/
    return new Symbol(TokenConstants.ERROR, yytext());
}
					case -7:
						break;
					case 7:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -8:
						break;
					case 8:
						{ return new Symbol(TokenConstants.MINUS); }
					case -9:
						break;
					case 9:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -10:
						break;
					case 10:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.EQ); }
					case -12:
						break;
					case 12:
						{ return new Symbol(TokenConstants.LT); }
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.PLUS); }
					case -14:
						break;
					case 14:
						{ return new Symbol(TokenConstants.DIV); }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.DOT); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.NEG); }
					case -17:
						break;
					case 17:
						{ return new Symbol(TokenConstants.COMMA); }
					case -18:
						break;
					case 18:
						{ return new Symbol(TokenConstants.SEMI); }
					case -19:
						break;
					case 19:
						{ return new Symbol(TokenConstants.COLON); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.AT); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -23:
						break;
					case 23:
						{
    return new Symbol(TokenConstants.INT_CONST, new IntSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -24:
						break;
					case 24:
						{ yybegin(STRING); }
					case -25:
						break;
					case 25:
						{ yybegin(COMMENT); ++comment_level; }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.ERROR, "Unmatched *)"); }
					case -27:
						break;
					case 27:
						{ ; }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.FI); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.IF); }
					case -30:
						break;
					case 30:
						{ return new Symbol(TokenConstants.IN); }
					case -31:
						break;
					case 31:
						{ return new Symbol(TokenConstants.OF); }
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.DARROW); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.LE); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LET); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.NEW); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.NOT); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.CASE); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.LOOP); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.ELSE); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.ESAC); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.THEN); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.POOL); }
					case -44:
						break;
					case 44:
						{
    return new Symbol(TokenConstants.BOOL_CONST, Boolean.parseBoolean(yytext()));
}
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.CLASS); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.WHILE); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -49:
						break;
					case 49:
						{ ; }
					case -50:
						break;
					case 50:
						{ --comment_level; if (comment_level == 0) yybegin(YYINITIAL); }
					case -51:
						break;
					case 51:
						{
  string_buf.append(yytext());
}
					case -52:
						break;
					case 52:
						{ ; }
					case -53:
						break;
					case 53:
						{
  yybegin(YYINITIAL);
  String text = string_buf.toString();
  string_buf.setLength(0);
  if (text.length() >= MAX_STR_CONST) {
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  }
  return new Symbol(TokenConstants.STR_CONST, new StringSymbol(text, text.length(), text.hashCode()));
}
					case -54:
						break;
					case 54:
						{
  yybegin(ERROR_STRING);
  string_buf.setLength(0);
  return new Symbol(TokenConstants.ERROR, "String contains null character");
}
					case -55:
						break;
					case 55:
						{ string_buf.append("\n");}
					case -56:
						break;
					case 56:
						{ string_buf.append("\\"); }
					case -57:
						break;
					case 57:
						{ string_buf.append("\n"); }
					case -58:
						break;
					case 58:
						{ string_buf.append("\t"); }
					case -59:
						break;
					case 59:
						{ string_buf.append("\f"); }
					case -60:
						break;
					case 60:
						{ string_buf.append("\""); }
					case -61:
						break;
					case 61:
						{ string_buf.append("\b"); }
					case -62:
						break;
					case 62:
						{ string_buf.append("\t");}
					case -63:
						break;
					case 63:
						{ string_buf.append("\f");}
					case -64:
						break;
					case 64:
						{ ; }
					case -65:
						break;
					case 65:
						{ yybegin(YYINITIAL); }
					case -66:
						break;
					case 67:
						{ ; }
					case -67:
						break;
					case 68:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -68:
						break;
					case 69:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -69:
						break;
					case 70:
						{ return new Symbol(TokenConstants.FI); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.IF); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.IN); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.OF); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.LET); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.NEW); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.NOT); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.CASE); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.LOOP); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.ELSE); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.ESAC); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.THEN); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.POOL); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.CLASS); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.WHILE); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -86:
						break;
					case 87:
						{ ; }
					case -87:
						break;
					case 89:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -88:
						break;
					case 90:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -89:
						break;
					case 91:
						{ ; }
					case -90:
						break;
					case 93:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -91:
						break;
					case 94:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -92:
						break;
					case 95:
						{ ; }
					case -93:
						break;
					case 97:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -94:
						break;
					case 98:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -95:
						break;
					case 100:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -96:
						break;
					case 101:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -97:
						break;
					case 102:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -98:
						break;
					case 103:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -99:
						break;
					case 104:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -100:
						break;
					case 105:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -101:
						break;
					case 106:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -102:
						break;
					case 107:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -103:
						break;
					case 108:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -104:
						break;
					case 109:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -105:
						break;
					case 110:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -106:
						break;
					case 111:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -107:
						break;
					case 112:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -108:
						break;
					case 113:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -109:
						break;
					case 114:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -110:
						break;
					case 115:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -111:
						break;
					case 116:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -112:
						break;
					case 117:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -113:
						break;
					case 118:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -114:
						break;
					case 119:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -115:
						break;
					case 120:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -116:
						break;
					case 121:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -117:
						break;
					case 122:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -118:
						break;
					case 123:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -119:
						break;
					case 124:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -120:
						break;
					case 125:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -121:
						break;
					case 126:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -122:
						break;
					case 127:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -123:
						break;
					case 128:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -124:
						break;
					case 129:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -125:
						break;
					case 130:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -126:
						break;
					case 131:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -127:
						break;
					case 132:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -128:
						break;
					case 133:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -129:
						break;
					case 134:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -130:
						break;
					case 135:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -131:
						break;
					case 136:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -132:
						break;
					case 137:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -133:
						break;
					case 138:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -134:
						break;
					case 139:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -135:
						break;
					case 140:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -136:
						break;
					case 141:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -137:
						break;
					case 142:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -138:
						break;
					case 143:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -139:
						break;
					case 144:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -140:
						break;
					case 145:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -141:
						break;
					case 146:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -142:
						break;
					case 147:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -143:
						break;
					case 148:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -144:
						break;
					case 149:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -145:
						break;
					case 150:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -146:
						break;
					case 151:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -147:
						break;
					case 152:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -148:
						break;
					case 153:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -149:
						break;
					case 154:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -150:
						break;
					case 155:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -151:
						break;
					case 156:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -152:
						break;
					case 157:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -153:
						break;
					case 158:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -154:
						break;
					case 159:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -155:
						break;
					case 160:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -156:
						break;
					case 161:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -157:
						break;
					case 162:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -158:
						break;
					case 163:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -159:
						break;
					case 164:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -160:
						break;
					case 165:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -161:
						break;
					case 166:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -162:
						break;
					case 167:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -163:
						break;
					case 168:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -164:
						break;
					case 169:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -165:
						break;
					case 170:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -166:
						break;
					case 171:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -167:
						break;
					case 172:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -168:
						break;
					case 173:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -169:
						break;
					case 174:
						{
    return new Symbol(TokenConstants.TYPEID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -170:
						break;
					case 175:
						{
    return new Symbol(TokenConstants.OBJECTID, new IdSymbol(yytext(), yytext().length(), yytext().hashCode()));
}
					case -171:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
