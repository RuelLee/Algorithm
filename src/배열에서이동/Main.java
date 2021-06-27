package 배열에서이동;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        // 1,1 -> n,n으로 상하좌우로 지나갈 때, 지나치는 숫자들 중 가장 큰 수와 가장 작은 수의 차이가 가장 적은 값은?
        // 처음엔 DP 문제인 줄 알고 각 지나칠 때의 최소 최대값을 저장하고, 차이가 적어질 때만 갱신하도록 했다.
        // -> 실패. 차이가 크더라도 다음 번에 어떤 숫자가 오느냐에 따라서 오히려 다음 번 차이는 오히려 더 커져버릴 수 있다.
        // ex) 최소: 40, 최대 100과, 최소 60 최대 140으로 i,j에 도달할 수 있었다면, 40, 100만 기억될 것이다.
        // 하지만 n,n으로 가는데 반드시 135를 지나야만 한다면 60, 140이 더 유리했을 것이다.
        // 따라서 이는 최소, 최대 값을 여러번 시도해보며 이 값으로 1,1에서 n,n에 도달할 수 있는지를 체크하며 그 값을 찾아나가야한다.
        // 주어지는 숫자의 최소 최대값으로 최대 차이를 정해두고, 이분탐색으로 그 값의 범위를 줄여나가며 최소 차이의 값을 찾아야한다.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[][] nums = new int[n][n];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                nums[i][j] = sc.nextInt();
                min = Math.min(nums[i][j], min);
                max = Math.max(nums[i][j], max);
            }
        }

        int diffMin = 0;
        int diffMax = max - min;
        int middle = (diffMax - diffMin) / 2;

        while (diffMin < diffMax) {     // 최소 diff 인 middle 을 찾는 이분탐색
            boolean pass = false;
            for (int i = min; i + middle <= max; i++) {
                // 해당 middle 값으로 도달할 수 있다면, diffMax 를 middle 값으로 정하자.
                if (canReach(i, i + middle, nums)) {
                    diffMax = middle;
                    min = i;    // i일 때부터 해당 middle 값이 통과한 겂이므로, 범위가 좁아질 다음 번엔 i부터 시작하는게 시간을 줄일 수 있을 것이다.
                    pass = true;
                    break;
                }
            }
            // 해당 middle 값으로 도달 할 수 없었다면, diffMin 값을 middle+1 값으로 정하자.
            if (!pass)
                diffMin = middle + 1;
            // middle 값 재할당
            middle = (diffMax + diffMin) / 2;
        }
        System.out.println(middle);
    }

    static boolean canReach(int min, int max, int[][] nums) {       // 주어진 min, max 값으로 1,1 -> n,n 으로 도달할 수 있는지 체크한다.
        boolean[][] check = new boolean[nums.length][nums[0].length];
        Queue<Point> queue = new LinkedList<>();
        if (nums[0][0] <= max && nums[0][0] >= min)
            queue.add(new Point(0, 0));
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.r == nums.length - 1 && current.c == nums.length - 1)
                return true;

            for (int d = 0; d < 4; d++) {
                int nextR = current.r + dr[d];
                int nextC = current.c + dc[d];

                if (checkArea(nextR, nextC, nums) && !check[nextR][nextC] && min <= nums[nextR][nextC] && nums[nextR][nextC] <= max) {
                    check[nextR][nextC] = true;
                    queue.add(new Point(nextR, nextC));
                }
            }
        }
        return false;
    }

    static boolean checkArea(int r, int c, int[][] nums) {
        return r >= 0 && r < nums.length && c >= 0 && c < nums[r].length;
    }
}