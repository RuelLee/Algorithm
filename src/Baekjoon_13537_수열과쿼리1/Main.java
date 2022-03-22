/*
 Author : Ruel
 Problem : Baekjoon 13537번 수열과 쿼리 1
 Problem address : https://www.acmicpc.net/problem/13537
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13537_수열과쿼리1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<Integer>[] segmentTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // n개의 수로 이루어진 수열이 주어진다
        // 그 후 m개의 쿼리가 i j k의 형태로 주어진다
        // i ~ j번쨰 수 중 k보다 큰 수는 몇 개인지 답하여라
        //
        // n이 최대 10만, m도 최대 10만까지 쓰이므로 일일이 구해서는 당연히 안된다.
        // 여러가지 알고리즘이 쓰인 복합적인 문제다
        // 먼저 범위에 따른 수열을 구하기 위해 세그먼트 트리가 쓰인다
        // 보통 세그먼트 트리에서는 최대값 내지 최소값으로 하나의 값을 썼지만 이번에는 여러 '수열'을 저장해야하므로 리스트로 정하자.
        // 그런데 이 때 우리가 원하는 형태는 'n보다 큰' 이므로 정렬이 되어있다면 해당 수를 구하는 게 더 편하다
        // 세그먼트 트리 형태로 범위를 반으로 나누어서 고려하는 분할정복 형태인데, 이와 관련된 효율이 좋은 소팅이 생각난다. 머지 소트다.
        // 따라서 세그먼트 트리를 구성해 나가면서 머지 소트를 동시에 시행해 각각을 정렬된 상태로 만든다.
        // 그 후 주어지는 쿼리에 따라 해당 하는 범위에 세그먼트 트리 노드로 가서, 이분 탐색으로 해당 수보다 큰 수의 개수를 구해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        segmentTree = new ArrayList[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1];
        for (int i = 0; i < segmentTree.length; i++)
            segmentTree[i] = new ArrayList<>();
        setSegmentTree(1, 1, n);

        int m = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            sb.append(getNumBiggerThanN(k, 1, start, end, 1, n)).append("\n");
        }
        System.out.print(sb);
    }

    static int binarySearch(int k, List<Integer> list) {        // 해당 세그먼트 트리 노드(리스트)에서 k보다 큰 수의 개수를 구한다.
        int start = 0;
        int end = list.size();
        while (start < end) {
            int mid = (start + end) / 2;
            if (list.get(mid) <= k)     // k보다 같거나 작다면 start를 mid + 1 값으로 넣어준다.
                start = mid + 1;
            else
                end = mid;
        }
        // 최종적으로 start에는 k보다 큰 수 중 가장 작은 값의 idx 값이 있다
        // 우리가 원하는 건 k보다 큰 수의 개수 이므로, 전체 리스트의 크기에서 해당 idx의 값을 빼주면 된다.
        return list.size() - start;
    }

    static int getNumBiggerThanN(int n, int loc, int targetStart, int targetEnd, int seekStart, int seekEnd) {
        if (targetStart == seekStart && targetEnd == seekEnd)       // 범위가 정확히 일치할 때는 이분 탐색으로 해당 노드를 탐색한다.
            return binarySearch(n, segmentTree[loc]);

        int mid = (seekStart + seekEnd) / 2;
        if (targetEnd <= mid)   // 찾는 범위가 왼쪽 자식 노드에 포함된다면
            return getNumBiggerThanN(n, loc * 2, targetStart, targetEnd, seekStart, mid);
        else if (targetStart > mid)     // 찾는 범위가 오른쪽 자식 노드에 포함된다면
            return getNumBiggerThanN(n, loc * 2 + 1, targetStart, targetEnd, mid + 1, seekEnd);
        else {      // 찾는 범위가 두 자식 노드에 걸쳐있다면
            return getNumBiggerThanN(n, loc * 2, targetStart, mid, seekStart, mid) +
                    getNumBiggerThanN(n, loc * 2 + 1, mid + 1, targetEnd, mid + 1, seekEnd);
        }
    }

    static void setSegmentTree(int loc, int start, int end) {       // 세그먼트 트리 초기 세팅.
        if (start == end) {     // start == end 라면 단말 노드. 그냥 nums에서 값을 가져온다.
            segmentTree[loc].add(nums[start - 1]);
            return;
        }

        // 단말 노드가 아닌 경우에는 왼쪽 자식 노드와 오른쪽 자식 노드로 나눈다.
        int mid = (start + end) / 2;
        // 자식 노드들에 대해 세그먼트 트리 생성
        setSegmentTree(loc * 2, start, mid);
        setSegmentTree(loc * 2 + 1, mid + 1, end);

        // 위의 과정이 끝났다면 왼쪽 자식 노드와 오른쪽 자식 노드 모두 정렬된 리스트가 된다.
        // 따라서 두 리스틑 가지고 머지소트를 시행해 n번 노드(리스트)도 생성해준다.
        int leftIdx = 0;
        int rightIdx = 0;
        int totalSize = segmentTree[loc * 2].size() + segmentTree[loc * 2 + 1].size();
        for (int i = 0; i < totalSize; i++) {
            if (leftIdx == segmentTree[loc * 2].size())     // 만약 왼쪽 자식 노드를 모두 살펴보았다면
                segmentTree[loc].add(segmentTree[loc * 2 + 1].get(rightIdx++));     // 오른쪽 자식 노드의 다음 수를 가져온다.
            else if (rightIdx == segmentTree[loc * 2 + 1].size())       // 만약 오른쪽 자식 노드를 모두 살펴보았다면
                segmentTree[loc].add(segmentTree[loc * 2].get(leftIdx++));      // 왼쪽 자식 노드의 다음 수를 가져온다.
            else        // 둘 다 아직 다 살펴보지 않았다면, 왼쪽 자식 노드의 다음 수와, 오른쪽 자식 노드의 다음 수 중 더 작은 값을 가져온다.
                segmentTree[loc].add(segmentTree[loc * 2].get(leftIdx) < segmentTree[loc * 2 + 1].get(rightIdx) ? segmentTree[loc * 2].get(leftIdx++) : segmentTree[loc * 2 + 1].get(rightIdx++));
        }
    }
}