package 예상대진표;

public class Solution {
    public static void main(String[] args) {
        // a번째의 선수와, b번째의 선수가 둘이 만나기 전까지 승리한다고 할 때,
        // 두 선수는 몇 라운드에서 만나는가.
        // 짝수번째인 선수의 다음 번호는 짝수 / 2 번 값.
        // 홀수번째인 선수는 (홀수 + 1) / 2 값
        // 둘이 만나게 될 때는 작은 선수 값 + 1값이 큰 선수 값이어야 하고,
        // 큰 선수는 자신이 짝수여야 한다.

        int n = 8;
        int a = 4;
        int b = 7;

        int big = Math.max(a, b);
        int small = Math.min(a, b);
        int round = 1;
        while (!(small + 1 == big && big % 2 == 0)) {
            big = calNextNum(big);
            small = calNextNum(small);
            round++;
        }
        System.out.println(round);
    }

    static int calNextNum(int a) {
        if (a % 2 == 0)
            return a / 2;
        else
            return (a + 1) / 2;
    }
}