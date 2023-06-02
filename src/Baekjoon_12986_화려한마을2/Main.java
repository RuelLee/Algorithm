/*
 Author : Ruel
 Problem : Baekjoon 12986번 화려한 마을2
 Problem address : https://www.acmicpc.net/problem/12986
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12986_화려한마을2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 집과 q개의 쿼리가 주어진다.
        // 각 집에 대해서 칠해져있는 색이 주어진다.
        // 쿼리는 특정 구간에 있는 집들의 색 중 가장 많은 것의 개수이다.
        // 이를 출력하는 프로그램을 만들어라.
        //
        // 오프라인 쿼리, mo's,
        // 값에 대한 수정이 없는 쿼리 -> 오프라인 쿼리
        // 구간에 대한 쿼리 -> mo's
        // 각 집에 대한 색 수정이 없으므로 오프라인 쿼리를 통해 계산에 유리하도록 정렬하여 쿼리를 처리하고
        // 이 때 범위가 주어지므로 이를 mo's 알고리즘을 통해 정렬한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 집과 q개의 쿼리
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 각 집에 대한 색깔
        int[] homes = Arrays.stream(br.readLine().split(" ")).mapToInt(value -> Integer.parseInt(value) + 100_000).toArray();
        // 해당 색의 집이 모두 몇 개가 있는가
        int[] colorCounts = new int[200_001];
        // 같은 색을 갖는 집들을 하나의 그룹으로 볼 때
        // 같은 그룹원의 수를 갖는 그룹이 모두 몇 그룹인지 센다.
        // 예를 들어 색이 1인 집이 3개, 2인 집이 2개, 3인 집이 3개라면
        // 0, 0, 1, 2
        // 색이 2인 집이 2개이고 그러한 그룹이 하나
        // 색이 1, 3인 집들이 각각 3개 있고, 그러한 그룹이 (1, 3)으로 2개
        int[] appearCounts = new int[100_001];

        int[][] queries = new int[q][];
        // 모스 알고리즘을 통해 정렬한다.
        // 시작점에 대해서는 제곱근 오름차순 비교
        // 끝점에 대해서는 오름차순 비교
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            int sqrt1 = (int) Math.sqrt(queries[o1][0]);
            int sqrt2 = (int) Math.sqrt(queries[o2][0]);
            if (sqrt1 == sqrt2)
                return Integer.compare(queries[o1][1], queries[o2][1]);
            return Integer.compare(sqrt1, sqrt2);
        });
        for (int i = 0; i < queries.length; i++) {
            queries[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(value -> Integer.parseInt(value) - 1).toArray();
            priorityQueue.offer(i);
        }

        // 초기값으로 0번 집에 대해서 넣고 시작한다.
        int left = 0;
        int right = 0;
        int max = 1;
        appearCounts[++colorCounts[homes[0]]]++;

        int[] answers = new int[q];
        while (!priorityQueue.isEmpty()) {
            // 쿼리 번호
            int queryIdx = priorityQueue.poll();
            // 구간
            int[] query = queries[queryIdx];

            // right 포인터가 끝점보다 오른쪽에 있다면
            // 왼쪽으로 포인터를 이동시켜야한다.
            while (query[1] < right) {
                // right가 가르키고 있는 색의 그룹이 현재 최대 그룹이고
                // 유일한 그룹이라면
                // right가 right - 1로 이동하며, 개수가 하나 줄어들므로
                // max 값이 하나 줄어든다.
                if (max == colorCounts[homes[right]] && appearCounts[colorCounts[homes[right]]] == 1)
                    max = colorCounts[homes[right]] - 1;

                // right가 가르키는 색에 대한 그룹원의 수가 하나 줄어들 것이다.
                // 따라서 같은 그룹원을 갖는 그룹이 모두 몇 그룹이 있는지를 세는 appearCounts에 값도 하나 줄어든다.
                appearCounts[colorCounts[homes[right]]]--;
                // 그룹원의 수를 하나 줄이고
                colorCounts[homes[right]]--;
                // 줄어든 수의 그룹원의 개수는 증가한다.
                // 만약 4개의 색이었는데, 3개의 색으로 줄었다면
                // appearCounts[4]는 하나가 줄지만 appearCounts[3]은 하나가 늘어난다.
                appearCounts[colorCounts[homes[right]]]++;
                // 포인터 이동
                right--;
            }

            // 끝점보다 right가 작아 right를 우측으로 이동시키는 경우
            while (query[1] > right) {
                // right를 이동시키고
                right++;
                // right가 가르키는 색의 그룹원이 증가하므로
                // appearCounts는 하나 감소
                appearCounts[colorCounts[homes[right]]]--;
                // 그룹원 증가
                colorCounts[homes[right]]++;
                // 늘어난 그룹원에 대한 그룹 수는 증가.
                appearCounts[colorCounts[homes[right]]]++;
                // 만약 늘어난 그룹원의 수가 유일한 최대그룹원의 수라면
                // max값을 갱신.
                if (max < colorCounts[homes[right]])
                    max = colorCounts[homes[right]];
            }

            // 같은 작업을 left 포인터에 대해서도 행해준다.
            while (query[0] < left) {
                left--;
                appearCounts[colorCounts[homes[left]]]--;
                colorCounts[homes[left]]++;
                appearCounts[colorCounts[homes[left]]]++;
                if (max < colorCounts[homes[left]])
                    max = colorCounts[homes[left]];
            }

            while (query[0] > left) {
                if (max == colorCounts[homes[left]] && appearCounts[colorCounts[homes[left]]] == 1)
                    max = colorCounts[homes[left]] - 1;
                appearCounts[colorCounts[homes[left]]]--;
                colorCounts[homes[left]]--;
                appearCounts[colorCounts[homes[left]]]++;
                left++;
            }
            // max가 가르키고 있는 값이
            // 가장 많은 그룹원을 갖고 있는 그룹의 그룹원 수
            // 해당 값을 쿼리 답으로 등록
            answers[queryIdx] = max;
        }

        // 쿼리들에 대한 답을 순서대로 기록하여
        StringBuilder sb = new StringBuilder();
        for (int answer : answers)
            sb.append(answer).append("\n");
        // 전체 답안을 출력한다.
        System.out.print(sb);
    }
}