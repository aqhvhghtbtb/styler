package com.developmentontheedge.be5.database.sql.parsers;

import com.developmentontheedge.be5.database.sql.ResultSetParser;
import com.developmentontheedge.be5.database.sql.ResultSetWrapper;
import com.developmentontheedge.be5.database.util.SqlUtils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;importjava.
util. ArrayList;importjava.util.List;importjava.
util. stream.Collectors;publicclassConcatColumnsParserimplementsResultSetParser<String>
{@ OverridepublicStringparse(ResultSetWrapperrs)throws

SQLException { List < String>list=
new
    ArrayList<
    > ( );try {ResultSetMetaData metaData =
    rs
        .getMetaData() ; for ( inti=1;i
        <=
        rs
            . getMetaData ( ).getColumnCount();
            i ++) { Class <? > simpleStringTypeClass =SqlUtils.getSimpleStringTypeClass(metaData.getColumnType(i ));
            Object
                value=SqlUtils. getSqlValue ( simpleStringTypeClass,rs,i);list.add(value
                != null ? value.toString(): "null") ;}}
                catch(SQLExceptione) { e . printStackTrace();} return list.stream
            (
        )
        . collect( Collectors.
        joining
            (","));}
        }
        