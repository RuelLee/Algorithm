/*
 Author : Ruel
 Problem : Baekjoon 32387번 충전하기
 Problem address : https://www.acmicpc.net/problem/32387
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32387_충전하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n개의 포트가 있는 멀트 포트 충전기가 있다.
        // i번 포트는 i만큼의 전력을 충전해준다.
        // 다음과 같은 쿼리 q개가 주어진다.
        // 1 i : i번 포트보다 같거나 크지만 가장 작은 포트에 전자기기를 꽂는다.
        // 성공적으로 꽂았다면, 해당 포트 번호를 출력하며, 그런 포트가 없다면 -1을 출력한다.
        // 2 i : i번 포트에 기기가 꽂혀있다면 뽑는다.
        // 꽂혀있다면 몇 번째 쿼리에서 꽂힌 기기인지 출력하고, 그렇지 않다면 -1을 출력한다
        //
        // 트리 셋 문제
        // 인 듯하나, 이분 탐색, 누적합, 펜윅 트리로도 풀 수 있어 그렇게 푼 문제
        // 트리셋으로 사용 가능한 포트를 모두 담아두고, 찾아도 되나
        // 펜윅 트리와 이분 탐색을 통해서도 유사하게 구현할 수 있다.
        // 오히려 지우는 과정이 없으니 시간상 유리할 것이라 생각했다.
        // 누적합을 통해 펜윅 트리에, 기기가 꽂힌 포트는 1 아닌 포트는 0으로 하여
        // 범위 내에 비어있는 포트가 존재하는지 여부를 이분탐색으로 탐색하여
        // 기준보다 같거나 크지만 가장 작은 포트를 찾는다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 포트, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 펜윅 트리
        fenwickTree = new int[n + 1];
        // 플러그에 꽂힌 기기가 몇번째 행동으로 꽂힌 기기인지 표시
        int[] plugs = new int[n + 1];
        StringBuilder sb = new StringBuilder();
        // 쿼리 처리
        for (int j = 0; j < q; j++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int i = Integer.parseInt(st.nextToken());
            
            // 1번 쿼리일 경우
            if (t == 1) {
                // i보다 크거나 같으면서 가장 작은 비어있는 포트를 찾는다.
                int idx = findMinWattFromN(i);
                // 존재하지 않는다면 -1
                if (idx == fenwickTree.length)
                    sb.append(-1);
                else {
                    // 찾았다면 idx번째 플러그가 몇 번째 행동으로 꽂힌 기기인지 표시하고
                    plugs[idx] = j + 1;
                    // 펜윅 트리에도 idx번에 1을 넣는다.
                    inputValue(idx, 1);
                    // 답지에는 해당 idx를 기록
                    sb.append(idx);
                }
                sb.append("\n");
            } else {
                // 2번 쿼리일 경우
                // 포트가 비어있다면 -1
                if (plugs[i] == 0)
                    sb.append(-1);
                else {
                    // 전자 기기가 꽂혀있다면
                    // 해당 기기가 몇 번째 행동으로 꽂혔는지 기록
                    // 해당 기기가 몇 번째 행동으로 꽂혔는지 기록
                    sb.append(plugs[i]);
                    // 펜윅 트리에 i번 값을 -1
                    inputValue(i, -1);
                    // i번째 포트에 기기를 뽑았으므로 0으로 다시 바꿔줌
                    plugs[i] = 0;
                }
                sb.append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // n보다 크거나 같지만 비어있는 가장 작은 포트를 찾는다.
    // 이분 탐색
    static int findMinWattFromN(int n) {
        // 범위는 n ~ 펜윅 트리의 크기
        int start = n;
        int end = fenwickTree.length;
        while (start < end) {
            int mid = (start + end) / 2;
            // 비어있는 포트가 존재한다면 end의 범위를 mid로 줄인다.
            if (getSums(mid) - getSums(n - 1) < mid - n + 1)
                end = mid;
            else        // 비어있는 포트가 존재하지 않는다면, start를 mid+1로 높인다.
                start = mid + 1;
        }
        // 최종적으로 찾은 포트 번호가 start에 남는다.
        // 해당 결과 반환
        return start;
    }

    // 펜윅 트리를 통해
    // 1 ~ idx까지 포트들에 꽂힌 전자기기의 수를 반환한다.
    static int getSums(int idx) {
        int sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx번째 포트에 전자기기를 꽂거나 뽑음을 펜윅 트리에 기록한다.
    static void inputValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }
}