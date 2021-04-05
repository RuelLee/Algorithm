package 디스크컨트롤러;

import java.util.PriorityQueue;
import java.util.Queue;

class Job {
    int inputTime;
    int durationTime;

    public Job(int inputTime, int durationTime) {
        this.inputTime = inputTime;
        this.durationTime = durationTime;
    }
}

public class Solution {
    public static void main(String[] args) {
        // 작업을 처리할 때, 소모시간이 적은 것을 우선적으로 처리하면 평균 작업 시간이 줄어들게 된다!

        // 작업을 처리 중이지 않을 때는 받아들인 작업을 먼저 처리.
        // 전 작업이 끝나는 시간으로 이동하여, 작업 중에 들어온 다른 작업들 중 소요 시간이 적은 것을 선정하여 처리.
        // ↑ 두 단계를 모든 작업이 처리될 때까지 반복.

        int[][] jobs = {{0, 3}, {1, 9}, {2, 6}};

        Queue<Job> jobQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.inputTime, o2.inputTime));     // 작업 입력 시간 별로 선정해줄 우선순위큐.

        for (int i = 0; i < jobs.length; i++)
            jobQueue.add(new Job(jobs[i][0], jobs[i][1]));

        int totalDurationTime = 0;
        int curTime = 0;

        while (!jobQueue.isEmpty()) {
            Queue<Job> selectQueue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.durationTime, o2.durationTime));    // 새로운 작업들 중 소요 시간이 적은 작업을 선정해줄 우선선위큐.

            Job cur;
            do {
                cur = jobQueue.poll();
                if (cur.inputTime <= curTime)   // 작업 입력 시간이 현재 시간보다 이르다면 selectQueue에 삽입.
                    selectQueue.add(cur);
                else if (selectQueue.isEmpty()) {   // 작업 입력 시간이 현재 시간보다 늦지만, 그 사이에 아무런 작업이 입력되지 않았으므로, 해당 시간으로 넘어가고, 해당 작업을 삽입. 동시에 입력된 다른 작업이 있을 수 있다!
                    curTime = cur.inputTime;
                    selectQueue.add(cur);
                } else  // 현재 시간 보다 늦은 입력 시간을 갖고, 이미 다른 작업(들)이 selectQueue에 들어가 있다면 다시 jobQueue로 돌려준다.
                    jobQueue.add(cur);
            } while (cur.inputTime <= curTime && !jobQueue.isEmpty());

            Job selected = selectQueue.poll();
            totalDurationTime = totalDurationTime + (curTime + selected.durationTime - selected.inputTime);
            curTime = curTime + selected.durationTime;
            jobQueue.addAll(selectQueue);

        }
        System.out.println(totalDurationTime / jobs.length);
    }
}