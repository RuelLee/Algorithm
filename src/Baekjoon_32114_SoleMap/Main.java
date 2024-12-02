/*
 Author : Ruel
 Problem : Baekjoon 32114번 SoleMap
 Problem address : https://www.acmicpc.net/problem/32114
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32114_SoleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시가 일직선으로 늘어서 있고, 모든 도시는 옆 도시와 양방향 도로로 연결되어있다.
        // i번째 도시와 i+1번째 도시가 연결된 도로의 차선이 주어진다.
        // 또한 m개의 교통 정보가 주어진다.
        // 교통 정보는 u v x로 u번 도시에서 v번 도시로 가는 차량이 x대라는 뜻이다.
        // 각 도로에 걸리는 부담은 하루 동안 지나가는 차량의 수와 차선이 주어질 때
        // 각 차로를 지나는 차량 대수의 제곱의 합의 최솟값이다.
        // 예를 들어 차량이 4, 차선이 3일 경우
        // 각 차선에 2대, 1대, 1대가 지나가,
        // 2^2 + 1^2 + 1^1 인 4가 도로에 걸리는 부담이다.
        // 전체 도로의 부담을 구하라
        //
        // 누적합 문제
        // 교통 정보를 토대로 한 도로에 다니는 차량을 각각 구해야한다.
        // 이를 위해 누적합으로
        // 각 교통 정보의 시작 위치의 차량에 +x, 도착 위치에 -x를 하여
        // 누적합 처리를 하면 각각의 도로에 다니는 차량의 수를 구할 수 있다. (imos 법)
        // 이를 바탕으로 각각의 도로에 걸리는 부담을 계산해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 교통 상황
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각 도로의 차선 수
        int[] paths = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 도로에 다니는 차량을 imos 누적합으로 구한다.
        int[] psums = new int[n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken());

            // 시작 위치에선 +x
            psums[u] += x;
            // 도착 위치에선 -x
            psums[v] -= x;
        }
        // 누적합 처리
        for (int i = 1; i < psums.length; i++)
            psums[i] += psums[i - 1];

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            // psums[i]대의 차량을 paths[i] 차선에 최대한 균등하게 분배해야한다.
            // psums[i] / paths[i] 대의 차량을 각 차선에 분배하고
            // psums[i] % paths[i]에 해당하는 차선 만큼은 +1대만큼 더 가져간다면
            // 최대한 균등하게 분배할 수 있다.
             
            // 도로에 걸리는 부담
            long burden = 0;
            // 차량을 paths[i] 차선에 균등하게 분배.
            long carPerPath = psums[i] / paths[i];
            // 균등하게 분배할 수 있는 차선의 수
            int path = paths[i] - (psums[i] % paths[i]);
            // 해당 차선들의 부담 계산
            burden += carPerPath * carPerPath * path;
            // psums[i] % paths[i] = (paths[i] - path) 만큼의 차선은
            // carPerPath + 1대 만큼의 차량이 지나간다.
            // 해당 부담 계산
            burden += (carPerPath + 1) * (carPerPath + 1) * (paths[i] - path);
            // 답 기록
            sb.append(burden).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}