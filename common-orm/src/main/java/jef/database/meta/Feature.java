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
package jef.database.meta;

/**
 * 这个类列举各种和数据库相关的特性
 * @author jiyi
 *
 */
public enum Feature {
	/**
	 * 必须使用sequence才能实现自增，即表本身不支持自增列 (Oracle)
	 */
	AUTOINCREMENT_NEED_SEQUENCE,
	/**
	 * 允许使用Rownum/支持rowUID来限定结果数量 特别SQL语法特性(Oracle)
	 */
	SELECT_ROW_NUM,
	/**
	 * 用户名作为Schema (Oracle)
	 */
	USER_AS_SCHEMA, // 
	/**
	 * 数据库名作为Schema或catlog (MySQL) 
	 */
	DBNAME_AS_SCHEMA, // 
	/**
	 * 允许使用NULL在列定义中
	 */
	COLUMN_DEF_ALLOW_NULL,
	/**
	 * ResultSet只能向前滚动，不支持向后滚动结果集(SQLlite）
	 */
	TYPE_FORWARD_ONLY,
	/**
	 * 不支持元数据中的INDEX查询(SQLlite）
	 */
	NOT_SUPPORT_INDEX_META, 
	/**
	 * 自增列必须为主键，某些数据库只支持将主键定义为自增(SQLlite）
	 */
	AUTOINCREMENT_MUSTBE_PK,
	/**
	 * 支持级联删除所有外键(Oracle)
	 */
	DROP_CASCADE, 
	/**
	 * 是否支持用||表示字符串相加。
	 * 如果没有这个特性的话，JEF就只能将字符串修改为concat(a,b,c...)的函数了 (Oracle)
	 */
	SUPPORT_CONCAT,	
	
	/**
	 * SQLServer特性,不支持||表示字符串相加，而是用+表示
	 */
	CONCAT_IS_ADD,
	
	/**
	 * Oracle特性，支持 start with ... connect by ....类型的语句
	 */
	SUPPORT_CONNECT_BY,
	
	/**
	 * DERBY特性，在CASE WHEN THEN ELSE END这个系列的语法中，Derby的语法和别的数据库是不一样的。居然不允许在case后面写switch条件，而必须在每个when后面写条件表达式
	 */
	CASE_WITHOUT_SWITCH,
	/**
	 * Abbout derby Bug: https://issues.apache.org/jira/browse/DERBY-3609 Since
	 * Derby return generated keys feature implement partially,
	 * 
	 * 不光Derby的驱动是这样，Sqlite的驱动也是一样的。
	 */
	BATCH_GENERATED_KEY_ONLY_LAST,
	
	/**
	 * SQLSErver特性，Batch模式下使用getGeneratedKeys无法正常返回主键值，所以不得不使用@@IDENTITY返回结果
	 */
	BATCH_GENERATED_KEY_BY_FUNCTION,
	/**
	 * 要想从元数据中获取备注需要特别的参数才行(Oracle)
	 */
	REMARK_META_FETCH, 
	/**
	 * Derby和Postgres特性，alter table语句中修改列支持必须用更复杂的语法
	 * column-alteration syntax
	 *  key words must 
	 * column-Name SET DATA TYPE VARCHAR(integer) |
	 * 	column-Name SET DATA TYPE VARCHAR FOR BIT DATA(integer) |
	 * column-name SET INCREMENT BY integer-constant |
	 * column-name RESTART WITH integer-constant |
	 * column-name [ NOT ] NULL |
	 * column-name [ WITH | SET ] DEFAULT default-value |
	 * column-name DROP DEFAULT 
	 */
	COLUMN_ALTERATION_SYNTAX,//Derby feature，很复杂的 modify column语法
	/**
	 * 在执行ALTER TABLE语句的时候一次只能操作一个列 (Derby)
	 */
	ONE_COLUMN_IN_SINGLE_DDL,
	/**
	 * 必须将改表语句中的多列用括号起来，不然报错(Oracle)
	 */
	BRUKETS_FOR_ALTER_TABLE,
	/**
	 * 在一个alter table中可以操作多个列，但是每列的前面要加上命令(MYSQL, POSTGRES)
	 */
	ALTER_FOR_EACH_COLUMN,
	/**
	 * Apache Derby上，如果调用ResultSet.newRecord()创建记录，下次正常插入记录时该表中自增主键会冲突。
	 */
	NOT_FETCH_NEXT_AUTOINCREAMENTD,
	/**
	 * 游标操作特性，在游标上直接插入记录时是偶限制
	 */
	CURSOR_ENDS_ON_INSERT_ROW,
	/**
	 * 支持Sequence
	 */
	SUPPORT_SEQUENCE,
	/**
	 * 支持Limit限定结果
	 */
	SUPPORT_LIMIT,
	/**
	 * 不支持Truncate语句
	 */
	NOT_SUPPORT_TRUNCATE,
	/**
	 * 不支持外键，目前SQLite按不支持外键处理，其驱动不够健壮
	 */
	NOT_SUPPORT_FOREIGN_KEY,
	/**
	 * 不支持在Like语句中使用Escape语句作为转义
	 */
	NOT_SUPPORT_LIKE_ESCAPE, 
	/**
	 * 不支持插入时使用DEFAULT关键字 (SQLite)
	 */
	NOT_SUPPORT_KEYWORD_DEFAULT, 
	/**
	 * 不支持获取用户函数
	 */
	NOT_SUPPORT_USER_FUNCTION,
	/**
	 * SQLite操作Blob时，不支持setBinaryStream，必须用setBytes
	 */
	NOT_SUPPORT_SET_BINARY,
	/**
	 * SQLite特性，不支持修改表删除字段
	 */
	NOT_SUPPORT_ALTER_DROP_COLUMN,
	/**
	 * Union语句上每个子句两边加上括号
	 */
	UNION_WITH_BUCK,
	/**
	 * 绑定变量语法特性
	 */
	NO_BIND_FOR_SELECT, // 查询语句不支持绑定变量
	NO_BIND_FOR_UPDATE, // 更新语句不支持绑定变量
	NO_BIND_FOR_INSERT, // 插入语句不支持绑定变量
	NO_BIND_FOR_DELETE // 删除语句不支持绑定变量
}
