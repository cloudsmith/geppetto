lexer grammar PPLexer;

options {
	tokenVocab=InternalPPLexer;
}

@header {
package org.cloudsmith.geppetto.pp.dsl.lexer;

// Use our own Lexer superclass by means of import. 
import org.eclipse.xtext.parser.antlr.Lexer;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
}

@members{
  private Map<String, Integer> literals = getLiteralsMap();

  private static Map<String, Integer> getLiteralsMap() {
 	Map<String, Integer> result = new HashMap<String, Integer>();  
  	result.put("inherits", KW_INHERITS );
	result.put("default", KW_DEFAULT );
	result.put("unless", KW_UNLESS );
	result.put("import", KW_IMPORT );
	result.put("define", KW_DEFINE );
	result.put("undef", KW_UNDEF );
	result.put("false", KW_FALSE );
	result.put("elsif", KW_ELSIF );
	result.put("class", KW_CLASS );
	result.put("true", KW_TRUE );
	result.put("node", KW_NODE );
	result.put("else", KW_ELSE );
	result.put("case", KW_CASE );
	result.put("and", KW_AND );
	result.put("if", KW_IF );
	result.put("in", KW_IN );
	result.put("or", KW_OR );
	return result;
  }
  private boolean isReAcceptable() {
  	if(singleQuotedString || doubleQuotedString)
  		return false;
  	// accept after ',' 'node', '{','}, '=~', '!~'
  	switch(lastSignificantToken) {
  		// NOTE: Must manually make sure these refer to the correct KEYWORD numbers
  		case KW_COMMA       : // ','
  		case KW_NODE        : // 'node'
  		case KW_LBRACE      : // '{'
  		case KW_RBRACE      : // '}'
  		case KW_MATCHES     : // '=~'
  		case KW_NOT_MATCHES : // '!~'
  		case KW_INHERITS    : // 'inherits'
  		case 0 : // nothing seen before, used when serializing
  			return true;
  		default:
  			return false;
  		}
  }
	private boolean singleQuotedString = false;

	private boolean doubleQuotedString = false;

	protected int lastSignificantToken = 0;

	private int dqIndex = 0;

	private boolean dqStack[] = new boolean[10];

	private int braceNesting = 0;

	private void enterBrace() {
		if(!isInterpolating())
			return;
		braceNesting++;
	}

	private void exitBrace() {
		if(!isInterpolating())
			return;
		braceNesting--;
		if(braceNesting == 0)
			popDq();
	}

	private boolean isInDqString() {
		return doubleQuotedString;
	}

	private boolean isInSqString() {
		return singleQuotedString;
	}

	private boolean isInterpolating() {
		return dqIndex > 0;
	}

	private boolean isNotInString() {
		return !singleQuotedString && !doubleQuotedString;
	}

	private void popDq() {
		if(dqIndex == 0)
			doubleQuotedString = false; // bad state, but stay alive
		else
			doubleQuotedString = dqStack[--dqIndex];
	}

	private void pushDq() {
		if(dqIndex >= dqStack.length)
			dqStack = Arrays.copyOf(dqStack, dqStack.length + 10);

		dqStack[dqIndex++] = doubleQuotedString;
		doubleQuotedString = false;
	}
	private int replaceLiteral(int originalType, String text) {
		// replace originalType with keyword if string completely matches a keyword and
		// we are not in a string...
	    Integer t = originalType;
		if(isNotInString()) {
			Integer t2 = literals.get(text);
			if(t2 != null)
			   t = t2;
		}
		return t;
	}
}
KW_INHERITS : {isNotInString()}?=>'inherits';

KW_DEFAULT  : {isNotInString()}?=> 'default';

KW_DEFINE   : {isNotInString()}?=>'define';

KW_IMPORT   : {isNotInString()}?=>'import';

KW_UNLESS   : {isNotInString()}?=> 'unless';

KW_CLASS    : {isNotInString()}?=>'class';

KW_ELSIF    : {isNotInString()}?=>'elsif';

