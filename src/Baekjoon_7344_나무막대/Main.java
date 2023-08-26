/*
 Author : Ruel
 Problem : Baekjoon 7344번 나무 막대
 Problem address : https://www.acmicpc.net/problem/7344
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_7344_나무막대;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스마다
        // n개의 나무 막대들에 대한 길이와 무게가 주어지며, 이 막대들을 가공한다.
        // 가공하는 기계는 첫 막대에 대해서 1분의 준비 시간이 필요하며
        // 이후 막대에 대해서는 이전 막대보다 길이와 무게가 모두 크거나 같다면 
        // 준비 시간 필요없이 바로 가공을 할 수 있다고 한다.
        // 모든 막대를 가공하는데 필요한 최소 준비 시간은?
        //
        // 정렬, 그리디 문제
        // 먼저 길이에 대해 정렬, 길이가 같다면 무게에 대해 정렬을 한다.
        // 그 후 순차적으로 살펴보며, 길이와 무게가 위 조건에 부합한다면 준비 시간을 증가시키지 않고
        // 가공하는 과정을 반복한다.
        // 이는 기계를 재정비하는 시간 혹은 첫번째 막대를 가공하는 시간이 동일하기 때문에
        // 따로 기계를 재정비하지 않고, 가공할 수 있는 막대를 우선 가공한 뒤
        // 가공되지 않은 막대는 새로운 첫번째 막대로 생각하는 것과 같다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트케이스의 개수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // n개의 막대
            int n = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[][] sticks = new int[n][2];
            for (int i = 0; i < sticks.length; i++) {
                for (int j = 0; j < sticks[i].length; j++)
                    sticks[i][j] = Integer.parseInt(st.nextToken());
            }

            // 먼저 길이 순, 길이가 같다면 무게 순을호 정렬한다.
            Arrays.sort(sticks, (o1, o2) -> {
                if (o1[0] == o2[0])
                    return Integer.compare(o1[1], o2[1]);
                return Integer.compare(o1[0], o2[0]);
            });
            
            // 가공한 막대의 수
            int count = 0;
            // 한 사이클 = 첫번째 막대를 위한 준비 시간
            int cycle = 0;
            boolean[] finished = new boolean[n];
            while (count < n) {
                // 현재까지 가공한 최대 길이와 무게
                int l = 0;
                int w = 0;
                for (int i = 0; i < sticks.length; i++) {
                    // 이미 가공했다면 건너뛰기
                    if (finished[i])
                        continue;
                    
                    // i번째 막대의 길이가 l보다 크거나 같고
                    // 무게가 w보다 크거나 같다면
                    if (sticks[i][0] >= l && sticks[i][1] >= w) {
                        // 가공한다.
                        finished[i] = true;
                        // 길이와 무게 기록
                        l = sticks[i][0];
                        w = sticks[i][1];
                        // 가공한 막대 개수 추가
                        count++;
                    }
                }
                // 한 사이클 추가.
                cycle++;
            }
            // 모든 막대를 가공하는데 소요된 사이클 기록
            // = 총 소모된 준비 시간
            sb.append(cycle).append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}