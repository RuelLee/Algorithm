/*
 Author : Ruel
 Problem : Baekjoon 2458번 키 순서
 Problem address : https://www.acmicpc.net/problem/2458
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2458_키순서;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 학생이 주어지고, 두 명의 키를 비교한 결과 m개가 주어진다
        // 자신의 키가 몇 번째인지 알 수 있는 학생의 수를 구하라
        //
        // 의외로 플로이드 와샬 문제였다
        // 자신의 키의 순서를 알 수 있다 -> 자신과 모든 학생들 간의 연결된 경로가 있다
        // 따라서 플로이드 와샬을 통해 연결된 학생의 수가 n-1인 학생의 명 수를 찾으면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 인접 행렬
        boolean[][] adjMatrix = new boolean[n][n];
        // 자신의 키와 비교 가능한 학생의 수
        int[] students = new int[n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            // a학생과 b학생의 직접적인 결과가 있다.
            // 인접 행렬에 true
            // 그리고 자신의 키와 비교 가능한 학생 수를 하나씩 늘려준다.
            adjMatrix[a][b] = true;
            students[a]++;
            students[b]++;
        }

        // 플로이드 와샬
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (via == start)
                    continue;

                for (int end = 0; end < adjMatrix.length; end++) {
                    if (end == via || end == start)
                        continue;

                    // 직접적인 비교 결과가 없고
                    // via를 통해 start학생과 end 학생의 키를 비교하는 것이 가능하다면
                    // 인접 행렬에 true 값을 넣어주고
                    // start, end 각각 자신과 비교 가능한 학생 수를 하나씩 늘려준다.
                    if (!adjMatrix[start][end] && adjMatrix[start][via] && adjMatrix[via][end]) {
                        adjMatrix[start][end] = true;
                        students[start]++;
                        students[end]++;
                    }
                }
            }
        }

        // 최종적으로 자신과 다른 모든 학생과 키 비교가 가능한 학생의 수를 출력하면 된다.
        System.out.println(Arrays.stream(students).filter(value -> value == n - 1).count());
    }
}