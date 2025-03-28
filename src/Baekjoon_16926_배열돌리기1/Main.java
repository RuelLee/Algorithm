/*
 Author : Ruel
 Problem : Baekjoon 16926번 배열 돌리기 1
 Problem address : https://www.acmicpc.net/problem/16926
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16926_배열돌리기1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n * m 크기의 배열이 주어진다.
        // 배열을 다음과 같이 반시계 방향으로 돌린다.
        // A[1][1] ← A[1][2] ← A[1][3] ← A[1][4] ← A[1][5]
        //    ↓                                       ↑
        // A[2][1]   A[2][2] ← A[2][3] ← A[2][4]   A[2][5]
        //   ↓         ↓                   ↑         ↑
        // A[3][1]   A[3][2] → A[3][3] → A[3][4]   A[3][5]
        //    ↓                                       ↑
        // A[4][1] → A[4][2] → A[4][3] → A[4][4] → A[4][5]
        // r번 회전한 결과를 출력하라
        //
        // 구현 문제
        // n과 m이 최대 300, r이 최대 1000으로 주어지므로
        // 일일이 한번씩 돌리더라도 최대 300 * 300 * 1000으로 1억번 안으로 해결이 되긴한다.
        // 회전을 할 때 서로 값 영향을 끼치는 둘레를 하나씩 계산해나간다.
        // 먼저 위에 주어진 예시에 따르면
        // (1, 1), (1, 2), ... (1, 5), ... (4, 5), ...
        // 의 좌표를 순서대로 계산해둔다.
        // 그러면 r번 회전할 결과값은 현재 좌표에서 r만큼 떨어진 위치의 좌표가 된다.
        // 이를 이용하여 일일이 회전시키지 말고 한번에 변동량만큼을 계산하자.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n * m 크기의 배열
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 을 r번 반시계로 회전시킨다.
        int r = Integer.parseInt(st.nextToken());
        
        // 처음 주어지는 배열
        int[][] arrays = new int[n][m];
        for (int i = 0; i < arrays.length; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < arrays[i].length; j++)
                arrays[i][j] = Integer.parseInt(st.nextToken());
        }
        
        // 회전한 결과
        int[][] rotated = new int[n][m];
        for (int i = 0; i < Math.min(n, m) / 2; i++) {
            // 지금 살펴보는 둘레의 높이와 너비
            int height = n - i * 2;
            int width = m - i * 2;
            
            // 둘레에 해당하는 수들의 좌표
            int[][] idxes = new int[(height + width) * 2 - 4][2];
            int count = 0;
            // (i, i)에서 시작
            int currentR = i;
            int currentC = i;
            // 오른쪽 끝에 다다를 때까지
            while (currentC < i + (width - 1)) {
                idxes[count][0] = currentR;
                idxes[count++][1] = currentC++;
            }
            // 아래 끝에 다다를 때까지
            while (currentR < i + (height - 1)) {
                idxes[count][0] = currentR++;
                idxes[count++][1] = currentC;
            }
            // 왼쪽 끝에 다다를 때까지
            while (currentC > i) {
                idxes[count][0] = currentR;
                idxes[count++][1] = currentC--;
            }
            // 다시 시작점에 다다를 때까지
            while (currentR > i) {
                idxes[count][0] = currentR--;
                idxes[count++][1] = currentC;
            }
            
            // 해당하는 변동량 계산
            int diff = r % ((height + width) * 2 - 4);
            // 반시계로 회전시키므로, j번 위치에는 (j + diff) % ((height + width) * 2 - 4)번의 값이 오게 된다.
            for (int j = 0; j < idxes.length; j++) {
                int target = (j + diff) % idxes.length;
                rotated[idxes[j][0]][idxes[j][1]] = arrays[idxes[target][0]][idxes[target][1]];
            }
        }
        
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rotated.length; i++) {
            sb.append(rotated[i][0]);
            for (int j = 1; j < rotated[i].length; j++)
                sb.append(" ").append(rotated[i][j]);
            sb.append("\n");
        }
        // 답 출력
        System.out.print(sb);
    }
}