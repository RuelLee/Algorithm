/*
 Author : Ruel
 Problem : Baekjoon 1963번 소수 경로
 Problem address : https://www.acmicpc.net/problem/1963
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 소수경로;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class State {
    int number;
    int cycle;

    public State(int number, int cycle) {
        this.number = number;
        this.cycle = cycle;
    }
}

public class Main {
    public static void main(String[] args) {
        // BFS 문제
        // 특정 소수에서 다른 소수로 변환하는데, 한번에 한 숫자만 바꿀 수 있으며, 이에 거쳐가는 숫자들 또한 소수여야한다
        // 소수임을 판별할 수 있어야하고, 각 자리 별 숫자를 변경할 수 있어야한다
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < t; tc++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            Queue<State> queue = new LinkedList<>();
            queue.offer(new State(a, 0));       // 원래 숫자를 cycle 0 으로 큐에 넣는다.
            HashSet<Integer> hashSet = new HashSet<>();
            hashSet.add(a);         // 해쉬셋에 표시
            int answer = Integer.MAX_VALUE;
            while (!queue.isEmpty()) {
                State current = queue.poll();
                if (current.number == b) {      // 목적한 숫자에 도달했다면, 기록된 cycle이 정답
                    answer = current.cycle;
                    break;
                }

                for (int pow = 0; pow < 4; pow++) {     // 1의 자리부터, 1000의 자리까지
                    int targetNum = (current.number / (int) Math.pow(10, pow)) % 10;        // 해당 숫자가 무엇인지 알아내고

                    for (int i = 1; i <= targetNum; i++) {
                        if (pow == 3 && i == targetNum)     // 만약 천의 자리일 경우, 0이 되는 걸 막는다
                            continue;

                        // 해당 자리 숫자보다 작은 숫자들 * 10 ^ pow을 빼주면서, 특정 자리의 숫자에 0 ~ targetNum -1으로 바꿔 숫자를 만든다.
                        // ex) pow = 1, current.number = 1171일 때 -> 1161(1171 - 10), 1151(1171 - 20),,, 1101(1171 - 70)
                        int changedNum = current.number - i * (int) Math.pow(10, pow);
                        if (isPrimeNumber(changedNum) && !hashSet.contains(changedNum)) {           // 소수이며 이전에 계산된 적이 없다면
                            queue.offer(new State(changedNum, current.cycle + 1));          // 큐에 넣어주고
                            hashSet.add(changedNum);        // 해쉬셋에 표시해준다.
                        }
                    }

                    for (int i = 1; i < 10 - targetNum; i++) {      // 해당 자리의 숫자가 9가 될 때까지
                        // 10 ^ pow 값을 더해주며
                        // ex) pow = 1, current.number == 1171일 때 -> 1181(1171 + 10), 1191(1171 + 20)
                        int changedNum = current.number + i * (int) Math.pow(10, pow);
                        if (isPrimeNumber(changedNum) && !hashSet.contains(changedNum)) {       // 마찬가지로 소수와 중복 여부를 판별
                            queue.offer(new State(changedNum, current.cycle + 1));
                            hashSet.add(changedNum);
                        }
                    }
                }
            }
            // answer 값이 그대로 Integer.MAX_VALUE 값이라면 답이 없는 경우.
            // 값이 바뀐 경우는 answer이 답!
            sb.append(answer == Integer.MAX_VALUE ? "Impossible" : answer).append("\n");
        }
        System.out.println(sb);
    }

    static boolean isPrimeNumber(int n) {
        if (n == 1)
            return false;

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}