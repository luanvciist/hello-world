package com.viettel.mykid.ulti;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class FileUploadUtil {
	
	private FileUploadUtil() {
	}

	/**
	 * Create list extension is image
	 * @param file
	 * @return
	 */
	public static List<String> getListExtensionImageFile() {
		List<String> imageList = new ArrayList<String>();
		imageList.add("image/jpeg");
		imageList.add("image/gif");
		imageList.add("image/jpg");
		imageList.add("image/png");
		return imageList;
	}
	
	public static void resize(File inputFile, String folder, String outputImagePath, int scaledWidth, int scaledHeight)
			throws IOException {
		 
		File directory = new File(folder);
		if (!directory.exists()) {
			directory.mkdir();
		}
		
		// reads input image
		BufferedImage inputImage = ImageIO.read(inputFile);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath.lastIndexOf('.') + 1);

		File outputFile = new File(outputImagePath);
		
		// writes to output file
		ImageIO.write(outputImage, formatName, outputFile);
	}
}
