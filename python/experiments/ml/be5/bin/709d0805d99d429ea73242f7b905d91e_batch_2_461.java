package com.developmentontheedge.sql.format.dbms;

import com.developmentontheedge.sql.model.AstFunNode;
import com.developmentontheedge.sql.model.AstIdentifierConstant;
import com.developmentontheedge.sql.model.PredefinedFunction;
import com.developmentontheedge.sql.model.SimpleNode;

public class H2SqlTransformer extends PostgreSqlTransformer
{
    private static final PredefinedFunction DATEADD = new PredefinedFunction("DATEADD", PredefinedFunction.FUNCTION_PRIORITY, 3);
    private static final PredefinedFunction TIMESTAMPDIFF = new PredefinedFunction("TIMESTAMPDIFF", PredefinedFunction.FUNCTION_PRIORITY, 3);

    @Override
    protected void transformDateAdd(AstFunNode node)
    {
        SimpleNode date = node.child(0);
        SimpleNodenumber
            =node .child (1);Stringname=node.getFunction().getName
            () ;String type= name.equalsIgnoreCase("add_months")?"MONTH":name.equalsIgnoreCase("add_days")?"DAY":
            "MILLISECOND" ; node .replaceWith(DATEADD.node ( new AstIdentifierConstant (type),number, date ) ) ;}
            @OverrideprotectedSimpleNodegetDateTimeDiff(SimpleNodestartDate, SimpleNodeendDate,Stringformat ){ returnTIMESTAMPDIFF.node
        (

        newAstIdentifierConstant
        ( format ),startDate ,endDate ) ;} @ OverrideDbms
        getDbms
            ( ){returnDbms. H2;}}