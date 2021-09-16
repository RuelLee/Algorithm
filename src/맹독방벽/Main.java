/*
 Author : Ruel
 Problem : Baekjoon 7420번 맹독 방벽
 Problem address : https://www.acmicpc.net/problem/7420
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 맹독방벽;

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
        // 건물들이 이루는 최대 크기의 다각형을 찾고
        // 그 다각형으로부터 l만큼 거리가 떨어진 벽의 길이를 찾으라는 문제!
        // 먼저 다각형을 그라함 스캔으로 구한다.
        // 주의할 점은 그 후, 각 다각형의 꼭지점에서의 벽은 꼭지점의 각도에 해당하는 원의 일부분 형태를 띄게 된다.
        // 하지만 이 역시 쉽게 풀리는 것이 다각형 내부 각도의 합은 360도이니, 결국 원의 일부분 형태를 모두 합치면 결국 원이 된다.
        // 그라함 스캔으로 건물들이 이루는 최대 크기의 다각형 꼭지점들을 찾고, 그 둘레를 구한 다음
        // 주어지는 2 * Pi * l을 해서 원의 둘레를 구한 다음 더해주면 끝이다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int l = sc.nextInt();

        Stack<Point> stack = new Stack<>();
        Point criteria = null;
        for (int i = 0; i < n; i++) {
            stack.push(new Point(sc.nextInt(), sc.nextInt()));
            if (criteria == null || criteria.y > stack.peek().y || (criteria.y == stack.peek().y && criteria.x > stack.peek().x))
                criteria = stack.peek();        // 기준점 찾기
        }

        Point forComparison = criteria;     // 우선순위큐의 기준이 될 기준점
        PriorityQueue<Point> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            long tanO1 = (long) (o1.y - forComparison.y) * (o2.x - forComparison.x);
            long tanO2 = (long) (o2.y - forComparison.y) * (o1.x - forComparison.x);

            if (tanO1 == tanO2) {
                if (o1.y == o2.y)
                    return Integer.compare(o1.x, o2.x);
                return Integer.compare(o1.y, o2.y);
            }
            return Long.compare(tanO1, tanO2);
        });

        while (!stack.isEmpty()) {  // 기준을 제외하고 전부 우선순위큐에 담아주자.
            if (stack.peek() == criteria)
                stack.pop();
            else
                priorityQueue.offer(stack.pop());
        }
        stack.push(criteria);
        while (!priorityQueue.isEmpty()) {  // 우선순위큐가 빌 때까지
            while (stack.size() > 1) {      // 스택의 내용물이이 2개 보다 많은 동안
                Point second = stack.pop();     // 스택에서 second를 뽑아내고

                if (isCCW(stack.peek(), second, priorityQueue.peek())) {        // stack.peek와 second, priorityQueue.Peek로 ccw를 계산하여 180 미만일 때만
                    stack.push(second);     // second를 다시 스택에 넣고 종료
                    break;
                }
            }
            stack.push(priorityQueue.poll());       // stack의 사이즈가 1이거나, stack의 위에서 두번째 -> stack의 첫번째 -> priorityQueue의 첫번째가 이루는 각도 180 미만일 때, stack에 우선순위큐의 내용물을 담아준다.
        }

        double distance = 0;
        Point first = stack.peek();
        while (stack.size() > 1)        // 스택의 내용물을 모두 뽑아가며 둘레 계산
            distance += calcDistance(stack.pop(), stack.peek());
        distance += calcDistance(stack.pop(), first);
        distance += 2 * Math.PI * l;        // 마지막으로 원의 둘레 길이 더하기
        System.out.println((int) Math.floor(distance + 0.5));   // 반올림한 결과값.
    }

    static double calcDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    static boolean isCCW(Point a, Point b, Point c) {
        long ccw = (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
        return ccw > 0;
    }
}