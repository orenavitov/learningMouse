package com.mih;


import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class mihFuture {

    public static void main(String args[]) {
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        int A = 'A';
        int Q = 'Q';
        byte[] byteArray = new byte[10];
        byteArray[0] = (byte) A;
        byteArray[1] = (byte) Q;
        String result = encoder.encodeToString(byteArray);
        System.out.println("the encode result is " + result);
        byte[] resultByte = decoder.decode(result);
        for (int i = 0; i < resultByte.length; i++) {
            System.out.println("the decode byte is " + resultByte[i]);
        }

    }
}
