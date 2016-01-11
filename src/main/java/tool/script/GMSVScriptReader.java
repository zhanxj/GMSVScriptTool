package tool.script;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

/**
 * A reader of GMSV script.
 * @author 	fuhuiyuan
 */
public class GMSVScriptReader {
	
	/** A set of keywords */
	private SetMultimap<String, String> keywords = HashMultimap.create();
	
	/**
	 * Read keywords from a dirctionary.
	 * @param 	dir
	 * 			Dirctionary
	 */
	public void read(File dir) {
		// Read all files.
		try {
			for (File file : dir.listFiles()) {
				readFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Show all keywords on Console.
		keywords.asMap().forEach((k, c) -> {
			StringBuilder builder = new StringBuilder(k);
			builder.append(" :");
			c.forEach(word -> builder.append(" '").append(word).append("' |"));
			builder.setLength(builder.length() - 1);
			System.out.println(builder.toString());
		});
	}
	
	/**
	 * Read keywords from a file.
	 * @param 	file
	 * 			GMSV file
	 * @throws 	Exception
	 */
	private void readFile(File file) throws Exception {
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#")) { // Not comment
					for (String info : line.split(" ")) { // Segmentation of a line
						info = info.trim();
						if (info.matches("^[a-zA-Z]+.*")) { // A keyword
							info = info.indexOf('?') > -1 ? info.split("\\?")[0] : info;
							info = info.indexOf('[') > -1 ? info.split("\\[")[0] : info;
							info = info.indexOf('(') > -1 ? info.split("\\(")[0] : info;
							if (info.toLowerCase().startsWith("giveleak")) { // "giveleak" special keyword
								info = info.substring(0, "giveleak0item".length());
							} else if (info.toLowerCase().startsWith("haveleak")) { // "haveleak" special keyword
								info = info.substring(0, "haveleak0item".length());
							} else if (info.toLowerCase().startsWith("killleak")) { // "killleak" special keyword
								info = info.substring(0, "killleak0item".length());
							} else { // Common keyword
								Pattern pattern = Pattern.compile("[0-9]");
								Matcher matcher = pattern.matcher(info);
								int index = matcher.find() ? info.indexOf(matcher.group()) : -1;
								info = index > -1 ? info.substring(0, index) : info;
								if (index > -1) { // Remove numbers.
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
