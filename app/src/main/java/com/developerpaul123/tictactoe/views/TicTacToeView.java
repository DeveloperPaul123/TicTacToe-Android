package com.developerpaul123.tictactoe.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    public interface TicTacToeListener {
        public void onSquareClicked(int row, int col);
    }

    private RectF rects[][];
    private Board board;
    private TicTacToeListener listener;
    private Paint mainPaint;
    private Paint xoPaint;
    private float squarePadding, mainPaintStrokeSize, xoPaintStrokeSize;

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

        xoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xoPaint.setColor(getResources().getColor(R.color.colorAccent));
        xoPaint.setStrokeWidth(xoPaintStrokeSize);
        xoPaint.setAntiAlias(true);
        xoPaint.setStrokeCap(Paint.Cap.ROUND);
        xoPaint.setStyle(Paint.Style.STROKE);

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
                    Path p = getXPath(rects[z][q]);
                    canvas.drawPath(p, xoPaint);
                }
                else if(r.getValue(q) == PlayerType.COMPUTER_MCTS.getValue() ||
                        r.getValue(q) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                    Path p = getOPath(rects[z][q]);
                    canvas.drawPath(p, xoPaint);
                }
            }
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
                            board.addAMove(new Point(i, j), PlayerType.COMPUTER_MCTS);
                            break;
                        }
                    }
                }
                invalidate();
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
        this.rects = new RectF[board.getRows()][board.getColumns()];
        invalidate();
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
