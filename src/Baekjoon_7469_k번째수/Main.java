/*
 Author : Ruel
 Problem : Baekjoon 7469 k번째 수
 Problem address : https://www.acmicpc.net/problem/7469
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7469_k번째수;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int[] nums;
    static int[] compressed;
    static int[] restore;
    static int[][] mergesortTree;

    public static void main(String[] args) throws IOException {
        // n개의 수열이 주어진다
        // 그리고 q개의 쿼리가 주어진다
        // 쿼리는 i j k의 형태를 띄는데, i번째 수부터, j번째 수까지 중 k번째 수는 무엇인가에 대한 질의이다.
        // 쿼리에 해당하는 답을 출력하라
        //
        // 상당히 생각할 게 많았던 문제
        // 각 수는 절대값이 10^9을 넘지 않는다고 했으므로, int 범위 내이지만 음수도 등장할 수 있다. 그리고 그 개수는 최대 10만개이다
        // 주어지는 수의 개수에 비해 수의 범위가 넓고 음수도 있다 -> 좌표 압축을 통해 범위를 줄여준다면 좋다.
        // 다음 쿼리에 대해 생각해보면 i ~ j 수 중에 k번째 수를 찾는 것이다. q는 최대 5000까지 주어지므로, 그 때 그 때 범위를 받아 정렬해서도 안된다.
        // 머지 소트 트리 형태로 구성하자.
        // 그 다음 어떻게 k번째 수를 찾을지 생각해보자
        // 머지 소트 트리를 이용해서 그 때 그 때 수열을 만든다? -> 값의 연산과 복사가 너무 많아진다
        // 이분 탐색을 통해 n보다 같거나 작은 수가 몇 개인지 찾아나간다. 이 때 n보다 작은 수가 k개라면 n이 답이다.
        // 머지 소트 트리에서 n보다 작은 수가 몇개인지 셀 때도 이미 정렬이 된 형태이기 때문에 다시 이분 탐색을 사용할 수 있다.
        // 그 후, 구해진 n을 압축하기 전의 수로 복구해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 원래 주어지는 수열.
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 우선순위큐를 통해 좌표압축을 해주자. idx값을 넣고, nums에서 대소를 비교한다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> nums[o]));
        for (int i = 0; i < n; i++)
            priorityQueue.offer(i);
        compressed = new int[n];
        restore = new int[n];
        for (int i = 0; i < compressed.length; i++) {
            compressed[priorityQueue.peek()] = i;       // nums[idx]에 해당하는 값은 i로 압축된다.
            restore[i] = priorityQueue.poll();      // i로 압축된 값을 나중에 복구할 때 사용한다.
        }

        mergesortTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1][];
        setMergesortTree(1, 1, n);      // 머지 소트 트리 생성

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {   // q개의 쿼리를 처리하자.
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());       // start 부터
            int end = Integer.parseInt(st.nextToken());     // end 까지의 수 중
            int k = Integer.parseInt(st.nextToken());       // k번째 수를 구하라.

            // 좌표 압축을 통해 0 ~ n -1까지의 수로 표현되었다. 이를 이분 탐색으로 찾자.
            int left = 0;
            int right = n;
            while (left < right) {
                int mid = (left + right) / 2;
                // mid 값보다 같거나 작은 수가 k개 보다 작다면
                if (countBelowNFromStartToEnd(mid, start, end, 1, 1, n) < k)
                    left = mid + 1;
                // mid 값보다 같거나 작은 수가 k개 이상이라면
                else
                    right = mid;
            }
            // 최종저긍로 구해진 left값은 좌표압축이 된 값이다.
            // 이를 restore에 넣어 복구 시키면 원래 idx 값이 나온다
            // 이를 다시 nums에 넣어주면 원래 값이 나온다.
            sb.append(nums[restore[left]]).append('\n');
        }
        System.out.print(sb);
    }

    static int countBelowNFromStartToEnd(int n, int start, int end, int loc, int seekStart, int seekEnd) {      // start ~ end까지 범위에서 n 이하 수의 개수를 센다.
        if (start == seekStart && end == seekEnd) {     // 범위가 일치한다면
            // 이분 탐색을 통해 n보다 큰 수들 중 가장 작은 수를 찾는다.
            int left = 0;
            int right = mergesortTree[loc].length;

            while (left < right) {
                int mid = (left + right) / 2;
                // mid에 해당하는 값이 n보다 같거나 작다면
                if (mergesortTree[loc][mid] <= n)
                    left = mid + 1;
                // mid에 해당하는 값이 n보다 크다면
                else
                    right = mid;
            }
            // 최종적으로 left에는 n보다 큰 수들 중 가장 작은 수의 idx값이 들어있다.
            // idx가 0부터 시작하므로, 해당 left 값 자체가 n보다 같거나 작은 수의 개수와 같다.
            // left 리턴.
            return left;
        }

        int mid = (seekStart + seekEnd) / 2;
        if (end <= mid)
            return countBelowNFromStartToEnd(n, start, end, loc * 2, seekStart, mid);
        else if (start > mid)
            return countBelowNFromStartToEnd(n, start, end, loc * 2 + 1, mid + 1, seekEnd);
        else
            return countBelowNFromStartToEnd(n, start, mid, loc * 2, seekStart, mid) +
                    countBelowNFromStartToEnd(n, mid + 1, end, loc * 2 + 1, mid + 1, seekEnd);
    }

    static void setMergesortTree(int loc, int start, int end) {       // 머지 소트 트리 생성.
        if (start == end) {     // start == end라면 단말 노드. compressed에 저장된 값을 가져온다.
            mergesortTree[loc] = new int[]{compressed[start - 1]};
            return;
        }

        int mid = (start + end) / 2;        // 중간값을 기준으로
        setMergesortTree(loc * 2, start, mid);        // 왼쪽 자식 노드
        setMergesortTree(loc * 2 + 1, mid + 1, end);      // 오른쪽 자식 노드

        mergesortTree[loc] = new int[end - start + 1];        // segmentTree[loc]에는 총 start - end + 1개의 수가 정렬된다.
        // 머지 소트 트리이므로 두 자식 노드들은 정렬이 된 상태다. 각각의 자식 노드 인덱스 값을 증가시키며 대소를 비교하여 값을 추가해주면 된다.
        int leftIdx = 0;
        int rightIdx = 0;
        for (int i = 0; i < mergesortTree[loc].length; i++) {
            if (leftIdx == mergesortTree[loc * 2].length)
                mergesortTree[loc][i] = mergesortTree[loc * 2 + 1][rightIdx++];
            else if (rightIdx == mergesortTree[loc * 2 + 1].length)
                mergesortTree[loc][i] = mergesortTree[loc * 2][leftIdx++];
            else
                mergesortTree[loc][i] = mergesortTree[loc * 2][leftIdx] < mergesortTree[loc * 2 + 1][rightIdx] ?
                        mergesortTree[loc * 2][leftIdx++] : mergesortTree[loc * 2 + 1][rightIdx++];
        }
    }
}