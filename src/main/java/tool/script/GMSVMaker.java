package tool.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
//		clearFileButJava(dir);
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
			Process process = Runtime.getRuntime().exec(bat.getPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearFileButJava(File dir) {
		for (File java : dir.listFiles(file -> !file.getName().endsWith(".java"))) {
			java.delete();
		}
	}

}
