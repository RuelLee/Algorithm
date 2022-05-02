/*
 Author : Ruel
 Problem : Baekjoon 15591번 MooTube (Silver)
 Problem address : https://www.acmicpc.net/problem/15591
 GIt hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_15591_MooTube_Silver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Usado {
    int movie;
    int value;

    public Usado(int movie, int value) {
        this.movie = movie;
        this.value = value;
    }
}

public class Main {
    static List<List<Usado>> connection;

    public static void main(String[] args) {
        // 각 동영상 간에 유사도를 가지고서 추천 동영상의 개수를 알아내는 문제
        // 각 동영상 간 유사도는 거쳐가는 동영상 간의 최소 유사도로 정함.
        // 따라서 최소 유사도가 기준보다 높은 시점까지만 계산하고 그 이후로는 계산 안해도 됨.
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int q = sc.nextInt();

        connection = new ArrayList<>();
        for (int i = 0; i < n + 1; i++)
            connection.add(new ArrayList<>());

        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            connection.get(a).add(new Usado(b, c));
            connection.get(b).add(new Usado(a, c));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int k = sc.nextInt();
            int v = sc.nextInt();
            // getRecommendation 메소드로 계산하면, 처음 v 역시 개수에 포함되어있다.
            // 연관 영상의 개수'만' 출력하는 것이므로, v의 개수인 1을 빼주자.
            sb.append(getRecommendation(v, k, new boolean[n + 1]) - 1).append("\n");
        }
        System.out.println(sb);
    }

    static int getRecommendation(int movie, int k, boolean[] visited) {     // movie에 대해 유사도가 k이상은 동영상들을 bfs로 탐색
        if (visited[movie])     // 이미 지나간 적이 있다면
            return 0;       // 0값을 리턴

        visited[movie] = true;      // 아니라면 방문체크하고,
        int sum = 1;        // 자신을 세, 1을 추가
        for (Usado u : connection.get(movie)) {     // 현재 자신과 연관이 있는 동영상들 중 유사도가 k 이상은 동영상을 찾는다
            if (!visited[u.movie] && u.value >= k)      // 방문 하지 않았고, 유사도가 k 이상이라면
                sum += getRecommendation(u.movie, k, visited);      // 그 때 bfs를 돌려 나온 값을 sum에 더한다
        }
        // 최종적으로 sum이 movie로부터 파생되는 동영상들중 유사도가 k 이상인 영상의 개수.
        return sum;
    }
}