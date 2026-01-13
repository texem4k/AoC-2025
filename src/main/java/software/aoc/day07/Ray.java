package software.aoc.day07;

public class Ray {
    public int x;
    public int y;

    public Ray(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void goDeeper(){
        this.y++;
    }

    public Ray splitLeft() {
        return new Ray(this.x - 1, this.y);
    }

    public Ray splitRight() {
        return new Ray(this.x + 1, this.y);
    }

}
