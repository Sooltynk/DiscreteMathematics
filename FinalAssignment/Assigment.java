package discretemathematics;

import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

import static discretemathematics.Assigment.Topic1.exercise4;

/*
 * This delivery consists of implementing all the methods annotated with the comment "// TO DO".
 *
 * Each subject has equal weight, and the assessment consists of:
 *
 * - Mainly, the correct operation of each method (trying with different inputs). You have
 *   some examples in the `main` method.
 *
 * - Code cleanup (think of it as typos). The standard you must follow is the guide
 *   Google style guide for Java: https://google.github.io/styleguide/javaguide.html . Is not
 *   necessary to follow it strictly, but we will base ourselves on it to judge if any deviate from it
 *   a lot.
 *
 * Because of how this delivery is set up, you don't need (and can't) use any `import`
 * additional, nor methods of classes that are not already imported. What you can do is define
 * all the additional methods you want (in order and within the relevant topic).
 *
 * You can do this delivery in groups of a maximum of 3 people, and you will need at least Java 8.
 * To submit, please put your names below and submit only this file.
 * - Name 1: Alberto Pérez Ancín
 * - Name 2: Iván Riera Morales
 * - Name 3: Marc Oliver Orfila 
 *
 * The delivery will be made through a task in the Digital Classroom before the date given to you
 * communicated and we recommend that you work with a fork of this repository to follow more easily
 * updates with new statements. If you can't visualize a statement well, make sure
 * that your text editor is configured with UTF-8 encoding.
 */
class Assigment {

    /*
     * Here are the exercises for Topic 1 (Logic).
     *
     * The methods receive as parameters the universe (represented as an array) and the appropriate predicates
     * (for example `Predicate<Integer> p`). To evaluate this predicate, if `x` is an element of
     * universe, you can do it like `p.test(x)`, it returns a boolean. The predicates of two
     * variables are of type `BiPredicate<Integer, Integer>` and are similarly evaluated as
     * `p.test(x, y)`.
     *
     * In each of these exercises we ask you to return `true` given the universe and the predicates
     * or `false' depending on whether the given proposition is true (assume that the universe is sufficiently
     * small enough to use brute force))
     */
    static class Topic1 {

