/*
 Author : Ruel
 Problem : Baekjoon 1671번 상어의 저녁식사
 Problem address : https://www.acmicpc.net/problem/1671
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_1671_상어의저녁식사;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static List<List<Integer>> eatableSharks;
    static int[] matched;
    static int[] NumOfSharkEaten;
    static int[][] sharks;

    public static void main(String[] args) throws IOException {
        // n마리의 상어의 크기, 속도, 지능이 주어진다
        // 상어는 크기, 속도 지능이 모두 자신보다 작거나 같은 상어를 최대 2마리 먹을 수 있다
        // 살아남을 수 있는 상어의 최소 수는?
        // 이분 매칭 문제
        // 자신보다 능력치가 작거나 같은 상어들과 연결시킨 후, 최대 2마리를 매칭 시켜주자
        // 능력치 완전히 같을 경우, 순환이 일어나거나 서로가 서로를 잡아먹는 경우가 발생하면 안되므로,
        // 해당 경우에는 상어의 번호를 크거나 작은 순으로 일정하게 잡아먹는다고 가정하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 상어의 능력치들.
        sharks = new int[n + 1][3];
        StringTokenizer st;
        for (int i = 1; i < sharks.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                sharks[i][j] = Integer.parseInt(st.nextToken());
        }

        // 해당 상어가 먹을 수 있는 상어들
        eatableSharks = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            eatableSharks.add(new ArrayList<>());
        for (int i = 1; i < sharks.length; i++) {
            for (int j = 1; j < sharks.length; j++) {
                if (i == j)
                    continue;

                if (sharks[i][0] >= sharks[j][0] && sharks[i][1] >= sharks[j][1] && sharks[i][2] >= sharks[j][2])
                    eatableSharks.get(i).add(j);
            }
        }

        // 해당 상어가 먹힌 상대 상어를 저장.
        matched = new int[n + 1];
        // 해당 상어가 잡아먹은 다른 상어의 수.
        NumOfSharkEaten = new int[n + 1];
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j <= n; j++) {
                // 자신이 먹은 상어의 수가 2보다 적고, 매칭을 통해 잡아먹을 수 있는 상어를 찾는다면
                // 잡아먹은 상어의 수를 늘려준다.
                if (NumOfSharkEaten[j] < 2 && bipartiteMatching(j, new boolean[n + 1]))
                    NumOfSharkEaten[j]++;
            }
        }
        // 최종적으로 matched에는 자신이 먹힌 상어가 번호가 적혀있다
        // n개의 상어에서, 잡아먹힌 상어의 수를 빼주면, 살아남은 상어의 수가 나온다.
        System.out.println(n - Arrays.stream(matched).filter(value -> value > 0).count());
    }
    
    // 이분 매칭
    static boolean bipartiteMatching(int n, boolean[] visited) {
        // 방문 체크
        visited[n] = true;

        // n 상어가 먹을 수 있는 상어들.
        for (int shark : eatableSharks.get(n)) {
            // 만약 n번과 shark번 상어가 동일한 능력치지만, n < shark 인 경우에는 못 먹는다고 하자
            // 건너뛴다.
            if (sharks[n][0] == sharks[shark][0] && sharks[n][1] == sharks[shark][1] && sharks[n][2] == sharks[shark][2] && n < shark)
                continue;

            // shark가 아직 먹히지 않았거나, shark를 잡아먹은 상어에게 다른 상어를 먹도록 매칭이 가능하다면
            if (matched[shark] == 0 || (!visited[matched[shark]] && bipartiteMatching(matched[shark], visited))) {
                // shark는 n이 먹었다고 표시
                matched[shark] = n;
                // true 반환.
                return true;
            }
        }
        // 먹을 상어 매칭이 불가능한 경우.
        // false 리턴.
        return false;
    }
}