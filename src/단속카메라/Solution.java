package 단속카메라;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Car {
    int start;
    int end;

    public Car(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Solution {
    public static void main(String[] args) {
        // 최대 중복을 맞출 필요는 없다.
        // 중복이 가능하다면 가능한 것대로, 그렇지 않다면 그냥 안한대로 냅둬도 최소 감시카메라의 개수는 동일.
        // 시작 시간 순으로 정렬하여, 하나씩 뒤로 중복 구간을 체크하여 중복 구간만 남기자.
        // 더 이상 중복되지 않는 구간은 감시 카메라 1대 설치 확정.

        int[][] routes = {{-20, 15}, {-14, -5}, {-18, -13}, {-5, -3}};

        Queue<Car> carQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.start, o2.start));

        for (int[] a : routes)
            carQueue.add(new Car(a[0], a[1]));

        Queue<Car> answer = new LinkedList<>();

        while (!carQueue.isEmpty()) {
            if (carQueue.size() == 1) {
                answer.add(carQueue.poll());
                break;
            }

            Car a = carQueue.poll();
            Car b = carQueue.poll();

            if (a.end >= b.start) { // 중복 구간이 있다면
                int smallerEnd = a.end < b.end ? a.end : b.end;
                carQueue.add(new Car(b.start, smallerEnd)); // 중복 구간만 다시 carQueue에 담아, 뒷 차와 비교하자
            } else {    // 없다면 a는 독립된 구간으로 answer에 담고, b는 다 시 뒷차와 비교하기 위해 carQueue에 담자.
                answer.add(a);
                carQueue.add(b);
            }
        }
        System.out.println(answer.size());
    }
}