package Q2개이하로다른비트;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        // number 보다 큰 수 중에 비트가 2개 이하로 다른 최소의 수는?
        long[] numbers = {5};
        long[] answer = new long[numbers.length];

        for (int i = 0; i < numbers.length; i++)
            answer[i] = findNextNum(numbers[i]);
        System.out.println(Arrays.toString(answer));
    }

    static long findNextNum(long num) {
        String aString = Long.toBinaryString(num);
        String answer = "";
        if (aString.charAt(aString.length() - 1) == '0')        // 마지막이 0으로 끝나면 그냥 +1시켜주면 답.
            return num + 1;
        else {
            boolean check = false;
            for (int i = aString.length() - 1; i >= 0; i--) {
                if (aString.charAt(i) == '0') {     // 뒤에서부터 0을 찾아가면서 찾는다면
                    check = true;
                    answer = aString.substring(0, i);   // i번째 0을 1로 바꾸고, i+1번째 1을 0으로 바꾼다.
                    answer += "10";
                    if (i + 2 <= aString.length())  //  원래 길이보다 같거나 짧다면, i+2이후의 문자열을 붙여준다.
                        answer += aString.substring(i + 2);
                    break;
                }
            }
            if (!check) {   // 0을 못 찾았다면, 가장 맨 앞에 10을 붙여준다.
                answer = "10";
                answer += aString.substring(1);
            }
        }
        return Long.parseLong(answer, 2);
    }
}
