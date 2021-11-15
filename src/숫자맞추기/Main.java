/*
 Author : Ruel
 Problem : Baekjoon 2494번 숫자 맞추기
 Problem address : https://www.acmicpc.net/problem/2494
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package 숫자맞추기;

import java.util.Scanner;

public class Main {
    static int[][][] cycleGearTurned;
    static String original;
    static String target;

    public static void main(String[] args) {
        // 0 ~ 9까지 적혀있는 기어가 여러개 있다
        // 각 기어를 좌/우 회전이 가능한데, 왼쪽으로 회전시키면 자신을 포함한 아래 기어들이 모두 돌아가고
        // 오른쪽으로 회전시키면 자신만 오른쪽 회전이 된다
        // 원래 상태와 원하는 상태의 기어 모습이 주어질 때, 최소 회전 수와 각 기어의 회전을 출력하라
        // 13392번 방법을 출력하지 않는 숫자 맞추기에 각 기어의 회전 수를 출력하는 방법이 추가되었다
        // cycleGearTurned[a][b][c]라고 할 때, a는 a + 1번째 기어, b는 첫번째부터 a번째 기어까지 왼쪽으로 돌린 총 회수
        // cycleGearTurned[a][b][0]은 그 때의 좌/우 총 회전 수, cycleGearTurned[a][b][1]는 a + 1번째 기어의 회전 방향과 회수
        // cycleGearTurned[a][b][1]은 cycleGearTurned[a][b][0]의 회전 수를 갖기 위해 선택된 a + 1번째 기어의 상태(0 ~ 9)
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        cycleGearTurned = new int[n + 1][10][3];

        sc.nextLine();
        original = sc.nextLine();
        target = sc.nextLine();

        findMinCycle(0, 0);         // 이전의 기어가 돌아가지 않은 상태로 첫번째 기어부터 판별 시작.
        StringBuilder sb = new StringBuilder();
        sb.append(cycleGearTurned[0][0][0]).append("\n");       // 최소 회전을 위한 총 회전 수
        int idx = 0;        // 첫 상태는 0부터 시작.
        for (int i = 0; i < cycleGearTurned.length; i++) {
            if (cycleGearTurned[i][idx][1] != 0)        // i + 1번째 기어를 회전된 기록이 있을 때만
                sb.append((i + 1)).append(" ").append(cycleGearTurned[i][idx][1]).append("\n");     // 기어의 회전 기록
            idx = cycleGearTurned[i][idx][2];       // 다음 기어의 상태
        }
        System.out.println(sb);
    }

    static int findMinCycle(int n, int leftTurned) {
        if (cycleGearTurned[n][leftTurned][0] != 0)
            return cycleGearTurned[n][leftTurned][0];

        if (n == cycleGearTurned.length - 1)        // 가상의 n + 1번째 기어는 회전 시키지 않아도 가능. 그냥 0 리턴해주자.
            return 0;

        int currentGear = (original.charAt(n) - '0' + leftTurned) % 10;     // 여태까지 반영된 왼쪽 회전에 따른 현재 기어의 번호
        int targetGear = target.charAt(n) - '0';            // 원하는 기어의 번호
        int needLeftTurn = currentGear <= targetGear ? targetGear - currentGear : 10 - currentGear + targetGear;        // 왼쪽으로 돌릴 때 필요한 회전
        int needRightTurn = targetGear <= currentGear ? currentGear - targetGear : currentGear + 10 - targetGear;       // 오른쪽을 돌릴 때 필요한 회전

        // 마지막 기어부터 n번째 기어까지 필요한 총 회전 수
        // 이번 기어를 왼쪽으로 needLeftTurn 회전한다면, n + 1번째 기어는 (leftTurned + needLeftTurn) % 10 회전 상태
        int totalTurnWhenThisGearLeftTurn = needLeftTurn + findMinCycle(n + 1, (leftTurned + needLeftTurn) % 10);
        // 이번 기어를 오른쪽으로 needRightTurn 회전한다면 n + 1번째 기어는 왼쪽으로 leftTurned 그대로 회전 상태
        int totalTurnWhenThisGearRightTurn = needRightTurn + findMinCycle(n + 1, leftTurned);

        if (totalTurnWhenThisGearLeftTurn < totalTurnWhenThisGearRightTurn) {       // 왼쪽으로 회전한게 오른쪽으로 회전한 것이 총 회전 회수가 더 적다면
            cycleGearTurned[n][leftTurned][0] = totalTurnWhenThisGearLeftTurn;      // 총 회전 회수 기록
            cycleGearTurned[n][leftTurned][1] = needLeftTurn;                       // 이번 기어의 회전 기록
            cycleGearTurned[n][leftTurned][2] = (leftTurned + needLeftTurn) % 10;   // 다음 기어의 회전 상태
        } else {
            cycleGearTurned[n][leftTurned][0] = totalTurnWhenThisGearRightTurn;
            cycleGearTurned[n][leftTurned][1] = -needRightTurn;
            cycleGearTurned[n][leftTurned][2] = leftTurned;
        }
        return cycleGearTurned[n][leftTurned][0];
    }
}