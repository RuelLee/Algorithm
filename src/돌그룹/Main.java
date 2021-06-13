package 돌그룹;

import java.util.*;

class State {
    int[] array;

    public State(int[] array) {
        this.array = array;
    }
}

public class Main {
    public static void main(String[] args) {
        // 세 수 중 두 수를 골라, 큰 수에서 작은 수 만큼을 떼어, 작은 수에 더해준다.
        // 위와 같은 반복을 했을 때, 세 수를 같은 수로 만들 수 있는지 확인한다.
        // 완전 탐색을 통해 풀었다.
        Scanner sc = new Scanner(System.in);

        Queue<State> queue = new LinkedList<>();
        int[] temp = new int[3];
        for (int i = 0; i < 3; i++)
            temp[i] = sc.nextInt();
        Arrays.sort(temp);
        queue.add(new State(temp));

        boolean can = false;
        HashSet<String> hashSet = new HashSet<>();      // 현재의 상태를 저장할 String HashSet.
        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.array[0] == current.array[1] && current.array[1] == current.array[2]) {     // 세 수가 모두 같다면 종료.
                can = true;
                break;
            }
            next(queue, current.array, hashSet, 0);     // 그렇지 않다면, 0번 수를 작은 수로 처리했을 때와
            next(queue, current.array, hashSet, 1);     // 1번 수를 작은 수로 처리 했을 때 두가지 모두 처리해주자.
        }
        System.out.println(can ? "1" : "0");
    }

    static void next(Queue<State> queue, int[] array, HashSet<String> hashSet, int order) {     // order 가 작은 수로 선택한 값의 idx
        int[] temp = array.clone();
        temp[2] -= temp[order];
        temp[order] *= 2;
        Arrays.sort(temp);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++)
            sb.append(temp[i]);

        if (hashSet.contains(sb.toString()))    // 만들어진 상태가 HashSet 에 저장되어있다면 다시 Queue 에 넣을 필요가 없다.
            return;

        hashSet.add(sb.toString());         // 아니라면 HashSet에 현재 상태를 저장하고
        queue.add(new State(temp));         // Queue 에 담는다.
    }
}