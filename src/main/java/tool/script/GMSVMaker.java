package tool.script;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

public class GMSVMaker {

	public static void main(String[] args) {
		List<Grammar> grammars = new GMSVGrammarReader().readGrammarFromFile(new File("D:/file/workspace/CrossGateResource/base/数值.xls"));
		File dir = new File("D:/file/workspace/CrossGateData/src/cg/data/script/antlr");
		File g4 = new File(dir, "GMSV.g4");
		new GMSVGrammarMaker().make(grammars, g4, new File("D:/file/workspace/GMSVScriptTool/template/GMSV.g4"));
		File bat = new File(dir, "antlr4.bat");
		makeBat(bat, g4);
		execBat(bat);
		addPackage(dir, "cg.data.script.antlr");
		clearFileButJava(dir);
	}
	
	public static void makeBat(File bat, File g4) {
		if (!bat.exists()) {
			try {
				bat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		StringBuilder builder = new StringBuilder("java org.antlr.v4.Tool ");
		builder.append(g4.getPath());
		
		try {
			FileWriter writer = new FileWriter(bat);
			writer.append(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void execBat(File bat) {
		try {
			Runtime.getRuntime().exec(bat.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addPackage(File dir, String pkg) {
		for (File java : dir.listFiles(file -> file.getName().endsWith(".java"))) {
			StringBuilder builder = new StringBuilder();
			try (LineNumberReader reader = new LineNumberReader(new FileReader(java))) {
				String line;
				boolean addPackage = false;
				while ((line = reader.readLine()) != null) {
					if (addPackage) {
						builder.append(line).append("\r\n");
					} else {
						if (line.trim().startsWith("//")) {
							builder.append(line).append("\r\n");
						} else if (line.trim().startsWith("package")) {
							builder.append(line).append("\r\n");
							addPackage = true;
						} else {
							builder.append("package ").append(pkg).append(";").append("\r\n");
							builder.append(line).append("\r\n");
							addPackage = true;
						}
					}
				}
				
				FileWriter writer = new FileWriter(java);
				writer.append(builder.toString());
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void clearFileButJava(File dir) {
		for (File java : dir.listFiles(file -> !file.getName().endsWith(".java"))) {
			java.delete();
		}
	}

}
