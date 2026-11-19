/*
 Author : Ruel
 Problem : Jungol 12205번 포화이진트리(perfect binary tree)의 중위순회
 Problem address : https://jungol.co.kr/problem/12205
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_12205_포화이진트리의중위순회;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 높이(말단 노드로부터 루트 노드까지의 거리)가 h인 포화이진트리가 주어질 때
        // 중위 순회로 n번째 방문하는 노드의 번호는?
        //
        // 이분 탐색 문제
        // 중위 순회를 하므로, 순회의 순서는 왼쪽 자식 노드 -> 부모 노드 -> 오른쪽 자식 노드
        // 따라서 현재 높이가 h라면, 왼쪽 서브 트리의 노드 수가 2^h -1, 오른쪽 서브 트리에 노드의 수가 2^h - 1 개이다.
        // n이 왼쪽 자식 노드의 수보다 작은 경우
        // 왼쪽 자식 노드로 이동
        // n이 정확히 2^h인 경우,부모 노드
        // n이 2^h보다 큰 경우, 오른쪽 노드로 이동하길 반복하여 노드를 찾아낸다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 높이 h인 포화 이진 트리에서 중위 순회를 할 때
        // n번째 노드의 번호를 출력한다.
        int h = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        // 제곱을 미리 계산
        int[] pows = new int[h + 1];
        pows[0] = 1;
        for (int i = 1; i <= h; i++)
            pows[i] = pows[i - 1] * 2;

        // 루트. 시작 정점.
        int node = 1;
        // 아직 높이와 n이 1이상인 경우에만 계산
        while (h > 0 && n > 0) {
            // 왼쪽 자식 노드의 수보다 n이 같거나 작다면
            if (n < pows[h]) {
                // 왼쪽 자식 노드로 이동하고 높이를 하나 차감
                node *= 2;
                h--;
            } else { // 그 외의 경우
                // n에서 2^h만큼을 빼고
                n -= pows[h];
                // n이 0보다 큰 경우에는 오른쪽 자식 노드로 이동
                // 아닌 경우는 n이 0이 되므로 부모 노드를 가르킴
                if (n > 0) {
                    node = node * 2 + 1;
                    h--;
                }
            }
        }
        // 답 출력
        System.out.println(node);
    }
}