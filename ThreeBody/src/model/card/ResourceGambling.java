package model.card;

import java.util.List;

import model.Player;
import model.operation.Description;
import model.operation.Operation;
import model.operation.ResourceChange;
import model.operation.ResourceChange.Type;
import dto.GameDTO;

/*
 * 获取用户自行输入的资源数
 * 然后随机得到输入数的两倍资源或是得到的资源数为0
 */
public class ResourceGambling extends Card {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean success;

	public ResourceGambling(String operator, String receiver,
			int requiredResource,boolean success) {
		super(operator, receiver);
		this.name = "资源赌博";
		this.requiredResource = requiredResource;
		this.success = success;
	}

	@Override
	public void process(List<Operation> subOperations) {

		// pay resources
		ResourceChange rc = new ResourceChange(operator, receiver,
				Type.DECREASE, this.requiredResource);
		subOperations.add(rc);
		
		if(success){
			// description
			Description description = new Description(operator, receiver, "赌博成功！");
			subOperations.add(description);
			ResourceChange rca = new ResourceChange(operator, receiver,
					Type.INCREASE, 2 * (this.requiredResource));
			subOperations.add(rca);
		}else{
			Description description = new Description(operator, receiver, "赌博失败！");
			subOperations.add(description);
		}
		
		// setUsed
		Player pOperator = this.findOperator(GameDTO.getInstance());
		pOperator.setPrivilegeGamble(false);
		
		pOperator.refreshCardUnavailable();
	}

}
