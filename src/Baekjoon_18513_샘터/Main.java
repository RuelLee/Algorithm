/*
 Author : Ruel
 Problem : Baekjoon 18513번 샘터
 Problem address : https://www.acmicpc.net/problem/18513
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18513_샘터;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Point {
    int loc;
    int diff;
    int delta;

    public Point(int loc, int diff, int delta) {
        this.loc = loc;
        this.diff = diff;
        this.delta = delta;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 샘터가 존재하며, k개의 집을 지으려고 한다.
        // 각 집은 샘터에서 떨어진 만큼의 위치가 불행도이다.
        // k채 집의 불행도 합이 최소가 되도록 집을 지으려할 때 그 합은?
        //
        // 너비 우선 탐색 문제.
        // 샘터의 위치가 -100,000_000 ~ 100,000,000까지 주어지므로 해쉬셋을 통한 방문체크를 진행하였다.
        // 각 샘터에 대해 좌우방향으로 집을 지어나가되, 샘터에서 떨어진 위치가 적은 순으로 계산해나간다.
        // 다른 샘터나, 집을 만나면 해당 방향으로 탐색은 종료한다.
        // k채의 집을 지을때까지 진행한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 주어지는 우물의 위치.
        int[] wells = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 우물의 위치엔 집을 지을 수 없으므로 방문체크해둔다.
        HashSet<Integer> checked = new HashSet<>();
        for (int well : wells)
            checked.add(well);

        // 우물의 좌우에 집을 지을 수 있는지 살펴보고 거리 차이에 대해 순차적으로 접근한다.
        Queue<Point> queue = new LinkedList<>();
        for (int well : wells) {
            if (!checked.contains(well - 1))
                queue.offer(new Point(well - 1, -1, -1));
            if (!checked.contains(well + 1))
                queue.offer(new Point(well + 1, +1, +1));
        }

        // 총 불행도의 합
        long sum = 0;
        // 지은 집의 수
        int houses = 0;
        while (!queue.isEmpty() && houses < k) {
            Point current = queue.poll();
            // 우물이거나 이미 집을 지었다면 건너뛴다.
            if (checked.contains(current.loc))
                continue;

            // 집의 개수 증가
            houses++;
            // 방문 체크
            checked.add(current.loc);
            // 불행도의 합 증가.
            sum += Math.abs(current.diff);

            // 진행하던 방향대로 한 칸 탐색한다.
            int next = current.loc + current.delta;
            // 우물이나 집이 지어진 것이 아니라면
            // 우선순위큐에 추가하여 다음번에 탐색한다.
            if (!checked.contains(next))
                queue.offer(new Point(next, current.diff + current.delta, current.delta));
        }

        // 전체 불행도의 합 출력
        System.out.println(sum);
    }
}