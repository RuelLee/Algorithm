/*
 Author : Ruel
 Problem : Baekjoon 2268번 수들의 합 7
 Problem address : https://www.acmicpc.net/problem/2268
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2268_수들의합7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static long[] segmentTree;

    public static void main(String[] args) throws IOException {
        // n개의 수, m개의 쿼리가 주어진다.
        // 수는 모두 처음엔 0이며
        // 쿼리는
        // 0 i j -> i ~ j 까지의 합
        // 1 i k -> i번째 수를 k로 바꿈.
        // n, m은 최대 100만, 1 <= k <= 100_000
        // 합의 쿼리 개수 만큼 각 줄에 합을 출력하라.
        //
        // 세그먼트 트리 내지 펜윅 트리를 이용하여 연산을 줄여야하는 문제.
        // Main은 세그먼트 트리, Main2는 펜윅 트리를 이용하여 풂.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // n의 개수에 따라 필요한 트리 깊이를 찾고, 해당하는 개수만큼 배열을 선언해준다.
        segmentTree = new long[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 0일 때는 합을 구한다.
            if (Integer.parseInt(st.nextToken()) == 0) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                // 작은 순서 값 ~ 큰 순서 값까지의 합.
                sb.append(getSum(Math.min(a, b), Math.max(a, b), 1, 1, n)).append("\n");
            } else {
                // order번째 수를 value로 바꿈.
                int order = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                modify(order, value);
            }
        }
        // 기록된 StringBuilder를 출력.
        System.out.print(sb);
    }

    // targetStart ~ targetEnd까지의 합을 구한다.
    static long getSum(int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        // 값의 범위가 일치할 때까지
        while (!(targetStart == seekStart && targetEnd == seekEnd)) {
            // mid 값 설정.
            int mid = (seekStart + seekEnd) / 2;
            // 원하는 범위가 mid보다 같거나 작다면
            // 왼쪽 자식 노드를 태우고, seekEnd의 범위를 줄여준다.
            if (targetEnd <= mid) {
                seekEnd = mid;
                loc *= 2;
            // 원하는 범위가 mid보다 크다면
            // 오른쪽 자식 노드를 태우고, seekStart의 범위를 줄인다.
            } else if (targetStart > mid) {
                seekStart = mid + 1;
                loc = loc * 2 + 1;
            } else {
            // 위에 속하지 않는다면 범위가 걸친 경우
            //  targetStart ~ mid까지, mid + 1 ~ targetEnd 까지로 범위를 나눈다.
                return getSum(targetStart, mid, loc * 2, seekStart, mid) +
                        getSum(mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
            }
        }
        // 원하는 범위에 도달한 경우, segmentTree 값을 반환한다.
        return segmentTree[loc];
    }

    // order번째 수를 value로 수정한다.
    static void modify(int order, int value) {
        int start = 1;
        int end = n;
        int loc = 1;
        // start < end인 동안
        while (start < end) {
            // mid값 설정.
            int mid = (start + end) / 2;
            // mid보다 같거나 작다면 왼쪽 자식 노드
            if (order <= mid) {
                end = mid;
                loc *= 2;
            // mid보다 크다면 오른쪽 자식 노드
            } else {
                start = mid + 1;
                loc = loc * 2 + 1;
            }
        }
        // 해당하는 순서에 일치하는 위치에 도달했다면 값을 수정하고
        segmentTree[loc] = value;

        // 부모노드로 올라가면서 양쪽 자식 노드의 합으로 값을 갱신해준다.
        loc /= 2;
        while (loc > 0) {
            segmentTree[loc] = segmentTree[loc * 2] + segmentTree[loc * 2 + 1];
            loc /= 2;
        }
    }
}