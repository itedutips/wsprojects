package com.itedutips.oauth2provider.recycler;

import java.util.Map;
import java.util.Set;

import com.itedutips.oauth2provider.model.InMemoryObject;
import com.itedutips.oauth2provider.model.OpenIDSession;

public class RecycleObj extends Thread {
	
	public void run()
	{
		for(;;)
		{
			try {
				sleep(4000);
				recycleoldsessions();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
			
		}
	}
	
	public void recycleoldsessions()
	{
		Map<String,OpenIDSession> obj = InMemoryObject.getMapInstance();
		Set<String> keyObjs = obj.keySet();
		for(String key : keyObjs)
		{
			System.out.println("Keys " + key); 
			OpenIDSession session = obj.get(key);
			if(session.isExpired())
			{
				System.out.println("Session " + session.getClient_id() + " expired"); 
				obj.remove(key);
			}
		}
	}

}
