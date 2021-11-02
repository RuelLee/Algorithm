/*
 Author : Ruel
 Problem : Baekjoon 1781번 컵라면
 Problem address : https://www.acmicpc.net/problem/1781
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 컵라면;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class CupNoodle {
    int deadLine;
    int value;

    public CupNoodle(int deadLine, int value) {
        this.deadLine = deadLine;
        this.value = value;
    }
}

public class Main {
    static int[] vacantTurn;
    static int[] turnValue;

    public static void main(String[] args) throws IOException {
        // 그리디 알고리즘
        // 각각의 문제마다 기한과 보상이 주어진다
        // 어떤 문제를 우선적으로 푸는 것이 최대 보상을 얻을 수 있는가에 대한 문제
        // 단순히 turn을 증가시키면서 turn이 지나지 않은 문제 중 가장 보상이 큰 것부터 푼다고 계산하면 안된다!
        // 각 턴마다 모두 풀 문제를 채울 수 있음에도, 비는 경우가 발생할 수 있다.
        // 예를 들어 기한/보상이 1/1, 2/4, 3/5가 주어지고 위와 같이 푼다면 답이 9가 나온다. 사실은 턴마다 모두 문제를 배정해서 10이여야한다
        // 따라서 각 턴마다 자신보다 같거나 작은 턴 중에 아직 문제가 할당되지 않은 턴을 저장해두자.
        // 그리고 우선순위큐에서 가장 보상이 큰 문제를 꺼내 해당문제를 최대한 데드라인과 가깝게 할당한다.
        // 이런 식으로 차근차근 할당하여 모든 턴에 문제가 가득차거나 더 이상 문제가 없다면 종료한다.
        // 그 후 턴에 할당된 문제들의 보상의 총합을 구하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        PriorityQueue<CupNoodle> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.value, o1.value));      // 보상에 따라 내림차순으로 정렬한다.
        int maxDeadLine = 0;
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int deadLine = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            maxDeadLine = Math.max(maxDeadLine, deadLine);      // 최대 기한을 저장해둬서 배열을 할당할 때 사용할 것이다.
            priorityQueue.offer(new CupNoodle(deadLine, value));
        }

        vacantTurn = new int[maxDeadLine + 1];      // 자신보다 같거나 작은 턴들 중 아직 비어있는 곳을 나타낼 것이다.
        turnValue = new int[maxDeadLine + 1];       // 해당 turn에 할당된 문제의 보상을 저장할 것이다.
        for (int i = 1; i < vacantTurn.length; i++)     // 아직 할당되기 전이므로 각 턴에는 자기 자신 값을 할당한다.
            vacantTurn[i] = i;

        while (!priorityQueue.isEmpty()) {
            while (!priorityQueue.isEmpty() && findVacantTurn(priorityQueue.peek().deadLine) == 0)      // 우선순위큐가 비어있지 않으면서, 해당 문제의 기한보다 turn 중 비어있는 곳이 없다면
                priorityQueue.poll();       // 값을 버린다.

            if (priorityQueue.isEmpty())        // 만약 우선순위큐가 비었다면 그만한다.
                break;

            CupNoodle current = priorityQueue.poll();       // 문제가 선정되었다.
            fillValue(current.deadLine, current.value);     // 해당 문제에 turn을 할당해준다.
        }
        long sum = 0;
        for (int i = 1; i < turnValue.length; i++)      // 1턴부터 maxTurn까지 보상의 합을 구한다.
            sum += turnValue[i];
        System.out.println(sum);
    }

    static int findVacantTurn(int n) {      // 자신보다 같거나 작은 turn 중 가장 큰 turn을 반환한다.
        if (vacantTurn[n] == n)     // vacantTurn[n]이 n 값이라면 해당 공간의 비어있는 것이다. 해당 위치를 반환하자.
            return n;
        // vacantTurn에 저장된 turn이 비어있지 않다면 비어있는 곳까지 재귀적으로 찾아간다.
        return vacantTurn[n] = findVacantTurn(vacantTurn[n]);
    }

    static void fillValue(int turn, int value) {
        int targetTurn = findVacantTurn(turn);      // 비어있는 가장 적절한 turn을 찾는다.
        turnValue[targetTurn] = value;          // 해당 턴에 값을 할당한다.
        vacantTurn[targetTurn] = findVacantTurn(targetTurn - 1);        // targetTurn의 공간은 채워졌으므로, 다음 비어있는 공간을 찾아 그 값을 넣어주자.
    }
}