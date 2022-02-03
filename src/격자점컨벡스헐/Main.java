/*
 Author : Ruel
 Problem : Baekjoon 2699번 격자점 컨벡스헐
 Problem address : https://www.acmicpc.net/problem/2699
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 격자점컨벡스헐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static Point[] points;

    public static void main(String[] args) throws IOException {
        // 오랜만에 풀어본 볼록 껍질 문제
        // 볼록 껍질 -> 점들의 좌표가 주어질 때, 최외곽 점들을 이어 내각이 180미만인 다각형을 만드는 것.
        // 볼록 껍질에서 중요한 점 -> 어느 점을 기준으로 삼고 어느 순서대로 점들을 계산할 것인가
        // -> 새로 추가하는 점을 통해 내각이 180을 넘지 않는가
        // 먼저 순서는 기준이 되는 점을 삼고, tan값을 토대로 순서를 삼아 계산한다!
        // 추가하는 점이 만드는 각도는 CCW를 활용해 판단한다!
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            int n = Integer.parseInt(br.readLine());
            points = new Point[n];

            StringTokenizer st = null;
            Point criteria = null;
            for (int i = 0; i < n; i++) {
                if (i % 5 == 0) st = new StringTokenizer(br.readLine());
                points[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                // 기준점을 선정한다
                // 문제에서 가장 y값이 큰 점. 그러한 점이 여러개라면 그 중에서 x값이 가장 작은 점으로 시작한다 하였다.
                if (criteria == null || criteria.y < points[i].y || (criteria.y == points[i].y && criteria.x > points[i].x))
                    criteria = points[i];
            }

            Point finalCriteria = criteria;
            // 시작점은 가장 왼쪽 가장 위쪽 점이고, 순서는 시계방향으로 돌아야한다.
            // 따라서 우리는 4사분면에서의 tan값을 살펴봐야한다.
            // 4사분면에 시계방향으로 돌기 위해서는 tan값이 큰 값부터 -> 작은 값으로 살펴봐야한다.
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
                int tan1 = (points[o1].y - finalCriteria.y) * (points[o2].x - finalCriteria.x);
                int tan2 = (points[o2].y - finalCriteria.y) * (points[o1].x - finalCriteria.x);

                // 만약 tan 값이 같다면(두 점 중 하나가 기준점이거나, 기준점과 o1, o2가 일직선 상에 있을 때)
                if (tan1 == tan2) {
                    // 만약 y값이 같다면, x값이 작은 값부터
                    if (points[o1].y == points[o2].y)
                        return Integer.compare(points[o1].x, points[o2].x);
                    // 그렇지 않다면 y값이 큰 값부터.
                    return Integer.compare(points[o2].y, points[o1].y);
                }
                // tan 값이 서로 다르다면, tan값이 큰 값부터.
                return Integer.compare(tan2, tan1);
            });
            for (int i = 0; i < points.length; i++)
                priorityQueue.offer(i);

            Deque<Integer> deque = new LinkedList<>();
            while (!priorityQueue.isEmpty()) {
                while (deque.size() > 1) {
                    // 데크에 가장 앞에 있는 값이 두번째 점이 될 것이다.
                    int second = deque.pollFirst();

                    // 값을 빼낸 후에, 데크에 가장 앞에 있는 점이 첫번째, 방금 꺼낸 점이 두번째, 우선순위큐에 있는 점이 세번째.
                    // 이 점들로 ccw를 계산하여, 0보다 작은 값을 가질 때(시계 방향일 때) second를 데크 가장 앞에 다시 담아준다.
                    // 그렇지 않다면 그러한 두번째 점이 만족할 때까지 값을 비울 것이다.
                    if (getCCW(deque.peekFirst(), second, priorityQueue.peek()) < 0) {
                        deque.offerFirst(second);
                        break;
                    }
                }
                // 데크의 점이 2개 미만으로 있거나, 시계방향이 성립할 경우, 우선순위큐에 있는 점을 데크 가장 앞에 넣는다.
                deque.addFirst(priorityQueue.poll());
            }
            sb.append(deque.size()).append("\n");
            // 우리는 살펴보았던 순서대로 점들을 출력해야하므로, 데크의 가장 끝 값부터 값을 빼내준다.
            while (!deque.isEmpty())
                sb.append(points[deque.peekLast()].x).append(" ").append(points[deque.pollLast()].y).append("\n");
        }
        System.out.println(sb);
    }

    // 세 점이 이루는 방향이 시계인지, 반시계인지 판별해주는 CCW(벡터의 외적)
    static int getCCW(int a, int b, int c) {
        return (points[b].x - points[a].x) * (points[c].y - points[a].y) - (points[b].y - points[a].y) * (points[c].x - points[a].x);
    }
}