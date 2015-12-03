package tool.script;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

public class GMSVGrammarMaker {
	
	public void make(List<Grammar> grammars, File dest, File template) {
		StringBuilder builder = new StringBuilder(readTemplate(template));
		grammars.forEach((grammar) -> {
			String rule = grammar.getName().trim();
			builder.append(rule.toLowerCase()).append(" : '").append(rule).append("'");
			grammar.getParams().forEach((attribute) -> {
				builder.append(" ").append(attribute.getClazz().toUpperCase());
			});
			builder.append("; // ").append(grammar.getDescript()).append("\r\n");
		});
		
		try {
			FileWriter writer = new FileWriter(dest);
			writer.append(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String readTemplate(File template) {
		try {
			LineNumberReader reader = new LineNumberReader(new FileReader(template));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\r\n");
			}
			reader.close();
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
