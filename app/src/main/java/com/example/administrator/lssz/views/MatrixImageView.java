package com.example.administrator.lssz.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author lc. 2018-03-30 09:56
 * @since 1.0.0
 */

public class MatrixImageView extends AppCompatImageView {

    private ImageClickListener imageClickListener;

    /** 无状态 */
    private final int STATE_NONE = 0;
    /** 正在拖拽 */
    private final int STATE_DRAGING = 1;
    /** 正在缩放 */
    private final int STATE_ZOOMING = 2;

    private final static float CLICK_RANGE = 10F;

    private int mStartPointerId = 0;

    private int mState = STATE_NONE;

    /**
     * 两个手指之间的距离
     */
    private float mFingerDistance = 0f;

    /**
     * 手指实时的位置
     */
    private PointF mTempPoint = new PointF();
    /**
     * 矩阵值
     */
    private float[] mMatrixValue = new float[9];

    /**
     * 手指开始落下的位置
     */
    private PointF mStartPoint = new PointF();

    /**
     * 手指抬起的位置
     */
    private PointF mEndPoint = new PointF();

    /**
     * 矩阵
     */
    private Matrix mMatrix = new Matrix();

    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.BLACK);
        setScaleType(ScaleType.FIT_CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();
        boolean isActionConsume = true;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mState = STATE_NONE;
                mStartPointerId = event.getPointerId(actionIndex);
                mStartPoint.set(event.getX(), event.getY());
                mTempPoint.set(mStartPoint);
                if (getScaleType() != ScaleType.MATRIX) {
                    setScaleType(ScaleType.MATRIX);
                    mMatrix.set(getImageMatrix());
                    mMatrix.getValues(mMatrixValue);
                    setImageMatrix(mMatrix);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mFingerDistance = calculateFingerDistance(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 保证多个手指抬起后，sTartPoint  mTempPoint 位置为留着屏幕上那个手指的位置
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if (i != actionIndex) {
                        mStartPoint.set(event.getX(i), event.getY(i));
                        mTempPoint.set(mStartPoint);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                if (pointerCount == 1) {
                    drag(event);
                }
                if (pointerCount == 2) {
                    zoom(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                mEndPoint.set(event.getX(), event.getY());
                int pointerId = event.getPointerId(actionIndex);
                // 落下和抬起都是同一个手指 && 没有执行过拖拽和缩放 && 手指移动距离够小 == 用户点击
                if (pointerId == mStartPointerId
                        && mState == STATE_NONE
                        && calculateMoveDistance(mStartPoint, mEndPoint) <= CLICK_RANGE) {
                    performClick();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mEndPoint.set(event.getX(), event.getY());
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 计算两个手指之间的距离
     */
    private float calculateFingerDistance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算手指移动的距离
     */
    private float calculateMoveDistance(PointF start, PointF end) {
        float dx = end.x - start.x;
        float dy = end.y - start.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 处理缩放
     */
    private void zoom(MotionEvent event) {
        mState = STATE_ZOOMING;

        float endDistance = calculateFingerDistance(event);
        float scale = endDistance / mFingerDistance;
        mFingerDistance = endDistance;

        mMatrix.set(getImageMatrix());
        mMatrix.getValues(mMatrixValue);
        mMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mMatrix);
    }

    /**
     * 处理拖拽
     */
    private void drag(MotionEvent event) {
        // 滑动距离太小，不执行拖拽
        if (Math.abs(event.getY() - mStartPoint.y) < CLICK_RANGE) {
            return;
        }
        mState = STATE_DRAGING;

        float dragX = event.getX() - mTempPoint.x;
        float dragY = event.getY() - mTempPoint.y;
        mTempPoint.set(event.getX(), event.getY());

        mMatrix.set(getImageMatrix());
        mMatrix.getValues(mMatrixValue);
        mMatrix.postTranslate(0, dragY);
        setImageMatrix(mMatrix);
    }

    public interface ImageClickListener {
        void imageListener(boolean isClick);
    }
}
