/*
 Author : Ruel
 Problem : Baekjoon 1708번 볼록 껍질
 Problem address : https://www.acmicpc.net/problem/1708
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 볼록껍질;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) {
        // 기하학 문제
        // 점 집합이 주어질 때, 다른 점들을 내부에 모두 포함하는 가장 큰 볼록 다각형을 구하는 문제
        // 그라함 스캔이라는 방법을 이용한다!
        // y가 가장 작은(y가 같다면 x가 더 작은) 기준점을 잡는다.
        // 해당 기준 점으로부터 반시계방향으로 점들을 정렬한다(tan 값을 기준으로 정렬하면 된다!)
        // stack에 첫번째와 두번째 점을 담는다
        // 그 후 stack에서 두번째 점을 꺼내고, 첫번째 점, 두번째 점, 세번째 점으로 ccw를 계산한다
        // 양수 값이라면, 두번째, 세번째 값을 모두 스택에 담고, 네번째 점으로 넘어간다
        // 음수 값이라면 두번째 값을 버린다.
        // -> 스택 안에 값이 하나밖에 없을 경우에는, 세번째 값을 넣고 위 과정 반복.
        // -> 스택 안에 값이 더 있을 경우에는, 위 계산에서 첫번째 였던 값이 두번째로 올라오고, 첫번째 이전에 있던 값이 첫번째로 올라온다. ( 세번째의 점과 어울리지 않는 두번째 점을 지속적으로 버리기 위함)
        // 최종적으로 남는 점들이 볼록 다각형을 이루는 점들의 집합.

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        Point[] points = new Point[n];

        Point criteria = null;
        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());

            if (criteria == null || criteria.y > points[i].y || (criteria.y == points[i].y && criteria.x > points[i].x))        // y값이 가장 작으면서도 x값이 적은 값을 기준으로 삼는다.
                criteria = points[i];
        }

        Point usedByComparator = criteria;
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // 사실 tanO1은 (o1.y - criteria.y) / (o1.x - criteria.x)
            // tanO2는 (o2.y - criteria.y) / (o2.x - criteria.x) 여야하지만
            // 나눗셈일 경우, 분모가 0이 될 위험이 있기 때문에, 양쪽에 서로의 분모를 곱해줘서 해당 경우를 방지해준다.
            long tanO1 = (long) (o1.y - usedByComparator.y) * (o2.x - usedByComparator.x);
            long tanO2 = (long) (o2.y - usedByComparator.y) * (o1.x - usedByComparator.x);

            if (tanO1 == tanO2) {       // 값이 같을 경우
                if (o1.y == o2.y)
                    return Integer.compare(o1.x, o2.x);     // x값이 더 작은 순서대로
                return Integer.compare(o1.y, o2.y);     // y값이 더 작은 순서대로 정렬한다
            }
            return Long.compare(tanO1, tanO2);      // 다를 경우에는 tan 값이 적은 순서대로
        });
        for (Point p : points) {        // 우선순위큐에 기준을 제외하고 담아주자.
            if (p == criteria)
                continue;
            priorityQueue.offer(p);
        }

        Stack<Point> stack = new Stack<>();
        stack.push(criteria);       // 스택에 기준을 담아두고
        while (!priorityQueue.isEmpty()) {      // 우선순위큐가 빌 때까지
            while (stack.size() > 1) {      // 스택의 값이 2개 이상일 때만
                Point second = stack.pop();     // 두번째 값을 빼오고

                if (calcCCW(stack.peek(), second, priorityQueue.peek()) > 0) {      // ccw가 양수일 때만
                    stack.push(second);     // 두번째 값을 다시 스택에 값을 담고, break를 걸어 바로 우선순위큐의 값 또한 스택에 담아주자
                    break;
                }
            }
            // 우선순위큐의 값이 담기는 시점은 위 ccw를 통과하거나, stack의 값이 하나만 있을 경우.
            stack.push(priorityQueue.poll());
        }
        // 최종적으로 남아있는 stack의 점들이 볼록 다각형을 이루는 집합.
        System.out.println(stack.size());
    }

    static int calcCCW(Point a, Point b, Point c) {
        long ccw = (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
        if (ccw > 0)
            return 1;
        return -1;
    }
}