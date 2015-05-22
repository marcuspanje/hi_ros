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

    public static int mark = 0;
    public static byte[] getByteArr2(int j) {
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
    
    public static byte[] getByteArr(int j) {
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            Integer k  = (j >> 8*(3-i)) & 0x000000FF;
            b[3-i] = k.byteValue();
        }
        return b;

    }
    public static void fillArr(byte[] b1, byte[] b2, int offset, int len) {
        for (int i = 0; i < len; i++) {
            b1[offset+i] = b2[i];
            mark++;
        };
    }

    public static void main(String[] args) throws IOException {
        

        String hostName = "marcuspan-MacBookAir";
        int portNumber = 11311;

        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            OutputStream OutStr = echoSocket.getOutputStream();
            PrintWriter out =
                new PrintWriter(OutStr, true);
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
            byte[][] header = new byte[6][];
            header[0] = msgdef.getBytes();
	        header[1] = callerid.getBytes();
	        header[2] = latch.getBytes();
	        header[3] = md5sum.getBytes();
	        header[4] = topic.getBytes();
	        header[5] = type.getBytes();
            
            byte[] b_msg = msg.getBytes();
            int len_header = 0;
            for (int i = 0; i < header.length; i++) {
                len_header = len_header + header[i].length + 4;
            }
            
            len_header = len_header + 4;
            System.out.println(len_header);
            int len_msg = b_msg.length + 4 + 4;
            int len_all = len_header + len_msg; 
            byte[] send = new byte[len_all];
            mark = 0;
            fillArr(send, getByteArr(len_header), mark, 4);
            for (int i = 0; i < header.length; i++) {
                fillArr(send, getByteArr(header[i].length), mark, 4);
                fillArr(send, header[i], mark, header[i].length);
            }
            fillArr(send, getByteArr(len_msg), mark, 4);
            fillArr(send, getByteArr(b_msg.length), mark, 4);
            fillArr(send, b_msg, mark, b_msg.length);
            Byte firstb = new Byte("-80");
            
            send[0] = firstb.byteValue();
                 
            for (int i = 0; i < send.length; i++) {
                Byte b = new Byte(send[i]);
                System.out.println(Integer.toHexString(b.intValue())); 
            }
            OutStr.write(send);
			while(true) {
				String s = in.readLine();
				if (s == null)
					break;
               	System.out.println(s);
			}
   	    	/*	
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
            }*/
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
