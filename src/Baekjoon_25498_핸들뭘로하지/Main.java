/*
 Author : Ruel
 Problem : Baekjoon 25498번 핸들 뭘로 하지
 Problem address : https://www.acmicpc.net/problem/25498
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25498_핸들뭘로하지;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int node;
    int idx;

    public Seek(int node, int idx) {
        this.node = node;
        this.idx = idx;
    }
}

public class Main {
    static char[] chars;
    static List<List<Integer>> connections;

    public static void main(String[] args) throws IOException {
        // n개의 노드로 이루어진 트리가 주어지며, 루트 노드는 1이다.
        // 각 노드마다 할당된 알파벳이 있다.
        // 루트 노드에서 시작하여 단말 노드를 만날 때까지 진행하며
        // 거친 노드들의 알파벳을 이어 단어를 만들었을 때
        // 사전 순으로 가장 늦은 단어를 찾아라
        //
        // 최단 경로
        // 비스무리하게 풀었다.
        // 먼저, 갈 수 있는 다음 노드들을 해당 노드가 단어에서 오는 순서에 따라 오름차순
        // 순서가 같다면, 알파벳 순서에 대해 역순으로 정렬하도록 우선순위큐를 정의하고 담았다.
        // 우선순위큐에서 차례대로 뽑아가며, 단어를 만들어가며, 현재 순서의 알파벳과 같거나 더 늦은 순서를 갖는 경우에
        // 해당 순서의 알파벳을 갱신토록 하였다.
        // 우선순위큐에서 순서대로 살펴보므로, 사실 더 이른 알파벳이 느린 알파벳보다 일찍 등장하지는 않지만
        // 같은 알파벳은 등장할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 노드와 각 노드에 할당된 알파벳
        int n = Integer.parseInt(br.readLine());
        chars = br.readLine().toCharArray();
        
        // 노드 연결 정보
        connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        StringTokenizer st;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        // 이미 거쳐간 노드를 해쉬셋에 저장
        HashSet<Integer> hashSet = new HashSet<>();
        // 첫 알파벳은 무조건 루트 노드
        char[] answer = new char[n];
        answer[0] = chars[0];
        // 우선순위큐에 따라
        // 단어의 순서에 따라, 같다면 알파벳의 순서가 느린 것을 우선.
        PriorityQueue<Seek> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.idx == o2.idx)
                return Character.compare(chars[o2.node], chars[o1.node]);
            return Integer.compare(o1.idx, o2.idx);
        });
        priorityQueue.offer(new Seek(0, 0));
        while (!priorityQueue.isEmpty()) {
            Seek current = priorityQueue.poll();
            // 만약 answer에 담긴 알파벳이 자신보다 더 늦은 순서의 알파벳이라면
            // current는 살펴볼 필요없이 건너뛴다.
            if (answer[current.idx] > chars[current.node])
                continue;
            
            // 해쉬셋에 해당 노드 기록
            hashSet.add(current.node);
            // 다음 노드가
            for (int next : connections.get(current.node)) {
                // 이전에 방문한 노드가 아니고, 다음에 올 알파벳들 중 가장 늦거나 같은 순서를 갖고 있는 경우
                if (!hashSet.contains(next) && (answer[current.idx + 1] == '\u0000' || chars[next] >= answer[current.idx + 1])) {
                    // 알파벳 갱신
                    answer[current.idx + 1] = chars[next];
                    // 우선순위큐 추가
                    priorityQueue.offer(new Seek(next, current.idx + 1));
                }
            }
        }
        
        // answer를 그대로 String으로 만들어 답 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.length && answer[i] != '\u0000'; i++)
            sb.append(answer[i]);
        System.out.println(sb);
    }
}