package dto;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.ImageIcon;

import model.Account;
import control.MainControl;

/**
 * 假如account为空，除了ID返回 “未登录” ， 其他都返回null
 * @author Sissel
 *
 */
public class AccountDTO {
	
	private Account account;
	
	/*
	 * singleton
	 */
	private static AccountDTO instance;

	private AccountDTO(Account account) {
		this.account = account;
	}
	
	public static AccountDTO getInstance(){
		return instance;
	}
	
	public static void initializeByLocalData(Account account){
		instance = new AccountDTO(account);
	}
	
	public static void synchronize(Account acc){
		if(acc == null){
			instance.account = null;
			return;
		}
		if(instance.account == null){
			instance.account = acc;
			return;
		}
		instance.account = acc;
	}
	
	public String getId() {
		if(account == null){
			return "未登录";
		}else if(!MainControl.getInstance().isConnected() && !PreferenceDTO.getInstance().isAutoLogin()){
			return "未登录";
		}
		return account.getId();
	}
	public Image getHead() {
		if(account == null || account.getHead()==null || !new File(account.getHead()).exists()){
			return null;
		}
		return new ImageIcon(account.getHead()).getImage();
	}
	public int getPoint() {
		return  account == null ? null : account.getPoint();
	}
	public int getRank() {
		return account == null ? null : account.getRank();
	}
	public int getTotalGames() {
		return account == null ? null : account.getTotalGames();
	}
	public int getWins() {
		return account == null ? null : account.getWins();
	}
	public int getLosts() {
		return account == null ? null : account.getLosts();
	}
	
	public void setHead(File sourceFile){
		String end = null;
		if(sourceFile.getName().endsWith(".png") || sourceFile.getName().endsWith(".PNG")){
			end = ".png";
		}else{
			end = ".jpg";
		}
		account.setHead("userdata/"+account.getId()+end);
		File target = new File("userdata/"+account.getId()+end);
		if(!target.exists()){
			try {
				target.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			nioTransferCopy(sourceFile, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    private static void nioTransferCopy(File source, File target) throws IOException {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inStream.close();
            in.close();
            outStream.close();
            out.close();
        }
    }
}
