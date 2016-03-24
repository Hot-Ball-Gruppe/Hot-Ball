/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Position.DoublePosition;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.Controller;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.controller.ai.AI_Difficulty;
import com.hot.ball.hotball.controller.ai.analysis.VoronoiArea;
import com.hot.ball.hotball.controller.ai.roles.Role;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.BallState;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.ball.InAir;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;

/**
 *
 * @author Dromlius
 */
public final class Player extends GameObject {
//---------DEFAULT PLAYERS----------------------

    public static Player Felix = new Player("Felix", new Stats(1, 1, 1), Role.Balanced);
    public static Player Adrian = new Player("Adrian", new Stats(1, 1, 1), Role.Defensive);

    public static Player Leo = new Player("Leo", new Stats(1, 1, 1), Role.Balanced);
    public static Player Patryk = new Player("Patryk", new Stats(1, 1, 1), Role.Balanced);
    public static Player Friedrich = new Player("Friedrich", new Stats(1, 1, 1), Role.Aggressive);
    public static Player Thomas = new Player("Thomas", new Stats(1, 1, 1), Role.Defensive);

    /*
    public static Player Dummy1 = new Player("Dummy1", new Stats(1, 1, 1), null);
    public static Player Dummy2 = new Player("Dummy2", new Stats(1, 1, 1), null);
    public static Player Dummy3 = new Player("Dummy3", new Stats(1, 1, 1), null);
    public static Player Dummy4 = new Player("Dummy4", new Stats(1, 1, 1), null);
    public static Player Dummy5 = new Player("Dummy5", new Stats(1, 1, 1), null);
    public static Player Dummy6 = new Player("Dummy6", new Stats(1, 1, 1), null);*/
    @Override
    protected double getDECAY_FACTOR() {
        return 4;
    }

    private static Player humanPlayer;

    public static Player getHumanPlayer() {
        return humanPlayer;
    }

    public static void setHumanPlayer(Player human) {
        if (!human.equals(humanPlayer)) {
      //      human.setController(HumanController.get());
            if (humanPlayer != null) {
                humanPlayer.setController(new AIController());
            }
            humanPlayer = human;
        }
    }

    private final String name;
    private Team team;

    private Controller controller;

    private double facing;

    private final TackleZone tackleZone;
    private final Role role;

    private final double totalMaxSpeed;
    private final int throwPower;

    private final double maxThrowDist;

    private double currentMaxSpeed;

    private double minChanceToHit;

    private VoronoiArea voronoiArea;

    private Player(String name, Stats stats, Role role) {
        super(new DoublePosition(0, 0), 24);
        this.name = name;

        totalMaxSpeed = 200 * stats.getSpeed();
        throwPower = (int) (1000 * stats.getPower());

        minChanceToHit = stats.getChance() / 100;

        final double zähler = Math.pow(Court.get().getDecayBase(), -Ball.get().getDECAY_FACTOR() * 1.52) - 1;
        final double nenner = Ball.get().getDECAY_FACTOR() * Math.log(Court.get().getDecayBase());
        maxThrowDist = (-zähler / nenner) * throwPower;

        tackleZone = new TackleZone(this, (int) (85 * stats.getTackle()));

        currentMaxSpeed = totalMaxSpeed;

        this.role = role;
        controller = new AIController();

        this.facing = Math.random() * 2 * Math.PI;
    }

    public String getName() {
        return name;
    }

