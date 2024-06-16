/*
 Author : Ruel
 Problem : Baekjoon 29618번 미술 시간
 Problem address : https://www.acmicpc.net/problem/29618
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_29618_미술시간;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n칸과 q개의 쿼리가 주어진다.
        // 쿼리는
        // a b x 의 형태로 주어지며, a부터 b까지의 칸 중 색이 아직 칠해지지 않은 칸을 x 색으로 칠한다는 뜻이다.
        // 쿼리를 모두 처리한 후
        // n개 칸의 색을 모두 출력하라
        // 색이 칠해지지 않았다면 0번 색이다.
        //
        // 우선순위큐 문제
        // 쿼리가 범위로 주어지므로, 여러 쿼리가 중복되는 범위를 가질 수 있다.
        // 따라서 우선순위큐를 통해, 구간을 처리할 때, 우선권을 갖는 쿼리를 선별하여 해당 색을 칠한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 칸, q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 쿼리들
        int[][] queries = new int[q][];
        for (int i = 0; i < q; i++)
            queries[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 처음 칠하는 칸이 먼저 등장하는 순서
        PriorityQueue<Integer> appearOrder = new PriorityQueue<>(Comparator.comparingInt(o -> queries[o][0]));
        // 쿼리가 먼저 등장하여 색에 대해 우선권을 갖음을 표현하는 우선순위큐
        PriorityQueue<Integer> priority = new PriorityQueue<>();
        // appearOrder에 모든 idx를 집어넣음
        for (int i = 0; i < queries.length; i++)
            appearOrder.offer(i);

        StringBuilder sb = new StringBuilder();
        // 1번 칸부터 n번칸까지 처리
        for (int i = 1; i <= n; i++) {
            // appearOrder에 i부터 해당하는 쿼리가 최상단에 있다면
            // 해당 쿼리들을 priority 우선순위큐에 추가.
            while (!appearOrder.isEmpty() && queries[appearOrder.peek()][0] == i)
                priority.offer(appearOrder.poll());
            
            // priority 우선순위큐에서 범위가 지나간 쿼리가 최상단에 있다면
            // 해당 쿼리 제거
            while (!priority.isEmpty() && queries[priority.peek()][1] < i)
                priority.poll();
            
            // 만약 priority 우선순위큐가 비어있다면
            // 해당 칸에 해당하는 쿼리가 없는 경우. 0번 색 그대로
            if (priority.isEmpty())
                sb.append(0);
            else        // 있는 경우, 해당 색을 칠함.
                sb.append(queries[priority.peek()][2]);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 전체 칸들의 색 출력
        System.out.println(sb);
    }
}