/*
 Author : Ruel
 Problem : Baekjoon 20056번 마법사 상어와 파이어볼
 Problem address : https://www.acmicpc.net/problem/20056
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_20056_마법사상어와파이어볼;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Fireball {
    int m;
    int s;
    int d;

    public Fireball(int m, int s, int d) {
        this.m = m;
        this.s = s;
        this.d = d;
    }
}

public class Main {
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 격자에 m개의 파이어볼이 있다.
        // 각 격자의 왼쪽 끝과 오른쪽 끝, 위 쪽과 아래 쪽은 이어져 있다.
        // 파이어볼은 각각 m 질량, s 속도, d 방향을 갖고 있고
        // 방향은
        // 7 0 1
        // 6   2
        // 5 4 3 으로 주어진다.
        // 파이어볼들에게 이동을 명령하면
        // 1. 모든 파이어볼은 각각 d 방향으로 s만큼 이동한다.
        // 2. 이동이 끝난 뒤, 파이어볼이 2개 이상 있는 칸에서는
        //  2-1. 파이어볼이 모두 하나로 합쳐진다.
        //  2-2. 4개의 파이어볼로 나뉘어진다.
        //  2-3. 질량은 (합쳐진 파이어볼의 질량 / 5)의 내림, 속도 (합쳐진 파어볼의 속도 합 / 파이어볼의 개수)
        //       방향은 합쳐진 파이어볼이 모두 홀수 혹은 짝수라면 0, 2, 4, 6 그렇지 않다면 1, 3, 5, 7
        //  2-4. 질량이 0인 파이어볼은 소멸된다.
        // k번 이동을 명령한 뒤, 남은 파이어볼들의 질량 합을 구하라
        //
        // 시뮬레이션 문제
        // 주어진 조건에 따라 잘 구현하면 된다.
        // 격자의 끝과 끝이 연결되어있음에 유의한다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // N * N크기의 격자, M의 초기 파이어볼 수, 명령 횟수 K
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        // 각 위치에서의 파이어볼
        Queue<Fireball>[][][] fireballs = new Queue[2][N][N];
        for (int i = 0; i < fireballs.length; i++) {
            for (int j = 0; j < fireballs[i].length; j++)
                for (int k = 0; k < fireballs[i][j].length; k++)
                    fireballs[i][j][k] = new LinkedList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            fireballs[0][r][c].offer(new Fireball(m, s, d));
        }
        
        // 끝과 끝이 연결되어있으므로, 파이어볼이 한 방향으로
        // 이동하며, 끝을 몇바퀴나 도는 경우가 생길 수 있으므로
        // 그 값을 보정해주기 위한 값
        int correction = (1000 / N + 1) * N;
        for (int i = 0; i < K; i++) {
            // 파이어볼을 이동시킨다.
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    while (!fireballs[0][j][k].isEmpty()) {
                        Fireball current = fireballs[0][j][k].poll();
                        int nextR = (j + dr[current.d] * current.s + correction) % N;
                        int nextC = (k + dc[current.d] * current.s + correction) % N;

                        fireballs[1][nextR][nextC].offer(current);
                    }
                }
            }

            // 두 개 이상 모여있는 파이어볼들을 분할한다.
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    // 하나라면 그냥 넘기고
                    if (fireballs[1][j][k].size() == 1)
                        fireballs[0][j][k].offer(fireballs[1][j][k].poll());
                    // 두개라면 분할
                    else if (fireballs[1][j][k].size() >= 2) {
                        // 질량 합, 속도 합
                        int mSum = 0;
                        int sSum = 0;
                        // 파이어볼의 개수
                        int count = fireballs[1][j][k].size();
                        // 전부 방향이 홀수인지 혹은 짝수인지 확인
                        boolean allOdd = true;
                        boolean allEven = true;
                        // 파이어볼들을 전부 꺼낸다.
                        while (!fireballs[1][j][k].isEmpty()) {
                            Fireball current = fireballs[1][j][k].poll();
                            // 질량 합, 속도 합
                            mSum += current.m;
                            sSum += current.s;

                            if (current.d % 2 == 0)
                                allOdd = false;
                            else
                                allEven = false;
                        }
                        
                        // 전체 질량 합을 5로 나누었을 때
                        // 내림값이 0이라면 파이어볼들은 소멸
                        if (mSum / 5 == 0)
                            continue;

                        // 그렇지 않은 경우
                        // 전부 홀수 혹은 짝수 방향이라면 0 2 4 6
                        // 그렇지 않다면 1 3 5 7 방향의 4개 파이어볼로 분할된다.
                        for (int l = (allOdd | allEven) ? 0 : 1; l < 8; l += 2)
                            fireballs[0][j][k].offer(new Fireball(mSum / 5, sSum / count, l));
                    }
                }
            }
        }
        
        // 남아있는 파이어볼들의 질량 합을 구한다.
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                while (!fireballs[0][i][j].isEmpty())
                    sum += fireballs[0][i][j].poll().m;
            }
        }
        // 답 출력
        System.out.println(sum);
    }
}