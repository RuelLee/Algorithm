/*
 Author : Ruel
 Problem : Baekjoon 5419번 북서풍
 Problem address : https://www.acmicpc.net/problem/5419
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5419_북서풍;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 강한 북서풍이 불고 있다.
        // 따라서 동쪽과 남쪽 사이의 방향으로만 이동이 가능하다.
        // t개의 테스트케이스 동안, n개의 섬이 주어질 때
        // 항해 가능한 섬의 쌍의 수를 구하는 프로그램을 작성하시오.
        //
        // 스위핑 + 세그먼트 트리 + 좌표 압축 문제
        // 왼쪽 위에서 오른쪽 아래 방향으로만 이동이 가능하다
        // 따라서 가장 맨 왼쪽 위의 지점에서부터 오른쪽, 아래로 하나씩 섬들을 살펴보며
        // 이전에 나왔던 섬들 중 자신에게 도달할 수 있는 섬들이 몇개인지 세어나간다.
        // 기본적으로 y가 감소하는 방향으로 나아가기 때문에 x에 관해서만 세그먼트 트리로
        // 현재 섬보다 작은 x값을 갖던 섬이 몇 개인지 세어가며 더해주면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 섬
            int n = Integer.parseInt(br.readLine());
            // 우선순위큐를 통해 섬들을 줄세운다.
            // y가 같다면 x에 대해서 오름차순
            // y가 다르다면 y에 대해 내림차순
            // 왼쪽 위에서 오른쪽 아래로 살펴본다.
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
                if (o1[1] == o2[1])
                    return Integer.compare(o1[0], o2[0]);
                return Integer.compare(o2[1], o1[1]);
            });
            // 값의 범위가 -10^9 ~ 10^9이며, n이 최대 75000개 주어지므로, 좌표 압축을 통해 값의 범위를 줄여준다.
            PriorityQueue<Integer> xCompress = new PriorityQueue<>();
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int[] island = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
                // 우선순위큐에 섬 담기.
                priorityQueue.offer(island);
                // 섬의 x좌표만 모아 압축하기.
                xCompress.offer(island[0]);
            }
            
            // 압축된 좌표를 표시할 해쉬맵.
            HashMap<Integer, Integer> xs = new HashMap<>();
            while (!xCompress.isEmpty()) {
                if (xs.containsKey(xCompress.peek()))
                    xCompress.poll();
                else
                    xs.put(xCompress.poll(), xs.size() + 1);
            }

            // 펜윅 트리를 통해 누적합을 구한다.
            int[] fenwickTree = new int[xs.size() + 1];
            // 세어진 섬 쌍의 개수.
            long count = 0;
            while (!priorityQueue.isEmpty()) {
                // 우선순위큐에서 해당하는 섬의 x좌표를 꺼내, 압축된 값을 가져온다.
                int x = xs.get(priorityQueue.poll()[0]);
                // 이전에 지나왔던 섬들 중 현재 섬으로 도달가능한 섬들은
                // x보다 같거나 작은 좌표값을 갖는 섬들이다.
                // 따라서 해당하는 섬들의 개수를 count에 더한다.
                count += countValues(x, fenwickTree);
                // 현재 섬을 펜윅트리에 추가한다.
                inputValue(x, fenwickTree);
            }
            // 최종 쌍의 개수를 출력.
            sb.append(count).append("\n");
        }
        System.out.print(sb);
    }

    // 펜윅 트리에 x좌표에 섬이 하나 있다는 사실을 기록한다.
    static void inputValue(int x, int[] fenwickTree) {
        while (x < fenwickTree.length) {
            fenwickTree[x] += 1;
            x += (x & -x);
        }
    }

    // x좌표 이하에 있는 섬들의 개수를 반환한다.
    static int countValues(int x, int[] fenwickTree) {
        int sum = 0;
        while (x > 0) {
            sum += fenwickTree[x];
            x -= (x & -x);
        }
        return sum;
    }
}