import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String POS_ANSWER = ANSI_GREEN + "YES!" + ANSI_RESET;
    public static final String NEG_ANSWER = ANSI_RED + "NO!" + ANSI_RESET;

    /**
     * Main method.
     */
    public static void main(String[] args) {
        GraphTask a = new GraphTask();
        a.run();
    }

    /**
     * Actual main method to run examples and everything.
     */
    public void run() {
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println(ANSI_RED + "Start" + ANSI_RESET);
            System.out.println("----------------------------------------");
            Graph g = new Graph("G");
            int rndEdgesCount = 15 + rnd.nextInt(11);
            g.createRandomSimpleGraph(15, rndEdgesCount);
            System.out.print("Our graph with 15 vertices and "+ rndEdgesCount +" edges:");
            System.out.println(g);

            System.out.print("For the given graph find its diameter ");
            System.out.println("using Kristina's function " + ANSI_BLUE + "findDiameterOfGraph()" + ANSI_RESET + ":");
            System.out.print("And the diameter is: ");
            long t1 = System.nanoTime();
            int diameter = g.findDiameterOfGraph();
            long t2 = System.nanoTime();
            double delta = (t2 - t1) / 1000000d;
            System.out.println(diameter);
            System.out.println("Time elapsed: " + delta);
            g.resetGraphInfo();
            System.out.println("----------------------------------------");
            System.out.println();


            System.out.println("From this graph taking two random vertices:");
            Vertex v1 = g.getRandomGraphVertex();
            System.out.println("Vertex 1: " + v1);
            Vertex v2 = v1;
            while (v2 == v1) {
                v2 = g.getRandomGraphVertex();
            }
            System.out.println("Vertex 2: " + v2);
            System.out.println();

            System.out.print("Between those vertices find all the possible paths");
            System.out.println(" using Ksenija's function " + ANSI_BLUE + "allPathsBetweenVertexes()" + ANSI_RESET + ":");
            t1 = System.nanoTime();
            List<List<Arc>> paths = g.allPathsBetweenVertexes(v1, v2);
            t2 = System.nanoTime();
            System.out.println("And the total paths amount: " + paths.size());
            delta = (t2 - t1) / 1000000d;
            System.out.println("Time elapsed: " + delta);
            System.out.println("----------------------------------------");
            System.out.println();
            g.resetGraphInfo();

            System.out.println("Out of existing paths between " + v1 + " and " + v2 + ", find the longest (by arcs weights) ");
            System.out.println("using Anželika's function " + ANSI_BLUE + "getLongestElementarPath()" + ANSI_RESET + ":");

            t1 = System.nanoTime();
            ArrayList<Arc> longestPath = g.getLongestElementarPath(v1, v2);
            t2 = System.nanoTime();
            System.out.println("And the longest path is: " + longestPath);
            System.out.println("Longest path total weight is " + g.getPathLength(longestPath));
            delta = (t2 - t1) / 1000000d;
            System.out.println("Time elapsed: " + delta);
            System.out.println("Using <code>allPaths.contains(longestPath)</code> check if the longest path is listed in all paths");
            System.out.println("Longest path is listed? " + (paths.contains(longestPath) ? POS_ANSWER : NEG_ANSWER));
            System.out.println("----------------------------------------");
            System.out.println();

            System.out.println("Make the given graph biconnected using Juri's function " + ANSI_BLUE + "makeBiconnected()" + ANSI_RESET + ":");
            System.out.print("First of all find graph cut vertices: ");
            List<Vertex> av = g.getArticulationVertices();
            System.out.println(av);
            System.out.println("Then apply " + ANSI_BLUE + "makeBiconnected()" + ANSI_RESET + " function");
            t1 = System.nanoTime();
            g.makeBiconnected();
            t2 = System.nanoTime();
            delta = (t2 - t1) / 1000000d;
            System.out.println("Time elapsed: " + delta);
            g.resetGraphInfo();

            System.out.println("Ensure, that graph became biconnected searching for the cut vertices again (no cut vertices should be found)");
            av = g.getArticulationVertices();
            System.out.println("No articulation vertices found? " + (av.isEmpty() ? POS_ANSWER : NEG_ANSWER));
            g.resetGraphInfo();
            System.out.println("----------------------------------------");
            System.out.println();

            System.out.println("On this (already biconnected) graph find its power of 2 (in sense of multiplication) ");
            System.out.println("using Maria's function " + ANSI_BLUE + "pow()" + ANSI_RESET + ":");
            t1 = System.nanoTime();
            g.pow(2);
            t2 = System.nanoTime();
            delta = (t2 - t1) / 1000000d;
            System.out.println("Time elapsed: " + delta);
            System.out.println();
            System.out.println(ANSI_RED + "End!" + ANSI_RESET);
            System.out.println();
            System.out.println();
        }
    }

    class Vertex {

        protected String id;
        protected Vertex next;
        protected Arc first;
        protected int info = 0;

        /**
         * Vertex discovery time in DFS tree
         */
        protected int dfn = 0;

        /**
         * Field to keep the earliest visited vertex dfn (the vertex with minimum discovery time)
         * that can be reached from subtree rooted with <code>this</code>
         */
        protected int low = -1;

        /**
         * When performing path search, set as true, if vertex was visited by the algorithm
         */
        public boolean wasVisited = false;

        Vertex(String s, Vertex v, Arc e) {
            id = s;
            next = v;
            first = e;
        }

        Vertex(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }
    }


    /**
     * Arc represents one arrow in the graph. Two-directional edges are
     * represented by two Arc objects (for both directions).
     */
    public class Arc {

        protected String id;
        protected Vertex target;
        protected Arc next;
        protected int info = 0;
        protected int weight = 0;

        Arc(String s, Vertex v, Arc a) {
            id = s;
            target = v;
            next = a;
        }

        Arc(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id + "{" + weight + "}";
        }

    }


    class Graph {

        private String id;
        protected Vertex first;
        protected int info = 0;
        // You can add more fields, if needed

        Graph(String s, Vertex v) {
            id = s;
            first = v;
        }

        Graph(String s) {
            this(s, null);
        }

        @Override
        public String toString() {
            String nl = System.getProperty("line.separator");
            StringBuffer sb = new StringBuffer(nl);
            sb.append(id);
            sb.append(nl);
            Vertex v = first;
            while (v != null) {
                sb.append(v.toString());
                sb.append(String.format("{%s [%s]}", v.dfn, v.low));
                sb.append(" -->");
                Arc a = v.first;
                while (a != null) {
                    sb.append(" ");
                    sb.append(a.toString());
                    sb.append(" (");
                    sb.append(v.toString());
                    sb.append("->");
                    sb.append(a.target.toString());
                    sb.append(")");
                    a = a.next;
                }
                sb.append(nl);
                v = v.next;
            }
            return sb.toString();
        }

        public Vertex createVertex(String vid) {
            Vertex res = new Vertex(vid);
            res.next = first;
            first = res;
            return res;
        }

        public Arc createArc(String aid, Vertex from, Vertex to, int weight) {
            Arc res = new Arc(aid);
            res.weight = weight;
            res.next = from.first;
            from.first = res;
            res.target = to;
            return res;
        }

        /**
         * Create a connected undirected random tree with n vertices.
         * Each new vertex is connected to some random existing vertex.
         *
         * @param n number of vertices added to this graph
         */
        public Vertex createRandomTree(int n) {
            if (n <= 0)
                return null;
            Vertex[] varray = new Vertex[n];
            int i = 0;
            for (; i < n; i++) {
                varray[i] = createVertex("v" + (n - i));
                if (i > 0) {
                    int vnr = (int) (Math.random() * i);
                    int weight = new Random().nextInt(9) + 1;
                    createArc("a" + varray[vnr].toString() + "_"
                            + varray[i].toString(), varray[vnr], varray[i], weight);
                    createArc("a" + varray[i].toString() + "_"
                            + varray[vnr].toString(), varray[i], varray[vnr], weight);
                } else {
                }
            }
            return varray[0];
        }

        /**
         * Create an adjacency matrix of this graph.
         * Side effect: corrupts info fields in the graph
         *
         * @return adjacency matrix
         */
        public int[][] createAdjMatrix() {
            info = 0;
            Vertex v = first;
            while (v != null) {
                v.info = info++;
                v = v.next;
            }
            int[][] res = new int[info][info];
            v = first;
            while (v != null) {
                int i = v.info;
                Arc a = v.first;
                while (a != null) {
                    int j = a.target.info;
                    res[i][j]++;
                    a = a.next;
                }
                v = v.next;
            }
            return res;
        }

        /**
         * Create a connected simple (undirected, no loops, no multiple
         * arcs, edges (in range 1-10) have weights) random graph with n vertices and m edges.
         *
         * @param n number of vertices
         * @param m number of edges
         */
        public void createRandomSimpleGraph(int n, int m) {
            if (n <= 0)
                return;
            if (n > 2500)
                throw new IllegalArgumentException("Too many vertices: " + n);
            if (m < n - 1 || m > n * (n - 1) / 2)
                throw new IllegalArgumentException
                        ("Impossible number of edges: " + m);
            first = null;
            createRandomTree(n);       // n-1 edges created here
            Vertex[] vert = new Vertex[n];
            Vertex v = first;
            int c = 0;
            while (v != null) {
                vert[c++] = v;
                v = v.next;
            }
            int[][] connected = createAdjMatrix();
            int edgeCount = m - n + 1;  // remaining edges
            while (edgeCount > 0) {
                int i = (int) (Math.random() * n);  // random source
                int j = (int) (Math.random() * n);  // random target
                if (i == j)
                    continue;  // no loops
                if (connected[i][j] != 0 || connected[j][i] != 0)
                    continue;  // no multiple edges
                Vertex vi = vert[i];
                Vertex vj = vert[j];
                int weight = new Random().nextInt(9) + 1;  // Assign random weight for edge
                createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj, weight);
                connected[i][j] = 1;
                createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi, weight);
                connected[j][i] = 1;
                edgeCount--;  // a new edge happily created
            }
        }

        /** Helper method to get random vertex of graph */
        public Vertex getRandomGraphVertex() {
            int verticesCount = 0;
            Vertex v = first;
            while (v != null) {
                v = v.next;
                verticesCount++;
            }

            Random rnd = new Random();
            int vertexNumber = rnd.nextInt(verticesCount);

            v = first;
            for (int i = 0; i < vertexNumber; i++) {
                v = v.next;
            }
            return v;
        }

        /** Helper method to reset graph vertices and vertices arcs fields to default values */
        public void resetGraphInfo() {
            Vertex v = first;

            while (v != null) {
                v.dfn = 0;
                v.low = -1;
                v.wasVisited = false;
                Arc a = v.first;
                while (a != null) {
                    a.info = 0;
                    a = a.next;
                }
                v = v.next;
            }
        }

        /**
         * Get a list of articulation vertices of graph <code>this</code>.
         * Function provides the arguments to findArticulationVertices method.
         *
         * @return List of articulation vertices
         */
        public List<Vertex> getArticulationVertices() {
            Set<Vertex> res = new HashSet<>();
            AtomicInteger globalCounter = new AtomicInteger(0);
            findArticulationVertices(first, null, res, globalCounter);
            return new ArrayList<>(res);
        }

        /**
         * Recursive function that uses deep-first search to construct a DFS tree
         * and set up the discovery time for vertices.
         * While backtracking computes vertices low values and by this value determines,
         * is the given vertex an cut vertex.
         * Adds cut vertices into common <code>articulationVertices</code>.
         * <code>Set</code> if used as a data structure of <code>articulationVertices</code>
         * because same vertex can be determined to be cut vertex multiple times.
         * <code>AtomicInteger</code> is used for DFS discovery time counter to increase value globally,
         * comparing to Integer which will be increased only in current function.
         * Based on Aakash Hasija solution:
         * https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
         *
         * @param curr                 currently processed Vertex
         * @param parent               ancestor of Vertex <code>curr</code> in DFS tree
         * @param articulationVertices set of found articulation vertices
         * @param counter              global counter for setting vertices discovery time up
         */
        private void findArticulationVertices(Vertex curr, Vertex parent, Set<Vertex> articulationVertices,
                                              AtomicInteger counter) {
            // Count of children in DFS Tree, used only for first vertex in DFS tree
            int children = 0;

            // Initialize discovery time and low value
            curr.dfn = counter.incrementAndGet();
            curr.low = counter.get();

            // Go through all vertex connections
            Arc connection = curr.first;
            while (connection != null) {
                Vertex target = connection.target;
                // If target is not visited yet,
                // then make it a child of current Vertex in DFS tree and recur for it
                if (target.dfn <= 0) { // means that vertex was not visited
                    children++;

                    findArticulationVertices(target, curr, articulationVertices, counter);

                    // Check if the subtree rooted with target has a connection to
                    // one of the ancestors of current Vertex
                    curr.low = Math.min(curr.low, target.low);

                    boolean isDFSTreeRoot = curr.id.equals(first.id);

                    // current Vertex is an articulation point in following cases

                    // (1) it is root of DFS tree and has two or more children.
                    if (isDFSTreeRoot && children > 1)
                        articulationVertices.add(curr);

                        // (2) If it is not root and low value of one of its child
                        // is equals-more than discovery value of current Vertex.
                    else if (!isDFSTreeRoot && target.low >= curr.dfn)
                        articulationVertices.add(curr);
                }
                // Update low value of current Vertex for parent function calls.
                else if (target != parent) {
                    curr.low = Math.min(curr.low, target.dfn);
                }

                connection = connection.next; // take next connection
            }
        }

        /**
         * Algorithm to add edges to vertices to create global cycle
         * between all the vertices of graph <code>this</code>.
         * Such cycle assures the biconnectivity of graph.
         * Each vertex connections are iterated to ensure that connection
         * with next vertex is missing, before create an edge.
         */
        public void makeBiconnected() {
            Vertex v = first;
            boolean isEndOfCycle = false;

            while (!isEndOfCycle) {

                Vertex next;
                if (v.next != null) {
                    next = v.next;
                } else {
                    next = first;
                    isEndOfCycle = true;
                }

                Arc connection = v.first;
                boolean shouldAddConnection = true;
                while (shouldAddConnection && connection != null) {
                    if (connection.target.id.equals(next.id)) shouldAddConnection = false;
                    connection = connection.next;
                }
                if (shouldAddConnection) {
                    createArc("a" + v.toString() + '_' + next.toString(), v, next, 1);
                    createArc("a" + next.toString() + '_' + v.toString(), next, v, 1);
                }
                v = next;
            }
        }

        /**
         * Raise graph to the power of <code>d</code> in sense of multiplication
         * @param d power value
         */
        public void pow(int d) {
            if (d < 0) {
                throw new IllegalArgumentException("Degree must be positive!");
            }
            if (d == 0) removeArcs(first);
            else {
                int[][] degreeMatrix = matrixDegree(d);
                removeArcs(first);
                addArcsByMatrix(degreeMatrix);
            }
        }


        /**
         * The method to print out the matrix
         *
         * @param m the matrix of graph
         */
        public void printMatrix(int[][] m) {
            List<Vertex> vertices = new ArrayList<>();
            Vertex v = first;
            while (v != null) {
                vertices.add(v);
                v = v.next;
            }

            System.out.print("   ");
            for (Vertex vertex : vertices) {
                System.out.format("%4s", vertex.toString());
            }
            System.out.println();

            int counter = 0;
            for (int[] ints : m) {
                System.out.format("%3s", vertices.get(counter).toString());
                counter++;
                for (int j = 0; j < m[0].length; j++) {
                    System.out.format("%3d ", ints[j]);
                }
                System.out.println();
            }
        }


        /**
         * Method which raises graph matrix in a degree(uses recursion)
         *
         * @return matrix raised in a degree
         */
        public int[][] matrixDegree(int d) {
            int[][] mA = createAdjMatrix();
            System.out.println("Matrix of graph " + id + ":");
            printMatrix(mA);
            System.out.println("Matrix of graph " + id + "^" + d + ":");
            int[][] res = mA;

            while (d > 1) {
                res = matrixMultiplication(mA, res);
                d -= 1;
            }

            printMatrix(res);
            return res;
        }
