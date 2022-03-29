/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;

import Main.PrimerPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author migue
 */

//Clase la cual se encarga de escuchar y accionar un evento al momento de un jugador entrar al servidor
public class Entrar implements Listener {

    //declaracion de variables
    private PrimerPlugin primerPlugin;

    public Entrar(PrimerPlugin primerPlugin) {

        this.primerPlugin = primerPlugin;
    }

    
    //enveto a ejecutar al momento del jugador entrar
    @EventHandler
    public void alEntrar(PlayerJoinEvent evento) {
        Player jugador = evento.getPlayer();

        FileConfiguration config = primerPlugin.getConfig();
        //se verfica si el archivo Config.yml tiene el atributo spawn y tambien si este esta habilitado
        if (config.contains("Config.Spawn")) {
            if (config.getString("Config.Spawn.status").equalsIgnoreCase("true")) {
                
                Double x = Double.valueOf(config.getString("Config.Spawn.x"));
                Double y = Double.valueOf(config.getString("Config.Spawn.y"));
                Double z = Double.valueOf(config.getString("Config.Spawn.z"));
                float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
                float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
                World world = primerPlugin.getServer().getWorld(config.getString("Config.Spawn.world"));

                Location lugar = new Location(world, x, y, z, yaw, pitch);
                jugador.teleport(lugar);

            } else {
                jugador.sendMessage(primerPlugin.nombrePlugin + " Configuracion desactivada, revisar config.yml");
            }

        }
        if (config.getString("Config.mensaje-bienvenida").equals("true")) {
            String texto = "Config.mensaje-bienvenida-texto";
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString(texto)).replaceAll("%player%", jugador.getName()));
        }

    }

}
