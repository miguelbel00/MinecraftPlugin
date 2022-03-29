/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Comandos.*;
import Eventos.Entrar;
import java.io.File;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author migue
 */
//Clase main del plugin
public class PrimerPlugin extends JavaPlugin{
    //Se instancia un objeto de tipo descripcion el cual nos dara la informacuion del .yml
    PluginDescriptionFile Descripcion = getDescription();
    
    //Declaracion de variables para la ruta del archivo config.yml
    String rutaConfig;
    //plugin management that help to administrate the plugin in to server
    PluginManager pm = getServer().getPluginManager();

    //Variables del archivo descripcion .yml
    public String versionPlugin = Descripcion.getVersion();
    public String nombrePlugin = ChatColor.YELLOW+"["+ChatColor.AQUA+Descripcion.getName()+ChatColor.YELLOW+"]";
    public String nombreCreador = ChatColor.GOLD+""+Descripcion.getAuthors();
    //Se crea un map dentro de otro map que contendra los comandos y sus atributos
    public Map<String, Map<String, Object>> comandos = Descripcion.getCommands();
    
 
    //Funcion que se ejecuta al momento de iniciar el plugin
    @Override
    public void onEnable(){
        exceptionMethod();
        
    }
    
    //intento de implementacion del metodo try catch para manejo de excepciones
    public void exceptionMethod(){
           try{
    
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"--------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(nombrePlugin+ChatColor.WHITE+" Ha sido activado (version: "+ChatColor.RED+versionPlugin+ChatColor.WHITE+")");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"--------------------------------------------------");
        //Llama a la funcion que trae los comandos creados
        registrarConfig();
        registrarEventos();
        Comandos();

        }catch(Exception e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"--------------------------------------------------");
            Bukkit.getConsoleSender().sendMessage(nombrePlugin+ChatColor.WHITE+" Se encontraron errores, comunicarse con el desarrollador");
            Bukkit.getConsoleSender().sendMessage(nombrePlugin+ChatColor.WHITE+" Desactivando plugin por seguridad");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"--------------------------------------------------");
            
        }
        
    }
    
    
    //Funcion que se ejecuta al momento de finalizar el plugin
    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"--------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(nombrePlugin+ChatColor.WHITE+" Ha sido desactivado (version: "+ChatColor.RED+versionPlugin+ChatColor.WHITE+")");
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"--------------------------------------------------");
        pm.disablePlugin(this);
    }

    //Funcion que contiene los comandos creados y les asigna una palabra en el minecraft
    public void Comandos(){
        this.getCommand("pp").setExecutor(new Principal(this));
        
    }
    //Funcion para registrar los eventos
    public void registrarEventos(){
            pm.registerEvents(new Entrar(this), this);
        
    }
    
    
    public void registrarConfig(){
        
        File config = new File(this.getDataFolder(),"config.yml");
        rutaConfig = config.getPath();
        if(!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
            
        }
    }
    
    
    
}
