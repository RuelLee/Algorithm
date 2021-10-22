/*
 Author : Ruel
 Problem : Baekjoon 17471번 게리맨더링
 Problem address : https://www.acmicpc.net/problem/17471
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 게리맨더링;

import java.util.*;

public class Main {
    static int[] population;
    static List<HashSet<Integer>> connection;
    static final int MAX = 1001;

    public static void main(String[] args) {
        // 게리맨더링
        // 도시들이 서로 연결된 간선이 주어질 때, 인구수의 합의 차가 최소인 두 구역으로 나눈다.
        // 각 구역의 도시들은 서로 연결이 되어있어야한다.
        // 조합을 사용해서 한 구역의 도시들을 뽑는다(안 뽑힌 그룹은 자연스럽게 다른 구역)
        // 이 두 구역의 도시들이 서로 연결되어있는지 체크한다.
        // 연결이 되어있다면, 각 구역 인구의 합을 구하고 차를 구해 최소값을 갱신해준다.

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        population = new int[n + 1];
        for (int i = 1; i < n + 1; i++)
            population[i] = sc.nextInt();

        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new HashSet<>());
        for (int i = 0; i < n; i++) {
            int num = sc.nextInt();
            for (int j = 0; j < num; j++) {
                int b = sc.nextInt();
                connection.get(i + 1).add(b);
                connection.get(b).add(i + 1);
            }
        }
        int answer = dfs(0, 1, 0);
        System.out.println(answer == MAX ? -1 : answer);
    }

    static int dfs(int bitmask, int turn, int selected) {       // dfs를 활용하여 조합으로 구현
        if (turn > population.length) {     // turn이 도시의 수를 넘어간다면 끝
            if (dividedIntoTwo(bitmask)) {      // bitmask된 값을 보고 두 구역으로 나뉘었는지 체크한다.
                return calcPopDiff(bitmask);        // 그렇다면 두 구역 인구의 차를 리턴한다.
            } else
                return MAX;     // 그렇지 않다면 MAX 값을 리턴한다.
        }

        int min = MAX;      // 최소값은 MAX로 세팅해주고 turn 도시를 한 구역에 포함했을 때와 다른 구역에 포함했을 때, 둘 중 최소 값을 가져와 반환한다.
        min = Math.min(min, dfs((bitmask | (1 << turn)), turn + 1, selected + 1));      // 한 구역에 포함했을 때
        min = Math.min(min, dfs(bitmask, turn + 1, selected));      // 다른 구역에 포함했을 때
        return min;
    }

    static int calcPopDiff(int bitmask) {       // bitmask를 보고 구역의 인구 합의 차를 구한다.
        int[] groupPop = new int[2];
        for (int i = 1; i < population.length; i++)
            groupPop[((bitmask & (1 << i)) == 0 ? 0 : 1)] += population[i];

        return Math.abs(groupPop[0] - groupPop[1]);
    }

    static boolean dividedIntoTwo(int bitmask) {        // 두 구역으로 나뉘었는지 확인한다.
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> zeroTeam = new HashSet<>();
        for (int i = 1; i < connection.size(); i++) {
            if ((bitmask & (1 << i)) == 0) {        // bitmask 값이 0인 한 도시를 선택한다.
                queue.offer(i);
                zeroTeam.add(i);
                break;
            }
        }
        while (!queue.isEmpty()) {      // 그 도시로부터 BFS를 수행하며 연결되어있는 bitmask가 0인 도시들을 추가해나간다.
            int current = queue.poll();
            for (int next : connection.get(current)) {
                if (!zeroTeam.contains(next) && (bitmask & (1 << next)) == 0) {
                    queue.offer(next);
                    zeroTeam.add(next);
                }
            }
        }
        HashSet<Integer> oneTeam = new HashSet<>();
        for (int i = 1; i < connection.size(); i++) {
            if ((bitmask & (1 << i)) != 0) {        // bitmask가 1인 한 도시를 선택
                queue.offer(i);
                oneTeam.add(i);
                break;
            }
        }
        while (!queue.isEmpty()) {      // 마찬가지로 bfs로 bitmask가 1인 도시들을 추가해나간다.
            int current = queue.poll();
            for (int next : connection.get(current)) {
                if (!oneTeam.contains(next) && (bitmask & (1 << next)) != 0) {
                    queue.offer(next);
                    oneTeam.add(next);
                }
            }
        }
        // 각각의 구역의 도시의 개수가 0이어선 안되고, 두 구역 도시들의 개수 합 또한 전체 도시 개수와 같아야한다.
        return !zeroTeam.isEmpty() && !oneTeam.isEmpty() && zeroTeam.size() + oneTeam.size() == connection.size() - 1;
    }
}