package tv.zhangjia.bike.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * �ݹ��ȡĿ¼��
 * @author Kevin
 */
public class Codes {
	static int fileCount = 0; // �ļ���
	static int codeCount = 0; // ������
	static int classCount = 0; // ��ĸ���
	static int interfaceCount = 0;
	static int method = 0;
	static int co = 0;
	static Map<String, Integer> codes = new TreeMap<>();
	static Map<String, Integer> methods = new TreeMap<>();

	public void exeMethod(String f, File file) {

		// Scanner input = new Scanner(System.in);
		// File file = null;
		// while (true) {
		// // f = input.nextLine();
		// file = new File(f);
		// if (!file.exists()) {
		// System.out.print("�����ַ���Ϸ������������룺");
		// } else {
		// break;
		// }
		//
		// }

		statistics(file);
		int i = 1;
//		System.out.println(codes);
		//����ֵ����
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(codes.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue() - o2.getValue());
			}
		});
		/*
		 * for (Map.Entry<String, Integer> entry : codes.entrySet()) {
		 * System.out.print(i++ + ":\t"); System.out.printf("%-20s", entry.getKey());
		 * System.out.print("\t��  " + entry.getValue() + " �д���"); method +=
		 * methods.get(entry.getKey()); System.out.println("\t��  " +
		 * methods.get(entry.getKey()) + "������"); codeCount += entry.getValue(); }
		 * 
		 */
		for (Entry<String, Integer> code : list) {
			System.out.print(i++ + ":\t");
			System.out.printf("%-20s", code.getKey());
			System.out.print("\t��  " + code.getValue() + " �д���");
			method += methods.get(code.getKey());
			System.out.println("\t��  " + methods.get(code.getKey()) + "������");
			codeCount += code.getValue();
		}
		System.out.println("\n\n--------------------����Ŀͳ�ƽ������--------------------");
		System.out.print(
				"һ���У�\n" + fileCount + "���ļ�\n" + classCount + "����\n" + interfaceCount + "���ӿ�\n" + method + "������\n");
		System.out.println(codeCount + "�д���\n����ಢ������д�ĺã�����ʮ�о��ܽ���Ĺ��ܣ���д��100�У��������Ͱɣ�");

		// input.close();
	}

	public void statistics(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				statistics(f);
			}

		} else {
			Reader reader = null;
			Reader reader2 = null;
			co = 0;
			try {
				reader = new FileReader(file);
				reader2 = new FileReader(file);
				;
				char[] cbuf = new char[32];
				int len = 0;
				StringBuilder sb = new StringBuilder();
				while ((len = reader.read(cbuf)) != -1) {
					String str = new String(cbuf, 0, len);
					sb.append(str);
				}
				String str = sb.toString();
				if (str.contains("public class")) {
					classCount++;
				}

				if (!file.getName().contains("Codes") && (str.contains("public interface")
						|| str.contains("private interface") || str.contains("protected interface"))) {
					interfaceCount++;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			LineNumberReader l = new LineNumberReader(reader2);
			try {
				while (l.readLine() != null) {
					co++;
				}
				codes.put(file.getName(), co);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileCount++;

			// ȥ��Java
			String s1 = file.getPath().replaceAll(".java", "");
			// ȥ��srcǰ�����
			String s2 = s1.substring(s1.indexOf("src") + 4);
			// ȥ��\
			String s3 = s2.replaceAll("\\\\", ".");

			Method[] ms = null;
			try {
				ms = Class.forName(s3).getDeclaredMethods();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ׼��һ���ַ������飬���������ֶ���
			methods.put(file.getName(), ms.length);

		}

	}

}