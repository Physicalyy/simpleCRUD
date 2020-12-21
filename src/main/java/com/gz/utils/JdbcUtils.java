package com.gz.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具类
 * 
 * @author gongyy
 **/
public class JdbcUtils {

	/**
	 * 将 ResultSet 转换为 List
	 * */
	public static List<Map<String, Object>> convertResultSetToMapList(ResultSet rs, Boolean toLowerCase) {
		List<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();

		ResultSetMetaData rsmd;

		if (rs == null)
			return resultListMap;
		try {
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] colnums = new  String[columnCount];
			int[] colnumstype = new  int[columnCount];
 			for (int j = 1; j <= columnCount; j++) {
 				
 				colnums[j-1] = getColnumLabel(rsmd.getColumnLabel(j));
				colnumstype[j-1] = rsmd.getColumnType(j);
			}

			while (rs.next()) {
				Map<String, Object> rowMap = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					Object colValue = null;
					String colName = colnums[i-1];
					if (colnumstype[i-1] == Types.CLOB) {
						continue;
					} else if (colnumstype[i-1] == Types.BLOB) {
						continue;
					}else if(colnumstype[i-1] == Types.TIMESTAMP){
						colValue = rs.getTimestamp(i);
					} 
					else {
						colValue = rs.getObject(i);
					}
					rowMap.put(colName, colValue);
				}
				resultListMap.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return resultListMap;
	}
	
	
	public static List<Map<String, Object>> convertResultSetToMapListFj(ResultSet rs, Boolean toLowerCase) {
		List<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();

		ResultSetMetaData rsmd;

		if (rs == null)
			return resultListMap;
		try {
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			String[] colnums = new  String[columnCount];
			int[] colnumstype = new  int[columnCount];
 			for (int j = 1; j <= columnCount; j++) {
 				
 				colnums[j-1] = getColnumLabel(rsmd.getColumnLabel(j));
				colnumstype[j-1] = rsmd.getColumnType(j);
			}

			while (rs.next()) {
				Map<String, Object> rowMap = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					Object colValue = null;
					String colName = colnums[i-1];
					if (colnumstype[i-1] == Types.CLOB) {
						Clob clob = rs.getClob(i);
						if (clob != null && (int) clob.length() > 0) {
							colValue = clob.getSubString(1, (int) clob.length());
						}
					} else if (colnumstype[i-1] == Types.BLOB) {
						colValue = rs.getBytes(i);
					}else if(colnumstype[i-1] == Types.TIMESTAMP){
						colValue = rs.getTimestamp(i);
					} 
					else {
						colValue = rs.getObject(i);
					}
					rowMap.put(colName, colValue);
				}
				resultListMap.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					rs = null;
				}
			}
		}
		return resultListMap;
	}
	
	public static String getColnumLabel(String colnumLabel){
		
		colnumLabel = colnumLabel.toLowerCase();
		if(colnumLabel.indexOf("_") >-1){
			String labels[] = colnumLabel.split("_");
			int len = labels.length;
			if(len > 1){
				String lab = labels[0];
				for(int i = 1 ; i < len ; i++){
					lab += labels[i].substring(0, 1).toUpperCase()+labels[i].substring(1);
				}
				return lab;
			}else{
				return  labels[0];
			}

		}else{
			return colnumLabel;
		}
	}

	/**
	 * 存储过程输入参数绑定
	 * */
	public static void bindStatementInput(CallableStatement cs, int indexed, Object value) throws SQLException {
		if (value instanceof String) {
			cs.setString(indexed, (String) value);
		} else if (value instanceof Integer) {
			cs.setInt(indexed, ((Integer) value).intValue());
		} else if (value instanceof Double) {
			cs.setDouble(indexed, ((Double) value).doubleValue());
		} else if (value instanceof Float) {
			cs.setFloat(indexed, ((Float) value).floatValue());
		} else if (value instanceof Long) {
			cs.setLong(indexed, ((Long) value).longValue());
		} else if (value instanceof Boolean) {
			cs.setBoolean(indexed, ((Boolean) value).booleanValue());
		} else if (value instanceof java.util.Date) {
			cs.setDate(indexed, new java.sql.Date(((java.util.Date) value).getTime()));
		} else if (value instanceof java.sql.Date) {
			cs.setDate(indexed, (java.sql.Date) value);
		} else if (value instanceof BigDecimal) {
			cs.setBigDecimal(indexed, (BigDecimal) value);
		} else if (value instanceof Timestamp) {
			cs.setTimestamp(indexed, (Timestamp) value);
		} else if (value instanceof Time) {
			cs.setTime(indexed, (Time) value);
		} else if (value instanceof Blob) {
			cs.setBlob(indexed, (Blob) value);
		} else if (value instanceof Clob) {
			cs.setClob(indexed, (Clob) value);
		} else {
			cs.setObject(indexed, value);
		}
	}

	/**
	 * 存储过程传出参数绑定
	 * */
	public static void bindStatementOutput(CallableStatement cs, int indexed, String dataType) throws SQLException {
		dataType = StringUtils.lowerCase(dataType);

		if (StringUtils.equals(dataType, "string") || StringUtils.equals(dataType, "char")) {
			cs.registerOutParameter(indexed, Types.CHAR);
		} else if (StringUtils.equals(dataType, "int") || StringUtils.equals(dataType, "integer")) {
			cs.registerOutParameter(indexed, Types.INTEGER);
		} else if (StringUtils.equals(dataType, "date")) {
			cs.registerOutParameter(indexed, Types.DATE);
		} else if (StringUtils.equals(dataType, "decimal")) {
			cs.registerOutParameter(indexed, Types.DECIMAL);
		} else if (StringUtils.equals(dataType, "float")) {
			cs.registerOutParameter(indexed, Types.FLOAT);
		} else if (StringUtils.equals(dataType, "double")) {
			cs.registerOutParameter(indexed, Types.DOUBLE);
		} else if (StringUtils.equals(dataType, "cursor")) {// OracleTypes.CURSOR
			cs.registerOutParameter(indexed, -10);
		} else {
			cs.registerOutParameter(indexed, Types.CHAR);
		}
	}

	/**
	 * 类型转换
	 * */
	public static Object convertType(CallableStatement cs, int indexed, String dataType) {
		dataType = StringUtils.lowerCase(dataType);

		Object dataValue = null;
		try {
			dataValue = cs.getObject(indexed);
		} catch (SQLException e) {
			// e.printStackTrace();
		}

		if (dataValue == null) {
			return null;
		}

		if (StringUtils.equals(dataType, "string") || StringUtils.equals(dataType, "char"))
			return dataValue;
		else if (StringUtils.equals(dataType, "int") || StringUtils.equals(dataType, "integer"))
			return (Integer) dataValue;
		else if (StringUtils.equals(dataType, "date"))
			return dataValue;
		else if (StringUtils.equals(dataType, "decimal"))
			return new BigDecimal(StringUtils.defaultIfEmpty((String) dataValue, "0"));
		else if (StringUtils.equals(dataType, "float"))
			return new Float(StringUtils.defaultIfEmpty((String) dataValue, "0"));
		else if (StringUtils.equals(dataType, "double"))
			return new Double(StringUtils.defaultIfEmpty((String) dataValue, "0"));
//		else if (StringUtils.equals(dataType, "cursor"))
//			return com.zebone.platform.modules.utils.JdbcUtils.convertResultSetToMapList((ResultSet) dataValue, false);
		else
			return dataValue;
	}

	public static void close(Connection conn, ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
		close(conn);
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				rs = null;
			}
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				conn = null;
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				stmt = null;
			}
		}
	}
}
