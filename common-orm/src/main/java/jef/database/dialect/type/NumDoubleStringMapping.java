package jef.database.dialect.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import jef.database.dialect.DatabaseDialect;
import jef.database.wrapper.result.IResultSet;
import jef.tools.StringUtils;

public class NumDoubleStringMapping extends AColumnMapping<String>{

	public Object set(PreparedStatement st, Object value, int index, DatabaseDialect session) throws SQLException {
		if(StringUtils.isEmpty(value)){
			st.setNull(index, java.sql.Types.DOUBLE);
		}else{
			st.setDouble(index, Double.parseDouble((String)value));
		}
		return value;
	}

	public int getSqlType() {
		return java.sql.Types.DOUBLE;
	}

	@Override
	protected String getSqlExpression(Object value, DatabaseDialect profile) {
		return value.toString();
	}
	
	public Object getProperObject(IResultSet rs, int n) throws SQLException {
		Object obj=rs.getObject(n);
		if(obj==null)return null;
		return obj.toString();
	}
}
