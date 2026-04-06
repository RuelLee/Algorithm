/*
 Author : Ruel
 Problem : Baekjoon 17408번 수열과 쿼리 24
 Problem address : https://www.acmicpc.net/problem/17408
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17408_수열과쿼리24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[][] segmentTree;

    public static void main(String[] args) throws IOException {
        // a1, ... , an의 수열이 주어진다.
        // 이 때, 다음 두 종류의 쿼리가 m개 주어질 때, 해당 쿼리들을 처리하라
        // 1 i v : ai를 v로 바꾼다
        // 2 l r : l <= i < j <= r을 만족하는 ai + aj의 최댓값을 출력하라
        //
        // 세그먼트 트리 문제
        // 세그먼트 트리로, 범위 내에 가장 큰 값 2개를 관리하도록 한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 수열의 크기 n
        n = Integer.parseInt(br.readLine());
        // 세그먼트 트리 크기
        int size = 1;
        while (size < n)
            size *= 2;
        size *= 2;
        segmentTree = new int[size][2];

        // 수열을 세그먼트 트리에 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            inputValue(i + 1, Integer.parseInt(st.nextToken()));

        // m개의 쿼리
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());
            // 1번 쿼리인 경우
            // 값 변경
            if (o == 1)
                inputValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            else {
                // 2번 쿼리인 경우
                // 상위 2개의 값의 합을 기록
                int[] answer = getValues(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, 1, n);
                sb.append(answer[0] + answer[1]).append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }

    // start ~ end까지의 범위에 가장 큰 두 값을 가져온다.
    // idx 현재 위치, seekStart, seekEnd 현재 왼쪽 오른쪽 범위
    static int[] getValues(int start, int end, int idx, int seekStart, int seekEnd) {
        // 범위가 일치한다면 해당 값을 가져옴
        if (seekStart == start && seekEnd == end)
            return segmentTree[idx];

        // 일치하지 않는다면 반으로 나누어
        int mid = (seekStart + seekEnd) / 2;
        // 왼쪽 범위에 들어간다면
        // 왼쪽 범위에서만 가져오고
        if (end <= mid)
            return getValues(start, end, idx * 2, seekStart, mid);
        else if (start > mid)       // 오른쪽 범위에 들어간다면 오른쪽 범위에서만 가져온다.
            return getValues(start, end, idx * 2 + 1, mid + 1, seekEnd);
        else {
            // 그렇지않고, 양 범위에 걸친 경우
            // 왼쪽 범위에서 해당하는 만큼을 가져오고
            int[] left = getValues(start, mid, idx * 2, seekStart, mid);
            // 오른쪽 범위에서 해당하는 만큼을 가져와
            int[] right = getValues(mid + 1, end, idx * 2 + 1, mid + 1, seekEnd);

            // 가져온 4개의 값을 비교하여 가장 큰 두 값을 배열로 만들어 반환
            if (left[0] >= right[0])
                return new int[]{left[0], Math.max(left[1], right[0])};
            else
                return new int[]{right[0], Math.max(left[0], right[1])};
        }
    }

    // idx 위치에 value 값 갱신
    static void inputValue(int idx, int value) {
        // 현재 위치
        int cur = 1;
        // 범위
        int start = 1;
        int end = n;
        // 주소가 범위가 아닌 하나의 값이 될 때가지
        while (start < end) {
            // 반을 나누어
            int mid = (start + end) / 2;
            // 왼쪽 범위에 속하는지
            if (idx <= mid) {
                end = mid;
                cur *= 2;
            } else {
                // 오른쪽 범위에 속하는지
                start = mid + 1;
                cur = cur * 2 + 1;
            }
        }

        // 해당 위치에 값 갱신
        segmentTree[cur][0] = value;

        // 그 후, 부모 노드로 거슬러 올라가며 값 갱신
        cur /= 2;
        while (cur > 0) {
            // 두 자식 노드 중
            // 최댓값이 더 큰 쪽 idx
            int biggerIdx = 0;
            // 최댓값이 더 작은 쪽 idx
            int smallerIdx = 0;
            if (segmentTree[cur * 2][0] >= segmentTree[cur * 2 + 1][0]) {
                biggerIdx = cur * 2;
                smallerIdx = cur * 2 + 1;
            } else {
                biggerIdx = cur * 2 + 1;
                smallerIdx = cur * 2;
            }
            // 최댓값의 큰 쪽의 최댓값이 cur에서도 최댓값이며
            segmentTree[cur][0] = segmentTree[biggerIdx][0];
            // 두번째 큰 값은, 최댓값이 큰 쪽의 두번째 값과
            // 최댓값이 작은 쪽의 큰 값을 비교하여 더 큰 값을 기록
            segmentTree[cur][1] = Math.max(segmentTree[biggerIdx][1], segmentTree[smallerIdx][0]);
            cur /= 2;
        }
    }
}