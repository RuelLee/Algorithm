/*
 Author : Ruel
 Problem : Baekjoon 12899번 데이터 구조
 Problem address : https://www.acmicpc.net/problem/12899
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12899_데이터구조;

import java.io.*;
import java.util.StringTokenizer;

public class Main2 {
    static int[] segmentTree;

    public static void main(String[] args) throws IOException {
        // 해당 문제를 세그먼트 트리의 구조를 이용해서 푸는 경우.
        // 세그먼트 트리를 이용하여 해당 위치에 존재하는 수의 개수의 합으로 표현한다.
        // 그 후 자식 노드들을 타고 나가면서 이 합을 이용하며 x번째 수를 찾는다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        segmentTree = new int[(int) Math.pow(2, Math.ceil(Math.log(2_000_000) / Math.log(2)) + 1)];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            // 쿼리가 1인 경우, 세그먼트 트리에 x 개수 증가.
            if (t == 1)
                inputValue(x);
            // 쿼리가 2인 경우 세그먼트 트리에서 x번째 수를 기록하고, 해당 수를 하나 감소.
            else
                sb.append(calcXthNumAndDelete(x)).append("\n");
        }
        System.out.print(sb);
    }
    
    // 세그먼트 트리에 x 수를 입력하는 메소드
    static void inputValue(int x) {
        int loc = 1;
        int start = 1;
        int end = 2_000_000;
        // x에 해당하는 말단 노드까지 찾아간다.
        while (start < end) {
            int mid = (start + end) / 2;
            // 오른쪽 자식 노드를 타는 경우
            if (x > mid) {
                start = mid + 1;
                loc = loc * 2 + 1;
            } else {
                // 왼쪽 자식 노드를 타는 경우.
                end = mid;
                loc *= 2;
            }
        }

        // 부모 노드로 거슬러 올라가며 해당하는 범위에 존재하는 수의 개수를
        // 나타내는 세그먼트 트리 값을 하나씩 증가시킨다.
        while (loc > 0) {
            segmentTree[loc]++;
            loc /= 2;
        }
    }

    // x번째 수를 찾고 해당 수의 개수를 하나 줄인다.
    static int calcXthNumAndDelete(int x) {
        int loc = 1;
        int start = 1;
        int end = 2_000_000;
        // 말단 노드가 될 때까지
        while (start < end) {
            int mid = (start + end) / 2;
            // x번째 수가 왼쪽 자식 노드가 갖고 있는 값보다 큰 경우
            // 해당하는 x번째 수는 오른쪽 자식 노드에 존재한다.
            if (x > segmentTree[loc * 2]) {
                // 오른쪽 자식 노드로 갈 경우, 왼쪽 자식 노드에 속하는 수만큼은 사라지므로
                // x에서 왼쪽 자식 노드에 속한 수의 개수만큼을 제해준다.
                x -= segmentTree[loc * 2];
                loc = loc * 2 + 1;
                start = mid + 1;
            } else {
                // 왼쪽 자식 노드로 갈 경우, x번째 수를 유지.
                loc *= 2;
                end = mid;
            }
        }

        // 최종적으로 찾아진 위치에서 루트 노드로 거슬러 올라가며 수의 개수를 하나씩 줄여나간다.
        while (loc > 0) {
            segmentTree[loc]--;
            loc /= 2;
        }
        // 찾아진 수는 start
        return start;
    }
}