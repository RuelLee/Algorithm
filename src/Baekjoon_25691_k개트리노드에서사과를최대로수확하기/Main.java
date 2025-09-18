/*
 Author : Ruel
 Problem : Baekjoon 25691번 k개 트리 노드에서 사과를 최대로 수확하기
 Problem address : https://www.acmicpc.net/problem/25691
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_25691_k개트리노드에서사과를최대로수확하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> child;
    static int[] apples;

    public static void main(String[] args) throws IOException {
        // n개의 노드와 n-1개의 간선으로 이루어진 트리t가 주어진다.
        // 노드들의 루트는 0번이며, 각 n-1번까지의 번호가 붙어있다.
        // 각 노드에는 하나의 사과가 달려있거나 달려있지 않다.
        // 루트 노드에 시작하여 최대 k개의 노드를 방문하며 얻을 수 있는 최대 사과를 구하라
        // 같은 노드를 중복하여 방문할 수 있다.
        //
        // 브루트 포스, 비트마스킹 문제
        // n과 k가 최대 17로 그리 크지 않으므로 직접 모든 경우의 수를 계산할 수 있다.
        // 단, 같은 노드를 중복하여 방문할 수 있다는 것은, 한 노드의 자식 노드들을 복수개 접근할 수 있다는 의미이다.
        // 따라서 자식 노드들을 방문할 때마다 결과값을 병합해주는 과정이 필요하다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 노드, 최대 방문 노드 제한 k
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // 자식 노드
        child = new ArrayList<>();
        for (int i = 0; i < n; i++)
            child.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            child.get(Integer.parseInt(st.nextToken())).add(Integer.parseInt(st.nextToken()));
        }
        
        // 각 노드의 사과가 달려있는지 여부
        apples = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < apples.length; i++)
            apples[i] = Integer.parseInt(st.nextToken());
        
        // 최종 결과값
        HashMap<Integer, Integer> hashMap = bruteForce(0, 1, apples[0]);
        // 비트마스킹으로 방문 노드가 처리되어있으므로
        // 비트의 수가 k개 이하인 것들 중 최대값을 찾는다.
        int answer = 0;
        for (int key : hashMap.keySet()) {
            if (Integer.bitCount(key) <= k)
                answer = Math.max(answer, hashMap.get(key));
        }
        // 답 출력
        System.out.println(answer);
    }
    
    // idx를 방문한 상태의 bitmask와 현재 사과의 apple
    static HashMap<Integer, Integer> bruteForce(int idx, int bitmask, int apple) {
        // 현재 노드에서 파생될 수 있는 경우의 수을 해쉬맵에 저장
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        // 현재 상태
        hashMap.put(bitmask, apple);
        // 자식 노드들 방문
        for (int next : child.get(idx)) {
            // 자식 노드 방문 결과
            HashMap<Integer, Integer> returned = bruteForce(next, bitmask | (1 << next), apple + apples[next]);
            // 새로운 newHashMap에 hashMap과 returned의 병합 결과를 저장
            HashMap<Integer, Integer> newHashMap = new HashMap<>();
            // returned의 bitmask key2
            for (int key2 : returned.keySet()) {
                // hashMap의 bitmask key1
                // 두 비트를 or 연산으로 병합한 뒤, 
                // 중복되는 사과의 수(=조상 노드의 사과의 수 = bitmask일 때의 사과의 수 = apple)를 제외한 사과의 수를 저장
                for (int key1 : hashMap.keySet())
                    newHashMap.put(key1 | key2, hashMap.get(key1) + returned.get(key2) - apple);
                // 그리곤 key2 자체의 값도 저장
                newHashMap.put(key2, returned.get(key2));
            }
            // newHashMap에 저장된 결과를 hashMap으로 모두 옮겨줌.
            hashMap.putAll(newHashMap);
        }
        return hashMap;
    }
}