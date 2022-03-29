/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comandos;

import Main.PrimerPlugin;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author migue
 */
public class Principal implements CommandExecutor {

    //declaracion de variable privada tipo plugin
    private PrimerPlugin primerPlugin;

    //constructor de la clase
    public Principal(PrimerPlugin primerPlugin) {
        this.primerPlugin = primerPlugin;
    }

    //clase abstracta en la cual se configuran todos lso comandos
    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        //se verifica si el ente que lo envia es un jugador
        if (sender instanceof Player) {
            //se crea un archivo tipo configuracion
            FileConfiguration config = primerPlugin.getConfig();

            //se crea un objeto tipo jugador el cual se convertira el ente que envia el comando
            Player jugador = (Player) sender;
            //se evalua si tiene argumentos
            if (args.length > 0) {
                //si el argumento es version mostrara un mensaje con la version del plugin
                if (args[0].equalsIgnoreCase("version")) {
                    jugador.sendMessage(primerPlugin.nombrePlugin + " se ejecuta en la version " + primerPlugin.versionPlugin);
                    return true;
                    
                    //si el argumento es help este le mostrara todos los comandos y sus descripciones almacenadas en plugin.yml
                } else if (args[0].equalsIgnoreCase("help")) {

                    jugador.sendMessage(primerPlugin.nombrePlugin + "--- Help ---");
                    for (String k : primerPlugin.comandos.keySet()) {
                        Map<String, Object> v = primerPlugin.comandos.get(k);
                        for (String k2 : v.keySet()) {
                            jugador.sendMessage(primerPlugin.nombrePlugin + " " + k + ": " + v.get(k2));
                        }
                    }
                    return true;

                    //si el argumento es creador este le mostrara un mensaje acerca de quien es el creador
                } else if (args[0].equalsIgnoreCase("creador")) {

                    jugador.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
                    jugador.sendMessage(primerPlugin.nombrePlugin + " Fue creado por " + primerPlugin.nombreCreador);
                    jugador.sendMessage(ChatColor.BLUE + "--------------------------------------------------");
                    return true;

                    //Si el argumento es spawn este evaluara si existe este apartado en el archiconf.yml y tambien si el atributo status esta activa
                    //en caso negativo este simplenmente mostrara un mensaje, en caso afirmativo este obtendra la informacion sobre la ubicacion almacenada
                } else if (args[0].equalsIgnoreCase("spawn")) {
                    if (config.contains("Config.Spawn")) {
                        
                        if (config.getString("Config.Spawn.status").equalsIgnoreCase("true")) {
                            
                            //obtenemos las variables de el archivo config.yml
                            Double x = Double.valueOf(config.getString("Config.Spawn.x"));
                            Double y = Double.valueOf(config.getString("Config.Spawn.y"));
                            Double z = Double.valueOf(config.getString("Config.Spawn.z"));
                            float yaw = Float.valueOf(config.getString("Config.Spawn.yaw"));
                            float pitch = Float.valueOf(config.getString("Config.Spawn.pitch"));
                            World world = primerPlugin.getServer().getWorld(config.getString("Config.Spawn.world"));
                            Location lugar = new Location(world, x, y, z, yaw, pitch);
                            jugador.teleport(lugar);
                            jugador.sendMessage(primerPlugin.nombrePlugin + " Teletransportado al spawn");
                            return true;
                        } else {
                            jugador.sendMessage(primerPlugin.nombrePlugin + " Configuracion desactivada, revisar config.yml");
                            return false;
                        }
                    } else {

                        jugador.sendMessage(primerPlugin.nombrePlugin + " El spawn del servidor no ha sido configurado");
                        return false;

                    }

                    //Si el argumento es sespawn este obtendra las coordenadas del jugador y las almacenara en el archivo config.yml
                    //en donde se utilizaran para consultar el spawn
                } else if (args[0].equalsIgnoreCase("setspawn")) {

                    if (config.getString("Config.Spawn.status").equalsIgnoreCase("true")) {
                        Location lugar = jugador.getLocation();
                        Double x = lugar.getX();
                        Double y = lugar.getY();
                        Double z = lugar.getZ();
                        float yaw = lugar.getYaw();
                        float pitch = lugar.getPitch();
                        String world = lugar.getWorld().getName();
                        String status = "true";

                        config.set("Config.Spawn.x", x);
                        config.set("Config.Spawn.y", y);
                        config.set("Config.Spawn.z", z);
                        config.set("Config.Spawn.yaw", yaw);
                        config.set("Config.Spawn.pitch", pitch);
                        config.set("Config.Spawn.world", world);
                        config.set("Config.Spawn.status", status);
                        primerPlugin.saveConfig();
                        jugador.sendMessage(primerPlugin.nombrePlugin + " El spawn del servidor se configuro correctamente");

                        return true;
                    } else {
                        jugador.sendMessage(primerPlugin.nombrePlugin + " Configuracion desactivada, revisar config.yml");
                        return false;
                    }
                    //recarga el plugin
                } else if (args[0].equalsIgnoreCase("reload")) {

                    primerPlugin.reloadConfig();
                    jugador.sendMessage(primerPlugin.nombrePlugin + " Plugin recargado correctamente");
                    return true;

                } else {

                    jugador.sendMessage(primerPlugin.nombrePlugin + " El Comando No existe!!");
                    return false;

                }
            } else {
                jugador.sendMessage(primerPlugin.nombrePlugin + " Usa \"/primerplugin help\" para obtener ayuda");
                return false;
            }

        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + primerPlugin.nombrePlugin + ChatColor.RED + " No puedes ejecutarlo en la consola");
            return false;
        }

    }

}
