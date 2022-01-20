package com.tuana9a.OLD;

import com.tuana9a.OLD.nature.Animal;
import com.tuana9a.OLD.weapon.Weapon;
import com.tuana9a.OLD.weapon.WeaponOut;
import com.tuana9a.ani.TwoDirectAnimation;
import com.tuana9a.app.App;
import com.tuana9a.app.KeyboardManager;
import com.tuana9a.app.ResourceManager;
import com.tuana9a.entity.abilities.CanCollide;
import com.tuana9a.game.Camera;
import com.tuana9a.game.EntityManager;
import com.tuana9a.utils.Algebra;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Entity implements CanCollide {

    public static final int NORMAL = 0,//EXPLAIN: state init by default
            RIGHT_INDEX = 0, LEFT_INDEX = 1, X_INDEX = 0, Y_INDEX = 1;

    protected final EntityManager entityManager;

    protected int id;

    public int state = NORMAL; //-1 la nullPointerException
    public int direct = RIGHT_INDEX;
    public App.Timer abstractTimer = new App.Timer();
    public List<Entity> collidedEntities = new LinkedList<>();

    public double x, y;
    public Pos pos;//TODO: khi nào rảnh thì refactor lại x,y ở trên

    public double width, height;
    public Size size;//TODO: khi nào rảnh thì refactor lại size và w, h ở trên

    public Pos pos_CAM;

    public Block bounds;
    public Block bounds_ORIGIN;

    public double[][] rotateRels;//storage giá trị cố định, dùng update bên dưới

    public double xRotateCam, yRotateCam;//gia tri tuyệt đối so với màn hình
    public Pos posRotate_CAM;//TODO: refactor x, y RotateCam ở trên

    public double rotate_R_MAIN;//EXPLAIN! tính độ lệch góc, hướng di chuyển, CORE
    public double rotate_L;//EXPLAIN! ONLY dùng để đúng về mặt hình ảnh, xoay vũ khí, vẽ góc quay, SUPPORT

    public TwoDirectAnimation moveAnimation;
    public StateAnimation[] allStateAnimations;

    protected abstract void init(int id);

    protected abstract void initAbstract(int id);

    protected Entity(EntityManager entityManager, int id, double x, double y) {
        this.entityManager = entityManager;
        this.id = id;
        updatePosition(x, y);
        init(id);       //EXPLAIN: khởi tạo các thứ cơ bản
        rotateRels = new double[][]{{width / 2, height / 2}, {width / 2, height / 2}};
        initAbstract(id);  //EXPLAIN: khởi tạo các thứ khác;
    }

    public abstract void update();

    public void render(Graphics g) {
        updateAllCamProperties();

        moveAnimation.render(g);

        if (allStateAnimations != null && allStateAnimations[state] != null) {
            StateAnimation animation = allStateAnimations[state];
            if (animation != null) {
                animation.render(g);
                animation.update();
            }
        }
        KeyboardManager keyboardManager = KeyboardManager.getInstance();
        if (keyboardManager.getToggle(KeyboardManager.OUTER_BOUND_MODE_KEY)) {
            renderOuterBound(g);
        }
        if (keyboardManager.getToggle(KeyboardManager.INNER_BOUND_MODE_KEY)) {
            renderInnerBound(g);
        }
        if (keyboardManager.getToggle(KeyboardManager.ROTATION_MODE_KEY)) {
            renderRotation(g);
        }
    }

    //SECTION: interaction handler
    public void clearCollided() {
        collidedEntities.clear();
    }


    //SECTION: all rotate
    public void updateRotate(double radian) {
        //CAUTION không được thay đổi
        rotate_R_MAIN = radian;
        correctRotateMain();
        rotate_L = rotate_R_MAIN + (rotate_R_MAIN >= 0 ? -1 : 1) * Math.PI;
        //        updateBoundRotate();
    }

    private void correctRotateMain() {
        if (rotate_R_MAIN > Math.PI) {
            while (rotate_R_MAIN > Math.PI) {
                rotate_R_MAIN -= 2 * Math.PI;
            }
        } else if (rotate_R_MAIN < -Math.PI) {
            while (rotate_R_MAIN < -Math.PI) {
                rotate_R_MAIN += 2 * Math.PI;
            }
        }
    }

    public void updateBoundRotate() {
        double xRelTemp = bounds_ORIGIN.x() + bounds_ORIGIN.w() / 2 - rotateRels[direct][X_INDEX];
        double yRelTemp = bounds_ORIGIN.y() + bounds_ORIGIN.h() / 2 - rotateRels[direct][Y_INDEX];

        boolean heightSmaller = bounds_ORIGIN.h() < bounds_ORIGIN.w();

        double tempRadian = 0;
        if (direct == RIGHT_INDEX) {
            tempRadian = rotate_R_MAIN;
        } else if (direct == LEFT_INDEX) {
            tempRadian = rotate_L;
        }

        if (heightSmaller) {
            bounds.w((int) Math.max(bounds_ORIGIN.w() * Math.abs(Math.cos(tempRadian)), bounds_ORIGIN.h()));
            bounds.h((int) Math.max(bounds_ORIGIN.w() * Math.abs(Math.sin(tempRadian)), bounds_ORIGIN.h()));
        } else {//width smaller
            bounds.w((int) Math.max(bounds_ORIGIN.h() * Math.abs(Math.sin(tempRadian)), bounds_ORIGIN.w()));
            bounds.h((int) Math.max(bounds_ORIGIN.h() * Math.abs(Math.cos(tempRadian)), bounds_ORIGIN.w()));
        }
        bounds.x((int) (rotateRels[direct][X_INDEX] + Algebra.rotateX(xRelTemp, yRelTemp, tempRadian) -
                (double) bounds.w() / 2));
        bounds.y((int) (rotateRels[direct][Y_INDEX] + Algebra.rotateY(xRelTemp, yRelTemp, tempRadian) -
                (double) bounds.h() / 2));
    }

    public boolean isRotated() {
        return Double.compare(rotate_R_MAIN, 0) != 0 || Double.compare(rotate_L, 0) != 0;
    }


    //SECTION: all update all position properties
    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private void updateAllCamProperties() {
        Camera camera = Camera.getInstance();
        pos_CAM.x(camera.getCamRenderX(x));
        pos_CAM.y(camera.getCamRenderY(y));
        xRotateCam = camera.getCamRenderX(x + rotateRels[direct][X_INDEX]);
        yRotateCam = camera.getCamRenderY(y + rotateRels[direct][Y_INDEX]);
    }


    //SECTION: state
    public void updateState(int state) {
        this.state = state;
    }


    //SECTION: collision detection
    public Rectangle bounds(double xOffset, double yOffset) {
        return new Rectangle((int) (x + bounds.x() + xOffset),
                (int) (y + bounds.y() + yOffset),
                bounds.w(),
                bounds.h());
    }

    public Rectangle bounds() {
        return bounds(0, 0);
    }

    public boolean isCollide(Entity e) {
        //        return isCollide(0, 0, e);
        if (e instanceof MoveEntity) {
            MoveEntity me = (MoveEntity) e;
            return isCollide(me.xMove, me.yMove, e);
        }//TEST new collide
        return isCollide(0, 0, e);
    }

    public boolean isCollide(double xOffset, double yOffset, Entity e) {
        return bounds(xOffset, yOffset).intersects(e.bounds());
    }


    //SECTION: render extension, support render
    private void renderInnerBound(Graphics g) {
        //in bound
        if (this instanceof WeaponOut) {
            g.setColor(Color.GREEN);
        } else if (this instanceof Animal) {
            g.setColor(Color.BLUE);
        } else if (this instanceof Weapon) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }

        g.fillRect((int) pos_CAM.x() + bounds.x(), (int) pos_CAM.y() + bounds.y(), bounds.w(), bounds.h());
        //        g.setColor(Color.CYAN);
        //        g.drawRect((int) xCam + bounds.x, (int) yCam + bounds.y, bounds.width, bounds.height);
    } // F1

    private void renderOuterBound(Graphics g) {
        g.setColor(Color.CYAN);
        //        g.setColor(Color.BLACK);
        g.drawRect((int) pos_CAM.x(), (int) pos_CAM.y(), (int) width, (int) height);


        //        g.setColor(Color.RED);
        //        g.drawLine((int) xCam, (int) yCam + actualSize.y, (int) xCam + actualSize.x, (int) yCam + actualSize.y);
        //        g.drawLine((int) xCam, (int) yCam, (int) xCam + actualSize.x, (int) yCam);
        //
        //        g.drawLine((int) xCam + actualSize.x, (int) yCam, (int) xCam + actualSize.x, (int) yCam + actualSize.y);
        //        g.drawLine((int) xCam, (int) yCam, (int) xCam, (int) yCam + actualSize.y);

    } // F2

    private void renderRotation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        double tempRadian = 0;
        if (direct == RIGHT_INDEX) {
            tempRadian = rotate_R_MAIN;
        } else if (direct == LEFT_INDEX) {
            tempRadian = rotate_L;
        }

        g2d.rotate(tempRadian, xRotateCam, yRotateCam);

        //ve goc xoay
        g.setColor(Color.YELLOW);

        //        int r2 = (int) width / 2;
        int r2 = (int) width / 4;
        g.fillArc((int) (xRotateCam - r2 / 2),
                (int) (yRotateCam - r2 / 2),
                r2,
                r2,
                0,
                (int) Math.toDegrees(-tempRadian));

        //ve 2 canh
        g.setColor(Color.CYAN);
        g2d.setColor(Color.CYAN);

        //        int r1 = bounds.width;
        int r1 = (int) width / 2;
        g.drawLine((int) xRotateCam, (int) yRotateCam, (int) xRotateCam + r1, (int) yRotateCam);
        g2d.drawLine((int) xRotateCam, (int) yRotateCam, (int) xRotateCam + r1, (int) yRotateCam);
        g.drawArc((int) (xRotateCam - r1 / 2),
                (int) (yRotateCam - r1 / 2),
                r1,
                r1,
                0,
                (int) Math.toDegrees(-tempRadian));

        //ve tam quay
        g.setColor(Color.RED);
        int r0 = 3;
        g.fillOval((int) xRotateCam - r0, (int) yRotateCam - r0, r0 * 2, r0 * 2);
    } // F3


    //SECTION: getter setter
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setBounds(int x, int y, int w, int h) {
        bounds = new Block(x, y, w, h);
    }

    public void initBoundsOrigin() {
        bounds_ORIGIN.x(bounds.x());
        bounds_ORIGIN.y(bounds.y());
        bounds_ORIGIN.w(bounds.w());
        bounds_ORIGIN.h(bounds.h());
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }


    //SECTION: inner class
    public static class MoveAnimation extends TwoDirectAnimation {
        private final Entity entity;

        public MoveAnimation(Entity entity, ResourceManager resourceManager, String[] rightNames) {
            super(rightNames);
            this.entity = entity;
        }

        @Override
        public void update() {
            if (updateTimer.timeup()) {
                leftAnimation.next();
                rightAnimation.next();
                updateTimer.reset();
            }
        }

        @Override
        public void render(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            int x = (int) entity.pos_CAM.x();
            int y = (int) entity.pos_CAM.y();
            int w = (int) entity.width;
            int h = (int) entity.height;

            switch (entity.direct) {
                case Entity.RIGHT_INDEX:
                    g2d.rotate(entity.rotate_R_MAIN, entity.xRotateCam, entity.yRotateCam);
                    g2d.drawImage(rightAnimation.currentImage(), x, y, w, h, null);
                    break;
                case Entity.LEFT_INDEX:
                    g2d.rotate(entity.rotate_L, entity.xRotateCam, entity.yRotateCam);
                    g2d.drawImage(leftAnimation.currentImage(), x, y, w, h, null);
                    break;
            }
        }

    }

    //EXPLAIN: ý tưởng mỗi state animation sẽ độc lập chứ k check status trong class này
    public static class StateAnimation extends TwoDirectAnimation {
        private final Entity entity;

        public double[][] positionRel;  //EXPLAIN [ [relX trái, relY trái],[relX phải, relY phải] ]
        public double[] rotateRel;      //EXPLAIN  [ rotate rel trái,rotate rel phải ]

        public double width;
        public double height;

        public StateAnimation(Entity entity, ResourceManager resourceManager, String[] rightNames) {
            super(rightNames);
            this.entity = entity;
            this.positionRel = new double[][]{{0, 0}, {0, 0}};
            this.rotateRel = new double[]{0, 0};
            this.width = 0;
            this.height = 0;
        }

        @Override
        public void update() {
            if (updateTimer.timeup()) {
                leftAnimation.next();
                rightAnimation.next();
                updateTimer.reset();
            }
        }

        @Override
        public void render(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            int x = (int) entity.pos_CAM.x();
            int y = (int) entity.pos_CAM.y();
            int w = (int) width;
            int h = (int) height;

            double rotateRel_ = rotateRel[entity.direct];
            double positionRelX = positionRel[entity.direct][X_INDEX];
            double positionRelY = positionRel[entity.direct][Y_INDEX];

            switch (entity.direct) {
                case Entity.RIGHT_INDEX:
                    //EXPLAIN: vị trị được tính bằng xCam + positionRel chính là vị trí xoay cam
                    g2d.rotate(rotateRel_,
                            entity.xRotateCam + positionRelX,
                            entity.yRotateCam + positionRelY);
                    g2d.drawImage(rightAnimation.currentImage(), x, y, w, h, null);
                    break;
                case Entity.LEFT_INDEX:
                    g2d.rotate(rotateRel_,
                            entity.xRotateCam + positionRelX,
                            entity.yRotateCam + positionRelY);
                    g2d.drawImage(leftAnimation.currentImage(), x, y, w, h, null);
                    break;
            }
        }

    }

    //EXPLAIN: position
    public static class Pos {
        private double x;
        private double y;

        public Pos() {
            this.x = 0;
            this.y = 0;
        }

        public Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double x() {
            return x;
        }

        public double y() {
            return y;
        }

        public void x(double x) {
            this.x = x;
        }

        public void y(double y) {
            this.y = y;
        }
    }

    public static class Size {
        private int w;
        private int h;

        public Size() {
            this.w = 0;
            this.h = 0;
        }

        public Size(int w, int h) {
            this.w = w;
            this.h = h;
        }

        public void w(int v) {
            this.w = v;
        }

        public void h(int v) {
            this.h = v;
        }

        public int w() {
            return this.w;
        }

        public int h() {
            return this.h;
        }

    }

    public static class Block {
        private final Rectangle rectangle;

        public Block() {
            this.rectangle = new Rectangle(0, 0, 0, 0);
        }

        public Block(int x, int y, int w, int h) {
            rectangle = new Rectangle(x, y, w, h);
        }

        public void x(int v) {
            this.rectangle.x = v;
        }

        public void y(int v) {
            this.rectangle.y = v;
        }

        public void w(int v) {
            this.rectangle.width = v;
        }

        public void h(int v) {
            this.rectangle.height = v;
        }

        public int x() {
            return this.rectangle.x;
        }

        public int y() {
            return this.rectangle.y;
        }

        public int w() {
            return this.rectangle.width;
        }

        public int h() {
            return this.rectangle.height;
        }

    }

}
