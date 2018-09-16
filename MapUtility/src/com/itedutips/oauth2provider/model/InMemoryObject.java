package com.itedutips.oauth2provider.model;

import java.util.HashMap;
import java.util.Map;

import com.itedutips.oauth2provider.recycler.RecycleObj;

public final class InMemoryObject {

	private static InMemoryObject memoryObj = null;
	private static Map<String,OpenIDSession> mapObject = null;
	private InMemoryObject()
	{
		mapObject = new HashMap<>();
		RecycleObj recycleObj= new RecycleObj();
		recycleObj.start();
	}
	
	public static Map<String,OpenIDSession>  getMapInstance()
	{
		if(memoryObj == null)
		{
			memoryObj = new InMemoryObject();
		}
		
		return mapObject;
	}
	public static void main(String[] args) {
		

	}

}
