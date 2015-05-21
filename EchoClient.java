/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.io.*;
import java.net.*;

public class EchoClient {


    public static byte[] getByteArr(int j) {
        int i = 3;
        byte[] b = new byte[4];
        String bin_str = Integer.toBinaryString(j);
            while (bin_str.length() > 8) {
                int len = bin_str.length();
                String right = bin_str.substring(len-8);
                String left = bin_str.substring(0, len-8);
                bin_str = left;
                Integer i1 = Integer.parseInt(right, 2);
                b[i] = i1.byteValue();
                i--;
            }
        if (i < 0) return b;
        if (bin_str.length() == 0) 
            bin_str = "0";

        Integer i1 = Integer.parseInt(bin_str, 2);
        b[i] = i1.byteValue();
        i--;
        Integer zero = new Integer(0);
        while (i > -1) {
            b[i] = zero.byteValue();   
            i--;
        } 
        return b;
         

    }

    public static void main(String[] args) throws IOException {
        

        String hostName = "marcuspan-MacBookAir";
        int portNumber = 11311;

        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
			String msgdef = "message_definition=string data\n\n"; 
			String callerid = "callerid=/rostopic_4767_1316912741557";
			String latch = "latching=1"; 
            String md5sum = "md5sum=99ce8a1687cec8cbd883ec73ca41d1";
            String topic = "topic=/chatter";
            String type = "type=std_msgs/String";
            String msg = "hello";
	        byte[] b_msgdef = msgdef.getBytes();
	        byte[] b_callerid = callerid.getBytes();
	        byte[] b_latch = latch.getBytes();
	        byte[] b_md5sum = md5sum.getBytes();
	        byte[] b_topic = topic.getBytes();
	        byte[] b_type = type.getBytes();
	        byte[] b_msg = msg.getBytes();
            int len_header = b_msgdef.length + b_callerid.length + b_latch.length 
            + b_md5sum.length + b_topic.length + b_type.length;
            int len_header_all = len_header + 6*4;
            int len_msg = b_msg.length; 
            int len_msg_all = len_msg + 4;
            int len_all = len_msg_all + len_header_all; 
            byte[] b = new byte[len_all];
            int i = 0;
            byte[] header0 =  getByteArr(len_header_all);
            for (int j = 0; j < 4; j++) {
                b[i+j] = header0[j];
                 
             
   	    		
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
				while(true) {
					String s = in.readLine();
					if (s == null)
						break;
                	System.out.println(s);
				}
				System.out.println("done reading");
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
