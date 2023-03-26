/*
 Author : Ruel
 Problem : Baekjoon 13544번 수열과 쿼리 3
 Problem address : https://www.acmicpc.net/problem/13544
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13544_수열과쿼리3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] segmentTree;
    static int[] nums;

    public static void main(String[] args) throws IOException {
        // 길이가 n인 수열이 주어진다. 다음 쿼리
        // a b c -> i ~ j번까지의 부분 수열에서 k보다 큰 원소의 개수
        // 여기서 i = a xor lastAnser, j = b xor lastAnswer, k = c xor lastAnswer
        // m개에 대한 답을 출력하라
        //
        // 머지 소트 트리 문제
        // 쿼리가 xor 연산을 이용한 이유는 오프라인 쿼리로 처리할 수 없게하기 위해서다
        // 문제 자체는 이전에 풀었던 수열과 쿼리 1(https://www.acmicpc.net/problem/13537)과 동일하다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 주어지는 수열
        int n = Integer.parseInt(br.readLine());
        nums = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 머지 소트 트리 생성 및 초기화 
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1) + 1][];
        setMergesortTree(1, 0, n - 1);

        // m개의 쿼리
        int m = Integer.parseInt(br.readLine());
        // 이전 답
        int lastAnswer = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // a, b, c에 대한 쿼리를 처리
            lastAnswer = processQuery(a ^ lastAnswer, b ^ lastAnswer, c ^ lastAnswer, 1, 1, n);
            sb.append(lastAnswer).append("\n");
        }

        // 전체 답안 출력
        System.out.print(sb);
    }

    // 쿼리 처리
    static int processQuery(int i, int j, int k, int loc, int start, int end) {
        // 일치하는 범위를 찾았다면
        // 이진탐색을 통해 해당 범위에서 k보다 큰 수의 개수를 센다.
        if (i == start && j == end)
            return binarySearch(segmentTree[loc], k);

        // 일치하지 않다면 범위를 자식 노드들을 타고 들어가며 범위를 좁혀나간다.
        int mid = (start + end) / 2;
        // 범위가 왼쪽 자식 노드에 모두 포함되는 경우
        if (j <= mid)
            return processQuery(i, j, k, loc * 2, start, mid);
        else if (i > mid)       // 범위가 오른쪽 자식 노드에 모두 포함되는 경우
            return processQuery(i, j, k, loc * 2 + 1, mid + 1, end);
        else {      // 범위가 왼쪽과 오른쪽 자식 노드에 걸쳐있는 경우
            return processQuery(i, mid, k, loc * 2, start, mid) +
                    processQuery(mid + 1, j, k, loc * 2 + 1, mid + 1, end);
        }
    }

    // 머지 소트 트리는 정렬이 되어있는 상태이므로
    // 이진 탐색을 통해 k보다 큰 수의 개수를 빠르게 찾아낸다.
    static int binarySearch(int[] array, int k) {
        int start = 0;
        int end = array.length;
        while (start < end) {
            int mid = (start + end) / 2;
            if (array[mid] <= k)
                start = mid + 1;
            else
                end = mid;
        }
        // start 부터 arrays.length - 1까지가 만족하는 범위
        // array의 idx가 0부터 시작됨에 유의하며 개수를 반환한다.
        return array.length - start;
    }

    // 머지 소트 트리 초기화
    static void setMergesortTree(int loc, int start, int end) {
        // 범위가 1인 단일값인 경우.
        if (start == end) {
            segmentTree[loc] = new int[]{nums[start]};
            return;
        }

        // 범위가 2이상이라 자식 노드로 분할해야하는 경우.
        int mid = (start + end) / 2;
        setMergesortTree(loc * 2, start, mid);
        setMergesortTree(loc * 2 + 1, mid + 1, end);
        // 자식 노드들의 세팅이 끝났다.

        // 이제 현재 노드의 세팅.
        // 현재 노드에 주어지는 배열의 크기는 두 자식 노드들의 크기 합
        segmentTree[loc] = new int[segmentTree[loc * 2].length + segmentTree[loc * 2 + 1].length];
        // 머지 소트 형태이므로 두 자식 노드 모두 정렬이 끝나있다.
        // 따라서 두 자식 노드에 포인터들을 두고 값을 비교하며 더 작은 값을 현재 노드에 순서대로 넣어간다.
        int left = 0;
        int right = 0;
        for (int i = 0; i < segmentTree[loc].length; i++) {
            // 왼쪽 자식 노드의 해당하는 수열을 모두 사용한 경우
            // 오른쪽 자식 노드의 값을 하나씩 추가
            if (left == segmentTree[loc * 2].length)
                segmentTree[loc][i] = segmentTree[loc * 2 + 1][right++];
                // 반대로 오른쪽 자식 노드에 해당하는 수열을 모두 사용한 경우
            else if (right == segmentTree[loc * 2 + 1].length)
                segmentTree[loc][i] = segmentTree[loc * 2][left++];

            else {
                // 두 자식 노드들의 수들을 모두 사용하지 않은 경우
                // 포인터들의 값을 비교해나가며 더 작은 값을 우선적으로 배치해나간다.
                segmentTree[loc][i] = segmentTree[loc * 2][left] < segmentTree[loc * 2 + 1][right] ?
                        segmentTree[loc * 2][left++] : segmentTree[loc * 2 + 1][right++];
            }
        }
    }
}