package main.java.service;

import org.apache.commons.lang3.StringUtils;

public class UtilHelper {

    public static Integer getRandomNumber(Integer min, Integer max){
        return min + (int) (Math.random() * (max - min + 1));
    }

    /**
     * Retorna una palabra con una determinada cantidad de caracteres
     * Los primeros caracteres están en mayúscula y los últimos son números
     *
     * @param length
     * @return
     */
    public static String randomAlphabetic(Integer length) {

        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();


        Integer quantityOfNumbers = length>4?2:1;
        Integer numberOfLetters = length - quantityOfNumbers;
        for (int i = 0; i < length; i++) {
            String caracter;
            if( i<= numberOfLetters){
                caracter = String.valueOf(chars.charAt((int) (Math.random() * (length ))));
            }
            else{
                caracter = getRandomNumber(1,9).toString();
            }

            sb.append(caracter);
        }
        Integer lettersUp = length/2 >=3 ? 3:1 ;

        return sb.toString().substring(0, lettersUp).toUpperCase() + sb.toString().substring(lettersUp);
    }
}
