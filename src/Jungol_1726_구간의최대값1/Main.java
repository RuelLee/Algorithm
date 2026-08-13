/*
 Author : Ruel
 Problem : Jungol 1726번 구간의 최대값1
 Problem address : https://jungol.co.kr/problem/1726
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1726_구간의최대값1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 수열의 원소 n, 구간의 개수 q가 주어진다.
        // 그리고 크기가 n인 수열이 주어진다.
        // 각 구간에 최댓값을 구하라
        //
        // 세그먼트 트리 문제
        // 세그먼트 트리를 만들고, 부모 노드에 두 자식 노드 중 큰 값을 기록해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 수열의 크기 n, 구간의 개수 q
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 세그먼트 트리의 크기
        // n보다 같거나 큰 2의 최소 제곱수를 찾고
        // 그 2배만큼의 크기가 필요하다
        int pow = 1;
        while (pow < n)
            pow *= 2;
        pow *= 2;
        segmentTree = new int[pow];

        // 수열
        int[] array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = Integer.parseInt(br.readLine());
        // 세그먼트 트리 세팅
        inputValue(1, 0, n - 1, array);

        // q개의 쿼리 처리
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(getMaxValue(1, 1, n, a, b)).append("\n");
        }
        System.out.print(sb);
    }

    // 현재 세그먼트 트리의 주소 loc, 현재 범위 cStart ~ cEnd, 찾고자하는 범위 sStart ~ sEnd
    static int getMaxValue(int loc, int cStart, int cEnd, int sStart, int sEnd) {
        // 범위가 일치하는 경우 값 반환
        if (cStart == sStart && cEnd == sEnd)
            return segmentTree[loc];

        // 구간을 반 쪼개
        int mid = (cStart + cEnd) / 2;
        // 왼쪽 구간에 속하는 경우
        if (sEnd <= mid)
            return getMaxValue(loc * 2, cStart, mid, sStart, sEnd);
        // 오른쪽 구간에 속하는 경우
        else if (sStart > mid)
            return getMaxValue(loc * 2 + 1, mid + 1, cEnd, sStart, sEnd);
        else        // 구 구간에 걸치는 경우
            return Math.max(getMaxValue(loc * 2, cStart, mid, sStart, mid),
                    getMaxValue(loc * 2 + 1, mid + 1, cEnd, mid + 1, sEnd));
    }

    // 세그먼트 트리 세팅
    static void inputValue(int loc, int start, int end, int[] array) {
        // 범위가 하나의 값으로 모아진 경우
        // 값 세팅
        if (start == end) {
            segmentTree[loc] = array[start];
            return;
        }

        // 아직 범위인 경우
        // 두 개의 구간으로 쪼갠다.
        int mid = (start + end) / 2;
        inputValue(loc * 2, start, mid, array);
        inputValue(loc * 2 + 1, mid + 1, end, array);
        // 양 쪽 구간의 범위가 지정되면
        // 두 구간을 포함하는 현재 loc의 값을 두 구간의 최대값으로 세팅한다.
        segmentTree[loc] = Math.max(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
    }
}