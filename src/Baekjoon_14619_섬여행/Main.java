/*
 Author : Ruel
 Problem : Baekjoon 14619번 섬 여행
 Problem address : https://www.acmicpc.net/problem/14619
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14619_섬여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[][] memo;
    static int[] heights;
    static List<List<Integer>> bridges;

    public static void main(String[] args) throws IOException {
        // n개의 섬의 높이, 섬들을 잇는 m개의 다리 정보가 주어진다.
        // a에서 k개의 다리를 이동하여 도착할 수 있는 가장 낮은 섬을 묻는 t개의 질의가 주어진다.
        // 해당 질의들에 답하라
        //
        // 메모이제이션 문제
        // memo[섬][이동해야하는다리횟수] = 도달할 수 있는 섬의 최소 높이
        // 로 정하고 계산한 결과를 다음 계산에 참조하는 형식으로 푼다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 섬, m개의 다리
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 각 섬의 높이
        heights = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        // 다리 정보
        bridges = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            bridges.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            bridges.get(x).add(y);
            bridges.get(y).add(x);
        }
        
        // 각 섬에서 다리 이용횟수에 따라 도달할 수 있는 섬의 최소 높이 기록
        memo = new int[n + 1][501];
        for (int[] mm : memo)
            Arrays.fill(mm, Integer.MAX_VALUE);

        // t개의 쿼리 처리
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            
            // 메모이제이션을 활용하여 계산
            int answer = findLowestInK(a, k);
            // answer가 초기값이라면 도달이 불가능한 경우 -1
            // 아니라면 해당 값을 기록
            sb.append(answer == Integer.MAX_VALUE ? -1 : answer).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    // island에서 k번 다리를 이용해 갈 수 있는 가장 낮은 섬의 높이를 찾는다.
    static int findLowestInK(int island, int k) {
        // 값이 계산되어있다면 해당 값 바로 반환.
        if (memo[island][k] != Integer.MAX_VALUE)
            return memo[island][k];
        // k가 0이 된다면, 해당 섬의 높이 반환.
        else if (k == 0)
            return memo[island][k] = heights[island - 1];
        
        // island에서 k번 이동하는 것은
        // next에서 k-1번 이동하는 것과 같음과 메모이제이션을 이용하여 계산.
        for (int next : bridges.get(island))
            memo[island][k] = Math.min(memo[island][k], findLowestInK(next, k - 1));
        
        // 계산된 결과값 반환
        return memo[island][k];
    }
}