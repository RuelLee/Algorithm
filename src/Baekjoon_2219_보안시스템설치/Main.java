/*
 Author : Ruel
 Problem : Baekjoon 2219번 보안 시스템 설치
 Problem address : https://www.acmicpc.net/problem/2219
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2219_보안시스템설치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 컴퓨터와 m개의 회선 정보가 주어진다.
        // 모든 회선을 폐쇄한 뒤, 최소한의 회선을 복구하되, 서로 다른 두 컴퓨터 간에 통신이 가능하도록 한다.
        // 다른 컴퓨터와 통신을 하는데 평균 시간이 최소인 컴퓨터에 보안 시스템을 설치한다.'
        // 보안 시스템을 설치하는 컴퓨터는?
        //
        // 플로이드-워셜 문제
        // 먼저 m개의 회선을 갖고서 각각의 컴퓨터 다른 컴퓨터에 이르는 최소 시간을 모두 구한다.
        // 그 후, 구해진 행렬에서 i행은 i번 컴퓨터가 다른 컴퓨터들과 통신하는데 걸리는 시간들이므로
        // 총 시간을 합을 구해, 합이 가장 적은 컴퓨터에 보안 시스템을 설치한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 컴퓨터
        int n = Integer.parseInt(st.nextToken());
        // m개의 회선
        int m = Integer.parseInt(st.nextToken());
        
        // 인접행렬
        int[][] adjMatrix = new int[n][n];
        // 초기값 설정
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        // 회선 정보 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken());

            adjMatrix[a][b] = adjMatrix[b][a] = Math.min(adjMatrix[a][b], c);
        }
        
        // 플로이드 워셜
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;

                for (int end = 0; end < adjMatrix.length; end++) {
                    if (end == via || end == start || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                }
            }
        }

        // 평균 통신 시간(=통신 시간의 총합)이 가장 적은 컴퓨터를 찾는다.
        int com = 0;
        int cost = Integer.MAX_VALUE;
        for (int i = 0; i < adjMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < adjMatrix[i].length; j++) {
                // 자기 자신과 통신하는 경우는 건너뜀
                if (i == j)
                    continue;
                // i에서 j로 통신하는 방법이 없는 경우, 보안시스템을 설치해선 안된다.
                // 합에 큰 값을 넣어 고려되지 않도록 만든다.
                else if (adjMatrix[i][j] == Integer.MAX_VALUE) {
                    sum = Integer.MAX_VALUE;
                    break;
                }
                sum += adjMatrix[i][j];
            }

            // 합이 최소 비용을 갱신했다면
            // 해당 컴퓨터와 합을 기억해둔다.
            if (cost > sum) {
                com = i + 1;
                cost = sum;
            }
        }
        
        // 답안 출력
        System.out.println(com);
    }
}