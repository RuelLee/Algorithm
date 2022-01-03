/*
 Author : Ruel
 Problem : Baekjoon 17182번 우주 탐사선
 Problem address : https://www.acmicpc.net/problem/17182
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 우주탐사선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int fullBitmask;
    static int[][][] adjMatrix;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        // 총 행성의 개수, 시작 지점
        // 각 행성 간의 이동 시간이 주어진다
        // 모든 행성을 최소 한번씩 방문하는 최소 이동 시간을 구하라
        // 어쨌든 모든 행성에 대해서 서로 간의 최소 이동 시간이 필요하다 -> 플로이드 와샬
        // 최소 한번씩 방문을 어떻게 체크할 것인가 -> 행성의 수가 최대 10개로 적다 -> 플로이드 와샬로 계산하면서 경유한 행성들을 비트마스킹으로 남겨주자.
        // 플로이드 와샬 + 비트마스킹 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        init(n);
        for (int i = 0; i < adjMatrix.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < adjMatrix[i].length; j++) {
                adjMatrix[i][j][0] |= 1 << j;       // i에서 j지점으로 이동할 때 j 지점을 비트마스킹 해두자.
                adjMatrix[i][j][1] = Integer.parseInt(st.nextToken());      // 거리 저장
            }
        }

        // 플로이드 와샬
        for (int via = 0; via < adjMatrix.length; via++) {      // 경유 지점
            for (int start = 0; start < adjMatrix.length; start++) {        // 출발 지점
                if (start == via)
                    continue;

                for (int end = 0; end < adjMatrix.length; end++) {      // 도착 지점
                    if (end == start || end == via)
                        continue;

                    if (adjMatrix[start][end][1] > adjMatrix[start][via][1] + adjMatrix[via][end][1]) {     // 최소 이동 시간이 갱신이 된다면
                        // 최소 이동 경로가 start -> via -> end로 갱신됐으므로, start -> via에서 거친 행성들과 via -> end로 가며 거친 행성들을 합집합 해주자. 
                        adjMatrix[start][end][0] = adjMatrix[start][via][0] | adjMatrix[via][end][0];
                        // 그 때 소요 시간 갱신
                        adjMatrix[start][end][1] = adjMatrix[start][via][1] + adjMatrix[via][end][1];
                    }
                }
            }
        }

        // k 행성에 시작. 이미 k행성이므로 k행성 비트마스킹. 이 때 소요시간은 0.
        dfs(k, 1 << k, 0);
        System.out.println(answer);
    }

    static void dfs(int current, int bitmask, int cost) {
        if (bitmask == fullBitmask) {       // 모든 지점을 거쳤다면(첫번째 비트부터, n-1번째 비트까지 모두 1값을 갖는다면)
            answer = Math.min(answer, cost);        // 그 때의 소요 시간과 answer를 비교해서 최소값으로 갱신.
            return;
        }


        for (int next = 0; next < adjMatrix[current].length; next++) {      // 다음 행성
            if (current == next)        // 현재 위치와 같다면 패스
                continue;

            // 현재 지나온 경로에 next가 표시 되어있지 않다면(아직 방문한 적이 없다면), 방문한다
            // 그 때의 bitmask는 adjMatrix[current][next][0]에 기록된 행성들을 합집합해주낟
            // 역시 소요 시간은 adMatrix[current][next][1]을 더해준다.
            if ((bitmask & (1 << next)) == 0)
                dfs(next, bitmask | adjMatrix[current][next][0], cost + adjMatrix[current][next][1]);
        }
    }

    static void init(int n) {
        int bit = 0;
        for (int i = 0; i < n; i++)
            bit |= 1 << i;
        // 0 ~ (n - 1)까지의 비트를 모두 1로 채운 값을 fullBitMask로 정한다.
        fullBitmask = bit;

        adjMatrix = new int[n][n][2];
    }
}