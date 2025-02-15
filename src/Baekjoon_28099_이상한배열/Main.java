/*
 Author : Ruel
 Problem : Baekjoon 28099번 이상한 배열
 Problem address : https://www.acmicpc.net/problem/28099
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_28099_이상한배열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 길이 n의 배열이 주어진다.
        // 다음 조건을 만족하면 이상한 배열이라고 한다.
        // Ai == Aj를 만족할 때, 1 <= i < k < j <= N 조건의 k에 대해
        // Ak <= Ai를 만족한다.
        // 배열이 주어질 떄, 해당 배열이 이상한 배열인지 확인하라
        //
        // 세그먼트 트리 문제
        // 특정한 수 i에 대해
        // 가장 처음에 등장한 i의 idx, 가장 마지막에 등장한 i의 idx를 계산한다.
        // 그 사이에 존재하는 모든 수는 i보다 같거나 작아야한다.
        // 세그먼트 트리를 통해 구간의 최대값을 구할 수 있도록 세팅하고
        // 해당 해당 범위 내에 i보다 큰 수가 있는지 판별하면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int testCase = 0; testCase < t; testCase++) {
            int n = Integer.parseInt(br.readLine());
            
            // 세그먼트 트리 초기화
            segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
            // 처음 등장한 수의 idx와 마지막에 등장한 idx
            int[] firstAppear = new int[n + 1];
            Arrays.fill(firstAppear, Integer.MAX_VALUE);
            int[] lastAppear = new int[n + 1];
            
            // 세그먼트 트리 채움.
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                int num = Integer.parseInt(st.nextToken());
                putValue(1, num, i + 1, 1, n);
                // 처음, 마지막 등장 idx 관리
                firstAppear[num] = Math.min(firstAppear[num], i + 1);
                lastAppear[num] = Math.max(lastAppear[num], i + 1);
            }

            boolean possible = true;
            for (int i = 1; i < firstAppear.length; i++) {
                // 처음 등장이 초기값이라면 등장하지 않은 경우
                // 건너 뛴다.
                if (firstAppear[i] == Integer.MAX_VALUE)
                    continue;

                // i가 처음 등장한 idx와 마지막에 등장한 idx 내의 범위에서
                // i보다 큰 값이 있어서는 안된다.
                // 있다면 불가능한 경우.
                if (getMax(1, firstAppear[i], lastAppear[i], 1, n) > i) {
                    possible = false;
                    break;
                }
            }
            // 답 작성
            sb.append(possible ? "Yes" : "No").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 세그먼트 트리를 통해
    // start ~ end 범위 내의 가장 큰 값을 찾는다.
    static int getMax(int loc, int start, int end, int seekStart, int seekEnd) {
        if (start == seekStart && end == seekEnd)
            return segmentTree[loc];
        
        // 현재 범위의 중간값
        int mid = (seekStart + seekEnd) / 2;
        // 중간값보다 같거나 작은 범위에 구하고자하는 범위가 들어간다면
        // 왼쪽 자식 노드만 참고해도 된다.
        if (end <= mid)
            return getMax(loc * 2, start, end, seekStart, mid);
        else if (start > mid)       // 반대의 경우 오른쪽 자식 노드만 참고해도 된다.
            return getMax(loc * 2 + 1, start, end, mid + 1, seekEnd);
        else        // 그렇지 않고 걸친 경우에는 양쪽 자식 노드를 모두 참고한다.
            return Math.max(getMax(loc * 2, start, mid, seekStart, mid),
                    getMax(loc * 2 + 1, mid + 1, end, mid + 1, seekEnd));
    }
    
    // target번째에 value값을 넣는다.
    static void putValue(int loc, int value, int target, int start, int end) {
        // target의 idx에 해당하는 위치를 찾는다.
        while (start < end) {
            int mid = (start + end) / 2;
            if (target <= mid) {
                loc *= 2;
                end = mid;
            } else {
                loc = loc * 2 + 1;
                start = mid + 1;
            }
        }
        // 값을 삽입 후
        segmentTree[loc] = value;
        // 1번까지 거슬러 올라가며 세그먼트 트리 값 갱신
        loc /= 2;
        while (loc > 0) {
            segmentTree[loc] = Math.max(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
            loc /= 2;
        }
    }
}