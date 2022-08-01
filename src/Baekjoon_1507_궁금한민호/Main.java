/*
 Author : Ruel
 Problem : Baekjoon 1507번 궁금한 민호
 Problem address : https://www.acmicpc.net/problem/1507
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1507_궁금한민호;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시로 이루어진 나라가 있다.
        // 각 도시에서 다른 도시에 이르는 최단 경로가 계산되어있다.
        // 모든 도시를 연결하는 최소 도로의 이동 시간 합을 구하라.
        // 불가능하다면 -1을 출력한다.
        //
        // 플로이드-와샬 문제
        // 플로이드 와샬을 통해, 출발지 -> 도착지의 값과 출발지 -> 경유지 -> 도착지의 값이 같다면
        // 출발지 -> 도착지의 도로는 위 경유지를 통해 간 것이므로 해당 도로를 없애준다고 생각하자.
        // 문제에서 말하는 '불가능한 경우'가 조금 이상하다고 느껴지는데
        // 출발지 -> 도착지의 값이 출발지 -> 경유지 -> 도착지의 값보다 더 큰 경우이다.
        // 이 경우 문제에서 최단 경로가 계산되어있다고 했는데, 모순되는게 아닌가 싶은데, 문제에서 그렇다고 하니 그렇다고 하자..
        // 위와 같은 경우들을 구해 도로들을 하나씩 지워주고, 도로가 양방향임에 주의하며 남은 도로들의 이동 시간의 합을 구해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[][] adjMatrix = new int[n][];
        for (int i = 0; i < adjMatrix.length; i++)
            adjMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int answer = 0;
        // 경유지
        for (int via = 0; via < n; via++) {
            // 출발지
            for (int start = 0; start < n; start++) {
                if (start == via)
                    continue;

                // 도착지
                for (int end = 0; end < n; end++) {
                    if (end == via || end == start)
                        continue;

                    // 출발지 -> 경유지, 경유지 -> 도착지의 도로가 존재하고
                    if (adjMatrix[start][via] != 0 && adjMatrix[via][end] != 0) {
                        int viaSum = adjMatrix[start][via] + adjMatrix[via][end];

                        // 경유지를 거쳐오는 것이 바로 오는 경우와 같다친다면,
                        // 해당 도로를 없애준다.
                        if (viaSum == adjMatrix[start][end])
                            adjMatrix[start][end] = 0;
                        // 만약 경유지를 거쳐가는 것이 더 소요시간이 적다면 문제에서 말하는
                        // '불가능한 경우'
                        else if (viaSum < adjMatrix[start][end]) {
                            answer = -1;
                            break;
                        }
                    }
                }
            }
        }

        // 불가능한 경우기 아니라면
        if (answer != -1) {
            // 남은 도로들의 소요 시간의 합을 구한다.
            for (int i = 0; i < n; i++) {
                // 양방향이므로 i < j인 경우만 고려해서 더하면 한번만 더할 수 있다.
                for (int j = i + 1; j < n; j++)
                    answer += adjMatrix[i][j];
            }
        }

        // 답 출력.
        System.out.println(answer);
    }
}