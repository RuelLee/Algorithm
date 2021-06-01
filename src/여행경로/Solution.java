package 여행경로;

import java.util.Arrays;

class Ticket implements Comparable<Ticket> {
    String origin;
    String destination;

    public Ticket(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public int compareTo(Ticket o) {
        return this.destination.compareTo(o.destination);
    }
}

public class Solution {
    static boolean finished;

    public static void main(String[] args) {
        // 시작은 ICN.
        // 모든 항공권을 사용하되, 가능한 경로가 여러가지인 경우, 알파벳 순으로 더 빠른 것을 채용한다.
        // 티켓들을 도착지 기준으로 정렬하자 -> 가장 먼저 만들어지는 경우가 알파벳순으로 가장 이른 순.
        // 끝났는지 여부를 알려줄 finished 정적 변수.
        String[][] tickets = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}};
        Ticket[] tickets1 = Arrays.stream(tickets).map(strings -> new Ticket(strings[0], strings[1])).toArray(Ticket[]::new);
        Arrays.sort(tickets1);

        finished = false;
        String[] answer = new String[tickets.length + 1];
        answer[0] = "ICN";
        boolean[] check = new boolean[tickets.length];
        dfs(tickets1, 0, check, answer);
        System.out.println(Arrays.toString(answer));
    }

    static void dfs(Ticket[] tickets1, int turn, boolean[] check, String[] answer) {    // dfs로 탐색해나가자
        if (finished || turn >= tickets1.length) {      // 다른 함수에서 연산이 끝났거나, 이번 함수로써 answer 가 모두 채워졌다면, 재귀 종료.
            finished = true;
            return;
        }

        for (int i = 0; i < tickets1.length; i++) {
            if (!finished && !check[i] && tickets1[i].origin.equals(answer[turn])) {    // 다른 함수에서도 끝나지 않았고, 사용하지 않은 티켓이며, 출발지가 전 티켓의 도착지일 때
                check[i] = true;
                answer[turn + 1] = tickets1[i].destination;
                dfs(tickets1, turn + 1, check, answer);
                check[i] = false;
            }
        }
    }
}