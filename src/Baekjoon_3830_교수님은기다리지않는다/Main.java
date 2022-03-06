/*
 Author : Ruel
 Problem : Baekjoon 3830번 교수님은 기다리지 않는다
 Problem address : https://www.acmicpc.net/problem/3830
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3830_교수님은기다리지않는다;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int[] ranks;
    static long[] diffFromParents;

    public static void main(String[] args) throws IOException {
        // n개의 샘플이 존재하고 있으며, m개의 행동이 주어진다
        // ! a b c, ? a b의 형태로 주어지며
        // !는 a보다 b가 c만큼 더 무겁다 라는 의미이며
        // ?는 a보다 b가 얼마나 더 무겁냐라는 물음이다.
        // 이전까지 주어진 정보들로 a b의 차이를 알 수 없다면 UNKNOWN을 출력한다
        //
        // 분리 집합(union find) 문제인데 차이를 구해야하기 때문에 조금 생각을 요했다
        // 분리 집합으로 서로 간의 차이를 알 수 있는 집합을 나누고
        // diffFromParents라는 배열을 통해 parents로 부터의 크기 차이를 계산해 저장해둔다.
        // 그리고 쿼리가 들어왓을 때 같은 집합이라면 diffFromParents를 통해 차이를 계산하고
        // 다른 집합이라면 UNKNOWN을 출력해준다.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while (true) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            if (n == 0 && m == 0)
                break;
            init(n);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                char action = st.nextToken().charAt(0);     // 행동의 종류
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                if (action == '!') {        // 두 샘플의 차이를 알려주는 작업이라면
                    int c = Integer.parseInt(st.nextToken());
                    // union 메소드를 통해 두 집합을 하나로 묶고 c만큼의 차이를 반영한다.
                    union(a, b, c);
                } else {
                    // 차이를 물어보는 쿼리라면
                    // 같은 집합이라면, 차이를 계산하고
                    if (findParents(a) == findParents(b))
                        sb.append(diffFromParents[b] - diffFromParents[a]).append("\n");
                    // 다른 집합이라면 차이를 계산할 수 없으므로 UNKNOWN을 출력한다.
                    else
                        sb.append("UNKNOWN").append("\n");
                }
            }
        }
        System.out.println(sb);
    }

    static void union(int a, int b, int c) {        // 두 집합을 묶어주며, a보다 b가 c만큼 더 무겁다는 정보를 반영한다.
        // 연산을 줄이기 위해 두 그룹의 최초 부모를 구해 랭크를 따진다.
        int pa = findParents(a);
        int pb = findParents(b);

        //
        if (ranks[pa] >= ranks[pb]) {
            // a가 속한 그룹이 더 랭크가 크거나 같을 경우
            // b그룹을 a그룹에 합친다.
            parents[pb] = pa;
            // 그리고 pa와 pb의 차이를 계산해서 pb에 반영시켜줘야한다(b그룹의 최초 부모가 pa가 됐기 때문)
            // pa ~ pb의 차이는 pa ~ a ~ c(=pa ~ b) 까지의 차이에서 pb ~ b까지의 차이를 빼주면 된다.
            diffFromParents[pb] = diffFromParents[a] + c - diffFromParents[b];
            // 랭크가 같았을 경우 a그룹의 랭크 증가.
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else {
            // 반대일 경우 a그룹을 b그룹에 합친다.
            parents[pa] = pb;
            // pb ~ pa의 차이는
            // pb ~ b에서 pa ~ a ~ c(= pa ~ b)를 빼준다.
            diffFromParents[pa] = diffFromParents[b] - c - diffFromParents[a];
        }
    }

    static int findParents(int n) {     // 부모를 찾아주는 메소드
        if (parents[n] == n)
            return n;       // 자기 자신이 최초라면 그대로 n 리턴
        // 아니라면 최초 부모 pn을 찾고
        int pn = findParents(parents[n]);
        // 이전 부모 n과 pn의 차이를 diffFromParents[n]에 더해준다.
        diffFromParents[n] += diffFromParents[parents[n]];
        // 그리고 n의 부모로 pn을 표시해준다.
        return parents[n] = pn;
    }

    static void init(int n) {
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        diffFromParents = new long[n + 1];
    }
}