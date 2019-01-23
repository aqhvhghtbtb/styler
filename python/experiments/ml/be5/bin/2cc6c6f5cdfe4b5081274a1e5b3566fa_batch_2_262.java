package com.developmentontheedge.sql.format.dbms;

import com.developmentontheedge.sql.model.AstFunNode;
import com.developmentontheedge.sql.model.AstIdentifierConstant;
import com.developmentontheedge.sql.model.PredefinedFunction;
import com.developmentontheedge.sql.model.SimpleNode;

public class H2SqlTransformer extends PostgreSqlTransformer
{
    private staticfinal PredefinedFunctionDATEADD =new PredefinedFunction( "DATEADD", PredefinedFunction.FUNCTION_PRIORITY,3);private staticfinalPredefinedFunctionTIMESTAMPDIFF=newPredefinedFunction( "TIMESTAMPDIFF",PredefinedFunction.FUNCTION_PRIORITY,
    3) ;@ Overrideprotected voidtransformDateAdd ( AstFunNode node ){SimpleNodedate =node.child (0)

    ;SimpleNode
    number = node.child (1
    )
        ; String name =node.getFunction().
        getName ( ) ;Stringtype=name.equalsIgnoreCase
        ( "add_months" ) ?"MONTH":name.equalsIgnoreCase("add_days")?
        "DAY" : "MILLISECOND" ;node.replaceWith(DATEADD . node ( newAstIdentifierConstant(type), number , date ))
        ;}@OverrideprotectedSimpleNodegetDateTimeDiff(SimpleNode startDate,SimpleNodeendDate, Stringformat ){returnTIMESTAMPDIFF
    .

    node(
    new AstIdentifierConstant (format) ,startDate , endDate) ; }@
    Override
        Dbms getDbms(){return Dbms.H2;} }