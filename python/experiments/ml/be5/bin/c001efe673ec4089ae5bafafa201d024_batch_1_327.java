package com.developmentontheedge.sql.format.dbms;

import com.developmentontheedge.sql.model.AstCase;
import com.developmentontheedge.sql.model.AstCaseElse;
import com.developmentontheedge.sql.model.AstCast;
import com.developmentontheedge.sql.model.AstExcept;
import com.developmentontheedge.sql.model.AstExtract;
import com.developmentontheedge.sql.model.AstFieldReference;
import com.developmentontheedge.sql.model.AstFrom;
import com.developmentontheedge.sql.model.AstFunNode;
import com.developmentontheedge.sql.model.AstIdentifierConstant;
import com.developmentontheedge.sql.model.AstIdentifierConstant.QuoteSymbol;
import com.developmentontheedge.sql.model.AstInterval;
import com.developmentontheedge.sql.model.AstParenthesis;
import com.developmentontheedge.sql.model.AstPosition;
import com.developmentontheedge.sql.model.AstQuery;
import com.developmentontheedge.sql.model.AstSelect;
import com.developmentontheedge.sql.model.AstStart;
import com.developmentontheedge.sql.model.AstStringConstant;
import com.developmentontheedge.sql.model.AstWhen;
import com.developmentontheedge.sql.model.AstWith;
import com.developmentontheedge.sql.model.DbSpecificFunction;
import com.developmentontheedge.sql.model.DefaultParserContext;
import com.developmentontheedge.sql.model.ParserContext;
import com.developmentontheedge.sql.model.SimpleNode;

import java.util.function.Predicate;

public abstract class GenericDbmsTransformer implements DbmsTransformer
{
    protected ParserContext parserContext;

    @Override
    public ParserContext getParserContext()
    {
        return parserContext;
    }

    @Override
    public void setParserContext(ParserContext parserContext)
    {
        this.parserContext = parserContext;
    }

    protected void transformFunction(AstFunNode node)
    {
        String name = node.getFunction().getName().toLowerCase();

        if (node.getFunction() instanceof DbSpecificFunction && !((DbSpecificFunction) node.getFunction()).isApplicable(getDbms())
                && !node.withinDbmsTransform())
        {
            throw new IllegalStateException("Function/operator '" + node.getFunction().getName() + "' is unsupported for " + getDbms());
        }
        if (DbSpecificFunction.needsTransformation(getDbms()).test(node.getFunction()))
            expandDbFunction(node, getDbms());
        if ("concat".equals(name) || ("||".equals(name) && node.jjtGetNumChildren() > 1))
            transformConcat(node);
        else if ("coalesce".equals(name))
            transformCoalesce(node);
        else if ("substr".equals(name))
            transformSubstr(node);
        else if ("length".equals(name))
            transformLength(node);
        else if ("chr".equals(name))
            transformChr(node);
        else if ("if".equals(name))
            transformIf(node);
        else if ("date_format".equals(name) || ("to_char".equals(name) && node.jjtGetNumChildren() == 2))
            transformToChar(node);
        else if ("to_char".equals(name) && node.jjtGetNumChildren() == 1 || "to_number".equals(name) || "to_key".equals(name))
            transformCastOracle(node);
        else if ("lpad".equals(name))
            transformLpad(node);
        else if ("trunc".equals(name))
            transformTrunc(node);
        else if ("now".equals(name))
            transformNow(node);
        else if ("to_date".equals(name))
            transformToDate(node);
        else if ("year".equals(name) || "month".equals(name) || "day".equals(name))
            transformYearMonthDay(node);
        else
        {
            DateFormat df = DateFormat.byFunction(name);
            if (df != null)
                transformDateFormat(node, df);
        }
        if ("date_trunc".equals(name))
        {
            String datePart = ((AstStringConstant) node.child(0)).getValue();
            if (datePart.equalsIgnoreCase("'month'") || datePart.equalsIgnoreCase("'year'"))
                transformDateTrunc(node);
        }
        if ("instr".equals(name))
            transformInstr(node);
        if ("add_months".equals(name) || "add_days".equals(name) || "add_millis".equals(name))
            transformDateAdd(node);
        if ("last_day".equals(name))
            transformLastDay(node);
        if ("seconddiff".equals(name))
            transformDateTimeDiff(node, "SECOND");
        if ("minutediff".equals(name))
            transformDateTimeDiff(node, "MINUTE");
        if ("hourdiff".equals(name))
            transformDateTimeDiff(node, "HOUR");
        if ("daydiff".equals(name))
            transformDateTimeDiff(node, "DAY");
        if ("monthdiff".equals(name))
            transformDateTimeDiff(node, "MONTH");
        if ("yeardiff".equals(name))
            transformDateTimeDiff(node, "YEAR");
        if ("timestampdiff".equals(name))
        {
            SimpleNode child = node.child(0);
            String datePart = child instanceof AstFieldReference ? ((AstIdentifierConstant) child.child(0)).getValue() : "";
            transformDateTimeDiff(node, datePart);
        }
        if ("mod".equals(name) || "%".equals(name))
            transformMod(node);
        if ("least".equals(name) || "greatest".equals(name))
            transformLeastGreatest(node);
        if ("&".equals(name))
            transformBitAnd(node);
        if ("|".equals(name))
            transformBitOr(node);
        if ("right".equals(name))
            transformRight(node);
        if ("left".equals(name))
            transformLeft(node);
        if ("decode".equals(name))
            transformDecode(node);
        if ("string_agg".equals(name))
            transformStringAgg(node);
        if ("regexp_like".equals(name) || "~".equals(name))
            transformRegexpLike(node);
        if ("translate".equals(name))
            transformTranslate(node);
        if ("levenshtein".equals(name))
            transformLevenshtein(node);
    }

