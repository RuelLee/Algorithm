package 해킹;

import java.util.*;

class Infection {
    int to;
    int time;

    public Infection(int to, int time) {
        this.to = to;
        this.time = time;
    }
}

public class Main {
    public static void main(String[] args) {
        // 감염이 시작되는 컴퓨터와 감염된 시작에서 전파될 수 있는 컴퓨터들이 주어진다.
        // 각 컴퓨터가 감염되는 시간을 기록하는 배열과
        // 각 컴퓨터에서 전파할 수있는 컴퓨터와 시간을 갖는 리스트들.
        // 을 가지고 BFS 로 차근차근 진행해보자.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < testCase; tc++) {
            int n = sc.nextInt();
            int d = sc.nextInt();
            int c = sc.nextInt() - 1;

            List<Infection>[] lists = new List[n];      // 각 컴퓨터에서 전파할 수 있는 컴퓨터들에 대한 리스트.
            for (int i = 0; i < lists.length; i++)
                lists[i] = new ArrayList<>();

            for (int i = 0; i < d; i++) {
                int to = sc.nextInt() - 1;
                int from = sc.nextInt() - 1;
                int time = sc.nextInt();

                lists[from].add(new Infection(to, time));
            }

            int[] infectedTime = new int[n];        // 각 컴퓨터가 감염되는 최소 시간.
            Arrays.fill(infectedTime, Integer.MAX_VALUE);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(c);
            infectedTime[c] = 0;
            int infectedCount = 1;      // 처음 컴퓨터는 감염된 채 시작.
            while (!queue.isEmpty()) {
                int computer = queue.poll();

                for (Infection i : lists[computer]) {       // 해당 컴퓨터에서 진행될 다른 컴퓨터들
                    if (infectedTime[i.to] > infectedTime[computer] + i.time) {     // 이미 주어진 감염시간보다 작은 감염시간이 주어지면
                        if (infectedTime[i.to] == Integer.MAX_VALUE)        // 만약 미감염 컴퓨터였다면 새로운 감염 컴퓨터로 카운트.
                            infectedCount++;
                        infectedTime[i.to] = infectedTime[computer] + i.time;       // 새로운 감염시간으로 갱신해준다.
                        queue.add(i.to);    // 그리고 감염 컴퓨터를 Queue 에 넣어 다른 컴퓨터들을 전염시키도록하자
                    }
                }
            }
            int maxTime = Arrays.stream(infectedTime).filter(value -> value != Integer.MAX_VALUE).max().getAsInt();     // 감염 컴퓨터 중 가장 늦은 감염 시간.
            sb.append(infectedCount).append(" ").append(maxTime).append("\n");
        }
        System.out.println(sb);
    }
}