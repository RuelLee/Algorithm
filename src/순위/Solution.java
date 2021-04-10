package 순위;

import java.util.*;

class Player {
    Set<Integer> winSet = new HashSet<>();  // 자신이 이긴 상대를 저장해둘 set
    Set<Integer> defeatSet = new HashSet<>();   // 자신이 진 상대를 저장해둘 set
    boolean[] winCheck;     // 내가 이긴 상대에 대해 그 상대가 이긴 상대까지 체크했는지 표시할 공간.
    boolean[] defeatCheck;  // 내가 진 상대에 대해 그 상대가 진 상대까지 체크했는지 표시할 공간.

    public Player(int n) {
        winCheck = new boolean[n + 1];
        defeatCheck = new boolean[n + 1];
    }
}

public class Solution {
    public static void main(String[] args) {
        // 자신이 이긴 상대가 이긴 상대는 결과적으로 자신이 이길 수 있다.
        // 자신 보다 강한 상대들과 자신 보다 약한 상대들을 모두 찾아내어, 합이 n-1 값으면 자신의 순위를 정할 수 있다.

        int n = 5;
        int[][] results = {{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}};

        Player[] players = new Player[n + 1];
        for (int i = 1; i < players.length; i++)
            players[i] = new Player(n);

        for (int[] record : results) {      // 승부 결과를 각각 플레이어의 set에 기록시켜두자.
            players[record[0]].winSet.add(record[1]);
            players[record[1]].defeatSet.add(record[0]);
        }

        for (int i = 1; i < players.length; i++) {  // 한 플레이어마다 돌아가며
            Player player = players[i];

            Queue<Integer> winQueue = new LinkedList<>(player.winSet);

            Queue<Integer> defeatQueue = new LinkedList<>(player.defeatSet);


            while (!winQueue.isEmpty()) {       // player가 이긴 opponent를 하나씩 방문하며
                int opponent = winQueue.poll();

                if (!player.winCheck[opponent]) {   // 이미 방문했던 opponent라면 지나가자.
                    player.winSet.addAll(players[opponent].winSet);     // oppoent가 이긴 사람들은 player보다 약하므로, player의 약한 사람 set에 추가해두자.
                    player.winCheck[opponent] = true;       // 한번 체크한 상대는 다시 체크 안해도 되므로, boolean 배열에 체크해두자.
                    winQueue.addAll(players[opponent].winSet);  // 다음에 방문할 winQueue에 opponent가 이긴 상대도 넣어 방문하도록하자.

                    players[opponent].defeatSet.addAll(player.defeatSet);   // opponent 입장에서는 player에게 약한 것이므로, opponent의 강한 사람 set에 player를 추가해두자.
                    players[opponent].defeatCheck[i] = true;    // opponent 차례에 player를 방문하더라도 이번에 이미 set에 추가해뒀으므로 연산하지 않게 boolean 배열에 체크해두자.
                }
            }
            while (!defeatQueue.isEmpty()) {    // player가 진 opponent에 대해서 위와 같이 반복한다.
                int opponent = defeatQueue.poll();

                if (!player.defeatCheck[opponent]) {
                    player.defeatSet.addAll(players[opponent].defeatSet);
                    player.defeatCheck[opponent] = true;
                    defeatQueue.addAll(players[opponent].defeatSet);

                    players[opponent].winSet.addAll(player.winSet);
                    players[opponent].winCheck[i] = true;
                }
            }
        }
        int answer = 0;
        for (int i = 1; i < players.length; i++) {      // 각 player를 방문하며, 자신보다 강한 상대 + 약한 상대 값이 n-1인 경우에는 자신의 순위를 확정할 수 있다.
            if (players[i].winSet.size() + players[i].defeatSet.size() == n - 1)
                answer++;
        }
        System.out.println(answer);
    }
}