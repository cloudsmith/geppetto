lexer grammar PPLexer;

options {
	tokenVocab=InternalPPLexer;
}

@header {
package org.cloudsmith.geppetto.pp.dsl.lexer;

// Use our own Lexer superclass by means of import. 
import org.eclipse.xtext.parser.antlr.Lexer;
}

@members{
  private boolean singleQuotedString = false;
  private boolean doubleQuotedString = false;
  protected int lastSignificantToken = 0;
  
  private boolean isNotInString() {
  	return !singleQuotedString && !doubleQuotedString ;
  }
  private boolean isInDqString() {
  	return !singleQuotedString && !doubleQuotedString  ;
  }
  private boolean isInSqString() {
  	return !singleQuotedString && !doubleQuotedString ;
  }
  private boolean isReAcceptable() {
  	if(singleQuotedString || doubleQuotedString)
  		return false;
  	// accept after ',' 'node', '{','}, '=~', '!~'
  	switch(lastSignificantToken) {
  		case KEYWORD_9 : // ','
  		case KEYWORD_48 : // 'node'
  		case KEYWORD_21 : // '{'
  		case KEYWORD_22 : // '}'
  		case KEYWORD_35 : // '=~'
  		case KEYWORD_24 : // '!~'
  			return true;
  		}
  	return false;
  }
}


KEYWORD_57 : {isNotInString()}?=>'inherits';

KEYWORD_56 : {isNotInString()}?=>'default';

KEYWORD_54 : {isNotInString()}?=>'define';

KEYWORD_55 : {isNotInString()}?=>'import';

KEYWORD_50 : {isNotInString()}?=>'class';

KEYWORD_51 : {isNotInString()}?=>'elsif';

KEYWORD_52 : {isNotInString()}?=>'false';

KEYWORD_53 : {isNotInString()}?=>'undef';

KEYWORD_46 : {isNotInString()}?=>'case';

KEYWORD_47 : {isNotInString()}?=>'else';

KEYWORD_48 : {isNotInString()}?=>'node';

KEYWORD_49 : {isNotInString()}?=>'true';

KEYWORD_43 : {isNotInString()}?=>'<<|';

KEYWORD_44 : {isNotInString()}?=>'and';

KEYWORD_45 : {isNotInString()}?=>'|>>';

KEYWORD_23 : {isNotInString()}?=>'!=';

KEYWORD_24 : {isNotInString()}?=>'!~';

KEYWORD_25 : {isNotInString()}?=>'+=';

KEYWORD_26 : {isNotInString()}?=>'+>';

KEYWORD_27 : {isNotInString()}?=>'->';

KEYWORD_28 : {isNotInString()}?=>'<-';

KEYWORD_29 : {isNotInString()}?=>'<<';

KEYWORD_30 : {isNotInString()}?=>'<=';

KEYWORD_31 : {isNotInString()}?=>'<|';

KEYWORD_32 : {isNotInString()}?=>'<~';

KEYWORD_33 : {isNotInString()}?=>'==';

KEYWORD_34 : {isNotInString()}?=>'=>';

KEYWORD_35 : {isNotInString()}?=>'=~';

KEYWORD_36 : {isNotInString()}?=>'>=';

KEYWORD_37 : {isNotInString()}?=>'>>';

KEYWORD_38 : {isNotInString()}?=>'if';

KEYWORD_39 : {isNotInString()}?=>'in';

KEYWORD_40 : {isNotInString()}?=>'or';

KEYWORD_41 : {isNotInString()}?=>'|>';

KEYWORD_42 : {isNotInString()}?=>'~>';

KEYWORD_1 : {isNotInString()}?=>'!';

KEYWORD_2 : {!isInSqString()}=> '"' 
	{ 	// flip if in dq string or not
		doubleQuotedString = !doubleQuotedString;
	};

KEYWORD_3 : {isNotInString()}?=>'$';

KEYWORD_4 : {!isInDqString()}?=>'\''
	{ 	// flip if in sq string or not
		singleQuotedString = !singleQuotedString;
	};

KEYWORD_5 : {isNotInString()}?=>'(';

KEYWORD_6 : {isNotInString()}?=>')';

KEYWORD_7 : {isNotInString()}?=>'*';

KEYWORD_8 : {isNotInString()}?=>'+';

KEYWORD_9 : {isNotInString()}?=>',';

KEYWORD_10 : {isNotInString()}?=>'-';

// higher precedence than just a '/' if RE is acceptable
RULE_REGULAR_EXPRESION : {isReAcceptable()}?=>'/' RULE_RE_BODY '/' RULE_RE_FLAGS;

KEYWORD_11 : {isNotInString()}?=>'/';

KEYWORD_12 : {isNotInString()}?=>':';

KEYWORD_13 : {isNotInString()}?=>';';

KEYWORD_14 : {isNotInString()}?=>'<';

KEYWORD_15 : {isNotInString()}?=>'=';

KEYWORD_16 : {isNotInString()}?=>'>';

KEYWORD_17 : {isNotInString()}?=>'?';

KEYWORD_18 : {isNotInString()}?=>'@';

KEYWORD_19 : {isNotInString()}?=>'[';

KEYWORD_20 : {isNotInString()}?=>']';

KEYWORD_21 : {isNotInString()}?=>'{';

KEYWORD_22 : {isNotInString()}?=>'}';



RULE_DOUBLE_STRING_CHARACTERS : {isInDqString()}?=>(~(('"'|'\\'|'$'))|('\\' .))+;

RULE_SINGLE_STRING_CHARACTERS : {isInSqString()}?=>(~(('\''|'\\'))|('\\' .))+;


RULE_ML_COMMENT : {isNotInString()}?=> ('/*' ( options {greedy=false;} : . )* '*/');

RULE_SL_COMMENT : {isNotInString()}?=> '#' ~(('\r'|'\n'))* ('\r'? '\n')?;

RULE_WS : (' '|'\u00A0'|'\t'|'\r'|'\n')+;

RULE_UNION_NAME_OR_REFERENCE : '::'? ('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-'|'.')+ ('::' ('0'..'9'|'a'..'z'|'A'..'Z'|'_'|'-'|'.')+)*;

RULE_ANY_OTHER : .;

//---FRAGMENTS
//
fragment RULE_RE_BODY : RULE_RE_FIRST_CHAR RULE_RE_FOLLOW_CHAR*;

fragment RULE_RE_FIRST_CHAR : (~(('\n'|'*'|'/'|'\\'))|RULE_RE_BACKSLASH_SEQUENCE);

fragment RULE_RE_FOLLOW_CHAR : (RULE_RE_FIRST_CHAR|'*');

fragment RULE_RE_BACKSLASH_SEQUENCE : '\\' ~('\n');

fragment RULE_RE_FLAGS : ('a'..'z')*;




