//cmd
//cd C:\Program Files (x86)\OpenOffice 4\program
//soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard


package com.qld.pdf;

import java.io.File;

import java.net.ConnectException;

import java.util.Date;

import com.artofsolving.jodconverter.DocumentConverter;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;

import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * 
 * ��office�ļ�ͨ��openoffice ����ת��Ϊ pdf
 * 
 * @author zhouhuiqiang
 *
 * 
 * 
 */

public class Doc2Pdf {

	private static boolean docToPdf(File inputFile, File outputFile) {

		// connect to an OpenOffice.org instance running on port 8100

		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);

		try {
			connection.connect();

			System.out.println("��ʼת���ĵ�" + inputFile.getName() + "��pdf");
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
		} catch (ConnectException cex) {
			cex.printStackTrace();
			return false;
		} finally {
			// close the connection

			if (connection != null) {

				connection.disconnect();

				connection = null;

			}

		}

		return true;
	}

	class PdfThread extends java.lang.Thread {

		public File inputFile;

		public File outputFile;

		public void run() {

			docToPdf(inputFile, outputFile);

			System.out.println(outputFile.getName() + "�ļ�������");

		}

	}

	public void startWorkWithThread(String inputFileName, String outPutFileName) {

		File inputFile = new File(inputFileName);

		File outputFile = new File(outPutFileName);

		PdfThread t1 = new PdfThread();

		t1.inputFile = inputFile;

		t1.outputFile = outputFile;

		t1.start();

	}

	/**
	 * 
	 * ���ĵ�ת��Ϊpdf��ʽ ��ת��doc,xls
	 * 
	 * @param inputFileName
	 *            ת��Դ�ļ���ȫ·��
	 * 
	 * @param outPutFileName
	 *            ת��Ŀ���ļ���ȫ·��
	 * 
	 * @return �Ƿ�ɹ�
	 * 
	 */

	public static boolean doc2pdf(String inputFilePath, String outPutFilePath) {

		File inputFile = new File(inputFilePath);

		if (inputFile.exists()) {

			File outputFile = new File(outPutFilePath);

			return docToPdf(inputFile, outputFile);

		} else {

			System.out.println("�ļ���" + inputFilePath + " �����ڣ�");

		}

		return false;

	}
	
	public static void main(String[] args) {
		Doc2Pdf.doc2pdf("D:\\gao\\xinglong (1).doc", "D:\\gao\\xinglong (1).pdf");
	}
}
