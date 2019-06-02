/*
 * Gargi Sharma
 * Comp 6411
 */
public  class calling {

	public static void masterProcess(String text,String Sender, String Receiver, long timer)  {	
		String replyTime = ("["+timer+"]");
		try {
			long randomTime=1 + (long)(Math.random() * 100);
			Thread.sleep(randomTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(text=="introCase"){
			long time=System.currentTimeMillis();
			String introTime = ("["+time+"]");			
			String fetchedData= Sender+" received intro message from "+Receiver+ introTime;
			exchange.getResult(fetchedData,Sender,Receiver,1,time);
		}
		if(text=="replyCase"){
			String fetchedData= Receiver+" received reply message from "+Sender+ replyTime;
			exchange.getResult(fetchedData,Sender,Receiver,2,timer);
		}
	}

}

