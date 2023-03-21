/*
 Author : Ruel
 Problem : Baekjoon 9694번 무엇을 아느냐가 아니라 누구를 아느냐가 문제다
 Problem address : https://www.acmicpc.net/problem/9694
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_9694_무엇을아느냐가아니라누구를아느냐가문제다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Relationship {
    int friend;
    int intimacy;

    public Relationship(int friend, int intimacy) {
        this.friend = friend;
        this.intimacy = intimacy;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 정치인들의 친밀도를 조사하여 다음 4단계로 나누었다.
        // 1 최측근, 2 측근, 3 비즈니스 관계, 4 지인
        // n개의 관계와 m명의 사람이 주어진다.
        // 0번 사람은 m번 사람을 소개받길 원하며, 그 때 거치는 인맥간 친밀도의 합을 최소화하고 싶다.
        // 그 때 0번부터 m-1까지 거치는 인맥들을 출력하라
        //
        // dijkstra 문제
        // 다른 말로 표현하였지만 결국 다익스트라 문제
        // 0번부터 m - 1까지 도달하는 최소 거리를 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            // m명이 주어지므로 큰 값으로는 (m + 1 ) * 4로 설정하여 오버플로우가 나지 않도록 한다.
            int MAX = (m + 1) * 4;
            
            // m명 간의 관계를 나타내는 인접행렬
            int[][] adjMatrix = new int[m][m];
            for (int[] am : adjMatrix)
                Arrays.fill(am, MAX);
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());
                adjMatrix[x][y] = adjMatrix[y][x] = z;
            }
            
            // dijkstra
            // 0번부터 각 번호에 이르기까지의 최소 거리
            int[] minDistances = new int[m];
            Arrays.fill(minDistances, MAX);
            minDistances[0] = 0;
            // 방문 체크
            boolean[] visited = new boolean[m];
            PriorityQueue<Relationship> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(value -> value.intimacy));
            priorityQueue.offer(new Relationship(0, 0));
            // 0번이 해당 사람에게 도달하는 최소 친밀도 경로에서
            // 연결되는 직전 사람을 표시한다.
            int[] friends = new int[m];
            Arrays.fill(friends, -1);
            while (!priorityQueue.isEmpty()) {
                Relationship current = priorityQueue.poll();
                // m - 1번 사람에게 도달할 경우 종료
                if (current.friend == m - 1)
                    break;
                else if (visited[current.friend])       // 방문했다면(더 적은 친밀도 합으로) 건너뛰기
                    continue;

                // current.friend의 인맥들을 살펴보고
                // 해당하는 인맥에 이르는 친밀도 합이 최소인지 살펴본다.
                for (int i = 0; i < adjMatrix[current.friend].length; i++) {
                    // i와 current.friend가 동일인이거나 최대값(인맥이 아닌 경우) 건너뛰기
                    if (i == current.friend || adjMatrix[current.friend][i] == MAX)
                        continue;
                    
                    // i까지 이르는 최소 친밀도 합을 갱신한다면
                    if (minDistances[i] > minDistances[current.friend] + adjMatrix[current.friend][i]) {
                        // 값 갱신 후
                        minDistances[i] = minDistances[current.friend] + adjMatrix[current.friend][i];
                        // i에 연결되는 친구에 current.friend 기록
                        friends[i] = current.friend;
                        // 우선순위큐에 담아 다음에 i에 대해 탐색한다.
                        priorityQueue.offer(new Relationship(i, minDistances[i]));
                    }
                }
                // 방문 체크
                visited[current.friend] = true;
            }
            
            // dijkstra 알고리즘이 끝난 후
            sb.append("Case #").append(t + 1).append(": ");
            // m - 1에 이르는 친구가 초기값(없다)면
            // -1 출력
            if (friends[m - 1] == -1)
                sb.append(-1);
            else {      // 경로가 존재한다면
                // 스택을 통해 m - 1번부터 0번까지 거꾸로 거슬러 올라간다.
                Stack<Integer> stack = new Stack<>();
                stack.push(m - 1);
                while (friends[stack.peek()] != -1)
                    stack.push(friends[stack.peek()]);
                // 그 후 스택에서 꺼내며 답안 기록
                while (!stack.isEmpty())
                    sb.append(stack.pop()).append(" ");
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("\n");
        }
        // 전체 답안 출력.
        System.out.println(sb);
    }
}