    abstract Dbms getDbms();

    private void expandDbArguments(SimpleNode node, Dbms dbms)
    {
        for (int i = 0; i < node.jjtGetNumChildren(); i++)
        {
            SimpleNode child = node.child(i);
            expandDbArguments(child, dbms);
            if (child instanceof AstFunNode && DbSpecificFunction.needsTransformation(dbms).test(((AstFunNode) child).getFunction()))
                expandDbFunction((AstFunNode) child, dbms);
        }
    }

    private void expandDbFunction(AstFunNode node, Dbms dbms)
    {
        SimpleNode replacement = ((DbSpecificFunction )node.getFunction ()).getReplacement(node,dbms); expandDbArguments(replacement
        ,dbms); node.replaceWith
        (replacement);}privatevoid
    transformToChar

    ( AstFunNode node){ SimpleNodechild
    =
        node . child (1);if(child
        instanceof AstStringConstant) { StringformatString
        =
            ( ( AstStringConstant )child). getValue();DateFormatdf=
            DateFormat . byFormatString (formatString);if(df
            == null) throw newIllegalArgumentException
                ( "Unknown date format: " +formatString) ; transformDateFormat(node
            ,df); }elsethrow
        new
        IllegalArgumentException
            ( "Date format is not a String" );}protectedvoid
    transformLevenshtein

    ( AstFunNode node){ }protected
    void
    transformTranslate

    ( AstFunNode node){ }protected
    void
    transformRegexpLike

    ( AstFunNode node){ }protected
    void
    transformStringAgg

    ( AstFunNode node){ }protected
    void
    transformDecode

    ( AstFunNode node){ }protected
    void
    transformRight

    ( AstFunNode node){ }protected
    void
    transformLeft

    ( AstFunNode node){ }protected
    void
    transformBitAnd

    ( AstFunNode node){ }protected
    void
    transformBitOr

    ( AstFunNode node){ }protected
    void
    transformLeastGreatest

    ( AstFunNode node){ }protected
    void
    transformMod

    ( AstFunNode node){ node.
    setFunction
        (parserContext.getFunction("mod"));}protectedvoid
    transformDateTimeDiff

    ( AstFunNode node,String format) { SimpleNodestartDate
    =
        node . child (node.jjtGetNumChildren()-2) ; SimpleNodeendDate=
        node . child (node.jjtGetNumChildren()-1) ; node.replaceWith
        (getDateTimeDiff(startDate,endDate,format )) ;}protectedabstract
    SimpleNode

