/*
 Author : Ruel
 Problem : Baekjoon 23900번 알고리즘 수업 - 선택 정렬 6
 Problem address : https://www.acmicpc.net/problem/23900
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_23900_알고리즘수업_선택정렬6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

class Order {
    int idx;
    int num;

    public Order(int idx, int num) {
        this.idx = idx;
        this.num = num;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 크기 n인 두 배열 a, b가 주어진다.
        // a를 선택 정렬하는 도중에 배열의 형태가 b랑 같게 된다면
        // 1을, 그렇지 않다면 0을 출력하라
        //
        // 우선순위큐, 정렬
        // 우선순위큐를 통해 a 배열을 큰 수를 뒤에 배치해가며 선택 정렬하며
        // 그 때마다 b와 같은 형태를 띄는지 비교한다.
        // '정렬' 문제이기 때문에 현재까지 정렬한 부분에 대해서는 값이 변하지 않는다.
        // 따라서 현재 정렬한 부분보다 오른쪽 한 칸에 대해서 b와 동일한지 검사한다면
        // 그 이후에 대해서는 이전에 계산한 결과를 통해 '정렬'되어있음을 알기 때문에 동일한지 살피지 않아도 된다.
        // 따라서 0 ~ 현재 정렬한 부분 -1 까지가 일치하는지 살펴보면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n 크기의 두 배열
        int n = Integer.parseInt(br.readLine());
        int[] a = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] b = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 우선순위큐에 a를 담는다.
        PriorityQueue<Order> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.num, o1.num));
        for (int i = 0; i < a.length; i++)
            priorityQueue.offer(new Order(i, a[i]));

        // 아직 정렬 되지 않은 가장 큰 인덱스
        int notSortEndIdx = a.length - 1;
        // a, b가 일치하는지 여부.
        // 초기 상태가 같을 수도 있으므로 확인
        boolean found = differentIdx(a, b, a.length - 1) == -1;
        // 아직 일치하는 시점을 찾지 못하고,
        // 전부 정렬되지 않았으며, 우선순위큐에 값이 담겨있는 동안 계속 반복한다.
        while (!found && notSortEndIdx > -1 && !priorityQueue.isEmpty()) {
            // 우선순위큐에서 값을 꺼낸다
            Order current = priorityQueue.poll();
            // 만약 꺼낸 값과 a 배열이 값 차이가 있다면 건너뛴다.
            // 위 경우는 정렬로 인해 값 변경이 생겨 쓸 수 없는 값이므로.
            if (a[current.idx] != current.num)
                continue;
                // 정렬하려는 값의 위치와 아직 정렬되지 않은 가장 큰 위치가 동일하다면
                // 값 변경이 필요없는 경우.
                // 정렬되었다고 치고 넘어간다.
            else if (notSortEndIdx == current.idx) {
                notSortEndIdx--;
                continue;
            }

            // 두 위치의 값을 변경
            a[current.idx] = a[notSortEndIdx];
            a[notSortEndIdx] = current.num;
            // 정렬되지 않은 가장 큰 인덱스 값 변경
            notSortEndIdx--;
            current.num = a[current.idx];
            // 우선 순위큐에 current.idx 위치의 값 다시 추가.
            priorityQueue.offer(current);

            // 두 배열의 값이 달라지기 시작하는 idx
            int diffIdx = differentIdx(a, b, notSortEndIdx);
            // 0보다 작다면 0번 위치까지 모두 일치하는 경우를 찾은 것.
            // found에 true 기록
            if (diffIdx < 0)
                found = true;
            // 만약 notSortEndIdx보다 큰 값이 돌아왔다면
            // 이는 정렬되어있는 부분에 대해서 불일치 하는 값이 존재.
            // 위 경우에는 a와 b가 같아질 일이 없다.
            // 그대로 반복문 종료
            else if (diffIdx > notSortEndIdx)
                break;
        }
        // 일치하는 때는 찾았다면 1 아니라면 0을 출력한다.
        System.out.println(found ? 1 : 0);
    }

    // startIdx부터 a, b 배열이 일치하는지 확인한다.
    static int differentIdx(int[] a, int[] b, int startIdx) {
        // startIdx + 1 위치는 방금 정렬한 부분.
        // 위에 대해서 a와 b배열의 값이 일치하는지 확인한다.
        // 일치하지 않는다면 정렬되서 더 이상 값 변경이 없는데도 값이 일치하지 않기 때문에
        // a와 b배열이 같아지는 경우는 존재하지 않는다.
        if (startIdx + 1 < a.length && a[startIdx + 1] != b[startIdx + 1])
            return startIdx + 1;
        
        // startIdx ~ 0까지의 값들이 일치하는지 확인.
        // 일치하지 않는다면 해당 위치
        for (int i = startIdx; i >= 0; i--) {
            if (a[i] != b[i])
                return i;
        }
        // 일치한다면 -1을 반환
        return -1;
    }
}