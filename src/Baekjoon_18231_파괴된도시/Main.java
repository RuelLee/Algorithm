/*
 Author : Ruel
 Problem : Baekjoon 18231번 파괴된 도시
 Problem address : https://www.acmicpc.net/problem/18231
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_18231_파괴된도시;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시, m개의 도로가 주어진다.
        // 폭탄으로 도시를 파괴시키면 직접적으로 도로로 연결된 도시들까지 같이 파괴된다고 한다.
        // m개의 파괴된 도시가 주어졌을 때
        // 폭탄으로 파괴된 도시의 개수와 번호를 출력하라
        // 그러한 경우가 여러가지라면 그 중 아무거나 한 가지를 출력한다.
        //
        // 그리디 문제
        // 파괴된 도시들에 한해, 해당 도시에서 폭탄을 폭파시킬 수 있는지 확인한다.
        // 다시말해 도로로 연결된 인근 도시들 또한 모두 파괴된 상태인지 확인한다.
        // 그 후, 가능한 모든 도시를 파괴시켰을 때
        // 파괴되지 않고 남은 도시가 존재하는지 확인한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 도시, m개의 도로
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 도로 상태
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connections.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            connections.get(u).add(v);
            connections.get(v).add(u);
        }
        
        // k개의 파괴된 도시
        int k = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        boolean[] destroyed = new boolean[n + 1];
        for (int i = 0; i < k; i++)
            destroyed[Integer.parseInt(st.nextToken())] = true;
        
        // 해당 도시와 인근 도시가 모두 파괴되어
        // 해당 도시에 폭탄을 폭파시킬 수 있는지 여부
        boolean[] possibilities = new boolean[n + 1];
        for (int i = 1; i < possibilities.length; i++) {
            if (destroyed[i]) {
                possibilities[i] = true;
                for (int near : connections.get(i)) {
                    if (!destroyed[near]) {
                        possibilities[i] = false;
                        break;
                    }
                }
            }
        }

        boolean[] results = new boolean[n + 1];
        StringBuilder sb = new StringBuilder();
        int count = 0;
        // 가능한 모든 도시에 폭탄을 폭파하며 개수르 센다.
        for (int i = 1; i < destroyed.length; i++) {
            if (possibilities[i]) {
                sb.append(i).append(" ");
                count++;
                results[i] = true;
                for (int near : connections.get(i))
                    results[near] = true;
            }
        }
        sb.insert(0, "\n").insert(0, count);

        boolean result = true;
        // 처음 주어진 파괴된 도시들이 모두 파괴가 되었는지 확인한다.
        for (int i = 1; i < results.length; i++) {
            if (destroyed[i]) {
                if (results[i] != destroyed[i]) {
                    result = false;
                    break;
                }
            }
        }
        // 모두 파괴되었다면 개수와 폭탄이 폭파된 도시들의 정보
        // 그렇지 않다면 -1을 출력
        System.out.println(result ? sb : -1);
    }
}