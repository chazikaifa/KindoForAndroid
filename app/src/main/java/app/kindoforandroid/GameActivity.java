package app.kindoforandroid;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by lenovo on 2015/10/31 0031.
 */
public class GameActivity extends Activity {
    //storge the id of step cubic
    private int[] p1_step = {R.id.p1_1,R.id.p1_2,R.id.p1_3,R.id.p1_4};
    private int[] p2_step = {R.id.p2_1,R.id.p2_2,R.id.p2_3,R.id.p2_4};
    private ImageView[][] Step = null;
    private ImageView[] player = null;
    private static GameActivity gameActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("游戏开始");
        init();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
    }


    //get the player's step cubic
    private void init(){
        gameActivity = this;
        ImageView[] p1Step = new ImageView[4];
        ImageView[] p2Step = new ImageView[4];
        ImageView step;
        for(int i=0;i<4;i++) {
            step = (ImageView) findViewById(p1_step[i]);
            p1Step[i] = step;
            step = (ImageView) findViewById(p2_step[i]);
            p2Step[i] = step;
        }
        Step = new ImageView[][]{p1Step, p2Step};
        ImageView player1,player2;
        player1 = (ImageView) findViewById(R.id.player1);
        player2 = (ImageView) findViewById(R.id.player2);
        player = new ImageView[]{player1, player2};
    }

    public ImageView[][] getStep() {
        return Step;
    }

    public static GameActivity getGameActivity() {
        return gameActivity;
    }

    public ImageView[] getPlayer() {
        return player;
    }
}
