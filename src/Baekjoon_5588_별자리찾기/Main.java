/*
 Author : Ruel
 Problem : Baekjoon 5588번 별자리 찾기
 Problem address : https://www.acmicpc.net/problem/5588
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_5588_별자리찾기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // m개의 별로 이루어진 별자리가 주어진다.
        // 각각의 별의 위치가 주어진다.
        // n개의 별로 이루어진 밤하늘의 사진이 주어진다.
        // 각각의 별 좌표가 주어진다.
        // 원하는 별자리를 찾기 위해선
        // 별자리 좌료를 사진 속 좌표로 변환하기 위해 변환해야하는 양을 구하라
        //
        // 브루트 포스, 해쉬맵
        // 별자리에서의 첫번째 별과 사진에서의 모든 별을 하나씩 매칭해보며
        // 별자리엔 있는 별이 사진에 없다면 다음 사진의 별과 별자리의 첫번째 별을 매칭하는 방법을 계속해나간다.
        // m이 최대 200, n이 최대 1000이므로 무리 없이 할 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 별자리에 속한 별 m개
        int m = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] stars = new int[m][2];
        for (int i = 0; i < stars.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < stars[i].length; j++)
                stars[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 사진에 나온 별 n개
        // 해당 별들은 첫번째 별과 매칭되는 별로써의 정보와
        // 나머지 별로써 해당 위치 별이 존재하는지 여부를 확인할 때 필요하다.
        // 따라서 배열과 해쉬맵 두가지 형태로 저장해둔다.
        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][2];
        HashMap<Integer, HashSet<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            map[i][0] = x;
            map[i][1] = y;

            if (!hashMap.containsKey(x))
                hashMap.put(x, new HashSet<>());
            hashMap.get(x).add(y);
        }

        int[] answer = new int[2];
        // 첫번째 별과 매칭될 사진에서의 기준 별
        for (int[] criteria : map) {
            // 변환량
            int xDiff = criteria[0] - stars[0][0];
            int yDIff = criteria[1] - stars[0][1];

            // 별자리 별을 모두 매칭할 수 있는가.
            boolean possible = true;
            // 별자리 별들이 모두 매칭될수 있는지 확인
            for (int[] star : stars) {
                // 하나의 별이라도 x좌표 값이 없거나, y좌표 값이 없는 경우
                // 불가능하므로 possible false 후, break
                if (!hashMap.containsKey(star[0] + xDiff) ||
                        !hashMap.get(star[0] + xDiff).contains(star[1] + yDIff)) {
                    possible = false;
                    break;
                }
            }
            // 모든 별에 대해 매칭이 됐다면
            // 가능한 경우이므로
            // 해당 변환량을 기록 후 반복문 종료
            if (possible) {
                answer[0] = xDiff;
                answer[1] = yDIff;
                break;
            }
        }
        // 답 출력
        System.out.println(answer[0] + " " + answer[1]);
    }
}