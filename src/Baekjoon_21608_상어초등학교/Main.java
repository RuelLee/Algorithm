/*
 Author : Ruel
 Problem : Baekjoon 21608번 상어 초등학교
 Problem address : https://www.acmicpc.net/problem/21608
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_21608_상어초등학교;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Seat implements Comparable<Seat> {
    int favorite;
    int vacancy;
    int row;
    int col;

    public Seat(int favorite, int vacancy, int row, int col) {
        this.favorite = favorite;
        this.vacancy = vacancy;
        this.row = row;
        this.col = col;
    }

    // Comparable 인터페이스를 implements 했다.
    // 따라서 필수적으로 compareTo 함수를 구현해야한다.
    @Override
    public int compareTo(Seat o) {
        if (this.favorite == o.favorite) {
            if (this.vacancy == o.vacancy) {
                // 행의 번호가 같다면 열의 번호가 적은 곳으로
                if (this.row == o.row)
                    return Integer.compare(this.col, o.col);
                // 공석의 수가 같다면 열의 번호가 적은 순으로
                return Integer.compare(this.row, o.row);
            }
            // 인접한 친구의 수가 같다면 공석이 많은 곳으로
            return Integer.compare(o.vacancy, this.vacancy);
        }
        // 인접한 친구가 가장 많은 곳이 더 좋은 점수를 갖는다.
        return Integer.compare(o.favorite, this.favorite);
    }
}

public class Main {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[] scores = {0, 1, 10, 100, 1000};

    public static void main(String[] args) throws IOException {
        // n * n 크기의 교실과 n * n 명의 학생이 주어진다.
        // 학생들은 각자 자신이 좋아하는 친구 4명을 선정했다.
        // 학생들의 자리를 배치하려고 한다.
        // 자리는 상하좌우로 서로 '인접'한다라고 한다.
        // 자리를 배치할 때, 학생이 주어지는 순서에 따라
        // 비어있는 칸 중 좋아하는 친구가 가장 많이 인접한 곳에 자리를 정한다.
        // 그러한 곳이 여러개라면 비어있는 칸이 많이 인접한 곳으로 정한다.
        // 그러한 곳도 여러곳이라면 행의 번호가 적은, 열의 번호가 적은 곳을 우선으로 자리를 정한다.
        // 각 학생들의 만족도는 인접한 좋아하는 친구의 수에 따라 0, 1, 10, 100, 1000점으로 주어진다고 한다.
        // 전체 학생도의 만족도를 가장 크게 할 때, 그 값은?
        //
        // 코드량이 좀 많아질 수 있는 구현 문제
        // 자리를 선택할 때 각 자리의 우선순위가 주어진다.
        // 따라서 한 학생마다 모든 자리를 살펴보며, 자신에게 어울리는 자리를 선택하가며 자리를 정한다
        // 그렇게 모든 학생의 자리 선정이 끝난 후, 점수를 계산해 출력하자.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 비어있는 교실
        int[][] classroom = new int[n][n];
        // 각 학생들이 좋아하는 친구들.
        HashSet<Integer>[] favoriteFriends = new HashSet[n * n + 1];
        for (int i = 0; i < favoriteFriends.length; i++)
            favoriteFriends[i] = new HashSet<>();
        // 주어지는 학생들을 순서대로 담는다.
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n * n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            order.add(student);
            for (int j = 0; j < 4; j++)
                favoriteFriends[student].add(Integer.parseInt(st.nextToken()));

        }

        // 주어졌던 학생 순서대로 자리 배치를 시작한다.
        for (int student : order) {
            // 자신에게 가장 적절한 자리.
            Seat properSeat = null;
            // 모든 자리를 살펴본다.
            for (int i = 0; i < classroom.length; i++) {
                for (int j = 0; j < classroom[i].length; j++) {
                    // 비어있지 않다면 건너뛴다.
                    if (classroom[i][j] != 0)
                        continue;

                    // i, j 칸에 인접한 친구, 비어있는 자리를 센다.
                    int friend = 0;
                    int empty = 0;
                    for (int d = 0; d < 4; d++) {
                        int nearR = i + dr[d];
                        int nearC = j + dc[d];
                        
                        // 인접한 칸이 교실의 범위 내이고
                        if (checkArea(nearR, nearC, classroom)) {
                            // 비어있다면 empty 카운터 증가
                            if (classroom[nearR][nearC] == 0)
                                empty++;
                            // 좋아하는 친구라면 frined 카운터 증가.
                            else if (favoriteFriends[student].contains(classroom[nearR][nearC]))
                                friend++;
                        }
                    }
                    
                    // 현재 자리에 대한 정보.
                    Seat current = new Seat(friend, empty, i, j);
                    // 비어있다면 일단 현재 자리를 가장 적절한 자리로 담고
                    if (properSeat == null)
                        properSeat = current;
                    // 그렇지 않다면 자리를 비교하여 더 좋은 자리일 경우 교체해준다.
                    else if (properSeat.compareTo(current) > 0)
                        properSeat = current;
                }
            }
            // 최종적으로 남는 properSeat에 student를 배치한다.
            classroom[properSeat.row][properSeat.col] = student;
        }

        // 완성된 자리 배치의 전체 만족도를 구한다.
        int totalScore = 0;
        for (int i = 0; i < classroom.length; i++) {
            for (int j = 0; j < classroom[i].length; j++) {
                // 인접한 좋아하는 친구의 수를 센다.
                int count = 0;
                for (int d = 0; d < 4; d++) {
                    int nearR = i + dr[d];
                    int nearC = j + dc[d];

                    if (checkArea(nearR, nearC, classroom) && favoriteFriends[classroom[i][j]].contains(classroom[nearR][nearC]))
                        count++;
                }
                // 좋아하는 친구의 수에 따른 점수를 누적한다.
                totalScore += scores[count];
            }
        }
        // 전체 만족도 출력.
        System.out.println(totalScore);
    }

    static boolean checkArea(int r, int c, int[][] classroom) {
        return r >= 0 && r < classroom.length && c >= 0 && c < classroom.length;
    }
}