    public int getThrowPower() {
        return throwPower;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public double getCurrentMaxSpeed() {
        return currentMaxSpeed * (Ball.get().isControlledBy(this) ? 0.8 : 1) * (Team.RED.isMember(this) ? AI_Difficulty.getDifficulty().getSpeedFactor() : 1);
    }

    public double getTackleZoneSize() {
        return tackleZone.getCurrentFactor() * tackleZone.maxSize;
    }

    private void setController(Controller controller) {
        this.controller = controller;
    }

    public VoronoiArea getVoronoiArea() {
        return voronoiArea;
    }

    public void setVoronoiArea(VoronoiArea voronoiArea) {
        this.voronoiArea = voronoiArea;
    }

    public boolean isHuman() {
        return controller instanceof HumanController;
    }

    private static final float ANIMATION_TIMER = 1f / 14;
    private static final Animation[] WALKING_ANIMATION;
    private static final Animation[] DRIBBLING_ANIMATION;
    private static final TextureRegion[] STANDING_TEXTURE;
    private static final TextureRegion[] HOLDING_TEXTURE;
    private static final TextureRegion[] THROWING_TEXTURE;

    private static final Texture DANGER_FRAME;
    private static final Texture DANGER_BAR;
    private static final Texture DANGER_SIGN;

    private static final Texture WAYPOINT;

    private float totalAnimationTime = (float) (Math.random() * 10);

    private static final double ROTATION_PER_SECOND = Math.PI * 2;

    static {
        TextureRegion[][][] split = new TextureRegion[][][]{TextureRegion.split(new Texture(Gdx.files.internal("res/player0.png")), 64, 64), TextureRegion.split(new Texture(Gdx.files.internal("res/player1.png")), 64, 64), TextureRegion.split(new Texture(Gdx.files.internal("res/player2.png")), 64, 64), TextureRegion.split(new Texture(Gdx.files.internal("res/player3.png")), 64, 64)};

        WALKING_ANIMATION = new Animation[4];
        DRIBBLING_ANIMATION = new Animation[4];
        STANDING_TEXTURE = new TextureRegion[4];
        HOLDING_TEXTURE = new TextureRegion[4];
        THROWING_TEXTURE = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            WALKING_ANIMATION[i] = new Animation(ANIMATION_TIMER, split[i][1]);
            DRIBBLING_ANIMATION[i] = new Animation(ANIMATION_TIMER, split[i][0]);
            STANDING_TEXTURE[i] = split[i][1][0];
            HOLDING_TEXTURE[i] = split[i][2][0];
            THROWING_TEXTURE[i] = split[i][2][1];
        }

        DANGER_FRAME = new Texture(Gdx.files.internal("res/Rahmen.png"));
        DANGER_BAR = new Texture(Gdx.files.internal("res/Farbleiste.png"));
        DANGER_SIGN = new Texture(Gdx.files.internal("res/DANGER.png"));

        WAYPOINT = new Texture(Gdx.files.internal("res/waypoint.png"));
    }

    public double getFacing() {
        return facing;
    }

    @Override
    public void draw(Graphics g) {
        int spriteColor = (team.getColor() == TeamColor.Blue) ? 0 : 2;
        spriteColor += (Ball.get().isControlledBy(this) || isHuman()) ? 1 : 0;
        tackleZone.draw(g,spriteColor);
        TextureRegion keyFrame;
        BallState ballState = Ball.get().getState();
        if (ballState instanceof InAir && this.equals(((InAir) ballState).getThrower()) && ((InAir) ballState).getAirTime() < 0.2) {
            keyFrame = THROWING_TEXTURE[spriteColor];
        } else if (getCurrentVelocity().getLength() > getCurrentMaxSpeed() / 3) {
            if (Ball.get().isControlledBy(this)) {
                keyFrame = DRIBBLING_ANIMATION[spriteColor].getKeyFrame(totalAnimationTime, true);
            } else {
                keyFrame = WALKING_ANIMATION[spriteColor].getKeyFrame(totalAnimationTime, true);
            }
        } else if (Ball.get().isControlledBy(this)) {
            keyFrame = HOLDING_TEXTURE[spriteColor];
        } else {
            keyFrame = STANDING_TEXTURE[spriteColor];
        }
        if (controller instanceof AIController && ((AIController) controller).getOrder() != null) {
            Position order = ((AIController) controller).getOrder();
            g.drawLineRel(getPosition(), order);
            g.drawImageRel(WAYPOINT, order.getRoundX(), order.getRoundY() + WAYPOINT.getHeight() / 2);
        }
        g.drawImageRel(keyFrame, getPosition().getRoundX(), getPosition().getRoundY(), facing);
        //  g.drawString((int)getVoronoiArea().getCenter().getX()+" "+(int)getVoronoiArea().getCenter().getY(),  getPosition().getRoundX()+100, getPosition().getRoundY()+100);
        // g.drawImage(keyFrame, voronoiArea.getCenter().getRoundX(), voronoiArea.getCenter().getRoundY(), 32, 32, facing);
        if (isHuman() && inDanger) {
            g.drawImageScreenCenter(DANGER_BAR, currentTakeDownTime / TAKEDOWNTIME);
            g.drawImageScreenCenter(DANGER_FRAME);
            g.drawImageScreenCenterShake(DANGER_SIGN, currentTakeDownTime / TAKEDOWNTIME);
        }

        if (isHuman() && Ball.get().isControlledBy(this) && getChanceToHit() > minChanceToHit) {
            g.drawStringRel("" + (int) (100 * getChanceToHit()), getTeam().getAttacking().getPosition().getRoundX() - 25, getTeam().getAttacking().getPosition().getRoundY() + 65);
        }
    }

