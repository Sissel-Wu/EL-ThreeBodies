package util;

import java.util.Arrays;
import java.util.Random;

import model.Coordinate;

public class SillySophonHelper {
	
	private static int LENGTH = 3;
	
	public static int[] generatePossibleList(int correct) {
		// 生成
		int[] ist = new int[LENGTH];
		Arrays.fill(ist, -1);
		// 把正确的放在随机位置
		Random random = new Random();
		ist[random.nextInt(LENGTH)] = correct;
		// 生成余下的
		outer:
		for(int i = 0;i < LENGTH;i++){
			if(ist[i] == correct){
				continue;
			}
			int maybe = random.nextInt(Coordinate.MAX_NUM);
			// 如果和之前生成的某个相同，退回重来
			for(int j = 0;j < i;j++){
				if(maybe == ist[j]){
					i--;
					continue outer;
				}
			}
			ist[i] = maybe;
		}
		return ist;
	}

}
