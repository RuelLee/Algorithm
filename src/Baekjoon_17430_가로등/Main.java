/*
 Author : Ruel
 Problem : Baekjoon 17430번 가로등
 Problem address : https://www.acmicpc.net/problem/17430
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17430_가로등;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 가로등이 2차원 공간에 배치되어있다.
        // i번째 가로등의 위치는 (xi, yi)이다.
        // 두 가로등 i와 j(i<j)가 있을 때, (xi, yj), (xj, yi)에 가로등이 있으면 가로등 i와 j가 균형 잡혀있다고 한다.
        // 모든 가로등 쌍이 균형잡혀있는지 아닌지 구하라
        //
        // 해쉬 맵
        // 그냥 임의의 하나 가로등이 다른 모든 가로등에 대해 균형 잡혀있는지를 살펴보면 되는 문제이다.
        // 이 때, 균형 여부를 판단하기 위해 해쉬 맵과 해쉬 셋으로 해당 좌표에 가로등이 있음을 표시한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // t개의 테스트 케이스
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < t; testCase++) {
            // 해쉬 맵과 해쉬 셋을 이용하여 해당 좌표에 가로등이 있음을 표현
            HashMap<Integer, HashSet<Integer>> hashMap = new HashMap<>();
            int n = Integer.parseInt(br.readLine());
            // 가로등의 좌표
            int[][] streetLamps = new int[n][2];
            StringTokenizer st;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 2; j++)
                    streetLamps[i][j] = Integer.parseInt(st.nextToken());

                if (!hashMap.containsKey(streetLamps[i][0]))
                    hashMap.put(streetLamps[i][0], new HashSet<>());
                hashMap.get(streetLamps[i][0]).add(streetLamps[i][1]);
            }
            
            // 임의의 가로등(0번)과 다른 모든 가로등이 균형 잡혀있는지 판별
            boolean possible = true;
            for (int i = 1; i < streetLamps.length; i++) {
                // x좌표가 같거나, y좌표가 같다면 두 가로등만으로도 균형이 잡힘.
                if (streetLamps[0][0] == streetLamps[i][0] ||
                        streetLamps[0][1] == streetLamps[i][1])
                    continue;
                
                // 균형이 잡히지 않은 경우.
                // 해당 사항 기록 후 반복문 종료
                if (!hashMap.get(streetLamps[0][0]).contains(streetLamps[i][1]) ||
                        !hashMap.get(streetLamps[i][0]).contains(streetLamps[0][1])) {
                    possible = false;
                    break;
                }
            }
            
            // 가로등들이 균형 잡혀있다면 BALANCED
            // 그렇지 않다면 NOT BALANCED 기록
            sb.append(possible ? "BALANCED" : "NOT BALANCED").append("\n");
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}