KW_FALSE    : {isNotInString()}?=>'false';

KW_UNDEF    : {isNotInString()}?=>'undef';

KW_CASE        : {isNotInString()}?=>'case';

KW_ELSE        : {isNotInString()}?=>'else';

KW_NODE        : {isNotInString()}?=>'node';

KW_TRUE        : {isNotInString()}?=>'true';

KW_LLCOLLECT   : {isNotInString()}?=>'<<|';

KW_ESC_DLR_BRACE : '\\${';

KW_AND         : {isNotInString()}?=>'and';

KW_RRCOLLECT   : {isNotInString()}?=>'|>>';

KW_NOT_EQ      : {isNotInString()}?=>'!=';

KW_NOT_MATCHES : {isNotInString()}?=>'!~';

KW_DLR_BRACE : '${' {
	if(doubleQuotedString) {
		// in string expression interpolation mode
		pushDq();
		enterBrace();
	} else if(singleQuotedString) {
		_type = RULE_ANY_OTHER;
	}
};

KW_PLUS_EQ   : {isNotInString()}?=>'+=';

KW_APPEND    : {isNotInString()}?=>'+>';

KW_RARR      : {isNotInString()}?=>'->';

KW_IN_EDGE   : {isNotInString()}?=>'<-';

KW_LSHIFT    : {isNotInString()}?=>'<<';

KW_LT_EQ      : {isNotInString()}?=>'<=';

KW_LCOLLECT  : {isNotInString()}?=>'<|';

KW_IN_EDGE_SUB : {isNotInString()}?=>'<~';

KW_EQUALS    : {isNotInString()}?=>'==';

KW_FARROW    : {isNotInString()}?=>'=>';

KW_MATCHES   : {isNotInString()}?=>'=~';

KW_GT_EQ      : {isNotInString()}?=>'>=';

KW_RSHIFT    : {isNotInString()}?=>'>>';

KW_ESC_DQ    : '\\"';

KW_ESC_DLR   : '\\$';

KW_ESC_SQ    : '\\\'';

KW_ESC_ESC   : '\\\\';

KW_IF        : {isNotInString()}?=>'if';

KW_IN        : {isNotInString()}?=>'in';

KW_OR        : {isNotInString()}?=>'or';

KW_RCOLLECT  : {isNotInString()}?=>'|>';

KW_OUT_EDGE_SUB : {isNotInString()}?=>'~>';

KW_NOT       : {isNotInString()}?=>'!';

KW_DQ        : {!singleQuotedString}?=> '"' 
	{ 	// flip if in dq string or not
		doubleQuotedString = !doubleQuotedString;
	};

KW_MODULO    : {isNotInString()}?=>'%';

KW_SQ        : {!doubleQuotedString}?=>'\''
	{ 	// flip if in sq string or not
		singleQuotedString = !singleQuotedString;
	};

KW_LPAR      : {isNotInString()}?=>'(';

KW_RPAR      : {isNotInString()}?=>')';

KW_MUL       : {isNotInString()}?=>'*';

KW_PLUS      : {isNotInString()}?=>'+';

KW_COMMA     : {isNotInString()}?=>',';

KW_MINUS     : '-' {
	if(singleQuotedString || doubleQuotedString)
		_type = RULE_ANY_OTHER;
};

KW_DOT       : '.' {
	if(singleQuotedString || doubleQuotedString)
		_type = RULE_ANY_OTHER;
}; 

KW_SLASH     : {isNotInString()}?=>'/' {
	if(isReAcceptable()) {
		mRULE_REGULAR_EXPRESSION();
		return;
	}
};

KW_COLON     : {isNotInString()}?=>':';

KW_SEMI      : {isNotInString()}?=>';';

KW_LT        : {isNotInString()}?=>'<';

KW_EQ        : {isNotInString()}?=>'=';

KW_GT        : {isNotInString()}?=>'>';

KW_QMARK     : {isNotInString()}?=>'?';

