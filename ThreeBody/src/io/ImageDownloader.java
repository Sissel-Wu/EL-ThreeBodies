package io;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import dto.AccountDTO;
import server.interfaces.RMIImage;
import util.R;
import util.R.info;

public class ImageDownloader {
	
	private RMIImage rmii;

	public ImageDownloader() {
		super();
	}
	
	public Image downloadHeadByID(String id){
		setUpRMII();
		File file = null;
		String location = null;
		try {
			switch(rmii.checkFormat(id, R.img_type.HEAD)){
			case PNG:
				if (id == AccountDTO.getInstance().getId()){
					location = "tmp\\"+id+".png";
				}else 
				break;
			case JPG:
				file = new File("tmp\\"+id+".jpg");
				break;
			}
			file.createNewFile();
			byte[] imgBytes = rmii.downloadImage(id, R.img_type.HEAD);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(imgBytes);
			bos.close();
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
		if(file.getName().endsWith("jpg")){
			format = R.img_format.JPG;
		}else if(file.getName().endsWith("png")){
			format = R.img_format.PNG;
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
//		rmii = NetClient.getInstance().getImageServer();
	}

}
