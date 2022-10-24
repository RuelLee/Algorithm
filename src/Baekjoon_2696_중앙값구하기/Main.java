/*
 Author : Ruel
 Problem : Baekjoon 2696번 중앙값 구하기
 Problem address : https://www.acmicpc.net/problem/2696
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_2696_중앙값구하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        // t개의 테스트케이스.
        // 테스트케이스마다 n이 주어지고, n개의 수가 주어진다.
        // 이 때 출력하는 중앙값의 개수와
        // 이 때 홀수번째마다 주어진 수들의 중앙값을 출력하라.
        // 수들은 한 줄에 10개씩 나뉘어져있고
        // 답안도 한 줄에 10개씩 수를 나눠 출력해야한다.
        //
        // 우선순위큐를 이용한 간단한 문제
        // 입력과 출력 조건 대로 맞춰 출력하는게 조금 귀찮았던 문제.
        // 어렵지는 않다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스의 수
        int testCase = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < testCase; t++) {
            // m개의 수가 주어진다.
            int m = Integer.parseInt(br.readLine());
            // 출력할 중앙값의 수는 (m + 1) / 2개
            sb.append((m + 1) / 2).append("\n");
            // 왼쪽 우선순위큐는 최대힙
            PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder());
            // 오른쪽 우선순위큐는 최소힙
            PriorityQueue<Integer> right = new PriorityQueue<>();
            int mid = 0;
            // m개의 수가 주어지므로
            // m을 10으로 나눠 올림한 값만큼의 줄이 입력된다.
            for (int i = 0; i < Math.ceil(m / (double) 10); i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                // 남은 수가 10개 이상이라면 10개의 입력이 들어오고, 다음 줄이 더 있을 것이다.
                // 그렇지 않고, 남은 수가 10개 미만이라면 해당 개수만큼의 수만 입력이 된다.
                int end = Math.min(m - i * 10, 10);
                // 만약 이번이 첫번째 줄이라면 아직 중앙값에 대한 정보가 없다.
                // 따라서
                if (i == 0) {
                    // 첫번째 수를 mid로 입력 받고
                    mid = Integer.parseInt(st.nextToken());
                    // 중앙값에 기록한다.
                    sb.append(mid).append(" ");
                }
                // 이번이 첫번째 줄이라면(i==0) 첫 수의 입력을 처리했기 때문에 1부터 시작해서 9개의 수를 계산하고
                // 그렇지 않다면 0부터 시작해서 총 10번의 수들을 계산한다.
                for (int j = (i == 0 ? 1 : 0); j < end; j++) {
                    // 수를 입력 받고
                    int num = Integer.parseInt(st.nextToken());
                    // mid보다 크다면 오른쪽에
                    if (num > mid)
                        right.offer(num);
                    // 작다면 왼쪽 우선순위큐에 담는다.
                    else
                        left.offer(num);

                    // 이번 입력이 홀수번째라면 중앙값을 출력해야한다.
                    if (j % 2 == 0) {
                        // 홀수개의 입력이 있었으므로, 중앙값을 제외한 수는 짝수
                        // 양쪽 우선순위큐의 사이즈가 같아야한다.
                        // 서로 다르다면
                        while (left.size() != right.size()) {
                            // 왼쪽 우선순위큐가 더 크다면
                            if (left.size() > right.size()) {
                                // mid값을 오른쪽 우선순위큐에
                                right.offer(mid);
                                // mid에는 왼쪽 우선순위큐 값을 빼 저장한다.
                                mid = left.poll();
                            } else {
                                // 반대의 경우.
                                left.offer(mid);
                                mid = right.poll();
                            }
                        }
                        // 양쪽 우선순위큐의 사이즈가 같아졌다면 그 때의
                        // 중앙값 mid를 기록한다.
                        sb.append(mid).append(" ");
                    }
                }
                // 총 10개의 중앙값을 한 줄에 출력해야하므로
                // 0부터 시작하여, 홀수번째 수마다 줄바꿈을 해줘야한다.
                // 혹은 마지막번째라면 역시 줄바꿈을 해준다.
                if (i % 2 == 1 || (i + 1) * 10 >= m) {
                    // 앞에 들어있는 공백문자를 제거해주고,
                    sb.deleteCharAt(sb.length() - 1);
                    // 줄바꿈을 해준다
                    sb.append("\n");
                }
            }
        }
        // 답안을 출력.
        System.out.print(sb);
    }
}