        /*
         * Is it true that ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
         */
        static boolean exercise1(
                int[] universe,
                BiPredicate<Integer, Integer> p,
                Predicate<Integer> q,
                Predicate<Integer> r) {

            for (int x : universe) {
                for (int y : universe) {
                    if (p.test(x, y) && !(r.test(y) && q.test(x))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        /*
         * Is it true that ∃!x. ∀y. Q(y) -> P(x) ?
         */
        static boolean exercise2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
            for (int y : universe) {
                int counter;
                counter = 0;
                for (int x : universe) {
                    if (p.test(x) || !(q.test(y))) {
                        counter++;
                    }
                }
                if (counter != 1) {
                    return false;
                }
            }

            return true;
        }

        /*
         * Is it true that ¬(∃x. ∀y. y ⊆ x) ?
         *
         * Note that the members of the universe are arrays, treat them as sets and you can assume
         * that each of them is ordered from smallest to largest.
         */
        static boolean exercise3(int[][] universe) {
            for (int[] x : universe) {
                boolean isTrue;
                isTrue = true;

                for (int[] y : universe) {
                    if (checkSubset(x, y)) {
                        isTrue = true;
                    } else {
                        isTrue = false;
                        break;
                    }
                }

                if (isTrue == true) {
                    return false;
                }
            }
            return true;
        }

        /*
         * Is it true that ∀x. ∃!y. x·y ≡ 1 (mod n) ?
         */
        static boolean exercise4(int[] universe, int n) {
            for (int x : universe) {
                for (int y : universe) {
                    if ((x * y) % n == 1) {
                        return true;
                    }
                }
            }
            return false;
        }

        //ADDITIONAL METHOD (Used for exercise 3)
        /**
         * Functional method to check if is a subset
         *
         * @param array1
         * @param array2
         * @return false o true
         */
        static boolean checkSubset(int[] array1, int[] array2) {
            int subset1 = array1.length;
            int suset2 = array2.length;
            int i;
            int j;
            for (i = 0; i < subset2; i++) {
                for (j = 0; j < subset1; j++) {
                    if (array2[i] == array1[j]) {
                        break;
                    }
                }

                if (j == subset1) {
                    return false;
                }
            }

            return true;
        }

        /*
         * Here I have some examples and proves related to these exercises (see `main`)
         */
        static void tests() {
            // Exercise 1
            // ∀x,y. P(x,y) -> Q(x) ^ R(y)

            assertThat(
                    exercise1(
                            new int[]{2, 3, 5, 6},
                            (x, y) -> x * y <= 4,
                            x -> x <= 3,
                            x -> x <= 3
                    )
            );

            assertThat(
                    !exercise1(
                            new int[]{-2, -1, 0, 1, 2, 3},
                            (x, y) -> x * y >= 0,
                            x -> x >= 0,
                            x -> x >= 0
                    )
            );

            // Exercise 2
            // ∃!x. ∀y. Q(y) -> P(x) ?
            assertThat(
                    exercise2(
                            new int[]{-1, 1, 2, 3, 4},
                            x -> x < 0,
                            x -> true
                    )
            );

            assertThat(
                    !exercise2(
                            new int[]{1, 2, 3, 4, 5, 6},
                            x -> x % 2 == 0, // x is a multiple of 2
                            x -> x % 4 == 0 // x is a multiple of 4
                    )
            );

            // Exercise 3
            // ¬(∃x. ∀y. y ⊆ x) ?
            assertThat(
                    exercise3(new int[][]{{1, 2}, {0, 3}, {1, 2, 3}, {}})
            );

            assertThat(
                    !exercise3(new int[][]{{1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3}})
            );

            // Exercise 4
            // It is true that ∀x. ∃!y. x·y ≡ 1 (mod n) ?
            assertThat(
                    exercise4(
                            new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                            11
                    )
            );

            assertThat(
                    !exercise4(
                            new int[]{0, 5, 7},
                            13
                    )
            );
        }
    }

    /*
     * Here are the exercises for Topic 2 (Sets).
     *
     * As in Topic 1, for simplicity we will treat sets as arrays (without
     * repeated elements). So a set of sets of integers will have type int[][].
     *
     * We will also represent the relations as two-dimensional arrays, where the second dimension
     * it has only two elements. For example
     *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
     * and we will also give the set where it is defined, for example
     *   int[] a = {0,1,2};
     *
     * The functions f : A -> B (where A and B are subsets of the integers) are represented by giving int[] a,
     * int[] b, and an object of type Function<Integer, Integer> that you can evaluate as f.apply(x) (where
     * x is an integer from a and the result f.apply(x) is an integer from b).
     */
    static class Topic2 {

        /*
         * Is `p` a partition of `a`?
         *
         * `p` is an array of sets, you must check that they are elements of `a`. You can assume that
         * both `a` and each of the elements of `p` are ordered from smallest to largest.
         */
        static boolean exercise1(int[] a, int[][] p) {
            int[] a2;
            a2 = new int[a.length];
            int j;
            j = 0;
            for (int[] p1 : p) {
                for (int i = 0; i < p1.length; i++) {
                    if (j < a.length) {
                        a2[j] = p1[i];
                        j++;
                    } else {
                        return false;
                    }
                }
            }
            Arrays.sort(a2);
            for (int i = 0; i < a.length; i++) {
                if (a2[i] != a[i]) {
                    return false;
                }
            }
            return true;
        }

        /*
         * Check whether the relation `rel` defined on `a` is a partial order and that `x` is its minimum.
         *
         * You can assume that `x` belongs to `a` and that `a` is ordered from least to greatest.
         */
        static boolean exercise2(int[] a, int[][] rel, int x) {
            return false; // TO DO
        }

        /*
         * Assume that `f' is a function with domain `dom' and codomain `codom'. Find the antiimage of
         * `y` (sort the result from smallest to largest, you can use `Arrays.sort()`). You can assume
         * that `y' belongs to `codom' and that both `dom' and `codom' are also ordered from least to greatest.
         */
        static int[] exercise3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
            int[] antiimage;
            antiimage = new int[dom.length];
            int i;
            i = 0;
            for (int x : dom) {
                if (f.apply(x) == y) {
                    antiimage[i] = x;
                    i++;
                }
            }
            int[] result;
            result = new int[i];
            System.arraycopy(antiimage, 0, result, 0, i);
            return result;
        }

        /*
         * Assume that `f` is a function with domain `dom' and codomain `codom'. Return:
         * - 3 if `f` is bijective
         * - 2 if `f` it is only exhaustive
         * - 1 if `f` it is only injective
         * - 0 in any other case
         *
         * You can assume that `dom` and `codom` are ordered from least to greatest. For convenience, you can
         * use the constants defined below:
         */
        static final int NOTHING_SPECIAL = 0;
        static final int INJECTIVE = 1;
        static final int SURJECTIVE = 2;
        static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

        static int exercise4(int[] dom, int[] codom,
                Function<Integer, Integer> f) {
            boolean injective;
            injective = false;
            boolean exhaustive;
            exhaustive = true;
            int[] image;
            image = new int[dom.length];
            int k;
            k = 0;
            for (int x : dom) {
                image[k] = f.apply(x);
                k++;
            }
            Arrays.sort(image);

            for (int x : codom) {
                boolean iterate = false;
                for (int i = 0; i < image.length; i++) {
                    if (image[i] == x) {
                        iterate = true;
                    }
                }
                if (iterate) {
                } else {
                    exhaustive = false;
                }
            }

            if (dom.length == codom.length && exhaustive) {
                injective = true;
            } else {
                boolean iterate = false;
                for (int i = 0; i < image.length; i++) {
                    for (int j = i + 1; j < image.length; j++) {
                        if (image[i] == image[j]) {
                            iterate = true;
                        }
                    }
                }
                if (iterate == false) {
                    injective = true;
                }
            }

            if (exhaustive && injective) {
                return BIJECTIVE;
            } else {
                if (inyectiva) {
                    return INJECTIVE;
                }
                if (exhaustiva) {
                    return SURJECTIVE;
                }
            }
            return NOTHING_SPECIAL;
        }

        /*
         * Here are some examples and tests related to these exercises (see `main`)
         */
        static void tests() {
            // Exercise 1
            // `p` is a partition of `a`?

            assertThat(
                    exercise1(
                            new int[]{1, 2, 3, 4, 5},
                            new int[][]{{1, 2}, {3, 5}, {4}}
                    )
            );

            assertThat(
                    !exercise1(
                            new int[]{1, 2, 3, 4, 5},
                            new int[][]{{1, 2}, {5}, {1, 4}}
                    )
            );

            // Exercise 2
            // is `rel` defined over `a` of partial order and `x` its minimum?
//            ArrayList<int[]> divisibility = new ArrayList<int[]>();
//
//            for (int i = 1; i < 8; i++) {
//                for (int j = 1; j <= i; j++) {
//                    if (i % j == 0) {
//                        // i is a multiple of, that is, j|i
//                        divisibility.add(new int[]{j, i});
//                    }
//                }
//            }
//
//            assertThat(
//                    exercise2(
//                            new int[]{1, 2, 3, 4, 5, 6, 7},
//                            divisibility.toArray(new int[][]{}),
//                            1
//                    )
//            );
//
//            assertThat(
//                    !exercise2(
//                            new int[]{1, 2, 3},
//                            new int[][]{{1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3}},
//                            1
//                    )
//            );
//
//            assertThat(
//                    !exercise2(
//                            new int[]{1, 2, 3, 4, 5, 6, 7},
//                            divisibility.toArray(new int[][]{}),
//                            2
//                    )
//            );
            // Exercise 3
            // calculate the antiimage of `y`
            assertThat(
                    Arrays.equals(
                            new int[]{0, 2},
                            exercise3(
                                    new int[]{0, 1, 2, 3},
                                    new int[]{0, 1},
                                    x -> x % 2, // remainder to divide between 2
                                    0
                            )
                    )
            );

            assertThat(
                    Arrays.equals(
                            new int[]{},
                            exercise3(
                                    new int[]{0, 1, 2, 3},
                                    new int[]{0, 1, 2, 3, 4},
                                    x -> x + 1,
                                    0
                            )
                    )
            );

            // Exercise 4
            // classify the function into res/injective/exhaustive/bijective
            assertThat(
                    exercise4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3},
                            x -> (x + 1) % 4
                    )
                    == BIJECTIVE
            );

            assertThat(
                    exercise4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3, 4},
                            x -> x + 1
                    )
                    == INJECTIVE
            );

