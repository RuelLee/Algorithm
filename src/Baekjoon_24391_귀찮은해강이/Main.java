/*
 Author : Ruel
 Problem : Baekjoon 24391번 귀찮은 해강이
 Problem address : https://www.acmicpc.net/problem/24391
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_24391_귀찮은해강이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[] parents, ranks;

    public static void main(String[] args) throws IOException {
        // 1 ~ n번까지의 강의들이 주어지고, 각 강의들은 번호와 동일한 건물에서 진행한다.
        // 몇몇 건물들은 서로 연결이 되어있어서 밖으로 나오지 않을 수 있다.
        // m개의 연결된 건물쌍과, n개의 강의코드로 이루어진 강의 시간표가 주어질 때
        // 건물 밖으로 나와야하는 횟수를 출력하라
        //
        // 분리집합 문제
        // 연결된 건물끼리 한 그룹으로 묶고
        // 시간표를 살펴보며 밖으로 나와야하는 횟수를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 분리집합을 위한 parents와 ranks 배열 초기화
        parents = new int[n + 1];
        for (int i = 1; i < parents.length; i++)
            parents[i] = i;
        ranks = new int[n + 1];

        // m개의 연결된 건물 쌍이 주어진다.
        // 한 그룹으로 묶는다.
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        
        // 강의 스케쥴
        int[] schedule = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        // 이전 강의와 비교하여 서로 같은 건물에서 진행하는지 확인하고
        // 그렇지 않다면 count 증가
        for (int i = 1; i < schedule.length; i++) {
            if (findParent(schedule[i - 1]) != findParent(schedule[i]))
                count++;
        }

        System.out.println(count);
    }

    // a, b를 하나의 그룹으로 묶는다.
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

    // n이 속한 그룹의 대표를 찾는다.
    static int findParent(int n) {
        if (parents[n] == n)
            return n;
        return parents[n] = findParent(parents[n]);
    }
}