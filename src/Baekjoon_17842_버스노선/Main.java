/*
 Author : Ruel
 Problem : Baekjoon 17842번 버스 노선
 Problem address : https://www.acmicpc.net/problem/17842
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_17842_버스노선;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 버스 노선을 정리하고자 한다.
        // 1. 출발 정류장과 도착 정류장이 있다.
        // 2. 어떤 버스 노선이 k개의 정류장을 방문하면, 각 정류장 사이에는 도로가 존재해야한다.
        // 3. 같은 정류장을 여러번 방문해서는 안된다.
        // 도로는 트리 형태를 띄고 있다.
        // 모든 도로에 적어도 하나 이상의 버스 노선이 지나가도록 버스 노선을 구성하고자할 때
        // 필요한 버스 노선의 최소 수는?
        //
        // 트리 문제
        // 노선의 최소 개수가 필요하므로
        // 하나의 노선은 무조건 하나의 단말 노드에서 다른 단말노드로 이동해야한다.
        // 따라서 모든 단말노드의 개수를 세고,
        // 짝수라면 그 수의 반 만큼의 노선으로, 홀수라면 해당 수 /2 + 1만큼의 노선으로 커버 가능하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 정류장
        int n = Integer.parseInt(br.readLine());
        
        // 각 정류장에 연결된 도로들
        int[] connections = new int[n];
        StringTokenizer st;
        // 단말 노드의 수
        int leafNodes = 0;
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++) {
                // node에 연결된 도로가 존재한다.
                int node = Integer.parseInt(st.nextToken());

                // node에 연결된 도로가 유일하다면 단말 노드
                if (connections[node]++ == 0)
                    leafNodes++;
                else if (connections[node] == 2)        // 그렇지 않다면 단말 노드가 아니다.
                    leafNodes--;
            }
        }
        
        // (단말 노드의 수 + 1) / 2 가 필요한 최소 노선의 개수
        System.out.println((leafNodes + 1) / 2);
    }
}