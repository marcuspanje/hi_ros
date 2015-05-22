public class Test{

    public static void changeArr(int[] b) {
        for (int i = 0; i < b.length; i++){ 
            b[i] =  100;
        }
    }

	public static void main(String[] args) {

        byte[] b = EchoClient.getByteArr2(176);
        for (int i = 0; i < b.length; i++) 
            System.out.println(b[i]);//shd b 0010
        byte[] b2 = EchoClient.getByteArr2(255);
        for (int i = 0; i < b2.length; i++) 
            System.out.println(b2[i]);//shd b 000-1
        
        int[] b3 = {0, 0, 0, 0};
        for (int i = 0; i < b3.length; i++) 
            System.out.println(b3[i]);//shd b 0001
        changeArr(b3);
    
        for (int i = 0; i < b3.length; i++) 
            System.out.println(b3[i]);//shd b 0001
        
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
