package tool.script;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class GMSVScriptReader {
	
	private SetMultimap<String, String> keywords = HashMultimap.create();
	
	public void read(File dir) {
		try {
			for (File file : dir.listFiles()) {
				readFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		keywords.asMap().forEach((k, c) -> {
			StringBuilder builder = new StringBuilder(k);
			builder.append(" :");
			c.forEach(word -> builder.append(" '").append(word).append("' |"));
			builder.setLength(builder.length() - 1);
			System.out.println(builder.toString());
		});
	}
	
	private void readFile(File file) throws Exception {
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#")) {
					for (String info : line.split(" ")) {
						info = info.trim();
						if (info.matches("^[a-zA-Z]+.*")) {
							info = info.indexOf('?') > -1 ? info.split("\\?")[0] : info;
							info = info.indexOf('[') > -1 ? info.split("\\[")[0] : info;
							info = info.indexOf('(') > -1 ? info.split("\\(")[0] : info;
							if (info.toLowerCase().startsWith("giveleak")) {
								info = info.substring(0, "giveleak0item".length());
							} else if (info.toLowerCase().startsWith("haveleak")) {
								info = info.substring(0, "haveleak0item".length());
							} else if (info.toLowerCase().startsWith("killleak")) {
								info = info.substring(0, "killleak0item".length());
							} else {
								Pattern pattern = Pattern.compile("[0-9]");
								Matcher matcher = pattern.matcher(info);
								int index = matcher.find() ? info.indexOf(matcher.group()) : -1;
								info = index > -1 ? info.substring(0, index) : info;
								if (index > -1) {
									info = info.indexOf('_') > -1 ? info.split("_")[0] : info;
								}
							}
							info = info.replace("<", "");
							info = info.replace("==", "");
							info = info.replace("=", "");
							info = info.replace("!=", "");
							info = info.replace(",", "");
							keywords.put(info.toLowerCase(), info);
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new GMSVScriptReader().read(new File("D:/file/workspace/CrossGateResource/npc"));
	}

}
