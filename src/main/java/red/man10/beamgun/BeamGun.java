package red.man10.beamgun;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takatronix on 2017/03/21.
 */
public class BeamGun {
    private final BeamGunPlugin plugin;
    public BeamGun(BeamGunPlugin plugin) {
        this.plugin = plugin;
    }


    String   controllerName = "§e§lMan10 BeamGun";
    Material controllerMaterial = Material.REDSTONE_TORCH_ON;
    BeamGun(BeamGunPlugin plugin,String name){
        this.plugin = plugin;
    }
    public  void giveController(Player p, String type){
        ItemStack item = new ItemStack(controllerMaterial,1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(controllerName);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§b§lMan10テック社の最新兵器 Man10BeamGun");
        lore.add("§b§l最新技術でつくられており、もはや魔法と区別がつかない");
        im.setLore(lore);
        item.setItemMeta(im);
        p.getInventory().addItem(item);
    }

    int    target = 0;
    EntityLink link = new EntityLink();
    public void onPlayerInteract(PlayerInteractEvent e){

        Player p = e.getPlayer();


       // p.sendMessage(e.toString());


        //      スレッドで１ティックごとスキャン
        Location pos = p.getLocation();
        pos.add(0,1,0);

        Vector v = p.getLocation().getDirection().normalize();
        double maxDistance = 60;
        double hitDistnce = 1.2;
        double step = 1;
        List<Entity> entities = p.getNearbyEntities(maxDistance,maxDistance,maxDistance);
        //p.sendMessage("entieies:"+entities.size());


        int count = 0;


        for(;;){
            double x = v.getX() * step;
            double y = v.getY() * step;
            double z = v.getZ() * step;
            pos.add(x,y,z);
            p.getWorld().playEffect(pos, Effect.WITCH_MAGIC,0);
             //p.sendMessage("cont:"+count);
             //count ++;

            double distance =  pos.distance(p.getLocation());
            if(distance > maxDistance){
               // p.sendMessage("終了しました");
                break;
            }
            if(pos.getBlock().getType() != Material.AIR){
                //pos.getBlock().setType(Material.MAGMA);
                //return;
                //  this.cancel();
                //return;
            }


            for(Entity ent:entities) {
                double d = pos.distance(ent.getLocation());
                // p.sendMessage(ent.getName()+ ":distance"+d);
                if(d <= hitDistnce) {
                   // p.sendMessage("Hit!:"+ent.getName());
                    ent.setGlowing(true);

                    if(target == 0){
                        p.sendMessage("source is "+ent.getName());
                        link.source = ent;

                        target = 1;
                        return;
                    }else if (target == 1){

                        p.sendMessage("dest is "+ent.getName());
                        link.dest = ent;
                        if(link.dest == link.source){
                            p.sendMessage("same target");
                            target = 0;
                            return;
                        }


                        plugin.links.add(link);
                        p.sendMessage("links:"+ plugin.links.size());
                        target = 0;
                        return;
                    }
                    //Vector v2 = v;
                    //v2.multiply(-1);
                    //ent.setVelocity(v2);
                }else{
                    //ent.setGlowing(false);
                    //break;
                }
            }
        }

/*
        new BukkitRunnable(){

            int     count = 0;
            @Override
            public void run() {

                double step = 1;
                double x = v.getX() * step;
                double y = v.getY() * step;
                double z = v.getZ() * step;
                pos.add(x,y,z);
                p.getWorld().playEffect(pos, Effect.SMOKE,0);
               // p.sendMessage("cont:"+count);
                count ++;

                double distance =  pos.distance(p.getLocation());
                if(distance > maxDistance){
                    p.sendMessage("終了しました");
                    this.cancel();
                    return;
                }
                if(pos.getBlock().getType() != Material.AIR){
                  //  this.cancel();
                    //return;
                }


                for(Entity ent:entities) {
                   double d = pos.distance(ent.getLocation());
                   // p.sendMessage(ent.getName()+ ":distance"+d);
                    if(d <= hitDistnce) {
                        p.sendMessage("Hit!:"+ent.getName());
                        this.cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
        */
    }


    public  void findTarget(Player p){

            p.sendMessage("drawing to target");
            Location pos = p.getLocation();
            Vector v = p.getLocation().getDirection().normalize();
            new BukkitRunnable(){
                double count = 0;
                @Override
                public void run() {
                    //Location l = p.getLocation();
                    //org.bukkit.util.Vector v = p.getLocation().getDirection().normalize();
                    double sp = 1;
                    double x = v.getX() * sp;
                    double y = v.getY() * sp;
                    double z = v.getZ() * sp;
                    pos.add(x,y,z);


                    double distance =  pos.distance(p.getLocation());
                    if(distance > 10){
                        p.sendMessage("終了しました");
                        this.cancel();
                    }
                    //p.sendMessage("distance = " + distance);
                    if(pos.getBlock().getType() != Material.AIR){
                        this.cancel();
                    }
                    pos.getBlock().setType(Material.BEACON);
                    //p.sendMessage(pos.toString());

                }
            }.runTaskTimer(plugin, 0, 1);
    }
    public void onClickLeft(PlayerInteractEvent e){

    }
    public void onClickRight(PlayerInteractEvent e){

    }
}
