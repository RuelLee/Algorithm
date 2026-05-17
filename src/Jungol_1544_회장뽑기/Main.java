/*
 Author : Ruel
 Problem : Jungol 1544번 회장뽑기
 Problem address : https://jungol.co.kr/problem/1544
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1544_회장뽑기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 최대 50명의 회원이 주어진다. 그리고 친구인 관계들이 주어진다.
        // 이 중에서 회장을 뽑는데, 다른 회원들과 가장 가까운 사람이 회장이 된다고 한다.
        // 가까운 정도는
        // 모든 사람이 친구일 경우 1, 모든 사람이 친구 혹은 친구의 친구일 경우 2
        // 모든 사람이 친구, 친구의 친구, 친구의 친구의 친구일 경우 3, ... 식으로 늘어난다.
        // 회장으로 가능한 사람의 점수와 인원
        // 그리고 각 사람의 번호를 출력하라
        //
        // 플로이드 워셜 문제
        // 친구 사이일 경우 1로 값을 정하고 플로이드 워셜을 통해
        // 다른 모든 사람들과의 관계를 밝힌다.
        // 그리고 각 사람들마다 가장 먼 관계가 자신의 점수가 된다.
        // 이를 통해 가장 점수가 낮은 사람의 점수와 인원 수, 그리고 각 번호를 구한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n명
        int n = Integer.parseInt(br.readLine());

        // 친구 관계
        int[][] adjMatrix = new int[n + 1][n + 1];
        // 초기화
        for (int[] am : adjMatrix)
            Arrays.fill(am, Integer.MAX_VALUE);
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // 두 값이 -1로 주어질 경우 친구 관계 입력 종료
            if (a == -1 && b == -1)
                break;

            // a와 b는 친구
            adjMatrix[a][b] = adjMatrix[b][a] = 1;
        }

        // 플로이드 워셜
        for (int via = 1; via <= n; via++) {
            for (int start = 1; start <= n; start++) {
                if (start == via || adjMatrix[start][via] == Integer.MAX_VALUE)
                    continue;

                for (int end = 1; end <= n; end++) {
                    if (end == start || end == via || adjMatrix[via][end] == Integer.MAX_VALUE)
                        continue;

                    adjMatrix[start][end] = Math.min(adjMatrix[start][end], adjMatrix[start][via] + adjMatrix[via][end]);
                }
            }
        }

        // 각 사람의 점수를 구한다.
        int[] counts = new int[n + 1];
        counts[0] = Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // 자기 자신과의 관계는 건너뜀
                if (i == j)
                    continue;
                // 가장 먼 관계를 구한다.
                counts[i] = Math.max(counts[i], adjMatrix[i][j]);
            }
            // 현재까지의 최소 점수보다 더 작은 경우
            // 큐를 비우고 채워나간다.
            if (counts[i] < counts[queue.peek()]) {
                queue.clear();
                queue.offer(i);
            } else if (counts[i] == counts[queue.peek()])       // 같은 경우 큐에 추가한다.
                queue.offer(i);
        }

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        // 점수와 인원 수
        sb.append(counts[queue.peek()]).append(" ").append(queue.size()).append("\n");
        // 그리고 각각의 번호를 기록
        sb.append(queue.poll());
        while (!queue.isEmpty())
            sb.append(" ").append(queue.poll());
        // 답 출력
        System.out.println(sb);
    }
}