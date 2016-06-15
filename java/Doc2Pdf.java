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
 * 将office文件通过openoffice 工具转换为 pdf
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

			System.out.println("开始转换文档" + inputFile.getName() + "成pdf");
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

			System.out.println(outputFile.getName() + "文件已生成");

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
	 * 将文档转换为pdf格式 可转换doc,xls
	 * 
	 * @param inputFileName
	 *            转换源文件，全路径
	 * 
	 * @param outPutFileName
	 *            转换目的文件，全路径
	 * 
	 * @return 是否成功
	 * 
	 */

	public static boolean doc2pdf(String inputFilePath, String outPutFilePath) {

		File inputFile = new File(inputFilePath);

		if (inputFile.exists()) {

			File outputFile = new File(outPutFilePath);

			return docToPdf(inputFile, outputFile);

		} else {

			System.out.println("文件：" + inputFilePath + " 不存在！");

		}

		return false;

	}
	
	public static void main(String[] args) {
		Doc2Pdf.doc2pdf("D:\\gao\\xinglong (1).doc", "D:\\gao\\xinglong (1).pdf");
	}
}
