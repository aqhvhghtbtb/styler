package com.developmentontheedge.be5.database.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ResultSetWrapper implements ResultSet
{
    private ResultSet _res;

    ResultSetWrapper(ResultSet resultSet)
    {
        this._res = resultSet;
    }

    void setResultSet(ResultSet _res)
    {
        this._res = _res;
    }

    @Override
    public boolean next() throws SQLException
    {
        throw new UnsupportedOperationException("Method next() run automatically. Do not call this.");
    }

    @Override
    public void close() throws SQLException
    {
        _res.close();
    }

    @Override
    public boolean wasNull() throws SQLException
    {
        return _res.wasNull();
    }

    @Override
    public String getString(int columnIndex) throws SQLException
    {
        return _res.getString(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException
    {
        return _res.getBoolean(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException
    {
        return _res.getByte(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException
    {
        return _res.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException
    {
        return _res.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException
    {
        return _res.getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException
    {
        return _res.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException
    {
        return _res.getDouble(columnIndex);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException
    {
        return _res.getBigDecimal(columnIndex, scale);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException
    {
        return _res.getBytes(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException
    {
        return _res.getDate(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException
    {
        return _res.getTime(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException
    {
        return _res.getTimestamp(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException
    {
        return _res.getAsciiStream(columnIndex);
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException
    {
        return _res.getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException
    {
        return _res.getBinaryStream(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException
    {
        return _res.getString(columnLabel);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException
    {
        return _res.getBoolean(columnLabel);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException
    {
        return _res.getByte(columnLabel);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException
    {
        return _res.getShort(columnLabel);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException
    {
        return _res.getInt(columnLabel);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException
    {
        return _res.getLong(columnLabel);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException
    {
        return _res.getFloat(columnLabel);
    }

    @

    Override public doublegetDouble( StringcolumnLabel ) throws
    SQLException
        { return_res.getDouble(columnLabel)
    ;

    }@
    Override@
    Deprecated public BigDecimalgetBigDecimal( StringcolumnLabel , intscale ) throws
    SQLException
        { return_res.getBigDecimal(columnLabel ,scale)
    ;

    }@
    Override publicbyte[ ]getBytes( StringcolumnLabel ) throws
    SQLException
        { return_res.getBytes(columnLabel)
    ;

    }@
    Override public DategetDate( StringcolumnLabel ) throws
    SQLException
        { return_res.getDate(columnLabel)
    ;

    }@
    Override public TimegetTime( StringcolumnLabel ) throws
    SQLException
        { return_res.getTime(columnLabel)
    ;

    }@
    Override public TimestampgetTimestamp( StringcolumnLabel ) throws
    SQLException
        { return_res.getTimestamp(columnLabel)
    ;

    }@
    Override public InputStreamgetAsciiStream( StringcolumnLabel ) throws
    SQLException
        { return_res.getAsciiStream(columnLabel)
    ;

    }@
    Override@
    Deprecated public InputStreamgetUnicodeStream( StringcolumnLabel ) throws
    SQLException
        { return_res.getUnicodeStream(columnLabel)
    ;

    }@
    Override public InputStreamgetBinaryStream( StringcolumnLabel ) throws
    SQLException
        { return_res.getBinaryStream(columnLabel)
    ;

    }@
    Override public SQLWarninggetWarnings( ) throws
    SQLException
        { return_res.getWarnings()
    ;

    }@
    Override public voidclearWarnings( ) throws
    SQLException
        {_res.clearWarnings()
    ;

    }@
    Override public StringgetCursorName( ) throws
    SQLException
        { return_res.getCursorName()
    ;

    }@
    Override public ResultSetMetaDatagetMetaData( ) throws
    SQLException
        { return_res.getMetaData()
    ;

    }@
    Override public ObjectgetObject( intcolumnIndex ) throws
    SQLException
        { return_res.getObject(columnIndex)
    ;

    }@
    Override public ObjectgetObject( StringcolumnLabel ) throws
    SQLException
        { return_res.getObject(columnLabel)
    ;

    }@
    Override public intfindColumn( StringcolumnLabel ) throws
    SQLException
        { return_res.findColumn(columnLabel)
    ;

    }@
    Override public ReadergetCharacterStream( intcolumnIndex ) throws
    SQLException
        { return_res.getCharacterStream(columnIndex)
    ;

    }@
    Override public ReadergetCharacterStream( StringcolumnLabel ) throws
    SQLException
        { return_res.getCharacterStream(columnLabel)
    ;

    }@
    Override public BigDecimalgetBigDecimal( intcolumnIndex ) throws
    SQLException
        { return_res.getBigDecimal(columnIndex)
    ;

    }@
    Override public BigDecimalgetBigDecimal( StringcolumnLabel ) throws
    SQLException
        { return_res.getBigDecimal(columnLabel)
    ;

    }@
    Override public booleanisBeforeFirst( ) throws
    SQLException
        { return_res.isBeforeFirst()
    ;

    }@
    Override public booleanisAfterLast( ) throws
    SQLException
        { return_res.isAfterLast()
    ;

    }@
    Override public booleanisFirst( ) throws
    SQLException
        { return_res.isFirst()
    ;

    }@
    Override public booleanisLast( ) throws
    SQLException
        { return_res.isLast()
    ;

    }@
    Override public voidbeforeFirst( ) throws
    SQLException
        {_res.beforeFirst()
    ;

    }@
    Override public voidafterLast( ) throws
    SQLException
        {_res.afterLast()
    ;

    }@
    Override public booleanfirst( ) throws
    SQLException
        { return_res.first()
    ;

    }@
    Override public booleanlast( ) throws
    SQLException
        { return_res.last()
    ;

    }@
    Override public intgetRow( ) throws
    SQLException
        { return_res.getRow()
    ;

    }@
    Override public booleanabsolute( introw ) throws
    SQLException
        { return_res.absolute(row)
    ;

    }@
    Override public booleanrelative( introws ) throws
    SQLException
        { return_res.relative(rows)
    ;

    }@
    Override public booleanprevious( ) throws
    SQLException
        { return_res.previous()
    ;

    }@
    Override public voidsetFetchDirection( intdirection ) throws
    SQLException
        {_res.setFetchDirection(direction)
    ;

    }@
    Override public intgetFetchDirection( ) throws
    SQLException
        { return_res.getFetchDirection()
    ;

    }@
    Override public voidsetFetchSize( introws ) throws
    SQLException
        {_res.setFetchSize(rows)
    ;

    }@
    Override public intgetFetchSize( ) throws
    SQLException
        { return_res.getFetchSize()
    ;

    }@
    Override public intgetType( ) throws
    SQLException
        { return_res.getType()
    ;

    }@
    Override public intgetConcurrency( ) throws
    SQLException
        { return_res.getConcurrency()
    ;

    }@
    Override public booleanrowUpdated( ) throws
    SQLException
        { return_res.rowUpdated()
    ;

    }@
    Override public booleanrowInserted( ) throws
    SQLException
        { return_res.rowInserted()
    ;

    }@
    Override public booleanrowDeleted( ) throws
    SQLException
        { return_res.rowDeleted()
    ;

    }@
    Override public voidupdateNull( intcolumnIndex ) throws
    SQLException
        {_res.updateNull(columnIndex)
    ;

    }@
    Override public voidupdateBoolean( intcolumnIndex , booleanx ) throws
    SQLException
        {_res.updateBoolean(columnIndex ,x)
    ;

    }@
    Override public voidupdateByte( intcolumnIndex , bytex ) throws
    SQLException
        {_res.updateByte(columnIndex ,x)
    ;

    }@
    Override public voidupdateShort( intcolumnIndex , shortx ) throws
    SQLException
        {_res.updateShort(columnIndex ,x)
    ;

    }@
    Override public voidupdateInt( intcolumnIndex , intx ) throws
    SQLException
        {_res.updateInt(columnIndex ,x)
    ;

    }@
    Override public voidupdateLong( intcolumnIndex , longx ) throws
    SQLException
        {_res.updateLong(columnIndex ,x)
    ;

    }@
    Override public voidupdateFloat( intcolumnIndex , floatx ) throws
    SQLException
        {_res.updateFloat(columnIndex ,x)
    ;

    }@
    Override public voidupdateDouble( intcolumnIndex , doublex ) throws
    SQLException
        {_res.updateDouble(columnIndex ,x)
    ;

    }@
    Override public voidupdateBigDecimal( intcolumnIndex , BigDecimalx ) throws
    SQLException
        {_res.updateBigDecimal(columnIndex ,x)
    ;

    }@
    Override public voidupdateString( intcolumnIndex , Stringx ) throws
    SQLException
        {_res.updateString(columnIndex ,x)
    ;

    }@
    Override public voidupdateBytes( intcolumnIndex ,byte[ ]x ) throws
    SQLException
        {_res.updateBytes(columnIndex ,x)
    ;

    }@
    Override public voidupdateDate( intcolumnIndex , Datex ) throws
    SQLException
        {_res.updateDate(columnIndex ,x)
    ;

    }@
    Override public voidupdateTime( intcolumnIndex , Timex ) throws
    SQLException
        {_res.updateTime(columnIndex ,x)
    ;

    }@
    Override public voidupdateTimestamp( intcolumnIndex , Timestampx ) throws
    SQLException
        {_res.updateTimestamp(columnIndex ,x)
    ;

    }@
    Override public voidupdateAsciiStream( intcolumnIndex , InputStreamx , intlength ) throws
    SQLException
        {_res.updateAsciiStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateBinaryStream( intcolumnIndex , InputStreamx , intlength ) throws
    SQLException
        {_res.updateBinaryStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateCharacterStream( intcolumnIndex , Readerx , intlength ) throws
    SQLException
        {_res.updateCharacterStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateObject( intcolumnIndex , Objectx , intscaleOrLength ) throws
    SQLException
        {_res.updateObject(columnIndex ,x ,scaleOrLength)
    ;

    }@
    Override public voidupdateObject( intcolumnIndex , Objectx ) throws
    SQLException
        {_res.updateObject(columnIndex ,x)
    ;

    }@
    Override public voidupdateNull( StringcolumnLabel ) throws
    SQLException
        {_res.updateNull(columnLabel)
    ;

    }@
    Override public voidupdateBoolean( StringcolumnLabel , booleanx ) throws
    SQLException
        {_res.updateBoolean(columnLabel ,x)
    ;

    }@
    Override public voidupdateByte( StringcolumnLabel , bytex ) throws
    SQLException
        {_res.updateByte(columnLabel ,x)
    ;

    }@
    Override public voidupdateShort( StringcolumnLabel , shortx ) throws
    SQLException
        {_res.updateShort(columnLabel ,x)
    ;

    }@
    Override public voidupdateInt( StringcolumnLabel , intx ) throws
    SQLException
        {_res.updateInt(columnLabel ,x)
    ;

    }@
    Override public voidupdateLong( StringcolumnLabel , longx ) throws
    SQLException
        {_res.updateLong(columnLabel ,x)
    ;

    }@
    Override public voidupdateFloat( StringcolumnLabel , floatx ) throws
    SQLException
        {_res.updateFloat(columnLabel ,x)
    ;

    }@
    Override public voidupdateDouble( StringcolumnLabel , doublex ) throws
    SQLException
        {_res.updateDouble(columnLabel ,x)
    ;

    }@
    Override public voidupdateBigDecimal( StringcolumnLabel , BigDecimalx ) throws
    SQLException
        {_res.updateBigDecimal(columnLabel ,x)
    ;

    }@
    Override public voidupdateString( StringcolumnLabel , Stringx ) throws
    SQLException
        {_res.updateString(columnLabel ,x)
    ;

    }@
    Override public voidupdateBytes( StringcolumnLabel ,byte[ ]x ) throws
    SQLException
        {_res.updateBytes(columnLabel ,x)
    ;

    }@
    Override public voidupdateDate( StringcolumnLabel , Datex ) throws
    SQLException
        {_res.updateDate(columnLabel ,x)
    ;

    }@
    Override public voidupdateTime( StringcolumnLabel , Timex ) throws
    SQLException
        {_res.updateTime(columnLabel ,x)
    ;

    }@
    Override public voidupdateTimestamp( StringcolumnLabel , Timestampx ) throws
    SQLException
        {_res.updateTimestamp(columnLabel ,x)
    ;

    }@
    Override public voidupdateAsciiStream( StringcolumnLabel , InputStreamx , intlength ) throws
    SQLException
        {_res.updateAsciiStream(columnLabel ,x ,length)
    ;

    }@
    Override public voidupdateBinaryStream( StringcolumnLabel , InputStreamx , intlength ) throws
    SQLException
        {_res.updateBinaryStream(columnLabel ,x ,length)
    ;

    }@
    Override public voidupdateCharacterStream( StringcolumnLabel , Readerreader , intlength ) throws
    SQLException
        {_res.updateCharacterStream(columnLabel ,reader ,length)
    ;

    }@
    Override public voidupdateObject( StringcolumnLabel , Objectx , intscaleOrLength ) throws
    SQLException
        {_res.updateObject(columnLabel ,x ,scaleOrLength)
    ;

    }@
    Override public voidupdateObject( StringcolumnLabel , Objectx ) throws
    SQLException
        {_res.updateObject(columnLabel ,x)
    ;

    }@
    Override public voidinsertRow( ) throws
    SQLException
        {_res.insertRow()
    ;

    }@
    Override public voidupdateRow( ) throws
    SQLException
        {_res.updateRow()
    ;

    }@
    Override public voiddeleteRow( ) throws
    SQLException
        {_res.deleteRow()
    ;

    }@
    Override public voidrefreshRow( ) throws
    SQLException
        {_res.refreshRow()
    ;

    }@
    Override public voidcancelRowUpdates( ) throws
    SQLException
        {_res.cancelRowUpdates()
    ;

    }@
    Override public voidmoveToInsertRow( ) throws
    SQLException
        {_res.moveToInsertRow()
    ;

    }@
    Override public voidmoveToCurrentRow( ) throws
    SQLException
        {_res.moveToCurrentRow()
    ;

    }@
    Override public StatementgetStatement( ) throws
    SQLException
        { return_res.getStatement()
    ;

    }@
    Override public ObjectgetObject( intcolumnIndex ,Map<String ,Class<?> >map ) throws
    SQLException
        { return_res.getObject(columnIndex ,map)
    ;

    }@
    Override public RefgetRef( intcolumnIndex ) throws
    SQLException
        { return_res.getRef(columnIndex)
    ;

    }@
    Override public BlobgetBlob( intcolumnIndex ) throws
    SQLException
        { return_res.getBlob(columnIndex)
    ;

    }@
    Override public ClobgetClob( intcolumnIndex ) throws
    SQLException
        { return_res.getClob(columnIndex)
    ;

    }@
    Override public ArraygetArray( intcolumnIndex ) throws
    SQLException
        { return_res.getArray(columnIndex)
    ;

    }@
    Override public ObjectgetObject( StringcolumnLabel ,Map<String ,Class<?> >map ) throws
    SQLException
        { return_res.getObject(columnLabel ,map)
    ;

    }@
    Override public RefgetRef( StringcolumnLabel ) throws
    SQLException
        { return_res.getRef(columnLabel)
    ;

    }@
    Override public BlobgetBlob( StringcolumnLabel ) throws
    SQLException
        { return_res.getBlob(columnLabel)
    ;

    }@
    Override public ClobgetClob( StringcolumnLabel ) throws
    SQLException
        { return_res.getClob(columnLabel)
    ;

    }@
    Override public ArraygetArray( StringcolumnLabel ) throws
    SQLException
        { return_res.getArray(columnLabel)
    ;

    }@
    Override public DategetDate( intcolumnIndex , Calendarcal ) throws
    SQLException
        { return_res.getDate(columnIndex ,cal)
    ;

    }@
    Override public DategetDate( StringcolumnLabel , Calendarcal ) throws
    SQLException
        { return_res.getDate(columnLabel ,cal)
    ;

    }@
    Override public TimegetTime( intcolumnIndex , Calendarcal ) throws
    SQLException
        { return_res.getTime(columnIndex ,cal)
    ;

    }@
    Override public TimegetTime( StringcolumnLabel , Calendarcal ) throws
    SQLException
        { return_res.getTime(columnLabel ,cal)
    ;

    }@
    Override public TimestampgetTimestamp( intcolumnIndex , Calendarcal ) throws
    SQLException
        { return_res.getTimestamp(columnIndex ,cal)
    ;

    }@
    Override public TimestampgetTimestamp( StringcolumnLabel , Calendarcal ) throws
    SQLException
        { return_res.getTimestamp(columnLabel ,cal)
    ;

    }@
    Override public URLgetURL( intcolumnIndex ) throws
    SQLException
        { return_res.getURL(columnIndex)
    ;

    }@
    Override public URLgetURL( StringcolumnLabel ) throws
    SQLException
        { return_res.getURL(columnLabel)
    ;

    }@
    Override public voidupdateRef( intcolumnIndex , Refx ) throws
    SQLException
        {_res.updateRef(columnIndex ,x)
    ;

    }@
    Override public voidupdateRef( StringcolumnLabel , Refx ) throws
    SQLException
        {_res.updateRef(columnLabel ,x)
    ;

    }@
    Override public voidupdateBlob( intcolumnIndex , Blobx ) throws
    SQLException
        {_res.updateBlob(columnIndex ,x)
    ;

    }@
    Override public voidupdateBlob( StringcolumnLabel , Blobx ) throws
    SQLException
        {_res.updateBlob(columnLabel ,x)
    ;

    }@
    Override public voidupdateClob( intcolumnIndex , Clobx ) throws
    SQLException
        {_res.updateClob(columnIndex ,x)
    ;

    }@
    Override public voidupdateClob( StringcolumnLabel , Clobx ) throws
    SQLException
        {_res.updateClob(columnLabel ,x)
    ;

    }@
    Override public voidupdateArray( intcolumnIndex , Arrayx ) throws
    SQLException
        {_res.updateArray(columnIndex ,x)
    ;

    }@
    Override public voidupdateArray( StringcolumnLabel , Arrayx ) throws
    SQLException
        {_res.updateArray(columnLabel ,x)
    ;

    }@
    Override public RowIdgetRowId( intcolumnIndex ) throws
    SQLException
        { return_res.getRowId(columnIndex)
    ;

    }@
    Override public RowIdgetRowId( StringcolumnLabel ) throws
    SQLException
        { return_res.getRowId(columnLabel)
    ;

    }@
    Override public voidupdateRowId( intcolumnIndex , RowIdx ) throws
    SQLException
        {_res.updateRowId(columnIndex ,x)
    ;

    }@
    Override public voidupdateRowId( StringcolumnLabel , RowIdx ) throws
    SQLException
        {_res.updateRowId(columnLabel ,x)
    ;

    }@
    Override public intgetHoldability( ) throws
    SQLException
        { return_res.getHoldability()
    ;

    }@
    Override public booleanisClosed( ) throws
    SQLException
        { return_res.isClosed()
    ;

    }@
    Override public voidupdateNString( intcolumnIndex , StringnString ) throws
    SQLException
        {_res.updateNString(columnIndex ,nString)
    ;

    }@
    Override public voidupdateNString( StringcolumnLabel , StringnString ) throws
    SQLException
        {_res.updateNString(columnLabel ,nString)
    ;

    }@
    Override public voidupdateNClob( intcolumnIndex , NClobnClob ) throws
    SQLException
        {_res.updateNClob(columnIndex ,nClob)
    ;

    }@
    Override public voidupdateNClob( StringcolumnLabel , NClobnClob ) throws
    SQLException
        {_res.updateNClob(columnLabel ,nClob)
    ;

    }@
    Override public NClobgetNClob( intcolumnIndex ) throws
    SQLException
        { return_res.getNClob(columnIndex)
    ;

    }@
    Override public NClobgetNClob( StringcolumnLabel ) throws
    SQLException
        { return_res.getNClob(columnLabel)
    ;

    }@
    Override public SQLXMLgetSQLXML( intcolumnIndex ) throws
    SQLException
        { return_res.getSQLXML(columnIndex)
    ;

    }@
    Override public SQLXMLgetSQLXML( StringcolumnLabel ) throws
    SQLException
        { return_res.getSQLXML(columnLabel)
    ;

    }@
    Override public voidupdateSQLXML( intcolumnIndex , SQLXMLxmlObject ) throws
    SQLException
        {_res.updateSQLXML(columnIndex ,xmlObject)
    ;

    }@
    Override public voidupdateSQLXML( StringcolumnLabel , SQLXMLxmlObject ) throws
    SQLException
        {_res.updateSQLXML(columnLabel ,xmlObject)
    ;

    }@
    Override public StringgetNString( intcolumnIndex ) throws
    SQLException
        { return_res.getNString(columnIndex)
    ;

    }@
    Override public StringgetNString( StringcolumnLabel ) throws
    SQLException
        { return_res.getNString(columnLabel)
    ;

    }@
    Override public ReadergetNCharacterStream( intcolumnIndex ) throws
    SQLException
        { return_res.getNCharacterStream(columnIndex)
    ;

    }@
    Override public ReadergetNCharacterStream( StringcolumnLabel ) throws
    SQLException
        { return_res.getNCharacterStream(columnLabel)
    ;

    }@
    Override public voidupdateNCharacterStream( intcolumnIndex , Readerx , longlength ) throws
    SQLException
        {_res.updateNCharacterStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateNCharacterStream( StringcolumnLabel , Readerreader , longlength ) throws
    SQLException
        {_res.updateNCharacterStream(columnLabel ,reader ,length)
    ;

    }@
    Override public voidupdateAsciiStream( intcolumnIndex , InputStreamx , longlength ) throws
    SQLException
        {_res.updateAsciiStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateBinaryStream( intcolumnIndex , InputStreamx , longlength ) throws
    SQLException
        {_res.updateBinaryStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateCharacterStream( intcolumnIndex , Readerx , longlength ) throws
    SQLException
        {_res.updateCharacterStream(columnIndex ,x ,length)
    ;

    }@
    Override public voidupdateAsciiStream( StringcolumnLabel , InputStreamx , longlength ) throws
    SQLException
        {_res.updateAsciiStream(columnLabel ,x ,length)
    ;

    }@
    Override public voidupdateBinaryStream( StringcolumnLabel , InputStreamx , longlength ) throws
    SQLException
        {_res.updateBinaryStream(columnLabel ,x ,length)
    ;

    }@
    Override public voidupdateCharacterStream( StringcolumnLabel , Readerreader , longlength ) throws
    SQLException
        {_res.updateCharacterStream(columnLabel ,reader ,length)
    ;

    }@
    Override public voidupdateBlob( intcolumnIndex , InputStreaminputStream , longlength ) throws
    SQLException
        {_res.updateBlob(columnIndex ,inputStream ,length)
    ;

    }@
    Override public voidupdateBlob( StringcolumnLabel , InputStreaminputStream , longlength ) throws
    SQLException
        {_res.updateBlob(columnLabel ,inputStream ,length)
    ;

    }@
    Override public voidupdateClob( intcolumnIndex , Readerreader , longlength ) throws
    SQLException
        {_res.updateClob(columnIndex ,reader ,length)
    ;

    }@
    Override public voidupdateClob( StringcolumnLabel , Readerreader , longlength ) throws
    SQLException
        {_res.updateClob(columnLabel ,reader ,length)
    ;

    }@
    Override public voidupdateNClob( intcolumnIndex , Readerreader , longlength ) throws
    SQLException
        {_res.updateNClob(columnIndex ,reader ,length)
    ;

    }@
    Override public voidupdateNClob( StringcolumnLabel , Readerreader , longlength ) throws
    SQLException
        {_res.updateNClob(columnLabel ,reader ,length)
    ;

    }@
    Override public voidupdateNCharacterStream( intcolumnIndex , Readerx ) throws
    SQLException
        {_res.updateNCharacterStream(columnIndex ,x)
    ;

    }@
    Override public voidupdateNCharacterStream( StringcolumnLabel , Readerreader ) throws
    SQLException
        {_res.updateNCharacterStream(columnLabel ,reader)
    ;

    }@
    Override public voidupdateAsciiStream( intcolumnIndex , InputStreamx ) throws
    SQLException
        {_res.updateAsciiStream(columnIndex ,x)
    ;

    }@
    Override public voidupdateBinaryStream( intcolumnIndex , InputStreamx ) throws
    SQLException
        {_res.updateBinaryStream(columnIndex ,x)
    ;

    }@
    Override public voidupdateCharacterStream( intcolumnIndex , Readerx ) throws
    SQLException
        {_res.updateCharacterStream(columnIndex ,x)
    ;

    }@
    Override public voidupdateAsciiStream( StringcolumnLabel , InputStreamx ) throws
    SQLException
        {_res.updateAsciiStream(columnLabel ,x)
    ;

    }@
    Override public voidupdateBinaryStream( StringcolumnLabel , InputStreamx ) throws
    SQLException
        {_res.updateBinaryStream(columnLabel ,x)
    ;

    }@
    Override public voidupdateCharacterStream( StringcolumnLabel , Readerreader ) throws
    SQLException
        {_res.updateCharacterStream(columnLabel ,reader)
    ;

    }@
    Override public voidupdateBlob( intcolumnIndex , InputStreaminputStream ) throws
    SQLException
        {_res.updateBlob(columnIndex ,inputStream)
    ;

    }@
    Override public voidupdateBlob( StringcolumnLabel , InputStreaminputStream ) throws
    SQLException
        {_res.updateBlob(columnLabel ,inputStream)
    ;

    }@
    Override public voidupdateClob( intcolumnIndex , Readerreader ) throws
    SQLException
        {_res.updateClob(columnIndex ,reader)
    ;

    }@
    Override public voidupdateClob( StringcolumnLabel , Readerreader ) throws
    SQLException
        {_res.updateClob(columnLabel ,reader)
    ;

    }@
    Override public voidupdateNClob( intcolumnIndex , Readerreader ) throws
    SQLException
        {_res.updateNClob(columnIndex ,reader)
    ;

    }@
    Override public voidupdateNClob( StringcolumnLabel , Readerreader ) throws
    SQLException
        {_res.updateNClob(columnLabel ,reader)
    ;

    }@
    Override public<T > TgetObject( intcolumnIndex ,Class<T >type ) throws
    SQLException
        { return_res.getObject(columnIndex ,type)
    ;

    }@
    Override public<T > TgetObject( StringcolumnLabel ,Class<T >type ) throws
    SQLException
        { return_res.getObject(columnLabel ,type)
    ;

    }@
    Override public voidupdateObject( intcolumnIndex , Objectx , SQLTypetargetSqlType , intscaleOrLength ) throws
    SQLException
        {_res.updateObject(columnIndex ,x ,targetSqlType ,scaleOrLength)
    ;

    }@
    Override public voidupdateObject( StringcolumnLabel , Objectx , SQLTypetargetSqlType , intscaleOrLength ) throws
    SQLException
        {_res.updateObject(columnLabel ,x ,targetSqlType ,scaleOrLength)
    ;

    }@
    Override public voidupdateObject( intcolumnIndex , Objectx , SQLTypetargetSqlType ) throws
    SQLException
        {_res.updateObject(columnIndex ,x ,targetSqlType)
    ;

    }@
    Override public voidupdateObject( StringcolumnLabel , Objectx , SQLTypetargetSqlType ) throws
    SQLException
        {_res.updateObject(columnLabel ,x ,targetSqlType)
    ;

    }@
    Override public<T > Tunwrap(Class<T >iface ) throws
    SQLException
        { return_res.unwrap(iface)
    ;

    }@
    Override public booleanisWrapperFor(Class<? >iface ) throws
    SQLException
        { return_res.isWrapperFor(iface)
    ;

}