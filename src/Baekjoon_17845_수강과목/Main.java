/*
 Author : Ruel
 Problem : Baekjoon 17845번 수강 과목
 Problem address : https://www.acmicpc.net/problem/17845
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17845_수강과목;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 공부 시간 n과 과목의 수 k가 주어진다
        // 각 과목은 필요 공부 시간과 중요도가 주어진다.
        // 공부 시간 내에 중요도의 합을 최대로 하고자할 때 그 값은?
        //
        // 배낭 문제
        // 주어진 시간 내에 가장 높은 합을 만들어야한다.
        // n과 k 값이 크지 않으므로 DP로 해결할 수 있다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 주어진 입력
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 각 과목의 정보
        int[][] subjects = new int[k][2];
        for (int i = 0; i < subjects.length; i++)
            subjects[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 최대 시간은 n 시간.
        int[] dp = new int[n + 1];
        // 과목마다 DP를 채워나간다.
        for (int[] subject : subjects) {
            // i를 오름차순으로 계산하면 해당 과목에 대한 값이 중복 반영될 수 있다.
            // 따라서 내림차순으로 처리한다.
            // i + subject의 필요시간 만큼 동안 만들 수 있는 중요도 합은
            // dp[i + subject[1]]에 기록된다.
            // 따라서 해당 시간에 subject를 반영시킬 경우 만족도의 합 dp[i] + subject[0]과
            // 기존에 계산되어있던 값과 비교하여 더 큰 값을 남겨둔다.
            for (int i = n - subject[1]; i >= 0; i--)
                dp[i + subject[1]] = Math.max(dp[i + subject[1]], dp[i] + subject[0]);
        }

        // 최종적으로 n시간에 기록된 값을 출력한다.
        System.out.println(dp[n]);
    }
}