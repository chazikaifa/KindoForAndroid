package app.kindoforandroid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 2015/10/30 0030.
 */
public class Card extends ImageView {

    private int Wall=0;//墙的方向，-1为不能架墙，0为没有，1为左，2为右，3为上，4为下
    private int Master=-1;//方格的归属，-1为未占领，0为玩家1,1为玩家2
    private TextView label;//卡片内显示的内容
    private ImageView image = this;
    //save the id of every drawable
    private int[] King = {R.drawable.king_blue,R.drawable.king_red};
    private int[] Wall_Left = {R.drawable.wall_blue_left,R.drawable.wall_red_left};
    private int[] Wall_Right = {R.drawable.wall_blue_right,R.drawable.wall_red_right};
    private int[] Wall_Up = {R.drawable.wall_blue_up,R.drawable.wall_red_up};
    private int[] Wall_Down = {R.drawable.wall_blue_down,R.drawable.wall_red_down};
    private int[] WallForbidden = {R.drawable.wallforbidden_blue,R.drawable.wallforbidden_red};
    private int[] Normal = {R.drawable.normal_blue,R.drawable.normal_red};
    private int[] Recent_Normal = {R.drawable.recent_normal_blue,R.drawable.recent_normal_red};
    private int[] Recent_Wall_Left = {R.drawable.recent_wall_blue_left,R.drawable.recent_wall_red_left};
    private int[] Recent_Wall_Right = {R.drawable.recent_wall_blue_right,R.drawable.recent_wall_red_right};
    private int[] Recent_Wall_Up = {R.drawable.recent_wall_blue_up,R.drawable.recent_wall_red_up};
    private int[] Recent_Wall_Down = {R.drawable.recent_wall_blue_down,R.drawable.recent_wall_red_down};
    private int[] Recent_WallForbidden = {R.drawable.recent_wallforbidden_blue,R.drawable.recent_wallforbidden_red};

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        image.setImageResource(R.drawable.normal_none);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        image.setImageResource(R.drawable.normal_none);
    }

    public Card(Context context) {
        super(context);
        image.setImageResource(R.drawable.normal_none);
    }


    public void refresh()
    {
        Wall = 0;
        setMaster(-1);
    }


    public int getWall()
    {
        return Wall;
    }


    public boolean setWall(int wall)
    {
        if (wall == -1)
        {
            Wall = wall;
            image.setImageResource(R.drawable.wallforbidden_none);
            return false;
        }
        else if(Wall == wall||Wall == -1)
            return false;
        else
        {
            switch (wall)
            {
                case 1:
                    image.setImageResource(Wall_Left[Master]);
                    break;
                case 2:
                    image.setImageResource(Wall_Right[Master]);
                    break;
                case 3:
                    image.setImageResource(Wall_Up[Master]);
                    break;
                case 4:
                    image.setImageResource(Wall_Down[Master]);
                    break;
            }
            Wall = wall;
            return true;
        }
    }


    public int getMaster()
    {
        return Master;
    }

    //change the Card's master
    public void setMaster(int master)
    {
        Master=master;
        if(Wall!=-1)
        {
            Wall = 0;
            if(Master == -1)
            {
                image.setImageResource(R.drawable.normal_none);
            }
            else
            {
                image.setImageResource(Normal[Master]);
            }
        }
        else
        {
            image.setImageResource(WallForbidden[Master]);
        }

    }

    //set King
    public void setMain(int master)
    {
        Master=master;
        Wall=-1;
        image.setImageResource(King[Master]);
    }


    //set the Card is acted Recently
    public  void setRecent()
    {
        if(Wall == -1)
        {
            image.setImageResource(Recent_WallForbidden[Master]);
        }
        else
        {
            switch(Wall)
            {
                case 0:
                    image.setImageResource(Recent_Normal[Master]);
                    break;
                case 1:
                    image.setImageResource(Recent_Wall_Left[Master]);
                    break;
                case 2:
                    image.setImageResource(Recent_Wall_Right[Master]);
                    break;
                case 3:
                    image.setImageResource(Recent_Wall_Up[Master]);
                    break;
                case 4:
                    image.setImageResource(Recent_Wall_Down[Master]);
                    break;
            }
        }
    }

    //cancel the Card is acted Recently
    public void cancelRecent()
    {
        if(Wall == -1)
        {
            image.setImageResource(WallForbidden[Master]);
        }
        else
        {
            switch(Wall)
            {
                case 0:
                    image.setImageResource(Normal[Master]);
                    break;
                case 1:
                    image.setImageResource(Wall_Left[Master]);
                    break;
                case 2:
                    image.setImageResource(Wall_Right[Master]);
                    break;
                case 3:
                    image.setImageResource(Wall_Up[Master]);
                    break;
                case 4:
                    image.setImageResource(Wall_Down[Master]);
                    break;
            }
        }
    }
}