            assertThat(
                    exercise4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1},
                            x -> x / 2
                    )
                    == SURJECTIVE
            );

            assertThat(
                    exercise4(
                            new int[]{0, 1, 2, 3},
                            new int[]{0, 1, 2, 3},
                            x -> x <= 1 ? x + 1 : x - 1
                    )
                    == NOTHING_SPECIAL
            );
        }
    }

    /*
     * Here are the exercises for Topic 3 (Arithmetic).
     *
     */
    static class Topic3 {

        /*
         * Given `a`, `b` return the greatest common divisor between `a` and `b`.
         *
         * You can assume that `a` and `b` are positive.
         */
        static int exercise1(int a, int b) {
            return calculateMcdAlgorithmEuclidean(a, b);
        }

        /*
         * Is it true that `a``x` + `b``y` = `c` has a solution?.
         *
         * You can assume that `a`, `b` and `c` are positive.
         */
        static boolean exercise2(int a, int b, int c) {
            return c % calculateMcdAlgorithmEuclidean(a, b) == 0;
        }

        /*
         * Quin es l'invers de `a` module `n`?
         *
         * Return the inverse always between 1 and `n-1`, in case it does not exist return -1
         */
        static int exercise3(int a, int n) {
            for (int i = 1; i < n; i++) {
                if (i * a % n == 1) {
                    return i;
                }
            }
            return -1;
        }

        //ADDITIONAL METHOD (Used for exercise 1 and exercise 2)
        /**
         * Functional method to calculate the greatest common divisor by means of the
         * Euclidean algorithm
         *
         * @param firstNumber
         * @param secondNumber
         * @return
         */
        public static int calculateMcdAlgorithmEuclidean(int firstNumber,
                int secondNumber) {
            if (secondNumber == 0) {
                return firstNumber;
            }
            return calculateMcdAlgorithmEuclidean(secondNumber, firstNumber
                    % secondNumber);
        }

        /*
         * Here are some examples and tests related to these exercises (see `main`)
         */
        static void tests() {
            // Exercise 1
            // `mcd(a,b)`

            assertThat(
                    exercise1(2, 4) == 2
            );

            assertThat(
                    exercise1(1236, 984) == 12
            );

            // Exercise 2
            // `a``x` + `b``y` = `c` has a solution?
            assertThat(
                    exercise2(4, 2, 2)
            );
            assertThat(
                    !exercise2(6, 2, 1)
            );
            // Exercise 3
            // inverse of `a` module `n`
            assertThat(exercise3(2, 5) == 3);
            assertThat(exercise3(2, 6) == -1);
        }
    }

    static class Topic4 {

        /*
         * Given an adjacency matrix `A` of an undirected graph, return the order and size of the graph..
         */
        static int[] exercise1(int[][] A) {
            return new int[]{A.length, calculateEdgesNumber(A)};

        }

        /*
         * Given an adjacency matrix `A` of an undirected graph, say whether the graph is Eulerian.
         */
        static boolean exercise2(int[][] A) {
            int degreeCounter;
            degreeCounter = 0;
            boolean eulerianGraph;
            eulerianGraph = true;

            for (int[] A1 : A) {
                for (int j = 0; j < A1.length; j++) {
                    if (A1[j] == 1) {
                        degreeCounter++;
                    }
                }
                if (degreeCounter % 2 != 0) {
                    eulerianGraph = false;
                }
                degreeCounter = 0;
            }
            return eulerianGraph;
        }

        /*
         * Given `n` the number of leaves of a rooted tree and `d` the number of children of the internal nodes and the root,
         * return the total number of vertices in the tree
         *
         */
        static int exercise3(int n, int d) {
            return (n * d - 1) / (d - 1);
        }

        /*
         * Given an adjacency matrix `A` of a connected undirected graph, say whether the graph contains any cycle.
         */
        static boolean exercise4(int[][] A) {
            return calculateEdgesNumber(A) != A.length - 1;
        }

        //ADDITIONAL METHOD (Used for exercise 1 and exercise 4) 
        /**
         * Functional method to calculate the number of edges of a matrix of
         * Adjacency
         *
         * @param adjacencyMatrix
         * @return edgesNumber / 2
         */
        public static int calculateEdgesNumber(int[][] adjacencyMatrix) {
            int V;
            V = adjacencyMatrix.length;
            int edgesNumber;
            edgesNumber = 0;

            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (adjacencyMatrix[i][j] != 1) {
                    } else {
                        edgesNumber++;
                    }
                }
            }

            return edgesNumber / 2;
        }

        /*
         * Here are some examples and tests related to these exercises (see `main`)
         */
        static void tests() {
            // Exercise 1
            // `order and size`

            assertThat(
                    Arrays.equals(exercise1(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}}), new int[]{3, 2})
            );

            assertThat(
                    Arrays.equals(exercise1(new int[][]{{0, 1, 0, 1}, {1, 0, 1, 1}, {0, 1, 0, 1}, {1, 1, 1, 0}}), new int[]{4, 5})
            );

            // Exercise 2
            // `Is it Eulerian?`
            assertThat(
                    exercise2(new int[][]{{0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
            );
            assertThat(
                    !exercise2(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
            );
            // Exercise 3
            // `How many nodes does the tree have?`
            assertThat(exercise3(5, 2) == 9);
            assertThat(exercise3(7, 3) == 10);

            // Exercise 4
            // `Does it contain any cycle?`
            assertThat(
                    exercise4(new int[][]{{0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
            );
            assertThat(
                    !exercise4(new int[][]{{0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
            );

        }
    }

    /*
     * This `main` method contains some examples of parameters and the results they should give
     * the exercises You can use them as a guide and you can also add others (we won't have them in
     * caution, but it is highly recommended).
     *
     * You can take advantage of the `assertThat` method to easily check that a value is `true`.
     */
    public static void main(String[] args) {
        Topic1.tests();
        Topic2.tests();
        Topic3.tests();
        Topic4.tests();
    }

    static void assertThat(boolean b) {
        if (!b) {
            throw new AssertionError();
        }
    }
}
