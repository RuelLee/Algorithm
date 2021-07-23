/*
 Author : Ruel
 Problem : Baekjoon 9466번 텀 프로젝트
 Problem address : https://www.acmicpc.net/problem/9466
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 텀프로젝트;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 사이클이 어떻게 발생하는지 고민하는 것이 중요!
        // 1. 사이클이 발생하지 않는 경우.
        // 1 -> 2 -> 3 -> 4
        // 1에서 시작하든 2에서 시작하든, 3에서 시작하든 모두 사이클이 발생하지 않는다.
        // 모두 방문체크를 해주고 팀원의 숫자에는 포함하지 말자
        // 2. 시작점을 포함한 사이클이 발생하는 경우.
        // 1 -> 2 -> 3 -> 1
        // 1, 2, 3에서 어느 점에서 시작하든 3명의 팀이 생긴다.
        // 모두 방문 체크해주고, 3명을 팀을 이룬 인원에 포함하자
        // 3. 시작점 등 을 포함하지 않는 사이클이 발생한 경우.
        // 1 -> 2 -> 3 -> 4 -> 3
        // 1을 포함하지 않는 3, 4로만 사이클이 생겨났다.
        // 위 경우, 사이클이 생겨난 인원만 다시 체크하여 팀을 이룬 인원에 포함하고
        // 방문체크는 모두 해주자. 2에서 시작하더라도 어차피 2는 포함되지 않을 것이다.
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < T; testCase++) {
            int n = sc.nextInt();

            int[] select = new int[n];
            for (int i = 0; i < select.length; i++)
                select[i] = sc.nextInt() - 1;

            boolean[] check = new boolean[n];

            int count = 0;
            for (int i = 0; i < n; i++) {   // 순서대로 방문한다.
                if (!check[i])  //
                    count += isTeam(i, select, check);
            }
            sb.append(n - count).append("\n");
        }
        System.out.println(sb);
    }

    static int isTeam(int n, int[] select, boolean[] check) {
        HashSet<Integer> hashSet = new HashSet<>();     // n에서 시작한 팀원 찾기에서 방문한 인원을 표시할 HashSet
        hashSet.add(n);
        int current = select[n];    // n이 선택한 사람부터 시작해서, 다시 n으로 돌아오는지 살펴보자.
        // 다른 팀원 찾기에서 방문체크를 마친(check) 인원을 만났거나, (시작점을 포함하지 않는 사이클을 이미 발견했음)
        // 다시 n으로 돌아왔거나 (시작점을 포함한 사이클 발생)
        // 이번 팀원 찾기에서 방문했던 사람을 다시 마주친 경우(시작점을 포함하지 않는 사이클이 발생)
        while (!check[current] && current != n && !hashSet.contains(current)) {
            hashSet.add(current);
            current = select[current];
        }

        int count = 0;
        if (current == n)   // 시작점을 포함하므로 HashSet에 담겨있는 인원이 전부 팀원
            count = hashSet.size();
        // 시작점 + a를 포함하지 않는 사이클이 있다. 이는 while 반복이 멈춘 current로 시작했을 때 생기는 사이클이다.
        // 따라서 current를 시작점으로 하는 팀원찾기를 한번 더 재귀적으로 돌려서 인원을 세주자.
        else if (hashSet.contains(current))
            count = isTeam(current, select, check);

        for (int i : hashSet)   // 이번에 방문했던 모든 인원은 방문 체크를 해주자.
            check[i] = true;
        return count;
    }
}