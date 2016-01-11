package tool.script;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;

/**
 * Grammar Script maker of GMSV.
 * @author 	fuhuiyuan
 */
public class GMSVGrammarMaker {
	
	/** Rules of return */
	private final ListMultimap<String, String> rules = LinkedListMultimap.create();
	
	/**
	 * Generate the Grammar Script.
	 * @param 	grammars
	 * 			A list contains all grammars.
	 * @param 	dest
	 * 			Generate file path.
	 * @param 	template
	 * 			GMSV base rule.
	 */
	public void make(List<Grammar> grammars, File dest, File template) {
		// Step.1 : Append single rules.
		StringBuilder builder = new StringBuilder(readTemplate(template));
		grammars.forEach((grammar) -> {
			String pattern = grammar.getPattern();
			if (pattern.length() > 0) { // If no pattern, do not support it.
				// Append rule.
				String rule = grammar.getName().trim();
				builder.append(rule.toLowerCase()).append(" : ").append(pattern); // Patten
				grammar.getParams().forEach((attribute) -> {
					builder.append(" ").append(attribute.getClazz().toLowerCase()).append("Rule"); // Attribute
				});
				builder.append("; // ").append(grammar.getDescript()).append("\r\n"); // Descript
				// Process return
				String returnClass = grammar.getReturnValue() == null ? "" : grammar.getReturnValue().getClazz();
				if (returnClass.length() > 0) { // Cache return info.
					rules.put(returnClass.toUpperCase(), rule);
				}
			}
		});
		// Step.2 : Append mutil rules.
		rules.asMap().forEach((key, collection) -> {
			List<String> list = Lists.newLinkedList(collection);
			builder.append("mutil").append(key).append(" : ").append(list.remove(0));
			list.forEach(rule -> builder.append(" | ").append(rule.toLowerCase()));
			builder.append(";").append("\r\n");
		});
		// Step.3 : Generate file.
		try {
			FileWriter writer = new FileWriter(dest);
			writer.append(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read a template file of GMSV.
	 * @param 	template
	 * 			template file
	 * @return	template content
	 */
	private String readTemplate(File template) {
		try {
			LineNumberReader reader = new LineNumberReader(new FileReader(template));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("//@")) { // Special return
					line = line.replaceFirst("//@", "");
					String[] infos = line.split(":");
					rules.put(infos[0].trim(), infos[0].trim());
				}
				builder.append(line).append("\r\n");
			}
			reader.close();
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
