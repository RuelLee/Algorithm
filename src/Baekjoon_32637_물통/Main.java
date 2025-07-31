/*
 Author : Ruel
 Problem : Baekjoon 32637번 물통
 Problem address : https://www.acmicpc.net/problem/32637
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_32637_물통;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 용량이 x인 물통과 크기가 n인 수열이 주어진다.
        // m개의 쿼리가 주어지며 쿼리는
        // y : 처음 물통에 y만큼을 채우고, 수열의 수만큼 차례대로 물통의 물을 더 넣거나 뺐을 때, 최종적으로 남은 물의 양
        // 을 뜻한다.
        // 물통에 물을 더할 때는 x를 넘을 수 없고, 물을 뺄 때는 0보다 작아질 수 없다.
        //
        // 애드 혹 문제
        // 물통에 물을 x보다 많이 담을 수 없고, 0보다 적어질 수 없다는 점을 유의하자.
        // 물통의 물이 각 단계를 거칠 때마다 가질 수 있는 최소량과 최대량 그리고 그 최소량과 최대량을 갖기 위해 처음에 부어야하는 물의 양을 계산하면 된다.
        // 그러면 쿼리가 주어질 때마다 O(1)로 처리할 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 물통의 용량 x와 수열의 크기 n
        long x = Long.parseLong(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        
        // 주어진 수열
        long[] a = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++)
            a[i] = Long.parseLong(st.nextToken());
        
        // min[0] = 각 단계를 거칠 때마다의 물이 최소량일 때
        // 처음에 부을 수 있는 물의 최대값
        // min[1] = 각 단계를 거칠 때마다의 물의 최소량
        long[] min = new long[2];
        // max[0] = 각 단계를 거칠 떄마다의 물이 최대량일 때
        // 처음에 부을 수 있는 물의 최소량
        // max[1] = 각 단계를 거칠 때마다의 물의 최대량
        long[] max = new long[2];
        max[0] = max[1] = x;
        for (int i = 0; i < a.length; i++) {
            // 이번에 물을 더 붓는 경우
            if (a[i] >= 0) {
                // max가 상한이 x를 넘는다면
                // 해당 양 만큼의 물을 처음에 덜 붓더라도 상한이 되게 된다.
                // 해당 값 계산
                if (max[1] + a[i] > x)
                    max[0] = Math.max(max[0] + x - max[1] - a[i], 0);
                // 현재 단계의 최소량 최대량 물도 증가.
                // 상한은 x
                min[1] = Math.min(min[1] + a[i], x);
                max[1] = Math.min(max[1] + a[i], x);
            } else {
                // 이번에 물을 빼는 경우
                // 물의 양이 0보다 적어지는 경우
                // 해당 양 만큼 물이 더 있더라도 0가 된다.
                // 해당 값 계산
                if (min[1] + a[i] < 0)
                    min[0] = Math.min(min[0] - (min[1] + a[i]), x);
                // 현재 단계의 최소량, 최대량 물도 증가
                // 하한은 0
                min[1] = Math.max(min[1] + a[i], 0);
                max[1] = Math.max(max[1] + a[i], 0);
            }
        }
        
        // m개의 쿼리
        int m = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            // 초기에 y만큼 물을 붓는 경우
            long y = Long.parseLong(br.readLine());
            // y가 min[0]보다 같거나 작다면 min[1] 값으로 고정
            if (y <= min[0])
                sb.append(min[1]).append("\n");
            // y가 max[0]보다 같거나 크다면 max[1] 값으로 고정
            else if (y >= max[0])
                sb.append(max[1]).append("\n");
            else        // 그 외의 경우는 최소량에서 추가량만큼 양이 증가
                sb.append(min[1] + y - min[0]).append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}