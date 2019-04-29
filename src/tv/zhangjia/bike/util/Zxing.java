package tv.zhangjia.bike.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import tv.zhangjia.bike.dao.BikeDao;
import tv.zhangjia.bike.dao.impl.BikeDaoImpl;
import tv.zhangjia.bike.entity.Bike;

public class Zxing {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	/**
	 * ���ö�ά�����
	 * @param contents ��ά������
	 * @return 
	 * @throws WriterException
	 */
	private static BitMatrix encode(String contents) throws WriterException {
		final Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		return new QRCodeWriter().encode(contents, BarcodeFormat.QR_CODE, 320, 320, hints);
	}

	
	/**
	 * ���ɶ�ά��
	 * @param bike Ҫ���ɵ�bike����
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void generateQR(Bike bike) throws WriterException, IOException {
		String bi2 = "ID:" + bike.getId() + "  ���ͣ�" + bike.getType() +"  ״̬" + bike.getStatus()+ "  ����" + bike.getAmount();
		BitMatrix bt = encode(bi2);
		File file = new File("E:" + File.separator + "bike" + File.separator + "img"   + File.separator + bike.getId() + ".png");
		
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		String address = file.toString();
		bike.setQr(address);
		writeToFile(bt, "png", file);
	}

	
	/**
	 * ����ͼƬ��С����ɫ
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * �Ѷ�ά�뱣��������
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("����д�� " + format + file);
		}
	}

}