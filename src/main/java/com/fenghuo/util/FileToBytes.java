package com.fenghuo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileToBytes {
	private static Logger log = LoggerFactory.getLogger(FileToBytes.class);
		//序列化方法
		 public static byte[] object2Bytes(Object value) {
		        if (value == null)
		            return null;
		        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		        ObjectOutputStream outputStream;
		        try {
		            outputStream = new ObjectOutputStream(arrayOutputStream);
		            outputStream.writeObject(value);
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                arrayOutputStream.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return arrayOutputStream.toByteArray();
		    }
		//反序列化方法
		    public static Object byte2Object(byte[] bytes) {
		        if (bytes == null || bytes.length == 0)
		            return null;
		        try {
		            ObjectInputStream inputStream;
		            inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
		            Object obj = inputStream.readObject();
		            return obj;
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		        return null;
		    }
		   
		
		    
		
}
