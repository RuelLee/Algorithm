/*
 Author : Ruel
 Problem : Baekjoon 14267번 회사 문화 1
 Problem address : https://www.acmicpc.net/problem/14267
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_14267_회사문화1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> child;
    static int[] praises;

    public static void main(String[] args) throws IOException {
        // 1번부터 n번까지의 직원들이 주어지고, 1번은 사장이다.
        // 이 회사는 내리칭찬 문화가 있는데, 자신이 받은 칭찬을 부하직원들에게 칭찬해주는 문화이다.
        // 자신의 상사와, 각 직원이 받은 칭찬이 주어질 때
        // 모든 직원들이 최종적으로 받은 칭찬은 얼마인지 구하라
        //
        // 트리에서의 DP 문제
        // 일단 각 자리에서 받은 칭찬들을 기록해두고
        // 사장에서부터 자식노드를 타고 내려가며, 자신이 받은 칭찬만큼을 부하 직원에게도 더해주면 된다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 자신의 상사
        int[] parents = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 상사로부터 부하 직원들을 찾자.
        child = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            child.add(new ArrayList<>());
        // i번 직원의 상사가 parents[i]라면, parents[i]의 부하 직원이 i
        for (int i = 1; i < parents.length; i++)
            child.get(parents[i] - 1).add(i);

        // 각 직원의 칭찬 받은 정도
        praises = new int[n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            praises[Integer.parseInt(st.nextToken()) - 1] += Integer.parseInt(st.nextToken());
        }

        // 사장으로부터 내려가며 총 받은 칭찬을 계산한다.
        calcPraises(0);

        // 칭찬량을 출력.
        StringBuilder sb = new StringBuilder();
        for (int praise : praises)
            sb.append(praise).append(" ");
        System.out.println(sb);
    }

    // 자기가 받은 칭찬을 부하 직원에게 내려보낸다.
    static void calcPraises(int loc) {
        // 자신의 모든 부하 직원들에게
        for (int node : child.get(loc)) {
            // 자신이 받은 칭찬만큼을 내려보낸다.
            praises[node] += praises[loc];
            // 그리고 부하 직원에게도 재귀적으로 메소드를 호출한다.
            calcPraises(node);
        }
    }
}