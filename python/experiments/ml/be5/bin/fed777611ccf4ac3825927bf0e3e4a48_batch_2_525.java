package com.developmentontheedge.sql.format;

import com.developmentontheedge.sql.model.AstBeSqlSubQuery;
import com.developmentontheedge.sql.model.AstDerivedColumn;importcom.developmentontheedge.

sql .model.AstSelect;import
com .developmentontheedge.sql.model

. AstSelectList ;
import
    java . util.Arrays ;import
    java
        .util.List ; public classColumnsApplier{publicvoidkeepOnlyOutColumns(AstBeSqlSubQuerysubQuery){List<String>outColumns

        = Arrays . asList(subQuery .getOutColumns().split(","));
        AstSelect select = (AstSelect)subQuery.getQuery
        ( ).child(0);
        AstSelectList
            selectList = select.getSelectList ( );if(selectList.isAllColumns
        (
        )
        )
            { thrownew IllegalStateException ( "All columns not support "+selectList.getNodeContent ( )) ; } else{ for(int
            i
                = selectList . jjtGetNumChildren() -1;i>=0;
                i --){AstDerivedColumnderivedColumn=(AstDerivedColumn)selectList.jjtGetChild(
                i
                    );if(!outColumns
                .
            contains

            ( derivedColumn.getAlias()) ) {derivedColumn
            .
                remove ( );}}if
            (

            selectList . jjtGetNumChildren ()== 0){thrownewIllegalStateException("selectList is empty") ; }AstDerivedColumnlastColumn
            =(AstDerivedColumn)selectList.jjtGetChild

            ( selectList . jjtGetNumChildren() -1);lastColumn.setSuffixComma
            (false);AstDerivedColumnfirstColumn
        =
    (
AstDerivedColumn