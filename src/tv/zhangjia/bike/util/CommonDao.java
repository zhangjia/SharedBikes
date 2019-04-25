package tv.zhangjia.bike.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ͨ�õ�DAO�����Զ����������ɾ�Ĳ�
 *
 */
public abstract class CommonDao<T> extends JDBCUtils{

	/**
	 * �����������ɾ�Ĳ���
	 * @return
	 */
	public int executeUpdate(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//��ȡ����
			conn = getConnection();
			//����������
			pstm = conn.prepareStatement(sql);
			if(params != null && params.length > 0) {
				//��Ҫ����ռλ��
				for(int i = 0; i < params.length; i++) {
					//����ռλ��
					pstm.setObject(i+1, params[i]);
				}
			}
			//ִ��
			int i = pstm.executeUpdate();
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//�ͷ���Դ
			close(pstm, conn);
		}
		return 0;
	}
	
	public List<T> query4BeanList(String sql, Object... params){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			//��ȡ����
			conn = getConnection();
			//����������
			pstm = conn.prepareStatement(sql);
			if(params != null && params.length > 0) {
				//��Ҫ����ռλ��
				for(int i = 0; i < params.length; i++) {
					//����ռλ��
					pstm.setObject(i+1, params[i]);
//					pstm.setDate(parameterIndex, x);
				}
			}
			//ִ��SQL���
			rs = pstm.executeQuery();
			//������
			while(rs.next()) {
				T t = getBeanFromResultSet(rs);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public T query4Bean(String sql, Object... params) {
		List<T> list = query4BeanList(sql, params);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public abstract T getBeanFromResultSet(ResultSet rs) throws SQLException;
	
	public int queryCurrentId(String sql) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		try {
			//��ȡ����
			conn = getConnection();
			//����������
			pstm = conn.prepareStatement(sql);
			
			//ִ��SQL���
			rs = pstm.executeQuery();
			//������
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
