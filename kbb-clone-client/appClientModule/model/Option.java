package model;

class Option implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private float price;
	/****************************** CONSTRUCTORS ******************************/
	protected Option() {
		this("NONE", 0);
	}

	protected Option(String name) {
		this(name, 0);
	}

	protected Option(float price) {
		this("NONE", price);
	}
	// top of the class constructor chain
	protected Option(String name, float price) {
		this.name = name;
		this.price = price;
	}

	/****************************** GETTERS ******************************/
	protected String getName() {
		return name;
	}

	protected float getPrice() {
		return price;
	}

	/******************
	 * Setters
	 *
	 */
	protected void setPrice(float price) {
		this.price = price;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/***************
	 *
	 * Option.toString()
	 */
	public String toString() {
		java.text.DecimalFormat twoDecimal = new java.text.DecimalFormat("$#0.00");
		StringBuilder out = new StringBuilder(name).append(": ").append(twoDecimal.format(price));
		return out.toString();
	}
	public void print() {
		System.out.println(this.toString());
	}

}
