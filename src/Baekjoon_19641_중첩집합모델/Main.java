/*
 Author : Ruel
 Problem : Baekjoon 19641번 중첩 집합 모델
 Problem address : https://www.acmicpc.net/problem/19641
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19641_중첩집합모델;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] left, right;
    static List<PriorityQueue<Integer>> connections;
    static int cnt = 1;

    public static void main(String[] args) throws IOException {
        // 1레벨 HI-ARC - (경영지원실, 개발부)
        // 2레벨 경영지원실 - (사업전략팀, 법무팀), 개발부 - (개발1팀, 개발2팀, 운영팀)
        // 3레벨 사업전략팀 - 사원1, 사원2, 법무팀 - 사원3, 개발1팀 - 사원4, 사원5, 개발2팀 - 사원6, 사원7, 사원8, 운영팀 - 사원9
        // 같은 조직도가 주어졌을 때
        // 부서명/이름	left	right	level
        // HI-ARC	    1   	34	    1
        // 경영지원실	2	    13	    2
        // 사업전략팀	3	    8   	3
        // 사원1      	4	    5   	4
        // 사원2      	6	    7   	4
        // 법무팀      	9	    12  	3
        // 사원3	    10	    11  	4
        // 개발부	    14	    33  	2
        // 개발1팀	    15	    20	    3
        // 사원4	    16	    17	    4
        // 사원5	    18	    19	    4
        // 개발2팀	    21	    28	    3
        // 사원6	    22	    23	    4
        // 사원7	    24	    25  	4
        // 사원8	    26	    27	    4
        // 운영팀	    29	    32	    3
        // 사원9	    30  	31	    4
        // 같이 나타낼 수 있다.
        // 트리를 구성하는 n개의 노드, 간선의 정보, 루트가 주어질 때
        // 각 노드의 번호와 left, right를 출력하라
        //
        // 오일러 경로 테크닉
        // 비연속적인 노드들을 연속적인 노드로 바꿔 표현하는 것.
        // DFS 탐색을 통해 새로운 노드 번호를 부여한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드
        int n = Integer.parseInt(br.readLine());

        // left, right
        left = new int[n + 1];
        right = new int[n + 1];
        // 연결 정보
        connections = new ArrayList<>();
        // 최소힙 우선순위큐를 통해 오름차순 정렬이 되게끔한다.
        for (int i = 0; i < n + 1; i++)
            connections.add(new PriorityQueue<>());
        StringTokenizer st;
        for (int i = 1; i < connections.size(); i++) {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());
            while (num != -1) {
                connections.get(node).offer(num);
                num = Integer.parseInt(st.nextToken());
            }
        }

        int root = Integer.parseInt(br.readLine());
        // root가 주어질 때, left, right를 연결 정보를 바탕으로 채운다.
        findAnswer(root);

        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < left.length; i++)
            sb.append(i).append(" ").append(left[i]).append(" ").append(right[i]).append("\n");
        // 전체 답 출력
        System.out.print(sb);
    }

    // idx번 방문
    static void findAnswer(int idx) {
        // left[idx]에 현재 cnt 기록
        left[idx] = cnt++;

        // 자식 노드들을 방문
        while (!connections.get(idx).isEmpty()) {
            int next = connections.get(idx).poll();
            // 상위 노드라면 건너뜀
            if (left[next] > 0)
                continue;
            findAnswer(next);
        }
        // 마지막으로 right[idx]에 cnt를 기록
        right[idx] = cnt++;
    }
}