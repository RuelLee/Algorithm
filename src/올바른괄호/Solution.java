package 올바른괄호;


public class Solution {
    public static void main(String[] args) {
        // (으로 열렸다면 반드시 )으로 닫혀야한다.
        // (의 갯수는 항상 )보다 같거나 크다.
        // 만약 (의 갯수가 더 커지게 된다면 그 시점에서 이상한 문자열.

        String s = "(()(";

        int countOpen = 0;
        int countClose = 0;

        for (int i = 0; i < s.length(); i++) {
            if (countClose > countOpen)
                break;

            if (s.charAt(i) == '(')
                countOpen++;
            else if (s.charAt(i) == ')')
                countClose++;
        }

        System.out.println(countOpen == countClose);
    }
}