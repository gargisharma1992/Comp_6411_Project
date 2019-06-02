% Gargi sharma
% Comp 6411
% Calling module

-module(calling).
-export([masterProcess/1]).

%Master Process
masterProcess(SendingData)->
    receive
    	{defaultCase, ReceivedData}->
			whereis(ReceivedData) ! {introCase, SendingData},
			masterProcess(SendingData);	

		{introCase, ReceivedData}->
			timer:sleep(rand:uniform(100)),
			Time = exchange:getTime(),
			whereis (serverModule) ! {SendingData,"received intro message from ", ReceivedData,Time},
			whereis (SendingData) ! {replyCase,ReceivedData,Time},
			masterProcess(SendingData);
		
        {replyCase,ReceivedData,Time}->
			whereis (serverModule) ! {ReceivedData, "received reply message from ",SendingData,Time},
			masterProcess(SendingData)
		
		after 1000->	
			io:fwrite("Process ~p has received no calls for 1 second, ending...~n",[SendingData])			
    end.