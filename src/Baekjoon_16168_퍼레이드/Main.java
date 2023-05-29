/*
 Author : Ruel
 Problem : Baekjoon 16168번 퍼레이드
 Problem address : https://www.acmicpc.net/problem/16168
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16168_퍼레이드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // v개의 지점 e개의 연결 구간이 주어진다.
        // 지점은 중복 방문이 가능하지만, 연결 구간은 한 번만 지나야한다.
        // e개의 연결 구간을 모두 지나는 경로를 만들 수 있는가?
        //
        // 분리 집합, 오일러 경로 문제
        // 흔히 말하는 한 붓 그리기 문제
        // 먼저 모든 지점들이 연결되어있는가를 판별하기 위해 분리 집합을 사용한다.
        // 그리고 모든 지점을 한 붓으로 그릴 수 있는가를 오일러 경로로 판단한다.
        // 오일러 경로는 모든 지점들의 진입, 진출 차수가 짝수이거나
        // 시작과 끝 지점의 진입, 진출 차수가 홀수인 경우이다.
        // 연결 구간들의 입력을 받으며 계산하여 처리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // v개의 지점, e개의 연결 구간
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        
        // 분리 집합을 위한 초기 세팅
        parents = new int[v + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[v + 1];
        
        // 진입 or 진출 차수
        int[] inOrOut = new int[v + 1];
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // a와 b의 진입, 진출 차수 증가
            inOrOut[a]++;
            inOrOut[b]++;

            // 서로 다른 집합이라면 연결이 되므로 하나의 집합으로 묶는다.
            if (findParent(a) != findParent(b))
                union(a, b);
        }

        // 진입출 차수가 홀수인 것의 개수를 센다.
        int oddCounter = 0;
        // 분리 집합을 토해 모든 정점들이 연결되는지 확인한다.
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 1; i < parents.length; i++) {
            hashSet.add(findParent(i));
            if (inOrOut[i] % 2 == 1)
                oddCounter++;
        }

        boolean answer = false;
        // 모든 정점들이 하나의 집합이고(= 모두 연결이 되고)
        // 진입출 차수가 홀수인 정점의 개수가 0 혹은 2개라면
        // 한 붓 그리기가 가능하다.
        if (hashSet.size() == 1 && (oddCounter == 0 || oddCounter == 2))
            answer = true;

        // 결과 출력
        System.out.println(answer ? "YES" : "NO");
    }

    // union 연산
    // a와 b를 하나의 집합으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pb] = pa;
    }

    // n의 집합을 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}