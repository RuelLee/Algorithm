/*
 Author : Ruel
 Problem : Jungol 4156번 Timeline
 Problem address : https://jungol.co.kr/problem/4156
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_4156_Timeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 우유짜기 세션이 주어진다. 각 우유짜기 세션은 m일 안에 이루어졌다.
        // 각 세션의 가능 일이 주어진다.
        // c개의 기억이 있는데 a b x 형태로 주어지고, b 세션은 a 세션이 이루어진 뒤, 최소 x일 이후에 이루어졌다는 기억이다.
        // 각 세션의 최소 가능 일을 출력하라
        //
        // 위상 정렬 문제
        // 위상 정렬을 통해 관계를 정리하고,
        // 진입 차수가 0인 세션과 연결된 이후 세션들의 진입 차수를 낮춰주며, 최소 가능 일을 관게 맞게 수정해나가면 된다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 세션, 최대 일 m, 기억의 개수 c개
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 각 세션의 최초 최소 가능일
        int[] days = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            days[i] = Integer.parseInt(st.nextToken());

        // 기억 정리
        List<List<int[]>> list = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            list.add(new ArrayList<>());

        int[] inDegrees = new int[n + 1];
        for (int i = 0; i < c; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            list.get(a).add(new int[]{b, x});
            // b의 진입 차수 증가
            inDegrees[b]++;
        }

        // 진입 차수가 0인 세션을 추가
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < inDegrees.length; i++)
            if (inDegrees[i] == 0)
                queue.add(i);

        while (!queue.isEmpty()) {
            // 현재 진입 차수가 0인 current
            int current = queue.poll();

            // 연결된 기억들을 보며
            // 이후 세션이 x보다 같거나 큰 지, 아니라면 맞게끔 수정해나간다.
            // 그리고 진입 차수가 0이 된다면 큐에 추가
            for (int[] memory : list.get(current)) {
                days[memory[0]] = Math.max(days[memory[0]], days[current] + memory[1]);
                if (--inDegrees[memory[0]] == 0)
                    queue.offer(memory[0]);
            }
        }

        // 답 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++)
            sb.append(days[i]).append("\n");
        // 출력
        System.out.print(sb);
    }
}