/*
 Author : Ruel
 Problem : Baekjoon 14867번 물통
 Problem address : https://www.acmicpc.net/problem/14867
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 물통;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class State {
    int a;
    int b;
    int turn;

    public State(int a, int b, int turn) {
        this.a = a;
        this.b = b;
        this.turn = turn;
    }
}

public class Main {
    static HashMap<Integer, HashMap<Integer, Integer>> minTurn;

    public static void main(String[] args) {
        // 완전 탐색 시뮬레이션 문제
        // 다만 주어지는 값의 범위가 커서, 이를 배열로 방문 체크할 경우, 메모리가 터져버린다
        // 이중 해쉬맵을 사용하여 방문했을 때, 그 때의 턴을 기록해두었다.
        Scanner sc = new Scanner(System.in);

        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();
        minTurn = new HashMap<>();

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, 0, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (visited(current.a, current.b))          // 방문한 적이 있다면 패쓰
                continue;
            else        // 아니라면 방문 표시
                inputMinTurn(current.a, current.b, current.turn);

            if (current.a == c && current.b == d)   // 원하는 목표에 도달했다면 종료.
                break;

            if (!visited(a, current.b))     // a에 물을 채우는 경우
                queue.offer(new State(a, current.b, current.turn + 1));
            if (!visited(current.a, b))     // b에 물을 채우는 경우
                queue.offer(new State(current.a, b, current.turn + 1));

            if (!visited(current.a, 0))     // b의 물을 버리는 경우
                queue.offer(new State(current.a, 0, current.turn + 1));
            if (!visited(0, current.b))     // a의 물을 버리는 경우
                queue.offer(new State(0, current.b, current.turn + 1));

            int sum = current.a + current.b;
            if (sum > a) {      // 물의 총량이 a보다 클 때
                if (!visited(a, sum - a))       // b컵의 물을 a에 따르는 경우
                    queue.offer(new State(a, sum - a, current.turn + 1));
            } else if (!visited(sum, 0))        // 물의 총량이 a보다 같거나 작아 모두 따를 수 있는 경우
                queue.offer(new State(sum, 0, current.turn + 1));

            if (sum > b) {      // 물의 총량이 b보다 클 때
                if (!visited(sum - b, b))       // a컵의 물을 b에 따르는 경우
                    queue.offer(new State(sum - b, b, current.turn + 1));
            } else if (!visited(0, sum))        // 물의 총량이 b보다 같거나 작아 모두 따를 수 있는 경우.
                queue.offer(new State(0, sum, current.turn + 1));
        }
        System.out.println(!visited(c, d) ? -1 : minTurn.get(c).get(d));
    }

    static boolean visited(int a, int b) {      // 해쉬맵에 기록된 값을 보고 방문했는지 체크한다.
        // a key와 b key가 모두 존재한다면 방문한 것.
        return minTurn.containsKey(a) && minTurn.get(a).containsKey(b);
    }

    static void inputMinTurn(int a, int b, int turn) {      // 파라미터를 토대로 방문 체크를 해준다.
        if (!minTurn.containsKey(a)) {      // a key가 없다면, 해쉬맵을 생성하고, a 키로 삽입.
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            minTurn.put(a, hashMap);
        }

        if (!minTurn.get(a).containsKey(b))     // b key가 없다면, b 키로 turn 값을 삽입.
            minTurn.get(a).put(b, turn);
    }
}