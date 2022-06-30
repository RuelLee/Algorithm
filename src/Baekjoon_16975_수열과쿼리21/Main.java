/*
 Author : Ruel
 Problem : Baekjoon 16975번 수열과 쿼리 21
 Problem address : https://www.acmicpc.net/problem/16975
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16975_수열과쿼리21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long[] segmentTree;
    static int n;

    public static void main(String[] args) throws IOException {
        // 길이가 N인 수열 A1, A2, ..., AN이 주어진다. 이때, 다음 쿼리를 수행하라.
        // 1 i j k: Ai, Ai+1, ..., Aj에 k를 더한다.
        // 2 x: Ax 를 출력한다.
        //
        // 세그먼트 트리 문제
        // 보통 세그먼트 트리는 큰 범위의 구간에서 합을 구하거나, 구간의 최소값, 내지 최대 값등을 빠르게 구할 때 사용한다
        // 하지만 이번 문제는 구간에 각 값들을 각각 증가시키거나 감소시킨다.
        // 따라서 세그먼트 트리의 단말 노드가 아닌 노드는 각각 범위를 갖고 있는데
        // 이 범위에 해당하는 증감을 기록해두는 용도로 사용하자
        // 그리고 단말 노드에 해당하는 값을 찾을 때 거쳐가는 노드들의 합을 구하며 값을 찾자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        // 세그먼트 트리 생성
        int size = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1);
        segmentTree = new long[size];

        // 세그먼트 트리 초기화
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        setSegmentTree(1, 1, n, nums);

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1번 쿼리
            if (Integer.parseInt(st.nextToken()) == 1)
                modifyValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                        1, 1, n);
                // 2번 쿼리
            else
                sb.append(getValue(Integer.parseInt(st.nextToken()))).append("\n");
        }
        System.out.print(sb);
    }

    // target 위치의 값을 구한다.
    static long getValue(int target) {
        int loc = 1;
        int start = 1;
        int end = n;
        // 1번 노드의 값으로 초기화.
        long sum = segmentTree[loc];
        // target에 해당하는 노드를 찾아간다.
        while (start < end) {
            int mid = (start + end) / 2;
            if (target > mid) {
                loc = loc * 2 + 1;
                start = mid + 1;
            } else {
                loc *= 2;
                end = mid;
            }
            // target에 도달할 때까지 거쳐가는 노드들의 값을 모두 더해간다.
            sum += segmentTree[loc];
        }
        // target은 위에 거쳐온 노드들의 범위에 모두 해당하므로
        // 해당 노드들의 증감과 target의 값을 더한 값을 리턴한다.
        return sum;
    }

    // 값을
    static void modifyValue(int targetStart, int targetEnd, int value, int loc, int seekStart, int seekEnd) {
        // 일치하는 범위에 도달할 때까지.
        while (targetStart != seekStart || targetEnd != seekEnd) {
            int mid = (seekStart + seekEnd) / 2;
            // 오른쪽 자식 노드로 타고 가는 경우.
            if (targetStart > mid) {
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            } else if (targetEnd <= mid) {
                // 왼쪽 자식 노드로 타고 가는 경우.
                loc *= 2;
                seekEnd = mid;
            } else {
                // 두 자식 노드를 모두 타야하는 경우.
                // 양 자식 노드에 각각 재귀적으로 메소드를 호출한다.
                modifyValue(targetStart, mid, value, loc * 2, seekStart, mid);
                modifyValue(mid + 1, targetEnd, value, loc * 2 + 1, mid + 1, seekEnd);
                // 각 메소드들이 분할된 범위로 찾아 갔을 것이므로 반복문 종료.
                break;
            }
        }

        // 일치하는 범위를 만난 경우에는
        // value값을 segmentTree[loc]에 더해준다.
        if (targetStart == seekStart && targetEnd == seekEnd)
            segmentTree[loc] += value;
    }

    // 세그먼트 트리 초기화.
    static void setSegmentTree(int loc, int start, int end, int[] nums) {
        // 단말 노드인 경우
        if (start == end) {
            // 처음 주어진 수로 초기화한다.
            segmentTree[loc] = nums[start - 1];
            return;
        }

        // 단말 노드가 아닌 경우.
        // 범위를 반으로 나눠
        int mid = (start + end) / 2;
        // 왼쪽 자식 노드
        setSegmentTree(loc * 2, start, mid, nums);
        // 오른쪽 자식 노드 각각 재귀 호출한다.
        setSegmentTree(loc * 2 + 1, mid + 1, end, nums);
    }
}