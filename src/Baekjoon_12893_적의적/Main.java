/*
 Author : Ruel
 Problem : Baekjoon 12893번 적의 적
 Problem address : https://www.acmicpc.net/problem/12893
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12893_적의적;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 적의 적은 친구라는 가설을 세웠다.
        // 하지만 친구 관계가 복잡하여 적의 적이더라도 나의 적이 될 수도 있다.
        // n명과 m개의 적대 관계가 주어질 때, 가설이 성립하는지 확인하라
        //
        // 분리집합 문제
        // 한 사람의 적을 모두 같은 팀으로 묶는다.
        // 그 후, 그 한 사람과 적 중 하나가 같은 팀이 된다면 모순이 발생하는 것이므로
        // 가설이 성립하지 않는 것이 된다.
        // 반대로 같은 팀이 되지 않는다면 가설이 성립한다고 볼 수 있다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 분리 집합을 위한 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];
        
        // 적대 관계
        List<List<Integer>> hostility = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            hostility.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            hostility.get(a).add(b);
            hostility.get(b).add(a);
        }
        
        // 가설 성립 여부
        boolean possible = true;
        for (int i = 0; i < hostility.size(); i++) {
            // 적대 관계가 없다면 건너뛴다.
            if (hostility.get(i).isEmpty())
                continue;

            // i와 적대 관계인 모두를 한 팀으로 묶는다.
            for (int j = 1; j < hostility.get(i).size(); j++)
                union(hostility.get(i).get(0), hostility.get(i).get(j));

            // i와 적 중 하나가 같은 팀으로 묶이는 모순이 발생했는지 확인한다.
            // 그렇다면 가설이 성립하지 않고, 반복문을 통한 모순 찾기를 종료한다.
            if (findParent(i) == findParent(hostility.get(i).get(0))) {
                possible = false;
                break;
            }
        }

        // 가설이 성립하는지 출력한다.
        System.out.println(possible ? 1 : 0);
    }

    // a와 b를 한 팀으로 묶는다.
    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (ranks[pa] >= ranks[pb]) {
            parents[pb] = pa;
            if (ranks[pa] == ranks[pb])
                ranks[pa]++;
        } else
            parents[pa] = pb;
    }

    // n이 속한 팀의 대표를 출력한다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}