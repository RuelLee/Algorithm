/*
 Author : Ruel
 Problem : Baekjoon 27297번 맨해튼에서의 모임
 Problem address : https://www.acmicpc.net/problem/27297
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_27297_맨해튼에서의모임;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n차원의 점 m개가 주어진다.
        // 모든 점들로부터 맨해튼 거리가 가장 가까운 점을 찾고자한다.
        // 이 때의 모든 거리의 합과 해당 위치를 출력하라
        //
        // 정렬, 누적합 문제
        // 각 차원에 대해서 점들을 구하면 되므로
        // 각 차원에 해당하는 좌표들끼리 모아 정렬한 뒤,
        // 이전에 등장한 점들로부터 떨어진 거리의 합을 왼쪽에서부터와 오른쪽에서부터 각각 구한다.
        // 그 후, 그 합이 최소가 되는 점의 좌표를 기록하고, 거리를 더해나가는 방식으로 답을 구한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n차원의 점 m개
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 점들에 대해
        // 좌표가 같다면 같은 행에 모아둔다.
        long[][] arrays = new long[n][m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                arrays[j][i] = Long.parseLong(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        // 거리 합
        long sum = 0;
        for (int i = 0; i < n; i++) {
            // 행에 대해 정렬
            Arrays.sort(arrays[i]);
            // 왼쪽에서부터와 오른쪽에서부터 거리 합
            long[] fromLeft = new long[m];
            long[] fromRight = new long[m];
            for (int j = 1; j < arrays[i].length; j++) {
                // 왼쪽에서부터 계산
                // j번째 점보다 왼쪽에 있는 점들에 대해
                // j-1지점에 있던 값보다 (j와 j-1의 거리) * 점의 개수만큼 더 멀어지게 된다.
                fromLeft[j] = fromLeft[j - 1] + j * (arrays[i][j] - arrays[i][j - 1]);
                // 오른쪽에서부터 계산
                fromRight[m - j - 1] = fromRight[m - j] + j * (arrays[i][m - j] - arrays[i][m - j - 1]);
            }

            // 두 거리의 합이 가장 작은 지점을 찾는다.
            int minIdx = 0;
            for (int j = 1; j < m; j++) {
                if (fromLeft[j] + fromRight[j] < fromLeft[minIdx] + fromRight[minIdx])
                    minIdx = j;
            }
            // 해당 지점까지의 거리 합 누적.
            sum += fromLeft[minIdx] + fromRight[minIdx];
            // 해당 지점의 좌표 기록
            sb.append(arrays[i][minIdx]).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 거리 합 출력
        System.out.println(sum);
        // 좌표 출력
        System.out.println(sb);
    }
}