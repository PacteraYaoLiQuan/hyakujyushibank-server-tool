package com.scsk.util;

import java.util.Random;

public class RandomUtil {

    public static String getStringRandom() {  
        
        String val = "";  
        Random random = new Random();  
          
        //いくつかの乱数を生成するためのパラメータの長さ、  
        for(int i = 0; i < 8; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //アルファベットや数値出力  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //出力は、大文字または小文字であります  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    } 
}
