package 친구네트워크;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static int[] parent;
    static int[] friends;

    public static void main(String[] args) {
        // union-find 문제이나, 매번 네트워크 내의 인원을 출력해야해서 약간의 어레인지가 필요하다.
        // 보통 ranks로 구분하여 두 집합의 크기를 비교했으나, 이 경우에는 인원의 숫자를 매번 출력해야하니, 이를 기준으로 삼아 풀어보자.
        Scanner sc = new Scanner(System.in);

        int testCase = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 0; tc < testCase; tc++) {
            int f = sc.nextInt();
            sc.nextLine();
            init(f);    // 최대 f * 2만큼의 인원이 들어올 수 있다(모두 다른 이름인 경우) 이에 맞춰 parent 와 friends 배열들을 선언해주자.
            HashMap<String, Integer> hashMap = new HashMap<>();     // 이름에 따른 번호를 할당해줄 것이다.

            for (int i = 0; i < f; i++) {
                String[] names = sc.nextLine().split(" ");
                for (String name : names) {
                    if (!hashMap.containsKey(name)) {       // 각 이름마다 이미 알고 있던 이름인지 확인하여
                        hashMap.put(name, hashMap.size());      // 없다면 새로 번호를 할당 받고
                        int num = hashMap.get(name);
                        parent[num] = num;          // parent 배열의 값과
                        friends[num] = 1;           // frineds 배열의 값을 초기화해주자.
                    }
                }
                if (findParent(hashMap.get(names[0])) != findParent(hashMap.get(names[1])))
                    union(hashMap.get(names[0]), hashMap.get(names[1]));    // 두 사람을 하나의 네트워크로 만들어주고,
                sb.append(friends[findParent(hashMap.get(names[0]))]).append("\n");         // 문제에서 애매하게 표현되어있어 헤맸으나, 처음 주어진 사람의 네트워크에 속한 인원을 출력한다.
            }
        }
        System.out.println(sb.toString());
    }

    static void init(int f) {
        parent = new int[f * 2];
        friends = new int[f * 2];
    }

    static int findParent(int n) {
        if (parent[n] == n)
            return n;
        parent[n] = findParent(parent[n]);
        return parent[n];
    }

    static void union(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if (friends[pa] > friends[pb]) {        // 큰 상관은 없으나 연산 상 우위에 있으므로, 친구가 많은 집합에 포함시켜주자.
            parent[pb] = pa;
            friends[pa] += friends[pb];     // 이 때 작은 쪽 집합의 인원만큼 큰 집합에 더해진다.
        } else {
            parent[pa] = pb;            // 반대의 경우도 마찬가지.
            friends[pb] += friends[pa];
        }
    }
}