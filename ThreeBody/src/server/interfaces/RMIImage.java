package server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import util.R;
import util.R.info;

public interface RMIImage extends Remote{
	
	byte[] downloadImage(String id,R.img_type type) throws RemoteException;
	
	info uploadImage(String id,byte[] file,R.img_type type,R.img_format format) throws RemoteException;
	
	R.img_format checkFormat(String id,R.img_type type) throws RemoteException;

}