/*
 Author : Ruel
 Problem : Baekjoon 15980번 명상 방해꾼
 Problem address : https://www.acmicpc.net/problem/15980
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15980_명상방해꾼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 스승님이 m초간 명상을 한다.
        // 명상을 하는 동안, 왼편의 새가 지저귀면 정신의 중심이 왼편으로
        // 오른편의 새가 지저귀면 오른편으로 영향을 준다.
        // 각 새에 대해 방향과 1초마다 지저귀는지, 안 지저귀는지가 주어진다.
        // m초간 명상하는 중, 정신의 중심의 절댓값이 최대가 된 순간이 스승님이 방해를 받은 정도이다.
        // 한마리의 새를 잡아, 방해를 적게 받고 싶을 때
        // 잡아야하는 새의 번호와 방해를 받은 정도를 출력하라.
        //
        // 누적합, 브루트포스 문제
        // 각 새마다 초마다 누적합을 구하고, 전체 새에 대해서도 누적합을 구한다.
        // 그 후, 한마리씩 해당 새를 잡았을 때의 방해 정도를 계산하고
        // 그 값이 최소가 되는 새와 그 값을 찾는다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // n마리의 새, m초
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        // 각 새의 누적합
        int[][] birds = new int[n][m];
        // 전체 누적합
        int[] total = new int[m];
        for (int i = 0; i < birds.length; i++) {
            st = new StringTokenizer(br.readLine());
            // 위치, 지저귐
            char loc = st.nextToken().charAt(0);
            String cry = st.nextToken();

            // 누적합 처리
            birds[i][0] = (cry.charAt(0) == '1' ? 1 : 0) * (loc == 'L' ? -1 : 1);
            total[0] += birds[i][0];
            for (int j = 1; j < birds[i].length; j++) {
                if (cry.charAt(j) == '1')
                    birds[i][j] = (loc == 'L' ? -1 : 1);
                birds[i][j] += birds[i][j - 1];
                total[j] += birds[i][j];
            }
        }

        // 한 마리씩 제외해가며 방해받은 정도를 최소화하는 새를 찾는다.
        int totalMax = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i < birds.length; i++) {
            int max = 0;
            for (int j = 0; j < total.length; j++)
                max = Math.max(max, Math.abs(total[j] - birds[i][j]));
            if (totalMax > max) {
                totalMax = max;
                idx = i;
            }
        }
        // 찾은 새의 번호와 받은 방해 정도.
        System.out.println(idx + 1);
        System.out.println(totalMax);
    }
}