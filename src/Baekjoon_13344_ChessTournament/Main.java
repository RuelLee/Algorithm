/*
 Author : Ruel
 Problem : Baekjoon 13344번 Chess Tournament
 Problem address : https://www.acmicpc.net/problem/13344
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_13344_ChessTournament;

import java.util.*;

class Beat {
    int a;
    int b;

    public Beat(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beat)) return false;

        Beat beat = (Beat) o;

        if (a != beat.a) return false;
        return b == beat.b;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        return result;
    }
}

public class Main {
    static int[] parents;
    static int[] ranks;

    public static void main(String[] args) {
        // union-find와 위상정렬을 둘 다 사용해야하는 문제!
        // 먼저 같은 실력을 가진 사람끼리 한 그룹으로 묶는다
        // 그 후, 실력이 낮은 그룹에 대한 리스트를 만든다.
        // 실력이 높은 그룹을 먼저 꺼내고, 그룹원의 인원 수를 체크
        // 현 그룹이 이길 수 있는 그룹들에 대해 진입차수를 낮춰준다
        // 그 중 진입 차수가 사라진 그룹들은 큐에 넣어 위 사항을 반복해주자.
        // 최종적으로 체크된 꺼내진 그룹원들의 인원 수가 n과 일치하면 성립
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        init(n);
        Queue<Beat> afterSameGroup = new LinkedList<>();

        sc.nextLine();
        for (int i = 0; i < m; i++) {
            String[] split = sc.nextLine().split(" ");
            int a = Integer.parseInt(split[0]);
            int b = Integer.parseInt(split[2]);
            char c = split[1].charAt(0);

            if (c == '=')   // 같은 그룹에 대해 입력을 받고
                union(a, b);
            else        // '>' 연산에 대해서는 큐에 넣어 조금 미뤄놓자
                afterSameGroup.add(new Beat(a, b));
        }

        HashMap<Integer, HashSet<Integer>> sameGroup = new HashMap<>();
        for (int i = 0; i < parents.length; i++) {          // 같은 그룹끼리 HashSet으로 모아주자.
            if (sameGroup.containsKey(findParents(i)))
                sameGroup.get(findParents(i)).add(i);
            else {
                HashSet<Integer> hashSet = new HashSet<>();
                hashSet.add(i);
                sameGroup.put(findParents(i), hashSet);
            }
        }

        HashMap<Integer, HashSet<Integer>> canBeat = new HashMap<>();
        int[] inDegree = new int[n];
        HashSet<Beat> check = new HashSet<>();
        while (!afterSameGroup.isEmpty()) {         // '>' 연산에 대해 시작!
            Beat current = afterSameGroup.poll();

            if (!check.contains(new Beat(findParents(current.a), findParents(current.b)))) {        // a 그룹이 b그룹에 이긴다고 표시한 적이 없다면
                if (canBeat.containsKey(findParents(current.a)))        // current.a 그룹이 current.b 그룹에 대해서는 이길 수 있으므로, 해당 사항을 표시해주자
                    canBeat.get(findParents(current.a)).add(findParents(current.b));
                else {
                    HashSet<Integer> hashSet = new HashSet<>();
                    hashSet.add(findParents(current.b));
                    canBeat.put(findParents(current.a), hashSet);
                }
                inDegree[findParents(current.b)] += sameGroup.get(findParents(current.a)).size();       // 그리고 b그룹의 진입차수를 a그룹의 인원수만큼 늘려주자.
                check.add(new Beat(findParents(current.a), findParents(current.b)));            // 체크표시.
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i : sameGroup.keySet()) {      // 그룹에 대해서, 진입차수가 0인 그룹을 뽑자.
            if (inDegree[i] == 0)
                queue.add(i);
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            count += sameGroup.get(current).size();     // 진입차수가 0이므로, 현재 인원만큼은 리스트 가능! count로 인원 수 체크

            if (canBeat.containsKey(current)) {
                for (int cb : canBeat.get(current)) {       // 현 그룹이 이길 수 있는 그룹들의 진입차수를 현 그룹의 인원 수만큼 낮춰주자.
                    inDegree[cb] -= sameGroup.get(current).size();
                    if (inDegree[cb] == 0)      // 진입차수가 0이 되었다면 queueㄴ에 넣어 반복!
                        queue.add(cb);
                }
            }
        }
        System.out.println(count == n ? "consistent" : "inconsistent");
    }

    static void init(int n) {
        parents = new int[n];
        for (int i = 0; i < parents.length; i++)
            parents[i] = i;

        ranks = new int[n];
    }

    static int findParents(int n) {
        if (parents[n] == n)
            return n;

        return parents[n] = findParents(parents[n]);
    }

    static void union(int a, int b) {
        int pa = findParents(a);
        int pb = findParents(b);

        if (ranks[pa] > ranks[pb])
            parents[pb] = pa;
        else {
            parents[pa] = pb;
            if (ranks[pa] == ranks[pb])
                ranks[pb]++;
        }
    }
}