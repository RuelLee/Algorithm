package 거스름돈;

public class Solution {
    public static void main(String[] args) {
        // 거스름돈의 가짓수를 생각하는 문제.
        // DP로 해결 가능.

        int n = 5;
        int[] money = {1, 2, 5};

        int[] dp = new int[n + 1];  // 목적한 값인 n값 까지의 가지수를 구한다.

        for (int k : money) {    // 잔돈 종류를 하나씩 돌아가며,
            dp[k]++; // k원을 거슬러 주는 방법은 k원 화폐 하나로 돌려주는 방법이 1가지 존재.
            for (int j = k; j < dp.length; j++) {
                dp[j] = dp[j] + dp[j - k];   // j원을 돌려주는 방법은, '이미 연산된 j원을 돌려주는 방법'과 'j-k원에서 k원 화폐를 하나 더해서 돌려주는 방법'이 존재.
                dp[j] %= 1000000007;    // int값은 약 21억까지 처리가 가능한데, 1000000007값의 나머지로 끊으므로, 21억이 넘는 값은 없다. 따라서 일단 값 저장하고 1000000007의 나머지로 바꿔주자.
            }
        }
        System.out.println(dp[n]);
    }
}