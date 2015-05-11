package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Account;
import dto.PreferenceDTO;

/**
 * 
 * 负责存读账户缓存，数据缓存（战报等），Preference
 *
 */
public class UserData {
	
	public static void savePreference(PreferenceDTO pfr){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("Preference.dat")));
			oos.writeObject(pfr);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PreferenceDTO loadPreference(){
		PreferenceDTO localData = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File("Preference.dat")));
			localData = (PreferenceDTO) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return localData;
	}

	public static void saveAccount(Account ac) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("Account.dat")));
			oos.writeObject(ac);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Account loadAccount() {
		Account localData = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File("Account.dat")));
			localData = (Account) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return localData;
	}
	
	public static void saveTransientID(String transientID){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("transientID.dat")));
			oos.writeObject(transientID);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String loadTransientID(){
		String tid = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File("transientID.dat")));
			tid = (String) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tid;
	}
	
	public static void clearAccount(){
		File localData = new File("Account.dat");
		localData.delete();
	}
}
