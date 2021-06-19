package 해킹;

import java.util.*;

class Contamination {
    int n;
    int time;

    public Contamination(int n, int time) {
        this.n = n;
        this.time = time;
    }
}

public class Main2 {
    public static void main(String[] args) {
        // 각 컴퓨터 간에 최소 거리로 잇는다고 봐도 되므로 다익스트라 알고리즘을 사용할 수도 있다.
        // 다익스트라 ->
        // 1. 한 정점을 선택과 해당 정점의 비용을 0으로 설정.
        // 2. 현재 정점으로 갈 수 있는 다음 정점을 방문하고, 현재 정점 cost + 다음 정점으로의 비용을 계산하여 최소값이라면, 다음 정점의 비용에 갱신해준다.
        // 3. 2에서의 '현재' 정점을 제외하고 남은 정점 중 방문 비용이 적은 정점을 '현재' 정점으로 설정한다.
        // 2와 3 과정을 반복해 모든 정점에서 방문을 반복한다.
        // 각 정점의 비용이 시작 지점에서 시작해서 방문하는 비용 중 최소비용으로 갱신될 것이다.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < testCase; tc++) {
            int n = sc.nextInt();       // 컴퓨터의 갯수
            int d = sc.nextInt();       // 종속 설정의 갯수
            int c = sc.nextInt() - 1;   // 소요 시간.

            List<Contamination>[] lists = new List[n];
            for (int i = 0; i < lists.length; i++)
                lists[i] = new ArrayList<>();

            for (int i = 0; i < d; i++) {       // lists[i] = i 컴퓨터에서 방문할 수 있는 정점과 비용에 대한 리스트.
                int to = sc.nextInt() - 1;
                lists[sc.nextInt() - 1].add(new Contamination(to, sc.nextInt()));
            }

            Contamination[] computers = new Contamination[n];       // 각 computer 에 도달하는 최소비용을 저장할 배열.
            for (int i = 0; i < computers.length; i++) {
                if (i == c)
                    computers[i] = new Contamination(i, 0);
                else
                    computers[i] = new Contamination(i, Integer.MAX_VALUE);
            }
            boolean[] check = new boolean[n];       // 이미 방문한 컴퓨터를 표시할 boolean 배열.
            PriorityQueue<Contamination> priorityQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.time, o2.time));
            // 각 컴퓨터에 도달하는 최소비용을 담은 배열을 우선순위큐에 담는다. 도달 시간이 짧은 순으로.(최초는 시작 컴퓨터가 나올 것이다.)
            priorityQueue.addAll(Arrays.asList(computers));

            while (!priorityQueue.isEmpty()) {
                Contamination current = priorityQueue.poll();
                if (current.time == Integer.MAX_VALUE)      // 도달 시간이 최소순으로 나오는데, 도달하지 않은 컴퓨터가 나왔다면, 더이상 방문할 수 없다.
                    break;
                for (Contamination next : lists[current.n]) {       // 현재 컴퓨터에서 전파할 수 있는 다음 컴퓨터를 살펴보자.
                    if (!check[next.n] && computers[next.n].time > (computers[current.n].time + next.time)) {       // 아직 감염 전이고, 최소시간이 갱신된다면
                        computers[next.n].time = computers[current.n].time + next.time;     // 최소 시간 갱신 후
                        priorityQueue.remove(computers[next.n]);        // 우선순위큐에 삭제 후, 다시 넣어 순서 재배치.
                        priorityQueue.add(computers[next.n]);
                    }
                }
                check[current.n] = true;
            }

            int contaminationCount = 0;
            int maxTime = 0;
            for (Contamination ct : computers) {
                if (ct.time < Integer.MAX_VALUE) {
                    contaminationCount++;
                    maxTime = Math.max(maxTime, ct.time);
                }
            }
            sb.append(contaminationCount).append(" ").append(maxTime).append("\n");
        }
        System.out.println(sb);
    }
}