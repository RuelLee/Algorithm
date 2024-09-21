/*
 Author : Ruel
 Problem : Baekjoon 4055번 파티가 좋아 파티가 좋아
 Problem address : https://www.acmicpc.net/problem/4055
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_4055_파티가좋아파티가좋아;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        // p개의 파티들이 주어지며, 파티들은 정각에 시작하고 끝난다.
        // 각 파티들의 시작과 종료 시간이 주어진다.
        // 각 파티에는 30분 이상 참가해야 참석으로 간주한다.
        // 최대한 많은 파티들에 참가하고자할 때, 그 수는?
        //
        // 그리디, 정렬 문제
        // 그리디 문제이나, 정렬 조건에 대해 조금 생각해야하는 문제
        // 파티들에 최대한 많이 참가하고자하므로, 종료시간에 대해 오름차순 정렬한다.
        // 종료 시간이 이른 파티들부터 우선 참석한다.
        // 각 파티에 30분이 이상 참석해야하므로 한 시간에 참석할 수 있는 파티는 최대 2개이다.
        // 이를 유의하며 그리디하게 문제를 해결한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input = br.readLine();
        int testCase = 0;
        while (!input.equals("0")) {
            testCase++;
            // p개의 파티
            int p = Integer.parseInt(input);
            int[][] parties = new int[p][];
            for (int i = 0; i < parties.length; i++)
                parties[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            // 파티들을 종료시간에 대해 오름차순 정렬
            Arrays.sort(parties, Comparator.comparingInt(o -> o[1]));
            
            // 참가한 파티의 수
            int count = 0;
            // 해당 시간에 참여한 파티의 수
            int[] times = new int[24];
            for (int[] party : parties) {
                // 파티가 열리는 시간 동안 참석할 수 있는지 살펴본다.
                for (int i = party[0]; i < party[1]; i++) {
                    // 비어있는 시간이 존재한다면
                    if (times[i] < 2) {
                        // 해당 시간에 참석 파티 수 증가
                        times[i]++;
                        // 전체 파티 참석 수 증가
                        count++;
                        break;
                    }
                }
            }
            // 최종적으로 참석한 모든 파티의 수 count를 바탕으로 답안 작성
            sb.append("On day " + testCase + " Emma can attend as many as " + count + " parties.").append("\n");
            input = br.readLine();
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}