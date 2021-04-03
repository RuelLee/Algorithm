package 타일링;

public class Solution {
    public static void main(String[] args) {
        // 2 x 1 을 채우는 방법 |
        // 2 x 2 를 채우는 방법 || or =
        // 2 x 3 을 채우는 방법 ||| or |= or =1
        // 2 x 4 를 채우는 방법 |||| or ||= or |=| or =|| or ==
        // 2 x n 을 채우는 방법 =  2 x (n-1) 에는 뒤에 |을 더하고, 2 x (n-2)에선 뒤에 = 을 더합.
        // 결국 피보나치 수열.

        int n = 600000;

        long[] fibonacci = new long[600001];
        fibonacci[1] = 1;
        fibonacci[2] = 2;

        for (int i = 3; i <= n; i++)
            fibonacci[i] = (fibonacci[i - 2] + fibonacci[i - 1]) % 1000000007;

        System.out.println(fibonacci[n]);
    }
}