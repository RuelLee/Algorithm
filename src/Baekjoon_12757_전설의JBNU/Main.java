/*
 Author : Ruel
 Problem : Baekjoon 12757번 전설의 JBNU
 Problem address : https://www.acmicpc.net/problem/12757
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12757_전설의JBNU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    static TreeMap<Integer, Integer> treeMap;
    static int k;

    public static void main(String[] args) throws IOException {
        // key를 통해 value에 접근하는 데이터베이스가 있다.
        // key의 값이 부정확할 수 있으므로, 어느 정도 보정을 하여 쿼리를 처리하고 싶다.
        // key는 최대 k의 범위 내에서 원래 값에 가장 가까운 값으로 보정이 된다.
        // 초기에 n 쌍의 key와 value가 주어지고, m개의 쿼리가 주어질 때, 처리 결과를 출력하라
        // 다음은 쿼리 형태이다.
        // 1 key value : 해당 key와 value 값을 추가한다.
        // 2 key value : 해당 key의 값을 value로 변경한다. 만족하는 유일한 key가 없을 경우 무시한다.
        // 3 key : 해당 key로 검색된 데이터를 출력한다. 만족하는 key가 없는 경우 -1, 두 개 이상 존재한다면 ?를 출력한다
        //
        // 트리 맵
        // 을 사용하면 어렵지 않게 풀 수 있는 문제.
        // 먼저 key가 주어지면, 해당 key가 존재하는지 확인하고
        // 존재하지 않는다면, 해당 key보다 작지만 가장 큰 key, 해당 key보다 크지만 가장 작은 key
        // 를 가져와 두 개의 차이를 비교하고, 차이가 더 적고, k보다 같거나 작다면 해당 값으로 key 값을 보정한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 초기 데이터, m개의 쿼리, 보정 최대 범위 k
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        treeMap = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            treeMap.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            int key = Integer.parseInt(st.nextToken());
            // 1번 쿼리인 경우
            // key value 추가
            if (order == 1)
                treeMap.put(key, Integer.parseInt(st.nextToken()));
            else if (order == 2) {      
                // 2번 쿼리인 경우
                int value = Integer.parseInt(st.nextToken());
                // 보정된 키
                int adjustedKey = findKey(key);
                // 키가 0보다 작다면 무시
                if (adjustedKey < 0)
                    continue;
                // 그 외의 경우, 보정된 키 값에 연결된 값을 value로 수정
                treeMap.put(adjustedKey, value);
            } else {
                // 3번 쿼리인 경우
                int adjustedKey = findKey(key);
                
                // 보정된 키 값이 -1인 경우.
                // 존재하지 않는 경우. -1 기록
                if (adjustedKey == -1)
                    sb.append(-1);
                else if (adjustedKey == -2)     // 해당하는 키가 복수인 경우. 2 기록
                    sb.append("?");
                else    // 그 외의 경우엔 해당하는 value 값 기록
                    sb.append(treeMap.get(adjustedKey));
                sb.append("\n");
            }
        }
        // 전체 답안 출력
        System.out.print(sb);
    }

    static int findKey(int key) {
        // 보정이 필요없이 딱 맞는 key가 존재한다면 해당 key 값 반환
        if (treeMap.containsKey(key))
            return key;
        
        // key보다 작지만 가장 큰 다음 key
        int lower = treeMap.lowerKey(key) != null ? treeMap.lowerKey(key) : -1;
        // key보다 크지만 가장 작은 다음 key
        int higher = treeMap.higherKey(key) != null ? treeMap.higherKey(key) : -1;

        // lower가 -1이 아니고
        if (lower != -1) {
            int lowerDIff = (key - lower);
            if (higher != -1) {     // higher 또한 -1이 아닌 경우
                int higherDiff = (higher - key);
                // 키 값의 차이가 같은 경우는 해당하는 보정 키가 복수인 경우
                // -2 반환
                if (lowerDIff == higherDiff)
                    return -2;
                
                // lower 키가 더 가깝고 차이가 k이하인 경우
                if (lowerDIff < higherDiff && lowerDIff <= k)
                    return lower;
                // higher 키가 더 가깝고 차이가 k 이하인 경우
                else if (higherDiff < lowerDIff && higherDiff <= k)
                    return higher;
            } else if (lowerDIff <= k)      // lower만 -1이 아닌데, 차이가 k이하이라면 lower 반환
                return lower;
        } else if (higher != -1 && (higher - key) <= k)     // higher만 -1이 아니고, 차이가 k이하인 경우 higher 반환
            return higher;

        // 어느 경우도 속하지 않아 key 값을 찾지 못한 경우
        // -1 반환
        return -1;
    }
}