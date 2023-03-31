/*
 Author : Ruel
 Problem : Baekjoon 10713번 기차 여행
 Problem address : https://www.acmicpc.net/problem/10713
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_10713_기차여행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 도시와 n - 1개의 철도가 주어진다.
        // i번 철도는 i번 도시와 i + 1번 도시를 잇는 철도이다.
        // 각 철도는 티켓을 구매하여 탑승하는 방법과, ic카드를 구매 후, 충전하여 탑승하는 방법이 있다.
        // ic카드는 별도의 요금이 청구되며, 카드가 있는 경우, ic 카드 결제가 티켓 결제보다 싸다.
        // m개의 방문하고자하는 도시가 순서대로 주어진다.
        // 이 때 철도를 통해 최소한의 비용으로 방문하고자할 때 그 비용은?
        //
        // 누적합 문제
        // 각 철도를 몇 번 탑승하는지를 세어야한다.
        // 그 후, 탑승 횟수에 따른 티켓 비용과 ic 카드 비용을 계산하여 더 적은 쪽을 취한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // n개의 도시
        int n = Integer.parseInt(st.nextToken());
        // 방문하고자하는 m개의 도시 순서
        int m = Integer.parseInt(st.nextToken());
        
        // 방문 도시 순서
        int[] visit = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 각 철도의 티켓, ic카드 통과 비용, ic 카드 비용
        int[][] trains = new int[n][];
        for (int i = 1; i < trains.length; i++)
            trains[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // 방문 도시 순서를 보고, 각 철도를 몇 번씩 이용하는지 센다.
        int[] psum = new int[n + 1];
        for (int i = 1; i < visit.length; i++) {
            int min = Math.min(visit[i - 1], visit[i]);
            int max = Math.max(visit[i - 1], visit[i]);

            // min 도시 부터 max도시까지 이동하므로
            // 철도는 min ~ max - 1 철도까지 이용한다.
            // 따라서 min에는 +1, max에는 -1 을 표시해둔다.
            // 이 뜻은 min 철도부터 한 번씩 탑승하며
            // max부터는 이 +1을 0으로 복구해준다.
            psum[min]++;
            psum[max]--;
        }

        // 그 후, 앞에서부터 순서대로 값을 비교하며
        // i에 i-1 값을 차곡차곡 누적시켜준다.
        // 그러면 해당 철도를 총 몇 번 이용했는지를 구할 수 있다.
        for (int i = 1; i < psum.length; i++)
            psum[i] += psum[i - 1];

        // 각 철도를 살펴보며
        // 티켓 비용과 ic카드 총 비용을 구해 더 적은 쪽으로 이용한다.
        // 비용이 int 범위를 넘어갈 수 있음에 유의하자.
        long sum = 0;
        for (int i = 1; i < trains.length; i++) {
            long ticket = (long) trains[i][0] * psum[i];
            long ic = trains[i][2] + (long) trains[i][1] * psum[i];
            sum += Math.min(ticket, ic);
        }

        // 총합을 출력한다.
        System.out.println(sum);
    }
}