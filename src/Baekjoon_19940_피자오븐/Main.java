/*
 Author : Ruel
 Problem : Baekjoon 19940번 피자 오븐
 Problem address : https://www.acmicpc.net/problem/19940
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_19940_피자오븐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static final int[] buttons = {60, 10, -10, 1, -1};

    public static void main(String[] args) throws IOException {
        // 피자를 굽는 오븐이 있다.
        // 굽는 시간을 조작하는 버튼은 5개가 있는데
        // ADDH : +60M
        // ADDT : +10M
        // MINT : -10M
        // ADDO : +1M
        // MINO : -1M
        // T개의 테스트케이스가 주어질 때
        // 각 목표 시간을 만드는데 최소로 버튼을 누르는 방법을 출력하라
        // 그러한 방법이 여러가지일 경우에는 앞쪽 버튼을 누르는 수가 적은 것을 우선한다.
        //
        // BFS 문제
        // 시간이 최대 1000만까지 주어지지만 생각을 해보면 60분까지만 구하면 된다.
        // 60, 120, 180, ..., 60*n의 경우, 무조건 60분 버튼을 누르는 것이 좋다.
        // 따라서 시간이 주어질 때, 해당 시간을 넘지 않는 선에서 60분 버튼을 최대한 누른 후
        // 나머지 시간에 대해서는 BFS를 통해 얻어진 결과를 합산한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트케이스
        int testCase = Integer.parseInt(br.readLine());
        // 0 ~ 60분까지에 대해서는 직접 BFS를 통해 계산한다.
        int[][] minButtons = new int[61][5];
        for (int[] mb : minButtons)
            Arrays.fill(mb, Integer.MAX_VALUE);
        minButtons[0] = new int[]{0, 0, 0, 0, 0};
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        // BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int d = 0; d < 5; d++) {
                int nextTime = Math.max(0, current + buttons[d]);
                minButtons[current][d]++;
                // 60분 이내이고,
                // 새로운 결과가 기존 결과보다 더 적은 수의 버튼을 누르거나
                // 사전순으로 더 앞설 때.
                if (nextTime < minButtons.length &&
                        doPrecede(minButtons[current], minButtons[nextTime])) {
                    minButtons[nextTime] = minButtons[current].clone();
                    queue.offer(nextTime);
                }
                minButtons[current][d]--;
            }
        }

        
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // 목표 시간
            int n = Integer.parseInt(br.readLine());

            // 60분 버튼은 n 이내에 누를 수 있는 만큼 최대한 누르고
            // 나머지는 BFS 결과에 따른다.
            sb.append(n / 60 + minButtons[n % 60][0]).append(" ");

            // 나머지 버튼들은 n % d에 해당하는 BFS 결과를 기록한다.
            for (int d = 1; d < 5; d++)
                sb.append(minButtons[n % 60][d]).append(" ");
            sb.deleteCharAt(sb.length() - 1).append("\n");
        }
        
        // 전체 답안 출력
        System.out.print(sb);
    }

    // 버튼 입력 결과가 주어질 때
    // 사전순으로 앞서는지 확인한다.
    static boolean doPrecede(int[] a, int[] b) {
        int aSum = Arrays.stream(a).sum();
        int bSum = Arrays.stream(b).sum();
        
        // 버튼 누른 횟수가 다르다면 더 적은 쪽을 골라내고
        // 결과값 반환
        if (aSum < bSum)
            return true;
        else if (aSum > bSum)
            return false;

        // 그렇지 않다면 버튼을 누른 횟수를 60분버튼부터 확인하며
        // 더 적은 쪽을 골라낸다.
        for (int i = 0; i < 5; i++) {
            if (a[i] < b[i])
                return true;
            else if (a[i] > b[i])
                return false;
        }

        // 모든 값이 같다면
        // 연산을 더 적게 하기 위해 값을 갱신하지 않도록
        // false를 반환한다.
        return false;
    }
}