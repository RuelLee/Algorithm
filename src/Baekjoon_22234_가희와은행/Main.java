/*
 Author : Ruel
 Problem : Baekjoon 22234번 가희와 은행
 Problem address : https://www.acmicpc.net/problem/22234
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_22234_가희와은행;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Client {
    int num;
    int work;
    double inputTime;

    public Client(int num, int work, double inputTime) {
        this.num = num;
        this.work = work;
        this.inputTime = inputTime;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // 은행 창구와 고객들이 주어진다.
        // 은행 창구는 각 손님마다 최대 t 시간을 할당해 업무를 처리하고
        // 업무가 끝나지 않는다면 해당 고객은 줄의 맨 뒤로가 다시 줄을 선다.
        // 창구가 열리기 전 n명의 손님이 선착순으로 고객 번호와 작업의 요구 시간이 주어진다.
        // 그 후, 창구가 열리고 1초 이후에 들어온 m명의 손님이 주어진다.
        // 각 고객은 고객 번호와 작업 요구 시간 그리고 도착한 시간이 주어진다.
        // 0초부터 w - 1초까지 창구에서 처리하고 있는 손님의 번호를 출력하라
        //
        // 시뮬레이션, 우선순위큐 문제
        // 손님의 업무를 순차적으로 처리하되, t를 초과한다면 다시 줄의 맨 뒤로 보내면 된다.
        // 같은 시간에 줄을 선 사람과, 작업이 마치지 못해 다시 맨 뒤로 간 사람의 우선순위가
        // 겹칠 수 있는데, 이는 새로 들어와서 줄을 선 사람이 우선하게 됨을 유의하자
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 이미 줄을 선 n명의 고객
        int n = Integer.parseInt(st.nextToken());
        // 창구에서 한번에 처리하는 시간 t
        int t = Integer.parseInt(st.nextToken());
        // 0 ~ w-1 초까지의 처리 손님 번호를 출력한다.
        int w = Integer.parseInt(st.nextToken());
        
        // 우선순위큐를 통해 선착순으로 처리한다.
        PriorityQueue<Client> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(value -> value.inputTime));
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Client(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.MIN_VALUE + i));
        }

        // m명의 손님들도 도착 시간에 따라 우선순위큐에 넣는다.
        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            priorityQueue.offer(new Client(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        // 0초부터 작업 처리 시작
        int time = 0;
        StringBuilder sb = new StringBuilder();
        // 우선순위큐가 비어있지 않고, 시간이 w 이내인 경우에만 돌린다.
        while (!priorityQueue.isEmpty() && time < w) {
            Client current = priorityQueue.poll();

            // 이번에 처리할 업무의 시간.
            // currnet의 작업과 t 그리고 
            // w와 비교해 남은 시간들 중 가장 작은 값만큼을 처리한다.
            int process = Math.min(Math.min(current.work, t), w - time);
            // 해당 시간 만큼 기록
            for (int i = 0; i < process; i++)
                sb.append(current.num).append("\n");
            
            // 만약 작업이 아직 남았다면
            if (current.work - process > 0) {
                // 이번에 처리한 만큼 감소
                current.work -= process;
                // 시간은 현재 시간 + process 만큼
                // 만약 같은 시간에 먼저 줄을 선 사람이 있을 수 있으므로
                // 작은 값을 더해 먼저 줄을 선 사람이 우선순위를 갖도록 한다.
                current.inputTime = time + process + 0.5;
                // 우선순위큐에 추가
                priorityQueue.offer(current);
            }
            // 작업 처리만큼 시간 경과
            time += process;
        }
        // 전체 답안 출력
        System.out.print(sb);
    }
}