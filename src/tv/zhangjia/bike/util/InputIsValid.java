package tv.zhangjia.bike.util;

import java.math.BigInteger;
import java.util.Scanner;

public class InputIsValid {
	public static void main(String[] args) {
		while (true) {
			Scanner input = new Scanner(System.in);
			System.out.print("�����룺");
			String str = input.next();

			if (new InputIsValid().isInt(str)) {
				// if (str.matches("^[0-9]*$") && str.matches("^\\d{m,n}$")) {
				System.out.println("�Ϸ�");
			} else {
				System.out.println("���Ϸ�");
			}

		}
	}

	/**
	 * �����Ƿ���Int���͵������� �� 0
	 * @param str Ҫ�жϵ��ַ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public boolean isNumber(String str) {
		if (str.matches("^[0-9]*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			// �������ĳ���Int����
			if (l.compareTo(n) > 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}

	}

	/**
	 * �����Ƿ���Int���͵���������������0
	 * @param str Ҫ�жϵ��ַ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public boolean isPositiveInteger(String str) {
		if (str.matches("^[1-9]*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			// �������ĳ���Int����
			if (l.compareTo(n) > 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �����Ƿ���Int���͵�����������������������0��
	 * @param str Ҫ�жϵ��ַ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public boolean isInt(String str) {
		if (str.matches("^-?[0-9]\\d*$")) {
			BigInteger l = new BigInteger(str);
			BigInteger n = new BigInteger("32767");
			BigInteger m = new BigInteger("-32768");
			// �������ĳ���Int����
			if (l.compareTo(n) > 0  || l.compareTo(m) < 0 ) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ������Ƿ���������������double
	 * 
	 * @param str Ҫ�жϵ��ַ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public boolean isDouble(String str) {
		if (str.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*$")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * �ж�������ֻ����Ƿ�Ϸ�
	 * @param str Ҫ�жϵ��ֻ���
	 * @return �Ϸ�����true�����Ϸ�����false
	 */
	public boolean isTrueTel(String str) {
		if (str.matches( "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
			return true;
		} else {
			return false;
		}
	}

}
