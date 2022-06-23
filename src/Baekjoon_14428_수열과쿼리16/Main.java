/*
 Author : Ruel
 Problem : Baekjoon 14428번 수열과 쿼리 16
 Problem address : https://www.acmicpc.net/problem/14428
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14428_수열과쿼리16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열과 m개의 쿼리가 주어진다
        // 쿼리의 형태는
        // 1 i v -> i번째 수를 v로 바꾼다
        // 2 i j -> i ~ j에서 가장 작은 값의 인덱스를 출력한다. 그러한 값이 여러개인 경우에는 작은 idx를 출력.
        //
        // 세그먼트 트리 문제
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        nums = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < nums.length; i++)
            nums[i] = Integer.parseInt(st.nextToken());
        // 세그먼트 트리를 처음 주어진 수열로 초기화한다.
        setSegmentTree(1, 1, n);

        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        // m개의 쿼리
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            // 값을 변경하는 경우.
            if (Integer.parseInt(st.nextToken()) == 1)
                updateValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            // 구간 최소 인덱스를 구하는 경우.
            else
                sb.append(getMinIdx(1, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, n)).append("\n");
        }
        System.out.print(sb);
    }

    // targetStart ~ targetEnd까지의 수들 중 가장 작은 값의 인덱스를 반환한다.
    static int getMinIdx(int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {
        // 원하는 범위를 찾았을 때, 세그먼트 트리 값을 리턴한다.
        if (targetStart == seekStart && targetEnd == seekEnd)
            return segmentTree[loc];

        // 아니라면 범위의 중간값을 구해
        int mid = (seekStart + seekEnd) / 2;
        // targetEnd가 mid보다 같거나 작다면, 왼쪽 자식 트리를 탐색한다.
        if (targetEnd <= mid)
            return getMinIdx(loc * 2, targetStart, targetEnd, seekStart, mid);
        // targetStart가 mid보다 크다면 오른쪽 자식 트리를 탐색한다.
        else if (targetStart > mid)
            return getMinIdx(loc * 2 + 1, targetStart, targetEnd, mid + 1, seekEnd);
        // 그렇지 않고, mid가 targetStart부터 targetEnd 사이에 있을 때는
        else {
            // seekStart ~ mid까지의 범위와
            int leftSide = getMinIdx(loc * 2, targetStart, mid, seekStart, mid);
            // mid + 1 ~ seekEnd까지의 범위로 나눠 각각의 최소 인덱스를 가져온 후
            int rightSide = getMinIdx(loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
            // 두 인덱스를 통해 더 작은 값을 찾고 해당하는 값을 반환한다.
            return nums[leftSide] <= nums[rightSide] ? leftSide : rightSide;
        }
    }

    // 수열의 수를 변경하는 메소드
    static void updateValue(int target, int value) {
        int loc = 1;
        int start = 1;
        int end = nums.length - 1;
        // 해당하는 단말 노드까지 찾아간다.
        while (start < end) {
            int mid = (start + end) / 2;
            if (target > mid) {
                loc = loc * 2 + 1;
                start = mid + 1;
            } else {
                loc *= 2;
                end = mid;
            }
        }
        
        // 수열의 값을 변경해준 뒤
        nums[target] = value;
        // 단말 노드로부터 부모노드로 거슬러 올라가며
        loc /= 2;
        while (loc > 0) {
            // 왼쪽 자식 노드와 오른쪽 자식 노드를 비교하며 더 작은 값의 인덱스로 값을 갱신해나간다.
            segmentTree[loc] = nums[segmentTree[loc * 2]] <= nums[segmentTree[loc * 2 + 1]] ?
                    segmentTree[loc * 2] : segmentTree[loc * 2 + 1];
            loc /= 2;
        }
    }

    // nums 배열을 통해 세그먼트 트리를 초기화한다.
    static void setSegmentTree(int loc, int start, int end) {
        // 단말 노드라면
        if (start == end) {
            // loc에 해당하는 수열의 idx를 저장한다.
            segmentTree[loc] = start;
            return;
        }

        // start와 end의 평균 값을 구해(=왼쪽 자식 노드와 오른쪽 자식 노드의 기준)
        int mid = (start + end) / 2;
        // 왼쪽 자식 노드로 초기화 메소드를 호출하고
        setSegmentTree(loc * 2, start, mid);
        // 오른쪽 자식 노드로도 초기화 메소드를 재귀적으로 호출한다.
        setSegmentTree(loc * 2 + 1, mid + 1, end);
        // 그 후, 왼쪽 자식 노드와 오른쪽 자식 노드의 인덱스를 통해 더 작은 수의 인덱스를 저장한다.
        segmentTree[loc] = nums[segmentTree[loc * 2]] <= nums[segmentTree[loc * 2 + 1]] ?
                segmentTree[loc * 2] : segmentTree[loc * 2 + 1];
    }
}