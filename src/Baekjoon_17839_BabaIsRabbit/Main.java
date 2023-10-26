/*
 Author : Ruel
 Problem : Baekjoon 17839번 Baba is Rabbit
 Problem address : https://www.acmicpc.net/problem/17839
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17839_BabaIsRabbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // is 라는 단어를 통해 사물을 다른 사물로 바꿀 수 있다.
        // p is q -> p라는 사물을 q라는 사물로 바꾼다.
        // n개의 명령들이 주어질 때
        // Baba라는 사물에 명령들을 적용해 어떤 사물로 만들 수 있는지 구해보자.
        //
        // 그래프 탐색 문제
        // 명령에 따라서 사물끼리의 연결관계를 만들고, 탐색하면 되는 문제.
        // 당연히 String 형태로 주어지므로 이를 수 형태로 바꾸어 계산해주자.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 명령들
        int n = Integer.parseInt(br.readLine());

        // 시작 단어
        String startWord = "Baba";
        // 단어들을 수와 매칭한다.
        HashMap<String, Integer> indexes = new HashMap<>();
        indexes.put(startWord, 0);
        // 매칭된 단어들을 순서대로 리스트에 저장한다.
        List<String> words = new ArrayList<>();
        words.add(startWord);
        // 단어들의 연결 관계
        List<List<Integer>> orders = new ArrayList<>();
        orders.add(new ArrayList<>());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 사물 p와 q
            String p = st.nextToken();
            st.nextToken();
            String q = st.nextToken();

            // 각각의 단어가 처음 등장한 단어인지 확인하고
            // 처음 등장했다면, 수를 매칭하고, 리스트에 추가하고, 연결관계를 위한 리스트를 추가한다.
            if (!indexes.containsKey(p)) {
                indexes.put(p, indexes.size());
                words.add(p);
                orders.add(new ArrayList<>());
            }
            if (!indexes.containsKey(q)) {
                indexes.put(q, indexes.size());
                words.add(q);
                orders.add(new ArrayList<>());
            }

            // p -> q의 연결관계를 형성한다.
            orders.get(indexes.get(p)).add(indexes.get(q));
        }
        
        // 방문 체크
        boolean[] visited = new boolean[indexes.size()];
        visited[0] = true;
        Queue<Integer> queue = new LinkedList<>();
        // Baba라는 단어가 0번으로 등록되어있고, 해당 단어부터 탐색을 시작한다.
        queue.offer(0);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 가능한 다른 사물들을 살펴본다.
            for (int next : orders.get(current)) {
                // 아직 방문한 적이 없다면
                if (!visited[next]) {
                    // 방문 체크 후, 큐에 추가
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }

        // 단어들을 사전순으로 출력해야하므로
        // 우선순위큐에 담자.
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        // 방문체크가 된 단어들을 우선순위큐에 담는다.
        for (int i = 1; i < visited.length; i++) {
            if (visited[i])
                priorityQueue.offer(words.get(i));
        }

        // 순서대로 꺼내 답안 작성 후 출력.
        StringBuilder sb = new StringBuilder();
        while (!priorityQueue.isEmpty())
            sb.append(priorityQueue.poll()).append("\n");
        System.out.print(sb);
    }
}