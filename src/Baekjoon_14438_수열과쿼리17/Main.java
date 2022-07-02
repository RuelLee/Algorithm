/*
 Author : Ruel
 Problem : Baekjoon 14438번 수열과 쿼리 17
 Problem address : https://www.acmicpc.net/problem/14438
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14438_수열과쿼리17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 크기 n의 수열이 주어진다
        // m개의 쿼리가 주어진다
        // 쿼리의 종류는
        // 1 i v -> Ai를 v로 바꾼다
        // 2 i j -> i ~ j 구간의 최소 값을 출력한다.
        //
        // 세그먼트 트리 문제
        // 1번 쿼리는 Ai까지 찾아가서 값을 바꾼 후, 부모 노드로 올라가며 양쪽 자식 노드를 비교하며 최소값을 갱신해야하며
        // 2번 쿼리는 원하는 구간까지 찾아가서 세그먼트 트리 값을 참고한다
        // 원하는 구간이 여러 노드에 걸쳐있을 경우, 그들의 최소값을 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        // 기본 수열
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 세그먼트 트리 선언.
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)];
        // 세그먼트 트리 초기화.
        setSegmentTree(nums, 1, 1, n);

        // m개의 쿼리.
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1번 쿼리
            if (Integer.parseInt(st.nextToken()) == 1)
                modifyValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            // 2번 쿼리.
            else
                sb.append(getMinValue(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 1, 1, n)).append("\n");
        }
        System.out.print(sb);
    }

    // targetStart ~ targetEnd까지의 최소 값을 찾는다.
    static int getMinValue(int targetStart, int targetEnd, int loc, int seekStart, int seekEnd) {
        // 최대한 자식 노드로 많이 타고 들어간다.
        while (targetStart != seekStart || targetEnd != seekEnd) {
            int mid = (seekStart + seekEnd) / 2;
            // 왼쪽 자식 노드
            if (targetEnd <= mid) {
                loc *= 2;
                seekEnd = mid;
            } else if (targetStart > mid) {
                // 오른쪽 자식 노드
                loc = loc * 2 + 1;
                seekStart = mid + 1;
            } else
                // 두 자식 노드에 모두 걸쳐있는 경우 종료한다.
                break;
        }

        // 원하는 범위를 찾았다면 해당 세그먼트 트리값 리턴.
       if (targetStart == seekStart && targetEnd == seekEnd)
            return segmentTree[loc];
       // 그게 아니라면 양쪽 자식 노드에 걸쳐있는 경우.
        int mid = (seekStart + seekEnd) / 2;
        // 재귀적으로 각 자식 노드에 메소드를 호출한 뒤, 두 값중 최소값을 반환한다.
        return Math.min(getMinValue(targetStart, mid, loc * 2, seekStart, mid),
                getMinValue(mid + 1, targetEnd, loc * 2 + 1, mid + 1, seekEnd));
    }

    // 1번 쿼리를 수행할 메소드.
    // target의 값을 value로 고친다.
    static void modifyValue(int target, int value) {
        int loc = 1;
        int start = 1;
        int end = n;
        // target에 해당하는 idx loc을 찾는다.
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
        // 원하는 위치까지 왔다면 값을 수정하고
        segmentTree[loc] = value;
        // 부모 노드로 올라간다.
        loc /= 2;
        // 최상위 노드까지
        while (loc > 0) {
            // 양쪽 자식 노드의 값을 비교해 최소값으로 자신의 값을 갱신하고
            segmentTree[loc] = Math.min(segmentTree[loc * 2], segmentTree[loc * 2 + 1]);
            // 부모 노드로 간다.
            loc /= 2;
        }
    }

    // 세그먼트 트리 초기화 함수.
    static int setSegmentTree(int[] nums, int loc, int seekStart, int seekEnd) {
        // 단말 노드에 도착했다면, 해당 순서의 수로 값 설정.
        if (seekStart == seekEnd)
            return segmentTree[loc] = nums[seekStart - 1];

        // 그렇지 않다면 양쪽 자식 노드로 각각 타고 들어간다.
        int mid = (seekStart + seekEnd) / 2;
        // 각각 자식 노드로 초기화 함수를 재귀호출한 뒤, 리턴으로 받는 해당 서브 트리의 최소값을 비교해
        // 최소값으로 세그먼트 트리 값을 설정하고, 리턴한다.
        return segmentTree[loc] = Math.min(setSegmentTree(nums, loc * 2, seekStart, mid),
                setSegmentTree(nums, loc * 2 + 1, mid + 1, seekEnd));
    }
}