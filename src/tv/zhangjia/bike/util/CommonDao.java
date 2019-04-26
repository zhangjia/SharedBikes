package tv.zhangjia.bike.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ͨ�õ�DAO�����Զ����������ɾ�Ĳ�
 *
 */
public class CommonDao extends JDBCUtils {

	/**
	 * �����������ɾ�Ĳ���
	 * 
	 * @return
	 */
	public int executeUpdate(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			// ��ȡ����
			conn = getConnection();
			// ����������
			pstm = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				// ��Ҫ����ռλ��
				for (int i = 0; i < params.length; i++) {
					// ����ռλ��
					pstm.setObject(i + 1, params[i]);
				}
			}
			// ִ��
			int i = pstm.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// �ͷ���Դ
			close(pstm, conn);
		}
		return 0;
	}

	public <T> List<T> query4BeanList(String sql, BeanResultSetHandler<T> beanResultSetHandler, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			// ��ȡ����
			conn = getConnection();
			// ����������
			pstm = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				// ��Ҫ����ռλ��
				for (int i = 0; i < params.length; i++) {
					// ����ռλ��
					pstm.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL���
			rs = pstm.executeQuery();
			// ������
			while (rs.next()) {
				T t = beanResultSetHandler.getBeanFromResultSet(rs);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, conn);
		}
		return list;
	}

	public <T> T query4Bean(String sql, BeanResultSetHandler<T> beanResultSetHandler, Object... params) {
		List<T> list = query4BeanList(sql, beanResultSetHandler, params);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public <T> List<T> query4BeanList(String sql, Class<T> clazz, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			// ��ȡ����
			conn = getConnection();
			// ����������
			pstm = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				// ��Ҫ����ռλ��
				for (int i = 0; i < params.length; i++) {
					// ����ռλ��
					pstm.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL���
			rs = pstm.executeQuery();
			// ������
			// ��ȡ���е�����
			Field[] fields = clazz.getDeclaredFields();
			// ��ȡԪ����
			ResultSetMetaData metaData = rs.getMetaData();
			// ��ȡ���ж�����
			int columnCount = metaData.getColumnCount();
			// ׼��һ���ַ������飬���������ֶ���
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnNames[i] = metaData.getColumnName(i + 1);
			}
			while (rs.next()) {
				T t = clazz.newInstance();
				for (Field field : fields) {
					// ��ȡ������
					String name = field.getName();
					// ����������ƴ��set������
					String smn = "";
//					System.out.println(field.getType().getName());
					if (field.getType() == boolean.class && name.contains("is")) {
						smn = "set" + name.substring(name.indexOf("is") + 2);
					} else {

						smn = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					}
					// ����set�������ҵ�set����
					Class<?> type = field.getType();
					Method sm = clazz.getMethod(smn, type);
					for (String columnName : columnNames) {
						if (columnName.replaceAll("_", "").equalsIgnoreCase(name)) {
							// ��ResultSet�л�ȡֵ
							Object value = rs.getObject(columnName);
							if (value == null) {
								break;
							}
							// ���ÿ��value�������Լ����Ե�����
							if (type == int.class || type == Integer.class) {
								value = rs.getInt(columnName);
								sm.invoke(t, value);
								break;
							}
							if (type == double.class || type == Double.class) {
								value = rs.getDouble(columnName);
								sm.invoke(t, value);
								break;
							}
							
							if (type == long.class || type == Long.class) {
								value = rs.getLong(columnName);
								sm.invoke(t, value);
								break;
							}
							if (field.getType() == boolean.class || field.getType() == Boolean.class) {
								value = rs.getBoolean(columnName);
								sm.invoke(t, value);
								break;
							}
							// ִ��set����
//							System.out.println(value.getClass());
							sm.invoke(t, value);
						}
					}
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, conn);
		}
		return list;
	}

	public <T> T query4Bean(String sql, Class<T> clazz, Object... params) {
		List<T> list = query4BeanList(sql, clazz, params);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> query4MapList(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			conn = getConnection();
			pstm = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				// ��Ҫ����ռλ��
				for (int i = 0; i < params.length; i++) {
					// ����ռλ��
					pstm.setObject(i + 1, params[i]);
				}
			}
			rs = pstm.executeQuery();
			// ��������
			// ��ȡԪ����
			ResultSetMetaData metaData = rs.getMetaData();
			// ͨ��metaData��ȡ���ж�����
			int columnCount = metaData.getColumnCount();
			// ׼��һ���ַ������飬�����洢ÿ���е�����
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				// ͨ��metaData��ȡ�ֶ���
				columnNames[i] = metaData.getColumnName(i + 1);
			}
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = columnNames[i];
					map.put(columnName, rs.getObject(columnName));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, conn);
		}
		return list;
	}

	public Map<String, Object> query4Map(String sql, Object... params) {
		List<Map<String, Object>> list = query4MapList(sql, params);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	protected interface BeanResultSetHandler<T> {
		T getBeanFromResultSet(ResultSet rs) throws SQLException;
	}
	
	
	//��ȡ����int���͵�����
	public int query4IntData(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			// ��ȡ����
			conn = getConnection();
			// ����������
			pstm = conn.prepareStatement(sql);
			if (params != null && params.length > 0) {
				// ��Ҫ����ռλ��
				for (int i = 0; i < params.length; i++) {
					// ����ռλ��
					pstm.setObject(i + 1, params[i]);
				}
			}
			// ִ��SQL���
			rs = pstm.executeQuery();
			// ������
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, pstm, conn);
		}
		return 0;
	}
	
	//��ȡ����double���͵�����
		public double query4DoubleData(String sql, Object... params) {
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				// ��ȡ����
				conn = getConnection();
				// ����������
				pstm = conn.prepareStatement(sql);
				if (params != null && params.length > 0) {
					// ��Ҫ����ռλ��
					for (int i = 0; i < params.length; i++) {
						// ����ռλ��
						pstm.setObject(i + 1, params[i]);
					}
				}
				// ִ��SQL���
				rs = pstm.executeQuery();
				// ������
				if (rs.next()) {
					return rs.getDouble(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs, pstm, conn);
			}
			return 0;
		}
		
		
		//��ȡ�����ַ�����������
		public String query4StringData(String sql, Object... params) {
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				// ��ȡ����
				conn = getConnection();
				// ����������
				pstm = conn.prepareStatement(sql);
				if (params != null && params.length > 0) {
					// ��Ҫ����ռλ��
					for (int i = 0; i < params.length; i++) {
						// ����ռλ��
						pstm.setObject(i + 1, params[i]);
					}
				}
				// ִ��SQL���
				rs = pstm.executeQuery();
				// ������
				if (rs.next()) {
					return rs.getString(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(rs, pstm, conn);
			}
			return null;
		}
}
