/*
 Author : Ruel
 Problem : Baekjoon 33576번 자습실과 쿼리
 Problem address : https://www.acmicpc.net/problem/33576
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_33576_자습실과쿼리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static long[] fenwickTree;
    static int[][] walls;
    static int leftIdx, rightIdx;

    public static void main(String[] args) throws IOException {
        // n개의 구역이 주어지고, 이중 m개의 구역에 벽이 설치되어있다.
        // i번째 벽은 di의 내구도를 갖고, wi 구역에 있다.
        // q명의 학생이 있으며, 각 학생의 위치가 주어진다.
        // 학생들은 1번 혹은 n번 구역으로 도달할 시, 탈출할 수 있으며, 만나는 벽은 di만큼의 망치질을 통해 부수고 나아갈 수 있다.
        // 각 학생은
        // 1. 최소한의 망치질로 탈출하는 방법으로 탈출한다.
        // 2. 그러한 방법이 여러개일 경우, 이동 거리가 최소인 경우로 한다.
        // 3. 2번 또한 여러개라면 1번 구역으로 탈출한다.
        // 각 학생들이 탈출하기 위해 한 망치질 횟수를 출력한다.
        //
        // 누적합, 두 포인터 문제
        // 먼저 각 학생은 왼쪽 혹은 오른쪽으로 탈출한다.
        // 따라서 남은 벽의 di의 총합이 필요하다. 이를 누적합을 통해 계산한다.
        // 또, 벽이 부순 경우, 다음 차례 학생에게 위 사항이 반영되므로, 해당 벽을 허물 필요가 있다.
        // 탈출하기 위해서는 끝까지 모두 벽을 허물 필요가 있으므로
        // 두 포인터를 1번과 n번 구역에 배치한 뒤, 학생이 있는 위치까지 포인터를 이동시키며 벽을 허문다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        
        // 펜윅 트리를 활용
        fenwickTree = new long[n + 1];
        // 벽의 정보
        walls = new int[m][2];
        for (int i = 0; i < walls.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < walls[i].length; j++)
                walls[i][j] = Integer.parseInt(st.nextToken());
            // 펜윅 트리에 벽의 정보 추가
            inputValue(walls[i][0], walls[i][1]);
        }
        // 벽을 번호 순서대로 정렬
        Arrays.sort(walls, Comparator.comparingInt(o -> o[0]));
        
        // 아직 허물어지지 않은 벽의 두 포인터
        leftIdx = 0;
        rightIdx = m - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            // 학생의 위치
            int idx = Integer.parseInt(br.readLine());
            // 왼쪽과 오른쪽으로 탈출할 때의 망치질 횟수
            long leftSum = getSum(idx);
            long rightSum = getSum(n) - leftSum;
            
            // 양쪽이 같다면
            if (leftSum == rightSum) {
                // 이동거리가 왼쪽이 더 적거나 같다면 1번으로 탈출
                if (idx - 1 <= n - idx) {
                    sb.append(leftSum).append("\n");
                    toLeft(idx);
                } else {        // 오른쪽이 더 적은 경우
                    sb.append(rightSum).append("\n");
                    toRight(idx);
                }
            } else if (leftSum < rightSum) {        // 왼쪽 벽 망치질이 더 적은 경우
                sb.append(leftSum).append("\n");
                toLeft(idx);
            } else {        // 오른쪽 벽 망치질이 더 적은 경우
                sb.append(rightSum).append("\n");
                toRight(idx);
            }
        }
        // 답 출력
        System.out.print(sb);
    }

    // idx부터 오른쪽 벽들을 모두 허문다.
    static void toRight(int idx) {
        while (rightIdx >= 0 && walls[rightIdx][0] > idx) {
            inputValue(walls[rightIdx][0], -walls[rightIdx][1]);
            rightIdx--;
        }
    }

    // idx부터 왼쪽 벽들을 모두 허문다.
    static void toLeft(int idx) {
        while (leftIdx < walls.length && walls[leftIdx][0] < idx) {
            inputValue(walls[leftIdx][0], -walls[leftIdx][1]);
            leftIdx++;
        }
    }
    
    // 1 ~ idx까지의 누적합
    static long getSum(int idx) {
        long sum = 0;
        while (idx > 0) {
            sum += fenwickTree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    // idx번째에 value를 추가한다.
    static void inputValue(int idx, int value) {
        while (idx < fenwickTree.length) {
            fenwickTree[idx] += value;
            idx += (idx & -idx);
        }
    }
}