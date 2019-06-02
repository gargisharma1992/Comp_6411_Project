% Gargi sharma
% Comp 6411
% Exchange module

-module(exchange).
-export([start/0,getTime/0]).

%-Get process id ---------------------------------------------------------------------------------------------------%
fetchPID([Head|Tail])->
	Sender = element(1,Head), 
	register(Sender, spawn(calling,masterProcess,[Sender])),
	fetchPID(Tail);
fetchPID([])->
	register(serverModule,self()).

%-Split data here --------------------------------------------------------------------------------------------------%
splittedData([Head|Tail])->
	FetchElements = Head,
	SplittedHead = element(1,FetchElements),
	SplittedTail = element(2,FetchElements),
	splitTailPart(SplittedHead,SplittedTail),
	splittedData(Tail);
splittedData([])->ignore.

splitTailPart(SplittedHead,[Head|Tail])->
	whereis (SplittedHead) ! {defaultCase,Head},
	splitTailPart(SplittedHead,Tail);
splitTailPart(_,[])->ignore.

displaySplitResult(ReadTextFile)->
	io:fwrite("\n"),
	io:fwrite("** Calls to be made **\n"),
	lists:foreach(fun(A) ->{Head,Tail} = A,	
	io:fwrite("~p : ~p ~n",[Head,Tail]) end, ReadTextFile),
	io:fwrite("\n").

%-Get Time ---------------------------------------------------------------------------------------------------------%
getTime()->
	Time = element(3,erlang:now()).

%-Get Result -------------------------------------------------------------------------------------------------------%
getResult()->
	receive{Sender, Message, ReceivedData, Time}->
			io:fwrite("~w ~s~w[~w]~n", [Sender, Message, ReceivedData,Time]),
			getResult()
		after 1500->io:fwrite("~nMaster has received no replies for 1.5 seconds, ending...~n")
	end.

%-Start function ---------------------------------------------------------------------------------------------------%
start()->
	{ok, ReadTextFile} = file:consult("calls.txt"),
	fetchPID(ReadTextFile),	
	displaySplitResult(ReadTextFile),
	splittedData(ReadTextFile),
	getResult().