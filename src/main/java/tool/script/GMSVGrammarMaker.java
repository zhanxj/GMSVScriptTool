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

public class GMSVGrammarMaker {
	
	private final ListMultimap<String, String> map = LinkedListMultimap.create();
	
	public void make(List<Grammar> grammars, File dest, File template) {
		StringBuilder builder = new StringBuilder(readTemplate(template));
		grammars.forEach((grammar) -> {
			String pattern = grammar.getPattern();
			if (pattern.length() > 0) {
				String rule = grammar.getName().trim();
				builder.append(rule.toLowerCase()).append(" : ").append(pattern);
				grammar.getParams().forEach((attribute) -> {
					builder.append(" ").append(attribute.getClazz().toLowerCase()).append("Rule");
				});
				builder.append("; // ").append(grammar.getDescript()).append("\r\n");
				
				String returnClass = grammar.getReturnValue() == null ? "" : grammar.getReturnValue().getClazz();
				if (returnClass.length() > 0) {
					map.put(returnClass.toUpperCase(), rule);
				}
			}
		});
		
		map.asMap().forEach((key, collection) -> {
			List<String> list = Lists.newLinkedList(collection);
			builder.append("mutil").append(key).append(" : ").append(list.remove(0));
			list.forEach(rule -> builder.append(" | ").append(rule.toLowerCase()));
			builder.append(";").append("\r\n");
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
				if (line.startsWith("//@")) {
					line = line.replaceFirst("//@", "");
					String[] infos = line.split(":");
					map.put(infos[0].trim(), infos[0].trim());
					builder.append(line).append("\r\n");
				} else {
					builder.append(line).append("\r\n");
				}
			}
			reader.close();
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
