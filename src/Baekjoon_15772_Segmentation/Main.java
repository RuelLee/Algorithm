/*
 Author : Ruel
 Problem : Baekjoon 15772번 Segmentation
 Problem address : https://www.acmicpc.net/problem/15772
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15772_Segmentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    // 채널에 이용하는 온라인 유저를 12가지로 분류하고
    // 표와 같이 정리했다.
    // Champion              Loyal Customer      Loyal Customer      About to Leave      Can't Lose Them
    // Loyal Customer        Loyal Customer      Loyal Customer      About to Leave      About to Leave
    // Potential Loyalist    Potential Loyalist  Need Attention      About to Leave      About to Leave
    // Potential Loyalist    Potential Loyalist  About to Sleep      Hibernating         Lost
    // New Customer          Promising           About to Sleep      Lost                Lost
    // 가로축 기준은 recency이고, 세로축 기준은 frequency이며
    // 각각의 r1 r2 r3 r4, f1, f2, f3, f4 값이 주어진다.
    // r = 현재 시간이 t일 때, t - (유저의 최근 접속 시간)
    // f = 유저의 접속 횟수로 정의한다.
    // 단위 시간 마다
    // 쿼리가 주어지는데
    // 1 user -> user가 접속
    // 2 user -> user의 분류를 출력
    //
    // 해쉬 맵 문제
    // 분류들을 저장해두고, 표 또한 분류에 맞도록 저장해둔다.
    // 각 유저를 해쉬맵을 통해 idx로 바꾸고
    // 유저마다 가장 최근 접속 시간과 접속 횟수를 기록한다.
    // 2번 쿼리가 들어왔을 때, 현재 시간과 최근 접속 시간을 비교하여 r값을 구하고
    // 총 누적 접속 횟수를 통해 f값을 구해 분류해내면 된다.
    
    // 분류들
    static String[] classifications = {"New Customer", "Promising", "About to Sleep", "Hibernating", "Lost", "Potential Loyalist", "Need Attention", "About to Leave", "Champion", "Loyal Customer", "Can't Lose Them", "None"};
    // 분류표
    static int[][] classMap = new int[][]{
            {0, 1, 2, 4, 4},
            {5, 5, 2, 3, 4},
            {5, 5, 6, 7, 7},
            {9, 9, 9, 7, 7},
            {8, 9, 9, 7, 10}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // r의 기준값 4개
        int[] r = new int[5];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < r.length; i++)
            r[i] = Integer.parseInt(st.nextToken());
        
        // f의 기준값 4개
        int[] f = new int[5];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < f.length; i++)
            f[i] = Integer.parseInt(st.nextToken());

        // 해쉬맵을 통해 user 이름을 idx로 변환
        HashMap<String, Integer> userToIdx = new HashMap<>();
        // n번의 쿼리 처리
        int n = Integer.parseInt(br.readLine());
        // 각 유저의 최근 접속 시간과 총 누적 접속 횟수
        int[] recency = new int[n];
        int[] frequency = new int[n];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            // 쿼리
            int q = Integer.parseInt(st.nextToken());
            // 유저명
            String id = st.nextToken();
            
            // 1번 쿼리인 경우
            if (q == 1) {
                // 처음 방문한 유저라면 해쉬맵에 기록
                if (!userToIdx.containsKey(id))
                    userToIdx.put(id, userToIdx.size());
                // 해쉬맵에서 유저 idx를 가져옴
                int idx = userToIdx.get(id);
                // 최근 접속 시간 갱신 및 누적 접속 횟수 추가
                recency[idx] = i;
                frequency[idx]++;
            } else if (!userToIdx.containsKey(id))      // 접속하지 않은 유저의 분류를 하려고 하는 경우
                sb.append("None").append("\n");
            else {
                // 2번 쿼리인 경우
                // 유저의 idx
                int idx = userToIdx.get(id);
                // 해당하는 r값
                int rValue = 0;
                while (rValue + 1 < r.length && (i - recency[idx]) > r[rValue + 1])
                    rValue++;
                
                // 해당하는 f값
                int fValue = 0;
                while (fValue + 1 < f.length && frequency[idx] > f[fValue + 1])
                    fValue++;
                // 분류표에 맞는 분류를 기록
                sb.append(classifications[classMap[fValue][rValue]]).append("\n");
            }
        }
        // 전체 답 출력
        System.out.print(sb);
    }
}