KW_AT        : {isNotInString()}?=>'@';

KW_LBRACK    : {isNotInString()}?=>'[';

KW_RBRACK    : {isNotInString()}?=>']';

// Look ahead is needed to differentiate between '{' in general, and a '{' that starts a lambda.
// (Too many ambiguities and surprising backtracking result otherwise). This is solved by looking ahead
// here. If there is there a '|' following the '{'?, then the pseudo token RULE_LAMBDA is returned 
// instead of the token '{'. 
//  
KW_LBRACE    : {isNotInString()}?=>'{' {
	enterBrace();
	// This may be the start of a Ruby style lambda, help the parser by scanning ahead
	int c = 0;
    for(int i = 1; ; i++) {
		c = input.LT(i);
        if ( c =='\t' || c=='\n' || c=='\r' || c==' '|| c=='\u00A0')
			continue;
		if (c == '|') {
			_type = RULE_LAMBDA;
		}
		break;
	}
};

KW_PIPE      : {isNotInString()}?=>'|';

KW_RBRACE    : {isNotInString()}?=>'}' {exitBrace();};

// Standard /* */ comment that also eats trailing WS and endof line if that is all that is trailing
//
RULE_ML_COMMENT : {isNotInString()}?=> (('/*' ( options {greedy=false;} : . )*'*/') (' '|'\u00A0'|'\t')* ('\r'? '\n')?) ;

RULE_SL_COMMENT : {isNotInString()}?=> '#' ~(('\r'|'\n'))* ('\r'? '\n')?;

RULE_WS : (' '|'\u00A0'|'\t'|'\r'|'\n')+ {
	if(doubleQuotedString || singleQuotedString) {
		_type = RULE_ANY_OTHER;
	}
};

// Pseudo rule, this is done with lookahead on a left brace, since all WS after '{' should be delivered as such.
// Also see KW_LBRACE.
//
RULE_LAMBDA : {false}?=> '{' '|' ;

// Do not check if matched text is a keyword (it is allowed after a '$'
RULE_DOLLAR_VAR : '$' 
	((':' ':')=>RULE_NS)? ('0'..'9'|'a'..'z'|'A'..'Z'|'_')+ 
	((':' ':')=>RULE_NS ('0'..'9'|'a'..'z'|'A'..'Z'|'_')+)* ;

// Covers non decimal numbers and names
RULE_WORD_CHARS : ('0'..'9'|'a'..'z'|'A'..'Z'|'_'|(':' ':')=>RULE_NS) ('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-'|(':' ':')=>RULE_NS)*
{	// check if what was matched is a keyword - emit that instead
	_type = replaceLiteral(_type, getText());
};

// If lookahead is NUMERIC, lex as number but produce WORD_CHARS
//
RULE_NUMBER : {isNotInString()}?=> (NUMERIC)=>NUMERIC  {
	_type = RULE_WORD_CHARS;
};

RULE_REGULAR_EXPRESSION : {isReAcceptable()}?=>'/' RULE_RE_BODY '/' RULE_RE_FLAGS?;

fragment NUMERIC
 	: ('0' ('x'|'X'))=>('0' ('x'|'X') ('0'..'9'|'a'..'f'|'A'..'F')+)
	| ('0'..'9')+ ('.' ('0'..'9')+)? (('e'|'E') '-'? ('0'..'9')+)?
    ;

fragment RULE_NS : '::' ;

fragment RULE_RE_BODY : (RULE_RE_FIRST_CHAR RULE_RE_FOLLOW_CHAR*);

fragment RULE_RE_FIRST_CHAR : (~(('\n'|'*'|'/'|'\\'))|RULE_RE_BACKSLASH_SEQUENCE);

fragment RULE_RE_FOLLOW_CHAR : (RULE_RE_FIRST_CHAR|'*');

fragment RULE_RE_BACKSLASH_SEQUENCE : ('\\' ~('\n'));

fragment RULE_RE_FLAGS : ('a'..'z')+;

RULE_ANY_OTHER : .;
