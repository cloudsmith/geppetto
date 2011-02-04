lexer grammar PPLexer;

options {
	tokenVocab=InternalPPLexer;
}

@header {
package org.cloudsmith.geppetto.pp.dsl.lexer;

// Use our own Lexer superclass by means of import. 
import org.eclipse.xtext.parser.antlr.Lexer;
import java.util.Arrays;
}

@members{
  private boolean isReAcceptable() {
  	if(singleQuotedString || doubleQuotedString)
  		return false;
  	// accept after ',' 'node', '{','}, '=~', '!~'
  	switch(lastSignificantToken) {
  		// NOTE: Must manually make sure these refer to the correct KEYWORD numbers
  		case KEYWORD_9 : // ','
  		case KEYWORD_55 : // 'node'
  		case KEYWORD_21 : // '{'
  		case KEYWORD_22 : // '}'
  		case KEYWORD_37 : // '=~'
  		case KEYWORD_24 : // '!~'
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
}
KEYWORD_64 : {isNotInString()}?=>'inherits';

KEYWORD_63 : {isNotInString()}?=>'default';

KEYWORD_61 : {isNotInString()}?=>'define';

KEYWORD_62 : {isNotInString()}?=>'import';

KEYWORD_57 : {isNotInString()}?=>'class';

KEYWORD_58 : {isNotInString()}?=>'elsif';

KEYWORD_59 : {isNotInString()}?=>'false';

KEYWORD_60 : {isNotInString()}?=>'undef';

KEYWORD_53 : {isNotInString()}?=>'case';

KEYWORD_54 : {isNotInString()}?=>'else';

KEYWORD_55 : {isNotInString()}?=>'node';

KEYWORD_56 : {isNotInString()}?=>'true';

KEYWORD_49 : {isNotInString()}?=>'<<|';

KEYWORD_50 : '\\${';

KEYWORD_51 : {isNotInString()}?=>'and';

KEYWORD_52 : {isNotInString()}?=>'|>>';

KEYWORD_23 : {isNotInString()}?=>'!=';

KEYWORD_24 : {isNotInString()}?=>'!~';

KEYWORD_25 : '${' {
	if(doubleQuotedString) {
		// in string expression interpolation mode
		pushDq();
		enterBrace();
	}
};

KEYWORD_26 : {isNotInString()}?=>'+=';

KEYWORD_27 : {isNotInString()}?=>'+>';

KEYWORD_28 : {isNotInString()}?=>'->';

KEYWORD_29 : '::';

KEYWORD_30 : {isNotInString()}?=>'<-';

KEYWORD_31 : {isNotInString()}?=>'<<';

KEYWORD_32 : {isNotInString()}?=>'<=';

KEYWORD_33 : {isNotInString()}?=>'<|';

KEYWORD_34 : {isNotInString()}?=>'<~';

KEYWORD_35 : {isNotInString()}?=>'==';

KEYWORD_36 : {isNotInString()}?=>'=>';

KEYWORD_37 : {isNotInString()}?=>'=~';

KEYWORD_38 : {isNotInString()}?=>'>=';

KEYWORD_39 : {isNotInString()}?=>'>>';

KEYWORD_40 : '\\"';

KEYWORD_41 : '\\$';

KEYWORD_42 : '\\\'';

KEYWORD_43 : '\\\\';

KEYWORD_44 : {isNotInString()}?=>'if';

KEYWORD_45 : {isNotInString()}?=>'in';

KEYWORD_46 : {isNotInString()}?=>'or';

KEYWORD_47 : {isNotInString()}?=>'|>';

KEYWORD_48 : {isNotInString()}?=>'~>';

KEYWORD_1 : {isNotInString()}?=>'!';

KEYWORD_2 : {!singleQuotedString}?=> '"' 
	{ 	// flip if in dq string or not
		doubleQuotedString = !doubleQuotedString;
	};

KEYWORD_3 : '$';

KEYWORD_4 : {!doubleQuotedString}?=>'\''
	{ 	// flip if in sq string or not
		singleQuotedString = !singleQuotedString;
	};

KEYWORD_5 : {isNotInString()}?=>'(';

KEYWORD_6 : {isNotInString()}?=>')';

KEYWORD_7 : {isNotInString()}?=>'*';

KEYWORD_8 : {isNotInString()}?=>'+';

KEYWORD_9 : {isNotInString()}?=>',';

KEYWORD_10 : '-' {
	if(singleQuotedString || doubleQuotedString)
		_type = RULE_ANY_OTHER;
};

KEYWORD_11 : {isNotInString()}?=>'/' {
	if(isReAcceptable()) {
		mRULE_REGULAR_EXPRESSION();
		return;
	}
};

KEYWORD_12 : {isNotInString()}?=>':';

KEYWORD_13 : {isNotInString()}?=>';';

KEYWORD_14 : {isNotInString()}?=>'<';

KEYWORD_15 : {isNotInString()}?=>'=';

KEYWORD_16 : {isNotInString()}?=>'>';

KEYWORD_17 : {isNotInString()}?=>'?';

KEYWORD_18 : {isNotInString()}?=>'@';

KEYWORD_19 : {isNotInString()}?=>'[';

KEYWORD_20 : {isNotInString()}?=>']';

KEYWORD_21 : {isNotInString()}?=>'{' {enterBrace();};

KEYWORD_22 : {isNotInString()}?=>'}' {exitBrace();};

RULE_ML_COMMENT : {isNotInString()}?=> ('/*' ( options {greedy=false;} : . )*'*/');

RULE_SL_COMMENT : {isNotInString()}?=> '#' ~(('\r'|'\n'))* ('\r'? '\n')?;

RULE_WS : (' '|'\u00A0'|'\t'|'\r'|'\n')+;

RULE_WORD_CHARS : ('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'.'|'-')+ ;

RULE_REGULAR_EXPRESSION : {isReAcceptable()}?=>'/' RULE_RE_BODY '/' RULE_RE_FLAGS?;

fragment RULE_RE_BODY : (RULE_RE_FIRST_CHAR RULE_RE_FOLLOW_CHAR*);

fragment RULE_RE_FIRST_CHAR : (~(('\n'|'*'|'/'|'\\'))|RULE_RE_BACKSLASH_SEQUENCE);

fragment RULE_RE_FOLLOW_CHAR : (RULE_RE_FIRST_CHAR|'*');

fragment RULE_RE_BACKSLASH_SEQUENCE : ('\\' ~('\n'));

fragment RULE_RE_FLAGS : ('a'..'z')+;

RULE_ANY_OTHER : .;

