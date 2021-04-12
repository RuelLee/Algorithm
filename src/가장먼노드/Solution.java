package 가장먼노드;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    static int[] distance;
    static List<Integer>[] lists;

    public static void main(String[] args) {
        int n = 11;
        int[][] edge = {{1, 2}, {1, 3}, {2, 4}, {2, 5}, {3, 5}, {3, 6}, {4, 8}, {4, 9}, {5, 9}, {5, 10}, {6, 10}, {6, 11}};

        distance = new int[n];      // 1로부터의 거리를 저장할 공간
        Arrays.fill(distance, 20000);
        distance[0] = 0;    // 1로부터 1까지의 거리는 0

        lists = new List[n];
        for (int i = 0; i < lists.length; i++)
            lists[i] = new LinkedList<>();

        for (int[] arr : edge) {        //lists 공간에 각 노드부터 연결된 노드의 리스트를 저장해두자.
            int a = arr[0];
            int b = arr[1];

            lists[a - 1].add(b - 1);
            lists[b - 1].add(a - 1);
        }
        findMinDistance(0);

        int max = 0;        // 최대값이 몇개인지 구하는 부분
        int count = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] == 20000)
                continue;
            if (max < distance[i]) {
                max = distance[i];
                count = 1;
            } else if (max == distance[i])
                count++;
        }
        System.out.println(count);
    }

    static void findMinDistance(int num) {
        for (int i : lists[num]) {  // num값으로부터 이어져 있는 다른 노드들을 확인하자.
            if (distance[i] > distance[num] + 1) {  //num값으로부터 이어져있는 노드가 가진 distance가 num까지의 distance + 1보다 크다면, distance +1 값으로 갱신.
                distance[i] = distance[num] + 1;
                findMinDistance(i);     // 값이 갱신된 노드만 재귀적으로 다음 단계를 진행한다. -> 사실 재귀로 푼다면 오버플로우 위험이 있다. BFS를 활용해보자.
            }
        }
    }
}