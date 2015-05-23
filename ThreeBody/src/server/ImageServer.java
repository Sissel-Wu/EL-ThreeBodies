package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.interfaces.RMIImage;
import util.R;
import util.R.img_format;
import util.R.img_type;
import util.R.info;


public class ImageServer extends UnicastRemoteObject implements RMIImage {

	protected ImageServer() throws RemoteException {
		super();
		try {
			Naming.rebind("ImgService", this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			new ImageServer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public byte[] downloadImage(String id, img_type type)
			throws RemoteException {
		File file = new File(getLocation(id, type));
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			byte[] result = new byte[bis.available()];
			bis.read(result);
			bis.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public info uploadImage(String id, byte[] file, img_type type, img_format format)
			throws RemoteException {
		String location = null;
		switch(type){
		case HEAD:
			location = "/root/EL/head/";
			switch(format){
			case PNG:
				location += (id+".png");
				break;
			case JPG:
				location += (id+".jpg");
				break;
			}
			break;
		case ADVERTISEMENT:
			break;
		}
		try {
			File img = new File(location);
			img.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(img));
			bos.write(file);
			bos.close();
			return R.info.SUCCESS;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public img_format checkFormat(String id, img_type type)
			throws RemoteException {
		String location = getLocation(id, type);
		int index = location.lastIndexOf(".");
		switch(location.substring(index+1, location.length()-1)){
		case "png":
			return img_format.PNG;
		case "jpg":
			return img_format.JPG;
		}
		return null;
	}
	
	private String getLocation(String id,img_type type){
		String location = null;
		switch(type){
		case HEAD:
			File tmp = new File("/root/EL/heads");
			for(String name:tmp.list()){
				if(name.startsWith(id+".")){
					location = "/root/EL/heads/"+name;
					break;
				}
			}
		case ADVERTISEMENT:
			location = "/root/EL/ads/ad0";
		}
		return location;
	}

}