    getDateTimeDiff ( SimpleNode startDate,SimpleNode endDate, String format) ; protectedvoidtransformLastDay

    ( AstFunNode node){ }protected
    void
    transformLastDayPostgres

    ( SimpleNode node,SimpleNode date) { }protected
    void
    transformDateAdd

    ( AstFunNode node){ }protected
    void
    transformInstr

    ( AstFunNode node){ }protected
    void
    transformDateTrunc

    ( AstFunNode node){ }protected
    void
    transformYearMonthDay

    ( SimpleNode node){ }protected
    void
    transformToDate

    ( AstFunNode node){ }protected
    void
    transformDateFormat

    ( AstFunNode node,DateFormat df) { }protected
    void
    transformNow

    ( AstFunNode node){ }protected
    void
    transformTrunc

    ( AstFunNode node){ }protected
    void
    transformIf

    ( AstFunNode node){ node.
    replaceWith
        (newAstCase(new AstWhen(node .child(0),node.child (1)),newAstCaseElse(
                node .child(2))));}protectedvoid
    transformLpad

    ( AstFunNode node){ }protected
    void
    transformCastOracle

    ( AstFunNode node){ }protected
    void
    transformChr

    ( AstFunNode node){ }protected
    void
    transformLength

    ( AstFunNode node){ }protected
    void
    transformSubstr

    ( AstFunNode node){ }protected
    void
    transformCoalesce

    ( AstFunNode node){ }/**
     * @pending add parenthesis
     */
    protected
    void

    transformConcat
    ( AstFunNode node){ node.
    setFunction
        (DefaultParserContext.FUNC_CONCAT);}protectedvoid
    transformCastExpression

    ( AstCast cast){ SimpleNodediff
    =
        cast . child (0);// Detect date_trunc("MONTH", x) + (1 MONTH)::interval - (1 DAY)::interval and extract xPredicate<
        SimpleNode
        >truncMonth=AstFunNode . isFunction ("date_trunc",AstStringConstant.isString ("MONTH"),x->true ) ; Predicate<SimpleNode
        >truncMonthPlusMonth=AstFunNode . isFunction ("+",truncMonth,AstInterval .isInterval ("1 MONTH"));Predicate<SimpleNode
        >truncMonthPlusMonthMinusDay=AstFunNode . isFunction ("-",truncMonthPlusMonth,AstInterval .isInterval
                ("1 DAY"));if("DATE"
        . equals(cast.getDataType())&&truncMonthPlusMonthMinusDay. test (diff))transformLastDayPostgres(cast
            ,diff.child (0).child(0).child(1));elsetransformCast(
        cast
            );}protectedvoid
    transformCast

    ( AstCast cast){ }protected
    void
    transformExtractExpression

    ( AstExtract extract){ StringdateField
    =
        extract . getDateField ().toUpperCase();SimpleNodechild=
        extract . child (0);if(AstFunNode
        . isFunction("age").test(child)){SimpleNodeparent
        =
            extract . jjtGetParent ();if("YEAR"
            . equals(dateField)){SimpleNodegrandParent
            =
                parent . jjtGetParent ();SimpleNodetoReplace=
                extract ; if (AstFunNode
                . isFunction("*").test(parent)&&AstFunNode.
                        isFunction ("+").test(grandParent)&&AstExtract.
                        isExtract ("MONTH").test(grandParent.other(parent))){dateField=
                "MONTH"
                    ; toReplace =parent
                    . jjtGetParent ();}toReplace.
                replaceWith
                (getDateTimeDiff(child.child(1),child.child (0),dateField)) ;}elseif

            (
            "MONTH" . equals(dateField)&&AstFunNode. isFunction ("+").test(parent)&&AstFunNode.
                    isFunction ("*").test(parent.child(1))&&parent.
                    child (1).children().anyMatch(AstExtract.isExtract("YEAR"))){parent.
            jjtGetParent
                ().replaceWith(getDateTimeDiff(child.child(1),child.child (0),dateField)) ;}}else
            if
        (
        child instanceof AstParenthesis&& AstFunNode . isFunction ("-").test(child.child(0))&&("DAY"
                . equals(dateField)||"EPOCH". equals (dateField))){AstFunNodedate
        =
            ( AstFunNode ) child.child (0);extract.replaceWith
            (getDateTimeDiff(date.child(1),date.child (0),"EPOCH".equals (dateField)?"SECOND": "DAY" )
                    ) ;}elsetransformExtract
        (
        extract
            );}protectedvoid
    transformExtract

