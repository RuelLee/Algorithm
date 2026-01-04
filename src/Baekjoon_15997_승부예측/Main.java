/*
 Author : Ruel
 Problem : Baekjoon 15997번 승부 예측
 Problem address : https://www.acmicpc.net/problem/15997
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15997_승부예측;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static double[][][] probability;
    static double[] possibility;
    static int[] points;

    public static void main(String[] args) throws IOException {
        // 4개의 팀이 참여하는 조별리그가 주어진다.
        // 모든 팀은 자신의 팀을 제외한 다른 팀과 3번의 경기를 치룬다.
        // 이긴 팀은 3점, 비긴 팀은 1점, 진 팀은 0점을 얻는다.
        // 상위 두 팀이 다음 라운드로 진출하며, 동점이 발생한 경우, 추첨을 통해 진출한다.
        // 6경기 전체에 대한 각 팀이 이길 확률이 주어질 때
        // 각 팀이 다음 라운드에 진출할 확률은?
        //
        // 브루트 포스 문제
        // 브루트 포스를 통해, 모든 경기, 모든 결과에 대해 계산하고
        // 상위 두 팀을 선정한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 각 팀의 이름과 번호를 매칭
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < 4; i++)
            hashMap.put(st.nextToken(), hashMap.size());
        // 이기고, 비기고, 졌을 때 승점
        points = new int[]{3, 1, 0};

        // 각 팀의 이기고, 비기고, 질 확률
        probability = new double[4][4][3];
        for (int i = 0; i < 6; i++) {
            st = new StringTokenizer(br.readLine());
            int a = hashMap.get(st.nextToken());
            int b = hashMap.get(st.nextToken());
            for (int j = 0; j < 3; j++)
                probability[a][b][j] = probability[b][a][2 - j] = Double.parseDouble(st.nextToken());
        }

        // 다음 라운드 진출 확률
        possibility = new double[4];
        findAnswer(0, 1, new int[4]);
        // 답안 작성
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < possibility.length; i++)
            sb.append(String.format("%.6f", possibility[i])).append("\n");
        // 답안 출력
        System.out.print(sb);
    }

    static void findAnswer(int idx, double rate, int[] scores) {
        // 확률이 없는 경우, 그만 계산.
        if (rate == 0)
            return;
        // 모든 경우를 계산한 경우.
        else if (idx == 16) {
            PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(scores[o2], scores[o1]));
            for (int i = 0; i < 4; i++)
                pq.offer(i);
            // 상위 두 팀이 진출
            int remain = 2;
            // 점수 순으로 가장 높은 두 팀을 선정
            // 남은 자리가 0이 될 때까지 반복
            while (!pq.isEmpty() && remain > 0) {
                // 가장 높은 점수의 팀
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(pq.poll());
                // 만약 다음 팀 또한 점수가 같은 경우, 큐에 담는다.
                while (!pq.isEmpty() && scores[pq.peek()] == scores[queue.peek()])
                    queue.offer(pq.poll());
                // 남은 자리보다 같거나 적은 수의 팀이 큐에 있는 경우
                // 해당 수만큼을 다음 라운드에 진출
                if (queue.size() <= remain) {
                    remain -= queue.size();
                    // 그 때의 확률을 누적
                    while (!queue.isEmpty())
                        possibility[queue.poll()] += rate;
                } else {
                    // 남은 진출 팀의 수보다 더 많은 수의 팀이 큐에 담겨있다면
                    // 추첨을 통해 공정하게 진출
                    // 현재 큐에 담긴 팀의 수
                    int size = queue.size();
                    // 남은 자리 / size의 확률로 각 팀이 다음 라운드에 진출
                    while (!queue.isEmpty())
                        possibility[queue.poll()] += rate * remain / size;
                    // 남은 자리는 0
                    remain = 0;
                }
            }
            return;
        }

        // idx를 통해
        // left팀과 right팀을 구분.
        int left = idx / 4;
        int right = idx % 4;
        // 만약 right가 left보다 같거나 작다면 해당 경우는
        // left < right인 경우에서 세어질 것이기 때문에 넘김.
        if (right <= left)
            findAnswer(idx + 1, rate, scores);
        else {
            // 올바른 팀이 선정된 경우
            // left팀이 right팀을 이기고 비기고 질 확률을 계산
            for (int j = 0; j < 3; j++) {
                // 확률이 0이 아닌 경우
                if (probability[left][right][j] != 0) {
                    // left 팀과 right팀에 해당하는 점수를 가산한 뒤
                    scores[left] += points[j];
                    scores[right] += points[2 - j];
                    // 다음 경기를 하러 보냄
                    findAnswer(idx + 1, rate * probability[left][right][j], scores);
                    // 해당 경우로 파생되는 경우들이 끝났다면 score 값을 복구
                    scores[left] -= points[j];
                    scores[right] -= points[2 - j];
                }
            }
        }
    }
}