package 이진변환반복하기;

public class Solution {
    public static void main(String[] args) {
        // 주어진대로, 0을 제외 -> 남겨진 1의 길이를 2진법 숫자로 변환을 반복
        // 더 이상 바뀌지 않을 때까지 진행하고, 이러면서 사라진 0의 갯수를 count
        // 몇번 과정을 반복했는지도 횟수도 count
        // 2진법으로 변환은 Integer.toBinaryString 메소드를 사용하면 간단.
        String s = "01110";

        int cycleCount = 0;
        int zeroCount = 0;
        boolean changed = true;
        StringBuilder sb;
        while (changed) {
            sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0')
                    zeroCount++;
                else
                    sb.append(s.charAt(i));
            }
            String newS = Integer.toBinaryString(sb.toString().length());
            if (newS.equals(s))
                changed = false;
            else
                cycleCount++;
            s = newS;
        }
    }
}