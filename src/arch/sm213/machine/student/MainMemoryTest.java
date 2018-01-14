package arch.sm213.machine.student;


import machine.AbstractMainMemory;
import org.junit.Test;



import static org.junit.Assert.*;

public class MainMemoryTest {

    @Test
    public void testIntegerToBytes() {
        MainMemory mem = new MainMemory(0);

        byte[] test = new byte[4];
        test[0] = 00;
        test[1] = 00;
        test[2] = 00;
        test[3] = 01;

        assertArrayEquals(test, mem.integerToBytes(00000001));

        byte[] test1 = new byte[4];
        test1[0] = 00;
        test1[1] = 00;
        test1[2] = 00;
        test1[3] = (byte)0x80;

        assertArrayEquals(test1, mem.integerToBytes(128));

        byte[] test2 = new byte[4];
        test2[0] = (byte) 0xff;
        test2[1] = (byte) 0xff;
        test2[2] = (byte) 0xff;
        test2[3] = (byte) 0xff;

        assertArrayEquals(test2, mem.integerToBytes(-1));

        byte[] test3 = new byte[4];
        test3[0] = (byte) 00;
        test3[1] = (byte) 00;
        test3[2] = (byte) 0x01;
        test3[3] = (byte) 00;

        assertArrayEquals(test3, mem.integerToBytes(256));

        byte[] test4 = new byte[4];
        test4[0] = (byte) 0xff;
        test4[1] = (byte) 0xff;
        test4[2] = (byte) 0xff;
        test4[3] = (byte) 0x77;

        assertArrayEquals(test4, mem.integerToBytes(-137));

        byte[] test5 = new byte[4];
        test5[0] = (byte) 0x7f;
        test5[1] = (byte) 0xff;
        test5[2] = (byte) 0xff;
        test5[3] = (byte) 0xff;

        assertArrayEquals(test5, mem.integerToBytes(Integer.MAX_VALUE));

        byte[] test6 = new byte[4];
        test6[0] = (byte) 0x80;
        test6[1] = (byte) 00;
        test6[2] = (byte) 00;
        test6[3] = (byte) 00;

        assertArrayEquals(test6, mem.integerToBytes(Integer.MIN_VALUE));

    }

    @Test
    public void testBytesToInteger(){

        MainMemory mem = new MainMemory(0);


        assertEquals(01, mem.bytesToInteger((byte)00, (byte) 00, (byte) 00, (byte) 01));

        assertEquals(-1, mem.bytesToInteger((byte) 0xff, (byte) 0xff,(byte) 0xff,(byte) 0xff));

        assertEquals(256, mem.bytesToInteger((byte) 00, (byte) 00, (byte) 01, (byte) 00));

        assertEquals(-137, mem.bytesToInteger((byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0x77));

        assertEquals(Integer.MAX_VALUE, mem.bytesToInteger((byte) 0x7f, (byte) 0xff,(byte) 0xff, (byte) 0xff)); // not working

        assertEquals(Integer.MIN_VALUE, mem.bytesToInteger((byte) 0x80, (byte) 00, (byte) 00, (byte) 00));

    }

    @Test
    public void testAccessAlignment() {
        MainMemory mem = new MainMemory(0);

        assertFalse(mem.isAccessAligned(1, 4));
        assertFalse(mem.isAccessAligned(3, 4));
        assertFalse(mem.isAccessAligned(4, 3));
        assertTrue(mem.isAccessAligned(4, 2));
        assertTrue(mem.isAccessAligned(4, 4));

    }

    @Test
    public void testGetAndSet()throws AbstractMainMemory.InvalidAddressException{
        MainMemory mem = new MainMemory(128);

        byte [] intTest = {2,4,6,8};
        byte [] shortTest = {2,4};
        byte [] longTest = {2,4,6,8,10,12,14,16};

        try{
            mem.set(-1, intTest);
            fail("InvalidAddressException should have been thrown");
        }
        catch (Exception e){
            //expected
        }

        try {
            mem.set(122, longTest);
            fail("InvalidAddressException should have been thrown");
        }
        catch (Exception e){
            //expected
        }

        mem.set(4,  intTest);
        bytesEquals(intTest,mem.get(4,4));

        mem.set(8,shortTest);
        bytesEquals(shortTest,mem.get(8,2));

        mem.set(10,longTest);
        bytesEquals(longTest,mem.get(10,8));


    }

    private void bytesEquals(byte[] a, byte[] b) {
        if (a.length == b.length) {
            for (int i = 0; i < a.length; i++)
                assertEquals(a[i], b[i]);

        }
    }
}
