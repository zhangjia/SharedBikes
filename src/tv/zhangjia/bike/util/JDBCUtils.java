package tv.zhangjia.bike.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC�Ĺ����࣬���𣺼����������������ӣ��ͷ���Դ
 *
 */
public class JDBCUtils {

	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private static final String USER = "bike";
	private static final String PASSWORD = "z";

	static {
		// ��Ϊ����ֻ��Ҫ����һ�Σ������ھ�̬�����н��м���
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������ݿ������
	 * 
	 * @return ����
	 */
	public /*static*/ Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param rs
	 *            �����
	 * @param stm
	 *            ������
	 * @param conn
	 *            ����
	 */
	public /*static*/ void close(ResultSet rs, Statement stm, Connection conn) {
		try {
			// �رս����
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// �ر�������
				if (stm != null && !stm.isClosed()) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					// �ر�����
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param stm
	 *            ������
	 * @param conn
	 *            ����
	 */
	public /*static*/ void close(Statement stm, Connection conn) {
		close(null, stm, conn);
	}

}
