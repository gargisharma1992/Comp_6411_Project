/*
 * Gargi Sharma
 * Comp 6411
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class exchange {
	
	//Create an array list
	static ArrayList<getData> arrayList= new ArrayList<getData>();
	public static void main(String[] args) throws InterruptedException {
		
		//Heading
		System.out.println("** Calls to be made **");
		
		try (BufferedReader br = new BufferedReader(new FileReader("calls.txt"))) {			
		    String ReadTextFile;
		    while ((ReadTextFile = br.readLine()) != null) {
		    	
		    	// create an object Thread 
		    	getData objectThread= new getData(); 
		    	
		    	// Display calls to be made heading		    	
		    	String heading = ReadTextFile.replace("{", "").replace("}.", "");
		    	String callHeading = heading.replaceFirst(",", ":");
		    	System.out.println(callHeading);		    	
		    	
		    	// Split data from text file
		       	String replacedData = ReadTextFile.replace("{", "").replace("}","").replace("[", "").replace("]","").replace(".","").replaceAll(" ","");
		    	String[] splitData = replacedData.split(",");
		    	
		    	// Check if splitData data is not null
		    	if(splitData!=null){		    		
			    	for (int i = 0; i < splitData.length; i++) {			    		
			    	  	if(splitData[i]!=null && i==0 ){
			    	  		objectThread.Sender=splitData[i];			    	  		
				    	}else{
			    	  		objectThread.Receiver.add(splitData[i]);
			    	  	}			    
			    	}
			    	arrayList.add(objectThread);			    	
			    	objectThread.start();
		    	}
		    }
		    
		}catch (IOException e) {
			e.printStackTrace();
		}		
		System.out.println("");
		Thread.sleep(1000);
		endProcess();	
	}	
	
	//End thread processes
	public static void endProcess() throws InterruptedException {		
		for (getData addItem : arrayList) {	
			System.out.println("");
			System.out.println( "Process "+ addItem.Sender+" has received no calls for 1 second, ending...");		
			Thread.sleep(100);
		}
			Thread.sleep(150);
			System.out.println();
			System.out.println( "Master has received no replies for 1.5 seconds, ending...");		  
	}

	//Get result
	public static void getResult(String fetchData,String Sender, String Receiver,int itemCount,long timer){		
		if(itemCount==1){
			System.out.println(fetchData);
	   	    calling.masterProcess("replyCase",Sender,Receiver,timer);
		}
		if(itemCount==2){
			System.out.println(fetchData);
	   	    calling.masterProcess("Exit",Sender,Receiver,timer);
		}
	}	
}

//Thread class
class getData extends Thread {
	ArrayList<String> Receiver = new ArrayList<String>();	
	String Sender;
	public void run() {	
    	for (String string : Receiver) {	
			try {
				//main thread sleep time of intro and rep
				Thread.sleep(10);   
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	calling.masterProcess("introCase",string,Sender,0);
    	} 
	 }
}

