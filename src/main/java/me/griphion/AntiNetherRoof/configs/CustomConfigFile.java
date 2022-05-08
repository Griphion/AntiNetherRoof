package me.griphion.AntiNetherRoof.configs;

import me.griphion.AntiNetherRoof.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public abstract class CustomConfigFile {

    private final String fileName;
    private final String directory;
    private FileConfiguration fileConfiguration = null;
    private File configFile = null;

//    public static final Map<directoryEnum,String> directoryPath;
//    static {
//        directoryPath = new HashMap<>();
//        directoryPath.put(directoryEnum.NONE, "");
//        directoryPath.put(directoryEnum.DRAGON,"/Dragones");
//        directoryPath.put(directoryEnum.LOOTTABLE,"/LootTables");
//    }
//    private final directoryEnum directory;
//    public enum directoryEnum{
//        NONE,
//        DRAGON,
//        LOOTTABLE
//    }

    public CustomConfigFile(String fileName, String directory){
        this.fileName = fileName;
        this.directory = directory;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null) this.configFile = new File(Core.getInstance().getDataFolder() + directory, fileName + ".yml");

        fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = Core.getInstance().getResource(fileName + ".yml");
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.fileConfiguration.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.fileConfiguration == null) reloadConfig();
        return this.fileConfiguration;
    }

    public void saveConfig(){
        if(this.fileConfiguration == null || this.configFile == null) return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            Core.getInstance().getLogger().log(Level.SEVERE, "No se pudo guardar la Config de " + this.configFile, e);
        }
    }

    public void saveDefaultConfig(){
        if(this.configFile == null){
            this.configFile = new File(Core.getInstance().getDataFolder() + directory, fileName + ".yml");
        }
        if(!this.configFile.exists()){
            try{
                if(configFile.createNewFile()){
                    Core.getInstance().getLogger().log(Level.INFO, "Se ha creado el archivo de configuración: <" + this.configFile + "> exitosamente.");
                }
            }catch (Exception e){
                Core.getInstance().getLogger().log(Level.SEVERE, "No se pudo crear el archivo de configuración: " + this.configFile, e);
            }
        }
    }

    public void saveCopyDefaults(){
        fileConfiguration.options().copyDefaults(true);
        saveConfig();
    }

    public boolean createConfigDirectory(){
        if(!directory.equals("")){
           return new File(Core.getInstance().getDataFolder() + directory).mkdirs();
        }
        return false;
    }

    public void deleteFile() {
        if(!configFile.delete()){
            Bukkit.getLogger().log(Level.INFO, "No se pudo eliminar el archivo: '" + fileName + "'. Este se eliminará cuando se cierre el servidor.");
            configFile.deleteOnExit();
        }

    }

    public boolean isEmpty() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Core.getInstance().getDataFolder() + directory + "/" + fileName + ".yml"));
            return br.readLine() == null;
        }catch (IOException e){
            Bukkit.getLogger().log(Level.SEVERE, "Error al leer el archivo: " + this.fileName, e);
            return true;
        }

    }

    public void loadConfigFromJar(final String config, final String path) {

        File configFile = new File(Core.getInstance().getDataFolder() + path, config);

        if (!configFile.exists()) {

            try (InputStream fis = Core.getInstance().getClass().getResourceAsStream(path + "/" + config); FileOutputStream fos = new FileOutputStream(configFile)) {
                byte[] buf = new byte[1024];
                int i;
                while (true) {
                    assert fis != null;
                    if ((i = fis.read(buf)) == -1) break;
                    fos.write(buf, 0, i);
                }
            } catch (IOException | AssertionError e) {
                Core.getInstance().getServer().getLogger().log(Level.SEVERE, "Error al cargar la config del JAR!", e);
            }

        }

    }
}
