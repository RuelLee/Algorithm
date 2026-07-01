/*
 Author : Ruel
 Problem : Jungol 8229번 당구장
 Problem address : https://jungol.co.kr/problem/8229
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Jungol_8229_당구장;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 공과 집중력 x가 주어진다.
        // 각 공마다 떨어뜨리기 위한 집중력 소모치가 있고, 그 전에 떨어뜨려야하는 선행공이 주어진다. (없는 경우는 -1로 주어진다)
        // 가장 번호가 큰 공을 떨어뜨리고자할 때, 가능한 번호는?
        //
        // 트리, bfs 문제
        // 선행공이 하나만 주어지므로, 하나의 공에 여러개의 후행공이 붙는 트리 형태가 된다.
        // 선행공이 없는 경우는 해당 공만 쓰러뜨리면 되므로,
        // dp[번호] = 남은 집중력으로 계산해나간다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n개의 공, 플레이어의 처음 집중력
        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());

        // 공마다의 집중력 소모치
        int[] concentrations = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++)
            concentrations[i] = Integer.parseInt(st.nextToken());

        // 후행 공들
        List<List<Integer>> child = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            child.add(new ArrayList<>());
        long[] remainCon = new long[n + 1];
        Arrays.fill(remainCon, -1);
        Queue<Integer> queue = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            int preBall = Integer.parseInt(st.nextToken());
            // 선행 공이 없고
            if (preBall == -1) {
                // 초기 집중력이, i번 공의 집중력 소모치보다 같거나 큰 경우
                if (x >= concentrations[i]) {
                    // 남은 집중력을 기록하고 큐에 추가
                    remainCon[i] = x - concentrations[i];
                    queue.offer(i);
                }
            } else      // 그 외의 경우 후행공을 선행공에 기록
                child.get(preBall).add(i);
        }

        // bfs
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : child.get(current)) {
                if (remainCon[current] >= concentrations[next]) {
                    remainCon[next] = remainCon[current] - concentrations[next];
                    queue.offer(next);
                }
            }
        }

        // 집중력이 0이상인 공들은 떨어뜨릴 수 있는 공이므로
        // 해당하는 가장 큰 번호의 공을 찾는다.
        int answer = -1;
        for (int i = n; i > 0; i--) {
            if (remainCon[i] >= 0) {
                answer = i;
                break;
            }
        }
        // 답 출력
        System.out.println(answer);
    }
}