package br.edu.cefsa.ec6.laion;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	private static void saveOnDisk(String path, String data) throws IOException{
		byte[] bytes = data.getBytes();
		FileOutputStream out = new FileOutputStream(path);

		out.write(bytes);
		out.close();
	}

	public static void main(String[] args) {
		try {
			DolSystem dolSystem = new DolSystem(System.getProperty("user.dir") + "/inputs/input5.txt");

			SvgGenerator svgGenerator = new SvgGenerator(dolSystem);
			String svg = svgGenerator.generateSvg();
	
			saveOnDisk(System.getProperty("user.dir") + "/output.svg", svg);
			System.out.println("Done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
