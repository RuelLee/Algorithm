/*
 Author : Ruel
 Problem : Baekjoon 16958번 텔레포트
 Problem address : https://www.acmicpc.net/problem/16958
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16958_텔레포트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 도시 간의 이동 시간은 |x1- x2| + |y1 - y2|로 계산한다.
        // 특별한 도시 간에는 특정 시간 t로 이동할 수 있다.
        // n개의 특별한 도시 여부와 도시의 좌표가 주어진다.
        // 그 후 m개의 도시 쌍이 주어질 때
        // 도시 쌍의 최소 이동 시간들을 구하라
        //
        // 플로이드 워셜 문제
        // 이 문제에서는 두 도시 간의 이동 시간을 바로 주지 않는다.
        // 두 도시가 특별한 도시인지 여부를 먼저 생각하고
        // 그 후 좌표에 따른 이동 시간을 따져서 두 도시 간의 이동 시간을 구한다.
        // 물론 여기서 끝이 아니라 두 도시를 이동할 때, 다른 도시를 경유하면 더 빨라지는지 여부도 계산해야한다.
        // 여기서 플로이드 워셜을 사용한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 주어지는 도시의 개수 n과 특정 이동 시간 t
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        // 도시들의 정보
        int[][] cities = new int[n][];
        for (int i = 0; i < cities.length; i++)
            cities[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 각 도시 간의 이동 거리를 계산한다.
        int[][] distances = new int[n][n];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                // i, j가 같을 경우엔 건너뛰고
                if (i == j)
                    continue;
                
                // i, j 간의 이동 시간이 아직 계산이 안됐다면
                else if (distances[i][j] == 0) {
                    // 두 도시가 서로 특별한 도시라면
                    // 초기값을 t로 세팅
                    if (cities[i][0] == 1 && cities[j][0] == 1)
                        distances[i][j] = t;
                    // 아니라면 큰 값으로 세팅
                    else
                        distances[i][j] = Integer.MAX_VALUE;

                    // 두 도시 간의 거리에 좌표로서 계산할 이동 시간을 비교해보고
                    // 해당 시간이 초기값보다 작다면 갱신해준다.
                    distances[i][j] = distances[j][i]
                            = Math.min(distances[i][j],
                            Math.abs(cities[i][1] - cities[j][1]) + Math.abs(cities[i][2] - cities[j][2]));
                }
            }
        }
        // 경유하는 경우를 제외했을 때의 이동 시간 계산 끝.

        // 이제 플로이드 워셜로 경유했을 때 이동 시간이 줄어드는지 확인.
        for (int via = 0; via < n; via++) {
            for (int start = 0; start < n; start++) {
                if (start == via)
                    continue;

                for (int end = 0; end < n; end++) {
                    if (end == via || end == start)
                        continue;

                    if (distances[start][end] > distances[start][via] + distances[via][end])
                        distances[start][end] = distances[start][via] + distances[via][end];
                }
            }
        }
        
        // m개의 도시 쌍들에 대해 최소 이동 시간 기록
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            sb.append(distances[a][b]).append("\n");
        }

        // 답안 출력
        System.out.print(sb);
    }
}