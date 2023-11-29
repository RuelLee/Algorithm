/*
 Author : Ruel
 Problem : Baekjoon 22345번 누적 거리
 Problem address : https://www.acmicpc.net/problem/22345
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22345_누적거리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 일직선 상의 n개의 마을에 대해, 인구와 위치가 주어진다.
        // q개의 후보지 중 하나에서 모임을 개최하려고 한다.
        // 누적거리는 각 마을에 있는 모든 사람들이 해당 위치에 이동하는 거리의 합이다.
        // 후보지들에 대한 누적거리 합을 출력하라.
        //
        // 누적합, 이분탐색 문제
        // 왼쪽에서부터 인구와 누적 거리에 대한 누적합을 구한다.
        // 그 후, 이분 탐색을 통해 후보지에 가장 가까운 마을 찾고
        // 해당 마을에 모여, 다 같이 이동한다고 생각하고 계산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 마을, q개의 후보지
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 마을들에 대한 인구와 위치
        int[][] villages = new int[n][];
        for (int i = 0; i < villages.length; i++)
            villages[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 위치에 따라 정렬
        Arrays.sort(villages, Comparator.comparing(o -> o[1]));
        
        // 왼쪽 마을과, 오른쪽 마을에 대한 인구와 누적 거리에 대한 누적합
        long[][] fromLeft = new long[n][2];
        fromLeft[0][0] = villages[0][0];
        long[][] fromRight = new long[n][2];
        fromRight[n - 1][0] = villages[n - 1][0];
        for (int i = 1; i < fromLeft.length; i++) {
            fromLeft[i][0] = fromLeft[i - 1][0] + villages[i][0];
            fromLeft[i][1] = fromLeft[i - 1][1] + fromLeft[i - 1][0] * (villages[i][1] - villages[i - 1][1]);

            fromRight[n - i - 1][0] = fromRight[n - i][0] + villages[n - i - 1][0];
            fromRight[n - i - 1][1] = fromRight[n - i][1] + fromRight[n - i][0] * (villages[n - i][1] - villages[n - i - 1][1]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 후보지
            int loc = Integer.parseInt(br.readLine());
            long answer;
            // 만약 첫번째 마을 보다 왼쪽이라면
            // 첫번째 마을에 모두 모여 한번에 왼쪽으로 이동
            if (loc <= villages[0][1])
                answer = fromRight[0][1] + fromRight[0][0] * (villages[0][1] - loc);
            else if (loc >= villages[n - 1][1])     // 마지막 마을 보다 오른쪽이라면, 마지막 마을에 모여 모두 이동
                answer = fromLeft[n - 1][1] + fromLeft[n - 1][0] * (loc - villages[n - 1][1]);
            else {      // 마을들 사이에 후보지가 있는 경우
                // 이분 탐색을 통해 가장 가까운 마을을 구한다.
                int left = 0;
                int right = n - 1;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (villages[mid][1] < loc)
                        left = mid + 1;
                    else
                        right = mid;
                }
                
                // 만약 찾은 right가 loc가 정확히 일치한다면 그냥 더하면 되고
                if (villages[right][1] == loc)
                    answer = fromLeft[right][1] + fromRight[right][1];
                else
                    answer = fromLeft[right - 1][1] + fromLeft[right - 1][0] * (loc - villages[right - 1][1]) +
                            fromRight[right][1] + fromRight[right][0] * (villages[right][1] - loc);
                // 그렇지 않다면, loc보다 왼쪽 마을은 right -1 마을에 모이고
                // 오른쪽 마을을 right에 모여 loc에 간다.

            }
            // 계산한 값 기록
            sb.append(answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}