/*
 Author : Ruel
 Problem : Jungol 4001번 균형 잡힌 사진
 Problem address : https://jungol.co.kr/problem/4001
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4001_균형잡힌사진;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // 일렬로 늘어선 n마리의 소의 키가 주어진다.
        // 자신보다 왼쪽에 있으며 더 큰 소의 수를 Li, 오른쪽에 있으며 더 큰 소를 Ri라 할 때
        // max(Li, Ri) > 2 * min(Li, Ri)인 경우 불균형해보인다고 한다.
        // 불균형해보이는 소의 수를 세라
        //
        // 좌표 압축, 세그먼트 트리 문제
        // 값의 범위가 10억까지 주어지지만, n은 최대 10만으로 주어지므로 좌표 압축을 통해 10만까지로 줄일 수 있다.
        // 이를 세그먼트 트리를 통해, 한 방향으로 값을 추가해나가며 자신보다 큰 소의 수를 세어나간다.
        // 그리고 마지막에 두 방향에서 센 값들을 비교한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n마리의 소
        int n = Integer.parseInt(br.readLine());
        int[] cows = new int[n];
        // 우선순위큐로 오름차순으로 정리
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> cows[o]));
        for (int i = 0; i < cows.length; i++) {
            cows[i] = Integer.parseInt(br.readLine());
            priorityQueue.offer(i);
        }
        // 좌표 압축
        int cnt = 1;
        while (!priorityQueue.isEmpty())
            cows[priorityQueue.poll()] = cnt++;

        // 펜윅 트리
        fenwickTree = new int[n + 1];
        // 한 방향으로 살펴보며
        int[][] counts = new int[n][2];
        for (int i = 0; i < cows.length; i++) {
            // 자신보다 큰 소의 수
            counts[i][0] = getSum(n) - getSum(cows[i]);
            // 자신을 펜윅 트리에 추가
            addValue(cows[i]);
        }

        // 역방향
        Arrays.fill(fenwickTree, 0);
        int ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            counts[i][1] = getSum(n) - getSum(cows[i]);
            // 만약 불균형한 소라면 카운트
            if (counts[i][0] > counts[i][1] * 2 || counts[i][0] * 2 < counts[i][1])
                ans++;
            addValue(cows[i]);
        }
        // 답 출력
        System.out.println(ans);
    }

    // idx보다 같거나 작은 수의 개수를 센다.
    static int getSum(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= idx & -idx;
        }
        return sum;
    }

    // idx 값을 하나 추가한다.
    static void addValue(int idx) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx]++;
            idx += idx & -idx;
        }
    }
}