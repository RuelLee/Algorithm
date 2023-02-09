/*
 Author : Ruel
 Problem : Baekjoon 22252번 정보 상인 호석
 Problem address : https://www.acmicpc.net/problem/22252
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22252_정보상인호석;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 정보 상인들에게서 정보를 구매하려고 한다
        // 어느 정보 상인에게 b개의 정보를 구매한다고 하면, 해당 정보 상인이 갖고 있는 가장 비싼 정보를 b개 산다.
        // q개의 쿼리가 주어진다.
        // 1 Name k c1 c2 c3 ... ck
        // 이름이 Name인 정보 상인이 k개의 정보를 갖고 있으며 각 가치는 c1, ..., ck이다.
        // 2 Name b
        // Name 정보 상인에게서 b개의 정보를 산다.
        // 모든 쿼리를 마쳤을 때, 구매 비용을 출력하라
        //
        // 해쉬맵과 우선순위큐
        // 를 이용한 문제. 각 정보 상인이 String으로 주어지고, 정보들은 내림차순으로 접근한다.
        // 따라서 이를 빠르게 이용하기 위해 해쉬맵과 우선순위큐를 사용하여 정보 상인들과 정보들을 담는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine());
        
        // key값으로 정보상인의 이름, value로 우선순위큐를 갖는 해쉬맵
        HashMap<String, PriorityQueue<Integer>> merchants = new HashMap<>();
        // 정보들의 총 비용
        long sum = 0;
        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 1번 쿼리인 경우
            if (Integer.parseInt(st.nextToken()) == 1) {
                // 정보 상인 이름
                String merchant = st.nextToken();
                // 이 해쉬맵에 등록되지 않았다면 등록.
                // 당연히 우선순위큐는 내림차순으로 보기 위해 최대힙 우선순위큐
                if (!merchants.containsKey(merchant))
                    merchants.put(merchant, new PriorityQueue<>(Comparator.reverseOrder()));
                
                // k개의 정보들을 해당하는 정보상인의 우선순위큐에 추가.
                int k = Integer.parseInt(st.nextToken());
                for (int j = 0; j < k; j++)
                    merchants.get(merchant).offer(Integer.parseInt(st.nextToken()));
            } else {        // 2번 쿼리인 경우
                String merchant = st.nextToken();
                // 만약 해쉬맵에 등록되어있지 않다면 당연히 정보도 없다.
                // 건너뛴다.
                if (!merchants.containsKey(merchant))
                    continue;
                // b개의 정보를 구매한다.
                int b = Integer.parseInt(st.nextToken());
                // 우선순위큐가 비거나 b개의 정보를 구매할 때까지
                // 우선순위큐에서 하나씩 수들을 뽑아 sum에 더해주고
                // b를 낮춰간다.
                while (!merchants.get(merchant).isEmpty() && b > 0) {
                    sum += merchants.get(merchant).poll();
                    b--;
                }
            }
        }

        // 전체 비용 출력
        System.out.println(sum);
    }
}