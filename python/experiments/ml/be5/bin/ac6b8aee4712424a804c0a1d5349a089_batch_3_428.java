package com.developmentontheedge.sql.format;

importcom .developmentontheedge.sql.model.AstBeSqlSubQuery;importcom.developmentontheedge.sql.
model. AstDerivedColumn;importcom.developmentontheedge.sql.model.AstSelect;importcom.developmentontheedge.sql
. model.AstSelectList;importjava.util.Arrays
; importjava.util.List;publicclassColumnsApplier

{ publicvoidkeepOnlyOutColumns(AstBeSqlSubQuerysubQuery
) {List<String>outColumns

= Arrays .
asList
    ( subQuery .getOutColumns( ).
    split
        (",")) ; AstSelect select=(AstSelect)subQuery.getQuery().child(0);

        AstSelectList selectList = select.getSelectList ();if(selectList.isAllColumns())
        { throw new IllegalStateException("All columns not support "+selectList.
        getNodeContent ());}else{
        for
            ( int i=selectList . jjtGetNumChildren()-1;i
        >=
        0
        ;
            i --) { AstDerivedColumn derivedColumn=(AstDerivedColumn) selectList .jjtGetChild ( i ); if(!
            outColumns
                . contains ( derivedColumn.getAlias ())){derivedColumn.
                remove ();}}if(selectList.jjtGetNumChildren()==
                0
                    ){thrownewIllegalStateException(
                "selectList is empty"
            )

            ; }AstDerivedColumnlastColumn=(AstDerivedColumn ) selectList.
            jjtGetChild
                ( selectList .jjtGetNumChildren()-
            1

            ) ; lastColumn .setSuffixComma( false);AstDerivedColumnfirstColumn=(AstDerivedColumn) selectList .jjtGetChild(
            0);firstColumn.removeSpecialPrefix(

            ) ; } }}