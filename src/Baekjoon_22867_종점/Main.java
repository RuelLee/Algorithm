/*
 Author : Ruel
 Problem : Baekjoon 22867번 종점
 Problem address : https://www.acmicpc.net/problem/22867
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22867_종점;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // 버스들의 종점에서 들어오는 시간과 나가는 시간이 주어진다.
        // 동일 시간에 나가고 들어오는 버스들이 있다면, 나가는 버스가 우선적으로 나가고
        // 들어오는 버스가 그 후에 들어온다.
        // 종점에 필요한 버스 정비 공간의 최소 개수는?
        //
        // 문자열, 정렬, 스위핑
        // 먼저 시간의 형태로 주어지므로 이를 대소를 판별 가능한 수로 바꿔야한다.
        // 그 후로는 버스들의 종점 입장 시간에 따라 정렬하고
        // 다음 입장 버스 시간과 퇴장한 버스들을 살펴보며 필요한 최소 정비 공간의 개수를 센다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // n대의 버스
        int n = Integer.parseInt(br.readLine());
        int[][] buses = new int[n][2];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++)
                buses[i][j] = stringToInt(st.nextToken());
        }
        // 입장 시간 오름차순 정렬
        Arrays.sort(buses, Comparator.comparingInt(o -> o[0]));
        
        // 현재 종점에 남아있는 버스
        PriorityQueue<Integer> terminal = new PriorityQueue<>();
        int max = 0;
        for (int[] bus : buses) {
            // bus의 입장시간보다 작거나 같은 다른 버스들은
            // 종점에서 퇴장한다.
            while (!terminal.isEmpty() && terminal.peek() <= bus[0])
                terminal.poll();
            // bus 입장
            terminal.offer(bus[1]);
            // 현재 종점의 버스 수
            max = Math.max(max, terminal.size());
        }
        // 필요한 정비 공간의 최소 수 출력
        System.out.println(max);
    }

    static int stringToInt(String s) {
        String[] split = s.split(":");
        int sum = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
        sum *= 100000;
        sum += Double.parseDouble(split[2]) * 1000;
        return sum;
    }
}