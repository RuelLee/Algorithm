/*
 Author : Ruel
 Problem : Baekjoon 3101번 토끼의 이동
 Problem address : https://www.acmicpc.net/problem/3101
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_3101_토끼의이동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1부터 n^2의 수가 지그 재그 대각선 순서로 n * n 행렬에 채워져있다.
        // 1 2 6
        // 3 5 7
        // 4 8 9
        // 토끼는 지금 1이 있는 칸에 있으며 사방으로 점프할 수 있다.
        // 토끼가 점프한 방법이 주어졌을 때, 방문한 칸의 합을 구하라
        //
        // 누적합 + 시뮬레이션 문제
        // n이 최대 10만으로 주어지므로 직접 배열을 생성해서 값을 찾는 건 불가능하다.
        // 왼쪽 위 대각선의 형태로 구분이 되어지므로
        // row + col을 한 줄로 볼 수 있다.
        // 따라서 한 줄에 대해, 마지막 수를 구해주었다.
        // 에를 들어, 위의 n = 3인 경우에 대해서는
        // 2 * n - 1개의 대각선 줄이 존재하게 되고
        // 첫번째 줄에는 최대 1이 등장하므로 1
        // 두번째 줄에는 최대 3이 등장하므로 3
        // 세번쨰 줄에는 최대 6, 8, 9의 형태로 구해주었다.
        // 그 후, 좌표가 주어질 때, r + c의 형태로 몇번째 줄인지 알 수 있다.
        // 그리고나서, r + c 가 n보다 작으며, 짝수인 경우는 왼쪽 아래에서 오른쪽 위로 증가하는 형태이므로
        // r만큼을 빼주어 값을 보정하고, 홀수인 경우에는 반대로 감소하는 형태이므로 c만큼을 빼주며 값을 찾는다.
        // r + c가 n보다 같거나 큰 경우에는 r이 n보다 증가하지 않으며, c 또한 0에서 시작하지 않는다.
        // 이점에 유의하며 짝수인 경우에는 결국 c가 n - 1에서 끝난다는 점은 알고 있으므로 이를 이용하여 값을 보정하며
        // 홀수인 경우에는 누적합에 해당하는 마지막 수의 row가 n - 1에서 시작한다는 점을 고려해여 n - 1 - r을 통해 보정한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 주어지는 행렬의 크기와 점프의 횟수
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // r + c번째 줄에 대한 마지막수를 누적합을 통해 구한다.
        long[] psum = new long[n * 2 - 1];
        psum[0] = 1;
        // n 미만인 경우에는 i + 1 값을 하나씩 추가로 더해가며
        // n이상인 경우에는 (2 * n - i - 1)의 형태로 증가량이 하나씩 줄어간다.
        for (int i = 1; i < psum.length; i++)
            psum[i] = psum[i - 1] + (i < n ? (i + 1) : (2 * n - i - 1));
        
        // 좌표
        int r = 0;
        int c = 0;
        // 방문한 지점들의 합
        long sum = 1;
        String jumps = br.readLine();
        for (int i = 0; i < jumps.length(); i++) {
            // 스위치 문을 통해 좌표를 구하고
            switch (jumps.charAt(i)) {
                case 'U' -> r--;
                case 'D' -> r++;
                case 'L' -> c--;
                case 'R' -> c++;
            }
            
            // r + c가 n 미만인 경우
            if (r + c < n)
                sum += psum[r + c] - ((r + c) % 2 == 0 ? r : c);
            // r + c가 n이상인 경우
            else
                sum += psum[r + c] - ((r + c) % 2 == 0 ? (n - 1 - c) : (n - 1 - r));
        }

        // 방문한 지점들의 합을 출력한다.
        System.out.println(sum);
    }
}