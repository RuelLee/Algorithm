/*
 Author : Ruel
 Problem : Baekjoon 1082번 방 번호
 Problem address : https://www.acmicpc.net/problem/1082
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 방번호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static String[][] dp;

    public static void main(String[] args) throws IOException {
        // n-1번까지 사용할 수 있는 숫자가 주어지고, 그 때의 가격과 소지하고 있는 돈 m원이 주어진다
        // 만들 수 있는 가장 큰 수는 무엇인가
        // DP를 사용하는 배낭 문제
        // 가장 큰 숫자를 먼저 사용하고 0을 마지막에 사용해야 같은 숫자들을 쓰더라도 가장 큰 수를 만들 수 있다.
        // 첫번째에 n-1 숫자를 사용하여 마지막에 0을 붙이는 경우까지 생각한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        // nums[0]은 비워두고, nums[1]에는 쓸 수 있는 가장 큰 숫자의 금액이, nums[nums.length-1]에는 0번 숫자의 금액이 들어간다
        for (int i = nums.length - 1; i > 0; i--)
            nums[i] = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(br.readLine());

        // 숫자의 가격이 1이고 m이 50일 경우, 50자리 수가 만들어질 수 있다
        // int나 long이 아닌 String으로 받자.
        // 0번 행과 0번 열은 0으로 초기세팅.
        dp = new String[n + 1][m + 1];
        Arrays.fill(dp[0], String.valueOf(0));
        for (int i = 1; i < dp.length; i++)
            dp[i][0] = String.valueOf(0);

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                dp[i][j] = findBingOne(dp[i - 1][j], dp[i][j - 1]);     // i번째 숫자를 안 사용하며 j원까지 사용했을 때의 값과, i번째 숫자를 사용하며 j-1원까지 사용했을 때의 값 중 큰 값으로 일단 세팅.
                // 사용가능 금액이 i번째 수를 사용할 수 있는 금액이라면, 현재 금액과, dp[i][j-nums[i]]에 i번째 숫자인 (nums.length - i - 1)을 뒤에 붙였을 때의 값과 비교해 큰 값을 가져온다.
                if (j >= nums[i])
                    dp[i][j] = findBingOne(dp[i][j], dp[i][j - nums[i]].equals("0") ? String.valueOf(nums.length - i - 1) : dp[i][j - nums[i]] + (nums.length - i - 1));
            }
        }
        System.out.println(dp[dp.length - 1][dp[dp.length - 1].length - 1]);
    }

    static String findBingOne(String a, String b) {     // String인 두 수를 비교하는 메소드
        if (a.length() > b.length())        // 길이가 다르면 긴 쪽이 크다.
            return a;
        else if (b.length() > a.length())
            return b;

        // 길이가 같다면
        // 가장 큰 자리 수부터 값일 비교
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) > b.charAt(i))      // i번재 수가 a가 더 크다면 바로 a 리턴.
                return a;
            else if (a.charAt(i) < b.charAt(i))     // b가 더 크다면 바로 b 리턴.
                return b;
        }
        // 여기까지 온다면 a와 b는 같은 수. 아무거나 리턴.
        return a;
    }
}