    public final static double MAX_THROW_DIST = 300;
    private double chanceToHit;
    private final static double TAKEDOWNTIME = 1.0;
    private double currentTakeDownTime = 0;

    public void calcChanceToHit(int enemyTZ) {
        double adj_max_throw_dist = MAX_THROW_DIST + minChanceToHit * 800;
        double dist = getPosition().getDistance(getTeam().getAttacking().getPosition());
        chanceToHit = Math.max(minChanceToHit, Math.min(0.99, (-dist / adj_max_throw_dist + (float) Court.BASKET_DIST_FROM_OUTLINE / adj_max_throw_dist + 0.95 + minChanceToHit)) / Math.pow(2, enemyTZ));
    }

    public double getChanceToHit() {
        return chanceToHit;
    }

    private boolean inDanger;

    @Override
    public void action(double timeDiff) {
        totalAnimationTime += timeDiff;

        int enemyTZ = 0;
        Player closestEnemy = null;
        double closestEnemyDistance = Double.POSITIVE_INFINITY;

        int friendlyTZ = 0;
        for (Zone z : getInterferingZones()) {
            if ((z instanceof TackleZone)) {
                Player tackler = ((TackleZone) z).getPlayer();
                if (!this.equals(tackler)) {
                    if (tackler.getTeam() == getTeam()) {
                        friendlyTZ++;
                    } else {
                        enemyTZ++;
                        double enemyDistance = tackler.getPosition().getDistance(getPosition());
                        if (enemyDistance < closestEnemyDistance) {
                            closestEnemy = tackler;
                            closestEnemyDistance = enemyDistance;
                        }
                    }
                }
            }
        }
        calcChanceToHit(enemyTZ);
        currentMaxSpeed = totalMaxSpeed / (1 + Math.max(0, enemyTZ - friendlyTZ));
        if (Ball.get().isControlledBy(this) && enemyTZ > 0) {
            currentTakeDownTime = Math.min(TAKEDOWNTIME, currentTakeDownTime + timeDiff);
            if (currentTakeDownTime >= TAKEDOWNTIME) {
                Ball.get().setState(new Controlled(closestEnemy));
                closestEnemy.thrown = true;
                closestEnemy.throwTimeOut = MAXTHROWTIMEOUT / 2;
                tackleZone.reset();
                currentTakeDownTime = 0;
            }
            inDanger = true;
        } else {
            currentTakeDownTime = Math.max(0, currentTakeDownTime - timeDiff);
            inDanger = false;
        }

        Vector accDir = controller.getMoveVector(this);

        clearInterfeeringZones();

        if (accDir.getLength()
                > 1) {
            accDir.setLength(1);
        }

        accelerate(timeDiff, accDir);

        if (isHuman()) {
            facing = controller.getFacing(this);
        } else {
            facing = allignFacing(facing, controller.getFacing(this), timeDiff);
        }

        tackleZone.action(timeDiff);

        if (thrown) {
            throwTimeOut += timeDiff;
            if (throwTimeOut > MAXTHROWTIMEOUT) {
                throwTimeOut = 0;
                thrown = false;
            }
        }
    }

    public double getMaxThrowDist() {
        return maxThrowDist;
    }

    private double allignFacing(double currentFacing, double towards, double timeDiff) {

        double angle = towards - currentFacing;
        angle += (angle > Math.PI) ? -2 * Math.PI : (angle < -Math.PI) ? 2 * Math.PI : 0;
        double maxAngle = ROTATION_PER_SECOND * timeDiff;
        if (maxAngle > Math.abs(angle)) {
            return towards;
        } else {
            return currentFacing + Math.signum(angle) * maxAngle;
        }
    }
    private final static double MAXTHROWTIMEOUT = TAKEDOWNTIME + 0.2;
    private double throwTimeOut = 0;
    boolean thrown;

    public void throwBall(Position target) {
        if (!thrown) {
            if (Ball.get().isControlledBy(this)) {
                Ball.get().throwBall(target, getThrowPower());
                facing = getPosition().angleBetween(target);
                thrown = true;
            }
        }
    }

    public void order(Position p) {
        if (!isHuman()) {
            ((AIController) controller).setOrder(p);
        }
    }

}
