/*
 * Copyright 2026 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated from src/main/antlr-db2/DB2Parser.g4 by ANTLR 4.13.2
package org.openrewrite.mainframe.db2.internal.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DB2Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, LINE_COMMENT=2, BLOCK_COMMENT=3, ADD=4, AFTER=5, ALL=6, ALLOCATE=7, 
		ALLOW=8, ALTER=9, AND=10, ANY=11, AS=12, ASENSITIVE=13, ASSOCIATE=14, 
		AUXILIARY=15, BEFORE=16, BEGIN=17, BETWEEN=18, BUFFERPOOL=19, BY=20, CALL=21, 
		CAPTURE=22, CASCADED=23, CASE=24, CAST=25, CCSID=26, CHAR=27, CHARACTER=28, 
		CHECK=29, CLONE=30, CLOSE=31, CLUSTER=32, COLLECTION=33, COLLID=34, COLUMN=35, 
		COMMENT=36, COMMIT=37, CONCAT=38, CONDITION=39, CONNECT=40, CONNECTION=41, 
		CONSTRAINT=42, CONTAINS=43, CONTINUE=44, CREATE=45, CUBE=46, CURRENT=47, 
		CURSOR=48, DATA=49, DATABASE=50, DAY=51, DAYS=52, DBINFO=53, DECLARE=54, 
		DEFAULT=55, DELETE=56, DESCRIPTOR=57, DETERMINISTIC=58, DISABLE=59, DISALLOW=60, 
		DISTINCT=61, DO=62, DOUBLE=63, DROP=64, DSSIZE=65, DYNAMIC=66, EDITPROC=67, 
		ELSE=68, ELSEIF=69, ENCODING=70, END=71, ENDING=72, ERASE=73, ESCAPE=74, 
		EXCEPT=75, EXCEPTION=76, EXEC=77, EXECUTE=78, EXISTS=79, EXIT=80, EXPLAIN=81, 
		EXTERNAL=82, FENCED=83, FETCH=84, FIELDPROC=85, FINAL=86, FIRST=87, FOR=88, 
		FREE=89, FROM=90, FULL=91, FUNCTION=92, GENERATED=93, GET=94, GLOBAL=95, 
		GO=96, GOTO=97, GRANT=98, GROUP=99, HANDLER=100, HAVING=101, HOLD=102, 
		HOUR=103, HOURS=104, IF=105, IMMEDIATE=106, IN=107, INCLUSIVE=108, INDEX=109, 
		INHERIT=110, INNER=111, INOUT=112, INSENSITIVE=113, INSERT=114, INTERSECT=115, 
		INTO=116, IS=117, ISOBID=118, ITERATE=119, JAR=120, JOIN=121, KEY=122, 
		LABEL=123, LANGUAGE=124, LAST=125, LC_CTYPE=126, LEAVE=127, LEFT=128, 
		LIKE=129, LOCAL=130, LOCALE=131, LOCATOR=132, LOCATORS=133, LOCK=134, 
		LOCKMAX=135, LOCKSIZE=136, LONG=137, LOOP=138, MAINTAINED=139, MATERIALIZED=140, 
		MICROSECOND=141, MICROSECONDS=142, MINUTE=143, MINUTES=144, MODIFIES=145, 
		MONTH=146, MONTHS=147, NEXT=148, NO=149, NONE=150, NOT=151, NULL=152, 
		NULLS=153, NUMPARTS=154, OBID=155, OF=156, OLD=157, ON=158, OPEN=159, 
		OPTIMIZATION=160, OPTIMIZE=161, OR=162, ORDER=163, ORGANIZATION=164, OUT=165, 
		OUTER=166, PACKAGE=167, PARAMETER=168, PART=169, PADDED=170, PARTITION=171, 
		PARTITIONED=172, PARTITIONING=173, PATH=174, PIECESIZE=175, PERIOD=176, 
		PLAN=177, PRECISION=178, PREPARE=179, PREVVAL=180, PRIQTY=181, PRIVILEGES=182, 
		PROCEDURE=183, PROGRAM=184, PSID=185, PUBLIC=186, QUERY=187, QUERYNO=188, 
		READS=189, REFERENCES=190, REFRESH=191, RESIGNAL=192, RELEASE=193, RENAME=194, 
		REPEAT=195, RESTRICT=196, RESULT=197, RETURN=198, RETURNS=199, REVOKE=200, 
		RIGHT=201, ROLE=202, ROLLBACK=203, ROLLUP=204, ROUND_CEILING=205, ROUND_DOWN=206, 
		ROUND_FLOOR=207, ROUND_HALF_DOWN=208, ROUND_HALF_EVEN=209, ROUND_HALF_UP=210, 
		ROUND_UP=211, ROW=212, ROWSET=213, RUN=214, SAVEPOINT=215, SCHEMA=216, 
		SCRATCHPAD=217, SECOND=218, SECONDS=219, SECQTY=220, SECURITY=221, SEQUENCE=222, 
		SELECT=223, SENSITIVE=224, SET=225, SIGNAL=226, SIMPLE=227, SOME=228, 
		SOURCE=229, SPECIFIC=230, STANDARD=231, STATIC=232, STAY=233, STOGROUP=234, 
		STORES=235, STYLE=236, SUMMARY=237, SYNONYM=238, SYSFUN=239, SYSIBM=240, 
		SYSPROC=241, SYSTEM=242, TABLE=243, TABLESPACE=244, THEN=245, TO=246, 
		TRIGGER=247, TRUNCATE=248, TYPE=249, UNDO=250, UNION=251, UNIQUE=252, 
		UNTIL=253, UPDATE=254, USER=255, USING=256, VALIDPROC=257, VALUE=258, 
		VALUES=259, VARIABLE=260, VARIANT=261, VCAT=262, VIEW=263, VOLATILE=264, 
		VOLUMES=265, WHEN=266, WHENEVER=267, WHERE=268, WHILE=269, WITH=270, WLM=271, 
		XMLCAST=272, XMLEXISTS=273, XMLNAMESPACES=274, YEAR=275, YEARS=276, ZONE=277, 
		AUTHENTICATION=278, AUTHID=279, BASED=280, CONTROL=281, UPON=282, ABSOLUTE=283, 
		ACCESS=284, ACTION=285, ADMIN=286, ALIAS=287, ALWAYS=288, APPEND=289, 
		ASC=290, ASUTIME=291, AT=292, ATOMIC=293, ATTRIBUTES=294, AUDIT=295, AUX=296, 
		BIT=297, CACHE=298, CALLED=299, CARDINALITY=300, CASCADE=301, CHANGE=302, 
		CHANGED=303, CHANGES=304, COMPARISONS=305, COMPRESS=306, CONTEXT=307, 
		COPY=308, CURSORS=309, CYCLE=310, DATACLAS=311, DB2=312, DB2SQL=313, DEBUG=314, 
		DEFER=315, DEFINE=316, DEFINER=317, DESC=318, EACH=319, ENABLE=320, ENFORCED=321, 
		ENVIRONMENT=322, EVERY=323, EXCLUDE=324, EXCLUDING=325, EXCLUSIVE=326, 
		FOREIGN=327, FREEPAGE=328, GBPCACHE=329, GENERATE=330, HASH=331, HIDDEN_KW=332, 
		HISTORY=333, ID=334, IDENTITY=335, IMPLICITLY=336, INCLUDE=337, INCLUDING=338, 
		INCREMENT=339, INDEXBP=340, INLINE=341, INPUT=342, INSTEAD=343, KEYS=344, 
		LARGE=345, LENGTH=346, LIMIT=347, LOAD=348, LOB=349, LOGGED=350, MAIN=351, 
		MASK=352, MAXPARTITIONS=353, MAXROWS=354, MAXVALUE=355, MEMBER=356, MGMTCLAS=357, 
		MINVALUE=358, MIXED=359, MODE=360, NAME=361, NEW=362, NEW_TABLE=363, OLD_TABLE=364, 
		ONLY=365, OPTION=366, OPTIONS=367, ORGANIZE=368, PAGE=369, PAGENUM=370, 
		PCTFREE=371, PERMISSION=372, PRIMARY=373, QUALIFIER=374, RANDOM=375, RANGE=376, 
		REFERENCING=377, REGENERATE=378, REGISTERS=379, RELATIVE=380, REMOVE=381, 
		REPLACE=382, RESET=383, RESIDENT=384, RESTART=385, RETAIN=386, ROTATE=387, 
		ROWS=388, SBCS=389, SECURED=390, SEGSIZE=391, SETS=392, SHARE=393, SIZE=394, 
		SPACE=395, SPECIAL=396, SQL=397, SQLID=398, START=399, STARTING=400, STATEMENT=401, 
		STORCLAS=402, SUB=403, TEMPORARY=404, TIME=405, TIMESTAMP=406, TRACKMOD=407, 
		TRANSACTION=408, TRUSTED=409, UNLOAD=410, USAGE=411, USE=412, VARCHAR=413, 
		VARGRAPHIC=414, VARYING=415, VERSIONING=416, WITHOUT=417, WORK=418, WORKFILE=419, 
		XMLPATTERN=420, YES=421, LPAREN=422, RPAREN=423, COMMA=424, SEMI=425, 
		DOT=426, COLON=427, STAR=428, PLUS=429, MINUS=430, SLASH=431, CONCAT_OP=432, 
		EQ=433, NEQ=434, LTE=435, GTE=436, LT=437, GT=438, QUESTION=439, STRING=440, 
		HEX_STRING=441, DELIMITED_IDENTIFIER=442, PLACEHOLDER=443, NUMBER=444, 
		IDENTIFIER=445, HOST_VARIABLE=446;
	public static final int
		RULE_compilationUnit = 0, RULE_statement = 1, RULE_queryStatement = 2, 
		RULE_terminator = 3, RULE_createTable = 4, RULE_tableContents = 5, RULE_copyOption = 6, 
		RULE_tableElement = 7, RULE_columnDefinition = 8, RULE_columnAttribute = 9, 
		RULE_generatedAs = 10, RULE_defaultValue = 11, RULE_periodDefinition = 12, 
		RULE_tableConstraint = 13, RULE_constraintBody = 14, RULE_referentialAction = 15, 
		RULE_constraintOption = 16, RULE_columnList = 17, RULE_tableOption = 18, 
		RULE_hashSpace = 19, RULE_partitionKey = 20, RULE_partitionClause = 21, 
		RULE_partitionSpec = 22, RULE_partitionBound = 23, RULE_partitionLimit = 24, 
		RULE_partitionValue = 25, RULE_createIndex = 26, RULE_indexModifier = 27, 
		RULE_indexKey = 28, RULE_indexOption = 29, RULE_createTablespace = 30, 
		RULE_tablespaceOption = 31, RULE_createDatabase = 32, RULE_databaseOption = 33, 
		RULE_createStogroup = 34, RULE_stogroupOption = 35, RULE_createView = 36, 
		RULE_viewOption = 37, RULE_createAlias = 38, RULE_createSynonym = 39, 
		RULE_createSequence = 40, RULE_sequenceOption = 41, RULE_createRole = 42, 
		RULE_createAuxiliaryTable = 43, RULE_createType = 44, RULE_createVariable = 45, 
		RULE_createMask = 46, RULE_createPermission = 47, RULE_createTrustedContext = 48, 
		RULE_trustedContextOption = 49, RULE_trustedAttribute = 50, RULE_trustedUser = 51, 
		RULE_caseExpression = 52, RULE_declareGlobalTemporaryTable = 53, RULE_createTrigger = 54, 
		RULE_triggerEvent = 55, RULE_triggerCorrelation = 56, RULE_triggerGranularity = 57, 
		RULE_createProcedure = 58, RULE_createFunction = 59, RULE_routineParameter = 60, 
		RULE_routineClause = 61, RULE_compoundStatement = 62, RULE_bodyItem = 63, 
		RULE_triggeredStatement = 64, RULE_alterTable = 65, RULE_alterTableAction = 66, 
		RULE_alterColumnAction = 67, RULE_alterTablespace = 68, RULE_alterIndex = 69, 
		RULE_alterDatabase = 70, RULE_alterStogroup = 71, RULE_alterSequence = 72, 
		RULE_alterView = 73, RULE_alterProcedure = 74, RULE_alterFunction = 75, 
		RULE_alterTrigger = 76, RULE_alterMask = 77, RULE_alterPermission = 78, 
		RULE_alterTrustedContext = 79, RULE_alterStogroupAction = 80, RULE_dropStatement = 81, 
		RULE_droppedObject = 82, RULE_grantStatement = 83, RULE_revokeStatement = 84, 
		RULE_privilege = 85, RULE_privilegeObject = 86, RULE_qualifiedNameList = 87, 
		RULE_grantee = 88, RULE_commentStatement = 89, RULE_commentTarget = 90, 
		RULE_labelStatement = 91, RULE_labelTarget = 92, RULE_renameStatement = 93, 
		RULE_setStatement = 94, RULE_specialRegister = 95, RULE_commitStatement = 96, 
		RULE_rollbackStatement = 97, RULE_savepointStatement = 98, RULE_releaseSavepointStatement = 99, 
		RULE_lockStatement = 100, RULE_insertStatement = 101, RULE_valuesRow = 102, 
		RULE_queryExpression = 103, RULE_setOperator = 104, RULE_querySpecification = 105, 
		RULE_selectList = 106, RULE_selectItem = 107, RULE_tableReference = 108, 
		RULE_joinType = 109, RULE_sortKey = 110, RULE_searchCondition = 111, RULE_predicate = 112, 
		RULE_comparisonOperator = 113, RULE_expression = 114, RULE_caseWhen = 115, 
		RULE_specialValue = 116, RULE_constant = 117, RULE_signedNumber = 118, 
		RULE_qualifiedName = 119, RULE_identifier = 120, RULE_dataType = 121, 
		RULE_typeName = 122, RULE_typeAttribute = 123, RULE_storageOption = 124, 
		RULE_end = 125, RULE_nonReserved = 126;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "statement", "queryStatement", "terminator", "createTable", 
			"tableContents", "copyOption", "tableElement", "columnDefinition", "columnAttribute", 
			"generatedAs", "defaultValue", "periodDefinition", "tableConstraint", 
			"constraintBody", "referentialAction", "constraintOption", "columnList", 
			"tableOption", "hashSpace", "partitionKey", "partitionClause", "partitionSpec", 
			"partitionBound", "partitionLimit", "partitionValue", "createIndex", 
			"indexModifier", "indexKey", "indexOption", "createTablespace", "tablespaceOption", 
			"createDatabase", "databaseOption", "createStogroup", "stogroupOption", 
			"createView", "viewOption", "createAlias", "createSynonym", "createSequence", 
			"sequenceOption", "createRole", "createAuxiliaryTable", "createType", 
			"createVariable", "createMask", "createPermission", "createTrustedContext", 
			"trustedContextOption", "trustedAttribute", "trustedUser", "caseExpression", 
			"declareGlobalTemporaryTable", "createTrigger", "triggerEvent", "triggerCorrelation", 
			"triggerGranularity", "createProcedure", "createFunction", "routineParameter", 
			"routineClause", "compoundStatement", "bodyItem", "triggeredStatement", 
			"alterTable", "alterTableAction", "alterColumnAction", "alterTablespace", 
			"alterIndex", "alterDatabase", "alterStogroup", "alterSequence", "alterView", 
			"alterProcedure", "alterFunction", "alterTrigger", "alterMask", "alterPermission", 
			"alterTrustedContext", "alterStogroupAction", "dropStatement", "droppedObject", 
			"grantStatement", "revokeStatement", "privilege", "privilegeObject", 
			"qualifiedNameList", "grantee", "commentStatement", "commentTarget", 
			"labelStatement", "labelTarget", "renameStatement", "setStatement", "specialRegister", 
			"commitStatement", "rollbackStatement", "savepointStatement", "releaseSavepointStatement", 
			"lockStatement", "insertStatement", "valuesRow", "queryExpression", "setOperator", 
			"querySpecification", "selectList", "selectItem", "tableReference", "joinType", 
			"sortKey", "searchCondition", "predicate", "comparisonOperator", "expression", 
			"caseWhen", "specialValue", "constant", "signedNumber", "qualifiedName", 
			"identifier", "dataType", "typeName", "typeAttribute", "storageOption", 
			"end", "nonReserved"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'ADD'", "'AFTER'", "'ALL'", "'ALLOCATE'", "'ALLOW'", 
			"'ALTER'", "'AND'", "'ANY'", "'AS'", "'ASENSITIVE'", "'ASSOCIATE'", "'AUXILIARY'", 
			"'BEFORE'", "'BEGIN'", "'BETWEEN'", "'BUFFERPOOL'", "'BY'", "'CALL'", 
			"'CAPTURE'", "'CASCADED'", "'CASE'", "'CAST'", "'CCSID'", "'CHAR'", "'CHARACTER'", 
			"'CHECK'", "'CLONE'", "'CLOSE'", "'CLUSTER'", "'COLLECTION'", "'COLLID'", 
			"'COLUMN'", "'COMMENT'", "'COMMIT'", "'CONCAT'", "'CONDITION'", "'CONNECT'", 
			"'CONNECTION'", "'CONSTRAINT'", "'CONTAINS'", "'CONTINUE'", "'CREATE'", 
			"'CUBE'", "'CURRENT'", "'CURSOR'", "'DATA'", "'DATABASE'", "'DAY'", "'DAYS'", 
			"'DBINFO'", "'DECLARE'", "'DEFAULT'", "'DELETE'", "'DESCRIPTOR'", "'DETERMINISTIC'", 
			"'DISABLE'", "'DISALLOW'", "'DISTINCT'", "'DO'", "'DOUBLE'", "'DROP'", 
			"'DSSIZE'", "'DYNAMIC'", "'EDITPROC'", "'ELSE'", "'ELSEIF'", "'ENCODING'", 
			"'END'", "'ENDING'", "'ERASE'", "'ESCAPE'", "'EXCEPT'", "'EXCEPTION'", 
			"'EXEC'", "'EXECUTE'", "'EXISTS'", "'EXIT'", "'EXPLAIN'", "'EXTERNAL'", 
			"'FENCED'", "'FETCH'", "'FIELDPROC'", "'FINAL'", "'FIRST'", "'FOR'", 
			"'FREE'", "'FROM'", "'FULL'", "'FUNCTION'", "'GENERATED'", "'GET'", "'GLOBAL'", 
			"'GO'", "'GOTO'", "'GRANT'", "'GROUP'", "'HANDLER'", "'HAVING'", "'HOLD'", 
			"'HOUR'", "'HOURS'", "'IF'", "'IMMEDIATE'", "'IN'", "'INCLUSIVE'", "'INDEX'", 
			"'INHERIT'", "'INNER'", "'INOUT'", "'INSENSITIVE'", "'INSERT'", "'INTERSECT'", 
			"'INTO'", "'IS'", "'ISOBID'", "'ITERATE'", "'JAR'", "'JOIN'", "'KEY'", 
			"'LABEL'", "'LANGUAGE'", "'LAST'", "'LC_CTYPE'", "'LEAVE'", "'LEFT'", 
			"'LIKE'", "'LOCAL'", "'LOCALE'", "'LOCATOR'", "'LOCATORS'", "'LOCK'", 
			"'LOCKMAX'", "'LOCKSIZE'", "'LONG'", "'LOOP'", "'MAINTAINED'", "'MATERIALIZED'", 
			"'MICROSECOND'", "'MICROSECONDS'", "'MINUTE'", "'MINUTES'", "'MODIFIES'", 
			"'MONTH'", "'MONTHS'", "'NEXT'", "'NO'", "'NONE'", "'NOT'", "'NULL'", 
			"'NULLS'", "'NUMPARTS'", "'OBID'", "'OF'", "'OLD'", "'ON'", "'OPEN'", 
			"'OPTIMIZATION'", "'OPTIMIZE'", "'OR'", "'ORDER'", "'ORGANIZATION'", 
			"'OUT'", "'OUTER'", "'PACKAGE'", "'PARAMETER'", "'PART'", "'PADDED'", 
			"'PARTITION'", "'PARTITIONED'", "'PARTITIONING'", "'PATH'", "'PIECESIZE'", 
			"'PERIOD'", "'PLAN'", "'PRECISION'", "'PREPARE'", "'PREVVAL'", "'PRIQTY'", 
			"'PRIVILEGES'", "'PROCEDURE'", "'PROGRAM'", "'PSID'", "'PUBLIC'", "'QUERY'", 
			"'QUERYNO'", "'READS'", "'REFERENCES'", "'REFRESH'", "'RESIGNAL'", "'RELEASE'", 
			"'RENAME'", "'REPEAT'", "'RESTRICT'", "'RESULT'", "'RETURN'", "'RETURNS'", 
			"'REVOKE'", "'RIGHT'", "'ROLE'", "'ROLLBACK'", "'ROLLUP'", "'ROUND_CEILING'", 
			"'ROUND_DOWN'", "'ROUND_FLOOR'", "'ROUND_HALF_DOWN'", "'ROUND_HALF_EVEN'", 
			"'ROUND_HALF_UP'", "'ROUND_UP'", "'ROW'", "'ROWSET'", "'RUN'", "'SAVEPOINT'", 
			"'SCHEMA'", "'SCRATCHPAD'", "'SECOND'", "'SECONDS'", "'SECQTY'", "'SECURITY'", 
			"'SEQUENCE'", "'SELECT'", "'SENSITIVE'", "'SET'", "'SIGNAL'", "'SIMPLE'", 
			"'SOME'", "'SOURCE'", "'SPECIFIC'", "'STANDARD'", "'STATIC'", "'STAY'", 
			"'STOGROUP'", "'STORES'", "'STYLE'", "'SUMMARY'", "'SYNONYM'", "'SYSFUN'", 
			"'SYSIBM'", "'SYSPROC'", "'SYSTEM'", "'TABLE'", "'TABLESPACE'", "'THEN'", 
			"'TO'", "'TRIGGER'", "'TRUNCATE'", "'TYPE'", "'UNDO'", "'UNION'", "'UNIQUE'", 
			"'UNTIL'", "'UPDATE'", "'USER'", "'USING'", "'VALIDPROC'", "'VALUE'", 
			"'VALUES'", "'VARIABLE'", "'VARIANT'", "'VCAT'", "'VIEW'", "'VOLATILE'", 
			"'VOLUMES'", "'WHEN'", "'WHENEVER'", "'WHERE'", "'WHILE'", "'WITH'", 
			"'WLM'", "'XMLCAST'", "'XMLEXISTS'", "'XMLNAMESPACES'", "'YEAR'", "'YEARS'", 
			"'ZONE'", "'AUTHENTICATION'", "'AUTHID'", "'BASED'", "'CONTROL'", "'UPON'", 
			"'ABSOLUTE'", "'ACCESS'", "'ACTION'", "'ADMIN'", "'ALIAS'", "'ALWAYS'", 
			"'APPEND'", "'ASC'", "'ASUTIME'", "'AT'", "'ATOMIC'", "'ATTRIBUTES'", 
			"'AUDIT'", "'AUX'", "'BIT'", "'CACHE'", "'CALLED'", "'CARDINALITY'", 
			"'CASCADE'", "'CHANGE'", "'CHANGED'", "'CHANGES'", "'COMPARISONS'", "'COMPRESS'", 
			"'CONTEXT'", "'COPY'", "'CURSORS'", "'CYCLE'", "'DATACLAS'", "'DB2'", 
			"'DB2SQL'", "'DEBUG'", "'DEFER'", "'DEFINE'", "'DEFINER'", "'DESC'", 
			"'EACH'", "'ENABLE'", "'ENFORCED'", "'ENVIRONMENT'", "'EVERY'", "'EXCLUDE'", 
			"'EXCLUDING'", "'EXCLUSIVE'", "'FOREIGN'", "'FREEPAGE'", "'GBPCACHE'", 
			"'GENERATE'", "'HASH'", "'HIDDEN'", "'HISTORY'", "'ID'", "'IDENTITY'", 
			"'IMPLICITLY'", "'INCLUDE'", "'INCLUDING'", "'INCREMENT'", "'INDEXBP'", 
			"'INLINE'", "'INPUT'", "'INSTEAD'", "'KEYS'", "'LARGE'", "'LENGTH'", 
			"'LIMIT'", "'LOAD'", "'LOB'", "'LOGGED'", "'MAIN'", "'MASK'", "'MAXPARTITIONS'", 
			"'MAXROWS'", "'MAXVALUE'", "'MEMBER'", "'MGMTCLAS'", "'MINVALUE'", "'MIXED'", 
			"'MODE'", "'NAME'", "'NEW'", "'NEW_TABLE'", "'OLD_TABLE'", "'ONLY'", 
			"'OPTION'", "'OPTIONS'", "'ORGANIZE'", "'PAGE'", "'PAGENUM'", "'PCTFREE'", 
			"'PERMISSION'", "'PRIMARY'", "'QUALIFIER'", "'RANDOM'", "'RANGE'", "'REFERENCING'", 
			"'REGENERATE'", "'REGISTERS'", "'RELATIVE'", "'REMOVE'", "'REPLACE'", 
			"'RESET'", "'RESIDENT'", "'RESTART'", "'RETAIN'", "'ROTATE'", "'ROWS'", 
			"'SBCS'", "'SECURED'", "'SEGSIZE'", "'SETS'", "'SHARE'", "'SIZE'", "'SPACE'", 
			"'SPECIAL'", "'SQL'", "'SQLID'", "'START'", "'STARTING'", "'STATEMENT'", 
			"'STORCLAS'", "'SUB'", "'TEMPORARY'", "'TIME'", "'TIMESTAMP'", "'TRACKMOD'", 
			"'TRANSACTION'", "'TRUSTED'", "'UNLOAD'", "'USAGE'", "'USE'", "'VARCHAR'", 
			"'VARGRAPHIC'", "'VARYING'", "'VERSIONING'", "'WITHOUT'", "'WORK'", "'WORKFILE'", 
			"'XMLPATTERN'", "'YES'", "'('", "')'", "','", "';'", "'.'", "':'", "'*'", 
			"'+'", "'-'", "'/'", "'||'", "'='", null, "'<='", "'>='", "'<'", "'>'", 
			"'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "LINE_COMMENT", "BLOCK_COMMENT", "ADD", "AFTER", "ALL", "ALLOCATE", 
			"ALLOW", "ALTER", "AND", "ANY", "AS", "ASENSITIVE", "ASSOCIATE", "AUXILIARY", 
			"BEFORE", "BEGIN", "BETWEEN", "BUFFERPOOL", "BY", "CALL", "CAPTURE", 
			"CASCADED", "CASE", "CAST", "CCSID", "CHAR", "CHARACTER", "CHECK", "CLONE", 
			"CLOSE", "CLUSTER", "COLLECTION", "COLLID", "COLUMN", "COMMENT", "COMMIT", 
			"CONCAT", "CONDITION", "CONNECT", "CONNECTION", "CONSTRAINT", "CONTAINS", 
			"CONTINUE", "CREATE", "CUBE", "CURRENT", "CURSOR", "DATA", "DATABASE", 
			"DAY", "DAYS", "DBINFO", "DECLARE", "DEFAULT", "DELETE", "DESCRIPTOR", 
			"DETERMINISTIC", "DISABLE", "DISALLOW", "DISTINCT", "DO", "DOUBLE", "DROP", 
			"DSSIZE", "DYNAMIC", "EDITPROC", "ELSE", "ELSEIF", "ENCODING", "END", 
			"ENDING", "ERASE", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", "EXECUTE", 
			"EXISTS", "EXIT", "EXPLAIN", "EXTERNAL", "FENCED", "FETCH", "FIELDPROC", 
			"FINAL", "FIRST", "FOR", "FREE", "FROM", "FULL", "FUNCTION", "GENERATED", 
			"GET", "GLOBAL", "GO", "GOTO", "GRANT", "GROUP", "HANDLER", "HAVING", 
			"HOLD", "HOUR", "HOURS", "IF", "IMMEDIATE", "IN", "INCLUSIVE", "INDEX", 
			"INHERIT", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INTERSECT", "INTO", 
			"IS", "ISOBID", "ITERATE", "JAR", "JOIN", "KEY", "LABEL", "LANGUAGE", 
			"LAST", "LC_CTYPE", "LEAVE", "LEFT", "LIKE", "LOCAL", "LOCALE", "LOCATOR", 
			"LOCATORS", "LOCK", "LOCKMAX", "LOCKSIZE", "LONG", "LOOP", "MAINTAINED", 
			"MATERIALIZED", "MICROSECOND", "MICROSECONDS", "MINUTE", "MINUTES", "MODIFIES", 
			"MONTH", "MONTHS", "NEXT", "NO", "NONE", "NOT", "NULL", "NULLS", "NUMPARTS", 
			"OBID", "OF", "OLD", "ON", "OPEN", "OPTIMIZATION", "OPTIMIZE", "OR", 
			"ORDER", "ORGANIZATION", "OUT", "OUTER", "PACKAGE", "PARAMETER", "PART", 
			"PADDED", "PARTITION", "PARTITIONED", "PARTITIONING", "PATH", "PIECESIZE", 
			"PERIOD", "PLAN", "PRECISION", "PREPARE", "PREVVAL", "PRIQTY", "PRIVILEGES", 
			"PROCEDURE", "PROGRAM", "PSID", "PUBLIC", "QUERY", "QUERYNO", "READS", 
			"REFERENCES", "REFRESH", "RESIGNAL", "RELEASE", "RENAME", "REPEAT", "RESTRICT", 
			"RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLE", "ROLLBACK", 
			"ROLLUP", "ROUND_CEILING", "ROUND_DOWN", "ROUND_FLOOR", "ROUND_HALF_DOWN", 
			"ROUND_HALF_EVEN", "ROUND_HALF_UP", "ROUND_UP", "ROW", "ROWSET", "RUN", 
			"SAVEPOINT", "SCHEMA", "SCRATCHPAD", "SECOND", "SECONDS", "SECQTY", "SECURITY", 
			"SEQUENCE", "SELECT", "SENSITIVE", "SET", "SIGNAL", "SIMPLE", "SOME", 
			"SOURCE", "SPECIFIC", "STANDARD", "STATIC", "STAY", "STOGROUP", "STORES", 
			"STYLE", "SUMMARY", "SYNONYM", "SYSFUN", "SYSIBM", "SYSPROC", "SYSTEM", 
			"TABLE", "TABLESPACE", "THEN", "TO", "TRIGGER", "TRUNCATE", "TYPE", "UNDO", 
			"UNION", "UNIQUE", "UNTIL", "UPDATE", "USER", "USING", "VALIDPROC", "VALUE", 
			"VALUES", "VARIABLE", "VARIANT", "VCAT", "VIEW", "VOLATILE", "VOLUMES", 
			"WHEN", "WHENEVER", "WHERE", "WHILE", "WITH", "WLM", "XMLCAST", "XMLEXISTS", 
			"XMLNAMESPACES", "YEAR", "YEARS", "ZONE", "AUTHENTICATION", "AUTHID", 
			"BASED", "CONTROL", "UPON", "ABSOLUTE", "ACCESS", "ACTION", "ADMIN", 
			"ALIAS", "ALWAYS", "APPEND", "ASC", "ASUTIME", "AT", "ATOMIC", "ATTRIBUTES", 
			"AUDIT", "AUX", "BIT", "CACHE", "CALLED", "CARDINALITY", "CASCADE", "CHANGE", 
			"CHANGED", "CHANGES", "COMPARISONS", "COMPRESS", "CONTEXT", "COPY", "CURSORS", 
			"CYCLE", "DATACLAS", "DB2", "DB2SQL", "DEBUG", "DEFER", "DEFINE", "DEFINER", 
			"DESC", "EACH", "ENABLE", "ENFORCED", "ENVIRONMENT", "EVERY", "EXCLUDE", 
			"EXCLUDING", "EXCLUSIVE", "FOREIGN", "FREEPAGE", "GBPCACHE", "GENERATE", 
			"HASH", "HIDDEN_KW", "HISTORY", "ID", "IDENTITY", "IMPLICITLY", "INCLUDE", 
			"INCLUDING", "INCREMENT", "INDEXBP", "INLINE", "INPUT", "INSTEAD", "KEYS", 
			"LARGE", "LENGTH", "LIMIT", "LOAD", "LOB", "LOGGED", "MAIN", "MASK", 
			"MAXPARTITIONS", "MAXROWS", "MAXVALUE", "MEMBER", "MGMTCLAS", "MINVALUE", 
			"MIXED", "MODE", "NAME", "NEW", "NEW_TABLE", "OLD_TABLE", "ONLY", "OPTION", 
			"OPTIONS", "ORGANIZE", "PAGE", "PAGENUM", "PCTFREE", "PERMISSION", "PRIMARY", 
			"QUALIFIER", "RANDOM", "RANGE", "REFERENCING", "REGENERATE", "REGISTERS", 
			"RELATIVE", "REMOVE", "REPLACE", "RESET", "RESIDENT", "RESTART", "RETAIN", 
			"ROTATE", "ROWS", "SBCS", "SECURED", "SEGSIZE", "SETS", "SHARE", "SIZE", 
			"SPACE", "SPECIAL", "SQL", "SQLID", "START", "STARTING", "STATEMENT", 
			"STORCLAS", "SUB", "TEMPORARY", "TIME", "TIMESTAMP", "TRACKMOD", "TRANSACTION", 
			"TRUSTED", "UNLOAD", "USAGE", "USE", "VARCHAR", "VARGRAPHIC", "VARYING", 
			"VERSIONING", "WITHOUT", "WORK", "WORKFILE", "XMLPATTERN", "YES", "LPAREN", 
			"RPAREN", "COMMA", "SEMI", "DOT", "COLON", "STAR", "PLUS", "MINUS", "SLASH", 
			"CONCAT_OP", "EQ", "NEQ", "LTE", "GTE", "LT", "GT", "QUESTION", "STRING", 
			"HEX_STRING", "DELIMITED_IDENTIFIER", "PLACEHOLDER", "NUMBER", "IDENTIFIER", 
			"HOST_VARIABLE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DB2Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    private static IntervalSet identifierStart;

	    /**
	     * The predicates that take a rule's context are ones ANTLR treats as context-dependent. SLL prediction
	     * evaluates each only in its own rule's decisions, so the predicates of nested rules never combine.
	     */
	    boolean ahead(ParserRuleContext rule, int type) {
	        return _input.LA(1) == type;
	    }

	    private boolean startsIdentifier(int type) {
	        if (identifierStart == null) {
	            identifierStart = _ATN.nextTokens(_ATN.ruleToStartState[RULE_identifier]);
	        }
	        return identifierStart.contains(type);
	    }

	    /**
	     * Whether the END ahead closes the compound statement, rather than a control flow statement in its body.
	     */
	    boolean closesBlock(ParserRuleContext block) {
	        if (_input.LA(1) != END) {
	            return false;
	        }
	        switch (_input.LA(2)) {
	            case IF: case WHILE: case FOR: case CASE: case LOOP: case REPEAT:
	                return false;
	            default:
	                return true;
	        }
	    }

	    /**
	     * Whether a dot and another part continue the qualified name. The table of a COMMENT ON COLUMN or LABEL ON
	     * COLUMN target leaves its last part to the column, and a dot before a star belongs to the select item.
	     */
	    boolean continuesName(ParserRuleContext name) {
	        if (_input.LA(1) != DOT || !startsIdentifier(_input.LA(2))) {
	            return false;
	        }
	        ParserRuleContext target = name.getParent();
	        boolean columnTable = (target instanceof CommentTargetContext || target instanceof LabelTargetContext) &&
	                target.getStart().getType() == COLUMN;
	        return !columnTable || _input.LA(3) == DOT;
	    }

	    private static IntervalSet[] namedOptionStart;

	    /**
	     * Whether the word ahead starts one of the option rule's named alternatives, all but its last. The last
	     * alternative takes any word, so that an unknown option still parses, and a named option's value word would
	     * otherwise read as an unknown option of its own.
	     */
	    boolean startsNamedOption(ParserRuleContext option) {
	        if (namedOptionStart == null) {
	            namedOptionStart = new IntervalSet[ruleNames.length];
	        }
	        int rule = option.getRuleIndex();
	        if (namedOptionStart[rule] == null) {
	            ATNState block = _ATN.ruleToStartState[rule].transition(0).target;
	            IntervalSet start = new IntervalSet();
	            for (int alt = 0; alt < block.getNumberOfTransitions() - 1; alt++) {
	                start.addAll(_ATN.nextTokens(block.transition(alt).target));
	            }
	            namedOptionStart[rule] = start;
	        }
	        return namedOptionStart[rule].contains(_input.LA(1));
	    }

	    /**
	     * Whether a column's default value follows. A name only starts one as a function call, and anything else
	     * that could also be the next column attribute is taken as the value.
	     */
	    boolean startsDefaultValue(ParserRuleContext attribute) {
	        int type = _input.LA(1);
	        switch (type) {
	            case NUMBER: case PLUS: case MINUS: case STRING: case HEX_STRING: case PLACEHOLDER: case USER: case NULL:
	                return true;
	            case CURRENT:
	                return _input.LA(2) == SQLID;
	            default:
	                if (!startsIdentifier(type)) {
	                    return false;
	                }
	                int i = 2;
	                while (_input.LA(i) == DOT && startsIdentifier(_input.LA(i + 1))) {
	                    i += 2;
	                }
	                return _input.LA(i) == LPAREN;
	        }
	    }

	    /**
	     * Whether the index key ahead is a column name alone, which the key's expression alternative also matches.
	     */
	    boolean columnKeyAhead(ParserRuleContext key) {
	        if (!startsIdentifier(_input.LA(1))) {
	            return false;
	        }
	        switch (_input.LA(2)) {
	            case COMMA: case RPAREN: case ASC: case DESC: case RANDOM:
	                return true;
	            default:
	                return false;
	        }
	    }

	public DB2Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompilationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DB2Parser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCompilationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 9)) & ~0x3f) == 0 && ((1L << (_la - 9)) & 36064050513182721L) != 0) || ((((_la - 98)) & ~0x3f) == 0 && ((1L << (_la - 98)) & 68753096705L) != 0) || ((((_la - 193)) & ~0x3f) == 0 && ((1L << (_la - 193)) & 5372904579L) != 0) || _la==LPAREN || _la==SEMI) {
				{
				{
				setState(254);
				statement();
				}
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(260);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public CreateTableContext createTable() {
			return getRuleContext(CreateTableContext.class,0);
		}
		public CreateIndexContext createIndex() {
			return getRuleContext(CreateIndexContext.class,0);
		}
		public CreateTablespaceContext createTablespace() {
			return getRuleContext(CreateTablespaceContext.class,0);
		}
		public CreateDatabaseContext createDatabase() {
			return getRuleContext(CreateDatabaseContext.class,0);
		}
		public CreateStogroupContext createStogroup() {
			return getRuleContext(CreateStogroupContext.class,0);
		}
		public CreateViewContext createView() {
			return getRuleContext(CreateViewContext.class,0);
		}
		public CreateAliasContext createAlias() {
			return getRuleContext(CreateAliasContext.class,0);
		}
		public CreateSynonymContext createSynonym() {
			return getRuleContext(CreateSynonymContext.class,0);
		}
		public CreateSequenceContext createSequence() {
			return getRuleContext(CreateSequenceContext.class,0);
		}
		public CreateRoleContext createRole() {
			return getRuleContext(CreateRoleContext.class,0);
		}
		public CreateAuxiliaryTableContext createAuxiliaryTable() {
			return getRuleContext(CreateAuxiliaryTableContext.class,0);
		}
		public CreateTypeContext createType() {
			return getRuleContext(CreateTypeContext.class,0);
		}
		public CreateVariableContext createVariable() {
			return getRuleContext(CreateVariableContext.class,0);
		}
		public CreateMaskContext createMask() {
			return getRuleContext(CreateMaskContext.class,0);
		}
		public CreatePermissionContext createPermission() {
			return getRuleContext(CreatePermissionContext.class,0);
		}
		public CreateTrustedContextContext createTrustedContext() {
			return getRuleContext(CreateTrustedContextContext.class,0);
		}
		public CreateTriggerContext createTrigger() {
			return getRuleContext(CreateTriggerContext.class,0);
		}
		public CreateProcedureContext createProcedure() {
			return getRuleContext(CreateProcedureContext.class,0);
		}
		public CreateFunctionContext createFunction() {
			return getRuleContext(CreateFunctionContext.class,0);
		}
		public DeclareGlobalTemporaryTableContext declareGlobalTemporaryTable() {
			return getRuleContext(DeclareGlobalTemporaryTableContext.class,0);
		}
		public AlterTableContext alterTable() {
			return getRuleContext(AlterTableContext.class,0);
		}
		public AlterTablespaceContext alterTablespace() {
			return getRuleContext(AlterTablespaceContext.class,0);
		}
		public AlterIndexContext alterIndex() {
			return getRuleContext(AlterIndexContext.class,0);
		}
		public AlterDatabaseContext alterDatabase() {
			return getRuleContext(AlterDatabaseContext.class,0);
		}
		public AlterStogroupContext alterStogroup() {
			return getRuleContext(AlterStogroupContext.class,0);
		}
		public AlterSequenceContext alterSequence() {
			return getRuleContext(AlterSequenceContext.class,0);
		}
		public AlterViewContext alterView() {
			return getRuleContext(AlterViewContext.class,0);
		}
		public AlterProcedureContext alterProcedure() {
			return getRuleContext(AlterProcedureContext.class,0);
		}
		public AlterFunctionContext alterFunction() {
			return getRuleContext(AlterFunctionContext.class,0);
		}
		public AlterTriggerContext alterTrigger() {
			return getRuleContext(AlterTriggerContext.class,0);
		}
		public AlterMaskContext alterMask() {
			return getRuleContext(AlterMaskContext.class,0);
		}
		public AlterPermissionContext alterPermission() {
			return getRuleContext(AlterPermissionContext.class,0);
		}
		public AlterTrustedContextContext alterTrustedContext() {
			return getRuleContext(AlterTrustedContextContext.class,0);
		}
		public DropStatementContext dropStatement() {
			return getRuleContext(DropStatementContext.class,0);
		}
		public GrantStatementContext grantStatement() {
			return getRuleContext(GrantStatementContext.class,0);
		}
		public RevokeStatementContext revokeStatement() {
			return getRuleContext(RevokeStatementContext.class,0);
		}
		public CommentStatementContext commentStatement() {
			return getRuleContext(CommentStatementContext.class,0);
		}
		public LabelStatementContext labelStatement() {
			return getRuleContext(LabelStatementContext.class,0);
		}
		public RenameStatementContext renameStatement() {
			return getRuleContext(RenameStatementContext.class,0);
		}
		public SetStatementContext setStatement() {
			return getRuleContext(SetStatementContext.class,0);
		}
		public CommitStatementContext commitStatement() {
			return getRuleContext(CommitStatementContext.class,0);
		}
		public RollbackStatementContext rollbackStatement() {
			return getRuleContext(RollbackStatementContext.class,0);
		}
		public SavepointStatementContext savepointStatement() {
			return getRuleContext(SavepointStatementContext.class,0);
		}
		public ReleaseSavepointStatementContext releaseSavepointStatement() {
			return getRuleContext(ReleaseSavepointStatementContext.class,0);
		}
		public LockStatementContext lockStatement() {
			return getRuleContext(LockStatementContext.class,0);
		}
		public InsertStatementContext insertStatement() {
			return getRuleContext(InsertStatementContext.class,0);
		}
		public QueryStatementContext queryStatement() {
			return getRuleContext(QueryStatementContext.class,0);
		}
		public TerminatorContext terminator() {
			return getRuleContext(TerminatorContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				createTable();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				createIndex();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(264);
				createTablespace();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(265);
				createDatabase();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(266);
				createStogroup();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(267);
				createView();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(268);
				createAlias();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(269);
				createSynonym();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(270);
				createSequence();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(271);
				createRole();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(272);
				createAuxiliaryTable();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(273);
				createType();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(274);
				createVariable();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(275);
				createMask();
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(276);
				createPermission();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(277);
				createTrustedContext();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(278);
				createTrigger();
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(279);
				createProcedure();
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(280);
				createFunction();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(281);
				declareGlobalTemporaryTable();
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(282);
				alterTable();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(283);
				alterTablespace();
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(284);
				alterIndex();
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(285);
				alterDatabase();
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(286);
				alterStogroup();
				}
				break;
			case 26:
				enterOuterAlt(_localctx, 26);
				{
				setState(287);
				alterSequence();
				}
				break;
			case 27:
				enterOuterAlt(_localctx, 27);
				{
				setState(288);
				alterView();
				}
				break;
			case 28:
				enterOuterAlt(_localctx, 28);
				{
				setState(289);
				alterProcedure();
				}
				break;
			case 29:
				enterOuterAlt(_localctx, 29);
				{
				setState(290);
				alterFunction();
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 30);
				{
				setState(291);
				alterTrigger();
				}
				break;
			case 31:
				enterOuterAlt(_localctx, 31);
				{
				setState(292);
				alterMask();
				}
				break;
			case 32:
				enterOuterAlt(_localctx, 32);
				{
				setState(293);
				alterPermission();
				}
				break;
			case 33:
				enterOuterAlt(_localctx, 33);
				{
				setState(294);
				alterTrustedContext();
				}
				break;
			case 34:
				enterOuterAlt(_localctx, 34);
				{
				setState(295);
				dropStatement();
				}
				break;
			case 35:
				enterOuterAlt(_localctx, 35);
				{
				setState(296);
				grantStatement();
				}
				break;
			case 36:
				enterOuterAlt(_localctx, 36);
				{
				setState(297);
				revokeStatement();
				}
				break;
			case 37:
				enterOuterAlt(_localctx, 37);
				{
				setState(298);
				commentStatement();
				}
				break;
			case 38:
				enterOuterAlt(_localctx, 38);
				{
				setState(299);
				labelStatement();
				}
				break;
			case 39:
				enterOuterAlt(_localctx, 39);
				{
				setState(300);
				renameStatement();
				}
				break;
			case 40:
				enterOuterAlt(_localctx, 40);
				{
				setState(301);
				setStatement();
				}
				break;
			case 41:
				enterOuterAlt(_localctx, 41);
				{
				setState(302);
				commitStatement();
				}
				break;
			case 42:
				enterOuterAlt(_localctx, 42);
				{
				setState(303);
				rollbackStatement();
				}
				break;
			case 43:
				enterOuterAlt(_localctx, 43);
				{
				setState(304);
				savepointStatement();
				}
				break;
			case 44:
				enterOuterAlt(_localctx, 44);
				{
				setState(305);
				releaseSavepointStatement();
				}
				break;
			case 45:
				enterOuterAlt(_localctx, 45);
				{
				setState(306);
				lockStatement();
				}
				break;
			case 46:
				enterOuterAlt(_localctx, 46);
				{
				setState(307);
				insertStatement();
				}
				break;
			case 47:
				enterOuterAlt(_localctx, 47);
				{
				setState(308);
				queryStatement();
				}
				break;
			case 48:
				enterOuterAlt(_localctx, 48);
				{
				setState(309);
				terminator();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryStatementContext extends ParserRuleContext {
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public QueryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterQueryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitQueryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitQueryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryStatementContext queryStatement() throws RecognitionException {
		QueryStatementContext _localctx = new QueryStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_queryStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			queryExpression();
			setState(313);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TerminatorContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(DB2Parser.SEMI, 0); }
		public TerminatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTerminator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTerminator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTerminator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminatorContext terminator() throws RecognitionException {
		TerminatorContext _localctx = new TerminatorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_terminator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTableContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TableContentsContext tableContents() {
			return getRuleContext(TableContentsContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TableOptionContext> tableOption() {
			return getRuleContexts(TableOptionContext.class);
		}
		public TableOptionContext tableOption(int i) {
			return getRuleContext(TableOptionContext.class,i);
		}
		public CreateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTableContext createTable() throws RecognitionException {
		CreateTableContext _localctx = new CreateTableContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_createTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(317);
			match(CREATE);
			setState(318);
			match(TABLE);
			setState(319);
			qualifiedName();
			setState(320);
			tableContents();
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9570167533076480L) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & 875951227036762185L) != 0) || ((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & 172897663934661377L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 146710104736350353L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953421825L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(321);
				tableOption();
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(327);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableContentsContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<TableElementContext> tableElement() {
			return getRuleContexts(TableElementContext.class);
		}
		public TableElementContext tableElement(int i) {
			return getRuleContext(TableElementContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode LIKE() { return getToken(DB2Parser.LIKE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public List<CopyOptionContext> copyOption() {
			return getRuleContexts(CopyOptionContext.class);
		}
		public CopyOptionContext copyOption(int i) {
			return getRuleContext(CopyOptionContext.class,i);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public TableContentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableContents; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTableContents(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTableContents(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTableContents(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableContentsContext tableContents() throws RecognitionException {
		TableContentsContext _localctx = new TableContentsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_tableContents);
		int _la;
		try {
			int _alt;
			setState(358);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(LPAREN);
				setState(330);
				tableElement();
				setState(335);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(331);
					match(COMMA);
					setState(332);
					tableElement();
					}
					}
					setState(337);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(338);
				match(RPAREN);
				}
				break;
			case LIKE:
				enterOuterAlt(_localctx, 2);
				{
				setState(340);
				match(LIKE);
				setState(341);
				qualifiedName();
				setState(345);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(342);
						copyOption();
						}
						} 
					}
					setState(347);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				}
				break;
			case AS:
				enterOuterAlt(_localctx, 3);
				{
				setState(348);
				match(AS);
				setState(349);
				match(LPAREN);
				setState(350);
				queryExpression();
				setState(351);
				match(RPAREN);
				setState(355);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(352);
						copyOption();
						}
						} 
					}
					setState(357);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CopyOptionContext extends ParserRuleContext {
		public TerminalNode INCLUDING() { return getToken(DB2Parser.INCLUDING, 0); }
		public TerminalNode EXCLUDING() { return getToken(DB2Parser.EXCLUDING, 0); }
		public List<NonReservedContext> nonReserved() {
			return getRuleContexts(NonReservedContext.class);
		}
		public NonReservedContext nonReserved(int i) {
			return getRuleContext(NonReservedContext.class,i);
		}
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public CopyOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_copyOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCopyOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCopyOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCopyOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CopyOptionContext copyOption() throws RecognitionException {
		CopyOptionContext _localctx = new CopyOptionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_copyOption);
		int _la;
		try {
			int _alt;
			setState(376);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(360);
				_la = _input.LA(1);
				if ( !(_la==EXCLUDING || _la==INCLUDING) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(362); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(361);
						nonReserved();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(364); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				match(WITH);
				setState(368);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NO) {
					{
					setState(367);
					match(NO);
					}
				}

				setState(370);
				match(DATA);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(372); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(371);
						nonReserved();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(374); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableElementContext extends ParserRuleContext {
		public TableConstraintContext tableConstraint() {
			return getRuleContext(TableConstraintContext.class,0);
		}
		public PeriodDefinitionContext periodDefinition() {
			return getRuleContext(PeriodDefinitionContext.class,0);
		}
		public ColumnDefinitionContext columnDefinition() {
			return getRuleContext(ColumnDefinitionContext.class,0);
		}
		public TableElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTableElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTableElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTableElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableElementContext tableElement() throws RecognitionException {
		TableElementContext _localctx = new TableElementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tableElement);
		try {
			setState(381);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(378);
				tableConstraint();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(379);
				periodDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(380);
				columnDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnDefinitionContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public List<ColumnAttributeContext> columnAttribute() {
			return getRuleContexts(ColumnAttributeContext.class);
		}
		public ColumnAttributeContext columnAttribute(int i) {
			return getRuleContext(ColumnAttributeContext.class,i);
		}
		public ColumnDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterColumnDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitColumnDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitColumnDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnDefinitionContext columnDefinition() throws RecognitionException {
		ColumnDefinitionContext _localctx = new ColumnDefinitionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_columnDefinition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(383);
			identifier();
			setState(384);
			dataType();
			setState(388);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(385);
					columnAttribute();
					}
					} 
				}
				setState(390);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnAttributeContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode DEFAULT() { return getToken(DB2Parser.DEFAULT, 0); }
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode GENERATED() { return getToken(DB2Parser.GENERATED, 0); }
		public TerminalNode ALWAYS() { return getToken(DB2Parser.ALWAYS, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public GeneratedAsContext generatedAs() {
			return getRuleContext(GeneratedAsContext.class,0);
		}
		public TerminalNode PRIMARY() { return getToken(DB2Parser.PRIMARY, 0); }
		public TerminalNode KEY() { return getToken(DB2Parser.KEY, 0); }
		public TerminalNode CONSTRAINT() { return getToken(DB2Parser.CONSTRAINT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode UNIQUE() { return getToken(DB2Parser.UNIQUE, 0); }
		public TerminalNode CHECK() { return getToken(DB2Parser.CHECK, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public SearchConditionContext searchCondition() {
			return getRuleContext(SearchConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode REFERENCES() { return getToken(DB2Parser.REFERENCES, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public List<ReferentialActionContext> referentialAction() {
			return getRuleContexts(ReferentialActionContext.class);
		}
		public ReferentialActionContext referentialAction(int i) {
			return getRuleContext(ReferentialActionContext.class,i);
		}
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode SBCS() { return getToken(DB2Parser.SBCS, 0); }
		public TerminalNode MIXED() { return getToken(DB2Parser.MIXED, 0); }
		public TerminalNode BIT() { return getToken(DB2Parser.BIT, 0); }
		public TerminalNode CCSID() { return getToken(DB2Parser.CCSID, 0); }
		public TerminalNode FIELDPROC() { return getToken(DB2Parser.FIELDPROC, 0); }
		public List<ConstantContext> constant() {
			return getRuleContexts(ConstantContext.class);
		}
		public ConstantContext constant(int i) {
			return getRuleContext(ConstantContext.class,i);
		}
		public TerminalNode INLINE() { return getToken(DB2Parser.INLINE, 0); }
		public TerminalNode LENGTH() { return getToken(DB2Parser.LENGTH, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode IMPLICITLY() { return getToken(DB2Parser.IMPLICITLY, 0); }
		public TerminalNode HIDDEN_KW() { return getToken(DB2Parser.HIDDEN_KW, 0); }
		public TerminalNode VOLATILE() { return getToken(DB2Parser.VOLATILE, 0); }
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TerminalNode SECURITY() { return getToken(DB2Parser.SECURITY, 0); }
		public TerminalNode LABEL() { return getToken(DB2Parser.LABEL, 0); }
		public ColumnAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterColumnAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitColumnAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitColumnAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnAttributeContext columnAttribute() throws RecognitionException {
		ColumnAttributeContext _localctx = new ColumnAttributeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_columnAttribute);
		int _la;
		try {
			int _alt;
			setState(475);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(391);
				match(NOT);
				setState(392);
				match(NULL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(393);
				match(NULL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(394);
				match(DEFAULT);
				setState(397);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(395);
					defaultValue();
					}
					break;
				case 2:
					{
					setState(396);
					if (!(!startsDefaultValue(_localctx))) throw new FailedPredicateException(this, "!startsDefaultValue($ctx)");
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(399);
				match(WITH);
				setState(400);
				match(DEFAULT);
				setState(403);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(401);
					defaultValue();
					}
					break;
				case 2:
					{
					setState(402);
					if (!(!startsDefaultValue(_localctx))) throw new FailedPredicateException(this, "!startsDefaultValue($ctx)");
					}
					break;
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(405);
				match(GENERATED);
				setState(409);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ALWAYS:
					{
					setState(406);
					match(ALWAYS);
					}
					break;
				case BY:
					{
					setState(407);
					match(BY);
					setState(408);
					match(DEFAULT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(412);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(411);
					generatedAs();
					}
					break;
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(416);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CONSTRAINT) {
					{
					setState(414);
					match(CONSTRAINT);
					setState(415);
					identifier();
					}
				}

				setState(418);
				match(PRIMARY);
				setState(419);
				match(KEY);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(422);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CONSTRAINT) {
					{
					setState(420);
					match(CONSTRAINT);
					setState(421);
					identifier();
					}
				}

				setState(424);
				match(UNIQUE);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(427);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CONSTRAINT) {
					{
					setState(425);
					match(CONSTRAINT);
					setState(426);
					identifier();
					}
				}

				setState(429);
				match(CHECK);
				setState(430);
				match(LPAREN);
				setState(431);
				searchCondition(0);
				setState(432);
				match(RPAREN);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(436);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CONSTRAINT) {
					{
					setState(434);
					match(CONSTRAINT);
					setState(435);
					identifier();
					}
				}

				setState(438);
				match(REFERENCES);
				setState(439);
				qualifiedName();
				setState(441);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(440);
					columnList();
					}
					break;
				}
				setState(446);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(443);
						referentialAction();
						}
						} 
					}
					setState(448);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(449);
				match(FOR);
				setState(450);
				_la = _input.LA(1);
				if ( !(_la==BIT || _la==MIXED || _la==SBCS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(451);
				match(DATA);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(452);
				match(CCSID);
				setState(453);
				identifier();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(454);
				match(FIELDPROC);
				setState(455);
				qualifiedName();
				setState(459);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(456);
						constant();
						}
						} 
					}
					setState(461);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(462);
				match(INLINE);
				setState(463);
				match(LENGTH);
				setState(464);
				match(NUMBER);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(465);
				match(IMPLICITLY);
				setState(466);
				match(HIDDEN_KW);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(468);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(467);
					match(NOT);
					}
				}

				setState(470);
				match(VOLATILE);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(471);
				match(AS);
				setState(472);
				match(SECURITY);
				setState(473);
				match(LABEL);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(474);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GeneratedAsContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TerminalNode IDENTITY() { return getToken(DB2Parser.IDENTITY, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<SequenceOptionContext> sequenceOption() {
			return getRuleContexts(SequenceOptionContext.class);
		}
		public SequenceOptionContext sequenceOption(int i) {
			return getRuleContext(SequenceOptionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode BEGIN() { return getToken(DB2Parser.BEGIN, 0); }
		public TerminalNode END() { return getToken(DB2Parser.END, 0); }
		public TerminalNode CHANGE() { return getToken(DB2Parser.CHANGE, 0); }
		public TerminalNode TIMESTAMP() { return getToken(DB2Parser.TIMESTAMP, 0); }
		public TerminalNode TRANSACTION() { return getToken(DB2Parser.TRANSACTION, 0); }
		public TerminalNode START() { return getToken(DB2Parser.START, 0); }
		public TerminalNode ID() { return getToken(DB2Parser.ID, 0); }
		public GeneratedAsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_generatedAs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterGeneratedAs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitGeneratedAs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitGeneratedAs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GeneratedAsContext generatedAs() throws RecognitionException {
		GeneratedAsContext _localctx = new GeneratedAsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_generatedAs);
		int _la;
		try {
			setState(511);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(477);
				match(AS);
				setState(478);
				match(IDENTITY);
				setState(492);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					setState(479);
					match(LPAREN);
					setState(480);
					sequenceOption();
					setState(487);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979175040515L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 319483L) != 0)) {
						{
						{
						setState(482);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==COMMA) {
							{
							setState(481);
							match(COMMA);
							}
						}

						setState(484);
						sequenceOption();
						}
						}
						setState(489);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(490);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(494);
				match(AS);
				setState(495);
				match(LPAREN);
				setState(496);
				expression(0);
				setState(497);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(499);
				match(AS);
				setState(500);
				match(ROW);
				setState(505);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case BEGIN:
					{
					setState(501);
					match(BEGIN);
					}
					break;
				case END:
					{
					setState(502);
					match(END);
					}
					break;
				case CHANGE:
					{
					setState(503);
					match(CHANGE);
					setState(504);
					match(TIMESTAMP);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(507);
				match(AS);
				setState(508);
				match(TRANSACTION);
				setState(509);
				match(START);
				setState(510);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultValueContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode USER() { return getToken(DB2Parser.USER, 0); }
		public TerminalNode CURRENT() { return getToken(DB2Parser.CURRENT, 0); }
		public TerminalNode SQLID() { return getToken(DB2Parser.SQLID, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public DefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDefaultValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDefaultValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValueContext defaultValue() throws RecognitionException {
		DefaultValueContext _localctx = new DefaultValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_defaultValue);
		int _la;
		try {
			setState(532);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(513);
				constant();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(514);
				match(USER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(515);
				match(CURRENT);
				setState(516);
				match(SQLID);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(517);
				match(NULL);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(518);
				qualifiedName();
				setState(519);
				match(LPAREN);
				setState(528);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9147955051233280L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158270467L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 321126614253585L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 2190458609659L) != 0)) {
					{
					setState(520);
					expression(0);
					setState(525);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(521);
						match(COMMA);
						setState(522);
						expression(0);
						}
						}
						setState(527);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(530);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PeriodDefinitionContext extends ParserRuleContext {
		public TerminalNode PERIOD() { return getToken(DB2Parser.PERIOD, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(DB2Parser.COMMA, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public PeriodDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPeriodDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPeriodDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPeriodDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodDefinitionContext periodDefinition() throws RecognitionException {
		PeriodDefinitionContext _localctx = new PeriodDefinitionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_periodDefinition);
		try {
			setState(551);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(534);
				match(PERIOD);
				setState(535);
				identifier();
				setState(536);
				match(LPAREN);
				setState(537);
				identifier();
				setState(538);
				match(COMMA);
				setState(539);
				identifier();
				setState(540);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(542);
				match(PERIOD);
				setState(543);
				match(FOR);
				setState(544);
				identifier();
				setState(545);
				match(LPAREN);
				setState(546);
				identifier();
				setState(547);
				match(COMMA);
				setState(548);
				identifier();
				setState(549);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableConstraintContext extends ParserRuleContext {
		public ConstraintBodyContext constraintBody() {
			return getRuleContext(ConstraintBodyContext.class,0);
		}
		public TerminalNode CONSTRAINT() { return getToken(DB2Parser.CONSTRAINT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TableConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTableConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTableConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTableConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableConstraintContext tableConstraint() throws RecognitionException {
		TableConstraintContext _localctx = new TableConstraintContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_tableConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(555);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONSTRAINT) {
				{
				setState(553);
				match(CONSTRAINT);
				setState(554);
				identifier();
				}
			}

			setState(557);
			constraintBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintBodyContext extends ParserRuleContext {
		public TerminalNode PRIMARY() { return getToken(DB2Parser.PRIMARY, 0); }
		public TerminalNode KEY() { return getToken(DB2Parser.KEY, 0); }
		public List<ColumnListContext> columnList() {
			return getRuleContexts(ColumnListContext.class);
		}
		public ColumnListContext columnList(int i) {
			return getRuleContext(ColumnListContext.class,i);
		}
		public List<ConstraintOptionContext> constraintOption() {
			return getRuleContexts(ConstraintOptionContext.class);
		}
		public ConstraintOptionContext constraintOption(int i) {
			return getRuleContext(ConstraintOptionContext.class,i);
		}
		public TerminalNode FOREIGN() { return getToken(DB2Parser.FOREIGN, 0); }
		public TerminalNode REFERENCES() { return getToken(DB2Parser.REFERENCES, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public List<ReferentialActionContext> referentialAction() {
			return getRuleContexts(ReferentialActionContext.class);
		}
		public ReferentialActionContext referentialAction(int i) {
			return getRuleContext(ReferentialActionContext.class,i);
		}
		public TerminalNode UNIQUE() { return getToken(DB2Parser.UNIQUE, 0); }
		public TerminalNode WHERE() { return getToken(DB2Parser.WHERE, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode CHECK() { return getToken(DB2Parser.CHECK, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public SearchConditionContext searchCondition() {
			return getRuleContext(SearchConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public ConstraintBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterConstraintBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitConstraintBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitConstraintBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintBodyContext constraintBody() throws RecognitionException {
		ConstraintBodyContext _localctx = new ConstraintBodyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constraintBody);
		int _la;
		try {
			int _alt;
			setState(614);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRIMARY:
				enterOuterAlt(_localctx, 1);
				{
				setState(559);
				match(PRIMARY);
				setState(560);
				match(KEY);
				setState(561);
				columnList();
				setState(565);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(562);
						constraintOption();
						}
						} 
					}
					setState(567);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				}
				}
				break;
			case FOREIGN:
				enterOuterAlt(_localctx, 2);
				{
				setState(568);
				match(FOREIGN);
				setState(569);
				match(KEY);
				setState(571);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(570);
					identifier();
					}
				}

				setState(573);
				columnList();
				setState(574);
				match(REFERENCES);
				setState(575);
				qualifiedName();
				setState(577);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(576);
					columnList();
					}
					break;
				}
				setState(582);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(579);
						referentialAction();
						}
						} 
					}
					setState(584);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
				}
				setState(588);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(585);
						constraintOption();
						}
						} 
					}
					setState(590);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				}
				}
				break;
			case UNIQUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(591);
				match(UNIQUE);
				setState(595);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHERE) {
					{
					setState(592);
					match(WHERE);
					setState(593);
					match(NOT);
					setState(594);
					match(NULL);
					}
				}

				setState(597);
				columnList();
				setState(601);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(598);
						constraintOption();
						}
						} 
					}
					setState(603);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,42,_ctx);
				}
				}
				break;
			case CHECK:
				enterOuterAlt(_localctx, 4);
				{
				setState(604);
				match(CHECK);
				setState(605);
				match(LPAREN);
				setState(606);
				searchCondition(0);
				setState(607);
				match(RPAREN);
				setState(611);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(608);
						constraintOption();
						}
						} 
					}
					setState(613);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReferentialActionContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode DELETE() { return getToken(DB2Parser.DELETE, 0); }
		public TerminalNode RESTRICT() { return getToken(DB2Parser.RESTRICT, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode ACTION() { return getToken(DB2Parser.ACTION, 0); }
		public TerminalNode SET() { return getToken(DB2Parser.SET, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode UPDATE() { return getToken(DB2Parser.UPDATE, 0); }
		public TerminalNode ENFORCED() { return getToken(DB2Parser.ENFORCED, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode QUERY() { return getToken(DB2Parser.QUERY, 0); }
		public TerminalNode OPTIMIZATION() { return getToken(DB2Parser.OPTIMIZATION, 0); }
		public ReferentialActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referentialAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterReferentialAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitReferentialAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitReferentialAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferentialActionContext referentialAction() throws RecognitionException {
		ReferentialActionContext _localctx = new ReferentialActionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_referentialAction);
		int _la;
		try {
			setState(640);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(616);
				match(ON);
				setState(617);
				match(DELETE);
				setState(624);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case RESTRICT:
					{
					setState(618);
					match(RESTRICT);
					}
					break;
				case CASCADE:
					{
					setState(619);
					match(CASCADE);
					}
					break;
				case NO:
					{
					setState(620);
					match(NO);
					setState(621);
					match(ACTION);
					}
					break;
				case SET:
					{
					setState(622);
					match(SET);
					setState(623);
					match(NULL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
				match(ON);
				setState(627);
				match(UPDATE);
				setState(631);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case RESTRICT:
					{
					setState(628);
					match(RESTRICT);
					}
					break;
				case NO:
					{
					setState(629);
					match(NO);
					setState(630);
					match(ACTION);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(634);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(633);
					match(NOT);
					}
				}

				setState(636);
				match(ENFORCED);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(637);
				match(ENABLE);
				setState(638);
				match(QUERY);
				setState(639);
				match(OPTIMIZATION);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintOptionContext extends ParserRuleContext {
		public TerminalNode ENFORCED() { return getToken(DB2Parser.ENFORCED, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode QUERY() { return getToken(DB2Parser.QUERY, 0); }
		public TerminalNode OPTIMIZATION() { return getToken(DB2Parser.OPTIMIZATION, 0); }
		public ConstraintOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterConstraintOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitConstraintOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitConstraintOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstraintOptionContext constraintOption() throws RecognitionException {
		ConstraintOptionContext _localctx = new ConstraintOptionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_constraintOption);
		int _la;
		try {
			setState(649);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
			case ENFORCED:
				enterOuterAlt(_localctx, 1);
				{
				setState(643);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(642);
					match(NOT);
					}
				}

				setState(645);
				match(ENFORCED);
				}
				break;
			case ENABLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(646);
				match(ENABLE);
				setState(647);
				match(QUERY);
				setState(648);
				match(OPTIMIZATION);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnListContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public ColumnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterColumnList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitColumnList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitColumnList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnListContext columnList() throws RecognitionException {
		ColumnListContext _localctx = new ColumnListContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_columnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(651);
			match(LPAREN);
			setState(652);
			identifier();
			setState(657);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(653);
				match(COMMA);
				setState(654);
				identifier();
				}
				}
				setState(659);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(660);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableOptionContext extends ParserRuleContext {
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DATABASE() { return getToken(DB2Parser.DATABASE, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode PARTITION() { return getToken(DB2Parser.PARTITION, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<PartitionKeyContext> partitionKey() {
			return getRuleContexts(PartitionKeyContext.class);
		}
		public PartitionKeyContext partitionKey(int i) {
			return getRuleContext(PartitionKeyContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<PartitionClauseContext> partitionClause() {
			return getRuleContexts(PartitionClauseContext.class);
		}
		public PartitionClauseContext partitionClause(int i) {
			return getRuleContext(PartitionClauseContext.class,i);
		}
		public TerminalNode RANGE() { return getToken(DB2Parser.RANGE, 0); }
		public TerminalNode SIZE() { return getToken(DB2Parser.SIZE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode ORGANIZE() { return getToken(DB2Parser.ORGANIZE, 0); }
		public TerminalNode HASH() { return getToken(DB2Parser.HASH, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public HashSpaceContext hashSpace() {
			return getRuleContext(HashSpaceContext.class,0);
		}
		public TerminalNode KEY() { return getToken(DB2Parser.KEY, 0); }
		public TerminalNode CCSID() { return getToken(DB2Parser.CCSID, 0); }
		public TerminalNode VALIDPROC() { return getToken(DB2Parser.VALIDPROC, 0); }
		public TerminalNode EDITPROC() { return getToken(DB2Parser.EDITPROC, 0); }
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode ATTRIBUTES() { return getToken(DB2Parser.ATTRIBUTES, 0); }
		public TerminalNode AUDIT() { return getToken(DB2Parser.AUDIT, 0); }
		public TerminalNode NONE() { return getToken(DB2Parser.NONE, 0); }
		public TerminalNode CHANGES() { return getToken(DB2Parser.CHANGES, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode CAPTURE() { return getToken(DB2Parser.CAPTURE, 0); }
		public TerminalNode VOLATILE() { return getToken(DB2Parser.VOLATILE, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode CARDINALITY() { return getToken(DB2Parser.CARDINALITY, 0); }
		public TerminalNode APPEND() { return getToken(DB2Parser.APPEND, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode RESTRICT() { return getToken(DB2Parser.RESTRICT, 0); }
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode DROP() { return getToken(DB2Parser.DROP, 0); }
		public TerminalNode OBID() { return getToken(DB2Parser.OBID, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode LOGGED() { return getToken(DB2Parser.LOGGED, 0); }
		public StorageOptionContext storageOption() {
			return getRuleContext(StorageOptionContext.class,0);
		}
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public TableOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTableOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTableOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTableOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableOptionContext tableOption() throws RecognitionException {
		TableOptionContext _localctx = new TableOptionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_tableOption);
		int _la;
		try {
			int _alt;
			setState(751);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(662);
				match(IN);
				setState(663);
				qualifiedName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(664);
				match(IN);
				setState(665);
				match(DATABASE);
				setState(666);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(667);
				match(PARTITION);
				setState(668);
				match(BY);
				setState(670);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
				case 1:
					{
					setState(669);
					_la = _input.LA(1);
					if ( !(_la==RANGE || _la==SIZE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				setState(683);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(672);
					match(LPAREN);
					setState(673);
					partitionKey();
					setState(678);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(674);
						match(COMMA);
						setState(675);
						partitionKey();
						}
						}
						setState(680);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(681);
					match(RPAREN);
					}
					break;
				}
				setState(688);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(685);
						partitionClause();
						}
						} 
					}
					setState(690);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,55,_ctx);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(691);
				match(ORGANIZE);
				setState(692);
				match(BY);
				setState(711);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case HASH:
					{
					setState(693);
					match(HASH);
					setState(694);
					match(LPAREN);
					setState(695);
					identifier();
					setState(700);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(696);
						match(COMMA);
						setState(697);
						identifier();
						}
						}
						setState(702);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(703);
					match(RPAREN);
					setState(705);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,57,_ctx) ) {
					case 1:
						{
						setState(704);
						hashSpace();
						}
						break;
					}
					}
					break;
				case KEY:
				case SEQUENCE:
					{
					setState(708);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==KEY) {
						{
						setState(707);
						match(KEY);
						}
					}

					setState(710);
					match(SEQUENCE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(713);
				match(CCSID);
				setState(714);
				identifier();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(715);
				match(VALIDPROC);
				setState(716);
				qualifiedName();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(717);
				match(EDITPROC);
				setState(718);
				qualifiedName();
				setState(722);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
				case 1:
					{
					setState(719);
					match(WITH);
					setState(720);
					match(ROW);
					setState(721);
					match(ATTRIBUTES);
					}
					break;
				}
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(724);
				match(AUDIT);
				setState(725);
				_la = _input.LA(1);
				if ( !(_la==ALL || _la==NONE || _la==CHANGES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(726);
				match(DATA);
				setState(727);
				match(CAPTURE);
				setState(728);
				_la = _input.LA(1);
				if ( !(_la==NONE || _la==CHANGES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(730);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(729);
					match(NOT);
					}
				}

				setState(732);
				match(VOLATILE);
				setState(734);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
				case 1:
					{
					setState(733);
					match(CARDINALITY);
					}
					break;
				}
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(736);
				match(CARDINALITY);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(737);
				match(APPEND);
				setState(738);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(739);
				match(WITH);
				setState(740);
				match(RESTRICT);
				setState(741);
				match(ON);
				setState(742);
				match(DROP);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(743);
				match(OBID);
				setState(744);
				match(NUMBER);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(746);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(745);
					match(NOT);
					}
				}

				setState(748);
				match(LOGGED);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(749);
				storageOption();
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(750);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HashSpaceContext extends ParserRuleContext {
		public TerminalNode HASH() { return getToken(DB2Parser.HASH, 0); }
		public TerminalNode SPACE() { return getToken(DB2Parser.SPACE, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public HashSpaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hashSpace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterHashSpace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitHashSpace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitHashSpace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HashSpaceContext hashSpace() throws RecognitionException {
		HashSpaceContext _localctx = new HashSpaceContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_hashSpace);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(753);
			match(HASH);
			setState(754);
			match(SPACE);
			setState(755);
			match(NUMBER);
			setState(757);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
			case 1:
				{
				setState(756);
				identifier();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionKeyContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ASC() { return getToken(DB2Parser.ASC, 0); }
		public TerminalNode DESC() { return getToken(DB2Parser.DESC, 0); }
		public PartitionKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionKeyContext partitionKey() throws RecognitionException {
		PartitionKeyContext _localctx = new PartitionKeyContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_partitionKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			identifier();
			setState(761);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(760);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionClauseContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<PartitionSpecContext> partitionSpec() {
			return getRuleContexts(PartitionSpecContext.class);
		}
		public PartitionSpecContext partitionSpec(int i) {
			return getRuleContext(PartitionSpecContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode NUMPARTS() { return getToken(DB2Parser.NUMPARTS, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public PartitionClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionClauseContext partitionClause() throws RecognitionException {
		PartitionClauseContext _localctx = new PartitionClauseContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_partitionClause);
		int _la;
		try {
			setState(776);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(763);
				match(LPAREN);
				setState(764);
				partitionSpec();
				setState(769);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(765);
					match(COMMA);
					setState(766);
					partitionSpec();
					}
					}
					setState(771);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(772);
				match(RPAREN);
				}
				break;
			case NUMPARTS:
				enterOuterAlt(_localctx, 2);
				{
				setState(774);
				match(NUMPARTS);
				setState(775);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionSpecContext extends ParserRuleContext {
		public TerminalNode PARTITION() { return getToken(DB2Parser.PARTITION, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public List<PartitionBoundContext> partitionBound() {
			return getRuleContexts(PartitionBoundContext.class);
		}
		public PartitionBoundContext partitionBound(int i) {
			return getRuleContext(PartitionBoundContext.class,i);
		}
		public List<StorageOptionContext> storageOption() {
			return getRuleContexts(StorageOptionContext.class);
		}
		public StorageOptionContext storageOption(int i) {
			return getRuleContext(StorageOptionContext.class,i);
		}
		public PartitionSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionSpecContext partitionSpec() throws RecognitionException {
		PartitionSpecContext _localctx = new PartitionSpecContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_partitionSpec);
		try {
			int _alt;
			setState(803);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARTITION:
				enterOuterAlt(_localctx, 1);
				{
				setState(778);
				match(PARTITION);
				setState(779);
				match(NUMBER);
				setState(783);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(780);
						partitionBound();
						}
						} 
					}
					setState(785);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,69,_ctx);
				}
				setState(789);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(786);
						storageOption();
						}
						} 
					}
					setState(791);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,70,_ctx);
				}
				}
				break;
			case ENDING:
			case EVERY:
			case STARTING:
				enterOuterAlt(_localctx, 2);
				{
				setState(793); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(792);
						partitionBound();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(795); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,71,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(800);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(797);
						storageOption();
						}
						} 
					}
					setState(802);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,72,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionBoundContext extends ParserRuleContext {
		public TerminalNode STARTING() { return getToken(DB2Parser.STARTING, 0); }
		public PartitionLimitContext partitionLimit() {
			return getRuleContext(PartitionLimitContext.class,0);
		}
		public TerminalNode FROM() { return getToken(DB2Parser.FROM, 0); }
		public TerminalNode INCLUSIVE() { return getToken(DB2Parser.INCLUSIVE, 0); }
		public TerminalNode EXCLUSIVE() { return getToken(DB2Parser.EXCLUSIVE, 0); }
		public TerminalNode ENDING() { return getToken(DB2Parser.ENDING, 0); }
		public TerminalNode AT() { return getToken(DB2Parser.AT, 0); }
		public TerminalNode EVERY() { return getToken(DB2Parser.EVERY, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PartitionBoundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionBound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionBound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionBound(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionBound(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionBoundContext partitionBound() throws RecognitionException {
		PartitionBoundContext _localctx = new PartitionBoundContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_partitionBound);
		int _la;
		try {
			setState(829);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STARTING:
				enterOuterAlt(_localctx, 1);
				{
				setState(805);
				match(STARTING);
				setState(807);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FROM) {
					{
					setState(806);
					match(FROM);
					}
				}

				setState(809);
				partitionLimit();
				setState(811);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
				case 1:
					{
					setState(810);
					_la = _input.LA(1);
					if ( !(_la==INCLUSIVE || _la==EXCLUSIVE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				break;
			case ENDING:
				enterOuterAlt(_localctx, 2);
				{
				setState(813);
				match(ENDING);
				setState(815);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AT) {
					{
					setState(814);
					match(AT);
					}
				}

				setState(817);
				partitionLimit();
				setState(819);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
				case 1:
					{
					setState(818);
					_la = _input.LA(1);
					if ( !(_la==INCLUSIVE || _la==EXCLUSIVE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				break;
			case EVERY:
				enterOuterAlt(_localctx, 3);
				{
				setState(821);
				match(EVERY);
				setState(822);
				match(LPAREN);
				setState(823);
				constant();
				setState(825);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(824);
					identifier();
					}
				}

				setState(827);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionLimitContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<PartitionValueContext> partitionValue() {
			return getRuleContexts(PartitionValueContext.class);
		}
		public PartitionValueContext partitionValue(int i) {
			return getRuleContext(PartitionValueContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public PartitionLimitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionLimit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionLimit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionLimit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionLimit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionLimitContext partitionLimit() throws RecognitionException {
		PartitionLimitContext _localctx = new PartitionLimitContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_partitionLimit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(831);
			match(LPAREN);
			setState(832);
			partitionValue();
			setState(837);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(833);
				match(COMMA);
				setState(834);
				partitionValue();
				}
				}
				setState(839);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(840);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PartitionValueContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode MINVALUE() { return getToken(DB2Parser.MINVALUE, 0); }
		public TerminalNode MAXVALUE() { return getToken(DB2Parser.MAXVALUE, 0); }
		public PartitionValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_partitionValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPartitionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPartitionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPartitionValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PartitionValueContext partitionValue() throws RecognitionException {
		PartitionValueContext _localctx = new PartitionValueContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_partitionValue);
		try {
			setState(845);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case STRING:
			case HEX_STRING:
			case PLACEHOLDER:
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(842);
				constant();
				}
				break;
			case MINVALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(843);
				match(MINVALUE);
				}
				break;
			case MAXVALUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(844);
				match(MAXVALUE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateIndexContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<IndexKeyContext> indexKey() {
			return getRuleContexts(IndexKeyContext.class);
		}
		public IndexKeyContext indexKey(int i) {
			return getRuleContext(IndexKeyContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode XMLPATTERN() { return getToken(DB2Parser.XMLPATTERN, 0); }
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public List<IndexModifierContext> indexModifier() {
			return getRuleContexts(IndexModifierContext.class);
		}
		public IndexModifierContext indexModifier(int i) {
			return getRuleContext(IndexModifierContext.class,i);
		}
		public List<IndexOptionContext> indexOption() {
			return getRuleContexts(IndexOptionContext.class);
		}
		public IndexOptionContext indexOption(int i) {
			return getRuleContext(IndexOptionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public CreateIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateIndexContext createIndex() throws RecognitionException {
		CreateIndexContext _localctx = new CreateIndexContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_createIndex);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(847);
			match(CREATE);
			setState(851);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TYPE || _la==UNIQUE) {
				{
				{
				setState(848);
				indexModifier();
				}
				}
				setState(853);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(854);
			match(INDEX);
			setState(855);
			qualifiedName();
			setState(856);
			match(ON);
			setState(857);
			qualifiedName();
			setState(871);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(858);
				match(LPAREN);
				setState(859);
				indexKey();
				setState(864);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(860);
					match(COMMA);
					setState(861);
					indexKey();
					}
					}
					setState(866);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(867);
				match(RPAREN);
				}
				break;
			case XMLPATTERN:
				{
				setState(869);
				match(XMLPATTERN);
				setState(870);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(876);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(873);
					indexOption();
					}
					} 
				}
				setState(878);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
			}
			setState(879);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexModifierContext extends ParserRuleContext {
		public TerminalNode UNIQUE() { return getToken(DB2Parser.UNIQUE, 0); }
		public TerminalNode WHERE() { return getToken(DB2Parser.WHERE, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public IndexModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterIndexModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitIndexModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitIndexModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexModifierContext indexModifier() throws RecognitionException {
		IndexModifierContext _localctx = new IndexModifierContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_indexModifier);
		int _la;
		try {
			setState(889);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNIQUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(881);
				match(UNIQUE);
				setState(885);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHERE) {
					{
					setState(882);
					match(WHERE);
					setState(883);
					match(NOT);
					setState(884);
					match(NULL);
					}
				}

				}
				break;
			case TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(887);
				match(TYPE);
				setState(888);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexKeyContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASC() { return getToken(DB2Parser.ASC, 0); }
		public TerminalNode DESC() { return getToken(DB2Parser.DESC, 0); }
		public TerminalNode RANDOM() { return getToken(DB2Parser.RANDOM, 0); }
		public IndexKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterIndexKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitIndexKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitIndexKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexKeyContext indexKey() throws RecognitionException {
		IndexKeyContext _localctx = new IndexKeyContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_indexKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(895);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,88,_ctx) ) {
			case 1:
				{
				setState(891);
				if (!(columnKeyAhead(_localctx))) throw new FailedPredicateException(this, "columnKeyAhead($ctx)");
				setState(892);
				identifier();
				}
				break;
			case 2:
				{
				setState(893);
				if (!(!columnKeyAhead(_localctx))) throw new FailedPredicateException(this, "!columnKeyAhead($ctx)");
				setState(894);
				expression(0);
				}
				break;
			}
			setState(898);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC || _la==RANDOM) {
				{
				setState(897);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC || _la==RANDOM) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexOptionContext extends ParserRuleContext {
		public TerminalNode CLUSTER() { return getToken(DB2Parser.CLUSTER, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode PADDED() { return getToken(DB2Parser.PADDED, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode KEYS() { return getToken(DB2Parser.KEYS, 0); }
		public TerminalNode INCLUDE() { return getToken(DB2Parser.INCLUDE, 0); }
		public TerminalNode EXCLUDE() { return getToken(DB2Parser.EXCLUDE, 0); }
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode PARTITIONED() { return getToken(DB2Parser.PARTITIONED, 0); }
		public TerminalNode PARTITION() { return getToken(DB2Parser.PARTITION, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public List<PartitionClauseContext> partitionClause() {
			return getRuleContexts(PartitionClauseContext.class);
		}
		public PartitionClauseContext partitionClause(int i) {
			return getRuleContext(PartitionClauseContext.class,i);
		}
		public TerminalNode RANGE() { return getToken(DB2Parser.RANGE, 0); }
		public TerminalNode SIZE() { return getToken(DB2Parser.SIZE, 0); }
		public TerminalNode GENERATE() { return getToken(DB2Parser.GENERATE, 0); }
		public TerminalNode USING() { return getToken(DB2Parser.USING, 0); }
		public TerminalNode XMLPATTERN() { return getToken(DB2Parser.XMLPATTERN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode KEY() { return getToken(DB2Parser.KEY, 0); }
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public TerminalNode BUFFERPOOL() { return getToken(DB2Parser.BUFFERPOOL, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode CLOSE() { return getToken(DB2Parser.CLOSE, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode COPY() { return getToken(DB2Parser.COPY, 0); }
		public TerminalNode DEFER() { return getToken(DB2Parser.DEFER, 0); }
		public TerminalNode DEFINE() { return getToken(DB2Parser.DEFINE, 0); }
		public TerminalNode COMPRESS() { return getToken(DB2Parser.COMPRESS, 0); }
		public TerminalNode PIECESIZE() { return getToken(DB2Parser.PIECESIZE, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public StorageOptionContext storageOption() {
			return getRuleContext(StorageOptionContext.class,0);
		}
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public IndexOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterIndexOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitIndexOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitIndexOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexOptionContext indexOption() throws RecognitionException {
		IndexOptionContext _localctx = new IndexOptionContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_indexOption);
		int _la;
		try {
			int _alt;
			setState(957);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,98,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(901);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(900);
					match(NOT);
					}
				}

				setState(903);
				match(CLUSTER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(905);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(904);
					match(NOT);
					}
				}

				setState(907);
				match(PADDED);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(908);
				_la = _input.LA(1);
				if ( !(_la==EXCLUDE || _la==INCLUDE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(909);
				match(NULL);
				setState(910);
				match(KEYS);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(911);
				match(INCLUDE);
				setState(912);
				columnList();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(913);
				match(PARTITIONED);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(914);
				match(PARTITION);
				setState(915);
				match(BY);
				setState(917);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
				case 1:
					{
					setState(916);
					_la = _input.LA(1);
					if ( !(_la==RANGE || _la==SIZE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				setState(922);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(919);
						partitionClause();
						}
						} 
					}
					setState(924);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,93,_ctx);
				}
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(925);
				match(GENERATE);
				setState(927);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==KEY) {
					{
					setState(926);
					match(KEY);
					}
				}

				setState(929);
				match(USING);
				setState(932);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
				case 1:
					{
					setState(930);
					match(XMLPATTERN);
					}
					break;
				case 2:
					{
					setState(931);
					expression(0);
					}
					break;
				}
				setState(935);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,96,_ctx) ) {
				case 1:
					{
					setState(934);
					match(STRING);
					}
					break;
				}
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(937);
				match(BUFFERPOOL);
				setState(938);
				identifier();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(939);
				match(CLOSE);
				setState(940);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(941);
				match(COPY);
				setState(942);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(943);
				match(DEFER);
				setState(944);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(945);
				match(DEFINE);
				setState(946);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(947);
				match(COMPRESS);
				setState(948);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(949);
				match(PIECESIZE);
				setState(950);
				match(NUMBER);
				setState(952);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
				case 1:
					{
					setState(951);
					identifier();
					}
					break;
				}
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(954);
				storageOption();
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(955);
				if (!(!startsNamedOption(_localctx))) throw new FailedPredicateException(this, "!startsNamedOption($ctx)");
				setState(956);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTablespaceContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TABLESPACE() { return getToken(DB2Parser.TABLESPACE, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public List<TablespaceOptionContext> tablespaceOption() {
			return getRuleContexts(TablespaceOptionContext.class);
		}
		public TablespaceOptionContext tablespaceOption(int i) {
			return getRuleContext(TablespaceOptionContext.class,i);
		}
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LARGE() { return getToken(DB2Parser.LARGE, 0); }
		public CreateTablespaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTablespace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateTablespace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateTablespace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateTablespace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTablespaceContext createTablespace() throws RecognitionException {
		CreateTablespaceContext _localctx = new CreateTablespaceContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_createTablespace);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(959);
			match(CREATE);
			setState(961);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LARGE || _la==LOB) {
				{
				setState(960);
				_la = _input.LA(1);
				if ( !(_la==LARGE || _la==LOB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(963);
			match(TABLESPACE);
			setState(964);
			identifier();
			setState(967);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				{
				setState(965);
				match(IN);
				setState(966);
				identifier();
				}
				break;
			}
			setState(972);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(969);
					tablespaceOption();
					}
					} 
				}
				setState(974);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,101,_ctx);
			}
			setState(975);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TablespaceOptionContext extends ParserRuleContext {
		public TerminalNode USING() { return getToken(DB2Parser.USING, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode VCAT() { return getToken(DB2Parser.VCAT, 0); }
		public TerminalNode SEGSIZE() { return getToken(DB2Parser.SEGSIZE, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode NUMPARTS() { return getToken(DB2Parser.NUMPARTS, 0); }
		public List<PartitionClauseContext> partitionClause() {
			return getRuleContexts(PartitionClauseContext.class);
		}
		public PartitionClauseContext partitionClause(int i) {
			return getRuleContext(PartitionClauseContext.class,i);
		}
		public TerminalNode MAXPARTITIONS() { return getToken(DB2Parser.MAXPARTITIONS, 0); }
		public TerminalNode DSSIZE() { return getToken(DB2Parser.DSSIZE, 0); }
		public TerminalNode LOCKSIZE() { return getToken(DB2Parser.LOCKSIZE, 0); }
		public TerminalNode ANY() { return getToken(DB2Parser.ANY, 0); }
		public TerminalNode TABLESPACE() { return getToken(DB2Parser.TABLESPACE, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode PAGE() { return getToken(DB2Parser.PAGE, 0); }
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LOCKMAX() { return getToken(DB2Parser.LOCKMAX, 0); }
		public TerminalNode SYSTEM() { return getToken(DB2Parser.SYSTEM, 0); }
		public TerminalNode BUFFERPOOL() { return getToken(DB2Parser.BUFFERPOOL, 0); }
		public TerminalNode CCSID() { return getToken(DB2Parser.CCSID, 0); }
		public TerminalNode CLOSE() { return getToken(DB2Parser.CLOSE, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode COMPRESS() { return getToken(DB2Parser.COMPRESS, 0); }
		public TerminalNode DEFINE() { return getToken(DB2Parser.DEFINE, 0); }
		public TerminalNode TRACKMOD() { return getToken(DB2Parser.TRACKMOD, 0); }
		public TerminalNode LOGGED() { return getToken(DB2Parser.LOGGED, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode MEMBER() { return getToken(DB2Parser.MEMBER, 0); }
		public TerminalNode CLUSTER() { return getToken(DB2Parser.CLUSTER, 0); }
		public TerminalNode PAGENUM() { return getToken(DB2Parser.PAGENUM, 0); }
		public TerminalNode ABSOLUTE() { return getToken(DB2Parser.ABSOLUTE, 0); }
		public TerminalNode RELATIVE() { return getToken(DB2Parser.RELATIVE, 0); }
		public TerminalNode MAXROWS() { return getToken(DB2Parser.MAXROWS, 0); }
		public StorageOptionContext storageOption() {
			return getRuleContext(StorageOptionContext.class,0);
		}
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public TablespaceOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tablespaceOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTablespaceOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTablespaceOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTablespaceOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TablespaceOptionContext tablespaceOption() throws RecognitionException {
		TablespaceOptionContext _localctx = new TablespaceOptionContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_tablespaceOption);
		int _la;
		try {
			int _alt;
			setState(1030);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(977);
				match(USING);
				setState(982);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STOGROUP:
					{
					setState(978);
					match(STOGROUP);
					setState(979);
					identifier();
					}
					break;
				case VCAT:
					{
					setState(980);
					match(VCAT);
					setState(981);
					identifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(984);
				match(SEGSIZE);
				setState(985);
				match(NUMBER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(986);
				match(NUMPARTS);
				setState(987);
				match(NUMBER);
				setState(991);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(988);
						partitionClause();
						}
						} 
					}
					setState(993);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,103,_ctx);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(994);
				match(MAXPARTITIONS);
				setState(995);
				match(NUMBER);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(996);
				match(DSSIZE);
				setState(997);
				match(NUMBER);
				setState(999);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,104,_ctx) ) {
				case 1:
					{
					setState(998);
					identifier();
					}
					break;
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1001);
				match(LOCKSIZE);
				setState(1002);
				_la = _input.LA(1);
				if ( !(_la==ANY || ((((_la - 212)) & ~0x3f) == 0 && ((1L << (_la - 212)) & 6442450945L) != 0) || _la==LOB || _la==PAGE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1003);
				match(LOCKMAX);
				setState(1004);
				_la = _input.LA(1);
				if ( !(_la==SYSTEM || _la==NUMBER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1005);
				match(BUFFERPOOL);
				setState(1006);
				identifier();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1007);
				match(CCSID);
				setState(1008);
				identifier();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1009);
				match(CLOSE);
				setState(1010);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1011);
				match(COMPRESS);
				setState(1012);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1013);
				match(DEFINE);
				setState(1014);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1015);
				match(TRACKMOD);
				setState(1016);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(1017);
				match(LOGGED);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(1018);
				match(NOT);
				setState(1019);
				match(LOGGED);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(1020);
				match(MEMBER);
				setState(1021);
				match(CLUSTER);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(1022);
				match(PAGENUM);
				setState(1023);
				_la = _input.LA(1);
				if ( !(_la==ABSOLUTE || _la==RELATIVE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(1024);
				match(MAXROWS);
				setState(1025);
				match(NUMBER);
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(1026);
				if (!(!ahead(_localctx, USING))) throw new FailedPredicateException(this, "!ahead($ctx, USING)");
				setState(1027);
				storageOption();
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(1028);
				if (!(!startsNamedOption(_localctx))) throw new FailedPredicateException(this, "!startsNamedOption($ctx)");
				setState(1029);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateDatabaseContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode DATABASE() { return getToken(DB2Parser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<DatabaseOptionContext> databaseOption() {
			return getRuleContexts(DatabaseOptionContext.class);
		}
		public DatabaseOptionContext databaseOption(int i) {
			return getRuleContext(DatabaseOptionContext.class,i);
		}
		public CreateDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateDatabase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateDatabase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateDatabaseContext createDatabase() throws RecognitionException {
		CreateDatabaseContext _localctx = new CreateDatabaseContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_createDatabase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1032);
			match(CREATE);
			setState(1033);
			match(DATABASE);
			setState(1034);
			identifier();
			setState(1038);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217580183552L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728569839633L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1035);
				databaseOption();
				}
				}
				setState(1040);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1041);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DatabaseOptionContext extends ParserRuleContext {
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TerminalNode WORKFILE() { return getToken(DB2Parser.WORKFILE, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode BUFFERPOOL() { return getToken(DB2Parser.BUFFERPOOL, 0); }
		public TerminalNode INDEXBP() { return getToken(DB2Parser.INDEXBP, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public TerminalNode CCSID() { return getToken(DB2Parser.CCSID, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public DatabaseOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_databaseOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDatabaseOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDatabaseOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDatabaseOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DatabaseOptionContext databaseOption() throws RecognitionException {
		DatabaseOptionContext _localctx = new DatabaseOptionContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_databaseOption);
		int _la;
		try {
			setState(1058);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(1043);
				match(AS);
				setState(1044);
				match(WORKFILE);
				setState(1047);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FOR) {
					{
					setState(1045);
					match(FOR);
					setState(1046);
					identifier();
					}
				}

				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1049);
				match(BUFFERPOOL);
				setState(1050);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1051);
				match(INDEXBP);
				setState(1052);
				identifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1053);
				match(STOGROUP);
				setState(1054);
				identifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1055);
				match(CCSID);
				setState(1056);
				identifier();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1057);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateStogroupContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<StogroupOptionContext> stogroupOption() {
			return getRuleContexts(StogroupOptionContext.class);
		}
		public StogroupOptionContext stogroupOption(int i) {
			return getRuleContext(StogroupOptionContext.class,i);
		}
		public CreateStogroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createStogroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateStogroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateStogroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateStogroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateStogroupContext createStogroup() throws RecognitionException {
		CreateStogroupContext _localctx = new CreateStogroupContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_createStogroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1060);
			match(CREATE);
			setState(1061);
			match(STOGROUP);
			setState(1062);
			identifier();
			setState(1066);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 5383278148534289L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1063);
				stogroupOption();
				}
				}
				setState(1068);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1069);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StogroupOptionContext extends ParserRuleContext {
		public TerminalNode VOLUMES() { return getToken(DB2Parser.VOLUMES, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> STRING() { return getTokens(DB2Parser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(DB2Parser.STRING, i);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode VCAT() { return getToken(DB2Parser.VCAT, 0); }
		public TerminalNode DATACLAS() { return getToken(DB2Parser.DATACLAS, 0); }
		public TerminalNode MGMTCLAS() { return getToken(DB2Parser.MGMTCLAS, 0); }
		public TerminalNode STORCLAS() { return getToken(DB2Parser.STORCLAS, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public StogroupOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stogroupOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterStogroupOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitStogroupOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitStogroupOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StogroupOptionContext stogroupOption() throws RecognitionException {
		StogroupOptionContext _localctx = new StogroupOptionContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_stogroupOption);
		int _la;
		try {
			setState(1097);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1071);
				match(VOLUMES);
				setState(1072);
				match(LPAREN);
				setState(1075);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STRING:
					{
					setState(1073);
					match(STRING);
					}
					break;
				case CAPTURE:
				case CLONE:
				case COLLID:
				case DBINFO:
				case ENCODING:
				case FINAL:
				case FIRST:
				case ISOBID:
				case JAR:
				case LAST:
				case LC_CTYPE:
				case LOCALE:
				case MAINTAINED:
				case MATERIALIZED:
				case NEXT:
				case NULLS:
				case OBID:
				case OPTIMIZATION:
				case OPTIMIZE:
				case ORGANIZATION:
				case PADDED:
				case PLAN:
				case PREVVAL:
				case PROGRAM:
				case PSID:
				case QUERYNO:
				case ROWSET:
				case SCRATCHPAD:
				case SIMPLE:
				case SOURCE:
				case STANDARD:
				case STORES:
				case STYLE:
				case SUMMARY:
				case SYSFUN:
				case SYSIBM:
				case SYSPROC:
				case TYPE:
				case VALUE:
				case VARIANT:
				case AUTHENTICATION:
				case AUTHID:
				case BASED:
				case CONTROL:
				case UPON:
				case ABSOLUTE:
				case ACCESS:
				case ACTION:
				case ADMIN:
				case ALWAYS:
				case ASC:
				case ASUTIME:
				case AT:
				case ATOMIC:
				case ATTRIBUTES:
				case AUX:
				case BIT:
				case CACHE:
				case CALLED:
				case CARDINALITY:
				case CASCADE:
				case CHANGE:
				case CHANGED:
				case CHANGES:
				case COMPARISONS:
				case COMPRESS:
				case CONTEXT:
				case COPY:
				case CURSORS:
				case CYCLE:
				case DATACLAS:
				case DB2:
				case DB2SQL:
				case DEBUG:
				case DEFER:
				case DEFINE:
				case DEFINER:
				case DESC:
				case EACH:
				case ENABLE:
				case ENFORCED:
				case ENVIRONMENT:
				case EVERY:
				case EXCLUDE:
				case EXCLUDING:
				case EXCLUSIVE:
				case FREEPAGE:
				case GBPCACHE:
				case GENERATE:
				case HASH:
				case HIDDEN_KW:
				case HISTORY:
				case ID:
				case IDENTITY:
				case IMPLICITLY:
				case INCLUDE:
				case INCLUDING:
				case INCREMENT:
				case INDEXBP:
				case INLINE:
				case INPUT:
				case INSTEAD:
				case KEYS:
				case LARGE:
				case LENGTH:
				case LIMIT:
				case LOAD:
				case LOB:
				case LOGGED:
				case MAIN:
				case MASK:
				case MAXPARTITIONS:
				case MAXROWS:
				case MAXVALUE:
				case MEMBER:
				case MGMTCLAS:
				case MINVALUE:
				case MIXED:
				case MODE:
				case NAME:
				case NEW:
				case NEW_TABLE:
				case OLD_TABLE:
				case ONLY:
				case OPTION:
				case OPTIONS:
				case ORGANIZE:
				case PAGE:
				case PAGENUM:
				case PCTFREE:
				case PERMISSION:
				case PRIMARY:
				case QUALIFIER:
				case RANDOM:
				case RANGE:
				case REGENERATE:
				case REGISTERS:
				case RELATIVE:
				case REMOVE:
				case REPLACE:
				case RESET:
				case RESIDENT:
				case RESTART:
				case RETAIN:
				case ROTATE:
				case ROWS:
				case SBCS:
				case SECURED:
				case SEGSIZE:
				case SETS:
				case SHARE:
				case SIZE:
				case SPACE:
				case SPECIAL:
				case SQL:
				case SQLID:
				case START:
				case STARTING:
				case STATEMENT:
				case STORCLAS:
				case SUB:
				case TEMPORARY:
				case TIME:
				case TIMESTAMP:
				case TRACKMOD:
				case TRUSTED:
				case UNLOAD:
				case USAGE:
				case USE:
				case VARCHAR:
				case VARGRAPHIC:
				case VARYING:
				case VERSIONING:
				case WITHOUT:
				case WORK:
				case XMLPATTERN:
				case YES:
				case DELIMITED_IDENTIFIER:
				case PLACEHOLDER:
				case IDENTIFIER:
					{
					setState(1074);
					identifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1084);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1077);
					match(COMMA);
					setState(1080);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case STRING:
						{
						setState(1078);
						match(STRING);
						}
						break;
					case CAPTURE:
					case CLONE:
					case COLLID:
					case DBINFO:
					case ENCODING:
					case FINAL:
					case FIRST:
					case ISOBID:
					case JAR:
					case LAST:
					case LC_CTYPE:
					case LOCALE:
					case MAINTAINED:
					case MATERIALIZED:
					case NEXT:
					case NULLS:
					case OBID:
					case OPTIMIZATION:
					case OPTIMIZE:
					case ORGANIZATION:
					case PADDED:
					case PLAN:
					case PREVVAL:
					case PROGRAM:
					case PSID:
					case QUERYNO:
					case ROWSET:
					case SCRATCHPAD:
					case SIMPLE:
					case SOURCE:
					case STANDARD:
					case STORES:
					case STYLE:
					case SUMMARY:
					case SYSFUN:
					case SYSIBM:
					case SYSPROC:
					case TYPE:
					case VALUE:
					case VARIANT:
					case AUTHENTICATION:
					case AUTHID:
					case BASED:
					case CONTROL:
					case UPON:
					case ABSOLUTE:
					case ACCESS:
					case ACTION:
					case ADMIN:
					case ALWAYS:
					case ASC:
					case ASUTIME:
					case AT:
					case ATOMIC:
					case ATTRIBUTES:
					case AUX:
					case BIT:
					case CACHE:
					case CALLED:
					case CARDINALITY:
					case CASCADE:
					case CHANGE:
					case CHANGED:
					case CHANGES:
					case COMPARISONS:
					case COMPRESS:
					case CONTEXT:
					case COPY:
					case CURSORS:
					case CYCLE:
					case DATACLAS:
					case DB2:
					case DB2SQL:
					case DEBUG:
					case DEFER:
					case DEFINE:
					case DEFINER:
					case DESC:
					case EACH:
					case ENABLE:
					case ENFORCED:
					case ENVIRONMENT:
					case EVERY:
					case EXCLUDE:
					case EXCLUDING:
					case EXCLUSIVE:
					case FREEPAGE:
					case GBPCACHE:
					case GENERATE:
					case HASH:
					case HIDDEN_KW:
					case HISTORY:
					case ID:
					case IDENTITY:
					case IMPLICITLY:
					case INCLUDE:
					case INCLUDING:
					case INCREMENT:
					case INDEXBP:
					case INLINE:
					case INPUT:
					case INSTEAD:
					case KEYS:
					case LARGE:
					case LENGTH:
					case LIMIT:
					case LOAD:
					case LOB:
					case LOGGED:
					case MAIN:
					case MASK:
					case MAXPARTITIONS:
					case MAXROWS:
					case MAXVALUE:
					case MEMBER:
					case MGMTCLAS:
					case MINVALUE:
					case MIXED:
					case MODE:
					case NAME:
					case NEW:
					case NEW_TABLE:
					case OLD_TABLE:
					case ONLY:
					case OPTION:
					case OPTIONS:
					case ORGANIZE:
					case PAGE:
					case PAGENUM:
					case PCTFREE:
					case PERMISSION:
					case PRIMARY:
					case QUALIFIER:
					case RANDOM:
					case RANGE:
					case REGENERATE:
					case REGISTERS:
					case RELATIVE:
					case REMOVE:
					case REPLACE:
					case RESET:
					case RESIDENT:
					case RESTART:
					case RETAIN:
					case ROTATE:
					case ROWS:
					case SBCS:
					case SECURED:
					case SEGSIZE:
					case SETS:
					case SHARE:
					case SIZE:
					case SPACE:
					case SPECIAL:
					case SQL:
					case SQLID:
					case START:
					case STARTING:
					case STATEMENT:
					case STORCLAS:
					case SUB:
					case TEMPORARY:
					case TIME:
					case TIMESTAMP:
					case TRACKMOD:
					case TRUSTED:
					case UNLOAD:
					case USAGE:
					case USE:
					case VARCHAR:
					case VARGRAPHIC:
					case VARYING:
					case VERSIONING:
					case WITHOUT:
					case WORK:
					case XMLPATTERN:
					case YES:
					case DELIMITED_IDENTIFIER:
					case PLACEHOLDER:
					case IDENTIFIER:
						{
						setState(1079);
						identifier();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					setState(1086);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1087);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1088);
				match(VCAT);
				setState(1089);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1090);
				match(DATACLAS);
				setState(1091);
				identifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1092);
				match(MGMTCLAS);
				setState(1093);
				identifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1094);
				match(STORCLAS);
				setState(1095);
				identifier();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1096);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateViewContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode VIEW() { return getToken(DB2Parser.VIEW, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public List<ViewOptionContext> viewOption() {
			return getRuleContexts(ViewOptionContext.class);
		}
		public ViewOptionContext viewOption(int i) {
			return getRuleContext(ViewOptionContext.class,i);
		}
		public CreateViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createView; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateView(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateView(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateViewContext createView() throws RecognitionException {
		CreateViewContext _localctx = new CreateViewContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_createView);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1099);
			match(CREATE);
			setState(1100);
			match(VIEW);
			setState(1101);
			qualifiedName();
			setState(1103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(1102);
				columnList();
				}
			}

			setState(1105);
			match(AS);
			setState(1106);
			queryExpression();
			setState(1110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WITH) {
				{
				{
				setState(1107);
				viewOption();
				}
				}
				setState(1112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1113);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ViewOptionContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode CHECK() { return getToken(DB2Parser.CHECK, 0); }
		public TerminalNode OPTION() { return getToken(DB2Parser.OPTION, 0); }
		public TerminalNode CASCADED() { return getToken(DB2Parser.CASCADED, 0); }
		public TerminalNode LOCAL() { return getToken(DB2Parser.LOCAL, 0); }
		public ViewOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_viewOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterViewOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitViewOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitViewOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ViewOptionContext viewOption() throws RecognitionException {
		ViewOptionContext _localctx = new ViewOptionContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_viewOption);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1115);
			match(WITH);
			setState(1117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CASCADED || _la==LOCAL) {
				{
				setState(1116);
				_la = _input.LA(1);
				if ( !(_la==CASCADED || _la==LOCAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1119);
			match(CHECK);
			setState(1120);
			match(OPTION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateAliasContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode ALIAS() { return getToken(DB2Parser.ALIAS, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode PUBLIC() { return getToken(DB2Parser.PUBLIC, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public CreateAliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createAlias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateAlias(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateAliasContext createAlias() throws RecognitionException {
		CreateAliasContext _localctx = new CreateAliasContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_createAlias);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1122);
			match(CREATE);
			setState(1124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PUBLIC) {
				{
				setState(1123);
				match(PUBLIC);
				}
			}

			setState(1126);
			match(ALIAS);
			setState(1127);
			qualifiedName();
			setState(1128);
			match(FOR);
			setState(1130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEQUENCE || _la==TABLE) {
				{
				setState(1129);
				_la = _input.LA(1);
				if ( !(_la==SEQUENCE || _la==TABLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1132);
			qualifiedName();
			setState(1133);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateSynonymContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode SYNONYM() { return getToken(DB2Parser.SYNONYM, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public CreateSynonymContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createSynonym; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateSynonym(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateSynonym(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateSynonym(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateSynonymContext createSynonym() throws RecognitionException {
		CreateSynonymContext _localctx = new CreateSynonymContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_createSynonym);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1135);
			match(CREATE);
			setState(1136);
			match(SYNONYM);
			setState(1137);
			identifier();
			setState(1138);
			match(FOR);
			setState(1139);
			qualifiedName();
			setState(1140);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateSequenceContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public List<SequenceOptionContext> sequenceOption() {
			return getRuleContexts(SequenceOptionContext.class);
		}
		public SequenceOptionContext sequenceOption(int i) {
			return getRuleContext(SequenceOptionContext.class,i);
		}
		public CreateSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createSequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateSequenceContext createSequence() throws RecognitionException {
		CreateSequenceContext _localctx = new CreateSequenceContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_createSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1142);
			match(CREATE);
			setState(1143);
			match(SEQUENCE);
			setState(1144);
			qualifiedName();
			setState(1147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1145);
				match(AS);
				setState(1146);
				dataType();
				}
			}

			setState(1152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979175040515L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1149);
				sequenceOption();
				}
				}
				setState(1154);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1155);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SequenceOptionContext extends ParserRuleContext {
		public TerminalNode START() { return getToken(DB2Parser.START, 0); }
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public SignedNumberContext signedNumber() {
			return getRuleContext(SignedNumberContext.class,0);
		}
		public TerminalNode INCREMENT() { return getToken(DB2Parser.INCREMENT, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public TerminalNode MINVALUE() { return getToken(DB2Parser.MINVALUE, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode MAXVALUE() { return getToken(DB2Parser.MAXVALUE, 0); }
		public TerminalNode CYCLE() { return getToken(DB2Parser.CYCLE, 0); }
		public TerminalNode CACHE() { return getToken(DB2Parser.CACHE, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode ORDER() { return getToken(DB2Parser.ORDER, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public SequenceOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequenceOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSequenceOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSequenceOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSequenceOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SequenceOptionContext sequenceOption() throws RecognitionException {
		SequenceOptionContext _localctx = new SequenceOptionContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_sequenceOption);
		try {
			setState(1192);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,126,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1157);
				match(START);
				setState(1158);
				match(WITH);
				setState(1159);
				signedNumber();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1160);
				match(INCREMENT);
				setState(1161);
				match(BY);
				setState(1162);
				signedNumber();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1167);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MINVALUE:
					{
					setState(1163);
					match(MINVALUE);
					setState(1164);
					signedNumber();
					}
					break;
				case NO:
					{
					setState(1165);
					match(NO);
					setState(1166);
					match(MINVALUE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1173);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MAXVALUE:
					{
					setState(1169);
					match(MAXVALUE);
					setState(1170);
					signedNumber();
					}
					break;
				case NO:
					{
					setState(1171);
					match(NO);
					setState(1172);
					match(MAXVALUE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1178);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CYCLE:
					{
					setState(1175);
					match(CYCLE);
					}
					break;
				case NO:
					{
					setState(1176);
					match(NO);
					setState(1177);
					match(CYCLE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1184);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CACHE:
					{
					setState(1180);
					match(CACHE);
					setState(1181);
					match(NUMBER);
					}
					break;
				case NO:
					{
					setState(1182);
					match(NO);
					setState(1183);
					match(CACHE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1189);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ORDER:
					{
					setState(1186);
					match(ORDER);
					}
					break;
				case NO:
					{
					setState(1187);
					match(NO);
					setState(1188);
					match(ORDER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1191);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateRoleContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public CreateRoleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createRole; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateRole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateRole(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateRole(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateRoleContext createRole() throws RecognitionException {
		CreateRoleContext _localctx = new CreateRoleContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_createRole);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1194);
			match(CREATE);
			setState(1195);
			match(ROLE);
			setState(1196);
			identifier();
			setState(1197);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateAuxiliaryTableContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public TerminalNode STORES() { return getToken(DB2Parser.STORES, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode AUX() { return getToken(DB2Parser.AUX, 0); }
		public TerminalNode AUXILIARY() { return getToken(DB2Parser.AUXILIARY, 0); }
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode APPEND() { return getToken(DB2Parser.APPEND, 0); }
		public TerminalNode COLUMN() { return getToken(DB2Parser.COLUMN, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode PART() { return getToken(DB2Parser.PART, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public CreateAuxiliaryTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createAuxiliaryTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateAuxiliaryTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateAuxiliaryTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateAuxiliaryTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateAuxiliaryTableContext createAuxiliaryTable() throws RecognitionException {
		CreateAuxiliaryTableContext _localctx = new CreateAuxiliaryTableContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_createAuxiliaryTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1199);
			match(CREATE);
			setState(1200);
			_la = _input.LA(1);
			if ( !(_la==AUXILIARY || _la==AUX) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1201);
			match(TABLE);
			setState(1202);
			qualifiedName();
			setState(1205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IN) {
				{
				setState(1203);
				match(IN);
				setState(1204);
				qualifiedName();
				}
			}

			setState(1207);
			match(STORES);
			setState(1208);
			qualifiedName();
			setState(1211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==APPEND) {
				{
				setState(1209);
				match(APPEND);
				setState(1210);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLUMN) {
				{
				setState(1213);
				match(COLUMN);
				setState(1214);
				identifier();
				}
			}

			setState(1219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PART) {
				{
				setState(1217);
				match(PART);
				setState(1218);
				match(NUMBER);
				}
			}

			setState(1221);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTypeContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode DISTINCT() { return getToken(DB2Parser.DISTINCT, 0); }
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode COMPARISONS() { return getToken(DB2Parser.COMPARISONS, 0); }
		public CreateTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTypeContext createType() throws RecognitionException {
		CreateTypeContext _localctx = new CreateTypeContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_createType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1223);
			match(CREATE);
			setState(1225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISTINCT) {
				{
				setState(1224);
				match(DISTINCT);
				}
			}

			setState(1227);
			match(TYPE);
			setState(1228);
			qualifiedName();
			setState(1229);
			match(AS);
			setState(1230);
			dataType();
			setState(1233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WITH) {
				{
				setState(1231);
				match(WITH);
				setState(1232);
				match(COMPARISONS);
				}
			}

			setState(1235);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateVariableContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode VARIABLE() { return getToken(DB2Parser.VARIABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(DB2Parser.DEFAULT, 0); }
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public CreateVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createVariable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateVariableContext createVariable() throws RecognitionException {
		CreateVariableContext _localctx = new CreateVariableContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_createVariable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1237);
			match(CREATE);
			setState(1238);
			match(VARIABLE);
			setState(1239);
			qualifiedName();
			setState(1240);
			dataType();
			setState(1243);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(1241);
				match(DEFAULT);
				setState(1242);
				defaultValue();
				}
			}

			setState(1245);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateMaskContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode MASK() { return getToken(DB2Parser.MASK, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode COLUMN() { return getToken(DB2Parser.COLUMN, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode RETURN() { return getToken(DB2Parser.RETURN, 0); }
		public CaseExpressionContext caseExpression() {
			return getRuleContext(CaseExpressionContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public CreateMaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createMask; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateMask(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateMask(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateMask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateMaskContext createMask() throws RecognitionException {
		CreateMaskContext _localctx = new CreateMaskContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_createMask);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1247);
			match(CREATE);
			setState(1248);
			match(MASK);
			setState(1249);
			qualifiedName();
			setState(1250);
			match(ON);
			setState(1251);
			qualifiedName();
			setState(1254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1252);
				match(AS);
				setState(1253);
				identifier();
				}
			}

			setState(1256);
			match(FOR);
			setState(1257);
			match(COLUMN);
			setState(1258);
			identifier();
			setState(1259);
			match(RETURN);
			setState(1260);
			caseExpression();
			setState(1262);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISABLE || _la==ENABLE) {
				{
				setState(1261);
				_la = _input.LA(1);
				if ( !(_la==DISABLE || _la==ENABLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1264);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreatePermissionContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode PERMISSION() { return getToken(DB2Parser.PERMISSION, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public List<TerminalNode> FOR() { return getTokens(DB2Parser.FOR); }
		public TerminalNode FOR(int i) {
			return getToken(DB2Parser.FOR, i);
		}
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public List<TerminalNode> ACCESS() { return getTokens(DB2Parser.ACCESS); }
		public TerminalNode ACCESS(int i) {
			return getToken(DB2Parser.ACCESS, i);
		}
		public TerminalNode WHEN() { return getToken(DB2Parser.WHEN, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public SearchConditionContext searchCondition() {
			return getRuleContext(SearchConditionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode CONTROL() { return getToken(DB2Parser.CONTROL, 0); }
		public TerminalNode USING() { return getToken(DB2Parser.USING, 0); }
		public TerminalNode ENFORCED() { return getToken(DB2Parser.ENFORCED, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public CreatePermissionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createPermission; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreatePermission(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreatePermission(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreatePermission(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatePermissionContext createPermission() throws RecognitionException {
		CreatePermissionContext _localctx = new CreatePermissionContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_createPermission);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1266);
			match(CREATE);
			setState(1267);
			match(PERMISSION);
			setState(1268);
			qualifiedName();
			setState(1269);
			match(ON);
			setState(1270);
			qualifiedName();
			setState(1273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1271);
				match(AS);
				setState(1272);
				identifier();
				}
			}

			setState(1275);
			match(FOR);
			setState(1276);
			match(ROW);
			setState(1277);
			match(ACCESS);
			setState(1279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONTROL) {
				{
				setState(1278);
				match(CONTROL);
				}
			}

			setState(1282);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==USING) {
				{
				setState(1281);
				match(USING);
				}
			}

			setState(1284);
			match(WHEN);
			setState(1285);
			match(LPAREN);
			setState(1286);
			searchCondition(0);
			setState(1287);
			match(RPAREN);
			setState(1292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ENFORCED) {
				{
				setState(1288);
				match(ENFORCED);
				setState(1289);
				match(FOR);
				setState(1290);
				match(ALL);
				setState(1291);
				match(ACCESS);
				}
			}

			setState(1295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DISABLE || _la==ENABLE) {
				{
				setState(1294);
				_la = _input.LA(1);
				if ( !(_la==DISABLE || _la==ENABLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1297);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTrustedContextContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TRUSTED() { return getToken(DB2Parser.TRUSTED, 0); }
		public TerminalNode CONTEXT() { return getToken(DB2Parser.CONTEXT, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode BASED() { return getToken(DB2Parser.BASED, 0); }
		public TerminalNode UPON() { return getToken(DB2Parser.UPON, 0); }
		public TerminalNode CONNECTION() { return getToken(DB2Parser.CONNECTION, 0); }
		public TerminalNode USING() { return getToken(DB2Parser.USING, 0); }
		public TerminalNode SYSTEM() { return getToken(DB2Parser.SYSTEM, 0); }
		public TerminalNode AUTHID() { return getToken(DB2Parser.AUTHID, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TrustedContextOptionContext> trustedContextOption() {
			return getRuleContexts(TrustedContextOptionContext.class);
		}
		public TrustedContextOptionContext trustedContextOption(int i) {
			return getRuleContext(TrustedContextOptionContext.class,i);
		}
		public CreateTrustedContextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTrustedContext; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateTrustedContext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateTrustedContext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateTrustedContext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTrustedContextContext createTrustedContext() throws RecognitionException {
		CreateTrustedContextContext _localctx = new CreateTrustedContextContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_createTrustedContext);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1299);
			match(CREATE);
			setState(1300);
			match(TRUSTED);
			setState(1301);
			match(CONTEXT);
			setState(1302);
			identifier();
			setState(1303);
			match(BASED);
			setState(1304);
			match(UPON);
			setState(1305);
			match(CONNECTION);
			setState(1306);
			match(USING);
			setState(1307);
			match(SYSTEM);
			setState(1308);
			match(AUTHID);
			setState(1309);
			identifier();
			setState(1313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 621496766834933760L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158263299L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 144431916643598353L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1310);
				trustedContextOption();
				}
				}
				setState(1315);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1316);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrustedContextOptionContext extends ParserRuleContext {
		public TerminalNode ATTRIBUTES() { return getToken(DB2Parser.ATTRIBUTES, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<TrustedAttributeContext> trustedAttribute() {
			return getRuleContexts(TrustedAttributeContext.class);
		}
		public TrustedAttributeContext trustedAttribute(int i) {
			return getRuleContext(TrustedAttributeContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public TerminalNode DEFAULT() { return getToken(DB2Parser.DEFAULT, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public List<TrustedUserContext> trustedUser() {
			return getRuleContexts(TrustedUserContext.class);
		}
		public TrustedUserContext trustedUser(int i) {
			return getRuleContext(TrustedUserContext.class,i);
		}
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public TrustedContextOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trustedContextOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTrustedContextOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTrustedContextOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTrustedContextOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrustedContextOptionContext trustedContextOption() throws RecognitionException {
		TrustedContextOptionContext _localctx = new TrustedContextOptionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_trustedContextOption);
		int _la;
		try {
			setState(1351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,146,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1318);
				match(ATTRIBUTES);
				setState(1319);
				match(LPAREN);
				setState(1320);
				trustedAttribute();
				setState(1325);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1321);
					match(COMMA);
					setState(1322);
					trustedAttribute();
					}
					}
					setState(1327);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1328);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1330);
				_la = _input.LA(1);
				if ( !(_la==DISABLE || _la==ENABLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1332);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NO) {
					{
					setState(1331);
					match(NO);
					}
				}

				setState(1334);
				match(DEFAULT);
				setState(1335);
				match(ROLE);
				setState(1337);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,144,_ctx) ) {
				case 1:
					{
					setState(1336);
					identifier();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1339);
				match(WITH);
				setState(1340);
				match(USE);
				setState(1341);
				match(FOR);
				setState(1342);
				trustedUser();
				setState(1347);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1343);
					match(COMMA);
					setState(1344);
					trustedUser();
					}
					}
					setState(1349);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1350);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrustedAttributeContext extends ParserRuleContext {
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public TerminalNode EQ() { return getToken(DB2Parser.EQ, 0); }
		public TrustedAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trustedAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTrustedAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTrustedAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTrustedAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrustedAttributeContext trustedAttribute() throws RecognitionException {
		TrustedAttributeContext _localctx = new TrustedAttributeContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_trustedAttribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1353);
			nonReserved();
			setState(1356);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQ:
				{
				setState(1354);
				match(EQ);
				}
				break;
			case STRING:
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1358);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TrustedUserContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode PUBLIC() { return getToken(DB2Parser.PUBLIC, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public TerminalNode AUTHENTICATION() { return getToken(DB2Parser.AUTHENTICATION, 0); }
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode WITHOUT() { return getToken(DB2Parser.WITHOUT, 0); }
		public TrustedUserContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trustedUser; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTrustedUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTrustedUser(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTrustedUser(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrustedUserContext trustedUser() throws RecognitionException {
		TrustedUserContext _localctx = new TrustedUserContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_trustedUser);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1362);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case IDENTIFIER:
				{
				setState(1360);
				identifier();
				}
				break;
			case PUBLIC:
				{
				setState(1361);
				match(PUBLIC);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1366);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ROLE) {
				{
				setState(1364);
				match(ROLE);
				setState(1365);
				identifier();
				}
			}

			setState(1369);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,150,_ctx) ) {
			case 1:
				{
				setState(1368);
				_la = _input.LA(1);
				if ( !(_la==WITH || _la==WITHOUT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			}
			setState(1372);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,151,_ctx) ) {
			case 1:
				{
				setState(1371);
				match(AUTHENTICATION);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseExpressionContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(DB2Parser.CASE, 0); }
		public TerminalNode END() { return getToken(DB2Parser.END, 0); }
		public List<CaseWhenContext> caseWhen() {
			return getRuleContexts(CaseWhenContext.class);
		}
		public CaseWhenContext caseWhen(int i) {
			return getRuleContext(CaseWhenContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(DB2Parser.ELSE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CaseExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCaseExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCaseExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCaseExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseExpressionContext caseExpression() throws RecognitionException {
		CaseExpressionContext _localctx = new CaseExpressionContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_caseExpression);
		int _la;
		try {
			setState(1387);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,154,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1374);
				match(CASE);
				setState(1376); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1375);
					caseWhen();
					}
					}
					setState(1378); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(1382);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(1380);
					match(ELSE);
					setState(1381);
					expression(0);
					}
				}

				setState(1384);
				match(END);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1386);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclareGlobalTemporaryTableContext extends ParserRuleContext {
		public TerminalNode DECLARE() { return getToken(DB2Parser.DECLARE, 0); }
		public TerminalNode GLOBAL() { return getToken(DB2Parser.GLOBAL, 0); }
		public TerminalNode TEMPORARY() { return getToken(DB2Parser.TEMPORARY, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TableContentsContext tableContents() {
			return getRuleContext(TableContentsContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TableOptionContext> tableOption() {
			return getRuleContexts(TableOptionContext.class);
		}
		public TableOptionContext tableOption(int i) {
			return getRuleContext(TableOptionContext.class,i);
		}
		public DeclareGlobalTemporaryTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declareGlobalTemporaryTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDeclareGlobalTemporaryTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDeclareGlobalTemporaryTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDeclareGlobalTemporaryTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclareGlobalTemporaryTableContext declareGlobalTemporaryTable() throws RecognitionException {
		DeclareGlobalTemporaryTableContext _localctx = new DeclareGlobalTemporaryTableContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_declareGlobalTemporaryTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1389);
			match(DECLARE);
			setState(1390);
			match(GLOBAL);
			setState(1391);
			match(TEMPORARY);
			setState(1392);
			match(TABLE);
			setState(1393);
			qualifiedName();
			setState(1394);
			tableContents();
			setState(1398);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9570167533076480L) != 0) || ((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & 875951227036762185L) != 0) || ((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & 172897663934661377L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 146710104736350353L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953421825L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1395);
				tableOption();
				}
				}
				setState(1400);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1401);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateTriggerContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode TRIGGER() { return getToken(DB2Parser.TRIGGER, 0); }
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TriggerEventContext> triggerEvent() {
			return getRuleContexts(TriggerEventContext.class);
		}
		public TriggerEventContext triggerEvent(int i) {
			return getRuleContext(TriggerEventContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode BEFORE() { return getToken(DB2Parser.BEFORE, 0); }
		public TerminalNode AFTER() { return getToken(DB2Parser.AFTER, 0); }
		public TerminalNode INSTEAD() { return getToken(DB2Parser.INSTEAD, 0); }
		public TerminalNode OF() { return getToken(DB2Parser.OF, 0); }
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public TriggeredStatementContext triggeredStatement() {
			return getRuleContext(TriggeredStatementContext.class,0);
		}
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public List<TerminalNode> OR() { return getTokens(DB2Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(DB2Parser.OR, i);
		}
		public List<TriggerCorrelationContext> triggerCorrelation() {
			return getRuleContexts(TriggerCorrelationContext.class);
		}
		public TriggerCorrelationContext triggerCorrelation(int i) {
			return getRuleContext(TriggerCorrelationContext.class,i);
		}
		public TriggerGranularityContext triggerGranularity() {
			return getRuleContext(TriggerGranularityContext.class,0);
		}
		public CreateTriggerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTrigger; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateTrigger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateTrigger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateTrigger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateTriggerContext createTrigger() throws RecognitionException {
		CreateTriggerContext _localctx = new CreateTriggerContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_createTrigger);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1403);
			match(CREATE);
			setState(1404);
			match(TRIGGER);
			setState(1405);
			qualifiedName();
			setState(1408);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NO) {
				{
				setState(1406);
				match(NO);
				setState(1407);
				match(CASCADE);
				}
			}

			setState(1414);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BEFORE:
				{
				setState(1410);
				match(BEFORE);
				}
				break;
			case AFTER:
				{
				setState(1411);
				match(AFTER);
				}
				break;
			case INSTEAD:
				{
				setState(1412);
				match(INSTEAD);
				setState(1413);
				match(OF);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1416);
			triggerEvent();
			setState(1421);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(1417);
				match(OR);
				setState(1418);
				triggerEvent();
				}
				}
				setState(1423);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1424);
			match(ON);
			setState(1425);
			qualifiedName();
			setState(1429);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1426);
					triggerCorrelation();
					}
					} 
				}
				setState(1431);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			}
			setState(1433);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,160,_ctx) ) {
			case 1:
				{
				setState(1432);
				triggerGranularity();
				}
				break;
			}
			setState(1437);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,161,_ctx) ) {
			case 1:
				{
				setState(1435);
				compoundStatement();
				}
				break;
			case 2:
				{
				setState(1436);
				triggeredStatement();
				}
				break;
			}
			setState(1439);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TriggerEventContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(DB2Parser.INSERT, 0); }
		public TerminalNode DELETE() { return getToken(DB2Parser.DELETE, 0); }
		public TerminalNode UPDATE() { return getToken(DB2Parser.UPDATE, 0); }
		public TerminalNode OF() { return getToken(DB2Parser.OF, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TriggerEventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerEvent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTriggerEvent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTriggerEvent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTriggerEvent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggerEventContext triggerEvent() throws RecognitionException {
		TriggerEventContext _localctx = new TriggerEventContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_triggerEvent);
		int _la;
		try {
			setState(1455);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INSERT:
				enterOuterAlt(_localctx, 1);
				{
				setState(1441);
				match(INSERT);
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1442);
				match(DELETE);
				}
				break;
			case UPDATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1443);
				match(UPDATE);
				setState(1453);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OF) {
					{
					setState(1444);
					match(OF);
					setState(1445);
					identifier();
					setState(1450);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1446);
						match(COMMA);
						setState(1447);
						identifier();
						}
						}
						setState(1452);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TriggerCorrelationContext extends ParserRuleContext {
		public TerminalNode REFERENCING() { return getToken(DB2Parser.REFERENCING, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode OLD() { return getToken(DB2Parser.OLD, 0); }
		public TerminalNode NEW() { return getToken(DB2Parser.NEW, 0); }
		public TerminalNode OLD_TABLE() { return getToken(DB2Parser.OLD_TABLE, 0); }
		public TerminalNode NEW_TABLE() { return getToken(DB2Parser.NEW_TABLE, 0); }
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TriggerCorrelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerCorrelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTriggerCorrelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTriggerCorrelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTriggerCorrelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggerCorrelationContext triggerCorrelation() throws RecognitionException {
		TriggerCorrelationContext _localctx = new TriggerCorrelationContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_triggerCorrelation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1457);
			match(REFERENCING);
			setState(1458);
			_la = _input.LA(1);
			if ( !(_la==OLD || ((((_la - 362)) & ~0x3f) == 0 && ((1L << (_la - 362)) & 7L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1460);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ROW) {
				{
				setState(1459);
				match(ROW);
				}
			}

			setState(1463);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(1462);
				match(AS);
				}
			}

			setState(1465);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TriggerGranularityContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode EACH() { return getToken(DB2Parser.EACH, 0); }
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode STATEMENT() { return getToken(DB2Parser.STATEMENT, 0); }
		public TerminalNode MODE() { return getToken(DB2Parser.MODE, 0); }
		public TerminalNode DB2SQL() { return getToken(DB2Parser.DB2SQL, 0); }
		public TriggerGranularityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerGranularity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTriggerGranularity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTriggerGranularity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTriggerGranularity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggerGranularityContext triggerGranularity() throws RecognitionException {
		TriggerGranularityContext _localctx = new TriggerGranularityContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_triggerGranularity);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1467);
			match(FOR);
			setState(1468);
			match(EACH);
			setState(1469);
			_la = _input.LA(1);
			if ( !(_la==ROW || _la==STATEMENT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1472);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
			case 1:
				{
				setState(1470);
				match(MODE);
				setState(1471);
				match(DB2SQL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateProcedureContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode PROCEDURE() { return getToken(DB2Parser.PROCEDURE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode OR() { return getToken(DB2Parser.OR, 0); }
		public TerminalNode REPLACE() { return getToken(DB2Parser.REPLACE, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<RoutineClauseContext> routineClause() {
			return getRuleContexts(RoutineClauseContext.class);
		}
		public RoutineClauseContext routineClause(int i) {
			return getRuleContext(RoutineClauseContext.class,i);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public List<RoutineParameterContext> routineParameter() {
			return getRuleContexts(RoutineParameterContext.class);
		}
		public RoutineParameterContext routineParameter(int i) {
			return getRuleContext(RoutineParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public CreateProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createProcedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateProcedure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateProcedure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateProcedureContext createProcedure() throws RecognitionException {
		CreateProcedureContext _localctx = new CreateProcedureContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_createProcedure);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1474);
			match(CREATE);
			setState(1477);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OR) {
				{
				setState(1475);
				match(OR);
				setState(1476);
				match(REPLACE);
				}
			}

			setState(1479);
			match(PROCEDURE);
			setState(1480);
			qualifiedName();
			setState(1493);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				{
				setState(1481);
				match(LPAREN);
				setState(1490);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -9214364818939576320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415341310639800321L) != 0) || ((((_la - 137)) & ~0x3f) == 0 && ((1L << (_la - 137)) & 2683916901484557L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(1482);
					routineParameter();
					setState(1487);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1483);
						match(COMMA);
						setState(1484);
						routineParameter();
						}
						}
						setState(1489);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1492);
				match(RPAREN);
				}
				break;
			}
			setState(1498);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,172,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1495);
					routineClause();
					}
					} 
				}
				setState(1500);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,172,_ctx);
			}
			setState(1502);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(1501);
				compoundStatement();
				}
			}

			setState(1504);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateFunctionContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode FUNCTION() { return getToken(DB2Parser.FUNCTION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode OR() { return getToken(DB2Parser.OR, 0); }
		public TerminalNode REPLACE() { return getToken(DB2Parser.REPLACE, 0); }
		public List<RoutineParameterContext> routineParameter() {
			return getRuleContexts(RoutineParameterContext.class);
		}
		public RoutineParameterContext routineParameter(int i) {
			return getRuleContext(RoutineParameterContext.class,i);
		}
		public List<RoutineClauseContext> routineClause() {
			return getRuleContexts(RoutineClauseContext.class);
		}
		public RoutineClauseContext routineClause(int i) {
			return getRuleContext(RoutineClauseContext.class,i);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public CreateFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCreateFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCreateFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCreateFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateFunctionContext createFunction() throws RecognitionException {
		CreateFunctionContext _localctx = new CreateFunctionContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_createFunction);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1506);
			match(CREATE);
			setState(1509);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OR) {
				{
				setState(1507);
				match(OR);
				setState(1508);
				match(REPLACE);
				}
			}

			setState(1511);
			match(FUNCTION);
			setState(1512);
			qualifiedName();
			setState(1513);
			match(LPAREN);
			setState(1522);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -9214364818939576320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415341310639800321L) != 0) || ((((_la - 137)) & ~0x3f) == 0 && ((1L << (_la - 137)) & 2683916901484557L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
				{
				setState(1514);
				routineParameter();
				setState(1519);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1515);
					match(COMMA);
					setState(1516);
					routineParameter();
					}
					}
					setState(1521);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1524);
			match(RPAREN);
			setState(1528);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,177,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1525);
					routineClause();
					}
					} 
				}
				setState(1530);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,177,_ctx);
			}
			setState(1532);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(1531);
				compoundStatement();
				}
			}

			setState(1534);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RoutineParameterContext extends ParserRuleContext {
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode OUT() { return getToken(DB2Parser.OUT, 0); }
		public TerminalNode INOUT() { return getToken(DB2Parser.INOUT, 0); }
		public RoutineParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_routineParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterRoutineParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitRoutineParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitRoutineParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoutineParameterContext routineParameter() throws RecognitionException {
		RoutineParameterContext _localctx = new RoutineParameterContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_routineParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1537);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & 288230376151711777L) != 0)) {
				{
				setState(1536);
				_la = _input.LA(1);
				if ( !(((((_la - 107)) & ~0x3f) == 0 && ((1L << (_la - 107)) & 288230376151711777L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1540);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,180,_ctx) ) {
			case 1:
				{
				setState(1539);
				identifier();
				}
				break;
			}
			setState(1542);
			dataType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RoutineClauseContext extends ParserRuleContext {
		public TerminalNode RETURNS() { return getToken(DB2Parser.RETURNS, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<RoutineParameterContext> routineParameter() {
			return getRuleContexts(RoutineParameterContext.class);
		}
		public RoutineParameterContext routineParameter(int i) {
			return getRuleContext(RoutineParameterContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode LANGUAGE() { return getToken(DB2Parser.LANGUAGE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode EXTERNAL() { return getToken(DB2Parser.EXTERNAL, 0); }
		public TerminalNode NAME() { return getToken(DB2Parser.NAME, 0); }
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public TerminalNode PARAMETER() { return getToken(DB2Parser.PARAMETER, 0); }
		public TerminalNode STYLE() { return getToken(DB2Parser.STYLE, 0); }
		public TerminalNode SPECIFIC() { return getToken(DB2Parser.SPECIFIC, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DETERMINISTIC() { return getToken(DB2Parser.DETERMINISTIC, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode CONTAINS() { return getToken(DB2Parser.CONTAINS, 0); }
		public TerminalNode SQL() { return getToken(DB2Parser.SQL, 0); }
		public TerminalNode READS() { return getToken(DB2Parser.READS, 0); }
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode MODIFIES() { return getToken(DB2Parser.MODIFIES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public List<TerminalNode> NULL() { return getTokens(DB2Parser.NULL); }
		public TerminalNode NULL(int i) {
			return getToken(DB2Parser.NULL, i);
		}
		public TerminalNode INPUT() { return getToken(DB2Parser.INPUT, 0); }
		public TerminalNode CALLED() { return getToken(DB2Parser.CALLED, 0); }
		public TerminalNode RESULT() { return getToken(DB2Parser.RESULT, 0); }
		public TerminalNode SETS() { return getToken(DB2Parser.SETS, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode DYNAMIC() { return getToken(DB2Parser.DYNAMIC, 0); }
		public TerminalNode FENCED() { return getToken(DB2Parser.FENCED, 0); }
		public TerminalNode COLLID() { return getToken(DB2Parser.COLLID, 0); }
		public TerminalNode WLM() { return getToken(DB2Parser.WLM, 0); }
		public TerminalNode ENVIRONMENT() { return getToken(DB2Parser.ENVIRONMENT, 0); }
		public TerminalNode RUN() { return getToken(DB2Parser.RUN, 0); }
		public TerminalNode OPTIONS() { return getToken(DB2Parser.OPTIONS, 0); }
		public TerminalNode SPECIAL() { return getToken(DB2Parser.SPECIAL, 0); }
		public TerminalNode REGISTERS() { return getToken(DB2Parser.REGISTERS, 0); }
		public TerminalNode INHERIT() { return getToken(DB2Parser.INHERIT, 0); }
		public TerminalNode DEFAULT() { return getToken(DB2Parser.DEFAULT, 0); }
		public TerminalNode DEBUG() { return getToken(DB2Parser.DEBUG, 0); }
		public TerminalNode MODE() { return getToken(DB2Parser.MODE, 0); }
		public TerminalNode ALLOW() { return getToken(DB2Parser.ALLOW, 0); }
		public TerminalNode DISALLOW() { return getToken(DB2Parser.DISALLOW, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode ASUTIME() { return getToken(DB2Parser.ASUTIME, 0); }
		public TerminalNode LIMIT() { return getToken(DB2Parser.LIMIT, 0); }
		public TerminalNode STAY() { return getToken(DB2Parser.STAY, 0); }
		public TerminalNode RESIDENT() { return getToken(DB2Parser.RESIDENT, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode PROGRAM() { return getToken(DB2Parser.PROGRAM, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode MAIN() { return getToken(DB2Parser.MAIN, 0); }
		public TerminalNode SUB() { return getToken(DB2Parser.SUB, 0); }
		public TerminalNode SECURITY() { return getToken(DB2Parser.SECURITY, 0); }
		public TerminalNode DB2() { return getToken(DB2Parser.DB2, 0); }
		public TerminalNode USER() { return getToken(DB2Parser.USER, 0); }
		public TerminalNode DEFINER() { return getToken(DB2Parser.DEFINER, 0); }
		public TerminalNode COMMIT() { return getToken(DB2Parser.COMMIT, 0); }
		public TerminalNode RETURN() { return getToken(DB2Parser.RETURN, 0); }
		public TerminalNode PACKAGE() { return getToken(DB2Parser.PACKAGE, 0); }
		public TerminalNode PATH() { return getToken(DB2Parser.PATH, 0); }
		public TerminalNode QUALIFIER() { return getToken(DB2Parser.QUALIFIER, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public RoutineClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_routineClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterRoutineClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitRoutineClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitRoutineClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RoutineClauseContext routineClause() throws RecognitionException {
		RoutineClauseContext _localctx = new RoutineClauseContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_routineClause);
		int _la;
		try {
			setState(1649);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,191,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1544);
				match(RETURNS);
				setState(1558);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CAPTURE:
				case CHAR:
				case CHARACTER:
				case CLONE:
				case COLLID:
				case DBINFO:
				case DOUBLE:
				case ENCODING:
				case FINAL:
				case FIRST:
				case ISOBID:
				case JAR:
				case LAST:
				case LC_CTYPE:
				case LOCALE:
				case LONG:
				case MAINTAINED:
				case MATERIALIZED:
				case NEXT:
				case NULLS:
				case OBID:
				case OPTIMIZATION:
				case OPTIMIZE:
				case ORGANIZATION:
				case PADDED:
				case PLAN:
				case PREVVAL:
				case PROGRAM:
				case PSID:
				case QUERYNO:
				case ROWSET:
				case SCRATCHPAD:
				case SIMPLE:
				case SOURCE:
				case STANDARD:
				case STORES:
				case STYLE:
				case SUMMARY:
				case SYSFUN:
				case SYSIBM:
				case SYSPROC:
				case TYPE:
				case VALUE:
				case VARIANT:
				case AUTHENTICATION:
				case AUTHID:
				case BASED:
				case CONTROL:
				case UPON:
				case ABSOLUTE:
				case ACCESS:
				case ACTION:
				case ADMIN:
				case ALWAYS:
				case ASC:
				case ASUTIME:
				case AT:
				case ATOMIC:
				case ATTRIBUTES:
				case AUX:
				case BIT:
				case CACHE:
				case CALLED:
				case CARDINALITY:
				case CASCADE:
				case CHANGE:
				case CHANGED:
				case CHANGES:
				case COMPARISONS:
				case COMPRESS:
				case CONTEXT:
				case COPY:
				case CURSORS:
				case CYCLE:
				case DATACLAS:
				case DB2:
				case DB2SQL:
				case DEBUG:
				case DEFER:
				case DEFINE:
				case DEFINER:
				case DESC:
				case EACH:
				case ENABLE:
				case ENFORCED:
				case ENVIRONMENT:
				case EVERY:
				case EXCLUDE:
				case EXCLUDING:
				case EXCLUSIVE:
				case FREEPAGE:
				case GBPCACHE:
				case GENERATE:
				case HASH:
				case HIDDEN_KW:
				case HISTORY:
				case ID:
				case IDENTITY:
				case IMPLICITLY:
				case INCLUDE:
				case INCLUDING:
				case INCREMENT:
				case INDEXBP:
				case INLINE:
				case INPUT:
				case INSTEAD:
				case KEYS:
				case LARGE:
				case LENGTH:
				case LIMIT:
				case LOAD:
				case LOB:
				case LOGGED:
				case MAIN:
				case MASK:
				case MAXPARTITIONS:
				case MAXROWS:
				case MAXVALUE:
				case MEMBER:
				case MGMTCLAS:
				case MINVALUE:
				case MIXED:
				case MODE:
				case NAME:
				case NEW:
				case NEW_TABLE:
				case OLD_TABLE:
				case ONLY:
				case OPTION:
				case OPTIONS:
				case ORGANIZE:
				case PAGE:
				case PAGENUM:
				case PCTFREE:
				case PERMISSION:
				case PRIMARY:
				case QUALIFIER:
				case RANDOM:
				case RANGE:
				case REGENERATE:
				case REGISTERS:
				case RELATIVE:
				case REMOVE:
				case REPLACE:
				case RESET:
				case RESIDENT:
				case RESTART:
				case RETAIN:
				case ROTATE:
				case ROWS:
				case SBCS:
				case SECURED:
				case SEGSIZE:
				case SETS:
				case SHARE:
				case SIZE:
				case SPACE:
				case SPECIAL:
				case SQL:
				case SQLID:
				case START:
				case STARTING:
				case STATEMENT:
				case STORCLAS:
				case SUB:
				case TEMPORARY:
				case TIME:
				case TIMESTAMP:
				case TRACKMOD:
				case TRUSTED:
				case UNLOAD:
				case USAGE:
				case USE:
				case VARCHAR:
				case VARGRAPHIC:
				case VARYING:
				case VERSIONING:
				case WITHOUT:
				case WORK:
				case XMLPATTERN:
				case YES:
				case DELIMITED_IDENTIFIER:
				case PLACEHOLDER:
				case IDENTIFIER:
					{
					setState(1545);
					dataType();
					}
					break;
				case TABLE:
					{
					setState(1546);
					match(TABLE);
					setState(1547);
					match(LPAREN);
					setState(1548);
					routineParameter();
					setState(1553);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1549);
						match(COMMA);
						setState(1550);
						routineParameter();
						}
						}
						setState(1555);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(1556);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1560);
				match(LANGUAGE);
				setState(1561);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1562);
				match(EXTERNAL);
				setState(1568);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,184,_ctx) ) {
				case 1:
					{
					setState(1563);
					match(NAME);
					setState(1566);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case STRING:
						{
						setState(1564);
						match(STRING);
						}
						break;
					case CAPTURE:
					case CLONE:
					case COLLID:
					case DBINFO:
					case ENCODING:
					case FINAL:
					case FIRST:
					case ISOBID:
					case JAR:
					case LAST:
					case LC_CTYPE:
					case LOCALE:
					case MAINTAINED:
					case MATERIALIZED:
					case NEXT:
					case NULLS:
					case OBID:
					case OPTIMIZATION:
					case OPTIMIZE:
					case ORGANIZATION:
					case PADDED:
					case PLAN:
					case PREVVAL:
					case PROGRAM:
					case PSID:
					case QUERYNO:
					case ROWSET:
					case SCRATCHPAD:
					case SIMPLE:
					case SOURCE:
					case STANDARD:
					case STORES:
					case STYLE:
					case SUMMARY:
					case SYSFUN:
					case SYSIBM:
					case SYSPROC:
					case TYPE:
					case VALUE:
					case VARIANT:
					case AUTHENTICATION:
					case AUTHID:
					case BASED:
					case CONTROL:
					case UPON:
					case ABSOLUTE:
					case ACCESS:
					case ACTION:
					case ADMIN:
					case ALWAYS:
					case ASC:
					case ASUTIME:
					case AT:
					case ATOMIC:
					case ATTRIBUTES:
					case AUX:
					case BIT:
					case CACHE:
					case CALLED:
					case CARDINALITY:
					case CASCADE:
					case CHANGE:
					case CHANGED:
					case CHANGES:
					case COMPARISONS:
					case COMPRESS:
					case CONTEXT:
					case COPY:
					case CURSORS:
					case CYCLE:
					case DATACLAS:
					case DB2:
					case DB2SQL:
					case DEBUG:
					case DEFER:
					case DEFINE:
					case DEFINER:
					case DESC:
					case EACH:
					case ENABLE:
					case ENFORCED:
					case ENVIRONMENT:
					case EVERY:
					case EXCLUDE:
					case EXCLUDING:
					case EXCLUSIVE:
					case FREEPAGE:
					case GBPCACHE:
					case GENERATE:
					case HASH:
					case HIDDEN_KW:
					case HISTORY:
					case ID:
					case IDENTITY:
					case IMPLICITLY:
					case INCLUDE:
					case INCLUDING:
					case INCREMENT:
					case INDEXBP:
					case INLINE:
					case INPUT:
					case INSTEAD:
					case KEYS:
					case LARGE:
					case LENGTH:
					case LIMIT:
					case LOAD:
					case LOB:
					case LOGGED:
					case MAIN:
					case MASK:
					case MAXPARTITIONS:
					case MAXROWS:
					case MAXVALUE:
					case MEMBER:
					case MGMTCLAS:
					case MINVALUE:
					case MIXED:
					case MODE:
					case NAME:
					case NEW:
					case NEW_TABLE:
					case OLD_TABLE:
					case ONLY:
					case OPTION:
					case OPTIONS:
					case ORGANIZE:
					case PAGE:
					case PAGENUM:
					case PCTFREE:
					case PERMISSION:
					case PRIMARY:
					case QUALIFIER:
					case RANDOM:
					case RANGE:
					case REGENERATE:
					case REGISTERS:
					case RELATIVE:
					case REMOVE:
					case REPLACE:
					case RESET:
					case RESIDENT:
					case RESTART:
					case RETAIN:
					case ROTATE:
					case ROWS:
					case SBCS:
					case SECURED:
					case SEGSIZE:
					case SETS:
					case SHARE:
					case SIZE:
					case SPACE:
					case SPECIAL:
					case SQL:
					case SQLID:
					case START:
					case STARTING:
					case STATEMENT:
					case STORCLAS:
					case SUB:
					case TEMPORARY:
					case TIME:
					case TIMESTAMP:
					case TRACKMOD:
					case TRUSTED:
					case UNLOAD:
					case USAGE:
					case USE:
					case VARCHAR:
					case VARGRAPHIC:
					case VARYING:
					case VERSIONING:
					case WITHOUT:
					case WORK:
					case XMLPATTERN:
					case YES:
					case DELIMITED_IDENTIFIER:
					case PLACEHOLDER:
					case IDENTIFIER:
						{
						setState(1565);
						identifier();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1570);
				match(PARAMETER);
				setState(1571);
				match(STYLE);
				setState(1572);
				identifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1573);
				match(SPECIFIC);
				setState(1574);
				qualifiedName();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1576);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(1575);
					match(NOT);
					}
				}

				setState(1578);
				match(DETERMINISTIC);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1589);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CONTAINS:
					{
					setState(1579);
					match(CONTAINS);
					setState(1580);
					match(SQL);
					}
					break;
				case READS:
					{
					setState(1581);
					match(READS);
					setState(1582);
					match(SQL);
					setState(1583);
					match(DATA);
					}
					break;
				case MODIFIES:
					{
					setState(1584);
					match(MODIFIES);
					setState(1585);
					match(SQL);
					setState(1586);
					match(DATA);
					}
					break;
				case NO:
					{
					setState(1587);
					match(NO);
					setState(1588);
					match(SQL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1594);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CALLED:
					{
					setState(1591);
					match(CALLED);
					}
					break;
				case RETURNS:
					{
					setState(1592);
					match(RETURNS);
					setState(1593);
					match(NULL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1596);
				match(ON);
				setState(1597);
				match(NULL);
				setState(1598);
				match(INPUT);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1600);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DYNAMIC) {
					{
					setState(1599);
					match(DYNAMIC);
					}
				}

				setState(1602);
				match(RESULT);
				setState(1603);
				match(SETS);
				setState(1604);
				match(NUMBER);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1608);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FENCED:
					{
					setState(1605);
					match(FENCED);
					}
					break;
				case NOT:
					{
					setState(1606);
					match(NOT);
					setState(1607);
					match(FENCED);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1610);
				match(COLLID);
				setState(1611);
				identifier();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1612);
				match(WLM);
				setState(1613);
				match(ENVIRONMENT);
				setState(1614);
				identifier();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1615);
				match(RUN);
				setState(1616);
				match(OPTIONS);
				setState(1617);
				match(STRING);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(1618);
				_la = _input.LA(1);
				if ( !(_la==DEFAULT || _la==INHERIT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1619);
				match(SPECIAL);
				setState(1620);
				match(REGISTERS);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(1621);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1729382256910270720L) != 0) || _la==ENABLE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1622);
				match(DEBUG);
				setState(1623);
				match(MODE);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(1624);
				match(ASUTIME);
				setState(1629);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NO:
					{
					setState(1625);
					match(NO);
					setState(1626);
					match(LIMIT);
					}
					break;
				case LIMIT:
					{
					setState(1627);
					match(LIMIT);
					setState(1628);
					match(NUMBER);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(1631);
				match(STAY);
				setState(1632);
				match(RESIDENT);
				setState(1633);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(1634);
				match(PROGRAM);
				setState(1635);
				match(TYPE);
				setState(1636);
				_la = _input.LA(1);
				if ( !(_la==MAIN || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(1637);
				match(SECURITY);
				setState(1638);
				_la = _input.LA(1);
				if ( !(((((_la - 255)) & ~0x3f) == 0 && ((1L << (_la - 255)) & 4755801206503243777L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				{
				setState(1639);
				match(COMMIT);
				setState(1640);
				match(ON);
				setState(1641);
				match(RETURN);
				setState(1642);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(1643);
				match(PACKAGE);
				setState(1644);
				match(PATH);
				setState(1645);
				identifier();
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(1646);
				match(QUALIFIER);
				setState(1647);
				identifier();
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(1648);
				nonReserved();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompoundStatementContext extends ParserRuleContext {
		public TerminalNode BEGIN() { return getToken(DB2Parser.BEGIN, 0); }
		public TerminalNode END() { return getToken(DB2Parser.END, 0); }
		public TerminalNode ATOMIC() { return getToken(DB2Parser.ATOMIC, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCompoundStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCompoundStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_compoundStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1651);
			match(BEGIN);
			setState(1653);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,192,_ctx) ) {
			case 1:
				{
				setState(1652);
				match(ATOMIC);
				}
				break;
			}
			setState(1658);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,193,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1655);
					bodyItem();
					}
					} 
				}
				setState(1660);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,193,_ctx);
			}
			setState(1661);
			if (!(closesBlock(_localctx))) throw new FailedPredicateException(this, "closesBlock($ctx)");
			setState(1662);
			match(END);
			setState(1664);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,194,_ctx) ) {
			case 1:
				{
				setState(1663);
				identifier();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyItemContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public TerminalNode END() { return getToken(DB2Parser.END, 0); }
		public TerminalNode IF() { return getToken(DB2Parser.IF, 0); }
		public TerminalNode WHILE() { return getToken(DB2Parser.WHILE, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode CASE() { return getToken(DB2Parser.CASE, 0); }
		public TerminalNode LOOP() { return getToken(DB2Parser.LOOP, 0); }
		public TerminalNode REPEAT() { return getToken(DB2Parser.REPEAT, 0); }
		public TerminalNode BEGIN() { return getToken(DB2Parser.BEGIN, 0); }
		public BodyItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bodyItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterBodyItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitBodyItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitBodyItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyItemContext bodyItem() throws RecognitionException {
		BodyItemContext _localctx = new BodyItemContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_bodyItem);
		int _la;
		try {
			setState(1670);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BEGIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1666);
				compoundStatement();
				}
				break;
			case END:
				enterOuterAlt(_localctx, 2);
				{
				setState(1667);
				match(END);
				setState(1668);
				_la = _input.LA(1);
				if ( !(_la==CASE || ((((_la - 88)) & ~0x3f) == 0 && ((1L << (_la - 88)) & 1125899906973697L) != 0) || _la==REPEAT || _la==WHILE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case WS:
			case LINE_COMMENT:
			case BLOCK_COMMENT:
			case ADD:
			case AFTER:
			case ALL:
			case ALLOCATE:
			case ALLOW:
			case ALTER:
			case AND:
			case ANY:
			case AS:
			case ASENSITIVE:
			case ASSOCIATE:
			case AUXILIARY:
			case BEFORE:
			case BETWEEN:
			case BUFFERPOOL:
			case BY:
			case CALL:
			case CAPTURE:
			case CASCADED:
			case CASE:
			case CAST:
			case CCSID:
			case CHAR:
			case CHARACTER:
			case CHECK:
			case CLONE:
			case CLOSE:
			case CLUSTER:
			case COLLECTION:
			case COLLID:
			case COLUMN:
			case COMMENT:
			case COMMIT:
			case CONCAT:
			case CONDITION:
			case CONNECT:
			case CONNECTION:
			case CONSTRAINT:
			case CONTAINS:
			case CONTINUE:
			case CREATE:
			case CUBE:
			case CURRENT:
			case CURSOR:
			case DATA:
			case DATABASE:
			case DAY:
			case DAYS:
			case DBINFO:
			case DECLARE:
			case DEFAULT:
			case DELETE:
			case DESCRIPTOR:
			case DETERMINISTIC:
			case DISABLE:
			case DISALLOW:
			case DISTINCT:
			case DO:
			case DOUBLE:
			case DROP:
			case DSSIZE:
			case DYNAMIC:
			case EDITPROC:
			case ELSE:
			case ELSEIF:
			case ENCODING:
			case ENDING:
			case ERASE:
			case ESCAPE:
			case EXCEPT:
			case EXCEPTION:
			case EXEC:
			case EXECUTE:
			case EXISTS:
			case EXIT:
			case EXPLAIN:
			case EXTERNAL:
			case FENCED:
			case FETCH:
			case FIELDPROC:
			case FINAL:
			case FIRST:
			case FOR:
			case FREE:
			case FROM:
			case FULL:
			case FUNCTION:
			case GENERATED:
			case GET:
			case GLOBAL:
			case GO:
			case GOTO:
			case GRANT:
			case GROUP:
			case HANDLER:
			case HAVING:
			case HOLD:
			case HOUR:
			case HOURS:
			case IF:
			case IMMEDIATE:
			case IN:
			case INCLUSIVE:
			case INDEX:
			case INHERIT:
			case INNER:
			case INOUT:
			case INSENSITIVE:
			case INSERT:
			case INTERSECT:
			case INTO:
			case IS:
			case ISOBID:
			case ITERATE:
			case JAR:
			case JOIN:
			case KEY:
			case LABEL:
			case LANGUAGE:
			case LAST:
			case LC_CTYPE:
			case LEAVE:
			case LEFT:
			case LIKE:
			case LOCAL:
			case LOCALE:
			case LOCATOR:
			case LOCATORS:
			case LOCK:
			case LOCKMAX:
			case LOCKSIZE:
			case LONG:
			case LOOP:
			case MAINTAINED:
			case MATERIALIZED:
			case MICROSECOND:
			case MICROSECONDS:
			case MINUTE:
			case MINUTES:
			case MODIFIES:
			case MONTH:
			case MONTHS:
			case NEXT:
			case NO:
			case NONE:
			case NOT:
			case NULL:
			case NULLS:
			case NUMPARTS:
			case OBID:
			case OF:
			case OLD:
			case ON:
			case OPEN:
			case OPTIMIZATION:
			case OPTIMIZE:
			case OR:
			case ORDER:
			case ORGANIZATION:
			case OUT:
			case OUTER:
			case PACKAGE:
			case PARAMETER:
			case PART:
			case PADDED:
			case PARTITION:
			case PARTITIONED:
			case PARTITIONING:
			case PATH:
			case PIECESIZE:
			case PERIOD:
			case PLAN:
			case PRECISION:
			case PREPARE:
			case PREVVAL:
			case PRIQTY:
			case PRIVILEGES:
			case PROCEDURE:
			case PROGRAM:
			case PSID:
			case PUBLIC:
			case QUERY:
			case QUERYNO:
			case READS:
			case REFERENCES:
			case REFRESH:
			case RESIGNAL:
			case RELEASE:
			case RENAME:
			case REPEAT:
			case RESTRICT:
			case RESULT:
			case RETURN:
			case RETURNS:
			case REVOKE:
			case RIGHT:
			case ROLE:
			case ROLLBACK:
			case ROLLUP:
			case ROUND_CEILING:
			case ROUND_DOWN:
			case ROUND_FLOOR:
			case ROUND_HALF_DOWN:
			case ROUND_HALF_EVEN:
			case ROUND_HALF_UP:
			case ROUND_UP:
			case ROW:
			case ROWSET:
			case RUN:
			case SAVEPOINT:
			case SCHEMA:
			case SCRATCHPAD:
			case SECOND:
			case SECONDS:
			case SECQTY:
			case SECURITY:
			case SEQUENCE:
			case SELECT:
			case SENSITIVE:
			case SET:
			case SIGNAL:
			case SIMPLE:
			case SOME:
			case SOURCE:
			case SPECIFIC:
			case STANDARD:
			case STATIC:
			case STAY:
			case STOGROUP:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYNONYM:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case SYSTEM:
			case TABLE:
			case TABLESPACE:
			case THEN:
			case TO:
			case TRIGGER:
			case TRUNCATE:
			case TYPE:
			case UNDO:
			case UNION:
			case UNIQUE:
			case UNTIL:
			case UPDATE:
			case USER:
			case USING:
			case VALIDPROC:
			case VALUE:
			case VALUES:
			case VARIABLE:
			case VARIANT:
			case VCAT:
			case VIEW:
			case VOLATILE:
			case VOLUMES:
			case WHEN:
			case WHENEVER:
			case WHERE:
			case WHILE:
			case WITH:
			case WLM:
			case XMLCAST:
			case XMLEXISTS:
			case XMLNAMESPACES:
			case YEAR:
			case YEARS:
			case ZONE:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALIAS:
			case ALWAYS:
			case APPEND:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUDIT:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FOREIGN:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REFERENCING:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRANSACTION:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case WORKFILE:
			case XMLPATTERN:
			case YES:
			case LPAREN:
			case RPAREN:
			case COMMA:
			case SEMI:
			case DOT:
			case COLON:
			case STAR:
			case PLUS:
			case MINUS:
			case SLASH:
			case CONCAT_OP:
			case EQ:
			case NEQ:
			case LTE:
			case GTE:
			case LT:
			case GT:
			case QUESTION:
			case STRING:
			case HEX_STRING:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case NUMBER:
			case IDENTIFIER:
			case HOST_VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1669);
				_la = _input.LA(1);
				if ( _la <= 0 || (_la==BEGIN || _la==END) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TriggeredStatementContext extends ParserRuleContext {
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public TriggeredStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggeredStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTriggeredStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTriggeredStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTriggeredStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TriggeredStatementContext triggeredStatement() throws RecognitionException {
		TriggeredStatementContext _localctx = new TriggeredStatementContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_triggeredStatement);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1673); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1672);
					bodyItem();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1675); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,196,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterTableContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<AlterTableActionContext> alterTableAction() {
			return getRuleContexts(AlterTableActionContext.class);
		}
		public AlterTableActionContext alterTableAction(int i) {
			return getRuleContext(AlterTableActionContext.class,i);
		}
		public AlterTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTableContext alterTable() throws RecognitionException {
		AlterTableContext _localctx = new AlterTableContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_alterTable);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1677);
			match(ALTER);
			setState(1678);
			match(TABLE);
			setState(1679);
			qualifiedName();
			setState(1681); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1680);
					alterTableAction();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1683); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,197,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(1685);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterTableActionContext extends ParserRuleContext {
		public TerminalNode ADD() { return getToken(DB2Parser.ADD, 0); }
		public ColumnDefinitionContext columnDefinition() {
			return getRuleContext(ColumnDefinitionContext.class,0);
		}
		public TerminalNode COLUMN() { return getToken(DB2Parser.COLUMN, 0); }
		public TableConstraintContext tableConstraint() {
			return getRuleContext(TableConstraintContext.class,0);
		}
		public PeriodDefinitionContext periodDefinition() {
			return getRuleContext(PeriodDefinitionContext.class,0);
		}
		public TerminalNode PARTITION() { return getToken(DB2Parser.PARTITION, 0); }
		public PartitionSpecContext partitionSpec() {
			return getRuleContext(PartitionSpecContext.class,0);
		}
		public TerminalNode VERSIONING() { return getToken(DB2Parser.VERSIONING, 0); }
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode HISTORY() { return getToken(DB2Parser.HISTORY, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public AlterColumnActionContext alterColumnAction() {
			return getRuleContext(AlterColumnActionContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode DROP() { return getToken(DB2Parser.DROP, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public TerminalNode RESTRICT() { return getToken(DB2Parser.RESTRICT, 0); }
		public TerminalNode PRIMARY() { return getToken(DB2Parser.PRIMARY, 0); }
		public TerminalNode KEY() { return getToken(DB2Parser.KEY, 0); }
		public TerminalNode FOREIGN() { return getToken(DB2Parser.FOREIGN, 0); }
		public TerminalNode UNIQUE() { return getToken(DB2Parser.UNIQUE, 0); }
		public TerminalNode CHECK() { return getToken(DB2Parser.CHECK, 0); }
		public TerminalNode CONSTRAINT() { return getToken(DB2Parser.CONSTRAINT, 0); }
		public TerminalNode RENAME() { return getToken(DB2Parser.RENAME, 0); }
		public TerminalNode TO() { return getToken(DB2Parser.TO, 0); }
		public TerminalNode ROTATE() { return getToken(DB2Parser.ROTATE, 0); }
		public TerminalNode LAST() { return getToken(DB2Parser.LAST, 0); }
		public TerminalNode ENDING() { return getToken(DB2Parser.ENDING, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode FIRST() { return getToken(DB2Parser.FIRST, 0); }
		public TerminalNode AT() { return getToken(DB2Parser.AT, 0); }
		public TerminalNode RESET() { return getToken(DB2Parser.RESET, 0); }
		public TableOptionContext tableOption() {
			return getRuleContext(TableOptionContext.class,0);
		}
		public AlterTableActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTableAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterTableAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterTableAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterTableAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTableActionContext alterTableAction() throws RecognitionException {
		AlterTableActionContext _localctx = new AlterTableActionContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_alterTableAction);
		int _la;
		try {
			setState(1768);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,208,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1687);
				match(ADD);
				setState(1689);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLUMN) {
					{
					setState(1688);
					match(COLUMN);
					}
				}

				setState(1691);
				columnDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1693);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ADD) {
					{
					setState(1692);
					match(ADD);
					}
				}

				setState(1695);
				tableConstraint();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1696);
				match(ADD);
				setState(1697);
				periodDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1698);
				match(ADD);
				setState(1699);
				match(PARTITION);
				setState(1701);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,200,_ctx) ) {
				case 1:
					{
					setState(1700);
					partitionSpec();
					}
					break;
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1703);
				match(ADD);
				{
				setState(1704);
				match(VERSIONING);
				setState(1705);
				match(USE);
				setState(1706);
				match(HISTORY);
				setState(1707);
				match(TABLE);
				setState(1708);
				qualifiedName();
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1709);
				match(ALTER);
				setState(1711);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLUMN) {
					{
					setState(1710);
					match(COLUMN);
					}
				}

				setState(1713);
				identifier();
				setState(1714);
				alterColumnAction();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1716);
				match(ALTER);
				setState(1717);
				match(PARTITION);
				setState(1718);
				match(NUMBER);
				setState(1719);
				partitionSpec();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1720);
				match(DROP);
				setState(1722);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLUMN) {
					{
					setState(1721);
					match(COLUMN);
					}
				}

				setState(1724);
				identifier();
				setState(1726);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,203,_ctx) ) {
				case 1:
					{
					setState(1725);
					_la = _input.LA(1);
					if ( !(_la==RESTRICT || _la==CASCADE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1728);
				match(DROP);
				setState(1740);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PRIMARY:
					{
					setState(1729);
					match(PRIMARY);
					setState(1730);
					match(KEY);
					}
					break;
				case FOREIGN:
					{
					setState(1731);
					match(FOREIGN);
					setState(1732);
					match(KEY);
					setState(1733);
					identifier();
					}
					break;
				case UNIQUE:
					{
					setState(1734);
					match(UNIQUE);
					setState(1735);
					identifier();
					}
					break;
				case CHECK:
					{
					setState(1736);
					match(CHECK);
					setState(1737);
					identifier();
					}
					break;
				case CONSTRAINT:
					{
					setState(1738);
					match(CONSTRAINT);
					setState(1739);
					identifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1742);
				match(DROP);
				setState(1743);
				match(VERSIONING);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1744);
				match(RENAME);
				setState(1746);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLUMN) {
					{
					setState(1745);
					match(COLUMN);
					}
				}

				setState(1748);
				identifier();
				setState(1749);
				match(TO);
				setState(1750);
				identifier();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1752);
				match(ROTATE);
				setState(1753);
				match(PARTITION);
				setState(1754);
				_la = _input.LA(1);
				if ( !(_la==FIRST || _la==NUMBER) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1755);
				match(TO);
				setState(1756);
				match(LAST);
				setState(1757);
				match(ENDING);
				setState(1759);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AT) {
					{
					setState(1758);
					match(AT);
					}
				}

				setState(1761);
				match(LPAREN);
				setState(1762);
				constant();
				setState(1763);
				match(RPAREN);
				setState(1765);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,207,_ctx) ) {
				case 1:
					{
					setState(1764);
					match(RESET);
					}
					break;
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1767);
				tableOption();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterColumnActionContext extends ParserRuleContext {
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public TerminalNode SET() { return getToken(DB2Parser.SET, 0); }
		public TerminalNode DEFAULT() { return getToken(DB2Parser.DEFAULT, 0); }
		public DefaultValueContext defaultValue() {
			return getRuleContext(DefaultValueContext.class,0);
		}
		public TerminalNode DROP() { return getToken(DB2Parser.DROP, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode GENERATED() { return getToken(DB2Parser.GENERATED, 0); }
		public TerminalNode ALWAYS() { return getToken(DB2Parser.ALWAYS, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public GeneratedAsContext generatedAs() {
			return getRuleContext(GeneratedAsContext.class,0);
		}
		public TerminalNode IDENTITY() { return getToken(DB2Parser.IDENTITY, 0); }
		public TerminalNode INLINE() { return getToken(DB2Parser.INLINE, 0); }
		public TerminalNode LENGTH() { return getToken(DB2Parser.LENGTH, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public List<SequenceOptionContext> sequenceOption() {
			return getRuleContexts(SequenceOptionContext.class);
		}
		public SequenceOptionContext sequenceOption(int i) {
			return getRuleContext(SequenceOptionContext.class,i);
		}
		public AlterColumnActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterColumnAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterColumnAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterColumnAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterColumnAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterColumnActionContext alterColumnAction() throws RecognitionException {
		AlterColumnActionContext _localctx = new AlterColumnActionContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_alterColumnAction);
		int _la;
		try {
			int _alt;
			setState(1810);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,214,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1771);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==SET) {
					{
					setState(1770);
					match(SET);
					}
				}

				setState(1773);
				match(DATA);
				setState(1774);
				match(TYPE);
				setState(1775);
				dataType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1776);
				match(SET);
				setState(1777);
				match(DEFAULT);
				setState(1779);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,210,_ctx) ) {
				case 1:
					{
					setState(1778);
					defaultValue();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1781);
				match(DROP);
				setState(1782);
				match(DEFAULT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1783);
				match(SET);
				setState(1784);
				match(NOT);
				setState(1785);
				match(NULL);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1786);
				match(DROP);
				setState(1787);
				match(NOT);
				setState(1788);
				match(NULL);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1789);
				match(SET);
				setState(1790);
				match(GENERATED);
				setState(1794);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ALWAYS:
					{
					setState(1791);
					match(ALWAYS);
					}
					break;
				case BY:
					{
					setState(1792);
					match(BY);
					setState(1793);
					match(DEFAULT);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1797);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(1796);
					generatedAs();
					}
				}

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1799);
				match(DROP);
				setState(1800);
				match(IDENTITY);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1801);
				match(SET);
				setState(1802);
				match(INLINE);
				setState(1803);
				match(LENGTH);
				setState(1804);
				match(NUMBER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1806); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1805);
						sequenceOption();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1808); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,213,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterTablespaceContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode TABLESPACE() { return getToken(DB2Parser.TABLESPACE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode PART() { return getToken(DB2Parser.PART, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public List<TablespaceOptionContext> tablespaceOption() {
			return getRuleContexts(TablespaceOptionContext.class);
		}
		public TablespaceOptionContext tablespaceOption(int i) {
			return getRuleContext(TablespaceOptionContext.class,i);
		}
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LARGE() { return getToken(DB2Parser.LARGE, 0); }
		public AlterTablespaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTablespace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterTablespace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterTablespace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterTablespace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTablespaceContext alterTablespace() throws RecognitionException {
		AlterTablespaceContext _localctx = new AlterTablespaceContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_alterTablespace);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1812);
			match(ALTER);
			setState(1814);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LARGE || _la==LOB) {
				{
				setState(1813);
				_la = _input.LA(1);
				if ( !(_la==LARGE || _la==LOB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(1816);
			match(TABLESPACE);
			setState(1817);
			qualifiedName();
			setState(1820);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,216,_ctx) ) {
			case 1:
				{
				setState(1818);
				match(PART);
				setState(1819);
				match(NUMBER);
				}
				break;
			}
			setState(1825);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,217,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1822);
					tablespaceOption();
					}
					} 
				}
				setState(1827);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,217,_ctx);
			}
			setState(1828);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterIndexContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode REGENERATE() { return getToken(DB2Parser.REGENERATE, 0); }
		public List<IndexOptionContext> indexOption() {
			return getRuleContexts(IndexOptionContext.class);
		}
		public IndexOptionContext indexOption(int i) {
			return getRuleContext(IndexOptionContext.class,i);
		}
		public AlterIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterIndexContext alterIndex() throws RecognitionException {
		AlterIndexContext _localctx = new AlterIndexContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_alterIndex);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1830);
			match(ALTER);
			setState(1831);
			match(INDEX);
			setState(1832);
			qualifiedName();
			setState(1840);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,219,_ctx) ) {
			case 1:
				{
				setState(1833);
				match(REGENERATE);
				}
				break;
			case 2:
				{
				setState(1837);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,218,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1834);
						indexOption();
						}
						} 
					}
					setState(1839);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,218,_ctx);
				}
				}
				break;
			}
			setState(1842);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterDatabaseContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode DATABASE() { return getToken(DB2Parser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<DatabaseOptionContext> databaseOption() {
			return getRuleContexts(DatabaseOptionContext.class);
		}
		public DatabaseOptionContext databaseOption(int i) {
			return getRuleContext(DatabaseOptionContext.class,i);
		}
		public AlterDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterDatabase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterDatabase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterDatabaseContext alterDatabase() throws RecognitionException {
		AlterDatabaseContext _localctx = new AlterDatabaseContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_alterDatabase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1844);
			match(ALTER);
			setState(1845);
			match(DATABASE);
			setState(1846);
			identifier();
			setState(1850);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217580183552L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728569839633L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1847);
				databaseOption();
				}
				}
				setState(1852);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1853);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterStogroupContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<AlterStogroupActionContext> alterStogroupAction() {
			return getRuleContexts(AlterStogroupActionContext.class);
		}
		public AlterStogroupActionContext alterStogroupAction(int i) {
			return getRuleContext(AlterStogroupActionContext.class,i);
		}
		public AlterStogroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterStogroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterStogroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterStogroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterStogroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterStogroupContext alterStogroup() throws RecognitionException {
		AlterStogroupContext _localctx = new AlterStogroupContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_alterStogroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1855);
			match(ALTER);
			setState(1856);
			match(STOGROUP);
			setState(1857);
			identifier();
			setState(1861);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 5383278148534289L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1858);
				alterStogroupAction();
				}
				}
				setState(1863);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1864);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterSequenceContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TerminalNode> RESTART() { return getTokens(DB2Parser.RESTART); }
		public TerminalNode RESTART(int i) {
			return getToken(DB2Parser.RESTART, i);
		}
		public List<SequenceOptionContext> sequenceOption() {
			return getRuleContexts(SequenceOptionContext.class);
		}
		public SequenceOptionContext sequenceOption(int i) {
			return getRuleContext(SequenceOptionContext.class,i);
		}
		public List<TerminalNode> WITH() { return getTokens(DB2Parser.WITH); }
		public TerminalNode WITH(int i) {
			return getToken(DB2Parser.WITH, i);
		}
		public List<SignedNumberContext> signedNumber() {
			return getRuleContexts(SignedNumberContext.class);
		}
		public SignedNumberContext signedNumber(int i) {
			return getRuleContext(SignedNumberContext.class,i);
		}
		public AlterSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterSequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterSequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterSequenceContext alterSequence() throws RecognitionException {
		AlterSequenceContext _localctx = new AlterSequenceContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_alterSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1866);
			match(ALTER);
			setState(1867);
			match(SEQUENCE);
			setState(1868);
			qualifiedName();
			setState(1877);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979175040515L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				setState(1875);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,223,_ctx) ) {
				case 1:
					{
					setState(1869);
					match(RESTART);
					setState(1872);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==WITH) {
						{
						setState(1870);
						match(WITH);
						setState(1871);
						signedNumber();
						}
					}

					}
					break;
				case 2:
					{
					setState(1874);
					sequenceOption();
					}
					break;
				}
				}
				setState(1879);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1880);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterViewContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode VIEW() { return getToken(DB2Parser.VIEW, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode REGENERATE() { return getToken(DB2Parser.REGENERATE, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public AlterViewContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterView; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterView(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterView(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterView(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterViewContext alterView() throws RecognitionException {
		AlterViewContext _localctx = new AlterViewContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_alterView);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1882);
			match(ALTER);
			setState(1883);
			match(VIEW);
			setState(1884);
			qualifiedName();
			setState(1885);
			match(REGENERATE);
			setState(1886);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterProcedureContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode PROCEDURE() { return getToken(DB2Parser.PROCEDURE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<RoutineClauseContext> routineClause() {
			return getRuleContexts(RoutineClauseContext.class);
		}
		public RoutineClauseContext routineClause(int i) {
			return getRuleContext(RoutineClauseContext.class,i);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public List<RoutineParameterContext> routineParameter() {
			return getRuleContexts(RoutineParameterContext.class);
		}
		public RoutineParameterContext routineParameter(int i) {
			return getRuleContext(RoutineParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public AlterProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterProcedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterProcedure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterProcedure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterProcedureContext alterProcedure() throws RecognitionException {
		AlterProcedureContext _localctx = new AlterProcedureContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_alterProcedure);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1888);
			match(ALTER);
			setState(1889);
			match(PROCEDURE);
			setState(1890);
			qualifiedName();
			setState(1903);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,227,_ctx) ) {
			case 1:
				{
				setState(1891);
				match(LPAREN);
				setState(1900);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -9214364818939576320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415341310639800321L) != 0) || ((((_la - 137)) & ~0x3f) == 0 && ((1L << (_la - 137)) & 2683916901484557L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(1892);
					routineParameter();
					setState(1897);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1893);
						match(COMMA);
						setState(1894);
						routineParameter();
						}
						}
						setState(1899);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1902);
				match(RPAREN);
				}
				break;
			}
			setState(1908);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,228,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1905);
					routineClause();
					}
					} 
				}
				setState(1910);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,228,_ctx);
			}
			setState(1912);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(1911);
				compoundStatement();
				}
			}

			setState(1914);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterFunctionContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode FUNCTION() { return getToken(DB2Parser.FUNCTION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<RoutineClauseContext> routineClause() {
			return getRuleContexts(RoutineClauseContext.class);
		}
		public RoutineClauseContext routineClause(int i) {
			return getRuleContext(RoutineClauseContext.class,i);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public List<RoutineParameterContext> routineParameter() {
			return getRuleContexts(RoutineParameterContext.class);
		}
		public RoutineParameterContext routineParameter(int i) {
			return getRuleContext(RoutineParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public AlterFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterFunctionContext alterFunction() throws RecognitionException {
		AlterFunctionContext _localctx = new AlterFunctionContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_alterFunction);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1916);
			match(ALTER);
			setState(1917);
			match(FUNCTION);
			setState(1918);
			qualifiedName();
			setState(1931);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,232,_ctx) ) {
			case 1:
				{
				setState(1919);
				match(LPAREN);
				setState(1928);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -9214364818939576320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415341310639800321L) != 0) || ((((_la - 137)) & ~0x3f) == 0 && ((1L << (_la - 137)) & 2683916901484557L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(1920);
					routineParameter();
					setState(1925);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1921);
						match(COMMA);
						setState(1922);
						routineParameter();
						}
						}
						setState(1927);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1930);
				match(RPAREN);
				}
				break;
			}
			setState(1936);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,233,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1933);
					routineClause();
					}
					} 
				}
				setState(1938);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,233,_ctx);
			}
			setState(1940);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN) {
				{
				setState(1939);
				compoundStatement();
				}
			}

			setState(1942);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterTriggerContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode TRIGGER() { return getToken(DB2Parser.TRIGGER, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode SECURED() { return getToken(DB2Parser.SECURED, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public AlterTriggerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTrigger; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterTrigger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterTrigger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterTrigger(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTriggerContext alterTrigger() throws RecognitionException {
		AlterTriggerContext _localctx = new AlterTriggerContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_alterTrigger);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1944);
			match(ALTER);
			setState(1945);
			match(TRIGGER);
			setState(1946);
			qualifiedName();
			setState(1948);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(1947);
				match(NOT);
				}
			}

			setState(1950);
			match(SECURED);
			setState(1951);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterMaskContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode MASK() { return getToken(DB2Parser.MASK, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public AlterMaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterMask; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterMask(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterMask(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterMask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterMaskContext alterMask() throws RecognitionException {
		AlterMaskContext _localctx = new AlterMaskContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_alterMask);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1953);
			match(ALTER);
			setState(1954);
			match(MASK);
			setState(1955);
			qualifiedName();
			setState(1956);
			_la = _input.LA(1);
			if ( !(_la==DISABLE || _la==ENABLE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1957);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterPermissionContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode PERMISSION() { return getToken(DB2Parser.PERMISSION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode DISABLE() { return getToken(DB2Parser.DISABLE, 0); }
		public AlterPermissionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterPermission; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterPermission(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterPermission(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterPermission(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterPermissionContext alterPermission() throws RecognitionException {
		AlterPermissionContext _localctx = new AlterPermissionContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_alterPermission);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1959);
			match(ALTER);
			setState(1960);
			match(PERMISSION);
			setState(1961);
			qualifiedName();
			setState(1962);
			_la = _input.LA(1);
			if ( !(_la==DISABLE || _la==ENABLE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(1963);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterTrustedContextContext extends ParserRuleContext {
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode TRUSTED() { return getToken(DB2Parser.TRUSTED, 0); }
		public TerminalNode CONTEXT() { return getToken(DB2Parser.CONTEXT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TrustedContextOptionContext> trustedContextOption() {
			return getRuleContexts(TrustedContextOptionContext.class);
		}
		public TrustedContextOptionContext trustedContextOption(int i) {
			return getRuleContext(TrustedContextOptionContext.class,i);
		}
		public AlterTrustedContextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTrustedContext; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterTrustedContext(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterTrustedContext(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterTrustedContext(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterTrustedContextContext alterTrustedContext() throws RecognitionException {
		AlterTrustedContextContext _localctx = new AlterTrustedContextContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_alterTrustedContext);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1965);
			match(ALTER);
			setState(1966);
			match(TRUSTED);
			setState(1967);
			match(CONTEXT);
			setState(1968);
			identifier();
			setState(1972);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 621496766834933760L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158263299L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 144431916643598353L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) {
				{
				{
				setState(1969);
				trustedContextOption();
				}
				}
				setState(1974);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1975);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlterStogroupActionContext extends ParserRuleContext {
		public TerminalNode VOLUMES() { return getToken(DB2Parser.VOLUMES, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode ADD() { return getToken(DB2Parser.ADD, 0); }
		public TerminalNode REMOVE() { return getToken(DB2Parser.REMOVE, 0); }
		public List<TerminalNode> STRING() { return getTokens(DB2Parser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(DB2Parser.STRING, i);
		}
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public StogroupOptionContext stogroupOption() {
			return getRuleContext(StogroupOptionContext.class,0);
		}
		public AlterStogroupActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterStogroupAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterAlterStogroupAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitAlterStogroupAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitAlterStogroupAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlterStogroupActionContext alterStogroupAction() throws RecognitionException {
		AlterStogroupActionContext _localctx = new AlterStogroupActionContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_alterStogroupAction);
		int _la;
		try {
			setState(1996);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,240,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1977);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==REMOVE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1978);
				match(VOLUMES);
				setState(1979);
				match(LPAREN);
				setState(1982);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STRING:
					{
					setState(1980);
					match(STRING);
					}
					break;
				case CAPTURE:
				case CLONE:
				case COLLID:
				case DBINFO:
				case ENCODING:
				case FINAL:
				case FIRST:
				case ISOBID:
				case JAR:
				case LAST:
				case LC_CTYPE:
				case LOCALE:
				case MAINTAINED:
				case MATERIALIZED:
				case NEXT:
				case NULLS:
				case OBID:
				case OPTIMIZATION:
				case OPTIMIZE:
				case ORGANIZATION:
				case PADDED:
				case PLAN:
				case PREVVAL:
				case PROGRAM:
				case PSID:
				case QUERYNO:
				case ROWSET:
				case SCRATCHPAD:
				case SIMPLE:
				case SOURCE:
				case STANDARD:
				case STORES:
				case STYLE:
				case SUMMARY:
				case SYSFUN:
				case SYSIBM:
				case SYSPROC:
				case TYPE:
				case VALUE:
				case VARIANT:
				case AUTHENTICATION:
				case AUTHID:
				case BASED:
				case CONTROL:
				case UPON:
				case ABSOLUTE:
				case ACCESS:
				case ACTION:
				case ADMIN:
				case ALWAYS:
				case ASC:
				case ASUTIME:
				case AT:
				case ATOMIC:
				case ATTRIBUTES:
				case AUX:
				case BIT:
				case CACHE:
				case CALLED:
				case CARDINALITY:
				case CASCADE:
				case CHANGE:
				case CHANGED:
				case CHANGES:
				case COMPARISONS:
				case COMPRESS:
				case CONTEXT:
				case COPY:
				case CURSORS:
				case CYCLE:
				case DATACLAS:
				case DB2:
				case DB2SQL:
				case DEBUG:
				case DEFER:
				case DEFINE:
				case DEFINER:
				case DESC:
				case EACH:
				case ENABLE:
				case ENFORCED:
				case ENVIRONMENT:
				case EVERY:
				case EXCLUDE:
				case EXCLUDING:
				case EXCLUSIVE:
				case FREEPAGE:
				case GBPCACHE:
				case GENERATE:
				case HASH:
				case HIDDEN_KW:
				case HISTORY:
				case ID:
				case IDENTITY:
				case IMPLICITLY:
				case INCLUDE:
				case INCLUDING:
				case INCREMENT:
				case INDEXBP:
				case INLINE:
				case INPUT:
				case INSTEAD:
				case KEYS:
				case LARGE:
				case LENGTH:
				case LIMIT:
				case LOAD:
				case LOB:
				case LOGGED:
				case MAIN:
				case MASK:
				case MAXPARTITIONS:
				case MAXROWS:
				case MAXVALUE:
				case MEMBER:
				case MGMTCLAS:
				case MINVALUE:
				case MIXED:
				case MODE:
				case NAME:
				case NEW:
				case NEW_TABLE:
				case OLD_TABLE:
				case ONLY:
				case OPTION:
				case OPTIONS:
				case ORGANIZE:
				case PAGE:
				case PAGENUM:
				case PCTFREE:
				case PERMISSION:
				case PRIMARY:
				case QUALIFIER:
				case RANDOM:
				case RANGE:
				case REGENERATE:
				case REGISTERS:
				case RELATIVE:
				case REMOVE:
				case REPLACE:
				case RESET:
				case RESIDENT:
				case RESTART:
				case RETAIN:
				case ROTATE:
				case ROWS:
				case SBCS:
				case SECURED:
				case SEGSIZE:
				case SETS:
				case SHARE:
				case SIZE:
				case SPACE:
				case SPECIAL:
				case SQL:
				case SQLID:
				case START:
				case STARTING:
				case STATEMENT:
				case STORCLAS:
				case SUB:
				case TEMPORARY:
				case TIME:
				case TIMESTAMP:
				case TRACKMOD:
				case TRUSTED:
				case UNLOAD:
				case USAGE:
				case USE:
				case VARCHAR:
				case VARGRAPHIC:
				case VARYING:
				case VERSIONING:
				case WITHOUT:
				case WORK:
				case XMLPATTERN:
				case YES:
				case DELIMITED_IDENTIFIER:
				case PLACEHOLDER:
				case IDENTIFIER:
					{
					setState(1981);
					identifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1991);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1984);
					match(COMMA);
					setState(1987);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case STRING:
						{
						setState(1985);
						match(STRING);
						}
						break;
					case CAPTURE:
					case CLONE:
					case COLLID:
					case DBINFO:
					case ENCODING:
					case FINAL:
					case FIRST:
					case ISOBID:
					case JAR:
					case LAST:
					case LC_CTYPE:
					case LOCALE:
					case MAINTAINED:
					case MATERIALIZED:
					case NEXT:
					case NULLS:
					case OBID:
					case OPTIMIZATION:
					case OPTIMIZE:
					case ORGANIZATION:
					case PADDED:
					case PLAN:
					case PREVVAL:
					case PROGRAM:
					case PSID:
					case QUERYNO:
					case ROWSET:
					case SCRATCHPAD:
					case SIMPLE:
					case SOURCE:
					case STANDARD:
					case STORES:
					case STYLE:
					case SUMMARY:
					case SYSFUN:
					case SYSIBM:
					case SYSPROC:
					case TYPE:
					case VALUE:
					case VARIANT:
					case AUTHENTICATION:
					case AUTHID:
					case BASED:
					case CONTROL:
					case UPON:
					case ABSOLUTE:
					case ACCESS:
					case ACTION:
					case ADMIN:
					case ALWAYS:
					case ASC:
					case ASUTIME:
					case AT:
					case ATOMIC:
					case ATTRIBUTES:
					case AUX:
					case BIT:
					case CACHE:
					case CALLED:
					case CARDINALITY:
					case CASCADE:
					case CHANGE:
					case CHANGED:
					case CHANGES:
					case COMPARISONS:
					case COMPRESS:
					case CONTEXT:
					case COPY:
					case CURSORS:
					case CYCLE:
					case DATACLAS:
					case DB2:
					case DB2SQL:
					case DEBUG:
					case DEFER:
					case DEFINE:
					case DEFINER:
					case DESC:
					case EACH:
					case ENABLE:
					case ENFORCED:
					case ENVIRONMENT:
					case EVERY:
					case EXCLUDE:
					case EXCLUDING:
					case EXCLUSIVE:
					case FREEPAGE:
					case GBPCACHE:
					case GENERATE:
					case HASH:
					case HIDDEN_KW:
					case HISTORY:
					case ID:
					case IDENTITY:
					case IMPLICITLY:
					case INCLUDE:
					case INCLUDING:
					case INCREMENT:
					case INDEXBP:
					case INLINE:
					case INPUT:
					case INSTEAD:
					case KEYS:
					case LARGE:
					case LENGTH:
					case LIMIT:
					case LOAD:
					case LOB:
					case LOGGED:
					case MAIN:
					case MASK:
					case MAXPARTITIONS:
					case MAXROWS:
					case MAXVALUE:
					case MEMBER:
					case MGMTCLAS:
					case MINVALUE:
					case MIXED:
					case MODE:
					case NAME:
					case NEW:
					case NEW_TABLE:
					case OLD_TABLE:
					case ONLY:
					case OPTION:
					case OPTIONS:
					case ORGANIZE:
					case PAGE:
					case PAGENUM:
					case PCTFREE:
					case PERMISSION:
					case PRIMARY:
					case QUALIFIER:
					case RANDOM:
					case RANGE:
					case REGENERATE:
					case REGISTERS:
					case RELATIVE:
					case REMOVE:
					case REPLACE:
					case RESET:
					case RESIDENT:
					case RESTART:
					case RETAIN:
					case ROTATE:
					case ROWS:
					case SBCS:
					case SECURED:
					case SEGSIZE:
					case SETS:
					case SHARE:
					case SIZE:
					case SPACE:
					case SPECIAL:
					case SQL:
					case SQLID:
					case START:
					case STARTING:
					case STATEMENT:
					case STORCLAS:
					case SUB:
					case TEMPORARY:
					case TIME:
					case TIMESTAMP:
					case TRACKMOD:
					case TRUSTED:
					case UNLOAD:
					case USAGE:
					case USE:
					case VARCHAR:
					case VARGRAPHIC:
					case VARYING:
					case VERSIONING:
					case WITHOUT:
					case WORK:
					case XMLPATTERN:
					case YES:
					case DELIMITED_IDENTIFIER:
					case PLACEHOLDER:
					case IDENTIFIER:
						{
						setState(1986);
						identifier();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					setState(1993);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1994);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1995);
				stogroupOption();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DropStatementContext extends ParserRuleContext {
		public TerminalNode DROP() { return getToken(DB2Parser.DROP, 0); }
		public DroppedObjectContext droppedObject() {
			return getRuleContext(DroppedObjectContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode RESTRICT() { return getToken(DB2Parser.RESTRICT, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public DropStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDropStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDropStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDropStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DropStatementContext dropStatement() throws RecognitionException {
		DropStatementContext _localctx = new DropStatementContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_dropStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1998);
			match(DROP);
			setState(1999);
			droppedObject();
			setState(2001);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RESTRICT || _la==CASCADE) {
				{
				setState(2000);
				_la = _input.LA(1);
				if ( !(_la==RESTRICT || _la==CASCADE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(2003);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DroppedObjectContext extends ParserRuleContext {
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode TABLESPACE() { return getToken(DB2Parser.TABLESPACE, 0); }
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LARGE() { return getToken(DB2Parser.LARGE, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public TerminalNode DATABASE() { return getToken(DB2Parser.DATABASE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public TerminalNode VIEW() { return getToken(DB2Parser.VIEW, 0); }
		public TerminalNode ALIAS() { return getToken(DB2Parser.ALIAS, 0); }
		public TerminalNode PUBLIC() { return getToken(DB2Parser.PUBLIC, 0); }
		public TerminalNode SYNONYM() { return getToken(DB2Parser.SYNONYM, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public TerminalNode TRIGGER() { return getToken(DB2Parser.TRIGGER, 0); }
		public TerminalNode PROCEDURE() { return getToken(DB2Parser.PROCEDURE, 0); }
		public TerminalNode FUNCTION() { return getToken(DB2Parser.FUNCTION, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<DataTypeContext> dataType() {
			return getRuleContexts(DataTypeContext.class);
		}
		public DataTypeContext dataType(int i) {
			return getRuleContext(DataTypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode SPECIFIC() { return getToken(DB2Parser.SPECIFIC, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode DISTINCT() { return getToken(DB2Parser.DISTINCT, 0); }
		public TerminalNode VARIABLE() { return getToken(DB2Parser.VARIABLE, 0); }
		public TerminalNode MASK() { return getToken(DB2Parser.MASK, 0); }
		public TerminalNode PERMISSION() { return getToken(DB2Parser.PERMISSION, 0); }
		public TerminalNode TRUSTED() { return getToken(DB2Parser.TRUSTED, 0); }
		public TerminalNode CONTEXT() { return getToken(DB2Parser.CONTEXT, 0); }
		public TerminalNode AUX() { return getToken(DB2Parser.AUX, 0); }
		public TerminalNode AUXILIARY() { return getToken(DB2Parser.AUXILIARY, 0); }
		public TerminalNode PACKAGE() { return getToken(DB2Parser.PACKAGE, 0); }
		public DroppedObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_droppedObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDroppedObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDroppedObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDroppedObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DroppedObjectContext droppedObject() throws RecognitionException {
		DroppedObjectContext _localctx = new DroppedObjectContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_droppedObject);
		int _la;
		try {
			setState(2069);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(2005);
				match(TABLE);
				setState(2006);
				qualifiedName();
				}
				break;
			case TABLESPACE:
			case LARGE:
			case LOB:
				enterOuterAlt(_localctx, 2);
				{
				setState(2008);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LARGE || _la==LOB) {
					{
					setState(2007);
					_la = _input.LA(1);
					if ( !(_la==LARGE || _la==LOB) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2010);
				match(TABLESPACE);
				setState(2011);
				qualifiedName();
				}
				break;
			case INDEX:
				enterOuterAlt(_localctx, 3);
				{
				setState(2012);
				match(INDEX);
				setState(2013);
				qualifiedName();
				}
				break;
			case DATABASE:
				enterOuterAlt(_localctx, 4);
				{
				setState(2014);
				match(DATABASE);
				setState(2015);
				identifier();
				}
				break;
			case STOGROUP:
				enterOuterAlt(_localctx, 5);
				{
				setState(2016);
				match(STOGROUP);
				setState(2017);
				identifier();
				}
				break;
			case VIEW:
				enterOuterAlt(_localctx, 6);
				{
				setState(2018);
				match(VIEW);
				setState(2019);
				qualifiedName();
				}
				break;
			case PUBLIC:
			case ALIAS:
				enterOuterAlt(_localctx, 7);
				{
				setState(2021);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(2020);
					match(PUBLIC);
					}
				}

				setState(2023);
				match(ALIAS);
				setState(2024);
				qualifiedName();
				}
				break;
			case SYNONYM:
				enterOuterAlt(_localctx, 8);
				{
				setState(2025);
				match(SYNONYM);
				setState(2026);
				identifier();
				}
				break;
			case SEQUENCE:
				enterOuterAlt(_localctx, 9);
				{
				setState(2027);
				match(SEQUENCE);
				setState(2028);
				qualifiedName();
				}
				break;
			case ROLE:
				enterOuterAlt(_localctx, 10);
				{
				setState(2029);
				match(ROLE);
				setState(2030);
				identifier();
				}
				break;
			case TRIGGER:
				enterOuterAlt(_localctx, 11);
				{
				setState(2031);
				match(TRIGGER);
				setState(2032);
				qualifiedName();
				}
				break;
			case FUNCTION:
			case PROCEDURE:
				enterOuterAlt(_localctx, 12);
				{
				setState(2033);
				_la = _input.LA(1);
				if ( !(_la==FUNCTION || _la==PROCEDURE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2034);
				qualifiedName();
				setState(2047);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,246,_ctx) ) {
				case 1:
					{
					setState(2035);
					match(LPAREN);
					setState(2044);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & -9214364818939576320L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 137)) & ~0x3f) == 0 && ((1L << (_la - 137)) & 2683916633049101L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
						{
						setState(2036);
						dataType();
						setState(2041);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(2037);
							match(COMMA);
							setState(2038);
							dataType();
							}
							}
							setState(2043);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
					}

					setState(2046);
					match(RPAREN);
					}
					break;
				}
				}
				break;
			case SPECIFIC:
				enterOuterAlt(_localctx, 13);
				{
				setState(2049);
				match(SPECIFIC);
				setState(2050);
				_la = _input.LA(1);
				if ( !(_la==FUNCTION || _la==PROCEDURE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2051);
				qualifiedName();
				}
				break;
			case DISTINCT:
			case TYPE:
				enterOuterAlt(_localctx, 14);
				{
				setState(2053);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DISTINCT) {
					{
					setState(2052);
					match(DISTINCT);
					}
				}

				setState(2055);
				match(TYPE);
				setState(2056);
				qualifiedName();
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 15);
				{
				setState(2057);
				match(VARIABLE);
				setState(2058);
				qualifiedName();
				}
				break;
			case MASK:
			case PERMISSION:
				enterOuterAlt(_localctx, 16);
				{
				setState(2059);
				_la = _input.LA(1);
				if ( !(_la==MASK || _la==PERMISSION) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2060);
				qualifiedName();
				}
				break;
			case TRUSTED:
				enterOuterAlt(_localctx, 17);
				{
				setState(2061);
				match(TRUSTED);
				setState(2062);
				match(CONTEXT);
				setState(2063);
				identifier();
				}
				break;
			case AUXILIARY:
			case AUX:
				enterOuterAlt(_localctx, 18);
				{
				setState(2064);
				_la = _input.LA(1);
				if ( !(_la==AUXILIARY || _la==AUX) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2065);
				match(TABLE);
				setState(2066);
				qualifiedName();
				}
				break;
			case PACKAGE:
				enterOuterAlt(_localctx, 19);
				{
				setState(2067);
				match(PACKAGE);
				setState(2068);
				qualifiedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GrantStatementContext extends ParserRuleContext {
		public List<TerminalNode> GRANT() { return getTokens(DB2Parser.GRANT); }
		public TerminalNode GRANT(int i) {
			return getToken(DB2Parser.GRANT, i);
		}
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode OF() { return getToken(DB2Parser.OF, 0); }
		public PrivilegeObjectContext privilegeObject() {
			return getRuleContext(PrivilegeObjectContext.class,0);
		}
		public QualifiedNameListContext qualifiedNameList() {
			return getRuleContext(QualifiedNameListContext.class,0);
		}
		public TerminalNode TO() { return getToken(DB2Parser.TO, 0); }
		public List<GranteeContext> grantee() {
			return getRuleContexts(GranteeContext.class);
		}
		public GranteeContext grantee(int i) {
			return getRuleContext(GranteeContext.class,i);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public List<PrivilegeContext> privilege() {
			return getRuleContexts(PrivilegeContext.class);
		}
		public PrivilegeContext privilege(int i) {
			return getRuleContext(PrivilegeContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode OPTION() { return getToken(DB2Parser.OPTION, 0); }
		public TerminalNode ADMIN() { return getToken(DB2Parser.ADMIN, 0); }
		public GrantStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grantStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterGrantStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitGrantStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitGrantStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrantStatementContext grantStatement() throws RecognitionException {
		GrantStatementContext _localctx = new GrantStatementContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_grantStatement);
		int _la;
		try {
			setState(2142);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,257,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2071);
				match(GRANT);
				setState(2072);
				match(USE);
				setState(2073);
				match(OF);
				setState(2074);
				privilegeObject();
				setState(2075);
				qualifiedNameList();
				setState(2076);
				match(TO);
				setState(2077);
				grantee();
				setState(2082);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2078);
					match(COMMA);
					setState(2079);
					grantee();
					}
					}
					setState(2084);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2085);
				end();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2087);
				match(GRANT);
				setState(2088);
				privilege();
				setState(2093);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2089);
					match(COMMA);
					setState(2090);
					privilege();
					}
					}
					setState(2095);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2096);
				_la = _input.LA(1);
				if ( !(_la==IN || _la==ON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2098);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,251,_ctx) ) {
				case 1:
					{
					setState(2097);
					privilegeObject();
					}
					break;
				}
				setState(2100);
				qualifiedNameList();
				setState(2101);
				match(TO);
				setState(2102);
				grantee();
				setState(2107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2103);
					match(COMMA);
					setState(2104);
					grantee();
					}
					}
					setState(2109);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(2110);
					match(WITH);
					setState(2111);
					match(GRANT);
					setState(2112);
					match(OPTION);
					}
				}

				setState(2115);
				end();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2117);
				match(GRANT);
				setState(2118);
				privilege();
				setState(2123);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2119);
					match(COMMA);
					setState(2120);
					privilege();
					}
					}
					setState(2125);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2126);
				match(TO);
				setState(2127);
				grantee();
				setState(2132);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2128);
					match(COMMA);
					setState(2129);
					grantee();
					}
					}
					setState(2134);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2138);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(2135);
					match(WITH);
					setState(2136);
					match(ADMIN);
					setState(2137);
					match(OPTION);
					}
				}

				setState(2140);
				end();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RevokeStatementContext extends ParserRuleContext {
		public TerminalNode REVOKE() { return getToken(DB2Parser.REVOKE, 0); }
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode OF() { return getToken(DB2Parser.OF, 0); }
		public PrivilegeObjectContext privilegeObject() {
			return getRuleContext(PrivilegeObjectContext.class,0);
		}
		public QualifiedNameListContext qualifiedNameList() {
			return getRuleContext(QualifiedNameListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(DB2Parser.FROM, 0); }
		public List<GranteeContext> grantee() {
			return getRuleContexts(GranteeContext.class);
		}
		public GranteeContext grantee(int i) {
			return getRuleContext(GranteeContext.class,i);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public List<PrivilegeContext> privilege() {
			return getRuleContexts(PrivilegeContext.class);
		}
		public PrivilegeContext privilege(int i) {
			return getRuleContext(PrivilegeContext.class,i);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode BY() { return getToken(DB2Parser.BY, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode RESTRICT() { return getToken(DB2Parser.RESTRICT, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public RevokeStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_revokeStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterRevokeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitRevokeStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitRevokeStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RevokeStatementContext revokeStatement() throws RecognitionException {
		RevokeStatementContext _localctx = new RevokeStatementContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_revokeStatement);
		int _la;
		try {
			setState(2216);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,267,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2144);
				match(REVOKE);
				setState(2145);
				match(USE);
				setState(2146);
				match(OF);
				setState(2147);
				privilegeObject();
				setState(2148);
				qualifiedNameList();
				setState(2149);
				match(FROM);
				setState(2150);
				grantee();
				setState(2155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2151);
					match(COMMA);
					setState(2152);
					grantee();
					}
					}
					setState(2157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2158);
				end();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2160);
				match(REVOKE);
				setState(2161);
				privilege();
				setState(2166);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2162);
					match(COMMA);
					setState(2163);
					privilege();
					}
					}
					setState(2168);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2169);
				_la = _input.LA(1);
				if ( !(_la==IN || _la==ON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2171);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,260,_ctx) ) {
				case 1:
					{
					setState(2170);
					privilegeObject();
					}
					break;
				}
				setState(2173);
				qualifiedNameList();
				setState(2174);
				match(FROM);
				setState(2175);
				grantee();
				setState(2180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2176);
					match(COMMA);
					setState(2177);
					grantee();
					}
					}
					setState(2182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BY) {
					{
					setState(2183);
					match(BY);
					setState(2184);
					match(ALL);
					}
				}

				setState(2188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RESTRICT || _la==CASCADE) {
					{
					setState(2187);
					_la = _input.LA(1);
					if ( !(_la==RESTRICT || _la==CASCADE) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2190);
				end();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2192);
				match(REVOKE);
				setState(2193);
				privilege();
				setState(2198);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2194);
					match(COMMA);
					setState(2195);
					privilege();
					}
					}
					setState(2200);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2201);
				match(FROM);
				setState(2202);
				grantee();
				setState(2207);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2203);
					match(COMMA);
					setState(2204);
					grantee();
					}
					}
					setState(2209);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2212);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==BY) {
					{
					setState(2210);
					match(BY);
					setState(2211);
					match(ALL);
					}
				}

				setState(2214);
				end();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrivilegeContext extends ParserRuleContext {
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode PRIVILEGES() { return getToken(DB2Parser.PRIVILEGES, 0); }
		public TerminalNode SELECT() { return getToken(DB2Parser.SELECT, 0); }
		public TerminalNode INSERT() { return getToken(DB2Parser.INSERT, 0); }
		public TerminalNode UPDATE() { return getToken(DB2Parser.UPDATE, 0); }
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public TerminalNode DELETE() { return getToken(DB2Parser.DELETE, 0); }
		public TerminalNode REFERENCES() { return getToken(DB2Parser.REFERENCES, 0); }
		public TerminalNode ALTER() { return getToken(DB2Parser.ALTER, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public TerminalNode TRIGGER() { return getToken(DB2Parser.TRIGGER, 0); }
		public TerminalNode EXECUTE() { return getToken(DB2Parser.EXECUTE, 0); }
		public TerminalNode USAGE() { return getToken(DB2Parser.USAGE, 0); }
		public TerminalNode LOAD() { return getToken(DB2Parser.LOAD, 0); }
		public TerminalNode UNLOAD() { return getToken(DB2Parser.UNLOAD, 0); }
		public TerminalNode CREATE() { return getToken(DB2Parser.CREATE, 0); }
		public TerminalNode DROP() { return getToken(DB2Parser.DROP, 0); }
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode OF() { return getToken(DB2Parser.OF, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public PrivilegeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_privilege; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPrivilege(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPrivilege(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPrivilege(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivilegeContext privilege() throws RecognitionException {
		PrivilegeContext _localctx = new PrivilegeContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_privilege);
		int _la;
		try {
			setState(2245);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,271,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2218);
				match(ALL);
				setState(2220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PRIVILEGES) {
					{
					setState(2219);
					match(PRIVILEGES);
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2222);
				match(SELECT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2223);
				match(INSERT);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2224);
				match(UPDATE);
				setState(2226);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(2225);
					columnList();
					}
				}

				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2228);
				match(DELETE);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2229);
				match(REFERENCES);
				setState(2231);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(2230);
					columnList();
					}
				}

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(2233);
				match(ALTER);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(2234);
				match(INDEX);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(2235);
				match(TRIGGER);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(2236);
				match(EXECUTE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(2237);
				match(USAGE);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(2238);
				match(LOAD);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(2239);
				match(UNLOAD);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(2240);
				match(CREATE);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(2241);
				match(DROP);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(2242);
				match(USE);
				setState(2243);
				match(OF);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(2244);
				identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrivilegeObjectContext extends ParserRuleContext {
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode TABLESPACE() { return getToken(DB2Parser.TABLESPACE, 0); }
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LARGE() { return getToken(DB2Parser.LARGE, 0); }
		public TerminalNode DATABASE() { return getToken(DB2Parser.DATABASE, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public TerminalNode SCHEMA() { return getToken(DB2Parser.SCHEMA, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public TerminalNode PROCEDURE() { return getToken(DB2Parser.PROCEDURE, 0); }
		public TerminalNode FUNCTION() { return getToken(DB2Parser.FUNCTION, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode DISTINCT() { return getToken(DB2Parser.DISTINCT, 0); }
		public TerminalNode VARIABLE() { return getToken(DB2Parser.VARIABLE, 0); }
		public TerminalNode PACKAGE() { return getToken(DB2Parser.PACKAGE, 0); }
		public TerminalNode COLLECTION() { return getToken(DB2Parser.COLLECTION, 0); }
		public TerminalNode PLAN() { return getToken(DB2Parser.PLAN, 0); }
		public TerminalNode BUFFERPOOL() { return getToken(DB2Parser.BUFFERPOOL, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public TerminalNode SYSTEM() { return getToken(DB2Parser.SYSTEM, 0); }
		public PrivilegeObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_privilegeObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPrivilegeObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPrivilegeObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPrivilegeObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivilegeObjectContext privilegeObject() throws RecognitionException {
		PrivilegeObjectContext _localctx = new PrivilegeObjectContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_privilegeObject);
		int _la;
		try {
			setState(2268);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(2247);
				match(TABLE);
				}
				break;
			case TABLESPACE:
			case LARGE:
			case LOB:
				enterOuterAlt(_localctx, 2);
				{
				setState(2249);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LARGE || _la==LOB) {
					{
					setState(2248);
					_la = _input.LA(1);
					if ( !(_la==LARGE || _la==LOB) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2251);
				match(TABLESPACE);
				}
				break;
			case DATABASE:
				enterOuterAlt(_localctx, 3);
				{
				setState(2252);
				match(DATABASE);
				}
				break;
			case STOGROUP:
				enterOuterAlt(_localctx, 4);
				{
				setState(2253);
				match(STOGROUP);
				}
				break;
			case SCHEMA:
				enterOuterAlt(_localctx, 5);
				{
				setState(2254);
				match(SCHEMA);
				}
				break;
			case SEQUENCE:
				enterOuterAlt(_localctx, 6);
				{
				setState(2255);
				match(SEQUENCE);
				}
				break;
			case FUNCTION:
			case PROCEDURE:
				enterOuterAlt(_localctx, 7);
				{
				setState(2256);
				_la = _input.LA(1);
				if ( !(_la==FUNCTION || _la==PROCEDURE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case DISTINCT:
			case TYPE:
				enterOuterAlt(_localctx, 8);
				{
				setState(2258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DISTINCT) {
					{
					setState(2257);
					match(DISTINCT);
					}
				}

				setState(2260);
				match(TYPE);
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 9);
				{
				setState(2261);
				match(VARIABLE);
				}
				break;
			case PACKAGE:
				enterOuterAlt(_localctx, 10);
				{
				setState(2262);
				match(PACKAGE);
				}
				break;
			case COLLECTION:
				enterOuterAlt(_localctx, 11);
				{
				setState(2263);
				match(COLLECTION);
				}
				break;
			case PLAN:
				enterOuterAlt(_localctx, 12);
				{
				setState(2264);
				match(PLAN);
				}
				break;
			case BUFFERPOOL:
				enterOuterAlt(_localctx, 13);
				{
				setState(2265);
				match(BUFFERPOOL);
				}
				break;
			case ROLE:
				enterOuterAlt(_localctx, 14);
				{
				setState(2266);
				match(ROLE);
				}
				break;
			case SYSTEM:
				enterOuterAlt(_localctx, 15);
				{
				setState(2267);
				match(SYSTEM);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameListContext extends ParserRuleContext {
		public List<QualifiedNameContext> qualifiedName() {
			return getRuleContexts(QualifiedNameContext.class);
		}
		public QualifiedNameContext qualifiedName(int i) {
			return getRuleContext(QualifiedNameContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public QualifiedNameListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedNameList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterQualifiedNameList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitQualifiedNameList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitQualifiedNameList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameListContext qualifiedNameList() throws RecognitionException {
		QualifiedNameListContext _localctx = new QualifiedNameListContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_qualifiedNameList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2270);
			qualifiedName();
			setState(2275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(2271);
				match(COMMA);
				setState(2272);
				qualifiedName();
				}
				}
				setState(2277);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GranteeContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(DB2Parser.PUBLIC, 0); }
		public TerminalNode AT() { return getToken(DB2Parser.AT, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode USER() { return getToken(DB2Parser.USER, 0); }
		public GranteeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grantee; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterGrantee(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitGrantee(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitGrantee(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GranteeContext grantee() throws RecognitionException {
		GranteeContext _localctx = new GranteeContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_grantee);
		int _la;
		try {
			setState(2288);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PUBLIC:
				enterOuterAlt(_localctx, 1);
				{
				setState(2278);
				match(PUBLIC);
				setState(2281);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AT) {
					{
					setState(2279);
					match(AT);
					setState(2280);
					match(ALL);
					}
				}

				}
				break;
			case ROLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(2283);
				match(ROLE);
				setState(2284);
				identifier();
				}
				break;
			case USER:
				enterOuterAlt(_localctx, 3);
				{
				setState(2285);
				match(USER);
				setState(2286);
				identifier();
				}
				break;
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 4);
				{
				setState(2287);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CommentStatementContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(DB2Parser.COMMENT, 0); }
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public CommentTargetContext commentTarget() {
			return getRuleContext(CommentTargetContext.class,0);
		}
		public TerminalNode IS() { return getToken(DB2Parser.IS, 0); }
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public CommentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commentStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCommentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCommentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCommentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentStatementContext commentStatement() throws RecognitionException {
		CommentStatementContext _localctx = new CommentStatementContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_commentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2290);
			match(COMMENT);
			setState(2291);
			match(ON);
			setState(2292);
			commentTarget();
			setState(2293);
			match(IS);
			setState(2294);
			match(STRING);
			setState(2295);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CommentTargetContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode VIEW() { return getToken(DB2Parser.VIEW, 0); }
		public TerminalNode ALIAS() { return getToken(DB2Parser.ALIAS, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public TerminalNode TRIGGER() { return getToken(DB2Parser.TRIGGER, 0); }
		public TerminalNode SEQUENCE() { return getToken(DB2Parser.SEQUENCE, 0); }
		public TerminalNode ROLE() { return getToken(DB2Parser.ROLE, 0); }
		public TerminalNode VARIABLE() { return getToken(DB2Parser.VARIABLE, 0); }
		public TerminalNode PACKAGE() { return getToken(DB2Parser.PACKAGE, 0); }
		public TerminalNode COLUMN() { return getToken(DB2Parser.COLUMN, 0); }
		public TerminalNode DOT() { return getToken(DB2Parser.DOT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode DISTINCT() { return getToken(DB2Parser.DISTINCT, 0); }
		public TerminalNode PROCEDURE() { return getToken(DB2Parser.PROCEDURE, 0); }
		public TerminalNode FUNCTION() { return getToken(DB2Parser.FUNCTION, 0); }
		public CommentTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commentTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCommentTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCommentTarget(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCommentTarget(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentTargetContext commentTarget() throws RecognitionException {
		CommentTargetContext _localctx = new CommentTargetContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_commentTarget);
		int _la;
		try {
			setState(2313);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,280,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INDEX || _la==PACKAGE || ((((_la - 202)) & ~0x3f) == 0 && ((1L << (_la - 202)) & 2594110768761798657L) != 0) || _la==ALIAS) {
					{
					setState(2297);
					_la = _input.LA(1);
					if ( !(_la==INDEX || _la==PACKAGE || ((((_la - 202)) & ~0x3f) == 0 && ((1L << (_la - 202)) & 2594110768761798657L) != 0) || _la==ALIAS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2300);
				qualifiedName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2301);
				match(COLUMN);
				setState(2302);
				qualifiedName();
				setState(2303);
				match(DOT);
				setState(2304);
				identifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2307);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DISTINCT) {
					{
					setState(2306);
					match(DISTINCT);
					}
				}

				setState(2309);
				match(TYPE);
				setState(2310);
				qualifiedName();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2311);
				_la = _input.LA(1);
				if ( !(_la==FUNCTION || _la==PROCEDURE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2312);
				qualifiedName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabelStatementContext extends ParserRuleContext {
		public TerminalNode LABEL() { return getToken(DB2Parser.LABEL, 0); }
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public LabelTargetContext labelTarget() {
			return getRuleContext(LabelTargetContext.class,0);
		}
		public TerminalNode IS() { return getToken(DB2Parser.IS, 0); }
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public LabelStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterLabelStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitLabelStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitLabelStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelStatementContext labelStatement() throws RecognitionException {
		LabelStatementContext _localctx = new LabelStatementContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_labelStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2315);
			match(LABEL);
			setState(2316);
			match(ON);
			setState(2317);
			labelTarget();
			setState(2318);
			match(IS);
			setState(2319);
			match(STRING);
			setState(2320);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabelTargetContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode ALIAS() { return getToken(DB2Parser.ALIAS, 0); }
		public TerminalNode COLUMN() { return getToken(DB2Parser.COLUMN, 0); }
		public TerminalNode DOT() { return getToken(DB2Parser.DOT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LabelTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterLabelTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitLabelTarget(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitLabelTarget(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelTargetContext labelTarget() throws RecognitionException {
		LabelTargetContext _localctx = new LabelTargetContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_labelTarget);
		int _la;
		try {
			setState(2331);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TABLE:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALIAS:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(2323);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TABLE || _la==ALIAS) {
					{
					setState(2322);
					_la = _input.LA(1);
					if ( !(_la==TABLE || _la==ALIAS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2325);
				qualifiedName();
				}
				break;
			case COLUMN:
				enterOuterAlt(_localctx, 2);
				{
				setState(2326);
				match(COLUMN);
				setState(2327);
				qualifiedName();
				setState(2328);
				match(DOT);
				setState(2329);
				identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RenameStatementContext extends ParserRuleContext {
		public TerminalNode RENAME() { return getToken(DB2Parser.RENAME, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode TO() { return getToken(DB2Parser.TO, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public TerminalNode INDEX() { return getToken(DB2Parser.INDEX, 0); }
		public RenameStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renameStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterRenameStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitRenameStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitRenameStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RenameStatementContext renameStatement() throws RecognitionException {
		RenameStatementContext _localctx = new RenameStatementContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_renameStatement);
		int _la;
		try {
			setState(2349);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,284,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2333);
				match(RENAME);
				setState(2335);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TABLE) {
					{
					setState(2334);
					match(TABLE);
					}
				}

				setState(2337);
				qualifiedName();
				setState(2338);
				match(TO);
				setState(2339);
				identifier();
				setState(2340);
				end();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2342);
				match(RENAME);
				setState(2343);
				match(INDEX);
				setState(2344);
				qualifiedName();
				setState(2345);
				match(TO);
				setState(2346);
				identifier();
				setState(2347);
				end();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SetStatementContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(DB2Parser.SET, 0); }
		public SpecialRegisterContext specialRegister() {
			return getRuleContext(SpecialRegisterContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode EQ() { return getToken(DB2Parser.EQ, 0); }
		public SetStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSetStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSetStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSetStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetStatementContext setStatement() throws RecognitionException {
		SetStatementContext _localctx = new SetStatementContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_setStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2351);
			match(SET);
			setState(2352);
			specialRegister();
			setState(2354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(2353);
				match(EQ);
				}
			}

			setState(2356);
			expression(0);
			setState(2357);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecialRegisterContext extends ParserRuleContext {
		public TerminalNode CURRENT() { return getToken(DB2Parser.CURRENT, 0); }
		public List<NonReservedContext> nonReserved() {
			return getRuleContexts(NonReservedContext.class);
		}
		public NonReservedContext nonReserved(int i) {
			return getRuleContext(NonReservedContext.class,i);
		}
		public TerminalNode SCHEMA() { return getToken(DB2Parser.SCHEMA, 0); }
		public TerminalNode PATH() { return getToken(DB2Parser.PATH, 0); }
		public SpecialRegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specialRegister; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSpecialRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSpecialRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSpecialRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecialRegisterContext specialRegister() throws RecognitionException {
		SpecialRegisterContext _localctx = new SpecialRegisterContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_specialRegister);
		int _la;
		try {
			int _alt;
			setState(2373);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,288,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2360);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==CURRENT) {
					{
					setState(2359);
					match(CURRENT);
					}
				}

				setState(2363); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(2362);
						nonReserved();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(2365); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,287,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2367);
				match(CURRENT);
				setState(2368);
				match(SCHEMA);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2369);
				match(CURRENT);
				setState(2370);
				match(PATH);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2371);
				match(PATH);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2372);
				match(SCHEMA);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CommitStatementContext extends ParserRuleContext {
		public TerminalNode COMMIT() { return getToken(DB2Parser.COMMIT, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode WORK() { return getToken(DB2Parser.WORK, 0); }
		public CommitStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commitStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCommitStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCommitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCommitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommitStatementContext commitStatement() throws RecognitionException {
		CommitStatementContext _localctx = new CommitStatementContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_commitStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2375);
			match(COMMIT);
			setState(2377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WORK) {
				{
				setState(2376);
				match(WORK);
				}
			}

			setState(2379);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RollbackStatementContext extends ParserRuleContext {
		public TerminalNode ROLLBACK() { return getToken(DB2Parser.ROLLBACK, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode WORK() { return getToken(DB2Parser.WORK, 0); }
		public TerminalNode TO() { return getToken(DB2Parser.TO, 0); }
		public TerminalNode SAVEPOINT() { return getToken(DB2Parser.SAVEPOINT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public RollbackStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rollbackStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterRollbackStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitRollbackStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitRollbackStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RollbackStatementContext rollbackStatement() throws RecognitionException {
		RollbackStatementContext _localctx = new RollbackStatementContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_rollbackStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2381);
			match(ROLLBACK);
			setState(2383);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WORK) {
				{
				setState(2382);
				match(WORK);
				}
			}

			setState(2390);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO) {
				{
				setState(2385);
				match(TO);
				setState(2386);
				match(SAVEPOINT);
				setState(2388);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(2387);
					identifier();
					}
				}

				}
			}

			setState(2392);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SavepointStatementContext extends ParserRuleContext {
		public TerminalNode SAVEPOINT() { return getToken(DB2Parser.SAVEPOINT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public TerminalNode ROLLBACK() { return getToken(DB2Parser.ROLLBACK, 0); }
		public TerminalNode RETAIN() { return getToken(DB2Parser.RETAIN, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode CURSORS() { return getToken(DB2Parser.CURSORS, 0); }
		public SavepointStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_savepointStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSavepointStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSavepointStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSavepointStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SavepointStatementContext savepointStatement() throws RecognitionException {
		SavepointStatementContext _localctx = new SavepointStatementContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_savepointStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2394);
			match(SAVEPOINT);
			setState(2395);
			identifier();
			setState(2396);
			match(ON);
			setState(2397);
			match(ROLLBACK);
			setState(2398);
			match(RETAIN);
			setState(2400);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CURSORS) {
				{
				setState(2399);
				match(CURSORS);
				}
			}

			setState(2402);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReleaseSavepointStatementContext extends ParserRuleContext {
		public TerminalNode RELEASE() { return getToken(DB2Parser.RELEASE, 0); }
		public TerminalNode SAVEPOINT() { return getToken(DB2Parser.SAVEPOINT, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode TO() { return getToken(DB2Parser.TO, 0); }
		public ReleaseSavepointStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_releaseSavepointStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterReleaseSavepointStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitReleaseSavepointStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitReleaseSavepointStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReleaseSavepointStatementContext releaseSavepointStatement() throws RecognitionException {
		ReleaseSavepointStatementContext _localctx = new ReleaseSavepointStatementContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_releaseSavepointStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2404);
			match(RELEASE);
			setState(2406);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TO) {
				{
				setState(2405);
				match(TO);
				}
			}

			setState(2408);
			match(SAVEPOINT);
			setState(2409);
			identifier();
			setState(2410);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LockStatementContext extends ParserRuleContext {
		public TerminalNode LOCK() { return getToken(DB2Parser.LOCK, 0); }
		public TerminalNode TABLE() { return getToken(DB2Parser.TABLE, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode MODE() { return getToken(DB2Parser.MODE, 0); }
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode SHARE() { return getToken(DB2Parser.SHARE, 0); }
		public TerminalNode EXCLUSIVE() { return getToken(DB2Parser.EXCLUSIVE, 0); }
		public TerminalNode PART() { return getToken(DB2Parser.PART, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public LockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lockStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterLockStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitLockStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitLockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LockStatementContext lockStatement() throws RecognitionException {
		LockStatementContext _localctx = new LockStatementContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_lockStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2412);
			match(LOCK);
			setState(2413);
			match(TABLE);
			setState(2414);
			qualifiedName();
			setState(2417);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PART) {
				{
				setState(2415);
				match(PART);
				setState(2416);
				match(NUMBER);
				}
			}

			setState(2419);
			match(IN);
			setState(2420);
			_la = _input.LA(1);
			if ( !(_la==EXCLUSIVE || _la==SHARE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(2421);
			match(MODE);
			setState(2422);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InsertStatementContext extends ParserRuleContext {
		public TerminalNode INSERT() { return getToken(DB2Parser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(DB2Parser.INTO, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EndContext end() {
			return getRuleContext(EndContext.class,0);
		}
		public TerminalNode VALUES() { return getToken(DB2Parser.VALUES, 0); }
		public List<ValuesRowContext> valuesRow() {
			return getRuleContexts(ValuesRowContext.class);
		}
		public ValuesRowContext valuesRow(int i) {
			return getRuleContext(ValuesRowContext.class,i);
		}
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public ColumnListContext columnList() {
			return getRuleContext(ColumnListContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public InsertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterInsertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitInsertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitInsertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InsertStatementContext insertStatement() throws RecognitionException {
		InsertStatementContext _localctx = new InsertStatementContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_insertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2424);
			match(INSERT);
			setState(2425);
			match(INTO);
			setState(2426);
			qualifiedName();
			setState(2428);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,296,_ctx) ) {
			case 1:
				{
				setState(2427);
				columnList();
				}
				break;
			}
			setState(2440);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VALUES:
				{
				setState(2430);
				match(VALUES);
				setState(2431);
				valuesRow();
				setState(2436);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2432);
					match(COMMA);
					setState(2433);
					valuesRow();
					}
					}
					setState(2438);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case SELECT:
			case LPAREN:
				{
				setState(2439);
				queryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(2442);
			end();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValuesRowContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public ValuesRowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valuesRow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterValuesRow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitValuesRow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitValuesRow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValuesRowContext valuesRow() throws RecognitionException {
		ValuesRowContext _localctx = new ValuesRowContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_valuesRow);
		int _la;
		try {
			setState(2456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,300,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2444);
				match(LPAREN);
				setState(2445);
				expression(0);
				setState(2450);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2446);
					match(COMMA);
					setState(2447);
					expression(0);
					}
					}
					setState(2452);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2453);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2455);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueryExpressionContext extends ParserRuleContext {
		public List<QuerySpecificationContext> querySpecification() {
			return getRuleContexts(QuerySpecificationContext.class);
		}
		public QuerySpecificationContext querySpecification(int i) {
			return getRuleContext(QuerySpecificationContext.class,i);
		}
		public List<SetOperatorContext> setOperator() {
			return getRuleContexts(SetOperatorContext.class);
		}
		public SetOperatorContext setOperator(int i) {
			return getRuleContext(SetOperatorContext.class,i);
		}
		public QueryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterQueryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitQueryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitQueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryExpressionContext queryExpression() throws RecognitionException {
		QueryExpressionContext _localctx = new QueryExpressionContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_queryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2458);
			querySpecification();
			setState(2464);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EXCEPT || _la==INTERSECT || _la==UNION) {
				{
				{
				setState(2459);
				setOperator();
				setState(2460);
				querySpecification();
				}
				}
				setState(2466);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SetOperatorContext extends ParserRuleContext {
		public TerminalNode UNION() { return getToken(DB2Parser.UNION, 0); }
		public TerminalNode EXCEPT() { return getToken(DB2Parser.EXCEPT, 0); }
		public TerminalNode INTERSECT() { return getToken(DB2Parser.INTERSECT, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public SetOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSetOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSetOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSetOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetOperatorContext setOperator() throws RecognitionException {
		SetOperatorContext _localctx = new SetOperatorContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_setOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2467);
			_la = _input.LA(1);
			if ( !(_la==EXCEPT || _la==INTERSECT || _la==UNION) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(2469);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALL) {
				{
				setState(2468);
				match(ALL);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuerySpecificationContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(DB2Parser.SELECT, 0); }
		public SelectListContext selectList() {
			return getRuleContext(SelectListContext.class,0);
		}
		public TerminalNode FROM() { return getToken(DB2Parser.FROM, 0); }
		public List<TableReferenceContext> tableReference() {
			return getRuleContexts(TableReferenceContext.class);
		}
		public TableReferenceContext tableReference(int i) {
			return getRuleContext(TableReferenceContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode WHERE() { return getToken(DB2Parser.WHERE, 0); }
		public List<SearchConditionContext> searchCondition() {
			return getRuleContexts(SearchConditionContext.class);
		}
		public SearchConditionContext searchCondition(int i) {
			return getRuleContext(SearchConditionContext.class,i);
		}
		public TerminalNode GROUP() { return getToken(DB2Parser.GROUP, 0); }
		public List<TerminalNode> BY() { return getTokens(DB2Parser.BY); }
		public TerminalNode BY(int i) {
			return getToken(DB2Parser.BY, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode HAVING() { return getToken(DB2Parser.HAVING, 0); }
		public TerminalNode ORDER() { return getToken(DB2Parser.ORDER, 0); }
		public List<SortKeyContext> sortKey() {
			return getRuleContexts(SortKeyContext.class);
		}
		public SortKeyContext sortKey(int i) {
			return getRuleContext(SortKeyContext.class,i);
		}
		public TerminalNode FETCH() { return getToken(DB2Parser.FETCH, 0); }
		public TerminalNode FIRST() { return getToken(DB2Parser.FIRST, 0); }
		public TerminalNode ONLY() { return getToken(DB2Parser.ONLY, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode DISTINCT() { return getToken(DB2Parser.DISTINCT, 0); }
		public TerminalNode ROW() { return getToken(DB2Parser.ROW, 0); }
		public TerminalNode ROWS() { return getToken(DB2Parser.ROWS, 0); }
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public QuerySpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_querySpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterQuerySpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitQuerySpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitQuerySpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuerySpecificationContext querySpecification() throws RecognitionException {
		QuerySpecificationContext _localctx = new QuerySpecificationContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_querySpecification);
		int _la;
		try {
			setState(2530);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SELECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(2471);
				match(SELECT);
				setState(2473);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ALL || _la==DISTINCT) {
					{
					setState(2472);
					_la = _input.LA(1);
					if ( !(_la==ALL || _la==DISTINCT) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(2475);
				selectList();
				setState(2476);
				match(FROM);
				setState(2477);
				tableReference(0);
				setState(2482);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2478);
					match(COMMA);
					setState(2479);
					tableReference(0);
					}
					}
					setState(2484);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(2487);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WHERE) {
					{
					setState(2485);
					match(WHERE);
					setState(2486);
					searchCondition(0);
					}
				}

				setState(2499);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==GROUP) {
					{
					setState(2489);
					match(GROUP);
					setState(2490);
					match(BY);
					setState(2491);
					expression(0);
					setState(2496);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(2492);
						match(COMMA);
						setState(2493);
						expression(0);
						}
						}
						setState(2498);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(2503);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==HAVING) {
					{
					setState(2501);
					match(HAVING);
					setState(2502);
					searchCondition(0);
					}
				}

				setState(2515);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ORDER) {
					{
					setState(2505);
					match(ORDER);
					setState(2506);
					match(BY);
					setState(2507);
					sortKey();
					setState(2512);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(2508);
						match(COMMA);
						setState(2509);
						sortKey();
						}
						}
						setState(2514);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(2524);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==FETCH) {
					{
					setState(2517);
					match(FETCH);
					setState(2518);
					match(FIRST);
					setState(2520);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==NUMBER) {
						{
						setState(2519);
						match(NUMBER);
						}
					}

					setState(2522);
					_la = _input.LA(1);
					if ( !(_la==ROW || _la==ROWS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(2523);
					match(ONLY);
					}
				}

				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(2526);
				match(LPAREN);
				setState(2527);
				queryExpression();
				setState(2528);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectListContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(DB2Parser.STAR, 0); }
		public List<SelectItemContext> selectItem() {
			return getRuleContexts(SelectItemContext.class);
		}
		public SelectItemContext selectItem(int i) {
			return getRuleContext(SelectItemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public SelectListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSelectList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSelectList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSelectList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectListContext selectList() throws RecognitionException {
		SelectListContext _localctx = new SelectListContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_selectList);
		int _la;
		try {
			setState(2541);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(2532);
				match(STAR);
				}
				break;
			case CAPTURE:
			case CASE:
			case CAST:
			case CLONE:
			case COLLID:
			case CURRENT:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULL:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case USER:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case LPAREN:
			case PLUS:
			case MINUS:
			case QUESTION:
			case STRING:
			case HEX_STRING:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case NUMBER:
			case IDENTIFIER:
			case HOST_VARIABLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(2533);
				selectItem();
				setState(2538);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(2534);
					match(COMMA);
					setState(2535);
					selectItem();
					}
					}
					setState(2540);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectItemContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode DOT() { return getToken(DB2Parser.DOT, 0); }
		public TerminalNode STAR() { return getToken(DB2Parser.STAR, 0); }
		public SelectItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSelectItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSelectItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSelectItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectItemContext selectItem() throws RecognitionException {
		SelectItemContext _localctx = new SelectItemContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_selectItem);
		int _la;
		try {
			setState(2554);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,318,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2543);
				expression(0);
				setState(2548);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512550400L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 755914301435L) != 0)) {
					{
					setState(2545);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(2544);
						match(AS);
						}
					}

					setState(2547);
					identifier();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2550);
				qualifiedName();
				setState(2551);
				match(DOT);
				setState(2552);
				match(STAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TableReferenceContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TableReferenceContext> tableReference() {
			return getRuleContexts(TableReferenceContext.class);
		}
		public TableReferenceContext tableReference(int i) {
			return getRuleContext(TableReferenceContext.class,i);
		}
		public TerminalNode JOIN() { return getToken(DB2Parser.JOIN, 0); }
		public TerminalNode ON() { return getToken(DB2Parser.ON, 0); }
		public SearchConditionContext searchCondition() {
			return getRuleContext(SearchConditionContext.class,0);
		}
		public JoinTypeContext joinType() {
			return getRuleContext(JoinTypeContext.class,0);
		}
		public TableReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTableReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTableReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTableReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableReferenceContext tableReference() throws RecognitionException {
		return tableReference(0);
	}

	private TableReferenceContext tableReference(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TableReferenceContext _localctx = new TableReferenceContext(_ctx, _parentState);
		TableReferenceContext _prevctx = _localctx;
		int _startState = 216;
		enterRecursionRule(_localctx, 216, RULE_tableReference, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2573);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case IDENTIFIER:
				{
				setState(2557);
				qualifiedName();
				setState(2562);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,320,_ctx) ) {
				case 1:
					{
					setState(2559);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(2558);
						match(AS);
						}
					}

					setState(2561);
					identifier();
					}
					break;
				}
				}
				break;
			case LPAREN:
				{
				setState(2564);
				match(LPAREN);
				setState(2565);
				queryExpression();
				setState(2566);
				match(RPAREN);
				setState(2571);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,322,_ctx) ) {
				case 1:
					{
					setState(2568);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(2567);
						match(AS);
						}
					}

					setState(2570);
					identifier();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(2586);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,325,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TableReferenceContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_tableReference);
					setState(2575);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2577);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (((((_la - 91)) & ~0x3f) == 0 && ((1L << (_la - 91)) & 137440002049L) != 0) || _la==RIGHT) {
						{
						setState(2576);
						joinType();
						}
					}

					setState(2579);
					match(JOIN);
					setState(2580);
					tableReference(0);
					setState(2581);
					match(ON);
					setState(2582);
					searchCondition(0);
					}
					} 
				}
				setState(2588);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,325,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JoinTypeContext extends ParserRuleContext {
		public TerminalNode INNER() { return getToken(DB2Parser.INNER, 0); }
		public TerminalNode LEFT() { return getToken(DB2Parser.LEFT, 0); }
		public TerminalNode RIGHT() { return getToken(DB2Parser.RIGHT, 0); }
		public TerminalNode FULL() { return getToken(DB2Parser.FULL, 0); }
		public TerminalNode OUTER() { return getToken(DB2Parser.OUTER, 0); }
		public JoinTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_joinType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterJoinType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitJoinType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitJoinType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JoinTypeContext joinType() throws RecognitionException {
		JoinTypeContext _localctx = new JoinTypeContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_joinType);
		int _la;
		try {
			setState(2594);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
				enterOuterAlt(_localctx, 1);
				{
				setState(2589);
				match(INNER);
				}
				break;
			case FULL:
			case LEFT:
			case RIGHT:
				enterOuterAlt(_localctx, 2);
				{
				setState(2590);
				_la = _input.LA(1);
				if ( !(_la==FULL || _la==LEFT || _la==RIGHT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2592);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(2591);
					match(OUTER);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SortKeyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ASC() { return getToken(DB2Parser.ASC, 0); }
		public TerminalNode DESC() { return getToken(DB2Parser.DESC, 0); }
		public SortKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sortKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSortKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSortKey(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSortKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SortKeyContext sortKey() throws RecognitionException {
		SortKeyContext _localctx = new SortKeyContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_sortKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2596);
			expression(0);
			setState(2598);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(2597);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SearchConditionContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public List<SearchConditionContext> searchCondition() {
			return getRuleContexts(SearchConditionContext.class);
		}
		public SearchConditionContext searchCondition(int i) {
			return getRuleContext(SearchConditionContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode AND() { return getToken(DB2Parser.AND, 0); }
		public TerminalNode OR() { return getToken(DB2Parser.OR, 0); }
		public SearchConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_searchCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSearchCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSearchCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSearchCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SearchConditionContext searchCondition() throws RecognitionException {
		return searchCondition(0);
	}

	private SearchConditionContext searchCondition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		SearchConditionContext _localctx = new SearchConditionContext(_ctx, _parentState);
		SearchConditionContext _prevctx = _localctx;
		int _startState = 222;
		enterRecursionRule(_localctx, 222, RULE_searchCondition, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2608);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,329,_ctx) ) {
			case 1:
				{
				setState(2601);
				match(NOT);
				setState(2602);
				searchCondition(3);
				}
				break;
			case 2:
				{
				setState(2603);
				match(LPAREN);
				setState(2604);
				searchCondition(0);
				setState(2605);
				match(RPAREN);
				}
				break;
			case 3:
				{
				setState(2607);
				predicate();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2615);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,330,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SearchConditionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_searchCondition);
					setState(2610);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(2611);
					_la = _input.LA(1);
					if ( !(_la==AND || _la==OR) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(2612);
					searchCondition(5);
					}
					} 
				}
				setState(2617);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,330,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ComparisonOperatorContext comparisonOperator() {
			return getRuleContext(ComparisonOperatorContext.class,0);
		}
		public TerminalNode BETWEEN() { return getToken(DB2Parser.BETWEEN, 0); }
		public TerminalNode AND() { return getToken(DB2Parser.AND, 0); }
		public TerminalNode NOT() { return getToken(DB2Parser.NOT, 0); }
		public TerminalNode IN() { return getToken(DB2Parser.IN, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public TerminalNode LIKE() { return getToken(DB2Parser.LIKE, 0); }
		public TerminalNode ESCAPE() { return getToken(DB2Parser.ESCAPE, 0); }
		public TerminalNode IS() { return getToken(DB2Parser.IS, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public TerminalNode EXISTS() { return getToken(DB2Parser.EXISTS, 0); }
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 224, RULE_predicate);
		int _la;
		try {
			setState(2673);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,338,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2618);
				expression(0);
				setState(2619);
				comparisonOperator();
				setState(2620);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2622);
				expression(0);
				setState(2624);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2623);
					match(NOT);
					}
				}

				setState(2626);
				match(BETWEEN);
				setState(2627);
				expression(0);
				setState(2628);
				match(AND);
				setState(2629);
				expression(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2631);
				expression(0);
				setState(2633);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2632);
					match(NOT);
					}
				}

				setState(2635);
				match(IN);
				setState(2636);
				match(LPAREN);
				setState(2646);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,334,_ctx) ) {
				case 1:
					{
					setState(2637);
					expression(0);
					setState(2642);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(2638);
						match(COMMA);
						setState(2639);
						expression(0);
						}
						}
						setState(2644);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				case 2:
					{
					setState(2645);
					queryExpression();
					}
					break;
				}
				setState(2648);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2650);
				expression(0);
				setState(2652);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2651);
					match(NOT);
					}
				}

				setState(2654);
				match(LIKE);
				setState(2655);
				expression(0);
				setState(2658);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,336,_ctx) ) {
				case 1:
					{
					setState(2656);
					match(ESCAPE);
					setState(2657);
					expression(0);
					}
					break;
				}
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2660);
				expression(0);
				setState(2661);
				match(IS);
				setState(2663);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(2662);
					match(NOT);
					}
				}

				setState(2665);
				match(NULL);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(2667);
				match(EXISTS);
				setState(2668);
				match(LPAREN);
				setState(2669);
				queryExpression();
				setState(2670);
				match(RPAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(2672);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonOperatorContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(DB2Parser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(DB2Parser.NEQ, 0); }
		public TerminalNode LT() { return getToken(DB2Parser.LT, 0); }
		public TerminalNode GT() { return getToken(DB2Parser.GT, 0); }
		public TerminalNode LTE() { return getToken(DB2Parser.LTE, 0); }
		public TerminalNode GTE() { return getToken(DB2Parser.GTE, 0); }
		public ComparisonOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterComparisonOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitComparisonOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitComparisonOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperatorContext comparisonOperator() throws RecognitionException {
		ComparisonOperatorContext _localctx = new ComparisonOperatorContext(_ctx, getState());
		enterRule(_localctx, 226, RULE_comparisonOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2675);
			_la = _input.LA(1);
			if ( !(((((_la - 433)) & ~0x3f) == 0 && ((1L << (_la - 433)) & 63L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(DB2Parser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(DB2Parser.MINUS, 0); }
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public TerminalNode CASE() { return getToken(DB2Parser.CASE, 0); }
		public TerminalNode END() { return getToken(DB2Parser.END, 0); }
		public List<CaseWhenContext> caseWhen() {
			return getRuleContexts(CaseWhenContext.class);
		}
		public CaseWhenContext caseWhen(int i) {
			return getRuleContext(CaseWhenContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(DB2Parser.ELSE, 0); }
		public TerminalNode CAST() { return getToken(DB2Parser.CAST, 0); }
		public TerminalNode AS() { return getToken(DB2Parser.AS, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TerminalNode STAR() { return getToken(DB2Parser.STAR, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DB2Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DB2Parser.COMMA, i);
		}
		public QueryExpressionContext queryExpression() {
			return getRuleContext(QueryExpressionContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public SpecialValueContext specialValue() {
			return getRuleContext(SpecialValueContext.class,0);
		}
		public TerminalNode HOST_VARIABLE() { return getToken(DB2Parser.HOST_VARIABLE, 0); }
		public TerminalNode QUESTION() { return getToken(DB2Parser.QUESTION, 0); }
		public TerminalNode SLASH() { return getToken(DB2Parser.SLASH, 0); }
		public TerminalNode CONCAT_OP() { return getToken(DB2Parser.CONCAT_OP, 0); }
		public TerminalNode CONCAT() { return getToken(DB2Parser.CONCAT, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 228;
		enterRecursionRule(_localctx, 228, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2727);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,343,_ctx) ) {
			case 1:
				{
				setState(2678);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2679);
				expression(11);
				}
				break;
			case 2:
				{
				setState(2680);
				match(LPAREN);
				setState(2681);
				expression(0);
				setState(2682);
				match(RPAREN);
				}
				break;
			case 3:
				{
				setState(2684);
				match(CASE);
				setState(2686); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(2685);
					caseWhen();
					}
					}
					setState(2688); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==WHEN );
				setState(2692);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(2690);
					match(ELSE);
					setState(2691);
					expression(0);
					}
				}

				setState(2694);
				match(END);
				}
				break;
			case 4:
				{
				setState(2696);
				match(CAST);
				setState(2697);
				match(LPAREN);
				setState(2698);
				expression(0);
				setState(2699);
				match(AS);
				setState(2700);
				dataType();
				setState(2701);
				match(RPAREN);
				}
				break;
			case 5:
				{
				setState(2703);
				qualifiedName();
				setState(2704);
				match(LPAREN);
				setState(2714);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STAR:
					{
					setState(2705);
					match(STAR);
					}
					break;
				case CAPTURE:
				case CASE:
				case CAST:
				case CLONE:
				case COLLID:
				case CURRENT:
				case DBINFO:
				case ENCODING:
				case FINAL:
				case FIRST:
				case ISOBID:
				case JAR:
				case LAST:
				case LC_CTYPE:
				case LOCALE:
				case MAINTAINED:
				case MATERIALIZED:
				case NEXT:
				case NULL:
				case NULLS:
				case OBID:
				case OPTIMIZATION:
				case OPTIMIZE:
				case ORGANIZATION:
				case PADDED:
				case PLAN:
				case PREVVAL:
				case PROGRAM:
				case PSID:
				case QUERYNO:
				case ROWSET:
				case SCRATCHPAD:
				case SIMPLE:
				case SOURCE:
				case STANDARD:
				case STORES:
				case STYLE:
				case SUMMARY:
				case SYSFUN:
				case SYSIBM:
				case SYSPROC:
				case TYPE:
				case USER:
				case VALUE:
				case VARIANT:
				case AUTHENTICATION:
				case AUTHID:
				case BASED:
				case CONTROL:
				case UPON:
				case ABSOLUTE:
				case ACCESS:
				case ACTION:
				case ADMIN:
				case ALWAYS:
				case ASC:
				case ASUTIME:
				case AT:
				case ATOMIC:
				case ATTRIBUTES:
				case AUX:
				case BIT:
				case CACHE:
				case CALLED:
				case CARDINALITY:
				case CASCADE:
				case CHANGE:
				case CHANGED:
				case CHANGES:
				case COMPARISONS:
				case COMPRESS:
				case CONTEXT:
				case COPY:
				case CURSORS:
				case CYCLE:
				case DATACLAS:
				case DB2:
				case DB2SQL:
				case DEBUG:
				case DEFER:
				case DEFINE:
				case DEFINER:
				case DESC:
				case EACH:
				case ENABLE:
				case ENFORCED:
				case ENVIRONMENT:
				case EVERY:
				case EXCLUDE:
				case EXCLUDING:
				case EXCLUSIVE:
				case FREEPAGE:
				case GBPCACHE:
				case GENERATE:
				case HASH:
				case HIDDEN_KW:
				case HISTORY:
				case ID:
				case IDENTITY:
				case IMPLICITLY:
				case INCLUDE:
				case INCLUDING:
				case INCREMENT:
				case INDEXBP:
				case INLINE:
				case INPUT:
				case INSTEAD:
				case KEYS:
				case LARGE:
				case LENGTH:
				case LIMIT:
				case LOAD:
				case LOB:
				case LOGGED:
				case MAIN:
				case MASK:
				case MAXPARTITIONS:
				case MAXROWS:
				case MAXVALUE:
				case MEMBER:
				case MGMTCLAS:
				case MINVALUE:
				case MIXED:
				case MODE:
				case NAME:
				case NEW:
				case NEW_TABLE:
				case OLD_TABLE:
				case ONLY:
				case OPTION:
				case OPTIONS:
				case ORGANIZE:
				case PAGE:
				case PAGENUM:
				case PCTFREE:
				case PERMISSION:
				case PRIMARY:
				case QUALIFIER:
				case RANDOM:
				case RANGE:
				case REGENERATE:
				case REGISTERS:
				case RELATIVE:
				case REMOVE:
				case REPLACE:
				case RESET:
				case RESIDENT:
				case RESTART:
				case RETAIN:
				case ROTATE:
				case ROWS:
				case SBCS:
				case SECURED:
				case SEGSIZE:
				case SETS:
				case SHARE:
				case SIZE:
				case SPACE:
				case SPECIAL:
				case SQL:
				case SQLID:
				case START:
				case STARTING:
				case STATEMENT:
				case STORCLAS:
				case SUB:
				case TEMPORARY:
				case TIME:
				case TIMESTAMP:
				case TRACKMOD:
				case TRUSTED:
				case UNLOAD:
				case USAGE:
				case USE:
				case VARCHAR:
				case VARGRAPHIC:
				case VARYING:
				case VERSIONING:
				case WITHOUT:
				case WORK:
				case XMLPATTERN:
				case YES:
				case LPAREN:
				case PLUS:
				case MINUS:
				case QUESTION:
				case STRING:
				case HEX_STRING:
				case DELIMITED_IDENTIFIER:
				case PLACEHOLDER:
				case NUMBER:
				case IDENTIFIER:
				case HOST_VARIABLE:
					{
					{
					setState(2706);
					expression(0);
					setState(2711);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(2707);
						match(COMMA);
						setState(2708);
						expression(0);
						}
						}
						setState(2713);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					}
					break;
				case RPAREN:
					break;
				default:
					break;
				}
				setState(2716);
				match(RPAREN);
				}
				break;
			case 6:
				{
				setState(2718);
				match(LPAREN);
				setState(2719);
				queryExpression();
				setState(2720);
				match(RPAREN);
				}
				break;
			case 7:
				{
				setState(2722);
				constant();
				}
				break;
			case 8:
				{
				setState(2723);
				specialValue();
				}
				break;
			case 9:
				{
				setState(2724);
				qualifiedName();
				}
				break;
			case 10:
				{
				setState(2725);
				match(HOST_VARIABLE);
				}
				break;
			case 11:
				{
				setState(2726);
				match(QUESTION);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(2743);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,345,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(2741);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,344,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2729);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(2730);
						_la = _input.LA(1);
						if ( !(_la==STAR || _la==SLASH) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2731);
						expression(16);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2732);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(2733);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(2734);
						expression(15);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2735);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(2736);
						match(CONCAT_OP);
						setState(2737);
						expression(14);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(2738);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(2739);
						match(CONCAT);
						setState(2740);
						expression(13);
						}
						break;
					}
					} 
				}
				setState(2745);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,345,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseWhenContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(DB2Parser.WHEN, 0); }
		public SearchConditionContext searchCondition() {
			return getRuleContext(SearchConditionContext.class,0);
		}
		public TerminalNode THEN() { return getToken(DB2Parser.THEN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public CaseWhenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseWhen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterCaseWhen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitCaseWhen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitCaseWhen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseWhenContext caseWhen() throws RecognitionException {
		CaseWhenContext _localctx = new CaseWhenContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_caseWhen);
		try {
			setState(2756);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,346,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2746);
				match(WHEN);
				setState(2747);
				searchCondition(0);
				setState(2748);
				match(THEN);
				setState(2749);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2751);
				match(WHEN);
				setState(2752);
				expression(0);
				setState(2753);
				match(THEN);
				setState(2754);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SpecialValueContext extends ParserRuleContext {
		public TerminalNode CURRENT() { return getToken(DB2Parser.CURRENT, 0); }
		public List<NonReservedContext> nonReserved() {
			return getRuleContexts(NonReservedContext.class);
		}
		public NonReservedContext nonReserved(int i) {
			return getRuleContext(NonReservedContext.class,i);
		}
		public TerminalNode USER() { return getToken(DB2Parser.USER, 0); }
		public TerminalNode NULL() { return getToken(DB2Parser.NULL, 0); }
		public SpecialValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specialValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSpecialValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSpecialValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSpecialValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecialValueContext specialValue() throws RecognitionException {
		SpecialValueContext _localctx = new SpecialValueContext(_ctx, getState());
		enterRule(_localctx, 232, RULE_specialValue);
		try {
			int _alt;
			setState(2766);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CURRENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(2758);
				match(CURRENT);
				setState(2760); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(2759);
						nonReserved();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(2762); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,347,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case USER:
				enterOuterAlt(_localctx, 2);
				{
				setState(2764);
				match(USER);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 3);
				{
				setState(2765);
				match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantContext extends ParserRuleContext {
		public SignedNumberContext signedNumber() {
			return getRuleContext(SignedNumberContext.class,0);
		}
		public TerminalNode STRING() { return getToken(DB2Parser.STRING, 0); }
		public TerminalNode HEX_STRING() { return getToken(DB2Parser.HEX_STRING, 0); }
		public TerminalNode PLACEHOLDER() { return getToken(DB2Parser.PLACEHOLDER, 0); }
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_constant);
		try {
			setState(2772);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(2768);
				signedNumber();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(2769);
				match(STRING);
				}
				break;
			case HEX_STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(2770);
				match(HEX_STRING);
				}
				break;
			case PLACEHOLDER:
				enterOuterAlt(_localctx, 4);
				{
				setState(2771);
				match(PLACEHOLDER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SignedNumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(DB2Parser.NUMBER, 0); }
		public TerminalNode PLUS() { return getToken(DB2Parser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(DB2Parser.MINUS, 0); }
		public SignedNumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_signedNumber; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterSignedNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitSignedNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitSignedNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SignedNumberContext signedNumber() throws RecognitionException {
		SignedNumberContext _localctx = new SignedNumberContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_signedNumber);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2775);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PLUS || _la==MINUS) {
				{
				setState(2774);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(2777);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QualifiedNameContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(DB2Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(DB2Parser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_qualifiedName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2779);
			identifier();
			setState(2785);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,351,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2780);
					if (!(continuesName(_localctx))) throw new FailedPredicateException(this, "continuesName($ctx)");
					setState(2781);
					match(DOT);
					setState(2782);
					identifier();
					}
					} 
				}
				setState(2787);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,351,_ctx);
			}
			setState(2788);
			if (!(!continuesName(_localctx))) throw new FailedPredicateException(this, "!continuesName($ctx)");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DB2Parser.IDENTIFIER, 0); }
		public TerminalNode DELIMITED_IDENTIFIER() { return getToken(DB2Parser.DELIMITED_IDENTIFIER, 0); }
		public TerminalNode PLACEHOLDER() { return getToken(DB2Parser.PLACEHOLDER, 0); }
		public NonReservedContext nonReserved() {
			return getRuleContext(NonReservedContext.class,0);
		}
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_identifier);
		try {
			setState(2794);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(2790);
				match(IDENTIFIER);
				}
				break;
			case DELIMITED_IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(2791);
				match(DELIMITED_IDENTIFIER);
				}
				break;
			case PLACEHOLDER:
				enterOuterAlt(_localctx, 3);
				{
				setState(2792);
				match(PLACEHOLDER);
				}
				break;
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
				enterOuterAlt(_localctx, 4);
				{
				setState(2793);
				nonReserved();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataTypeContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(DB2Parser.LPAREN, 0); }
		public List<TerminalNode> NUMBER() { return getTokens(DB2Parser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(DB2Parser.NUMBER, i);
		}
		public TerminalNode RPAREN() { return getToken(DB2Parser.RPAREN, 0); }
		public List<TypeAttributeContext> typeAttribute() {
			return getRuleContexts(TypeAttributeContext.class);
		}
		public TypeAttributeContext typeAttribute(int i) {
			return getRuleContext(TypeAttributeContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(DB2Parser.COMMA, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_dataType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2796);
			typeName();
			setState(2804);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,354,_ctx) ) {
			case 1:
				{
				setState(2797);
				match(LPAREN);
				setState(2798);
				match(NUMBER);
				setState(2801);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(2799);
					match(COMMA);
					setState(2800);
					match(NUMBER);
					}
				}

				setState(2803);
				match(RPAREN);
				}
				break;
			}
			setState(2809);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,355,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(2806);
					typeAttribute();
					}
					} 
				}
				setState(2811);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,355,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeNameContext extends ParserRuleContext {
		public TerminalNode CHAR() { return getToken(DB2Parser.CHAR, 0); }
		public TerminalNode VARYING() { return getToken(DB2Parser.VARYING, 0); }
		public TerminalNode CHARACTER() { return getToken(DB2Parser.CHARACTER, 0); }
		public TerminalNode LONG() { return getToken(DB2Parser.LONG, 0); }
		public TerminalNode VARCHAR() { return getToken(DB2Parser.VARCHAR, 0); }
		public TerminalNode VARGRAPHIC() { return getToken(DB2Parser.VARGRAPHIC, 0); }
		public TerminalNode DOUBLE() { return getToken(DB2Parser.DOUBLE, 0); }
		public TerminalNode PRECISION() { return getToken(DB2Parser.PRECISION, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTypeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_typeName);
		int _la;
		try {
			setState(2827);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CHAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(2812);
				match(CHAR);
				setState(2814);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,356,_ctx) ) {
				case 1:
					{
					setState(2813);
					match(VARYING);
					}
					break;
				}
				}
				break;
			case CHARACTER:
				enterOuterAlt(_localctx, 2);
				{
				setState(2816);
				match(CHARACTER);
				setState(2818);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,357,_ctx) ) {
				case 1:
					{
					setState(2817);
					match(VARYING);
					}
					break;
				}
				}
				break;
			case LONG:
				enterOuterAlt(_localctx, 3);
				{
				setState(2820);
				match(LONG);
				setState(2821);
				_la = _input.LA(1);
				if ( !(_la==VARCHAR || _la==VARGRAPHIC) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case DOUBLE:
				enterOuterAlt(_localctx, 4);
				{
				setState(2822);
				match(DOUBLE);
				setState(2824);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PRECISION) {
					{
					setState(2823);
					match(PRECISION);
					}
				}

				}
				break;
			case CAPTURE:
			case CLONE:
			case COLLID:
			case DBINFO:
			case ENCODING:
			case FINAL:
			case FIRST:
			case ISOBID:
			case JAR:
			case LAST:
			case LC_CTYPE:
			case LOCALE:
			case MAINTAINED:
			case MATERIALIZED:
			case NEXT:
			case NULLS:
			case OBID:
			case OPTIMIZATION:
			case OPTIMIZE:
			case ORGANIZATION:
			case PADDED:
			case PLAN:
			case PREVVAL:
			case PROGRAM:
			case PSID:
			case QUERYNO:
			case ROWSET:
			case SCRATCHPAD:
			case SIMPLE:
			case SOURCE:
			case STANDARD:
			case STORES:
			case STYLE:
			case SUMMARY:
			case SYSFUN:
			case SYSIBM:
			case SYSPROC:
			case TYPE:
			case VALUE:
			case VARIANT:
			case AUTHENTICATION:
			case AUTHID:
			case BASED:
			case CONTROL:
			case UPON:
			case ABSOLUTE:
			case ACCESS:
			case ACTION:
			case ADMIN:
			case ALWAYS:
			case ASC:
			case ASUTIME:
			case AT:
			case ATOMIC:
			case ATTRIBUTES:
			case AUX:
			case BIT:
			case CACHE:
			case CALLED:
			case CARDINALITY:
			case CASCADE:
			case CHANGE:
			case CHANGED:
			case CHANGES:
			case COMPARISONS:
			case COMPRESS:
			case CONTEXT:
			case COPY:
			case CURSORS:
			case CYCLE:
			case DATACLAS:
			case DB2:
			case DB2SQL:
			case DEBUG:
			case DEFER:
			case DEFINE:
			case DEFINER:
			case DESC:
			case EACH:
			case ENABLE:
			case ENFORCED:
			case ENVIRONMENT:
			case EVERY:
			case EXCLUDE:
			case EXCLUDING:
			case EXCLUSIVE:
			case FREEPAGE:
			case GBPCACHE:
			case GENERATE:
			case HASH:
			case HIDDEN_KW:
			case HISTORY:
			case ID:
			case IDENTITY:
			case IMPLICITLY:
			case INCLUDE:
			case INCLUDING:
			case INCREMENT:
			case INDEXBP:
			case INLINE:
			case INPUT:
			case INSTEAD:
			case KEYS:
			case LARGE:
			case LENGTH:
			case LIMIT:
			case LOAD:
			case LOB:
			case LOGGED:
			case MAIN:
			case MASK:
			case MAXPARTITIONS:
			case MAXROWS:
			case MAXVALUE:
			case MEMBER:
			case MGMTCLAS:
			case MINVALUE:
			case MIXED:
			case MODE:
			case NAME:
			case NEW:
			case NEW_TABLE:
			case OLD_TABLE:
			case ONLY:
			case OPTION:
			case OPTIONS:
			case ORGANIZE:
			case PAGE:
			case PAGENUM:
			case PCTFREE:
			case PERMISSION:
			case PRIMARY:
			case QUALIFIER:
			case RANDOM:
			case RANGE:
			case REGENERATE:
			case REGISTERS:
			case RELATIVE:
			case REMOVE:
			case REPLACE:
			case RESET:
			case RESIDENT:
			case RESTART:
			case RETAIN:
			case ROTATE:
			case ROWS:
			case SBCS:
			case SECURED:
			case SEGSIZE:
			case SETS:
			case SHARE:
			case SIZE:
			case SPACE:
			case SPECIAL:
			case SQL:
			case SQLID:
			case START:
			case STARTING:
			case STATEMENT:
			case STORCLAS:
			case SUB:
			case TEMPORARY:
			case TIME:
			case TIMESTAMP:
			case TRACKMOD:
			case TRUSTED:
			case UNLOAD:
			case USAGE:
			case USE:
			case VARCHAR:
			case VARGRAPHIC:
			case VARYING:
			case VERSIONING:
			case WITHOUT:
			case WORK:
			case XMLPATTERN:
			case YES:
			case DELIMITED_IDENTIFIER:
			case PLACEHOLDER:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 5);
				{
				setState(2826);
				qualifiedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeAttributeContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode DATA() { return getToken(DB2Parser.DATA, 0); }
		public TerminalNode SBCS() { return getToken(DB2Parser.SBCS, 0); }
		public TerminalNode MIXED() { return getToken(DB2Parser.MIXED, 0); }
		public TerminalNode BIT() { return getToken(DB2Parser.BIT, 0); }
		public TerminalNode CCSID() { return getToken(DB2Parser.CCSID, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode WITH() { return getToken(DB2Parser.WITH, 0); }
		public TerminalNode TIME() { return getToken(DB2Parser.TIME, 0); }
		public TerminalNode ZONE() { return getToken(DB2Parser.ZONE, 0); }
		public TerminalNode WITHOUT() { return getToken(DB2Parser.WITHOUT, 0); }
		public TypeAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeAttribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterTypeAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitTypeAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitTypeAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeAttributeContext typeAttribute() throws RecognitionException {
		TypeAttributeContext _localctx = new TypeAttributeContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_typeAttribute);
		int _la;
		try {
			setState(2840);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FOR:
				enterOuterAlt(_localctx, 1);
				{
				setState(2829);
				match(FOR);
				setState(2830);
				_la = _input.LA(1);
				if ( !(_la==BIT || _la==MIXED || _la==SBCS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(2831);
				match(DATA);
				}
				break;
			case CCSID:
				enterOuterAlt(_localctx, 2);
				{
				setState(2832);
				match(CCSID);
				setState(2833);
				identifier();
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 3);
				{
				setState(2834);
				match(WITH);
				setState(2835);
				match(TIME);
				setState(2836);
				match(ZONE);
				}
				break;
			case WITHOUT:
				enterOuterAlt(_localctx, 4);
				{
				setState(2837);
				match(WITHOUT);
				setState(2838);
				match(TIME);
				setState(2839);
				match(ZONE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StorageOptionContext extends ParserRuleContext {
		public TerminalNode PRIQTY() { return getToken(DB2Parser.PRIQTY, 0); }
		public SignedNumberContext signedNumber() {
			return getRuleContext(SignedNumberContext.class,0);
		}
		public TerminalNode SECQTY() { return getToken(DB2Parser.SECQTY, 0); }
		public TerminalNode ERASE() { return getToken(DB2Parser.ERASE, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode NO() { return getToken(DB2Parser.NO, 0); }
		public TerminalNode FREEPAGE() { return getToken(DB2Parser.FREEPAGE, 0); }
		public List<TerminalNode> NUMBER() { return getTokens(DB2Parser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(DB2Parser.NUMBER, i);
		}
		public TerminalNode PCTFREE() { return getToken(DB2Parser.PCTFREE, 0); }
		public TerminalNode FOR() { return getToken(DB2Parser.FOR, 0); }
		public TerminalNode UPDATE() { return getToken(DB2Parser.UPDATE, 0); }
		public TerminalNode GBPCACHE() { return getToken(DB2Parser.GBPCACHE, 0); }
		public TerminalNode CHANGED() { return getToken(DB2Parser.CHANGED, 0); }
		public TerminalNode ALL() { return getToken(DB2Parser.ALL, 0); }
		public TerminalNode NONE() { return getToken(DB2Parser.NONE, 0); }
		public TerminalNode SYSTEM() { return getToken(DB2Parser.SYSTEM, 0); }
		public TerminalNode USING() { return getToken(DB2Parser.USING, 0); }
		public TerminalNode STOGROUP() { return getToken(DB2Parser.STOGROUP, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode VCAT() { return getToken(DB2Parser.VCAT, 0); }
		public StorageOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storageOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterStorageOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitStorageOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitStorageOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StorageOptionContext storageOption() throws RecognitionException {
		StorageOptionContext _localctx = new StorageOptionContext(_ctx, getState());
		enterRule(_localctx, 248, RULE_storageOption);
		int _la;
		try {
			setState(2866);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRIQTY:
				enterOuterAlt(_localctx, 1);
				{
				setState(2842);
				match(PRIQTY);
				setState(2843);
				signedNumber();
				}
				break;
			case SECQTY:
				enterOuterAlt(_localctx, 2);
				{
				setState(2844);
				match(SECQTY);
				setState(2845);
				signedNumber();
				}
				break;
			case ERASE:
				enterOuterAlt(_localctx, 3);
				{
				setState(2846);
				match(ERASE);
				setState(2847);
				_la = _input.LA(1);
				if ( !(_la==NO || _la==YES) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case FREEPAGE:
				enterOuterAlt(_localctx, 4);
				{
				setState(2848);
				match(FREEPAGE);
				setState(2849);
				match(NUMBER);
				}
				break;
			case PCTFREE:
				enterOuterAlt(_localctx, 5);
				{
				setState(2850);
				match(PCTFREE);
				setState(2851);
				match(NUMBER);
				setState(2855);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,361,_ctx) ) {
				case 1:
					{
					setState(2852);
					match(FOR);
					setState(2853);
					match(UPDATE);
					setState(2854);
					match(NUMBER);
					}
					break;
				}
				}
				break;
			case GBPCACHE:
				enterOuterAlt(_localctx, 6);
				{
				setState(2857);
				match(GBPCACHE);
				setState(2858);
				_la = _input.LA(1);
				if ( !(_la==ALL || _la==NONE || _la==SYSTEM || _la==CHANGED) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case USING:
				enterOuterAlt(_localctx, 7);
				{
				setState(2859);
				match(USING);
				setState(2864);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STOGROUP:
					{
					setState(2860);
					match(STOGROUP);
					setState(2861);
					identifier();
					}
					break;
				case VCAT:
					{
					setState(2862);
					match(VCAT);
					setState(2863);
					identifier();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EndContext extends ParserRuleContext {
		public TerminalNode SEMI() { return getToken(DB2Parser.SEMI, 0); }
		public EndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_end; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterEnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitEnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitEnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndContext end() throws RecognitionException {
		EndContext _localctx = new EndContext(_ctx, getState());
		enterRule(_localctx, 250, RULE_end);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2869);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,364,_ctx) ) {
			case 1:
				{
				setState(2868);
				match(SEMI);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NonReservedContext extends ParserRuleContext {
		public TerminalNode ABSOLUTE() { return getToken(DB2Parser.ABSOLUTE, 0); }
		public TerminalNode ACCESS() { return getToken(DB2Parser.ACCESS, 0); }
		public TerminalNode ACTION() { return getToken(DB2Parser.ACTION, 0); }
		public TerminalNode ADMIN() { return getToken(DB2Parser.ADMIN, 0); }
		public TerminalNode ALWAYS() { return getToken(DB2Parser.ALWAYS, 0); }
		public TerminalNode ASUTIME() { return getToken(DB2Parser.ASUTIME, 0); }
		public TerminalNode AT() { return getToken(DB2Parser.AT, 0); }
		public TerminalNode ATOMIC() { return getToken(DB2Parser.ATOMIC, 0); }
		public TerminalNode ATTRIBUTES() { return getToken(DB2Parser.ATTRIBUTES, 0); }
		public TerminalNode AUX() { return getToken(DB2Parser.AUX, 0); }
		public TerminalNode CACHE() { return getToken(DB2Parser.CACHE, 0); }
		public TerminalNode CALLED() { return getToken(DB2Parser.CALLED, 0); }
		public TerminalNode CARDINALITY() { return getToken(DB2Parser.CARDINALITY, 0); }
		public TerminalNode CASCADE() { return getToken(DB2Parser.CASCADE, 0); }
		public TerminalNode CHANGE() { return getToken(DB2Parser.CHANGE, 0); }
		public TerminalNode CHANGED() { return getToken(DB2Parser.CHANGED, 0); }
		public TerminalNode CHANGES() { return getToken(DB2Parser.CHANGES, 0); }
		public TerminalNode COMPARISONS() { return getToken(DB2Parser.COMPARISONS, 0); }
		public TerminalNode COMPRESS() { return getToken(DB2Parser.COMPRESS, 0); }
		public TerminalNode CONTEXT() { return getToken(DB2Parser.CONTEXT, 0); }
		public TerminalNode COPY() { return getToken(DB2Parser.COPY, 0); }
		public TerminalNode CURSORS() { return getToken(DB2Parser.CURSORS, 0); }
		public TerminalNode CYCLE() { return getToken(DB2Parser.CYCLE, 0); }
		public TerminalNode DATACLAS() { return getToken(DB2Parser.DATACLAS, 0); }
		public TerminalNode DB2() { return getToken(DB2Parser.DB2, 0); }
		public TerminalNode DB2SQL() { return getToken(DB2Parser.DB2SQL, 0); }
		public TerminalNode DEBUG() { return getToken(DB2Parser.DEBUG, 0); }
		public TerminalNode DEFER() { return getToken(DB2Parser.DEFER, 0); }
		public TerminalNode DEFINE() { return getToken(DB2Parser.DEFINE, 0); }
		public TerminalNode DEFINER() { return getToken(DB2Parser.DEFINER, 0); }
		public TerminalNode EACH() { return getToken(DB2Parser.EACH, 0); }
		public TerminalNode ENABLE() { return getToken(DB2Parser.ENABLE, 0); }
		public TerminalNode ENFORCED() { return getToken(DB2Parser.ENFORCED, 0); }
		public TerminalNode ENVIRONMENT() { return getToken(DB2Parser.ENVIRONMENT, 0); }
		public TerminalNode EVERY() { return getToken(DB2Parser.EVERY, 0); }
		public TerminalNode EXCLUDE() { return getToken(DB2Parser.EXCLUDE, 0); }
		public TerminalNode EXCLUDING() { return getToken(DB2Parser.EXCLUDING, 0); }
		public TerminalNode EXCLUSIVE() { return getToken(DB2Parser.EXCLUSIVE, 0); }
		public TerminalNode GENERATE() { return getToken(DB2Parser.GENERATE, 0); }
		public TerminalNode HASH() { return getToken(DB2Parser.HASH, 0); }
		public TerminalNode HIDDEN_KW() { return getToken(DB2Parser.HIDDEN_KW, 0); }
		public TerminalNode HISTORY() { return getToken(DB2Parser.HISTORY, 0); }
		public TerminalNode ID() { return getToken(DB2Parser.ID, 0); }
		public TerminalNode IDENTITY() { return getToken(DB2Parser.IDENTITY, 0); }
		public TerminalNode IMPLICITLY() { return getToken(DB2Parser.IMPLICITLY, 0); }
		public TerminalNode INCLUDE() { return getToken(DB2Parser.INCLUDE, 0); }
		public TerminalNode INCLUDING() { return getToken(DB2Parser.INCLUDING, 0); }
		public TerminalNode INCREMENT() { return getToken(DB2Parser.INCREMENT, 0); }
		public TerminalNode INDEXBP() { return getToken(DB2Parser.INDEXBP, 0); }
		public TerminalNode INLINE() { return getToken(DB2Parser.INLINE, 0); }
		public TerminalNode INPUT() { return getToken(DB2Parser.INPUT, 0); }
		public TerminalNode INSTEAD() { return getToken(DB2Parser.INSTEAD, 0); }
		public TerminalNode KEYS() { return getToken(DB2Parser.KEYS, 0); }
		public TerminalNode LARGE() { return getToken(DB2Parser.LARGE, 0); }
		public TerminalNode LENGTH() { return getToken(DB2Parser.LENGTH, 0); }
		public TerminalNode LIMIT() { return getToken(DB2Parser.LIMIT, 0); }
		public TerminalNode LOAD() { return getToken(DB2Parser.LOAD, 0); }
		public TerminalNode LOB() { return getToken(DB2Parser.LOB, 0); }
		public TerminalNode LOGGED() { return getToken(DB2Parser.LOGGED, 0); }
		public TerminalNode MAIN() { return getToken(DB2Parser.MAIN, 0); }
		public TerminalNode MASK() { return getToken(DB2Parser.MASK, 0); }
		public TerminalNode MAXPARTITIONS() { return getToken(DB2Parser.MAXPARTITIONS, 0); }
		public TerminalNode MAXROWS() { return getToken(DB2Parser.MAXROWS, 0); }
		public TerminalNode MAXVALUE() { return getToken(DB2Parser.MAXVALUE, 0); }
		public TerminalNode MEMBER() { return getToken(DB2Parser.MEMBER, 0); }
		public TerminalNode MGMTCLAS() { return getToken(DB2Parser.MGMTCLAS, 0); }
		public TerminalNode MINVALUE() { return getToken(DB2Parser.MINVALUE, 0); }
		public TerminalNode MIXED() { return getToken(DB2Parser.MIXED, 0); }
		public TerminalNode MODE() { return getToken(DB2Parser.MODE, 0); }
		public TerminalNode NAME() { return getToken(DB2Parser.NAME, 0); }
		public TerminalNode NEW() { return getToken(DB2Parser.NEW, 0); }
		public TerminalNode NEW_TABLE() { return getToken(DB2Parser.NEW_TABLE, 0); }
		public TerminalNode OLD_TABLE() { return getToken(DB2Parser.OLD_TABLE, 0); }
		public TerminalNode ONLY() { return getToken(DB2Parser.ONLY, 0); }
		public TerminalNode OPTION() { return getToken(DB2Parser.OPTION, 0); }
		public TerminalNode OPTIONS() { return getToken(DB2Parser.OPTIONS, 0); }
		public TerminalNode ORGANIZE() { return getToken(DB2Parser.ORGANIZE, 0); }
		public TerminalNode PAGE() { return getToken(DB2Parser.PAGE, 0); }
		public TerminalNode PAGENUM() { return getToken(DB2Parser.PAGENUM, 0); }
		public TerminalNode PCTFREE() { return getToken(DB2Parser.PCTFREE, 0); }
		public TerminalNode PERMISSION() { return getToken(DB2Parser.PERMISSION, 0); }
		public TerminalNode QUALIFIER() { return getToken(DB2Parser.QUALIFIER, 0); }
		public TerminalNode RANDOM() { return getToken(DB2Parser.RANDOM, 0); }
		public TerminalNode RANGE() { return getToken(DB2Parser.RANGE, 0); }
		public TerminalNode REGENERATE() { return getToken(DB2Parser.REGENERATE, 0); }
		public TerminalNode REGISTERS() { return getToken(DB2Parser.REGISTERS, 0); }
		public TerminalNode RELATIVE() { return getToken(DB2Parser.RELATIVE, 0); }
		public TerminalNode REMOVE() { return getToken(DB2Parser.REMOVE, 0); }
		public TerminalNode REPLACE() { return getToken(DB2Parser.REPLACE, 0); }
		public TerminalNode RESET() { return getToken(DB2Parser.RESET, 0); }
		public TerminalNode RESIDENT() { return getToken(DB2Parser.RESIDENT, 0); }
		public TerminalNode RESTART() { return getToken(DB2Parser.RESTART, 0); }
		public TerminalNode RETAIN() { return getToken(DB2Parser.RETAIN, 0); }
		public TerminalNode ROTATE() { return getToken(DB2Parser.ROTATE, 0); }
		public TerminalNode ROWS() { return getToken(DB2Parser.ROWS, 0); }
		public TerminalNode SBCS() { return getToken(DB2Parser.SBCS, 0); }
		public TerminalNode SECURED() { return getToken(DB2Parser.SECURED, 0); }
		public TerminalNode SEGSIZE() { return getToken(DB2Parser.SEGSIZE, 0); }
		public TerminalNode SETS() { return getToken(DB2Parser.SETS, 0); }
		public TerminalNode SHARE() { return getToken(DB2Parser.SHARE, 0); }
		public TerminalNode SIZE() { return getToken(DB2Parser.SIZE, 0); }
		public TerminalNode SPACE() { return getToken(DB2Parser.SPACE, 0); }
		public TerminalNode SPECIAL() { return getToken(DB2Parser.SPECIAL, 0); }
		public TerminalNode SQL() { return getToken(DB2Parser.SQL, 0); }
		public TerminalNode SQLID() { return getToken(DB2Parser.SQLID, 0); }
		public TerminalNode START() { return getToken(DB2Parser.START, 0); }
		public TerminalNode STARTING() { return getToken(DB2Parser.STARTING, 0); }
		public TerminalNode STATEMENT() { return getToken(DB2Parser.STATEMENT, 0); }
		public TerminalNode STORCLAS() { return getToken(DB2Parser.STORCLAS, 0); }
		public TerminalNode SUB() { return getToken(DB2Parser.SUB, 0); }
		public TerminalNode TEMPORARY() { return getToken(DB2Parser.TEMPORARY, 0); }
		public TerminalNode TIME() { return getToken(DB2Parser.TIME, 0); }
		public TerminalNode TIMESTAMP() { return getToken(DB2Parser.TIMESTAMP, 0); }
		public TerminalNode TRACKMOD() { return getToken(DB2Parser.TRACKMOD, 0); }
		public TerminalNode TRUSTED() { return getToken(DB2Parser.TRUSTED, 0); }
		public TerminalNode UNLOAD() { return getToken(DB2Parser.UNLOAD, 0); }
		public TerminalNode USAGE() { return getToken(DB2Parser.USAGE, 0); }
		public TerminalNode USE() { return getToken(DB2Parser.USE, 0); }
		public TerminalNode VARCHAR() { return getToken(DB2Parser.VARCHAR, 0); }
		public TerminalNode VARGRAPHIC() { return getToken(DB2Parser.VARGRAPHIC, 0); }
		public TerminalNode VARYING() { return getToken(DB2Parser.VARYING, 0); }
		public TerminalNode VERSIONING() { return getToken(DB2Parser.VERSIONING, 0); }
		public TerminalNode WITHOUT() { return getToken(DB2Parser.WITHOUT, 0); }
		public TerminalNode WORK() { return getToken(DB2Parser.WORK, 0); }
		public TerminalNode XMLPATTERN() { return getToken(DB2Parser.XMLPATTERN, 0); }
		public TerminalNode YES() { return getToken(DB2Parser.YES, 0); }
		public TerminalNode ASC() { return getToken(DB2Parser.ASC, 0); }
		public TerminalNode DESC() { return getToken(DB2Parser.DESC, 0); }
		public TerminalNode BIT() { return getToken(DB2Parser.BIT, 0); }
		public TerminalNode FREEPAGE() { return getToken(DB2Parser.FREEPAGE, 0); }
		public TerminalNode GBPCACHE() { return getToken(DB2Parser.GBPCACHE, 0); }
		public TerminalNode PRIMARY() { return getToken(DB2Parser.PRIMARY, 0); }
		public TerminalNode NULLS() { return getToken(DB2Parser.NULLS, 0); }
		public TerminalNode TYPE() { return getToken(DB2Parser.TYPE, 0); }
		public TerminalNode AUTHENTICATION() { return getToken(DB2Parser.AUTHENTICATION, 0); }
		public TerminalNode AUTHID() { return getToken(DB2Parser.AUTHID, 0); }
		public TerminalNode BASED() { return getToken(DB2Parser.BASED, 0); }
		public TerminalNode CONTROL() { return getToken(DB2Parser.CONTROL, 0); }
		public TerminalNode UPON() { return getToken(DB2Parser.UPON, 0); }
		public TerminalNode VALUE() { return getToken(DB2Parser.VALUE, 0); }
		public TerminalNode SYSIBM() { return getToken(DB2Parser.SYSIBM, 0); }
		public TerminalNode SYSFUN() { return getToken(DB2Parser.SYSFUN, 0); }
		public TerminalNode SYSPROC() { return getToken(DB2Parser.SYSPROC, 0); }
		public TerminalNode SOURCE() { return getToken(DB2Parser.SOURCE, 0); }
		public TerminalNode STANDARD() { return getToken(DB2Parser.STANDARD, 0); }
		public TerminalNode SIMPLE() { return getToken(DB2Parser.SIMPLE, 0); }
		public TerminalNode SUMMARY() { return getToken(DB2Parser.SUMMARY, 0); }
		public TerminalNode MAINTAINED() { return getToken(DB2Parser.MAINTAINED, 0); }
		public TerminalNode MATERIALIZED() { return getToken(DB2Parser.MATERIALIZED, 0); }
		public TerminalNode ENCODING() { return getToken(DB2Parser.ENCODING, 0); }
		public TerminalNode LOCALE() { return getToken(DB2Parser.LOCALE, 0); }
		public TerminalNode PROGRAM() { return getToken(DB2Parser.PROGRAM, 0); }
		public TerminalNode PLAN() { return getToken(DB2Parser.PLAN, 0); }
		public TerminalNode QUERYNO() { return getToken(DB2Parser.QUERYNO, 0); }
		public TerminalNode OPTIMIZE() { return getToken(DB2Parser.OPTIMIZE, 0); }
		public TerminalNode OPTIMIZATION() { return getToken(DB2Parser.OPTIMIZATION, 0); }
		public TerminalNode ORGANIZATION() { return getToken(DB2Parser.ORGANIZATION, 0); }
		public TerminalNode PADDED() { return getToken(DB2Parser.PADDED, 0); }
		public TerminalNode CAPTURE() { return getToken(DB2Parser.CAPTURE, 0); }
		public TerminalNode CLONE() { return getToken(DB2Parser.CLONE, 0); }
		public TerminalNode STORES() { return getToken(DB2Parser.STORES, 0); }
		public TerminalNode STYLE() { return getToken(DB2Parser.STYLE, 0); }
		public TerminalNode ISOBID() { return getToken(DB2Parser.ISOBID, 0); }
		public TerminalNode PSID() { return getToken(DB2Parser.PSID, 0); }
		public TerminalNode OBID() { return getToken(DB2Parser.OBID, 0); }
		public TerminalNode DBINFO() { return getToken(DB2Parser.DBINFO, 0); }
		public TerminalNode SCRATCHPAD() { return getToken(DB2Parser.SCRATCHPAD, 0); }
		public TerminalNode FINAL() { return getToken(DB2Parser.FINAL, 0); }
		public TerminalNode VARIANT() { return getToken(DB2Parser.VARIANT, 0); }
		public TerminalNode LC_CTYPE() { return getToken(DB2Parser.LC_CTYPE, 0); }
		public TerminalNode JAR() { return getToken(DB2Parser.JAR, 0); }
		public TerminalNode COLLID() { return getToken(DB2Parser.COLLID, 0); }
		public TerminalNode ROWSET() { return getToken(DB2Parser.ROWSET, 0); }
		public TerminalNode PREVVAL() { return getToken(DB2Parser.PREVVAL, 0); }
		public TerminalNode NEXT() { return getToken(DB2Parser.NEXT, 0); }
		public TerminalNode LAST() { return getToken(DB2Parser.LAST, 0); }
		public TerminalNode FIRST() { return getToken(DB2Parser.FIRST, 0); }
		public NonReservedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonReserved; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).enterNonReserved(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DB2ParserListener ) ((DB2ParserListener)listener).exitNonReserved(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DB2ParserVisitor ) return ((DB2ParserVisitor<? extends T>)visitor).visitNonReserved(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonReservedContext nonReserved() throws RecognitionException {
		NonReservedContext _localctx = new NonReservedContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_nonReserved);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2871);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 9007217512546304L) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & 2415336775154335745L) != 0) || ((((_la - 139)) & ~0x3f) == 0 && ((1L << (_la - 139)) & 670979158262275L) != 0) || ((((_la - 213)) & ~0x3f) == 0 && ((1L << (_la - 213)) & 316728567742481L) != 0) || ((((_la - 278)) & ~0x3f) == 0 && ((1L << (_la - 278)) & -562949953554945L) != 0) || ((((_la - 342)) & ~0x3f) == 0 && ((1L << (_la - 342)) & -34359738369L) != 0) || ((((_la - 406)) & ~0x3f) == 0 && ((1L << (_la - 406)) & 57339L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return columnAttribute_sempred((ColumnAttributeContext)_localctx, predIndex);
		case 28:
			return indexKey_sempred((IndexKeyContext)_localctx, predIndex);
		case 29:
			return indexOption_sempred((IndexOptionContext)_localctx, predIndex);
		case 31:
			return tablespaceOption_sempred((TablespaceOptionContext)_localctx, predIndex);
		case 62:
			return compoundStatement_sempred((CompoundStatementContext)_localctx, predIndex);
		case 108:
			return tableReference_sempred((TableReferenceContext)_localctx, predIndex);
		case 111:
			return searchCondition_sempred((SearchConditionContext)_localctx, predIndex);
		case 114:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 119:
			return qualifiedName_sempred((QualifiedNameContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean columnAttribute_sempred(ColumnAttributeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return !startsDefaultValue(_localctx);
		case 1:
			return !startsDefaultValue(_localctx);
		}
		return true;
	}
	private boolean indexKey_sempred(IndexKeyContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return columnKeyAhead(_localctx);
		case 3:
			return !columnKeyAhead(_localctx);
		}
		return true;
	}
	private boolean indexOption_sempred(IndexOptionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return !startsNamedOption(_localctx);
		}
		return true;
	}
	private boolean tablespaceOption_sempred(TablespaceOptionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return !ahead(_localctx, USING);
		case 6:
			return !startsNamedOption(_localctx);
		}
		return true;
	}
	private boolean compoundStatement_sempred(CompoundStatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return closesBlock(_localctx);
		}
		return true;
	}
	private boolean tableReference_sempred(TableReferenceContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean searchCondition_sempred(SearchConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 9:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 15);
		case 11:
			return precpred(_ctx, 14);
		case 12:
			return precpred(_ctx, 13);
		case 13:
			return precpred(_ctx, 12);
		}
		return true;
	}
	private boolean qualifiedName_sempred(QualifiedNameContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return continuesName(_localctx);
		case 15:
			return !continuesName(_localctx);
		}
		return true;
	}

	private static final String _serializedATNSegment0 =
		"\u0004\u0001\u01be\u0b3a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007"+
		"\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007"+
		"\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007"+
		"\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007"+
		"\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007"+
		"\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007"+
		",\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u00021\u0007"+
		"1\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u00075\u00026\u0007"+
		"6\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007:\u0002;\u0007"+
		";\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007?\u0002@\u0007"+
		"@\u0002A\u0007A\u0002B\u0007B\u0002C\u0007C\u0002D\u0007D\u0002E\u0007"+
		"E\u0002F\u0007F\u0002G\u0007G\u0002H\u0007H\u0002I\u0007I\u0002J\u0007"+
		"J\u0002K\u0007K\u0002L\u0007L\u0002M\u0007M\u0002N\u0007N\u0002O\u0007"+
		"O\u0002P\u0007P\u0002Q\u0007Q\u0002R\u0007R\u0002S\u0007S\u0002T\u0007"+
		"T\u0002U\u0007U\u0002V\u0007V\u0002W\u0007W\u0002X\u0007X\u0002Y\u0007"+
		"Y\u0002Z\u0007Z\u0002[\u0007[\u0002\\\u0007\\\u0002]\u0007]\u0002^\u0007"+
		"^\u0002_\u0007_\u0002`\u0007`\u0002a\u0007a\u0002b\u0007b\u0002c\u0007"+
		"c\u0002d\u0007d\u0002e\u0007e\u0002f\u0007f\u0002g\u0007g\u0002h\u0007"+
		"h\u0002i\u0007i\u0002j\u0007j\u0002k\u0007k\u0002l\u0007l\u0002m\u0007"+
		"m\u0002n\u0007n\u0002o\u0007o\u0002p\u0007p\u0002q\u0007q\u0002r\u0007"+
		"r\u0002s\u0007s\u0002t\u0007t\u0002u\u0007u\u0002v\u0007v\u0002w\u0007"+
		"w\u0002x\u0007x\u0002y\u0007y\u0002z\u0007z\u0002{\u0007{\u0002|\u0007"+
		"|\u0002}\u0007}\u0002~\u0007~\u0001\u0000\u0005\u0000\u0100\b\u0000\n"+
		"\u0000\f\u0000\u0103\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0137"+
		"\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u0143"+
		"\b\u0004\n\u0004\f\u0004\u0146\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u014e\b\u0005\n\u0005"+
		"\f\u0005\u0151\t\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005\u0158\b\u0005\n\u0005\f\u0005\u015b\t\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u0162"+
		"\b\u0005\n\u0005\f\u0005\u0165\t\u0005\u0003\u0005\u0167\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0004\u0006\u016b\b\u0006\u000b\u0006\f\u0006\u016c"+
		"\u0001\u0006\u0001\u0006\u0003\u0006\u0171\b\u0006\u0001\u0006\u0001\u0006"+
		"\u0004\u0006\u0175\b\u0006\u000b\u0006\f\u0006\u0176\u0003\u0006\u0179"+
		"\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u017e\b\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0005\b\u0183\b\b\n\b\f\b\u0186\t\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u018e\b\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0003\t\u0194\b\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u019a"+
		"\b\t\u0001\t\u0003\t\u019d\b\t\u0001\t\u0001\t\u0003\t\u01a1\b\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0003\t\u01a7\b\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u01ac\b\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u01b5\b\t\u0001\t\u0001\t\u0001\t\u0003\t\u01ba\b\t\u0001\t\u0005\t"+
		"\u01bd\b\t\n\t\f\t\u01c0\t\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u01ca\b\t\n\t\f\t\u01cd\t\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u01d5\b\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0003\t\u01dc\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0003\n\u01e3\b\n\u0001\n\u0005\n\u01e6\b\n\n\n\f\n\u01e9\t\n\u0001"+
		"\n\u0001\n\u0003\n\u01ed\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u01fa\b\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u0200\b\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0005\u000b\u020c\b\u000b\n\u000b\f\u000b\u020f\t\u000b\u0003"+
		"\u000b\u0211\b\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0215\b\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003"+
		"\f\u0228\b\f\u0001\r\u0001\r\u0003\r\u022c\b\r\u0001\r\u0001\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0234\b\u000e\n\u000e"+
		"\f\u000e\u0237\t\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u023c\b\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u0242\b\u000e\u0001\u000e\u0005\u000e\u0245\b\u000e\n\u000e\f\u000e\u0248"+
		"\t\u000e\u0001\u000e\u0005\u000e\u024b\b\u000e\n\u000e\f\u000e\u024e\t"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0254"+
		"\b\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0258\b\u000e\n\u000e\f\u000e"+
		"\u025b\t\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0005\u000e\u0262\b\u000e\n\u000e\f\u000e\u0265\t\u000e\u0003\u000e\u0267"+
		"\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0271\b\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u0278\b\u000f\u0001"+
		"\u000f\u0003\u000f\u027b\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u0281\b\u000f\u0001\u0010\u0003\u0010\u0284\b\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u028a\b\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u0290\b\u0011"+
		"\n\u0011\f\u0011\u0293\t\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u029f\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0005\u0012\u02a5\b\u0012\n\u0012\f\u0012\u02a8\t\u0012\u0001\u0012"+
		"\u0001\u0012\u0003\u0012\u02ac\b\u0012\u0001\u0012\u0005\u0012\u02af\b"+
		"\u0012\n\u0012\f\u0012\u02b2\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u02bb\b\u0012"+
		"\n\u0012\f\u0012\u02be\t\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u02c2"+
		"\b\u0012\u0001\u0012\u0003\u0012\u02c5\b\u0012\u0001\u0012\u0003\u0012"+
		"\u02c8\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u02d3\b\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0003\u0012\u02db\b\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u02df\b"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u02eb"+
		"\b\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u02f0\b\u0012"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u02f6\b\u0013"+
		"\u0001\u0014\u0001\u0014\u0003\u0014\u02fa\b\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0005\u0015\u0300\b\u0015\n\u0015\f\u0015\u0303"+
		"\t\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u0309"+
		"\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0005\u0016\u030e\b\u0016"+
		"\n\u0016\f\u0016\u0311\t\u0016\u0001\u0016\u0005\u0016\u0314\b\u0016\n"+
		"\u0016\f\u0016\u0317\t\u0016\u0001\u0016\u0004\u0016\u031a\b\u0016\u000b"+
		"\u0016\f\u0016\u031b\u0001\u0016\u0005\u0016\u031f\b\u0016\n\u0016\f\u0016"+
		"\u0322\t\u0016\u0003\u0016\u0324\b\u0016\u0001\u0017\u0001\u0017\u0003"+
		"\u0017\u0328\b\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u032c\b\u0017"+
		"\u0001\u0017\u0001\u0017\u0003\u0017\u0330\b\u0017\u0001\u0017\u0001\u0017"+
		"\u0003\u0017\u0334\b\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0003\u0017\u033a\b\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u033e\b"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0344"+
		"\b\u0018\n\u0018\f\u0018\u0347\t\u0018\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0003\u0019\u034e\b\u0019\u0001\u001a\u0001\u001a"+
		"\u0005\u001a\u0352\b\u001a\n\u001a\f\u001a\u0355\t\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0005\u001a\u035f\b\u001a\n\u001a\f\u001a\u0362\t\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u0368\b\u001a\u0001\u001a"+
		"\u0005\u001a\u036b\b\u001a\n\u001a\f\u001a\u036e\t\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u0376"+
		"\b\u001b\u0001\u001b\u0001\u001b\u0003\u001b\u037a\b\u001b\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001c\u0003\u001c\u0380\b\u001c\u0001\u001c"+
		"\u0003\u001c\u0383\b\u001c\u0001\u001d\u0003\u001d\u0386\b\u001d\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u038a\b\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0003\u001d\u0396\b\u001d\u0001\u001d\u0005\u001d\u0399"+
		"\b\u001d\n\u001d\f\u001d\u039c\t\u001d\u0001\u001d\u0001\u001d\u0003\u001d"+
		"\u03a0\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u03a5\b"+
		"\u001d\u0001\u001d\u0003\u001d\u03a8\b\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001"+
		"\u001d\u0003\u001d\u03b9\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003"+
		"\u001d\u03be\b\u001d\u0001\u001e\u0001\u001e\u0003\u001e\u03c2\b\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0003\u001e\u03c8\b\u001e"+
		"\u0001\u001e\u0005\u001e\u03cb\b\u001e\n\u001e\f\u001e\u03ce\t\u001e\u0001"+
		"\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0003\u001f\u03d7\b\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0005\u001f\u03de\b\u001f\n\u001f\f\u001f\u03e1\t\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f"+
		"\u03e8\b\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0003\u001f\u0407\b\u001f\u0001 \u0001 \u0001 \u0001 \u0005 \u040d\b"+
		" \n \f \u0410\t \u0001 \u0001 \u0001!\u0001!\u0001!\u0001!\u0003!\u0418"+
		"\b!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0001!\u0003"+
		"!\u0423\b!\u0001\"\u0001\"\u0001\"\u0001\"\u0005\"\u0429\b\"\n\"\f\"\u042c"+
		"\t\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0001#\u0003#\u0434\b#\u0001"+
		"#\u0001#\u0001#\u0003#\u0439\b#\u0005#\u043b\b#\n#\f#\u043e\t#\u0001#"+
		"\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0003"+
		"#\u044a\b#\u0001$\u0001$\u0001$\u0001$\u0003$\u0450\b$\u0001$\u0001$\u0001"+
		"$\u0005$\u0455\b$\n$\f$\u0458\t$\u0001$\u0001$\u0001%\u0001%\u0003%\u045e"+
		"\b%\u0001%\u0001%\u0001%\u0001&\u0001&\u0003&\u0465\b&\u0001&\u0001&\u0001"+
		"&\u0001&\u0003&\u046b\b&\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001\'"+
		"\u0001\'\u0001\'\u0001\'\u0001\'\u0001(\u0001(\u0001(\u0001(\u0001(\u0003"+
		"(\u047c\b(\u0001(\u0005(\u047f\b(\n(\f(\u0482\t(\u0001(\u0001(\u0001)"+
		"\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0001)\u0003"+
		")\u0490\b)\u0001)\u0001)\u0001)\u0001)\u0003)\u0496\b)\u0001)\u0001)\u0001"+
		")\u0003)\u049b\b)\u0001)\u0001)\u0001)\u0001)\u0003)\u04a1\b)\u0001)\u0001"+
		")\u0001)\u0003)\u04a6\b)\u0001)\u0003)\u04a9\b)\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0001+\u0001+\u0001+\u0001+\u0001+\u0001+\u0003+\u04b6\b+\u0001"+
		"+\u0001+\u0001+\u0001+\u0003+\u04bc\b+\u0001+\u0001+\u0003+\u04c0\b+\u0001"+
		"+\u0001+\u0003+\u04c4\b+\u0001+\u0001+\u0001,\u0001,\u0003,\u04ca\b,\u0001"+
		",\u0001,\u0001,\u0001,\u0001,\u0001,\u0003,\u04d2\b,\u0001,\u0001,\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0003-\u04dc\b-\u0001-\u0001-\u0001"+
		".\u0001.\u0001.\u0001.\u0001.\u0001.\u0001.\u0003.\u04e7\b.\u0001.\u0001"+
		".\u0001.\u0001.\u0001.\u0001.\u0003.\u04ef\b.\u0001.\u0001.\u0001/\u0001"+
		"/\u0001/\u0001/\u0001/\u0001/\u0001/\u0003/\u04fa\b/\u0001/\u0001/\u0001"+
		"/\u0001/\u0003/\u0500\b/\u0001/\u0003/\u0503\b/\u0001/\u0001/\u0001/\u0001"+
		"/\u0001/\u0001/\u0001/\u0001/\u0003/\u050d\b/\u0001/\u0003/\u0510\b/\u0001"+
		"/\u0001/\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u00010\u0001"+
		"0\u00010\u00010\u00010\u00050\u0520\b0\n0\f0\u0523\t0\u00010\u00010\u0001"+
		"1\u00011\u00011\u00011\u00011\u00051\u052c\b1\n1\f1\u052f\t1\u00011\u0001"+
		"1\u00011\u00011\u00031\u0535\b1\u00011\u00011\u00011\u00031\u053a\b1\u0001"+
		"1\u00011\u00011\u00011\u00011\u00011\u00051\u0542\b1\n1\f1\u0545\t1\u0001"+
		"1\u00031\u0548\b1\u00012\u00012\u00012\u00032\u054d\b2\u00012\u00012\u0001"+
		"3\u00013\u00033\u0553\b3\u00013\u00013\u00033\u0557\b3\u00013\u00033\u055a"+
		"\b3\u00013\u00033\u055d\b3\u00014\u00014\u00044\u0561\b4\u000b4\f4\u0562"+
		"\u00014\u00014\u00034\u0567\b4\u00014\u00014\u00014\u00034\u056c\b4\u0001"+
		"5\u00015\u00015\u00015\u00015\u00015\u00015\u00055\u0575\b5\n5\f5\u0578"+
		"\t5\u00015\u00015\u00016\u00016\u00016\u00016\u00016\u00036\u0581\b6\u0001"+
		"6\u00016\u00016\u00016\u00036\u0587\b6\u00016\u00016\u00016\u00056\u058c"+
		"\b6\n6\f6\u058f\t6\u00016\u00016\u00016\u00056\u0594\b6\n6\f6\u0597\t"+
		"6\u00016\u00036\u059a\b6\u00016\u00016\u00036\u059e\b6\u00016\u00016\u0001"+
		"7\u00017\u00017\u00017\u00017\u00017\u00017\u00057\u05a9\b7\n7\f7\u05ac"+
		"\t7\u00037\u05ae\b7\u00037\u05b0\b7\u00018\u00018\u00018\u00038\u05b5"+
		"\b8\u00018\u00038\u05b8\b8\u00018\u00018\u00019\u00019\u00019\u00019\u0001"+
		"9\u00039\u05c1\b9\u0001:\u0001:\u0001:\u0003:\u05c6\b:\u0001:\u0001:\u0001"+
		":\u0001:\u0001:\u0001:\u0005:\u05ce\b:\n:\f:\u05d1\t:\u0003:\u05d3\b:"+
		"\u0001:\u0003:\u05d6\b:\u0001:\u0005:\u05d9\b:\n:\f:\u05dc\t:\u0001:\u0003"+
		":\u05df\b:\u0001:\u0001:\u0001;\u0001;\u0001;\u0003;\u05e6\b;\u0001;\u0001"+
		";\u0001;\u0001;\u0001;\u0001;\u0005;\u05ee\b;\n;\f;\u05f1\t;\u0003;\u05f3"+
		"\b;\u0001;\u0001;\u0005;\u05f7\b;\n;\f;\u05fa\t;\u0001;\u0003;\u05fd\b"+
		";\u0001;\u0001;\u0001<\u0003<\u0602\b<\u0001<\u0003<\u0605\b<\u0001<\u0001"+
		"<\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0005=\u0610\b=\n="+
		"\f=\u0613\t=\u0001=\u0001=\u0003=\u0617\b=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0003=\u061f\b=\u0003=\u0621\b=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0003=\u0629\b=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0001=\u0003=\u0636\b=\u0001=\u0001=\u0001"+
		"=\u0003=\u063b\b=\u0001=\u0001=\u0001=\u0001=\u0003=\u0641\b=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0003=\u0649\b=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0001=\u0003=\u065e\b=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0001=\u0001=\u0001=\u0003=\u0672\b=\u0001>\u0001>\u0003"+
		">\u0676\b>\u0001>\u0005>\u0679\b>\n>\f>\u067c\t>\u0001>\u0001>\u0001>"+
		"\u0003>\u0681\b>\u0001?\u0001?\u0001?\u0001?\u0003?\u0687\b?\u0001@\u0004"+
		"@\u068a\b@\u000b@\f@\u068b\u0001A\u0001A\u0001A\u0001A\u0004A\u0692\b"+
		"A\u000bA\fA\u0693\u0001A\u0001A\u0001B\u0001B\u0003B\u069a\bB\u0001B\u0001"+
		"B\u0003B\u069e\bB\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0003B\u06a6"+
		"\bB\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0003B\u06b0"+
		"\bB\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0003"+
		"B\u06bb\bB\u0001B\u0001B\u0003B\u06bf\bB\u0001B\u0001B\u0001B\u0001B\u0001"+
		"B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0003B\u06cd\bB\u0001"+
		"B\u0001B\u0001B\u0001B\u0003B\u06d3\bB\u0001B\u0001B\u0001B\u0001B\u0001"+
		"B\u0001B\u0001B\u0001B\u0001B\u0001B\u0001B\u0003B\u06e0\bB\u0001B\u0001"+
		"B\u0001B\u0001B\u0003B\u06e6\bB\u0001B\u0003B\u06e9\bB\u0001C\u0003C\u06ec"+
		"\bC\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0003C\u06f4\bC\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0003C\u0703\bC\u0001C\u0003C\u0706\bC\u0001C\u0001C\u0001C\u0001"+
		"C\u0001C\u0001C\u0001C\u0004C\u070f\bC\u000bC\fC\u0710\u0003C\u0713\b"+
		"C\u0001D\u0001D\u0003D\u0717\bD\u0001D\u0001D\u0001D\u0001D\u0003D\u071d"+
		"\bD\u0001D\u0005D\u0720\bD\nD\fD\u0723\tD\u0001D\u0001D\u0001E\u0001E"+
		"\u0001E\u0001E\u0001E\u0005E\u072c\bE\nE\fE\u072f\tE\u0003E\u0731\bE\u0001"+
		"E\u0001E\u0001F\u0001F\u0001F\u0001F\u0005F\u0739\bF\nF\fF\u073c\tF\u0001"+
		"F\u0001F\u0001G\u0001G\u0001G\u0001G\u0005G\u0744\bG\nG\fG\u0747\tG\u0001"+
		"G\u0001G\u0001H\u0001H\u0001H\u0001H\u0001H\u0001H\u0003H\u0751\bH\u0001"+
		"H\u0005H\u0754\bH\nH\fH\u0757\tH\u0001H\u0001H\u0001I\u0001I\u0001I\u0001"+
		"I\u0001I\u0001I\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0001J\u0005"+
		"J\u0768\bJ\nJ\fJ\u076b\tJ\u0003J\u076d\bJ\u0001J\u0003J\u0770\bJ\u0001"+
		"J\u0005J\u0773\bJ\nJ\fJ\u0776\tJ\u0001J\u0003J\u0779\bJ\u0001J\u0001J"+
		"\u0001K\u0001K\u0001K\u0001K\u0001K\u0001K\u0001K\u0005K\u0784\bK\nK\f"+
		"K\u0787\tK\u0003K\u0789\bK\u0001K\u0003K\u078c\bK\u0001K\u0005K\u078f"+
		"\bK\nK\fK\u0792\tK\u0001K\u0003K\u0795\bK\u0001K\u0001K\u0001L\u0001L"+
		"\u0001L\u0001L\u0003L\u079d\bL\u0001L\u0001L\u0001L\u0001M\u0001M\u0001"+
		"M\u0001M\u0001M\u0001M\u0001N\u0001N\u0001N\u0001N\u0001N\u0001N\u0001"+
		"O\u0001O\u0001O\u0001O\u0001O\u0005O\u07b3\bO\nO\fO\u07b6\tO\u0001O\u0001"+
		"O\u0001P\u0001P\u0001P\u0001P\u0001P\u0003P\u07bf\bP\u0001P\u0001P\u0001"+
		"P\u0003P\u07c4\bP\u0005P\u07c6\bP\nP\fP\u07c9\tP\u0001P\u0001P\u0003P"+
		"\u07cd\bP\u0001Q\u0001Q\u0001Q\u0003Q\u07d2\bQ\u0001Q\u0001Q\u0001R\u0001"+
		"R\u0001R\u0003R\u07d9\bR\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001"+
		"R\u0001R\u0001R\u0001R\u0001R\u0003R\u07e6\bR\u0001R\u0001R\u0001R\u0001"+
		"R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001"+
		"R\u0001R\u0001R\u0005R\u07f8\bR\nR\fR\u07fb\tR\u0003R\u07fd\bR\u0001R"+
		"\u0003R\u0800\bR\u0001R\u0001R\u0001R\u0001R\u0003R\u0806\bR\u0001R\u0001"+
		"R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001R\u0001"+
		"R\u0001R\u0001R\u0003R\u0816\bR\u0001S\u0001S\u0001S\u0001S\u0001S\u0001"+
		"S\u0001S\u0001S\u0001S\u0005S\u0821\bS\nS\fS\u0824\tS\u0001S\u0001S\u0001"+
		"S\u0001S\u0001S\u0001S\u0005S\u082c\bS\nS\fS\u082f\tS\u0001S\u0001S\u0003"+
		"S\u0833\bS\u0001S\u0001S\u0001S\u0001S\u0001S\u0005S\u083a\bS\nS\fS\u083d"+
		"\tS\u0001S\u0001S\u0001S\u0003S\u0842\bS\u0001S\u0001S\u0001S\u0001S\u0001"+
		"S\u0001S\u0005S\u084a\bS\nS\fS\u084d\tS\u0001S\u0001S\u0001S\u0001S\u0005"+
		"S\u0853\bS\nS\fS\u0856\tS\u0001S\u0001S\u0001S\u0003S\u085b\bS\u0001S"+
		"\u0001S\u0003S\u085f\bS\u0001T\u0001T\u0001T\u0001T\u0001T\u0001T\u0001"+
		"T\u0001T\u0001T\u0005T\u086a\bT\nT\fT\u086d\tT\u0001T\u0001T\u0001T\u0001"+
		"T\u0001T\u0001T\u0005T\u0875\bT\nT\fT\u0878\tT\u0001T\u0001T\u0003T\u087c"+
		"\bT\u0001T\u0001T\u0001T\u0001T\u0001T\u0005T\u0883\bT\nT\fT\u0886\tT"+
		"\u0001T\u0001T\u0003T\u088a\bT\u0001T\u0003T\u088d\bT\u0001T\u0001T\u0001"+
		"T\u0001T\u0001T\u0001T\u0005T\u0895\bT\nT\fT\u0898\tT\u0001T\u0001T\u0001"+
		"T\u0001T\u0005T\u089e\bT\nT\fT\u08a1\tT\u0001T\u0001T\u0003T\u08a5\bT"+
		"\u0001T\u0001T\u0003T\u08a9\bT\u0001U\u0001U\u0003U\u08ad\bU\u0001U\u0001"+
		"U\u0001U\u0001U\u0003U\u08b3\bU\u0001U\u0001U\u0001U\u0003U\u08b8\bU\u0001"+
		"U\u0001U\u0001U\u0001U\u0001U\u0001U\u0001U\u0001U\u0001U\u0001U\u0001"+
		"U\u0001U\u0003U\u08c6\bU\u0001V\u0001V\u0003V\u08ca\bV\u0001V\u0001V\u0001"+
		"V\u0001V\u0001V\u0001V\u0001V\u0003V\u08d3\bV\u0001V\u0001V\u0001V\u0001"+
		"V\u0001V\u0001V\u0001V\u0001V\u0003V\u08dd\bV\u0001W\u0001W\u0001W\u0005"+
		"W\u08e2\bW\nW\fW\u08e5\tW\u0001X\u0001X\u0001X\u0003X\u08ea\bX\u0001X"+
		"\u0001X\u0001X\u0001X\u0001X\u0003X\u08f1\bX\u0001Y\u0001Y\u0001Y\u0001"+
		"Y\u0001Y\u0001Y\u0001Y\u0001Z\u0003Z\u08fb\bZ\u0001Z\u0001Z\u0001Z\u0001"+
		"Z\u0001Z\u0001Z\u0001Z\u0003Z\u0904\bZ\u0001Z\u0001Z\u0001Z\u0001Z\u0003"+
		"Z\u090a\bZ\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001[\u0001\\\u0003"+
		"\\\u0914\b\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0001\\\u0003\\\u091c"+
		"\b\\\u0001]\u0001]\u0003]\u0920\b]\u0001]\u0001]\u0001]\u0001]\u0001]"+
		"\u0001]\u0001]\u0001]\u0001]\u0001]\u0001]\u0001]\u0003]\u092e\b]\u0001"+
		"^\u0001^\u0001^\u0003^\u0933\b^\u0001^\u0001^\u0001^\u0001_\u0003_\u0939"+
		"\b_\u0001_\u0004_\u093c\b_\u000b_\f_\u093d\u0001_\u0001_\u0001_\u0001"+
		"_\u0001_\u0001_\u0003_\u0946\b_\u0001`\u0001`\u0003`\u094a\b`\u0001`\u0001"+
		"`\u0001a\u0001a\u0003a\u0950\ba\u0001a\u0001a\u0001a\u0003a\u0955\ba\u0003"+
		"a\u0957\ba\u0001a\u0001a\u0001b\u0001b\u0001b\u0001b\u0001b\u0001b\u0003"+
		"b\u0961\bb\u0001b\u0001b\u0001c\u0001c\u0003c\u0967\bc\u0001c\u0001c\u0001"+
		"c\u0001c\u0001d\u0001d\u0001d\u0001d\u0001d\u0003d\u0972\bd\u0001d\u0001"+
		"d\u0001d\u0001d\u0001d\u0001e\u0001e\u0001e\u0001e\u0003e\u097d\be\u0001"+
		"e\u0001e\u0001e\u0001e\u0005e\u0983\be\ne\fe\u0986\te\u0001e\u0003e\u0989"+
		"\be\u0001e\u0001e\u0001f\u0001f\u0001f\u0001f\u0005f\u0991\bf\nf\ff\u0994"+
		"\tf\u0001f\u0001f\u0001f\u0003f\u0999\bf\u0001g\u0001g\u0001g\u0001g\u0005"+
		"g\u099f\bg\ng\fg\u09a2\tg\u0001h\u0001h\u0003h\u09a6\bh\u0001i\u0001i"+
		"\u0003i\u09aa\bi\u0001i\u0001i\u0001i\u0001i\u0001i\u0005i\u09b1\bi\n"+
		"i\fi\u09b4\ti\u0001i\u0001i\u0003i\u09b8\bi\u0001i\u0001i\u0001i\u0001"+
		"i\u0001i\u0005i\u09bf\bi\ni\fi\u09c2\ti\u0003i\u09c4\bi\u0001i\u0001i"+
		"\u0003i\u09c8\bi\u0001i\u0001i\u0001i\u0001i\u0001i\u0005i\u09cf\bi\n"+
		"i\fi\u09d2\ti\u0003i\u09d4\bi\u0001i\u0001i\u0001i\u0003i\u09d9\bi\u0001"+
		"i\u0001i\u0003i\u09dd\bi\u0001i\u0001i\u0001i\u0001i\u0003i\u09e3\bi\u0001"+
		"j\u0001j\u0001j\u0001j\u0005j\u09e9\bj\nj\fj\u09ec\tj\u0003j\u09ee\bj"+
		"\u0001k\u0001k\u0003k\u09f2\bk\u0001k\u0003k\u09f5\bk\u0001k\u0001k\u0001"+
		"k\u0001k\u0003k\u09fb\bk\u0001l\u0001l\u0001l\u0003l\u0a00\bl\u0001l\u0003"+
		"l\u0a03\bl\u0001l\u0001l\u0001l\u0001l\u0003l\u0a09\bl\u0001l\u0003l\u0a0c"+
		"\bl\u0003l\u0a0e\bl\u0001l\u0001l\u0003l\u0a12\bl\u0001l\u0001l\u0001"+
		"l\u0001l\u0001l\u0005l\u0a19\bl\nl\fl\u0a1c\tl\u0001m\u0001m\u0001m\u0003"+
		"m\u0a21\bm\u0003m\u0a23\bm\u0001n\u0001n\u0003n\u0a27\bn\u0001o\u0001"+
		"o\u0001o\u0001o\u0001o\u0001o\u0001o\u0001o\u0003o\u0a31\bo\u0001o\u0001"+
		"o\u0001o\u0005o\u0a36\bo\no\fo\u0a39\to\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0001p\u0003p\u0a41\bp\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0003p\u0a4a\bp\u0001p\u0001p\u0001p\u0001p\u0001p\u0005p\u0a51\bp\n"+
		"p\fp\u0a54\tp\u0001p\u0003p\u0a57\bp\u0001p\u0001p\u0001p\u0001p\u0003"+
		"p\u0a5d\bp\u0001p\u0001p\u0001p\u0001p\u0003p\u0a63\bp\u0001p\u0001p\u0001"+
		"p\u0003p\u0a68\bp\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001p\u0001"+
		"p\u0003p\u0a72\bp\u0001q\u0001q\u0001r\u0001r\u0001r\u0001r\u0001r\u0001"+
		"r\u0001r\u0001r\u0001r\u0004r\u0a7f\br\u000br\fr\u0a80\u0001r\u0001r\u0003"+
		"r\u0a85\br\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001"+
		"r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0005r\u0a96\br\nr\fr\u0a99"+
		"\tr\u0003r\u0a9b\br\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001"+
		"r\u0001r\u0001r\u0001r\u0003r\u0aa8\br\u0001r\u0001r\u0001r\u0001r\u0001"+
		"r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0001r\u0005r\u0ab6\br\nr"+
		"\fr\u0ab9\tr\u0001s\u0001s\u0001s\u0001s\u0001s\u0001s\u0001s\u0001s\u0001"+
		"s\u0001s\u0003s\u0ac5\bs\u0001t\u0001t\u0004t\u0ac9\bt\u000bt\ft\u0aca"+
		"\u0001t\u0001t\u0003t\u0acf\bt\u0001u\u0001u\u0001u\u0001u\u0003u\u0ad5"+
		"\bu\u0001v\u0003v\u0ad8\bv\u0001v\u0001v\u0001w\u0001w\u0001w\u0001w\u0005"+
		"w\u0ae0\bw\nw\fw\u0ae3\tw\u0001w\u0001w\u0001x\u0001x\u0001x\u0001x\u0003"+
		"x\u0aeb\bx\u0001y\u0001y\u0001y\u0001y\u0001y\u0003y\u0af2\by\u0001y\u0003"+
		"y\u0af5\by\u0001y\u0005y\u0af8\by\ny\fy\u0afb\ty\u0001z\u0001z\u0003z"+
		"\u0aff\bz\u0001z\u0001z\u0003z\u0b03\bz\u0001z\u0001z\u0001z\u0001z\u0003"+
		"z\u0b09\bz\u0001z\u0003z\u0b0c\bz\u0001{\u0001{\u0001{\u0001{\u0001{\u0001"+
		"{\u0001{\u0001{\u0001{\u0001{\u0001{\u0003{\u0b19\b{\u0001|\u0001|\u0001"+
		"|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001"+
		"|\u0003|\u0b28\b|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0001|\u0003"+
		"|\u0b31\b|\u0003|\u0b33\b|\u0001}\u0003}\u0b36\b}\u0001~\u0001~\u0001"+
		"~\u0000\u0003\u00d8\u00de\u00e4\u007f\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c"+
		"\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0\u00a2\u00a4"+
		"\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8\u00ba\u00bc"+
		"\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0\u00d2\u00d4"+
		"\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2\u00e4\u00e6\u00e8\u00ea\u00ec"+
		"\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa\u00fc\u00000\u0002\u0000\u0145"+
		"\u0145\u0152\u0152\u0003\u0000\u0129\u0129\u0167\u0167\u0185\u0185\u0002"+
		"\u0000\u0178\u0178\u018a\u018a\u0003\u0000\u0006\u0006\u0096\u0096\u0130"+
		"\u0130\u0002\u0000\u0096\u0096\u0130\u0130\u0002\u0000\u0095\u0095\u01a5"+
		"\u01a5\u0002\u0000\u0122\u0122\u013e\u013e\u0002\u0000ll\u0146\u0146\u0003"+
		"\u0000\u0122\u0122\u013e\u013e\u0177\u0177\u0002\u0000\u0144\u0144\u0151"+
		"\u0151\u0002\u0000\u0159\u0159\u015d\u015d\u0005\u0000\u000b\u000b\u00d4"+
		"\u00d4\u00f3\u00f4\u015d\u015d\u0171\u0171\u0002\u0000\u00f2\u00f2\u01bc"+
		"\u01bc\u0002\u0000\u011b\u011b\u017c\u017c\u0002\u0000\u0017\u0017\u0082"+
		"\u0082\u0002\u0000\u00de\u00de\u00f3\u00f3\u0002\u0000\u000f\u000f\u0128"+
		"\u0128\u0002\u0000;;\u0140\u0140\u0002\u0000\u010e\u010e\u01a1\u01a1\u0002"+
		"\u0000\u009d\u009d\u016a\u016c\u0002\u0000\u00d4\u00d4\u0191\u0191\u0003"+
		"\u0000kkpp\u00a5\u00a5\u0002\u000077nn\u0003\u0000\b\b;<\u0140\u0140\u0002"+
		"\u0000\u015f\u015f\u0193\u0193\u0003\u0000\u00ff\u00ff\u0138\u0138\u013d"+
		"\u013d\u0006\u0000\u0018\u0018XXii\u008a\u008a\u00c3\u00c3\u010d\u010d"+
		"\u0002\u0000\u0011\u0011GG\u0002\u0000\u00c4\u00c4\u012d\u012d\u0002\u0000"+
		"WW\u01bc\u01bc\u0002\u0000\u0004\u0004\u017d\u017d\u0002\u0000\\\\\u00b7"+
		"\u00b7\u0002\u0000\u0160\u0160\u0174\u0174\u0002\u0000kk\u009e\u009e\t"+
		"\u0000mm\u00a7\u00a7\u00ca\u00ca\u00de\u00de\u00f3\u00f3\u00f7\u00f7\u0104"+
		"\u0104\u0107\u0107\u011f\u011f\u0002\u0000\u00f3\u00f3\u011f\u011f\u0002"+
		"\u0000\u0146\u0146\u0189\u0189\u0003\u0000KKss\u00fb\u00fb\u0002\u0000"+
		"\u0006\u0006==\u0002\u0000\u00d4\u00d4\u0184\u0184\u0003\u0000[[\u0080"+
		"\u0080\u00c9\u00c9\u0002\u0000\n\n\u00a2\u00a2\u0001\u0000\u01b1\u01b6"+
		"\u0001\u0000\u01ad\u01ae\u0002\u0000\u01ac\u01ac\u01af\u01af\u0001\u0000"+
		"\u019d\u019e\u0004\u0000\u0006\u0006\u0096\u0096\u00f2\u00f2\u012f\u012f"+
		"\'\u0000\u0016\u0016\u001e\u001e\"\"55FFVWvvxx}~\u0083\u0083\u008b\u008c"+
		"\u0094\u0094\u0099\u0099\u009b\u009b\u00a0\u00a1\u00a4\u00a4\u00aa\u00aa"+
		"\u00b1\u00b1\u00b4\u00b4\u00b8\u00b9\u00bc\u00bc\u00d5\u00d5\u00d9\u00d9"+
		"\u00e3\u00e3\u00e5\u00e5\u00e7\u00e7\u00eb\u00ed\u00ef\u00f1\u00f9\u00f9"+
		"\u0102\u0102\u0105\u0105\u0116\u011e\u0120\u0120\u0122\u0126\u0128\u0146"+
		"\u0148\u0178\u017a\u0197\u0199\u01a2\u01a4\u01a5\u0d3b\u0000\u0101\u0001"+
		"\u0000\u0000\u0000\u0002\u0136\u0001\u0000\u0000\u0000\u0004\u0138\u0001"+
		"\u0000\u0000\u0000\u0006\u013b\u0001\u0000\u0000\u0000\b\u013d\u0001\u0000"+
		"\u0000\u0000\n\u0166\u0001\u0000\u0000\u0000\f\u0178\u0001\u0000\u0000"+
		"\u0000\u000e\u017d\u0001\u0000\u0000\u0000\u0010\u017f\u0001\u0000\u0000"+
		"\u0000\u0012\u01db\u0001\u0000\u0000\u0000\u0014\u01ff\u0001\u0000\u0000"+
		"\u0000\u0016\u0214\u0001\u0000\u0000\u0000\u0018\u0227\u0001\u0000\u0000"+
		"\u0000\u001a\u022b\u0001\u0000\u0000\u0000\u001c\u0266\u0001\u0000\u0000"+
		"\u0000\u001e\u0280\u0001\u0000\u0000\u0000 \u0289\u0001\u0000\u0000\u0000"+
		"\"\u028b\u0001\u0000\u0000\u0000$\u02ef\u0001\u0000\u0000\u0000&\u02f1"+
		"\u0001\u0000\u0000\u0000(\u02f7\u0001\u0000\u0000\u0000*\u0308\u0001\u0000"+
		"\u0000\u0000,\u0323\u0001\u0000\u0000\u0000.\u033d\u0001\u0000\u0000\u0000"+
		"0\u033f\u0001\u0000\u0000\u00002\u034d\u0001\u0000\u0000\u00004\u034f"+
		"\u0001\u0000\u0000\u00006\u0379\u0001\u0000\u0000\u00008\u037f\u0001\u0000"+
		"\u0000\u0000:\u03bd\u0001\u0000\u0000\u0000<\u03bf\u0001\u0000\u0000\u0000"+
		">\u0406\u0001\u0000\u0000\u0000@\u0408\u0001\u0000\u0000\u0000B\u0422"+
		"\u0001\u0000\u0000\u0000D\u0424\u0001\u0000\u0000\u0000F\u0449\u0001\u0000"+
		"\u0000\u0000H\u044b\u0001\u0000\u0000\u0000J\u045b\u0001\u0000\u0000\u0000"+
		"L\u0462\u0001\u0000\u0000\u0000N\u046f\u0001\u0000\u0000\u0000P\u0476"+
		"\u0001\u0000\u0000\u0000R\u04a8\u0001\u0000\u0000\u0000T\u04aa\u0001\u0000"+
		"\u0000\u0000V\u04af\u0001\u0000\u0000\u0000X\u04c7\u0001\u0000\u0000\u0000"+
		"Z\u04d5\u0001\u0000\u0000\u0000\\\u04df\u0001\u0000\u0000\u0000^\u04f2"+
		"\u0001\u0000\u0000\u0000`\u0513\u0001\u0000\u0000\u0000b\u0547\u0001\u0000"+
		"\u0000\u0000d\u0549\u0001\u0000\u0000\u0000f\u0552\u0001\u0000\u0000\u0000"+
		"h\u056b\u0001\u0000\u0000\u0000j\u056d\u0001\u0000\u0000\u0000l\u057b"+
		"\u0001\u0000\u0000\u0000n\u05af\u0001\u0000\u0000\u0000p\u05b1\u0001\u0000"+
		"\u0000\u0000r\u05bb\u0001\u0000\u0000\u0000t\u05c2\u0001\u0000\u0000\u0000"+
		"v\u05e2\u0001\u0000\u0000\u0000x\u0601\u0001\u0000\u0000\u0000z\u0671"+
		"\u0001\u0000\u0000\u0000|\u0673\u0001\u0000\u0000\u0000~\u0686\u0001\u0000"+
		"\u0000\u0000\u0080\u0689\u0001\u0000\u0000\u0000\u0082\u068d\u0001\u0000"+
		"\u0000\u0000\u0084\u06e8\u0001\u0000\u0000\u0000\u0086\u0712\u0001\u0000"+
		"\u0000\u0000\u0088\u0714\u0001\u0000\u0000\u0000\u008a\u0726\u0001\u0000"+
		"\u0000\u0000\u008c\u0734\u0001\u0000\u0000\u0000\u008e\u073f\u0001\u0000"+
		"\u0000\u0000\u0090\u074a\u0001\u0000\u0000\u0000\u0092\u075a\u0001\u0000"+
		"\u0000\u0000\u0094\u0760\u0001\u0000\u0000\u0000\u0096\u077c\u0001\u0000"+
		"\u0000\u0000\u0098\u0798\u0001\u0000\u0000\u0000\u009a\u07a1\u0001\u0000"+
		"\u0000\u0000\u009c\u07a7\u0001\u0000\u0000\u0000\u009e\u07ad\u0001\u0000"+
		"\u0000\u0000\u00a0\u07cc\u0001\u0000\u0000\u0000\u00a2\u07ce\u0001\u0000"+
		"\u0000\u0000\u00a4\u0815\u0001\u0000\u0000\u0000\u00a6\u085e\u0001\u0000"+
		"\u0000\u0000\u00a8\u08a8\u0001\u0000\u0000\u0000\u00aa\u08c5\u0001\u0000"+
		"\u0000\u0000\u00ac\u08dc\u0001\u0000\u0000\u0000\u00ae\u08de\u0001\u0000"+
		"\u0000\u0000\u00b0\u08f0\u0001\u0000\u0000\u0000\u00b2\u08f2\u0001\u0000"+
		"\u0000\u0000\u00b4\u0909\u0001\u0000\u0000\u0000\u00b6\u090b\u0001\u0000"+
		"\u0000\u0000\u00b8\u091b\u0001\u0000\u0000\u0000\u00ba\u092d\u0001\u0000"+
		"\u0000\u0000\u00bc\u092f\u0001\u0000\u0000\u0000\u00be\u0945\u0001\u0000"+
		"\u0000\u0000\u00c0\u0947\u0001\u0000\u0000\u0000\u00c2\u094d\u0001\u0000"+
		"\u0000\u0000\u00c4\u095a\u0001\u0000\u0000\u0000\u00c6\u0964\u0001\u0000"+
		"\u0000\u0000\u00c8\u096c\u0001\u0000\u0000\u0000\u00ca\u0978\u0001\u0000"+
		"\u0000\u0000\u00cc\u0998\u0001\u0000\u0000\u0000\u00ce\u099a\u0001\u0000"+
		"\u0000\u0000\u00d0\u09a3\u0001\u0000\u0000\u0000\u00d2\u09e2\u0001\u0000"+
		"\u0000\u0000\u00d4\u09ed\u0001\u0000\u0000\u0000\u00d6\u09fa\u0001\u0000"+
		"\u0000\u0000\u00d8\u0a0d\u0001\u0000\u0000\u0000\u00da\u0a22\u0001\u0000"+
		"\u0000\u0000\u00dc\u0a24\u0001\u0000\u0000\u0000\u00de\u0a30\u0001\u0000"+
		"\u0000\u0000\u00e0\u0a71\u0001\u0000\u0000\u0000\u00e2\u0a73\u0001\u0000"+
		"\u0000\u0000\u00e4\u0aa7\u0001\u0000\u0000\u0000\u00e6\u0ac4\u0001\u0000"+
		"\u0000\u0000\u00e8\u0ace\u0001\u0000\u0000\u0000\u00ea\u0ad4\u0001\u0000"+
		"\u0000\u0000\u00ec\u0ad7\u0001\u0000\u0000\u0000\u00ee\u0adb\u0001\u0000"+
		"\u0000\u0000\u00f0\u0aea\u0001\u0000\u0000\u0000\u00f2\u0aec\u0001\u0000"+
		"\u0000\u0000\u00f4\u0b0b\u0001\u0000\u0000\u0000\u00f6\u0b18\u0001\u0000"+
		"\u0000\u0000\u00f8\u0b32\u0001\u0000\u0000\u0000\u00fa\u0b35\u0001\u0000"+
		"\u0000\u0000\u00fc\u0b37\u0001\u0000\u0000\u0000\u00fe\u0100\u0003\u0002"+
		"\u0001\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000\u0100\u0103\u0001\u0000"+
		"\u0000\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000"+
		"\u0000\u0000\u0102\u0104\u0001\u0000\u0000\u0000\u0103\u0101\u0001\u0000"+
		"\u0000\u0000\u0104\u0105\u0005\u0000\u0000\u0001\u0105\u0001\u0001\u0000"+
		"\u0000\u0000\u0106\u0137\u0003\b\u0004\u0000\u0107\u0137\u00034\u001a"+
		"\u0000\u0108\u0137\u0003<\u001e\u0000\u0109\u0137\u0003@ \u0000\u010a"+
		"\u0137\u0003D\"\u0000\u010b\u0137\u0003H$\u0000\u010c\u0137\u0003L&\u0000"+
		"\u010d\u0137\u0003N\'\u0000\u010e\u0137\u0003P(\u0000\u010f\u0137\u0003"+
		"T*\u0000\u0110\u0137\u0003V+\u0000\u0111\u0137\u0003X,\u0000\u0112\u0137"+
		"\u0003Z-\u0000\u0113\u0137\u0003\\.\u0000\u0114\u0137\u0003^/\u0000\u0115"+
		"\u0137\u0003`0\u0000\u0116\u0137\u0003l6\u0000\u0117\u0137\u0003t:\u0000"+
		"\u0118\u0137\u0003v;\u0000\u0119\u0137\u0003j5\u0000\u011a\u0137\u0003"+
		"\u0082A\u0000\u011b\u0137\u0003\u0088D\u0000\u011c\u0137\u0003\u008aE"+
		"\u0000\u011d\u0137\u0003\u008cF\u0000\u011e\u0137\u0003\u008eG\u0000\u011f"+
		"\u0137\u0003\u0090H\u0000\u0120\u0137\u0003\u0092I\u0000\u0121\u0137\u0003"+
		"\u0094J\u0000\u0122\u0137\u0003\u0096K\u0000\u0123\u0137\u0003\u0098L"+
		"\u0000\u0124\u0137\u0003\u009aM\u0000\u0125\u0137\u0003\u009cN\u0000\u0126"+
		"\u0137\u0003\u009eO\u0000\u0127\u0137\u0003\u00a2Q\u0000\u0128\u0137\u0003"+
		"\u00a6S\u0000\u0129\u0137\u0003\u00a8T\u0000\u012a\u0137\u0003\u00b2Y"+
		"\u0000\u012b\u0137\u0003\u00b6[\u0000\u012c\u0137\u0003\u00ba]\u0000\u012d"+
		"\u0137\u0003\u00bc^\u0000\u012e\u0137\u0003\u00c0`\u0000\u012f\u0137\u0003"+
		"\u00c2a\u0000\u0130\u0137\u0003\u00c4b\u0000\u0131\u0137\u0003\u00c6c"+
		"\u0000\u0132\u0137\u0003\u00c8d\u0000\u0133\u0137\u0003\u00cae\u0000\u0134"+
		"\u0137\u0003\u0004\u0002\u0000\u0135\u0137\u0003\u0006\u0003\u0000\u0136"+
		"\u0106\u0001\u0000\u0000\u0000\u0136\u0107\u0001\u0000\u0000\u0000\u0136"+
		"\u0108\u0001\u0000\u0000\u0000\u0136\u0109\u0001\u0000\u0000\u0000\u0136"+
		"\u010a\u0001\u0000\u0000\u0000\u0136\u010b\u0001\u0000\u0000\u0000\u0136"+
		"\u010c\u0001\u0000\u0000\u0000\u0136\u010d\u0001\u0000\u0000\u0000\u0136"+
		"\u010e\u0001\u0000\u0000\u0000\u0136\u010f\u0001\u0000\u0000\u0000\u0136"+
		"\u0110\u0001\u0000\u0000\u0000\u0136\u0111\u0001\u0000\u0000\u0000\u0136"+
		"\u0112\u0001\u0000\u0000\u0000\u0136\u0113\u0001\u0000\u0000\u0000\u0136"+
		"\u0114\u0001\u0000\u0000\u0000\u0136\u0115\u0001\u0000\u0000\u0000\u0136"+
		"\u0116\u0001\u0000\u0000\u0000\u0136\u0117\u0001\u0000\u0000\u0000\u0136"+
		"\u0118\u0001\u0000\u0000\u0000\u0136\u0119\u0001\u0000\u0000\u0000\u0136"+
		"\u011a\u0001\u0000\u0000\u0000\u0136\u011b\u0001\u0000\u0000\u0000\u0136"+
		"\u011c\u0001\u0000\u0000\u0000\u0136\u011d\u0001\u0000\u0000\u0000\u0136"+
		"\u011e\u0001\u0000\u0000\u0000\u0136\u011f\u0001\u0000\u0000\u0000\u0136"+
		"\u0120\u0001\u0000\u0000\u0000\u0136\u0121\u0001\u0000\u0000\u0000\u0136"+
		"\u0122\u0001\u0000\u0000\u0000\u0136\u0123\u0001\u0000\u0000\u0000\u0136"+
		"\u0124\u0001\u0000\u0000\u0000\u0136\u0125\u0001\u0000\u0000\u0000\u0136"+
		"\u0126\u0001\u0000\u0000\u0000\u0136\u0127\u0001\u0000\u0000\u0000\u0136"+
		"\u0128\u0001\u0000\u0000\u0000\u0136\u0129\u0001\u0000\u0000\u0000\u0136"+
		"\u012a\u0001\u0000\u0000\u0000\u0136\u012b\u0001\u0000\u0000\u0000\u0136"+
		"\u012c\u0001\u0000\u0000\u0000\u0136\u012d\u0001\u0000\u0000\u0000\u0136"+
		"\u012e\u0001\u0000\u0000\u0000\u0136\u012f\u0001\u0000\u0000\u0000\u0136"+
		"\u0130\u0001\u0000\u0000\u0000\u0136\u0131\u0001\u0000\u0000\u0000\u0136"+
		"\u0132\u0001\u0000\u0000\u0000\u0136\u0133\u0001\u0000\u0000\u0000\u0136"+
		"\u0134\u0001\u0000\u0000\u0000\u0136\u0135\u0001\u0000\u0000\u0000\u0137"+
		"\u0003\u0001\u0000\u0000\u0000\u0138\u0139\u0003\u00ceg\u0000\u0139\u013a"+
		"\u0003\u00fa}\u0000\u013a\u0005\u0001\u0000\u0000\u0000\u013b\u013c\u0005"+
		"\u01a9\u0000\u0000\u013c\u0007\u0001\u0000\u0000\u0000\u013d\u013e\u0005"+
		"-\u0000\u0000\u013e\u013f\u0005\u00f3\u0000\u0000\u013f\u0140\u0003\u00ee"+
		"w\u0000\u0140\u0144\u0003\n\u0005\u0000\u0141\u0143\u0003$\u0012\u0000"+
		"\u0142\u0141\u0001\u0000\u0000\u0000\u0143\u0146\u0001\u0000\u0000\u0000"+
		"\u0144\u0142\u0001\u0000\u0000\u0000\u0144\u0145\u0001\u0000\u0000\u0000"+
		"\u0145\u0147\u0001\u0000\u0000\u0000\u0146\u0144\u0001\u0000\u0000\u0000"+
		"\u0147\u0148\u0003\u00fa}\u0000\u0148\t\u0001\u0000\u0000\u0000\u0149"+
		"\u014a\u0005\u01a6\u0000\u0000\u014a\u014f\u0003\u000e\u0007\u0000\u014b"+
		"\u014c\u0005\u01a8\u0000\u0000\u014c\u014e\u0003\u000e\u0007\u0000\u014d"+
		"\u014b\u0001\u0000\u0000\u0000\u014e\u0151\u0001\u0000\u0000\u0000\u014f"+
		"\u014d\u0001\u0000\u0000\u0000\u014f\u0150\u0001\u0000\u0000\u0000\u0150"+
		"\u0152\u0001\u0000\u0000\u0000\u0151\u014f\u0001\u0000\u0000\u0000\u0152"+
		"\u0153\u0005\u01a7\u0000\u0000\u0153\u0167\u0001\u0000\u0000\u0000\u0154"+
		"\u0155\u0005\u0081\u0000\u0000\u0155\u0159\u0003\u00eew\u0000\u0156\u0158"+
		"\u0003\f\u0006\u0000\u0157\u0156\u0001\u0000\u0000\u0000\u0158\u015b\u0001"+
		"\u0000\u0000\u0000\u0159\u0157\u0001\u0000\u0000\u0000\u0159\u015a\u0001"+
		"\u0000\u0000\u0000\u015a\u0167\u0001\u0000\u0000\u0000\u015b\u0159\u0001"+
		"\u0000\u0000\u0000\u015c\u015d\u0005\f\u0000\u0000\u015d\u015e\u0005\u01a6"+
		"\u0000\u0000\u015e\u015f\u0003\u00ceg\u0000\u015f\u0163\u0005\u01a7\u0000"+
		"\u0000\u0160\u0162\u0003\f\u0006\u0000\u0161\u0160\u0001\u0000\u0000\u0000"+
		"\u0162\u0165\u0001\u0000\u0000\u0000\u0163\u0161\u0001\u0000\u0000\u0000"+
		"\u0163\u0164\u0001\u0000\u0000\u0000\u0164\u0167\u0001\u0000\u0000\u0000"+
		"\u0165\u0163\u0001\u0000\u0000\u0000\u0166\u0149\u0001\u0000\u0000\u0000"+
		"\u0166\u0154\u0001\u0000\u0000\u0000\u0166\u015c\u0001\u0000\u0000\u0000"+
		"\u0167\u000b\u0001\u0000\u0000\u0000\u0168\u016a\u0007\u0000\u0000\u0000"+
		"\u0169\u016b\u0003\u00fc~\u0000\u016a\u0169\u0001\u0000\u0000\u0000\u016b"+
		"\u016c\u0001\u0000\u0000\u0000\u016c\u016a\u0001\u0000\u0000\u0000\u016c"+
		"\u016d\u0001\u0000\u0000\u0000\u016d\u0179\u0001\u0000\u0000\u0000\u016e"+
		"\u0170\u0005\u010e\u0000\u0000\u016f\u0171\u0005\u0095\u0000\u0000\u0170"+
		"\u016f\u0001\u0000\u0000\u0000\u0170\u0171\u0001\u0000\u0000\u0000\u0171"+
		"\u0172\u0001\u0000\u0000\u0000\u0172\u0179\u00051\u0000\u0000\u0173\u0175"+
		"\u0003\u00fc~\u0000\u0174\u0173\u0001\u0000\u0000\u0000\u0175\u0176\u0001"+
		"\u0000\u0000\u0000\u0176\u0174\u0001\u0000\u0000\u0000\u0176\u0177\u0001"+
		"\u0000\u0000\u0000\u0177\u0179\u0001\u0000\u0000\u0000\u0178\u0168\u0001"+
		"\u0000\u0000\u0000\u0178\u016e\u0001\u0000\u0000\u0000\u0178\u0174\u0001"+
		"\u0000\u0000\u0000\u0179\r\u0001\u0000\u0000\u0000\u017a\u017e\u0003\u001a"+
		"\r\u0000\u017b\u017e\u0003\u0018\f\u0000\u017c\u017e\u0003\u0010\b\u0000"+
		"\u017d\u017a\u0001\u0000\u0000\u0000\u017d\u017b\u0001\u0000\u0000\u0000"+
		"\u017d\u017c\u0001\u0000\u0000\u0000\u017e\u000f\u0001\u0000\u0000\u0000"+
		"\u017f\u0180\u0003\u00f0x\u0000\u0180\u0184\u0003\u00f2y\u0000\u0181\u0183"+
		"\u0003\u0012\t\u0000\u0182\u0181\u0001\u0000\u0000\u0000\u0183\u0186\u0001"+
		"\u0000\u0000\u0000\u0184\u0182\u0001\u0000\u0000\u0000\u0184\u0185\u0001"+
		"\u0000\u0000\u0000\u0185\u0011\u0001\u0000\u0000\u0000\u0186\u0184\u0001"+
		"\u0000\u0000\u0000\u0187\u0188\u0005\u0097\u0000\u0000\u0188\u01dc\u0005"+
		"\u0098\u0000\u0000\u0189\u01dc\u0005\u0098\u0000\u0000\u018a\u018d\u0005"+
		"7\u0000\u0000\u018b\u018e\u0003\u0016\u000b\u0000\u018c\u018e\u0004\t"+
		"\u0000\u0001\u018d\u018b\u0001\u0000\u0000\u0000\u018d\u018c\u0001\u0000"+
		"\u0000\u0000\u018e\u01dc\u0001\u0000\u0000\u0000\u018f\u0190\u0005\u010e"+
		"\u0000\u0000\u0190\u0193\u00057\u0000\u0000\u0191\u0194\u0003\u0016\u000b"+
		"\u0000\u0192\u0194\u0004\t\u0001\u0001\u0193\u0191\u0001\u0000\u0000\u0000"+
		"\u0193\u0192\u0001\u0000\u0000\u0000\u0194\u01dc\u0001\u0000\u0000\u0000"+
		"\u0195\u0199\u0005]\u0000\u0000\u0196\u019a\u0005\u0120\u0000\u0000\u0197"+
		"\u0198\u0005\u0014\u0000\u0000\u0198\u019a\u00057\u0000\u0000\u0199\u0196"+
		"\u0001\u0000\u0000\u0000\u0199\u0197\u0001\u0000\u0000\u0000\u019a\u019c"+
		"\u0001\u0000\u0000\u0000\u019b\u019d\u0003\u0014\n\u0000\u019c\u019b\u0001"+
		"\u0000\u0000\u0000\u019c\u019d\u0001\u0000\u0000\u0000\u019d\u01dc\u0001"+
		"\u0000\u0000\u0000\u019e\u019f\u0005*\u0000\u0000\u019f\u01a1\u0003\u00f0"+
		"x\u0000\u01a0\u019e\u0001\u0000\u0000\u0000\u01a0\u01a1\u0001\u0000\u0000"+
		"\u0000\u01a1\u01a2\u0001\u0000\u0000\u0000\u01a2\u01a3\u0005\u0175\u0000"+
		"\u0000\u01a3\u01dc\u0005z\u0000\u0000\u01a4\u01a5\u0005*\u0000\u0000\u01a5"+
		"\u01a7\u0003\u00f0x\u0000\u01a6\u01a4\u0001\u0000\u0000\u0000\u01a6\u01a7"+
		"\u0001\u0000\u0000\u0000\u01a7\u01a8\u0001\u0000\u0000\u0000\u01a8\u01dc"+
		"\u0005\u00fc\u0000\u0000\u01a9\u01aa\u0005*\u0000\u0000\u01aa\u01ac\u0003"+
		"\u00f0x\u0000\u01ab\u01a9\u0001\u0000\u0000\u0000\u01ab\u01ac\u0001\u0000"+
		"\u0000\u0000\u01ac\u01ad\u0001\u0000\u0000\u0000\u01ad\u01ae\u0005\u001d"+
		"\u0000\u0000\u01ae\u01af\u0005\u01a6\u0000\u0000\u01af\u01b0\u0003\u00de"+
		"o\u0000\u01b0\u01b1\u0005\u01a7\u0000\u0000\u01b1\u01dc\u0001\u0000\u0000"+
		"\u0000\u01b2\u01b3\u0005*\u0000\u0000\u01b3\u01b5\u0003\u00f0x\u0000\u01b4"+
		"\u01b2\u0001\u0000\u0000\u0000\u01b4\u01b5\u0001\u0000\u0000\u0000\u01b5"+
		"\u01b6\u0001\u0000\u0000\u0000\u01b6\u01b7\u0005\u00be\u0000\u0000\u01b7"+
		"\u01b9\u0003\u00eew\u0000\u01b8\u01ba\u0003\"\u0011\u0000\u01b9\u01b8"+
		"\u0001\u0000\u0000\u0000\u01b9\u01ba\u0001\u0000\u0000\u0000\u01ba\u01be"+
		"\u0001\u0000\u0000\u0000\u01bb\u01bd\u0003\u001e\u000f\u0000\u01bc\u01bb"+
		"\u0001\u0000\u0000\u0000\u01bd\u01c0\u0001\u0000\u0000\u0000\u01be\u01bc"+
		"\u0001\u0000\u0000\u0000\u01be\u01bf\u0001\u0000\u0000\u0000\u01bf\u01dc"+
		"\u0001\u0000\u0000\u0000\u01c0\u01be\u0001\u0000\u0000\u0000\u01c1\u01c2"+
		"\u0005X\u0000\u0000\u01c2\u01c3\u0007\u0001\u0000\u0000\u01c3\u01dc\u0005"+
		"1\u0000\u0000\u01c4\u01c5\u0005\u001a\u0000\u0000\u01c5\u01dc\u0003\u00f0"+
		"x\u0000\u01c6\u01c7\u0005U\u0000\u0000\u01c7\u01cb\u0003\u00eew\u0000"+
		"\u01c8\u01ca\u0003\u00eau\u0000\u01c9\u01c8\u0001\u0000\u0000\u0000\u01ca"+
		"\u01cd\u0001\u0000\u0000\u0000\u01cb\u01c9\u0001\u0000\u0000\u0000\u01cb"+
		"\u01cc\u0001\u0000\u0000\u0000\u01cc\u01dc\u0001\u0000\u0000\u0000\u01cd"+
		"\u01cb\u0001\u0000\u0000\u0000\u01ce\u01cf\u0005\u0155\u0000\u0000\u01cf"+
		"\u01d0\u0005\u015a\u0000\u0000\u01d0\u01dc\u0005\u01bc\u0000\u0000\u01d1"+
		"\u01d2\u0005\u0150\u0000\u0000\u01d2\u01dc\u0005\u014c\u0000\u0000\u01d3"+
		"\u01d5\u0005\u0097\u0000\u0000\u01d4\u01d3\u0001\u0000\u0000\u0000\u01d4"+
		"\u01d5\u0001\u0000\u0000\u0000\u01d5\u01d6\u0001\u0000\u0000\u0000\u01d6"+
		"\u01dc\u0005\u0108\u0000\u0000\u01d7\u01d8\u0005\f\u0000\u0000\u01d8\u01d9"+
		"\u0005\u00dd\u0000\u0000\u01d9\u01dc\u0005{\u0000\u0000\u01da\u01dc\u0003"+
		"\u00f0x\u0000\u01db\u0187\u0001\u0000\u0000\u0000\u01db\u0189\u0001\u0000"+
		"\u0000\u0000\u01db\u018a\u0001\u0000\u0000\u0000\u01db\u018f\u0001\u0000"+
		"\u0000\u0000\u01db\u0195\u0001\u0000\u0000\u0000\u01db\u01a0\u0001\u0000"+
		"\u0000\u0000\u01db\u01a6\u0001\u0000\u0000\u0000\u01db\u01ab\u0001\u0000"+
		"\u0000\u0000\u01db\u01b4\u0001\u0000\u0000\u0000\u01db\u01c1\u0001\u0000"+
		"\u0000\u0000\u01db\u01c4\u0001\u0000\u0000\u0000\u01db\u01c6\u0001\u0000"+
		"\u0000\u0000\u01db\u01ce\u0001\u0000\u0000\u0000\u01db\u01d1\u0001\u0000"+
		"\u0000\u0000\u01db\u01d4\u0001\u0000\u0000\u0000\u01db\u01d7\u0001\u0000"+
		"\u0000\u0000\u01db\u01da\u0001\u0000\u0000\u0000\u01dc\u0013\u0001\u0000"+
		"\u0000\u0000\u01dd\u01de\u0005\f\u0000\u0000\u01de\u01ec\u0005\u014f\u0000"+
		"\u0000\u01df\u01e0\u0005\u01a6\u0000\u0000\u01e0\u01e7\u0003R)\u0000\u01e1"+
		"\u01e3\u0005\u01a8\u0000\u0000\u01e2\u01e1\u0001\u0000\u0000\u0000\u01e2"+
		"\u01e3\u0001\u0000\u0000\u0000\u01e3\u01e4\u0001\u0000\u0000\u0000\u01e4"+
		"\u01e6\u0003R)\u0000\u01e5\u01e2\u0001\u0000\u0000\u0000\u01e6\u01e9\u0001"+
		"\u0000\u0000\u0000\u01e7\u01e5\u0001\u0000\u0000\u0000\u01e7\u01e8\u0001"+
		"\u0000\u0000\u0000\u01e8\u01ea\u0001\u0000\u0000\u0000\u01e9\u01e7\u0001"+
		"\u0000\u0000\u0000\u01ea\u01eb\u0005\u01a7\u0000\u0000\u01eb\u01ed\u0001"+
		"\u0000\u0000\u0000\u01ec\u01df\u0001\u0000\u0000\u0000\u01ec\u01ed\u0001"+
		"\u0000\u0000\u0000\u01ed\u0200\u0001\u0000\u0000\u0000\u01ee\u01ef\u0005"+
		"\f\u0000\u0000\u01ef\u01f0\u0005\u01a6\u0000\u0000\u01f0\u01f1\u0003\u00e4"+
		"r\u0000\u01f1\u01f2\u0005\u01a7\u0000\u0000\u01f2\u0200\u0001\u0000\u0000"+
		"\u0000\u01f3\u01f4\u0005\f\u0000\u0000\u01f4\u01f9\u0005\u00d4\u0000\u0000"+
		"\u01f5\u01fa\u0005\u0011\u0000\u0000\u01f6\u01fa\u0005G\u0000\u0000\u01f7"+
		"\u01f8\u0005\u012e\u0000\u0000\u01f8\u01fa\u0005\u0196\u0000\u0000\u01f9"+
		"\u01f5\u0001\u0000\u0000\u0000\u01f9\u01f6\u0001\u0000\u0000\u0000\u01f9"+
		"\u01f7\u0001\u0000\u0000\u0000\u01fa\u0200\u0001\u0000\u0000\u0000\u01fb"+
		"\u01fc\u0005\f\u0000\u0000\u01fc\u01fd\u0005\u0198\u0000\u0000\u01fd\u01fe"+
		"\u0005\u018f\u0000\u0000\u01fe\u0200\u0005\u014e\u0000\u0000\u01ff\u01dd"+
		"\u0001\u0000\u0000\u0000\u01ff\u01ee\u0001\u0000\u0000\u0000\u01ff\u01f3"+
		"\u0001\u0000\u0000\u0000\u01ff\u01fb\u0001\u0000\u0000\u0000\u0200\u0015"+
		"\u0001\u0000\u0000\u0000\u0201\u0215\u0003\u00eau\u0000\u0202\u0215\u0005"+
		"\u00ff\u0000\u0000\u0203\u0204\u0005/\u0000\u0000\u0204\u0215\u0005\u018e"+
		"\u0000\u0000\u0205\u0215\u0005\u0098\u0000\u0000\u0206\u0207\u0003\u00ee"+
		"w\u0000\u0207\u0210\u0005\u01a6\u0000\u0000\u0208\u020d\u0003\u00e4r\u0000"+
		"\u0209\u020a\u0005\u01a8\u0000\u0000\u020a\u020c\u0003\u00e4r\u0000\u020b"+
		"\u0209\u0001\u0000\u0000\u0000\u020c\u020f\u0001\u0000\u0000\u0000\u020d"+
		"\u020b\u0001\u0000\u0000\u0000\u020d\u020e\u0001\u0000\u0000\u0000\u020e"+
		"\u0211\u0001\u0000\u0000\u0000\u020f\u020d\u0001\u0000\u0000\u0000\u0210"+
		"\u0208\u0001\u0000\u0000\u0000\u0210\u0211\u0001\u0000\u0000\u0000\u0211"+
		"\u0212\u0001\u0000\u0000\u0000\u0212\u0213\u0005\u01a7\u0000\u0000\u0213"+
		"\u0215\u0001\u0000\u0000\u0000\u0214\u0201\u0001\u0000\u0000\u0000\u0214"+
		"\u0202\u0001\u0000\u0000\u0000\u0214\u0203\u0001\u0000\u0000\u0000\u0214"+
		"\u0205\u0001\u0000\u0000\u0000\u0214\u0206\u0001\u0000\u0000\u0000\u0215"+
		"\u0017\u0001\u0000\u0000\u0000\u0216\u0217\u0005\u00b0\u0000\u0000\u0217"+
		"\u0218\u0003\u00f0x\u0000\u0218\u0219\u0005\u01a6\u0000\u0000\u0219\u021a"+
		"\u0003\u00f0x\u0000\u021a\u021b\u0005\u01a8\u0000\u0000\u021b\u021c\u0003"+
		"\u00f0x\u0000\u021c\u021d\u0005\u01a7\u0000\u0000\u021d\u0228\u0001\u0000"+
		"\u0000\u0000\u021e\u021f\u0005\u00b0\u0000\u0000\u021f\u0220\u0005X\u0000"+
		"\u0000\u0220\u0221\u0003\u00f0x\u0000\u0221\u0222\u0005\u01a6\u0000\u0000"+
		"\u0222\u0223\u0003\u00f0x\u0000\u0223\u0224\u0005\u01a8\u0000\u0000\u0224"+
		"\u0225\u0003\u00f0x\u0000\u0225\u0226\u0005\u01a7\u0000\u0000\u0226\u0228"+
		"\u0001\u0000\u0000\u0000\u0227\u0216\u0001\u0000\u0000\u0000\u0227\u021e"+
		"\u0001\u0000\u0000\u0000\u0228\u0019\u0001\u0000\u0000\u0000\u0229\u022a"+
		"\u0005*\u0000\u0000\u022a\u022c\u0003\u00f0x\u0000\u022b\u0229\u0001\u0000"+
		"\u0000\u0000\u022b\u022c\u0001\u0000\u0000\u0000\u022c\u022d\u0001\u0000"+
		"\u0000\u0000\u022d\u022e\u0003\u001c\u000e\u0000\u022e\u001b\u0001\u0000"+
		"\u0000\u0000\u022f\u0230\u0005\u0175\u0000\u0000\u0230\u0231\u0005z\u0000"+
		"\u0000\u0231\u0235\u0003\"\u0011\u0000\u0232\u0234\u0003 \u0010\u0000"+
		"\u0233\u0232\u0001\u0000\u0000\u0000\u0234\u0237\u0001\u0000\u0000\u0000"+
		"\u0235\u0233\u0001\u0000\u0000\u0000\u0235\u0236\u0001\u0000\u0000\u0000"+
		"\u0236\u0267\u0001\u0000\u0000\u0000\u0237\u0235\u0001\u0000\u0000\u0000"+
		"\u0238\u0239\u0005\u0147\u0000\u0000\u0239\u023b\u0005z\u0000\u0000\u023a"+
		"\u023c\u0003\u00f0x\u0000\u023b\u023a\u0001\u0000\u0000\u0000\u023b\u023c"+
		"\u0001\u0000\u0000\u0000\u023c\u023d\u0001\u0000\u0000\u0000\u023d\u023e"+
		"\u0003\"\u0011\u0000\u023e\u023f\u0005\u00be\u0000\u0000\u023f\u0241\u0003"+
		"\u00eew\u0000\u0240\u0242\u0003\"\u0011\u0000\u0241\u0240\u0001\u0000"+
		"\u0000\u0000\u0241\u0242\u0001\u0000\u0000\u0000\u0242\u0246\u0001\u0000"+
		"\u0000\u0000\u0243\u0245\u0003\u001e\u000f\u0000\u0244\u0243\u0001\u0000"+
		"\u0000\u0000\u0245\u0248\u0001\u0000\u0000\u0000\u0246\u0244\u0001\u0000"+
		"\u0000\u0000\u0246\u0247\u0001\u0000\u0000\u0000\u0247\u024c\u0001\u0000"+
		"\u0000\u0000\u0248\u0246\u0001\u0000\u0000\u0000\u0249\u024b\u0003 \u0010"+
		"\u0000\u024a\u0249\u0001\u0000\u0000\u0000\u024b\u024e\u0001\u0000\u0000"+
		"\u0000\u024c\u024a\u0001\u0000\u0000\u0000\u024c\u024d\u0001\u0000\u0000"+
		"\u0000\u024d\u0267\u0001\u0000\u0000\u0000\u024e\u024c\u0001\u0000\u0000"+
		"\u0000\u024f\u0253\u0005\u00fc\u0000\u0000\u0250\u0251\u0005\u010c\u0000"+
		"\u0000\u0251\u0252\u0005\u0097\u0000\u0000\u0252\u0254\u0005\u0098\u0000"+
		"\u0000\u0253\u0250\u0001\u0000\u0000\u0000\u0253\u0254\u0001\u0000\u0000"+
		"\u0000\u0254\u0255\u0001\u0000\u0000\u0000\u0255\u0259\u0003\"\u0011\u0000"+
		"\u0256\u0258\u0003 \u0010\u0000\u0257\u0256\u0001\u0000\u0000\u0000\u0258"+
		"\u025b\u0001\u0000\u0000\u0000\u0259\u0257\u0001\u0000\u0000\u0000\u0259"+
		"\u025a\u0001\u0000\u0000\u0000\u025a\u0267\u0001\u0000\u0000\u0000\u025b"+
		"\u0259\u0001\u0000\u0000\u0000\u025c\u025d\u0005\u001d\u0000\u0000\u025d"+
		"\u025e\u0005\u01a6\u0000\u0000\u025e\u025f\u0003\u00deo\u0000\u025f\u0263"+
		"\u0005\u01a7\u0000\u0000\u0260\u0262\u0003 \u0010\u0000\u0261\u0260\u0001"+
		"\u0000\u0000\u0000\u0262\u0265\u0001\u0000\u0000\u0000\u0263\u0261\u0001"+
		"\u0000\u0000\u0000\u0263\u0264\u0001\u0000\u0000\u0000\u0264\u0267\u0001"+
		"\u0000\u0000\u0000\u0265\u0263\u0001\u0000\u0000\u0000\u0266\u022f\u0001"+
		"\u0000\u0000\u0000\u0266\u0238\u0001\u0000\u0000\u0000\u0266\u024f\u0001"+
		"\u0000\u0000\u0000\u0266\u025c\u0001\u0000\u0000\u0000\u0267\u001d\u0001"+
		"\u0000\u0000\u0000\u0268\u0269\u0005\u009e\u0000\u0000\u0269\u0270\u0005"+
		"8\u0000\u0000\u026a\u0271\u0005\u00c4\u0000\u0000\u026b\u0271\u0005\u012d"+
		"\u0000\u0000\u026c\u026d\u0005\u0095\u0000\u0000\u026d\u0271\u0005\u011d"+
		"\u0000\u0000\u026e\u026f\u0005\u00e1\u0000\u0000\u026f\u0271\u0005\u0098"+
		"\u0000\u0000\u0270\u026a\u0001\u0000\u0000\u0000\u0270\u026b\u0001\u0000"+
		"\u0000\u0000\u0270\u026c\u0001\u0000\u0000\u0000\u0270\u026e\u0001\u0000"+
		"\u0000\u0000\u0271\u0281\u0001\u0000\u0000\u0000\u0272\u0273\u0005\u009e"+
		"\u0000\u0000\u0273\u0277\u0005\u00fe\u0000\u0000\u0274\u0278\u0005\u00c4"+
		"\u0000\u0000\u0275\u0276\u0005\u0095\u0000\u0000\u0276\u0278\u0005\u011d"+
		"\u0000\u0000\u0277\u0274\u0001\u0000\u0000\u0000\u0277\u0275\u0001\u0000"+
		"\u0000\u0000\u0278\u0281\u0001\u0000\u0000\u0000\u0279\u027b\u0005\u0097"+
		"\u0000\u0000\u027a\u0279\u0001\u0000\u0000\u0000\u027a\u027b\u0001\u0000"+
		"\u0000\u0000\u027b\u027c\u0001\u0000\u0000\u0000\u027c\u0281\u0005\u0141"+
		"\u0000\u0000\u027d\u027e\u0005\u0140\u0000\u0000\u027e\u027f\u0005\u00bb"+
		"\u0000\u0000\u027f\u0281\u0005\u00a0\u0000\u0000\u0280\u0268\u0001\u0000"+
		"\u0000\u0000\u0280\u0272\u0001\u0000\u0000\u0000\u0280\u027a\u0001\u0000"+
		"\u0000\u0000\u0280\u027d\u0001\u0000\u0000\u0000\u0281\u001f\u0001\u0000"+
		"\u0000\u0000\u0282\u0284\u0005\u0097\u0000\u0000\u0283\u0282\u0001\u0000"+
		"\u0000\u0000\u0283\u0284\u0001\u0000\u0000\u0000\u0284\u0285\u0001\u0000"+
		"\u0000\u0000\u0285\u028a\u0005\u0141\u0000\u0000\u0286\u0287\u0005\u0140"+
		"\u0000\u0000\u0287\u0288\u0005\u00bb\u0000\u0000\u0288\u028a\u0005\u00a0"+
		"\u0000\u0000\u0289\u0283\u0001\u0000\u0000\u0000\u0289\u0286\u0001\u0000"+
		"\u0000\u0000\u028a!\u0001\u0000\u0000\u0000\u028b\u028c\u0005\u01a6\u0000"+
		"\u0000\u028c\u0291\u0003\u00f0x\u0000\u028d\u028e\u0005\u01a8\u0000\u0000"+
		"\u028e\u0290\u0003\u00f0x\u0000\u028f\u028d\u0001\u0000\u0000\u0000\u0290"+
		"\u0293\u0001\u0000\u0000\u0000\u0291\u028f\u0001\u0000\u0000\u0000\u0291"+
		"\u0292\u0001\u0000\u0000\u0000\u0292\u0294\u0001\u0000\u0000\u0000\u0293"+
		"\u0291\u0001\u0000\u0000\u0000\u0294\u0295\u0005\u01a7\u0000\u0000\u0295"+
		"#\u0001\u0000\u0000\u0000\u0296\u0297\u0005k\u0000\u0000\u0297\u02f0\u0003"+
		"\u00eew\u0000\u0298\u0299\u0005k\u0000\u0000\u0299\u029a\u00052\u0000"+
		"\u0000\u029a\u02f0\u0003\u00f0x\u0000\u029b\u029c\u0005\u00ab\u0000\u0000"+
		"\u029c\u029e\u0005\u0014\u0000\u0000\u029d\u029f\u0007\u0002\u0000\u0000"+
		"\u029e\u029d\u0001\u0000\u0000\u0000\u029e\u029f\u0001\u0000\u0000\u0000"+
		"\u029f\u02ab\u0001\u0000\u0000\u0000\u02a0\u02a1\u0005\u01a6\u0000\u0000"+
		"\u02a1\u02a6\u0003(\u0014\u0000\u02a2\u02a3\u0005\u01a8\u0000\u0000\u02a3"+
		"\u02a5\u0003(\u0014\u0000\u02a4\u02a2\u0001\u0000\u0000\u0000\u02a5\u02a8"+
		"\u0001\u0000\u0000\u0000\u02a6\u02a4\u0001\u0000\u0000\u0000\u02a6\u02a7"+
		"\u0001\u0000\u0000\u0000\u02a7\u02a9\u0001\u0000\u0000\u0000\u02a8\u02a6"+
		"\u0001\u0000\u0000\u0000\u02a9\u02aa\u0005\u01a7\u0000\u0000\u02aa\u02ac"+
		"\u0001\u0000\u0000\u0000\u02ab\u02a0\u0001\u0000\u0000\u0000\u02ab\u02ac"+
		"\u0001\u0000\u0000\u0000\u02ac\u02b0\u0001\u0000\u0000\u0000\u02ad\u02af"+
		"\u0003*\u0015\u0000\u02ae\u02ad\u0001\u0000\u0000\u0000\u02af\u02b2\u0001"+
		"\u0000\u0000\u0000\u02b0\u02ae\u0001\u0000\u0000\u0000\u02b0\u02b1\u0001"+
		"\u0000\u0000\u0000\u02b1\u02f0\u0001\u0000\u0000\u0000\u02b2\u02b0\u0001"+
		"\u0000\u0000\u0000\u02b3\u02b4\u0005\u0170\u0000\u0000\u02b4\u02c7\u0005"+
		"\u0014\u0000\u0000\u02b5\u02b6\u0005\u014b\u0000\u0000\u02b6\u02b7\u0005"+
		"\u01a6\u0000\u0000\u02b7\u02bc\u0003\u00f0x\u0000\u02b8\u02b9\u0005\u01a8"+
		"\u0000\u0000\u02b9\u02bb\u0003\u00f0x\u0000\u02ba\u02b8\u0001\u0000\u0000"+
		"\u0000\u02bb\u02be\u0001\u0000\u0000\u0000\u02bc\u02ba\u0001\u0000\u0000"+
		"\u0000\u02bc\u02bd\u0001\u0000\u0000\u0000\u02bd\u02bf\u0001\u0000\u0000"+
		"\u0000\u02be\u02bc\u0001\u0000\u0000\u0000\u02bf\u02c1\u0005\u01a7\u0000"+
		"\u0000\u02c0\u02c2\u0003&\u0013\u0000\u02c1\u02c0\u0001\u0000\u0000\u0000"+
		"\u02c1\u02c2\u0001\u0000\u0000\u0000\u02c2\u02c8\u0001\u0000\u0000\u0000"+
		"\u02c3\u02c5\u0005z\u0000\u0000\u02c4\u02c3\u0001\u0000\u0000\u0000\u02c4"+
		"\u02c5\u0001\u0000\u0000\u0000\u02c5\u02c6\u0001\u0000\u0000\u0000\u02c6"+
		"\u02c8\u0005\u00de\u0000\u0000\u02c7\u02b5\u0001\u0000\u0000\u0000\u02c7"+
		"\u02c4\u0001\u0000\u0000\u0000\u02c8\u02f0\u0001\u0000\u0000\u0000\u02c9"+
		"\u02ca\u0005\u001a\u0000\u0000\u02ca\u02f0\u0003\u00f0x\u0000\u02cb\u02cc"+
		"\u0005\u0101\u0000\u0000\u02cc\u02f0\u0003\u00eew\u0000\u02cd\u02ce\u0005"+
		"C\u0000\u0000\u02ce\u02d2\u0003\u00eew\u0000\u02cf\u02d0\u0005\u010e\u0000"+
		"\u0000\u02d0\u02d1\u0005\u00d4\u0000\u0000\u02d1\u02d3\u0005\u0126\u0000"+
		"\u0000\u02d2\u02cf\u0001\u0000\u0000\u0000\u02d2\u02d3\u0001\u0000\u0000"+
		"\u0000\u02d3\u02f0\u0001\u0000\u0000\u0000\u02d4\u02d5\u0005\u0127\u0000"+
		"\u0000\u02d5\u02f0\u0007\u0003\u0000\u0000\u02d6\u02d7\u00051\u0000\u0000"+
		"\u02d7\u02d8\u0005\u0016\u0000\u0000\u02d8\u02f0\u0007\u0004\u0000\u0000"+
		"\u02d9\u02db\u0005\u0097\u0000\u0000\u02da\u02d9\u0001\u0000\u0000\u0000"+
		"\u02da\u02db\u0001\u0000\u0000\u0000\u02db\u02dc\u0001\u0000\u0000\u0000"+
		"\u02dc\u02de\u0005\u0108\u0000\u0000\u02dd\u02df\u0005\u012c\u0000\u0000"+
		"\u02de\u02dd\u0001\u0000\u0000\u0000\u02de\u02df\u0001\u0000\u0000\u0000"+
		"\u02df\u02f0\u0001\u0000\u0000\u0000\u02e0\u02f0\u0005\u012c\u0000\u0000"+
		"\u02e1\u02e2\u0005\u0121\u0000\u0000\u02e2\u02f0\u0007\u0005\u0000\u0000"+
		"\u02e3\u02e4\u0005\u010e\u0000\u0000\u02e4\u02e5\u0005\u00c4\u0000\u0000"+
		"\u02e5\u02e6\u0005\u009e\u0000\u0000\u02e6\u02f0\u0005@\u0000\u0000\u02e7"+
		"\u02e8\u0005\u009b\u0000\u0000\u02e8\u02f0\u0005\u01bc\u0000\u0000\u02e9"+
		"\u02eb\u0005\u0097\u0000\u0000\u02ea\u02e9\u0001\u0000\u0000\u0000\u02ea"+
		"\u02eb\u0001\u0000\u0000\u0000\u02eb\u02ec\u0001\u0000\u0000\u0000\u02ec"+
		"\u02f0\u0005\u015e\u0000\u0000\u02ed\u02f0\u0003\u00f8|\u0000\u02ee\u02f0"+
		"\u0003\u00fc~\u0000\u02ef\u0296\u0001\u0000\u0000\u0000\u02ef\u0298\u0001"+
		"\u0000\u0000\u0000\u02ef\u029b\u0001\u0000\u0000\u0000\u02ef\u02b3\u0001"+
		"\u0000\u0000\u0000\u02ef\u02c9\u0001\u0000\u0000\u0000\u02ef\u02cb\u0001"+
		"\u0000\u0000\u0000\u02ef\u02cd\u0001\u0000\u0000\u0000\u02ef\u02d4\u0001"+
		"\u0000\u0000\u0000\u02ef\u02d6\u0001\u0000\u0000\u0000\u02ef\u02da\u0001"+
		"\u0000\u0000\u0000\u02ef\u02e0\u0001\u0000\u0000\u0000\u02ef\u02e1\u0001"+
		"\u0000\u0000\u0000\u02ef\u02e3\u0001\u0000\u0000\u0000\u02ef\u02e7\u0001"+
		"\u0000\u0000\u0000\u02ef\u02ea\u0001\u0000\u0000\u0000\u02ef\u02ed\u0001"+
		"\u0000\u0000\u0000\u02ef\u02ee\u0001\u0000\u0000\u0000\u02f0%\u0001\u0000"+
		"\u0000\u0000\u02f1\u02f2\u0005\u014b\u0000\u0000\u02f2\u02f3\u0005\u018b"+
		"\u0000\u0000\u02f3\u02f5\u0005\u01bc\u0000\u0000\u02f4\u02f6\u0003\u00f0"+
		"x\u0000\u02f5\u02f4\u0001\u0000\u0000\u0000\u02f5\u02f6\u0001\u0000\u0000"+
		"\u0000\u02f6\'\u0001\u0000\u0000\u0000\u02f7\u02f9\u0003\u00f0x\u0000"+
		"\u02f8\u02fa\u0007\u0006\u0000\u0000\u02f9\u02f8\u0001\u0000\u0000\u0000"+
		"\u02f9\u02fa\u0001\u0000\u0000\u0000\u02fa)\u0001\u0000\u0000\u0000\u02fb"+
		"\u02fc\u0005\u01a6\u0000\u0000\u02fc\u0301\u0003,\u0016\u0000\u02fd\u02fe"+
		"\u0005\u01a8\u0000\u0000\u02fe\u0300\u0003,\u0016\u0000\u02ff\u02fd\u0001"+
		"\u0000\u0000\u0000\u0300\u0303\u0001\u0000\u0000\u0000\u0301\u02ff\u0001"+
		"\u0000\u0000\u0000\u0301\u0302\u0001\u0000\u0000\u0000\u0302\u0304\u0001"+
		"\u0000\u0000\u0000\u0303\u0301\u0001\u0000\u0000\u0000\u0304\u0305\u0005"+
		"\u01a7\u0000\u0000\u0305\u0309\u0001\u0000\u0000\u0000\u0306\u0307\u0005"+
		"\u009a\u0000\u0000\u0307\u0309\u0005\u01bc\u0000\u0000\u0308\u02fb\u0001"+
		"\u0000\u0000\u0000\u0308\u0306\u0001\u0000\u0000\u0000\u0309+\u0001\u0000"+
		"\u0000\u0000\u030a\u030b\u0005\u00ab\u0000\u0000\u030b\u030f\u0005\u01bc"+
		"\u0000\u0000\u030c\u030e\u0003.\u0017\u0000\u030d\u030c\u0001\u0000\u0000"+
		"\u0000\u030e\u0311\u0001\u0000\u0000\u0000\u030f\u030d\u0001\u0000\u0000"+
		"\u0000\u030f\u0310\u0001\u0000\u0000\u0000\u0310\u0315\u0001\u0000\u0000"+
		"\u0000\u0311\u030f\u0001\u0000\u0000\u0000\u0312\u0314\u0003\u00f8|\u0000"+
		"\u0313\u0312\u0001\u0000\u0000\u0000\u0314\u0317\u0001\u0000\u0000\u0000"+
		"\u0315\u0313\u0001\u0000\u0000\u0000\u0315\u0316\u0001\u0000\u0000\u0000"+
		"\u0316\u0324\u0001\u0000\u0000\u0000\u0317\u0315\u0001\u0000\u0000\u0000"+
		"\u0318\u031a\u0003.\u0017\u0000\u0319\u0318\u0001\u0000\u0000\u0000\u031a"+
		"\u031b\u0001\u0000\u0000\u0000\u031b\u0319\u0001\u0000\u0000\u0000\u031b"+
		"\u031c\u0001\u0000\u0000\u0000\u031c\u0320\u0001\u0000\u0000\u0000\u031d"+
		"\u031f\u0003\u00f8|\u0000\u031e\u031d\u0001\u0000\u0000\u0000\u031f\u0322"+
		"\u0001\u0000\u0000\u0000\u0320\u031e\u0001\u0000\u0000\u0000\u0320\u0321"+
		"\u0001\u0000\u0000\u0000\u0321\u0324\u0001\u0000\u0000\u0000\u0322\u0320"+
		"\u0001\u0000\u0000\u0000\u0323\u030a\u0001\u0000\u0000\u0000\u0323\u0319"+
		"\u0001\u0000\u0000\u0000\u0324-\u0001\u0000\u0000\u0000\u0325\u0327\u0005"+
		"\u0190\u0000\u0000\u0326\u0328\u0005Z\u0000\u0000\u0327\u0326\u0001\u0000"+
		"\u0000\u0000\u0327\u0328\u0001\u0000\u0000\u0000\u0328\u0329\u0001\u0000"+
		"\u0000\u0000\u0329\u032b\u00030\u0018\u0000\u032a\u032c\u0007\u0007\u0000"+
		"\u0000\u032b\u032a\u0001\u0000\u0000\u0000\u032b\u032c\u0001\u0000\u0000"+
		"\u0000\u032c\u033e\u0001\u0000\u0000\u0000\u032d\u032f\u0005H\u0000\u0000"+
		"\u032e\u0330\u0005\u0124\u0000\u0000\u032f\u032e\u0001\u0000\u0000\u0000"+
		"\u032f\u0330\u0001\u0000\u0000\u0000\u0330\u0331\u0001\u0000\u0000\u0000"+
		"\u0331\u0333\u00030\u0018\u0000\u0332\u0334\u0007\u0007\u0000\u0000\u0333"+
		"\u0332\u0001\u0000\u0000\u0000\u0333\u0334\u0001\u0000\u0000\u0000\u0334"+
		"\u033e\u0001\u0000\u0000\u0000\u0335\u0336\u0005\u0143\u0000\u0000\u0336"+
		"\u0337\u0005\u01a6\u0000\u0000\u0337\u0339\u0003\u00eau\u0000\u0338\u033a"+
		"\u0003\u00f0x\u0000\u0339\u0338\u0001\u0000\u0000\u0000\u0339\u033a\u0001"+
		"\u0000\u0000\u0000\u033a\u033b\u0001\u0000\u0000\u0000\u033b\u033c\u0005"+
		"\u01a7\u0000\u0000\u033c\u033e\u0001\u0000\u0000\u0000\u033d\u0325\u0001"+
		"\u0000\u0000\u0000\u033d\u032d\u0001\u0000\u0000\u0000\u033d\u0335\u0001"+
		"\u0000\u0000\u0000\u033e/\u0001\u0000\u0000\u0000\u033f\u0340\u0005\u01a6"+
		"\u0000\u0000\u0340\u0345\u00032\u0019\u0000\u0341\u0342\u0005\u01a8\u0000"+
		"\u0000\u0342\u0344\u00032\u0019\u0000\u0343\u0341\u0001\u0000\u0000\u0000"+
		"\u0344\u0347\u0001\u0000\u0000\u0000\u0345\u0343\u0001\u0000\u0000\u0000"+
		"\u0345\u0346\u0001\u0000\u0000\u0000\u0346\u0348\u0001\u0000\u0000\u0000"+
		"\u0347\u0345\u0001\u0000\u0000\u0000\u0348\u0349\u0005\u01a7\u0000\u0000"+
		"\u03491\u0001\u0000\u0000\u0000\u034a\u034e\u0003\u00eau\u0000\u034b\u034e"+
		"\u0005\u0166\u0000\u0000\u034c\u034e\u0005\u0163\u0000\u0000\u034d\u034a"+
		"\u0001\u0000\u0000\u0000\u034d\u034b\u0001\u0000\u0000\u0000\u034d\u034c"+
		"\u0001\u0000\u0000\u0000\u034e3\u0001\u0000\u0000\u0000\u034f\u0353\u0005"+
		"-\u0000\u0000\u0350\u0352\u00036\u001b\u0000\u0351\u0350\u0001\u0000\u0000"+
		"\u0000\u0352\u0355\u0001\u0000\u0000\u0000\u0353\u0351\u0001\u0000\u0000"+
		"\u0000\u0353\u0354\u0001\u0000\u0000\u0000\u0354\u0356\u0001\u0000\u0000"+
		"\u0000\u0355\u0353\u0001\u0000\u0000\u0000\u0356\u0357\u0005m\u0000\u0000"+
		"\u0357\u0358\u0003\u00eew\u0000\u0358\u0359\u0005\u009e\u0000\u0000\u0359"+
		"\u0367\u0003\u00eew\u0000\u035a\u035b\u0005\u01a6\u0000\u0000\u035b\u0360"+
		"\u00038\u001c\u0000\u035c\u035d\u0005\u01a8\u0000\u0000\u035d\u035f\u0003"+
		"8\u001c\u0000\u035e\u035c\u0001\u0000\u0000\u0000\u035f\u0362\u0001\u0000"+
		"\u0000\u0000\u0360\u035e\u0001\u0000\u0000\u0000\u0360\u0361\u0001\u0000"+
		"\u0000\u0000\u0361\u0363\u0001\u0000\u0000\u0000\u0362\u0360\u0001\u0000"+
		"\u0000\u0000\u0363\u0364\u0005\u01a7\u0000\u0000\u0364\u0368\u0001\u0000"+
		"\u0000\u0000\u0365\u0366\u0005\u01a4\u0000\u0000\u0366\u0368\u0005\u01b8"+
		"\u0000\u0000\u0367\u035a\u0001\u0000\u0000\u0000\u0367\u0365\u0001\u0000"+
		"\u0000\u0000\u0368\u036c\u0001\u0000\u0000\u0000\u0369\u036b\u0003:\u001d"+
		"\u0000\u036a\u0369\u0001\u0000\u0000\u0000\u036b\u036e\u0001\u0000\u0000"+
		"\u0000\u036c\u036a\u0001\u0000\u0000\u0000\u036c\u036d\u0001\u0000\u0000"+
		"\u0000\u036d\u036f\u0001\u0000\u0000\u0000\u036e\u036c\u0001\u0000\u0000"+
		"\u0000\u036f\u0370\u0003\u00fa}\u0000\u03705\u0001\u0000\u0000\u0000\u0371"+
		"\u0375\u0005\u00fc\u0000\u0000\u0372\u0373\u0005\u010c\u0000\u0000\u0373"+
		"\u0374\u0005\u0097\u0000\u0000\u0374\u0376\u0005\u0098\u0000\u0000\u0375"+
		"\u0372\u0001\u0000\u0000\u0000\u0375\u0376\u0001\u0000\u0000\u0000\u0376"+
		"\u037a\u0001\u0000\u0000\u0000\u0377\u0378\u0005\u00f9\u0000\u0000\u0378"+
		"\u037a\u0005\u01bc\u0000\u0000\u0379\u0371\u0001\u0000\u0000\u0000\u0379"+
		"\u0377\u0001\u0000\u0000\u0000\u037a7\u0001\u0000\u0000\u0000\u037b\u037c"+
		"\u0004\u001c\u0002\u0001\u037c\u0380\u0003\u00f0x\u0000\u037d\u037e\u0004"+
		"\u001c\u0003\u0001\u037e\u0380\u0003\u00e4r\u0000\u037f\u037b\u0001\u0000"+
		"\u0000\u0000\u037f\u037d\u0001\u0000\u0000\u0000\u0380\u0382\u0001\u0000"+
		"\u0000\u0000\u0381\u0383\u0007\b\u0000\u0000\u0382\u0381\u0001\u0000\u0000"+
		"\u0000\u0382\u0383\u0001\u0000\u0000\u0000\u03839\u0001\u0000\u0000\u0000"+
		"\u0384\u0386\u0005\u0097\u0000\u0000\u0385\u0384\u0001\u0000\u0000\u0000"+
		"\u0385\u0386\u0001\u0000\u0000\u0000\u0386\u0387\u0001\u0000\u0000\u0000"+
		"\u0387\u03be\u0005 \u0000\u0000\u0388\u038a\u0005\u0097\u0000\u0000\u0389"+
		"\u0388\u0001\u0000\u0000\u0000\u0389\u038a\u0001\u0000\u0000\u0000\u038a"+
		"\u038b\u0001\u0000\u0000\u0000\u038b\u03be\u0005\u00aa\u0000\u0000\u038c"+
		"\u038d\u0007\t\u0000\u0000\u038d\u038e\u0005\u0098\u0000\u0000\u038e\u03be"+
		"\u0005\u0158\u0000\u0000\u038f\u0390\u0005\u0151\u0000\u0000\u0390\u03be"+
		"\u0003\"\u0011\u0000\u0391\u03be\u0005\u00ac\u0000\u0000\u0392\u0393\u0005"+
		"\u00ab\u0000\u0000\u0393\u0395\u0005\u0014\u0000\u0000\u0394\u0396\u0007"+
		"\u0002\u0000\u0000\u0395\u0394\u0001\u0000\u0000\u0000\u0395\u0396\u0001"+
		"\u0000\u0000\u0000\u0396\u039a\u0001\u0000\u0000\u0000\u0397\u0399\u0003"+
		"*\u0015\u0000\u0398\u0397\u0001\u0000\u0000\u0000\u0399\u039c\u0001\u0000"+
		"\u0000\u0000\u039a\u0398\u0001\u0000\u0000\u0000\u039a\u039b\u0001\u0000"+
		"\u0000\u0000\u039b\u03be\u0001\u0000\u0000\u0000\u039c\u039a\u0001\u0000"+
		"\u0000\u0000\u039d\u039f\u0005\u014a\u0000\u0000\u039e\u03a0\u0005z\u0000"+
		"\u0000\u039f\u039e\u0001\u0000\u0000\u0000\u039f\u03a0\u0001\u0000\u0000"+
		"\u0000\u03a0\u03a1\u0001\u0000\u0000\u0000\u03a1\u03a4\u0005\u0100\u0000"+
		"\u0000\u03a2\u03a5\u0005\u01a4\u0000\u0000\u03a3\u03a5\u0003\u00e4r\u0000"+
		"\u03a4\u03a2\u0001\u0000\u0000\u0000\u03a4\u03a3\u0001\u0000\u0000\u0000"+
		"\u03a5\u03a7\u0001\u0000\u0000\u0000\u03a6\u03a8\u0005\u01b8\u0000\u0000"+
		"\u03a7\u03a6\u0001\u0000\u0000\u0000\u03a7\u03a8\u0001\u0000\u0000\u0000"+
		"\u03a8\u03be\u0001\u0000\u0000\u0000\u03a9\u03aa\u0005\u0013\u0000\u0000"+
		"\u03aa\u03be\u0003\u00f0x\u0000\u03ab\u03ac\u0005\u001f\u0000\u0000\u03ac"+
		"\u03be\u0007\u0005\u0000\u0000\u03ad\u03ae\u0005\u0134\u0000\u0000\u03ae"+
		"\u03be\u0007\u0005\u0000\u0000\u03af\u03b0\u0005\u013b\u0000\u0000\u03b0"+
		"\u03be\u0007\u0005\u0000\u0000\u03b1\u03b2\u0005\u013c\u0000\u0000\u03b2"+
		"\u03be\u0007\u0005\u0000\u0000\u03b3\u03b4\u0005\u0132\u0000\u0000\u03b4"+
		"\u03be\u0007\u0005\u0000\u0000\u03b5\u03b6\u0005\u00af\u0000\u0000\u03b6"+
		"\u03b8\u0005\u01bc\u0000\u0000\u03b7\u03b9\u0003\u00f0x\u0000\u03b8\u03b7"+
		"\u0001\u0000\u0000\u0000\u03b8\u03b9\u0001\u0000\u0000\u0000\u03b9\u03be"+
		"\u0001\u0000\u0000\u0000\u03ba\u03be\u0003\u00f8|\u0000\u03bb\u03bc\u0004"+
		"\u001d\u0004\u0001\u03bc\u03be\u0003\u00fc~\u0000\u03bd\u0385\u0001\u0000"+
		"\u0000\u0000\u03bd\u0389\u0001\u0000\u0000\u0000\u03bd\u038c\u0001\u0000"+
		"\u0000\u0000\u03bd\u038f\u0001\u0000\u0000\u0000\u03bd\u0391\u0001\u0000"+
		"\u0000\u0000\u03bd\u0392\u0001\u0000\u0000\u0000\u03bd\u039d\u0001\u0000"+
		"\u0000\u0000\u03bd\u03a9\u0001\u0000\u0000\u0000\u03bd\u03ab\u0001\u0000"+
		"\u0000\u0000\u03bd\u03ad\u0001\u0000\u0000\u0000\u03bd\u03af\u0001\u0000"+
		"\u0000\u0000\u03bd\u03b1\u0001\u0000\u0000\u0000\u03bd\u03b3\u0001\u0000"+
		"\u0000\u0000\u03bd\u03b5\u0001\u0000\u0000\u0000\u03bd\u03ba\u0001\u0000"+
		"\u0000\u0000\u03bd\u03bb\u0001\u0000\u0000\u0000\u03be;\u0001\u0000\u0000"+
		"\u0000\u03bf\u03c1\u0005-\u0000\u0000\u03c0\u03c2\u0007\n\u0000\u0000"+
		"\u03c1\u03c0\u0001\u0000\u0000\u0000\u03c1\u03c2\u0001\u0000\u0000\u0000"+
		"\u03c2\u03c3\u0001\u0000\u0000\u0000\u03c3\u03c4\u0005\u00f4\u0000\u0000"+
		"\u03c4\u03c7\u0003\u00f0x\u0000\u03c5\u03c6\u0005k\u0000\u0000\u03c6\u03c8"+
		"\u0003\u00f0x\u0000\u03c7\u03c5\u0001\u0000\u0000\u0000\u03c7\u03c8\u0001"+
		"\u0000\u0000\u0000\u03c8\u03cc\u0001\u0000\u0000\u0000\u03c9\u03cb\u0003"+
		">\u001f\u0000\u03ca\u03c9\u0001\u0000\u0000\u0000\u03cb\u03ce\u0001\u0000"+
		"\u0000\u0000\u03cc\u03ca\u0001\u0000\u0000\u0000\u03cc\u03cd\u0001\u0000"+
		"\u0000\u0000\u03cd\u03cf\u0001\u0000\u0000\u0000\u03ce\u03cc\u0001\u0000"+
		"\u0000\u0000\u03cf\u03d0\u0003\u00fa}\u0000\u03d0=\u0001\u0000\u0000\u0000"+
		"\u03d1\u03d6\u0005\u0100\u0000\u0000\u03d2\u03d3\u0005\u00ea\u0000\u0000"+
		"\u03d3\u03d7\u0003\u00f0x\u0000\u03d4\u03d5\u0005\u0106\u0000\u0000\u03d5"+
		"\u03d7\u0003\u00f0x\u0000\u03d6\u03d2\u0001\u0000\u0000\u0000\u03d6\u03d4"+
		"\u0001\u0000\u0000\u0000\u03d7\u0407\u0001\u0000\u0000\u0000\u03d8\u03d9"+
		"\u0005\u0187\u0000\u0000\u03d9\u0407\u0005\u01bc\u0000\u0000\u03da\u03db"+
		"\u0005\u009a\u0000\u0000\u03db\u03df\u0005\u01bc\u0000\u0000\u03dc\u03de"+
		"\u0003*\u0015\u0000\u03dd\u03dc\u0001\u0000\u0000\u0000\u03de\u03e1\u0001"+
		"\u0000\u0000\u0000\u03df\u03dd\u0001\u0000\u0000\u0000\u03df\u03e0\u0001"+
		"\u0000\u0000\u0000\u03e0\u0407\u0001\u0000\u0000\u0000\u03e1\u03df\u0001"+
		"\u0000\u0000\u0000\u03e2\u03e3\u0005\u0161\u0000\u0000\u03e3\u0407\u0005"+
		"\u01bc\u0000\u0000\u03e4\u03e5\u0005A\u0000\u0000\u03e5\u03e7\u0005\u01bc"+
		"\u0000\u0000\u03e6\u03e8\u0003\u00f0x\u0000\u03e7\u03e6\u0001\u0000\u0000"+
		"\u0000\u03e7\u03e8\u0001\u0000\u0000\u0000\u03e8\u0407\u0001\u0000\u0000"+
		"\u0000\u03e9\u03ea\u0005\u0088\u0000\u0000\u03ea\u0407\u0007\u000b\u0000"+
		"\u0000\u03eb\u03ec\u0005\u0087\u0000\u0000\u03ec\u0407\u0007\f\u0000\u0000"+
		"\u03ed\u03ee\u0005\u0013\u0000\u0000\u03ee\u0407\u0003\u00f0x\u0000\u03ef"+
		"\u03f0\u0005\u001a\u0000\u0000\u03f0\u0407\u0003\u00f0x\u0000\u03f1\u03f2"+
		"\u0005\u001f\u0000\u0000\u03f2\u0407\u0007\u0005\u0000\u0000\u03f3\u03f4"+
		"\u0005\u0132\u0000\u0000\u03f4\u0407\u0007\u0005\u0000\u0000\u03f5\u03f6"+
		"\u0005\u013c\u0000\u0000\u03f6\u0407\u0007\u0005\u0000\u0000\u03f7\u03f8"+
		"\u0005\u0197\u0000\u0000\u03f8\u0407\u0007\u0005\u0000\u0000\u03f9\u0407"+
		"\u0005\u015e\u0000\u0000\u03fa\u03fb\u0005\u0097\u0000\u0000\u03fb\u0407"+
		"\u0005\u015e\u0000\u0000\u03fc\u03fd\u0005\u0164\u0000\u0000\u03fd\u0407"+
		"\u0005 \u0000\u0000\u03fe\u03ff\u0005\u0172\u0000\u0000\u03ff\u0407\u0007"+
		"\r\u0000\u0000\u0400\u0401\u0005\u0162\u0000\u0000\u0401\u0407\u0005\u01bc"+
		"\u0000\u0000\u0402\u0403\u0004\u001f\u0005\u0001\u0403\u0407\u0003\u00f8"+
		"|\u0000\u0404\u0405\u0004\u001f\u0006\u0001\u0405\u0407\u0003\u00fc~\u0000"+
		"\u0406\u03d1\u0001\u0000\u0000\u0000\u0406\u03d8\u0001\u0000\u0000\u0000"+
		"\u0406\u03da\u0001\u0000\u0000\u0000\u0406\u03e2\u0001\u0000\u0000\u0000"+
		"\u0406\u03e4\u0001\u0000\u0000\u0000\u0406\u03e9\u0001\u0000\u0000\u0000"+
		"\u0406\u03eb\u0001\u0000\u0000\u0000\u0406\u03ed\u0001\u0000\u0000\u0000"+
		"\u0406\u03ef\u0001\u0000\u0000\u0000\u0406\u03f1\u0001\u0000\u0000\u0000"+
		"\u0406\u03f3\u0001\u0000\u0000\u0000\u0406\u03f5\u0001\u0000\u0000\u0000"+
		"\u0406\u03f7\u0001\u0000\u0000\u0000\u0406\u03f9\u0001\u0000\u0000\u0000"+
		"\u0406\u03fa\u0001\u0000\u0000\u0000\u0406\u03fc\u0001\u0000\u0000\u0000"+
		"\u0406\u03fe\u0001\u0000\u0000\u0000\u0406\u0400\u0001\u0000\u0000\u0000"+
		"\u0406\u0402\u0001\u0000\u0000\u0000\u0406\u0404\u0001\u0000\u0000\u0000"+
		"\u0407?\u0001\u0000\u0000\u0000\u0408\u0409\u0005-\u0000\u0000\u0409\u040a"+
		"\u00052\u0000\u0000\u040a\u040e\u0003\u00f0x\u0000\u040b\u040d\u0003B"+
		"!\u0000\u040c\u040b\u0001\u0000\u0000\u0000\u040d\u0410\u0001\u0000\u0000"+
		"\u0000\u040e\u040c\u0001\u0000\u0000\u0000\u040e\u040f\u0001\u0000\u0000"+
		"\u0000\u040f\u0411\u0001\u0000\u0000\u0000\u0410\u040e\u0001\u0000\u0000"+
		"\u0000\u0411\u0412\u0003\u00fa}\u0000\u0412A\u0001\u0000\u0000\u0000\u0413"+
		"\u0414\u0005\f\u0000\u0000\u0414\u0417\u0005\u01a3\u0000\u0000\u0415\u0416"+
		"\u0005X\u0000\u0000\u0416\u0418\u0003\u00f0x\u0000\u0417\u0415\u0001\u0000"+
		"\u0000\u0000\u0417\u0418\u0001\u0000\u0000\u0000\u0418\u0423\u0001\u0000"+
		"\u0000\u0000\u0419\u041a\u0005\u0013\u0000\u0000\u041a\u0423\u0003\u00f0"+
		"x\u0000\u041b\u041c\u0005\u0154\u0000\u0000\u041c\u0423\u0003\u00f0x\u0000"+
		"\u041d\u041e\u0005\u00ea\u0000\u0000\u041e\u0423\u0003\u00f0x\u0000\u041f"+
		"\u0420\u0005\u001a\u0000\u0000\u0420\u0423\u0003\u00f0x\u0000\u0421\u0423"+
		"\u0003\u00fc~\u0000\u0422\u0413\u0001\u0000\u0000\u0000\u0422\u0419\u0001"+
		"\u0000\u0000\u0000\u0422\u041b\u0001\u0000\u0000\u0000\u0422\u041d\u0001"+
		"\u0000\u0000\u0000\u0422\u041f\u0001\u0000\u0000\u0000\u0422\u0421\u0001"+
		"\u0000\u0000\u0000\u0423C\u0001\u0000\u0000\u0000\u0424\u0425\u0005-\u0000"+
		"\u0000\u0425\u0426\u0005\u00ea\u0000\u0000\u0426\u042a\u0003\u00f0x\u0000"+
		"\u0427\u0429\u0003F#\u0000\u0428\u0427\u0001\u0000\u0000\u0000\u0429\u042c"+
		"\u0001\u0000\u0000\u0000\u042a\u0428\u0001\u0000\u0000\u0000\u042a\u042b"+
		"\u0001\u0000\u0000\u0000\u042b\u042d\u0001\u0000\u0000\u0000\u042c\u042a"+
		"\u0001\u0000\u0000\u0000\u042d\u042e\u0003\u00fa}\u0000\u042eE\u0001\u0000"+
		"\u0000\u0000\u042f\u0430\u0005\u0109\u0000\u0000\u0430\u0433\u0005\u01a6"+
		"\u0000\u0000\u0431\u0434\u0005\u01b8\u0000\u0000\u0432\u0434\u0003\u00f0"+
		"x\u0000\u0433\u0431\u0001\u0000\u0000\u0000\u0433\u0432\u0001\u0000\u0000"+
		"\u0000\u0434\u043c\u0001\u0000\u0000\u0000\u0435\u0438\u0005\u01a8\u0000"+
		"\u0000\u0436\u0439\u0005\u01b8\u0000\u0000\u0437\u0439\u0003\u00f0x\u0000"+
		"\u0438\u0436\u0001\u0000\u0000\u0000\u0438\u0437\u0001\u0000\u0000\u0000"+
		"\u0439\u043b\u0001\u0000\u0000\u0000\u043a\u0435\u0001\u0000\u0000\u0000"+
		"\u043b\u043e\u0001\u0000\u0000\u0000\u043c\u043a\u0001\u0000\u0000\u0000"+
		"\u043c\u043d\u0001\u0000\u0000\u0000\u043d\u043f\u0001\u0000\u0000\u0000"+
		"\u043e\u043c\u0001\u0000\u0000\u0000\u043f\u044a\u0005\u01a7\u0000\u0000"+
		"\u0440\u0441\u0005\u0106\u0000\u0000\u0441\u044a\u0003\u00f0x\u0000\u0442"+
		"\u0443\u0005\u0137\u0000\u0000\u0443\u044a\u0003\u00f0x\u0000\u0444\u0445"+
		"\u0005\u0165\u0000\u0000\u0445\u044a\u0003\u00f0x\u0000\u0446\u0447\u0005"+
		"\u0192\u0000\u0000\u0447\u044a\u0003\u00f0x\u0000\u0448\u044a\u0003\u00fc"+
		"~\u0000\u0449\u042f\u0001\u0000\u0000\u0000\u0449\u0440\u0001\u0000\u0000"+
		"\u0000\u0449\u0442\u0001\u0000\u0000\u0000\u0449\u0444\u0001\u0000\u0000"+
		"\u0000\u0449\u0446\u0001\u0000\u0000\u0000\u0449\u0448\u0001\u0000\u0000"+
		"\u0000\u044aG\u0001\u0000\u0000\u0000\u044b\u044c\u0005-\u0000\u0000\u044c"+
		"\u044d\u0005\u0107\u0000\u0000\u044d\u044f\u0003\u00eew\u0000\u044e\u0450"+
		"\u0003\"\u0011\u0000\u044f\u044e\u0001\u0000\u0000\u0000\u044f\u0450\u0001"+
		"\u0000\u0000\u0000\u0450\u0451\u0001\u0000\u0000\u0000\u0451\u0452\u0005"+
		"\f\u0000\u0000\u0452\u0456\u0003\u00ceg\u0000\u0453\u0455\u0003J%\u0000"+
		"\u0454\u0453\u0001\u0000\u0000\u0000\u0455\u0458\u0001\u0000\u0000\u0000"+
		"\u0456\u0454\u0001\u0000\u0000\u0000\u0456\u0457\u0001\u0000\u0000\u0000"+
		"\u0457\u0459\u0001\u0000\u0000\u0000\u0458\u0456\u0001\u0000\u0000\u0000"+
		"\u0459\u045a\u0003\u00fa}\u0000\u045aI\u0001\u0000\u0000\u0000\u045b\u045d"+
		"\u0005\u010e\u0000\u0000\u045c\u045e\u0007\u000e\u0000\u0000\u045d\u045c"+
		"\u0001\u0000\u0000\u0000\u045d\u045e\u0001\u0000\u0000\u0000\u045e\u045f"+
		"\u0001\u0000\u0000\u0000\u045f\u0460\u0005\u001d\u0000\u0000\u0460\u0461"+
		"\u0005\u016e\u0000\u0000\u0461K\u0001\u0000\u0000\u0000\u0462\u0464\u0005"+
		"-\u0000\u0000\u0463\u0465\u0005\u00ba\u0000\u0000\u0464\u0463\u0001\u0000"+
		"\u0000\u0000\u0464\u0465\u0001\u0000\u0000\u0000\u0465\u0466\u0001\u0000"+
		"\u0000\u0000\u0466\u0467\u0005\u011f\u0000\u0000\u0467\u0468\u0003\u00ee"+
		"w\u0000\u0468\u046a\u0005X\u0000\u0000\u0469\u046b\u0007\u000f\u0000\u0000"+
		"\u046a\u0469\u0001\u0000\u0000\u0000\u046a\u046b\u0001\u0000\u0000\u0000"+
		"\u046b\u046c\u0001\u0000\u0000\u0000\u046c\u046d\u0003\u00eew\u0000\u046d"+
		"\u046e\u0003\u00fa}\u0000\u046eM\u0001\u0000\u0000\u0000\u046f\u0470\u0005"+
		"-\u0000\u0000\u0470\u0471\u0005\u00ee\u0000\u0000\u0471\u0472\u0003\u00f0"+
		"x\u0000\u0472\u0473\u0005X\u0000\u0000\u0473\u0474\u0003\u00eew\u0000"+
		"\u0474\u0475\u0003\u00fa}\u0000\u0475O\u0001\u0000\u0000\u0000\u0476\u0477"+
		"\u0005-\u0000\u0000\u0477\u0478\u0005\u00de\u0000\u0000\u0478\u047b\u0003"+
		"\u00eew\u0000\u0479\u047a\u0005\f\u0000\u0000\u047a\u047c\u0003\u00f2"+
		"y\u0000\u047b\u0479\u0001\u0000\u0000\u0000\u047b\u047c\u0001\u0000\u0000"+
		"\u0000\u047c\u0480\u0001\u0000\u0000\u0000\u047d\u047f\u0003R)\u0000\u047e"+
		"\u047d\u0001\u0000\u0000\u0000\u047f\u0482\u0001\u0000\u0000\u0000\u0480"+
		"\u047e\u0001\u0000\u0000\u0000\u0480\u0481\u0001\u0000\u0000\u0000\u0481"+
		"\u0483\u0001\u0000\u0000\u0000\u0482\u0480\u0001\u0000\u0000\u0000\u0483"+
		"\u0484\u0003\u00fa}\u0000\u0484Q\u0001\u0000\u0000\u0000\u0485\u0486\u0005"+
		"\u018f\u0000\u0000\u0486\u0487\u0005\u010e\u0000\u0000\u0487\u04a9\u0003"+
		"\u00ecv\u0000\u0488\u0489\u0005\u0153\u0000\u0000\u0489\u048a\u0005\u0014"+
		"\u0000\u0000\u048a\u04a9\u0003\u00ecv\u0000\u048b\u048c\u0005\u0166\u0000"+
		"\u0000\u048c\u0490\u0003\u00ecv\u0000\u048d\u048e\u0005\u0095\u0000\u0000"+
		"\u048e\u0490\u0005\u0166\u0000\u0000\u048f\u048b\u0001\u0000\u0000\u0000"+
		"\u048f\u048d\u0001\u0000\u0000\u0000\u0490\u04a9\u0001\u0000\u0000\u0000"+
		"\u0491\u0492\u0005\u0163\u0000\u0000\u0492\u0496\u0003\u00ecv\u0000\u0493"+
		"\u0494\u0005\u0095\u0000\u0000\u0494\u0496\u0005\u0163\u0000\u0000\u0495"+
		"\u0491\u0001\u0000\u0000\u0000\u0495\u0493\u0001\u0000\u0000\u0000\u0496"+
		"\u04a9\u0001\u0000\u0000\u0000\u0497\u049b\u0005\u0136\u0000\u0000\u0498"+
		"\u0499\u0005\u0095\u0000\u0000\u0499\u049b\u0005\u0136\u0000\u0000\u049a"+
		"\u0497\u0001\u0000\u0000\u0000\u049a\u0498\u0001\u0000\u0000\u0000\u049b"+
		"\u04a9\u0001\u0000\u0000\u0000\u049c\u049d\u0005\u012a\u0000\u0000\u049d"+
		"\u04a1\u0005\u01bc\u0000\u0000\u049e\u049f\u0005\u0095\u0000\u0000\u049f"+
		"\u04a1\u0005\u012a\u0000\u0000\u04a0\u049c\u0001\u0000\u0000\u0000\u04a0"+
		"\u049e\u0001\u0000\u0000\u0000\u04a1\u04a9\u0001\u0000\u0000\u0000\u04a2"+
		"\u04a6\u0005\u00a3\u0000\u0000\u04a3\u04a4\u0005\u0095\u0000\u0000\u04a4"+
		"\u04a6\u0005\u00a3\u0000\u0000\u04a5\u04a2\u0001\u0000\u0000\u0000\u04a5"+
		"\u04a3\u0001\u0000\u0000\u0000\u04a6\u04a9\u0001\u0000\u0000\u0000\u04a7"+
		"\u04a9\u0003\u00fc~\u0000\u04a8\u0485\u0001\u0000\u0000\u0000\u04a8\u0488"+
		"\u0001\u0000\u0000\u0000\u04a8\u048f\u0001\u0000\u0000\u0000\u04a8\u0495"+
		"\u0001\u0000\u0000\u0000\u04a8\u049a\u0001\u0000\u0000\u0000\u04a8\u04a0"+
		"\u0001\u0000\u0000\u0000\u04a8\u04a5\u0001\u0000\u0000\u0000\u04a8\u04a7"+
		"\u0001\u0000\u0000\u0000\u04a9S\u0001\u0000\u0000\u0000\u04aa\u04ab\u0005"+
		"-\u0000\u0000\u04ab\u04ac\u0005\u00ca\u0000\u0000\u04ac\u04ad\u0003\u00f0"+
		"x\u0000\u04ad\u04ae\u0003\u00fa}\u0000\u04aeU\u0001\u0000\u0000\u0000"+
		"\u04af\u04b0\u0005-\u0000\u0000\u04b0\u04b1\u0007\u0010\u0000\u0000\u04b1"+
		"\u04b2\u0005\u00f3\u0000\u0000\u04b2\u04b5\u0003\u00eew\u0000\u04b3\u04b4"+
		"\u0005k\u0000\u0000\u04b4\u04b6\u0003\u00eew\u0000\u04b5\u04b3\u0001\u0000"+
		"\u0000\u0000\u04b5\u04b6\u0001\u0000\u0000\u0000\u04b6\u04b7\u0001\u0000"+
		"\u0000\u0000\u04b7\u04b8\u0005\u00eb\u0000\u0000\u04b8\u04bb\u0003\u00ee"+
		"w\u0000\u04b9\u04ba\u0005\u0121\u0000\u0000\u04ba\u04bc\u0007\u0005\u0000"+
		"\u0000\u04bb\u04b9\u0001\u0000\u0000\u0000\u04bb\u04bc\u0001\u0000\u0000"+
		"\u0000\u04bc\u04bf\u0001\u0000\u0000\u0000\u04bd\u04be\u0005#\u0000\u0000"+
		"\u04be\u04c0\u0003\u00f0x\u0000\u04bf\u04bd\u0001\u0000\u0000\u0000\u04bf"+
		"\u04c0\u0001\u0000\u0000\u0000\u04c0\u04c3\u0001\u0000\u0000\u0000\u04c1"+
		"\u04c2\u0005\u00a9\u0000\u0000\u04c2\u04c4\u0005\u01bc\u0000\u0000\u04c3"+
		"\u04c1\u0001\u0000\u0000\u0000\u04c3\u04c4\u0001\u0000\u0000\u0000\u04c4"+
		"\u04c5\u0001\u0000\u0000\u0000\u04c5\u04c6\u0003\u00fa}\u0000\u04c6W\u0001"+
		"\u0000\u0000\u0000\u04c7\u04c9\u0005-\u0000\u0000\u04c8\u04ca\u0005=\u0000"+
		"\u0000\u04c9\u04c8\u0001\u0000\u0000\u0000\u04c9\u04ca\u0001\u0000\u0000"+
		"\u0000\u04ca\u04cb\u0001\u0000\u0000\u0000\u04cb\u04cc\u0005\u00f9\u0000"+
		"\u0000\u04cc\u04cd\u0003\u00eew\u0000\u04cd\u04ce\u0005\f\u0000\u0000"+
		"\u04ce\u04d1\u0003\u00f2y\u0000\u04cf\u04d0\u0005\u010e\u0000\u0000\u04d0"+
		"\u04d2\u0005\u0131\u0000\u0000\u04d1\u04cf\u0001\u0000\u0000\u0000\u04d1"+
		"\u04d2\u0001\u0000\u0000\u0000\u04d2\u04d3\u0001\u0000\u0000\u0000\u04d3"+
		"\u04d4\u0003\u00fa}\u0000\u04d4Y\u0001\u0000\u0000\u0000\u04d5\u04d6\u0005"+
		"-\u0000\u0000\u04d6\u04d7\u0005\u0104\u0000\u0000\u04d7\u04d8\u0003\u00ee"+
		"w\u0000\u04d8\u04db\u0003\u00f2y\u0000\u04d9\u04da\u00057\u0000\u0000"+
		"\u04da\u04dc\u0003\u0016\u000b\u0000\u04db\u04d9\u0001\u0000\u0000\u0000"+
		"\u04db\u04dc\u0001\u0000\u0000\u0000\u04dc\u04dd\u0001\u0000\u0000\u0000"+
		"\u04dd\u04de\u0003\u00fa}\u0000\u04de[\u0001\u0000\u0000\u0000\u04df\u04e0"+
		"\u0005-\u0000\u0000\u04e0\u04e1\u0005\u0160\u0000\u0000\u04e1\u04e2\u0003"+
		"\u00eew\u0000\u04e2\u04e3\u0005\u009e\u0000\u0000\u04e3\u04e6\u0003\u00ee"+
		"w\u0000\u04e4\u04e5\u0005\f\u0000\u0000\u04e5\u04e7\u0003\u00f0x\u0000"+
		"\u04e6\u04e4\u0001\u0000\u0000\u0000\u04e6\u04e7\u0001\u0000\u0000\u0000"+
		"\u04e7\u04e8\u0001\u0000\u0000\u0000\u04e8\u04e9\u0005X\u0000\u0000\u04e9"+
		"\u04ea\u0005#\u0000\u0000\u04ea\u04eb\u0003\u00f0x\u0000\u04eb\u04ec\u0005"+
		"\u00c6\u0000\u0000\u04ec\u04ee\u0003h4\u0000\u04ed\u04ef\u0007\u0011\u0000"+
		"\u0000\u04ee\u04ed\u0001\u0000\u0000\u0000\u04ee\u04ef\u0001\u0000\u0000"+
		"\u0000\u04ef\u04f0\u0001\u0000\u0000\u0000\u04f0\u04f1\u0003\u00fa}\u0000"+
		"\u04f1]\u0001\u0000\u0000\u0000\u04f2\u04f3\u0005-\u0000\u0000\u04f3\u04f4"+
		"\u0005\u0174\u0000\u0000\u04f4\u04f5\u0003\u00eew\u0000\u04f5\u04f6\u0005"+
		"\u009e\u0000\u0000\u04f6\u04f9\u0003\u00eew\u0000\u04f7\u04f8\u0005\f"+
		"\u0000\u0000\u04f8\u04fa\u0003\u00f0x\u0000\u04f9\u04f7\u0001\u0000\u0000"+
		"\u0000\u04f9\u04fa\u0001\u0000\u0000\u0000\u04fa\u04fb\u0001\u0000\u0000"+
		"\u0000\u04fb\u04fc\u0005X\u0000\u0000\u04fc\u04fd\u0005\u00d4\u0000\u0000"+
		"\u04fd\u04ff\u0005\u011c\u0000\u0000\u04fe\u0500\u0005\u0119\u0000\u0000"+
		"\u04ff\u04fe\u0001\u0000\u0000\u0000\u04ff\u0500\u0001\u0000\u0000\u0000"+
		"\u0500\u0502\u0001\u0000\u0000\u0000\u0501\u0503\u0005\u0100\u0000\u0000"+
		"\u0502\u0501\u0001\u0000\u0000\u0000\u0502\u0503\u0001\u0000\u0000\u0000"+
		"\u0503\u0504\u0001\u0000\u0000\u0000\u0504\u0505\u0005\u010a\u0000\u0000"+
		"\u0505\u0506\u0005\u01a6\u0000\u0000\u0506\u0507\u0003\u00deo\u0000\u0507"+
		"\u050c\u0005\u01a7\u0000\u0000\u0508\u0509\u0005\u0141\u0000\u0000\u0509"+
		"\u050a\u0005X\u0000\u0000\u050a\u050b\u0005\u0006\u0000\u0000\u050b\u050d"+
		"\u0005\u011c\u0000\u0000\u050c\u0508\u0001\u0000\u0000\u0000\u050c\u050d"+
		"\u0001\u0000\u0000\u0000\u050d\u050f\u0001\u0000\u0000\u0000\u050e\u0510"+
		"\u0007\u0011\u0000\u0000\u050f\u050e\u0001\u0000\u0000\u0000\u050f\u0510"+
		"\u0001\u0000\u0000\u0000\u0510\u0511\u0001\u0000\u0000\u0000\u0511\u0512"+
		"\u0003\u00fa}\u0000\u0512_\u0001\u0000\u0000\u0000\u0513\u0514\u0005-"+
		"\u0000\u0000\u0514\u0515\u0005\u0199\u0000\u0000\u0515\u0516\u0005\u0133"+
		"\u0000\u0000\u0516\u0517\u0003\u00f0x\u0000\u0517\u0518\u0005\u0118\u0000"+
		"\u0000\u0518\u0519\u0005\u011a\u0000\u0000\u0519\u051a\u0005)\u0000\u0000"+
		"\u051a\u051b\u0005\u0100\u0000\u0000\u051b\u051c\u0005\u00f2\u0000\u0000"+
		"\u051c\u051d\u0005\u0117\u0000\u0000\u051d\u0521\u0003\u00f0x\u0000\u051e"+
		"\u0520\u0003b1\u0000\u051f\u051e\u0001\u0000\u0000\u0000\u0520\u0523\u0001"+
		"\u0000\u0000\u0000\u0521\u051f\u0001\u0000\u0000\u0000\u0521\u0522\u0001"+
		"\u0000\u0000\u0000\u0522\u0524\u0001\u0000\u0000\u0000\u0523\u0521\u0001"+
		"\u0000\u0000\u0000\u0524\u0525\u0003\u00fa}\u0000\u0525a\u0001\u0000\u0000"+
		"\u0000\u0526\u0527\u0005\u0126\u0000\u0000\u0527\u0528\u0005\u01a6\u0000"+
		"\u0000\u0528\u052d\u0003d2\u0000\u0529\u052a\u0005\u01a8\u0000\u0000\u052a"+
		"\u052c\u0003d2\u0000\u052b\u0529\u0001\u0000\u0000\u0000\u052c\u052f\u0001"+
		"\u0000\u0000\u0000\u052d\u052b\u0001\u0000\u0000\u0000\u052d\u052e\u0001"+
		"\u0000\u0000\u0000\u052e\u0530\u0001\u0000\u0000\u0000\u052f\u052d\u0001"+
		"\u0000\u0000\u0000\u0530\u0531\u0005\u01a7\u0000\u0000\u0531\u0548\u0001"+
		"\u0000\u0000\u0000\u0532\u0548\u0007\u0011\u0000\u0000\u0533\u0535\u0005"+
		"\u0095\u0000\u0000\u0534\u0533\u0001\u0000\u0000\u0000\u0534\u0535\u0001"+
		"\u0000\u0000\u0000\u0535\u0536\u0001\u0000\u0000\u0000\u0536\u0537\u0005"+
		"7\u0000\u0000\u0537\u0539\u0005\u00ca\u0000\u0000\u0538\u053a\u0003\u00f0"+
		"x\u0000\u0539\u0538\u0001\u0000\u0000\u0000\u0539\u053a\u0001\u0000\u0000"+
		"\u0000\u053a\u0548\u0001\u0000\u0000\u0000\u053b\u053c\u0005\u010e\u0000"+
		"\u0000\u053c\u053d\u0005\u019c\u0000\u0000\u053d\u053e\u0005X\u0000\u0000"+
		"\u053e\u0543\u0003f3\u0000\u053f\u0540\u0005\u01a8\u0000\u0000\u0540\u0542"+
		"\u0003f3\u0000\u0541\u053f\u0001\u0000\u0000\u0000\u0542\u0545\u0001\u0000"+
		"\u0000\u0000\u0543\u0541\u0001\u0000\u0000\u0000\u0543\u0544\u0001\u0000"+
		"\u0000\u0000\u0544\u0548\u0001\u0000\u0000\u0000\u0545\u0543\u0001\u0000"+
		"\u0000\u0000\u0546\u0548\u0003\u00fc~\u0000\u0547\u0526\u0001\u0000\u0000"+
		"\u0000\u0547\u0532\u0001\u0000\u0000\u0000\u0547\u0534\u0001\u0000\u0000"+
		"\u0000\u0547\u053b\u0001\u0000\u0000\u0000\u0547\u0546\u0001\u0000\u0000"+
		"\u0000\u0548c\u0001\u0000\u0000\u0000\u0549\u054c\u0003\u00fc~\u0000\u054a"+
		"\u054d\u0005\u01b1\u0000\u0000\u054b\u054d\u0001\u0000\u0000\u0000\u054c"+
		"\u054a\u0001\u0000\u0000\u0000\u054c\u054b\u0001\u0000\u0000\u0000\u054d"+
		"\u054e\u0001\u0000\u0000\u0000\u054e\u054f\u0005\u01b8\u0000\u0000\u054f"+
		"e\u0001\u0000\u0000\u0000\u0550\u0553\u0003\u00f0x\u0000\u0551\u0553\u0005"+
		"\u00ba\u0000\u0000\u0552\u0550\u0001\u0000\u0000\u0000\u0552\u0551\u0001"+
		"\u0000\u0000\u0000\u0553\u0556\u0001\u0000\u0000\u0000\u0554\u0555\u0005"+
		"\u00ca\u0000\u0000\u0555\u0557\u0003\u00f0x\u0000\u0556\u0554\u0001\u0000"+
		"\u0000\u0000\u0556\u0557\u0001\u0000\u0000\u0000\u0557\u0559\u0001\u0000"+
		"\u0000\u0000\u0558\u055a\u0007\u0012\u0000\u0000\u0559\u0558\u0001\u0000"+
		"\u0000\u0000\u0559\u055a\u0001\u0000\u0000\u0000\u055a\u055c\u0001\u0000"+
		"\u0000\u0000\u055b\u055d\u0005\u0116\u0000\u0000\u055c\u055b\u0001\u0000"+
		"\u0000\u0000\u055c\u055d\u0001\u0000\u0000\u0000\u055dg\u0001\u0000\u0000"+
		"\u0000\u055e\u0560\u0005\u0018\u0000\u0000\u055f\u0561\u0003\u00e6s\u0000"+
		"\u0560\u055f\u0001\u0000\u0000\u0000\u0561\u0562\u0001\u0000\u0000\u0000"+
		"\u0562\u0560\u0001\u0000\u0000\u0000\u0562\u0563\u0001\u0000\u0000\u0000"+
		"\u0563\u0566\u0001\u0000\u0000\u0000\u0564\u0565\u0005D\u0000\u0000\u0565"+
		"\u0567\u0003\u00e4r\u0000\u0566\u0564\u0001\u0000\u0000\u0000\u0566\u0567"+
		"\u0001\u0000\u0000\u0000\u0567\u0568\u0001\u0000\u0000\u0000\u0568\u0569"+
		"\u0005G\u0000\u0000\u0569\u056c\u0001\u0000\u0000\u0000\u056a\u056c\u0003"+
		"\u00e4r\u0000\u056b\u055e\u0001\u0000\u0000\u0000\u056b\u056a\u0001\u0000"+
		"\u0000\u0000\u056ci\u0001\u0000\u0000\u0000\u056d\u056e\u00056\u0000\u0000"+
		"\u056e\u056f\u0005_\u0000\u0000\u056f\u0570\u0005\u0194\u0000\u0000\u0570"+
		"\u0571\u0005\u00f3\u0000\u0000\u0571\u0572\u0003\u00eew\u0000\u0572\u0576"+
		"\u0003\n\u0005\u0000\u0573\u0575\u0003$\u0012\u0000\u0574\u0573\u0001"+
		"\u0000\u0000\u0000\u0575\u0578\u0001\u0000\u0000\u0000\u0576\u0574\u0001"+
		"\u0000\u0000\u0000\u0576\u0577\u0001\u0000\u0000\u0000\u0577\u0579\u0001"+
		"\u0000\u0000\u0000\u0578\u0576\u0001\u0000\u0000\u0000\u0579\u057a\u0003"+
		"\u00fa}\u0000\u057ak\u0001\u0000\u0000\u0000\u057b\u057c\u0005-\u0000"+
		"\u0000\u057c\u057d\u0005\u00f7\u0000\u0000\u057d\u0580\u0003\u00eew\u0000"+
		"\u057e\u057f\u0005\u0095\u0000\u0000\u057f\u0581\u0005\u012d\u0000\u0000"+
		"\u0580\u057e\u0001\u0000\u0000\u0000\u0580\u0581\u0001\u0000\u0000\u0000"+
		"\u0581\u0586\u0001\u0000\u0000\u0000\u0582\u0587\u0005\u0010\u0000\u0000"+
		"\u0583\u0587\u0005\u0005\u0000\u0000\u0584\u0585\u0005\u0157\u0000\u0000"+
		"\u0585\u0587\u0005\u009c\u0000\u0000\u0586\u0582\u0001\u0000\u0000\u0000"+
		"\u0586\u0583\u0001\u0000\u0000\u0000\u0586\u0584\u0001\u0000\u0000\u0000"+
		"\u0587\u0588\u0001\u0000\u0000\u0000\u0588\u058d\u0003n7\u0000\u0589\u058a"+
		"\u0005\u00a2\u0000\u0000\u058a\u058c\u0003n7\u0000\u058b\u0589\u0001\u0000"+
		"\u0000\u0000\u058c\u058f\u0001\u0000\u0000\u0000\u058d\u058b\u0001\u0000"+
		"\u0000\u0000\u058d\u058e\u0001\u0000\u0000\u0000\u058e\u0590\u0001\u0000"+
		"\u0000\u0000\u058f\u058d\u0001\u0000\u0000\u0000\u0590\u0591\u0005\u009e"+
		"\u0000\u0000\u0591\u0595\u0003\u00eew\u0000\u0592\u0594\u0003p8\u0000"+
		"\u0593\u0592\u0001\u0000\u0000\u0000\u0594\u0597\u0001\u0000\u0000\u0000"+
		"\u0595\u0593\u0001\u0000\u0000\u0000\u0595\u0596\u0001\u0000\u0000\u0000"+
		"\u0596\u0599\u0001\u0000\u0000\u0000\u0597\u0595\u0001\u0000\u0000\u0000"+
		"\u0598\u059a\u0003r9\u0000\u0599\u0598\u0001\u0000\u0000\u0000\u0599\u059a"+
		"\u0001\u0000\u0000\u0000\u059a\u059d\u0001\u0000\u0000\u0000\u059b\u059e"+
		"\u0003|>\u0000\u059c\u059e\u0003\u0080@\u0000\u059d\u059b\u0001\u0000"+
		"\u0000\u0000\u059d\u059c\u0001\u0000\u0000\u0000\u059e\u059f\u0001\u0000"+
		"\u0000\u0000\u059f\u05a0\u0003\u00fa}\u0000\u05a0m\u0001\u0000\u0000\u0000"+
		"\u05a1\u05b0\u0005r\u0000\u0000\u05a2\u05b0\u00058\u0000\u0000\u05a3\u05ad"+
		"\u0005\u00fe\u0000\u0000\u05a4\u05a5\u0005\u009c\u0000\u0000\u05a5\u05aa"+
		"\u0003\u00f0x\u0000\u05a6\u05a7\u0005\u01a8\u0000\u0000\u05a7\u05a9\u0003"+
		"\u00f0x\u0000\u05a8\u05a6\u0001\u0000\u0000\u0000\u05a9\u05ac\u0001\u0000"+
		"\u0000\u0000\u05aa\u05a8\u0001\u0000\u0000\u0000\u05aa\u05ab\u0001\u0000"+
		"\u0000\u0000\u05ab\u05ae\u0001\u0000\u0000\u0000\u05ac\u05aa\u0001\u0000"+
		"\u0000\u0000\u05ad\u05a4\u0001\u0000\u0000\u0000\u05ad\u05ae\u0001\u0000"+
		"\u0000\u0000\u05ae\u05b0\u0001\u0000\u0000\u0000\u05af\u05a1\u0001\u0000"+
		"\u0000\u0000\u05af\u05a2\u0001\u0000\u0000\u0000\u05af\u05a3\u0001\u0000"+
		"\u0000\u0000\u05b0o\u0001\u0000\u0000\u0000\u05b1\u05b2\u0005\u0179\u0000"+
		"\u0000\u05b2\u05b4\u0007\u0013\u0000\u0000\u05b3\u05b5\u0005\u00d4\u0000"+
		"\u0000\u05b4\u05b3\u0001\u0000\u0000\u0000\u05b4\u05b5\u0001\u0000\u0000"+
		"\u0000\u05b5\u05b7\u0001\u0000\u0000\u0000\u05b6\u05b8\u0005\f\u0000\u0000"+
		"\u05b7\u05b6\u0001\u0000\u0000\u0000\u05b7\u05b8\u0001\u0000\u0000\u0000"+
		"\u05b8\u05b9\u0001\u0000\u0000\u0000\u05b9\u05ba\u0003\u00f0x\u0000\u05ba"+
		"q\u0001\u0000\u0000\u0000\u05bb\u05bc\u0005X\u0000\u0000\u05bc\u05bd\u0005"+
		"\u013f\u0000\u0000\u05bd\u05c0\u0007\u0014\u0000\u0000\u05be\u05bf\u0005"+
		"\u0168\u0000\u0000\u05bf\u05c1\u0005\u0139\u0000\u0000\u05c0\u05be\u0001"+
		"\u0000\u0000\u0000\u05c0\u05c1\u0001\u0000\u0000\u0000\u05c1s\u0001\u0000"+
		"\u0000\u0000\u05c2\u05c5\u0005-\u0000\u0000\u05c3\u05c4\u0005\u00a2\u0000"+
		"\u0000\u05c4\u05c6\u0005\u017e\u0000\u0000\u05c5\u05c3\u0001\u0000\u0000"+
		"\u0000\u05c5\u05c6\u0001\u0000\u0000\u0000\u05c6\u05c7\u0001\u0000\u0000"+
		"\u0000\u05c7\u05c8\u0005\u00b7\u0000\u0000\u05c8\u05d5\u0003\u00eew\u0000"+
		"\u05c9\u05d2\u0005\u01a6\u0000\u0000\u05ca\u05cf\u0003x<\u0000\u05cb\u05cc"+
		"\u0005\u01a8\u0000\u0000\u05cc\u05ce\u0003x<\u0000\u05cd\u05cb\u0001\u0000"+
		"\u0000\u0000\u05ce\u05d1\u0001\u0000\u0000\u0000\u05cf\u05cd\u0001\u0000"+
		"\u0000\u0000\u05cf\u05d0\u0001\u0000\u0000\u0000\u05d0\u05d3\u0001\u0000"+
		"\u0000\u0000\u05d1\u05cf\u0001\u0000\u0000\u0000\u05d2\u05ca\u0001\u0000"+
		"\u0000\u0000\u05d2\u05d3\u0001\u0000\u0000\u0000\u05d3\u05d4\u0001\u0000"+
		"\u0000\u0000\u05d4\u05d6\u0005\u01a7\u0000\u0000\u05d5\u05c9\u0001\u0000"+
		"\u0000\u0000\u05d5\u05d6\u0001\u0000\u0000\u0000\u05d6\u05da\u0001\u0000"+
		"\u0000\u0000\u05d7\u05d9\u0003z=\u0000\u05d8\u05d7\u0001\u0000\u0000\u0000"+
		"\u05d9\u05dc\u0001\u0000\u0000\u0000\u05da\u05d8\u0001\u0000\u0000\u0000"+
		"\u05da\u05db\u0001\u0000\u0000\u0000\u05db\u05de\u0001\u0000\u0000\u0000"+
		"\u05dc\u05da\u0001\u0000\u0000\u0000\u05dd\u05df\u0003|>\u0000\u05de\u05dd"+
		"\u0001\u0000\u0000\u0000\u05de\u05df\u0001\u0000\u0000\u0000\u05df\u05e0"+
		"\u0001\u0000\u0000\u0000\u05e0\u05e1\u0003\u00fa}\u0000\u05e1u\u0001\u0000"+
		"\u0000\u0000\u05e2\u05e5\u0005-\u0000\u0000\u05e3\u05e4\u0005\u00a2\u0000"+
		"\u0000\u05e4\u05e6\u0005\u017e\u0000\u0000\u05e5\u05e3\u0001\u0000\u0000"+
		"\u0000\u05e5\u05e6\u0001\u0000\u0000\u0000\u05e6\u05e7\u0001\u0000\u0000"+
		"\u0000\u05e7\u05e8\u0005\\\u0000\u0000\u05e8\u05e9\u0003\u00eew\u0000"+
		"\u05e9\u05f2\u0005\u01a6\u0000\u0000\u05ea\u05ef\u0003x<\u0000\u05eb\u05ec"+
		"\u0005\u01a8\u0000\u0000\u05ec\u05ee\u0003x<\u0000\u05ed\u05eb\u0001\u0000"+
		"\u0000\u0000\u05ee\u05f1\u0001\u0000\u0000\u0000\u05ef\u05ed\u0001\u0000"+
		"\u0000\u0000\u05ef\u05f0\u0001\u0000\u0000\u0000\u05f0\u05f3\u0001\u0000"+
		"\u0000\u0000\u05f1\u05ef\u0001\u0000\u0000\u0000\u05f2\u05ea\u0001\u0000"+
		"\u0000\u0000\u05f2\u05f3\u0001\u0000\u0000\u0000\u05f3\u05f4\u0001\u0000"+
		"\u0000\u0000\u05f4\u05f8\u0005\u01a7\u0000\u0000\u05f5\u05f7\u0003z=\u0000"+
		"\u05f6\u05f5\u0001\u0000\u0000\u0000\u05f7\u05fa\u0001\u0000\u0000\u0000"+
		"\u05f8\u05f6\u0001\u0000\u0000\u0000\u05f8\u05f9\u0001\u0000\u0000\u0000"+
		"\u05f9\u05fc\u0001\u0000\u0000\u0000\u05fa\u05f8\u0001\u0000\u0000\u0000"+
		"\u05fb\u05fd\u0003|>\u0000\u05fc\u05fb\u0001\u0000\u0000\u0000\u05fc\u05fd"+
		"\u0001\u0000\u0000\u0000\u05fd\u05fe\u0001\u0000\u0000\u0000\u05fe\u05ff"+
		"\u0003\u00fa}\u0000\u05ffw\u0001\u0000\u0000\u0000\u0600\u0602\u0007\u0015"+
		"\u0000\u0000\u0601\u0600\u0001\u0000\u0000\u0000\u0601\u0602\u0001\u0000"+
		"\u0000\u0000\u0602\u0604\u0001\u0000\u0000\u0000\u0603\u0605\u0003\u00f0"+
		"x\u0000\u0604\u0603\u0001\u0000\u0000\u0000\u0604\u0605\u0001\u0000\u0000"+
		"\u0000\u0605\u0606\u0001\u0000\u0000\u0000\u0606\u0607\u0003\u00f2y\u0000"+
		"\u0607y\u0001\u0000\u0000\u0000\u0608\u0616\u0005\u00c7\u0000\u0000\u0609"+
		"\u0617\u0003\u00f2y\u0000\u060a\u060b\u0005\u00f3\u0000\u0000\u060b\u060c"+
		"\u0005\u01a6\u0000\u0000\u060c\u0611\u0003x<\u0000\u060d\u060e\u0005\u01a8"+
		"\u0000\u0000\u060e\u0610\u0003x<\u0000\u060f\u060d\u0001\u0000\u0000\u0000"+
		"\u0610\u0613\u0001\u0000\u0000\u0000\u0611\u060f\u0001\u0000\u0000\u0000"+
		"\u0611\u0612\u0001\u0000\u0000\u0000\u0612\u0614\u0001\u0000\u0000\u0000"+
		"\u0613\u0611\u0001\u0000\u0000\u0000\u0614\u0615\u0005\u01a7\u0000\u0000"+
		"\u0615\u0617\u0001\u0000\u0000\u0000\u0616\u0609\u0001\u0000\u0000\u0000"+
		"\u0616\u060a\u0001\u0000\u0000\u0000\u0617\u0672\u0001\u0000\u0000\u0000"+
		"\u0618\u0619\u0005|\u0000\u0000\u0619\u0672\u0003\u00f0x\u0000\u061a\u0620"+
		"\u0005R\u0000\u0000\u061b\u061e\u0005\u0169\u0000\u0000\u061c\u061f\u0005"+
		"\u01b8\u0000\u0000\u061d\u061f\u0003\u00f0x\u0000\u061e\u061c\u0001\u0000"+
		"\u0000\u0000\u061e\u061d\u0001\u0000\u0000\u0000\u061f\u0621\u0001\u0000"+
		"\u0000\u0000\u0620\u061b\u0001\u0000\u0000\u0000\u0620\u0621\u0001\u0000"+
		"\u0000\u0000\u0621\u0672\u0001\u0000\u0000\u0000\u0622\u0623\u0005\u00a8"+
		"\u0000\u0000\u0623\u0624\u0005\u00ec\u0000\u0000\u0624\u0672\u0003\u00f0"+
		"x\u0000\u0625\u0626\u0005\u00e6\u0000\u0000\u0626\u0672\u0003\u00eew\u0000"+
		"\u0627\u0629\u0005\u0097\u0000\u0000\u0628\u0627\u0001\u0000\u0000\u0000"+
		"\u0628\u0629\u0001\u0000\u0000\u0000\u0629\u062a\u0001\u0000\u0000\u0000"+
		"\u062a\u0672\u0005:\u0000\u0000\u062b\u062c\u0005+\u0000\u0000\u062c\u0636"+
		"\u0005\u018d\u0000\u0000\u062d\u062e\u0005\u00bd\u0000\u0000\u062e\u062f"+
		"\u0005\u018d\u0000\u0000\u062f\u0636\u00051\u0000\u0000\u0630\u0631\u0005"+
		"\u0091\u0000\u0000\u0631\u0632\u0005\u018d\u0000\u0000\u0632\u0636\u0005"+
		"1\u0000\u0000\u0633\u0634\u0005\u0095\u0000\u0000\u0634\u0636\u0005\u018d"+
		"\u0000\u0000\u0635\u062b\u0001\u0000\u0000\u0000\u0635\u062d\u0001\u0000"+
		"\u0000\u0000\u0635\u0630\u0001\u0000\u0000\u0000\u0635\u0633\u0001\u0000"+
		"\u0000\u0000\u0636\u0672\u0001\u0000\u0000\u0000\u0637\u063b\u0005\u012b"+
		"\u0000\u0000\u0638\u0639\u0005\u00c7\u0000\u0000\u0639\u063b\u0005\u0098"+
		"\u0000\u0000\u063a\u0637\u0001\u0000\u0000\u0000\u063a\u0638\u0001\u0000"+
		"\u0000\u0000\u063b\u063c\u0001\u0000\u0000\u0000\u063c\u063d\u0005\u009e"+
		"\u0000\u0000\u063d\u063e\u0005\u0098\u0000\u0000\u063e\u0672\u0005\u0156"+
		"\u0000\u0000\u063f\u0641\u0005B\u0000\u0000\u0640\u063f\u0001\u0000\u0000"+
		"\u0000\u0640\u0641\u0001\u0000\u0000\u0000\u0641\u0642\u0001\u0000\u0000"+
		"\u0000\u0642\u0643\u0005\u00c5\u0000\u0000\u0643\u0644\u0005\u0188\u0000"+
		"\u0000\u0644\u0672\u0005\u01bc\u0000\u0000\u0645\u0649\u0005S\u0000\u0000"+
		"\u0646\u0647\u0005\u0097\u0000\u0000\u0647\u0649\u0005S\u0000\u0000\u0648"+
		"\u0645\u0001\u0000\u0000\u0000\u0648\u0646\u0001\u0000\u0000\u0000\u0649"+
		"\u0672\u0001\u0000\u0000\u0000\u064a\u064b\u0005\"\u0000\u0000\u064b\u0672"+
		"\u0003\u00f0x\u0000\u064c\u064d\u0005\u010f\u0000\u0000\u064d\u064e\u0005"+
		"\u0142\u0000\u0000\u064e\u0672\u0003\u00f0x\u0000\u064f\u0650\u0005\u00d6"+
		"\u0000\u0000\u0650\u0651\u0005\u016f\u0000\u0000\u0651\u0672\u0005\u01b8"+
		"\u0000\u0000\u0652\u0653\u0007\u0016\u0000\u0000\u0653\u0654\u0005\u018c"+
		"\u0000\u0000\u0654\u0672\u0005\u017b\u0000\u0000\u0655\u0656\u0007\u0017"+
		"\u0000\u0000\u0656\u0657\u0005\u013a\u0000\u0000\u0657\u0672\u0005\u0168"+
		"\u0000\u0000\u0658\u065d\u0005\u0123\u0000\u0000\u0659\u065a\u0005\u0095"+
		"\u0000\u0000\u065a\u065e\u0005\u015b\u0000\u0000\u065b\u065c\u0005\u015b"+
		"\u0000\u0000\u065c\u065e\u0005\u01bc\u0000\u0000\u065d\u0659\u0001\u0000"+
		"\u0000\u0000\u065d\u065b\u0001\u0000\u0000\u0000\u065e\u0672\u0001\u0000"+
		"\u0000\u0000\u065f\u0660\u0005\u00e9\u0000\u0000\u0660\u0661\u0005\u0180"+
		"\u0000\u0000\u0661\u0672\u0007\u0005\u0000\u0000\u0662\u0663\u0005\u00b8"+
		"\u0000\u0000\u0663\u0664\u0005\u00f9\u0000\u0000\u0664\u0672\u0007\u0018"+
		"\u0000\u0000\u0665\u0666\u0005\u00dd\u0000\u0000\u0666\u0672\u0007\u0019"+
		"\u0000\u0000\u0667\u0668\u0005%\u0000\u0000\u0668\u0669\u0005\u009e\u0000"+
		"\u0000\u0669\u066a\u0005\u00c6\u0000\u0000\u066a\u0672\u0007\u0005\u0000"+
		"\u0000\u066b\u066c\u0005\u00a7\u0000\u0000\u066c\u066d\u0005\u00ae\u0000"+
		"\u0000\u066d\u0672\u0003\u00f0x\u0000\u066e\u066f\u0005\u0176\u0000\u0000"+
		"\u066f\u0672\u0003\u00f0x\u0000\u0670\u0672\u0003\u00fc~\u0000\u0671\u0608"+
		"\u0001\u0000\u0000\u0000\u0671\u0618\u0001\u0000\u0000\u0000\u0671\u061a"+
		"\u0001\u0000\u0000\u0000\u0671\u0622\u0001\u0000\u0000\u0000\u0671\u0625"+
		"\u0001\u0000\u0000\u0000\u0671\u0628\u0001\u0000\u0000\u0000\u0671\u0635"+
		"\u0001\u0000\u0000\u0000\u0671\u063a\u0001\u0000\u0000\u0000\u0671\u0640"+
		"\u0001\u0000\u0000\u0000\u0671\u0648\u0001\u0000\u0000\u0000\u0671\u064a"+
		"\u0001\u0000\u0000\u0000\u0671\u064c\u0001\u0000\u0000\u0000\u0671\u064f"+
		"\u0001\u0000\u0000\u0000\u0671\u0652\u0001\u0000\u0000\u0000\u0671\u0655"+
		"\u0001\u0000\u0000\u0000\u0671\u0658\u0001\u0000\u0000\u0000\u0671\u065f"+
		"\u0001\u0000\u0000\u0000\u0671\u0662\u0001\u0000\u0000\u0000\u0671\u0665"+
		"\u0001\u0000\u0000\u0000\u0671\u0667\u0001\u0000\u0000\u0000\u0671\u066b"+
		"\u0001\u0000\u0000\u0000\u0671\u066e\u0001\u0000\u0000\u0000\u0671\u0670"+
		"\u0001\u0000\u0000\u0000\u0672{\u0001\u0000\u0000\u0000\u0673\u0675\u0005"+
		"\u0011\u0000\u0000\u0674\u0676\u0005\u0125\u0000\u0000\u0675\u0674\u0001"+
		"\u0000\u0000\u0000\u0675\u0676\u0001\u0000\u0000\u0000\u0676\u067a\u0001"+
		"\u0000\u0000\u0000\u0677\u0679\u0003~?\u0000\u0678\u0677\u0001\u0000\u0000"+
		"\u0000\u0679\u067c\u0001\u0000\u0000\u0000\u067a\u0678\u0001\u0000\u0000"+
		"\u0000\u067a\u067b\u0001\u0000\u0000\u0000\u067b\u067d\u0001\u0000\u0000"+
		"\u0000\u067c\u067a\u0001\u0000\u0000\u0000\u067d\u067e\u0004>\u0007\u0001"+
		"\u067e\u0680\u0005G\u0000\u0000\u067f\u0681\u0003\u00f0x\u0000\u0680\u067f"+
		"\u0001\u0000\u0000\u0000\u0680\u0681\u0001\u0000\u0000\u0000\u0681}\u0001"+
		"\u0000\u0000\u0000\u0682\u0687\u0003|>\u0000\u0683\u0684\u0005G\u0000"+
		"\u0000\u0684\u0687\u0007\u001a\u0000\u0000\u0685\u0687\b\u001b\u0000\u0000"+
		"\u0686\u0682\u0001\u0000\u0000\u0000\u0686\u0683\u0001\u0000\u0000\u0000"+
		"\u0686\u0685\u0001\u0000\u0000\u0000\u0687\u007f\u0001\u0000\u0000\u0000"+
		"\u0688\u068a\u0003~?\u0000\u0689\u0688\u0001\u0000\u0000\u0000\u068a\u068b"+
		"\u0001\u0000\u0000\u0000\u068b\u0689\u0001\u0000\u0000\u0000\u068b\u068c"+
		"\u0001\u0000\u0000\u0000\u068c\u0081\u0001\u0000\u0000\u0000\u068d\u068e"+
		"\u0005\t\u0000\u0000\u068e\u068f\u0005\u00f3\u0000\u0000\u068f\u0691\u0003"+
		"\u00eew\u0000\u0690\u0692\u0003\u0084B\u0000\u0691\u0690\u0001\u0000\u0000"+
		"\u0000\u0692\u0693\u0001\u0000\u0000\u0000\u0693\u0691\u0001\u0000\u0000"+
		"\u0000\u0693\u0694\u0001\u0000\u0000\u0000\u0694\u0695\u0001\u0000\u0000"+
		"\u0000\u0695\u0696\u0003\u00fa}\u0000\u0696\u0083\u0001\u0000\u0000\u0000"+
		"\u0697\u0699\u0005\u0004\u0000\u0000\u0698\u069a\u0005#\u0000\u0000\u0699"+
		"\u0698\u0001\u0000\u0000\u0000\u0699\u069a\u0001\u0000\u0000\u0000\u069a"+
		"\u069b\u0001\u0000\u0000\u0000\u069b\u06e9\u0003\u0010\b\u0000\u069c\u069e"+
		"\u0005\u0004\u0000\u0000\u069d\u069c\u0001\u0000\u0000\u0000\u069d\u069e"+
		"\u0001\u0000\u0000\u0000\u069e\u069f\u0001\u0000\u0000\u0000\u069f\u06e9"+
		"\u0003\u001a\r\u0000\u06a0\u06a1\u0005\u0004\u0000\u0000\u06a1\u06e9\u0003"+
		"\u0018\f\u0000\u06a2\u06a3\u0005\u0004\u0000\u0000\u06a3\u06a5\u0005\u00ab"+
		"\u0000\u0000\u06a4\u06a6\u0003,\u0016\u0000\u06a5\u06a4\u0001\u0000\u0000"+
		"\u0000\u06a5\u06a6\u0001\u0000\u0000\u0000\u06a6\u06e9\u0001\u0000\u0000"+
		"\u0000\u06a7\u06a8\u0005\u0004\u0000\u0000\u06a8\u06a9\u0005\u01a0\u0000"+
		"\u0000\u06a9\u06aa\u0005\u019c\u0000\u0000\u06aa\u06ab\u0005\u014d\u0000"+
		"\u0000\u06ab\u06ac\u0005\u00f3\u0000\u0000\u06ac\u06e9\u0003\u00eew\u0000"+
		"\u06ad\u06af\u0005\t\u0000\u0000\u06ae\u06b0\u0005#\u0000\u0000\u06af"+
		"\u06ae\u0001\u0000\u0000\u0000\u06af\u06b0\u0001\u0000\u0000\u0000\u06b0"+
		"\u06b1\u0001\u0000\u0000\u0000\u06b1\u06b2\u0003\u00f0x\u0000\u06b2\u06b3"+
		"\u0003\u0086C\u0000\u06b3\u06e9\u0001\u0000\u0000\u0000\u06b4\u06b5\u0005"+
		"\t\u0000\u0000\u06b5\u06b6\u0005\u00ab\u0000\u0000\u06b6\u06b7\u0005\u01bc"+
		"\u0000\u0000\u06b7\u06e9\u0003,\u0016\u0000\u06b8\u06ba\u0005@\u0000\u0000"+
		"\u06b9\u06bb\u0005#\u0000\u0000\u06ba\u06b9\u0001\u0000\u0000\u0000\u06ba"+
		"\u06bb\u0001\u0000\u0000\u0000\u06bb\u06bc\u0001\u0000\u0000\u0000\u06bc"+
		"\u06be\u0003\u00f0x\u0000\u06bd\u06bf\u0007\u001c\u0000\u0000\u06be\u06bd"+
		"\u0001\u0000\u0000\u0000\u06be\u06bf\u0001\u0000\u0000\u0000\u06bf\u06e9"+
		"\u0001\u0000\u0000\u0000\u06c0\u06cc\u0005@\u0000\u0000\u06c1\u06c2\u0005"+
		"\u0175\u0000\u0000\u06c2\u06cd\u0005z\u0000\u0000\u06c3\u06c4\u0005\u0147"+
		"\u0000\u0000\u06c4\u06c5\u0005z\u0000\u0000\u06c5\u06cd\u0003\u00f0x\u0000"+
		"\u06c6\u06c7\u0005\u00fc\u0000\u0000\u06c7\u06cd\u0003\u00f0x\u0000\u06c8"+
		"\u06c9\u0005\u001d\u0000\u0000\u06c9\u06cd\u0003\u00f0x\u0000\u06ca\u06cb"+
		"\u0005*\u0000\u0000\u06cb\u06cd\u0003\u00f0x\u0000\u06cc\u06c1\u0001\u0000"+
		"\u0000\u0000\u06cc\u06c3\u0001\u0000\u0000\u0000\u06cc\u06c6\u0001\u0000"+
		"\u0000\u0000\u06cc\u06c8\u0001\u0000\u0000\u0000\u06cc\u06ca\u0001\u0000"+
		"\u0000\u0000\u06cd\u06e9\u0001\u0000\u0000\u0000\u06ce\u06cf\u0005@\u0000"+
		"\u0000\u06cf\u06e9\u0005\u01a0\u0000\u0000\u06d0\u06d2\u0005\u00c2\u0000"+
		"\u0000\u06d1\u06d3\u0005#\u0000\u0000\u06d2\u06d1\u0001\u0000\u0000\u0000"+
		"\u06d2\u06d3\u0001\u0000\u0000\u0000\u06d3\u06d4\u0001\u0000\u0000\u0000"+
		"\u06d4\u06d5\u0003\u00f0x\u0000\u06d5\u06d6\u0005\u00f6\u0000\u0000\u06d6"+
		"\u06d7\u0003\u00f0x\u0000\u06d7\u06e9\u0001\u0000\u0000\u0000\u06d8\u06d9"+
		"\u0005\u0183\u0000\u0000\u06d9\u06da\u0005\u00ab\u0000\u0000\u06da\u06db"+
		"\u0007\u001d\u0000\u0000\u06db\u06dc\u0005\u00f6\u0000\u0000\u06dc\u06dd"+
		"\u0005}\u0000\u0000\u06dd\u06df\u0005H\u0000\u0000\u06de\u06e0\u0005\u0124"+
		"\u0000\u0000\u06df\u06de\u0001\u0000\u0000\u0000\u06df\u06e0\u0001\u0000"+
		"\u0000\u0000\u06e0\u06e1\u0001\u0000\u0000\u0000\u06e1\u06e2\u0005\u01a6"+
		"\u0000\u0000\u06e2\u06e3\u0003\u00eau\u0000\u06e3\u06e5\u0005\u01a7\u0000"+
		"\u0000\u06e4\u06e6\u0005\u017f\u0000\u0000\u06e5\u06e4\u0001\u0000\u0000"+
		"\u0000\u06e5\u06e6\u0001\u0000\u0000\u0000\u06e6\u06e9\u0001\u0000\u0000"+
		"\u0000\u06e7\u06e9\u0003$\u0012\u0000\u06e8\u0697\u0001\u0000\u0000\u0000"+
		"\u06e8\u069d\u0001\u0000\u0000\u0000\u06e8\u06a0\u0001\u0000\u0000\u0000"+
		"\u06e8\u06a2\u0001\u0000\u0000\u0000\u06e8\u06a7\u0001\u0000\u0000\u0000"+
		"\u06e8\u06ad\u0001\u0000\u0000\u0000\u06e8\u06b4\u0001\u0000\u0000\u0000"+
		"\u06e8\u06b8\u0001\u0000\u0000\u0000\u06e8\u06c0\u0001\u0000\u0000\u0000"+
		"\u06e8\u06ce\u0001\u0000\u0000\u0000\u06e8\u06d0\u0001\u0000\u0000\u0000"+
		"\u06e8\u06d8\u0001\u0000\u0000\u0000\u06e8\u06e7\u0001\u0000\u0000\u0000"+
		"\u06e9\u0085\u0001\u0000\u0000\u0000\u06ea\u06ec\u0005\u00e1\u0000\u0000"+
		"\u06eb\u06ea\u0001\u0000\u0000\u0000\u06eb\u06ec\u0001\u0000\u0000\u0000"+
		"\u06ec\u06ed\u0001\u0000\u0000\u0000\u06ed\u06ee\u00051\u0000\u0000\u06ee"+
		"\u06ef\u0005\u00f9\u0000\u0000\u06ef\u0713\u0003\u00f2y\u0000\u06f0\u06f1"+
		"\u0005\u00e1\u0000\u0000\u06f1\u06f3\u00057\u0000\u0000\u06f2\u06f4\u0003"+
		"\u0016\u000b\u0000\u06f3\u06f2\u0001\u0000\u0000\u0000\u06f3\u06f4\u0001"+
		"\u0000\u0000\u0000\u06f4\u0713\u0001\u0000\u0000\u0000\u06f5\u06f6\u0005"+
		"@\u0000\u0000\u06f6\u0713\u00057\u0000\u0000\u06f7\u06f8\u0005\u00e1\u0000"+
		"\u0000\u06f8\u06f9\u0005\u0097\u0000\u0000\u06f9\u0713\u0005\u0098\u0000"+
		"\u0000\u06fa\u06fb\u0005@\u0000\u0000\u06fb\u06fc\u0005\u0097\u0000\u0000"+
		"\u06fc\u0713\u0005\u0098\u0000\u0000\u06fd\u06fe\u0005\u00e1\u0000\u0000"+
		"\u06fe\u0702\u0005]\u0000\u0000\u06ff\u0703\u0005\u0120\u0000\u0000\u0700"+
		"\u0701\u0005\u0014\u0000\u0000\u0701\u0703\u00057\u0000\u0000\u0702\u06ff"+
		"\u0001\u0000\u0000\u0000\u0702\u0700\u0001\u0000\u0000\u0000\u0703\u0705"+
		"\u0001\u0000\u0000\u0000\u0704\u0706\u0003\u0014\n\u0000\u0705\u0704\u0001"+
		"\u0000\u0000\u0000\u0705\u0706\u0001\u0000\u0000\u0000\u0706\u0713\u0001"+
		"\u0000\u0000\u0000\u0707\u0708\u0005@\u0000\u0000\u0708\u0713\u0005\u014f"+
		"\u0000\u0000\u0709\u070a\u0005\u00e1\u0000\u0000\u070a\u070b\u0005\u0155"+
		"\u0000\u0000\u070b\u070c\u0005\u015a\u0000\u0000\u070c\u0713\u0005\u01bc"+
		"\u0000\u0000\u070d\u070f\u0003R)\u0000\u070e\u070d\u0001\u0000\u0000\u0000"+
		"\u070f\u0710\u0001\u0000\u0000\u0000\u0710\u070e\u0001\u0000\u0000\u0000"+
		"\u0710\u0711\u0001\u0000\u0000\u0000\u0711\u0713\u0001\u0000\u0000\u0000"+
		"\u0712\u06eb\u0001\u0000\u0000\u0000\u0712\u06f0\u0001\u0000\u0000\u0000"+
		"\u0712\u06f5\u0001\u0000\u0000\u0000\u0712\u06f7\u0001\u0000\u0000\u0000"+
		"\u0712\u06fa\u0001\u0000\u0000\u0000\u0712\u06fd\u0001\u0000\u0000\u0000"+
		"\u0712\u0707\u0001\u0000\u0000\u0000\u0712\u0709\u0001\u0000\u0000\u0000"+
		"\u0712\u070e\u0001\u0000\u0000\u0000\u0713\u0087\u0001\u0000\u0000\u0000"+
		"\u0714\u0716\u0005\t\u0000\u0000\u0715\u0717\u0007\n\u0000\u0000\u0716"+
		"\u0715\u0001\u0000\u0000\u0000\u0716\u0717\u0001\u0000\u0000\u0000\u0717"+
		"\u0718\u0001\u0000\u0000\u0000\u0718\u0719\u0005\u00f4\u0000\u0000\u0719"+
		"\u071c\u0003\u00eew\u0000\u071a\u071b\u0005\u00a9\u0000\u0000\u071b\u071d"+
		"\u0005\u01bc\u0000\u0000\u071c\u071a\u0001\u0000\u0000\u0000\u071c\u071d"+
		"\u0001\u0000\u0000\u0000\u071d\u0721\u0001\u0000\u0000\u0000\u071e\u0720"+
		"\u0003>\u001f\u0000\u071f\u071e\u0001\u0000\u0000\u0000\u0720\u0723\u0001"+
		"\u0000\u0000\u0000\u0721\u071f\u0001\u0000\u0000\u0000\u0721\u0722\u0001"+
		"\u0000\u0000\u0000\u0722\u0724\u0001\u0000\u0000\u0000\u0723\u0721\u0001"+
		"\u0000\u0000\u0000\u0724\u0725\u0003\u00fa}\u0000\u0725\u0089\u0001\u0000"+
		"\u0000\u0000\u0726\u0727\u0005\t\u0000\u0000\u0727\u0728\u0005m\u0000"+
		"\u0000\u0728\u0730\u0003\u00eew\u0000\u0729\u0731\u0005\u017a\u0000\u0000"+
		"\u072a\u072c\u0003:\u001d\u0000\u072b\u072a\u0001\u0000\u0000\u0000\u072c"+
		"\u072f\u0001\u0000\u0000\u0000\u072d\u072b\u0001\u0000\u0000\u0000\u072d"+
		"\u072e\u0001\u0000\u0000\u0000\u072e\u0731\u0001\u0000\u0000\u0000\u072f"+
		"\u072d\u0001\u0000\u0000\u0000\u0730\u0729\u0001\u0000\u0000\u0000\u0730"+
		"\u072d\u0001\u0000\u0000\u0000\u0731\u0732\u0001\u0000\u0000\u0000\u0732"+
		"\u0733\u0003\u00fa}\u0000\u0733\u008b\u0001\u0000\u0000\u0000\u0734\u0735"+
		"\u0005\t\u0000\u0000\u0735\u0736\u00052\u0000\u0000\u0736\u073a\u0003"+
		"\u00f0x\u0000\u0737\u0739\u0003B!\u0000\u0738\u0737\u0001\u0000\u0000"+
		"\u0000\u0739\u073c\u0001\u0000\u0000\u0000\u073a\u0738\u0001\u0000\u0000"+
		"\u0000\u073a\u073b\u0001\u0000\u0000\u0000\u073b\u073d\u0001\u0000\u0000"+
		"\u0000\u073c\u073a\u0001\u0000\u0000\u0000\u073d\u073e\u0003\u00fa}\u0000"+
		"\u073e\u008d\u0001\u0000\u0000\u0000\u073f\u0740\u0005\t\u0000\u0000\u0740"+
		"\u0741\u0005\u00ea\u0000\u0000\u0741\u0745\u0003\u00f0x\u0000\u0742\u0744"+
		"\u0003\u00a0P\u0000\u0743\u0742\u0001\u0000\u0000\u0000\u0744\u0747\u0001"+
		"\u0000\u0000\u0000\u0745\u0743\u0001\u0000\u0000\u0000\u0745\u0746\u0001"+
		"\u0000\u0000\u0000\u0746\u0748\u0001\u0000\u0000\u0000\u0747\u0745\u0001"+
		"\u0000\u0000\u0000\u0748\u0749\u0003\u00fa}\u0000\u0749\u008f\u0001\u0000"+
		"\u0000\u0000\u074a\u074b\u0005\t\u0000\u0000\u074b\u074c\u0005\u00de\u0000"+
		"\u0000\u074c\u0755\u0003\u00eew\u0000\u074d\u0750\u0005\u0181\u0000\u0000"+
		"\u074e\u074f\u0005\u010e\u0000\u0000\u074f\u0751\u0003\u00ecv\u0000\u0750"+
		"\u074e\u0001\u0000\u0000\u0000\u0750\u0751\u0001\u0000\u0000\u0000\u0751"+
		"\u0754\u0001\u0000\u0000\u0000\u0752\u0754\u0003R)\u0000\u0753\u074d\u0001"+
		"\u0000\u0000\u0000\u0753\u0752\u0001\u0000\u0000\u0000\u0754\u0757\u0001"+
		"\u0000\u0000\u0000\u0755\u0753\u0001\u0000\u0000\u0000\u0755\u0756\u0001"+
		"\u0000\u0000\u0000\u0756\u0758\u0001\u0000\u0000\u0000\u0757\u0755\u0001"+
		"\u0000\u0000\u0000\u0758\u0759\u0003\u00fa}\u0000\u0759\u0091\u0001\u0000"+
		"\u0000\u0000\u075a\u075b\u0005\t\u0000\u0000\u075b\u075c\u0005\u0107\u0000"+
		"\u0000\u075c\u075d\u0003\u00eew\u0000\u075d\u075e\u0005\u017a\u0000\u0000"+
		"\u075e\u075f\u0003\u00fa}\u0000\u075f\u0093\u0001\u0000\u0000\u0000\u0760"+
		"\u0761\u0005\t\u0000\u0000\u0761\u0762\u0005\u00b7\u0000\u0000\u0762\u076f"+
		"\u0003\u00eew\u0000\u0763\u076c\u0005\u01a6\u0000\u0000\u0764\u0769\u0003"+
		"x<\u0000\u0765\u0766\u0005\u01a8\u0000\u0000\u0766\u0768\u0003x<\u0000"+
		"\u0767\u0765\u0001\u0000\u0000\u0000\u0768\u076b\u0001\u0000\u0000\u0000"+
		"\u0769\u0767\u0001\u0000\u0000\u0000\u0769\u076a\u0001\u0000\u0000\u0000"+
		"\u076a\u076d\u0001\u0000\u0000\u0000\u076b\u0769\u0001\u0000\u0000\u0000"+
		"\u076c\u0764\u0001\u0000\u0000\u0000\u076c\u076d\u0001\u0000\u0000\u0000"+
		"\u076d\u076e\u0001\u0000\u0000\u0000\u076e\u0770\u0005\u01a7\u0000\u0000"+
		"\u076f\u0763\u0001\u0000\u0000\u0000\u076f\u0770\u0001\u0000\u0000\u0000"+
		"\u0770\u0774\u0001\u0000\u0000\u0000\u0771\u0773\u0003z=\u0000\u0772\u0771"+
		"\u0001\u0000\u0000\u0000\u0773\u0776\u0001\u0000\u0000\u0000\u0774\u0772"+
		"\u0001\u0000\u0000\u0000\u0774\u0775\u0001\u0000\u0000\u0000\u0775\u0778"+
		"\u0001\u0000\u0000\u0000\u0776\u0774\u0001\u0000\u0000\u0000\u0777\u0779"+
		"\u0003|>\u0000\u0778\u0777\u0001\u0000\u0000\u0000\u0778\u0779\u0001\u0000"+
		"\u0000\u0000\u0779\u077a\u0001\u0000\u0000\u0000\u077a\u077b\u0003\u00fa"+
		"}\u0000\u077b\u0095\u0001\u0000\u0000\u0000\u077c\u077d\u0005\t\u0000"+
		"\u0000\u077d\u077e\u0005\\\u0000\u0000\u077e\u078b\u0003\u00eew\u0000"+
		"\u077f\u0788\u0005\u01a6\u0000\u0000\u0780\u0785\u0003x<\u0000\u0781\u0782"+
		"\u0005\u01a8\u0000\u0000\u0782\u0784\u0003x<\u0000\u0783\u0781\u0001\u0000"+
		"\u0000\u0000\u0784\u0787\u0001\u0000\u0000\u0000\u0785\u0783\u0001\u0000"+
		"\u0000\u0000\u0785\u0786\u0001\u0000\u0000\u0000\u0786\u0789\u0001\u0000"+
		"\u0000\u0000\u0787\u0785\u0001\u0000\u0000\u0000\u0788\u0780\u0001\u0000"+
		"\u0000\u0000\u0788\u0789\u0001\u0000\u0000\u0000\u0789\u078a\u0001\u0000"+
		"\u0000\u0000\u078a\u078c\u0005\u01a7\u0000\u0000\u078b\u077f\u0001\u0000"+
		"\u0000\u0000\u078b\u078c\u0001\u0000\u0000\u0000\u078c\u0790\u0001\u0000"+
		"\u0000\u0000\u078d\u078f\u0003z=\u0000\u078e\u078d\u0001\u0000\u0000\u0000"+
		"\u078f\u0792\u0001\u0000\u0000\u0000\u0790\u078e\u0001\u0000\u0000\u0000"+
		"\u0790\u0791\u0001\u0000\u0000\u0000\u0791\u0794\u0001\u0000\u0000\u0000"+
		"\u0792\u0790\u0001\u0000\u0000\u0000\u0793\u0795\u0003|>\u0000\u0794\u0793"+
		"\u0001\u0000\u0000\u0000\u0794\u0795\u0001\u0000\u0000\u0000\u0795\u0796"+
		"\u0001\u0000\u0000\u0000\u0796\u0797\u0003\u00fa}\u0000\u0797\u0097\u0001"+
		"\u0000\u0000\u0000\u0798\u0799\u0005\t\u0000\u0000\u0799\u079a\u0005\u00f7"+
		"\u0000\u0000\u079a\u079c\u0003\u00eew\u0000\u079b\u079d\u0005\u0097\u0000"+
		"\u0000\u079c\u079b\u0001\u0000\u0000\u0000\u079c\u079d\u0001\u0000\u0000"+
		"\u0000\u079d\u079e\u0001\u0000\u0000\u0000\u079e\u079f\u0005\u0186\u0000"+
		"\u0000\u079f\u07a0\u0003\u00fa}\u0000\u07a0\u0099\u0001\u0000\u0000\u0000"+
		"\u07a1\u07a2\u0005\t\u0000\u0000\u07a2\u07a3\u0005\u0160\u0000\u0000\u07a3"+
		"\u07a4\u0003\u00eew\u0000\u07a4\u07a5\u0007\u0011\u0000\u0000\u07a5\u07a6"+
		"\u0003\u00fa}\u0000\u07a6\u009b\u0001\u0000\u0000\u0000\u07a7\u07a8\u0005"+
		"\t\u0000\u0000\u07a8\u07a9\u0005\u0174\u0000\u0000\u07a9\u07aa\u0003\u00ee"+
		"w\u0000\u07aa\u07ab\u0007\u0011\u0000\u0000\u07ab\u07ac\u0003\u00fa}\u0000"+
		"\u07ac\u009d\u0001\u0000\u0000\u0000\u07ad\u07ae\u0005\t\u0000\u0000\u07ae"+
		"\u07af\u0005\u0199\u0000\u0000\u07af\u07b0\u0005\u0133\u0000\u0000\u07b0"+
		"\u07b4\u0003\u00f0x\u0000\u07b1\u07b3\u0003b1\u0000\u07b2\u07b1\u0001"+
		"\u0000\u0000\u0000\u07b3\u07b6\u0001\u0000\u0000\u0000\u07b4\u07b2\u0001"+
		"\u0000\u0000\u0000\u07b4\u07b5\u0001\u0000\u0000\u0000\u07b5\u07b7\u0001"+
		"\u0000\u0000\u0000\u07b6\u07b4\u0001\u0000\u0000\u0000\u07b7\u07b8\u0003"+
		"\u00fa}\u0000\u07b8\u009f\u0001\u0000\u0000\u0000\u07b9\u07ba\u0007\u001e"+
		"\u0000\u0000\u07ba\u07bb\u0005\u0109\u0000\u0000\u07bb\u07be\u0005\u01a6"+
		"\u0000\u0000\u07bc\u07bf\u0005\u01b8\u0000\u0000\u07bd\u07bf\u0003\u00f0"+
		"x\u0000\u07be\u07bc\u0001\u0000\u0000\u0000\u07be\u07bd\u0001\u0000\u0000"+
		"\u0000\u07bf\u07c7\u0001\u0000\u0000\u0000\u07c0\u07c3\u0005\u01a8\u0000"+
		"\u0000\u07c1\u07c4\u0005\u01b8\u0000\u0000\u07c2\u07c4\u0003\u00f0x\u0000"+
		"\u07c3\u07c1\u0001\u0000\u0000\u0000\u07c3\u07c2\u0001\u0000\u0000\u0000"+
		"\u07c4\u07c6\u0001\u0000\u0000\u0000\u07c5\u07c0\u0001\u0000\u0000\u0000"+
		"\u07c6\u07c9\u0001\u0000\u0000\u0000\u07c7\u07c5\u0001\u0000\u0000\u0000"+
		"\u07c7\u07c8\u0001\u0000\u0000\u0000\u07c8\u07ca\u0001\u0000\u0000\u0000"+
		"\u07c9\u07c7\u0001\u0000\u0000\u0000\u07ca\u07cd\u0005\u01a7\u0000\u0000"+
		"\u07cb\u07cd\u0003F#\u0000\u07cc\u07b9\u0001\u0000\u0000\u0000\u07cc\u07cb"+
		"\u0001\u0000\u0000\u0000\u07cd\u00a1\u0001\u0000\u0000\u0000\u07ce\u07cf"+
		"\u0005@\u0000\u0000\u07cf\u07d1\u0003\u00a4R\u0000\u07d0\u07d2\u0007\u001c"+
		"\u0000\u0000\u07d1\u07d0\u0001\u0000\u0000\u0000\u07d1\u07d2\u0001\u0000"+
		"\u0000\u0000\u07d2\u07d3\u0001\u0000\u0000\u0000\u07d3\u07d4\u0003\u00fa"+
		"}\u0000\u07d4\u00a3\u0001\u0000\u0000\u0000\u07d5\u07d6\u0005\u00f3\u0000"+
		"\u0000\u07d6\u0816\u0003\u00eew\u0000\u07d7\u07d9\u0007\n\u0000\u0000"+
		"\u07d8\u07d7\u0001\u0000\u0000\u0000\u07d8\u07d9\u0001\u0000\u0000\u0000"+
		"\u07d9\u07da\u0001\u0000\u0000\u0000\u07da\u07db\u0005\u00f4\u0000\u0000"+
		"\u07db\u0816\u0003\u00eew\u0000\u07dc\u07dd\u0005m\u0000\u0000\u07dd\u0816"+
		"\u0003\u00eew\u0000\u07de\u07df\u00052\u0000\u0000\u07df\u0816\u0003\u00f0"+
		"x\u0000\u07e0\u07e1\u0005\u00ea\u0000\u0000\u07e1\u0816\u0003\u00f0x\u0000"+
		"\u07e2\u07e3\u0005\u0107\u0000\u0000\u07e3\u0816\u0003\u00eew\u0000\u07e4"+
		"\u07e6\u0005\u00ba\u0000\u0000\u07e5\u07e4\u0001\u0000\u0000\u0000\u07e5"+
		"\u07e6\u0001\u0000\u0000\u0000\u07e6\u07e7\u0001\u0000\u0000\u0000\u07e7"+
		"\u07e8\u0005\u011f\u0000\u0000\u07e8\u0816\u0003\u00eew\u0000\u07e9\u07ea"+
		"\u0005\u00ee\u0000\u0000\u07ea\u0816\u0003\u00f0x\u0000\u07eb\u07ec\u0005"+
		"\u00de\u0000\u0000\u07ec\u0816\u0003\u00eew\u0000\u07ed\u07ee\u0005\u00ca"+
		"\u0000\u0000\u07ee\u0816\u0003\u00f0x\u0000\u07ef\u07f0\u0005\u00f7\u0000"+
		"\u0000\u07f0\u0816\u0003\u00eew\u0000\u07f1\u07f2\u0007\u001f\u0000\u0000"+
		"\u07f2\u07ff\u0003\u00eew\u0000\u07f3\u07fc\u0005\u01a6\u0000\u0000\u07f4"+
		"\u07f9\u0003\u00f2y\u0000\u07f5\u07f6\u0005\u01a8\u0000\u0000\u07f6\u07f8"+
		"\u0003\u00f2y\u0000\u07f7\u07f5\u0001\u0000\u0000\u0000\u07f8\u07fb\u0001"+
		"\u0000\u0000\u0000\u07f9\u07f7\u0001\u0000\u0000\u0000\u07f9\u07fa\u0001"+
		"\u0000\u0000\u0000\u07fa\u07fd\u0001\u0000\u0000\u0000\u07fb\u07f9\u0001"+
		"\u0000\u0000\u0000\u07fc\u07f4\u0001\u0000\u0000\u0000\u07fc\u07fd\u0001"+
		"\u0000\u0000\u0000\u07fd\u07fe\u0001\u0000\u0000\u0000\u07fe\u0800\u0005"+
		"\u01a7\u0000\u0000\u07ff\u07f3\u0001\u0000\u0000\u0000\u07ff\u0800\u0001"+
		"\u0000\u0000\u0000\u0800\u0816\u0001\u0000\u0000\u0000\u0801\u0802\u0005"+
		"\u00e6\u0000\u0000\u0802\u0803\u0007\u001f\u0000\u0000\u0803\u0816\u0003"+
		"\u00eew\u0000\u0804\u0806\u0005=\u0000\u0000\u0805\u0804\u0001\u0000\u0000"+
		"\u0000\u0805\u0806\u0001\u0000\u0000\u0000\u0806\u0807\u0001\u0000\u0000"+
		"\u0000\u0807\u0808\u0005\u00f9\u0000\u0000\u0808\u0816\u0003\u00eew\u0000"+
		"\u0809\u080a\u0005\u0104\u0000\u0000\u080a\u0816\u0003\u00eew\u0000\u080b"+
		"\u080c\u0007 \u0000\u0000\u080c\u0816\u0003\u00eew\u0000\u080d\u080e\u0005"+
		"\u0199\u0000\u0000\u080e\u080f\u0005\u0133\u0000\u0000\u080f\u0816\u0003"+
		"\u00f0x\u0000\u0810\u0811\u0007\u0010\u0000\u0000\u0811\u0812\u0005\u00f3"+
		"\u0000\u0000\u0812\u0816\u0003\u00eew\u0000\u0813\u0814\u0005\u00a7\u0000"+
		"\u0000\u0814\u0816\u0003\u00eew\u0000\u0815\u07d5\u0001\u0000\u0000\u0000"+
		"\u0815\u07d8\u0001\u0000\u0000\u0000\u0815\u07dc\u0001\u0000\u0000\u0000"+
		"\u0815\u07de\u0001\u0000\u0000\u0000\u0815\u07e0\u0001\u0000\u0000\u0000"+
		"\u0815\u07e2\u0001\u0000\u0000\u0000\u0815\u07e5\u0001\u0000\u0000\u0000"+
		"\u0815\u07e9\u0001\u0000\u0000\u0000\u0815\u07eb\u0001\u0000\u0000\u0000"+
		"\u0815\u07ed\u0001\u0000\u0000\u0000\u0815\u07ef\u0001\u0000\u0000\u0000"+
		"\u0815\u07f1\u0001\u0000\u0000\u0000\u0815\u0801\u0001\u0000\u0000\u0000"+
		"\u0815\u0805\u0001\u0000\u0000\u0000\u0815\u0809\u0001\u0000\u0000\u0000"+
		"\u0815\u080b\u0001\u0000\u0000\u0000\u0815\u080d\u0001\u0000\u0000\u0000"+
		"\u0815\u0810\u0001\u0000\u0000\u0000\u0815\u0813\u0001\u0000\u0000\u0000"+
		"\u0816\u00a5\u0001\u0000\u0000\u0000\u0817\u0818\u0005b\u0000\u0000\u0818"+
		"\u0819\u0005\u019c\u0000\u0000\u0819\u081a\u0005\u009c\u0000\u0000\u081a"+
		"\u081b\u0003\u00acV\u0000\u081b\u081c\u0003\u00aeW\u0000\u081c\u081d\u0005"+
		"\u00f6\u0000\u0000\u081d\u0822\u0003\u00b0X\u0000\u081e\u081f\u0005\u01a8"+
		"\u0000\u0000\u081f\u0821\u0003\u00b0X\u0000\u0820\u081e\u0001\u0000\u0000"+
		"\u0000\u0821\u0824\u0001\u0000\u0000\u0000\u0822\u0820\u0001\u0000\u0000"+
		"\u0000\u0822\u0823\u0001\u0000\u0000\u0000\u0823\u0825\u0001\u0000\u0000"+
		"\u0000\u0824\u0822\u0001\u0000\u0000\u0000\u0825\u0826\u0003\u00fa}\u0000"+
		"\u0826\u085f\u0001\u0000\u0000\u0000\u0827\u0828\u0005b\u0000\u0000\u0828"+
		"\u082d\u0003\u00aaU\u0000\u0829\u082a\u0005\u01a8\u0000\u0000\u082a\u082c"+
		"\u0003\u00aaU\u0000\u082b\u0829\u0001\u0000\u0000\u0000\u082c\u082f\u0001"+
		"\u0000\u0000\u0000\u082d\u082b\u0001\u0000\u0000\u0000\u082d\u082e\u0001"+
		"\u0000\u0000\u0000\u082e\u0830\u0001\u0000\u0000\u0000\u082f\u082d\u0001"+
		"\u0000\u0000\u0000\u0830\u0832\u0007!\u0000\u0000\u0831\u0833\u0003\u00ac"+
		"V\u0000\u0832\u0831\u0001\u0000\u0000\u0000\u0832\u0833\u0001\u0000\u0000"+
		"\u0000\u0833\u0834\u0001\u0000\u0000\u0000\u0834\u0835\u0003\u00aeW\u0000"+
		"\u0835\u0836\u0005\u00f6\u0000\u0000\u0836\u083b\u0003\u00b0X\u0000\u0837"+
		"\u0838\u0005\u01a8\u0000\u0000\u0838\u083a\u0003\u00b0X\u0000\u0839\u0837"+
		"\u0001\u0000\u0000\u0000\u083a\u083d\u0001\u0000\u0000\u0000\u083b\u0839"+
		"\u0001\u0000\u0000\u0000\u083b\u083c\u0001\u0000\u0000\u0000\u083c\u0841"+
		"\u0001\u0000\u0000\u0000\u083d\u083b\u0001\u0000\u0000\u0000\u083e\u083f"+
		"\u0005\u010e\u0000\u0000\u083f\u0840\u0005b\u0000\u0000\u0840\u0842\u0005"+
		"\u016e\u0000\u0000\u0841\u083e\u0001\u0000\u0000\u0000\u0841\u0842\u0001"+
		"\u0000\u0000\u0000\u0842\u0843\u0001\u0000\u0000\u0000\u0843\u0844\u0003"+
		"\u00fa}\u0000\u0844\u085f\u0001\u0000\u0000\u0000\u0845\u0846\u0005b\u0000"+
		"\u0000\u0846\u084b\u0003\u00aaU\u0000\u0847\u0848\u0005\u01a8\u0000\u0000"+
		"\u0848\u084a\u0003\u00aaU\u0000\u0849\u0847\u0001\u0000\u0000\u0000\u084a"+
		"\u084d\u0001\u0000\u0000\u0000\u084b\u0849\u0001\u0000\u0000\u0000\u084b"+
		"\u084c\u0001\u0000\u0000\u0000\u084c\u084e\u0001\u0000\u0000\u0000\u084d"+
		"\u084b\u0001\u0000\u0000\u0000\u084e\u084f\u0005\u00f6\u0000\u0000\u084f"+
		"\u0854\u0003\u00b0X\u0000\u0850\u0851\u0005\u01a8\u0000\u0000\u0851\u0853"+
		"\u0003\u00b0X\u0000\u0852\u0850\u0001\u0000\u0000\u0000\u0853\u0856\u0001"+
		"\u0000\u0000\u0000\u0854\u0852\u0001\u0000\u0000\u0000\u0854\u0855\u0001"+
		"\u0000\u0000\u0000\u0855\u085a\u0001\u0000\u0000\u0000\u0856\u0854\u0001"+
		"\u0000\u0000\u0000\u0857\u0858\u0005\u010e\u0000\u0000\u0858\u0859\u0005"+
		"\u011e\u0000\u0000\u0859\u085b\u0005\u016e\u0000\u0000\u085a\u0857\u0001"+
		"\u0000\u0000\u0000\u085a\u085b\u0001\u0000\u0000\u0000\u085b\u085c\u0001"+
		"\u0000\u0000\u0000\u085c\u085d\u0003\u00fa}\u0000\u085d\u085f\u0001\u0000"+
		"\u0000\u0000\u085e\u0817\u0001\u0000\u0000\u0000\u085e\u0827\u0001\u0000"+
		"\u0000\u0000\u085e\u0845\u0001\u0000\u0000\u0000\u085f\u00a7\u0001\u0000"+
		"\u0000\u0000\u0860\u0861\u0005\u00c8\u0000\u0000\u0861\u0862\u0005\u019c"+
		"\u0000\u0000\u0862\u0863\u0005\u009c\u0000\u0000\u0863\u0864\u0003\u00ac"+
		"V\u0000\u0864\u0865\u0003\u00aeW\u0000\u0865\u0866\u0005Z\u0000\u0000"+
		"\u0866\u086b\u0003\u00b0X\u0000\u0867\u0868\u0005\u01a8\u0000\u0000\u0868"+
		"\u086a\u0003\u00b0X\u0000\u0869\u0867\u0001\u0000\u0000\u0000\u086a\u086d"+
		"\u0001\u0000\u0000\u0000\u086b\u0869\u0001\u0000\u0000\u0000\u086b\u086c"+
		"\u0001\u0000\u0000\u0000\u086c\u086e\u0001\u0000\u0000\u0000\u086d\u086b"+
		"\u0001\u0000\u0000\u0000\u086e\u086f\u0003\u00fa}\u0000\u086f\u08a9\u0001"+
		"\u0000\u0000\u0000\u0870\u0871\u0005\u00c8\u0000\u0000\u0871\u0876\u0003"+
		"\u00aaU\u0000\u0872\u0873\u0005\u01a8\u0000\u0000\u0873\u0875\u0003\u00aa"+
		"U\u0000\u0874\u0872\u0001\u0000\u0000\u0000\u0875\u0878\u0001\u0000\u0000"+
		"\u0000\u0876\u0874\u0001\u0000\u0000\u0000\u0876\u0877\u0001\u0000\u0000"+
		"\u0000\u0877\u0879\u0001\u0000\u0000\u0000\u0878\u0876\u0001\u0000\u0000"+
		"\u0000\u0879\u087b\u0007!\u0000\u0000\u087a\u087c\u0003\u00acV\u0000\u087b"+
		"\u087a\u0001\u0000\u0000\u0000\u087b\u087c\u0001\u0000\u0000\u0000\u087c"+
		"\u087d\u0001\u0000\u0000\u0000\u087d\u087e\u0003\u00aeW\u0000\u087e\u087f"+
		"\u0005Z\u0000";
	private static final String _serializedATNSegment1 =
		"\u0000\u087f\u0884\u0003\u00b0X\u0000\u0880\u0881\u0005\u01a8\u0000\u0000"+
		"\u0881\u0883\u0003\u00b0X\u0000\u0882\u0880\u0001\u0000\u0000\u0000\u0883"+
		"\u0886\u0001\u0000\u0000\u0000\u0884\u0882\u0001\u0000\u0000\u0000\u0884"+
		"\u0885\u0001\u0000\u0000\u0000\u0885\u0889\u0001\u0000\u0000\u0000\u0886"+
		"\u0884\u0001\u0000\u0000\u0000\u0887\u0888\u0005\u0014\u0000\u0000\u0888"+
		"\u088a\u0005\u0006\u0000\u0000\u0889\u0887\u0001\u0000\u0000\u0000\u0889"+
		"\u088a\u0001\u0000\u0000\u0000\u088a\u088c\u0001\u0000\u0000\u0000\u088b"+
		"\u088d\u0007\u001c\u0000\u0000\u088c\u088b\u0001\u0000\u0000\u0000\u088c"+
		"\u088d\u0001\u0000\u0000\u0000\u088d\u088e\u0001\u0000\u0000\u0000\u088e"+
		"\u088f\u0003\u00fa}\u0000\u088f\u08a9\u0001\u0000\u0000\u0000\u0890\u0891"+
		"\u0005\u00c8\u0000\u0000\u0891\u0896\u0003\u00aaU\u0000\u0892\u0893\u0005"+
		"\u01a8\u0000\u0000\u0893\u0895\u0003\u00aaU\u0000\u0894\u0892\u0001\u0000"+
		"\u0000\u0000\u0895\u0898\u0001\u0000\u0000\u0000\u0896\u0894\u0001\u0000"+
		"\u0000\u0000\u0896\u0897\u0001\u0000\u0000\u0000\u0897\u0899\u0001\u0000"+
		"\u0000\u0000\u0898\u0896\u0001\u0000\u0000\u0000\u0899\u089a\u0005Z\u0000"+
		"\u0000\u089a\u089f\u0003\u00b0X\u0000\u089b\u089c\u0005\u01a8\u0000\u0000"+
		"\u089c\u089e\u0003\u00b0X\u0000\u089d\u089b\u0001\u0000\u0000\u0000\u089e"+
		"\u08a1\u0001\u0000\u0000\u0000\u089f\u089d\u0001\u0000\u0000\u0000\u089f"+
		"\u08a0\u0001\u0000\u0000\u0000\u08a0\u08a4\u0001\u0000\u0000\u0000\u08a1"+
		"\u089f\u0001\u0000\u0000\u0000\u08a2\u08a3\u0005\u0014\u0000\u0000\u08a3"+
		"\u08a5\u0005\u0006\u0000\u0000\u08a4\u08a2\u0001\u0000\u0000\u0000\u08a4"+
		"\u08a5\u0001\u0000\u0000\u0000\u08a5\u08a6\u0001\u0000\u0000\u0000\u08a6"+
		"\u08a7\u0003\u00fa}\u0000\u08a7\u08a9\u0001\u0000\u0000\u0000\u08a8\u0860"+
		"\u0001\u0000\u0000\u0000\u08a8\u0870\u0001\u0000\u0000\u0000\u08a8\u0890"+
		"\u0001\u0000\u0000\u0000\u08a9\u00a9\u0001\u0000\u0000\u0000\u08aa\u08ac"+
		"\u0005\u0006\u0000\u0000\u08ab\u08ad\u0005\u00b6\u0000\u0000\u08ac\u08ab"+
		"\u0001\u0000\u0000\u0000\u08ac\u08ad\u0001\u0000\u0000\u0000\u08ad\u08c6"+
		"\u0001\u0000\u0000\u0000\u08ae\u08c6\u0005\u00df\u0000\u0000\u08af\u08c6"+
		"\u0005r\u0000\u0000\u08b0\u08b2\u0005\u00fe\u0000\u0000\u08b1\u08b3\u0003"+
		"\"\u0011\u0000\u08b2\u08b1\u0001\u0000\u0000\u0000\u08b2\u08b3\u0001\u0000"+
		"\u0000\u0000\u08b3\u08c6\u0001\u0000\u0000\u0000\u08b4\u08c6\u00058\u0000"+
		"\u0000\u08b5\u08b7\u0005\u00be\u0000\u0000\u08b6\u08b8\u0003\"\u0011\u0000"+
		"\u08b7\u08b6\u0001\u0000\u0000\u0000\u08b7\u08b8\u0001\u0000\u0000\u0000"+
		"\u08b8\u08c6\u0001\u0000\u0000\u0000\u08b9\u08c6\u0005\t\u0000\u0000\u08ba"+
		"\u08c6\u0005m\u0000\u0000\u08bb\u08c6\u0005\u00f7\u0000\u0000\u08bc\u08c6"+
		"\u0005N\u0000\u0000\u08bd\u08c6\u0005\u019b\u0000\u0000\u08be\u08c6\u0005"+
		"\u015c\u0000\u0000\u08bf\u08c6\u0005\u019a\u0000\u0000\u08c0\u08c6\u0005"+
		"-\u0000\u0000\u08c1\u08c6\u0005@\u0000\u0000\u08c2\u08c3\u0005\u019c\u0000"+
		"\u0000\u08c3\u08c6\u0005\u009c\u0000\u0000\u08c4\u08c6\u0003\u00f0x\u0000"+
		"\u08c5\u08aa\u0001\u0000\u0000\u0000\u08c5\u08ae\u0001\u0000\u0000\u0000"+
		"\u08c5\u08af\u0001\u0000\u0000\u0000\u08c5\u08b0\u0001\u0000\u0000\u0000"+
		"\u08c5\u08b4\u0001\u0000\u0000\u0000\u08c5\u08b5\u0001\u0000\u0000\u0000"+
		"\u08c5\u08b9\u0001\u0000\u0000\u0000\u08c5\u08ba\u0001\u0000\u0000\u0000"+
		"\u08c5\u08bb\u0001\u0000\u0000\u0000\u08c5\u08bc\u0001\u0000\u0000\u0000"+
		"\u08c5\u08bd\u0001\u0000\u0000\u0000\u08c5\u08be\u0001\u0000\u0000\u0000"+
		"\u08c5\u08bf\u0001\u0000\u0000\u0000\u08c5\u08c0\u0001\u0000\u0000\u0000"+
		"\u08c5\u08c1\u0001\u0000\u0000\u0000\u08c5\u08c2\u0001\u0000\u0000\u0000"+
		"\u08c5\u08c4\u0001\u0000\u0000\u0000\u08c6\u00ab\u0001\u0000\u0000\u0000"+
		"\u08c7\u08dd\u0005\u00f3\u0000\u0000\u08c8\u08ca\u0007\n\u0000\u0000\u08c9"+
		"\u08c8\u0001\u0000\u0000\u0000\u08c9\u08ca\u0001\u0000\u0000\u0000\u08ca"+
		"\u08cb\u0001\u0000\u0000\u0000\u08cb\u08dd\u0005\u00f4\u0000\u0000\u08cc"+
		"\u08dd\u00052\u0000\u0000\u08cd\u08dd\u0005\u00ea\u0000\u0000\u08ce\u08dd"+
		"\u0005\u00d8\u0000\u0000\u08cf\u08dd\u0005\u00de\u0000\u0000\u08d0\u08dd"+
		"\u0007\u001f\u0000\u0000\u08d1\u08d3\u0005=\u0000\u0000\u08d2\u08d1\u0001"+
		"\u0000\u0000\u0000\u08d2\u08d3\u0001\u0000\u0000\u0000\u08d3\u08d4\u0001"+
		"\u0000\u0000\u0000\u08d4\u08dd\u0005\u00f9\u0000\u0000\u08d5\u08dd\u0005"+
		"\u0104\u0000\u0000\u08d6\u08dd\u0005\u00a7\u0000\u0000\u08d7\u08dd\u0005"+
		"!\u0000\u0000\u08d8\u08dd\u0005\u00b1\u0000\u0000\u08d9\u08dd\u0005\u0013"+
		"\u0000\u0000\u08da\u08dd\u0005\u00ca\u0000\u0000\u08db\u08dd\u0005\u00f2"+
		"\u0000\u0000\u08dc\u08c7\u0001\u0000\u0000\u0000\u08dc\u08c9\u0001\u0000"+
		"\u0000\u0000\u08dc\u08cc\u0001\u0000\u0000\u0000\u08dc\u08cd\u0001\u0000"+
		"\u0000\u0000\u08dc\u08ce\u0001\u0000\u0000\u0000\u08dc\u08cf\u0001\u0000"+
		"\u0000\u0000\u08dc\u08d0\u0001\u0000\u0000\u0000\u08dc\u08d2\u0001\u0000"+
		"\u0000\u0000\u08dc\u08d5\u0001\u0000\u0000\u0000\u08dc\u08d6\u0001\u0000"+
		"\u0000\u0000\u08dc\u08d7\u0001\u0000\u0000\u0000\u08dc\u08d8\u0001\u0000"+
		"\u0000\u0000\u08dc\u08d9\u0001\u0000\u0000\u0000\u08dc\u08da\u0001\u0000"+
		"\u0000\u0000\u08dc\u08db\u0001\u0000\u0000\u0000\u08dd\u00ad\u0001\u0000"+
		"\u0000\u0000\u08de\u08e3\u0003\u00eew\u0000\u08df\u08e0\u0005\u01a8\u0000"+
		"\u0000\u08e0\u08e2\u0003\u00eew\u0000\u08e1\u08df\u0001\u0000\u0000\u0000"+
		"\u08e2\u08e5\u0001\u0000\u0000\u0000\u08e3\u08e1\u0001\u0000\u0000\u0000"+
		"\u08e3\u08e4\u0001\u0000\u0000\u0000\u08e4\u00af\u0001\u0000\u0000\u0000"+
		"\u08e5\u08e3\u0001\u0000\u0000\u0000\u08e6\u08e9\u0005\u00ba\u0000\u0000"+
		"\u08e7\u08e8\u0005\u0124\u0000\u0000\u08e8\u08ea\u0005\u0006\u0000\u0000"+
		"\u08e9\u08e7\u0001\u0000\u0000\u0000\u08e9\u08ea\u0001\u0000\u0000\u0000"+
		"\u08ea\u08f1\u0001\u0000\u0000\u0000\u08eb\u08ec\u0005\u00ca\u0000\u0000"+
		"\u08ec\u08f1\u0003\u00f0x\u0000\u08ed\u08ee\u0005\u00ff\u0000\u0000\u08ee"+
		"\u08f1\u0003\u00f0x\u0000\u08ef\u08f1\u0003\u00f0x\u0000\u08f0\u08e6\u0001"+
		"\u0000\u0000\u0000\u08f0\u08eb\u0001\u0000\u0000\u0000\u08f0\u08ed\u0001"+
		"\u0000\u0000\u0000\u08f0\u08ef\u0001\u0000\u0000\u0000\u08f1\u00b1\u0001"+
		"\u0000\u0000\u0000\u08f2\u08f3\u0005$\u0000\u0000\u08f3\u08f4\u0005\u009e"+
		"\u0000\u0000\u08f4\u08f5\u0003\u00b4Z\u0000\u08f5\u08f6\u0005u\u0000\u0000"+
		"\u08f6\u08f7\u0005\u01b8\u0000\u0000\u08f7\u08f8\u0003\u00fa}\u0000\u08f8"+
		"\u00b3\u0001\u0000\u0000\u0000\u08f9\u08fb\u0007\"\u0000\u0000\u08fa\u08f9"+
		"\u0001\u0000\u0000\u0000\u08fa\u08fb\u0001\u0000\u0000\u0000\u08fb\u08fc"+
		"\u0001\u0000\u0000\u0000\u08fc\u090a\u0003\u00eew\u0000\u08fd\u08fe\u0005"+
		"#\u0000\u0000\u08fe\u08ff\u0003\u00eew\u0000\u08ff\u0900\u0005\u01aa\u0000"+
		"\u0000\u0900\u0901\u0003\u00f0x\u0000\u0901\u090a\u0001\u0000\u0000\u0000"+
		"\u0902\u0904\u0005=\u0000\u0000\u0903\u0902\u0001\u0000\u0000\u0000\u0903"+
		"\u0904\u0001\u0000\u0000\u0000\u0904\u0905\u0001\u0000\u0000\u0000\u0905"+
		"\u0906\u0005\u00f9\u0000\u0000\u0906\u090a\u0003\u00eew\u0000\u0907\u0908"+
		"\u0007\u001f\u0000\u0000\u0908\u090a\u0003\u00eew\u0000\u0909\u08fa\u0001"+
		"\u0000\u0000\u0000\u0909\u08fd\u0001\u0000\u0000\u0000\u0909\u0903\u0001"+
		"\u0000\u0000\u0000\u0909\u0907\u0001\u0000\u0000\u0000\u090a\u00b5\u0001"+
		"\u0000\u0000\u0000\u090b\u090c\u0005{\u0000\u0000\u090c\u090d\u0005\u009e"+
		"\u0000\u0000\u090d\u090e\u0003\u00b8\\\u0000\u090e\u090f\u0005u\u0000"+
		"\u0000\u090f\u0910\u0005\u01b8\u0000\u0000\u0910\u0911\u0003\u00fa}\u0000"+
		"\u0911\u00b7\u0001\u0000\u0000\u0000\u0912\u0914\u0007#\u0000\u0000\u0913"+
		"\u0912\u0001\u0000\u0000\u0000\u0913\u0914\u0001\u0000\u0000\u0000\u0914"+
		"\u0915\u0001\u0000\u0000\u0000\u0915\u091c\u0003\u00eew\u0000\u0916\u0917"+
		"\u0005#\u0000\u0000\u0917\u0918\u0003\u00eew\u0000\u0918\u0919\u0005\u01aa"+
		"\u0000\u0000\u0919\u091a\u0003\u00f0x\u0000\u091a\u091c\u0001\u0000\u0000"+
		"\u0000\u091b\u0913\u0001\u0000\u0000\u0000\u091b\u0916\u0001\u0000\u0000"+
		"\u0000\u091c\u00b9\u0001\u0000\u0000\u0000\u091d\u091f\u0005\u00c2\u0000"+
		"\u0000\u091e\u0920\u0005\u00f3\u0000\u0000\u091f\u091e\u0001\u0000\u0000"+
		"\u0000\u091f\u0920\u0001\u0000\u0000\u0000\u0920\u0921\u0001\u0000\u0000"+
		"\u0000\u0921\u0922\u0003\u00eew\u0000\u0922\u0923\u0005\u00f6\u0000\u0000"+
		"\u0923\u0924\u0003\u00f0x\u0000\u0924\u0925\u0003\u00fa}\u0000\u0925\u092e"+
		"\u0001\u0000\u0000\u0000\u0926\u0927\u0005\u00c2\u0000\u0000\u0927\u0928"+
		"\u0005m\u0000\u0000\u0928\u0929\u0003\u00eew\u0000\u0929\u092a\u0005\u00f6"+
		"\u0000\u0000\u092a\u092b\u0003\u00f0x\u0000\u092b\u092c\u0003\u00fa}\u0000"+
		"\u092c\u092e\u0001\u0000\u0000\u0000\u092d\u091d\u0001\u0000\u0000\u0000"+
		"\u092d\u0926\u0001\u0000\u0000\u0000\u092e\u00bb\u0001\u0000\u0000\u0000"+
		"\u092f\u0930\u0005\u00e1\u0000\u0000\u0930\u0932\u0003\u00be_\u0000\u0931"+
		"\u0933\u0005\u01b1\u0000\u0000\u0932\u0931\u0001\u0000\u0000\u0000\u0932"+
		"\u0933\u0001\u0000\u0000\u0000\u0933\u0934\u0001\u0000\u0000\u0000\u0934"+
		"\u0935\u0003\u00e4r\u0000\u0935\u0936\u0003\u00fa}\u0000\u0936\u00bd\u0001"+
		"\u0000\u0000\u0000\u0937\u0939\u0005/\u0000\u0000\u0938\u0937\u0001\u0000"+
		"\u0000\u0000\u0938\u0939\u0001\u0000\u0000\u0000\u0939\u093b\u0001\u0000"+
		"\u0000\u0000\u093a\u093c\u0003\u00fc~\u0000\u093b\u093a\u0001\u0000\u0000"+
		"\u0000\u093c\u093d\u0001\u0000\u0000\u0000\u093d\u093b\u0001\u0000\u0000"+
		"\u0000\u093d\u093e\u0001\u0000\u0000\u0000\u093e\u0946\u0001\u0000\u0000"+
		"\u0000\u093f\u0940\u0005/\u0000\u0000\u0940\u0946\u0005\u00d8\u0000\u0000"+
		"\u0941\u0942\u0005/\u0000\u0000\u0942\u0946\u0005\u00ae\u0000\u0000\u0943"+
		"\u0946\u0005\u00ae\u0000\u0000\u0944\u0946\u0005\u00d8\u0000\u0000\u0945"+
		"\u0938\u0001\u0000\u0000\u0000\u0945\u093f\u0001\u0000\u0000\u0000\u0945"+
		"\u0941\u0001\u0000\u0000\u0000\u0945\u0943\u0001\u0000\u0000\u0000\u0945"+
		"\u0944\u0001\u0000\u0000\u0000\u0946\u00bf\u0001\u0000\u0000\u0000\u0947"+
		"\u0949\u0005%\u0000\u0000\u0948\u094a\u0005\u01a2\u0000\u0000\u0949\u0948"+
		"\u0001\u0000\u0000\u0000\u0949\u094a\u0001\u0000\u0000\u0000\u094a\u094b"+
		"\u0001\u0000\u0000\u0000\u094b\u094c\u0003\u00fa}\u0000\u094c\u00c1\u0001"+
		"\u0000\u0000\u0000\u094d\u094f\u0005\u00cb\u0000\u0000\u094e\u0950\u0005"+
		"\u01a2\u0000\u0000\u094f\u094e\u0001\u0000\u0000\u0000\u094f\u0950\u0001"+
		"\u0000\u0000\u0000\u0950\u0956\u0001\u0000\u0000\u0000\u0951\u0952\u0005"+
		"\u00f6\u0000\u0000\u0952\u0954\u0005\u00d7\u0000\u0000\u0953\u0955\u0003"+
		"\u00f0x\u0000\u0954\u0953\u0001\u0000\u0000\u0000\u0954\u0955\u0001\u0000"+
		"\u0000\u0000\u0955\u0957\u0001\u0000\u0000\u0000\u0956\u0951\u0001\u0000"+
		"\u0000\u0000\u0956\u0957\u0001\u0000\u0000\u0000\u0957\u0958\u0001\u0000"+
		"\u0000\u0000\u0958\u0959\u0003\u00fa}\u0000\u0959\u00c3\u0001\u0000\u0000"+
		"\u0000\u095a\u095b\u0005\u00d7\u0000\u0000\u095b\u095c\u0003\u00f0x\u0000"+
		"\u095c\u095d\u0005\u009e\u0000\u0000\u095d\u095e\u0005\u00cb\u0000\u0000"+
		"\u095e\u0960\u0005\u0182\u0000\u0000\u095f\u0961\u0005\u0135\u0000\u0000"+
		"\u0960\u095f\u0001\u0000\u0000\u0000\u0960\u0961\u0001\u0000\u0000\u0000"+
		"\u0961\u0962\u0001\u0000\u0000\u0000\u0962\u0963\u0003\u00fa}\u0000\u0963"+
		"\u00c5\u0001\u0000\u0000\u0000\u0964\u0966\u0005\u00c1\u0000\u0000\u0965"+
		"\u0967\u0005\u00f6\u0000\u0000\u0966\u0965\u0001\u0000\u0000\u0000\u0966"+
		"\u0967\u0001\u0000\u0000\u0000\u0967\u0968\u0001\u0000\u0000\u0000\u0968"+
		"\u0969\u0005\u00d7\u0000\u0000\u0969\u096a\u0003\u00f0x\u0000\u096a\u096b"+
		"\u0003\u00fa}\u0000\u096b\u00c7\u0001\u0000\u0000\u0000\u096c\u096d\u0005"+
		"\u0086\u0000\u0000\u096d\u096e\u0005\u00f3\u0000\u0000\u096e\u0971\u0003"+
		"\u00eew\u0000\u096f\u0970\u0005\u00a9\u0000\u0000\u0970\u0972\u0005\u01bc"+
		"\u0000\u0000\u0971\u096f\u0001\u0000\u0000\u0000\u0971\u0972\u0001\u0000"+
		"\u0000\u0000\u0972\u0973\u0001\u0000\u0000\u0000\u0973\u0974\u0005k\u0000"+
		"\u0000\u0974\u0975\u0007$\u0000\u0000\u0975\u0976\u0005\u0168\u0000\u0000"+
		"\u0976\u0977\u0003\u00fa}\u0000\u0977\u00c9\u0001\u0000\u0000\u0000\u0978"+
		"\u0979\u0005r\u0000\u0000\u0979\u097a\u0005t\u0000\u0000\u097a\u097c\u0003"+
		"\u00eew\u0000\u097b\u097d\u0003\"\u0011\u0000\u097c\u097b\u0001\u0000"+
		"\u0000\u0000\u097c\u097d\u0001\u0000\u0000\u0000\u097d\u0988\u0001\u0000"+
		"\u0000\u0000\u097e\u097f\u0005\u0103\u0000\u0000\u097f\u0984\u0003\u00cc"+
		"f\u0000\u0980\u0981\u0005\u01a8\u0000\u0000\u0981\u0983\u0003\u00ccf\u0000"+
		"\u0982\u0980\u0001\u0000\u0000\u0000\u0983\u0986\u0001\u0000\u0000\u0000"+
		"\u0984\u0982\u0001\u0000\u0000\u0000\u0984\u0985\u0001\u0000\u0000\u0000"+
		"\u0985\u0989\u0001\u0000\u0000\u0000\u0986\u0984\u0001\u0000\u0000\u0000"+
		"\u0987\u0989\u0003\u00ceg\u0000\u0988\u097e\u0001\u0000\u0000\u0000\u0988"+
		"\u0987\u0001\u0000\u0000\u0000\u0989\u098a\u0001\u0000\u0000\u0000\u098a"+
		"\u098b\u0003\u00fa}\u0000\u098b\u00cb\u0001\u0000\u0000\u0000\u098c\u098d"+
		"\u0005\u01a6\u0000\u0000\u098d\u0992\u0003\u00e4r\u0000\u098e\u098f\u0005"+
		"\u01a8\u0000\u0000\u098f\u0991\u0003\u00e4r\u0000\u0990\u098e\u0001\u0000"+
		"\u0000\u0000\u0991\u0994\u0001\u0000\u0000\u0000\u0992\u0990\u0001\u0000"+
		"\u0000\u0000\u0992\u0993\u0001\u0000\u0000\u0000\u0993\u0995\u0001\u0000"+
		"\u0000\u0000\u0994\u0992\u0001\u0000\u0000\u0000\u0995\u0996\u0005\u01a7"+
		"\u0000\u0000\u0996\u0999\u0001\u0000\u0000\u0000\u0997\u0999\u0003\u00e4"+
		"r\u0000\u0998\u098c\u0001\u0000\u0000\u0000\u0998\u0997\u0001\u0000\u0000"+
		"\u0000\u0999\u00cd\u0001\u0000\u0000\u0000\u099a\u09a0\u0003\u00d2i\u0000"+
		"\u099b\u099c\u0003\u00d0h\u0000\u099c\u099d\u0003\u00d2i\u0000\u099d\u099f"+
		"\u0001\u0000\u0000\u0000\u099e\u099b\u0001\u0000\u0000\u0000\u099f\u09a2"+
		"\u0001\u0000\u0000\u0000\u09a0\u099e\u0001\u0000\u0000\u0000\u09a0\u09a1"+
		"\u0001\u0000\u0000\u0000\u09a1\u00cf\u0001\u0000\u0000\u0000\u09a2\u09a0"+
		"\u0001\u0000\u0000\u0000\u09a3\u09a5\u0007%\u0000\u0000\u09a4\u09a6\u0005"+
		"\u0006\u0000\u0000\u09a5\u09a4\u0001\u0000\u0000\u0000\u09a5\u09a6\u0001"+
		"\u0000\u0000\u0000\u09a6\u00d1\u0001\u0000\u0000\u0000\u09a7\u09a9\u0005"+
		"\u00df\u0000\u0000\u09a8\u09aa\u0007&\u0000\u0000\u09a9\u09a8\u0001\u0000"+
		"\u0000\u0000\u09a9\u09aa\u0001\u0000\u0000\u0000\u09aa\u09ab\u0001\u0000"+
		"\u0000\u0000\u09ab\u09ac\u0003\u00d4j\u0000\u09ac\u09ad\u0005Z\u0000\u0000"+
		"\u09ad\u09b2\u0003\u00d8l\u0000\u09ae\u09af\u0005\u01a8\u0000\u0000\u09af"+
		"\u09b1\u0003\u00d8l\u0000\u09b0\u09ae\u0001\u0000\u0000\u0000\u09b1\u09b4"+
		"\u0001\u0000\u0000\u0000\u09b2\u09b0\u0001\u0000\u0000\u0000\u09b2\u09b3"+
		"\u0001\u0000\u0000\u0000\u09b3\u09b7\u0001\u0000\u0000\u0000\u09b4\u09b2"+
		"\u0001\u0000\u0000\u0000\u09b5\u09b6\u0005\u010c\u0000\u0000\u09b6\u09b8"+
		"\u0003\u00deo\u0000\u09b7\u09b5\u0001\u0000\u0000\u0000\u09b7\u09b8\u0001"+
		"\u0000\u0000\u0000\u09b8\u09c3\u0001\u0000\u0000\u0000\u09b9\u09ba\u0005"+
		"c\u0000\u0000\u09ba\u09bb\u0005\u0014\u0000\u0000\u09bb\u09c0\u0003\u00e4"+
		"r\u0000\u09bc\u09bd\u0005\u01a8\u0000\u0000\u09bd\u09bf\u0003\u00e4r\u0000"+
		"\u09be\u09bc\u0001\u0000\u0000\u0000\u09bf\u09c2\u0001\u0000\u0000\u0000"+
		"\u09c0\u09be\u0001\u0000\u0000\u0000\u09c0\u09c1\u0001\u0000\u0000\u0000"+
		"\u09c1\u09c4\u0001\u0000\u0000\u0000\u09c2\u09c0\u0001\u0000\u0000\u0000"+
		"\u09c3\u09b9\u0001\u0000\u0000\u0000\u09c3\u09c4\u0001\u0000\u0000\u0000"+
		"\u09c4\u09c7\u0001\u0000\u0000\u0000\u09c5\u09c6\u0005e\u0000\u0000\u09c6"+
		"\u09c8\u0003\u00deo\u0000\u09c7\u09c5\u0001\u0000\u0000\u0000\u09c7\u09c8"+
		"\u0001\u0000\u0000\u0000\u09c8\u09d3\u0001\u0000\u0000\u0000\u09c9\u09ca"+
		"\u0005\u00a3\u0000\u0000\u09ca\u09cb\u0005\u0014\u0000\u0000\u09cb\u09d0"+
		"\u0003\u00dcn\u0000\u09cc\u09cd\u0005\u01a8\u0000\u0000\u09cd\u09cf\u0003"+
		"\u00dcn\u0000\u09ce\u09cc\u0001\u0000\u0000\u0000\u09cf\u09d2\u0001\u0000"+
		"\u0000\u0000\u09d0\u09ce\u0001\u0000\u0000\u0000\u09d0\u09d1\u0001\u0000"+
		"\u0000\u0000\u09d1\u09d4\u0001\u0000\u0000\u0000\u09d2\u09d0\u0001\u0000"+
		"\u0000\u0000\u09d3\u09c9\u0001\u0000\u0000\u0000\u09d3\u09d4\u0001\u0000"+
		"\u0000\u0000\u09d4\u09dc\u0001\u0000\u0000\u0000\u09d5\u09d6\u0005T\u0000"+
		"\u0000\u09d6\u09d8\u0005W\u0000\u0000\u09d7\u09d9\u0005\u01bc\u0000\u0000"+
		"\u09d8\u09d7\u0001\u0000\u0000\u0000\u09d8\u09d9\u0001\u0000\u0000\u0000"+
		"\u09d9\u09da\u0001\u0000\u0000\u0000\u09da\u09db\u0007\'\u0000\u0000\u09db"+
		"\u09dd\u0005\u016d\u0000\u0000\u09dc\u09d5\u0001\u0000\u0000\u0000\u09dc"+
		"\u09dd\u0001\u0000\u0000\u0000\u09dd\u09e3\u0001\u0000\u0000\u0000\u09de"+
		"\u09df\u0005\u01a6\u0000\u0000\u09df\u09e0\u0003\u00ceg\u0000\u09e0\u09e1"+
		"\u0005\u01a7\u0000\u0000\u09e1\u09e3\u0001\u0000\u0000\u0000\u09e2\u09a7"+
		"\u0001\u0000\u0000\u0000\u09e2\u09de\u0001\u0000\u0000\u0000\u09e3\u00d3"+
		"\u0001\u0000\u0000\u0000\u09e4\u09ee\u0005\u01ac\u0000\u0000\u09e5\u09ea"+
		"\u0003\u00d6k\u0000\u09e6\u09e7\u0005\u01a8\u0000\u0000\u09e7\u09e9\u0003"+
		"\u00d6k\u0000\u09e8\u09e6\u0001\u0000\u0000\u0000\u09e9\u09ec\u0001\u0000"+
		"\u0000\u0000\u09ea\u09e8\u0001\u0000\u0000\u0000\u09ea\u09eb\u0001\u0000"+
		"\u0000\u0000\u09eb\u09ee\u0001\u0000\u0000\u0000\u09ec\u09ea\u0001\u0000"+
		"\u0000\u0000\u09ed\u09e4\u0001\u0000\u0000\u0000\u09ed\u09e5\u0001\u0000"+
		"\u0000\u0000\u09ee\u00d5\u0001\u0000\u0000\u0000\u09ef\u09f4\u0003\u00e4"+
		"r\u0000\u09f0\u09f2\u0005\f\u0000\u0000\u09f1\u09f0\u0001\u0000\u0000"+
		"\u0000\u09f1\u09f2\u0001\u0000\u0000\u0000\u09f2\u09f3\u0001\u0000\u0000"+
		"\u0000\u09f3\u09f5\u0003\u00f0x\u0000\u09f4\u09f1\u0001\u0000\u0000\u0000"+
		"\u09f4\u09f5\u0001\u0000\u0000\u0000\u09f5\u09fb\u0001\u0000\u0000\u0000"+
		"\u09f6\u09f7\u0003\u00eew\u0000\u09f7\u09f8\u0005\u01aa\u0000\u0000\u09f8"+
		"\u09f9\u0005\u01ac\u0000\u0000\u09f9\u09fb\u0001\u0000\u0000\u0000\u09fa"+
		"\u09ef\u0001\u0000\u0000\u0000\u09fa\u09f6\u0001\u0000\u0000\u0000\u09fb"+
		"\u00d7\u0001\u0000\u0000\u0000\u09fc\u09fd\u0006l\uffff\uffff\u0000\u09fd"+
		"\u0a02\u0003\u00eew\u0000\u09fe\u0a00\u0005\f\u0000\u0000\u09ff\u09fe"+
		"\u0001\u0000\u0000\u0000\u09ff\u0a00\u0001\u0000\u0000\u0000\u0a00\u0a01"+
		"\u0001\u0000\u0000\u0000\u0a01\u0a03\u0003\u00f0x\u0000\u0a02\u09ff\u0001"+
		"\u0000\u0000\u0000\u0a02\u0a03\u0001\u0000\u0000\u0000\u0a03\u0a0e\u0001"+
		"\u0000\u0000\u0000\u0a04\u0a05\u0005\u01a6\u0000\u0000\u0a05\u0a06\u0003"+
		"\u00ceg\u0000\u0a06\u0a0b\u0005\u01a7\u0000\u0000\u0a07\u0a09\u0005\f"+
		"\u0000\u0000\u0a08\u0a07\u0001\u0000\u0000\u0000\u0a08\u0a09\u0001\u0000"+
		"\u0000\u0000\u0a09\u0a0a\u0001\u0000\u0000\u0000\u0a0a\u0a0c\u0003\u00f0"+
		"x\u0000\u0a0b\u0a08\u0001\u0000\u0000\u0000\u0a0b\u0a0c\u0001\u0000\u0000"+
		"\u0000\u0a0c\u0a0e\u0001\u0000\u0000\u0000\u0a0d\u09fc\u0001\u0000\u0000"+
		"\u0000\u0a0d\u0a04\u0001\u0000\u0000\u0000\u0a0e\u0a1a\u0001\u0000\u0000"+
		"\u0000\u0a0f\u0a11\n\u0001\u0000\u0000\u0a10\u0a12\u0003\u00dam\u0000"+
		"\u0a11\u0a10\u0001\u0000\u0000\u0000\u0a11\u0a12\u0001\u0000\u0000\u0000"+
		"\u0a12\u0a13\u0001\u0000\u0000\u0000\u0a13\u0a14\u0005y\u0000\u0000\u0a14"+
		"\u0a15\u0003\u00d8l\u0000\u0a15\u0a16\u0005\u009e\u0000\u0000\u0a16\u0a17"+
		"\u0003\u00deo\u0000\u0a17\u0a19\u0001\u0000\u0000\u0000\u0a18\u0a0f\u0001"+
		"\u0000\u0000\u0000\u0a19\u0a1c\u0001\u0000\u0000\u0000\u0a1a\u0a18\u0001"+
		"\u0000\u0000\u0000\u0a1a\u0a1b\u0001\u0000\u0000\u0000\u0a1b\u00d9\u0001"+
		"\u0000\u0000\u0000\u0a1c\u0a1a\u0001\u0000\u0000\u0000\u0a1d\u0a23\u0005"+
		"o\u0000\u0000\u0a1e\u0a20\u0007(\u0000\u0000\u0a1f\u0a21\u0005\u00a6\u0000"+
		"\u0000\u0a20\u0a1f\u0001\u0000\u0000\u0000\u0a20\u0a21\u0001\u0000\u0000"+
		"\u0000\u0a21\u0a23\u0001\u0000\u0000\u0000\u0a22\u0a1d\u0001\u0000\u0000"+
		"\u0000\u0a22\u0a1e\u0001\u0000\u0000\u0000\u0a23\u00db\u0001\u0000\u0000"+
		"\u0000\u0a24\u0a26\u0003\u00e4r\u0000\u0a25\u0a27\u0007\u0006\u0000\u0000"+
		"\u0a26\u0a25\u0001\u0000\u0000\u0000\u0a26\u0a27\u0001\u0000\u0000\u0000"+
		"\u0a27\u00dd\u0001\u0000\u0000\u0000\u0a28\u0a29\u0006o\uffff\uffff\u0000"+
		"\u0a29\u0a2a\u0005\u0097\u0000\u0000\u0a2a\u0a31\u0003\u00deo\u0003\u0a2b"+
		"\u0a2c\u0005\u01a6\u0000\u0000\u0a2c\u0a2d\u0003\u00deo\u0000\u0a2d\u0a2e"+
		"\u0005\u01a7\u0000\u0000\u0a2e\u0a31\u0001\u0000\u0000\u0000\u0a2f\u0a31"+
		"\u0003\u00e0p\u0000\u0a30\u0a28\u0001\u0000\u0000\u0000\u0a30\u0a2b\u0001"+
		"\u0000\u0000\u0000\u0a30\u0a2f\u0001\u0000\u0000\u0000\u0a31\u0a37\u0001"+
		"\u0000\u0000\u0000\u0a32\u0a33\n\u0004\u0000\u0000\u0a33\u0a34\u0007)"+
		"\u0000\u0000\u0a34\u0a36\u0003\u00deo\u0005\u0a35\u0a32\u0001\u0000\u0000"+
		"\u0000\u0a36\u0a39\u0001\u0000\u0000\u0000\u0a37\u0a35\u0001\u0000\u0000"+
		"\u0000\u0a37\u0a38\u0001\u0000\u0000\u0000\u0a38\u00df\u0001\u0000\u0000"+
		"\u0000\u0a39\u0a37\u0001\u0000\u0000\u0000\u0a3a\u0a3b\u0003\u00e4r\u0000"+
		"\u0a3b\u0a3c\u0003\u00e2q\u0000\u0a3c\u0a3d\u0003\u00e4r\u0000\u0a3d\u0a72"+
		"\u0001\u0000\u0000\u0000\u0a3e\u0a40\u0003\u00e4r\u0000\u0a3f\u0a41\u0005"+
		"\u0097\u0000\u0000\u0a40\u0a3f\u0001\u0000\u0000\u0000\u0a40\u0a41\u0001"+
		"\u0000\u0000\u0000\u0a41\u0a42\u0001\u0000\u0000\u0000\u0a42\u0a43\u0005"+
		"\u0012\u0000\u0000\u0a43\u0a44\u0003\u00e4r\u0000\u0a44\u0a45\u0005\n"+
		"\u0000\u0000\u0a45\u0a46\u0003\u00e4r\u0000\u0a46\u0a72\u0001\u0000\u0000"+
		"\u0000\u0a47\u0a49\u0003\u00e4r\u0000\u0a48\u0a4a\u0005\u0097\u0000\u0000"+
		"\u0a49\u0a48\u0001\u0000\u0000\u0000\u0a49\u0a4a\u0001\u0000\u0000\u0000"+
		"\u0a4a\u0a4b\u0001\u0000\u0000\u0000\u0a4b\u0a4c\u0005k\u0000\u0000\u0a4c"+
		"\u0a56\u0005\u01a6\u0000\u0000\u0a4d\u0a52\u0003\u00e4r\u0000\u0a4e\u0a4f"+
		"\u0005\u01a8\u0000\u0000\u0a4f\u0a51\u0003\u00e4r\u0000\u0a50\u0a4e\u0001"+
		"\u0000\u0000\u0000\u0a51\u0a54\u0001\u0000\u0000\u0000\u0a52\u0a50\u0001"+
		"\u0000\u0000\u0000\u0a52\u0a53\u0001\u0000\u0000\u0000\u0a53\u0a57\u0001"+
		"\u0000\u0000\u0000\u0a54\u0a52\u0001\u0000\u0000\u0000\u0a55\u0a57\u0003"+
		"\u00ceg\u0000\u0a56\u0a4d\u0001\u0000\u0000\u0000\u0a56\u0a55\u0001\u0000"+
		"\u0000\u0000\u0a57\u0a58\u0001\u0000\u0000\u0000\u0a58\u0a59\u0005\u01a7"+
		"\u0000\u0000\u0a59\u0a72\u0001\u0000\u0000\u0000\u0a5a\u0a5c\u0003\u00e4"+
		"r\u0000\u0a5b\u0a5d\u0005\u0097\u0000\u0000\u0a5c\u0a5b\u0001\u0000\u0000"+
		"\u0000\u0a5c\u0a5d\u0001\u0000\u0000\u0000\u0a5d\u0a5e\u0001\u0000\u0000"+
		"\u0000\u0a5e\u0a5f\u0005\u0081\u0000\u0000\u0a5f\u0a62\u0003\u00e4r\u0000"+
		"\u0a60\u0a61\u0005J\u0000\u0000\u0a61\u0a63\u0003\u00e4r\u0000\u0a62\u0a60"+
		"\u0001\u0000\u0000\u0000\u0a62\u0a63\u0001\u0000\u0000\u0000\u0a63\u0a72"+
		"\u0001\u0000\u0000\u0000\u0a64\u0a65\u0003\u00e4r\u0000\u0a65\u0a67\u0005"+
		"u\u0000\u0000\u0a66\u0a68\u0005\u0097\u0000\u0000\u0a67\u0a66\u0001\u0000"+
		"\u0000\u0000\u0a67\u0a68\u0001\u0000\u0000\u0000\u0a68\u0a69\u0001\u0000"+
		"\u0000\u0000\u0a69\u0a6a\u0005\u0098\u0000\u0000\u0a6a\u0a72\u0001\u0000"+
		"\u0000\u0000\u0a6b\u0a6c\u0005O\u0000\u0000\u0a6c\u0a6d\u0005\u01a6\u0000"+
		"\u0000\u0a6d\u0a6e\u0003\u00ceg\u0000\u0a6e\u0a6f\u0005\u01a7\u0000\u0000"+
		"\u0a6f\u0a72\u0001\u0000\u0000\u0000\u0a70\u0a72\u0003\u00e4r\u0000\u0a71"+
		"\u0a3a\u0001\u0000\u0000\u0000\u0a71\u0a3e\u0001\u0000\u0000\u0000\u0a71"+
		"\u0a47\u0001\u0000\u0000\u0000\u0a71\u0a5a\u0001\u0000\u0000\u0000\u0a71"+
		"\u0a64\u0001\u0000\u0000\u0000\u0a71\u0a6b\u0001\u0000\u0000\u0000\u0a71"+
		"\u0a70\u0001\u0000\u0000\u0000\u0a72\u00e1\u0001\u0000\u0000\u0000\u0a73"+
		"\u0a74\u0007*\u0000\u0000\u0a74\u00e3\u0001\u0000\u0000\u0000\u0a75\u0a76"+
		"\u0006r\uffff\uffff\u0000\u0a76\u0a77\u0007+\u0000\u0000\u0a77\u0aa8\u0003"+
		"\u00e4r\u000b\u0a78\u0a79\u0005\u01a6\u0000\u0000\u0a79\u0a7a\u0003\u00e4"+
		"r\u0000\u0a7a\u0a7b\u0005\u01a7\u0000\u0000\u0a7b\u0aa8\u0001\u0000\u0000"+
		"\u0000\u0a7c\u0a7e\u0005\u0018\u0000\u0000\u0a7d\u0a7f\u0003\u00e6s\u0000"+
		"\u0a7e\u0a7d\u0001\u0000\u0000\u0000\u0a7f\u0a80\u0001\u0000\u0000\u0000"+
		"\u0a80\u0a7e\u0001\u0000\u0000\u0000\u0a80\u0a81\u0001\u0000\u0000\u0000"+
		"\u0a81\u0a84\u0001\u0000\u0000\u0000\u0a82\u0a83\u0005D\u0000\u0000\u0a83"+
		"\u0a85\u0003\u00e4r\u0000\u0a84\u0a82\u0001\u0000\u0000\u0000\u0a84\u0a85"+
		"\u0001\u0000\u0000\u0000\u0a85\u0a86\u0001\u0000\u0000\u0000\u0a86\u0a87"+
		"\u0005G\u0000\u0000\u0a87\u0aa8\u0001\u0000\u0000\u0000\u0a88\u0a89\u0005"+
		"\u0019\u0000\u0000\u0a89\u0a8a\u0005\u01a6\u0000\u0000\u0a8a\u0a8b\u0003"+
		"\u00e4r\u0000\u0a8b\u0a8c\u0005\f\u0000\u0000\u0a8c\u0a8d\u0003\u00f2"+
		"y\u0000\u0a8d\u0a8e\u0005\u01a7\u0000\u0000\u0a8e\u0aa8\u0001\u0000\u0000"+
		"\u0000\u0a8f\u0a90\u0003\u00eew\u0000\u0a90\u0a9a\u0005\u01a6\u0000\u0000"+
		"\u0a91\u0a9b\u0005\u01ac\u0000\u0000\u0a92\u0a97\u0003\u00e4r\u0000\u0a93"+
		"\u0a94\u0005\u01a8\u0000\u0000\u0a94\u0a96\u0003\u00e4r\u0000\u0a95\u0a93"+
		"\u0001\u0000\u0000\u0000\u0a96\u0a99\u0001\u0000\u0000\u0000\u0a97\u0a95"+
		"\u0001\u0000\u0000\u0000\u0a97\u0a98\u0001\u0000\u0000\u0000\u0a98\u0a9b"+
		"\u0001\u0000\u0000\u0000\u0a99\u0a97\u0001\u0000\u0000\u0000\u0a9a\u0a91"+
		"\u0001\u0000\u0000\u0000\u0a9a\u0a92\u0001\u0000\u0000\u0000\u0a9a\u0a9b"+
		"\u0001\u0000\u0000\u0000\u0a9b\u0a9c\u0001\u0000\u0000\u0000\u0a9c\u0a9d"+
		"\u0005\u01a7\u0000\u0000\u0a9d\u0aa8\u0001\u0000\u0000\u0000\u0a9e\u0a9f"+
		"\u0005\u01a6\u0000\u0000\u0a9f\u0aa0\u0003\u00ceg\u0000\u0aa0\u0aa1\u0005"+
		"\u01a7\u0000\u0000\u0aa1\u0aa8\u0001\u0000\u0000\u0000\u0aa2\u0aa8\u0003"+
		"\u00eau\u0000\u0aa3\u0aa8\u0003\u00e8t\u0000\u0aa4\u0aa8\u0003\u00eew"+
		"\u0000\u0aa5\u0aa8\u0005\u01be\u0000\u0000\u0aa6\u0aa8\u0005\u01b7\u0000"+
		"\u0000\u0aa7\u0a75\u0001\u0000\u0000\u0000\u0aa7\u0a78\u0001\u0000\u0000"+
		"\u0000\u0aa7\u0a7c\u0001\u0000\u0000\u0000\u0aa7\u0a88\u0001\u0000\u0000"+
		"\u0000\u0aa7\u0a8f\u0001\u0000\u0000\u0000\u0aa7\u0a9e\u0001\u0000\u0000"+
		"\u0000\u0aa7\u0aa2\u0001\u0000\u0000\u0000\u0aa7\u0aa3\u0001\u0000\u0000"+
		"\u0000\u0aa7\u0aa4\u0001\u0000\u0000\u0000\u0aa7\u0aa5\u0001\u0000\u0000"+
		"\u0000\u0aa7\u0aa6\u0001\u0000\u0000\u0000\u0aa8\u0ab7\u0001\u0000\u0000"+
		"\u0000\u0aa9\u0aaa\n\u000f\u0000\u0000\u0aaa\u0aab\u0007,\u0000\u0000"+
		"\u0aab\u0ab6\u0003\u00e4r\u0010\u0aac\u0aad\n\u000e\u0000\u0000\u0aad"+
		"\u0aae\u0007+\u0000\u0000\u0aae\u0ab6\u0003\u00e4r\u000f\u0aaf\u0ab0\n"+
		"\r\u0000\u0000\u0ab0\u0ab1\u0005\u01b0\u0000\u0000\u0ab1\u0ab6\u0003\u00e4"+
		"r\u000e\u0ab2\u0ab3\n\f\u0000\u0000\u0ab3\u0ab4\u0005&\u0000\u0000\u0ab4"+
		"\u0ab6\u0003\u00e4r\r\u0ab5\u0aa9\u0001\u0000\u0000\u0000\u0ab5\u0aac"+
		"\u0001\u0000\u0000\u0000\u0ab5\u0aaf\u0001\u0000\u0000\u0000\u0ab5\u0ab2"+
		"\u0001\u0000\u0000\u0000\u0ab6\u0ab9\u0001\u0000\u0000\u0000\u0ab7\u0ab5"+
		"\u0001\u0000\u0000\u0000\u0ab7\u0ab8\u0001\u0000\u0000\u0000\u0ab8\u00e5"+
		"\u0001\u0000\u0000\u0000\u0ab9\u0ab7\u0001\u0000\u0000\u0000\u0aba\u0abb"+
		"\u0005\u010a\u0000\u0000\u0abb\u0abc\u0003\u00deo\u0000\u0abc\u0abd\u0005"+
		"\u00f5\u0000\u0000\u0abd\u0abe\u0003\u00e4r\u0000\u0abe\u0ac5\u0001\u0000"+
		"\u0000\u0000\u0abf\u0ac0\u0005\u010a\u0000\u0000\u0ac0\u0ac1\u0003\u00e4"+
		"r\u0000\u0ac1\u0ac2\u0005\u00f5\u0000\u0000\u0ac2\u0ac3\u0003\u00e4r\u0000"+
		"\u0ac3\u0ac5\u0001\u0000\u0000\u0000\u0ac4\u0aba\u0001\u0000\u0000\u0000"+
		"\u0ac4\u0abf\u0001\u0000\u0000\u0000\u0ac5\u00e7\u0001\u0000\u0000\u0000"+
		"\u0ac6\u0ac8\u0005/\u0000\u0000\u0ac7\u0ac9\u0003\u00fc~\u0000\u0ac8\u0ac7"+
		"\u0001\u0000\u0000\u0000\u0ac9\u0aca\u0001\u0000\u0000\u0000\u0aca\u0ac8"+
		"\u0001\u0000\u0000\u0000\u0aca\u0acb\u0001\u0000\u0000\u0000\u0acb\u0acf"+
		"\u0001\u0000\u0000\u0000\u0acc\u0acf\u0005\u00ff\u0000\u0000\u0acd\u0acf"+
		"\u0005\u0098\u0000\u0000\u0ace\u0ac6\u0001\u0000\u0000\u0000\u0ace\u0acc"+
		"\u0001\u0000\u0000\u0000\u0ace\u0acd\u0001\u0000\u0000\u0000\u0acf\u00e9"+
		"\u0001\u0000\u0000\u0000\u0ad0\u0ad5\u0003\u00ecv\u0000\u0ad1\u0ad5\u0005"+
		"\u01b8\u0000\u0000\u0ad2\u0ad5\u0005\u01b9\u0000\u0000\u0ad3\u0ad5\u0005"+
		"\u01bb\u0000\u0000\u0ad4\u0ad0\u0001\u0000\u0000\u0000\u0ad4\u0ad1\u0001"+
		"\u0000\u0000\u0000\u0ad4\u0ad2\u0001\u0000\u0000\u0000\u0ad4\u0ad3\u0001"+
		"\u0000\u0000\u0000\u0ad5\u00eb\u0001\u0000\u0000\u0000\u0ad6\u0ad8\u0007"+
		"+\u0000\u0000\u0ad7\u0ad6\u0001\u0000\u0000\u0000\u0ad7\u0ad8\u0001\u0000"+
		"\u0000\u0000\u0ad8\u0ad9\u0001\u0000\u0000\u0000\u0ad9\u0ada\u0005\u01bc"+
		"\u0000\u0000\u0ada\u00ed\u0001\u0000\u0000\u0000\u0adb\u0ae1\u0003\u00f0"+
		"x\u0000\u0adc\u0add\u0004w\u000e\u0001\u0add\u0ade\u0005\u01aa\u0000\u0000"+
		"\u0ade\u0ae0\u0003\u00f0x\u0000\u0adf\u0adc\u0001\u0000\u0000\u0000\u0ae0"+
		"\u0ae3\u0001\u0000\u0000\u0000\u0ae1\u0adf\u0001\u0000\u0000\u0000\u0ae1"+
		"\u0ae2\u0001\u0000\u0000\u0000\u0ae2\u0ae4\u0001\u0000\u0000\u0000\u0ae3"+
		"\u0ae1\u0001\u0000\u0000\u0000\u0ae4\u0ae5\u0004w\u000f\u0001\u0ae5\u00ef"+
		"\u0001\u0000\u0000\u0000\u0ae6\u0aeb\u0005\u01bd\u0000\u0000\u0ae7\u0aeb"+
		"\u0005\u01ba\u0000\u0000\u0ae8\u0aeb\u0005\u01bb\u0000\u0000\u0ae9\u0aeb"+
		"\u0003\u00fc~\u0000\u0aea\u0ae6\u0001\u0000\u0000\u0000\u0aea\u0ae7\u0001"+
		"\u0000\u0000\u0000\u0aea\u0ae8\u0001\u0000\u0000\u0000\u0aea\u0ae9\u0001"+
		"\u0000\u0000\u0000\u0aeb\u00f1\u0001\u0000\u0000\u0000\u0aec\u0af4\u0003"+
		"\u00f4z\u0000\u0aed\u0aee\u0005\u01a6\u0000\u0000\u0aee\u0af1\u0005\u01bc"+
		"\u0000\u0000\u0aef\u0af0\u0005\u01a8\u0000\u0000\u0af0\u0af2\u0005\u01bc"+
		"\u0000\u0000\u0af1\u0aef\u0001\u0000\u0000\u0000\u0af1\u0af2\u0001\u0000"+
		"\u0000\u0000\u0af2\u0af3\u0001\u0000\u0000\u0000\u0af3\u0af5\u0005\u01a7"+
		"\u0000\u0000\u0af4\u0aed\u0001\u0000\u0000\u0000\u0af4\u0af5\u0001\u0000"+
		"\u0000\u0000\u0af5\u0af9\u0001\u0000\u0000\u0000\u0af6\u0af8\u0003\u00f6"+
		"{\u0000\u0af7\u0af6\u0001\u0000\u0000\u0000\u0af8\u0afb\u0001\u0000\u0000"+
		"\u0000\u0af9\u0af7\u0001\u0000\u0000\u0000\u0af9\u0afa\u0001\u0000\u0000"+
		"\u0000\u0afa\u00f3\u0001\u0000\u0000\u0000\u0afb\u0af9\u0001\u0000\u0000"+
		"\u0000\u0afc\u0afe\u0005\u001b\u0000\u0000\u0afd\u0aff\u0005\u019f\u0000"+
		"\u0000\u0afe\u0afd\u0001\u0000\u0000\u0000\u0afe\u0aff\u0001\u0000\u0000"+
		"\u0000\u0aff\u0b0c\u0001\u0000\u0000\u0000\u0b00\u0b02\u0005\u001c\u0000"+
		"\u0000\u0b01\u0b03\u0005\u019f\u0000\u0000\u0b02\u0b01\u0001\u0000\u0000"+
		"\u0000\u0b02\u0b03\u0001\u0000\u0000\u0000\u0b03\u0b0c\u0001\u0000\u0000"+
		"\u0000\u0b04\u0b05\u0005\u0089\u0000\u0000\u0b05\u0b0c\u0007-\u0000\u0000"+
		"\u0b06\u0b08\u0005?\u0000\u0000\u0b07\u0b09\u0005\u00b2\u0000\u0000\u0b08"+
		"\u0b07\u0001\u0000\u0000\u0000\u0b08\u0b09\u0001\u0000\u0000\u0000\u0b09"+
		"\u0b0c\u0001\u0000\u0000\u0000\u0b0a\u0b0c\u0003\u00eew\u0000\u0b0b\u0afc"+
		"\u0001\u0000\u0000\u0000\u0b0b\u0b00\u0001\u0000\u0000\u0000\u0b0b\u0b04"+
		"\u0001\u0000\u0000\u0000\u0b0b\u0b06\u0001\u0000\u0000\u0000\u0b0b\u0b0a"+
		"\u0001\u0000\u0000\u0000\u0b0c\u00f5\u0001\u0000\u0000\u0000\u0b0d\u0b0e"+
		"\u0005X\u0000\u0000\u0b0e\u0b0f\u0007\u0001\u0000\u0000\u0b0f\u0b19\u0005"+
		"1\u0000\u0000\u0b10\u0b11\u0005\u001a\u0000\u0000\u0b11\u0b19\u0003\u00f0"+
		"x\u0000\u0b12\u0b13\u0005\u010e\u0000\u0000\u0b13\u0b14\u0005\u0195\u0000"+
		"\u0000\u0b14\u0b19\u0005\u0115\u0000\u0000\u0b15\u0b16\u0005\u01a1\u0000"+
		"\u0000\u0b16\u0b17\u0005\u0195\u0000\u0000\u0b17\u0b19\u0005\u0115\u0000"+
		"\u0000\u0b18\u0b0d\u0001\u0000\u0000\u0000\u0b18\u0b10\u0001\u0000\u0000"+
		"\u0000\u0b18\u0b12\u0001\u0000\u0000\u0000\u0b18\u0b15\u0001\u0000\u0000"+
		"\u0000\u0b19\u00f7\u0001\u0000\u0000\u0000\u0b1a\u0b1b\u0005\u00b5\u0000"+
		"\u0000\u0b1b\u0b33\u0003\u00ecv\u0000\u0b1c\u0b1d\u0005\u00dc\u0000\u0000"+
		"\u0b1d\u0b33\u0003\u00ecv\u0000\u0b1e\u0b1f\u0005I\u0000\u0000\u0b1f\u0b33"+
		"\u0007\u0005\u0000\u0000\u0b20\u0b21\u0005\u0148\u0000\u0000\u0b21\u0b33"+
		"\u0005\u01bc\u0000\u0000\u0b22\u0b23\u0005\u0173\u0000\u0000\u0b23\u0b27"+
		"\u0005\u01bc\u0000\u0000\u0b24\u0b25\u0005X\u0000\u0000\u0b25\u0b26\u0005"+
		"\u00fe\u0000\u0000\u0b26\u0b28\u0005\u01bc\u0000\u0000\u0b27\u0b24\u0001"+
		"\u0000\u0000\u0000\u0b27\u0b28\u0001\u0000\u0000\u0000\u0b28\u0b33\u0001"+
		"\u0000\u0000\u0000\u0b29\u0b2a\u0005\u0149\u0000\u0000\u0b2a\u0b33\u0007"+
		".\u0000\u0000\u0b2b\u0b30\u0005\u0100\u0000\u0000\u0b2c\u0b2d\u0005\u00ea"+
		"\u0000\u0000\u0b2d\u0b31\u0003\u00f0x\u0000\u0b2e\u0b2f\u0005\u0106\u0000"+
		"\u0000\u0b2f\u0b31\u0003\u00f0x\u0000\u0b30\u0b2c\u0001\u0000\u0000\u0000"+
		"\u0b30\u0b2e\u0001\u0000\u0000\u0000\u0b31\u0b33\u0001\u0000\u0000\u0000"+
		"\u0b32\u0b1a\u0001\u0000\u0000\u0000\u0b32\u0b1c\u0001\u0000\u0000\u0000"+
		"\u0b32\u0b1e\u0001\u0000\u0000\u0000\u0b32\u0b20\u0001\u0000\u0000\u0000"+
		"\u0b32\u0b22\u0001\u0000\u0000\u0000\u0b32\u0b29\u0001\u0000\u0000\u0000"+
		"\u0b32\u0b2b\u0001\u0000\u0000\u0000\u0b33\u00f9\u0001\u0000\u0000\u0000"+
		"\u0b34\u0b36\u0005\u01a9\u0000\u0000\u0b35\u0b34\u0001\u0000\u0000\u0000"+
		"\u0b35\u0b36\u0001\u0000\u0000\u0000\u0b36\u00fb\u0001\u0000\u0000\u0000"+
		"\u0b37\u0b38\u0007/\u0000\u0000\u0b38\u00fd\u0001\u0000\u0000\u0000\u016d"+
		"\u0101\u0136\u0144\u014f\u0159\u0163\u0166\u016c\u0170\u0176\u0178\u017d"+
		"\u0184\u018d\u0193\u0199\u019c\u01a0\u01a6\u01ab\u01b4\u01b9\u01be\u01cb"+
		"\u01d4\u01db\u01e2\u01e7\u01ec\u01f9\u01ff\u020d\u0210\u0214\u0227\u022b"+
		"\u0235\u023b\u0241\u0246\u024c\u0253\u0259\u0263\u0266\u0270\u0277\u027a"+
		"\u0280\u0283\u0289\u0291\u029e\u02a6\u02ab\u02b0\u02bc\u02c1\u02c4\u02c7"+
		"\u02d2\u02da\u02de\u02ea\u02ef\u02f5\u02f9\u0301\u0308\u030f\u0315\u031b"+
		"\u0320\u0323\u0327\u032b\u032f\u0333\u0339\u033d\u0345\u034d\u0353\u0360"+
		"\u0367\u036c\u0375\u0379\u037f\u0382\u0385\u0389\u0395\u039a\u039f\u03a4"+
		"\u03a7\u03b8\u03bd\u03c1\u03c7\u03cc\u03d6\u03df\u03e7\u0406\u040e\u0417"+
		"\u0422\u042a\u0433\u0438\u043c\u0449\u044f\u0456\u045d\u0464\u046a\u047b"+
		"\u0480\u048f\u0495\u049a\u04a0\u04a5\u04a8\u04b5\u04bb\u04bf\u04c3\u04c9"+
		"\u04d1\u04db\u04e6\u04ee\u04f9\u04ff\u0502\u050c\u050f\u0521\u052d\u0534"+
		"\u0539\u0543\u0547\u054c\u0552\u0556\u0559\u055c\u0562\u0566\u056b\u0576"+
		"\u0580\u0586\u058d\u0595\u0599\u059d\u05aa\u05ad\u05af\u05b4\u05b7\u05c0"+
		"\u05c5\u05cf\u05d2\u05d5\u05da\u05de\u05e5\u05ef\u05f2\u05f8\u05fc\u0601"+
		"\u0604\u0611\u0616\u061e\u0620\u0628\u0635\u063a\u0640\u0648\u065d\u0671"+
		"\u0675\u067a\u0680\u0686\u068b\u0693\u0699\u069d\u06a5\u06af\u06ba\u06be"+
		"\u06cc\u06d2\u06df\u06e5\u06e8\u06eb\u06f3\u0702\u0705\u0710\u0712\u0716"+
		"\u071c\u0721\u072d\u0730\u073a\u0745\u0750\u0753\u0755\u0769\u076c\u076f"+
		"\u0774\u0778\u0785\u0788\u078b\u0790\u0794\u079c\u07b4\u07be\u07c3\u07c7"+
		"\u07cc\u07d1\u07d8\u07e5\u07f9\u07fc\u07ff\u0805\u0815\u0822\u082d\u0832"+
		"\u083b\u0841\u084b\u0854\u085a\u085e\u086b\u0876\u087b\u0884\u0889\u088c"+
		"\u0896\u089f\u08a4\u08a8\u08ac\u08b2\u08b7\u08c5\u08c9\u08d2\u08dc\u08e3"+
		"\u08e9\u08f0\u08fa\u0903\u0909\u0913\u091b\u091f\u092d\u0932\u0938\u093d"+
		"\u0945\u0949\u094f\u0954\u0956\u0960\u0966\u0971\u097c\u0984\u0988\u0992"+
		"\u0998\u09a0\u09a5\u09a9\u09b2\u09b7\u09c0\u09c3\u09c7\u09d0\u09d3\u09d8"+
		"\u09dc\u09e2\u09ea\u09ed\u09f1\u09f4\u09fa\u09ff\u0a02\u0a08\u0a0b\u0a0d"+
		"\u0a11\u0a1a\u0a20\u0a22\u0a26\u0a30\u0a37\u0a40\u0a49\u0a52\u0a56\u0a5c"+
		"\u0a62\u0a67\u0a71\u0a80\u0a84\u0a97\u0a9a\u0aa7\u0ab5\u0ab7\u0ac4\u0aca"+
		"\u0ace\u0ad4\u0ad7\u0ae1\u0aea\u0af1\u0af4\u0af9\u0afe\u0b02\u0b08\u0b0b"+
		"\u0b18\u0b27\u0b30\u0b32\u0b35";
	public static final String _serializedATN = Utils.join(
		new String[] {
			_serializedATNSegment0,
			_serializedATNSegment1
		},
		""
	);
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}