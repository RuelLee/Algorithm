/*
 Author : Ruel
 Problem : Baekjoon 9240번 로버트 후드
 Problem address : https://www.acmicpc.net/problem/9240
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 로버트후드;

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
        // 이전에 풀었던 '볼록 껍질과 유사한 문제' 라는 것을 알 수 있다면 문제 끝
        // 최대 10만개의 점이 주어지기 때문에 일일이 계산해서는 안된다
        // 볼록 껍질을 구한 것과 마찬가지로 최외곽 점들만 모아준다
        // 그럼 점들의 개수가 상당히 압축될 것이다
        // 이렇게 압축된 점들로만 서로의 거리를 계산하여 최대 거리를 구하면 된다!
        Scanner sc = new Scanner(System.in);

        int c = sc.nextInt();

        Point criteria = null;
        Point[] arrows = new Point[c];
        for (int i = 0; i < c; i++) {
            arrows[i] = new Point(sc.nextInt(), sc.nextInt());
            if (criteria == null || arrows[i].y < criteria.y || (arrows[i].y == criteria.y && arrows[i].x < criteria.x))
                criteria = arrows[i];
        }

        Point forComparison = criteria;
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            int tanO1 = (o1.y - forComparison.y) * (o2.x - forComparison.x);        // o1의 탄젠트 값 계산
            int tanO2 = (o2.y - forComparison.y) * (o1.x - forComparison.x);        // o2의 탄젠트 값 계산(0으로 나뉘는 경우를 막기 위해 각각 (o1.x - forComparison.x) * (o2.x - forComparison.x)를 곱해줬다)

            if (tanO1 == tanO2) {
                if (o1.y == o2.y)
                    return Integer.compare(o1.x, o2.x);     // y가 같을 때는 x값이 작은 것을 먼저
                return Integer.compare(o1.y, o2.y);     // x 값이 같을 때는 y값이 작은 것을 먼저
            }
            return Integer.compare(tanO1, tanO2);       // 탄젠트 값의 대소 비교
        });
        for (Point p : arrows) {
            if (p == criteria)          // 기준을 빼고 우선순위큐에 담는다
                continue;
            priorityQueue.offer(p);
        }

        Stack<Point> stack = new Stack<>();
        stack.push(criteria);
        while (!priorityQueue.isEmpty()) {      // 우선순위큐가 빌 때까지
            while (stack.size() > 1) {          // stack 안에 최소 2개의 점이 들어있을 때
                Point second = stack.pop();

                if (isCCW(stack.peek(), second, priorityQueue.peek())) {        // 다음 점과의 방향을 계산하고, 180도를 넘어간다면 두번째 점을 지워준다
                    stack.push(second);
                    break;
                }
            }
            stack.push(priorityQueue.poll());       // 그리고선 우선순위큐에 있던 점을 담아준다.
        }

        double max = 0;
        while (!stack.isEmpty()) {
            Point arrow = stack.pop();      // 한 점을 꺼내
            for (Point another : stack)     // 나머지 점들과 거리 계산
                max = Math.max(max, calcDistance(arrow, another));
        }
        System.out.println(max);
    }

    static double calcDistance(Point a, Point b) {      // 두 점 사이의 거리 계산
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    static boolean isCCW(Point a, Point b, Point c) {       // 다음 점이 반시계 방향일 때만(== 180도 미만일 때만)
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) > 0;
    }
}