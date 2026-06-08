/*
 Author : Ruel
 Problem : Jungol 1506번 안전한 철판
 Problem address : https://jungol.co.kr/problem/1506
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_1506_안전한철판;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // x축과 나란하게 0의 위치에서부터 레이저를 쏜다.
        // 레이저는 세기만큼의 철판을 뚫을 수 있다.
        // 철판의 개수와 각 철판의 위치가 주어진다.
        // 각 철판은 y축과 평행하게 위치하고, 놓인 x값과 y의 값들이 주어진다.
        // 레이저의 영향을 받지 않은 철판의 번호를 오름차순으로 출력하라
        //
        // 시뮬레이션, 정렬 문제
        // 먼저 철판의 입력을 받고, 놓은 x의 값에 따라 정렬한다
        // 그 뒤 현재까지 놓인 철판의 y값에 따른 두께를 계산한다.
        // 이 때, y값 중 가장 적은 두께를 가진 곳이 레이저를 쏘았을 때, 현재 철판에 닿게되는 세기이다.
        // 따라서 해당 값이 레이저 세기보다 큰 경우만 따로 모아, 오름차순으로 출력한다

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 레이저의 세기, 철판의 개수
        int strength = Integer.parseInt(br.readLine());
        int num = Integer.parseInt(br.readLine());

        // 철판의 정보
        int[][] plates = new int[num][4];
        StringTokenizer st;
        for (int i = 0; i < plates.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++)
                plates[i][j] = Integer.parseInt(st.nextToken());
            // 철판의 번호 x값 작은y값 큰y값
            // 으로 주어지는데 y값의 경우 3 5로 주어질 경우, 3 ~ 5까지의 길이라는게 된다.
            // 이 때 앞의 3은 3부터라는 의미지만 뒤의 5는 45지라는 의미이므로
            // 값이 가르키는 범위를 통일하기 위해, 각 값을 i부터 i+1까지로 통일한다.
            // 3 4로 변형하여 값을 대입
            plates[i][3]--;
        }
        // x축 위치에 따라 정렬
        Arrays.sort(plates, Comparator.comparingInt(o -> o[1]));

        // 현재까지 각 y값에 쌓인 철판의 두께
        int[] depths = new int[1001];
        // 우선순위큐를 통해 철판을 오름차순으로 정렬
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 모든 철판을 살펴보며
        for (int[] plate : plates) {
            // 현재 철판이 모든 y값에 대해 최소 몇번째인지 계산
            int minDepth = Integer.MAX_VALUE;
            for (int i = plate[2]; i <= plate[3]; i++)
                minDepth = Math.min(minDepth, ++depths[i]);

            // 만약 자신의 순서가 strength보다 큰 경우, 레이저에 닿지 않으므로
            // 우선순위큐에 추가
            if (minDepth > strength)
                priorityQueue.offer(plate[0]);
        }

        StringBuilder sb = new StringBuilder();
        // 그러한 철판이 없는 경우 0 기록
        if (priorityQueue.isEmpty())
            sb.append(0);
        else {
            // 있는 경우
            // 우선순위큐에서 꺼내가며 기록
            sb.append(priorityQueue.poll());
            while (!priorityQueue.isEmpty())
                sb.append(" ").append(priorityQueue.poll());
        }
        // 답 출력
        System.out.println(sb);
    }
}