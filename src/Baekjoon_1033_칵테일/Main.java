/*
 Author : Ruel
 Problem : Baekjoon 1033번 칵테일
 Problem address : https://www.acmicpc.net/problem/1033
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1033_칵테일;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<List<Integer>> connections;
    static long[] ratio;

    public static void main(String[] args) throws IOException {
        // 칵테일에 10이하의 자연수 n 종류만큼의 재료가 들어간다.
        // 재료 쌍 n - 1개의 비율을 알아냈고, 이를 통해 전체 재료의 비율을 알아낼 수 있다.
        // 비율은 a b p q 형태이고
        // 들어가는 a와 b의 비율이 p : q이라는 뜻이다.
        // 이 때 칵테일을 만드는데 필요한 재료의 양을 구하라
        // 각 재료의 양은 정수이고, 총 질량은 0보다 커야한다.
        //
        // BFS, 유클리드 호제법
        // 전체 비율을 n-1개의 쌍으로 알아낼 수 있으므로
        // 결국 위 형태는 트리 형태를 띄게 된다.
        // 따라서 이전에 등장하지 않은 두 재료의 비율이라면 해당 재료의 비율로 정하고
        // 한쪽이 이전에 등장했다면, 등장하지 않은 쪽을 등장한 쪽의 비율에 맞춘다.
        // 둘 다 이전에 등장했고, a : b = p : q의 비율이라면
        // a와 연결된 재료들에는 b * q배, b와 연결된 재료들에게는 a * p배를 해주면
        // 두 재료의 비율이 p : q가 되게 된다.
        // 최종적으로 구한 재료 비율의 값이 배수가 되어서는 안되므로
        // 유클리드 호제법을 통해 최소화해주자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n개의 재료
        int n = Integer.parseInt(br.readLine());
        connections = new ArrayList<>();
        for (int i = 0; i < n; i++)
            connections.add(new ArrayList<>());
        
        // 비율
        ratio = new long[n];
        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 재료 a와 재료 b의 비율은 p : q
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            
            // 둘 다 이전에 등장하지 않았다면
            if (ratio[a] + ratio[b] == 0) {
                // p : q로 지정
                ratio[a] = p;
                ratio[b] = q;
            } else if (ratio[a] == 0) {
                // b가 이미 등장했었다면
                // b가 q의 배수인지 체크하고
                // 그렇다면 a만 b의 p/q배 해주고
                if (ratio[b] % q == 0)
                    ratio[a] = ratio[b] / q * p;
                else {      // 그렇지 않다면 a는 b의 p배
                    ratio[a] = ratio[b] * p;
                    // b는 q배해준다.
                    multiple(b, q);
                }
            } else if (ratio[b] == 0) {
                // 위 경우의 반대인 경우
                if (ratio[a] % p == 0)
                    ratio[b] = ratio[a] / p * q;
                else {
                    ratio[b] = ratio[a] * q;
                    multiple(a, p);
                }
            } else {
                // 양쪽 다 등장했었다면
                long tempA = ratio[a];
                // a에 대해서는 ratio[b] * p배
                multiple(a, ratio[b] * p);
                // b에 대해서는 ratio[a] * q배를 하여
                multiple(b, tempA * q);
                // 서로 간의 비율을 p : q로 만들어준다.
            }
            // 두 재료의 연결 상태 추가
            connections.get(a).add(b);
            connections.get(b).add(a);
        }

        if (n != 1) {
            // 재료가 2개 이상인 경우에는
            // 비율의 값을 최소화하기 위해 최소공배수를 구하여
            // 각각 나눠준다.
            long gcd = getGCD(ratio[0], ratio[1]);
            for (int i = 2; i < ratio.length; i++)
                gcd = getGCD(gcd, ratio[i]);
            for (int i = 0; i < ratio.length; i++)
                ratio[i] /= gcd;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ratio.length; i++)
            sb.append(ratio[i]).append(" ");
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);

    }

    // BFS
    // idx와 연결된 재료들의 비율에 multi배를 한다.
    static void multiple(int idx, long multi) {
        boolean[] visited = new boolean[connections.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(idx);
        while (!queue.isEmpty()) {
            int current = queue.poll();

            ratio[current] *= multi;
            for (int next : connections.get(current)) {
                if (!visited[next])
                    queue.offer(next);
            }
            visited[current] = true;
        }
    }

    // 유클리드 호제법
    // a와 b의 최소공배수를 구한다.
    static long getGCD(long a, long b) {
        long max = Math.max(a, b);
        long min = Math.min(a, b);
        while (min > 0) {
            long temp = max % min;
            max = min;
            min = temp;
        }
        return max;
    }
}