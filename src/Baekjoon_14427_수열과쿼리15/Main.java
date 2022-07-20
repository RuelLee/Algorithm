/*
 Author : Ruel
 Problem : Baekjoon 14427번 수열과 쿼리 15
 Problem address : https://www.acmicpc.net/problem/14427
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14427_수열과쿼리15;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열과 m개의 쿼리가 주어진다.
        // 쿼리
        // 1 i v : Ai를 v로 바꾼다. (1 ≤ i ≤ N, 1 ≤ v ≤ 109)
        // 2 : 수열에서 크기가 가장 작은 값의 인덱스를 출력한다. 그러한 값이 여러개인 경우에는 인덱스가 작은 것을 출력한다.
        // 주어지는 2번 쿼리에 대한 결과값을 출력하라.
        //
        // 세그먼트 트리 문제
        // 수열을 입력받고, 해당 수열의 인덱스로 세그먼트리를 세우고, 원래 값이 작은 인덱스를 세그먼트 트리에 저장해주자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 원래 수
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 세그먼트 트리 초기화
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        setSegmentTree(1, 1, n);

        // m개의 쿼리 처리
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1일 때는 target 값을 value로 바꿈
            if (1 == Integer.parseInt(st.nextToken())) {
                int target = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                changeValue(target, value);
            } else
                sb.append(segmentTree[1] + 1).append("\n");         // 아닐 경우, 전체 수열의 최소값의 인덱스 출력.
        }
        System.out.print(sb);
    }

    // target 위치의 값을 value로 바꾸고 세그먼트 트리를 갱신한다.
    static void changeValue(int target, int value) {
        int loc = 1;
        int seekStart = 1;
        int seekEnd = nums.length;
        // 수의 값을 바꾸고,
        nums[target - 1] = value;

        // 세그먼트 트리에서 target의 위치까지 찾아간다.
        while (seekStart < seekEnd) {
            int mid = (seekStart + seekEnd) / 2;
            if (target <= mid) {
                loc *= 2;
                seekEnd = mid;
            } else {
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            }
        }

        // 해당 세그먼트 트리의 조상노드로부터, 계속 조상노드로 올라가며
        loc /= 2;
        while (loc > 0) {
            // 원래 값을 비교하고, 더 작은 값의 인덱스를 저장한다.
            // 같을 경우, 인덱스가 작은 값을 저장하므로, 왼쪽 자식의 값이 오른쪽 자식의 값보다 같거나 작을 경우
            // 왼쪽 자식의 값을 저장한다.
            if (nums[segmentTree[loc * 2]] <= nums[segmentTree[loc * 2 + 1]])
                segmentTree[loc] = segmentTree[loc * 2];
            else
                segmentTree[loc] = segmentTree[loc * 2 + 1];
            loc /= 2;
        }
    }

    // 세그먼트 트리 초기화.
    static void setSegmentTree(int n, int seekStart, int seekEnd) {
        // 하나의 값으로 범위가 좁혀졌다면, 단말 노드
        // 원래 수의 인덱스를 저장하자.
        if (seekStart == seekEnd) {
            segmentTree[n] = seekStart - 1;
            return;
        }

        int mid = (seekStart + seekEnd) / 2;
        // 왼쪽 자식 노드 값 설정.
        setSegmentTree(n * 2, seekStart, mid);
        // 오른쪽 자식 노드 값 설정.
        setSegmentTree(n * 2 + 1, mid + 1, seekEnd);

        // 왼쪽 자식 노드의 값이 오른쪽 자식노드보다 같거나 작을 경우
        // 왼쪽 자식 노드의 값을 저장하고
        if (nums[segmentTree[n * 2]] <= nums[segmentTree[n * 2 + 1]])
            segmentTree[n] = segmentTree[n * 2];
            // 그렇지 않을 경우 오른쪽 자식 노드의 값을 저장한다.
        else
            segmentTree[n] = segmentTree[n * 2 + 1];
    }
}