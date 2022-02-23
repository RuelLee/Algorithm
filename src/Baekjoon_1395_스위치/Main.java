/*
 Author : Ruel
 Problem : Baekjoon 1395번 스위치
 Problem address : https://www.acmicpc.net/problem/1395
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1395_스위치;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int[] lazy;

    public static void main(String[] args) throws IOException {
        // 어제 풀어본 느리게 갱신되는 세그먼트 트리 복습 겸 다시 한번 유사한 문제풀이!
        // n개의 스위치가 꺼져있는 상태로 주어진다
        // o, s, t 형태의 m개의 명령이 주어진다
        // o가 0이라면, s부터 t까지의 스위치들을 반전시키는 것이며
        // o가 1이라면 s부터 t까지의 스위치들 중 켜져있는 것을 찾는 것이다
        // 스위치의 개수가 최대 10만개, 명령의 개수 또한 최대 10만개이므로 일일이 계산했다가는 시간 초과가 난다
        // 느리게 갱신되는 세그먼트 트리르 사용한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
        lazy = new int[segmentTree.length];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            if (o == 0)
                switchReverse(1, s, t, 1, n);
            else
                sb.append(getNumSwitchesOn(1, s, t, 1, n)).append("\n");
        }
        System.out.println(sb);
    }

    static int getNumSwitchesOn(int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {  // 켜져있는 스위치의 개수를 구한다.
        // 일치하는 범위라면, lazy[loc] 값이 0이면 segmentTree[loc]의 값을 그대로 가져오면 되고
        // lazy[loc]의 값이 1이라면(해당하는 범위의 스위치들이 반전된 상태), 전체 스위치 개수에서 segmentTree[loc]의 값을 빼준 만큼이 켜져있는 스위치의 개수다
        if (targetStart == seekStart && targetEnd == seekEnd)
            return lazy[loc] == 0 ? segmentTree[loc] : (seekEnd - seekStart + 1) - segmentTree[loc];

        // 일치하는 범위가 아니라면, lazy값을 자식 노드들에게 갱신해주고,
        lazyUpdate(loc, seekStart, seekEnd);
        int mid = (seekStart + seekEnd) / 2;
        // 왼쪽 자식노드
        if (targetEnd <= mid)
            return getNumSwitchesOn(loc * 2, targetStart, targetEnd, seekStart, mid);
        // 오른쪽 자식 노드
        else if (targetStart > mid)
            return getNumSwitchesOn(loc * 2 + 1, targetStart, targetEnd, mid + 1, seekEnd);
        // 두 자식 노드에 범위가 걸쳐있는 경우.
        else {
            return getNumSwitchesOn(loc * 2, targetStart, mid, seekStart, mid)
                    + getNumSwitchesOn(loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
        }
    }

    static void lazyUpdate(int loc, int start, int end) {
        // lazy값이 존재한다면
        if (lazy[loc] != 0) {
            // 해당 loc의 segmentTree에 값을 반영해주고,
            segmentTree[loc] = (end - start + 1) - segmentTree[loc];
            // 자식 노드가 있다면
            if (start != end) {
                // lazy값을 물려준다.
                lazy[loc * 2] = (lazy[loc * 2] + lazy[loc]) % 2;
                lazy[loc * 2 + 1] = (lazy[loc * 2 + 1] + lazy[loc]) % 2;
            }
            // 현 위치의 lazy값은 초기화.
            lazy[loc] = 0;
        }
    }

    static void switchReverse(int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {        // 스위치 반전
        if (targetStart == seekStart && targetEnd == seekEnd) {     // 범위가 일치한다면
            // lazy 값에 변화를 준다
            // 반전이 되어있지 않은 상태 0이라면 1로 바꿔 반전시켜주고
            // 이미 1로 반전이 되어있는 상태라면 0을 만들어 원상태로 만들어준다
            lazy[loc] = (lazy[loc] + 1) % 2;
            return;
        }

        // 범위가 일치하지 않는다면, lazy값 갱신을 해주고,
        lazyUpdate(loc, seekStart, seekEnd);
        int mid = (seekStart + seekEnd) / 2;
        // 왼쪽 자식 노드
        if (targetEnd <= mid)
            switchReverse(loc * 2, targetStart, targetEnd, seekStart, mid);
        // 오른쪽 자식 노드
        else if (targetStart > mid)
            switchReverse(loc * 2 + 1, targetStart, targetEnd, mid + 1, seekEnd);
        // 원하는 범위가 자식노드들에 걸쳐있다면
        else {
            switchReverse(loc * 2, targetStart, mid, seekStart, mid);
            switchReverse(loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
        }
        // 자식 노드들에 대한 계산이 끝나면, loc에 대한 값의 갱신을 한다
        // 자식노드들의 segmentTree값과 lazy값을 따져 segmentTree[loc]의 값을 결정한다
        // 자식노드들에 lazy값이 없다면 그대로 segmentTree 값을 가져오고, 반전이 있다면 해당하는 범위에서 segmentTree값을 뺀 값을 가져와 합쳐준다.
        segmentTree[loc] = (lazy[loc * 2] == 0 ? segmentTree[loc * 2] : (mid - seekStart + 1) - segmentTree[loc * 2])
                + (lazy[loc * 2 + 1] == 0 ? segmentTree[loc * 2 + 1] : (seekEnd - (mid + 1) + 1) - segmentTree[loc * 2 + 1]);
    }
}