# cookieserver
Fortune cookie server that will generate random fortune cookies for a client. 
Server port and text file to be passed as Server/MTServer command line arguments, e.g. 12345 fortunecookie.txt.
Client host and port to be passed as Client command line argument in format: <host>:<port>, e.g. localhost:12345
Use get-cookie to get a random cookie fortune.
get-cookie 5 returns 5 random cookie fortunes from the Server.
Maximum of 2 connections for Client.
