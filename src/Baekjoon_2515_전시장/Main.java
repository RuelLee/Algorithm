/*
 Author : Ruel
 Problem : Baekjoon 2515번 전시장
 Problem address : https://www.acmicpc.net/problem/2515
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2515_전시장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 그림이 각각 지면으로부터의 높이와 가치가 주어진다
        // 모든 그림을 한 줄로 세워두고, 관람객들은 이 그림들을 앞에서 포개진 모습으로 바라본다
        // 이 때 노출되는 높이가 s 이상인 그림을 판매 가능 그림이라고 한다
        // 판매 가능 그림의 가치가 최대가 되게끔 만들고자할 때 이 때 판매 가능 그림들의 가치 합은?
        //
        // 정렬과 DP에 대한 문제
        // 노출되는 그림들의 가치 합과 보여지는 그림들 중 가장 높은 높이의 값을 저장해야한다.
        // 그림들을 하나씩 높이 오름차순으로 살펴보며
        // 이번 그림이 s높이 이상 노출되는 경우(이전 그림중 최대 높이가 이번 그림보다 s이상 작은 경우)의 가치 합과
        // 이전 그림들의 가치 합 중 더 큰 값을 DP에 저장해나가며 그림들의 가치 합을 구해나간다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        // 각 그림의 높이와 가치
        int[][] paintings = new int[n + 1][2];
        for (int i = 1; i < paintings.length; i++)
            paintings[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 높이순으로 정렬
        Arrays.sort(paintings, Comparator.comparingInt(o -> o[0]));

        int[][] dp = new int[n + 1][2];
        for (int i = 1; i < dp.length; i++) {
            // i번째 그림이 노출이 되려면, 이전 그림들 중 가장 높은 높이가 i번째 그림의 높이보다 s이상 작아야한다.
            // 그 때의 최대 높이
            int maxHeight = paintings[i][0] - s;
            // 이분 탐색을 통해 이전 DP들 중 maxHeight보다 같거나 작은 값을 찾는다.
            int start = 0;
            int end = i - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                if (dp[mid][0] <= maxHeight)
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            int idx = end;

            // maxHeight보다 낮은 그림들의 최대 가치합과 그림 i의 가치 합이
            // 이전 DP의 가치합보다 크다면
            // 해당 값을 이번 dp에 저장
            if (dp[i - 1][1] < dp[idx][1] + paintings[i][1]) {
                dp[i][0] = paintings[i][0];
                dp[i][1] = dp[idx][1] + paintings[i][1];
            } else {            // 그렇지 않다면, 이전 DP값을 가져온다.
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1];
            }
        }
        // 최종적으로 DP 마지막 공간에 최대 가치의 합이 계산되고 이를 출력해준다.
        System.out.println(dp[dp.length - 1][1]);
    }
}