/*
 Author : Ruel
 Problem : Baekjoon 21870번 시철이가 사랑한 GCD
 Problem address : https://www.acmicpc.net/problem/21870
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21870_시철이가사랑한GCD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int n;

    public static void main(String[] args) throws IOException {
        // n개의 수가 주어진다.
        // 배열을 S, 배열의 크기를 |S|라 할 때
        // 1. S의 왼쪽부터 내림(|S| / 2)개의 원소를 선택하거나 오른쪽부터 올림(|S| / 2)개, 혹은 원소가 하나라면 그 원소를 선택한다.
        // 2. 선택한 원소들의 GCD를 구한다.
        // 3. 선택하지 않았던 원소 배열 S'에 대해 2번부터 반복한다.
        // 4. GCD 합의 최대값을 정의한다.
        // 전체 배열에 대한 GCD 합의 최대값은?
        //
        // 세그먼트 트리, 분할 정복, 유클리드 호제법, 브루트 포스 문제
        // 구간에 대한 GCD를 반복적으로 구하기 때문에 이를 세그먼트 트리로 정리
        // 그 후, 문제에 주어진 조건대로
        // 구간을 반 나누었을 때, 왼쪽 구간을 선택하는 경우, 오른쪽 구간을 선택하는 경우를 직접 계산
        // 두 경우의 값 중 더 큰 값을 취하는 형태를 재귀로 계속 행해나가면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 수
        n = Integer.parseInt(br.readLine());
        
        // 세그먼트 트리의 크기
        int size = 1;
        while (size < n)
            size <<= 1;
        size <<= 1;
        segmentTree = new int[size];
        
        // 세그먼트 트리에 값 추가
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            inputValue(i + 1, Integer.parseInt(st.nextToken()));

        // 1 ~ n 구간에 대한 최대 GCD 합 출력
        System.out.println(getMaxScore(1, n));
    }

    // start ~ end까지 범위에 대해 최대 GCD 합을 구한다.
    static int getMaxScore(int start, int end) {
        // 단일 원소일 경우. 해당 값 반환
        if (start == end)
            return query(start, end, 1, 1, n);
        // 그 외의 경우
        // 왼쪽 구간을 선택하고, 오른쪽 구간을 S'로 하는 경우
        // 왼쪽 구간을 S'으로 하고, 오른쪽 구간을 선택하는 경우
        // 두 경우 중 더 큰 값을 반환
        return Math.max(query(start, (start + end + 1) / 2 - 1, 1, 1, n) + getMaxScore((start + end + 1) / 2, end),
                getMaxScore(start, (start + end + 1) / 2 - 1) + query((start + end + 1) / 2, end, 1, 1, n));
    }

    // start ~ end 범위의 GCD를 구한다.
    static int query(int start, int end, int loc, int seekStart, int seekEnd) {
        // 범위 일치 시, 해당 loc 위치의 세그먼트 트리 값 반환
        if (seekStart == start && seekEnd == end)
            return segmentTree[loc];
        
        // 그 외의 경우 seekStart, seekEnd의 범위를 반으로 쪼개
        int mid = (seekStart + seekEnd) / 2;
        // end가 seekStart ~ mid까지에 온전히 왼쪽 자식 노드에 속할 때
        if (end <= mid)
            return query(start, end, loc * 2, seekStart, mid);
        // end가 seekStart ~ mid까지에 온전히 오른쪽 자식 노드에 속할 때
        else if (start > mid)
            return query(start, end, loc * 2 + 1, mid + 1, seekEnd);
        // 양쪽에 범위가 걸친 경우
        return getGCD(query(start, mid, loc * 2, seekStart, mid),
                query(mid + 1, end, loc * 2 + 1, mid + 1, seekEnd));
    }

    // 세그먼트 트리, 단말 노드에 값을 추가.
    static void inputValue(int idx, int value) {
        int start = 1;
        int end = n;
        int loc = 1;
        // 일치하는 단말 노드의 loc 찾기
        while (start < end) {
            int mid = (start + end) / 2;
            if (idx > mid) {
                start = mid + 1;
                loc = loc * 2 + 1;
            } else {
                end = mid;
                loc *= 2;
            }
        }
        segmentTree[loc] = value;
        loc >>= 1;

        // 부모 노드의 값 채우기.
        while (loc > 0) {
            if (segmentTree[loc * 2] == 0 || segmentTree[loc * 2 + 1] == 0)
                segmentTree[loc] = Math.max(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
            else
                segmentTree[loc] = getGCD(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
            loc >>= 1;
        }
    }

    // 유클리드 호제법
    // a와 b의 GCD를 구한다.
    static int getGCD(int a, int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min > 0) {
            int temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}