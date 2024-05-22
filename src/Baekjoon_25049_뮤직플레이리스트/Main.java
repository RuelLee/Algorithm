/*
 Author : Ruel
 Problem : Baekjoon 25049번 뮤직 플레이리스트
 Problem address : https://www.acmicpc.net/problem/25049
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25049_뮤직플레이리스트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 곡이 담긴 플레이리스트를 추천받았다.
        // 각 노래에 따른 만족도가 주어진다.
        // 순서대로 모든 곡을 듣되,
        // 2번 이전에 들었던 곡으로 돌아가 다시 그 곡부터 들을 수 있다.
        // 같은 노래를 세 번 이상 듣지 않도록하고자 할 때
        // 얻을 수 있는 최대 만족도는?
        //
        // 누적합, DP 문제
        // 문제를 조금 생각해보면
        // 전체합 + 부분합1 + 부분합2로 구성됨을 알 수 있다.
        // 어찌되었건, 모든 노래를 한번씩 들으며,
        // 원하는 두 구간을 구해 해당 구간을 한번씩 더들을 수 있으며, 두 구간은 서로 겹치지 않는다.
        // 따라서 왼쪽에서부터, 오른쪽에서부터 각각 누적합을 구해
        // 특정 노래를 기준으로 해당 노래 왼쪽 부분에서 가장 점수가 높은 구간의 점수
        // 해당 노래 오른쪽 부분에서 가장 점수가 높은 구간의 점수를 구해
        // 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노래와 각각의 만족도
        int n = Integer.parseInt(br.readLine());
        int[] musics = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 왼쪽, 오른쪽에서 시작하는 누적합
        long[] psumFromLeft = new long[n + 1];
        long[] psumFromRight = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            psumFromLeft[i] = psumFromLeft[i - 1] + musics[i - 1];
            psumFromRight[n - i] = psumFromRight[n - i + 1] + musics[n - i];
        }

        // i보다 작으면서 가장 낮은 점수를 가리키는 idx
        int minIdx = 0;
        // i를 기준으로 왼쪽에 구간에서
        // 가장 높은 점수 구간의 점수를 계산한다.
        long[] maxLeft = new long[n + 1];
        for (int i = 1; i < psumFromLeft.length; i++) {
            // maxLeft[i - 1]까지의 값과 minIdx ~ i-1까지의 구간의 값 중 더 큰 값을 저장
            maxLeft[i] = Math.max(maxLeft[i - 1], psumFromLeft[i] - psumFromLeft[minIdx]);

            // 만약 minIdx까지의 누적합보다 i까지의 누적합이 더 작다면
            // i를 minIdx로 저장
            if (psumFromLeft[i] < psumFromLeft[minIdx])
                minIdx = i;
        }

        minIdx = n;
        // i 기준으로 오른쪽 구간에서 가장 점수가 높은 구간의 점수를 계산한다.
        long[] maxRight = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], psumFromRight[i] - psumFromRight[minIdx]);
            if (psumFromRight[i] < psumFromRight[minIdx])
                minIdx = i;
        }

        // i를 기준으로 왼쪽 구간과 오른쪽 구간에서 가장 높은 점수를 갖는 두 구간의
        // 점수 합을 구하며, 그 중 가장 높은 값을 구한다.
        long answer = Long.MIN_VALUE;
        for (int i = 0; i < n + 1; i++)
            answer = Math.max(answer, maxLeft[i] + maxRight[i]);
        // 두 구간의 합 중 최대값을 구했으므로, 전체 합을 더해 답을 출력한다.
        System.out.println(answer + psumFromLeft[n]);
    }
}