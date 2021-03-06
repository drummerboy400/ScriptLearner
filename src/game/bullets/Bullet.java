package game.bullets;

import game.ScriptLearnerGame;
import game.engine.ScriptLearnerShooterMatchInstance;
import game.players.Player;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.GraphicsRelativeUtil.x;
import static utils.GraphicsRelativeUtil.y;

/**
 * 2/28/2015
 */
public class Bullet {
    private static final double velocity = .01;
    private static final double bulletLength = .05;
    private static final double bulletWidth = .008;
    private static final int maxLife = 100;

    private Player owner;
    private Color color;
    private double locX;
    private double locY;
    private double angle;
    private Polygon bullet;
    private int life;

    public Bullet(double locX, double locY, double angle, Color color, Player owner) {
        this.locX = locX;
        this.locY = locY;
        this.locX += 2 * velocity * Math.cos(angle);
        this.locY += 2 * velocity * Math.sin(angle);
        this.angle = angle;
        this.color = color;
        this.life = maxLife;
        this.owner = owner;
    }

    public double getAngle() {
        return angle;
    }

    public double getRelativeAngle(double x, double y) {
        return Math.atan2(y-locY,x-locX);
    }

    public double getX() {
        return locX;
    }
    public double getY() {
        return locY;
    }

    public Player getOwner() {
        return owner;
    }
    public boolean isContained(Polygon view) {
        return view.contains(x(locX),y(locY));
    }

    public boolean collides(double locx, double locy, double radius) {
        double r = radius * radius;
        r/=4;
        double pointx, pointy;
        pointx = locX + bulletLength / 2 * Math.cos(angle);
        pointy = locY + bulletLength / 2 * Math.sin(angle);
        if (r > (locx - pointx) * (locx - pointx) + (locy - pointy) * (locy - pointy)) {
            ScriptLearnerShooterMatchInstance.bullets.remove(this);
            owner.addKill();
            return true;
        }
        pointx = locX + bulletWidth / 2 * Math.sin(-angle);
        pointy = locY + bulletWidth / 2 * Math.cos(-angle);
        if (r > (locx - pointx) * (locx - pointx) + (locy - pointy) * (locy - pointy)) {
            ScriptLearnerShooterMatchInstance.bullets.remove(this);
            owner.addKill();
            return true;
        }
        pointx = locX - bulletWidth / 2 * Math.sin(-angle);
        pointy = locY - bulletWidth / 2 * Math.cos(-angle);
        if (r > (locx - pointx) * (locx - pointx) + (locy - pointy) * (locy - pointy)) {
            ScriptLearnerShooterMatchInstance.bullets.remove(this);
            owner.addKill();
            return true;
        }
        return false;
    }

    public double distance(double x, double y) {
        return Math.sqrt((x - locX)*(x-locX)+(y-locY)*(y-locY));
    }

    public boolean run() {
        locX += velocity * Math.cos(angle);
        locY += velocity * Math.sin(angle);
        life--;

        if (life == 0 || locX < 0 || locX > 1 || locY < 0 || locY > 1) {
            ScriptLearnerShooterMatchInstance.bullets.remove(this);
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        bullet = new Polygon();
        bullet.addPoint(x(locX + bulletLength / 2 * Math.cos(angle)), y(locY + bulletLength / 2 * Math.sin(angle)));
        bullet.addPoint(x(locX + bulletWidth / 2 * Math.sin(-angle)), y(locY + bulletWidth / 2 * Math.cos(-angle)));
        bullet.addPoint(x(locX - bulletWidth / 2 * Math.sin(-angle)), y(locY - bulletWidth / 2 * Math.cos(-angle)));
        g.fillPolygon(bullet);
    }
}
