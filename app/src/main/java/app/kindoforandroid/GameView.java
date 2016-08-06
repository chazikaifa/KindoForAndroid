package app.kindoforandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * Created by lenovo on 2015/10/30 0030.
 */
public class GameView extends GridLayout {

    private int cardWidth;
    private Card[][] cardMap = new Card[5][5];
    private int[] BounsStep=new int[2];
    private boolean isFirstStart = true;
    private int[][][] RecentAction = new int[2][4][2];
    private int RecentActStep[] = {0,0};
    private int ActivePlayer = 0;// '0' stands for player 1,'1' stands for player 2
    private int stepLeft = 2;// Each player have 2 step every turn basically
    private static GameView gameView = null;

    private int[] Step_Active = {R.drawable.active_step_blue,R.drawable.active_step_red};
    private int[] playerActive = {R.drawable.icon_player1,R.drawable.icon_player2};
    private int[] playerInactive = {R.drawable.icon_player1_inactive,R.drawable.icon_player2_inactive};

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        GameInit();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GameInit();
    }
    public GameView(Context context) {
        super(context);
        GameInit();
    }

    private void GameInit()
    {
        gameView = this;
        for(int i=0;i<4;i++) {
            RecentAction[1][i][0] = 5;
            RecentAction[1][i][1] = 5;
        }
        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        int position[] = new int[2];
                        position = judgePosition(startX, startY);
//					    InsertSL(position[0],position[1]);
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -cardWidth / 2) {
                                left(position[0], position[1]);
                            } else if (offsetX > cardWidth / 2) {
                                right(position[0], position[1]);
                            } else {
                                click(position[0], position[1]);
                            }
                        } else {
                            if (offsetY < -cardWidth / 2) {
                                up(position[0], position[1]);
                            } else if (offsetY > cardWidth / 2) {
                                down(position[0], position[1]);
                            } else {
                                click(position[0], position[1]);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        cardWidth = (Math.min(w,h))/5;
        if(isFirstStart)
            addCard(cardWidth);
    }

    private void addCard(int cardWidth)
    {
        Card c;
        for(int y=0;y<5;y++)
        {
            for(int x=0;x<5;x++)
            {
                c = new Card(getContext());
                addView(c,cardWidth,cardWidth);

                cardMap[x][y] = c;
            }
        }
        cardMap[0][4].setMain(0);
        cardMap[4][0].setMain(1);
        cardMap[1][1].setWall(-1);
        cardMap[2][2].setWall(-1);
        cardMap[3][3].setWall(-1);
        isFirstStart = false;
    }

    //set up walls in the follow
    private void left(int startX,int startY)
    {
        if(startX>4||startY>4||cardMap[startX][startY].getMaster()!=ActivePlayer)
        {
//			SL.pop();
            return;
        }
        if(!cardMap[startX][startY].setWall(1))
        {
//			SL.pop();
            return;
        }
        else if(wallWin())
            gameOver(ActivePlayer);
        else
        {
            moveFinished();
        }
    }
    private void right(int startX,int startY)
    {
        if(startX>4||startY>4||cardMap[startX][startY].getMaster()!=ActivePlayer)
        {
//			SL.pop();
            return;
        }
        if(!cardMap[startX][startY].setWall(2))
        {
//			SL.pop();
            return;
        }
        else if(wallWin())
            gameOver(ActivePlayer);
        else
        {
            moveFinished();
        }
    }
    private void up(int startX,int startY)
    {
        if(startX>4||startY>4||cardMap[startX][startY].getMaster()!=ActivePlayer)
        {
//			SL.pop();
            return;
        }
        if(!cardMap[startX][startY].setWall(3))
        {
//			SL.pop();
            return;
        }
        else if(wallWin())
            gameOver(ActivePlayer);
        else
        {
            moveFinished();
        }
    }
    private void down(int startX,int startY)
    {
        if(startX>4||startY>4||cardMap[startX][startY].getMaster()!=ActivePlayer)
        {
//			SL.pop();
            return;
        }
        if(!cardMap[startX][startY].setWall(4))
        {
//			SL.pop();
            return;
        }
        else if(wallWin())
            gameOver(ActivePlayer);
        else
        {
            moveFinished();
        }
    }

    //when click,occupy the place if it's connect with other place
    private void click(int startX,int startY)
    {
        if(startX>4||startY>4||cardMap[startX][startY].getMaster()==ActivePlayer)
        {
//			SL.pop();
            return;
        }
        if(!connected(startX,startY))
        {
//			SL.pop();
            return;
        }
        cardMap[startX][startY].setMaster(ActivePlayer);
        if(startX==0&&startY==4)
            gameOver(1);
        else if(startX==4&&startY==0)
            gameOver(0);
        else
        {
            RecordAction(startX,startY);
            if(isRepeatRecentAct(startX,startY)&&BounsStep[1-ActivePlayer]<2){
                BounsStep[1-ActivePlayer]++;
                ImageView[][] Step = GameActivity.getGameActivity().getStep();
                Step[1-ActivePlayer][1+BounsStep[1-ActivePlayer]].setImageResource(R.drawable.inactive_step);
            }
            moveFinished();
        }
    }

    //record the recent action
    private void RecordAction(int x,int y){
        RecentAction[ActivePlayer][RecentActStep[ActivePlayer]][0] = x;
        RecentAction[ActivePlayer][RecentActStep[ActivePlayer]][1] = y;
        RecentActStep[ActivePlayer]++;
    }

    //After an action,Judge if it's cut off the emery and who's next
    private void moveFinished()
    {
        ImageView[][] Step = GameActivity.getGameActivity().getStep();
        ImageView[] player = GameActivity.getGameActivity().getPlayer();
        if(cutOff())
        {
            if(BounsStep[ActivePlayer]<2) {
                BounsStep[ActivePlayer]++;
            }
        }
        if(stepLeft == 1)
        {
            int i;
            //turn the current player into inactive
            for(i=0;i<2+BounsStep[ActivePlayer];i++)
                Step[ActivePlayer][i].setImageResource(R.drawable.inactive_step);
            for(;i<4;i++)
                Step[ActivePlayer][i].setImageResource(R.drawable.unusable_step);
            setRecentView();

            //change active player
            player[ActivePlayer].setImageResource(playerInactive[ActivePlayer]);
            ActivePlayer=1-ActivePlayer;
            player[ActivePlayer].setImageResource(playerActive[ActivePlayer]);
            for(i=0;i<4;i++){
                //set the recent action bigger than 4 so that it won't be judged as a valid action
                RecentAction[ActivePlayer][i][0]=5;
                RecentAction[ActivePlayer][i][1]=5;
            }
            RecentActStep[ActivePlayer] = 0;

            //turn the other player into active
            stepLeft = 2 + BounsStep[ActivePlayer];
            for(i=0;i<stepLeft;i++)
                Step[ActivePlayer][i].setImageResource(Step_Active[ActivePlayer]);
            for(;i<4;i++)
                Step[ActivePlayer][i].setImageResource(R.drawable.unusable_step);
            BounsStep[ActivePlayer] = 0;
        }
        else {
            stepLeft--;
            Step[ActivePlayer][stepLeft].setImageResource(R.drawable.unusable_step);
        }
    }

    //Judge the action area
    private int[] judgePosition(float startX,float startY)
    {
        int[] position=new int[2];
        position[0]=(int) (startX/cardWidth);
        position[1]=(int) (startY/cardWidth);
        return position;
    }

    //Judge if the action cut off the emery's area
    private boolean cutOff()
    {
        int[][] tempMap = new int[5][5];
        for(int j=0;j<5;j++)
            for(int i=0;i<5;i++)
                if(cardMap[i][j].getMaster()==(1-ActivePlayer))
                    tempMap[i][j]=1;
                else
                    tempMap[i][j]=0;
        TempMap tp = new TempMap(tempMap);
        if(ActivePlayer==0)
            tp.deleteConnected(4,0);
        else
            tp.deleteConnected(0,4);
        return !tp.isEmpty(cardMap);
    }

    private boolean isRepeatRecentAct(int x,int y)
    {
        for(int i=0;i<RecentActStep[1-ActivePlayer];i++){
            if(x==RecentAction[1-ActivePlayer][i][0]&&y==RecentAction[1-ActivePlayer][i][1])
                return true;
        }
        return false;
    }

    private void setRecentView(){
        cancelRecentView();
        for(int i=0;i<RecentActStep[ActivePlayer];i++){
            int x = RecentAction[ActivePlayer][i][0];
            int y = RecentAction[ActivePlayer][i][1];
            cardMap[x][y].setRecent();
        }
    }

    private void cancelRecentView(){
       for(int i = 0;i<RecentActStep[1-ActivePlayer];i++){
            int x = RecentAction[1-ActivePlayer][i][0];
            int y = RecentAction[1-ActivePlayer][i][1];
            cardMap[x][y].cancelRecent();
        }
    }

    //Judge if the action area is connect with his other area
    private boolean connected(int startX,int startY)
    {
        if(startX>0)
        {
            if(cardMap[startX-1][startY].getMaster()==ActivePlayer&&cardMap[startX][startY].getWall()!=1)
                return true;
        }
        if(startX<4)
        {
            if(cardMap[startX+1][startY].getMaster()==ActivePlayer&&cardMap[startX][startY].getWall()!=2)
                return true;
        }
        if(startY>0)
        {
            if(cardMap[startX][startY-1].getMaster()==ActivePlayer&&cardMap[startX][startY].getWall()!=3)
                return true;
        }
        if(startY<4)
        {
            if(cardMap[startX][startY+1].getMaster()==ActivePlayer&&cardMap[startX][startY].getWall()!=4)
                return true;
        }
        return false;
    }

    //Alert that someone has won and ask whether start a new game
    private void gameOver(int winner)
    {
        new AlertDialog.Builder(getContext()).setTitle("GameOver").setMessage("player"+(winner+1)+"胜利！").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                onceAgain();
            }
        }).show();
    }

    //Judge if the player win by setting a wall
    private boolean wallWin()
    {
        boolean flag=true;
        if(ActivePlayer==0)
        {
            for(int i=0;i<4&&flag;i++)
                if(cardMap[0][i].getMaster()!=ActivePlayer||cardMap[0][i].getWall()!=2)
                    flag=false;
            for(int i=1;i<5&&flag;i++)
                if(cardMap[i][4].getMaster()!=ActivePlayer||cardMap[i][4].getWall()!=3)
                    flag=false;
        }
        else if(ActivePlayer==1)
        {
            for(int i=0;i<4&&flag;i++)
                if(cardMap[i][0].getMaster()!=ActivePlayer||cardMap[i][0].getWall()!=4)
                    flag=false;
            for(int i=1;i<5&&flag;i++)
                if(cardMap[4][i].getMaster()!=ActivePlayer||cardMap[4][i].getWall()!=1)
                    flag=false;
        }
        return flag;
    }

    //Restart the Game
    public void onceAgain()
    {
        for(int j=0;j<5;j++)
            for(int i=0;i<5;i++)
                cardMap[i][j].refresh();
        cardMap[0][4].setMain(0);
        cardMap[4][0].setMain(1);
        cardMap[1][1].setWall(-1);
        cardMap[2][2].setWall(-1);
        cardMap[3][3].setWall(-1);
        BounsStep = new int[2];
        ActivePlayer = 0;
        stepLeft = 2;
        for(int i=0;i<4;i++) {
            RecentAction[0][i][0] = 5;
            RecentAction[0][i][1] = 5;
            RecentAction[1][i][0] = 5;
            RecentAction[1][i][1] = 5;
        }
        RecentActStep = new int[]{0, 0};
        ImageView[][] Step = GameActivity.getGameActivity().getStep();
        int i;
        for(i=0;i<2;i++) {
            Step[0][i].setImageResource(Step_Active[0]);
            Step[1][i].setImageResource(R.drawable.inactive_step);
        }
        for(;i<4;i++) {
            Step[0][i].setImageResource(R.drawable.unusable_step);
            Step[1][i].setImageResource(R.drawable.unusable_step);
        }
    }

//    public static GameView getGameView()
//    {
//        return gameView;
//    }
}
