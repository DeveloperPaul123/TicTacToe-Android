package com.developerpaul123.tictactoe.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import com.developerpaul123.tictactoe.R;
import com.developerpaul123.tictactoe.gameobjects.Board;
import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;
import com.developerpaul123.tictactoe.gameobjects.Row;

import java.util.List;

/**
 * Created by Paul on 11/23/2015.
 * UI widget for tic tac toe. Binds to a board and draws the current board.
 */
public class TicTacToeView extends View {

    /**
     * Listener for this view. Provides a callback for where the user has clicked.
     */
    public interface TicTacToeListener {
        public void onSquareClicked(int row, int col);
        public void onMoveAdded(Board board);
    }

    /**
     * Array of rectangles that enclose the squares of the board.
     */
    private RectF rects[][];

    /**
     * Current board.
     */
    private Board board;

    /**
     * Current listener.
     */
    private TicTacToeListener listener;

    /**
     * Main paint for the board.
     */
    private Paint mainPaint;

    /**
     * Paint for x moves.
     */
    private Paint xPaint;

    /**
     * xoPaint for o player moves.
     */
    private Paint oPaint;

    /**
     * Seperate paint for the animation.
     */
    private Paint animationPaint;

    /**
     * Float values for spacing.
     */
    private float squarePadding, mainPaintStrokeSize, xoPaintStrokeSize;

    /**
     * The current path to animate
     */
    private AnimatedPath currentPath;

    public TicTacToeView(Context context) {
        super(context);
        init();
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initialize various values and other things.
     */
    public void init() {
        mainPaintStrokeSize = 32.0f;
        xoPaintStrokeSize = 48.0f;

        squarePadding = (xoPaintStrokeSize/2) + (mainPaintStrokeSize/2) + 16.0f;

        mainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mainPaint.setStrokeWidth(mainPaintStrokeSize);
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeCap(Paint.Cap.ROUND);

        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(getResources().getColor(R.color.secondPlayerColor));
        oPaint.setStrokeWidth(xoPaintStrokeSize);
        oPaint.setAntiAlias(true);
        oPaint.setStrokeCap(Paint.Cap.ROUND);
        oPaint.setStyle(Paint.Style.STROKE);

        xPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xPaint.setColor(getResources().getColor(R.color.colorAccent));
        xPaint.setStrokeCap(Paint.Cap.ROUND);
        xPaint.setStrokeWidth(xoPaintStrokeSize);
        xPaint.setAntiAlias(true);
        xPaint.setStyle(Paint.Style.STROKE);

        animationPaint = new Paint(xPaint);

        board = new Board(3, 3);
        rects = new RectF[3][3];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int rows = board.getRows();
        int cols = board.getColumns();
        int height = getHeight();
        int width = getWidth();
        float splitHeight = height/rows;
        float splitWidth = width/cols;

        //draw horizontal lines.
        for(int i = 0; i < rows; i++) {
            canvas.drawLine(8.0f, ((i/(rows-1)) * splitHeight) + splitHeight,
                    width - 8.0f, ((i/(rows-1)) * splitHeight) + splitHeight, mainPaint);
            for(int u = 0; u < cols; u++) {
                RectF rect = new RectF();
                float left = u * splitWidth;
                float right = left + splitWidth;
                float top = i * splitHeight;
                float bottom = top + splitHeight;
                rect.set(left, top, right, bottom);
                rects[i][u] = rect;
            }
        }

        //draw vertical lines.
        for(int j = 0; j < cols; j++) {
            canvas.drawLine(((j/(cols-1))*splitWidth) + splitWidth, 8.0f,
                    ((j/(cols-1))*splitWidth) + splitWidth, height - 8.0f, mainPaint);
        }

        //draw current plays.
        List<Row> boardState = board.getBoardList();
        for(int z = 0; z < boardState.size(); z++) {
            Row r = boardState.get(z);
            for(int q = 0; q < r.getSize(); q++) {
                if(r.getValue(q) == PlayerType.USER.getValue()) {
                    //x plays.
                    Path p = getXPath(rects[z][q]);
                    canvas.drawPath(p, xPaint);
                }
                else if(r.getValue(q) == PlayerType.COMPUTER_MCTS.getValue() ||
                        r.getValue(q) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                    //o plays.
                    Path p = getOPath(rects[z][q]);
                    canvas.drawPath(p, oPaint);
                }
            }
        }

        if(currentPath != null) {
            currentPath.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for(int i = 0; i < board.getRows(); i++) {
                    for(int j = 0; j < board.getColumns(); j++) {
                        if(rects[i][j].contains(x, y)) {
                            if(listener != null) {
                                listener.onSquareClicked(i, j);
                            }
                            break;
                        }
                    }
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentwidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(parentHeight, parentwidth);
        this.setMeasuredDimension(size, size);
    }

    /**
     * Set a listener for when the user hits a square.
     * @param listener
     */
    public void setTicTacToeListener(TicTacToeListener listener) {
        this.listener = listener;
    }

    /**
     * Set the current board.
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;

        this.currentPath = null;
        invalidate();
    }

    /**
     * Add a move to the current board.
     * @param p the point of play.
     * @param playerType the player that made the move.
     */
    public void addAMove(final Point p, final PlayerType playerType) {
        if(playerType.getValue() == PlayerType.USER.getValue()) {
            animationPaint.setColor(getResources().getColor(R.color.colorAccent));
            currentPath = new AnimatedPath(this, getXPath(rects[p.getRow()][p.getColumn()]), animationPaint);
            currentPath.animate(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    board.addAMove(new Point(p.getRow(), p.getColumn()), PlayerType.USER);
                    currentPath = null;
                    if(listener != null) {
                        listener.onMoveAdded(board);
                    }
                    invalidate();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else if(playerType.getValue() == PlayerType.COMPUTER_MINIMAX.getValue() ||
                playerType.getValue() == PlayerType.COMPUTER_MCTS.getValue()) {
            animationPaint.setColor(getResources().getColor(R.color.secondPlayerColor));
            currentPath = new AnimatedPath(this, getXPath(rects[p.getRow()][p.getColumn()]), animationPaint);
            currentPath.animate(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    board.addAMove(new Point(p.getRow(), p.getColumn()),
                            playerType.getValue() == PlayerType.COMPUTER_MINIMAX.getValue() ?
                                    PlayerType.COMPUTER_MINIMAX: PlayerType.COMPUTER_MCTS);
                    currentPath = null;
                    invalidate();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    /**
     * Helper method to get an X path to draw.
     * @param rect the containing rectangle.
     * @return a path that draws an X.
     */
    private Path getXPath(RectF rect) {
        Path p = new Path();
        p.moveTo(rect.left + squarePadding, rect.top + squarePadding);
        p.lineTo(rect.right - squarePadding, rect.bottom - squarePadding);
        p.moveTo(rect.right - squarePadding, rect.top + squarePadding);
        p.lineTo(rect.left + squarePadding, rect.bottom - squarePadding);
        return p;
    }

    /**
     * Helper method to get an O path to draw O.
     * @param rect the containing rectangle.
     * @return a path that draws an O.
     */
    private Path getOPath(RectF rect) {
        Path p = new Path();
        p.moveTo(rect.centerX(), rect.centerY());
        p.addCircle(rect.centerX(), rect.centerY(), (rect.width()/2) - squarePadding, Path.Direction.CW);
        return p;
    }

}
