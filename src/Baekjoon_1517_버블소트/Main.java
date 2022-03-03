/*
 Author : Ruel
 Problem : Baekjoon 1517번 버블 소트
 Problem address : https://www.acmicpc.net/problem/1517
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1517_버블소트;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Num {
    int value;
    int loc;

    public Num(int value, int loc) {
        this.value = value;
        this.loc = loc;
    }
}

public class Main {
    static int[] segmentTree;
    static int[] lazy;

    public static void main(String[] args) throws IOException {
        // n개의 수가 주어질 때
        // 이를 버블 소트로 정렬한다. 이 때 일어나는 swap의 횟수는?
        // 버블 소트는 1과 2, 2와 3, 3과 4 ... 씩 두개의 값을 살펴보며 값을 정렬하는 방법이다
        // 예를 들어 1 4 3 2가 있다고 한다면,
        // '1 4' 3 2 -> 1 '4 3' 2 -> 1 3 '4 2' -> 1 3 2 4
        // 1 '3 2' 4 -> 1 2 3 4
        // 와 같이 앞에서부터 살펴보며 양쪽 중 큰 숫자를 가능한 맨 뒤로 끌고 올라간다.
        // 하지만 우리가 원하는 건 swap의 횟수이므로 꼭 순서대로 따라갈 필요는 없다
        // 위와 같이 1 4 3 2가 있다면, 가장 먼저 4를 꺼내, 4번째 위치로 보낸다. -> 이 때 필요한 이동의 횟수 2. 그리고 원래 4가 있던 2번째 위치 이후로의 숫자들은 자리가 하나씩 앞으로 떙겨지게 되었다.
        // 다음으로 큰 3을 꺼내 다음 위치인 3으로 보낸다 -> 현재 위치는 두번째 -> 3번째 -> 1번
        // 다음으로 큰 2를 꺼내 다음 위치인 2로 보낸다. -> 현재 위치 2 -> 2번째 -> 0번
        // 다음으로 큰 1을 꺼내 다음 위치인 1로 보낸다. -> 현재 위치 1 -> 1 -> 0번
        // 총 횟수 3번이 필요하다.
        // 따라서 이 문제를 우선순위큐와 세그먼트 트리를 이용하여 풀 것이다
        // 우선 순위큐는 수들을 내림차순으로 정렬하여 큰 순서대로 swap의 횟수를 셀 것이다
        // 세그먼트 트리는 원래 위치와 정렬되어진 위치 사이에 존재하는 수들을 앞으로 한칸씩 위치를 땡겨주는데 사용한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
        lazy = new int[segmentTree.length];     // 이전 문제 풀이를 통해 느리게 전파하는 세그먼트 트리를 사용한다면 참조 횟수를 줄여 시간복잡도를 줄일 수 있다는 점을 알았다. 이용하자.

        PriorityQueue<Num> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.value == o2.value)       // 값이 같다면 원래 위치가 뒤에 위치한 수(swap이 적게 일어나는 수)를 먼저 꺼낸다.
                return Integer.compare(o2.loc, o1.loc);
            return Integer.compare(o2.value, o1.value);     // 값이 다르다면 큰 순서대로 꺼낸다.
        });
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)     // 우선순위큐에 값과 원래 위치를 넣는다.
            priorityQueue.offer(new Num(Integer.parseInt(st.nextToken()), i + 1));

        int order = n;      // 순서는 가장 뒤의 순서인 n번부터 1번까지 맞춰나갈 것이다.
        long swapCount = 0;     // 그 때의 swap 횟수를 센다.
        while (!priorityQueue.isEmpty()) {
            Num current = priorityQueue.poll();     // 수를 꺼내고
            int currentLoc = current.loc + getValue(current.loc, 1, 1, n);      // current 보다 큰 수들의 이동으로 인해 옮겨진 위치를 보정해준다.
            int diff = order-- - currentLoc;        // 이동해야하는 거리.
            swapCount += diff;      // 해당 횟수만큼 swap 시켜주고.

            // 변경이 한번 이상 일어났다면, 원래 위치로부터 끝(n)까지의 위치를 모두 앞으로 하나씩 땡겨준다.
            // 원래 순서로 current 뒤에 current보다 작은 수가 있었다면, 다음에 위의 위치가 보정하는데 참고될 것이고
            // 큰 수들이었다면 이미 정렬이 끝난 시점이기 때문에 참조되지 않을 것이다.
            if (diff != 0)
                addValue(-1, current.loc, n, 1, 1, n);
        }
        System.out.println(swapCount);
    }

    // target의 위치 보정값을 가져온다.
    static int getValue(int target, int loc, int seekStart, int seekEnd) {
        if (lazy[loc] != 0)     // lazy값이 존재한다면 업데이트.
            updateLazy(loc, seekStart, seekEnd);

        if (seekStart == seekEnd)       // 말단 노드라면 세그먼트 트리에 존재하는 값을 가져온다.
            return segmentTree[loc] + lazy[loc];

        // 정확한 위치가 아니라면 이분탐색으로 위치를 찾아간다.
        int mid = (seekStart + seekEnd) / 2;
        if (target <= mid)
            return getValue(target, loc * 2, seekStart, mid);
        else
            return getValue(target, loc * 2 + 1, mid + 1, seekEnd);
    }

    // 보정값을 입력할 때 사용할 메소드
    // 보정값은 수가 이동한 '구간'으로 적용시켜야한다.
    static void addValue(int value, int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        if (targetStart == seekStart && targetEnd == seekEnd) {
            lazy[loc] += value;
            if (targetStart == targetEnd)       // 말단 노드라면 바로 lazy 값을 업데이트 시켜주자.
                updateLazy(loc, targetStart, targetEnd);
            return;
        }

        int mid = (seekStart + seekEnd) / 2;
        if (targetEnd <= mid)       // 왼쪽 자식 노드인 경우
            addValue(value, targetStart, targetEnd, loc * 2, seekStart, mid);
        else if (targetStart > mid)     // 오른쪽 자식 노드인 경우
            addValue(value, targetStart, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
        else {      // 구간이 자식 노드들에게  걸친 경우.
            addValue(value, targetStart, mid, loc * 2, seekStart, mid);
            addValue(value, mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
        }
    }

    static void updateLazy(int loc, int start, int end) {       // lazy 값 업데이트
        if (lazy[loc] != 0) {   // lazy값이 있다면
            segmentTree[loc] += lazy[loc];      // 세그먼트 트리에 반영하고
            if (start < end) {      // 현재 말단 노드가 아니라면, 자식 노드들에게 전파한다.
                lazy[loc * 2] += lazy[loc];
                lazy[loc * 2 + 1] += lazy[loc];
            }
            lazy[loc] = 0;      // lazy값 초기화.
        }
    }
}