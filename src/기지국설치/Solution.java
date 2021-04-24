package 기지국설치;

public class Solution {
    public static void main(String[] args) {
        int n = 16;
        int[] stations = {9};
        int w = 2;

        int start = 1;
        int needStations = 0;
        for (int point : stations) {    // 기지국 단위로 건너뛰며, 전파가 닿지 않는 구역을 계산하여, 각 범위의 필요 기지국 갯수를 더한다.
            if (point - w - 1 < start) {    // 설치된 기지국의 전파범위가 start 지점을 포함한다면, 해당 기지국의 전파가 닿지 않는 가장 가까운 거리를 start 지점에 다시 넣어주고, 다음 기지국을 조사한다.
                start = point + w + 1;
                continue;
            }
            needStations += calcStations(start, point - w - 1, w);
            start = point + w + 1;
        }

        if (start <= n) // 설치된 마지막 기지국과, 남은 범위를 비교하여, 아직 전부 커버되지 않았다면, 남은 구역에 필요한 기지국 갯수를 더해준다.
            needStations += calcStations(start, n, w);

        System.out.println(needStations);
    }

    static int calcStations(int start, int end, int w) {    // 전파가 닿지 않는 구역의 시작과 끝, w값을 주면 몇개의 기지국이 필요한지 반환한다.
        int coverage = 2 * w + 1;
        int range = end - start + 1;

        int need = range / coverage;
        if (range % coverage != 0)
            need += 1;
        return need;
    }
}