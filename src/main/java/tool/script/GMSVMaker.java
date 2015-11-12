package tool.script;

import java.io.File;
import java.util.List;

public class GMSVMaker {

	public static void main(String[] args) {
		List<Grammar> grammars = new GMSVGrammarReader().readGrammarFromFile(new File("D:/file/workspace/CrossGateResource/base/数值.xls"));
		new GMSVGrammarMaker().make(grammars, new File("D:/file/workspace/CrossGateData/src/cg/data/script/antlr/GMSV.g4"), new File("D:/file/workspace/GMSVScriptTool/template/GMSV.g4"));
	}

}
