/*
 Author : Ruel
 Problem : Baekjoon 12016번 라운드 로빈 스케줄러
 Problem address : https://www.acmicpc.net/problem/12016
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_12016_라운드로빈스케줄러;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Main {
    static int[] fenwickTree;

    public static void main(String[] args) throws IOException {
        // n개의 작업에 대해 필요한 시간이 주어진다
        // 라운드 로빈 스케줄러로 작업들을 처리한다했을 때, 각 작업들이 끝나는 시간을 출력하라.
        // 각 작업을 돌아가면서 1초씩 실행시킨다
        // 나보다 앞 번호의 작업들 중에 몇 개의 작업들이 이미 끝났는지 체크가 필요하다 -> 세그먼트 트리, 펜윅 트리로 처리하자
        // 작업들은 소요 시간이 적은 것부터 처리하되, 같은 소요 시간을 갖는다면, 앞 번호의 작업이 먼저 끝난다! -> 우선순위큐로 처리하자
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        // 끝난 작업들을 표시할 펜윅 트리
        fenwickTree = new int[n + 1];
        // 각 작업들
        int[] works = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 작업들은 소요 시간이 적은 순으로 처리하되, 같읕 소요 시간을 가질 경우, 작업 번호가 작은 작업이 우선된다.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            if (works[o1] == works[o2])
                return Integer.compare(o1, o2);
            return Integer.compare(works[o1], works[o2]);
        });
        for (int i = 0; i < works.length; i++)
            priorityQueue.offer(i);

        // 초기값으로, 0번 작업이 1초간 처리됐다고 하자.
        int workedEachWork = 1;
        long time = 1;
        int lastEndWork = 0;
        // 각 작업이 끝나는 시간.
        long[] endTime = new long[n];
        while (!priorityQueue.isEmpty()) {
            // 다음 끝날 작업
            int nextEndWork = priorityQueue.peek();

            // 이전에 끝난 작업보다 더 큰 작업 번호를 갖고 있다면(0번 작업이 바로 종료될 경우는 같은 경우)
            if (lastEndWork <= nextEndWork) {
                // lastEndWork 작업까지 works[nextEndWork]만큼 처리 됐다고 생각하고 그 만큼을 time에 추가.
                time += (long) (works[nextEndWork] - workedEachWork) * priorityQueue.size();
                // 그 후, lastEndWork ~ nextEndWork까지의 아직 처리되지 않은 작업이 몇 개인지 세고 그 만큼을 time을 추가해주자.
                time += calcNotEndWorks(nextEndWork) - calcNotEndWorks(lastEndWork);
            } else {
                // 이전에 끝난 작업이 다음 처리될 작업보다 큰 작업 번호를 갖고 있을 때
                // lastEndWork 작업까지 works[nextEndWork] - 1 만큼 처리됐다고 생각하자
                time += (long) (works[nextEndWork] - workedEachWork - 1) * priorityQueue.size();
                // 그 후, lastEndWork 이후부터 마지막까지 작업들의 works[nextEndWork]번째 처리해주고, nextEndWork 이하의 작업들을 처리해준다고 생각하자.
                time += calcNotEndWorks(n - 1) - calcNotEndWorks(lastEndWork) + calcNotEndWorks(nextEndWork);
            }
            // nextEndWork 작업이 종료. 현재 시각 기록
            endTime[nextEndWork] = time;
            // 작업이 처리됐다고 펜윅 트리에 표시
            checkEnd(nextEndWork);
            // 현재 진행 중인 각 작업에서의 n번째를 nextEndWork의 소요시간만큼으로 설정하자.
            workedEachWork = works[nextEndWork];
            // 마지막 끝난 작업을 nextEndWork로 표시
            lastEndWork = nextEndWork;
            // 우선 순위 큐에서 해당 작업 제거.
            priorityQueue.poll();
        }
        // endTime을 순서대로 출력해주면 결과값.
        System.out.print(Arrays.stream(endTime).mapToObj(value -> value + "\n").collect(Collectors.joining()));
    }

    // 작업 번호 to까지의 작업들 중 아직 안 끝난 작업의 개수를 반환한다.
    static int calcNotEndWorks(int to) {
        // 0번은 펜윅트리에서 사용되야하기 때문에, 값을 하나씩 증가시켜서 사용한다.
        int end = ++to;
        int sum = 0;
        while (to > 0) {
            sum += fenwickTree[to];
            to -= (to & -to);
        }
        // 처음 to 번호에서, 끝난 작업들의 개수를 빼주면 아직 안 끝난 작업의 개수.
        return end - sum;
    }

    // 끝났다고 표시한다. 해당 번호에 1을 표시해준다.
    static void checkEnd(int n) {
        // 마찬가지로 펜윅 트리에서 0번이 사용되므로..
        n++;
        while (n < fenwickTree.length) {
            fenwickTree[n] += 1;
            n += (n & -n);
        }
    }
}