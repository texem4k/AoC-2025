package software.aoc.day08;

public record PairJunctionBox(JunctionBox A, JunctionBox B) {

    public JunctionBox A() {
        return A;
    }
    public JunctionBox B() {
        return B;
    }

    public long euclideanDistance(){
        return (long) Math.sqrt(pqDistance(A.x(), B.x()) + pqDistance(A.y(), B.y()) + pqDistance(A.z(), B.z()));
    }

    public long pqDistance(long p, long q){
        return (long) Math.pow((p-q), 2);
    }
}
