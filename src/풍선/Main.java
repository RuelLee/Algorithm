/*
 Author : Ruel
 Problem : Baejoon 4716번 풍선
 Problem address : https://www.acmicpc.net/problem/4716
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 풍선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Team {
    int people;
    int a;
    int b;

    public Team(int people, int a, int b) {
        this.people = people;
        this.a = a;
        this.b = b;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 각 팀마다 풍선을 나눠주는 곳까지의 거리가 주어지고
        // 풍선 재고량이 주어질 때, 총 인원의 이동거리가 최소가 되는 분배 방법을 물었다
        // 정렬이 중요한데, 여기서 정렬 기준은 a, b 풍선을 나눠주는 두 곳의 거리 차이다.
        // 거리 차가 클수록 자신이 가까운 풍선 배급소로 가는 것이 유리하다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (true) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (n == 0)
                break;

            // 우선순위큐로 두 배급소로 가는 거리의 차가 큰 순으로 정렬해주었다.
            PriorityQueue<Team> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(Math.abs(o2.a - o2.b), Math.abs(o1.a - o1.b)));
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                priorityQueue.offer(new Team(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
            }

            int sum = 0;
            while (!priorityQueue.isEmpty()) {
                Team current = priorityQueue.poll();
                if (current.a < current.b) {        // a가 더 가깝다면
                    if (a >= current.people) {     // 재고와 인원을 비교하여 분배한다.
                        a -= current.people;
                        sum += current.people * current.a;
                    } else {            // a 배급소에서 전부 처리 못한다면 b 배급소로 나머지 인원을 넘긴다.
                        current.people -= a;
                        sum += a * current.a;
                        a = 0;
                        b -= current.people;
                        sum += current.people * current.b;
                    }
                } else {            // b가 더 가깝다면
                    if (b >= current.people) {      // b 배급소에서 전부 처리 가능하다면 처리.
                        b -= current.people;
                        sum += current.people * current.b;
                    } else {            // 아니라면 잔여 인원을 a 배급소로 보낸다
                        current.people -= b;
                        sum += b * current.b;
                        b = 0;
                        a -= current.people;
                        sum += current.people * current.a;
                    }
                }
            }
            sb.append(sum).append("\n");        // 총 이동거리의 합.
        }
        System.out.println(sb);
    }
}