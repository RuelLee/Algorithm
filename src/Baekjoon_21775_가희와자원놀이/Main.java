/*
 Author : Ruel
 Problem : Baekjoon 21775번 가희와 자원 놀이
 Problem address : https://www.acmicpc.net/problem/21775
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21775_가희와자원놀이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n명의 사람과 t장의 연산 카드, 1 ~ 2 x 10^9의 자원 카드가 주어진다.
        // 연산 카드에 대해
        // next : 아무 일도 일어나지 않고 이 카드를 버린다.
        // acquire n : n번 자원을 획득하려 시도한다. 공용 공간에 있다면 자신의 자원으로 끌어오고
        // 만약 다른 사람이 자원을 갖고 있다면, 해당 카드를 자기 손에 갖고 있다, 다음 자기 순서에 다시 시도한다.
        // relase n : n번 자원을 공용 공간으로 반납한다.
        // 자원 카드 n을 획득한 사람에게 acquire n 카드가 다시 주어지지 않으며
        // 획득하지 않은 자원을 release 하는 경우도 주어지지 않는다.
        // 각 턴마다 턴을 진행할 사람이 주어진다.
        // 카드는 위에서부터 순서대로 주어지되, 카드의 번호와 연산이 주어진다.
        // 모든 턴을 진행했을 때, 각 턴에 진행된 카드의 번호를 출력하라
        //
        // 시뮬레이션, 맵 문제
        // 주어진 조건대로 돌아가도록 만드는 시뮬레이션 문제
        // 다만 자원의 범위가 매우 넓으므로 맵을 통해 사용하는 자원들에 대한 정보만 관리한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n명의 사람, t장의 연산 카드
        int n = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        
        // 각 턴에 행동을 할 사람
        Queue<Integer> turn = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < t; i++)
            turn.offer(Integer.parseInt(st.nextToken()));
        
        // 각 카드의 정보
        int[][] cards = new int[t + 1][2];
        for (int i = 1; i < cards.length; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            cards[i][0] = num;
            String order = st.nextToken();
            if (order.equals("next"))
                continue;
            int resource = Integer.parseInt(st.nextToken());
            cards[i][1] = (order.equals("acquire") ? 1 : -1) * resource;
        }

        // 플레이어가 손에 카드를 쥐고 있다면 해당 카드의 idx를 저장한다.
        int[] players = new int[n + 1];
        // 자원이 플레이어에게 할당되었는지를 기록한다.
        HashMap<Integer, Integer> resources = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        // 맨 윗장의 카드부터
        int cardIdx = 1;
        while (!turn.isEmpty()) {
            // 이번 순서의 플레이어
            int currentPlayer = turn.poll();
            // 만약 손에 쥐고 있는 카드가 있다면
            if (players[currentPlayer] > 0) {
                // 해당 카드를 수행한다.
                sb.append(cards[players[currentPlayer]][0]).append("\n");
                // 만약 자원이 공용 공간에 있다면 자원을 자신에게 할당하고 카드를 버린다.
                if (resources.get(cards[players[currentPlayer]][1]) == 0) {
                    resources.put(cards[players[currentPlayer]][1], currentPlayer);
                    players[currentPlayer] = 0;
                }
            } else {        // 손에 쥐고 있는 카드가 없는 경우.
                // 카드가 acquire인 경우
                if (cards[cardIdx][1] > 0) {
                    // 처음 등장한 자원이거나, 공용 공간에 있는 경우
                    // 바로 자신에게 할당한다.
                    if (!resources.containsKey(cards[cardIdx][1]) ||
                            resources.get(cards[cardIdx][1]) == 0) {
                        resources.put(cards[cardIdx][1], currentPlayer);
                    } else      // 아닌 경우, 자신의 손으로 옮긴다.
                        players[currentPlayer] = cardIdx;
                } else if (cards[cardIdx][1] < 0)       // release 카드인 경우
                    resources.put(-cards[cardIdx][1], 0);
                // 해당 카드의 번호 기록
                sb.append(cards[cardIdx++][0]).append("\n");
            }
        }
        // 답 출력
        System.out.print(sb);
    }
}