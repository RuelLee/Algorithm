/*
 Author : Ruel
 Problem : Baekjoon 1867번 돌멩이 제거
 Problem address : https://www.acmicpc.net/problem/1867
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 돌멩이제거;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<List<Integer>> stones;
    static int[] colSelected;

    public static void main(String[] args) {
        // 돌을 하나씩 탐색해가며, row, col 어느쪽도 선택이 안되어있다면, 양쪽을 선택하고, 추후에 동일 row, col에 돌이 나타났을 때, 처음 선택했던 둘 다 중 겹치지 않는 하나를 없애는 작업 반복.
        // row, col 이 선택된 개수의 합에서 양쪽 다 선택된 돌의 개수를 뺀 값이 답이라 생각했지만 실패했다.
        // 이분매칭으로 row를 하나의 정점, col을 하나의 정점으로 판단해야하는 문제였다.
        // 생각을 조금 바꿔보자
        // 각 row에서 중복되지 않는 col을 선택하도록 하자(= 이분 매칭)
        // 이 때 각각 row의 경우를 생각해보면
        // 1. 자신이 하나의 col을 선택했고, 자신에게 속한 다른 col들이 전부 다른 row에 속한 경우
        // -> 해당 col을 따라서 돌을 줍는다.
        // 2. 선택한 col이 없는 경우
        // -> 자신에게 속한 돌들이 모두 다른 row에서 선택한 col에 따라 돌이 모두 제거됐다.
        // 3. 자신이 하나의 col을 선택했지만, 다른 row에게 속하지 않는 col이 있는 경우
        // ->  다른 row에 속한 col은 해당 row에서 처리해줄 것이고, 현재 row에서 선택한 col과 다른 row에 선택받지 못한 col이 있을 것이다.
        // -> 이는 현재 row를 선택한 걸로 보고 row를 따라 돌을 주으며 모두 처리해준다.
        // 문제만 보고서는 언뜻 이분매칭이 떠오르지 않지만, 이분매칭인 문제였다.

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int p = sc.nextInt();
        init(n, p, sc);

        int answer = 0;
        for (int i = 1; i < n + 1; i++) {
            if (dfs(i, new boolean[n + 1]))
                answer++;
        }
        System.out.println(answer);
    }

    static boolean dfs(int r, boolean[] check) {
        if (check[r])
            return false;

        check[r] = true;
        for (int col : stones.get(r)) {
            if (colSelected[col] == 0) {
                colSelected[col] = r;
                return true;
            }
        }

        for (int col : stones.get(r)) {
            if (dfs(colSelected[col], check)) {
                colSelected[col] = r;
                return true;
            }
        }
        return false;
    }

    static void init(int n, int p, Scanner sc) {
        colSelected = new int[n + 1];

        stones = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            stones.add(new ArrayList<>());
        for (int i = 0; i < p; i++)
            stones.get(sc.nextInt()).add(sc.nextInt());
    }
}