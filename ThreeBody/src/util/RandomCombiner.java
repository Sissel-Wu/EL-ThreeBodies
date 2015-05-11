package util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Account;
import model.Coordinate;

/**
 * 可以生成几个元素个数相同的集合的元素间的随机的一一映射
 * 辅助游戏开始时生成随机的角色和顺序
 * @author Sissel 
 */
public class RandomCombiner {

	private final int rowSize;
	private List<Object[]> columns;

	public RandomCombiner(int rowSize) {
		this.rowSize = rowSize;
		columns = new LinkedList<Object[]>();
	}

	public void addColumn(Object[] column) {
		columns.add(column);
	}

	public Object[][] generate() {
		Object[][] records = new Object[rowSize][columns.size()];
		for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
			Object[] objects = columns.get(columnIndex);
			RandomSequence rs = new RandomSequence(rowSize);
			for (Object object : objects) {
				records[rs.next()][columnIndex] = object;
			}
		}
		return records;
	}

	/**
	 * 
	 * @author Sissel
	 * 生成元素不重复的数字序列
	 * 原理：随机数random得到随机序号，返回next同时从列表中删除该元素
	 */
	private class RandomSequence {

		List<Integer> nums;
		Random random;

		public RandomSequence(int number) {
			nums = new LinkedList<Integer>();
			for (int i = 0; i < number; i++) {
				nums.add(i);
			}
			random = new Random();
		}

		public int next() {
			int randomIndex = random.nextInt(nums.size());
			int result = nums.get(randomIndex);
			nums.remove(randomIndex);
			return result;
		}

	}
	
	// TEST PASSED
	public static void main(String[] args) {
		RandomCombiner rc = new RandomCombiner(4);
		Account[] accs = new Account[4];
		accs[0] = new Account("A");
		accs[1] = new Account("B");
		accs[2] = new Account("C");
		accs[3] = new Account("AD");
		
		rc.addColumn(accs);
		String[] roles = new String[4];
		roles[0] = "TB";
		roles[1] = "EARTH";
		roles[2] = "TB";
		roles[3] = "UNIFIER";
		rc.addColumn(roles);
		
		rc.addColumn(Coordinate.generateCoordinates(4));
		
		for (Object[] records : rc.generate()) {
			Account ac = (Account)records[0];
			String rl = (String)records[1];
			Coordinate cd = (Coordinate)records[2];
			System.out.println(ac.getId()+" ^ "+rl+" ^ "+cd);
		}
	}
}
