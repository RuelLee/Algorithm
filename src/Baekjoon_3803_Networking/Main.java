/*
 Author : Ruel
 Problem : Baekjoon 3803번 Networking
 Problem address : https://www.acmicpc.net/problem/3803
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3803_Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Connection {
    int a;
    int b;
    int cost;

    public Connection(int a, int b, int cost) {
        this.a = a;
        this.b = b;
        this.cost = cost;
    }
}

public class Main {
    static int[] parents = new int[51];
    static int[] ranks = new int[51];

    public static void main(String[] args) throws IOException {
        // 여러개의 테스트 케이스가 주어진다.
        // 당신은 p개의 지점과 r개의 연결들이 주어진다.
        // 모든 지점들을 직간접적으로 연결할 때의 최소 비용을 구하라
        //
        // 최소 스패닝 트리 문제
        // 테스트 케이스가 여러개 주어지는 것을 빼면 일반적인 최소 스패닝 트리 문제
        // 프림 혹은 크루스칼 알고리즘으로 해결 가능
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        String input = br.readLine();
        while (input != null) {
            st = new StringTokenizer(input);
            // p가 0이 주어지면 테스트케이스 종료
            // 지점의 개수
            int p = Integer.parseInt(st.nextToken());
            if (p == 0)
                break;
            // 연결의 개수
            int r = Integer.parseInt(st.nextToken());
            // p개의 개수에 따라 parents와 ranks 값 초기화
            init(p);

            // 우선순위큐에 연결들을 담아, 비용 오름차순으로 살펴본다.
            PriorityQueue<Connection> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
            for (int i = 0; i < r; i++) {
                st = new StringTokenizer(br.readLine());
                priorityQueue.offer(new Connection(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }

            int answer = 0;
            while (!priorityQueue.isEmpty()) {
                Connection current = priorityQueue.poll();
                // 만약 a와 b가 직간접적으로 연결이 되지 않았다면
                if (findParent(current.a) != findParent(current.b)) {
                    // current를 연결한다.
                    union(current.a, current.b);
                    // 그 때의 비용 추가
                    answer += current.cost;
                }
            }
            // 구해진 답 기록
            sb.append(answer).append("\n");

            // 다음 테스트케이스 입력
            br.readLine();
            input = br.readLine();
        }
        // 전체 답 출력
        System.out.print(sb);
    }

    // a집합과 b집합을 하나의 그룹으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 집합의 대표 찾기.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
    
    // p까지의 값 초기화
    static void init(int p) {
        for (int i = 0; i < p + 1; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
    }
}