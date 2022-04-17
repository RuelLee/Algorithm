/*
 Author : Ruel
 Problem : Baekjoon 10868번 최솟값
 Problem address : https://www.acmicpc.net/problem/10868
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10868_최솟값;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;

    public static void main(String[] args) throws IOException {
        // n개의 정수들이 주어진다
        // m개의 숫자 쌍 a, b가 주어질 때, 숫자 a번째부터 b번째까지의 수들 중 가장 작은 수를 출력하라
        //
        // 세그먼트 트리 문제
        // n이 최대 10만, m이 최대 10만까지 주어지므로 일일이 찾아서는 시간 초과가 난다
        // 세그먼트 트리를 이용하여 해당 구간에서의 최솟값을 찾아주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());


        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
        int[] nums = new int[n];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.parseInt(br.readLine());
        // setSegmentTree 메소드로 한번에 세그먼트 트리 세팅.
        setSegmentTree(nums, 1, 1, n);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            sb.append(getValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, 1, n)).append("\n");
        }
        System.out.print(sb);
    }

    static int getValue(int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        // 정확히 범위가 일치할 때는 segmentTree 값을 그대로 가져온다.
        if (targetStart == seekStart && targetEnd == seekEnd)
            return segmentTree[loc];

        int mid = (seekStart + seekEnd) / 2;
        // mid보다 찾고자 하는 범위가 같거나 작다면 왼쪽 자식 노드에서 탐색한다.
        if (targetEnd <= mid)
            return getValue(targetStart, targetEnd, loc * 2, seekStart, mid);
        // mid보다 찾고자 하는 범위가 크다면 오른쪽 자식 노드에서 탐색한다.
        else if (targetStart > mid)
            return getValue(targetStart, targetEnd, loc * 2 + 1, mid + 1, seekEnd);
        // 찾고자 하는 범위가 왼쪽 자식 노드, 오른쪽 자식 노드에 걸쳐있을 때.
        // 두 자식 노드에서 범위를 나눠 둘 다 탐색한 후, 더 작은 값을 가져온다.
        else
            return Math.min(getValue(targetStart, mid, loc * 2, seekStart, mid),
                    getValue(mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd));
    }

    static void setSegmentTree(int[] nums, int loc, int start, int end) {
        // start와 end가 같아지는 지점 = 말단 노드
        // 수를 그대로 세팅한다.
        if (start == end) {
            segmentTree[loc] = nums[start - 1];
            return;
        }

        int mid = (start + end) / 2;
        // 왼쪽 자식 노드 세팅
        setSegmentTree(nums, loc * 2, start, mid);
        // 오른족 자식 노드 세팅
        setSegmentTree(nums, loc * 2 + 1, mid + 1, end);
        // 자식 노드 세팅이 끝났으면 부모 노드에는 두 자식 노드 중 더 적은 값을 가져온다.
        segmentTree[loc] = Math.min(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
    }
}