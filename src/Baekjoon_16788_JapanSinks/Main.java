/*
 Author : Ruel
 Problem : Baekjoon 16788번 日本沈没 (Japan Sinks)
 Problem address : https://www.acmicpc.net/problem/16788
 Git hub : https://github.com/RuelLee
 Mail Address : lunaticmoonlight@gmail.com
*/

package Baekjoon_16788_JapanSinks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // n개의 일렬로 늘어선 구역의 높이가 주어진다.
        // 현재 물의 높이는 0이다.
        // 물이 점점 차오른다고 했을 때, 생기는 섬의 최대 개수를 출력하라
        //
        // 맵, 우선순위큐, 트리맵  문제
        // 먼저 연속하여 같은 높이가 주어지는 경우, 어차피 해당 연속한 구간은 한번에 잠긴다.
        // 따라서 하나의 구역으로 압축
        // 그래서 압축된 결과가 하나의 구역인데, 
        // 해당 구역이 0인 경우는 이미 모두 잠긴 상태 답 0
        // 그렇지 않은 경우는 1
        // 이제 압축된 결과가 하나가 아닌 경우
        // 높이가 낮은 구역부터 물에 잠긴다. 다만, 같은 높이는 모두 동시에 잠기게 되므로
        // 섬의 개수를 셀 때는, 현재 해수면으로 잠기는 구역을 모두 처리한 후에 해야한다.
        // 따라서 맵과 우선순위큐를 통하여 같은 높이의 값들을 동시에 처리한다.
        // 트리맵을 사용하면 편하다.
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        
        // 트리 맵을 통해 같은 높이의 구역을 하나의 리스트로 담는다.
        TreeMap<Integer, List<Integer>> treeMap = new TreeMap<>();
        // 별개로 이웃 구역에 접근하기 편하게 리스트로 순차적으로 높이를 담는다.
        List<Integer> heights = new ArrayList<>();
        // 높이들
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 첫번째 구역
        int first = Integer.parseInt(st.nextToken());
        heights.add(first);
        treeMap.put(first, new ArrayList<>());
        treeMap.get(first).add(0);
        // 이후 구역
        for (int i = 0; i < n - 1; i++) {
            // 현재 높이
            int height = Integer.parseInt(st.nextToken());
            
            // 만약 이전 높이랑 같다면 무시
            if (heights.get(heights.size() - 1) == height)
                continue;

            // 그 외의 경우, 트리 맵에 처음 생기는 높이인지 판별하고, 추가한다.
            if (!treeMap.containsKey(height))
                treeMap.put(height, new ArrayList<>());
            // 해당 트리맵과 리스트에 값 추가
            treeMap.get(height).add(heights.size());
            heights.add(height);
        }
        
        // 구역이 하나이고, 그 높이가 0인 경우는 모두 해수면에 잠겨 처음부터 섬이 0인 경우.
        // 높이가 0이 아닌 경우는, 해당 섬이 하나 존재하는 경우.
        int max = heights.size() == 1 && heights.get(0) == 0 ? 0 : 1;
        // 처음엔 모두 연결된 하나의 섬으로 생각
        int currentIsland = 1;
        // 트리 맵이 빌 때까지
        while (!treeMap.isEmpty()) {
            // 가장 낮은 구역들을 잠기게 한다.
            for (int idx : treeMap.pollFirstEntry().getValue()) {
                // 해당 구역의 좌우를 살펴봐 자신보다 높은 구역이 몇 개인지 센다.
                int higher = 0;
                if (idx - 1 >= 0 && heights.get(idx - 1) > heights.get(idx))
                    higher++;
                if (idx + 1 < heights.size() && heights.get(idx + 1) > heights.get(idx))
                    higher++;

                // 양쪽이 자신보다 높다면 자신이 잠김으로써 두 구역으로 나뉘게 되므로 하나의 섬이 늘어나는 셈이고
                // 한쪽만 자신보다 높다면, 섬의 가장자리부분이 그냥 잠기게 되는 걸로 섬의 개수는 그대로
                // 자신이 양쪽보다 높다면 현재 자신이 하나의 섬이었는데 잠기게 되므로 전체 섬의 개수는 -1
                // idx가 잠긴으로써 생기는 섬의 개수 변화 반영
                currentIsland += higher - 1;
            }
            
            // 같은 높이의 구역들을 모두 처리한 후, 최다 섬의 개수를 반영
            max = Math.max(max, currentIsland);
        }
        // 답 출력
        System.out.println(max);
    }
}