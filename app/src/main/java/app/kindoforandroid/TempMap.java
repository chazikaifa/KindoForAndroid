package app.kindoforandroid;

/**
 * Created by lenovo on 2015/10/30 0030.
 */
public class TempMap {
    public int[][] Map = null;
    public TempMap(int[][] map)
    {
        Map=map;
    }
    //去除所有与主子连通的棋子，其余的是不连通的
    public void deleteConnected(int x,int y)
    {
        Map[x][y]=0;
        if(x>0)
            if(Map[x-1][y]!=0)
                deleteConnected(x-1,y);
        if(x<4)
            if(Map[x+1][y]!=0)
                deleteConnected(x+1,y);
        if(y>0)
            if(Map[x][y-1]!=0)
                deleteConnected(x,y-1);
        if(y<4)
            if(Map[x][y+1]!=0)
                deleteConnected(x,y+1);
    }
    //判断数组是否为空，如果是，则没有被切断，如果否，则把被切断的归为对方
    public boolean isEmpty(Card[][] cardMap)
    {
        boolean flag=true;
        for(int j=0;j<5;j++)
            for(int i=0;i<5;i++)
                if(Map[i][j]!=0)
                {
                    flag=false;
                    cardMap[i][j].setMaster(1-cardMap[i][j].getMaster());//使不连通的棋子master换为另一个玩家
                }
        return flag;
    }
}
