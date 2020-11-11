package java_network_smtp_protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmailSocket {
	
	public static Socket smtpSocket;
	private static PrintWriter out;
	private static BufferedReader in;

	public static void main(String[] args) {
		
		/* ***** Initialization ***** */
		/* trying to open a socket on port 25 */
		/* trying to open in and out streams */
		
		try {
			
			smtpSocket = new Socket("localhost",25);
			in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream())); 
			out = new PrintWriter(smtpSocket.getOutputStream(), true);
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		/* ***** End of initialization ***** */
		
		/* Writing some data to the socket */
		if(smtpSocket != null && out != null && in != null) {
			try {
				
				/* ***** STEP1 ***** */
				/* Get greeting from the server */
				String responseLine;
				while((responseLine = in.readLine()) != null) {
					
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("220") != -1) {
						break;
					}
				}
				
				/* ***** STEP2 ***** */
				/* Initialization of the client's dialog */
				/* By identifying himself with HELO command */
				out.println("HELLO "+ InetAddress.getLocalHost().getHostAddress());
				System.out.println("HELLO "+ InetAddress.getLocalHost().getHostAddress());
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("250") != -1) {
						break;
					}
				}
				
				/* ***** STEP3 ***** */
				/* Client notifies the receiver of the originating email address of the message */
				/* With a MAIL FROM command */
				out.println("MAIL FROM: mytest@test.com");
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("250") != -1) {
						break;
					}
				}
				
				/* ***** STEP4 ***** */
				/* Client notifies the receiver of the recipient email address of the message */
				/* With a RCPT TO command */
				out.println("RCPT TO: theryadh27@gmail.com");
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("250") != -1) {
						break;
					}
				}
				
				
				/* ***** STEP5 ***** */
				/* Send data */
				out.println("DATA");
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("334") != -1) {
						break;
					}
				}
				
				/* ***** STEP6 ***** */
				/* Send email body */
				out.println("From: mytest@test.com");
				out.println("To: theryadh27@gmail.com");
				out.println("Subject: TEST EMAIL");
				out.println("");
				out.println("Subject: TEST EMAIL");
				out.println("This is a test message,");
				out.println("Greetings from myself.");
				out.println(""); // end of data sequence
				out.println(".");
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("250") != -1) {
						break;
					}
				}
				
				/* ***** STEP7 ***** */
				/*Send QUIT message*/
				out.println("QUIT");
				while((responseLine = in.readLine()) != null) {
					System.out.println("Server: "+ responseLine);
					if(responseLine.indexOf("221") != -1) {
						break;
					}
				}
				
				System.out.println("*************************");
				System.out.println("Email successefully sent");
				System.out.println("*************************");
				
				/* Closing all streams */
				in.close();
				out.close();
				smtpSocket.close();
				
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
	
		

	}

}
