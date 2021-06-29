/*
 Author : Ruel
 Problem : Baekjoon 11375번 열혈강호
 Problem address : https://www.acmicpc.net/problem/11375
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
 */

package 열혈강호;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 이분매칭이라는 알고리즘이 필요하다
        // 먼저 순서대로 작업자를 순서대로 방문하여, 자신이 맡을 수 있는 일을 하나씩 맡게 하자
        // 자신이 맡을 수 있는 일을 못맡았을 경우, 해당 작업에 대해 맡고 있는 작업자가 다른 작업을 맡게하자.
        // 해당 작업자가 다른 작업을 맡았을 경우, 자신에게 해당 작업을 할당하고 종료.
        // 해당 작업자가 안 맡았을 경우, 다른 작업에 대해서도 위와 같은 일을 반복.
        // 모든 작업에 대해 대체가 되지 않을 경우, 작업 할당 실패.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] works = new int[m];
        Arrays.fill(works, -1);
        int[][] canDoWorks = new int[n][];
        for (int i = 0; i < n; i++) {
            int size = sc.nextInt();
            canDoWorks[i] = new int[size];
            for (int w = 0; w < size; w++)
                canDoWorks[i][w] = sc.nextInt() - 1;
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (allocateWork(works, i, canDoWorks, new boolean[n])) // 작업 할당에 성공한 경우의 개수를 센다.
                count++;
        }
        System.out.println(count);
    }

    static boolean allocateWork(int[] works, int worker, int[][] canDoWorks, boolean[] check) {     // worker 작업자에게 작업을 할당한다.
        // 방문체크를 하지 않을 경우, 작업을 서로 맡기 위해 재귀를 반복하여 스택오버 플로우가 발생할 수 있다.
        check[worker] = true;
        boolean allocated = false;
        for (int idx : canDoWorks[worker]) {        // 자신이 할 수 있는 작업들 중 할당되지 않은 것이 있다면 자신이 맡자.
            if (works[idx] == -1) {
                works[idx] = worker;
                allocated = true;
                break;
            }
        }
        if (!allocated) {       // 자신이 할 수 있는 작업들이 모두 다른 작업자에게 할당되어있다면
            for (int idx : canDoWorks[worker]) {
                int workingWorker = works[idx];     // 자신이 맡을 수 있는 작업을 맡고 있는 작업자를 선택하여
                if (check[workingWorker])       // 이미 체크한 적이 있다면 패쓰.
                    continue;
                check[workingWorker] = true;    // 방문 체크
                if (allocateWork(works, workingWorker, canDoWorks, check)) {    // 다른 작업을 할당에 성공했다면, 자신에게 idx 작업을 자신에게 할당하고 종료.
                    works[idx] = worker;
                    allocated = true;
                    break;
                }
            }
        }
        return allocated;
    }
}