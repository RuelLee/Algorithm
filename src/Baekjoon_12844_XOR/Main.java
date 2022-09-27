/*
 Author : Ruel
 Problem : Baekjoon 12844번 XOR
 Problem address : https://www.acmicpc.net/problem/12844
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12844_XOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] segmentTree;
    static int[] lazy;

    public static void main(String[] args) throws IOException {
        // 크기가 n인 수열이 주어지고, m개의 쿼리가 주어진다 쿼리는
        // 1 i j k : i ~ j번째 수에 모두 k를 xor 한다.
        // 2 i j  :  i ~ j번째 수를 모두 xor한 후 출력.
        //
        // 세그먼트 트리 문제이되, 느리게 전파되는 세그먼트 트리 문제.
        // 1번 쿼리에 대해 최종적으로는 일일이 k 연산을 해야하는 것인데, 이것을 최대한 뒤로 늦출 수 있다.
        // xor 연산 중 하나의 성질이, value에 xor 연산을 짝수번했다면 value 값이 그대로 나온다는 점이 있다.
        // 따라서 4 ~ 7 이라는 범위에 각각 xor k를 한 값이 구하고 싶다면, 4 xor k xor 5 xor k ... 7 xor k를 한 값도 값이지만
        // 4 xor 5 xor ... xor 7을 한 값과도 같다. k번을 짝수번 xor 했으므로.
        // 따라서 세그먼트 트리에는 자식노드들을 일일이 xor한 값을 갖고 있되, lazy에는 자식에게 속한 모든 범위의 xor 값을 갖고 있으면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수의 개수
        int n = Integer.parseInt(br.readLine());
        // 세그먼트 트리
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n + 1) / Math.log(2)) + 1)];
        // lazy 값들.
        lazy = new int[segmentTree.length];
        int[] nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 초기화.
        init(nums, 1, 0, n - 1);

        // m개의 쿼리/
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < m; q++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int i = Integer.parseInt(st.nextToken());
            int j = Integer.parseInt(st.nextToken());
            // 1번 쿼리라면
            if (order == 1) {
                // i ~ j 범위에 대해 xor k값을 한다.
                // 이 때 범위가 일치하는 노드를 찾는다면 해당 노드에는 lazy[node]에 k값을 xor만 해준다.
                // 중간에 만나는 조상 노드의 lazy값들을 자식노드들로 전파해준다.
                int k = Integer.parseInt(st.nextToken());
                order(i, j, k, 1, 0, n - 1);
            } else {
                // 2번 쿼리라면 i ~ j 범위까지의 수를 xor한 값을 출력한다.
                sb.append(order(i, j, 0, 1, 0, n - 1)).append("\n");
            }
        }
        System.out.print(sb);
    }

    // 값 갱신과 조회를 하나의 메소드로 처리했다.
    // xor 연산에서 0이 항등원인 것을 이용한다.
    // value에 0 을 태워보내면 2번 쿼리로 사용할 수 있고, 0이 아닌 값을 태우면 1번 쿼리로 사용할 수 있다.
    static int order(int targetStart, int targetEnd, int value, int loc, int seekStart, int seekEnd) {
        // 정확히 일치하는 범위라면
        if (targetStart == seekStart && targetEnd == seekEnd) {
            // 현재 갖고 있는 value값을 lazy에 xor 연산해주고
            lazy[loc] ^= value;
            // 현재 노드 혹은 자식노드가 있다면 자식 노드들의 xor 값을 반환한다.
            // 이 때 lazy값이 있다면 해당 값을 같이 xor해 준다.
            return segmentTree[loc] ^ ((targetEnd - targetStart + 1) % 2 == 0 ? 0 : lazy[loc]);
        }

        // 만약 일치하지 않는 범위라면 최소한 단말 노드는 아니다.
        // 따라서 lazy값을 갖고 있다면 자식 노드들로 전파해준다.
        if (lazy[loc] != 0) {
            lazy[loc * 2] ^= lazy[loc];
            lazy[loc * 2 + 1] ^= lazy[loc];
            lazy[loc] = 0;
        }

        // 자식 노드들에게 xor할 값을 재귀적으로 메소드를 부른다.
        int mid = (seekStart + seekEnd) / 2;
        int returnValue;
        // 왼쪽 자식 노드
        if (targetEnd <= mid)
            returnValue = order(targetStart, targetEnd, value, loc * 2, seekStart, mid);
        // 오른쪽 자식 노드
        else if (targetStart > mid)
            returnValue = order(targetStart, targetEnd, value, loc * 2 + 1, mid + 1, seekEnd);
        // 범위가 두 자식노드들에 걸친 경우.
        else
            returnValue = order(targetStart, mid, value, loc * 2, seekStart, mid) ^
                    order(mid + 1, targetEnd, value, loc * 2 + 1, mid + 1, seekEnd);

        // segmentTree[loc]에는 자식 노드들의 값이 갱신됐으므로, 두 자식 노드들의 xor한 값으로 새롭게 값을 갱신한다.
        segmentTree[loc] = segmentTree[loc * 2] ^ ((mid - seekStart + 1) % 2 == 0 ? 0 : lazy[loc * 2]) ^
                segmentTree[loc * 2 + 1] ^ ((seekEnd - mid) % 2 == 0 ? 0 : lazy[loc * 2 + 1]);
        // 정확히 일치하는 범위에 대한 반환값을 returnValue로 값을 따로 저장해뒀다.
        // 이를 반환.
        return returnValue;
    }

    // 세그먼트 트리 초기화.
    static void init(int[] nums, int loc, int start, int end) {
        if (start == end) {
            segmentTree[loc] = nums[start];
            return;
        }

        int mid = (start + end) / 2;
        init(nums, loc * 2, start, mid);
        init(nums, loc * 2 + 1, mid + 1, end);
        segmentTree[loc] = segmentTree[loc * 2] ^ segmentTree[loc * 2 + 1];
    }
}