//      http://sh2533.blogspot.com/2013/01/blog-post_7928.html

        /**
         * Create a new matrix which is product of a multiplication of  2 matrix
         *
         * @return matrix multiplication
         */
        public int[][] matrixMultiplication(int[][] mA, int[][] mB) {
            int m = mA.length;
            int n = mB[0].length;
            int o = mB.length;
            int[][] res = new int[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < o; k++) {
                        res[i][j] += mA[i][k] * mB[k][j];
                    }
                    res[i][j] = res[i][j] > 0 ? 1 : 0;
                    if (i == j) res[i][j] = 0;
                }
            }

            return res;
        }


        /**
         * Adds to the graph all links that are specified in the matrix.
         * Does not add to points of connection in themselves, does not add multiple connections.
         */
        public void addArcsByMatrix(int[][] degreeMatrix) {
            int[][] connected = createAdjMatrix();
            int i = 0;
            int j = 0;
            Vertex vStart = first;
            Vertex vEnd = first;
            while (vStart != null) {
                while (vEnd != null) {
                    if (i == j) {
                        vEnd = vEnd.next;
                        j++;
                        continue;
                    } // no loops
                    if (degreeMatrix[i][j] == 0) {
                        vEnd = vEnd.next;
                        j++;
                        continue;
                    }
                    if (connected[i][j] != 0 || connected[j][i] != 0) {
                        vEnd = vEnd.next;
                        j++;
                        continue;
                    } // no multiple edges
                    createArc("a" + vStart.toString() + "_" + vEnd.toString(), vStart, vEnd, 1);
                    connected[i][j] = 1;
                    createArc("a" + vEnd.toString() + "_" + vStart.toString(), vEnd, vStart, 1);
                    connected[j][i] = 1;
                    vEnd = vEnd.next;
                    j++;
                }
                vEnd = first;
                j = 0;
                vStart = vStart.next;
                i++;
            }
        }

        /**
         * Method to remove Arcs, used only if degree is 0
         */
        public void removeArcs(Vertex start) {
            if (start.next != null && start.next.first != null) removeArcs(start.next);
            start.first = removeNextArc(start.first);
        }

        /**
         * Method to remove Arcs, which used in method "removeArcs"
         */
        private Arc removeNextArc(Arc arc) {
            if (arc.next != null) return removeNextArc(arc.next);
            return null;
        }

        /**
         * Create a longest path from start to end without repeat of Vertexes and Arcs.
         *
         * @param start Vertex
         * @param end   Vertex
         * @return List with arcs of path.
         */
        public ArrayList<Arc> getLongestElementarPath(Vertex start, Vertex end) {
            ArrayList<Arc> result = getLongestElementarPath(start, end, new ArrayList<>());
            if (result.size() == 0) {
                System.out.println(result);
                throw new IllegalArgumentException("Vertexes should be from the same graph!");
            }
            return result;
        }

        /**
         * Create a longest path from start to end without repeat of Vertexes and Arcs.
         *
         * @param start Vertex
         * @param end   Vertex
         * @param path   List with arcs
         * @return List with arcs of path.
         */
        public ArrayList<Arc> getLongestElementarPath(Vertex start, Vertex end, ArrayList<Arc> path) {
            if (start.equals(end)) {
                path.add(new Arc(""));
                return path;
            }
            Arc arc = start.first;
            if (arc == null) return path;

            start.wasVisited = true;
            ArrayList<Arc> longestPartialPath = new ArrayList<>();

            do {
                if (arc.target.wasVisited) {
                    arc = arc.next;
                    continue;
                }

                ArrayList<Arc> newPartialPath;

                ArrayList<Arc> temp = new ArrayList<>(path);
                temp.add(arc);
                if (arc.target.equals(end)) {
                    newPartialPath = temp;
                } else newPartialPath = getLongestElementarPath(arc.target, end, temp);

                if (getPathLength(newPartialPath) > getPathLength(longestPartialPath)) longestPartialPath = newPartialPath;
                arc = arc.next;

            } while (arc != null);
            start.wasVisited = false;

            return longestPartialPath;
        }

        /**
         * Calculate total length of arcs from input list.
         *
         * @param path List with arcs.
         * @return int sum of arcs length.
         */
        public int getPathLength(ArrayList<Arc> path) {
            int pathLen = 0;
            for (Arc arc : path) {
                pathLen += arc.weight;
            }
            return pathLen;
        }

        /**
         * Runner for allPathsBetweenVertexes(start, end, path)
         *
         * @param start beginning Vertex
         * @param end target Vertex
         * @return list of possible paths between start and end
         */
        public List<List<Arc>> allPathsBetweenVertexes(Vertex start, Vertex end) {
            return allPathsBetweenVertexes(start, end, new ArrayList<>());
        }


        /**
         * a method that looks for all possible paths between two vertexes.
         * you cannot use the same vertex twice during the journey.
         *
         * @param start number of vertices
         * @param end   number of edges
         * @param path   number of edges
         */
        public List<List<Arc>> allPathsBetweenVertexes(Vertex start, Vertex end, List<Arc> path) {
            List<List<Arc>> extraRes = new ArrayList<>();
            extraRes.add(path);

            if (start.equals(end)) return extraRes;
            Arc arc = start.first;
            if (arc == null) return extraRes;

            start.wasVisited = true;
            List<List<Arc>> pathsBetweenVertexes = new ArrayList<>();

            do {
                if (arc.target.wasVisited) {
                    arc = arc.next;
                    continue;
                }
                List<List<Arc>> newPartialPath;
                List<Arc> listOfPoints = new ArrayList<>(path);
                listOfPoints.add(arc);
                if (arc.target.equals(end)) {
                    List<List<Arc>> tmp = new ArrayList<>();
                    tmp.add(listOfPoints);
                    newPartialPath = tmp;
                }
                else newPartialPath = allPathsBetweenVertexes(arc.target, end, listOfPoints);

                pathsBetweenVertexes.addAll(newPartialPath);
                arc = arc.next;

            } while (arc != null);

            start.wasVisited = false;

            return pathsBetweenVertexes;
        }


        /**
         * Find diameter of graph by adding into list arc weights (as a distance between vertices)
         * from first random vertex to another and then from second random vertex to another.
         * Maximal of those distances is diameter of graph.
         *
         * @return diameter of graph
         */
        public int findDiameterOfGraph() {
            Vertex start = this.first;
            Vertex end = this.first;
            assert start != null;
            //array with distances from one random vertex to another
            List<List<Integer>> distanceMatrix = new ArrayList<>();

            do {
                List<Integer> row = new ArrayList<>();
                do {
                    row.add(getDistanceBetweenVertexes(start, end, 0)); // minimal path dist from one point to another
                    end = end.next;
                } while (end != null);

                distanceMatrix.add(row); //adding row to list
                end = this.first;
                start = start.next; //moving to another point
            } while (start != null);


            // finding longest distance from minimal paths
            return distanceMatrix
                    .stream()
                    .mapToInt(row -> row.stream()
                            .mapToInt(x -> x)
                            .max()
                            .orElseThrow())
                    .max()
                    .orElseThrow();
        }

        /**
         * Find minimal distance from one random vertex to another
         *
         * @param start    first random vertex from which we go
         * @param end      second random vertex to which we go
         * @param distance distance from one point to another
         * @return minimal distance
         */
        public int getDistanceBetweenVertexes(Vertex start, Vertex end, int distance) {
            start.wasVisited = true;
            // if start point equals second point then distance must be 0
            if (start.equals(end)) return distance;

            // arc for first vertex
            Arc arc = start.first;

            int shortestDistance = Integer.MAX_VALUE;

            do {
                // if vertex we want to go was used then moving to another
                if (arc.target.wasVisited) {
                    arc = arc.next;
                    continue;
                }
                int newDistance;

                if (arc.target.equals(end)) newDistance = distance + arc.weight;
                else newDistance = getDistanceBetweenVertexes(arc.target, end, distance + arc.weight);

                if (newDistance < shortestDistance) shortestDistance = newDistance;
                arc = arc.next;

            } while (arc != null);
            start.wasVisited = false;
            return shortestDistance;
        }

    }
}
