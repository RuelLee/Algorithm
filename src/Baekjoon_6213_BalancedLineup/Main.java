/*
 Author : Ruel
 Problem : Baekjoon 6213번 Balanced Lineup
 Problem address : https://www.acmicpc.net/problem/6213
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_6213_BalancedLineup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] segmentTree;
    static int n;

    public static void main(String[] args) throws IOException {
        // n마리의 소의 크기 주어진다.
        // q개의 쿼리가 주어진다
        // a b : a번부터 b번까지의 소들의 최대 키 차이.
        // 해당 쿼리들을 처리하라.
        //
        // 세그먼트 트리 문제
        // 오랜만에 세그먼트 트리 문제를 풀어보았다.
        // 세그먼트 트리를 활용하면, 특정 범위에 대한 계산값을 미리 계산해두기 때문에
        // 연산을 줄일 수 있는 장점이 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 소, q개의 쿼리
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 세그먼트 트리
        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)) + 1)][2];
        // 값 초기화
        for (int i = 0; i < segmentTree.length; i++) {
            segmentTree[i][0] = Integer.MAX_VALUE;
            segmentTree[i][1] = Integer.MIN_VALUE;
        }
        // i번 소의 키를 세그먼트 트리에 넣는다.
        for (int i = 0; i < n; i++)
            inputValue(i + 1, Integer.parseInt(br.readLine()));

        StringBuilder sb = new StringBuilder();
        // 각각의 쿼리를 처리한다.
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int[] minMax = findMinMax(1, a, b, 1, n);
            sb.append(minMax[1] - minMax[0]).append("\n");
        }
        System.out.print(sb);
    }

    // start ~ end 범위의 소들에서 최소, 최대키를 반환한다.
    static int[] findMinMax(int loc, int start, int end, int seekStart, int SeekEnd) {
        // 범위가 딱 맞아떨어진다면, 해당 값 반환.
        if (start == seekStart && end == SeekEnd)
            return segmentTree[loc];

        // 그 외의 경우
        // seekStart와 seekEnd의 중간 값인 mid를 기준으로
        int mid = (seekStart + SeekEnd) / 2;
        // end가 mid보다 같거나 작다면
        // 해당하는 범위가 왼쪽 하위 트리에 모두 있다.
        // 왼쪽 자식 노드로 넘긴다.
        if (end <= mid)
            return findMinMax(loc * 2, start, end, seekStart, mid);
        else if (start > mid)       // mid보다 크다면, 오른쪽 자식 노드에 모든 범위가 있다. 오른쪽 자식 노드로 넘긴다.
            return findMinMax(loc * 2 + 1, start, end, mid + 1, SeekEnd);

        // 그 외의 경우, 두 자식 노드 모두에 걸쳐있는 경우.
        // 왼쪽 자식 노드에서는 start ~ mid까지를
        int[] leftReturned = findMinMax(loc * 2, start, mid, seekStart, mid);
        // 오른쪽 자식 노드에서는 mid + 1 ~ end 까지를
        int[] rightReturned = findMinMax(loc * 2 + 1, mid + 1, end, mid + 1, SeekEnd);

        // 결과 값을 받아와 두 값을 비교하여 해당하는 최소, 최대값 배열을 만들어 반환한다.
        int[] answer = new int[2];
        answer[0] = Math.min(leftReturned[0], rightReturned[0]);
        answer[1] = Math.max(leftReturned[1], rightReturned[1]);
        return answer;
    }

    // idx번의 소의 키를 세그먼트 트리에 넣는다.
    static void inputValue(int idx, int value) {
        // 현재 위치 = 배열의 idx 번호
        int loc = 1;
        // 탐색하는 소의 번호 범위
        int start = 1;
        int end = n;
        // 특정 한 값의 번호를 찾을 때까지
        while (start < end) {
            int mid = (start + end) / 2;
            // idx가 mid보다 같거나 작다면 왼쪽 자식 노드
            if (idx <= mid) {
                loc *= 2;
                end = mid;
            } else {        // 그 외의 경우 오른쪽 자식 노드
                loc = loc * 2 + 1;
                start = mid + 1;
            }
        }
        // 최종적으로 얻은 loc에 해당하는 값 삽입.
        segmentTree[loc][0] = segmentTree[loc][1] = value;

        // 거꾸로 거슬러 올라가면서, 상위 노드의 값을 갱신해준다.
        loc /= 2;
        while (loc > 0) {
            segmentTree[loc][0] = Math.min(segmentTree[loc * 2][0], segmentTree[loc * 2 + 1][0]);
            segmentTree[loc][1] = Math.max(segmentTree[loc * 2][1], segmentTree[loc * 2 + 1][1]);
            loc /= 2;
        }
    }
}