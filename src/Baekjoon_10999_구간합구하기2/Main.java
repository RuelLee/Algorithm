/*
 Author : Ruel
 Problem : Baekjoon 10999번 구간 합 구하기 2
 Problem address : https://www.acmicpc.net/problem/10999
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10999_구간합구하기2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static long[] nums;
    static long[] segmentTree;
    static long[] lazy;

    public static void main(String[] args) throws IOException {
        // n(최대 100만)개의 수가 주어지는데 이 수들의 값의 변경이 빈번히 일어나며, 우리는 어떤 부분의 합을 구하려고 한다
        // m은 변경 횟수, k는 구간합을 구하는 횟수.
        // 부분의 합을 구할 때는 누적합을 사용한다
        // 하지만 그 수들이 변경이 되는 경우라면 세그먼트 트리를 이용한다
        // 문제는 수의 개수가 커서 값의 변경이 이뤄질 때마다 일일이 갱신한다면 연산이 너무 많아진다
        // 느리게 갱신되는 세그먼트 트리(lazy propagation)를 사용한다
        // 상위 노드에 해당하는 범위의 수들을 모두 변경할 경우, 말단 노드까지 가지 말고, 해당 노드에 lazy값을 변경값을 준다
        // 자신의 상위 노드에는 자신의 노드 값과 lazy값 * 자신에게 속한 말단 노드의 개수 만큼을 반영해준다.
        // 예를 들어  4
        //        2      2
        //      1   1  1   1 과 같은 세그먼트 트리에, 1 ~ 2번까지 값에 3을 더한다면, 일반 세그먼트 트리의 경우, 루트 노드와 왼쪽 서브트리의 노드들 4개의 갱신이 이뤄져야하지만
        // 느리게 갱신되는 세그먼트 트리를 사용하면
        //           10
        //        2(+3)  2
        //      1   1  1   1 과 같이 루트 노드와 그 왼쪽 자식 노드의 lazy값 변경만 일어난다. 왼쪽 자식 노드에 lazy값이 3 주어지고,
        // 루트 노드는 2 + (3 * 2) + 2 = 10의 값이 들어간다.
        // 쉽게 말하자면 일정 범위에 대한 수치에 변화값을 해당 노드에 저장해두는 것이다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        nums = new long[n];     // 입력받은 값
        for (int i = 0; i < nums.length; i++)
            nums[i] = Long.parseLong(br.readLine());

        segmentTree = new long[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];        // 세그먼트 트리
        initiation(1, 1, n);        // 값 초기화
        lazy = new long[segmentTree.length];        // 각 노드에 해당하는 lazy값 저장 공간.

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if (a == 1) {       // a가 1일 경우
                // b ~ c구간에 대해 d값만큼 값의 변경이 이뤄진다.
                long d = Long.parseLong(st.nextToken());
                update(1, d, b, c, 1, n);
            } else      // 그렇지 않을 경우, b ~ c까지의 합을 구한다.
                sb.append(getSum(1, b, c, 1, n)).append("\n");
        }
        System.out.println(sb);
    }

    static void lazyUpdate(int loc, int start, int end) {
        if (lazy[loc] != 0) {       // loc에 lazy 값이 있다면
            segmentTree[loc] += lazy[loc] * (end - start + 1);      // 해당 세그먼트 트리에 lazy값을 반영하고
            if (start != end) {     // 자식 노드가 있을 경우, 자식 노드들에게 lazy값을 내려보낸다.
                lazy[loc * 2] += lazy[loc];
                lazy[loc * 2 + 1] += lazy[loc];
            }
            lazy[loc] = 0;      // loc의 lazy값은 초기화.
        }
    }

    static long getSum(int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {
        if (targetStart == seekStart && targetEnd == seekEnd)       // 일치하는 범위라면
            return segmentTree[loc] + (seekEnd - seekStart + 1) * lazy[loc];        // 세그먼트 트리값과 lazy값을 반영한 값을 반환.

        lazyUpdate(loc, seekStart, seekEnd);        // 그렇지 않다면, lazy값에 대한 업데이트(자식 노드들에게 내려보내는 일)를 하고
        int mid = (seekStart + seekEnd) / 2;
        if (targetEnd <= mid)       // 원하는 범위가 왼쪽 자식 노드라면
            return getSum(loc * 2, targetStart, targetEnd, seekStart, mid);
        else if (targetStart > mid)     // 원하는 범위가 오른쪽 자식 노드라면
            return getSum(loc * 2 + 1, targetStart, targetEnd, mid + 1, seekEnd);
        else {      // 원하는 범위가 자식 노드들에 걸쳐 있다면
            return getSum(loc * 2, targetStart, mid, seekStart, mid)
                    + getSum(loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
        }
    }


    static void update(int loc, long diffValue, int targetStart, int targetEnd, int seekStart, int seekEnd) {        // 세그먼트 트리를 업데이트
        lazyUpdate(loc, seekStart, seekEnd);        // lazy값이 있다면 자식노드로 내려보내고
        if (targetStart == seekStart && targetEnd == seekEnd) {     // 범위가 일치한다면
            lazy[loc] += diffValue;     // 더 이상 내려가지말고 lazy 값만 주자.
            if (targetStart == targetEnd)       // 만약 말단 노드라면, lazy 값을 세그먼트 트리에 반영.
                lazyUpdate(loc, seekStart, seekEnd);
            return;
        }

        int mid = (seekStart + seekEnd) / 2;
        if (targetEnd <= mid)       // 왼쪽 자식 노드의 범위라면
            update(loc * 2, diffValue, targetStart, targetEnd, seekStart, mid);
        else if (targetStart > mid)     // 오른쪽 자식 노드의 범위라면
            update(loc * 2 + 1, diffValue, targetStart, targetEnd, mid + 1, seekEnd);
        else {      // 자식들의 범위에 걸쳐있다면
            update(loc * 2, diffValue, targetStart, mid, seekStart, mid);
            update(loc * 2 + 1, diffValue, mid + 1, targetEnd, mid + 1, seekEnd);
        }
        // 자식 노드들의 값의 업데이트가 끝났으니, loc의 값을 업데이트한다
        // 각 자식노드들의 세그먼트 트리의 값과 lazy값을 반영한 값으로 업데이트 한다.
        segmentTree[loc] = segmentTree[loc * 2] + lazy[loc * 2] * (mid - seekStart + 1)
                + segmentTree[loc * 2 + 1] + lazy[loc * 2 + 1] * (seekEnd - (mid + 1) + 1);
    }

    static long initiation(int loc, int seekStart, int seekEnd) {       // 초기값으로 세그먼트 트리 세팅.
        if (seekStart == seekEnd)       // 말단 노드라면, 입력받았단 값을 넣고 리턴.
            return segmentTree[loc] = nums[seekStart - 1];

        int mid = (seekStart + seekEnd) / 2;        // 범위를 반으로 나눠
        // 왼쪽 자식 노드와 오른쪽 자식 노드의 합으로 세그먼트 트리에 저장한다.
        return segmentTree[loc] = initiation(loc * 2, seekStart, mid)
                + initiation(loc * 2 + 1, mid + 1, seekEnd);
    }
}