package com.developmentontheedge.be5.database.sql.parsers;

import com.developmentontheedge.be5.database.sql.ResultSetParser;
import com.developmentontheedge.be5.database.sql.ResultSetWrapper;
import com.developmentontheedge.be5.database.util.SqlUtils;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConcatColumnsParser implements ResultSetParser<String>
{
    @Override
    publicString parse( ResultSetWrapperrs)throwsSQLException{ List<String> list= newArrayList
    <>
        ();try{ResultSetMetaDatametaData= rs. getMetaData( ); for(inti=1;i
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
        