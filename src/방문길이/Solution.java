package 방문길이;

import java.util.HashSet;
import java.util.Objects;

class Point {
    int r;
    int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

class Path {
    Point from;
    Point to;

    public Path(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        Path path = (Path) o;
        if (this.from.r == path.from.r && this.from.c == path.from.c && this.to.r == path.to.r && this.to.c == path.to.c)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from.r, from.c, to.r, to.c);
    }
}

public class Solution {
    static HashSet<Path> hashSet;

    public static void main(String[] args) {
        // x, y가 모두 -5 ~ +5 인 좌표계 안에서, 이동한다고 할 때, 처음으로 걷는 길의 길이를 계산하여야한다.
        // from과 to의 x,y 좌표가 담긴 Path를 만들어 HashSet에 담자.
        // 단 객체의 같음을 표시하여야하기 때문에 equals와 hashCode를 오버라이딩해서 같은 좌표를 갖으면 같은 객체로 판단하도록 해주자.
        // a -> b로 가나 b->a로 가나 길은 동일하므로, 둘 중의 하나의 경우에도 둘 다 HashSet에 담은 후, 마지막 전체 경로에서 1/2을 해주도록 하자.

        String dirs = "ULURRDLLU";

        hashSet = new HashSet<>();
        Point character = new Point(0, 0);

        for (int i = 0; i < dirs.length(); i++)
            character = move(dirs.charAt(i), character);
        System.out.println(hashSet.size() / 2);
    }

    static Point move(char c, Point point) {
        Point next = switch (c) {
            case 'U' -> new Point(point.r - 1, point.c);
            case 'D' -> new Point(point.r + 1, point.c);
            case 'R' -> new Point(point.r, point.c + 1);
            case 'L' -> new Point(point.r, point.c - 1);
            default -> null;
        };

        if (next.r < -5 || next.r > 5 || next.c < -5 || next.c > 5)     // 주어진 범위를 벗어날 때는 이동하지 말자.
            return point;

        hashSet.add(new Path(point, next));
        hashSet.add(new Path(next, point));
        return next;
    }
}