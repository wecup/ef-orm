package jef.database.innerpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import jef.database.DbClient;
import jef.database.DebugUtil;
import jef.database.Transaction;
import jef.database.routing.jdbc.JPreparedCall;
import jef.database.routing.jdbc.JPreparedStatement;
import jef.database.routing.jdbc.JStatement;

/**
 * 带SQL解析和路由功能的Connection
 * 
 * @author jiyi
 * 
 */
public class JConnection implements Connection {
	@SuppressWarnings("unused")
	private DbClient db;
	private Transaction currentSession;

	public JConnection(DbClient db) {
		this.db = db;
		this.currentSession = db.startTransaction().setAutoCommit(true);
	}

	@Override
	public Statement createStatement() throws SQLException {
		return createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		currentSession.setAutoCommit(autoCommit);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return currentSession.isAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		currentSession.commit(false);
	}

	@Override
	public void rollback() throws SQLException {
		currentSession.rollback(false);
	}

	@Override
	public void close() throws SQLException {
		currentSession.close();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return !currentSession.isOpen();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		currentSession.setReadonly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return currentSession.isReadonly();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		currentSession.setIsolationLevel(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return currentSession.getIsolationLevel();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return createStatement(resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return prepareStatement(sql, resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return prepareCall(sql, resultSetType, resultSetConcurrency, ResultSet.CLOSE_CURSORS_AT_COMMIT);
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return currentSession.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return currentSession.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		currentSession.rollbackToSavepoint(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		currentSession.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new JStatement(this, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new JPreparedStatement(sql, this, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new JPreparedCall(sql, this, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return new JPreparedStatement(sql, this, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return new JPreparedStatement(sql, this, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return new JPreparedStatement(sql, this, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		return conn().createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		return conn().createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return conn().createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return conn().createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return conn().isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		try {
			conn().setClientInfo(name, value);
		} catch (SQLException e) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		try {
			conn().setClientInfo(properties);
		} catch (SQLException e) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return conn().getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return conn().getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return conn().createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return conn().createStruct(typeName, attributes);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return conn().nativeSQL(sql);
	}

	private Connection conn() throws SQLException {
		return (Connection) DebugUtil.getIConnection(currentSession);
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn().getMetaData();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		conn().setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException {
		return conn().getCatalog();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return conn().getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		conn().clearWarnings();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return conn().getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		conn().setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		conn().setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		return conn().getHoldability();
	}
	
	public Transaction get(){
		return this.currentSession;
	}
}
