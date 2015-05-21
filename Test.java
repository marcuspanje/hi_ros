public class Test{

	public static void main(String[] args) {
        byte[] b = EchoClient.getByteArr(256);
        for (int i = 0; i < b.length; i++) 
            System.out.println(b[i]);
        
        
/*
        Integer i = new Integer(255);
        String s = "10000001";
        Integer i2 = Integer.parseInt(s, 2);
        System.out.println(i2.byteValue());
		String s = "abcdefg";

		byte[] bytes = s.getBytes();
		int len = bytes.length;
		for (int i = 0; i < len; i++) {
			Byte b = new Byte(bytes[i]);
			System.out.println(b.intValue());
		}*/
	}

}
