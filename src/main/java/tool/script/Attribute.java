package tool.script;

/**
 * Synax attribute of GMSV.
 * @author 	fuhuiyuan
 */
public class Attribute {
	
	/**
	 * Data class.
	 * <ol>
	 * <li>int : {@link Integer}</li>
	 * <li>string : {@link String}</li>
	 * <li>boolean : {@link Boolean}</li>
	 * </ol>
	 */
	private String clazz;
	
	private String descript;

	/**
	 * Get class name of Data.
	 * <ol>
	 * <li>int : {@link Integer}</li>
	 * <li>string : {@link String}</li>
	 * <li>boolean : {@link Boolean}</li>
	 * </ol>
	 * @return	class name
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * Set class name of Data.
	 * <ol>
	 * <li>int : {@link Integer}</li>
	 * <li>string : {@link String}</li>
	 * <li>boolean : {@link Boolean}</li>
	 * </ol>
	 * @param	clazz
	 * 			class name
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
