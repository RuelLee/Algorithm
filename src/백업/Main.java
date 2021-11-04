/*
 Author : Ruel
 Problem : Baekjoon 1150번 백업
 Problem address : https://www.acmicpc.net/problem/1150
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 백업;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    static final int MAX = 1000000001;

    public static void main(String[] args) {
        // 며칠에 걸쳐서 풀었는지 모르겠다
        // 정말 어려웠던 그리디 문제
        // k개로 주어지는 케이블을 통해 최대한 적은 거리로 회사 쌍을 만드는 문제.
        // 회사 수가 최대 10만개가 주어지므로 완전탐색으로는 풀 수 없다.
        // 그렇다면 거리가 가까운 순으로 회사를 짝지어 가는 방법을 생각할 수 있는데
        // 예를 들어 a b c d 라는 회사가 있고, b-c 간의 거리가 가장 짧다고 하면
        // a b-c d로 이루어진다. 이는 케이블이 하나일 경우에는 성립하지만 케이블이 2개라면 성립하지 않는 경우이다
        // a-b, c-d 형태로 이어지는 것이 옳을 것이다. 그렇다면 위 경우에서 어떻게 아래 경우로 넘어갈지 생각해야한다.
        // 아래의 길이는 a-b, c-d의 길이의 합이고, 만약 아래 경우를 택할 경우, 이미 반영된 b-c의 길이를 빼주는 방법으로 진행해야한다
        // 1. 우선순위큐로 케이블의 길이가 짧은 순으로 택한다
        // 2. 대신 이번 케이블 대신 양옆의 케이블이 선택되는 경우를 우선순위큐에 담아준다.(이 때의 길이는 (a-b) + (c-d) - (b-c))
        // 순으로 진행한다면 각 회사들을 최소의 길이의 케이블들로 연결할 수 있다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] rightCableIdx = new int[n + 1];       // 자신의 오른쪽에 있는 케이블의 인덱스를 담아줄 공간
        int[] leftCableIdx = new int[n + 1];        // 자신의 왼쪽에 있는 케이블의 인덱스를 담아줄 공간
        int[] distance = new int[n + 1];        // idx에 해당하는 케이블의 길이를 담아줄 공간
        distance[0] = distance[n] = MAX;        // ArrayIndexOutOfBounds exception 을 막기 위해 양옆에 하나의 공간을 더 설정해주되 길이를 최대값보다 크게 해 선택되지 않도록 한다.
        int pointA = sc.nextInt();              // 첫 회사의 위치.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(distance[o1], distance[o2]));
        for (int i = 1; i < n; i++) {
            int pointB = sc.nextInt();
            distance[i] = pointB - pointA;      // pointB ~ pointA의 길이를 갖는 i번 케이블이다
            rightCableIdx[i - 1] = i;       // i-1 idx의 오른쪽 케이블은 i이고
            leftCableIdx[i] = i - 1;        // i 케이블의 왼쪽 케이블은 i-1이다.
            priorityQueue.offer(i);
            pointA = pointB;            // pointA에 pointB를 담아, 다음 회사와 이번 회사 간의 길이를 케이블로 담도록 하자.
        }

        boolean[] connected = new boolean[n + 1];       // 선택 가능한 케이블인지 나타낼 visited 배열.
        int count = 0;      // 사용된 케이블의 개수를 센다
        int sum = 0;        // 길이를 잰다
        while (!priorityQueue.isEmpty() && count < k) {     // 우선순위큐가 비어있지 않고, k이하의 케이블을 사용했을 때만
            while (!priorityQueue.isEmpty() && connected[priorityQueue.peek()])         // 만약 선택이 불가능한 케이블이라면 우선순위큐에서 제거
                priorityQueue.poll();
            if (priorityQueue.isEmpty())
                break;

            int currentIdx = priorityQueue.poll();          // 선택된 케이블의 인덱스
            sum += distance[currentIdx];            // 선택된 케이블의 길이를 더해주고
            // b-c가 연결이 되었다면, a-b, c-d 케이블은 선택되서는 안된다. 따라서 연결될 수 없게 표시해준다.
            // 대신 위의 경우는 현재 인덱스가 그대로 이어받아줄 것이다.
            connected[leftCableIdx[currentIdx]] = connected[rightCableIdx[currentIdx]] = true;
            // b-c가 선택되었으므로, a-b, c-d 경우의 길이를 현재 인덱스에 담는다.
            distance[currentIdx] = distance[leftCableIdx[currentIdx]] + distance[rightCableIdx[currentIdx]] - distance[currentIdx];
            // 현재 인덱스의 왼쪽 케이블은 왼쪽 케이블이 갖고 있는 왼쪽 케이블이 될 것이고
            leftCableIdx[currentIdx] = leftCableIdx[leftCableIdx[currentIdx]];
            // 현재 인덱스의 오른쪽 케이블은 오른쪽 케이블이 갖고 있는 오른쪽 케이블이 될 것이다.
            rightCableIdx[currentIdx] = rightCableIdx[rightCableIdx[currentIdx]];
            // 오른쪽 케이블의 왼쪽 케이블은 현재 케이블이 되고
            leftCableIdx[rightCableIdx[currentIdx]] = currentIdx;
            // 왼쪽 케이블의 오른쪽 케이블은 현재 케이블이 된다.
            rightCableIdx[leftCableIdx[currentIdx]] = currentIdx;
            // 바뀐 새 케이블을 우선순위큐에 담아준다.
            priorityQueue.offer(currentIdx);
            // 사용된 케이블 수를 늘려준다.
            count++;
        }
        System.out.println(sum);
    }
}