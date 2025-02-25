/*
 Author : Ruel
 Problem : Baekjoon 2132번 나무 위의 벌레
 Problem address : https://www.acmicpc.net/problem/2132
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2132_나무위의벌레;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seek {
    int idx;
    int value;

    public Seek(int idx, int value) {
        this.idx = idx;
        this.value = value;
    }
}

public class Main {
    static List<List<Integer>> child;
    static int[] fruits;

    public static void main(String[] args) throws IOException {
        // n개의 정점을 갖는 트리가 주어진다.
        // 각 트리의 정점에는 열매가 맺는다.
        // 한 정점에서부터 출발하여, 방문한 정점은 다시 방문하지 않으며
        // 다른 한 정점으로 이동하며 가장 많은 열매들을 먹고자 한다.
        // 이 때 먹을 수 있는 열매의 수와 시작해야하는 정점의 번호를 출력하라
        // 그러한 정점이 여러개라면 가장 작은 정점의 번호를 출력한다.
        //
        // BFS, 트리의 지름 문제
        // 가장 먼 곳으로 가는 것은 아니지만, 열매 당 가중치를 준다고 한다면
        // 트리의 지름 문제와 다를 게 없는 문제.
        // 임의의 한 정점에서 가장 먼 한 정점 A를 찾는다.
        // A에서부터 가장 먼 정점 B를 찾는다.
        // A와 B가 트리의 지름!

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정점
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 정점의 열매 수
        fruits = new int[n + 1];
        for (int i = 1; i < fruits.length; i++)
            fruits[i] = Integer.parseInt(st.nextToken());
        
        // 자식 노드
        child = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            child.get(a).add(b);
            child.get(b).add(a);
        }
        
        // 1에서부터 가장 먼 정점
        int[] first = findFarthest(1);
        // first로부터 가장 먼 정점
        int[] second = findFarthest(first[1]);
        // second에 기록된 열매의 수와 first와 second 중 더 작은 번호를 출력
        System.out.println(second[0] + " " + Math.min(first[1], second[1]));
    }

    // start로부터 가장 먼 정점을 구한다.
    static int[] findFarthest(int start) {
        // 초기값
        int[] answer = new int[]{fruits[start], start};
        // start로부터 출발
        Queue<Seek> queue = new LinkedList<>();
        queue.offer(new Seek(start, fruits[start]));
        // 방문 표시
        boolean[] visited = new boolean[fruits.length];
        while (!queue.isEmpty()) {
            Seek current = queue.poll();
            visited[current.idx] = true;
            
            // 다음 정점
            for (int next : child.get(current.idx)) {
                // 미방문한 정점이라면
                if (!visited[next]) {
                    // 큐에 추가
                    queue.offer(new Seek(next, current.value + fruits[next]));
                    
                    // 현재 next까지 방문하며 먹은 열매가 가장 많은 경우
                    if (current.value + fruits[next] > answer[0]) {
                        answer[0] = current.value + fruits[next];
                        answer[1] = next;
                    } else if (current.value + fruits[next] == answer[0])
                        answer[1] = Math.min(answer[1], next);
                    // 열매의 수는 동일하나, 시작 정점의 번호가 더 작을 가능성이 있는 경우
                }
            }
        }
        // 결과 번환
        return answer;
    }
}