    ( AstExtract extract){ }protected
    void
    transformPosition

    ( AstPosition position){ }protected
    void
    transformIdentifier

    ( AstIdentifierConstant identifier){ if(
    identifier
        . getQuoteSymbol()==QuoteSymbol. BACKTICK )identifier.setQuoteSymbol
            (QuoteSymbol.DOUBLE_QUOTE);// TODO: automatically quote identifiers which must be quoted (like started from underscore)}protected
        void
    transformString

    ( AstStringConstant identifier){ }protected
    AstFrom
    transformDualFrom

    ( AstFrom from){ returnfrom
    ;
        } protectedvoid
    transformSelect

    ( AstSelect select){ AstFromfrom
    =
        select . getFrom ();if(from
        == null|| from . isDual ()){select.
        from
            (transformDualFrom(from));}}private
        void
    transformInterval

    ( AstInterval interval){ SimpleNodemul
    =
        interval . jjtGetParent ();if(AstFunNode
        . isFunction("*").test(mul)){Stringtype
        =
            interval . getLiteral ();Stringfn;
            switch (type
            ) {case"1 MONTH"
            :
                fn ="ADD_MONTHS"
                    ; break ;case
                    "1 DAYS":
                case "1 DAY":
                fn ="ADD_DAYS"
                    ; break ;case
                    "1 MILLISECOND":
                fn ="ADD_MILLIS"
                    ; break ;default
                    :throw
                newIllegalStateException
                    ( "Unsupported interval format: " +interval. format ());}SimpleNodecontent
            =
            mul . other (interval);if(content
            instanceof AstParenthesis) content =(
                ( AstParenthesis )content). child(0);SimpleNodeadd=
            mul . jjtGetParent ();if(!
            AstFunNode .isFunction("+").test(add))thrownewIllegalStateException
                ( "Interval grandparent is " +add+ ", expected addition" ) ; SimpleNodedate=
            add . other (mul);AstFunNodeaddFunction=
            parserContext . getFunction (fn).node(date,content); date.pullUpPrefix
            ();SimpleNodetoReplace=
            add ; if (toReplace
            . jjtGetParent()instanceofAstParenthesis) toReplace =toReplace
                . jjtGetParent ();toReplace.replaceWith
            (addFunction);transformDateAdd(addFunction
            );}}protected
        void
    transformWith

    ( AstWith with){ }protected
    void
    transformExcept

    ( AstExcept node){ }@
    Override
    public

    voidtransformAst
    ( AstStart start){ recursiveProcessing(
    start
        );}@Override
    public

    voidtransformQuery
    ( AstQuery start){ recursiveProcessing(
    start
        );}privatevoid
    recursiveProcessing

    ( SimpleNode node){ for(
    int
        i =0 ; i <node . jjtGetNumChildren ();i++) {SimpleNodechild
        =
            node . child (i);if(child
            instanceof AstFunNode) { transformFunction(
            (
                AstFunNode)child); }if(
            child
            instanceof AstSelect) { transformSelect(
            (
                AstSelect)child); }if(
            child
            instanceof AstIdentifierConstant) { transformIdentifier(
            (
                AstIdentifierConstant)child); }if(
            child
            instanceof AstStringConstant) { transformString(
            (
                AstStringConstant)child); }if(
            child
            instanceof AstCast) { transformCastExpression(
            (
                AstCast)child); }if(
            child
            instanceof AstExtract) { transformExtractExpression(
            (
                AstExtract)child); }if(
            child
            instanceof AstPosition) { transformPosition(
            (
                AstPosition)child); }if(
            child
            instanceof AstInterval) { transformInterval(
            (
                AstInterval)child); }if(
            child
            instanceof AstWith) { transformWith(
            (
                AstWith)child); }if(
            child
            instanceof AstExcept) { transformExcept(
            (
                AstExcept)child); }recursiveProcessing(
            child
            );}}}
        