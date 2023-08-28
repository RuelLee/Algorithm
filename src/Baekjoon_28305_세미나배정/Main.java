/*
 Author : Ruel
 Problem : Baekjoon 28305번 세미나 배정
 Problem address : https://www.acmicpc.net/problem/28305
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28305_세미나배정;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int t;
    static int[] seminars;

    public static void main(String[] args) throws IOException {
        // n개의 세미나가 각각 t일간 개최된다.
        // 각 세미나는 외부 전문가가 특강을 진행하는 a일에는 반드시 진행되어야한다.
        // 세미나 실을 최소로 빌리고자할 때, 그 개수는?
        //
        // 이분 탐색, 그리디 문제
        // 각각의 세미나가 t일이라는 범위를 갖고서 겹치는 날을 최소화해야한다.
        // 따라서 이분 탐색을 통해 빌려야하는 세미나실의 개수를 찾는다.
        // 또한 해당 세미나실의 개수로 전체 세미나들을 진행할 수 있는지 알아보기 위해서는
        // 그리디를 통해 각각의 세미나를 배치할 수 있는 가장 이른 날짜에 배치해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 전체 세미나의 수
        int n = Integer.parseInt(st.nextToken());
        // 진행되는 일
        t = Integer.parseInt(st.nextToken());

        // 각각의 세미나들을
        // 외부 인사가 참석하는 날짜에 대해 오름차순 정렬
        seminars = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(seminars);

        // 이분 탐색을 통해 최소로 빌려야하는 세미나실의 개수를 찾는다.
        int start = 1;
        int end = 200000;
        while (start < end) {
            int mid = (start + end) / 2;
            if (possible(mid))
                end = mid;
            else
                start = mid + 1;
        }

        System.out.println(start);
    }

    // room의 개수로 전체 세미나를 진행할 수 있는지 찾는다.
    static boolean possible(int room) {
        // 각각의 세미나가 끝나는 날을 큐로 관리
        Queue<Integer> endDays = new LinkedList<>();
        // room보다 이하의 세미나들을 일단 큐에 끝나는 날짜들을 담는다.
        // 해당 날짜가 포함되며, 가장 빠르게 세미나를 마치는 방법은
        // a - t + 1 일부터 a일까지 진행하는 것이다.
        for (int i = 0; i < Math.min(room, seminars.length); i++)
            endDays.offer(Math.max(t, seminars[i]));

        // room 개수보다 더 많은 세미나가 진행되고 있을 가능성이 있다.
        for (int i = room; i < seminars.length; i++) {
            // a - t + 1일보다 이른 날짜에 끝나는 세미나들을 살펴볼 필요가 없다.
            while (!endDays.isEmpty() &&
                    endDays.peek() < seminars[i] - t + 1)
                endDays.poll();

            // 만약 i번째 세미나를 추가하더라도 room의 개수를 초과하지 않는다면
            // 그냥 담는다.
            if (endDays.size() < room)
                endDays.offer(seminars[i]);
            else {
                // 진행되는 세미나의 수가 room과 같아질 때까지 큐에서 제거한다.
                while (endDays.size() > room)
                    endDays.poll();
                
                // 가장 이른 날짜에 끝나는 세미나가
                // i번째 세미나의 외부 전문가가 참석하는 a일보다 같거나 더 늦은 날짜에 끝난다면
                // room의 개수로는 전체 세미나 진행이 불가능하다.
                // false 리턴
                if (endDays.peek() >= seminars[i])
                    return false;

                // 그렇지 않고, a일 이전에 세미나가 종료가 된다면
                // 해당 세미나가 종료되고 바로 다음 날부터 i번째 세미나를 진행한다.
                endDays.offer(endDays.poll() + t);
            }
        }

        // 위 과정 동안 false를 통해 종료되지 않았다면
        // room의 개수로 전체 세미나들을 진행이 가능한 경우
        // true 반환.
        return true;
    }
}