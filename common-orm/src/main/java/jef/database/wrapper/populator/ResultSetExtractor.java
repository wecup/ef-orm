/*
 * JEF - Copyright 2009-2010 Jiyi (mr.jiyi@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jef.database.wrapper.populator;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import jef.database.wrapper.result.IResultSet;

/**
 * 直接对JDBC结果集进行操作的转换器
 * 
 * @author jiyi
 * 
 * @param <T>
 */
public interface ResultSetExtractor<T> {
	T transformer(IResultSet rs) throws SQLException;

	int getMaxRows();

	int getFetchSize();

	int getQueryTimeout();

	ResultSetExtractor<T> setMaxRows(int maxRows);

	ResultSetExtractor<T> setFetchSize(int fetchSize);

	ResultSetExtractor<T> setQueryTimeout(int timeout);

	void apply(Statement st) throws SQLException;

	boolean autoClose();

	public static final ResultSetExtractor<Long> GET_FIRST_LONG = new AbstractResultSetTransformer<Long>() {
		public Long transformer(IResultSet rs) throws SQLException {
			if (rs.next()) {
				return rs.getLong(1);
			} else {
				throw new SQLException("Result incorrect.count result must not be empty.");
			}
		}
	}.setMaxRows(1);

	public static final ResultSetExtractor<Integer> GET_FIRST_INT = new AbstractResultSetTransformer<Integer>() {
		public Integer transformer(IResultSet rs) throws SQLException {
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new SQLException("Result incorrect.count result must not be empty.");
			}
		}
	}.setMaxRows(1);

	public static final ResultSetExtractor<Date> GET_FIRST_TIMESTAMP = new AbstractResultSetTransformer<Date>() {
		public Date transformer(IResultSet rs) throws SQLException {
			if (rs.next()) {
				java.sql.Timestamp ts = rs.getTimestamp(1);
				return new java.util.Date(ts.getTime());
			} else {
				throw new SQLException("Result incorrect.count result must not be empty.");
			}
		}
	};
}
