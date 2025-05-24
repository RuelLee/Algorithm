/*
 Author : Ruel
 Problem : Baekjoon 13168번 내일로 여행
 Problem address : https://www.acmicpc.net/problem/13168
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13168_내일로여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static String[] types = {"Subway", "Bus", "Taxi", "Airplane", "KTX", "S-Train", "V-Train", "ITX-Saemaeul", "ITX-Cheongchun", "Mugunghwa"};

    public static void main(String[] args) throws IOException {
        // 한국에는 n개의 도시가 있으며, 이 중 m개의 도시를 방문한다. 같은 도시를 중복하여 방문하는 것이 가능하다.
        // 두 도시를 오갈 수 있는 k개의 교통 수단이 있다.
        // 교통 수단의 종류는 무궁화호, ITX-새마을, ITX-청춘, KTX, S-train, V-train, 지하철, 버스, 택시, 비행기가 있다.
        // 내일로라는 특별한 기차 티켓을 R원에 산다면, 무궁화호, ITX-새마을, ITX-청춘는 무료, S-train, V-train는 50% 할인된 가격으로 이용할 수 있다.
        // 주어진 여행을 할 때, 내일로를 구매하는 것이 이득인지 판별하라
        //
        // 플로이드 워셜 문제
        // n이 최대 100으로 그리 크지 않다.
        // 플로이드 워셜을 통해 모든 경로에 대해, 내일로 티켓을 샀을 때와 사지 않았을 때를 모두 구하면 된다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, r원의 내일로 티켓 가격
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        
        // 도시를 해쉬맵을 통해 int로 변환
        HashMap<String, Integer> hashMap = new HashMap<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            String s = st.nextToken();
            if (!hashMap.containsKey(s))
                hashMap.put(s, hashMap.size());
        }
        
        // m개의 방문 도시
        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        // 여행 방문 도시 순서
        int[] order = new int[m];
        for (int i = 0; i < m; i++)
            order[i] = hashMap.get(st.nextToken());
        
        // 인접 행렬
        // adjMatrix[from][to][내일로] = from 도시에서 to 도시로 가는데 내일로 티켓을 샀는지 여부에 따른 최소 가격
        int[][][] adjMatrix = new int[hashMap.size()][hashMap.size()][2];
        for (int[][] from : adjMatrix) {
            for (int[] to : from)
                Arrays.fill(to, Integer.MAX_VALUE);
        }
        
        // 내일로를 샀을 때 무료인 운송수단
        HashSet<String> free = new HashSet<>();
        for (int i = 7; i <= 9; i++)
            free.add(types[i]);
        // 50% 할인되는 운송 수단
        HashSet<String> half = new HashSet<>();
        for (int i = 5; i <= 6; i++)
            half.add(types[i]);
        
        // k개의 운송 수단
        int k = Integer.parseInt(br.readLine());
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            int from = hashMap.get(st.nextToken());
            int to = hashMap.get(st.nextToken());
            // 비용이 홀수일 경우, 50% 할인하면, 버려지는 금액이 생길 수 있으므로
            // 운송 수단 비용과 나중에 누적할 내일로 티켓 가격 모두 2배 처리
            int cost = Integer.parseInt(st.nextToken()) * 2;

            adjMatrix[from][to][0] = adjMatrix[to][from][0] = Math.min(adjMatrix[from][to][0], cost);
            adjMatrix[from][to][1] = adjMatrix[to][from][1] = Math.min(adjMatrix[from][to][1], cost / (half.contains(type) ? 2 : 1) * (free.contains(type) ? 0 : 1));
        }
        
        // 플로이드 워셜
        for (int via = 0; via < adjMatrix.length; via++) {
            for (int start = 0; start < adjMatrix.length; start++) {
                if (start == via || adjMatrix[start][via][0] == Integer.MAX_VALUE)
                    continue;

                for (int end = 0; end < adjMatrix[start].length; end++) {
                    if (end == via || end == start || adjMatrix[via][end][0] == Integer.MAX_VALUE)
                        continue;

                    for (int i = 0; i < 2; i++)
                        adjMatrix[start][end][i] = Math.min(adjMatrix[start][end][i], adjMatrix[start][via][i] + adjMatrix[via][end][i]);
                }
            }
        }

        int noPassSum = 0;
        // 운송 수단의 비용이 홀수일 경우, 50% 감액 시, 오차가 생길 수있기 때문에
        // 운송 수단의 비용과 내일로 티켓 가격 모두 2배로 책정하여 계산
        int passSum = r * 2;
        for (int i = 0; i < order.length - 1; i++) {
            noPassSum += adjMatrix[order[i]][order[i + 1]][0];
            passSum += adjMatrix[order[i]][order[i + 1]][1];
        }
        
        // 내일로 티켓을 샀을 때, 총합이 낮은 경우만 Yes
        // 그 외의 경우 No 출력
        System.out.println(passSum < noPassSum ? "Yes" : "No");
    }
}