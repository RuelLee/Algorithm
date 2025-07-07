/*
 Author : Ruel
 Problem : Baekjoon 16965번 구간과 쿼리
 Problem address : https://www.acmicpc.net/problem/16965
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16965_구간과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Range {
    int start;
    int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Main {
    static int[][] adjMatrix = new int[100][100];

    public static void main(String[] args) throws IOException {
        // n개의 쿼리가 주어질 때 쿼리를 처리하라
        // 1 x y (x < y): 새로운 구간 (x, y)를 집합에 추가한다. 구간의 크기는 이전에 추가된 구간의 크기보다 크다.
        // 2 a b (a ≠ b): a번째 추가된 구간에서 b번째 추가된 구간으로 이동하는 경로가 있으면 1, 없으면 0을 출력한다. 가장 처음 추가된 구간은 1번째 구간이다.
        // 구간 (x1, y1)에서 구간 (x2, y2)로 이동하려면 x2 < x1 < y2 또는 x2 < y1 < y2를 만족해야 한다.
        //
        // BFS, 플로이드-워셜
        // 구간을 넘어다니는 조건에 주의해야하는 문제
        // 구간이 서로 일부분만 겹친다면 서로 넘나들 수 있지만
        // 한 구간이 다른 한 구간을 완전히 포함한다면, 포함하는 구간에서 작은 구간으로는 이동할 수 없다.
        // 위 조건에 주의하며 서로 넘나들 수 있는지 여부를 판단하고
        // 플로이드-워셜로 한 구간에서 갈 수 있는 다른 구간들을 계산해주면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 쿼리
        int n = Integer.parseInt(br.readLine());
        
        // 구간들을 저장
        List<Range> ranges = new ArrayList<>();
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        boolean add = false;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (o == 1) {
                // 1번 쿼리일 경우, 구간 추가
                // 구간이 추가되었음 표시
                add = true;
                ranges.add(new Range(x, y));
                
                // 추가된 구간과, 이미 있던 구간들과 겹치는 부분이 있는지 확인
                for (int j = 0; j < ranges.size() - 1; j++) {
                    // 서로 겹치는 부분이 없는 경우 건너뜀
                    if (ranges.get(j).start >= y || ranges.get(j).end <= x)
                        continue;
                    
                    // 추가된 구간이 j 구간을 포함하는 경우
                    // j에서 추가된 구간으로만 이동 가능
                    if (x <= ranges.get(j).start && y >= ranges.get(j).end)
                        adjMatrix[j][ranges.size() - 1] = 1;
                    else        // 그 외에는 상호 이동 가능
                        adjMatrix[j][ranges.size() - 1] = adjMatrix[ranges.size() - 1][j] = 1;
                }
            } else {
                // 2번 쿼리인 경우
                // 이전 2번 쿼리를 처리한 후, 1번 쿼리가 있어서 추가된 구간이 있는 경우
                if (add) {
                    // 플로이드 워셜을 다시 돌려줌
                    floydWarshall(ranges.size());
                    // add를 다시 false로 돌려줌
                    add = false;
                }
                
                // x -> y로 갈 수 있는지 기록
                sb.append(adjMatrix[x - 1][y - 1]).append("\n");
            }
        }
        // 전체 답 출력
        System.out.print(sb);
    }
    
    // 플로이드 워셜
    static void floydWarshall(int size) {
        // start -> via -> end로 갈 수 있는지 확인한다.

        for (int via = 0; via < size; via++) {
            for (int start = 0; start < size; start++) {
                if (start == via || adjMatrix[start][via] == 0)
                    continue;

                for (int end = 0; end < size; end++) {
                    if (end == via || end == start || adjMatrix[via][end] == 0)
                        continue;

                    adjMatrix[start][end] = 1;
                }
            }
        }
    }
}