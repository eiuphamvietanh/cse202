import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class TreeDiameter {
    static int maxLevel = 0;
    static int minVertex = 0;

    public static void main(String[] args) throws IOException {
        sc = new InputReader(System.in);

        int n = sc.nextInt();
        Vertex[] vertexs = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertexs[i] = new Vertex(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            vertexs[u].add(vertexs[v]);
            vertexs[v].add(vertexs[u]);
        }

        // First BFS to find the farthest vertex
        int farthest = bfs(vertexs[0]);

        // Reset visited states for the second BFS
        for (Vertex vertex : vertexs) {
            vertex.visited = false;
            vertex.level = 0;
        }

        // Second BFS to find the tree diameter
        maxLevel = 0; // Reset maxLevel before second BFS
        bfs(vertexs[farthest], 0);

        System.out.println(Math.min(minVertex, farthest) + " " + maxLevel);
    }

    static void bfs(Vertex v, int level) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(v);
        v.visited = true;
        v.level = 0;
        minVertex = v.id;

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            for (Vertex w : u.adj) {
                if (!w.visited) {
                    queue.add(w);
                    w.visited = true;
                    w.level = u.level + 1;
                    if (w.level > maxLevel) {
                        maxLevel = w.level;
                        minVertex = w.id;
                    } else if (w.level == maxLevel) {
                        minVertex = Math.min(minVertex, w.id);
                    }
                }
            }
        }
    }

    static int bfs(Vertex v) {
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(v);
        v.visited = true;
        v.level = 0;

        int maxLevel = 0;
        int farthestNode = v.id;

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();
            for (Vertex w : u.adj) {
                if (!w.visited) {
                    queue.add(w);
                    w.visited = true;
                    w.level = u.level + 1;
                    if (w.level > maxLevel) {
                        maxLevel = w.level;
                        farthestNode = w.id;
                    } else if (w.level == maxLevel) {
                       farthestNode = Math.min(farthestNode, w.id);
                    }
                }
            }
        }
        return farthestNode;
    }

    static class Vertex {
        int id;
        int level;
        boolean visited;
        List<Vertex> adj = new ArrayList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public void add(Vertex v) {
            adj.add(v);
        }
    }

    static InputReader sc;

    static class InputReader {
        private byte[] inbuf = new byte[2 << 23];
        public int lenbuf = 0, ptrbuf = 0;
        public InputStream is;

        public InputReader(InputStream stream) throws IOException {
            inbuf = new byte[2 << 23];
            lenbuf = 0;
            ptrbuf = 0;
            is = System.in;
            lenbuf = is.read(inbuf);
        }

        public boolean hasNext() throws IOException {
            if (skip() >= 0) {
                ptrbuf--;
                return true;
            }
            return false;
        }

        public int nextInt() {
            int num = 0, b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }

        private int readByte() {
            if (lenbuf == -1)
                throw new InputMismatchException();
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0)
                    return -1;
            }
            return inbuf[ptrbuf++];
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        private int skip() {
            int b;
            while ((b = readByte()) != -1 && isSpaceChar(b))
                ;
            return b;
        }
    }
}
