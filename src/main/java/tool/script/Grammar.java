package tool.script;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * GMSV grammar.
 * @author 	fuhuiyuan
 */
public class Grammar {
	
	private String name, descript, pattern;
	
	private Attribute returnValue;
	
	private final List<Attribute> params;
	
	public Grammar() {
		params = Lists.newLinkedList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Attribute getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Attribute returnValue) {
		this.returnValue = returnValue;
	}

	public List<Attribute> getParams() {
		return params;
	}

	public void addParam(Attribute param) {
		if (param != null) {
			params.add(param);
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
