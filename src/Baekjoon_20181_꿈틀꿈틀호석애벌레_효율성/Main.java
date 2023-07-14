/*
 Author : Ruel
 Problem : Baekjoon 20181번 꿈틀꿈틀 호석 애벌레 - 효율성
 Problem address : https://www.acmicpc.net/problem/20181
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20181_꿈틀꿈틀호석애벌레_효율성;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 먹이가 일렬로 있다.
        // 애벌레가 지나가며 먹이를 연속적으로 먹거나, 먹지 않을 수 있다.
        // 이 때 만족도가 k이상이 되었다면, 그 순간 k를 초과한 만족도 만큼은 탈피 에너지로 축적이 되고
        // 즉시 만족도가 0이 된다고 한다.
        // 모든 먹이를 지나쳤을 때, 얻을 수 있는 최대 만족도는?
        //
        // 누적합, DP, 두포인터 문제
        // 먼저 구간 사이의 합이 k 이상이 되는 곳을 찾아야하므로 누적합과 두 포인터를 사용한다.
        // 그리고 또 최대 만족도를 구하므로 이를 위해 DP를 활용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 먹이와 최소 만족도 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        // 먹이들
        int[] foods = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 누적합
        long[] psums = new long[foods.length + 1];
        for (int i = 1; i < psums.length; i++)
            psums[i] = psums[i - 1] + foods[i - 1];
        
        //dp[i] i에 도달하는 얻을 수 있는 최대 만족도 합
        long[] dp = new long[psums.length];
        // 끝 포인터
        int end = 1;
        // 시작 포인터
        for (int start = 1; start < psums.length; start++) {
            // 애벌레가 1번 위치부터 계속 지나쳐온 것이므로
            // 이전 최대 만족도 합을 현재 위치로 가져올 수 있다.
            dp[start] = Math.max(dp[start], dp[start - 1]);
            // 만족도합이 k이상이 되는 최소 end를 찾는다.
            while (end < psums.length && psums[end] - psums[start - 1] < k)
                end++;
            
            // 만약 start ~ 끝까지 먹이를 먹더라도 k이상이 되지 않는다면
            // 그냥 건너뛴다.
            // 이전의 최대 만족도 합이 n번 위치까지 갈 수 있도록 반복문 자체는 진행
            if (end >= psums.length)
                continue;

            // 만족도 합이 k이상이라면
            // start - 1 위치까지 계산한 최대 만족도 합과
            // start ~ end까지의 먹이를 먹고 남은 탈피 에너지의 합을 dp[end] 값과 비교하고
            // 더 크다면 값을 남겨둔다.
            if (psums[end] - psums[start - 1] >= k)
                dp[end] = Math.max(dp[end], dp[start - 1] + psums[end] - psums[start - 1] - k);
        }

        // n까지 이동하며 얻을 수 있는 최대 만족도 합을 출력한다.
        System.out.println(dp[dp.length - 1]);
    }
}