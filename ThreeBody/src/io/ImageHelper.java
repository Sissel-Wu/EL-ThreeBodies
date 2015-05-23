package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;

import server.interfaces.RMIImage;
import util.R;
import util.R.img_format;
import util.R.info;

public class ImageHelper {
	
	private RMIImage rmii;

	public ImageHelper() {
		super();
	}
	
	public static void main(String[] args) {
		ImageHelper ih = new ImageHelper();
		ih.downloadHeadByID("q");
		
	}
	
	public ImageIcon downloadHeadByID(String id){
		setUpRMII();
		File file = null;
		try {
			R.img_format fm = rmii.checkFormat(id, R.img_type.HEAD);
			// 服务端找不到，返回null
			if(fm == null){
				return null;
			}
			switch(fm){
			case PNG:
				file = new File("tmp/"+id+".png");
				break;
			case JPG:
				file = new File("tmp/"+id+".jpg");
				break;
			}
			file.createNewFile();
			byte[] imgBytes = rmii.downloadImage(id, R.img_type.HEAD);
			if(imgBytes == null){
				return null;
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(imgBytes);
			bos.close();
			return new ImageIcon(file.getPath());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public info uploadHead(String id,File file){
		setUpRMII();
		R.img_type type = R.img_type.HEAD;
		R.img_format format = null;
		if(file.getName().endsWith("jpg") || file.getName().equals("JPG")){
			format = R.img_format.JPG;
		}else if(file.getName().endsWith("png") || file.getName().equals("PNG")){
			format = R.img_format.PNG;
		}else{
			return R.info.INVALID;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			bis.close();
			return rmii.uploadImage(id, bytes, type, format);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setUpRMII(){
		try {
			rmii = NetClient.getInstance().getImageServer();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
