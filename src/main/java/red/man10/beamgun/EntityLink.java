package red.man10.beamgun;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by takatronix on 2017/03/21.
 */
public class EntityLink {
    Entity   source;
    Entity   dest;

    public boolean isAlive(){
        if(source == null){
            return false;
        }
        if(source.isDead()){
            return  false;
        }

        if(dest == null){
            return false;
        }

        if(dest.isDead()){
            return  false;
        }
        return  true;
    }

    public  void drawLine(){
       // Bukkit.getServer().broadcastMessage("draw");

        if(isAlive() == false){
            return;
        }

     //  Bukkit.getServer().broadcastMessage(source.getName() + " " + dest.getName());

        Location ls = source.getLocation();
        Location ld = dest.getLocation();

        Vector v = new Vector(ls.getX() - ld.getX(),ls.getY() - ld.getY(),ls.getZ() - ld.getZ());
        double distance = ls.distance(ld);
      //  Bukkit.getServer().broadcastMessage("distance:"+distance);

        double step = 1;
        Location pos = ls;
        double maxDistance = 40;
        int count = 0;
        for(;;) {
            double x = v.getX() * step;
            double y = v.getY() * step;
            double z = v.getZ() * step;
            pos.add(x, y, z);
            source.getWorld().playEffect(pos, Effect.WITCH_MAGIC, 0);
            if(count >= 40){
                return;
            }

            count ++;
             distance = ls.distance(ld);
            //Bukkit.getServer().broadcastMessage("distance:"+distance + " count:"+count);

            if (distance >= maxDistance) {
                break;
            }
        }

    }

}
