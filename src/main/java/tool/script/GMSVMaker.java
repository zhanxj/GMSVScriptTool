package tool.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * GMSV parser maker.
 * @author 	fuhuiyuan
 */
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
	
	/**
	 * Generate a bat to execute ANTLR.g4.
	 * @param 	bat
	 * 			Generate bat file path.
	 * @param 	g4
	 * 			ANTLR.g4 file
	 */
	public static void makeBat(File bat, File g4) {
		// Step.1 : Create a bat file if not exists.
		if (!bat.exists()) {
			try {
				bat.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Step.2 : Make command.
		StringBuilder builder = new StringBuilder("java org.antlr.v4.Tool ");
		builder.append(g4.getPath());
		// Step.3 : Generate file.
		try {
			FileWriter writer = new FileWriter(bat);
			writer.append(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Execute a bat.
	 * @param 	bat
	 * 			Bat file.
	 */
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
	
	/**
	 * Delete all unuse files.
	 * @param 	dir
	 * 			Dirctionary of files.
	 */
	public static void clearFileButJava(File dir) {
		for (File java : dir.listFiles(file -> !file.getName().endsWith(".java"))) {
			java.delete();
		}
	}

}
