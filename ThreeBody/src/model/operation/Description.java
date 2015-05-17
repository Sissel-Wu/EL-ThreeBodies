package model.operation;

public class Description extends Operation{

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private String description;

	public Description(String operator, String receiver, String description) {
		super(operator, receiver);
		this.description = description;
	}
	
	@Override
	public String toOperator(){
		return description;
	}

}
