package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.card.SillySophon;
import model.operation.CardUse;
import model.operation.Operation;

public class OperationTranslator {
    public static void translate(Iterable<Operation> operations, String path) throws IOException{
    	
    	File file = new File(path);
    	
    	if(!file.exists()){
    		try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	BufferedWriter bw;
    	bw = new BufferedWriter(new FileWriter(file));
    	
    	boolean skip = true;
    	
    	for(Operation operation : operations){
    		String info = operation.toOperator();
    		// 跳过第一个回合结束
    		if(skip && info.contains("回合结束")){
    			skip = false;
    			continue;
    		}
    		bw.write(info+"\n");
    		if(info.contains("回合结束")){
    			bw.write("============================"+"\n");
    		}
    	}
    	
    	bw.close();
    }
    
    public static void main(String[] args) {
    	SillySophon ss = new SillySophon("1a", "2b", 3, null, -1);
		CardUse cardUse = new CardUse("1a", "2b", ss);
		
		SillySophon ss2 = new SillySophon("3a", "4b", 3, null, -1);
		CardUse cardUse2 = new CardUse("3a", "4b", ss2);
		
		List<Operation> operations = new ArrayList<Operation>();
		operations.add(cardUse);
		operations.add(cardUse2);
    	
		try {
			OperationTranslator.translate(operations,"test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
