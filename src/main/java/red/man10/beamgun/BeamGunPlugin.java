package red.man10.beamgun;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class BeamGunPlugin extends JavaPlugin implements Listener {


    String adminPermission = "man10.beamgun.admin";
    String userPermission = "man10.beamgun.use";
    String  prefix = "[§bBeamGun§f] ";

    //      ユーザーをキーにしたアイテム情報
    HashMap<UUID,BeamGun> map = new HashMap<UUID,BeamGun>();
    ArrayList<EntityLink> links = new ArrayList<EntityLink>();

    BeamGun getObject(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(!map.containsKey(p.getUniqueId())){
            return null;
        }
        return  map.get(uuid);
    }
    boolean isController(ItemStack item){

        return true;
    }
    public void giveController(Player p,String name){
        BeamGun bg = new BeamGun(this,name);
        bg.giveController(p,name);

        //      新規作成
        /////////////////////////////////////
       // BeamGun bg = new BeamGun();
        map.put(p.getUniqueId(),bg);
    }
    public void onTickTimer(){
        for(EntityLink link : links){
            link.drawLine();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents (this,this);
        getCommand("mbg").setExecutor(new BeamGunCommand(this));

        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                onTickTimer();
            }
        }, 0, 4);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void RightClick(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
     //   p.sendMessage("ee:"+e.getEventName());

    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if(!isController(p.getInventory().getItemInMainHand())){
            return;
        }
        //p.sendMessage(e.getAction().name());



        BeamGun bg = getObject(e.getPlayer().getUniqueId());
        if(bg != null){
            bg.onPlayerInteract(e);
        }
                /*
        //      コントローラーの右クリ
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK ) {
            Player p = e.getPlayer();
            if(isController(p.getInventory().getItemInMainHand())){
               // e.setCancelled(true);
            }
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK ) {
            Player p = e.getPlayer();
            //      リモコンでないなら
            if (!isController(p.getInventory().getItemInMainHand())) {
                return;
            }
        }
        */
/*
            /////////////////////////////////////
            //      すでに登録済み
            /////////////////////////////////////
            if(map.containsKey(p.getUniqueId())){
                SkyWalker sw = map.get(p.getUniqueId());
                if(sw != null){
                    sw.delete();
                    map.remove(p.getUniqueId());
                    p.setWalkSpeed((float).2);

                }
                return;
            }
            /////////////////////////////////////
            //      新規作成
            /////////////////////////////////////
            SkyWalker sw = new SkyWalker(this);
            map.put(p.getUniqueId(),sw);
            p.setVelocity(p.getVelocity().setY(1));
            sw.pos = new BlockPlace(p.getLocation());
            if(sw.pos != null){
                Location l = sw.pos.getLocation();
                //sw.pos.getLocation().getWorld().playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH ,1, 0);
                l.getWorld().playSound(l, Sound.BLOCK_CHORUS_FLOWER_GROW ,1, 0);
                p.setWalkSpeed((float)0.5);



                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    public void run() {
                        if(sw.isClosed){
                            sw.isClosed = false;
                            p.sendMessage(prefix + "You called SkyWalker.");
                        }
                    }
                }, 10);

            }
        }
*/

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        BeamGun bg = getObject(e.getPlayer().getUniqueId());
        if(bg == null){
            return;
        }
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {

    }
}
